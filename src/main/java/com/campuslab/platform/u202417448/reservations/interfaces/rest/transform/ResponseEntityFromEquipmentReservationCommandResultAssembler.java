package com.campuslab.platform.u202417448.reservations.interfaces.rest.transform;

import com.campuslab.platform.u202417448.reservations.application.commandservices.EquipmentReservationCommandFailure;
import com.campuslab.platform.u202417448.reservations.domain.model.aggregates.EquipmentReservation;
import com.campuslab.platform.u202417448.shared.application.result.Result;
import com.campuslab.platform.u202417448.shared.interfaces.rest.ErrorResponse;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Locale;

public final class ResponseEntityFromEquipmentReservationCommandResultAssembler {
    private ResponseEntityFromEquipmentReservationCommandResultAssembler() {
    }

    public static ResponseEntity<?> toResponseEntityFromResult(
            Result<EquipmentReservation, EquipmentReservationCommandFailure> result,
            MessageSource messages, Locale locale) {
        return result.<ResponseEntity<?>>fold(
                reservation -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(EquipmentReservationResourceFromEntityAssembler.toResourceFromEntity(reservation)),
                failure -> switch (failure) {
                    case DUPLICATE -> ResponseEntity.status(HttpStatus.CONFLICT)
                            .body(new ErrorResponse(messages.getMessage("reservation.duplicate", null, locale)));
                    case UNEXPECTED -> ResponseEntity.internalServerError()
                            .body(new ErrorResponse(messages.getMessage("request.unexpected", null, locale)));
                });
    }
}
