package com.next.gen.api;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;

@Entity
@Table(name = "emails")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    private User from;

    @ManyToOne
    private User toUser;

    @Column
    private String subject;

    private String content;
    private OffsetDateTime date;
}
