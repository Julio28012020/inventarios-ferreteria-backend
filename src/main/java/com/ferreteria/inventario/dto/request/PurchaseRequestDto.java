package com.ferreteria.inventario.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseRequestDto {

    private Long supplierId;
    private Long productId;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
    private LocalDate purchaseDate;
}
