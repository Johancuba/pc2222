package com.campuslab.platform.u202417448.reservations.interfaces.rest.transform;

import com.campuslab.platform.u202417448.reservations.domain.model.commands.CreateEquipmentReservationCommand;
import com.campuslab.platform.u202417448.reservations.interfaces.rest.resources.CreateEquipmentReservationResource;

public final class CreateEquipmentReservationCommandFromResourceAssembler {
    private CreateEquipmentReservationCommandFromResourceAssembler() {
    }

    public static CreateEquipmentReservationCommand toCommandFromResource(CreateEquipmentReservationResource resource) {
        return new CreateEquipmentReservationCommand(resource.reservationCode, resource.laboratoryId,
                resource.reservationPeriodStartDate, resource.reservationPeriodEndDate, resource.equipmentBatchQuantity,
                resource.equipmentBatchUnit, resource.accessLevel, resource.status, resource.reservationNotes);
    }
}
