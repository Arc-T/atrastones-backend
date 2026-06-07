CREATE
DATABASE IF NOT EXISTS `atrastones` CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_unicode_ci';
USE
`atrastones`;

-- Roles for RBAC
CREATE TABLE IF NOT EXISTS `roles`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `name`
    VARCHAR
(
    50
) NOT NULL,
    `description` VARCHAR
(
    255
) DEFAULT NULL COMMENT 'Role description for clarity',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE
(
    `name`
)
    ) ENGINE = InnoDB COMMENT = 'Stores user roles for RBAC';

-- Permissions for RBAC
CREATE TABLE IF NOT EXISTS `permissions`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `name`
    VARCHAR
(
    100
) NOT NULL,
    `description` VARCHAR
(
    255
) DEFAULT NULL COMMENT 'Permission description',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE
(
    `name`
)
    ) ENGINE = InnoDB COMMENT = 'Stores permissions for RBAC';

-- Maps roles to permissions
CREATE TABLE IF NOT EXISTS `role_permissions`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `role_id`
    INT
    UNSIGNED
    NOT
    NULL,
    `permission_id`
    INT
    UNSIGNED
    NOT
    NULL,
    `created_at`
    TIMESTAMP
    NOT
    NULL
    DEFAULT
    CURRENT_TIMESTAMP,
    INDEX
    `idx_perm_role_role`
(
    `role_id`
),
    INDEX `idx_perm_role_perm`
(
    `permission_id`
),
    CONSTRAINT `fk_role_perm_role` FOREIGN KEY
(
    `role_id`
) REFERENCES `roles`
(
    `id`
) ON DELETE CASCADE
  ON UPDATE RESTRICT,
    CONSTRAINT `fk_perm_role_permission` FOREIGN KEY
(
    `permission_id`
) REFERENCES `permissions`
(
    `id`
)
  ON DELETE CASCADE
  ON UPDATE RESTRICT,
    UNIQUE
(
    `permission_id`,
    `role_id`
)
    ) ENGINE = InnoDB COMMENT = 'Maps roles to permissions';

-- User groups for grouping users
CREATE TABLE IF NOT EXISTS `user_groups`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `name`
    VARCHAR
(
    100
) NOT NULL,
    `description` VARCHAR
(
    255
) DEFAULT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE
(
    `name`
)
    ) ENGINE = InnoDB COMMENT = 'Groups users for role assignment';

-- Users table
CREATE TABLE IF NOT EXISTS `users`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `first_name`
    VARCHAR
(
    100
) NOT NULL,
    `last_name` VARCHAR
(
    100
) NOT NULL,
    `gender` ENUM
(
    'MALE',
    'FEMALE'
) NOT NULL,
    `phone` VARCHAR
(
    15
) NOT NULL,
    `email` VARCHAR
(
    255
) NULL,
    `password` VARCHAR
(
    255
) NOT NULL COMMENT 'Hashed password (bcrypt)',
    `user_group_id` INT UNSIGNED NOT NULL,
    `is_locked` BOOLEAN NOT NULL DEFAULT FALSE,
    `description` VARCHAR
(
    500
) DEFAULT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at` TIMESTAMP DEFAULT NULL,
    INDEX `idx_users_email`
(
    `email`
),
    INDEX `idx_users_phone`
(
    `phone`
),
    INDEX `idx_users_group`
(
    `user_group_id`
),
    CONSTRAINT `fk_users_user_group` FOREIGN KEY
(
    `user_group_id`
) REFERENCES `user_groups`
(
    `id`
)
                                        ON DELETE RESTRICT
                                        ON UPDATE RESTRICT,
    UNIQUE
(
    `email`
),
    UNIQUE
(
    `phone`
)
    ) ENGINE = InnoDB COMMENT = 'Stores user information';

-- Maps roles to user groups
CREATE TABLE IF NOT EXISTS `user_group_roles`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `role_id`
    INT
    UNSIGNED
    NOT
    NULL,
    `user_group_id`
    INT
    UNSIGNED
    NOT
    NULL,
    `created_at`
    TIMESTAMP
    NOT
    NULL
    DEFAULT
    CURRENT_TIMESTAMP,
    INDEX
    `idx_user_group_roles_role`
(
    `role_id`
),
    INDEX `idx_user_group_roles_user_group`
(
    `user_group_id`
),
    CONSTRAINT `fk_user_group_roles_role` FOREIGN KEY
(
    `role_id`
) REFERENCES `roles`
(
    `id`
) ON DELETE CASCADE
  ON UPDATE RESTRICT,
    CONSTRAINT `fk_user_group_roles_user_group` FOREIGN KEY
(
    `user_group_id`
) REFERENCES `user_groups`
(
    `id`
)
  ON DELETE CASCADE
  ON UPDATE RESTRICT,
    UNIQUE
(
    `role_id`,
    `user_group_id`
)
    ) ENGINE = InnoDB COMMENT = 'Maps roles to user groups';

-- VIP groups for premium memberships
CREATE TABLE IF NOT EXISTS `vip_groups`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `name`
    VARCHAR
(
    100
) NOT NULL,
    `description` VARCHAR
(
    255
) DEFAULT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE
(
    `name`
)
    ) ENGINE = InnoDB COMMENT = 'Stores VIP group definitions';

-- VIP memberships for users
CREATE TABLE IF NOT EXISTS `vip_memberships`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `vip_group_id`
    INT
    UNSIGNED
    NOT
    NULL,
    `user_id`
    INT
    UNSIGNED
    NOT
    NULL,
    `created_at`
    TIMESTAMP
    NOT
    NULL
    DEFAULT
    CURRENT_TIMESTAMP,
    INDEX
    `idx_vip_memberships_group`
(
    `vip_group_id`
),
    INDEX `idx_vip_memberships_user`
(
    `user_id`
),
    CONSTRAINT `fk_vip_memberships_group` FOREIGN KEY
(
    `vip_group_id`
) REFERENCES `vip_groups`
(
    `id`
) ON DELETE CASCADE
  ON UPDATE RESTRICT,
    CONSTRAINT `fk_vip_memberships_user` FOREIGN KEY
(
    `user_id`
) REFERENCES `users`
(
    `id`
)
  ON DELETE CASCADE
  ON UPDATE RESTRICT,
    UNIQUE
(
    `user_id`,
    `vip_group_id`
)
    ) ENGINE = InnoDB COMMENT = 'Tracks user VIP memberships';

-- Provinces for addresses
CREATE TABLE IF NOT EXISTS `provinces`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `name`
    VARCHAR
(
    100
) NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE
(
    `name`
)
    ) ENGINE = InnoDB COMMENT 'Stores Iran provinces';

-- User addresses
CREATE TABLE IF NOT EXISTS `addresses`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `user_id`
    INT
    UNSIGNED
    NOT
    NULL,
    `address_type`
    ENUM
(
    'HOME',
    'WORK',
    'OTHER'
) NOT NULL,
    `province_id` INT UNSIGNED NOT NULL,
    `address_line` VARCHAR
(
    255
) NOT NULL,
    `display_order` INT UNSIGNED NOT NULL DEFAULT 0,
    `postal_code` VARCHAR
(
    10
) DEFAULT NULL COMMENT 'postal code (10 digits)',
    `latitude` DECIMAL
(
    9,
    6
) DEFAULT NULL,
    `longitude` DECIMAL
(
    9,
    6
) DEFAULT NULL,
    `description` VARCHAR
(
    255
) DEFAULT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    INDEX `idx_addresses_user`
(
    `user_id`
),
    INDEX `idx_addresses_province`
(
    `province_id`
),
    CONSTRAINT `fk_addresses_user` FOREIGN KEY
(
    `user_id`
) REFERENCES `users`
(
    `id`
)
                                        ON DELETE CASCADE
                                        ON UPDATE RESTRICT,
    CONSTRAINT `fk_addresses_province` FOREIGN KEY
(
    `province_id`
) REFERENCES `provinces`
(
    `id`
)
                                        ON DELETE RESTRICT
                                        ON UPDATE RESTRICT
    ) ENGINE = InnoDB COMMENT = 'Stores user addresses';

-- User activity logs (partitioned for scalability)
CREATE TABLE IF NOT EXISTS `user_logs`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `user_id`
    INT
    UNSIGNED
    NOT
    NULL,
    `action_type`
    VARCHAR
(
    50
) NOT NULL COMMENT 'e.g., LOGIN, PURCHASE',
    `action_id` VARCHAR
(
    50
) NOT NULL COMMENT 'Reference to entity (e.g., order ID)',
    `ip_address` VARCHAR
(
    45
) NOT NULL COMMENT 'Supports IPv4/IPv6',
    `user_agent` VARCHAR
(
    255
) NOT NULL,
    `description` VARCHAR
(
    500
) DEFAULT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX `idx_logs_user`
(
    `user_id`,
    `created_at`
),
    INDEX `idx_logs_action_type`
(
    `action_type`
),
    CONSTRAINT `fk_logs_user` FOREIGN KEY
(
    `user_id`
) REFERENCES `users`
(
    `id`
) ON DELETE CASCADE
  ON UPDATE RESTRICT
    ) ENGINE = InnoDB COMMENT = 'Logs user activities';

CREATE TABLE IF NOT EXISTS `shops`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `name`
    VARCHAR
(
    100
) NOT NULL,
    `phone` VARCHAR
(
    15
) NOT NULL COMMENT 'Shop contact number',
    `address_id` INT UNSIGNED NOT NULL,
    `status` ENUM
(
    'ACTIVE',
    'INACTIVE',
    'PENDING'
) NOT NULL DEFAULT 'PENDING',
    `description` VARCHAR
(
    500
) NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at` TIMESTAMP DEFAULT NULL,
    INDEX `idx_shops_phone`
