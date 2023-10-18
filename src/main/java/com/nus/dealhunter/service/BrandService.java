package com.nus.dealhunter.service;

import com.nus.dealhunter.exception.BrandServiceException;
import com.nus.dealhunter.model.Brand;
import com.nus.dealhunter.model.Product;
import com.nus.dealhunter.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


import java.util.Optional;


@Service
public class BrandService {
    @Autowired
    private BrandRepository brandRepository;


    public Boolean checkBrandNameExists(String brandname) {
        return brandRepository.existsByBrandname(brandname);
    }

    public List<Brand> getAllBrands(){
        try {
            return brandRepository.findAll();
        }catch (Exception e){
            throw new BrandServiceException("Failed to retrieve all brands ", e);
        }

    }

    public Brand saveBrand(Brand brand) {
        try {
            return brandRepository.save(brand);
        }catch (Exception e){
            throw new BrandServiceException("Failed to save brand", e);
        }

    }

    public void deleteBrand(Long id) {
        try {
            brandRepository.deleteById(id);
        }catch (Exception e){
            throw new BrandServiceException("Failed to delete brand", e);
        }

    }

    public List<Brand> getBrandByBrandname(String brandname) {
        try {
            return brandRepository.findByBrandname(brandname);
        }catch (Exception e){
            throw new BrandServiceException("Failed to find brand", e);
        }
    }



    public Brand save(Brand brand){
       return brandRepository.save(brand);
    }

    public  void delete(Long id){
        brandRepository.deleteById(id);
    }


    public Optional<Brand> findById(Long id){
        return brandRepository.findById(id);
    }



}
