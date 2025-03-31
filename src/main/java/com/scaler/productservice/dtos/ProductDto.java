package com.scaler.productservice.dtos;

import com.scaler.productservice.models.Category;
import com.scaler.productservice.models.Product;

public class ProductDto {
    private Long id;
    private String title;
    private double price;
    private CategoryDto categoryDto;
    private String description;
    private String image;

    public CategoryDto getCategory() {
        return categoryDto;
    }

    public void setCategory(CategoryDto categoryDto) {
        this.categoryDto = categoryDto;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static Product toProduct(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        Category category = new Category();
        category.setId(productDto.getCategory().getId());
        category.setDescription(productDto.getDescription());
        category.setName(productDto.getCategory().getName());
        product.setCategory(category);
        return product;
    }
}
