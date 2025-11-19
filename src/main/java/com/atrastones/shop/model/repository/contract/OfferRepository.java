package com.atrastones.shop.model.repository.contract;

import com.atrastones.shop.api.search.OfferSearch;
import com.atrastones.shop.dto.OfferDTO;
import com.atrastones.shop.model.entity.Category;
import com.atrastones.shop.model.entity.Offer;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OfferRepository {

    // ----------------------------- CREATE --------------------------------------

    long create(OfferDTO offer);

    // ----------------------------- UPDATE --------------------------------------

    void update(long id, OfferDTO offer);

    // ----------------------------- SELECT --------------------------------------

    Optional<Offer> get(Long id);

    List<Offer> getAll(OfferSearch search);

    Page<Offer> getAllPaginated(Pageable pageable, OfferSearch search);

    // ----------------------------- OPERATIONS --------------------------------------

    Long count();

    boolean exists(Long id);

}
