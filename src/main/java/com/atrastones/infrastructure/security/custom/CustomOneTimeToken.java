package com.atrastones.infrastructure.security.custom;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ott.*;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

@Configuration
public class CustomOneTimeToken implements OneTimeTokenService {

    @Override
    @NonNull
    public OneTimeToken generate(GenerateOneTimeTokenRequest request) {
        return new DefaultOneTimeToken(
                String.valueOf(ThreadLocalRandom.current().nextInt(1000, 10000)),
                request.getUsername(),
                Instant.ofEpochMilli(System.currentTimeMillis())
        );
    }

    @Override
    public @Nullable OneTimeToken consume(@NonNull OneTimeTokenAuthenticationToken authenticationToken) {
//        LocalDateTime otpExpireTime = getPhoneLatestSmsMessage(((CustomUserDetails) authenticationToken.getPrincipal()).getUsername())
//                .map(SmsDTO::createdAt)
//                .map(this::calculateRemainingTtl);
        return null;
    }

}
