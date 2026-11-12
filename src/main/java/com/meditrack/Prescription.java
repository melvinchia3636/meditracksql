package com.meditrack;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Domain object and JPA entity. The public constructor is the barricade: an
 * instance can only exist with present names and a dose within the allowed
 * range. JPA needs the protected no-arg constructor for rehydration.
 */
@Entity
@Table(name = "RX")
public class Prescription {

    public static final int MIN_DOSE = 1;
    public static final int MAX_DOSE = 1000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 40)
    private String patient;

    @Column(nullable = false, length = 40)
    private String drug;

    @Column(nullable = false)
    private int mg;

    protected Prescription() {
        // required by JPA
    }

    public Prescription(String patient, String drug, int mg) {
        if (patient == null || patient.isBlank()) {
            throw new IllegalArgumentException("patient name must not be blank");
        }
        if (drug == null || drug.isBlank()) {
            throw new IllegalArgumentException("drug name must not be blank");
        }
        if (mg < MIN_DOSE || mg > MAX_DOSE) {
            throw new IllegalArgumentException(
                    "dose must be between " + MIN_DOSE + " and " + MAX_DOSE + " mg: " + mg);
        }
        this.patient = patient;
        this.drug = drug;
        this.mg = mg;
    }

    public Long id() {
        return id;
    }

    public String patient() {
        return patient;
    }

    public String drug() {
        return drug;
    }

    public int mg() {
        return mg;
    }
}
