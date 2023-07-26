package com.prog.TD.security;

import com.prog.TD.service.AuthentificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SessionFilter implements HandlerInterceptor {
    @Autowired
    private AuthentificationService authenticationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String sessionId = authenticationService.extractSessionIdFromRequest(request);
        String requestURI = request.getRequestURI();
        boolean userLoggedIn = authenticationService.isUserLoggedIn(sessionId);

        if (userLoggedIn) {
            if (requestURI.equals("/login")) {
                response.sendRedirect("/");
                return false;
            }
            return true;
        } else {
            if (requestURI.equals("/login")) {
                return true;
            } else {
                response.sendRedirect("/login");
                return false;
            }
        }
    }


}
