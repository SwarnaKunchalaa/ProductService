package com.scaler.productservice.Services;

import com.scaler.productservice.dtos.PaymentRequestDto;
import com.scaler.productservice.dtos.ProductDto;
import com.scaler.productservice.exception.ProductNotFoundException;
import com.scaler.productservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;



@RestController
@RequestMapping("/order")
public class OrderService {

    @Autowired
    ProductService productService;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/{id}")
    public String orderProduct(@PathVariable("id") Long productId) throws ProductNotFoundException {
        ProductDto product = productService.getSingleProduct(productId);
        if (product == null) {
            throw new ProductNotFoundException("Product with id " + productId + " doesn't exist");
        }
        PaymentRequestDto paymentRequestDto = new PaymentRequestDto();
        paymentRequestDto.setOrderId(productId+"");
        Long price = (long)product.getPrice();
        paymentRequestDto.setAmount(price+10000);
          String url = restTemplate.postForObject("http://localhost:8082/payments",paymentRequestDto
                ,String.class);
        return url;
       // return "swarna";
    }
}
