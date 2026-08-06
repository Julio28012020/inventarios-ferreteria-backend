package com.ferreteria.inventario.service.impl;

import com.ferreteria.inventario.dto.request.CategoryRequestDTO;
import com.ferreteria.inventario.dto.response.CategoryResponseDTO;
import com.ferreteria.inventario.entity.Category;
import com.ferreteria.inventario.mapper.CategoryMapper;
import com.ferreteria.inventario.repository.CategoryRepository;
import com.ferreteria.inventario.service.CategoryService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    // Inyección de dependencias por constructor (Buena práctica)
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<CategoryResponseDTO> getAllCategories() {
        // Obtenemos las entidades, usamos Stream para mapear cada una a DTO y devolvemos una lista
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponseDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con el ID: " + id));
        return categoryMapper.toResponseDTO(category);
    }

    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO requestDTO) {
        // 1. Convertir DTO a Entidad
        Category category = categoryMapper.toEntity(requestDTO);
        // 2. Guardar en Base de Datos
        Category savedCategory = categoryRepository.save(category);
        // 3. Convertir Entidad guardada a DTO y retornar
        return categoryMapper.toResponseDTO(savedCategory);
    }

    @Override
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO requestDTO) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con el ID: " + id));

        // Actualizamos los campos
        existingCategory.setName(requestDTO.name());
        existingCategory.setDescription(requestDTO.description());

        Category updatedCategory = categoryRepository.save(existingCategory);
        return categoryMapper.toResponseDTO(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Categoría no encontrada con el ID: " + id);
        }
        categoryRepository.deleteById(id);
    }
}