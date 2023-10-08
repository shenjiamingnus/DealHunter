package com.nus.dealhunter.controller;

import com.nus.dealhunter.exception.ProductServiceException;
import com.nus.dealhunter.model.PriceHistory;
import com.nus.dealhunter.service.PriceHistoryService;
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
    private PriceHistoryService priceHistoryService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @GetMapping("/{Id}/price-history")
    public ResponseEntity<List<PriceHistory>> viewHistoricalPriceTrends(
            @PathVariable Long Id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<PriceHistory> priceHistoryList = priceHistoryService.viewHistoricalPriceTrends(Id, startDate, endDate);
        return ResponseEntity.ok(priceHistoryList);
    }





}
