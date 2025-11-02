package com.atrastones.shop.model.service.contract;

import com.atrastones.shop.dto.TagDTO;

import java.util.List;
import java.util.Optional;

public interface TagService {

    // ****************************** CRUD ******************************

    Optional<TagDTO> get(Long id);

    List<TagDTO> getAll();

    Long save(TagDTO tag);

    void remove(Long id);

    void edit(Long id, TagDTO tag);

}