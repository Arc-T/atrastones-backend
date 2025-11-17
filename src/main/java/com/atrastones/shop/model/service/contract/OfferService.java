package com.atrastones.shop.model.service.contract;

import com.atrastones.shop.api.search.OfferSearch;
import com.atrastones.shop.dto.OfferDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OfferService {

    /* ******************************** CRUD ******************************** */

    Optional<OfferDTO> get(Long id);

    List<OfferDTO> getAll(OfferSearch filter);

    Page<OfferDTO> getAllPageable(Pageable pageable, OfferSearch filter);

    void remove(Long id);

    Long save(OfferDTO category);

    void edit(Long id, OfferDTO category);

}
