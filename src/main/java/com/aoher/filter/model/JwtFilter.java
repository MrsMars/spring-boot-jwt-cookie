package com.aoher.filter.model;

import com.aoher.filter.util.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.aoher.filter.util.Constants.ATTRIBUTE_USERNAME;
import static com.aoher.filter.util.Constants.JWT_TOKEN_COOKIE_NAME;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final String SIGNING_KEY = "signingKey";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String username = JwtUtil.parseToken(request, JWT_TOKEN_COOKIE_NAME, SIGNING_KEY);
        if (username == null && getFilterConfig() != null) {
            String authService = this.getFilterConfig().getInitParameter("services.auth");
            response.sendRedirect(String.format("%s?redirect=%s", authService, request.getRequestURI()));
        } else {
            request.setAttribute(ATTRIBUTE_USERNAME, username);
            filterChain.doFilter(request, response);
        }
    }
}
