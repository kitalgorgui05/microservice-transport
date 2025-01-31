package com.memoire.kital.raph.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class GroupeTransportDTO implements Serializable {
    private String id;
    @NotNull
    @Size(min = 3, max = 10)
    private String nom;
    @NotNull
    private Integer nombreEleves;

    private Set<ZoneDTO> zones = new HashSet<>();
    public String getId() {
        return id;
    }
    public void setId(String id) {
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
    public Set<ZoneDTO> getZones() {
        return zones;
    }
    public void setZones(Set<ZoneDTO> zones) {
        this.zones = zones;
    }

    public GroupeTransportDTO(String id, String nom, Integer nombreEleves) {
        this.id = id;
        this.nom = nom;
        this.nombreEleves = nombreEleves;
    }

    public GroupeTransportDTO() {
    }

    public GroupeTransportDTO(String id) {
        this.id = id;
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
    @Override
    public String toString() {
        return "GroupeTransportDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", nombreEleves=" + getNombreEleves() +
            ", zones='" + getZones() + "'" +
            "}";
    }
}
