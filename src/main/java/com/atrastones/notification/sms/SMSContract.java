package com.atrastones.notification.sms;

import com.atrastones.notification.common.SMSEventDTO;
import com.atrastones.notification.sms.common.SMSProvider;

public interface SMSContract {

    SMSProvider getProvider();

    void send(SMSEventDTO event);

}
