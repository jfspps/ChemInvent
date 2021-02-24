package com.cheminvent.endpoint.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChemicalDTO {

    private String CAS_reg;
    private String name;
    private Integer stockQuantity;
}
