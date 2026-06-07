-- Create database (run this separately)
-- CREATE DATABASE atrastones
--     WITH
--     ENCODING = 'UTF8'
--     LC_COLLATE = 'en_US.UTF-8'
--     LC_CTYPE = 'en_US.UTF-8';

-- Connect to database
-- \c atrastones

-- Enable required extensions
CREATE
EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE
EXTENSION IF NOT EXISTS "pg_trgm";

-- Roles for RBAC
CREATE TABLE IF NOT EXISTS roles
(
    id
    SERIAL
    PRIMARY
    KEY,
    name
    VARCHAR
(
    50
) NOT NULL,
    description VARCHAR
(
    255
),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT NULL,
    UNIQUE
(
    name
)
    );

COMMENT
ON TABLE roles IS 'Stores user roles for RBAC';
COMMENT
ON COLUMN roles.description IS 'Role description for clarity';

-- Permissions for RBAC
CREATE TABLE IF NOT EXISTS permissions
(
    id
    SERIAL
    PRIMARY
    KEY,
    name
    VARCHAR
(
    100
) NOT NULL,
    description VARCHAR
(
    255
),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT NULL,
    UNIQUE
(
    name
)
    );

COMMENT
ON TABLE permissions IS 'Stores permissions for RBAC';
COMMENT
ON COLUMN permissions.description IS 'Permission description';

-- Maps roles to permissions
CREATE TABLE IF NOT EXISTS role_permissions
(
    id
    SERIAL
    PRIMARY
    KEY,
    role_id
    INT
    NOT
    NULL,
    permission_id
    INT
    NOT
    NULL,
    created_at
    TIMESTAMP
    NOT
    NULL
    DEFAULT
    CURRENT_TIMESTAMP,
    UNIQUE
(
    permission_id,
    role_id
),
    FOREIGN KEY
(
    role_id
) REFERENCES roles
(
    id
) ON DELETE CASCADE,
    FOREIGN KEY
(
    permission_id
) REFERENCES permissions
(
    id
)
  ON DELETE CASCADE
    );

COMMENT
ON TABLE role_permissions IS 'Maps roles to permissions';
CREATE INDEX idx_perm_role_role ON role_permissions (role_id);
CREATE INDEX idx_perm_role_perm ON role_permissions (permission_id);

-- User groups for grouping users
CREATE TABLE IF NOT EXISTS user_groups
(
    id
    SERIAL
    PRIMARY
    KEY,
    name
    VARCHAR
(
    100
) NOT NULL,
    description VARCHAR
(
    255
),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT NULL,
    UNIQUE
(
    name
)
    );

COMMENT
ON TABLE user_groups IS 'Groups users for role assignment';

-- Users table
CREATE TYPE gender_enum AS ENUM ('MALE', 'FEMALE');

CREATE TABLE IF NOT EXISTS users
(
    id
    SERIAL
    PRIMARY
    KEY,
    first_name
    VARCHAR
(
    100
) NOT NULL,
    last_name VARCHAR
(
    100
) NOT NULL,
    gender gender_enum NOT NULL,
    phone VARCHAR
(
    15
) NOT NULL,
    email VARCHAR
(
    255
),
    password VARCHAR
(
    255
) NOT NULL,
    user_group_id INT NOT NULL,
    is_locked BOOLEAN NOT NULL DEFAULT FALSE,
    description VARCHAR
(
    500
),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT NULL,
    deleted_at TIMESTAMP DEFAULT NULL,
    UNIQUE
(
    email
),
    UNIQUE
(
    phone
),
    FOREIGN KEY
(
    user_group_id
) REFERENCES user_groups
(
    id
)
    );

COMMENT
ON TABLE users IS 'Stores user information';
COMMENT
ON COLUMN users.password IS 'Hashed password (bcrypt)';

CREATE INDEX idx_users_email ON users (email);
CREATE INDEX idx_users_phone ON users (phone);
CREATE INDEX idx_users_group ON users (user_group_id);

-- Maps roles to user groups
CREATE TABLE IF NOT EXISTS user_group_roles
(
    id
    SERIAL
    PRIMARY
    KEY,
    role_id
    INT
    NOT
    NULL,
    user_group_id
    INT
    NOT
    NULL,
    created_at
    TIMESTAMP
    NOT
    NULL
    DEFAULT
    CURRENT_TIMESTAMP,
    UNIQUE
(
    role_id,
    user_group_id
),
    FOREIGN KEY
(
    role_id
) REFERENCES roles
(
    id
) ON DELETE CASCADE,
    FOREIGN KEY
(
    user_group_id
) REFERENCES user_groups
(
    id
)
  ON DELETE CASCADE
    );

COMMENT
ON TABLE user_group_roles IS 'Maps roles to user groups';
CREATE INDEX idx_user_group_roles_role ON user_group_roles (role_id);
CREATE INDEX idx_user_group_roles_user_group ON user_group_roles (user_group_id);

