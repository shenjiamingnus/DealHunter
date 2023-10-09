package com.nus.dealhunter.repository;

import java.util.Optional;

import com.nus.dealhunter.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.nus.dealhunter.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByProductname(String productname);

    Optional<Product> findByBrandname(String brandname);

    Boolean existsByProductname(String productname);

    Optional<Product> findByProductnameAndBrandname(String productname, String brandname);

}
