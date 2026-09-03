package com.ferreteria.inventario.mapper;

import org.springframework.stereotype.Component;

import com.ferreteria.inventario.dto.request.CustomerRequestDto;
import com.ferreteria.inventario.entity.Customer;

@Component
public class CustomerMapper {

    public Customer toEntity(CustomerRequestDto dto) {

        if (dto == null) {
            return null;
        }

        Customer customer = new Customer();

        customer.setDocumentType(dto.getDocumentType());
        customer.setDocumentNumber(dto.getDocumentNumber());
        customer.setFullName(dto.getFullName());
        customer.setPhone(dto.getPhone());
        customer.setEmail(dto.getEmail());
        customer.setAddress(dto.getAddress());
        customer.setCity(dto.getCity());

        return customer;
    }
}