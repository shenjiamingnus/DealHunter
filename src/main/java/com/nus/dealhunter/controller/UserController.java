package com.nus.dealhunter.controller;

import com.nus.dealhunter.enums.RoleName;
import com.nus.dealhunter.model.Role;
import com.nus.dealhunter.payload.request.AdminCreateRequest;
import com.nus.dealhunter.payload.request.UserEmailModifyRequest;
import io.swagger.annotations.Api;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nus.dealhunter.annotation.CurrentUser;
import com.nus.dealhunter.model.CustomUserDetails;
import com.nus.dealhunter.model.User;
import com.nus.dealhunter.payload.request.LoginRequest;
import com.nus.dealhunter.payload.request.SignupRequest;
import com.nus.dealhunter.payload.request.UserPasswordModifyRequest;
import com.nus.dealhunter.payload.response.GeneralApiResponse;
import com.nus.dealhunter.payload.response.JwtAuthenticationResponse;
import com.nus.dealhunter.service.UserService;
import com.nus.dealhunter.util.JwtTokenUtil;

@Api("Login/Register")
@RestController
@RequestMapping("/api/user")
public class UserController {

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserService userService;

  @Autowired
  JwtTokenUtil jwtTokenUtil;

  @PostMapping("/login")
  public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequest.getUsername(),
            loginRequest.getPassword()
        ));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String generateToken = jwtTokenUtil.generateToken(authentication);
    User user = userService.getUser(loginRequest.getUsername());
    Set<Role> roles = user.getRoles();
    int isAdmin = 0;
    for(Role r : roles) {
      if (r.getName() == RoleName.ADMIN) {
        isAdmin = 1;
        break;
      }
    }
    return ResponseEntity.ok(new JwtAuthenticationResponse(generateToken, user.getUsername(), isAdmin));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> register(@Valid @RequestBody SignupRequest signupRequest){
    if (userService.checkUserNameExists(signupRequest.getUsername())) {
      return new ResponseEntity<>(new GeneralApiResponse(false, "Username already registered!"), HttpStatus.BAD_REQUEST);
    }
    User user = userService.createUser(signupRequest);
    if (user != null) {
      return ResponseEntity.ok(new GeneralApiResponse(true, "User registered!"));
    }
    return ResponseEntity.ok(new GeneralApiResponse(false, "User register failed."));
  }

  @PutMapping("/modify/password")
  public ResponseEntity<?> modifyPassword(@Valid @RequestBody UserPasswordModifyRequest userModifyRequest, @CurrentUser CustomUserDetails userDetails) {
    User modifyUser = userService.modifyUserPassword(userModifyRequest, userDetails);
    if (modifyUser!= null) {
      return ResponseEntity.ok(new GeneralApiResponse(true, "User Detail modified!"));
    }
    return ResponseEntity.ok(new GeneralApiResponse(false, "User Detail modify failed"));
  }

  @PutMapping("/modify/email")
  public ResponseEntity<?> modifyEmail(@Valid @RequestBody UserEmailModifyRequest userModifyRequest, @CurrentUser CustomUserDetails userDetails) {
    User modifyUser = userService.modifyUserEmail(userModifyRequest, userDetails);
    if (modifyUser!= null) {
      return ResponseEntity.ok(new GeneralApiResponse(true, "User Detail modified!"));
    }
    return ResponseEntity.ok(new GeneralApiResponse(false, "User Detail modify failed"));
  }

  @PostMapping("/admin/create")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<?> createAdmin(@Valid @RequestBody AdminCreateRequest adminCreateRequest, @CurrentUser CustomUserDetails userDetails) {
    if (userService.checkUserNameExists(adminCreateRequest.getUsername())) {
      return new ResponseEntity<>(new GeneralApiResponse(false, "Username already registered!"), HttpStatus.BAD_REQUEST);
    }
    User createdUser = userService.createAdminUser(adminCreateRequest);
    if (createdUser != null) {
      return ResponseEntity.ok(new GeneralApiResponse(true, "User registered!"));
    }
    return ResponseEntity.ok(new GeneralApiResponse(false, "User register failed."));
  }

  @PostMapping("/admin/test")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<?> testAdmin(@Valid @RequestBody AdminCreateRequest adminCreateRequest, @CurrentUser CustomUserDetails userDetails) {
    if (userService.checkUserNameExists(adminCreateRequest.getUsername())) {
      return new ResponseEntity<>(new GeneralApiResponse(false, "Username already registered!"), HttpStatus.BAD_REQUEST);
    }
    User createdUser = userService.createAdminUser(adminCreateRequest);
    if (createdUser != null) {
      return ResponseEntity.ok(new GeneralApiResponse(true, "User registered!"));
    }
    return ResponseEntity.ok(new GeneralApiResponse(false, "User register failed."));
  }

}
