package com.nus.dealhunter.service;

import com.nus.dealhunter.model.Brand;
import com.nus.dealhunter.model.Product;
import com.nus.dealhunter.repository.BrandRepository;
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

    @Autowired
    private BrandRepository brandRepository;

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

    /**save and update*/
    public Product saveProduct(Product product) {
        try {

            // Check if the brand associated with the product exists
            if (product.getBrand().getId() != null && brandRepository.findById(product.getBrand().getId()).isEmpty()){
                // If the brand doesn't have an ID, it means it's a new brand, so need save it
                Brand savedBrand = brandRepository.save(product.getBrand());
                // Set the saved brand to the product
                product.setBrand(savedBrand);
            }
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
