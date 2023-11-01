package com.sec.gen.next.backend.api.internal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@Table(name = "user_place_assignment")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true, fluent = true)
public class UserPlaceAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private User user;

    @Enumerated(value = EnumType.STRING)
    private AssignmentRole assignmentRole;
}
