package com.nus.dealhunter.repository;


import com.nus.dealhunter.model.Product;
import com.nus.dealhunter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;
import java.util.Set;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByProductname(String productname);

    List<Product> findByBrandname(String brandname);

    Boolean existsByProductname(String productname);

//    Optional<Product> findByProductnameAndBrandname(String productname, String brandname);

    // 自定义方法，查找关注了特定商品的用户
    @Query("SELECT u FROM User u JOIN u.watchedProducts p WHERE p = :product")
    Set<User> findUsersWatchingProduct(@Param("product") Product product);

}