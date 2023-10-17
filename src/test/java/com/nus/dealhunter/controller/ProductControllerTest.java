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
import java.util.Arrays;
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
        // 创建模拟的 Product 对象
        Product product1 = new Product(Long.valueOf(1), "productname", 0d);
        Product product2 = new Product(Long.valueOf(2), "anotherproduct", 10.5d);
        Product product3 = new Product(Long.valueOf(3), "yetanotherproduct", 20.0d);

        // 使用 Arrays.asList 创建 List
        List<Product> productList = Arrays.asList(product1, product2, product3);

        // 模拟 getAllProducts 方法的行为
        when(productService.getAllProducts()).thenReturn(productList);

        // 测试 Controller
        ResponseEntity<List<Product>> result = productController.getAllProducts();

        // 断言
        Assertions.assertEquals(productList, result.getBody());
    }

    @Test
    void testGetProductByProductname() {

        // 创建模拟的 Product 对象
        Product product1 = new Product(Long.valueOf(1), "productname", 0d);

        // 使用 Arrays.asList 创建 List
        List<Product> productList = Arrays.asList(product1);

        // 模拟 getProductByProductname 方法的行为
        when(productService.getProductByProductname(anyString())).thenReturn(productList);

        // 测试 Controller
        ResponseEntity<List<Product>> result = productController.getProductByProductname("productname");

        // 断言
        Assertions.assertEquals(productList, result.getBody());
    }

    @Test
    void testGetProductById() {
        // 创建模拟的 Product 对象
        Product product1 = new Product(Long.valueOf(1), "productname", 0d);

        // 使用 Arrays.asList 创建 List
        List<Product> productList = Arrays.asList(product1);

        // 模拟 getProductById 方法的行为
        when(productService.getProductById(anyLong())).thenReturn(Optional.of(product1));

        // 测试 Controller
        ResponseEntity<Product> result = productController.getProductById(Long.valueOf(1));

        // 断言
        Assertions.assertEquals(product1, result.getBody());
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
    void testGetPriceHistory() {
        // 创建模拟的 Product 对象和 PriceHistory 对象
        Product product1 = new Product(Long.valueOf(1), "productname", 0d);
        PriceHistory priceHistory1 = new PriceHistory(0d, LocalDate.of(2023, Month.OCTOBER, 11), product1);

        // 使用 Arrays.asList 创建 List
        List<PriceHistory> priceHistoryList = Arrays.asList(priceHistory1);

        // 模拟 getProductPriceHistory 方法的行为
        when(productService.getProductPriceHistory(anyString(), anyString())).thenReturn(priceHistoryList);

        // 测试 Controller
        ResponseEntity<List<PriceHistory>> result = productController.getPriceHistory("productname", "brandname");

        // 断言
        Assertions.assertEquals(priceHistoryList, result.getBody());
    }

    @Test
    void testSubmitNewPrice() {
        // 创建模拟的 Product 对象
        Product product1 = new Product(Long.valueOf(1), "productname", 1d);

        // 模拟 submitNewPrice 方法的行为
        when(productService.submitNewPrice(anyString(), anyString(), anyDouble())).thenReturn(product1);

        // 测试 Controller
        ResponseEntity<Product> result = productController.submitNewPrice("productname", "brandname", 0d);

        // 断言
        Assertions.assertEquals(product1, result.getBody());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: https://weirddev.com/forum#!/testme