package com.prog.TD.service;

import com.prog.TD.modele.UserEntity;
import com.prog.TD.modele.UserSession;
import com.prog.TD.repository.UserRepository;
import com.prog.TD.repository.UserSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthentificationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSessionRepository userSessionRepository;

    @Autowired
    public AuthentificationService(UserRepository userRepository, UserSessionRepository userSessionRepository) {
        this.userRepository = userRepository;
        this.userSessionRepository = userSessionRepository;
    }

    public boolean authenticate(String userName, String password, HttpServletResponse response) {
        if (userName == null || password == null) {
            return false; // Si le nom d'utilisateur ou le mot de passe est null, l'authentification échoue.
        }

        UserEntity userEntity = userRepository.findByUserName(userName);
        if (userEntity != null && userEntity.getPassword().equals(password)) {
            UserSession session = new UserSession();
            session.setUserEntity(userEntity);
            session.setSessionId(UUID.randomUUID().toString());
            session.setExpirationDate(LocalDateTime.now().plusHours(12));
            UserSession userSession = userSessionRepository.save(session);
            Cookie cookie = new Cookie("userSessionId", userSession.getSessionId());
            cookie.setPath("/");
            cookie.setMaxAge(12 * 60 * 60);
            response.addCookie(cookie);
            return true;
        }
        return false;
    }

    public String extractSessionIdFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userSessionId")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public boolean isUserLoggedIn(String sessionId) {
        if (sessionId != null) {
            UserSession session = userSessionRepository.findBySessionId(sessionId);
            return session != null && session.getExpirationDate().isAfter(LocalDateTime.now());
        }
        return false;
    }

    // Correction: Changed the return type to void
    public void deleteCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

}
