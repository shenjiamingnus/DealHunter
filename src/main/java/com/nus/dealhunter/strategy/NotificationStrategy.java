package com.nus.dealhunter.strategy;

import com.nus.dealhunter.model.Product;
import com.nus.dealhunter.model.User;

public interface NotificationStrategy {

  boolean notify(User user, Product product, double newLowestPrice);

}
