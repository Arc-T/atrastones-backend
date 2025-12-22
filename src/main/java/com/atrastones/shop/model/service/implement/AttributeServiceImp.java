package com.atrastones.shop.model.service.implement;

import com.atrastones.shop.dto.search.AttributeSearch;
import com.atrastones.shop.dto.AttributeDTO;
import com.atrastones.shop.model.repository.contract.AttributeRepository;
import com.atrastones.shop.model.service.contract.AttributeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
public class AttributeServiceImp implements AttributeService {

    private final AttributeRepository attributeRepository;

    public AttributeServiceImp(AttributeRepository attributeRepository) {
        this.attributeRepository = attributeRepository;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        attributeRepository.delete(id);
    }

    @Override
    @Transactional
    public Long save(AttributeDTO attribute) {
        return attributeRepository.create(attribute);
    }

    @Override
    @Transactional
    public void update(Long id, AttributeDTO attribute) {
        attributeRepository.update(id, attribute);
    }

    @Override
    public AttributeDTO get(Long id) {
        return attributeRepository.get(id).map(AttributeDTO::toDTO)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<AttributeDTO> getAll(AttributeSearch search) {
        return attributeRepository.getAll(search).stream().map(AttributeDTO::toDTO).toList();
    }

    @Override
    public Page<AttributeDTO> getAllPaginated(AttributeSearch search, Pageable pageable) {
        return attributeRepository.getAllPaginated(search, pageable).map(AttributeDTO::toFullDTO);
    }

    @Override
    public List<AttributeDTO> getAllByCategoryId(Long categoryId) {
        return List.of();
    }

    @Override
    public boolean exists(Long id) {
        return false;
    }

}
