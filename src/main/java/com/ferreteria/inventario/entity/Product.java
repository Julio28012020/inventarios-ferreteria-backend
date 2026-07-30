package com.ferreteria.inventario.entity;

import java.math.BigDecimal;

import com.ferreteria.inventario.enums.ProductStatus;

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

    @Column (length = 500)
    private String imageUrl;
    
    @Column (nullable = false, precision = 10, scale = 2)
    private BigDecimal purchasePrice;

    @Column (nullable = false, precision = 10, scale = 2)
    private BigDecimal salePrice;

    @Column (nullable = false, precision = 10, scale = 2)
    private BigDecimal currentStock;

    @Column (nullable = false, precision = 10, scale = 2)
    private BigDecimal minimumStock;

    @Enumerated(EnumType.STRING)
    @Column (nullable = false)
    private ProductStatus status = ProductStatus.ACTIVE;

}
