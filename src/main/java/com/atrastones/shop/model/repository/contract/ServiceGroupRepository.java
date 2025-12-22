package com.atrastones.shop.model.repository.contract;

import com.atrastones.shop.dto.search.ServiceGroupSearch;
import com.atrastones.shop.dto.ServiceGroupDTO;
import com.atrastones.shop.model.entity.ServiceGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ServiceGroupRepository {

    // ----------------------------- CREATE --------------------------------------

    long create(ServiceGroupDTO service);

    // ----------------------------- UPDATE --------------------------------------

    void update(long id, ServiceGroupDTO service);

    // -------------------------------------- DELETE --------------------------------------

    boolean delete(Long id);

    // ----------------------------- SELECT --------------------------------------

    Optional<ServiceGroup> get(Long id);

    List<ServiceGroup> getAll(ServiceGroupSearch search);

    Page<ServiceGroup> getAllPaginated(Pageable pageable, ServiceGroupSearch search);

    // ----------------------------- OPERATIONS --------------------------------------

    Long count();

    boolean exists(Long id);

}
