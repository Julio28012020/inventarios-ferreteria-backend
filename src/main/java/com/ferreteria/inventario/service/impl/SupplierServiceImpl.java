package com.ferreteria.inventario.service.impl;

import java.util.List;
import java.util.ArrayList;

import com.ferreteria.inventario.repository.SupplierRepository;
import com.ferreteria.inventario.entity.Supplier;

import org.springframework.stereotype.Service;

import com.ferreteria.inventario.dto.request.SupplierRequestDto;
import com.ferreteria.inventario.mapper.SupplierMapper;
import com.ferreteria.inventario.service.SupplierService;

import org.springframework.transaction.annotation.Transactional;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    private final SupplierMapper supplierMapper;

    public SupplierServiceImpl(
            SupplierRepository supplierRepository,
            SupplierMapper supplierMapper) {
        this.supplierRepository = supplierRepository;
        this.supplierMapper = supplierMapper;
    }

    @Override
    @Transactional
    public Supplier saveFromDto(SupplierRequestDto dto) {
        // 1. Convertimos el DTO a la Entidad
        // El Mapper ya debe realizar la lógica de enlazar los contactos al supplier
        Supplier supplier = supplierMapper.toEntity(dto);
        
        // 2. Guardamos. Gracias a CascadeType.ALL, esto persistirá 
        // tanto el Supplier como todos sus SupplierContacts automáticamente.
        return supplierRepository.save(supplier);
    }

    @Override
    @Transactional
    public Supplier updateFromDto(Long id, SupplierRequestDto dto) {
        Supplier existingSupplier = findById(id);

        existingSupplier.setNit(dto.getNit());
        existingSupplier.setBusinessName(dto.getBusinessName());
        existingSupplier.setAddress(dto.getAddress());
        existingSupplier.setCity(dto.getCity());

        if (existingSupplier.getContacts() == null) {
            existingSupplier.setContacts(new ArrayList<>());
        } else {
            existingSupplier.getContacts().clear();
        }
        Supplier updatedSupplier = supplierMapper.toEntity(dto);
        if (updatedSupplier.getContacts() != null) {
            existingSupplier.getContacts().addAll(updatedSupplier.getContacts());
            existingSupplier.getContacts().forEach(contact -> contact.setSupplier(existingSupplier));
        }

        return supplierRepository.save(existingSupplier);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Supplier> findAll() {
        return supplierRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Supplier findById(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!supplierRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar, el proveedor no existe.");
        }
        supplierRepository.deleteById(id);
    }

}
