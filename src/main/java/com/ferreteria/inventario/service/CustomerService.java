package com.ferreteria.inventario.service;

import com.ferreteria.inventario.dto.request.CustomerRequestDto;
import com.ferreteria.inventario.entity.Customer;

import java.util.List;

public interface CustomerService {

    // Crear un cliente usando el DTO
    Customer saveFromDto(CustomerRequestDto dto);

    // Actualizar un cliente usando el DTO
    Customer updateFromDto(Long id, CustomerRequestDto dto);

    // Obtener todos los clientes
    List<Customer> findAll();

    // Buscar un cliente por su ID
    Customer findById(Long id);

    // Eliminar un cliente por su ID
    void delete(Long id);
}