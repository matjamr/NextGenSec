package com.next.gen.api;

import com.next.gen.sec.model.RegistrationSource;
import com.next.gen.sec.model.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private RegistrationSource source;

    @Column(unique = true)
    private String email;
    private String phoneNumber;
    private boolean passwordChange;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    private String name;
    private String surname;
    private String pictureUrl;
    private OffsetDateTime creationDate;
    private String password;

    @OneToMany(cascade = CascadeType.ALL)
    private List<SensitiveData> sensitiveData;

    @OneToOne
    private Places ownerShipPlace;

    @OneToMany
    private List<UserPlaceAssignment> userPlaceAssignments;

    @OneToMany
    private List<Email> emails;
}
