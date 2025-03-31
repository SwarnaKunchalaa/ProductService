package com.scaler.productservice.Services;

import com.scaler.productservice.dtos.CategoryDto;
import com.scaler.productservice.dtos.ProductDto;
import com.scaler.productservice.exception.ProductNotFoundException;
import com.scaler.productservice.models.Category;
import com.scaler.productservice.models.Product;
import com.scaler.productservice.repositories.CategoryRepository;
import com.scaler.productservice.repositories.ProductRepository;
import org.reactivestreams.Publisher;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("selfProductService")
public class SelfProductService implements ProductService{

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;



    SelfProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }

    @Override
    public ProductDto getSingleProduct(Long id) throws ProductNotFoundException{
        Optional<Product> product =  productRepository.findById(id);
        if(product.isEmpty()){
            throw new ProductNotFoundException("Product not  Found");
        }
        return convertProductDtoToProduct(product.get());
    }

    @Override
    public Page<Product> getAllProducts( int pageNumber, int pageSize) {
        Sort sort = Sort.by("title").ascending().and(Sort.by("price").descending());

        return productRepository.findAll(
                PageRequest.of(pageNumber,pageSize,sort)
        );

        //return null;
    }

    @Override
    public Product updateProduct(Long id, Product product) throws ProductNotFoundException{
        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isEmpty()){
            throw new ProductNotFoundException("Product not found");
        }
        Product productDB = productOptional.get();
        productDB.setTitle(product.getTitle());
        productDB.setPrice(product.getPrice());
        return productRepository.save(productDB);
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        return null;
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        Product product = ProductDto.toProduct(productDto);
        //Optional<Category> category = categoryRepository.findById(product.getCategory().getId());
        Category category = product.getCategory();
        if(category != null){
            if(category.getId()==null) {
                if (category.getName() != null) {
                    Optional<Category> categoryOptional = categoryRepository.findByName(category.getName());
                    if (categoryOptional.isEmpty()) {
                        category = categoryRepository.save(category);
                    } else {
                        category.setId(categoryOptional.get().getId());
                    }
                }
                else {
                    category = categoryRepository.save(category);
                }
                product.setCategory(category);
            }

        }
        else{
            throw new RuntimeException("Category should not be null");
        }
        productRepository.save(product);
        return convertProductDtoToProduct(product);
    }
    private ProductDto convertProductDtoToProduct(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setTitle(product.getTitle());
        productDto.setPrice(product.getPrice());

        CategoryDto category = new CategoryDto();
        category.setName(product.getCategory().getName());
        category.setDescription(product.getCategory().getDescription());
        category.setId(product.getCategory().getId());
        productDto.setCategory(category);
        return productDto;
    }
}
