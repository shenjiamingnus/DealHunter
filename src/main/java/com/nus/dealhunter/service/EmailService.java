package com.nus.dealhunter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    //发送价格更新邮件给关注了产品的用户
    public void sendEmail(String to, String subject, String text) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            // 发送邮件
            javaMailSender.send(message);

        //        // 获取关注了该产品的用户列表
//        Set<User> watchers = productRepository.findUsersWatchingProduct(product);
//
//        for (User user : watchers) {
//            // 创建邮件内容
//            SimpleMailMessage message = new SimpleMailMessage();
////            message.setFrom("619176497@qq.com");
//            message.setTo(user.getEmail());
//            message.setSubject("LowestPrice Update for " + product.getProductname());
//            message.setText("The newLowestPrice for " + product.getProductname() + " has been updated to " + newLowestPrice);
    }
}




