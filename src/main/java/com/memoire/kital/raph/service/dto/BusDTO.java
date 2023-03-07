package com.memoire.kital.raph.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class BusDTO implements Serializable {
    private String id;
    @NotNull
    private String matricule;
    @NotNull
    private String numero;
    @NotNull
    private Integer nombreplace;
    private ChauffeurDTO chauffeur;

    // getter and setter
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
    public ChauffeurDTO getChauffeur() {
        return chauffeur;
    }
    public void setChauffeur(ChauffeurDTO chauffeur) {
        this.chauffeur = chauffeur;
    }


    //all constructor
    public BusDTO(String id, String matricule, String numero, Integer nombreplace, ChauffeurDTO chauffeur) {
        this.id = id;
        this.matricule = matricule;
        this.numero = numero;
        this.nombreplace = nombreplace;
        this.chauffeur = chauffeur;
    }

    public BusDTO(String id) {
        this.id = id;
    }

    public BusDTO() {
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
    @Override
    public String toString() {
        return "BusDTO{" +
            "id=" + getId() +
            ", matricule='" + getMatricule() + "'" +
            ", numero='" + getNumero() + "'" +
            ", nombreplace=" + getNombreplace() +
            ", chauffeurId=" + getChauffeur() +
            "}";
    }
}