-- VIP groups for premium memberships
CREATE TABLE IF NOT EXISTS vip_groups
(
    id
    SERIAL
    PRIMARY
    KEY,
    name
    VARCHAR
(
    100
) NOT NULL,
    description VARCHAR
(
    255
),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT NULL,
    UNIQUE
(
    name
)
    );

COMMENT
ON TABLE vip_groups IS 'Stores VIP group definitions';

-- VIP memberships for users
CREATE TABLE IF NOT EXISTS vip_memberships
(
    id
    SERIAL
    PRIMARY
    KEY,
    vip_group_id
    INT
    NOT
    NULL,
    user_id
    INT
    NOT
    NULL,
    created_at
    TIMESTAMP
    NOT
    NULL
    DEFAULT
    CURRENT_TIMESTAMP,
    UNIQUE
(
    user_id,
    vip_group_id
),
    FOREIGN KEY
(
    vip_group_id
) REFERENCES vip_groups
(
    id
) ON DELETE CASCADE,
    FOREIGN KEY
(
    user_id
) REFERENCES users
(
    id
)
  ON DELETE CASCADE
    );

COMMENT
ON TABLE vip_memberships IS 'Tracks user VIP memberships';
CREATE INDEX idx_vip_memberships_group ON vip_memberships (vip_group_id);
CREATE INDEX idx_vip_memberships_user ON vip_memberships (user_id);

-- Provinces for addresses
CREATE TABLE IF NOT EXISTS provinces
(
    id
    SERIAL
    PRIMARY
    KEY,
    name
    VARCHAR
(
    100
) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE
(
    name
)
    );

COMMENT
ON TABLE provinces IS 'Stores Iran provinces';

-- User addresses
CREATE TYPE address_type_enum AS ENUM ('HOME', 'WORK', 'OTHER');

CREATE TABLE IF NOT EXISTS addresses
(
    id
    SERIAL
    PRIMARY
    KEY,
    user_id
    INT
    NOT
    NULL,
    address_type
    address_type_enum
    NOT
    NULL,
    province_id
    INT
    NOT
    NULL,
    address_line
    VARCHAR
(
    255
) NOT NULL,
    display_order INT NOT NULL DEFAULT 0,
    postal_code VARCHAR
(
    10
),
    latitude DECIMAL
(
    9,
    6
),
    longitude DECIMAL
(
    9,
    6
),
    description VARCHAR
(
    255
),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT NULL,
    FOREIGN KEY
(
    user_id
) REFERENCES users
(
    id
) ON DELETE CASCADE,
    FOREIGN KEY
(
    province_id
) REFERENCES provinces
(
    id
)
    );

COMMENT
ON TABLE addresses IS 'Stores user addresses';
COMMENT
ON COLUMN addresses.postal_code IS 'postal code (10 digits)';

CREATE INDEX idx_addresses_user ON addresses (user_id);
CREATE INDEX idx_addresses_province ON addresses (province_id);

-- User activity logs
CREATE TABLE IF NOT EXISTS user_logs
(
    id
    SERIAL
    PRIMARY
    KEY,
    user_id
    INT
    NOT
    NULL,
    action_type
    VARCHAR
(
    50
) NOT NULL,
    action_id VARCHAR
(
    50
) NOT NULL,
    ip_address VARCHAR
(
    45
) NOT NULL,
    user_agent VARCHAR
(
    255
) NOT NULL,
    description VARCHAR
(
    500
),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY
(
    user_id
) REFERENCES users
(
    id
) ON DELETE CASCADE
    );

COMMENT
ON TABLE user_logs IS 'Logs user activities';
COMMENT
ON COLUMN user_logs.action_type IS 'e.g., LOGIN, PURCHASE';
COMMENT
ON COLUMN user_logs.action_id IS 'Reference to entity (e.g., order ID)';
COMMENT
ON COLUMN user_logs.ip_address IS 'Supports IPv4/IPv6';

CREATE INDEX idx_logs_user ON user_logs (user_id, created_at);
CREATE INDEX idx_logs_action_type ON user_logs (action_type);

-- Shops
CREATE TYPE shop_status_enum AS ENUM ('ACTIVE', 'INACTIVE', 'PENDING');

CREATE TABLE IF NOT EXISTS shops
(
    id
    SERIAL
    PRIMARY
    KEY,
    name
    VARCHAR
(
    100
) NOT NULL,
    phone VARCHAR
(
    15
) NOT NULL,
    address_id INT NOT NULL,
    status shop_status_enum NOT NULL DEFAULT 'PENDING',
    description VARCHAR
(
    500
) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT NULL,
    deleted_at TIMESTAMP DEFAULT NULL,
    FOREIGN KEY
(
    address_id
) REFERENCES addresses
(
    id
)
    );

COMMENT
ON TABLE shops IS 'Stores shop details';
COMMENT
ON COLUMN shops.phone IS 'Shop contact number';

CREATE INDEX idx_shops_phone ON shops (phone);
CREATE INDEX idx_shops_address ON shops (address_id);
CREATE INDEX idx_shops_status ON shops (status);
CREATE INDEX idx_shops_deleted_at ON shops (deleted_at);

