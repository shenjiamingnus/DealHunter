package com.nus.dealhunter.repository;


import com.nus.dealhunter.model.PriceHistory;
import com.nus.dealhunter.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Long> {

    // 查询指定商品在指定日期范围内的价格历史记录
    List<PriceHistory> findByProductIdAndDateBetween(Long productId, LocalDate startDate, LocalDate endDate);

    // 查询指定商品的所有价格历史记录
    List<PriceHistory> findByProductId(Long productId);

//    // 查询指定ID价格历史记录
//    Optional<PriceHistory> findById(Long Id);


}
