package com.ferreteria.inventario.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Setter;
import lombok.Getter;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.EntityListeners;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
public abstract class BaseEntity {

    @Id                                
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    private Long id;

    @CreatedDate                           
    @Column(name = "registration_date",
            nullable = false,              
            updatable = false)             
    private LocalDateTime createdAt;

    @LastModifiedDate                       
    @Column(name = "last_modified_date")
    private LocalDateTime updatedAt;


}
