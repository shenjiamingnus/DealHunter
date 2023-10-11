package com.nus.dealhunter.repository;

import com.nus.dealhunter.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByProductname(String productname);

    List<Product> findByBrandname(String brandname);

    Boolean existsByProductname(String productname);

    Optional<Product> findByProductnameAndBrandname(String productname, String brandname);

}