package com.nus.dealhunter.controller;

import com.nus.dealhunter.model.PriceHistory;
import com.nus.dealhunter.model.Product;
import com.nus.dealhunter.service.ProductService;
import com.nus.dealhunter.util.JwtTokenUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class ProductControllerTest{
    @Mock
    ProductService productService;

    @InjectMocks
    ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts() {
        when(productService.getAllProducts()).thenReturn(List.of(new Product(Long.valueOf(1), "productname", 0d)));

        List<Product> result = productController.getAllProducts();
        Assertions.assertEquals(List.of(new Product(Long.valueOf(1), "productname", 0d)), result);
    }

    @Test
    void testGetProductByProductname() {
        when(productService.getProductByProductname(anyString())).thenReturn(List.of(new Product(Long.valueOf(1), "productname", 0d)));

        ResponseEntity<List<Product>> result = productController.getProductByProductname("productname");
        Assertions.assertEquals(List.of(new Product(Long.valueOf(1), "productname", 0d)), result.getBody());
    }

    @Test
    void testGetProductById() {
        when(productService.getProductById(anyLong())).thenReturn(Optional.of(new Product(Long.valueOf(1), "productname", 0d)));

        ResponseEntity<Product> result = productController.getProductById(Long.valueOf(1));
        Product product = Optional.of(new Product(Long.valueOf(1), "productname", 0d)).get();
        Assertions.assertEquals(product, result.getBody());
    }

    @Test
    void testCreateProduct() {
        when(productService.saveProduct(any())).thenReturn(new Product(Long.valueOf(1), "productname", 0d));

        ResponseEntity<Product> result = productController.createProduct(new Product(Long.valueOf(1), "productname", 0d));
        Product product = Optional.of(new Product(Long.valueOf(1), "productname", 0d)).get();
        Assertions.assertEquals(product, result.getBody());
    }

    @Test
    void testUpdateProduct() {
        when(productService.updateProduct(any())).thenReturn(new Product(Long.valueOf(1), "productnameUpdate", 0d));

        ResponseEntity<Product> result = productController.updateProduct(new Product(Long.valueOf(1), "productname", 0d));
        Assertions.assertEquals("productnameUpdate", result.getBody().getProductname());
    }

    @Test
    void testDeleteProduct() {
        ResponseEntity<Void> result = productController.deleteProduct(Long.valueOf(1));
        Assertions.assertEquals(null, result.getBody());
    }

    @Test
    void testgGetProductPriceHistory() {
        when(productService.getProductPriceHistory(anyLong())).thenReturn(List.of(new PriceHistory(0d, LocalDate.of(2023, Month.OCTOBER, 11), new Product(Long.valueOf(1), "productname", 0d))));

        ResponseEntity<List<PriceHistory>> result = productController.getProductPriceHistory(Long.valueOf(1));
        List<PriceHistory> priceHistory = List.of(new  PriceHistory(0d, LocalDate.of(2023, Month.OCTOBER, 11), new Product(Long.valueOf(1), "productname", 0d)));
        Assertions.assertEquals(priceHistory, result.getBody());
    }

    @Test
    void testSubmitNewPrice() {
        when(productService.submitNewPrice(anyLong(), anyDouble())).thenReturn(new Product(Long.valueOf(1), "productname", 1d));

        ResponseEntity<Product> result = productController.submitNewPrice(Long.valueOf(1), 0d);
        Product products = new Product(Long.valueOf(1), "productname", 1d);
        Assertions.assertEquals(products, result.getBody());
    }
}