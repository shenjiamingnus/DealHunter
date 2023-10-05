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

    @NotBlank
    @Size(max = 50)
    private String brandname;

    private String storeAddress;

    private String discription;

    private Double currentPrice;

    private Double lowestPrice;

    @CreatedDate
    private Instant createDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Brand> brands = new HashSet<>();


    public Product(String productname, String brandname) {
        this.productname = productname;
        this.brandname = brandname;
    }

    public Product() {}
}
