package com.scaler.productservice.dtos;

import com.scaler.productservice.models.Category;
import com.scaler.productservice.models.Product;

public class FakeStoreProductDto  {
    private Long id;
    private String title;
    private double price;
    private String category;
    private String description;
    private String image;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public static ProductDto toProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setTitle(product.getTitle());
        productDto.setPrice(product.getPrice());
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(product.getCategory().getName());
        categoryDto.setId(product.getCategory().getId());
        categoryDto.setDescription(product.getCategory().getDescription());
        productDto.setCategory(categoryDto);
        return productDto;
    }
}