-- Shops owned by users
CREATE TABLE IF NOT EXISTS shop_members
(
    id
    SERIAL
    PRIMARY
    KEY,
    shop_id
    INT
    NOT
    NULL,
    user_id
    INT
    NOT
    NULL,
    description
    TEXT,
    created_at
    TIMESTAMP
    NOT
    NULL
    DEFAULT
    CURRENT_TIMESTAMP,
    updated_at
    TIMESTAMP
    DEFAULT
    NULL,
    UNIQUE
(
    user_id
),
    FOREIGN KEY
(
    shop_id
) REFERENCES shops
(
    id
) ON DELETE CASCADE,
    FOREIGN KEY
(
    user_id
) REFERENCES users
(
    id
)
  ON DELETE CASCADE
    );

COMMENT
ON TABLE shop_members IS 'Shop staff members with their status';

CREATE INDEX idx_shop_members_shop ON shop_members (shop_id);
CREATE INDEX idx_shop_members_user ON shop_members (user_id);

-- Product categories (hierarchical)
CREATE TABLE IF NOT EXISTS categories
(
    id
    SERIAL
    PRIMARY
    KEY,
    name
    VARCHAR
(
    100
) NOT NULL,
    url VARCHAR
(
    255
) NOT NULL,
    icon VARCHAR
(
    50
),
    parent_id INT,
    display_order INT NOT NULL DEFAULT 0,
    description VARCHAR
(
    255
),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT NULL,
    UNIQUE
(
    url
),
    FOREIGN KEY
(
    parent_id
) REFERENCES categories
(
    id
) ON DELETE CASCADE
    );

COMMENT
ON TABLE categories IS 'Stores product categories';

CREATE INDEX idx_categories_parent ON categories (parent_id);
CREATE INDEX idx_categories_url ON categories (url);

-- Product attributes
CREATE TYPE attribute_type_enum AS ENUM ('TEXT', 'NUMBER', 'SELECT', 'MULTISELECT');

CREATE TABLE IF NOT EXISTS attributes
(
    id
    SERIAL
    PRIMARY
    KEY,
    name
    VARCHAR
(
    100
) NOT NULL,
    category_id INT NOT NULL,
    type attribute_type_enum NOT NULL DEFAULT 'TEXT',
    is_filterable BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT NULL,
    FOREIGN KEY
(
    category_id
) REFERENCES categories
(
    id
) ON DELETE CASCADE
    );

COMMENT
ON TABLE attributes IS 'Stores product attributes';
COMMENT
ON COLUMN attributes.name IS 'Supports Persian names';

CREATE INDEX idx_attributes_category ON attributes (category_id);
CREATE INDEX idx_attributes_filterable ON attributes (is_filterable);

-- Attribute values
CREATE TABLE IF NOT EXISTS attribute_values
(
    id
    SERIAL
    PRIMARY
    KEY,
    value
    VARCHAR
(
    200
) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE
(
    value
)
    );

COMMENT
ON TABLE attribute_values IS 'Stores possible attribute values';
COMMENT
ON COLUMN attribute_values.value IS 'Supports Persian values';

-- Maps attributes to their values
CREATE TABLE IF NOT EXISTS attribute_values_map
(
    id
    SERIAL
    PRIMARY
    KEY,
    attribute_id
    INT
    NOT
    NULL,
    attribute_value_id
    INT
    NOT
    NULL,
    created_at
    TIMESTAMP
    NOT
    NULL
    DEFAULT
    CURRENT_TIMESTAMP,
    UNIQUE
(
    attribute_id,
    attribute_value_id
),
    FOREIGN KEY
(
    attribute_id
) REFERENCES attributes
(
    id
) ON DELETE CASCADE,
    FOREIGN KEY
(
    attribute_value_id
) REFERENCES attribute_values
(
    id
)
  ON DELETE CASCADE
    );

COMMENT
ON TABLE attribute_values_map IS 'Maps attributes to their possible values';

CREATE INDEX idx_attr_values_map_attribute ON attribute_values_map (attribute_id);
CREATE INDEX idx_attr_values_map_value ON attribute_values_map (attribute_value_id);

-- Product tags
CREATE TABLE IF NOT EXISTS tags
(
    id
    SERIAL
    PRIMARY
    KEY,
    name
    VARCHAR
(
    100
) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE
(
    name
)
    );

COMMENT
ON TABLE tags IS 'Stores product tags';
COMMENT
ON COLUMN tags.name IS 'Supports Persian names';

-- Service groups
CREATE TABLE IF NOT EXISTS service_groups
(
    id
    SERIAL
    PRIMARY
    KEY,
    name
    VARCHAR
(
    100
) NOT NULL,
    description VARCHAR
(
    500
),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT NULL,
    UNIQUE
(
    name
)
    );

COMMENT
ON TABLE service_groups IS 'Stores service group definitions';
COMMENT
ON COLUMN service_groups.name IS 'Supports Persian names';

