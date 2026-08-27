package com.ferreteria.inventario.dto.request;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleDetailRequest {

    private Long productId;

    private BigDecimal quantity;

}