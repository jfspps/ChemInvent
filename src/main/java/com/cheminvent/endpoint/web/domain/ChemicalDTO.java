package com.cheminvent.endpoint.web.domain;

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

//    @Null
//    private UUID id;
//
//    @Null
//    private Long version;
//
//    @Null
//    private Timestamp createdDate;
//
//    @Null
//    private Timestamp lastModifiedDate;

    @NotNull
    private String CAS_reg;

    @NotBlank
    private String name;

    @Positive
    private Integer stockQuantity;
}
