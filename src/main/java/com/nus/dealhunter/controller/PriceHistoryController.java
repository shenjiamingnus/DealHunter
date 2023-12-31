package com.nus.dealhunter.controller;

import com.nus.dealhunter.exception.ProductServiceException;
import com.nus.dealhunter.model.PriceHistory;
import com.nus.dealhunter.payload.request.CreatePriceHistoryRequest;
import com.nus.dealhunter.payload.request.CreateProductRequest;
import com.nus.dealhunter.payload.response.GeneralApiResponse;
import com.nus.dealhunter.service.PriceHistoryService;
import com.nus.dealhunter.service.ProductService;
import com.nus.dealhunter.util.JwtTokenUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

import java.time.LocalDate;
import java.util.Collections;

@Api("PriceHistory/")
@RestController
@RequestMapping("/api/priceHistory")


public class PriceHistoryController {

    @Autowired
    private ProductService productService;

    @Autowired
    private PriceHistoryService priceHistoryService;


    @PostMapping("/price-history/create")
    public ResponseEntity<GeneralApiResponse> createPriceHistory(@RequestBody CreatePriceHistoryRequest createPriceHistoryRequest) {
        PriceHistory savedPriceHistory = priceHistoryService.createPriceHistory(createPriceHistoryRequest);
        if(savedPriceHistory != null){
            return ResponseEntity.ok(new GeneralApiResponse(true,"PriceHistory created!"));
        }else {
            return ResponseEntity.ok(new GeneralApiResponse(false,"PriceHistory failed to created"));
        }
    }

    @DeleteMapping("/price-history/remove/{priceHistoryId}")
    public ResponseEntity<Void> removePriceHistory(@PathVariable Long priceHistoryId) {
        priceHistoryService.deletePriceHistory(priceHistoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/price-history/product/{productId}")
    public ResponseEntity<List<PriceHistory>> getPriceHistoryByProductId(@PathVariable Long productId) {
        List<PriceHistory> priceHistoryList = priceHistoryService.getPriceHistoryByProductId(productId);
        return new ResponseEntity<>(priceHistoryList, HttpStatus.OK);
    }

    @PostMapping("/price-history/product/{productId}/add")
    public ResponseEntity<PriceHistory> addPriceHistoryToProduct(
            @PathVariable Long productId,
            @RequestBody CreatePriceHistoryRequest createPriceHistoryRequest) {
        try {
            PriceHistory newPriceHistory = priceHistoryService.createPriceHistory(createPriceHistoryRequest);
            PriceHistory addedPriceHistory = productService.addPriceHistoryToProduct(productId, newPriceHistory);
            return new ResponseEntity<>(addedPriceHistory, HttpStatus.CREATED);
        } catch (ProductServiceException e) {
            // 处理异常并返回适当的响应
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/price-history/{Id}")
    public ResponseEntity<List<PriceHistory>> viewHistoricalPriceTrends(
            @PathVariable Long Id,
            @RequestParam Instant startDate,
            @RequestParam Instant endDate) {
        List<PriceHistory> priceHistoryList = priceHistoryService.viewHistoricalPriceTrends(Id, startDate, endDate);
        return ResponseEntity.ok(priceHistoryList);
    }



}