(
    `phone`
),
    INDEX `idx_shops_address`
(
    `address_id`
),
    INDEX `idx_shops_status`
(
    `status`
),
    INDEX `idx_shops_deleted_at`
(
    `deleted_at`
),
    CONSTRAINT `fk_shops_address` FOREIGN KEY
(
    `address_id`
) REFERENCES `addresses`
(
    `id`
)
                                        ON DELETE RESTRICT
                                        ON UPDATE RESTRICT
    ) ENGINE = InnoDB COMMENT = 'Stores shop details';

-- Shops owned by users
CREATE TABLE IF NOT EXISTS `shop_members`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `shop_id`
    INT
    UNSIGNED
    NOT
    NULL,
    `user_id`
    INT
    UNSIGNED
    NOT
    NULL,
    `description`
    TEXT
    DEFAULT
    NULL,
    `created_at`
    TIMESTAMP
    NOT
    NULL
    DEFAULT
    CURRENT_TIMESTAMP,
    `updated_at`
    TIMESTAMP
    DEFAULT
    NULL
    ON
    UPDATE
    CURRENT_TIMESTAMP,
    UNIQUE
    KEY
    `unique_user_shop_membership`
(
    `user_id`
),
    INDEX `idx_shop_members_shop`
(
    `shop_id`
),
    INDEX `idx_shop_members_user`
(
    `user_id`
),
    CONSTRAINT `fk_shop_members_shop` FOREIGN KEY
(
    `shop_id`
) REFERENCES `shops`
(
    `id`
) ON DELETE CASCADE
  ON UPDATE RESTRICT,
    CONSTRAINT `fk_shop_members_user` FOREIGN KEY
(
    `user_id`
) REFERENCES `users`
(
    `id`
)
  ON DELETE CASCADE
  ON UPDATE RESTRICT
    ) ENGINE = InnoDB COMMENT = 'Shop staff members with their status';

-- Product categories (hierarchical)
CREATE TABLE IF NOT EXISTS `categories`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `name`
    VARCHAR
(
    100
) NOT NULL,
    `url` VARCHAR
(
    255
) NOT NULL,
    `icon` VARCHAR
(
    50
) DEFAULT NULL,
    `parent_id` INT UNSIGNED DEFAULT NULL,
    `display_order` INT UNSIGNED NOT NULL DEFAULT 0,
    `description` VARCHAR
(
    255
) DEFAULT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    INDEX `idx_categories_parent`
(
    `parent_id`
),
    INDEX `idx_categories_url`
(
    `url`
),
    CONSTRAINT `fk_categories_parent` FOREIGN KEY
(
    `parent_id`
) REFERENCES `categories`
(
    `id`
)
                                        ON DELETE CASCADE
                                        ON UPDATE RESTRICT,
    UNIQUE
(
    `url`
)
    ) ENGINE = InnoDB COMMENT = 'Stores product categories';

-- Product attributes
CREATE TABLE IF NOT EXISTS `attributes`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `name`
    VARCHAR
(
    100
) NOT NULL COMMENT 'Supports Persian names',
    `category_id` INT UNSIGNED NOT NULL,
    `type` ENUM
