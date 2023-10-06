package com.nus.dealhunter.model;

import java.time.Instant;
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

    public Brand(String brandname) {
        this.brandname = brandname;

    }

    public Brand() {}







}
