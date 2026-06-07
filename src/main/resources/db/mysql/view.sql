-- View for active discounts with basic info
CREATE
OR REPLACE VIEW vw_active_discounts AS
SELECT d.id,
       d.name,
       d.value,
       d.max_discount_amount,
       d.priority,
       d.is_stackable,
       d.stackable_group,
       d.start_date,
       d.expire_date,
       d.max_uses,
       d.max_uses_per_customer,
       d.used_count,
       dt.code as discount_type,
       ds.code as discount_scope,
       d.selection_type,
       d.description
FROM discounts d
         INNER JOIN discount_types dt ON d.discount_type_id = dt.id
         INNER JOIN discount_scopes ds ON d.discount_scope_id = ds.id
WHERE d.is_active = TRUE
  AND d.deleted_at IS NULL
  AND d.start_date <= NOW()
  AND (d.expire_date IS NULL OR d.expire_date > NOW())
  AND (d.max_uses IS NULL OR d.used_count < d.max_uses);

-- View for product pricing info (for display)
CREATE
OR REPLACE VIEW vw_product_base_pricing AS
SELECT p.id                                 as product_id,
       p.name                               as product_name,
       p.base_price,
       p.sale_price,
       COALESCE(p.sale_price, p.base_price) as current_price,
       p.category_id,
       c.name                               as category_name,
       p.sku,
       p.is_active                          as product_active,
       p.in_stock
FROM products p
         INNER JOIN categories c ON p.category_id = c.id
WHERE p.deleted_at IS NULL
  AND p.is_active = TRUE;

-- View for product discounts (just references)
CREATE
OR REPLACE VIEW vw_product_discount_references AS
SELECT p.id      as product_id,
       p.category_id,
       JSON_ARRAYAGG(
           DISTINCT JSON_OBJECT(
            'discount_id', d.id,
            'scope', ds.code,
            'type', dt.code
        )
    ) as discount_references
FROM products p
         LEFT JOIN discount_targets dtg ON (
    (dtg.target_type = 'PRODUCT' AND dtg.target_id = p.id)
        OR (dtg.target_type = 'CATEGORY' AND dtg.target_id = p.category_id)
    )
         LEFT JOIN discounts d ON dtg.discount_id = d.id
         LEFT JOIN discount_types dt ON d.discount_type_id = dt.id
         LEFT JOIN discount_scopes ds ON d.discount_scope_id = ds.id
WHERE d.is_active = TRUE
  AND d.deleted_at IS NULL
  AND d.start_date <= NOW()
  AND (d.expire_date IS NULL OR d.expire_date > NOW())
  AND NOT EXISTS (SELECT 1
                  FROM discount_exclusions ex
                  WHERE ex.discount_id = d.id
                    AND ex.entity_type = 'PRODUCT'
                    AND ex.entity_id = p.id)
GROUP BY p.id, p.category_id;

-- View for quick product display (combines pricing + discount references)
CREATE
OR REPLACE VIEW vw_product_display AS
SELECT p.product_id,
       p.product_name,
       p.base_price,
       p.sale_price,
       p.current_price,
       p.category_id,
       p.category_name,
       p.sku,
       p.product_active,
       p.in_stock,
       COALESCE(d.discount_references, JSON_ARRAY()) as applicable_discounts
FROM vw_product_base_pricing p
         LEFT JOIN vw_product_discount_references d ON p.product_id = d.product_id;