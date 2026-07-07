package com.sashia.ecommerce.domain.notification.sms;

import com.sashia.ecommerce.domain.notification.sms.common.SMSProvider;

public interface SMSFactoryContract {

    SMSContract getService(SMSProvider provider);

}
