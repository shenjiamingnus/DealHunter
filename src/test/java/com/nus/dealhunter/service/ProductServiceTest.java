package com.nus.dealhunter.service;


import com.nus.dealhunter.model.PriceHistory;
import com.nus.dealhunter.model.Product;
import com.nus.dealhunter.model.User;
import com.nus.dealhunter.repository.BrandRepository;
import com.nus.dealhunter.repository.ProductRepository;
import com.nus.dealhunter.repository.PriceHistoryRepository;
import com.nus.dealhunter.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;



import java.util.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class ProductServiceTest {
    @Mock
    ProductRepository productRepository;
    @Mock
    PriceHistoryRepository priceHistoryRepository;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    ProductService productService;

//    @Mock
//    private JavaMailSender javaMailSender;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }

    public ProductServiceTest() {
        MockitoAnnotations.openMocks(this);  // Initialize annotated mocks
    }
    @Test
    void testCheckProductNameExists() {
        when(productRepository.existsByProductname(anyString())).thenReturn(Boolean.TRUE);

        Boolean result = productService.checkProductNameExists("productname");
        Assertions.assertEquals(Boolean.TRUE, result);
    }

    @Test
    public void getAllProducts(){
        // Arrange
        List<Product> productList = Arrays.asList(new Product(), new Product());
        Mockito.when(productRepository.findAll()).thenReturn(productList);

        // Act
        List<Product> result = productService.getAllProducts();

        // Assert
        Assert.assertEquals(productList, result);

    }

    @Test
    void testGetProductById() {
        // Arrange
        Long productId = 1L;
        Product mockProduct = new Product(productId, "Productname","Brandname", 29.99);
        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

        // Act
        Optional<Product> result = productService.getProductById(productId);

        // Assert
        Assert.assertTrue(result.isPresent());
        Product returnedProduct = result.get();
        Assert.assertEquals(productId,returnedProduct.getId());
        Assert.assertEquals("Productname",returnedProduct.getProductname());
        /**0.001 acceptable difference for a double comparison*/
        Assert.assertEquals(29.99,returnedProduct.getCurrentPrice(),0.001);
    }

    @Test
    void testCreateProduct() {
//        // Arrange
//        CreateProductRequest createProductRequest = new CreateProductRequest("productname", "brandname", "storeAddress", "description", "imageUrl", 0d, 0L);
//
//        Product product = productService.createProduct(createProductRequest);
//
//        when(productRepository.save(any(Product.class))).thenReturn(product);
//
//        Product result = productService.createProduct(createProductRequest);
//
//
//        // Assert
//        Assert.assertEquals(result,product);


    }

    @Test
    void testUpdateProduct() {
//        // Arrange
//        Product productToSave = new Product(1L, "NewProduct", 19.99);
//        Brand brand = new Brand(1L, "NewBrand");
//        productToSave.setBrand(brand);
//
//        when(productRepository.save(productToSave)).thenReturn(productToSave);
//        when(brandRepository.findById(brand.getId())).thenReturn(Optional.empty());
//        when(brandRepository.save(brand)).thenReturn(brand);
//
//        // Act
//        Product savedProduct = productService.saveProduct(productToSave);
//
//        // Assert
//        Assert.assertEquals(productToSave, savedProduct);
    }


    @Test
    void testDeleteProduct() {
        productService.deleteProduct(Long.valueOf(1));
    }

    @Test
    void testGetProductByProductname() {
        // Arrange
        List<Product> productList = Arrays.asList(new Product(), new Product());
        String productname = "TestProduct";
        Mockito.when(productRepository.findByProductnameContaining(productname)).thenReturn(productList);

        // Act
        List<Product> result = productService.getProductByProductname(productname);

        // Assert
        Assert.assertEquals(productList, result);
    }

    @Test
    void testGetProductByBrandname() {
        // Arrange
        List<Product> productList = Arrays.asList(new Product(), new Product());
        String brandname = "TestBrand";
        Mockito.when(productRepository.findByBrandname(brandname)).thenReturn(productList);

        // Act
        List<Product> result = productService.getProductByBrandname(brandname);

        // Assert
        Assert.assertEquals(productList, result);
    }

    @Test
    void testGetProductPriceHistory() {
        // Arrange
        Long productId = 1L;
        Product mockProduct = new Product(productId, "productname", "brandname", 29.99);
        List<PriceHistory> priceHistoryList = Arrays.asList(new PriceHistory(), new PriceHistory());
        mockProduct.setPriceHistoryList(priceHistoryList);

        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

        // Act
        List<PriceHistory> result = productService.getProductPriceHistory(productId);

        // Assert
        Assert.assertEquals(priceHistoryList, result);
    }

    @Test
    void testAddPriceHistoryToProduct() {
        // Arrange
        Long productId = 1L;
        Long priceHistoryId = 2L;
        Product mockProduct = new Product(productId, "productname", "brandname", 29.99);
        PriceHistory priceHistory = new PriceHistory(priceHistoryId, 19.99, mockProduct.getCreateDate(), mockProduct);

        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));
        Mockito.when(priceHistoryRepository.save(priceHistory)).thenReturn(priceHistory);

        // Act
        PriceHistory result = productService.addPriceHistoryToProduct(productId, priceHistory);

        // Assert
        Assert.assertEquals(priceHistory, result);
        Assert.assertTrue(mockProduct.getPriceHistoryList().contains(priceHistory));
    }

    @Test
    void testDeletePriceHistoryFromProduct() {
        // Arrange
        Long productId = 1L;
        Long priceHistoryId = 2L;
        Product mockProduct = new Product(productId, "productname", "brandname", 29.99);
        PriceHistory priceHistory = new PriceHistory(priceHistoryId, 19.99, mockProduct.getCreateDate(), mockProduct);
        mockProduct.getPriceHistoryList().add(priceHistory);

        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

        // Act
        productService.deletePriceHistoryFromProduct(productId, priceHistoryId);

        // Assert
        Assert.assertFalse(mockProduct.getPriceHistoryList().contains(priceHistory));
    }

    @Test
    void testSubmitNewPrice() {
        // Arrange
        Long productId = 1L;
        double newPrice = 19.99;

        Product existingProduct = new Product(productId, "productname", "brandname", 29.99);
        existingProduct.setLowestPrice(29.99);

        PriceHistory newPriceHistory = new PriceHistory(1L, newPrice, existingProduct.getCreateDate(), existingProduct);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(priceHistoryRepository.save(any(PriceHistory.class))).thenReturn(newPriceHistory);
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

        // Act
        Product result = productService.submitNewPrice(productId, newPrice);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(existingProduct, result); // Check if result is the same object as existingProduct
        Assertions.assertEquals(newPrice, result.getCurrentPrice(), 0.001); // Check if currentPrice is updated
        Assertions.assertEquals(newPrice, result.getLowestPrice(), 0.001); // Check if lowestPrice is updated
//        Assertions.assertTrue(result.getPriceHistoryList().contains(newPriceHistory)); // Check if the newPriceHistory is added to priceHistoryList
    }


    @Test
    void testAddUserWatchesProduct() {
        // Arrange
        Long userId = 1L;
        Long productId = 2L;
        User user = new User("Username", "TestUser");
        user.setWatchedProducts(new HashSet<>());
        Product product = new Product("Productname", "TestProduct", 29.99);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);

        // Act
        productService.addUserWatchesProduct(userId, productId);

        // Assert
        Assertions.assertTrue(user.getWatchedProducts().contains(product));
        Assertions.assertTrue(product.getWatchers().contains(user));
    }

    @Test
    void testRemoveUserWatchesProduct() {
        // Arrange
        Long userId = 1L;
        Long productId = 2L;
        User user = new User("Username", "TestUser");
        Product product = new Product("Productname", "TestProduct", 29.99);
        user.addWatchedProduct(product);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);

        // Act
        productService.removeUserWatchesProduct(userId, productId);

        // Assert
        Assertions.assertFalse(user.getWatchedProducts().contains(product));
    }

    @Test
    void testIsUserWatchingProduct() {
        // Arrange
        Long userId = 1L;
        Long productId = 2L;
        User user = new User("Username", "TestUser");
        Product product = new Product("Productname", "TestProduct", 29.99);
        user.addWatchedProduct(product);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Act
        boolean isWatching = productService.isUserWatchingProduct(userId, productId);

        // Assert
        Assertions.assertTrue(isWatching);
    }

