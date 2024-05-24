package com.next.gen.api;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicUpdate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sensitive_data")
@NoArgsConstructor
@AllArgsConstructor
@Data
@DynamicUpdate
@Accessors(chain = true)
public class SensitiveData {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Image> images;

    @ManyToOne
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
