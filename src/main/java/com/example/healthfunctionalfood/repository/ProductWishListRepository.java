package com.example.healthfunctionalfood.repository;

import com.example.healthfunctionalfood.domain.product.ProductWishList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductWishListRepository extends JpaRepository<ProductWishList, Long> {
    Optional<ProductWishList> findByProductIdAndUserId(Long productId, Long id);
}
