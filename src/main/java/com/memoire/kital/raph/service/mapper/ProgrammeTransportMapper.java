package com.memoire.kital.raph.service.mapper;


import com.memoire.kital.raph.domain.*;
import com.memoire.kital.raph.service.dto.ProgrammeTransportDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProgrammeTransport} and its DTO {@link ProgrammeTransportDTO}.
 */
@Mapper(componentModel = "spring", uses = {GroupeTransportMapper.class, BusMapper.class})
public interface ProgrammeTransportMapper extends EntityMapper<ProgrammeTransportDTO, ProgrammeTransport> {

    @Mapping(source = "groupeTransport.id", target = "groupeTransportId")
    @Mapping(source = "bus.id", target = "busId")
    ProgrammeTransportDTO toDto(ProgrammeTransport programmeTransport);

    @Mapping(source = "groupeTransportId", target = "groupeTransport")
    @Mapping(source = "busId", target = "bus")
    ProgrammeTransport toEntity(ProgrammeTransportDTO programmeTransportDTO);

    default ProgrammeTransport fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProgrammeTransport programmeTransport = new ProgrammeTransport();
        programmeTransport.setId(id);
        return programmeTransport;
    }
}