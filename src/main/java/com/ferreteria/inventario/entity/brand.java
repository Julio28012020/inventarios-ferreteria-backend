package com.ferreteria.inventario.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "brans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class brand {

    @Column(name = "name", nullable = false, length = 100)
    private String name;

}
