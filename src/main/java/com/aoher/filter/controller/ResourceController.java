package com.aoher.filter.controller;

import com.aoher.filter.util.CookieUtil;
import com.aoher.filter.util.JwtUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.aoher.filter.util.Constants.JWT_TOKEN_COOKIE_NAME;

@Controller
public class ResourceController {

    @GetMapping("/")
    public String home() {
        return "redirect:/protected-resource";
    }

    @GetMapping("/protected-resource")
    public String protectedResource() {
        return "protected-resource";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        JwtUtil.invalidateRelatedTokens(request);
        CookieUtil.clear(response, JWT_TOKEN_COOKIE_NAME);
        return "redirect:/";
    }
}
