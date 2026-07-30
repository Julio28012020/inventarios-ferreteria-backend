package com.ferreteria.inventario.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ferreteria.inventario.entity.Product;
import com.ferreteria.inventario.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product saveProduct(Product product) {

        // Validar que el código sea obligatorio
        if (product.getCode() == null || product.getCode().isBlank()) {
            throw new IllegalArgumentException("El código del producto es obligatorio.");
        }

        // Validar que el código no esté repetido
        if (productRepository.existsByCode(product.getCode())) {
            throw new IllegalArgumentException("Ya existe un producto con ese código.");
        }

        // Validar que el nombre sea obligatorio
        if (product.getName() == null || product.getName().isBlank()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio.");
        }

        // Validar que el precio de compra sea obligatorio
        if (product.getPurchasePrice() == null) {
            throw new IllegalArgumentException("El precio de compra es obligatorio.");
        }

        // Validar que el precio de venta sea obligatorio
        if (product.getSalePrice() == null) {
            throw new IllegalArgumentException("El precio de venta es obligatorio.");
        }

        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) {

        Product existingProduct = findById(id);

        if (existingProduct == null) {
            throw new IllegalArgumentException("El producto no existe.");
        }

        existingProduct.setCode(product.getCode());
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setImageUrl(product.getImageUrl());
        existingProduct.setPurchasePrice(product.getPurchasePrice());
        existingProduct.setSalePrice(product.getSalePrice());
        existingProduct.setCurrentStock(product.getCurrentStock());
        existingProduct.setMinimumStock(product.getMinimumStock());
        existingProduct.setStatus(product.getStatus());

        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}