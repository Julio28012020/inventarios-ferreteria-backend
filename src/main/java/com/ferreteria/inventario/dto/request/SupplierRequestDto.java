package com.ferreteria.inventario.dto.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierRequestDto {

    private String nit;
    private String businessName;
    private String address;
    private String city;
    private List<SupplierContactRequestDto> contacts; // Lista de contactos al crear
}
