package com.cheminvent.endpoint.web.controllers;

import com.cheminvent.endpoint.repositories.ChemicalRepository;
import com.cheminvent.endpoint.web.domain.ChemicalDTO;
import com.cheminvent.endpoint.web.mappers.ChemicalsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/chemicals")
@Controller
public class ChemicalController {

    private final ChemicalsMapper chemicalsMapper;
    private final ChemicalRepository chemicalRepository;

    @GetMapping("/{chemicalsID}")
    public ResponseEntity<ChemicalDTO> getChemicalsById(@PathVariable("chemicalsID") UUID chemicals_Id){

        return new ResponseEntity<>(chemicalsMapper.ChemicalToChemicalDTO(chemicalRepository.findById(chemicals_Id).get()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveNewChemicals(@RequestBody @Validated ChemicalDTO chemicalDTO){

        chemicalRepository.save(chemicalsMapper.ChemicalDTOToChemical(chemicalDTO));

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{chemicalsID}")
    public ResponseEntity updateChemicalsById(@PathVariable("chemicalsID") UUID chemicals_Id, @RequestBody @Validated ChemicalDTO chemicalDTO){
        chemicalRepository.findById(chemicals_Id).ifPresent(chemical -> {
            chemical.setCAS_reg(chemicalDTO.getCAS_reg());
            chemical.setName(chemicalDTO.getName());
            chemical.setStockQuantity(chemicalDTO.getStockQuantity());
            chemical.setReagentState(chemicalDTO.getReagentState());

            chemicalRepository.save(chemical);
        });

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
