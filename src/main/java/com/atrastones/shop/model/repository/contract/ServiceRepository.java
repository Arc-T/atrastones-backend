package com.atrastones.shop.model.repository.contract;

import com.atrastones.shop.dto.search.ServiceSearch;
import com.atrastones.shop.dto.ServiceDTO;
import com.atrastones.shop.model.entity.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ServiceRepository {

    // ----------------------------- CREATE --------------------------------------

    long create(ServiceDTO service);

    // ----------------------------- UPDATE --------------------------------------

    void update(long id, ServiceDTO service);

    // -------------------------------------- DELETE --------------------------------------

    boolean delete(Long id);

    // ----------------------------- SELECT --------------------------------------

    Optional<Service> get(Long id);

    List<Service> getAll(ServiceSearch search);

    Page<Service> getAllPaginated(Pageable pageable, ServiceSearch search);

    // ----------------------------- OPERATIONS --------------------------------------

    Long count();

    boolean exists(Long id);

}
