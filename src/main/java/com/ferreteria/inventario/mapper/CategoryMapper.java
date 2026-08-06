package com.ferreteria.inventario.mapper;

import com.ferreteria.inventario.dto.request.CategoryRequestDTO;
import com.ferreteria.inventario.dto.response.CategoryResponseDTO;
import com.ferreteria.inventario.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    // Convierte lo que llega del Frontend a una Entidad para la Base de Datos
    public Category toEntity(CategoryRequestDTO dto) {
        Category category = new Category();
        category.setName(dto.name());
        category.setDescription(dto.description());
        return category;
    }

    // Convierte la Entidad de la Base de Datos a un DTO para el Frontend
    public CategoryResponseDTO toResponseDTO(Category entity) {
        return new CategoryResponseDTO(
            entity.getId(), // Este método viene de tu BaseEntity
            entity.getName(),
            entity.getDescription()
        );
    }
}