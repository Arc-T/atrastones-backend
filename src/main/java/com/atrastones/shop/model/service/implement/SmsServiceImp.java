package com.atrastones.shop.model.service.implement;

import com.atrastones.shop.dto.SmsDTO;
import com.atrastones.shop.model.service.contract.SmsService;
import com.atrastones.shop.type.SmsType;
import com.atrastones.shop.utils.sms.SmsFactory;
import com.atrastones.shop.utils.sms.SmsProvider;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class SmsServiceImp implements SmsService {

    private final SmsFactory smsFactory;

    public SmsServiceImp(SmsFactory smsFactory) {
        this.smsFactory = smsFactory;
    }

    @Override
    public void sendSms(String phone, String message) {
        smsFactory.getSMSService(SmsProvider.MELLI_PAYAMAK)
                  .send(phone, message);
    }

    @Override
    public Optional<SmsDTO> getPhoneLatestSmsMessage(String phone) {
        return Optional.empty();
    }

    @Override
    public Optional<SmsDTO> get(Long id) {
        return Optional.empty();
    }

    @Override
    public String getMessageTemplateByType(SmsType smsType) {
        return "";
    }

    @Override
    public int getOrCreateTtl(String phone) {
        return getPhoneLatestSmsMessage(phone)
                .map(SmsDTO::getCreatedAt)
                .map(this::calculateRemainingTtl)
                .filter(remaining -> remaining > 0)
                .orElseGet(() -> sendNewOtp(phone));
    }

    private String createNewOtpCode() {
        return String.valueOf(ThreadLocalRandom.current().nextInt(1000, 10000));
    }

    private int sendNewOtp(String phone) {
        String otpCode = createNewOtpCode();
        SmsDTO sms = SmsDTO.builder()
                .description(otpCode)
                .phone(phone)
                .text(MessageFormat.format(
                        getMessageTemplateByType(SmsType.OTP), otpCode))
                .build();
        sendSms(phone, sms.getText());
        return 120;
    }

    private int calculateRemainingTtl(LocalDateTime from) {
        long secondsElapsed = Duration.between(from, LocalDateTime.now()).getSeconds();
        return (int) Math.max(0, 120 - secondsElapsed);
    }

}
