package com.nus.dealhunter.factory;

import com.nus.dealhunter.enums.RoleName;
import com.nus.dealhunter.exception.CommonException;
import com.nus.dealhunter.model.Role;
import com.nus.dealhunter.model.User;
import com.nus.dealhunter.repository.RoleRepository;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {

  private static RoleRepository roleRepository;

  @Autowired
  public void setUserServer(RoleRepository roleRepository) {
    UserFactory.roleRepository = roleRepository;
  }

  public User getUser(String userType){
    if (userType == null){
      return null;
    }
    User user = new User();
    Role userRole = roleRepository.findByName(RoleName.USER)
        .orElseThrow(() -> new CommonException("User Role not set."));
    HashSet<Role> roles = new HashSet<>();
    roles.add(userRole);
    if ("NORMAL".equalsIgnoreCase(userType)) {
      user.setRoles(roles);
      return user;
    } else if ("ADMIN".equalsIgnoreCase(userType)) {
      Role adminRole = roleRepository.findByName(RoleName.ADMIN)
          .orElseThrow(() -> new CommonException("User Role not set."));
      roles.add(adminRole);
      user.setRoles(roles);
      return user;
    }
    return null;
  }

}
