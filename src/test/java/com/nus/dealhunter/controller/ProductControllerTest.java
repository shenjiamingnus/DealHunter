package com.nus.dealhunter.controller;


import com.nus.dealhunter.model.PriceHistory;
import com.nus.dealhunter.model.Product;
import com.nus.dealhunter.repository.ProductRepository;
import com.nus.dealhunter.service.ProductService;
import com.nus.dealhunter.service.PriceHistoryService;
import com.nus.dealhunter.util.JwtTokenUtil;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ProductService productService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductRepository priceHistoryRepository;

    @Mock
    JwtTokenUtil jwtTokenUtil;
    @InjectMocks
    ProductController productController;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts() {
        when(productService.getAllProducts()).thenReturn(List.of(new Product("productname", "brandname")));

        List<Product> result = productController.getAllProducts();
        Assertions.assertEquals(List.of(new Product("productname", "brandname")), result);
    }

    @Test
    void testGetProductById() {

        // Arrange
        Long productId = 1L;
        Product product = new Product();
        Mockito.when(productService.getProductById(productId)).thenReturn(Optional.of(product));

        // Act
        ResponseEntity<Product> result = productController.getProductById(productId);

        // Assert
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assert.assertEquals(product, result.getBody());
    }

    @Test
    void testCreateProduct() {
        when(productService.saveProduct(any())).thenReturn(new Product("productname", "brandname"));

        ResponseEntity<Product> result = productController.createProduct(new Product("productname", "brandname"));
        Assertions.assertNotEquals(null, result);
    }

    @Test
    void testDeleteProduct() {
        // Arrange
        Long productId = 1L;

        // Act
        ResponseEntity<Void> result = productController.deleteProduct(productId);

        // Assert
        Assert.assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        Mockito.verify(productService).deleteProduct(productId);
    }


    public void testGetProductPriceHistory() {
        String productName = "TestProduct";
        String brandName = "TestBrand";
        Product product = new Product(productName, brandName);
        List<PriceHistory> priceHistoryList = new ArrayList<>();

        when(productRepository.findByProductnameAndBrandname(productName, brandName)).thenReturn(Optional.of(product));

        List<PriceHistory> result = productService.getProductPriceHistory(productName, brandName);

        // 验证
        Assert.assertEquals(priceHistoryList, result);
    }

//    @Test
//    public void testSubmitNewPrice() {
//        // 准备测试数据
//        String productName = "TestProduct";
//        String brandName = "TestBrand";
//        double currentPrice = 99.99;
//        double newPrice = 89.99; // 新的价格
//
//        Product product = new Product(productName, brandName);
//        product.setCurrentPrice(currentPrice);
//        product.setLowestPrice(0); // 设置历史最低价为0
//
//        PriceHistory newPriceHistory = new PriceHistory(newPrice, LocalDate.now(), product);
//
//        // 模拟 productRepository 的行为
//        when(productRepository.findByProductnameAndBrandname(productName, brandName)).thenReturn(Optional.of(product));
//        when(productRepository.save(any(Product.class))).thenReturn(product);
//
//        // 模拟 priceHistoryRepository 的行为
//        when(priceHistoryRepository.save(any(PriceHistory.class))).thenReturn(newPriceHistory);
//
//        // 模拟 productService 的行为
//        when(productService.submitNewPrice(productName, brandName, newPrice)).thenReturn(product);
//
//        // 调用方法
//        ResponseEntity<Product> result = productController.submitNewPrice(productName, brandName, newPrice);
//
//        // 验证响应状态码
//        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
//
//        // 验证currentPrice是否正确更新
//        Assert.assertEquals(newPrice, result.getBody().getCurrentPrice(), 0.01);
//
//        // 验证lowestPrice是否正确更新
//        Assert.assertEquals(newPrice, result.getBody().getLowestPrice(), 0.01);
//
//        // 验证priceHistory是否被正确添加
//        Assert.assertNotNull(result.getBody().getPriceHistoryList());
//        Assert.assertFalse(result.getBody().getPriceHistoryList().isEmpty());
//        Assert.assertEquals(newPrice, result.getBody().getPriceHistoryList().get(0).getPrice(), 0.01);
//    }



}








//Generated with love by TestMe :) Please report issues and submit feature requests at: https://weirddev.com/forum#!/testme