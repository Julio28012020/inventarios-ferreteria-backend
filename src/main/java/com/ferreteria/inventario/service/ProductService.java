package com.ferreteria.inventario.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ferreteria.inventario.dto.request.ProductRequest;
import com.ferreteria.inventario.entity.Brand;
import com.ferreteria.inventario.entity.Product;
import com.ferreteria.inventario.enums.ProductStatus;
import com.ferreteria.inventario.repository.BrandRepository;
import com.ferreteria.inventario.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;

    public ProductService(ProductRepository productRepository, BrandRepository brandRepository) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
    }

    public List<Product> findAllProducts() {
        return productRepository.findByStatusNot(ProductStatus.DISCONTINUED);
    }

    public Product findById(Long id) {
        return productRepository
                .findByIdAndStatusNot(id, ProductStatus.DISCONTINUED)
                .orElse(null);
    }

    public Product saveProduct(ProductRequest request) {

        if (request.getCode() == null || request.getCode().isBlank()) {
            throw new IllegalArgumentException("El código del producto es obligatorio.");
        }

        if (productRepository.existsByCode(request.getCode())) {
            throw new IllegalArgumentException("Ya existe un producto con ese código.");
        }

        if (request.getName() == null || request.getName().isBlank()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio.");
        }

        if (request.getPurchasePrice() == null) {
            throw new IllegalArgumentException("El precio de compra es obligatorio.");
        }

        if (request.getSalePrice() == null) {
            throw new IllegalArgumentException("El precio de venta es obligatorio.");
        }

        if (request.getBrandId() == null) {
            throw new IllegalArgumentException("La marca es obligatoria.");
        }

        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new IllegalArgumentException("La marca especificada no existe."));

        Product product = new Product();

        product.setCode(request.getCode());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setBrand(brand);
        product.setImageUrl(request.getImageUrl());
        product.setPurchasePrice(request.getPurchasePrice());
        product.setSalePrice(request.getSalePrice());
        product.setCurrentStock(request.getCurrentStock());
        product.setMinimumStock(request.getMinimumStock());

        if (request.getStatus() == ProductStatus.ACTIVE ||
                request.getStatus() == ProductStatus.INACTIVE) {

            product.setStatus(request.getStatus());

        } else {
            product.setStatus(ProductStatus.ACTIVE);
        }

        product.setUnitOfMeasure(request.getUnitOfMeasure());

        return productRepository.save(product);
    }

    public Product updateProduct(Long id, ProductRequest request) {

        Product existingProduct = findById(id);

        if (existingProduct == null) {
            throw new IllegalArgumentException("El producto no existe.");
        }

        existingProduct.setCode(request.getCode());
        existingProduct.setName(request.getName());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setImageUrl(request.getImageUrl());
        existingProduct.setSalePrice(request.getSalePrice());
        existingProduct.setMinimumStock(request.getMinimumStock());

        if (request.getStatus() == ProductStatus.ACTIVE ||
                request.getStatus() == ProductStatus.INACTIVE) {

            existingProduct.setStatus(request.getStatus());
        }

        if (request.getBrandId() != null) {
            Brand brand = brandRepository.findById(request.getBrandId())
                    .orElseThrow(() -> new IllegalArgumentException("La marca especificada no existe."));
            existingProduct.setBrand(brand);
        }

        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "El producto no existe."));

        product.setStatus(ProductStatus.DISCONTINUED);

        productRepository.save(product);
    }
}