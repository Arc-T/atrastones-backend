package com.atrastones.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

public final class JwtUtils {

    private JwtUtils() {
    }

    /**
     * Shared secret for HMAC signing. In production, load from env/config to avoid hardcoding.
     * Must be kept secure and rotated periodically.
     */
    private static final String SECRET = "mE3RBNjPAhCBz2UGMxNDQREHf1IZio6C";

    /**
     * Access token lifetime: 1 hour. Balances security (short expiry) with UX (not too frequent refreshes).
     * Adjust based on app sensitivity; use L suffix to prevent int overflow.
     */
    private static final long EXPIRATION_TIME = 3600000L;

    /**
     * Refresh token lifetime: 7 days. Allows session persistence without re-auth, but revocable.
     * Shorter for high-security apps; pair with token blacklisting.
     */
    private static final long REFRESH_EXPIRATION_TIME = 604800000L; // 7 Days

    /**
     * UTC clock for timestamp consistency across generation/validation.
     * Prevents issues from local timezones or NTP drift.
     */
    private static final Clock CLOCK = Clock.systemUTC();

    private static Algorithm algorithm() {
        return Algorithm.HMAC256(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    public static String generateToken(UserDetails userDetails, Map<String, Object> extraClaims) {
        Instant now = Instant.now(CLOCK);
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(now.plusMillis(EXPIRATION_TIME)))
                .withClaim("permissions", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).toList())
                .withPayload(extraClaims)
                .sign(algorithm());
    }

    public static String generateToken(UserDetails userDetails) {
        return generateToken(userDetails, Map.of()); // Empty map for no extras
    }

    /**
     * Generates a long-lived refresh token for renewing access tokens.
     * Minimal claims (only subject + iat/exp) to reduce attack surface.
     * Store securely (e.g., httpOnly cookie); revoke on logout/suspicion.
     *
     * @param userDetails the authenticated user
     * @return compact JWT string
     */
    public static String generateRefreshToken(UserDetails userDetails) {
        Instant now = Instant.now(CLOCK);
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(now.plusMillis(REFRESH_EXPIRATION_TIME))) // Enforces REFRESH_EXPIRATION_TIME
                .sign(algorithm()); // No roles/payload—keeps it lightweight and stateless
    }

    public static String extractUsername(String token) {
        return getVerifier().verify(token).getSubject();
    }

    public static boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            DecodedJWT decoded = getVerifier().verify(token);
            Instant now = Instant.now(CLOCK);
            boolean notExpired = decoded.getExpiresAt().after(Date.from(now));
            boolean usernameMatches = decoded.getSubject().equals(userDetails.getUsername());
            // Anti-replay: Reject if iat is in the future (clock skew or tampering)
            boolean notIssuedInFuture = decoded.getIssuedAt().before(Date.from(now));
            return notExpired && usernameMatches && notIssuedInFuture;
        } catch (JWTVerificationException e) {
            // Optional: Log expiry for metrics (e.g., via SLF4J)
            return false; // Swallow and return false—callers handle rejection
        }
    }

    public static long getExpirationTime(String token) {
        try {
            DecodedJWT decoded = getVerifier().verify(token);
            return decoded.getExpiresAt().getTime(); // Millis since epoch
        } catch (JWTVerificationException e) {
            throw new IllegalArgumentException("Invalid or expired token", e);
        }
    }

    public static long getRemainingTime(String token) {
        long expTime = getExpirationTime(token);
        Instant now = Instant.now(CLOCK);
        return Math.max(0, expTime - now.toEpochMilli()); // Clamp to 0 for expired
    }

    private static JWTVerifier getVerifier() {
        return JWT.require(algorithm())
                .acceptLeeway(30) // 30s tolerance—balances security/usability
                .build();
    }

}