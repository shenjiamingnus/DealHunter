package com.nus.dealhunter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nus.dealhunter.enums.RoleName;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
public class CustomUserDetails implements UserDetails {

  private Long id;

  private String username;

  @JsonIgnore
  private String password;

  private Collection<? extends GrantedAuthority> authorities;

  public CustomUserDetails(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.authorities = authorities;
  }

  public CustomUserDetails(Long id, String username, String password) {
    this.id = id;
    this.username = username;
    this.password = password;
  }

  public static CustomUserDetails createUserDetails(User user) {
    List<GrantedAuthority> grantedAuthorities = user.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(
            Collectors.toList());
    System.out.println(grantedAuthorities.get(0));
    System.out.println(RoleName.ADMIN.name());
    return new CustomUserDetails(user.getId(), user.getUsername(), user.getPassword(), grantedAuthorities);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
