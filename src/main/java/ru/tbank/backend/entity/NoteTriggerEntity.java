package ru.tbank.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tbank.backend.enums.TriggerType;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "note_trigger")
public class NoteTriggerEntity {

    @Id
    @Column(name = "note_id")
    private UUID noteId;

    @Column(name = "trigger_id")
    private UUID triggerId;

    @Column(name = "trigger_type")
    @Enumerated(EnumType.STRING)
    private TriggerType triggerType;

    @Column(name = "is_ready")
    private Boolean isReady;

}
