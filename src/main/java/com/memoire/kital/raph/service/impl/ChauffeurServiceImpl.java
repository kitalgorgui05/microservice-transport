package com.memoire.kital.raph.service.impl;

import com.memoire.kital.raph.service.ChauffeurService;
import com.memoire.kital.raph.domain.Chauffeur;
import com.memoire.kital.raph.repository.ChauffeurRepository;
import com.memoire.kital.raph.service.dto.ChauffeurDTO;
import com.memoire.kital.raph.service.mapper.ChauffeurMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Chauffeur}.
 */
@Service
@Transactional
public class ChauffeurServiceImpl implements ChauffeurService {

    private final Logger log = LoggerFactory.getLogger(ChauffeurServiceImpl.class);

    private final ChauffeurRepository chauffeurRepository;

    private final ChauffeurMapper chauffeurMapper;

    public ChauffeurServiceImpl(ChauffeurRepository chauffeurRepository, ChauffeurMapper chauffeurMapper) {
        this.chauffeurRepository = chauffeurRepository;
        this.chauffeurMapper = chauffeurMapper;
    }

    @Override
    public ChauffeurDTO save(ChauffeurDTO chauffeurDTO) {
        log.debug("Request to save Chauffeur : {}", chauffeurDTO);
        Chauffeur chauffeur = chauffeurMapper.toEntity(chauffeurDTO);
        chauffeur = chauffeurRepository.save(chauffeur);
        return chauffeurMapper.toDto(chauffeur);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ChauffeurDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Chauffeurs");
        return chauffeurRepository.findAll(pageable)
            .map(chauffeurMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ChauffeurDTO> findOne(Long id) {
        log.debug("Request to get Chauffeur : {}", id);
        return chauffeurRepository.findById(id)
            .map(chauffeurMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Chauffeur : {}", id);
        chauffeurRepository.deleteById(id);
    }
}
