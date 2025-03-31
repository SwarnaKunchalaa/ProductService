package com.scaler.productservice.Controller;

//import com.scaler.productservice.Services.FakeStoreProductService;
import com.scaler.productservice.Services.ProductService;
import com.scaler.productservice.dtos.FakeStoreProductDto;
import com.scaler.productservice.dtos.ProductDto;
import com.scaler.productservice.exception.ProductNotFoundException;
import com.scaler.productservice.models.Category;
import com.scaler.productservice.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService ;

    public ProductController(ProductService productService){

        this.productService = productService;
    }

    //http://localhost/product/id
    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable("id") Long id)   throws ProductNotFoundException{

        return productService.getSingleProduct(id);
//        ResponseEntity<Product> responseEntity = null;
////        try {
////            responseEntity = new ResponseEntity<>(
////                    productService.getSingleProduct(id),
////                    HttpStatus.FORBIDDEN
////            );
////        }
////        catch (RuntimeException e){
////            responseEntity = new ResponseEntity<>(
////                    HttpStatus.NOT_FOUND
////            );
////        }
//        responseEntity = new ResponseEntity<>(
//                    productService.getSingleProduct(id),
//                    HttpStatus.FORBIDDEN
//            );
//        return responseEntity;

    }

    @GetMapping()
    public Page<Product> getAllProducts(@RequestParam(defaultValue = "1") int pageNumber, @RequestParam(defaultValue = "0") int pageSize)   throws Exception{
        return productService.getAllProducts(pageNumber, pageSize);
    }

    @PutMapping("/{id}")
     public Product updateProduct(@PathVariable("id") Long id, @RequestBody Product product) throws ProductNotFoundException {
         return productService.updateProduct(id, product);
    }

    @PatchMapping("/{id}")
    public Product patchProduct(@PathVariable("id") Long id, @RequestBody Product product) throws ProductNotFoundException {
        return  productService.updateProduct(id, product);
     }

     @DeleteMapping("/{id}")
     public void DeleteProduct(@PathVariable("id") Long id){
        productService.deleteProduct(id);
     }

     @GetMapping("/categories")
     public List<Category> getAllCategories(){
        return productService.getAllCategories();
     }


     @PostMapping()
    public ProductDto createProduct(@RequestBody ProductDto productDto){
        return productService.createProduct(productDto);
//         Product fakeStoreProductDto  = productService.createProduct(product);
//         System.out.println(fakeStoreProductDto.getTitle()+" "+fakeStoreProductDto.getPrice());
////        System.out.println(response.getPrice());
////         System.out.println("hello swarna");
//         return fakeStoreProductDto;
         //return null;
     }

//     @ExceptionHandler(ArithmeticException.class)
//     public ResponseEntity<String> handleArithmeticException(){
//         ResponseEntity<String> response = new ResponseEntity<>(
//                 "something went wrong, Coming from controller Advice inside controller",
//                 HttpStatus.NOT_FOUND
//         );
//         return response;
//     }
}
