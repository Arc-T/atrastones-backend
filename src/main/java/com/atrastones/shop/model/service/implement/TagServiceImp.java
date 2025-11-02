package com.atrastones.shop.model.service.implement;

import com.atrastones.shop.dto.TagDTO;
import com.atrastones.shop.model.repository.contract.TagRepository;
import com.atrastones.shop.model.service.contract.TagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TagServiceImp implements TagService {

    private final TagRepository tagRepository;

    public TagServiceImp(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Optional<TagDTO> get(Long id) {
        return Optional.empty();
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