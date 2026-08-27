package com.ferreteria.inventario.dto.request;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SupplierContactRequestDto {

    private String name;
    private String department;
    private String email;
    private String phone;

}
