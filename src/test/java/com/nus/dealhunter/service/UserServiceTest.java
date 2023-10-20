package com.nus.dealhunter.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.nus.dealhunter.enums.RoleName;
import com.nus.dealhunter.model.CustomUserDetails;
import com.nus.dealhunter.model.Role;
import com.nus.dealhunter.model.User;
import com.nus.dealhunter.payload.request.AdminCreateRequest;
import com.nus.dealhunter.payload.request.SignupRequest;
import com.nus.dealhunter.payload.request.UserEmailModifyRequest;
import com.nus.dealhunter.payload.request.UserPasswordModifyRequest;
import com.nus.dealhunter.repository.RoleRepository;
import com.nus.dealhunter.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class UserServiceTest {

  @Mock
  RoleRepository roleRepository;

  @Mock
  PasswordEncoder passwordEncoder;

  @Mock
  UserRepository userRepository;

  @InjectMocks
  UserService userService;

  @Test
  void checkUserNameExists() {
    when(userRepository.existsByUsername("aaa")).thenReturn(true);
    Assertions.assertTrue(userService.checkUserNameExists("aaa"));
  }

  @Test
  void getUser() {
    when(userRepository.findByUsername("aaa")).thenReturn(Optional.of(new User()));
    Assertions.assertNotNull(userService.getUser("aaa"));
  }

  @Test
  void testGetUser() {
    when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
    Assertions.assertNotNull(userService.getUser(1L));
  }

  @Test
  void createUser() {
    when(passwordEncoder.encode(anyString())).thenReturn("aaa");
    SignupRequest signupRequest = new SignupRequest();
    signupRequest.setUsername("aaa");
    signupRequest.setPassword("aaa");
    when(roleRepository.findByName(RoleName.USER)).thenReturn(Optional.of(new Role()));
    when(userRepository.save(any(User.class))).thenReturn(new User());
    Assertions.assertNotNull(userService.createUser(signupRequest));
  }

  @Test
  void createAdminUser() {
    AdminCreateRequest adminCreateRequest = new AdminCreateRequest();
    adminCreateRequest.setUsername("aaa");
    adminCreateRequest.setPassword("aaa");
    adminCreateRequest.setEmail("aaa");
    when(passwordEncoder.encode(anyString())).thenReturn("aaa");
    when(roleRepository.findByName(RoleName.USER)).thenReturn(Optional.of(new Role()));
    when(roleRepository.findByName(RoleName.ADMIN)).thenReturn(Optional.of(new Role()));
    when(userRepository.save(any(User.class))).thenReturn(new User());
    Assertions.assertNotNull(userService.createAdminUser(adminCreateRequest));
  }

  @Test
  void modifyUserPassword() {
    when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
    when(userRepository.save(any(User.class))).thenReturn(new User());
    UserPasswordModifyRequest userPasswordModifyRequest = new UserPasswordModifyRequest();
    userPasswordModifyRequest.setPassword("aaa");
    CustomUserDetails customUserDetails = new CustomUserDetails(1L, "a", "a");
    Assertions.assertNotNull(userService.modifyUserPassword(userPasswordModifyRequest, customUserDetails));

  }

  @Test
  void modifyUserEmail() {
    when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
    when(userRepository.save(any(User.class))).thenReturn(new User());
    UserEmailModifyRequest userEmailModifyRequest = new UserEmailModifyRequest();
    userEmailModifyRequest.setEmail("aaa");
    CustomUserDetails customUserDetails = new CustomUserDetails(1L, "a", "a");
    Assertions.assertNotNull(userService.modifyUserEmail(userEmailModifyRequest, customUserDetails));

  }
}
