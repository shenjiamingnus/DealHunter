package com.nus.dealhunter.controller;

import com.nus.dealhunter.model.Product;
import com.nus.dealhunter.model.User;
import com.nus.dealhunter.payload.response.GeneralApiResponse;
import com.nus.dealhunter.service.ProductService;
import com.nus.dealhunter.exception.ProductServiceException;
import com.nus.dealhunter.model.PriceHistory;
import com.nus.dealhunter.payload.request.*;
import com.nus.dealhunter.payload.response.GeneralApiResponse;
import com.nus.dealhunter.payload.response.JwtAuthenticationResponse;
import com.nus.dealhunter.util.JwtTokenUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Api("Product/")
@RestController
@RequestMapping("/api/products")

public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/productname")
    public ResponseEntity<List<Product>> getProductByProductname(@RequestParam String productname){
        List<Product> products = productService.getProductByProductname(productname);
        if(!products.isEmpty()){
            return ResponseEntity.ok(products);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id){
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/brandname")
    public ResponseEntity<List<Product>> getProductByBrandname(@RequestParam String brandname){
        List<Product> products = productService.getProductByBrandname(brandname);
        if(!products.isEmpty()){
            return ResponseEntity.ok(products);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        Product savedProduct = productService.saveProduct(product);
        return ResponseEntity.ok(savedProduct);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestBody Product product){
        Product savedProduct = productService.updateProduct(product);
        return ResponseEntity.ok(savedProduct);
    }

    //获取产品的价格历史记录
    @GetMapping("/getProductPriceHistory")
    public ResponseEntity<List<PriceHistory>> getProductPriceHistory(@PathVariable Long id) {
        List<PriceHistory> priceHistoryList = productService.getProductPriceHistory(id);
        return ResponseEntity.ok(priceHistoryList);
    }

    @PostMapping("/{productId}/submit-price")
    public ResponseEntity<Product> submitNewPrice(
            @PathVariable Long productId,
            @RequestParam double newPrice
    ) {
        Product updatedProduct = productService.submitNewPrice(productId, newPrice);
        return ResponseEntity.ok(updatedProduct);
    }



}





