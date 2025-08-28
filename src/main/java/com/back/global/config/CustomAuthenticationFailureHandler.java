package com.back.global.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = "";
        if (exception instanceof BadCredentialsException) {
            errorMessage = "아이디 또는 비밀번호가 올바르지 않습니다.";
        } else if (exception instanceof InternalAuthenticationServiceException) {
            errorMessage = "존재하지 않는 사용자입니다.";
        } else {
            errorMessage = "로그인에 실패하였습니다. 다시 시도해 주세요.";
        }

        request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);

        response.sendRedirect("/member/login");
    }
}
