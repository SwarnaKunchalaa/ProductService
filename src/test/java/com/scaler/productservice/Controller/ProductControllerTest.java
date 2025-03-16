package com.scaler.productservice.Controller;

import com.scaler.productservice.Services.ProductService;
import com.scaler.productservice.dtos.ProductDto;
import com.scaler.productservice.exception.ProductNotFoundException;
import com.scaler.productservice.models.Product;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest
class ProductControllerTest {

    @Autowired
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Test
    void getProductById() throws Exception {
        ProductDto expectedProduct = new ProductDto();
        expectedProduct.setTitle("Samsung");
        expectedProduct.setPrice(10000);


       when(productService.getSingleProduct(10L))
               .thenReturn(expectedProduct);
       ProductDto actualProduct = productService.getSingleProduct(20L);
       //Assert
    }

    @Test
    void getAllProducts() {
    }

    @Test
    void updateProduct() {
    }

    @Test
    void patchProduct() {
    }

    @Test
    void deleteProduct() {
    }

    @Test
    void createProduct() {
    }
}