-- Services
CREATE TABLE IF NOT EXISTS services
(
    id
    SERIAL
    PRIMARY
    KEY,
    name
    VARCHAR
(
    100
) NOT NULL,
    cost DECIMAL
(
    10,
    2
) NOT NULL,
    service_group_id INT NOT NULL,
    description VARCHAR
(
    500
),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT NULL,
    deleted_at TIMESTAMP DEFAULT NULL,
    FOREIGN KEY
(
    service_group_id
) REFERENCES service_groups
(
    id
)
    );

COMMENT
ON TABLE services IS 'Stores services with costs in Toman';
COMMENT
ON COLUMN services.cost IS 'Cost in Toman';

CREATE INDEX idx_services_group ON services (service_group_id);
CREATE INDEX idx_services_deleted_at ON services (deleted_at);

-- Discount types
CREATE TABLE IF NOT EXISTS discount_types
(
    id
    SERIAL
    PRIMARY
    KEY,
    name
    VARCHAR
(
    50
) NOT NULL,
    description VARCHAR
(
    255
),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT NULL,
    UNIQUE
(
    name
)
    );

COMMENT
ON TABLE discount_types IS 'Stores discount type definitions';

-- Discounts
CREATE TYPE discount_status_enum AS ENUM ('ACTIVE', 'INACTIVE', 'EXPIRED');

CREATE TABLE IF NOT EXISTS discounts
(
    id
    SERIAL
    PRIMARY
    KEY,
    name
    VARCHAR
(
    100
) NOT NULL,
    code VARCHAR
(
    50
) NOT NULL,
    status discount_status_enum NOT NULL DEFAULT 'ACTIVE',
    discount_type_id INT NOT NULL,
    amount INT NOT NULL,
    begin_date TIMESTAMP NOT NULL,
    expire_date TIMESTAMP NOT NULL,
    description VARCHAR
(
    500
),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT NULL,
    deleted_at TIMESTAMP DEFAULT NULL,
    CHECK
(
    amount
    >=
    0
),
    FOREIGN KEY
(
    discount_type_id
) REFERENCES discount_types
(
    id
)
    );

COMMENT
ON TABLE discounts IS 'Stores discount details';
COMMENT
ON COLUMN discounts.name IS 'Supports Persian names';
COMMENT
ON COLUMN discounts.amount IS 'Discount amount in Toman';

CREATE INDEX idx_discounts_status ON discounts (status, expire_date);
CREATE INDEX idx_discounts_code ON discounts (code);
CREATE INDEX idx_discounts_deleted_at ON discounts (deleted_at);

-- Products
CREATE TYPE product_status_enum AS ENUM ('ACTIVE', 'INACTIVE', 'OUT_OF_STOCK');

CREATE TABLE IF NOT EXISTS products
(
    id
    SERIAL
    PRIMARY
    KEY,
    name
    VARCHAR
(
    100
) NOT NULL,
    category_id INT NOT NULL,
    shop_id INT NOT NULL,
    quantity INT NOT NULL,
    price INT NOT NULL,
    service_group_id INT NOT NULL,
    discount_id INT,
    discount_amount INT,
    status product_status_enum NOT NULL DEFAULT 'ACTIVE',
    description VARCHAR
(
    1000
) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT NULL,
    deleted_at TIMESTAMP DEFAULT NULL,
    CHECK
(
    quantity
    >=
    0
),
    CHECK
(
    price
    >=
    0
),
    CHECK
(
    discount_amount
    >=
    0
),
    FOREIGN KEY
(
    category_id
) REFERENCES categories
(
    id
),
    FOREIGN KEY
(
    shop_id
) REFERENCES shops
(
    id
),
    FOREIGN KEY
(
    service_group_id
) REFERENCES service_groups
(
    id
),
    FOREIGN KEY
(
    discount_id
) REFERENCES discounts
(
    id
) ON DELETE SET NULL
    );

COMMENT
ON TABLE products IS 'Stores product details with prices in Toman';
COMMENT
ON COLUMN products.price IS 'Price in Toman';
COMMENT
ON COLUMN products.discount_amount IS 'Discount amount in Toman';

CREATE INDEX idx_products_category ON products (category_id, price, status);
CREATE INDEX idx_products_shop ON products (shop_id);
CREATE INDEX idx_products_service_group ON products (service_group_id);
CREATE INDEX idx_products_discount ON products (discount_id);
CREATE INDEX idx_products_status ON products (status);
CREATE INDEX idx_products_deleted_at ON products (deleted_at);

