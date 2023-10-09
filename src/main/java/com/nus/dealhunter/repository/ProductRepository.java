package com.nus.dealhunter.repository;

import java.util.List;
import java.util.Optional;

import com.nus.dealhunter.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByProductname(String productname);

    Optional<Product> findByBrandname(String brandname);

    Boolean existsByProductname(String productname);

    Optional<Product> findByProductnameAndBrandname(String productname, String brandname);

}
