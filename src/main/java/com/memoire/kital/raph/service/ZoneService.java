package com.memoire.kital.raph.service;

import com.memoire.kital.raph.service.dto.ZoneDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ZoneService {
    ZoneDTO save(ZoneDTO zoneDTO);
    Page<ZoneDTO> findAll(Pageable pageable);
    Optional<ZoneDTO> findOne(String id);
    void delete(String id);
}
