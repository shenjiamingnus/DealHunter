package com.nus.dealhunter.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "price_history")
public class PriceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double price;
    private LocalDate date;


    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;



    public PriceHistory(double price, LocalDate date, Product product) {
        this.price = price;
        this.date = date;
        this.product = product;
    }

    public PriceHistory() {
    }

}
