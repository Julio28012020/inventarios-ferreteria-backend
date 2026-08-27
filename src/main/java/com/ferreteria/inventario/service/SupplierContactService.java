package com.ferreteria.inventario.service;

import java.util.List;

import com.ferreteria.inventario.dto.request.SupplierContactRequestDto;
import com.ferreteria.inventario.entity.SupplierContact;

public interface SupplierContactService {

    List<SupplierContact> findBySupplierId(Long supplierId);

    SupplierContact findById(Long id);

    SupplierContact save(Long supplierId, SupplierContactRequestDto dto);

    SupplierContact update(Long id, SupplierContactRequestDto dto);

    void delete(Long id);
}
