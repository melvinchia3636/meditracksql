package com.meditrack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;

class PrescriptionServiceTest {

    private final PrescriptionRepository repository = mock(PrescriptionRepository.class);
    private final PrescriptionService service = new PrescriptionService(repository);

    @Test
    void prescribe_invalidDose_isRejectedAndNotStored() {
        assertThrows(IllegalArgumentException.class,
                () -> service.prescribe("Ahmad", "Ibuprofen", 1200));

        verifyNoInteractions(repository);
    }

    @Test
    void report_listsDrugsSortedWithDoses() {
        when(repository.findByPatient("Maria")).thenReturn(List.of(
                new Prescription("Maria", "Metformin", 500),
                new Prescription("Maria", "Amlodipine", 5)));

        assertEquals("Maria: Amlodipine(5mg) Metformin(500mg)", service.report("Maria"));
    }

    @Test
    void report_patientWithoutPrescriptions_returnsNameAndColon() {
        when(repository.findByPatient("Nobody")).thenReturn(List.of());

        assertEquals("Nobody:", service.report("Nobody"));
    }

    @Test
    void uniqueDrugs_returnsSortedDistinctNames() {
        when(repository.findByPatient("Maria")).thenReturn(List.of(
                new Prescription("Maria", "Metformin", 500),
                new Prescription("Maria", "Metformin", 850),
                new Prescription("Maria", "Amlodipine", 5)));

        assertEquals(List.of("Amlodipine", "Metformin"),
                List.copyOf(service.uniqueDrugs("Maria")));
    }
}
