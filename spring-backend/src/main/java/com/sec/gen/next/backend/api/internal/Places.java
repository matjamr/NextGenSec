package com.sec.gen.next.backend.api.internal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@Table(name = "places")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class Places {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private User owner;

    @Column(unique = true)
    private String emailPlace;

    @Column(unique = true)
    private String placeName;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<UserPlaceAssignment> authorizedUsers;

    @Enumerated(value = EnumType.STRING)
    private VerificationStage verificationStage;
}
