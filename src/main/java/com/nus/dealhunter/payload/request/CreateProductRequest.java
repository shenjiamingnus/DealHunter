package com.nus.dealhunter.payload.request;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
public class CreateProductRequest {
    @NotBlank
    @Size(max = 50)
    private String productname;

    @NotBlank
    @Size(max = 50)
    private String brandname;

    @NotBlank
    private String storeAddress;

    @NotBlank
    private String description;

    @NotBlank
    private String imageUrl;

    @NotBlank
    private Double currentPrice;

    @NotBlank
    private Long brand_id;


    /** this is for unit test*/
    public CreateProductRequest(String productname, String brandname, String storeAddress, String description, String imageUrl, double currentPrice, long brand_id) {
        this.productname=productname;
        this.brandname=brandname;
        this.storeAddress=storeAddress;
        this.description=description;
        this.imageUrl=imageUrl;
        this.currentPrice=currentPrice;
        this.brand_id=brand_id;
    }
}
