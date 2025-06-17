package ru.tbank.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "trigger_location")
public class TriggerLocationEntity extends TriggerEntity {

    @Column(name = "location")
    private String location;

    public TriggerLocationEntity(UUID id, String location) {
        this.setId(id);
        this.location = location;
    }

}
