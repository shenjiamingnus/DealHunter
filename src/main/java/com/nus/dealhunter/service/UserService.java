package com.nus.dealhunter.service;

import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import com.nus.dealhunter.enums.RoleName;
import com.nus.dealhunter.exception.CommonException;
import com.nus.dealhunter.model.CustomUserDetails;
import com.nus.dealhunter.model.Role;
import com.nus.dealhunter.model.User;
import com.nus.dealhunter.payload.request.SignupRequest;
import com.nus.dealhunter.payload.request.UserModifyRequest;
import com.nus.dealhunter.repository.RoleRepository;
import com.nus.dealhunter.repository.UserRepository;
import com.nus.dealhunter.util.FileUploadUtil;

@Service
public class UserService {

  @Value("${app.profilePhoto}")
  private String profilePhotoSavePath;

  @Value("${app.profilePhotoMapper}")
  private String profilePhotoMapperPath;

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
    Role userRole = roleRepository.findByName(RoleName.USER)
        .orElseThrow(() -> new CommonException("User Role not set."));
    user.setRoles(Collections.singleton(userRole));
    return userRepository.save(user);
  }

  public User modifyUser(UserModifyRequest userModifyRequest, CustomUserDetails userDetails) {
    User user = userRepository.findById(userDetails.getId()).orElseThrow(() ->
        new UsernameNotFoundException("User not found!"));
    user.setUsername(userModifyRequest.getUsername());
    user.setGender(userModifyRequest.getGender());

    return userRepository.save(user);
  }

  public User uploadAvatar(MultipartFile file, CustomUserDetails userDetails) {
    User user = userRepository.findById(userDetails.getId()).orElseThrow(() ->
        new UsernameNotFoundException("User not found!"));
    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    String suffixName = fileName.substring(fileName.lastIndexOf("."));
    fileName = UUID.randomUUID()+suffixName;

    try{
      FileUploadUtil.saveFile(profilePhotoSavePath, fileName, file);
    } catch (IOException e){
      return null;
    }
    user.setAvatar(profilePhotoMapperPath+"/"+fileName);
    User savedUser = userRepository.save(user);
    return savedUser;
  }

}
