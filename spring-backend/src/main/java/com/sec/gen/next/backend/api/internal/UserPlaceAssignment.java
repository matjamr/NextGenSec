package com.sec.gen.next.backend.api.internal;

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
@DynamicUpdate
public class UserPlaceAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private User user;

    @Enumerated(value = EnumType.STRING)
    private AssignmentRole assignmentRole;

    @Enumerated(value = EnumType.STRING)
    private VerificationStage verificationStage;

    @ManyToMany
    private List<Product> products;
}
