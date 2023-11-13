package com.nus.dealhunter.singleton;
import com.nus.dealhunter.enums.RoleName;
import com.nus.dealhunter.exception.CommonException;
import com.nus.dealhunter.model.Role;
import com.nus.dealhunter.repository.RoleRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleManager {

  private static RoleManager instance;

  private static RoleRepository roleRepository;

  @Autowired
  public void setUserServer(RoleRepository roleRepository) {
    RoleManager.roleRepository = roleRepository;
  }

  private RoleManager() {
  }

  public static synchronized RoleManager getInstance() {
    if (instance == null) {
      instance = new RoleManager();
    }
    return instance;
  }

  private Map<RoleName, Role> roleCache = new HashMap<>();

  public Role getRole(RoleName roleName) {
    if (roleCache.get(roleName) == null){
      Optional<Role> byName = roleRepository.findByName(roleName);
      if (!byName.isPresent()){
        throw new CommonException("Role not exist!");
      }
      roleCache.put(roleName, byName.get());
    }
    return roleCache.get(roleName);
  }

  public boolean hasRole(RoleName roleName) {
    return roleCache.containsKey(roleName);
  }
}