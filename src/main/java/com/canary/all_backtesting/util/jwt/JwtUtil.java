package com.canary.all_backtesting.util.jwt;

import com.canary.all_backtesting.domain.user.Role;
import com.canary.all_backtesting.util.jwt.exception.JwtUtilsException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

import static com.canary.all_backtesting.util.jwt.exception.JwtUtilsErrorCode.*;


@Slf4j
@Component
public class JwtUtil {

    private static SecretKey secretKey;
    private static String issuer;
    private static Long accessExpireMs;

    public JwtUtil(@Value("${jwt.secret}") String secret,
                   @Value("${jwt.access-expiration}") Long accessExpireMs,
                   @Value("${spring.application.name}") String issuer
    ) {
        secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        JwtUtil.issuer = issuer;
        JwtUtil.accessExpireMs = accessExpireMs;
    }

    public JwtTokenResponse createJwtToken(CreateJwtRequest request) {
        String accessToken = createAccessToken(request);
        return new JwtTokenResponse(accessToken, accessExpireMs / 1000);
    }

    private String createAccessToken(CreateJwtRequest request) {
        return Jwts.builder()
                .issuedAt(new Date(request.getNowMS()))
                .issuer(issuer)
                .claim("username", request.getUsername())
                .claim("role", request.getRole().name())
                .expiration(new Date(request.getNowMS() + accessExpireMs))
                .signWith(secretKey)
                .compact();
    }

    public String getUserName(String accessToken) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(accessToken)
                .getPayload()
                .get("username", String.class);
    }

    public Role getUserRole(String accessToken) {
        String roleName = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(accessToken)
                .getPayload()
                .get("role", String.class);
        return Role.valueOf(roleName);
    }

    public void validateToken(String accessToken) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(accessToken);
        } catch (ExpiredJwtException e) {
            log.error("JwtUtils.validateToken: 만료된 토큰입니다.");
            throw new JwtUtilsException(EXPIRED_ACCESS_TOKEN);
        } catch (JwtException e) {
            log.error("JwtUtils.validateToken: jwt token 검증시 오류가 발생했습니다.");
            throw new JwtUtilsException(JWT_UTIL_ERR);
        }
    }
}
