package ru.tbank.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.tbank.backend.dto.NoteProjection;
import ru.tbank.backend.entity.NoteEntity;
import ru.tbank.backend.entity.TriggerEntity;

import java.util.List;
import java.util.UUID;

public interface TriggerRepository extends JpaRepository<TriggerEntity, UUID> {
}
