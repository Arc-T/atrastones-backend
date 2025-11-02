package com.atrastones.shop.model.repository.implement;

import com.atrastones.shop.dto.ProductDTO;
import com.atrastones.shop.model.entity.Product;
import com.atrastones.shop.model.repository.contract.ProductRepository;
import com.atrastones.shop.utils.JdbcUtils;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link ProductRepository} using a mix of JDBC and JPA.
 * <p>
 * Handles CRUD operations for products. Uses {@link JdbcClient} for simple operations (create, update, delete, count, exists)
 * for performance, and {@link EntityManager} for select queries with joins to leverage ORM benefits.
 */
@Repository
public class ProductRepositoryImp implements ProductRepository {

    private final JdbcClient jdbcClient;
    private final EntityManager entityManager;

    public ProductRepositoryImp(JdbcClient jdbcClient, EntityManager entityManager) {
        this.jdbcClient = jdbcClient;
        this.entityManager = entityManager;
    }

    // ---------------------------- CREATE ----------------------------

    @Override
    public Long create(ProductDTO product) {

        String INSERT_PRODUCT_SQL = """
                INSERT INTO products (name, category_id, shop_id, quantity, price, service_group_id, discount_id, discount_amount, description)
                       VALUES (:name, :category_id, :shop_id, :quantity, :price, :service_group_id, :discount_id, :discount_amount, :description)
                """;

        return JdbcUtils.insert(
                jdbcClient.sql(INSERT_PRODUCT_SQL)
                        .param("name", product.getName())
                        .param("category_id", product.getCategoryId())
                        .param("shop_id", product.getShopId())
                        .param("quantity", product.getQuantity())
                        .param("price", product.getPrice())
                        .param("service_group_id", product.getServiceGroupId())
                        .param("discount_id", product.getDiscountId())
                        .param("discount_amount", product.getDiscountAmount())
                        .param("description", product.getDescription())
        );
    }

    // ---------------------------- UPDATE ----------------------------

    @Override
    public void update(ProductDTO product) {

        String UPDATE_PRODUCT_SQL = """
                UPDATE products
                       SET name = :name, category_id = :category_id, shop_id = :shop_id, quantity = :quantity,
                           price = :price, service_group_id = :service_group_id, discount_id = :discount_id,
                           discount_amount = :discount_amount, description = :description
                       WHERE id = :id
                """;

        JdbcUtils.update(
                jdbcClient.sql(UPDATE_PRODUCT_SQL)
                        .param("id", product.getId())
                        .param("name", product.getName())
                        .param("category_id", product.getCategoryId())
                        .param("shop_id", product.getShopId())
                        .param("quantity", product.getQuantity())
                        .param("price", product.getPrice())
                        .param("service_group_id", product.getServiceGroupId())
                        .param("discount_id", product.getDiscountId())
                        .param("discount_amount", product.getDiscountAmount())
                        .param("description", product.getDescription())
                , "PRODUCT.ID.INVALID"
        );
    }

    // ---------------------------- DELETE ----------------------------

    @Override
    public boolean delete(long id) {

        String DELETE_PRODUCT_SQL = """
                DELETE FROM products WHERE id = :id
                """;

        return JdbcUtils.delete(
                jdbcClient.sql(DELETE_PRODUCT_SQL)
                        .param("id", id)
                , "PRODUCT.ID.INVALID"
        );
    }

    // ---------------------------- SELECT ----------------------------

    @Override
    public Optional<Product> get(long id) {

        String SELECT_PRODUCT_HQL = """
                SELECT p FROM Product p
                         WHERE p.id = :id
                """;

        return Optional.ofNullable(
                entityManager.createQuery(SELECT_PRODUCT_HQL, Product.class)
                        .setParameter("id", id)
                        .getSingleResult()
        );
    }

    @Override
    public Optional<Product> getDetails(long id) {

        String SELECT_PRODUCT_DETAILS_HQL = """
                SELECT p FROM Product p
                         JOIN FETCH p.shop
                         JOIN FETCH p.category
                         LEFT JOIN FETCH p.serviceGroup
                         LEFT JOIN FETCH p.discount
                         WHERE p.id = :id
                """;

        return Optional.ofNullable(
                entityManager.createQuery(SELECT_PRODUCT_DETAILS_HQL, Product.class)
                        .setParameter("id", id)
                        .getSingleResult()
        );
    }

    @Override
    public Page<Product> getAllPaginated(Pageable pageable) {

        String SELECT_ALL_PRODUCTS_HQL = """
                SELECT p FROM Product p
                         JOIN FETCH p.shop
                         JOIN FETCH p.category
                """;

        return PageableExecutionUtils.getPage(
                entityManager.createQuery(SELECT_ALL_PRODUCTS_HQL, Product.class)
                        .setFirstResult((int) pageable.getOffset())
                        .setMaxResults(pageable.getPageSize())
                        .getResultList(),
                pageable,
                this::count
        );
    }

    @Override
    public List<Product> getAllByCategoryId(long categoryId) {

        String SELECT_PRODUCTS_BY_CATEGORY_HQL = """
                SELECT p FROM Product p
                         JOIN FETCH p.category
                         WHERE p.category.id = :category_id
                """;

        return entityManager.createQuery(SELECT_PRODUCTS_BY_CATEGORY_HQL, Product.class)
                .setParameter("category_id", categoryId)
                .getResultList();
    }

    @Override
    public List<Product> getOrderItems(long orderId) {
        return List.of();
    }

    // ---------------------------- OPERATION ----------------------------

    @Override
    public long count() {

        String COUNT_PRODUCT_SQL = """
                SELECT COUNT(*) FROM products
                """;

        return jdbcClient.sql(COUNT_PRODUCT_SQL)
                .query(Long.class)
                .single();
    }

    @Override
    public boolean exists(long id) {

        String EXISTS_PRODUCT_SQL = """
                SELECT EXISTS(SELECT 1 FROM products WHERE id = :id)
                """;

        return jdbcClient.sql(EXISTS_PRODUCT_SQL)
                .param("id", id)
                .query(Boolean.class)
                .single();
    }

    @Override
    public void addProductStats(long userId, long productId) {

//        String INSERT_PRODUCT_STATS_SQL = """
//                INSERT INTO product_stats (user_id, product_id, view_count, last_viewed)
//                VALUES (:user_id, :product_id, 1, CURRENT_TIMESTAMP)
//                ON DUPLICATE KEY UPDATE view_count = view_count + 1, last_viewed = CURRENT_TIMESTAMP
//                """;
//
//        try {
//            int rowsAffected = jdbcClient.sql(INSERT_PRODUCT_STATS_SQL)
//                    .param("user_id", userId)
//                    .param("product_id", productId)
//                    .update();
//
//            if (rowsAffected == 0) {
//                throw new EntityNotFoundException("Failed to add product stats for user ID " + userId + " and product ID " + productId);
//            }
//
//            log.debug("Added product stats for user ID: {} and product ID: {}", userId, productId);
//
//        } catch (DataAccessException ex) {
//            throw new RuntimeException(
//                    String.format("Failed to add product stats for user ID %d and product ID %d", userId, productId), ex
//            );
//        }
    }

}