(
    'TEXT',
    'NUMBER',
    'SELECT',
    'MULTISELECT'
) NOT NULL DEFAULT 'TEXT',
    `is_filterable` BOOLEAN NOT NULL DEFAULT FALSE,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    INDEX `idx_attributes_category`
(
    `category_id`
),
    INDEX `idx_attributes_filterable`
(
    `is_filterable`
),
    CONSTRAINT `fk_attributes_category` FOREIGN KEY
(
    `category_id`
) REFERENCES `categories`
(
    `id`
)
                                        ON DELETE CASCADE
                                        ON UPDATE RESTRICT
    ) ENGINE = InnoDB COMMENT = 'Stores product attributes';

-- Attribute values
CREATE TABLE IF NOT EXISTS `attribute_values`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `value`
    VARCHAR
(
    200
) NOT NULL COMMENT 'Supports Persian values',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE
(
    `value`
)
    ) ENGINE = InnoDB COMMENT = 'Stores possible attribute values';

-- Maps attributes to their values
CREATE TABLE IF NOT EXISTS `attribute_values_map`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `attribute_id`
    INT
    UNSIGNED
    NOT
    NULL,
    `attribute_value_id`
    INT
    UNSIGNED
    NOT
    NULL,
    `created_at`
    TIMESTAMP
    NOT
    NULL
    DEFAULT
    CURRENT_TIMESTAMP,
    INDEX
    `idx_attr_values_map_attribute`
(
    `attribute_id`
),
    INDEX `idx_attr_values_map_value`
(
    `attribute_value_id`
),
    CONSTRAINT `fk_attr_values_map_attribute` FOREIGN KEY
(
    `attribute_id`
) REFERENCES `attributes`
(
    `id`
) ON DELETE CASCADE
  ON UPDATE RESTRICT,
    CONSTRAINT `fk_attr_values_map_value` FOREIGN KEY
(
    `attribute_value_id`
) REFERENCES `attribute_values`
(
    `id`
)
  ON DELETE CASCADE
  ON UPDATE RESTRICT,
    UNIQUE
(
    `attribute_id`,
    `attribute_value_id`
)
    ) ENGINE = InnoDB COMMENT = 'Maps attributes to their possible values';

-- Product tags
CREATE TABLE IF NOT EXISTS `tags`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `name`
    VARCHAR
(
    100
) NOT NULL COMMENT 'Supports Persian names',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE
(
    `name`
)
    ) ENGINE = InnoDB COMMENT = 'Stores product tags';

-- Service groups
CREATE TABLE IF NOT EXISTS service_groups
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `name`
    VARCHAR
(
    100
) NOT NULL COMMENT 'Supports Persian names',
    `description` VARCHAR
(
    500
) DEFAULT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE
(
    `name`
)
    ) ENGINE = InnoDB COMMENT = 'Stores service group definitions';

-- Services
CREATE TABLE IF NOT EXISTS `services`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `name`
    VARCHAR
(
    100
) NOT NULL,
    `cost` DECIMAL
(
    12,
    0
) NOT NULL COMMENT 'Cost in Toman',
    `service_group_id` INT UNSIGNED NOT NULL,
    `description` VARCHAR
(
    500
) DEFAULT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at` TIMESTAMP DEFAULT NULL,
    INDEX `idx_services_group`
(
    `service_group_id`
),
    INDEX `idx_services_deleted_at`
(
    `deleted_at`
),
    CONSTRAINT `fk_services_group` FOREIGN KEY
(
    `service_group_id`
) REFERENCES service_groups
(
    `id`
)
                                        ON DELETE RESTRICT
                                        ON UPDATE RESTRICT
    ) ENGINE = InnoDB COMMENT = 'Stores services with costs in Toman';

-- Discount types - Defines how discounts are calculated
CREATE TABLE IF NOT EXISTS `discount_types`
(
    `id`
    TINYINT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `code`
    VARCHAR
(
    20
) NOT NULL COMMENT 'UNIT, PERCENT, FIXED, BUY_X_GET_Y',
    `name` VARCHAR
(
    50
) NOT NULL,
    `description` VARCHAR
(
    255
) DEFAULT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY `uk_discount_types_code`
(
    `code`
)
    ) ENGINE = InnoDB;

-- Discount scopes - Defines where discounts can be applied
CREATE TABLE IF NOT EXISTS `discount_scopes`
(
    `id`
    TINYINT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `code`
    VARCHAR
(
    20
) NOT NULL COMMENT 'ORDER, PRODUCT, CATEGORY, SHIPPING, CUSTOMER_SEGMENT',
    `name` VARCHAR
(
    50
) NOT NULL,
    `description` VARCHAR
(
    255
) DEFAULT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY `uk_discount_scopes_code`
(
    `code`
)
    ) ENGINE = InnoDB;

-- Main discounts table
CREATE TABLE IF NOT EXISTS `discounts`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `name`
    VARCHAR
(
    200
) NOT NULL COMMENT 'Discount display name',
    `discountable_type` VARCHAR
(
    200
) NOT NULL COMMENT 'like coupons',
    `discount_type_id` TINYINT UNSIGNED NOT NULL COMMENT 'References discount_types.id',
    `discount_scope_id` TINYINT UNSIGNED NOT NULL COMMENT 'References discount_scopes.id',
    `value` DECIMAL
(
    12,
    0
) NOT NULL COMMENT 'Discount amount or percentage (positive number)',
    `selection_type` ENUM
(
    'ALL',
    'INCLUDED',
    'EXCLUDED'
) NOT NULL DEFAULT 'ALL',
    `start_date` DATETIME NOT NULL,
    `expire_date` DATETIME DEFAULT NULL,
    `max_uses` INT UNSIGNED DEFAULT NULL COMMENT 'Global usage limit',
    `used_count` INT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'Track actual usage',
    `is_active` BOOLEAN NOT NULL DEFAULT TRUE,
    `description` VARCHAR
(
    500
) DEFAULT NULL,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at` DATETIME DEFAULT NULL,
    INDEX `idx_discounts_active_dates`
(
    `is_active`,
    `start_date`,
    `expire_date`
),
    INDEX `idx_discounts_type_scope`
(
    `discount_type_id`,
    `discount_scope_id`
),
    CONSTRAINT `fk_discounts_discount_type` FOREIGN KEY
