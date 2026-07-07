package com.sashia.ecommerce.domain.payment.opg.provider.zarinpal;

import com.sashia.ecommerce.domain.payment.opg.Bank;
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
