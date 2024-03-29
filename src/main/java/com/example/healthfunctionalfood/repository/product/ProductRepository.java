package com.example.healthfunctionalfood.repository.product;

import com.example.healthfunctionalfood.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select p from Product p where p.productName like %:search% or p.companyName like %:search% or p.funcRawMaterials like %:search% order by p.expertReviewAvg desc ")

    List<Product> findByProductNames(String search);

    List<Product> findByProductName(String search);

    @Query("select count(p) from Product p where p.productName like %:search% or p.etc like %:search% or p.companyName like %:search% or p.funcRawMaterials like %:search%")
    Integer findByProductCount(String search);

    List<Product> findAllById(Long id);

}
