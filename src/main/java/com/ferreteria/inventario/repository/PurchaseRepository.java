package com.ferreteria.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ferreteria.inventario.entity.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
