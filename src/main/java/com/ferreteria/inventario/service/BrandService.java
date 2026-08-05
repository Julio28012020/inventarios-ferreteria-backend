package com.ferreteria.inventario.service;

import com.ferreteria.inventario.entity.Brand;

import java.util.List;

public interface BrandService {

    List<Brand> findAll();

    Brand findById(Long id);

    Brand save(Brand brand);

    Brand update(Long id, Brand brand);

    void delete(Long id);
}
