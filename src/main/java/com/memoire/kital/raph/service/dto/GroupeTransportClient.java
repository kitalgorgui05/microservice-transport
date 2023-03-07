package com.memoire.kital.raph.service.dto;

import java.util.HashSet;
import java.util.Set;

public class GroupeTransportClient {
    private String id;
    private String nom;
    private Integer nombreEleves;
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
    public GroupeTransportClient(String id, String nom, Integer nombreEleves) {
        this.id = id;
        this.nom = nom;
        this.nombreEleves = nombreEleves;
    }

    public GroupeTransportClient() {
    }

    public GroupeTransportClient(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GroupeTransportClient)) {
            return false;
        }
        return id != null && id.equals(((GroupeTransportClient) o).id);
    }
    @Override
    public int hashCode() {
        return 31;
    }
    @Override
    public String toString() {
        return "GroupeTransportClient{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", nombreEleves=" + getNombreEleves() +
            "}";
    }
}
