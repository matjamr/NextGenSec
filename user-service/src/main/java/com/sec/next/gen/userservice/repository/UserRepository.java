package com.sec.next.gen.userservice.repository;

import com.sec.next.gen.userservice.models.AuthorizedUser;
import com.sec.next.gen.userservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select p FROM User p where p.email = ?1")
    Optional<User> findByEmail(String userEmail);
}
