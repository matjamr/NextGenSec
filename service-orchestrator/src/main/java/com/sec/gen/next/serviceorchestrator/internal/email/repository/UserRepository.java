package com.sec.gen.next.serviceorchestrator.internal.email.repository;

import com.next.gen.api.User;
import com.next.gen.sec.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String sendToName);

    List<User> findAllByRole(Role role);
}
