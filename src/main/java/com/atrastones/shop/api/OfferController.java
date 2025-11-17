package com.atrastones.shop.api;

import com.atrastones.shop.api.search.OfferSearch;
import com.atrastones.shop.dto.OfferDTO;
import com.atrastones.shop.dto.TagDTO;
import com.atrastones.shop.model.service.contract.OfferService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/offers")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping
    public ResponseEntity<List<OfferDTO>> readAll(OfferSearch search) {
        return ResponseEntity.ok(offerService.getAll(search));
    }

    @PostMapping
    public ResponseEntity<TagDTO> create(@Valid @RequestBody OfferDTO offer) {
        return ResponseEntity.created(URI.create("/tags/" + offerService.save(offer)))
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody OfferDTO offer) {
        offerService.edit(id, offer);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        offerService.remove(id);
        return ResponseEntity.noContent().build();
    }

}
