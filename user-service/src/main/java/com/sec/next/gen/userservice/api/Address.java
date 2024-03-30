package com.sec.next.gen.userservice.api;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Entity
@Table
@Accessors(chain = true)
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String streetName;
    private String postalCode;
    private String city;
}
