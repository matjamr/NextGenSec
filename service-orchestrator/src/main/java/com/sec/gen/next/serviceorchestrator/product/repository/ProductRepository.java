package com.sec.gen.next.serviceorchestrator.product.repository;

import com.next.gen.api.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
}
