package com.nus.dealhunter.builder;

import com.nus.dealhunter.model.Product;
import java.time.Instant;

public class ProductBuilder implements Builder{

  protected Product product;

  public void createProduct() {
    product = new Product();
  }

  public void buildBrandName(String brandname){
    product.setBrandName(brandname);
  }

  public void buildProductName(String productname){
    product.setProductName(productname);
  }

  public void buildStoreAddress(String soreAddress){
    product.setStoreAddress(soreAddress);
  }

  public void buildImageUrl(String imageUrl){
    product.setImageUrl(imageUrl);
  }

  public void buildCurrentPrice(Double currentPrice){
    product.setCurrentPrice(currentPrice);
  }

  public void buildLowestPrice(Double lowestPrice){
    product.setLowestPrice(lowestPrice);
  }

  public void buildCreateDate(){
    product.setCreateDate(Instant.now());
  }

  public void buildDescription(String description){
    product.setDescription(description);
  }

  public Product getProduct() {
    return product;
  }
}
