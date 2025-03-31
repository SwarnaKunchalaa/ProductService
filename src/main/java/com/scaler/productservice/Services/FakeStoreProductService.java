package com.scaler.productservice.Services;

import com.scaler.productservice.dtos.FakeStoreProductDto;
import com.scaler.productservice.dtos.ProductDto;
import com.scaler.productservice.exception.ProductNotFoundException;
import com.scaler.productservice.models.Category;
import com.scaler.productservice.models.Product;
import jakarta.annotation.PostConstruct;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("fakeStoreProductService")
@Primary
public class FakeStoreProductService implements ProductService {

    @Autowired
    private RestTemplate restTemplate;
    private RedisTemplate<String, Object> redisTemplate;

    public FakeStoreProductService(RestTemplate restTemplate,
                                   RedisTemplate redisTemplate) {
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
    }


    @Override
    public List<Category> getAllCategories() {
        return List.of();
    }

    @Override
    public ProductDto getSingleProduct(Long productId) throws ProductNotFoundException {

        //Check if this product is available in REDIS or not ?
        Product product = (Product) redisTemplate.opsForHash().get("PRODUCTS", "PRODUCT_" + productId);

        //Cache HIT
        if(product != null) {
            return FakeStoreProductDto.toProductDto(product);
        }
        FakeStoreProductDto fakeStoreProductDto = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + productId,
                FakeStoreProductDto.class);

        if (fakeStoreProductDto == null) {
            throw new ProductNotFoundException("Product with id " + productId + " doesn't exist");
        }
        product = convertFakeStoreProductDtoToProduct(fakeStoreProductDto);

        //Before returning the product, store it in the Redis.
        redisTemplate.opsForHash().put("PRODUCTS", "PRODUCT_" + productId, product);

        return FakeStoreProductDto.toProductDto(product);

    }

    @Override
    public Page<Product> getAllProducts(int pageNumber, int pageSize) {
        ResponseEntity<FakeStoreProductDto[]> fakeStoreProductDto = restTemplate.getForEntity("https://fakestoreapi.com/products",
                FakeStoreProductDto[].class);
        List<Product> products = new ArrayList<>();
        for(int i=0; i<fakeStoreProductDto.getBody().length; i++) {
            products.add(convertFakeStoreProductDtoToProduct(fakeStoreProductDto.getBody()[i]));
        }
        return new PageImpl<>(products);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        RequestCallback requestCallback = restTemplate.httpEntityCallback(product, FakeStoreProductDto.class);
        HttpMessageConverterExtractor<FakeStoreProductDto> responseExtractor = new HttpMessageConverterExtractor(FakeStoreProductDto.class, restTemplate.getMessageConverters());
        FakeStoreProductDto fakeStoreProductDto = restTemplate.execute("https://fakestoreapi.com/products/" + id,
                HttpMethod.PATCH, requestCallback, responseExtractor);
        return convertFakeStoreProductDtoToProduct(fakeStoreProductDto);
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {

        FakeStoreProductDto requestBody = new FakeStoreProductDto();
        requestBody.setTitle(productDto.getTitle());
        requestBody.setDescription(productDto.getCategory().getDescription());
        requestBody.setPrice(productDto.getPrice());
        requestBody.setCategory(productDto.getCategory().getName());

        ResponseEntity<FakeStoreProductDto> fakeStoreProductDto = restTemplate.postForEntity(
                "https://fakestoreapi.com/products",requestBody,
                FakeStoreProductDto.class);

        Product response = convertFakeStoreProductDtoToProduct(fakeStoreProductDto.getBody());
        return FakeStoreProductDto.toProductDto(response);
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        return null;
    }

    @Override
    public void deleteProduct(Long id) {

    }

    private Product convertFakeStoreProductDtoToProduct(FakeStoreProductDto fakeStoreProductDto) {
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setPrice(fakeStoreProductDto.getPrice());
        Category category = new Category();
        category.setName(fakeStoreProductDto.getCategory());
        category.setDescription(fakeStoreProductDto.getDescription());
        product.setCategory(category);
        return product;
    }
//    private Product convertFakeStoreProduct(FakeStoreProductDto fakeStoreProductDto) {
//        Product product = new Product();
//        product.setId(fakeStoreProductDto.getId());
//        product.setTitle(fakeStoreProductDto.getTitle());
//        product.setPrice(fakeStoreProductDto.getPrice());
//        Category category = new Category();
//        category.setName(fakeStoreProductDto.getCategory());
//        category.setDescription(fakeStoreProductDto.getDescription());
//        product.setCategory(category);
//        return product;
//    }

}
