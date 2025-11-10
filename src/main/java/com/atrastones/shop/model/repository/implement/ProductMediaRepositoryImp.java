package com.atrastones.shop.model.repository.implement;

import com.atrastones.shop.dto.ProductMediaDTO;
import com.atrastones.shop.model.repository.contract.ProductMediaRepository;
import com.atrastones.shop.utils.JdbcUtils;
import jakarta.persistence.EntityManager;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductMediaRepositoryImp implements ProductMediaRepository {

    private final JdbcClient jdbcClient;
    private final EntityManager entityManager;

    public ProductMediaRepositoryImp(JdbcClient jdbcClient, EntityManager entityManager) {
        this.jdbcClient = jdbcClient;
        this.entityManager = entityManager;
    }

    // ------------------------------------ CREATE ------------------------------------

    @Override
    public List<Long> createBatch(List<ProductMediaDTO> productMedias) {

        String INSERT_PRODUCT_MEDIA_SQL = """
                INSERT INTO product_media (product_id, media_type_id, url, display_order, extension)
                       VALUES (:product_id, :media_type_id, :url, :display_order, :extension)
                """;

        return JdbcUtils.insertBatch(
                productMedias.stream()
                        .map(media -> jdbcClient.sql(INSERT_PRODUCT_MEDIA_SQL)
                                .param("product_id", media.getProductId())
                                .param("media_type_id", 1)
                                .param("url", media.getUrl())
                                .param("display_order", media.getDisplayOrder())
                                .param("extension", media.getExtension())
                        )
                        .toList()
        );
    }

    // ------------------------------------ UPDATE ------------------------------------

    @Override
    public void update(Long id, ProductMediaDTO productMedia) {

        String UPDATE_PRODUCT_MEDIA_SQL = """
                UPDATE product_media
                       SET product_id = :product_id, media_type_id = :media_type_id, url = :url, display_order = :display_order, extension = :extension
                       WHERE id = :id
                """;

        JdbcUtils.update(
                jdbcClient.sql(UPDATE_PRODUCT_MEDIA_SQL)
                        .param("product_id", productMedia.getProductId())
                        .param("media_type_id", 1)
                        .param("url", productMedia.getUrl())
                        .param("display_order", productMedia.getDisplayOrder())
                        .param("extension", productMedia.getExtension())
                        .param("id", id)
                , "PRODUCT_MEDIA.ID.INVALID"
        );
    }

    // ------------------------------------ DELETE ------------------------------------

    @Override
    public boolean delete(Long id) {

        String DELETE_PRODUCT_MEDIA_SQL = """
                DELETE FROM product_media WHERE id = :id
                """;

        return JdbcUtils.delete(
                jdbcClient.sql(DELETE_PRODUCT_MEDIA_SQL)
                        .param("id", id)
                , "PRODUCT_MEDIA.ID.INVALID"
        );
    }

    // ------------------------------------ SELECT ------------------------------------

}
