package com.nus.dealhunter.service;


import com.nus.dealhunter.exception.PriceHistoryServiceException;
import com.nus.dealhunter.exception.ProductServiceException;
import com.nus.dealhunter.model.Brand;
import com.nus.dealhunter.model.PriceHistory;
import com.nus.dealhunter.model.Product;
import com.nus.dealhunter.payload.request.CreatePriceHistoryRequest;
import com.nus.dealhunter.repository.PriceHistoryRepository;
import com.nus.dealhunter.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PriceHistoryService {

    @Autowired
    private PriceHistoryRepository priceHistoryRepository;

    @Autowired
    private ProductRepository productRepository;


//    public PriceHistory savePriceHistory(PriceHistory priceHistory) {
//        try {
//            return priceHistoryRepository.save(priceHistory);
//        }catch(Exception e){
//            throw new PriceHistoryServiceException("Failed to create priceHistory ", e);
//        }
//    }
    public PriceHistory createPriceHistory(CreatePriceHistoryRequest createPriceHistoryRequest) {
        if (createPriceHistoryRequest == null) {
            return null;
        }

        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setPrice(createPriceHistoryRequest.getPrice());
        priceHistory.setCreateDate(Instant.now());
        priceHistory.setProduct(new Product(createPriceHistoryRequest.getProduct_id()));

        return priceHistoryRepository.save(priceHistory);
    }


    public PriceHistory updatePriceHistory(PriceHistory priceHistory){
        try {
            return priceHistoryRepository.save(priceHistory);
        } catch (Exception e) {
            throw new PriceHistoryServiceException("Failed to update priceHistory ", e);
        }
    }

    public void deletePriceHistory(Long priceHistoryId) {
        try {
            priceHistoryRepository.deleteById(priceHistoryId);
        }catch(Exception e){
            throw new PriceHistoryServiceException("Failed to retrieve all priceHistory ", e);
        }
    }

    public List<PriceHistory> getPriceHistoryByProductId(Long productId) {
        try {
            return priceHistoryRepository.findByProductId(productId);
        }catch(Exception e){
            throw new PriceHistoryServiceException("Failed to get priceHistory by productId ", e);
        }
    }

    public List<PriceHistory> viewHistoricalPriceTrends(Long Id, Instant startDate, Instant endDate) {
        try {
            Optional<Product> optionalProduct = productRepository.findById(Id);
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                // 查询价格历史记录，并根据提供的日期范围过滤数据
                List<PriceHistory> priceHistoryList = priceHistoryRepository.findByProductIdAndCreateDateBetween(Id, startDate, endDate);
                return priceHistoryList;
            } else {
                throw new ProductServiceException("Product with ID " + Id + " not found");
            }
        } catch (Exception e) {
            throw new ProductServiceException("Failed to view historical price trends for product with ID " + Id, e);
        }
    }


}