//    @Test
//    public void testSendLowestPriceUpdateEmails() {
//        // 创建一个模拟产品
//        Product product = new Product("Productname", "Brandname", 29.99);
//
//        // 创建一个模拟用户
//        User user = new User("Username", "TestUser");
//        user.setWatchedProducts(new HashSet<>());
//        user.setEmail("user@example.com");
//
//        Set<User> watchers = new HashSet<>();
//        watchers.add(user);
//        Set<Product> watchedProduct = new HashSet<>();
//        watchedProduct.add(product);
//
//        // 设置模拟用户关注的产品
//        user.setWatchedProducts(watchedProduct);
//
//        // 模拟 productRepository 返回关注了产品的用户
//        Mockito.when(productRepository.findUsersWatchingProduct(product)).thenReturn(watchers);
//
//        // 调用被测试方法
//        productService.sendLowestPriceUpdateEmails(product, 9.99);
//
//        // 验证邮件是否被发送
//        SimpleMailMessage expectedMessage = new SimpleMailMessage();
//        expectedMessage.setTo("user@example.com");
//        expectedMessage.setSubject("LowestPrice Update for Productname");
//        expectedMessage.setText("The newLowestPrice for Productname has been updated to 9.99");
//
//        Mockito.verify(javaMailSender).send(expectedMessage);
//    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: https://weirddev.com/forum#!/testme