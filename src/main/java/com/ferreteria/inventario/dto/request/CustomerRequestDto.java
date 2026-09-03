package com.ferreteria.inventario.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequestDto {

    private String documentType;

    private String documentNumber;

    private String fullName;

    private String phone;

    private String email;

    private String address;

    private String city;
}