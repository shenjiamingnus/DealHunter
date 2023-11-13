package com.nus.dealhunter.strategy;

import com.nus.dealhunter.model.Product;
import com.nus.dealhunter.model.User;
import com.nus.dealhunter.util.EmailUtil;

public class NotificationEmailStrategy implements NotificationStrategy {

  @Override
  public boolean notify(User user, Product product, double newLowestPrice) {
    EmailUtil.sendEmail(user, product, newLowestPrice);
    return false;
  }
}
