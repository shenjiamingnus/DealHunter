package com.nus.dealhunter.service;

import com.nus.dealhunter.model.PriceHistory;
import com.nus.dealhunter.model.Product;
import com.nus.dealhunter.repository.PriceHistoryRepository;
import com.nus.dealhunter.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.nus.dealhunter.exception.ProductServiceException;
import org.springframework.stereotype.Service;

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

    //when create a product, make LowestPrice = CurrentPrice
    public Product saveProduct(Product product) {
        try {
            product.setLowestPrice(product.getCurrentPrice());
            return productRepository.save(product);
        }catch (Exception e){
            throw new ProductServiceException("Failed to save product", e);
        }
    }

    public Product modifyProductname(Long id, String newProductname) {
        try {
            Optional<Product> existingProduct = productRepository.findById(id);
            if (existingProduct.isPresent()) {
                Product product = existingProduct.get();
                product.setProductname(newProductname);
                return productRepository.save(product);
            }
            return null;
        } catch (Exception e) {
            throw new ProductServiceException("Failed to modify productname", e);
        }
    }

    public Product modifyStoreAddress(Long id, String newStoreAddress) {
        try {
            Optional<Product> existingProduct = productRepository.findById(id);
            if (existingProduct.isPresent()) {
                Product product = existingProduct.get();
                product.setStoreAddress(newStoreAddress);
                return productRepository.save(product);
            }
            return null;
        } catch (Exception e) {
            throw new ProductServiceException("Failed to modify storeAddress", e);
        }
    }

    public Product modifyDiscription(Long id, String newDiscription) {
        try {
            Optional<Product> existingProduct = productRepository.findById(id);
            if (existingProduct.isPresent()) {
                Product product = existingProduct.get();
                product.setDiscription(newDiscription);
                return productRepository.save(product);
            }
            return null;
        } catch (Exception e) {
            throw new ProductServiceException("Failed to modify discription", e);
        }
    }

    public Product modifyImageUrl(Long id, String newImageUrl) {
        try {
            Optional<Product> existingProduct = productRepository.findById(id);
            if (existingProduct.isPresent()) {
                Product product = existingProduct.get();
                product.setImageUrl(newImageUrl);
                return productRepository.save(product);
            }
            return null;
        } catch (Exception e) {
            throw new ProductServiceException("Failed to modify image", e);
        }
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









