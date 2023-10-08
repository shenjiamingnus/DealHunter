package com.nus.dealhunter.controller;
import com.nus.dealhunter.model.Brand;
import com.nus.dealhunter.model.Product;
import com.nus.dealhunter.service.BrandService;
import com.nus.dealhunter.util.JwtTokenUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("Brand/")
@RestController
@RequestMapping("/api/brands")

public class BrandController {
    @Autowired
    private BrandService brandService;


    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @GetMapping
    public List<Brand> getAllBrands() {
        return brandService.getAllBrands();
    }

    @PostMapping
    public ResponseEntity<Brand> createBrand(@RequestBody Brand brand){
        Brand savedBrand = brandService.saveBrand(brand);
        return ResponseEntity.ok(savedBrand);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable Long id){
        brandService.deleteBrand(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/brandname")
    public ResponseEntity<List<Brand>> getBrandByBrandname(@RequestParam String brandname){
        List<Brand> brands = brandService.getBrandByBrandname(brandname);
        if(!brands.isEmpty()){
            return ResponseEntity.ok(brands);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

}
