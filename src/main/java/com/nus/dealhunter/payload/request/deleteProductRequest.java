package com.nus.dealhunter.payload.request;
import lombok.Data;
import javax.validation.constraints.NotBlank;
@Data
public class deleteProductRequest {
    @NotBlank
    private Long product_id;

}
