package com.nus.dealhunter.observer;


import com.nus.dealhunter.model.User;
import com.nus.dealhunter.model.Product;
import com.nus.dealhunter.service.EmailService;

public class UserObserver implements Observer {
    private EmailService emailService;

    private Long user_id;

    private Long product_id;

    public UserObserver(EmailService emailService) {
        this.emailService = emailService;
    }

    public UserObserver(EmailService emailService,Long user_id) {
        this.emailService = emailService;
    }

    public UserObserver(Long user_id) {
        this.user_id = user_id;
    }

    public UserObserver(Long user_id, Long product_id) {
        this.user_id = user_id;
        this.product_id = product_id;
    }

    public UserObserver(Long user_id, Long product_id, EmailService emailService) {
        this.user_id = user_id;
        this.product_id = product_id;
        this.emailService = emailService;
    }

    public void setUser_id(Long user_Id) {
        this.user_id = user_Id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public Long getProductr_id() {
        return product_id;
    }

    @Override
    public void update(Product product, User user, double newLowestPrice) {
        // 这里可以调用 EmailService 来发送邮件
        String userEmail = user.getEmail();
        String subject = "Lowest Price Update for " + product.getProductname();
        String messageText = "The new lowest price for " + product.getProductname() + " has been updated to " + newLowestPrice;

        emailService.sendEmail(userEmail, subject, messageText);
    }

    public UserObserver() {
    }

}
