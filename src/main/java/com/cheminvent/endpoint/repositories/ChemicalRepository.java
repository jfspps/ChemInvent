package com.cheminvent.endpoint.repositories;

import com.cheminvent.endpoint.model.Chemical;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface ChemicalRepository extends PagingAndSortingRepository<Chemical, UUID> {
}
