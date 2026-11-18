package com.campuslab.platform.u202417448.reservations.domain.model.valueobjects;

import java.time.LocalDate;

public record ReservationPeriod(LocalDate startDate, LocalDate endDate) {
    public ReservationPeriod {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date are required.");
        }
        if (!endDate.isAfter(startDate)) {
            throw new IllegalArgumentException("End date must be after start date.");
        }
    }

    public boolean contains(LocalDate checkDate) {
        if (checkDate == null) {
            throw new IllegalArgumentException("Date to check is required.");
        }
        return !checkDate.isBefore(startDate) && !checkDate.isAfter(endDate);
    }
}
