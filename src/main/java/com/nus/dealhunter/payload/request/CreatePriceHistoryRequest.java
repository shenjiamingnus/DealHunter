package com.nus.dealhunter.payload.request;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.time.LocalDate;

@Data
public class CreatePriceHistoryRequest {

    @NotBlank
    private Long id;

    @NotBlank
    private Double price;

    @NotBlank
    private Instant createDate;

    @NotBlank
    private Long product_id;

    public CreatePriceHistoryRequest(Long id, Double price, Instant createDate, Long product_id){
    }

}
