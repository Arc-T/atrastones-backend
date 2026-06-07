package com.atrastones.notification.sms;

import com.atrastones.notification.sms.common.SMSProvider;

public interface SMSFactoryContract {

    SMSContract getService(SMSProvider provider);

}
