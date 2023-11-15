package com.sec.gen.next.backend.image.repository;

import com.sec.gen.next.backend.api.internal.Image;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
public interface ImageRepository extends JpaRepository<Image, Integer> {
}
