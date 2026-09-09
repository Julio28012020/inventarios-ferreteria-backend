package com.ferreteria.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ferreteria.inventario.entity.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    boolean existsByCustomerId(Long customerId);

}