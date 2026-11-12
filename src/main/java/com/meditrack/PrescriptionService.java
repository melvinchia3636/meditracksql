package com.meditrack;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrescriptionService {

    private final PrescriptionRepository repository;

    @Autowired
    public PrescriptionService(PrescriptionRepository repository) {
        this.repository = repository;
    }

    /**
     * The entity constructor validates; invalid input throws
     * {@link IllegalArgumentException} and nothing is stored.
     */
    public void prescribe(String patient, String drug, int mg) {
        repository.save(new Prescription(patient, drug, mg));
    }

    public String report(String patient) {
        return repository.findByPatient(patient).stream()
                .sorted(Comparator.comparing(Prescription::drug))
                .map(p -> " " + p.drug() + "(" + p.mg() + "mg)")
                .collect(Collectors.joining("", patient + ":", ""));
    }

    public SortedSet<String> uniqueDrugs(String patient) {
        return repository.findByPatient(patient).stream()
                .map(Prescription::drug)
                .collect(Collectors.toCollection(TreeSet::new));
    }
}