-- Product statistics
CREATE TABLE IF NOT EXISTS product_stats
(
    id
    SERIAL
    PRIMARY
    KEY,
    product_id
    INT
    NOT
    NULL,
    views
    INT
    NOT
    NULL
    DEFAULT
    0,
    likes
    INT
    NOT
    NULL
    DEFAULT
    0,
    shares
    INT
    NOT
    NULL
    DEFAULT
    0,
    comments
    INT
    NOT
    NULL
    DEFAULT
    0,
    ratings
    SMALLINT
    NOT
    NULL,
    created_at
    TIMESTAMP
    NOT
    NULL
    DEFAULT
    CURRENT_TIMESTAMP,
    CHECK
(
    views
    >=
    0
),
    CHECK
(
    likes
    >=
    0
),
    CHECK
(
    shares
    >=
    0
),
    CHECK
(
    comments
    >=
    0
),
    CHECK
(
    ratings
    BETWEEN
    1
    AND
    5
),
    FOREIGN KEY
(
    product_id
) REFERENCES products
(
    id
) ON DELETE CASCADE
    );

COMMENT
ON TABLE product_stats IS 'Tracks product views and likes';

CREATE INDEX idx_stats_product ON product_stats (product_id);

-- Orders
CREATE TYPE order_status_enum AS ENUM ('PENDING', 'PROCESSING', 'SHIPPED', 'DELIVERED', 'CANCELLED');

CREATE TABLE IF NOT EXISTS orders
(
    id
    SERIAL
    PRIMARY
    KEY,
    user_id
    INT
    NOT
    NULL,
    address_id
    INT,
    total_price
    INT
    NOT
    NULL,
    status
    order_status_enum
    NOT
    NULL
    DEFAULT
    'PENDING',
    description
    VARCHAR
(
    500
),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT NULL,
    deleted_at TIMESTAMP DEFAULT NULL,
    CHECK
(
    total_price
    >=
    0
),
    FOREIGN KEY
(
    user_id
) REFERENCES users
(
    id
),
    FOREIGN KEY
(
    address_id
) REFERENCES addresses
(
    id
) ON DELETE SET NULL
    );

COMMENT
ON TABLE orders IS 'Stores customer orders';
COMMENT
ON COLUMN orders.total_price IS 'Total in Toman';
COMMENT
ON COLUMN orders.address_id IS 'Delivery address';

CREATE INDEX idx_orders_user ON orders (user_id, status);
CREATE INDEX idx_orders_address ON orders (address_id);
CREATE INDEX idx_orders_created_at ON orders (created_at);
CREATE INDEX idx_orders_deleted_at ON orders (deleted_at);

-- Order details (products/services in an order)
CREATE TYPE item_type_enum AS ENUM ('PRODUCT', 'SERVICE');
CREATE TYPE order_detail_status_enum AS ENUM ('PENDING', 'COMPLETED', 'CANCELLED');

CREATE TABLE IF NOT EXISTS order_details
(
    id
    SERIAL
    PRIMARY
    KEY,
    order_id
    INT
    NOT
    NULL,
    shop_id
    INT
    NOT
    NULL,
    item_type
    item_type_enum
    NOT
    NULL,
    item_id
    INT
    NOT
    NULL,
    quantity
    INT
    NOT
    NULL,
    unit_price
    INT
    NOT
    NULL,
    discount_id
    INT,
    discount_amount
    INT
    DEFAULT
    0,
    total_price
    INT
    NOT
    NULL,
    status
    order_detail_status_enum
    NOT
    NULL
    DEFAULT
    'PENDING',
    description
    VARCHAR
(
    500
),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT NULL,
    CHECK
(
    quantity
    >=
    0
),
    CHECK
(
    unit_price
    >=
    0
),
    CHECK
(
    discount_amount
    >=
    0
),
    CHECK
(
    total_price
    >=
    0
),
    FOREIGN KEY
(
    order_id
) REFERENCES orders
(
    id
) ON DELETE CASCADE,
    FOREIGN KEY
(
    shop_id
) REFERENCES shops
(
    id
),
    FOREIGN KEY
(
    discount_id
) REFERENCES discounts
(
    id
)
  ON DELETE SET NULL
    );

COMMENT
ON TABLE order_details IS 'Stores order line items';
COMMENT
ON COLUMN order_details.unit_price IS 'Unit price in Toman';
COMMENT
ON COLUMN order_details.discount_amount IS 'Discount per item in Toman';
COMMENT
ON COLUMN order_details.total_price IS 'Total after discount in Toman';

CREATE INDEX idx_details_order ON order_details (order_id);
CREATE INDEX idx_details_shop ON order_details (shop_id);
CREATE INDEX idx_details_discount ON order_details (discount_id);

-- Product reviews
CREATE TYPE review_action_enum AS ENUM ('LIKE', 'SHARE', 'COMMENT', 'SAVED', 'QUESTION', 'COMPARE');

CREATE TABLE IF NOT EXISTS product_reviews
(
    id
    SERIAL
    PRIMARY
    KEY,
    product_id
    INT
    NOT
    NULL,
    user_id
    INT
    NOT
    NULL,
    action_type
    review_action_enum
    NOT
    NULL,
    action_details
    VARCHAR
(
    500
),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT NULL,
    deleted_at TIMESTAMP DEFAULT NULL,
    UNIQUE
(
    product_id,
    user_id
),
    FOREIGN KEY
(
    product_id
) REFERENCES products
(
    id
) ON DELETE CASCADE,
    FOREIGN KEY
(
    user_id
) REFERENCES users
(
    id
)
  ON DELETE CASCADE
    );

