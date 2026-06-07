package com.atrastones.ecommerce.product;

import com.atrastones.ecommerce.product.common.ProductCreateDTO;
import com.atrastones.ecommerce.product.common.ProductProjection;
import com.atrastones.ecommerce.product.common.ProductSearchDTO;
import com.atrastones.ecommerce.product.common.ProductUpdateDTO;
import com.atrastones.infrastructure.db.JdbcUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepositoryImp implements ProductRepository {

    private final JdbcClient jdbcClient;

    @PersistenceContext
    private EntityManager entityManager;

    public ProductRepositoryImp(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    // ---------------------------- CREATE ----------------------------

    @Override
    public Long create(ProductCreateDTO product) {
        return JdbcUtils.insert(
                jdbcClient.sql("""
                                INSERT INTO products (name, category_id, shop_id, quantity, price, service_group_id, description)
                                       VALUES (:name, :category_id, :shop_id, :quantity, :price, :service_group_id, :description)
                                """)
                        .param("name", product.name())
                        .param("category_id", product.categoryId())
                        .param("shop_id", 1) //TODO: this should be get dynamically
                        .param("quantity", product.quantity())
                        .param("price", product.price())
                        .param("service_group_id", 1) //TODO: this should be get dynamically
                        .param("description", product.description())
        );
    }

    // ---------------------------- UPDATE ----------------------------

    @Override
    public void update(Long id, ProductUpdateDTO product) {
        JdbcUtils.update(
                jdbcClient.sql("""
                                UPDATE products
                                       SET name = :name, category_id = :category_id, shop_id = :shop_id, quantity = :quantity,
                                           price = :price, service_group_id = :service_group_id, description = :description
                                WHERE id = :id
                                """)
                        .param("name", product.name())
                        .param("category_id", product.categoryId())
                        .param("shop_id", 1L) //TODO: hardcoded
                        .param("quantity", product.quantity())
                        .param("price", product.price())
                        .param("service_group_id", product.serviceGroupId())
//                        .param("discount_id", product.getDiscountId())
//                        .param("discount_amount", product.getDiscountAmount())
                        .param("description", product.description())
                        .param("id", id)
        );
    }

    // ---------------------------- DELETE ----------------------------

    @Override
    public boolean delete(long id) {
        return JdbcUtils.delete(
                jdbcClient.sql("DELETE FROM products WHERE id = :id")
                        .param("id", id)
        );
    }

    // ---------------------------- SELECT ----------------------------

    @Override
    public Optional<Product> get(long id) {
        return Optional.ofNullable(
                entityManager.createQuery("""
                                SELECT p FROM Product p
                                         JOIN FETCH p.shop
                                         JOIN FETCH p.category
                                         LEFT JOIN FETCH p.discount
                                         WHERE p.id = :id
                                """, Product.class)
                        .setParameter("id", id)
                        .getSingleResult()
        );
    }

    @Override
    public Optional<Product> getDetails(long id) {
        return Optional.ofNullable(
                entityManager.createQuery("""
                                SELECT p FROM Product p
                                         JOIN FETCH p.shop
                                         JOIN FETCH p.category
                                         LEFT JOIN FETCH p.discount
                                         WHERE p.id = :id
                                """, Product.class)
                        .setParameter("id", id)
                        .getSingleResult()
        );
    }

    @Override
    public Page<ProductProjection> getAll(Pageable pageable, ProductSearchDTO filter) {
        List<ProductProjection> products = entityManager.createQuery("""
                            SELECT NEW com.atrastones.ecommerce.product.common.ProductProjection(
                                        p.id,
                                        p.name,
                                        p.categoryId,
                                        p.shopId,
                                        p.quantity,
                                        p.serviceGroupId,
                                        p.description,
                                        p.status,
                                        p.createdAt,
                                        p.updatedAt,
                                        p.deletedAt
                                    )
                            FROM Product p
                            ORDER BY p.createdAt DESC
                        """, ProductProjection.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return PageableExecutionUtils.getPage(products, pageable, products::size);
    }

    @Override
    public Page<Product> getAllBriefInfo(Pageable pageable, ProductSearchDTO filter) {
        return PageableExecutionUtils.getPage(
                entityManager.createQuery("""
                                    SELECT p FROM Product p
                                           JOIN FETCH p.prices pp
                                           JOIN FETCH p.media pm
                                    WHERE pp.isActive = TRUE
                                    AND pm.displayOrder = 1
                                """, Product.class)
                        .setFirstResult((int) pageable.getOffset())
                        .setMaxResults(pageable.getPageSize())
                        .getResultList(),
                pageable,
                this::count
        );
    }

    @Override
    public List<Product> getAllByCategoryId(long categoryId) {
        return entityManager.createQuery("""
                        SELECT p FROM Product p
                                 JOIN FETCH p.category
                        WHERE p.category.id = :category_id
                        """, Product.class)
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
        return jdbcClient.sql("SELECT COUNT(*) FROM products")
                .query(Long.class)
                .single();
    }

    @Override
    public boolean exists(long id) {
        return jdbcClient.sql("SELECT EXISTS(SELECT 1 FROM products WHERE id = :id)")
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