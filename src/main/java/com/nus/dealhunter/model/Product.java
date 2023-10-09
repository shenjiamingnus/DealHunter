package com.nus.dealhunter.model;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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

    private String storeAddress;

    private String discription;

    private Double currentPrice;

    private Double lowestPrice;

    @CreatedDate
    private Instant createDate;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private  Brand brand;


    public Product(String productname) {
        this.productname = productname;

    }


    public Product(Long id, String productname, double currentPrice) {
        this.id = id;
        this.productname = productname;
        this.currentPrice = currentPrice;
    }

    public Product() {}
}
