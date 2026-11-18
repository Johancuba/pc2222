package com.campuslab.platform.u202417448.reservations.application.internal.commandservices;

import com.campuslab.platform.u202417448.reservations.application.commandservices.*;
import com.campuslab.platform.u202417448.reservations.domain.model.aggregates.EquipmentReservation;
import com.campuslab.platform.u202417448.reservations.domain.model.commands.CreateEquipmentReservationCommand;
import com.campuslab.platform.u202417448.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class EquipmentReservationCommandServiceImpl implements EquipmentReservationCommandService {
    private final EquipmentReservationStore store;

    public EquipmentReservationCommandServiceImpl(EquipmentReservationStore store) {
        this.store = store;
    }

    @Override
    public Result<EquipmentReservation, EquipmentReservationCommandFailure> handle(CreateEquipmentReservationCommand command) {
        return store.insert(new EquipmentReservation(command));
    }
}
