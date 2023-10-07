package com.nus.dealhunter.repository;

import com.nus.dealhunter.enums.RoleName;
import com.nus.dealhunter.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

  Optional<Role> findByName(RoleName roleName);

}
