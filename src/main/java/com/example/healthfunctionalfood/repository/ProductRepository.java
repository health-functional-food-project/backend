package com.example.healthfunctionalfood.repository;

import com.example.healthfunctionalfood.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllById(Long id);
}
