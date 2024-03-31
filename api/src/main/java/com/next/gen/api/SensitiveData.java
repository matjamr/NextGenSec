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

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "image_ids", joinColumns = @JoinColumn(name = "image_id"))
    @Column(name = "image_ids", nullable = false)
    private List<String> imageIds = new ArrayList<>();

    @ManyToOne
    private Product product;
}
