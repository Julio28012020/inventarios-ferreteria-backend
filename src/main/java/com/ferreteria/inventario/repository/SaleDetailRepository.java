package com.ferreteria.inventario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ferreteria.inventario.entity.SaleDetail;

public interface SaleDetailRepository
        extends JpaRepository<SaleDetail, Long> {

    List<SaleDetail> findBySaleId(Long saleId);
}