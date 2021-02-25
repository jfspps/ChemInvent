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
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

// start documenting controller methods (updates get(), put() etc.)
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// setup MockMvc with REST docs
@ExtendWith(RestDocumentationExtension.class)
// customise this annotation if you want to build docs on an API running from a remote server
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "com.yourPage.something", uriPort = 80)
@WebMvcTest(ChemicalController.class)
@ComponentScan(basePackages = "com.cheminvent.endpoint.web.mappers")
class ChemicalControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ChemicalRepository chemicalRepository;

    private ChemicalDTO getValidChemicalDTO() {
        return ChemicalDTO.builder()
                .name("Water")
                .CAS_reg("7732-18-5")
                .stockQuantity(100)
                .reagentState(ReagentState.LIQUID)
                .build();
    }

    // set up Constraints fields (required for request-fields fields descriptors)
    private static class ConstrainedFields {

        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                            .descriptionsForProperty(path), ". ")));
        }
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
                                fieldWithPath("version").description("API version number"),
                                fieldWithPath("createdDate").description("Date when record was created"),
                                fieldWithPath("lastModifiedDate").description("Date when record was last modified"),
                                fieldWithPath("reagentState").description("Physical state of the reagent at RTP"),
                                fieldWithPath("name").description("Catalogue name of reagent"),
                                fieldWithPath("stockQuantity").description("Quantity of reagent available"),
                                fieldWithPath("cas_reg").description("Chemical Abstract Service registry number")
                        )));
    }

    // POST new Chemical entity
    @Test
    void saveNewChemicals() throws Exception {
        ChemicalDTO chemicalDTO = getValidChemicalDTO();
        String chemicalDTOToJSON = objectMapper.writeValueAsString(chemicalDTO);

        // add constraints
        ConstrainedFields fields = new ConstrainedFields(ChemicalDTO.class);

        // document the persistence layer objects (DTO converted to model, then saved);
        // note that some DTO fields are ignored
        mockMvc.perform(post("/api/v1/chemicals/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(chemicalDTOToJSON))
                .andExpect(status().isCreated())
                .andDo(document("v1/chemicals",
                        requestFields(
                                fields.withPath("id").ignored(),
                                fields.withPath("version").ignored(),
                                fields.withPath("createdDate").ignored(),
                                fields.withPath("lastModifiedDate").ignored(),
                                fields.withPath("reagentState").description("Physical state of the reagent at RTP"),
                                fields.withPath("name").description("Catalogue name of reagent"),
                                fields.withPath("stockQuantity").description("Quantity of reagent available"),
                                fields.withPath("cas_reg").description("Chemical Abstract Service registry number")
                        )));
    }

    // PUT or update chemical entity
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