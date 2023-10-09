package com.nus.dealhunter.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

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

    private Double currentPrice;

    private Double lowestPrice;


    @CreatedDate
    private Instant createDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "products_brands",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "brand_id"))
    private Set<Brand> brands = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PriceHistory> priceHistoryList = new ArrayList<>();

    public Product(String productname, String brandname) {
        this.productname = productname;
        this.brandname = brandname;
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
