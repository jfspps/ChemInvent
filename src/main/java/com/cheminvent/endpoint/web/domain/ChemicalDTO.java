package com.cheminvent.endpoint.web.domain;

import com.cheminvent.endpoint.model.ReagentState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChemicalDTO {

    // note that the DTO object need not reveal all Chemical properties
    @Null
    private UUID id;

    @Null
    private Long version;

    @Null
    private Timestamp createdDate;

    @Null
    private Timestamp lastModifiedDate;

    // the following are returned in the DTO returned
    @NotNull
    private ReagentState reagentState;

    @NotNull
    private String CAS_reg;

    @NotBlank
    private String name;

    @Positive
    private Integer stockQuantity;
}
