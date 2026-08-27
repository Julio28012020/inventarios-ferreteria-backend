package com.ferreteria.inventario.repository;

import com.ferreteria.inventario.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}