package com.scaler.productservice.Controller;

import com.scaler.productservice.Services.OrderService;
import com.scaler.productservice.Services.ProductService;
import com.scaler.productservice.dtos.ProductDto;
import com.scaler.productservice.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    ProductService productService;
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    OrderService orderService;

    @GetMapping("/{id}")
    public String orderProduct(@PathVariable("id") Long productId) throws ProductNotFoundException {
        return orderService.orderProduct(productId);
    }
}
