package com.ssafy.saessak.user.repository;

import com.ssafy.saessak.user.domain.Teacher;
import com.ssafy.saessak.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {

    Optional<User> findByEmailAndNickname(String email, String name);
}
