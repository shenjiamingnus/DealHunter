package com.nus.dealhunter.service;

import com.nus.dealhunter.model.Brand;
import com.nus.dealhunter.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BrandService {
    @Autowired
    private BrandRepository brandRepository;

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
