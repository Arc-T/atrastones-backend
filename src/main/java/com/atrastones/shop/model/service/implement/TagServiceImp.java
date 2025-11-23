package com.atrastones.shop.model.service.implement;

import com.atrastones.shop.dto.TagDTO;
import com.atrastones.shop.exception.ServiceLogicException;
import com.atrastones.shop.model.repository.contract.TagRepository;
import com.atrastones.shop.model.service.contract.TagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagServiceImp implements TagService {

    private final TagRepository tagRepository;

    public TagServiceImp(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public TagDTO get(Long id) {
        return tagRepository.get(id)
                .map(TagDTO::toDTO)
                .orElseThrow(() -> new ServiceLogicException("TAG.NOT.FOUND"));
    }

    @Override
    public List<TagDTO> getAll() {
        return List.of();
    }

    @Override
    @Transactional
    public Long save(TagDTO tag) {
        return tagRepository.create(tag);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        tagRepository.delete(id);
    }

    @Override
    @Transactional
    public void edit(Long id, TagDTO tag) {
        tagRepository.update(id, tag);
    }

}