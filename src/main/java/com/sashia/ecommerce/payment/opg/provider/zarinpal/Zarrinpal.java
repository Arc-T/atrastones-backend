package com.sashia.ecommerce.payment.opg.provider.zarinpal;

import com.sashia.ecommerce.payment.opg.Bank;
import org.springframework.stereotype.Component;

@Component
public class Zarrinpal extends Bank {

    @Override
    public void initiatePayment() {

    }

    @Override
    public boolean checkPayment() {
        return false;
    }

}
