package com.campuslab.platform.u202417448.reservations.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import io.swagger.v3.oas.annotations.media.Schema;
import com.campuslab.platform.u202417448.reservations.domain.model.valueobjects.ReservationStatus;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public class CreateEquipmentReservationResource {
    @NotNull(message = "{reservation.code.required}")
    public String reservationCode;
    @NotNull(message = "{reservation.lab.required}")
    public UUID laboratoryId;
    @NotNull(message = "{reservation.dates.required}")
    public LocalDate reservationPeriodStartDate;
    @NotNull(message = "{reservation.dates.required}")
    public LocalDate reservationPeriodEndDate;
    @NotNull(message = "{reservation.quantity.invalid}")
    public Integer equipmentBatchQuantity;
    @NotNull(message = "{reservation.unit.required}")
    public String equipmentBatchUnit;
    @NotNull(message = "{reservation.access.invalid}")
    public Integer accessLevel;
    @JsonSetter(nulls = Nulls.FAIL)
    @Schema(defaultValue = "PENDING")
    public ReservationStatus status = ReservationStatus.PENDING;
    public String reservationNotes;
}
