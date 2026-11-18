package com.campuslab.platform.u202417448.reservations.infrastructure.persistence.jpa;

import com.campuslab.platform.u202417448.reservations.domain.model.aggregates.EquipmentReservation;
import com.campuslab.platform.u202417448.reservations.domain.model.valueobjects.ReservationStatus;
import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "equipment_reservations", uniqueConstraints = @UniqueConstraint(
        name = EquipmentReservationEntity.RESERVATION_CODE_UNIQUE, columnNames = "reservation_code"))
public class EquipmentReservationEntity {
    static final String RESERVATION_CODE_UNIQUE = "uk_equipment_reservations_reservation_code";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "reservation_code", nullable = false)
    private String reservationCode;
    @Column(nullable = false)
    private UUID laboratoryId;
    @Column(nullable = false)
    private LocalDate reservationPeriodStartDate;
    @Column(nullable = false)
    private LocalDate reservationPeriodEndDate;
    @Column(nullable = false)
    private Integer equipmentBatchQuantity;
    @Column(nullable = false, columnDefinition = "text")
    private String equipmentBatchUnit;
    @Column(nullable = false)
    private Integer accessLevel;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status;
    @Column(columnDefinition = "text")
    private String reservationNotes;
    @Column(nullable = false, updatable = false)
    private Instant createdAt;
    @Column(nullable = false)
    private Instant updatedAt;

    protected EquipmentReservationEntity() {
    }

    EquipmentReservationEntity(EquipmentReservation reservation) {
        reservationCode = reservation.getReservationCode().code();
        laboratoryId = reservation.getLaboratoryId().value();
        reservationPeriodStartDate = reservation.getReservationPeriod().startDate();
        reservationPeriodEndDate = reservation.getReservationPeriod().endDate();
        equipmentBatchQuantity = reservation.getEquipmentBatch().quantity();
        equipmentBatchUnit = reservation.getEquipmentBatch().unit();
        accessLevel = reservation.getAccessLevel();
        status = reservation.getStatus();
        reservationNotes = reservation.getReservationNotes();
        createdAt = reservation.getCreatedAt();
        updatedAt = reservation.getUpdatedAt();
    }

    Long getId() {
        return id;
    }
}
