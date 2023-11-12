package com.nus.dealhunter.service;

import com.nus.dealhunter.enums.RoleName;
import com.nus.dealhunter.exception.CommonException;
import com.nus.dealhunter.factory.UserFactory;
import com.nus.dealhunter.model.CustomUserDetails;
import com.nus.dealhunter.model.Role;
import com.nus.dealhunter.model.User;
import com.nus.dealhunter.payload.request.AdminCreateRequest;
import com.nus.dealhunter.payload.request.SignupRequest;
import com.nus.dealhunter.payload.request.UserEmailModifyRequest;
import com.nus.dealhunter.payload.request.UserPasswordModifyRequest;
import com.nus.dealhunter.repository.RoleRepository;
import com.nus.dealhunter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  UserRepository userRepository;


  private static UserFactory userFactory;

  @Autowired
  public void setUserServer(UserFactory userFactory) {
    UserService.userFactory = userFactory;
  }

  public Boolean checkUserNameExists(String username) {
    return userRepository.existsByUsername(username);
  }

  public User getUser(String username) {
    return userRepository.findByUsername(username).orElseThrow(() ->
        new UsernameNotFoundException("User not found!"));
  }

  public User getUser(Long id) {
    return userRepository.findById(id).orElseThrow(() ->
        new UsernameNotFoundException("User not found!"));
  }

  public User createUser(SignupRequest signupRequest) {

    User user = userFactory.getUser("NORMAL");
    user.setUsername(signupRequest.getUsername());
    user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
    user.setCreateDate(Instant.now());
    user.setEmail(signupRequest.getEmail());
    return userRepository.save(user);

  }

  public User createAdminUser(AdminCreateRequest adminCreateRequest) {
    User user = userFactory.getUser("ADMIN");
    user.setUsername(adminCreateRequest.getUsername());
    user.setPassword(adminCreateRequest.getPassword());
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setEmail(adminCreateRequest.getEmail());
    user.setCreateDate(Instant.now());
    return userRepository.save(user);
  }

  public User modifyUserPassword(UserPasswordModifyRequest userPasswordModifyRequest, CustomUserDetails userDetails) {
    User user = userRepository.findById(userDetails.getId()).orElseThrow(() ->
        new UsernameNotFoundException("User not found!"));
    user.setPassword(passwordEncoder.encode(userPasswordModifyRequest.getPassword()));
    return userRepository.save(user);
  }

  public User modifyUserEmail(UserEmailModifyRequest userEmailModifyRequest, CustomUserDetails userDetails) {
    User user = userRepository.findById(userDetails.getId()).orElseThrow(() ->
        new UsernameNotFoundException("User not found!"));
    user.setEmail(userEmailModifyRequest.getEmail());
    return userRepository.save(user);
  }

}
