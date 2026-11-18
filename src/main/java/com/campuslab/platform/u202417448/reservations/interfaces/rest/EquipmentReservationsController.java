package com.campuslab.platform.u202417448.reservations.interfaces.rest;

import com.campuslab.platform.u202417448.reservations.application.commandservices.EquipmentReservationCommandService;
import com.campuslab.platform.u202417448.reservations.interfaces.rest.resources.CreateEquipmentReservationResource;
import com.campuslab.platform.u202417448.reservations.interfaces.rest.resources.EquipmentReservationResource;
import com.campuslab.platform.u202417448.reservations.interfaces.rest.transform.CreateEquipmentReservationCommandFromResourceAssembler;
import com.campuslab.platform.u202417448.reservations.interfaces.rest.transform.ResponseEntityFromEquipmentReservationCommandResultAssembler;
import com.campuslab.platform.u202417448.shared.interfaces.rest.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping(path = "/api/v1/equipment-reservations", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Equipment reservations", description = "Register equipment reservations for a laboratory.")
public class EquipmentReservationsController {
    private final EquipmentReservationCommandService commandService;
    private final MessageSource messages;

    public EquipmentReservationsController(EquipmentReservationCommandService commandService, MessageSource messages) {
        this.commandService = commandService;
        this.messages = messages;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create an equipment reservation", responses = {
            @ApiResponse(responseCode = "201", description = "Reservation created",
                    content = @Content(schema = @Schema(implementation = EquipmentReservationResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid reservation data",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Duplicate reservation code",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> createEquipmentReservation(@Valid @RequestBody CreateEquipmentReservationResource resource,
                                                        Locale locale) {
        var command = CreateEquipmentReservationCommandFromResourceAssembler.toCommandFromResource(resource);
        return ResponseEntityFromEquipmentReservationCommandResultAssembler.toResponseEntityFromResult(
                commandService.handle(command), messages, locale);
    }
}
