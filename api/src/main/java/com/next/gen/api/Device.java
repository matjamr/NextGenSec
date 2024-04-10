package com.next.gen.api;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Table(name = "device")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
@DynamicUpdate
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String deviceName;

    @ManyToOne
    private Places place;

    @ManyToOne
    private Product product;

    private OffsetDateTime installmentTime;
}
