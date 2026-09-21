package com.threatintel.security;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        System.out.println("====================================");
        System.out.println("Logged User : " + authentication.getName());

        for (GrantedAuthority authority : authentication.getAuthorities()) {

            System.out.println("Authority : " + authority.getAuthority());

            if ("ROLE_ADMIN".equals(authority.getAuthority())) {

                System.out.println("Redirecting to ADMIN Dashboard");
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                return;
            }

            if ("ROLE_USER".equals(authority.getAuthority())) {

                System.out.println("Redirecting to USER Dashboard");
                response.sendRedirect(request.getContextPath() + "/user/dashboard");
                return;
            }
        }

        response.sendRedirect(request.getContextPath() + "/");
    }
}