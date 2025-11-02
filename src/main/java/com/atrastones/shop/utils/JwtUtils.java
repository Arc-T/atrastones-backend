package com.atrastones.shop.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

/**
 * Utility class for JWT token generation, validation, and extraction.
 * Handles access tokens (short-lived, with roles) and refresh tokens (long-lived, minimal claims).
 * Uses HMAC256 algorithm with a fixed secret—consider externalizing for prod.
 * All times are in UTC via system clock for consistency.
 *
 * @author YourName
 */
public class JwtUtils {

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

    /**
     * Builds the HMAC256 algorithm instance lazily.
     * Uses UTF-8 encoding for the secret to avoid platform defaults.
     *
     * @return configured Algorithm
     */
    private static Algorithm algorithm() {
        return Algorithm.HMAC256(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Generates a short-lived access token with optional extra claims.
     * Includes username as subject, roles as claim, and sets iat/exp.
     * Use for API requests; always pair with refresh flow for long sessions.
     *
     * @param userDetails the authenticated user
     * @param extraClaims additional payload (e.g., permissions, device ID)
     * @return compact JWT string
     */
    public static String generateToken(UserDetails userDetails, Map<String, Object> extraClaims) {
        Instant now = Instant.now(CLOCK);
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(now.plusMillis(EXPIRATION_TIME)))
                .withClaim("roles", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).toList())
                .withPayload(extraClaims)
                .sign(algorithm());
    }

    /**
     * Convenience overload for access token without extra claims.
     * Delegates to full method for DRY principle.
     *
     * @param userDetails the authenticated user
     * @return compact JWT string
     */
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

    /**
     * Extracts the username (subject) from a valid JWT.
     * Performs full verification; throws JWTVerificationException on invalid/expired.
     * Use before loading UserDetails to avoid unnecessary DB hits.
     *
     * @param token the JWT string
     * @return username string
     * @throws JWTVerificationException if token is invalid
     */
    public static String extractUsername(String token) {
        return getVerifier().verify(token).getSubject();
    }

    /**
     * Validates a token against the provided UserDetails.
     * Checks signature, expiry, subject match, and iat sanity (not future-issued).
     * Uses consistent clock and leeway for robustness.
     * Returns false on any failure (including expiry)—no exceptions bubble up here.
     *
     * @param token the JWT string
     * @param userDetails expected user for comparison
     * @return true if valid and fresh
     */
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
            if (e instanceof TokenExpiredException) {
                // Optional: Log expiry for metrics (e.g., via SLF4J)
            }
            return false; // Swallow and return false—callers handle rejection
        }
    }

    /**
     * Extracts the expiration timestamp (Unix millis) from a valid token.
     * Useful for client-side expiry checks or logging remaining TTL.
     * Throws on invalid token—wrap in try-catch if needed.
     *
     * @param token the JWT string
     * @return exp time as long
     * @throws IllegalArgumentException wrapping verification failure
     */
    public static long getExpirationTime(String token) {
        try {
            DecodedJWT decoded = getVerifier().verify(token);
            return decoded.getExpiresAt().getTime(); // Millis since epoch
        } catch (JWTVerificationException e) {
            throw new IllegalArgumentException("Invalid or expired token", e);
        }
    }

    /**
     * Calculates remaining milliseconds until token expiry.
     * Returns 0 if already expired (for easy >=0 checks).
     * Relies on system clock; use for proactive refresh decisions.
     *
     * @param token the JWT string
     * @return remaining time in millis (>=0)
     */
    public static long getRemainingTime(String token) {
        long expTime = getExpirationTime(token);
        Instant now = Instant.now(CLOCK);
        return Math.max(0, expTime - now.toEpochMilli()); // Clamp to 0 for expired
    }

    /**
     * Builds a reusable JWT verifier with leeway for clock skew.
     * Leeway allows minor NTP de-syncs without false expiries.
     * Tune based on your infra (e.g., 60s for distributed systems).
     *
     * @return configured verifier
     */
    private static JWTVerifier getVerifier() {
        return JWT.require(algorithm())
                .acceptLeeway(30) // 30s tolerance—balances security/usability
                .build();
    }

}