package ru.tbank.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "trigger_time")
public class TriggerTimeEntity extends TriggerEntity {

    @Column(name = "time")
    private OffsetDateTime time;

    public TriggerTimeEntity(UUID id, OffsetDateTime time) {
        this.setId(id);
        this.setTime(time);
    }

}
