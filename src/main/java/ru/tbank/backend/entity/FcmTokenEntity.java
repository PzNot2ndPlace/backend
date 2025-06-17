package ru.tbank.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity(name = "fcm_token")
@AllArgsConstructor
@NoArgsConstructor
public class FcmTokenEntity {

    @Id
    @Column(name = "token")
    private String token;

    @Column(name = "user_id")
    private UUID userId;

}
