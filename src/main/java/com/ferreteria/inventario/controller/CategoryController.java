package com.ferreteria.inventario.controller;

import com.ferreteria.inventario.entity.Category;
import com.ferreteria.inventario.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("categories")
@CrossOrigin()
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    // Obtener todas las categorías
    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    // Obtener una categoría por ID
    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    // Crear una nueva categoría
    @PostMapping
    public ResponseEntity<Category> save(@RequestBody Category category) {
        Category newCategory = categoryService.save(category);
        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
    }

    // Actualizar una categoría
    @PutMapping("/{id}")
    public ResponseEntity<Category> update(
            @PathVariable Long id,
            @RequestBody Category category) {

        Category updatedCategory = categoryService.update(id, category);
        return ResponseEntity.ok(updatedCategory);
    }

    // Eliminar una categoría
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
