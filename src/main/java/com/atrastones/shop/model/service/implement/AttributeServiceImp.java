package com.atrastones.shop.model.service.implement;

import com.atrastones.shop.dto.AttributeDTO;
import com.atrastones.shop.mapper.contract.AttributeMapper;
import com.atrastones.shop.model.repository.contract.AttributeRepository;
import com.atrastones.shop.model.service.contract.AttributeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AttributeServiceImp implements AttributeService {

    private final AttributeRepository attributeRepository;
    private final AttributeMapper attributeMapper;

    public AttributeServiceImp(AttributeRepository attributeRepository, AttributeMapper attributeMapper) {
        this.attributeRepository = attributeRepository;
        this.attributeMapper = attributeMapper;
    }

    @Override
    @Transactional
    public void remove(Long id) {
        attributeRepository.delete(id);
    }

    @Override
    @Transactional
    public Long save(AttributeDTO attribute) {
        return attributeRepository.create(attribute);
    }

    @Override
    @Transactional
    public void edit(Long id, AttributeDTO attribute) {
        attributeRepository.update(id, attribute);
    }

    @Override
    public Optional<AttributeDTO> get(Long id) {
        return Optional.empty();
    }

    @Override
    public List<AttributeDTO> getAll() {
        return List.of();
    }

    @Override
    public Page<AttributeDTO> getAllPaginated(Pageable pageable) {
        return attributeRepository.getAllPaginated(pageable).map(attributeMapper::toFullDTO);
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
