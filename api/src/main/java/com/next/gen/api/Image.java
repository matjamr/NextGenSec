package com.next.gen.api;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "image")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column
    private String title;
    private boolean isSensitive;
    private String description;
    private String extension;

    @Lob
    @Column
    private byte[] content;
}
