package com.aoher.filter.util;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static com.aoher.filter.util.Constants.ATTRIBUTE_USERNAME;

public class JwtUtil {

    private static final String REDIS_SET_ACTIVE_SUBJECTS = "active-subjects";

    public static String generateToken(String signingKey, String subject) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        JwtBuilder builder = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, signingKey);

        String token = builder.compact();
        RedisUtil.INSTANCE.sadd(REDIS_SET_ACTIVE_SUBJECTS, subject);
        return token;
    }

    public static String parseToken(HttpServletRequest request,
                                    String jwtTokenCookieName,
                                    String signingKey) {
        String token = CookieUtil.getValue(request, jwtTokenCookieName);
        if (token == null) {
            return null;
        }

        String subject = Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(token)
                .getBody().getSubject();
        if (!RedisUtil.INSTANCE.sismember(REDIS_SET_ACTIVE_SUBJECTS, subject)) {
            return null;
        }

        return subject;
    }

    public static void invalidateRelatedTokens(HttpServletRequest request) {
        RedisUtil.INSTANCE.srem(
                REDIS_SET_ACTIVE_SUBJECTS,
                (String) request.getAttribute(ATTRIBUTE_USERNAME));
    }
}
