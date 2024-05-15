package com.next.gen.api;

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
    private String homeNumber;
    private String city;
    private String latitude;
    private String longitude;
}
