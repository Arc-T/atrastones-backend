package com.atrastones.shop.model.repository.implement;

import com.atrastones.shop.dto.ProductMediaDTO;
import com.atrastones.shop.model.entity.ProductMedia;
import com.atrastones.shop.model.repository.contract.ProductMediaRepository;
import com.atrastones.shop.utils.JdbcUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductMediaRepositoryImp implements ProductMediaRepository {

    private final JdbcClient jdbcClient;

    @PersistenceContext
    private EntityManager entityManager;

    public ProductMediaRepositoryImp(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    // ================================================ CREATE ================================================

    @Override
    public List<Long> createBatch(List<ProductMediaDTO> productMedia) {

        String INSERT_PRODUCT_MEDIA_SQL = """
                INSERT INTO product_media (product_id, media_type_id, url, display_order, extension)
                       VALUES (:product_id, :media_type_id, :url, :display_order, :extension)
                """;

        return JdbcUtils.insertBatch(productMedia.stream().map(
                        media ->
                                jdbcClient.sql(INSERT_PRODUCT_MEDIA_SQL)
                                        .param("product_id", media.productId())
                                        .param("media_type_id", 1)
                                        .param("url", media.url())
                                        .param("display_order", media.displayOrder())
                                        .param("extension", media.extension()))
                .toList());
    }

    // ================================================ UPDATE ================================================

    @Override
    public void update(Long id, ProductMediaDTO productMediaDTO) {

        String UPDATE_PRODUCT_MEDIA_SQL = """
                UPDATE product_media
                       SET product_id = :product_id, media_type_id = :media_type_id, url = :url, display_order = :display_order, extension = :extension
                       WHERE id = :id
                """;

        JdbcUtils.update(jdbcClient.sql(UPDATE_PRODUCT_MEDIA_SQL).param("product_id", productMediaDTO.productId()).param("media_type_id", 1).param("url", productMediaDTO.url()).param("display_order", productMediaDTO.displayOrder()).param("extension", productMediaDTO.extension()).param("id", id), "PRODUCT_MEDIA.ID.INVALID");
    }

    // ================================================ DELETE ================================================

    @Override
    public boolean deleteByProductIdAndUrl(Long productId, String url) {

        String DELETE_PRODUCT_MEDIA_SQL = """
                DELETE FROM product_media
                       WHERE product_id = :product_id
                       AND url = :url
                """;

        return JdbcUtils.delete(
                jdbcClient.sql(DELETE_PRODUCT_MEDIA_SQL)
                        .param("product_id", productId)
                        .param("url", url),
                "PRODUCT_MEDIA.ID.INVALID");
    }

    // ================================================ SELECT ================================================

    @Override
    public Optional<ProductMedia> get(Long id) {

        String SELECT_PRODUCT_MEDIA_HQL = """
                SELECT pm FROM ProductMedia pm
                         WHERE pm.id = :id
                """;

        return Optional.ofNullable(entityManager.createQuery(SELECT_PRODUCT_MEDIA_HQL, ProductMedia.class).setParameter("id", id).getSingleResult());
    }

}
