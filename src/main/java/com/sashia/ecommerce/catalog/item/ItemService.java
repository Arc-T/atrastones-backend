package com.sashia.ecommerce.catalog.item;

import com.sashia.ecommerce.catalog.item.dto.ItemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ItemService {

    Page<ItemDTO> getAll(Pageable pageable);

    Optional<ItemDTO> get(Long id);

}
