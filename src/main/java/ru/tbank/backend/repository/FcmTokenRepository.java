package ru.tbank.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tbank.backend.entity.FcmTokenEntity;

import java.util.List;
import java.util.UUID;

public interface FcmTokenRepository extends JpaRepository<FcmTokenEntity, UUID> {

    List<FcmTokenEntity> findAllByUserId(UUID userId);

}