(
    `discount_type_id`
) REFERENCES `discount_types`
(
    `id`
)
                                       ON DELETE RESTRICT
                                       ON UPDATE CASCADE,
    CONSTRAINT `fk_discounts_discount_scope` FOREIGN KEY
(
    `discount_scope_id`
) REFERENCES `discount_scopes`
(
    `id`
)
                                       ON DELETE RESTRICT
                                       ON UPDATE CASCADE
    ) ENGINE = InnoDB COMMENT = 'Discount rules and configuration';

-- Discount coupons/codes for customer-facing promotions
CREATE TABLE IF NOT EXISTS `discount_coupons`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `discount_id`
    INT
    UNSIGNED
    NOT
    NULL,
    `code`
    VARCHAR
(
    50
) NOT NULL COMMENT 'Customer-facing coupon code',
    `used_count` INT UNSIGNED NOT NULL DEFAULT 0,
    `valid_from` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `valid_until` DATETIME DEFAULT NULL,
    `is_active` BOOLEAN NOT NULL DEFAULT TRUE,
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE INDEX `uk_discount_coupons_code`
(
    `code`
),
    INDEX `idx_discount_coupons_discount`
(
    `discount_id`
),
    INDEX `idx_discount_coupons_validity`
(
    `is_active`,
    `valid_from`,
    `valid_until`
),
    INDEX `idx_discount_coupons_usage`
(
    `used_count`
),
    CONSTRAINT `fk_discount_coupons_discount` FOREIGN KEY
(
    `discount_id`
) REFERENCES `discounts`
(
    `id`
)
                                       ON DELETE CASCADE
                                       ON UPDATE CASCADE
    ) ENGINE = InnoDB COMMENT = 'Customer-facing discount coupons';

-- Discount eligibility conditions
CREATE TABLE IF NOT EXISTS `discount_conditions`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `discount_id`
    INT
    UNSIGNED
    NOT
    NULL,
    `condition_group`
    VARCHAR
(
    50
) DEFAULT NULL COMMENT 'Group number for AND/OR logic',
    `type` VARCHAR
(
    50
) NOT NULL COMMENT 'MIN_ORDER_AMOUNT, CUSTOMER_SEGMENT, PRODUCT_CATEGORY, etc.',
    `operator` ENUM
(
    'EQ',
    'NEQ',
    'GT',
    'GTE',
    'LT',
    'LTE',
    'IN',
    'NOT_IN',
    'BETWEEN',
    'CONTAINS'
) NOT NULL,
    `value` VARCHAR
(
    500
) DEFAULT NULL COMMENT 'Depends on condition_type',
    `value_secondary` VARCHAR
(
    500
) DEFAULT NULL COMMENT 'For BETWEEN operations',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX `idx_discount_conditions_discount_group`
(
    `discount_id`,
    `condition_group`
),
    INDEX `idx_discount_conditions_type`
(
    `type`
),
    CONSTRAINT `fk_discount_conditions_discount` FOREIGN KEY
(
    `discount_id`
) REFERENCES `discounts`
(
    `id`
) ON DELETE CASCADE
  ON UPDATE CASCADE
    ) ENGINE = InnoDB COMMENT = 'Discount eligibility conditions with flexible logic';

-- Discount target entities (what the discount applies to)
CREATE TABLE IF NOT EXISTS `discount_targets`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `discount_id`
    INT
    UNSIGNED
    NOT
    NULL,
    `target_type`
    ENUM
(
    'PRODUCT',
    'CATEGORY',
    'COLLECTION',
    'TAG',
    'CUSTOMER',
    'CUSTOMER_SEGMENT',
    'DELIVERY_METHOD'
) NOT NULL,
    `target_id` INT UNSIGNED NOT NULL COMMENT 'ID in the respective entity table',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY `uk_discount_targets`
(
    `discount_id`,
    `target_type`,
    `target_id`
),
    INDEX `idx_discount_targets_discount`
(
    `discount_id`
),
    INDEX `idx_discount_targets_entity`
(
    `target_type`,
    `target_id`
),
    CONSTRAINT `fk_discount_targets_discount` FOREIGN KEY
(
    `discount_id`
) REFERENCES `discounts`
(
    `id`
) ON DELETE CASCADE
  ON UPDATE CASCADE
    ) ENGINE = InnoDB COMMENT = 'Entities that discounts apply to (products, categories, customers, etc.)';

-- Discount usage tracking
CREATE TABLE IF NOT EXISTS `discount_usage`
(
    `id`
    BIGINT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `discountable_type`
    VARCHAR
(
    200
) NOT NULL,
    `discountable_id` INT UNSIGNED NOT NULL,
    `user_id` INT UNSIGNED NOT NULL,
    `order_id` INT UNSIGNED NOT NULL,
    `order_amount_before` DECIMAL
(
    12,
    0
) NOT NULL COMMENT 'Amount before discount (Tomans)',
    `discount_amount` DECIMAL
(
    12,
    0
) NOT NULL COMMENT 'Discount amount applied (Tomans)',
    `order_amount_after` DECIMAL
(
    12,
    0
) NOT NULL COMMENT 'Final order amount (Tomans)',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX `idx_discount_usage_discount_customer`
(
    `user_id`
),
    INDEX `idx_discount_usage_order`
(
    `order_id`
),
    INDEX `idx_discount_usage_created_at`
(
    `created_at`
),
    INDEX `idx_discount_usage_amount`
(
    `discount_amount`
)
    ) ENGINE = InnoDB COMMENT = 'Discount application tracking - all amounts in Iranian Tomans';

-- Products
CREATE TABLE IF NOT EXISTS `products`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `name`
    VARCHAR
(
    100
) NOT NULL,
    `category_id` INT UNSIGNED NOT NULL,
    `shop_id` INT UNSIGNED NOT NULL,
    `price` INT UNSIGNED NOT NULL COMMENT 'Price in Toman',
    `service_group_id` INT UNSIGNED NOT NULL,
    `status` ENUM
(
    'ACTIVE',
    'INACTIVE',
    'OUT_OF_STOCK'
) NOT NULL DEFAULT 'ACTIVE',
    `description` VARCHAR
