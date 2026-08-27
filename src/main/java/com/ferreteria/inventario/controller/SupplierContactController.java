package com.ferreteria.inventario.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ferreteria.inventario.dto.request.SupplierContactRequestDto;
import com.ferreteria.inventario.entity.SupplierContact;
import com.ferreteria.inventario.service.SupplierContactService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class SupplierContactController {

    private final SupplierContactService contactService;

    public SupplierContactController(SupplierContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/suppliers/{supplierId}/contacts")
    public ResponseEntity<List<SupplierContact>> findBySupplier(
            @PathVariable Long supplierId) {
        return ResponseEntity.ok(contactService.findBySupplierId(supplierId));
    }

    @GetMapping("/supplier-contacts/{id}")
    public ResponseEntity<SupplierContact> findById(@PathVariable Long id) {
        return ResponseEntity.ok(contactService.findById(id));
    }

    @PostMapping("/suppliers/{supplierId}/contacts")
    public ResponseEntity<SupplierContact> save(
            @PathVariable Long supplierId,
            @RequestBody SupplierContactRequestDto dto) {
        return new ResponseEntity<>(contactService.save(supplierId, dto), HttpStatus.CREATED);
    }

    @PutMapping("/supplier-contacts/{id}")
    public ResponseEntity<SupplierContact> update(
            @PathVariable Long id,
            @RequestBody SupplierContactRequestDto dto) {
        return ResponseEntity.ok(contactService.update(id, dto));
    }

    @DeleteMapping("/supplier-contacts/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        contactService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
