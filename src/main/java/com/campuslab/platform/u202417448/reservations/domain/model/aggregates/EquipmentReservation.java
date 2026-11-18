package com.campuslab.platform.u202417448.reservations.domain.model.aggregates;

import com.campuslab.platform.u202417448.reservations.domain.model.commands.CreateEquipmentReservationCommand;
import com.campuslab.platform.u202417448.reservations.domain.model.valueobjects.*;
import lombok.Getter;

import java.time.Instant;

@Getter
public class EquipmentReservation {
    private final Long id;
    private final ReservationCode reservationCode;
    private final LaboratoryId laboratoryId;
    private final ReservationPeriod reservationPeriod;
    private final EquipmentBatch equipmentBatch;
    private final Integer accessLevel;
    private final ReservationStatus status;
    private final String reservationNotes;
    private final Instant createdAt;
    private final Instant updatedAt;

    public EquipmentReservation(CreateEquipmentReservationCommand command) {
        this(null, command, Instant.now());
    }

    private EquipmentReservation(Long id, CreateEquipmentReservationCommand command, Instant createdAt) {
        this(id, new ReservationCode(command.reservationCode()), new LaboratoryId(command.laboratoryId()),
                new ReservationPeriod(command.reservationPeriodStartDate(), command.reservationPeriodEndDate()),
                new EquipmentBatch(command.equipmentBatchQuantity(), command.equipmentBatchUnit()),
                command.accessLevel(), command.status(), command.reservationNotes(), createdAt);
    }

    private EquipmentReservation(Long id, ReservationCode reservationCode, LaboratoryId laboratoryId,
                                 ReservationPeriod reservationPeriod, EquipmentBatch equipmentBatch,
                                 Integer accessLevel, ReservationStatus status, String reservationNotes,
                                 Instant createdAt) {
        if (accessLevel == null || accessLevel < 1 || accessLevel > 5) {
            throw new IllegalArgumentException("Access level must be between 1 and 5.");
        }
        if (status == null) {
            throw new IllegalArgumentException("Reservation status is required.");
        }
        this.id = id;
        this.reservationCode = reservationCode;
        this.laboratoryId = laboratoryId;
        this.reservationPeriod = reservationPeriod;
        this.equipmentBatch = equipmentBatch;
        this.accessLevel = accessLevel;
        this.status = status;
        this.reservationNotes = reservationNotes;
        this.createdAt = createdAt;
        this.updatedAt = createdAt;
    }

    public EquipmentReservation withId(Long generatedId) {
        return new EquipmentReservation(generatedId, reservationCode, laboratoryId, reservationPeriod,
                equipmentBatch, accessLevel, status, reservationNotes, createdAt);
    }

}
