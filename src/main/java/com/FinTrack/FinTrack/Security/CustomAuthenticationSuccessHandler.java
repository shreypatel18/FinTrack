package com.FinTrack.FinTrack.Security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

//redirects to dashboard if authentication succeeds

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();
        HttpSession session = request.getSession();
        session.setAttribute("username", username);

        // Forward to the dashboard endpoint
        response.sendRedirect(request.getContextPath() + "/dashBoard");
    }
}
