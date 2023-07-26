package com.prog.TD.repository;

import com.prog.TD.modele.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
    UserSession findBySessionId(String sessionId);
}
