package com.ferreteria.inventario.service;

import com.ferreteria.inventario.entity.Category;
import com.ferreteria.inventario.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id: " + id));
    }

    @Override
    public Category save(Category category) {

        if (categoryRepository.existsByName(category.getName())) {
            throw new RuntimeException("Ya existe una categoría con ese nombre.");
        }

        return categoryRepository.save(category);
    }

    @Override
    public Category update(Long id, Category category) {

        Category existingCategory = findById(id);

        existingCategory.setName(category.getName());
        existingCategory.setDescription(category.getDescription());

        return categoryRepository.save(existingCategory);
    }

    @Override
    public void delete(Long id) {

        Category category = findById(id);

        categoryRepository.delete(category);
    }
}
