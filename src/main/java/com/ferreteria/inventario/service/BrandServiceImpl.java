package com.ferreteria.inventario.service;

import com.ferreteria.inventario.entity.Brand;
import com.ferreteria.inventario.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Override
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    @Override
    public Brand findById(Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Marca no encontrada con id: " + id));
    }

    @Override
    public Brand save(Brand brand) {

        if (brandRepository.existsByName(brand.getName())) {
            throw new RuntimeException("Ya existe una marca con ese nombre.");
        }

        return brandRepository.save(brand);
    }

    @Override
    public Brand update(Long id, Brand brand) {

        Brand existingBrand = findById(id);

        existingBrand.setName(brand.getName());

        return brandRepository.save(existingBrand);
    }

    @Override
    public void delete(Long id) {

        Brand brand = findById(id);

        brandRepository.delete(brand);
    }
}
