package com.memoire.kital.raph.service.mapper;


import com.memoire.kital.raph.domain.*;
import com.memoire.kital.raph.service.dto.GroupeTransportDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link GroupeTransport} and its DTO {@link GroupeTransportDTO}.
 */
@Mapper(componentModel = "spring", uses = {ZoneMapper.class})
public interface GroupeTransportMapper extends EntityMapper<GroupeTransportDTO, GroupeTransport> {


    @Mapping(target = "removeZones", ignore = true)

    default GroupeTransport fromId(String id) {
        if (id == null) {
            return null;
        }
        GroupeTransport groupeTransport = new GroupeTransport();
        groupeTransport.setId(id);
        return groupeTransport;
    }
}
