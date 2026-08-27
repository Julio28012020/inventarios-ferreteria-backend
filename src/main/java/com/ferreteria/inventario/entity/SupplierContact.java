package com.ferreteria.inventario.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "supplier_contacts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupplierContact extends BaseEntity {

    private String name;
    private String department; //area del empledo
    private String email;
    private String phone;

    // Relación Many-to-One: Muchos contactos pertenecen a un proveedor
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    @JsonIgnore // Evita bucles infinitos en la serialización JSON
    private Supplier supplier;
}
