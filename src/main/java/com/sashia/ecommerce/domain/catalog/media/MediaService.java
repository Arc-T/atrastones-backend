package com.sashia.ecommerce.domain.catalog.media;

import com.sashia.ecommerce.domain.catalog.media.dto.MediaCreateRequest;
import com.sashia.ecommerce.domain.catalog.media.dto.MediaResponse;

import java.util.List;

public interface MediaService {

    List<Long> create(Long productId);

    void createDraft(MediaCreateRequest mediaCreateRequest);

    void createDraft(Long productId, MediaCreateRequest mediaCreateRequest);

    List<MediaResponse> readAllDraft();

    List<MediaResponse> readDraft(Long productId);

    void update(Long productId);

    void delete(Long id);

    void deleteDraft(String fileName);

    void deleteDraft(Long productId, String fileName);

}
