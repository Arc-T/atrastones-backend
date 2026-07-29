package com.sashia.ecommerce.promotion.engine.pipeline.handler;

import jakarta.validation.constraints.Null;

public record PromotionHandlerResult(
        boolean proceed,
        @Null String reason) {

    public static PromotionHandlerResult success() {
        return new PromotionHandlerResult(true, null);
    }

    public static PromotionHandlerResult failure(String reason) {
        return new PromotionHandlerResult(false, reason);
    }

}
//public enum HandlerFailureReason {
//
//    PROMOTION_INACTIVE,
//
//    PROMOTION_EXPIRED,
//
//    INVALID_SCOPE,
//
//    CONDITIONS_NOT_MET,
//
//    NO_TARGET_MATCH,
//
//    EXECUTION_FAILED
//
//}
