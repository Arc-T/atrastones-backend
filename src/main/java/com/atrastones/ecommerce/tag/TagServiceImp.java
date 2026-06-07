package com.atrastones.ecommerce.tag;

import com.atrastones.ecommerce.tag.common.TagCreateDTO;
import com.atrastones.ecommerce.tag.common.TagDTO;
import com.atrastones.ecommerce.tag.common.TagSearchDTO;
import com.atrastones.ecommerce.tag.common.TagUpdateDTO;
import com.atrastones.infrastructure.error.InvalidResourceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TagServiceImp implements TagService {

    private final TagRepository tagRepository;

    public TagServiceImp(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public TagDTO get(Long id) {
        return tagRepository.get(id).map(TagDTO::toDTO)
                .orElseThrow(() -> new InvalidResourceException("tag.not.found"));
    }

    @Override
    public Page<TagDTO> getAll(TagSearchDTO search, Pageable pageable) {
        return tagRepository.getAll(search, pageable).map(TagDTO::toDTO);
    }

    @Override
    @Transactional
    public Long create(TagCreateDTO tag) {
        return tagRepository.create(tag);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!tagRepository.exists(id))
            throw new InvalidResourceException("tag.not.found");
        tagRepository.delete(id);
    }

    @Override
    @Transactional
    public void edit(Long id, TagUpdateDTO tag) {
        if (!tagRepository.exists(id))
            throw new InvalidResourceException("tag.not.found");

        tagRepository.update(id, tag);
    }

    @Override
    public boolean exists(Long id) {
        return false;
    }

}