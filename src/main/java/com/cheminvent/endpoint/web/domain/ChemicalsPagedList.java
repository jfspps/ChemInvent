package com.cheminvent.endpoint.web.domain;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class ChemicalsPagedList extends PageImpl<ChemicalDTO> {

    public ChemicalsPagedList(List<ChemicalDTO> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public ChemicalsPagedList(List<ChemicalDTO> content) {
        super(content);
    }

}
