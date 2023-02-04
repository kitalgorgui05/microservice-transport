package com.memoire.kital.raph.service;

import com.memoire.kital.raph.service.dto.ChauffeurDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ChauffeurService {

    ChauffeurDTO save(ChauffeurDTO chauffeurDTO);
    Page<ChauffeurDTO> findAll(Pageable pageable);
    Optional<ChauffeurDTO> findOne(String id);
    String getNomChauffeur(String id);
    void delete(String id);
}
