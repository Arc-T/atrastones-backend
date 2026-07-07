package com.sashia.ecommerce.domain.notification.sms;

import com.sashia.ecommerce.domain.notification.common.SMSDTO;
import com.sashia.ecommerce.domain.notification.common.SMSEventDTO;
import com.sashia.ecommerce.domain.notification.common.SMSService;
import com.sashia.ecommerce.domain.notification.common.SMSType;
import com.sashia.ecommerce.domain.notification.sms.common.SMSProvider;
import com.sashia.ecommerce.domain.notification.sms.common.SMSTemplateDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Optional;

@Service
public class SMSServiceImp implements SMSService {

    private final SMSRepository smsRepository;
    private final SMSFactoryContract smsFactoryContract;

    public SMSServiceImp(SMSRepository smsRepository, SMSFactoryContract smsFactoryContract) {
        this.smsRepository = smsRepository;
        this.smsFactoryContract = smsFactoryContract;
    }

    @Override
    public Long create(SMSDTO smsDTO) {
        return smsRepository.create(smsDTO);
    }

    @Override
    public Optional<SMSDTO> get(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<SMSTemplateDTO> getTemplateByType(SMSType smsType) {
        return smsRepository.getTemplateByType(smsType).map(SMSTemplateDTO::toDTO);
    }

    @Override
    public Optional<SMSDTO> getPhoneLatestSmsMessage(String phone) {
        return Optional.empty();
    }

    // ****************************** OPERATIONS ******************************

    @Override
    @TransactionalEventListener
    public void sendSms(SMSEventDTO event) {
        smsFactoryContract.getService(SMSProvider.MELLI_PAYAMAK).send(event);
    }

}