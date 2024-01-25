package com.sec.gen.next.backend.internal.image.repository;

import com.sec.gen.next.backend.internal.api.internal.Image;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
public interface ImageRepository extends JpaRepository<Image, Integer> {
}
