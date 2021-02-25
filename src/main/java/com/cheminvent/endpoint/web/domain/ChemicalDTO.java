package com.cheminvent.endpoint.web.domain;

import com.cheminvent.endpoint.model.ReagentState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.OffsetDateTime;
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
    private OffsetDateTime createdDate;

    @Null
    private OffsetDateTime lastModifiedDate;

    // the following are returned in the DTO returned
    @NotNull
    private ReagentState reagentState;

    @NotNull
    private String CAS_reg;

    @NotBlank
    @Size(min = 1)
    private String name;

    @NotNull
    private Integer stockQuantity;
}
