package com.nus.dealhunter.service;

import com.nus.dealhunter.payload.request.AdminCreateRequest;
import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.nus.dealhunter.enums.RoleName;
import com.nus.dealhunter.exception.CommonException;
import com.nus.dealhunter.model.CustomUserDetails;
import com.nus.dealhunter.model.Role;
import com.nus.dealhunter.model.User;
import com.nus.dealhunter.payload.request.SignupRequest;
import com.nus.dealhunter.payload.request.UserModifyRequest;
import com.nus.dealhunter.repository.RoleRepository;
import com.nus.dealhunter.repository.UserRepository;

@Service
public class UserService {

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  UserRepository userRepository;

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
    User user = new User(signupRequest.getUsername(), signupRequest.getPassword());
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setCreateDate(Instant.now());
    user.setEmail(signupRequest.getEmail());
    Role userRole = roleRepository.findByName(RoleName.USER)
        .orElseThrow(() -> new CommonException("User Role not set."));
    user.setRoles(Collections.singleton(userRole));
    return userRepository.save(user);
  }

  public User createAdminUser(AdminCreateRequest adminCreateRequest) {
    User user = new User(adminCreateRequest.getUsername(), adminCreateRequest.getPassword());
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setCreateDate(Instant.now());
    Role userRole = roleRepository.findByName(RoleName.USER)
        .orElseThrow(() -> new CommonException("User Role not set."));
    Role adminRole = roleRepository.findByName(RoleName.ADMIN)
        .orElseThrow(() -> new CommonException("User Role not set."));
    Set<Role> roleSet = new HashSet<>();
    roleSet.add(userRole);
    roleSet.add(adminRole);
    user.setRoles(roleSet);
    return userRepository.save(user);
  }

  public User modifyUser(UserModifyRequest userModifyRequest, CustomUserDetails userDetails) {
    User user = userRepository.findById(userDetails.getId()).orElseThrow(() ->
        new UsernameNotFoundException("User not found!"));
    user.setPassword(passwordEncoder.encode(userModifyRequest.getPassword()));
    return userRepository.save(user);
  }



}
