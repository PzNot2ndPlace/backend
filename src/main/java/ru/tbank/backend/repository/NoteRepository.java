package ru.tbank.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.tbank.backend.dto.NoteProjection;
import ru.tbank.backend.entity.NoteEntity;

import java.util.List;
import java.util.UUID;

public interface NoteRepository extends JpaRepository<NoteEntity, UUID> {

    @Query(value = """
                SELECT n.id,
                   n.user_id,
                   n.text,
                   n.created_at,
                   n.updated_at,
                   n.category_type,
                   nt.trigger_id,
                   nt.trigger_type,
                   nt.is_ready,
                   CASE
                       WHEN nt.trigger_type = 'TIME' THEN tt.time::text
                       WHEN nt.trigger_type = 'LOCATION' THEN tl.location
                       ELSE NULL
                       END AS trigger_value
            FROM note n
                     JOIN
                 note_trigger nt ON n.id = nt.note_id
                     LEFT JOIN
                 trigger_time tt ON nt.trigger_type = 'TIME' AND nt.trigger_id = tt.id
                     LEFT JOIN
                 trigger_location tl ON nt.trigger_type = 'LOCATION' AND nt.trigger_id = tl.id
            WHERE n.id = :noteId
            """, nativeQuery = true)
    List<NoteProjection> findNote(@Param("noteId") UUID noteId);

    @Query(value = """
            SELECT
                n.id,
                n.user_id as userId,
                n.text,
                n.created_at as createdAt,
                n.updated_at as updatedAt,
                n.category_type as categoryType,
                nt.trigger_id as triggerId,
                nt.trigger_type as triggerType,
                nt.is_ready as isReady,
                CASE
                    WHEN nt.trigger_type = 'TIME' THEN tt.time::text
                    WHEN nt.trigger_type = 'LOCATION' THEN tl.location
                    ELSE NULL
                END as triggerValue
            FROM note n
            JOIN note_trigger nt ON n.id = nt.note_id
            LEFT JOIN trigger_time tt ON nt.trigger_type = 'TIME' AND nt.trigger_id = tt.id
            LEFT JOIN trigger_location tl ON nt.trigger_type = 'LOCATION' AND nt.trigger_id = tl.id
            WHERE n.user_id = :userId
            """, nativeQuery = true)
    List<NoteProjection> findByUserId(@Param("userId") UUID userId);

}
