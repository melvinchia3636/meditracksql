package com.meditrack;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class PrescriptionTest {

    @Test
    void lowerBound_isAccepted() {
        assertDoesNotThrow(() -> new Prescription("Maria", "Metformin", Prescription.MIN_DOSE));
    }

    @Test
    void upperBound_isAccepted() {
        assertDoesNotThrow(() -> new Prescription("Maria", "Metformin", Prescription.MAX_DOSE));
    }

    @Test
    void doseBelowRange_isRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new Prescription("Maria", "Metformin", Prescription.MIN_DOSE - 1));
    }

    @Test
    void doseAboveRange_isRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new Prescription("Maria", "Metformin", Prescription.MAX_DOSE + 1));
    }

    @Test
    void blankPatient_isRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new Prescription("  ", "Metformin", 500));
    }

    @Test
    void blankDrug_isRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new Prescription("Maria", "", 500));
    }
}
