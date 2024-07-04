package com.next.gen.api;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;

@Entity
@Table(name = "device")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String deviceName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Places place;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private OffsetDateTime installmentTime;
}
