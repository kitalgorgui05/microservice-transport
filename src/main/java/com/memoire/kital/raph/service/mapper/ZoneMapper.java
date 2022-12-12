package com.memoire.kital.raph.service.mapper;


import com.memoire.kital.raph.domain.*;
import com.memoire.kital.raph.service.dto.ZoneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Zone} and its DTO {@link ZoneDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ZoneMapper extends EntityMapper<ZoneDTO, Zone> {


    @Mapping(target = "groupetransports", ignore = true)
    @Mapping(target = "removeGroupetransports", ignore = true)
    Zone toEntity(ZoneDTO zoneDTO);

    default Zone fromId(String id) {
        if (id == null) {
            return null;
        }
        Zone zone = new Zone();
        zone.setId(id);
        return zone;
    }
}
