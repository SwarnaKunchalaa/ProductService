package com.scaler.productservice.Services;

import com.scaler.productservice.dtos.FakeStoreProductDto;
import com.scaler.productservice.dtos.ProductDto;
import com.scaler.productservice.models.Category;
import com.scaler.productservice.models.Product;
import jakarta.annotation.PostConstruct;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("fakeStoreProductService")

public class FakeStoreProductService implements ProductService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    RestTemplate restTemplate1;

    @Autowired
    RestTemplate restTemplate2;

    FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public List<Category> getAllCategories() {
        return List.of();
    }

    @Override
    public ProductDto getSingleProduct(Long productId) {
     //  throw new ArithmeticException("Something went wrong");
        FakeStoreProductDto fakeStoreProductDto = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + productId,
                FakeStoreProductDto.class);
        //convert fakeStoreProductDto to product

        return null;
                //convertFakeStoreProductDtoToProduct(fakeStoreProductDto);
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
    public ProductDto createProduct(Product product) {

//        System.out.println(restTemplate1+" Hello hoe "+restTemplate2);
//        FakeStoreProductDto requestBody = new FakeStoreProductDto();
//        requestBody.setTitle(product.getTitle());
//        requestBody.setDescription(product.getCategory().getDescription());
//        requestBody.setPrice(product.getPrice());
//       // requestBody.setCategory(product.getCategory().getName());
//
//        ResponseEntity<FakeStoreProductDto> fakeStoreProductDto = restTemplate.postForEntity(
//                "https://fakestoreapi.com/products",requestBody,
//                FakeStoreProductDto.class);
//        //Product response = convertFakeStoreProductDtoToProduct(fakeStoreProductDto.getBody());
//
//        System.out.println(fakeStoreProductDto.getBody().getTitle()+" "+fakeStoreProductDto.getBody().getPrice());
//        return fakeStoreProductDto.getBody();
        return null;
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
    @PostConstruct
    public void checkBeans() {
        System.out.println("✅ RestTemplate1: " + restTemplate1);
        System.out.println("✅ RestTemplate2: " + restTemplate2);
    }
}
