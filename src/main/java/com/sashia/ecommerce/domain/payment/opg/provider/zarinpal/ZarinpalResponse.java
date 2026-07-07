package com.sashia.ecommerce.domain.payment.opg.provider.zarinpal;

record ZarinpalResponse(
        SuccessResponse data,
        ErrorResponse errors
) {
    record SuccessResponse(
            Long code,
            String message,
            String authority,
            String feeType,
            Integer fee
    ) {
    }

    record ErrorResponse() {
    }

}
