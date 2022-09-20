package com.memoire.kital.raph.service.mapper;


import com.memoire.kital.raph.domain.*;
import com.memoire.kital.raph.service.dto.BusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Bus} and its DTO {@link BusDTO}.
 */
@Mapper(componentModel = "spring", uses = {ChauffeurMapper.class})
public interface BusMapper extends EntityMapper<BusDTO, Bus> {

    @Mapping(source = "chauffeur.id", target = "chauffeurId")
    BusDTO toDto(Bus bus);

    @Mapping(source = "chauffeurId", target = "chauffeur")
    Bus toEntity(BusDTO busDTO);

    default Bus fromId(Long id) {
        if (id == null) {
            return null;
        }
        Bus bus = new Bus();
        bus.setId(id);
        return bus;
    }
}