COMMENT
ON TABLE product_reviews IS 'Stores product reviews and comments';

CREATE INDEX idx_reviews_product ON product_reviews (product_id);
CREATE INDEX idx_reviews_user ON product_reviews (user_id);
CREATE INDEX idx_reviews_deleted_at ON product_reviews (deleted_at);

-- Product tags mapping
CREATE TABLE IF NOT EXISTS product_tags
(
    id
    SERIAL
    PRIMARY
    KEY,
    product_id
    INT
    NOT
    NULL,
    tag_id
    INT
    NOT
    NULL,
    created_at
    TIMESTAMP
    NOT
    NULL
    DEFAULT
    CURRENT_TIMESTAMP,
    UNIQUE
(
    product_id,
    tag_id
),
    FOREIGN KEY
(
    product_id
) REFERENCES products
(
    id
) ON DELETE CASCADE,
    FOREIGN KEY
(
    tag_id
) REFERENCES tags
(
    id
)
  ON DELETE CASCADE
    );

COMMENT
ON TABLE product_tags IS 'Maps products to tags';

CREATE INDEX idx_product_tags_product ON product_tags (product_id);
CREATE INDEX idx_product_tags_tag ON product_tags (tag_id);

-- Product attribute values
CREATE TABLE IF NOT EXISTS product_attribute_values
(
    id
    SERIAL
    PRIMARY
    KEY,
    product_id
    INT
    NOT
    NULL,
    attribute_id
    INT
    NOT
    NULL,
    attribute_value_id
    INT
    NOT
    NULL,
    created_at
    TIMESTAMP
    NOT
    NULL
    DEFAULT
    CURRENT_TIMESTAMP,
    UNIQUE
(
    product_id,
    attribute_id,
    attribute_value_id
),
    FOREIGN KEY
(
    product_id
) REFERENCES products
(
    id
) ON DELETE CASCADE,
    FOREIGN KEY
(
    attribute_id
) REFERENCES attributes
(
    id
)
  ON DELETE CASCADE,
    FOREIGN KEY
(
    attribute_value_id
) REFERENCES attribute_values
(
    id
)
  ON DELETE CASCADE
    );

COMMENT
ON TABLE product_attribute_values IS 'Maps products to attribute values';

CREATE INDEX idx_product_attr_values_product ON product_attribute_values (product_id);
CREATE INDEX idx_product_attr_values_attribute ON product_attribute_values (attribute_id);
CREATE INDEX idx_product_attr_values_value ON product_attribute_values (attribute_value_id);

-- Media types
CREATE TABLE IF NOT EXISTS media_types
(
    id
    SERIAL
    PRIMARY
    KEY,
    name
    VARCHAR
(
    50
) NOT NULL,
    description VARCHAR
(
    255
),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT NULL,
    UNIQUE
(
    name
)
    );

COMMENT
ON TABLE media_types IS 'Stores media type definitions';

-- Product media
CREATE TABLE IF NOT EXISTS product_media
(
    id
    SERIAL
    PRIMARY
    KEY,
    product_id
    INT
    NOT
    NULL,
    media_type_id
    INT
    NOT
    NULL,
    url
    VARCHAR
(
    255
) NOT NULL,
    display_order INT NOT NULL DEFAULT 0,
    extension VARCHAR
(
    10
) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY
(
    product_id
) REFERENCES products
(
    id
) ON DELETE CASCADE,
    FOREIGN KEY
(
    media_type_id
) REFERENCES media_types
(
    id
)
    );

COMMENT
ON TABLE product_media IS 'Stores product images/videos';
COMMENT
ON COLUMN product_media.url IS 'URL to media file';

CREATE INDEX idx_media_url ON product_media (url);
CREATE INDEX idx_media_product ON product_media (product_id);
CREATE INDEX idx_media_type ON product_media (media_type_id);

-- Wishlist
CREATE TABLE IF NOT EXISTS wishlist
(
    id
    SERIAL
    PRIMARY
    KEY,
    user_id
    INT
    NOT
    NULL,
    product_id
    INT
    NOT
    NULL,
    created_at
    TIMESTAMP
    NOT
    NULL
    DEFAULT
    CURRENT_TIMESTAMP,
    UNIQUE
(
    user_id,
    product_id
),
    FOREIGN KEY
(
    user_id
) REFERENCES users
(
    id
) ON DELETE CASCADE,
    FOREIGN KEY
(
    product_id
) REFERENCES products
(
    id
)
  ON DELETE CASCADE
    );

COMMENT
ON TABLE wishlist IS 'Stores user wishlist';

CREATE INDEX idx_wishlist_user ON wishlist (user_id);
CREATE INDEX idx_wishlist_product ON wishlist (product_id);

