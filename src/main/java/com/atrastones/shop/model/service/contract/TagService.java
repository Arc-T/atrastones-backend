package com.atrastones.shop.model.service.contract;

import java.util.List;

import com.atrastones.shop.dto.TagDTO;

public interface TagService {

    // ****************************** CRUD ******************************

     TagDTO get(Long id);

    List<TagDTO> getAll();

    Long save(TagDTO tag);

    void remove(Long id);

    void edit(Long id, TagDTO tag);

}