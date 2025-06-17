package ru.tbank.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "locations")
public class LocationEntity {

    @Id

    @GeneratedValue(generator = "UUID")
    // @GenericGenerator(
    //     name = "UUID",
    //     strategy = "org.hibernate.id.UUIDGenerator"
    // )
    @Column(name = "id")
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "name")
    private String name;

    @Column(name = "coords")
    private String coords;
}