(
    1000
) NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at` TIMESTAMP DEFAULT NULL,
    INDEX `idx_products_category`
(
    `category_id`,
    `price`,
    `status`
),
    INDEX `idx_products_shop`
(
    `shop_id`
),
    INDEX `idx_products_service_group`
(
    `service_group_id`
),
    INDEX `idx_products_status`
(
    `status`
),
    INDEX `idx_products_deleted_at`
(
    `deleted_at`
),
    CONSTRAINT `fk_products_category` FOREIGN KEY
(
    `category_id`
) REFERENCES `categories`
(
    `id`
)
                                        ON DELETE RESTRICT
                                        ON UPDATE RESTRICT,
    CONSTRAINT `fk_products_shop` FOREIGN KEY
(
    `shop_id`
) REFERENCES `shops`
(
    `id`
)
                                        ON DELETE RESTRICT
                                        ON UPDATE RESTRICT,
    CONSTRAINT `fk_products_service_group` FOREIGN KEY
(
    `service_group_id`
) REFERENCES service_groups
(
    `id`
)
                                        ON DELETE RESTRICT
                                        ON UPDATE RESTRICT
    ) ENGINE = InnoDB COMMENT = 'Stores product details with prices in Toman';

-- Products
CREATE TABLE IF NOT EXISTS `product_prices`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `product_id`
    INT
    UNSIGNED
    NOT
    NULL,
    `base_price`
    DECIMAL
(
    12,
    0
) NOT NULL,
    `is_active` BOOLEAN NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX `idx_product_prices_product`
(
    `product_id`
),
    INDEX `idx_product_prices_base_price`
(
    `base_price`
)
    );

CREATE TABLE IF NOT EXISTS `delivery_methods`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `code`
    VARCHAR
(
    200
) NOT NULL,
    `name` VARCHAR
(
    200
),
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE INDEX `uk_delivery_methods_code`
(
    `code`
)
    ) ENGINE = InnoDB;

-- Product statistics (partitioned for scalability)
CREATE TABLE IF NOT EXISTS `product_stats`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `product_id`
    INT
    UNSIGNED
    NOT
    NULL,
    `views`
    INT
    UNSIGNED
    NOT
    NULL
    DEFAULT
    0,
    `likes`
    INT
    UNSIGNED
    NOT
    NULL
    DEFAULT
    0,
    `shares`
    INT
    UNSIGNED
    NOT
    NULL
    DEFAULT
    0,
    `comments`
    INT
    UNSIGNED
    NOT
    NULL
    DEFAULT
    0,
    `ratings`
    TINYINT
    UNSIGNED
    NOT
    NULL
    CHECK
(
    ratings
    BETWEEN
    1
    AND
    5
),
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX `idx_stats_product`
(
    `product_id`
),
    CONSTRAINT `fk_stats_product` FOREIGN KEY
(
    `product_id`
) REFERENCES `products`
(
    `id`
) ON DELETE CASCADE
  ON UPDATE RESTRICT
    ) ENGINE = InnoDB COMMENT = 'Tracks product views and likes';

-- Orders
CREATE TABLE IF NOT EXISTS `orders`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `user_id`
    INT
    UNSIGNED
    NOT
    NULL,
    `address_id`
    INT
    UNSIGNED
    DEFAULT
    NULL
    COMMENT
    'Delivery address',
    `total_price`
    INT
    UNSIGNED
    NOT
    NULL
    COMMENT
    'Total in Toman',
    `description`
    VARCHAR
(
    500
) DEFAULT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at` TIMESTAMP DEFAULT NULL,
    INDEX `idx_orders_user`
(
    `user_id`
),
    INDEX `idx_orders_address`
(
    `address_id`
),
    INDEX `idx_orders_created_at`
(
    `created_at`
),
    INDEX `idx_orders_deleted_at`
(
    `deleted_at`
),
    CONSTRAINT `fk_orders_user` FOREIGN KEY
(
    `user_id`
) REFERENCES `users`
(
    `id`
)
                                        ON DELETE RESTRICT
                                        ON UPDATE RESTRICT,
    CONSTRAINT `fk_orders_address` FOREIGN KEY
(
    `address_id`
) REFERENCES `addresses`
(
    `id`
)
                                        ON UPDATE RESTRICT
    ) ENGINE = InnoDB COMMENT = 'Stores customer orders';

-- Orders
CREATE TABLE IF NOT EXISTS `order_transactions`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `order_id`
    INT
    UNSIGNED
    NOT
    NULL,
    `status`
    ENUM
(
    'PENDING',
    'PROCESSING',
    'SHIPPED',
    'DELIVERED',
    'CANCELLED',
    'RETURNED'
) NOT NULL DEFAULT 'PENDING',
    `is_last` BOOLEAN NOT NULL DEFAULT TRUE,
    `description` VARCHAR
(
    500
) DEFAULT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX `idx_order_transactions_order`
(
    `order_id`,
    `is_last`
),
    INDEX `idx_order_transactions_status`
(
    `status`
),
    INDEX `idx_orders_created_at`
(
    `created_at`
),
    CONSTRAINT `fk_order_transactions_order` FOREIGN KEY
(
    `order_id`
) REFERENCES `orders`
(
    `id`
) ON UPDATE RESTRICT
    ) ENGINE = InnoDB COMMENT = 'Stores customer orders';

-- Order details (products/services in an order)
CREATE TABLE IF NOT EXISTS `order_details`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `order_id`
    INT
    UNSIGNED
    NOT
    NULL,
    `shop_id`
    INT
    UNSIGNED
    NOT
    NULL,
    `item_type`
    ENUM
(
    'PRODUCT',
    'SERVICE'
) NOT NULL,
    `item_id` INT UNSIGNED NOT NULL,
    `quantity` INT UNSIGNED NOT NULL,
    `unit_price` INT UNSIGNED NOT NULL COMMENT 'Unit price in Toman',
    `discount_id` INT UNSIGNED DEFAULT NULL,
    `discount_amount` INT UNSIGNED DEFAULT 0 COMMENT 'Discount per item in Toman',
    `total_price` INT UNSIGNED NOT NULL COMMENT 'Total after discount in Toman',
    `status` ENUM
(
    'PENDING',
    'COMPLETED',
    'CANCELLED'
) NOT NULL DEFAULT 'PENDING',
    `description` VARCHAR
(
    500
) DEFAULT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    INDEX `idx_details_order`
(
    `order_id`
),
    INDEX `idx_details_shop`
(
    `shop_id`
),
    INDEX `idx_details_discount`
(
    `discount_id`
),
    CONSTRAINT `fk_details_order` FOREIGN KEY
(
    `order_id`
) REFERENCES `orders`
(
    `id`
)
                                        ON DELETE CASCADE
                                        ON UPDATE RESTRICT,
    CONSTRAINT `fk_details_shop` FOREIGN KEY
