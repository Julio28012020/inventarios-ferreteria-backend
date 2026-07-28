package com.ferreteria.inventario.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
public class category {

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "name", nullable = false)
    private String description; 

}
