package com.sec.gen.next.backend.internal.places.repository;

import com.sec.gen.next.backend.internal.api.internal.Places;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlacesRepository extends JpaRepository<Places, Integer> {

    boolean existsByPlaceNameOrEmailPlace(String placeName, String email);

    Optional<Places> findByPlaceName(String placeName);
}
