package ru.tbank.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tbank.backend.entity.NoteTriggerEntity;

import java.util.UUID;

public interface NoteTriggerRepository extends JpaRepository<NoteTriggerEntity, UUID> {

    NoteTriggerEntity findByTriggerId(UUID triggerId);

}
