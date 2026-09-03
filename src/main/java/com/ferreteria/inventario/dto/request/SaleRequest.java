package com.ferreteria.inventario.dto.request;

import java.util.List;

import com.ferreteria.inventario.enums.PaymentMethod;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleRequest {

    private Long customerId;

    private PaymentMethod paymentMethod;

    private List<SaleDetailRequest> details;
}