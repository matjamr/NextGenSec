package com.sec.gen.next.backend.api.internal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Entity
@Table(name = "device")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String deviceName;

    @ManyToOne
    private Places place;

    @ManyToOne
    private Product product;

    private LocalDateTime installmentTime;
}
