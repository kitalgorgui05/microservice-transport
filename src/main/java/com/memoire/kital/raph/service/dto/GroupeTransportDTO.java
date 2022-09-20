package com.memoire.kital.raph.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link com.memoire.kital.raph.domain.GroupeTransport} entity.
 */
public class GroupeTransportDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(min = 3, max = 10)
    private String nom;

    @NotNull
    private Integer nombreEleves;

    private Boolean etat;

    private Set<ZoneDTO> zones = new HashSet<>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getNombreEleves() {
        return nombreEleves;
    }

    public void setNombreEleves(Integer nombreEleves) {
        this.nombreEleves = nombreEleves;
    }

    public Boolean isEtat() {
        return etat;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    public Set<ZoneDTO> getZones() {
        return zones;
    }

    public void setZones(Set<ZoneDTO> zones) {
        this.zones = zones;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GroupeTransportDTO)) {
            return false;
        }

        return id != null && id.equals(((GroupeTransportDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GroupeTransportDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", nombreEleves=" + getNombreEleves() +
            ", etat='" + isEtat() + "'" +
            ", zones='" + getZones() + "'" +
            "}";
    }
}