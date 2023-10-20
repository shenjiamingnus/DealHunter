package com.nus.dealhunter.payload.request;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
@Data
public class UpdateProductRequest {
    @NotBlank
    private Long product_id;

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

    //页面显示但不可更改
    @NotBlank
    private Double lowestPrice;

    @NotBlank
    private Long brand_id;
}
