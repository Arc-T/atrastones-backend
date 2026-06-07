-- Procedure to get applicable discounts for a cart
DELIMITER
//
CREATE PROCEDURE sp_get_cart_discounts(
    IN p_user_id INT,
    IN p_cart_total DECIMAL (12, 2),
    IN p_product_ids JSON -- JSON array of product IDs: [1, 2, 3]
)
BEGIN
    -- Get ORDER-level discounts
SELECT d.*,
       dt.code                                                   as discount_type_code,
       ds.code                                                   as discount_scope_code,
       fn_get_discount_conditions(d.id)                          as conditions,
       -- Check basic eligibility
       fn_check_basic_eligibility(d.id, p_user_id, p_cart_total) as is_basic_eligible
FROM vw_active_discounts d
         INNER JOIN discount_types dt ON d.discount_type = dt.code
         INNER JOIN discount_scopes ds ON d.discount_scope = ds.code
WHERE d.discount_scope = 'ORDER'
ORDER BY d.priority DESC, d.id;

-- Get PRODUCT-level discounts for specific products
IF
p_product_ids IS NOT NULL AND JSON_LENGTH(p_product_ids) > 0 THEN
SELECT DISTINCT d.*,
                dt.code                                                   as discount_type_code,
                ds.code                                                   as discount_scope_code,
                fn_get_discount_conditions(d.id)                          as conditions,
                t.target_id                                               as applicable_product_id,
                -- Check basic eligibility
                fn_check_basic_eligibility(d.id, p_user_id, p_cart_total) as is_basic_eligible
FROM vw_active_discounts d
         INNER JOIN discount_types dt ON d.discount_type = dt.code
         INNER JOIN discount_scopes ds ON d.discount_scope = ds.code
         INNER JOIN discount_targets t ON d.id = t.discount_id
WHERE d.discount_scope IN ('PRODUCT', 'CATEGORY')
  AND (
    (t.target_type = 'PRODUCT' AND JSON_CONTAINS(p_product_ids, CAST(t.target_id AS JSON)))
        OR
    (t.target_type = 'CATEGORY' AND t.target_id IN (SELECT category_id
                                                    FROM products
                                                    WHERE JSON_CONTAINS(p_product_ids, CAST(id AS JSON))))
    )
  AND NOT EXISTS (SELECT 1
                  FROM discount_exclusions ex
                  WHERE ex.discount_id = d.id
                    AND ex.entity_type = 'PRODUCT'
                    AND ex.entity_id IN (SELECT CAST(j.val AS UNSIGNED)
                                         FROM JSON_TABLE(p_product_ids, '$[*]' COLUMNS (val INT PATH '$')) j))
ORDER BY d.priority DESC, d.id;
END IF;
END
//
DELIMITER ;

-- Procedure to get product display price with discount info
DELIMITER
//
CREATE PROCEDURE sp_get_product_price_display(
    IN p_product_id INT,
    IN p_user_id INT,
    IN p_quantity INT
)
BEGIN
    DECLARE
v_base_price DECIMAL(12, 2);
    DECLARE
v_sale_price DECIMAL(12, 2);
    DECLARE
v_current_price DECIMAL(12, 2);
    DECLARE
v_category_id INT;

    -- Get product base info
SELECT base_price,
       sale_price,
       COALESCE(sale_price, base_price),
       category_id
INTO v_base_price, v_sale_price, v_current_price, v_category_id
FROM products
WHERE id = p_product_id
  AND deleted_at IS NULL;

-- Get applicable discounts
SELECT d.id,
       d.name,
       d.value,
       d.max_discount_amount,
       d.priority,
       d.is_stackable,
       dt.code                          as discount_type,
       ds.code                          as discount_scope,
       d.selection_type,
       fn_get_discount_conditions(d.id) as conditions
FROM vw_active_discounts d
         INNER JOIN discount_types dt ON d.discount_type = dt.code
         INNER JOIN discount_scopes ds ON d.discount_scope = ds.code
WHERE d.id IN (SELECT discount_id
               FROM discount_targets
               WHERE (
                         (target_type = 'PRODUCT' AND target_id = p_product_id)
                             OR
                         (target_type = 'CATEGORY' AND target_id = v_category_id)
                         ))
  AND NOT EXISTS (SELECT 1
                  FROM discount_exclusions
                  WHERE discount_id = d.id
                    AND entity_type = 'PRODUCT'
                    AND entity_id = p_product_id)
ORDER BY d.priority DESC, d.id;

-- Return base price info
SELECT p_product_id                   as product_id,
       v_base_price                   as base_price,
       v_sale_price                   as sale_price,
       v_current_price                as current_price,
       v_category_id                  as category_id,
       p_quantity                     as requested_quantity,
       (v_current_price * p_quantity) as total_price;
END
//
DELIMITER ;

-- Procedure to update discount usage (atomic operation)
DELIMITER
//
CREATE PROCEDURE sp_record_discount_usage(
    IN p_discount_id INT,
    IN p_coupon_id INT,
    IN p_user_id INT,
    IN p_order_id INT,
    IN p_amount_before DECIMAL (12, 2),
    IN p_discount_amount DECIMAL (12, 2),
    IN p_items_count INT
)
BEGIN
    DECLARE
EXIT HANDLER FOR SQLEXCEPTION
BEGIN
ROLLBACK;
RESIGNAL;
END;

START TRANSACTION;

-- Update global usage count
UPDATE discounts
SET used_count = used_count + 1,
    updated_at = NOW()
WHERE id = p_discount_id;

-- Update coupon usage if applicable
IF
p_coupon_id IS NOT NULL THEN
UPDATE discount_coupons
SET used_count = used_count + 1,
    updated_at = NOW()
WHERE id = p_coupon_id;
END IF;

    -- Update per-customer usage
INSERT INTO discount_customer_usage (discount_id, user_id, usage_count, first_used, last_used)
VALUES (p_discount_id, p_user_id, 1, NOW(), NOW()) ON DUPLICATE KEY
UPDATE usage_count = usage_count + 1,
    last_used = NOW(),
    updated_at = NOW();

-- Record the usage
INSERT INTO discount_usage (discount_id,
                            discount_coupon_id,
                            user_id,
                            order_id,
                            order_amount_before,
                            discount_amount,
                            order_amount_after,
                            applied_items_count,
                            metadata)
VALUES (p_discount_id,
        p_coupon_id,
        p_user_id,
        p_order_id,
        p_amount_before,
        p_discount_amount,
        p_amount_before - p_discount_amount,
        p_items_count,
        JSON_OBJECT(
                'recorded_at', NOW(),
                'system', 'sp_record_discount_usage'
        ));
COMMIT;
END
//
DELIMITER ;
