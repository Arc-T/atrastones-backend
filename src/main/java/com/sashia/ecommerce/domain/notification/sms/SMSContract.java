package com.sashia.ecommerce.domain.notification.sms;

import com.sashia.ecommerce.domain.notification.common.SMSEventDTO;
import com.sashia.ecommerce.domain.notification.sms.common.SMSProvider;

public interface SMSContract {

    SMSProvider getProvider();

    void send(SMSEventDTO event);

}
