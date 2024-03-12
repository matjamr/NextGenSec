package com.sec.gen.next.backend.api.internal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "image")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
@DynamicUpdate
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = false)
    private String title;
    private String description;
    private String extension;

    @Lob
    @Column(unique = false)
    private byte[] content;
}
