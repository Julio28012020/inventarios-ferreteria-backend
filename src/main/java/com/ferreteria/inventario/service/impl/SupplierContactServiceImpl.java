package com.ferreteria.inventario.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ferreteria.inventario.dto.request.SupplierContactRequestDto;
import com.ferreteria.inventario.entity.Supplier;
import com.ferreteria.inventario.entity.SupplierContact;
import com.ferreteria.inventario.repository.SupplierContactRepository;
import com.ferreteria.inventario.repository.SupplierRepository;
import com.ferreteria.inventario.service.SupplierContactService;

@Service
public class SupplierContactServiceImpl implements SupplierContactService {

    private final SupplierContactRepository contactRepository;
    private final SupplierRepository supplierRepository;

    public SupplierContactServiceImpl(
            SupplierContactRepository contactRepository,
            SupplierRepository supplierRepository) {
        this.contactRepository = contactRepository;
        this.supplierRepository = supplierRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SupplierContact> findBySupplierId(Long supplierId) {
        if (!supplierRepository.existsById(supplierId)) {
            throw new RuntimeException("El proveedor no existe.");
        }
        return contactRepository.findBySupplierId(supplierId);
    }

    @Override
    @Transactional(readOnly = true)
    public SupplierContact findById(Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contacto no encontrado con id: " + id));
    }

    @Override
    @Transactional
    public SupplierContact save(Long supplierId, SupplierContactRequestDto dto) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("El proveedor no existe."));

        SupplierContact contact = new SupplierContact();
        copyData(dto, contact);
        contact.setSupplier(supplier);
        return contactRepository.save(contact);
    }

    @Override
    @Transactional
    public SupplierContact update(Long id, SupplierContactRequestDto dto) {
        SupplierContact contact = findById(id);
        copyData(dto, contact);
        return contactRepository.save(contact);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        SupplierContact contact = findById(id);
        contactRepository.delete(contact);
    }

    private void copyData(SupplierContactRequestDto dto, SupplierContact contact) {
        contact.setName(dto.getName());
        contact.setDepartment(dto.getDepartment());
        contact.setEmail(dto.getEmail());
        contact.setPhone(dto.getPhone());
    }
}
