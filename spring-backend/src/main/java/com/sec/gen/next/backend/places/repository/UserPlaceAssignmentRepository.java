package com.sec.gen.next.backend.places.repository;

import com.sec.gen.next.backend.api.internal.UserPlaceAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPlaceAssignmentRepository extends JpaRepository<UserPlaceAssignment, Integer> {
}
