package com.atrastones.shop.model.service.contract;

import com.atrastones.shop.dto.create.ProductCreate;
import com.atrastones.shop.dto.search.ProductSearch;
import com.atrastones.shop.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    // ********************************  CRUD  ********************************** //

    void remove(Long id);

    Long save(ProductCreate product);

    void edit(Long id, ProductDTO product);

    Optional<ProductDTO> get(Long id);

    List<ProductDTO> getAll(ProductSearch search);

    Page<ProductDTO> getAllPaginated(Pageable pageable, ProductSearch search);

    List<ProductDTO> getProductsInfoByIds(List<Long> ids); //TODO:list input

    List<ProductDTO> getProductsByCategory(Long categoryId, String sortType);

    List<ProductDTO> searchProductsByAttribute(List<AttributeValueDTO> attributes);

    // ****************************** RELATIONS ******************************* //

    List<TagDTO> getProductTags(Long id);

    List<ProductStatDTO> getProductStats(Long id);

    List<ProductMediaDTO> getProductMedia(Long id);

    List<ProductReviewDTO> getProductReviews(Long id);

    List<ProductAttributeValueDTO> getProductAttributes(Long id);

    // *****************************  PAGE  ************************************/


    // ****************************** OPERATIONS ******************************

    boolean exists(Long id);

}
