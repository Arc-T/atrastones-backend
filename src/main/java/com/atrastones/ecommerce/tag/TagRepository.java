package com.atrastones.ecommerce.tag;

import com.atrastones.ecommerce.tag.common.TagCreateDTO;
import com.atrastones.ecommerce.tag.common.TagSearchDTO;
import com.atrastones.ecommerce.tag.common.TagUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TagRepository {

    // ============================ CREATE ============================

    Long create(TagCreateDTO tag);

    // ============================ GET ============================

    Optional<Tag> get(Long id);

    Page<Tag> getAll(TagSearchDTO search, Pageable pageable);

    // ============================ UPDATE ============================

    void update(Long id, TagUpdateDTO tag);

    // ============================ DELETE ============================

    boolean delete(Long id);

    // ============================ OPERATIONS ============================

    long count();

    boolean exists(Long id);

}
