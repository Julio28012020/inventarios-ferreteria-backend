package com.ferreteria.inventario.entity;

import java.math.BigDecimal;

import com.ferreteria.inventario.enums.ProductStatus;
import com.ferreteria.inventario.enums.UnitOfMeasure;

import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;


@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Product extends BaseEntity {

    @Column (nullable = false, unique = true, length = 50)
    private String code;

    @Column (nullable = false, length = 100)
    private String name;

    @Column (columnDefinition = "TEXT")
    private String description;

    @Column (name = "brand_id", nullable = false)
    private int brandId;

    @Column (name = "image_url", length = 500)
    private String imageUrl;
    
    @Column (name = "purchase_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal purchasePrice;

    @Column (name = "sale_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal salePrice;

    @Column (name = "current_stock", nullable = false, precision = 10, scale = 2)
    private BigDecimal currentStock;

    @Column (name = "minimum_stock", nullable = false, precision = 10, scale = 2)
    private BigDecimal minimumStock;

    @Enumerated(EnumType.STRING)
    @Column (nullable = false)
    private ProductStatus status = ProductStatus.ACTIVE;

    @Enumerated(EnumType.STRING)
    @Column (name = "unit_of_measure", nullable = false)
    private UnitOfMeasure unitOfMeasure = UnitOfMeasure.PIECE;

}
