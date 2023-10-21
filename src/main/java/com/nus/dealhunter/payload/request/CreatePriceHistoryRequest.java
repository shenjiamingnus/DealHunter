package com.nus.dealhunter.payload.request;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
public class CreatePriceHistoryRequest {

    @NotNull
    private Double price;

    @NotNull
    private Instant createDate;

    @NotNull
    private Long product_id;

//    public CreatePriceHistoryRequest(Double price, Instant createDate, Long product_id){
//    }

}
