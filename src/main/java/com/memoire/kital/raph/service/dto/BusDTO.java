package com.memoire.kital.raph.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.memoire.kital.raph.domain.Bus} entity.
 */
public class BusDTO implements Serializable {

    private String id;

    @NotNull
    private String matricule;

    @NotNull
    private String numero;

    @NotNull
    private Integer nombreplace;


    private String chauffeurId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Integer getNombreplace() {
        return nombreplace;
    }

    public void setNombreplace(Integer nombreplace) {
        this.nombreplace = nombreplace;
    }

    public String getChauffeurId() {
        return chauffeurId;
    }

    public void setChauffeurId(String chauffeurId) {
        this.chauffeurId = chauffeurId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BusDTO)) {
            return false;
        }

        return id != null && id.equals(((BusDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BusDTO{" +
            "id=" + getId() +
            ", matricule='" + getMatricule() + "'" +
            ", numero='" + getNumero() + "'" +
            ", nombreplace=" + getNombreplace() +
            ", chauffeurId=" + getChauffeurId() +
            "}";
    }
}
