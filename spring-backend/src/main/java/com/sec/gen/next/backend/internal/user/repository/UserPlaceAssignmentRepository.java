package com.sec.gen.next.backend.internal.user.repository;

import com.sec.gen.next.backend.internal.api.internal.UserPlaceAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPlaceAssignmentRepository extends JpaRepository<UserPlaceAssignment, Integer> {
}
