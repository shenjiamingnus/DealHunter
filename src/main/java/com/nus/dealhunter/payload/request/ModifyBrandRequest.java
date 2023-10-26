package com.nus.dealhunter.payload.request;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
@Data
public class ModifyBrandRequest {
    @NotBlank
    @Size(max = 50)
    private String brandname;


    @NotBlank
    private String description;

    @NotBlank
    private String imageUrl;

    @NotBlank
    private Long id;

    public ModifyBrandRequest(Long id, String brandname, String description){
        this.id = id;
        this.brandname= brandname;
        this.description =description;


    }
}
