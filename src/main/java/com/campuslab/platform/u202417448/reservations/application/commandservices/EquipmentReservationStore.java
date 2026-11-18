package com.campuslab.platform.u202417448.reservations.application.commandservices;

import com.campuslab.platform.u202417448.reservations.domain.model.aggregates.EquipmentReservation;
import com.campuslab.platform.u202417448.shared.application.result.Result;

public interface EquipmentReservationStore {
    Result<EquipmentReservation, EquipmentReservationCommandFailure> insert(EquipmentReservation reservation);
}