(
    `shop_id`
) REFERENCES `shops`
(
    `id`
)
                                        ON DELETE RESTRICT
                                        ON UPDATE RESTRICT,
    CONSTRAINT `fk_details_discount` FOREIGN KEY
(
    `discount_id`
) REFERENCES `discounts`
(
    `id`
)
                                        ON UPDATE RESTRICT
    ) ENGINE = InnoDB COMMENT = 'Stores order line items';

-- Product reviews
CREATE TABLE IF NOT EXISTS `product_reviews`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `product_id`
    INT
    UNSIGNED
    NOT
    NULL,
    `user_id`
    INT
    UNSIGNED
    NOT
    NULL,
    `action_type`
    ENUM
(
    'LIKE',
    'SHARE',
    'COMMENT',
    'SAVED',
    'QUESTION',
    'COMPARE'
) NOT NULL,
    `action_details` VARCHAR
(
    500
) DEFAULT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at` TIMESTAMP DEFAULT NULL,
    INDEX `idx_reviews_product`
(
    `product_id`
),
    INDEX `idx_reviews_user`
(
    `user_id`
),
    INDEX `idx_reviews_deleted_at`
(
    `deleted_at`
),
    CONSTRAINT `fk_reviews_product` FOREIGN KEY
(
    `product_id`
) REFERENCES `products`
(
    `id`
)
                                        ON DELETE CASCADE
                                        ON UPDATE RESTRICT,
    CONSTRAINT `fk_reviews_user` FOREIGN KEY
(
    `user_id`
) REFERENCES `users`
(
    `id`
)
                                        ON DELETE CASCADE
                                        ON UPDATE RESTRICT,
    UNIQUE
(
    `product_id`,
    `user_id`
)
    ) ENGINE = InnoDB COMMENT = 'Stores product reviews and comments';

-- Product tags mapping
CREATE TABLE IF NOT EXISTS `product_tags`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `product_id`
    INT
    UNSIGNED
    NOT
    NULL,
    `tag_id`
    INT
    UNSIGNED
    NOT
    NULL,
    `created_at`
    TIMESTAMP
    NOT
    NULL
    DEFAULT
    CURRENT_TIMESTAMP,
    INDEX
    `idx_product_tags_product`
(
    `product_id`
),
    INDEX `idx_product_tags_tag`
(
    `tag_id`
),
    CONSTRAINT `fk_product_tags_product` FOREIGN KEY
(
    `product_id`
) REFERENCES `products`
(
    `id`
) ON DELETE CASCADE
  ON UPDATE RESTRICT,
    CONSTRAINT `fk_product_tags_tag` FOREIGN KEY
(
    `tag_id`
) REFERENCES `tags`
(
    `id`
)
  ON DELETE CASCADE
  ON UPDATE RESTRICT,
    UNIQUE
(
    `product_id`,
    `tag_id`
)
    ) ENGINE = InnoDB COMMENT = 'Maps products to tags';

-- Product attribute values
CREATE TABLE IF NOT EXISTS `product_attribute_values`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `product_id`
    INT
    UNSIGNED
    NOT
    NULL,
    `attribute_id`
    INT
    UNSIGNED
    NOT
    NULL,
    `attribute_value_id`
    INT
    UNSIGNED
    NOT
    NULL,
    `created_at`
    TIMESTAMP
    NOT
    NULL
    DEFAULT
    CURRENT_TIMESTAMP,
    INDEX
    `idx_product_attr_values_product`
(
    `product_id`
),
    INDEX `idx_product_attr_values_attribute`
(
    `attribute_id`
),
    INDEX `idx_product_attr_values_value`
(
    `attribute_value_id`
),
    CONSTRAINT `fk_product_attr_values_product` FOREIGN KEY
(
    `product_id`
) REFERENCES `products`
(
    `id`
) ON DELETE CASCADE
  ON UPDATE RESTRICT,
    CONSTRAINT `fk_product_attr_values_attribute` FOREIGN KEY
(
    `attribute_id`
) REFERENCES `attributes`
(
    `id`
)
  ON DELETE CASCADE
  ON UPDATE RESTRICT,
    CONSTRAINT `fk_product_attr_values_value` FOREIGN KEY
(
    `attribute_value_id`
) REFERENCES `attribute_values`
(
    `id`
)
  ON DELETE CASCADE
  ON UPDATE RESTRICT,
    UNIQUE
(
    `product_id`,
    `attribute_id`,
    `attribute_value_id`
)
    ) ENGINE = InnoDB COMMENT = 'Maps products to attribute values';

-- Media types
CREATE TABLE IF NOT EXISTS `media_types`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `name`
    VARCHAR
(
    50
) NOT NULL,
    `description` VARCHAR
(
    255
) DEFAULT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE
(
    `name`
)
    ) ENGINE = InnoDB COMMENT = 'Stores media type definitions';

-- Product media
CREATE TABLE IF NOT EXISTS `product_media`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `product_id`
    INT
    UNSIGNED
    NOT
    NULL,
    `media_type_id`
    INT
    UNSIGNED
    NOT
    NULL,
    `url`
    VARCHAR
(
    255
) NOT NULL COMMENT 'URL to media file',
    `display_order` INT UNSIGNED NOT NULL DEFAULT 0,
    `extension` VARCHAR
(
    10
) NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX `idx_media_url`
(
    `url`
),
    INDEX `idx_media_product`
(
    `product_id`
),
    INDEX `idx_media_type`
(
    `media_type_id`
),
    CONSTRAINT `fk_media_product` FOREIGN KEY
(
    `product_id`
) REFERENCES `products`
(
    `id`
) ON DELETE CASCADE
  ON UPDATE RESTRICT,
    CONSTRAINT `fk_media_type` FOREIGN KEY
(
    `media_type_id`
) REFERENCES `media_types`
(
    `id`
)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT
    ) ENGINE = InnoDB COMMENT = 'Stores product images/videos';

-- Wishlist
CREATE TABLE IF NOT EXISTS `wishlist`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `user_id`
    INT
    UNSIGNED
    NOT
    NULL,
    `product_id`
    INT
    UNSIGNED
    NOT
    NULL,
    `created_at`
    TIMESTAMP
    NOT
    NULL
    DEFAULT
    CURRENT_TIMESTAMP,
    INDEX
    `idx_wishlist_user`
(
    `user_id`
),
    INDEX `idx_wishlist_product`
(
    `product_id`
),
    CONSTRAINT `fk_wishlist_user` FOREIGN KEY
(
    `user_id`
) REFERENCES `users`
(
    `id`
) ON DELETE CASCADE
  ON UPDATE RESTRICT,
    CONSTRAINT `fk_wishlist_product` FOREIGN KEY
(
    `product_id`
) REFERENCES `products`
(
    `id`
)
  ON DELETE CASCADE
  ON UPDATE RESTRICT,
    UNIQUE
(
    `user_id`,
    `product_id`
)
    ) ENGINE = InnoDB COMMENT = 'Stores user wishlist';

-- Order statuses
CREATE TABLE IF NOT EXISTS `order_statuses`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `name`
    VARCHAR
(
    50
) NOT NULL,
    `description` VARCHAR
(
    255
) DEFAULT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE
(
    `name`
)
    ) ENGINE = InnoDB COMMENT = 'Stores order status definitions';

-- Order status history
CREATE TABLE IF NOT EXISTS `order_status_history`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `order_id`
    INT
    UNSIGNED
    NOT
    NULL,
    `status_id`
    INT
    UNSIGNED
    NOT
    NULL,
    `description`
    VARCHAR
(
    500
) DEFAULT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX `idx_transactions_order`
(
    `order_id`,
    `created_at`
),
    INDEX `idx_transactions_status`
(
    `status_id`
),
    CONSTRAINT `fk_transactions_order` FOREIGN KEY
(
    `order_id`
) REFERENCES `orders`
(
    `id`
) ON DELETE CASCADE
  ON UPDATE RESTRICT,
    CONSTRAINT `fk_transactions_status` FOREIGN KEY
(
    `status_id`
) REFERENCES `order_statuses`
(
    `id`
)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT
    ) ENGINE = InnoDB COMMENT = 'Tracks order status changes';

-- Invoice types
CREATE TABLE IF NOT EXISTS `invoice_types`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `name`
    VARCHAR
(
    50
) NOT NULL,
    `description` VARCHAR
(
    255
) DEFAULT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE
(
    `name`
)
    ) ENGINE = InnoDB COMMENT = 'Stores invoice type definitions';

-- Invoices
CREATE TABLE IF NOT EXISTS `invoices`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `order_id`
    INT
    UNSIGNED
    NOT
    NULL,
    `invoice_type_id`
    INT
    UNSIGNED
    NOT
    NULL,
    `total_price`
    INT
    UNSIGNED
    NOT
    NULL
    COMMENT
    'Total in Toman',
    `description`
    VARCHAR
(
    500
) DEFAULT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX `idx_invoices_order`
(
    `order_id`
),
    INDEX `idx_invoices_type`
(
    `invoice_type_id`
),
    CONSTRAINT `fk_invoices_order` FOREIGN KEY
(
    `order_id`
) REFERENCES `orders`
(
    `id`
) ON DELETE CASCADE
  ON UPDATE RESTRICT,
    CONSTRAINT `fk_invoices_type` FOREIGN KEY
(
    `invoice_type_id`
) REFERENCES `invoice_types`
(
    `id`
)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT
    ) ENGINE = InnoDB COMMENT = 'Stores invoices for orders';

-- Payments
CREATE TABLE IF NOT EXISTS `payments`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `user_id`
    INT
    UNSIGNED
    NOT
    NULL,
    `order_id`
    INT
    UNSIGNED
    NOT
    NULL,
    `payment_method`
    ENUM
(
    'CARD',
    'WEB',
    'APP',
    'CASH'
) NOT NULL,
    `amount` INT UNSIGNED NOT NULL COMMENT 'Amount in Toman',
    `status` ENUM
(
    'PENDING',
    'PAID',
    'FAILED',
    'REFUNDED'
) NOT NULL,
    `authority` VARCHAR
(
    100
) DEFAULT NULL COMMENT 'Payment gateway authority code',
    `reference_id` VARCHAR
(
    100
) DEFAULT NULL COMMENT 'Gateway reference ID',
    `fee_type` VARCHAR
(
    50
) DEFAULT NULL,
    `fee` INT UNSIGNED DEFAULT NULL COMMENT 'Fee in Toman',
    `description` VARCHAR
(
    500
) DEFAULT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX `idx_payments_user`
(
    `user_id`
),
    INDEX `idx_payments_order`
(
    `order_id`
),
    INDEX `idx_payments_reference`
(
    `reference_id`
),
    CONSTRAINT `fk_payments_user` FOREIGN KEY
(
    `user_id`
) REFERENCES `users`
(
    `id`
) ON DELETE RESTRICT
  ON UPDATE RESTRICT,
    CONSTRAINT `fk_payments_order` FOREIGN KEY
(
    `order_id`
) REFERENCES `orders`
(
    `id`
)
  ON DELETE CASCADE
  ON UPDATE RESTRICT
    ) ENGINE = InnoDB COMMENT = 'Stores payment transactions';

# -- Order remaining balances
#
CREATE TABLE IF NOT EXISTS `order_remaining_balances`
    #
(
    #
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    #
    `order_id`
    INT
    UNSIGNED
    NOT
    NULL,
    #
    `payment_id`
    INT
    UNSIGNED
    NOT
    NULL,
    #
    `remaining_amount`
    INT
    UNSIGNED
    UNSIGNED
    NOT
    NULL
    COMMENT
    'Remaining balance in Toman',
    #
    `created_at`
    TIMESTAMP
    NOT
    NULL
    DEFAULT
    CURRENT_TIMESTAMP,
    #
    INDEX
    `idx_balances_order`
(
    `order_id`
),
    # INDEX `idx_balances_payment`
(
    `payment_id`
),
    # CONSTRAINT `fk_balances_order` FOREIGN KEY
(
    `order_id`
) REFERENCES `orders`
(
    `id`
) ON DELETE CASCADE
  ON UPDATE RESTRICT,
    # CONSTRAINT `fk_balances_payment` FOREIGN KEY
(
    `payment_id`
) REFERENCES `payments`
(
    `id`
)
  ON DELETE CASCADE
  ON UPDATE RESTRICT
    # ) ENGINE = InnoDB COMMENT = 'Tracks remaining order balances';

-- SMS templates
CREATE TABLE IF NOT EXISTS `sms_templates`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `name`
    VARCHAR
(
    100
) NOT NULL,
    `message` TEXT NOT NULL COMMENT 'Supports Persian messages',
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE
(
    `name`
)
    ) ENGINE = InnoDB COMMENT = 'Stores SMS templates';

-- SMS statuses
CREATE TABLE IF NOT EXISTS `sms_statuses`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `code`
    VARCHAR
(
    50
) NOT NULL,
    `status` VARCHAR
(
    100
) NOT NULL,
    `description` VARCHAR
(
    255
) DEFAULT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE
(
    `code`
)
    ) ENGINE = InnoDB COMMENT = 'Stores SMS status codes';

-- SMS
CREATE TABLE IF NOT EXISTS `sms`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `phone`
    VARCHAR
(
    15
) NOT NULL COMMENT 'Iranian phone number',
    `status_id` INT UNSIGNED NOT NULL,
    `template_id` INT UNSIGNED DEFAULT NULL,
    `text` TEXT NOT NULL COMMENT 'Supports Persian text',
    `response` VARCHAR
(
    255
) DEFAULT NULL,
    `description` VARCHAR
(
    500
) DEFAULT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    INDEX `idx_sms_phone`
(
    `phone`
),
    INDEX `idx_sms_status`
(
    `status_id`
),
    INDEX `idx_sms_template`
(
    `template_id`
),
    CONSTRAINT `fk_sms_status` FOREIGN KEY
(
    `status_id`
) REFERENCES `sms_statuses`
(
    `id`
)
                                        ON DELETE RESTRICT
                                        ON UPDATE RESTRICT,
    CONSTRAINT `fk_sms_template` FOREIGN KEY
(
    `template_id`
) REFERENCES `sms_templates`
(
    `id`
)
                                        ON UPDATE RESTRICT
    ) ENGINE = InnoDB COMMENT = 'Stores sent SMS';

-- One time password(OTP)
CREATE TABLE IF NOT EXISTS `otp`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `target`
    VARCHAR
(
    255
) NOT NULL,
    `token` INT UNSIGNED NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `expire_at` TIMESTAMP NOT NULL,
    `used_at` TIMESTAMP NOT NULL,
    INDEX `idx_otp_target`
(
    `target`
)
    ) ENGINE = InnoDB COMMENT = 'Stores sent SMS';

-- New: Shopping cart
CREATE TABLE IF NOT EXISTS `carts`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `user_id`
    INT
    UNSIGNED
    NOT
    NULL,
    `created_at`
    TIMESTAMP
    NOT
    NULL
    DEFAULT
    CURRENT_TIMESTAMP,
    `updated_at`
    TIMESTAMP
    DEFAULT
    NULL
    ON
    UPDATE
    CURRENT_TIMESTAMP,
    INDEX
    `idx_carts_user`
(
    `user_id`
),
    CONSTRAINT `fk_carts_user` FOREIGN KEY
(
    `user_id`
) REFERENCES `users`
(
    `id`
) ON DELETE CASCADE
  ON UPDATE RESTRICT
    ) ENGINE = InnoDB COMMENT = 'Stores user shopping carts';

-- New: Cart items
CREATE TABLE IF NOT EXISTS `cart_items`
(
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    `cart_id`
    INT
    UNSIGNED
    NOT
    NULL,
    `item_type`
    ENUM
(
    'PRODUCT',
    'SERVICE'
) NOT NULL,
    `item_id` INT UNSIGNED NOT NULL,
    `quantity` INT UNSIGNED NOT NULL,
    `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    INDEX `idx_cart_items_cart`
