package com.meditrack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class PrescriptionRepositoryTest {

    @Autowired
    private PrescriptionRepository repository;

    @Test
    void saveThenFind_roundTripsPrescription() {
        repository.save(new Prescription("O'Brien", "Insulin", 10));

        List<Prescription> found = repository.findByPatient("O'Brien");

        assertEquals(1, found.size());
        assertEquals("O'Brien", found.get(0).patient());
        assertEquals("Insulin", found.get(0).drug());
        assertEquals(10, found.get(0).mg());
    }

    @Test
    void findByPatient_injectionString_returnsNoRows() {
        repository.save(new Prescription("Maria", "Metformin", 500));

        assertTrue(repository.findByPatient("x' OR '1'='1").isEmpty());
    }

    @Test
    void findByPatient_unknownPatient_returnsEmptyListNotNull() {
        assertTrue(repository.findByPatient("Nobody").isEmpty());
    }
}
