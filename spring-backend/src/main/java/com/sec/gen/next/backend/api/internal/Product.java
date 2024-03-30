package com.sec.gen.next.backend.api.internal;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
@DynamicUpdate
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String name;

    private String description;

    private Double monthlyPrice;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Image> images;

}
