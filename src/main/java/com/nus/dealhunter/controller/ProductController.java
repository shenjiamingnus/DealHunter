package com.nus.dealhunter.controller;

import com.nus.dealhunter.service.UserService;
import com.nus.dealhunter.util.JwtTokenUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api("Product/")
@RestController
@RequestMapping("/api/product")

public class ProductController {


    @Autowired
    UserService productService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;



}
