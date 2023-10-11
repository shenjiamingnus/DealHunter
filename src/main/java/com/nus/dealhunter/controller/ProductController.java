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

    @PutMapping("/{id}/productname")
    public ResponseEntity<Product> modifyProductname(@PathVariable Long id, @RequestParam String newProductname) {
        Product modifyProduct = productService.modifyProductname( id, newProductname);
        if (modifyProduct != null){
            return ResponseEntity.ok(modifyProduct);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/storeAddress")
    public ResponseEntity<Product> modifyBrandname(@PathVariable Long id, @RequestParam String newStoreAddress) {
        Product modifyProduct = productService.modifyStoreAddress( id, newStoreAddress);
        if (modifyProduct != null){
            return ResponseEntity.ok(modifyProduct);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/discription")
    public ResponseEntity<Product> modifyDiscription(@PathVariable Long id, @RequestParam String newDiscription) {
        Product modifyProduct = productService.modifyStoreAddress( id, newDiscription);
        if (modifyProduct != null){
            return ResponseEntity.ok(modifyProduct);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/ImageUrl")
    public ResponseEntity<Product> modifyImageUrl(@PathVariable Long id, @RequestParam String newImageUrl) {
        Product modifyProduct = productService.modifyImageUrl( id, newImageUrl);
        if (modifyProduct != null){
            return ResponseEntity.ok(modifyProduct);
        }else{
            return ResponseEntity.notFound().build();
        }
    }



    //获取产品的价格历史记录
    @GetMapping("/getPriceHistory")
    public ResponseEntity<List<PriceHistory>> getPriceHistory(@RequestParam String productname,
                                                              @RequestParam String brandname) {
        List<PriceHistory> priceHistoryList = productService.getProductPriceHistory(productname, brandname);
        return ResponseEntity.ok(priceHistoryList);

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





