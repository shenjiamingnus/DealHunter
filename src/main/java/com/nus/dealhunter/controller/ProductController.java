package com.nus.dealhunter.controller;

import com.nus.dealhunter.model.Product;
import com.nus.dealhunter.service.ProductService;
import com.nus.dealhunter.exception.ProductServiceException;
import com.nus.dealhunter.model.PriceHistory;
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

    @GetMapping("/productname")
    public ResponseEntity<List<Product>> getProductByProductname(@RequestParam String productname){
        List<Product> products = productService.getProductByProductname(productname);
        if(!products.isEmpty()){
            return ResponseEntity.ok(products);
        }else{
            return ResponseEntity.notFound().build();
        }
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



    //获取产品的价格历史记录
    @GetMapping("/getPriceHistory")
    public ResponseEntity<List<PriceHistory>> getPriceHistory(@RequestParam String productname,
                                                              @RequestParam String brandname) {
        try {
            List<PriceHistory> priceHistoryList = productService.getProductPriceHistory(productname, brandname);
            return ResponseEntity.ok(priceHistoryList);
        } catch (ProductServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/submitNewPrice")
    public ResponseEntity<Product> submitNewPrice(
            @RequestParam String productname,
            @RequestParam String brandname,
            @RequestParam double newPrice) {
        try {
            Product updatedProduct = productService.submitNewPrice(productname, brandname, newPrice);
            return ResponseEntity.ok(updatedProduct);
        } catch (ProductServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }



}





