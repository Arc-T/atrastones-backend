package com.sashia.ecommerce.discount.internal;

import com.sashia.ecommerce.discount.dto.DiscountSearchDTO;
import com.sashia.ecommerce.discount.DiscountService;
import com.sashia.ecommerce.discount.dto.DiscountCreateDTO;
import com.sashia.ecommerce.discount.dto.DiscountDTO;
import com.sashia.ecommerce.discount.dto.DiscountEditDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "/discounts")
class DiscountController {

    private final DiscountService discountService;

    DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ_ALL_DISCOUNTS')")
    ResponseEntity<Page<DiscountDTO>> readAll(Pageable pageable, DiscountSearchDTO search) {
        return ResponseEntity.ok(discountService.getAll(pageable, search));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ_DISCOUNT')")
    ResponseEntity<DiscountDTO> read(@PathVariable Long id) {
        return ResponseEntity.ok(discountService.get(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_DISCOUNT')")
    ResponseEntity<DiscountDTO> create(@RequestBody @Valid DiscountCreateDTO discount) {
        return ResponseEntity.created(URI.create("/discounts/" + discountService.save(discount))).build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_DISCOUNT')")
    ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid DiscountEditDTO discount) {
        discountService.update(id, discount);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_DISCOUNT')")
    ResponseEntity<?> readAll(@PathVariable Long id) {
        discountService.delete(id);
        return ResponseEntity.notFound().build();
    }

}
