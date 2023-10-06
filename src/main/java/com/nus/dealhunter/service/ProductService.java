package com.nus.dealhunter.service;

import com.nus.dealhunter.model.Product;
import com.nus.dealhunter.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.nus.dealhunter.exception.ProductServiceException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

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

    public Product saveProduct(Product product) {
        try {
            return productRepository.save(product);
        }catch (Exception e){
            throw new ProductServiceException("Failed to save product", e);
        }

    }

    public void deleteProduct(Long id) {
        try {
            productRepository.deleteById(id);
        }catch (Exception e){
            throw new ProductServiceException("Failed to delete product", e);
        }

    }





}
