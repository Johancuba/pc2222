package com.campuslab.platform.u202417448.reservations.interfaces.rest.resources;

public record EquipmentReservationResource(
        Long id, String reservationCode, String laboratoryId, String reservationPeriodStartDate,
        String reservationPeriodEndDate, Integer equipmentBatchQuantity, String equipmentBatchUnit,
        Integer accessLevel, String status, String reservationNotes) {
}
