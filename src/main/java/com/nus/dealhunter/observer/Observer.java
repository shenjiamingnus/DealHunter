package com.nus.dealhunter.observer;


import com.nus.dealhunter.model.Product;
import com.nus.dealhunter.model.User;

public interface Observer {
    void update(Product product, User user, double newLowestPrice);
}



