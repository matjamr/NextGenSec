package com.sec.gen.next.backend.api.internal;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
public class SensitiveData {

    private Integer id;
    private Image image;
    private Integer product_id;
}