-- Order statuses
CREATE TABLE IF NOT EXISTS order_statuses
(
    id
    SERIAL
    PRIMARY
    KEY,
    name
    VARCHAR
(
    50
) NOT NULL,
    description VARCHAR
(
    255
),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT NULL,
    UNIQUE
(
    name
)
    );

COMMENT
ON TABLE order_statuses IS 'Stores order status definitions';

-- Order status history
CREATE TABLE IF NOT EXISTS order_transactions
(
    id
    SERIAL
    PRIMARY
    KEY,
    order_id
    INT
    NOT
    NULL,
    status_id
    INT
    NOT
    NULL,
    description
    VARCHAR
(
    500
),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY
(
    order_id
) REFERENCES orders
(
    id
) ON DELETE CASCADE,
    FOREIGN KEY
(
    status_id
) REFERENCES order_statuses
(
    id
)
    );

COMMENT
ON TABLE order_transactions IS 'Tracks order status changes';

CREATE INDEX idx_transactions_order ON order_transactions (order_id, created_at);
CREATE INDEX idx_transactions_status ON order_transactions (status_id);

-- Invoice types
CREATE TABLE IF NOT EXISTS invoice_types
(
    id
    SERIAL
    PRIMARY
    KEY,
    name
    VARCHAR
(
    50
) NOT NULL,
    description VARCHAR
(
    255
),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT NULL,
    UNIQUE
(
    name
)
    );

COMMENT
ON TABLE invoice_types IS 'Stores invoice type definitions';

-- Invoices
CREATE TABLE IF NOT EXISTS invoices
(
    id
    SERIAL
    PRIMARY
    KEY,
    order_id
    INT
    NOT
    NULL,
    invoice_type_id
    INT
    NOT
    NULL,
    total_price
    INT
    NOT
    NULL,
    description
    VARCHAR
(
    500
),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CHECK
(
    total_price
    >=
    0
),
    FOREIGN KEY
(
    order_id
) REFERENCES orders
(
    id
) ON DELETE CASCADE,
    FOREIGN KEY
(
    invoice_type_id
) REFERENCES invoice_types
(
    id
)
    );

COMMENT
ON TABLE invoices IS 'Stores invoices for orders';
COMMENT
ON COLUMN invoices.total_price IS 'Total in Toman';

CREATE INDEX idx_invoices_order ON invoices (order_id);
CREATE INDEX idx_invoices_type ON invoices (invoice_type_id);

-- Payments
CREATE TYPE payment_method_enum AS ENUM ('CARD', 'WEB', 'APP', 'CASH', 'GATEWAY');
CREATE TYPE payment_status_enum AS ENUM ('PENDING', 'PAID', 'FAILED', 'REFUNDED');

CREATE TABLE IF NOT EXISTS payments
(
    id
    SERIAL
    PRIMARY
    KEY,
    user_id
    INT
    NOT
    NULL,
    order_id
    INT
    NOT
    NULL,
    payment_method
    payment_method_enum
    NOT
    NULL,
    amount
    INT
    NOT
    NULL,
    status
    payment_status_enum
    NOT
    NULL,
    authority
    VARCHAR
(
    100
),
    reference_id VARCHAR
(
    100
),
    fee_type VARCHAR
(
    50
),
    fee INT,
    description VARCHAR
(
    500
),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CHECK
(
    amount
    >=
    0
),
    CHECK
(
    fee
    >=
    0
),
    FOREIGN KEY
(
    user_id
) REFERENCES users
(
    id
),
    FOREIGN KEY
(
    order_id
) REFERENCES orders
(
    id
) ON DELETE CASCADE
    );

COMMENT
ON TABLE payments IS 'Stores payment transactions';
COMMENT
ON COLUMN payments.amount IS 'Amount in Toman';
COMMENT
ON COLUMN payments.fee IS 'Fee in Toman';
COMMENT
ON COLUMN payments.authority IS 'Payment gateway authority code';
COMMENT
ON COLUMN payments.reference_id IS 'Gateway reference ID';

CREATE INDEX idx_payments_user ON payments (user_id);
CREATE INDEX idx_payments_order ON payments (order_id);
CREATE INDEX idx_payments_reference ON payments (reference_id);

-- Order remaining balances
CREATE TABLE IF NOT EXISTS order_remaining_balances
(
    id
    SERIAL
    PRIMARY
    KEY,
    order_id
    INT
    NOT
    NULL,
    payment_id
    INT
    NOT
    NULL,
    remaining_amount
    INT
    NOT
    NULL,
    created_at
    TIMESTAMP
    NOT
    NULL
    DEFAULT
    CURRENT_TIMESTAMP,
    CHECK
(
    remaining_amount
    >=
    0
),
    FOREIGN KEY
(
    order_id
) REFERENCES orders
(
    id
) ON DELETE CASCADE,
    FOREIGN KEY
(
    payment_id
) REFERENCES payments
(
    id
)
  ON DELETE CASCADE
    );

COMMENT
ON TABLE order_remaining_balances IS 'Tracks remaining order balances';
COMMENT
ON COLUMN order_remaining_balances.remaining_amount IS 'Remaining balance in Toman';

