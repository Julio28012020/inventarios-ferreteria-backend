package com.ferreteria.inventario.dto.request;

import java.math.BigDecimal;
import com.ferreteria.inventario.enums.ProductStatus;
import com.ferreteria.inventario.enums.UnitOfMeasure;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
    private String code;
    private String name;
    private String description;
    private Long brandId;
    private String imageUrl;
    private BigDecimal purchasePrice;
    private BigDecimal salePrice;
    private BigDecimal currentStock;
    private BigDecimal minimumStock;
    private ProductStatus status;
    private UnitOfMeasure unitOfMeasure;
}