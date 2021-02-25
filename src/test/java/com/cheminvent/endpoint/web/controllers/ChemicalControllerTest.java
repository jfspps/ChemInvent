package com.cheminvent.endpoint.web.controllers;

import com.cheminvent.endpoint.model.Chemical;
import com.cheminvent.endpoint.model.ReagentState;
import com.cheminvent.endpoint.repositories.ChemicalRepository;
import com.cheminvent.endpoint.web.domain.ChemicalDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

// start documenting controller methods
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// setup MockMvc with REST docs
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
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

        // instead of adding chemicalId as a path parameter, pass a random UUID to chemicalId and request REST docs to document
        // said path parameter; query parameter not part of controller but documented anyway
        mockMvc.perform(get("/api/v1/chemicals/{chemicalId}", UUID.randomUUID().toString())
                .param("isAnalyticalSample", "no")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        .andDo(document("v1/chemicals",
                pathParameters(parameterWithName("chemicalId").description("UUID of given chemical reagent")),
                requestParameters(parameterWithName("isAnalyticalSample").description("Reagent is of analytical grade")),
                // document DTO properties returned from the API (note that none or all properties must be described
                responseFields(
                        fieldWithPath("id").description("Database ID of reagent"),
                        fieldWithPath("reagentState").description("Physical state of the reagent at RTP"),
                        fieldWithPath("name").description("Catalogue name of reagent"),
                        fieldWithPath("stockQuantity").description("Quantity of reagent available"),
                        fieldWithPath("cas_reg").description("Chemical Abstract Service registry number")
                )));
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