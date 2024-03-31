package com.next.gen.api;

import com.next.gen.sec.model.RegistrationSource;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
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

    @ManyToOne(cascade = CascadeType.ALL)
    private Address address;
    private String name;
    private String surname;
    private String pictureUrl;
    private LocalDateTime creationDate;
    private String password;

    @OneToMany
    private List<SensitiveData> sensitiveData;
}
