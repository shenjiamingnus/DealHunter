package com.nus.dealhunter.model;

import java.time.Instant;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

@Data
@Entity
@Table(name = "brands", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"brandname"})
})
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String brandname;

    private String discription;

    @CreatedDate
    private Instant createDate;

    @OneToMany(mappedBy = "brand")
    private Set<Product> products;

    public Brand(String brandname) {
        this.brandname = brandname;

    }

    public Brand() {}

    public Brand(Long id,String brandname){
        this.id = id;
        this.brandname = brandname;


    }







}