(
    `cart_id`
),
    CONSTRAINT `fk_cart_items_cart` FOREIGN KEY
(
    `cart_id`
) REFERENCES `carts`
(
    `id`
)
                                        ON DELETE CASCADE
                                        ON UPDATE RESTRICT
    ) ENGINE = InnoDB COMMENT = 'Stores items in user carts';

-- Login attempts for tracking user login activity
#
CREATE TABLE IF NOT EXISTS `login_attempts`
    #
(
    #
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    #
    `user_id`
    INT
    UNSIGNED
    NOT
    NULL
    COMMENT
    'References the user attempting to log in',
    #
    `status`
    ENUM
(
    'SUCCESS',
    'FAILED',
    'LOCKED',
    'PENDING'
) NOT NULL DEFAULT 'PENDING' COMMENT 'Outcome of the login attempt',
    # `ip_address` VARCHAR
(
    45
) NOT NULL COMMENT 'IPv4 or IPv6 address of the client',
    # `user_agent` VARCHAR
(
    255
) NOT NULL COMMENT 'Browser or client details',
    # `description` VARCHAR
(
    500
) DEFAULT NULL COMMENT 'Additional details (e.g., failure reason)',
    # `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    # `updated_at` TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    # `deleted_at` TIMESTAMP DEFAULT NULL COMMENT 'Soft delete timestamp',
    # INDEX `idx_login_attempts_user`
(
    `user_id`,
    `created_at`
),
    # INDEX `idx_login_attempts_status`
(
    `status`
),
    # INDEX `idx_login_attempts_ip`
(
    `ip_address`
),
    # INDEX `idx_login_attempts_deleted_at`
(
    `deleted_at`
),
    # CONSTRAINT `fk_login_attempts_user` FOREIGN KEY
(
    `user_id`
) REFERENCES `users`
(
    `id`
)
                                          ON DELETE RESTRICT
                                          ON UPDATE RESTRICT
    # ) ENGINE = InnoDB COMMENT = 'Tracks user login attempts with soft delete';

-- media
#
CREATE TABLE IF NOT EXISTS `media`
    #
(
    #
    `id`
    INT
    UNSIGNED
    NOT
    NULL
    PRIMARY
    KEY
    AUTO_INCREMENT,
    #
    `entity_type`
    VARCHAR
(
    50
) NOT NULL COMMENT 'PRODUCT,USER,SERVICE',
    # `entity_id` INT UNSIGNED NOT NULL,
    # `type` VARCHAR
(
    50
) NOT NULL COMMENT 'jpeg,jpg,png',
    # `url` VARCHAR
(
    255
) NOT NULL COMMENT 'URL to media file',
    # `display_order` TINYINT NOT NULL DEFAULT 0,
    # `extension` VARCHAR
(
    10
) NOT NULL,
    # `description` VARCHAR
(
    500
) DEFAULT NULL,
    # `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    # `updated_at` TIMESTAMP DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    # `deleted_at` TIMESTAMP DEFAULT NULL,
    # INDEX `idx_media_entity_type`
(
    `entity_type`
),
    # INDEX `idx_media_entity`
(
    `entity_id`
),
    # INDEX `idx_media_type`
(
    `type`
)
    # ) ENGINE = InnoDB COMMENT = 'Morph table to store media';