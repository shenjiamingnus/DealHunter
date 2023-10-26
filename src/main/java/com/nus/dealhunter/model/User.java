package com.nus.dealhunter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nus.dealhunter.util.EmailUtil;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"username"})
})
public class User implements Observer{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(max = 50)
  private String username;

  @NotBlank
  @Size(max = 100)
  private String password;

  private String email;

  @CreatedDate
  private Instant createDate;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "users_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
          name = "user_watched_products",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "product_id")
  )
  private Set<Product> watchedProducts = new HashSet<>();

  public void addWatchedProduct(Product product) {
    if (watchedProducts == null) {
      watchedProducts = new HashSet<>();
    }
    watchedProducts.add(product);
  }

  public void removeWatchedProduct(Product product) {
    if (watchedProducts != null) {
      watchedProducts.remove(product);
    }
  }


  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }

  @Override
  public boolean update(Product product, double newLowestPrice) {
    EmailUtil.sendEmail(this, product, newLowestPrice);
    return false;
  }

  public User() {}
}
