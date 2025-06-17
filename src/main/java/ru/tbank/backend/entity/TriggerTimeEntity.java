package ru.tbank.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tbank.backend.enums.CategoryType;
import ru.tbank.backend.utils.CategoryTypeConverter;

import java.time.OffsetDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "trigger_time")
public class TriggerTimeEntity extends TriggerEntity {

    @Column(name = "time")
    private OffsetDateTime time;

}
