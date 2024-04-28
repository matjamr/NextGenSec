package com.next.gen.api;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Entity
@Table(name = "user_place_assignment")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
@Builder(toBuilder = true)
public class UserPlaceAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private User user;

    @ManyToMany
    private List<Places> places;

    @Enumerated(value = EnumType.STRING)
    private AssignmentRole assignmentRole;

    @ManyToMany
    private List<Product> products;
}
