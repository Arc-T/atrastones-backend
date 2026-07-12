package com.sashia.ecommerce.domain.catalog.media;

import com.sashia.ecommerce.domain.catalog.media.dto.MediaRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MediaRepository extends JpaRepository<Media, Long> {

    List<Long> createBatch(List<MediaRequest> mediaRequest);

}
