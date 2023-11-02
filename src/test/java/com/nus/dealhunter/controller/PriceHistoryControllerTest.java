package com.nus.dealhunter.controller;

import com.nus.dealhunter.exception.PriceHistoryServiceException;
import com.nus.dealhunter.model.PriceHistory;
import com.nus.dealhunter.model.Product;
import com.nus.dealhunter.payload.request.CreatePriceHistoryRequest;
import com.nus.dealhunter.payload.response.GeneralApiResponse;
import com.nus.dealhunter.service.PriceHistoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class PriceHistoryControllerTest {

    @Mock
    private PriceHistoryService priceHistoryService;

    @InjectMocks
    PriceHistoryController priceHistoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePriceHistory() {
        // 模拟 PriceHistoryService 的行为
        CreatePriceHistoryRequest createPriceHistoryRequest = new CreatePriceHistoryRequest(20.0, Instant.now(), 1L);
        when(priceHistoryService.createPriceHistory(createPriceHistoryRequest)).thenReturn(new PriceHistory());

        // 调用控制器方法
        ResponseEntity<GeneralApiResponse> response = priceHistoryController.createPriceHistory(createPriceHistoryRequest);

        // 验证返回结果
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertTrue(response.getBody().getSuccess());
        Assertions.assertEquals("PriceHistory created!", response.getBody().getMessage());

        // 验证 PriceHistoryService 被调用
        verify(priceHistoryService, times(1)).createPriceHistory(createPriceHistoryRequest);
    }

    @Test
    public void testRemovePriceHistory() {
        ResponseEntity<Void> result = priceHistoryController.removePriceHistory(Long.valueOf(1));
        Assertions.assertEquals(null, result.getBody());
    }

    @Test
    public void testGetPriceHistoryByProductId() {
        // 创建模拟的 Product 对象和 PriceHistory 对象
        Product product1 = new Product("productname","brandname",0d);
        PriceHistory priceHistory1 = new PriceHistory(Long.valueOf(1),0d, Instant.now(), product1);

        // 模拟 priceHistoryService.getPriceHistoryByProductId 方法的行为
        List<PriceHistory> priceHistoryList = new ArrayList<>();

        when(priceHistoryService.getPriceHistoryByProductId(anyLong())).thenReturn(priceHistoryList);

        // 测试 Controller
        ResponseEntity<List<PriceHistory>> result = priceHistoryController.getPriceHistoryByProductId(Long.valueOf(1));

        // 断言
        Assertions.assertEquals(priceHistoryList, result.getBody());
    }

    @Test
    void testViewHistoricalPriceTrends() {
        // 创建模拟的 Product 对象和 PriceHistory 对象
        Product product1 = new Product("productname","brandname",0d);
        PriceHistory priceHistory1 = new PriceHistory(Long.valueOf(1),0d, Instant.now(), product1);
        Instant createTime1 = priceHistory1.getCreateDate();

        // 模拟 priceHistoryService.getPriceHistoryByProductId 方法的行为
        List<PriceHistory> priceHistoryList = new ArrayList<>();
        priceHistoryList.add(priceHistory1);

        // 模拟 viewHistoricalPriceTrends 方法的行为
        when(priceHistoryService.viewHistoricalPriceTrends(anyLong(), any(Instant.class), any(Instant.class))).thenReturn(priceHistoryList);

        // 测试 Controller
        ResponseEntity<List<PriceHistory>> result = priceHistoryController.viewHistoricalPriceTrends(Long.valueOf(1), createTime1,Instant.now());

        // 断言
        Assertions.assertEquals(priceHistoryList, result.getBody());
    }


}



