package ru.tbank.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tbank.backend.enums.CategoryType;
import ru.tbank.backend.utils.CategoryTypeConverter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "note")
public class NoteEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "text")
    private String text;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "category_type")
    @Convert(converter = CategoryTypeConverter.class)
    private CategoryType categoryType;

}
