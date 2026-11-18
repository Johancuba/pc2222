package com.campuslab.platform.u202417448.reservations.domain.model.commands;

import com.campuslab.platform.u202417448.reservations.domain.model.valueobjects.ReservationStatus;

import java.time.LocalDate;
import java.util.UUID;

public record CreateEquipmentReservationCommand(
        String reservationCode, UUID laboratoryId, LocalDate reservationPeriodStartDate,
        LocalDate reservationPeriodEndDate, Integer equipmentBatchQuantity, String equipmentBatchUnit,
        Integer accessLevel, ReservationStatus status, String reservationNotes) {
}
