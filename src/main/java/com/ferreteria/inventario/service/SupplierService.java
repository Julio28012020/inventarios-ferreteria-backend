package com.ferreteria.inventario.service;

import com.ferreteria.inventario.dto.request.SupplierRequestDto;
import com.ferreteria.inventario.entity.Supplier;

import java.util.List;

public interface SupplierService {
    
    // Método para crear/guardar usando el DTO
    Supplier saveFromDto(SupplierRequestDto dto);

        // Actualizar un proveedor usando el DTO
        Supplier updateFromDto(Long id, SupplierRequestDto dto);
    
    // Obtener todos los proveedores
    List<Supplier> findAll();
    
    // Buscar un proveedor por su ID
    Supplier findById(Long id);
    
    // Eliminar un proveedor por su ID
    void delete(Long id);
}
