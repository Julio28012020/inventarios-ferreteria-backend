package com.ferreteria.inventario.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ferreteria.inventario.dto.request.PurchaseRequestDto;
import com.ferreteria.inventario.entity.Product;
import com.ferreteria.inventario.entity.Purchase;
import com.ferreteria.inventario.entity.Supplier;
import com.ferreteria.inventario.repository.ProductRepository;
import com.ferreteria.inventario.repository.PurchaseRepository;
import com.ferreteria.inventario.repository.SupplierRepository;
import com.ferreteria.inventario.service.PurchaseService;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;

    public PurchaseServiceImpl(
            PurchaseRepository purchaseRepository,
            SupplierRepository supplierRepository,
            ProductRepository productRepository) {
        this.purchaseRepository = purchaseRepository;
        this.supplierRepository = supplierRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Purchase> findAll() {
        return purchaseRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Purchase findById(Long id) {
        return purchaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada con id: " + id));
    }

    @Override
    @Transactional
    public Purchase save(PurchaseRequestDto dto) {
        validate(dto);

        Supplier supplier = findSupplier(dto.getSupplierId());
        Product product = findProduct(dto.getProductId());

        Purchase purchase = new Purchase();
        purchase.setSupplier(supplier);
        purchase.setProduct(product);
        copyData(dto, purchase);
        
        // MODIFICADO: Ahora actualizamos tanto el stock como el precio de compra actual del producto
        addStockAndPricing(product, dto.getQuantity(), dto.getUnitPrice());

        return purchaseRepository.save(purchase);
    }

    @Override
    @Transactional
    public Purchase update(Long id, PurchaseRequestDto dto) {
        validate(dto);

        Purchase purchase = findById(id);
        Product oldProduct = purchase.getProduct();
        Product newProduct = findProduct(dto.getProductId());

        removeStock(oldProduct, purchase.getQuantity());
        
        // MODIFICADO: Actualizamos stock y precio en el producto al modificar la compra
        addStockAndPricing(newProduct, dto.getQuantity(), dto.getUnitPrice());

        purchase.setSupplier(findSupplier(dto.getSupplierId()));
        purchase.setProduct(newProduct);
        copyData(dto, purchase);

        return purchaseRepository.save(purchase);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Purchase purchase = findById(id);
        removeStock(purchase.getProduct(), purchase.getQuantity());
        purchaseRepository.delete(purchase);
    }

    private void copyData(PurchaseRequestDto dto, Purchase purchase) {
        purchase.setQuantity(dto.getQuantity());
        purchase.setUnitPrice(dto.getUnitPrice());
        purchase.setTotal(dto.getQuantity().multiply(dto.getUnitPrice()));
        purchase.setPurchaseDate(dto.getPurchaseDate() == null
                ? LocalDate.now()
                : dto.getPurchaseDate());
    }

    private void validate(PurchaseRequestDto dto) {
        if (dto.getSupplierId() == null) {
            throw new IllegalArgumentException("El proveedor es obligatorio.");
        }
        if (dto.getProductId() == null) {
            throw new IllegalArgumentException("El producto es obligatorio.");
        }
        if (dto.getQuantity() == null || dto.getQuantity().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }
        if (dto.getUnitPrice() == null || dto.getUnitPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El precio debe ser cero o mayor.");
        }
    }

    private Supplier findSupplier(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El proveedor no existe."));
    }

    private Product findProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El producto no existe."));
    }

    // NUEVO MÉTODO: Reemplaza al antiguo addStock para actualizar también el precio de compra actual
    private void addStockAndPricing(Product product, BigDecimal quantity, BigDecimal unitPrice) {
        // 1. Actualizar el precio de compra actual del producto
        product.setPurchasePrice(unitPrice);

        // 2. Sumar al stock actual (lógica original)
        BigDecimal currentStock = product.getCurrentStock() == null
                ? BigDecimal.ZERO
                : product.getCurrentStock();
        product.setCurrentStock(currentStock.add(quantity));
        
        productRepository.save(product);
    }

    private void removeStock(Product product, BigDecimal quantity) {
        BigDecimal currentStock = product.getCurrentStock() == null
                ? BigDecimal.ZERO
                : product.getCurrentStock();
        product.setCurrentStock(currentStock.subtract(quantity));
        productRepository.save(product);
    }
}