package com.ferreteria.inventario.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "suppliers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Supplier extends BaseEntity {
    
    @Column(nullable = false, unique = true)
    private String nit;
    
    @Column(name = "business_name", nullable = false)
    private String businessName;
    
    private String address;
    
    private String city;
    
    // Relación One-to-Many: Un proveedor tiene muchos contactos
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SupplierContact> contacts;
    

}
