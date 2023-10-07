package com.nus.dealhunter.model;

import com.nus.dealhunter.enums.RoleName;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Data
@Entity
@Table(name = "roles")
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @NaturalId
  @Column(length = 25)
  private RoleName name;

}
