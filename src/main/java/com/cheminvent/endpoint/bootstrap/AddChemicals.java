package com.cheminvent.endpoint.bootstrap;

import com.cheminvent.endpoint.model.Chemical;
import com.cheminvent.endpoint.repositories.ChemicalRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AddChemicals implements CommandLineRunner {

    private final ChemicalRepository chemicalRepository;

    public AddChemicals(ChemicalRepository chemicalRepository) {
        this.chemicalRepository = chemicalRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (chemicalRepository.count() == 0){

            chemicalRepository.save(Chemical.builder()
            .CAS_reg("108-24-7")
            .name("Acetic Anhydride")
            .stockQuantity(3).build());

            chemicalRepository.save(Chemical.builder()
                    .CAS_reg("67-64-1")
                    .name("Acetone")
                    .stockQuantity(2).build());

            chemicalRepository.save(Chemical.builder()
                    .CAS_reg("7681-11-0")
                    .name("Potassium Iodide")
                    .stockQuantity(10).build());
        }

        log.info("Added " + chemicalRepository.count() + " items(s) to the database");
    }
}
