package com.nus.dealhunter.service;


import com.nus.dealhunter.exception.PriceHistoryServiceException;
import com.nus.dealhunter.exception.ProductServiceException;
import com.nus.dealhunter.model.PriceHistory;
import com.nus.dealhunter.model.Product;
import com.nus.dealhunter.repository.PriceHistoryRepository;
import com.nus.dealhunter.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PriceHistoryService {
    @Autowired
    private PriceHistoryRepository priceHistoryRepository;

    @Autowired
    private ProductRepository productRepository;



    public void savePriceHistory(PriceHistory priceHistory) {
        priceHistoryRepository.save(priceHistory);
    }

    public PriceHistory createPriceHistory(PriceHistory priceHistory) {
        return priceHistoryRepository.save(priceHistory);
    }

    public void deletePriceHistory(Long priceHistoryId) {
        priceHistoryRepository.deleteById(priceHistoryId);
    }

    public PriceHistory updatePriceHistory(PriceHistory priceHistory) {
        return priceHistoryRepository.save(priceHistory);
    }

    public List<PriceHistory> getPriceHistoryByProductId(Long productId) {
        return priceHistoryRepository.findByProductId(productId);
    }



    public List<PriceHistory> getPriceHistory(long id) throws PriceHistoryServiceException{
        try {
            return priceHistoryRepository.findByProductId(id);
        }catch (Exception e){
            throw new PriceHistoryServiceException("Failed to retrieve product with ID: " + id, e);
        }
    }





    public List<PriceHistory> viewHistoricalPriceTrends(Long Id, LocalDate startDate, LocalDate endDate) {
        try {
            Optional<Product> optionalProduct = productRepository.findById(Id);
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                // 查询价格历史记录，并根据提供的日期范围过滤数据
                List<PriceHistory> priceHistoryList = priceHistoryRepository.findByProductIdAndDateBetween(Id, startDate, endDate);
                return priceHistoryList;
            } else {
                throw new ProductServiceException("Product with ID " + Id + " not found");
            }
        } catch (Exception e) {
            throw new ProductServiceException("Failed to view historical price trends for product with ID " + Id, e);
        }
    }




}
