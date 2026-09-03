package com.ferreteria.inventario.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.ferreteria.inventario.enums.PaymentMethod;
import com.ferreteria.inventario.enums.SaleStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleResponse {

    private Long id;

    private SaleStatus status;

    private PaymentMethod paymentMethod;

    private BigDecimal total;

    private LocalDateTime createdAt;

    private Long customerId;

    private String customerName;

    private String customerDocumentNumber;

    private List<SaleDetailResponse> details;
}