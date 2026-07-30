package com.ferreteria.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ferreteria.inventario.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByCode(String code);

}
