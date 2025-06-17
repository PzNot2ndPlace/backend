package ru.tbank.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tbank.backend.entity.LocationEntity;

import java.util.List;
import java.util.UUID;


public interface LocationRepository extends JpaRepository<LocationEntity, UUID> {

    List<LocationEntity> findAllByUserId(UUID userId);
}
