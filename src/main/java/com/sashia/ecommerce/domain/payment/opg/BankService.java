package com.sashia.ecommerce.domain.payment.opg;

public interface BankService {

    void initiatePayment();

    boolean checkPayment();

}
