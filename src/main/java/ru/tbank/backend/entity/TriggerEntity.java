package ru.tbank.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "trigger_entity")
public abstract class TriggerEntity {

    @Id
    @Column(name = "id")
    private UUID id;

}
