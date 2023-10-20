package com.nus.dealhunter.controller;

import com.nus.dealhunter.model.CustomUserDetails;
import com.nus.dealhunter.model.User;
import com.nus.dealhunter.payload.request.AdminCreateRequest;
import com.nus.dealhunter.payload.request.LoginRequest;
import com.nus.dealhunter.payload.request.SignupRequest;
import com.nus.dealhunter.payload.request.UserEmailModifyRequest;
import com.nus.dealhunter.payload.request.UserPasswordModifyRequest;
import com.nus.dealhunter.payload.response.JwtAuthenticationResponse;
import com.nus.dealhunter.service.UserService;
import com.nus.dealhunter.util.JwtTokenUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

//  @Autowired
//  private MockMvc mockMvc;

  @Mock
  UserService userService;

  @Mock
  AuthenticationManager authenticationManager;

  @Mock
  JwtTokenUtil jwtTokenUtil;

  @InjectMocks
  UserController userController;

  @Test
  void login(){
    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setUsername("string");
    loginRequest.setPassword("string");
    User user = new User();
    user.setUsername("string");
    Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
    when(userService.getUser(loginRequest.getUsername())).thenReturn(user);
    when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        loginRequest.getUsername(),
        loginRequest.getPassword()
    ))).thenReturn(authentication);
    when(jwtTokenUtil.generateToken(authentication)).thenReturn("aaa");
    ResponseEntity<JwtAuthenticationResponse> login = userController.login(loginRequest);
    Assertions.assertEquals(login.getBody().getUsername(), "string");
  }

  @Test
  void register() {
    SignupRequest signupRequest1 = new SignupRequest();
    signupRequest1.setUsername("string1");
    SignupRequest signupRequest2 = new SignupRequest();
    signupRequest1.setUsername("string2");

    when(userService.checkUserNameExists(signupRequest1.getUsername())).thenReturn(true);
    Assertions.assertFalse(userController.register(signupRequest1).getBody().getSuccess());
    when(userService.checkUserNameExists(signupRequest2.getUsername())).thenReturn(false);
    when(userService.createUser(signupRequest2)).thenReturn(new User());
    Assertions.assertTrue(userController.register(signupRequest2).getBody().getSuccess());

  }

  @Test
  void modifyPassword() {
    UserPasswordModifyRequest userPasswordModifyRequest1 = new UserPasswordModifyRequest();
    CustomUserDetails customUserDetails1 = new CustomUserDetails(1L, "a", "a");
    when(userService.modifyUserPassword(userPasswordModifyRequest1, customUserDetails1)).thenReturn(null);
    Assertions.assertFalse(userController.modifyPassword(userPasswordModifyRequest1, customUserDetails1).getBody().getSuccess());
    UserPasswordModifyRequest userPasswordModifyRequest2 = new UserPasswordModifyRequest();
    CustomUserDetails customUserDetails2 = new CustomUserDetails(2L, "b", "b");
    User user = new User();
    when(userService.modifyUserPassword(userPasswordModifyRequest2, customUserDetails2)).thenReturn(user);
    Assertions.assertTrue(userController.modifyPassword(userPasswordModifyRequest2, customUserDetails2).getBody().getSuccess());
  }

  @Test
  void modifyEmail() {
    UserEmailModifyRequest userPasswordModifyRequest1 = new UserEmailModifyRequest();
    CustomUserDetails customUserDetails1 = new CustomUserDetails(1L, "a", "a");
    when(userService.modifyUserEmail(userPasswordModifyRequest1, customUserDetails1)).thenReturn(null);
    Assertions.assertFalse(userController.modifyEmail(userPasswordModifyRequest1, customUserDetails1).getBody().getSuccess());
    UserEmailModifyRequest userPasswordModifyRequest2 = new UserEmailModifyRequest();
    CustomUserDetails customUserDetails2 = new CustomUserDetails(2L, "b", "b");
    User user = new User();
    when(userService.modifyUserEmail(userPasswordModifyRequest2, customUserDetails2)).thenReturn(user);
    Assertions.assertTrue(userController.modifyEmail(userPasswordModifyRequest2, customUserDetails2).getBody().getSuccess());
  }

  @Test
  void createAdmin() {
    AdminCreateRequest adminCreateRequest = new AdminCreateRequest();
    adminCreateRequest.setUsername("aaa");
    when(userService.checkUserNameExists(adminCreateRequest.getUsername())).thenReturn(true);
    CustomUserDetails customUserDetails1 = new CustomUserDetails(1L, "a", "a");
    Assertions.assertFalse(userController.createAdmin(adminCreateRequest, customUserDetails1).getBody().getSuccess());
    when(userService.checkUserNameExists(adminCreateRequest.getUsername())).thenReturn(false);
    when(userService.createAdminUser(adminCreateRequest)).thenReturn(null);
    Assertions.assertFalse(userController.createAdmin(adminCreateRequest, customUserDetails1).getBody().getSuccess());
    when(userService.createAdminUser(adminCreateRequest)).thenReturn(new User());
    Assertions.assertTrue(userController.createAdmin(adminCreateRequest, customUserDetails1).getBody().getSuccess());

  }
}