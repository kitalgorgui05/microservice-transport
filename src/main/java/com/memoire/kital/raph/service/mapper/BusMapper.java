package com.memoire.kital.raph.service.mapper;


import com.memoire.kital.raph.domain.*;
import com.memoire.kital.raph.service.dto.BusDTO;

import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {ChauffeurMapper.class})
public interface BusMapper extends EntityMapper<BusDTO, Bus> {

    @Mapping(source = "chauffeur.id", target = "chauffeurId")
    BusDTO toDto(Bus bus);

    @Mapping(source = "chauffeurId", target = "chauffeur")
    Bus toEntity(BusDTO busDTO);

    default Bus fromId(String id) {
        if (id == null) {
            return null;
        }
        Bus bus = new Bus();
        bus.setId(id);
        return bus;
    }
}
