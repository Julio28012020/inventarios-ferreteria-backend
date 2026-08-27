package com.ferreteria.inventario.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ferreteria.inventario.dto.request.SaleDetailRequest;
import com.ferreteria.inventario.dto.request.SaleRequest;
import com.ferreteria.inventario.dto.response.SaleDetailResponse;
import com.ferreteria.inventario.dto.response.SaleResponse;
import com.ferreteria.inventario.entity.Product;
import com.ferreteria.inventario.entity.Sale;
import com.ferreteria.inventario.entity.SaleDetail;
import com.ferreteria.inventario.enums.SaleStatus;
import com.ferreteria.inventario.repository.ProductRepository;
import com.ferreteria.inventario.repository.SaleDetailRepository;
import com.ferreteria.inventario.repository.SaleRepository;

@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final SaleDetailRepository saleDetailRepository;
    private final ProductRepository productRepository;

    public SaleService(
            SaleRepository saleRepository,
            SaleDetailRepository saleDetailRepository,
            ProductRepository productRepository) {

        this.saleRepository = saleRepository;
        this.saleDetailRepository = saleDetailRepository;
        this.productRepository = productRepository;
    }

    // Crear venta
    @Transactional
    public Sale createSale(SaleRequest request) {

        if (request.getPaymentMethod() == null) {
            throw new IllegalArgumentException(
                    "El método de pago es obligatorio.");
        }

        if (request.getDetails() == null ||
                request.getDetails().isEmpty()) {

            throw new IllegalArgumentException(
                    "La venta debe tener al menos un producto.");
        }

        Sale sale = new Sale();

        sale.setPaymentMethod(request.getPaymentMethod());
        sale.setTotal(BigDecimal.ZERO);
        sale.setStatus(SaleStatus.PENDING);

        Sale savedSale = saleRepository.save(sale);

        BigDecimal total = BigDecimal.ZERO;

        for (SaleDetailRequest detailRequest : request.getDetails()) {

            // Validar producto
            if (detailRequest.getProductId() == null) {

                throw new IllegalArgumentException(
                        "El producto es obligatorio.");
            }

            // Validar cantidad
            if (detailRequest.getQuantity() == null ||
                    detailRequest.getQuantity()
                            .compareTo(BigDecimal.ZERO) <= 0) {

                throw new IllegalArgumentException(
                        "La cantidad debe ser mayor que cero.");
            }

            // Buscar producto
            Product product = productRepository
                    .findById(detailRequest.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "El producto no existe."));

            // Validar stock
            if (product.getCurrentStock()
                    .compareTo(detailRequest.getQuantity()) < 0) {

                throw new IllegalArgumentException(
                        "No hay suficiente stock para el producto: "
                                + product.getName());
            }

            // Precio actual del producto
            BigDecimal unitPrice = product.getSalePrice();

            // Calcular subtotal
            BigDecimal subtotal = unitPrice.multiply(
                    detailRequest.getQuantity());

            // Crear detalle
            SaleDetail saleDetail = new SaleDetail();

            saleDetail.setSale(savedSale);
            saleDetail.setProduct(product);
            saleDetail.setQuantity(detailRequest.getQuantity());
            saleDetail.setUnitPrice(unitPrice);
            saleDetail.setSubtotal(subtotal);

            saleDetailRepository.save(saleDetail);

            // Descontar inventario
            product.setCurrentStock(
                    product.getCurrentStock()
                            .subtract(detailRequest.getQuantity()));

            productRepository.save(product);

            // Acumular total
            total = total.add(subtotal);
        }

        // Marcar venta como completada
        savedSale.setStatus(SaleStatus.COMPLETED);

        // Guardar total
        savedSale.setTotal(total);

        return saleRepository.save(savedSale);
    }

    // Obtener todas las ventas
public List<Sale> findAllSales() {
    return saleRepository.findAll();
}

// Obtener una venta por ID
public SaleResponse findSaleById(Long id) {

    Sale sale = saleRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException(
                    "La venta no existe."));

    List<SaleDetail> details = saleDetailRepository.findBySaleId(id);

    List<SaleDetailResponse> detailResponses = details.stream()
            .map(detail -> new SaleDetailResponse(
                    detail.getProduct().getId(),
                    detail.getProduct().getName(),
                    detail.getProduct().getCode(),
                    detail.getQuantity(),
                    detail.getUnitPrice(),
                    detail.getSubtotal()))
            .toList();

    return new SaleResponse(
            sale.getId(),
            sale.getStatus(),
            sale.getPaymentMethod(),
            sale.getTotal(),
            sale.getCreatedAt(),
            detailResponses);
}
}