CREATE INDEX idx_balances_order ON order_remaining_balances (order_id);
CREATE INDEX idx_balances_payment ON order_remaining_balances (payment_id);

-- SMS templates
CREATE TABLE IF NOT EXISTS sms_templates
(
    id
    SERIAL
    PRIMARY
    KEY,
    name
    VARCHAR
(
    100
) NOT NULL,
    message TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT NULL,
    UNIQUE
(
    name
)
    );

COMMENT
ON TABLE sms_templates IS 'Stores SMS templates';
COMMENT
ON COLUMN sms_templates.message IS 'Supports Persian messages';

-- SMS statuses
CREATE TABLE IF NOT EXISTS sms_statuses
(
    id
    SERIAL
    PRIMARY
    KEY,
    code
    VARCHAR
(
    50
) NOT NULL,
    status VARCHAR
(
    100
) NOT NULL,
    description VARCHAR
(
    255
),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT NULL,
    UNIQUE
(
    code
)
    );

COMMENT
ON TABLE sms_statuses IS 'Stores SMS status codes';

-- SMS
CREATE TABLE IF NOT EXISTS sms
(
    id
    SERIAL
    PRIMARY
    KEY,
    phone
    VARCHAR
(
    15
) NOT NULL,
    status_id INT NOT NULL,
    template_id INT,
    text TEXT NOT NULL,
    response VARCHAR
(
    255
),
    description VARCHAR
(
    500
),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT NULL,
    FOREIGN KEY
(
    status_id
) REFERENCES sms_statuses
(
    id
),
    FOREIGN KEY
(
    template_id
) REFERENCES sms_templates
(
    id
) ON DELETE SET NULL
    );

COMMENT
ON TABLE sms IS 'Stores sent SMS';
COMMENT
ON COLUMN sms.phone IS 'Iranian phone number';
COMMENT
ON COLUMN sms.text IS 'Supports Persian text';

CREATE INDEX idx_sms_phone ON sms (phone);
CREATE INDEX idx_sms_status ON sms (status_id);
CREATE INDEX idx_sms_template ON sms (template_id);

-- One time password(OTP)
CREATE TABLE IF NOT EXISTS otp
(
    id
    SERIAL
    PRIMARY
    KEY,
    target
    VARCHAR
(
    255
) NOT NULL,
    token INT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expire_at TIMESTAMP NOT NULL
    );

COMMENT
ON TABLE otp IS 'Stores sent SMS';

CREATE INDEX idx_otp_target ON otp (target);

-- Shopping cart
CREATE TABLE IF NOT EXISTS carts
(
    id
    SERIAL
    PRIMARY
    KEY,
    user_id
    INT
    NOT
    NULL,
    created_at
    TIMESTAMP
    NOT
    NULL
    DEFAULT
    CURRENT_TIMESTAMP,
    updated_at
    TIMESTAMP
    DEFAULT
    NULL,
    FOREIGN
    KEY
(
    user_id
) REFERENCES users
(
    id
) ON DELETE CASCADE
    );

COMMENT
ON TABLE carts IS 'Stores user shopping carts';

CREATE INDEX idx_carts_user ON carts (user_id);

-- Cart items
CREATE TABLE IF NOT EXISTS cart_items
(
    id
    SERIAL
    PRIMARY
    KEY,
    cart_id
    INT
    NOT
    NULL,
    item_type
    item_type_enum
    NOT
    NULL,
    item_id
    INT
    NOT
    NULL,
    quantity
    INT
    NOT
    NULL,
    created_at
    TIMESTAMP
    NOT
    NULL
    DEFAULT
    CURRENT_TIMESTAMP,
    updated_at
    TIMESTAMP
    DEFAULT
    NULL,
    CHECK
(
    quantity
    >=
    0
),
    FOREIGN KEY
(
    cart_id
) REFERENCES carts
(
    id
) ON DELETE CASCADE
    );

COMMENT
ON TABLE cart_items IS 'Stores items in user carts';

CREATE INDEX idx_cart_items_cart ON cart_items (cart_id);

-- Function to update updated_at timestamp
CREATE
OR REPLACE FUNCTION update_updated_at_column()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.updated_at
= CURRENT_TIMESTAMP;
RETURN NEW;
END;
$$
language 'plpgsql';

-- Create triggers for tables with updated_at columns
DO
$$
    DECLARE
tbl text;
BEGIN
FOR tbl IN
SELECT table_name
FROM information_schema.columns
WHERE column_name = 'updated_at'
  AND table_schema = 'public' LOOP
                EXECUTE format('
            DROP TRIGGER IF EXISTS update_%s_updated_at ON %s;
            CREATE TRIGGER update_%s_updated_at
            BEFORE UPDATE ON %s
            FOR EACH ROW
            EXECUTE FUNCTION update_updated_at_column();
        ', tbl, tbl, tbl, tbl);
END LOOP;
END;
$$;