package com.nus.dealhunter.observer;

import com.nus.dealhunter.model.Product;
import com.nus.dealhunter.model.User;

public interface Subject {

    void addUserObserver(UserObserver userObserver);

    void removeUserObserver(UserObserver userObserver);

    void notifyUserObservers(Product product, User user,double newLowestPrice);
}
