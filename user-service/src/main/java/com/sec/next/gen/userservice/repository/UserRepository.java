package com.sec.next.gen.userservice.repository;


import com.next.gen.api.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String userEmail);
    User save(@NonNull User user);
    boolean existsUserByEmail(String userEmail);
}
