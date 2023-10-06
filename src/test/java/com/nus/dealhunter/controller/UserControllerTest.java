package com.nus.dealhunter.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nus.dealhunter.payload.request.LoginRequest;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void login() throws Exception {
    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setUsername("string");
    loginRequest.setPassword("string");
    String loginRequestString = new ObjectMapper().writeValueAsString(loginRequest);

    mockMvc.perform(post("/api/user/login")
            .content(loginRequestString)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void register() {
  }

  @Test
  void modifyPassword() {
  }

  @Test
  void modifyEmail() {
  }

  @Test
  void createAdmin() {
  }
}