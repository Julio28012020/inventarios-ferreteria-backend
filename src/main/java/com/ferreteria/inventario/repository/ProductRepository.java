package com.ferreteria.inventario.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ferreteria.inventario.entity.Product;
import com.ferreteria.inventario.enums.ProductStatus;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByCode(String code);

    List<Product> findByStatusNot(ProductStatus status);

    Optional<Product> findByIdAndStatusNot(Long id, ProductStatus status);

}