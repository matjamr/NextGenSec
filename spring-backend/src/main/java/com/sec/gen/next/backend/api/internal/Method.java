package com.sec.gen.next.backend.api.internal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Entity
@Table(name = "method")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
@DynamicUpdate
public class Method {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne
    private Product product;

    @ManyToOne
    private User user;

    @ManyToMany
    private List<Image> images;

    @Enumerated(EnumType.STRING)
    VerificationStage verificationStage;
}
