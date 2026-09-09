package com.ferreteria.inventario.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ferreteria.inventario.dto.request.CustomerRequestDto;
import com.ferreteria.inventario.entity.Customer;
import com.ferreteria.inventario.mapper.CustomerMapper;
import com.ferreteria.inventario.repository.CustomerRepository;
import com.ferreteria.inventario.repository.SaleRepository;
import com.ferreteria.inventario.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final SaleRepository saleRepository;

    public CustomerServiceImpl(
            CustomerRepository customerRepository,
            CustomerMapper customerMapper,
            SaleRepository saleRepository) {

        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.saleRepository = saleRepository;
    }

    @Override
    @Transactional
    public Customer saveFromDto(CustomerRequestDto dto) {

        Customer customer = customerMapper.toEntity(dto);

        return customerRepository.save(customer);
    }

    @Override
    @Transactional
    public Customer updateFromDto(Long id, CustomerRequestDto dto) {

        Customer existingCustomer = findById(id);

        existingCustomer.setDocumentType(dto.getDocumentType());
        existingCustomer.setDocumentNumber(dto.getDocumentNumber());
        existingCustomer.setFullName(dto.getFullName());
        existingCustomer.setPhone(dto.getPhone());
        existingCustomer.setEmail(dto.getEmail());
        existingCustomer.setAddress(dto.getAddress());
        existingCustomer.setCity(dto.getCity());

        return customerRepository.save(existingCustomer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Customer findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Cliente no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public void delete(Long id) {

        if (!customerRepository.existsById(id)) {
            throw new RuntimeException(
                    "No se puede eliminar, el cliente no existe.");
        }

        if (saleRepository.existsByCustomerId(id)) {
            throw new RuntimeException(
                    "No se puede eliminar el cliente porque tiene compras asociadas.");
        }

        customerRepository.deleteById(id);
    }
}