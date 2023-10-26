package com.nus.dealhunter.observer;


import com.nus.dealhunter.model.Product;
import com.nus.dealhunter.model.User;

import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

public class ProductSubject implements Subject {

    private Long product_id; // 保存对应的产品

    @OneToMany(mappedBy = "product")
    private List<UserObserver> userObservers = new ArrayList<>();


    public void setProduct(Long product_id) {
        this.product_id = product_id;
    }

    public Long getProductId() {
        return product_id;
    }




    @Override
    public void addUserObserver(UserObserver userObserver) {
        userObservers.add(userObserver);
    }

    @Override
    public void removeUserObserver(UserObserver userObserver) {
        userObservers.remove(userObserver);
    }

    @Override
    public void notifyUserObservers(Product product, User user, double newLowestPrice) {
        for (UserObserver userObserver : userObservers) {
            // 调用观察者的 update 方法，将 product 和 newLowestPrice 传递给它
            userObserver.update(product, user, newLowestPrice);
        }
    }


    public ProductSubject(Long product_id) {
        this.product_id = product_id;
    }

    public ProductSubject() {
    }
}
