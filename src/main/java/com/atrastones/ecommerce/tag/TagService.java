package com.atrastones.ecommerce.tag;

import com.atrastones.ecommerce.tag.common.TagCreateDTO;
import com.atrastones.ecommerce.tag.common.TagDTO;
import com.atrastones.ecommerce.tag.common.TagSearchDTO;
import com.atrastones.ecommerce.tag.common.TagUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagService {

    // ========================= CREAT =========================

    Long create(TagCreateDTO tag);

    // ========================= READ =========================

    TagDTO get(Long id);

    Page<TagDTO> getAll(TagSearchDTO search, Pageable pageable);

    // ========================= UPDATE =========================

    void edit(Long id, TagUpdateDTO tag);

    // ========================= DELETE =========================

    void delete(Long id);

    // ========================= OPERATION =========================

    boolean exists(Long id);

}