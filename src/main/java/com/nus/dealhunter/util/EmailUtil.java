package com.nus.dealhunter.util;

import com.nus.dealhunter.model.Product;
import com.nus.dealhunter.model.User;
import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {

  @Autowired
  private JavaMailSender mailSender;

  private static JavaMailSender javaMailSender;

  @PostConstruct
  public void init() {
    javaMailSender = mailSender;
  }

  @Value("${spring.mail.username}")
  public void setHost(String from) {
    EmailUtil.from = from;
  }

  private static String from;

  public static boolean sendEmail(User user, Product product, double newLowestPrice){
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage);
    try {
      mimeMessageHelper.setFrom(from);
      mimeMessageHelper.setTo(user.getEmail());
      mimeMessageHelper.setSubject("LowestPrice Update for " + product.getProductname());
      mimeMessageHelper.setText("The newLowestPrice for " + product.getProductname() + " has been updated to " + newLowestPrice,true);
      javaMailSender.send(mimeMessageHelper.getMimeMessage());
    } catch (Exception e) {
      return false;
    }
    return true;
  }

}
