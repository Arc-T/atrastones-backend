package com.atrastones.shop.model.service.implement;

import com.atrastones.shop.api.search.OfferSearch;
import com.atrastones.shop.dto.OfferDTO;
import com.atrastones.shop.model.repository.contract.OfferRepository;
import com.atrastones.shop.model.service.contract.OfferService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfferServiceImp implements OfferService {

    private final OfferRepository offerRepository;

    public OfferServiceImp(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Override
    public Optional<OfferDTO> get(Long id) {
        return Optional.empty();
    }

    @Override
    public List<OfferDTO> getAll(OfferSearch search) {
        return List.of();
    }

    @Override
    public Page<OfferDTO> getAllPageable(Pageable pageable, OfferSearch search) {
        return offerRepository.getAllPaginated(pageable, search).map(OfferDTO::toDTO);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public Long save(OfferDTO category) {
        return 0L;
    }

    @Override
    public void edit(Long id, OfferDTO category) {

    }
}
