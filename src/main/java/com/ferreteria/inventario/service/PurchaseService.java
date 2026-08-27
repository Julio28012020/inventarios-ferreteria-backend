package com.ferreteria.inventario.service;

import java.util.List;

import com.ferreteria.inventario.dto.request.PurchaseRequestDto;
import com.ferreteria.inventario.entity.Purchase;

public interface PurchaseService {

    List<Purchase> findAll();

    Purchase findById(Long id);

    Purchase save(PurchaseRequestDto dto);

    Purchase update(Long id, PurchaseRequestDto dto);

    void delete(Long id);
}
