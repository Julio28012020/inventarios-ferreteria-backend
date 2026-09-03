package com.ferreteria.inventario.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends BaseEntity {

    @Column(nullable = false)
    private String documentType;

    @Column(nullable = false, unique = true)
    private String documentNumber;

    @Column(nullable = false)
    private String fullName;

    private String phone;

    private String email;

    private String address;

    private String city;
}