package com.sec.gen.next.backend.internal.product.repository;

import com.sec.gen.next.backend.internal.api.internal.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
