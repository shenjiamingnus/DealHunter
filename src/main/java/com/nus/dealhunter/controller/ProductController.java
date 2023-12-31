package com.nus.dealhunter.controller;

import com.nus.dealhunter.annotation.CurrentUser;
import com.nus.dealhunter.model.CustomUserDetails;
import com.nus.dealhunter.model.Product;
import com.nus.dealhunter.payload.request.CreateProductRequest;
import com.nus.dealhunter.payload.request.UpdateProductRequest;
import com.nus.dealhunter.payload.response.GeneralApiResponse;
import com.nus.dealhunter.service.ProductService;
import com.nus.dealhunter.exception.ProductServiceException;
import com.nus.dealhunter.model.PriceHistory;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@Api("Product/")
@RestController
@RequestMapping("/api/products")

public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
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
    public ResponseEntity<GeneralApiResponse> createProduct(@RequestBody CreateProductRequest createProductRequest){
        Product savedProduct = productService.createProduct(createProductRequest);
        if(savedProduct != null){
            return ResponseEntity.ok(new GeneralApiResponse(true,"Product created!"));
        }else {
            return ResponseEntity.ok(new GeneralApiResponse(false,"Product failed to created"));
        }
    }
    
    @PutMapping
    public ResponseEntity<GeneralApiResponse> updateProduct(@RequestBody UpdateProductRequest updateProductRequest){
        Product updatedProduct = productService.updateProduct(updateProductRequest);
        if(updatedProduct != null){
            return ResponseEntity.ok(new GeneralApiResponse(true,"Product updated!"));
        }else {
            return ResponseEntity.ok(new GeneralApiResponse(false,"Product failed to updated"));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    //获取产品的价格历史记录
    @GetMapping("/getProductPriceHistory/{productId}")
    public ResponseEntity<List<PriceHistory>> getProductPriceHistory(@PathVariable Long productId) {
        List<PriceHistory> priceHistoryList = productService.getProductPriceHistory(productId);
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

    @PostMapping("/{productId}/addWatchers")
    public ResponseEntity<Void> addUserWatchesProduct(@CurrentUser CustomUserDetails userDetails, @PathVariable Long productId) {
        productService.addUserWatchesProduct(userDetails.getId(), productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{productId}/deleteWatchers")
    public ResponseEntity<Void> removeUserWatchesProduct(@CurrentUser CustomUserDetails userDetails, @PathVariable Long productId) {
        productService.removeUserWatchesProduct(userDetails.getId(), productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{productId}/checkWatchers")
    public ResponseEntity<Boolean> isUserWatchingProduct(@CurrentUser CustomUserDetails userDetails, @PathVariable Long productId) {
        boolean isWatching = productService.isUserWatchingProduct(userDetails.getId(), productId);
        return new ResponseEntity<>(isWatching, HttpStatus.OK);
    }


}





