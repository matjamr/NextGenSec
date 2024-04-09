package com.sec.gen.next.serviceorchestrator.image.repository;

import com.next.gen.api.Image;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
public interface ImageRepository extends JpaRepository<Image, String> {
}
