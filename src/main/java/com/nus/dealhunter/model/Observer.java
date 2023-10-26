package com.nus.dealhunter.model;

import com.nus.dealhunter.model.Product;

public interface Observer {

  public boolean update(Product product, double newLowestPrice);
}
