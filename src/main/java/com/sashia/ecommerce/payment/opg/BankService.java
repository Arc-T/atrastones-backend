package com.sashia.ecommerce.payment.opg;

public interface BankService {

    void initiatePayment();

    boolean checkPayment();

}
