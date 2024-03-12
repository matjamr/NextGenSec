package com.sec.gen.next.backend.api.internal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
@DynamicUpdate
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private RegisterSource registrationSource;

    @Column(unique = true)
    private String email;
    private String phoneNumber;
    private String passwordChange;

    @ManyToOne
    private Address address;
    private String name;
    private String surname;
    private String pictureUrl;
    private LocalDateTime creationDate;

    @OneToMany
    private List<SensitiveData> sensitiveData;

    private String password;
}
