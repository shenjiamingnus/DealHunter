package com.nus.dealhunter.util;

import com.nus.dealhunter.model.Product;
import com.nus.dealhunter.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {

  @Autowired
  private static JavaMailSender javaMailSender;

  public static boolean sendEmail(User user, Product product, double newLowestPrice){
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(user.getEmail());
    message.setSubject("LowestPrice Update for " + product.getProductname());
    message.setText("The newLowestPrice for " + product.getProductname() + " has been updated to " + newLowestPrice);

    // 发送邮件
    try{
      javaMailSender.send(message);
    }catch (Exception e){
      return false;
    }
    return true;
  }

}
