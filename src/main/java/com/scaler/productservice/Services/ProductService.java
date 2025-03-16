package com.scaler.productservice.Services;

import com.scaler.productservice.dtos.ProductDto;
import com.scaler.productservice.exception.ProductNotFoundException;
import com.scaler.productservice.models.Category;
import com.scaler.productservice.models.Product;
import org.reactivestreams.Publisher;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    List<Category> getAllCategories();
    ProductDto getSingleProduct(Long id) throws ProductNotFoundException;
    Page<Product> getAllProducts(int pageNumber, int pageSize) throws ProductNotFoundException;
    Product updateProduct(Long id,Product product) throws ProductNotFoundException;
    Product replaceProduct(Long id,Product product);
    void deleteProduct(Long id);
    ProductDto createProduct(Product product);
}
