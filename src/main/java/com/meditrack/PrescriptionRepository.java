package com.meditrack;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data generates the implementation; there is no SQL in this project
 * outside the ORM mapping. Derived query methods keep callers persistence-
 * ignorant, and results are never {@code null}.
 */
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    List<Prescription> findByPatient(String patient);
}
