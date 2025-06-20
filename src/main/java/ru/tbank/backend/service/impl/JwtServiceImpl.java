package ru.tbank.backend.service.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.tbank.backend.config.userDetails.CustomUserDetails;
import ru.tbank.backend.service.JwtService;
import io.jsonwebtoken.Claims;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.function.Function;

@RequiredArgsConstructor
@Service
@Slf4j
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.signing-key}")
    private String jwtSigningKey;

    @Value("${jwt.expiration-time}")
    private Long jwtExpirationMs;

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(CustomUserDetails user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("token_id", UUID.randomUUID());
        return generateToken(claims, user);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            final String userName = extractUserName(token);
            boolean isValid = userName.equals(userDetails.getUsername())
                    && !isTokenExpired(token)
                    && !isTokenBanned(token);

            log.info("Token validation result: " + isValid);
            return isValid;
        } catch (Exception e) {
            log.error("Token validation error", e);
            return false;
        }
    }

    public void banToken(String token) {
        UUID tokenId = extractTokenId(token);
        Long expirationMs = extractExpirationMs(token);
//        bannedTokenRepository.addBannedToken(tokenId, expirationMs);
    }

    private Boolean isTokenBanned(String token) {
        return false;
    }

    private Long extractExpirationMs(String token) {
        return extractExpiration(token).getTime();
    }

    private UUID extractTokenId(String token) {
        return extractClaim(token, claims -> UUID.fromString(claims.get("token_id", String.class)));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        String base64Key = Base64.getEncoder().encodeToString(jwtSigningKey.getBytes(StandardCharsets.UTF_8));
        byte[] keyBytes = Decoders.BASE64.decode(base64Key);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}