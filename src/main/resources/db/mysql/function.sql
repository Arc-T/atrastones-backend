-- Function to check basic discount eligibility (simple conditions)
DELIMITER
//
CREATE FUNCTION check_discount_eligibility(
    p_discount_id INT,
    p_user_id INT,
    p_order_amount DECIMAL (12,2)
) RETURNS BOOLEAN
    READS SQL DATA
    DETERMINISTIC
BEGIN
    DECLARE
v_min_amount DECIMAL(12,2);
    DECLARE
v_user_usage INT;

-- Check minimum order amount condition
SELECT CAST(`value` AS DECIMAL(12, 2))
INTO v_min_amount
FROM discount_conditions
WHERE discount_id = p_discount_id
  AND type = 'MIN_ORDER_AMOUNT'
  AND operator = 'GTE' LIMIT 1;

IF
v_min_amount IS NOT NULL AND p_order_amount < v_min_amount THEN
        RETURN FALSE;
END IF;

    -- Check per-customer usage limit
SELECT usage_count
INTO v_user_usage
FROM discount_customer_usage
WHERE discount_id = p_discount_id
  AND user_id = p_user_id;

IF
v_user_usage IS NOT NULL THEN
        DECLARE
v_max_per_customer INT;
SELECT max_uses_per_customer
INTO v_max_per_customer
FROM discounts
WHERE id = p_discount_id;

IF
v_max_per_customer IS NOT NULL AND v_user_usage >= v_max_per_customer THEN
            RETURN FALSE;
END IF;
END IF;

RETURN TRUE;
END
//
DELIMITER ;

-- Function to get discount conditions as JSON
DELIMITER
//
CREATE FUNCTION fn_get_discount_conditions(p_discount_id INT) RETURNS JSON
    READS SQL DATA
BEGIN
    DECLARE
result JSON;

SELECT JSON_ARRAYAGG(
               JSON_OBJECT(
                       'condition_group', condition_group,
                       'logical_operator', logical_operator,
                       'type', type,
                       'operator', operator,
                       'value', value,
                       'value_secondary', value_secondary,
                       'is_negated', is_negated
               )
       )
INTO result
FROM discount_conditions
WHERE discount_id = p_discount_id
ORDER BY condition_group, id;

RETURN COALESCE(result, JSON_ARRAY());
END
//
DELIMITER ;

DELIMITER
//
CREATE FUNCTION fn_calculate_discount_amount(
    `product_discount_type` VARCHAR (20),
    `product_discount_value` DECIMAL (12,2),
    `product_max_amount` DECIMAL (12,2),
    `product_base_amount` DECIMAL (12,2)
) RETURNS DECIMAL(12, 2)
    DETERMINISTIC
BEGIN
    DECLARE
`v_discount_amount` DECIMAL(12,2);

CASE `product_discount_type`
        WHEN 'PERCENT' THEN
            SET `v_discount_amount` = (`product_base_amount` * `product_discount_value` / 100);
            IF
`product_max_amount` IS NOT NULL AND `v_discount_amount` > `product_max_amount` THEN
                SET `v_discount_amount` = `product_max_amount`;
END IF;
WHEN 'FIXED' THEN
            SET `v_discount_amount` = MIN(`product_discount_value`, `product_base_amount`);
WHEN 'UNIT' THEN
            -- Unit discount is usually applied per item in application
            SET `v_discount_amount` = `product_discount_value`;
ELSE
            SET `v_discount_amount` = 0;
END
CASE;

RETURN GREATEST(0, LEAST(`v_discount_amount`, `product_base_amount`));
END
//
DELIMITER ;