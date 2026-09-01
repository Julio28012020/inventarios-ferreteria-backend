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

    // Crear una venta
    @Transactional
    public SaleResponse createSale(SaleRequest request) {

        if (request == null) {
            throw new IllegalArgumentException("La información de la venta es obligatoria.");
        }

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
        sale.setStatus(SaleStatus.PENDING);
        sale.setTotal(BigDecimal.ZERO);

        Sale savedSale = saleRepository.save(sale);

        BigDecimal total = BigDecimal.ZERO;

        for (SaleDetailRequest detailRequest : request.getDetails()) {

            if (detailRequest == null) {
                throw new IllegalArgumentException(
                        "Existe un detalle de venta inválido.");
            }

            if (detailRequest.getProductId() == null) {
                throw new IllegalArgumentException(
                        "El producto es obligatorio.");
            }

            if (detailRequest.getQuantity() == null ||
                    detailRequest.getQuantity()
                            .compareTo(BigDecimal.ZERO) <= 0) {

                throw new IllegalArgumentException(
                        "La cantidad debe ser mayor que cero.");
            }

            Product product = productRepository
                    .findById(detailRequest.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "El producto con ID "
                                    + detailRequest.getProductId()
                                    + " no existe."));

            if (product.getSalePrice() == null) {
                throw new IllegalArgumentException(
                        "El producto "
                                + product.getName()
                                + " no tiene precio de venta.");
            }

            if (product.getCurrentStock() == null) {
                throw new IllegalArgumentException(
                        "El producto "
                                + product.getName()
                                + " no tiene stock configurado.");
            }

            if (product.getCurrentStock()
                    .compareTo(detailRequest.getQuantity()) < 0) {

                throw new IllegalArgumentException(
                        "No hay suficiente stock para el producto: "
                                + product.getName()
                                + ". Stock disponible: "
                                + product.getCurrentStock()
                                + ".");
            }

            BigDecimal unitPrice = product.getSalePrice();

            BigDecimal subtotal = unitPrice.multiply(
                    detailRequest.getQuantity());

            SaleDetail saleDetail = new SaleDetail();

            saleDetail.setSale(savedSale);
            saleDetail.setProduct(product);
            saleDetail.setQuantity(detailRequest.getQuantity());
            saleDetail.setUnitPrice(unitPrice);
            saleDetail.setSubtotal(subtotal);

            saleDetailRepository.save(saleDetail);

            BigDecimal newStock = product.getCurrentStock()
                    .subtract(detailRequest.getQuantity());

            product.setCurrentStock(newStock);

            productRepository.save(product);

            total = total.add(subtotal);
        }

        savedSale.setTotal(total);
        savedSale.setStatus(SaleStatus.COMPLETED);

        Sale finalSale = saleRepository.save(savedSale);

        return convertToResponse(finalSale);
    }

    // Obtener todas las ventas
    @Transactional(readOnly = true)
    public List<SaleResponse> findAllSales() {

        return saleRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // Obtener una venta por ID
    @Transactional(readOnly = true)
    public SaleResponse findSaleById(Long id) {

        if (id == null) {
            throw new IllegalArgumentException(
                    "El ID de la venta es obligatorio.");
        }

        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "La venta con ID " + id + " no existe."));

        return convertToResponse(sale);
    }

    // Convertir una entidad Sale a SaleResponse
    private SaleResponse convertToResponse(Sale sale) {

        List<SaleDetail> details =
                saleDetailRepository.findBySaleId(sale.getId());

        List<SaleDetailResponse> detailResponses = details
                .stream()
                .map(detail -> new SaleDetailResponse(
                        detail.getProduct().getId(),
                        detail.getProduct().getName(),
                        detail.getProduct().getCode(),
                        detail.getQuantity(),
                        detail.getUnitPrice(),
                        detail.getSubtotal()
                ))
                .toList();

        return new SaleResponse(
                sale.getId(),
                sale.getStatus(),
                sale.getPaymentMethod(),
                sale.getTotal(),
                sale.getCreatedAt(),
                detailResponses
        );
    }
}