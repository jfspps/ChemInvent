package com.cheminvent.endpoint.web.controllers;

import com.cheminvent.endpoint.model.Chemical;
import com.cheminvent.endpoint.model.ReagentState;
import com.cheminvent.endpoint.repositories.ChemicalRepository;
import com.cheminvent.endpoint.web.domain.ChemicalDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ChemicalController.class)
@ComponentScan(basePackages = "com.cheminvent.endpoint.web.mappers")
class ChemicalControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ChemicalRepository chemicalRepository;

    ChemicalDTO getValidChemicalDTO() {
        return ChemicalDTO.builder()
                .name("Water")
                .CAS_reg("7732-18-5")
                .stockQuantity(100)
                .reagentState(ReagentState.LIQUID)
                .build();
    }

    @Test
    void getChemicalsById() throws Exception {
        given(chemicalRepository.findById(any())).willReturn(Optional.of(Chemical.builder().build()));

        mockMvc.perform(get("/api/v1/chemicals/" + UUID.randomUUID().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void saveNewChemicals() throws Exception {
        ChemicalDTO chemicalDTO = getValidChemicalDTO();
        String chemicalDTOToJSON = objectMapper.writeValueAsString(chemicalDTO);

        mockMvc.perform(put("/api/v1/chemicals/" + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(chemicalDTOToJSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateChemicalsById() throws Exception {
        ChemicalDTO chemicalDTO = getValidChemicalDTO();
        String chemicalDTOToJSON = objectMapper.writeValueAsString(chemicalDTO);

        mockMvc.perform(put("/api/v1/chemicals/" + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(chemicalDTOToJSON))
                .andExpect(status().isNoContent());
    }
}