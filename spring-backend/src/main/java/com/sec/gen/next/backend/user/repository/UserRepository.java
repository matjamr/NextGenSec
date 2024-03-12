package com.sec.gen.next.backend.user.repository;

import com.sec.gen.next.backend.api.internal.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {

    @Transactional
    Optional<User> findUserByEmail(String email);
}
