package com.ferreteria.inventario.controller;

import java.util.List;

import com.ferreteria.inventario.dto.response.SaleResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ferreteria.inventario.dto.request.SaleRequest;
import com.ferreteria.inventario.entity.Sale;
import com.ferreteria.inventario.service.SaleService;

@RestController
@RequestMapping("/sales")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping
    public ResponseEntity<Sale> createSale(
            @RequestBody SaleRequest request) {

        Sale sale = saleService.createSale(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(sale);
    }

    @GetMapping
    public ResponseEntity<List<Sale>> getAllSales() {

        return ResponseEntity.ok(
                saleService.findAllSales());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleResponse> getSaleById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                saleService.findSaleById(id));
    }
}