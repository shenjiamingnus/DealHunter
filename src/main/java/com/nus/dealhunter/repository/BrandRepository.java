package com.nus.dealhunter.repository;

import com.nus.dealhunter.model.Brand;
import com.nus.dealhunter.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    Optional<Brand> findByBrandname(String brandname);

    Boolean existsByBrandname(String brandname);


}
