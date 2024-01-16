package com.sec.gen.next.backend.api.internal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicUpdate;

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

    @ManyToOne
    private Image image;

    @ManyToOne
    private Product product;
}
