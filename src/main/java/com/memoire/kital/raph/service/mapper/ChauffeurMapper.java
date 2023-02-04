package com.memoire.kital.raph.service.mapper;


import com.memoire.kital.raph.domain.*;
import com.memoire.kital.raph.service.dto.ChauffeurDTO;

import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {})
public interface ChauffeurMapper extends EntityMapper<ChauffeurDTO, Chauffeur> {

    default Chauffeur fromId(String id) {
        if (id == null) {
            return null;
        }
        Chauffeur chauffeur = new Chauffeur();
        chauffeur.setId(id);
        return chauffeur;
    }
}
