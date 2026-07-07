package com.sashia.ecommerce.domain.tag;

import com.sashia.ecommerce.domain.tag.common.TagCreateDTO;
import com.sashia.ecommerce.domain.tag.common.TagSearchDTO;
import com.sashia.ecommerce.domain.tag.common.TagUpdateDTO;
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
