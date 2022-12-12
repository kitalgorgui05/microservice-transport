package com.memoire.kital.raph.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TransportClient {

    private String id;

    @NotNull
    @Size(min = 3, max = 10)
    private String nom;

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
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransportClient)) {
            return false;
        }

        return id != null && id.equals(((TransportClient) o).id);
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
            ", nom='" + getNom() +
            "}";
    }
}
