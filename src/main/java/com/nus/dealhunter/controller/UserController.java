package com.nus.dealhunter.controller;

import com.nus.dealhunter.enums.RoleName;
import com.nus.dealhunter.model.Role;
import com.nus.dealhunter.payload.request.AdminCreateRequest;
import io.swagger.annotations.Api;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.nus.dealhunter.payload.request.UserModifyRequest;
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

  @PutMapping("/modify")
  public ResponseEntity<?> modify(@Valid @RequestBody UserModifyRequest userModifyRequest, @CurrentUser CustomUserDetails userDetails) {
//    if (!Objects.equals(userService.getUser(userDetails.getId()).getUsername(), userModifyRequest.getUsername())){
//      if (userService.checkUserNameExists(userModifyRequest.getUsername())) {
//        return ResponseEntity.ok(new GeneralApiResponse(false, "Username already registered!"));
//      }
//    }

    User modifyUser = userService.modifyUser(userModifyRequest, userDetails);
    if (modifyUser!= null) {
      return ResponseEntity.ok(new GeneralApiResponse(true, "User Detail modified!"));
    }
    return ResponseEntity.ok(new GeneralApiResponse(false, "User Detail modify failed"));
  }

  @PostMapping("/create/admin")
  public ResponseEntity<?> createAdmin(@Valid @RequestBody AdminCreateRequest adminCreateRequest, @CurrentUser CustomUserDetails userDetails) {
    // check if the current user is admin
    User user = userService.getUser(userDetails.getId());
    Set<Role> roles = user.getRoles();
    long count = roles.stream().filter(role -> role.getName() == RoleName.ADMIN).count();
    if (count == 0) {
      return ResponseEntity.ok(new GeneralApiResponse(false, "Current User is not Admin"));
    }
    // create new admin user
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
