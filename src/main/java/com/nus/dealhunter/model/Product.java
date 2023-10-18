package com.nus.dealhunter.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;


@Data
@Entity
@Table(name = "products", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"productname"})
})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String productname;

    @NotBlank
    @Size(max = 50)
    private String brandname;


    private String storeAddress;

    private String discription;

    private String imageUrl;

    private Double currentPrice;

    @JsonInclude
    private Double lowestPrice;


    @CreatedDate
    private Instant createDate;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private  Brand brand;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PriceHistory> priceHistoryList = new ArrayList<>();

    public Product(String productname) {
        this.productname = productname;

    }

    public Product(String productname, String brandname) {
        this.productname = productname;
        this.brandname = brandname;
    }

    public Product(Long id, String productname, double currentPrice ) {
        this.id = id;
        this.productname = productname;
        this.currentPrice = currentPrice;
        this .lowestPrice = currentPrice;
    }

    public Product(String productname, String brandname, String storeAddress) {
        this.productname = productname;
        this.brandname = brandname;
        this.storeAddress = storeAddress;
    }

    public Product(String productname, String brandname, String storeAddress, String discription) {
        this.productname = productname;
        this.brandname = brandname;
        this.storeAddress = storeAddress;
        this.discription = discription;
    }



    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }



    public void setCurrentPrice(double currentprice) {
        this.currentPrice = currentprice;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }



    public void setLowestPrice(double lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public double getLowestPrice() {
        return lowestPrice;
    }


    public Product() {}
}
