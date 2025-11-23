package com.atrastones.shop.model.repository.contract;

import com.atrastones.shop.dto.TagDTO;
import com.atrastones.shop.model.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TagRepository {

    // ============================ CRUD ============================

    boolean delete(Long id);

    Long create(TagDTO tag);

    void update(Long id, TagDTO tag);

    Optional<Tag> get(Long id);

    Page<Tag> getAllPaginated(Pageable pageable);

    // ============================ RELATIONS ============================

    // ============================ PAGES ============================

    // ============================ OPERATIONS ============================

    long count();

    boolean exists(Long id);

}
