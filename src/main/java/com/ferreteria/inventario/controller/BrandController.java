package com.ferreteria.inventario.controller;

import com.ferreteria.inventario.entity.Brand;
import com.ferreteria.inventario.service.BrandService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brands")
@CrossOrigin
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public ResponseEntity<List<Brand>> findAll() {
        return ResponseEntity.ok(brandService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Brand> findById(@PathVariable Long id) {
        return ResponseEntity.ok(brandService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Brand> save(@RequestBody Brand brand) {
        Brand newBrand = brandService.save(brand);
        return new ResponseEntity<>(newBrand, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Brand> update(
            @PathVariable Long id,
            @RequestBody Brand brand) {

        Brand updatedBrand = brandService.update(id, brand);
        return ResponseEntity.ok(updatedBrand);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        brandService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
