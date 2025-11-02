package com.atrastones.shop.model.repository.contract;

import com.atrastones.shop.dto.TagDTO;
import com.atrastones.shop.model.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagRepository {

    // ****************************** CRUD ******************************

    boolean delete(Long id);

    Long create(TagDTO tag);

    void update(Long id, TagDTO tag);

    Page<Tag> getAllPaginated(Pageable pageable);

    // ****************************** RELATIONS ******************************

    // ****************************** PAGES ******************************

    // ****************************** OPERATIONS ******************************

    /**
     * Counts the total number of attributes.
     *
     * @return the total count
     */
    long count();

    boolean exists(Long id);

}
