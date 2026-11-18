package com.campuslab.platform.u202417448.reservations.domain.model.valueobjects;

public record EquipmentBatch(Integer quantity, String unit) {
    public EquipmentBatch {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Equipment quantity must be greater than zero.");
        }
        if (unit == null || unit.isBlank()) {
            throw new IllegalArgumentException("Equipment unit is required.");
        }
    }

    public EquipmentBatch addUnits(Integer additionalQuantity) {
        if (additionalQuantity == null || additionalQuantity <= 0) {
            throw new IllegalArgumentException("Additional quantity must be greater than zero.");
        }
        if (additionalQuantity > Integer.MAX_VALUE - quantity) {
            throw new IllegalArgumentException("Equipment quantity exceeds the maximum integer value.");
        }
        return new EquipmentBatch(quantity + additionalQuantity, unit);
    }
}
