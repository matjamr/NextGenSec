package com.next.gen.api;

import com.next.gen.sec.model.WebhookAction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@Table(name = "webhook")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class Webhook {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    private WebhookAction action;

    private String url;

}
