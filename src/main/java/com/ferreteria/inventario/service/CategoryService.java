package com.ferreteria.inventario.service;

import com.ferreteria.inventario.dto.request.CategoryRequestDTO;
import com.ferreteria.inventario.dto.response.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryResponseDTO> getAllCategories();
    CategoryResponseDTO getCategoryById(Long id);
    CategoryResponseDTO createCategory(CategoryRequestDTO requestDTO);
    CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO requestDTO);
    void deleteCategory(Long id);
}
