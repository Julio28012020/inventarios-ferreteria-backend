package com.ferreteria.inventario.mapper;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.ferreteria.inventario.dto.request.SupplierRequestDto;
import com.ferreteria.inventario.entity.Supplier;
import com.ferreteria.inventario.entity.SupplierContact;

@Component
public class SupplierMapper {

    public Supplier toEntity(SupplierRequestDto dto) {
        if (dto == null) {
            return null;
        }

        Supplier supplier = new Supplier();
        supplier.setNit(dto.getNit());
        supplier.setBusinessName(dto.getBusinessName());
        supplier.setAddress(dto.getAddress());
        supplier.setCity(dto.getCity());

        if (dto.getContacts() != null) {
            var contacts = dto.getContacts().stream().map(contactDto -> {
                SupplierContact contact = new SupplierContact();
                contact.setName(contactDto.getName());
                contact.setDepartment(contactDto.getDepartment());
                contact.setEmail(contactDto.getEmail());
                contact.setPhone(contactDto.getPhone());
                contact.setSupplier(supplier); // Enlazar la relación bidireccional
                return contact;
            }).collect(Collectors.toList());

            supplier.setContacts(contacts);
        }

        return supplier;
    }
}
