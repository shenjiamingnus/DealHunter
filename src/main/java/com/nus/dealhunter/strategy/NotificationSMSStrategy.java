package com.nus.dealhunter.strategy;

import com.nus.dealhunter.model.Product;
import com.nus.dealhunter.model.User;

public class NotificationSMSStrategy implements NotificationStrategy{

  @Override
  public boolean notify(User user, Product product, double newLowestPrice) {
    return false;
  }
}
