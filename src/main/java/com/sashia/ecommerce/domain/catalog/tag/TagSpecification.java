package com.sashia.ecommerce.domain.catalog.tag;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class TagSpecification {

    static Specification<Tag> hasName(String name) {
        return (root, _, cb) -> {
            if (!StringUtils.hasText(name))
                return null;

            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

}
