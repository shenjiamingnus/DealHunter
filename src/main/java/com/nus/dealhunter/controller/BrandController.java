package com.nus.dealhunter.controller;
import com.nus.dealhunter.model.Brand;
import com.nus.dealhunter.payload.request.CreateBrandRequest;
import com.nus.dealhunter.payload.request.ModifyBrandRequest;
import com.nus.dealhunter.payload.response.GeneralApiResponse;
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

    @GetMapping
    public List<Brand> getAllBrands() {
        return brandService.getAllBrands();
    }

    @PostMapping
    public ResponseEntity<GeneralApiResponse> createBrand(@RequestBody CreateBrandRequest createBrandRequest){
        Brand savedBrand = brandService.createBrand(createBrandRequest);
        if(savedBrand != null){
            return ResponseEntity.ok(new GeneralApiResponse(true,"Brand created!"));
        }else {
            return ResponseEntity.ok(new GeneralApiResponse(false,"Brand failed to created"));
        }

    }
    @PutMapping
    public ResponseEntity<GeneralApiResponse> modifyBrand(@RequestBody ModifyBrandRequest modifyBrandRequest){
        Brand modifiedBrand = brandService.modifyBrand(modifyBrandRequest);
        if(modifiedBrand != null){
            return ResponseEntity.ok(new GeneralApiResponse(true,"Brand modified!"));
        }else {
            return ResponseEntity.ok(new GeneralApiResponse(false,"Brand failed to modify"));
        }

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
