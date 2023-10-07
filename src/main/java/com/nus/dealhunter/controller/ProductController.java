package com.nus.dealhunter.controller;

import com.nus.dealhunter.model.Product;
import com.nus.dealhunter.service.ProductService;
import com.nus.dealhunter.util.JwtTokenUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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




}
