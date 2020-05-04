package com.gmail.ek.chernyavskaya.boot.controller;

import com.gmail.ek.chernyavskaya.service.OrderService;
import com.gmail.ek.chernyavskaya.service.model.InvoiceDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class InvoiceApiController {

    private final OrderService orderService;

    public InvoiceApiController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<InvoiceDTO> getOrders() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public InvoiceDTO getOrderById(@PathVariable Long id) {
        return orderService.findById(id);
    }

}