package com.nus.dealhunter.service;

import com.nus.dealhunter.model.Brand;
import com.nus.dealhunter.model.PriceHistory;
import com.nus.dealhunter.model.Product;
import com.nus.dealhunter.payload.request.CreateProductRequest;
import com.nus.dealhunter.payload.request.UpdateProductRequest;
import com.nus.dealhunter.repository.PriceHistoryRepository;
import com.nus.dealhunter.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.nus.dealhunter.exception.ProductServiceException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PriceHistoryRepository priceHistoryRepository;

    public Boolean checkProductNameExists(String productname) {
        return productRepository.existsByProductname(productname);
    }


    public List<Product> getAllProducts(){
        try {
            return productRepository.findAll();
        }catch (Exception e){
            throw new ProductServiceException("Failed to retrieve all product ", e);
        }

    }
    public Optional<Product> getProductById(Long id) throws ProductServiceException {
        try {
            return productRepository.findById(id);
        }catch (Exception e){
            throw new ProductServiceException("Failed to retrieve product with ID: " + id, e);
        }

    }

    public List<Product> getProductByProductname(String productname) {
        try {
            return productRepository.findByProductname(productname);
        }catch (Exception e){
            throw new ProductServiceException("Failed to retrieve product with productname: " + productname, e);
        }
    }

    public List<Product> getProductByBrandname(String brandname){
        try {
            return productRepository.findByBrandname(brandname);
        }catch (Exception e){
            throw new ProductServiceException("Failed to retrieve product with Brandname: " + brandname, e);
        }
    }


    public Product createProduct(CreateProductRequest createProductRequest){
        if (createProductRequest == null) {
            return null;
        }
        Product product = new Product();
        product.setProductname(createProductRequest.getProductname());
        product.setBrandname(createProductRequest.getBrandname());
        product.setStoreAddress(createProductRequest.getStoreAddress());
        product.setDiscription(createProductRequest.getDescription());
        product.setImageUrl(createProductRequest.getImageUrl());
        product.setCurrentPrice(createProductRequest.getCurrentPrice());
        product.setLowestPrice(createProductRequest.getCurrentPrice());
        product.setCreateDate(Instant.now());
        //product.setBrand(new Brand(createProductRequest.getBrand_id(),createProductRequest.getBrandname()));
        if (createProductRequest.getBrand_id() != null && createProductRequest.getBrandname() != null) {
            product.setBrand(new Brand(createProductRequest.getBrand_id(), createProductRequest.getBrandname()));
        }
        return productRepository.save(product);
    }



    public Product updateProduct(UpdateProductRequest updateProductRequest){
        Product product = new Product();
        product.setId(updateProductRequest.getProduct_id());
        product.setProductname(updateProductRequest.getProductname());
        product.setBrandname(updateProductRequest.getBrandname());
        product.setStoreAddress(updateProductRequest.getStoreAddress());
        product.setDiscription(updateProductRequest.getDescription());
        product.setImageUrl(updateProductRequest.getImageUrl());
        product.setCurrentPrice(updateProductRequest.getCurrentPrice());
        //如现价低于最低价，更新最低价
        if(updateProductRequest.getCurrentPrice()<updateProductRequest.getLowestPrice()){
            product.setLowestPrice(updateProductRequest.getCurrentPrice());
        }else {
            product.setLowestPrice(updateProductRequest.getLowestPrice());
        }
        product.setCreateDate(Instant.now());
        product.setBrand(new Brand(updateProductRequest.getBrand_id(),updateProductRequest.getBrandname()));
        return productRepository.save(product);
    }




    public void deleteProduct(Long id) {
        try {
            productRepository.deleteById(id);
        }catch (Exception e){
            throw new ProductServiceException("Failed to delete product", e);
        }

    }

    public List<PriceHistory> getProductPriceHistory(String productname, String brandname) {
        try {
            Optional<Product> optionalProduct = productRepository.findByProductnameAndBrandname(productname, brandname);
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                // 获取产品的历史价格列表
                List<PriceHistory> priceHistoryList = product.getPriceHistoryList();
                return priceHistoryList;
            } else {
                throw new ProductServiceException("Product with productname " + productname + " and brandname " + brandname + " not found");
            }
        } catch (Exception e) {
            throw new ProductServiceException("Failed to submit new price for product with productname " + productname + " and brandname " + brandname, e);
        }
    }

    // 向商品的价格历史记录列表添加一条记录
    public PriceHistory addPriceHistoryToProduct(Long productId, PriceHistory priceHistory) throws ProductServiceException {
        try {
            Optional<Product> optionalProduct = productRepository.findById(productId);
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                priceHistory.setProduct(product);                       // 关联到商品
                PriceHistory savedPriceHistory = priceHistoryRepository.save(priceHistory);
                product.getPriceHistoryList().add(savedPriceHistory);   // 添加到商品的价格历史记录列表
                productRepository.save(product);                        // 更新商品对象，以保存关联的价格历史记录
                return savedPriceHistory;
            } else {
                throw new ProductServiceException("Product with ID " + productId + " not found");
            }
        } catch (Exception e) {
            throw new ProductServiceException("Failed to add price history to product with ID " + productId, e);
        }
    }


    public Product submitNewPrice(String productname, String brandname, double newPrice) {
        try {
            // 根据 productname 和 brandname 查找产品
            Optional<Product> optionalProduct = productRepository.findByProductnameAndBrandname(productname, brandname);
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();

                // 更新当前价格
                product.setCurrentPrice(newPrice);

                // 如果新价格低于历史最低价或历史最低价为0，更新历史最低价
                if (newPrice < product.getLowestPrice() || product.getLowestPrice() == 0) {
                    product.setLowestPrice(newPrice);
                }

                List<PriceHistory> priceHistoryList = product.getPriceHistoryList();
                if (priceHistoryList == null) {
                    priceHistoryList = new ArrayList<>();
                }

                // 创建新的价格历史记录对象
                PriceHistory newPriceHistory = new PriceHistory(newPrice, LocalDate.now(), product);

                // 将新的价格历史记录添加到历史价格列表中
                priceHistoryList.add(newPriceHistory);

                // 保存新的价格历史记录到数据库
                priceHistoryRepository.save(newPriceHistory);

                // 保存更新后的产品对象到数据库
                return productRepository.save(product);
            } else {
                throw new ProductServiceException("Product with productname " + productname + " and brandname " + brandname + " not found");
            }
        } catch (Exception e) {
            throw new ProductServiceException("Failed to submit new price for product with productname " + productname + " and brandname " + brandname, e);
        }
    }



}









