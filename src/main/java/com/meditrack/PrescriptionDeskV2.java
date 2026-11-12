package com.meditrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Composition root: the Spring container creates the DataSource, the
 * repository proxy and the service, then injects them. main() only starts the
 * container, asks for the wired service and runs V1's calls.
 */
@SpringBootApplication
public class PrescriptionDeskV2 {

    public static void main(String[] args) {
        try (ConfigurableApplicationContext context =
                SpringApplication.run(PrescriptionDeskV2.class, args)) {
            PrescriptionService service = context.getBean(PrescriptionService.class);

            prescribeSafely(service, "Maria", "Metformin", 500);
            prescribeSafely(service, "Maria", "Amlodipine", 5);
            prescribeSafely(service, "Ahmad", "Paracetamol", 1000);
            prescribeSafely(service, "Ahmad", "Ibuprofen", 1200);

            System.out.println(service.report("Maria"));
            System.out.println(service.report("Ahmad"));
            System.out.println(service.report("x' OR '1'='1"));
            prescribeSafely(service, "O'Brien", "Insulin", 10);

            System.out.println(service.report("O'Brien"));
        }
    }

    private static void prescribeSafely(PrescriptionService service,
            String patient, String drug, int mg) {
        try {
            service.prescribe(patient, drug, mg);
        } catch (IllegalArgumentException rejected) {
            System.out.println("REJECTED " + drug);
        }
    }
}
