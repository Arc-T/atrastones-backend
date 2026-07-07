package com.sashia.ecommerce.domain.attribute;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

class AttributeSpecs {

    static Specification<Attribute> hasName(String name) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(name))
                return null; // ignored by Spring

            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }
//
//    static Specification<Attribute> hasCode(AttributeSearchDTO search) {
//        return (root, query, cb) -> {
//            if (search.code() == null || search.code().isBlank()) {
//                return null;
//            }
//
//            return cb.equal(root.get("code"), search.code());
//        };
//    }
//
//    static Specification<Attribute> isActive(AttributeSearchDTO search) {
//        return (root, query, cb) -> {
//            if (search.active() == null) {
//                return null;
//            }
//
//            return cb.equal(root.get("active"), search.active());
//        };
//    }
}