package com.prog.TD.repository;

import com.prog.TD.modele.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    @Query(value = "select * from user_entity where user_name = ':userName'",nativeQuery = true)
    UserEntity findByUserName(String userName);
}
