package com.campuslab.platform.u202417448.reservations.infrastructure.persistence.jpa;

import com.campuslab.platform.u202417448.reservations.application.commandservices.EquipmentReservationCommandFailure;
import com.campuslab.platform.u202417448.reservations.application.commandservices.EquipmentReservationStore;
import com.campuslab.platform.u202417448.reservations.domain.model.aggregates.EquipmentReservation;
import com.campuslab.platform.u202417448.shared.application.result.Result;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Locale;

@Repository
public class EquipmentReservationRepositoryAdapter implements EquipmentReservationStore {
    private final EquipmentReservationRepository repository;

    public EquipmentReservationRepositoryAdapter(EquipmentReservationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result<EquipmentReservation, EquipmentReservationCommandFailure> insert(EquipmentReservation reservation) {
        try {
            var entity = repository.saveAndFlush(new EquipmentReservationEntity(reservation));
            return Result.success(reservation.withId(entity.getId()));
        } catch (DataAccessException exception) {
            if (isDuplicateCode(exception)) {
                return Result.failure(EquipmentReservationCommandFailure.DUPLICATE);
            }
            return Result.failure(EquipmentReservationCommandFailure.UNEXPECTED);
        }
    }

    private boolean isDuplicateCode(Throwable exception) {
        String constraintName = EquipmentReservationEntity.RESERVATION_CODE_UNIQUE;
        for (Throwable cause = exception; cause != null; cause = cause.getCause()) {
            if (cause instanceof ConstraintViolationException violation
                    && violation.getConstraintName() != null
                    && violation.getConstraintName().toLowerCase(Locale.ROOT).endsWith(constraintName)) {
                return true;
            }
            if (cause instanceof SQLException sqlException && sqlException.getErrorCode() == 1062
                    && sqlException.getMessage() != null && sqlException.getMessage().contains(constraintName)) {
                return true;
            }
        }
        return false;
    }
}
