package com.nus.dealhunter.controller;

import com.nus.dealhunter.exception.ProductServiceException;
import com.nus.dealhunter.model.PriceHistory;
import com.nus.dealhunter.service.PriceHistoryService;
import com.nus.dealhunter.service.ProductService;
import com.nus.dealhunter.util.JwtTokenUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @Autowired
    JwtTokenUtil jwtTokenUtil;



    @PostMapping("/create")
    public ResponseEntity<PriceHistory> savePriceHistory(@RequestBody PriceHistory priceHistory) {
        PriceHistory savedPriceHistory = priceHistoryService.savePriceHistory(priceHistory);
        return new ResponseEntity<>(savedPriceHistory, HttpStatus.CREATED);
    }

    @DeleteMapping("/remove/{priceHistoryId}")
    public ResponseEntity<Void> removePriceHistory(@PathVariable Long priceHistoryId) {
        priceHistoryService.deletePriceHistory(priceHistoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<PriceHistory>> getPriceHistoryByProductId(@PathVariable Long productId) {
        List<PriceHistory> priceHistoryList = priceHistoryService.getPriceHistoryByProductId(productId);
        return new ResponseEntity<>(priceHistoryList, HttpStatus.OK);
    }

    @PostMapping("/product/{productId}/add")
    public ResponseEntity<PriceHistory> addPriceHistoryToProduct(
            @PathVariable Long productId,
            @RequestBody PriceHistory priceHistory) {
        try {
            PriceHistory addedPriceHistory = productService.addPriceHistoryToProduct(productId, priceHistory);
            return new ResponseEntity<>(addedPriceHistory, HttpStatus.CREATED);
        } catch (ProductServiceException e) {
            // 处理异常并返回适当的响应
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{Id}/price-history")
    public ResponseEntity<List<PriceHistory>> viewHistoricalPriceTrends(
            @PathVariable Long Id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<PriceHistory> priceHistoryList = priceHistoryService.viewHistoricalPriceTrends(Id, startDate, endDate);
        return ResponseEntity.ok(priceHistoryList);
    }





}
