package com.memoire.kital.raph.service;

import com.memoire.kital.raph.service.dto.GroupeTransportDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface GroupeTransportService {

    GroupeTransportDTO save(GroupeTransportDTO groupeTransportDTO);
    Page<GroupeTransportDTO> findAll(Pageable pageable);
    Page<GroupeTransportDTO> findAllWithEagerRelationships(Pageable pageable);
    Optional<GroupeTransportDTO> findOne(String id);
    void delete(String id);
}
