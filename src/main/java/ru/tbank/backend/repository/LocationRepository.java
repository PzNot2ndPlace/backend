package ru.tbank.backend.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.tbank.backend.entity.LocationEntity;
import java.util.List;


public interface LocationRepository extends JpaRepository<LocationEntity, UUID>  {
    
    List<LocationEntity> findAllByUserId(UUID userId);
}
