package com.atrastones.shop.utils.sms;

import com.atrastones.shop.dto.SmsDTO;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;

@Slf4j
@Setter
@Component
@ConfigurationProperties(prefix = "sms.mellipayamak")
public class MelliPayamak extends Sms {

    private String username;

    private String password;

    private String from;

    private String url;

    private final RestClient restClient;

    public MelliPayamak() {
        this.restClient = RestClient.builder().baseUrl(url).build();
    }

    @Override
    protected String doSend(String phone, String message) {

        record MelliPayamakResponse(String Value,
                                    String RetStatus,
                                    String StrRetStatus) {
        }

        record SmsRequest(String username,
                          String password,
                          String from,
                          String phone,
                          String text,
                          Boolean isflash) {
        }

        SmsRequest requestBody = new SmsRequest(username, password, from, phone, message, true);

        try {

            MelliPayamakResponse response = restClient.post()
                    .uri("")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(requestBody)
                    .retrieve()
                    .body(MelliPayamakResponse.class);

//            SmsDTO.builder()
//                    .phone(phone)
//                    .statusId(Long.getLong(response.RetStatus))
//                    .text(message)
//                    .response(response.StrRetStatus())
//                    .description("Something for test")
//                    .createdAt(LocalDateTime.now())
//                    .build();

        } catch (Exception ex) {
            log.error("Failed to send SMS to phone={}: error={}", phone, ex.getMessage(), ex);
        }

        return phone;
    }

    @Override
    public SmsProvider getSMSProvider() {
        return SmsProvider.MELLI_PAYAMAK;
    }
}
