package com.nus.dealhunter.service;


import com.nus.dealhunter.exception.PriceHistoryServiceException;
import com.nus.dealhunter.model.PriceHistory;
import com.nus.dealhunter.model.Product;
import com.nus.dealhunter.payload.request.CreatePriceHistoryRequest;
import com.nus.dealhunter.repository.PriceHistoryRepository;
import com.nus.dealhunter.repository.ProductRepository;
import com.nus.dealhunter.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class PriceHistoryServiceTest {

    @Mock
    PriceHistoryRepository priceHistoryRepository;

    @InjectMocks
    PriceHistoryService priceHistoryService;

    public PriceHistoryServiceTest() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testCreatePriceHistory() {
        CreatePriceHistoryRequest request = new CreatePriceHistoryRequest(39.99, Instant.now(), 1L);

        // 创建模拟的 Product 对象和 PriceHistory 对象
        Product product1 = new Product("productname","brandname",0d);
        PriceHistory priceHistory1 = new PriceHistory(Long.valueOf(1),39.99, Instant.now(), product1);


        Mockito.when(priceHistoryRepository.save(any(PriceHistory.class))).thenReturn(priceHistory1);

        PriceHistory createdPriceHistory = priceHistoryService.createPriceHistory(request);

        Assertions.assertNotNull(createdPriceHistory);
        Assertions.assertEquals(39.99, createdPriceHistory.getPrice(), 0.001);
    }

    @Test
    public void testUpdatePriceHistory() {
        // 创建模拟的 Product 对象和 PriceHistory 对象
        Product product1 = new Product("productname","brandname",0d);
        PriceHistory priceHistory1 = new PriceHistory(Long.valueOf(1),39.99, Instant.now(), product1);

        Mockito.when(priceHistoryRepository.save(any(PriceHistory.class))).thenReturn(priceHistory1);

        PriceHistory updatedPriceHistory = priceHistoryService.updatePriceHistory(priceHistory1);

        Assertions.assertNotNull(updatedPriceHistory);
        Assertions.assertEquals(39.99, updatedPriceHistory.getPrice(), 0.001);
    }

    @Test
    void testDeletePriceHistory() {
        priceHistoryService.deletePriceHistory(Long.valueOf(1));
    }

    @Test
    public void testGetPriceHistoryByProductId() {
        // 创建模拟的 Product 对象和 PriceHistory 对象
        Product product1 = new Product("productname","brandname",0d);
        PriceHistory priceHistory1 = new PriceHistory(Long.valueOf(1),0d, Instant.now(), product1);

        // 模拟 priceHistoryService.getPriceHistoryByProductId 方法的行为
        List<PriceHistory> priceHistoryList = new ArrayList<>();

        when(priceHistoryService.getPriceHistoryByProductId(anyLong())).thenReturn(priceHistoryList);

        List<PriceHistory> priceHistoryList1 = priceHistoryService.getPriceHistoryByProductId(Long.valueOf(1));

        // 断言
        Assertions.assertEquals(priceHistoryList, priceHistoryList1);
    }

    @Test
    public void testViewHistoricalPriceTrendsException() {
        Long productId = 1L;
        Instant startDate = Instant.parse("2023-01-01T00:00:00Z");
        Instant endDate = Instant.parse("2023-02-01T00:00:00Z");

//        List<PriceHistory> priceHistoryList = new ArrayList<>();

        // 模拟 viewHistoricalPriceTrends 方法的行为
        when(priceHistoryService.viewHistoricalPriceTrends(anyLong(), any(Instant.class), any(Instant.class))).thenThrow(new PriceHistoryServiceException("Failed to view historical price trends for product with ID " + 1));

        // 调用被测试的方法，并捕获异常
        PriceHistoryServiceException exception = Assertions.assertThrows(PriceHistoryServiceException.class, () -> {
            priceHistoryService.viewHistoricalPriceTrends(productId, startDate, endDate);
        });

        // 验证异常消息是否符合预期
        Assertions.assertEquals("Failed to view historical price trends for product with ID 1", exception.getMessage());

        // 验证方法是否按预期调用
        Mockito.verify(priceHistoryService, times(1)).viewHistoricalPriceTrends(productId, startDate, endDate);
    }


}
