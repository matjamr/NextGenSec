package com.next.gen.api;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Entity
@Table(name = "places")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
@DynamicUpdate
public class Places {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    private User owner;

    @Column(unique = true)
    private String emailPlace;

    @Column(unique = true)
    private String placeName;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @OneToOne(cascade = CascadeType.REMOVE)
    private Image image;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<UserPlaceAssignment> authorizedUsers;

    @OneToMany
    private List<Product> products;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Device> devices;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Webhook> webhooks;
}
