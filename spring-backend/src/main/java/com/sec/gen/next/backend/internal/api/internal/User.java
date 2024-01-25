package com.sec.gen.next.backend.internal.api.internal;

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
@Accessors(chain = true, fluent = true)
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

    @ManyToOne
    private Address address;
    private String name;
    private String surname;
    private String pictureUrl;
    private LocalDateTime creationDate;

    @OneToMany
    private List<SensitiveData> sensitiveData;

    public Integer getId() {
        return id;
    }

    public RegisterSource getRegistrationSource() {
        return registrationSource;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
