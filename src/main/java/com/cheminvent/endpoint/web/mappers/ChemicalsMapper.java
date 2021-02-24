package com.cheminvent.endpoint.web.mappers;

import com.cheminvent.endpoint.model.Chemical;
import com.cheminvent.endpoint.web.domain.ChemicalDTO;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface ChemicalsMapper {
    ChemicalDTO ChemicalToChemicalDTO(Chemical chemical);

    Chemical ChemicalDTOToChemical(ChemicalDTO chemicalDTO);
}
