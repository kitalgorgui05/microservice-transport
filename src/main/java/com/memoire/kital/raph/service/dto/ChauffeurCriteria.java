package com.memoire.kital.raph.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

public class ChauffeurCriteria implements Serializable, Criteria {
    private StringFilter id;
    private StringFilter prenom;
    private StringFilter nom;
    private InstantFilter dateNaissance;
    private StringFilter lieuNaissance;
    private StringFilter cin;
    private StringFilter telephone;
    public ChauffeurCriteria() {
    }
    public ChauffeurCriteria(ChauffeurCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.prenom = other.prenom == null ? null : other.prenom.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.dateNaissance = other.dateNaissance == null ? null : other.dateNaissance.copy();
        this.lieuNaissance = other.lieuNaissance == null ? null : other.lieuNaissance.copy();
        this.cin = other.cin == null ? null : other.cin.copy();
        this.telephone = other.telephone == null ? null : other.telephone.copy();
    }
    @Override
    public ChauffeurCriteria copy() {
        return new ChauffeurCriteria(this);
    }
    public StringFilter getId() {
        return id;
    }
    public void setId(StringFilter id) {
        this.id = id;
    }
    public StringFilter getPrenom() {
        return prenom;
    }
    public void setPrenom(StringFilter prenom) {
        this.prenom = prenom;
    }
    public StringFilter getNom() {
        return nom;
    }
    public void setNom(StringFilter nom) {
        this.nom = nom;
    }
    public InstantFilter getDateNaissance() {
        return dateNaissance;
    }
    public void setDateNaissance(InstantFilter dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
    public StringFilter getLieuNaissance() {
        return lieuNaissance;
    }
    public void setLieuNaissance(StringFilter lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }
    public StringFilter getCin() {
        return cin;
    }
    public void setCin(StringFilter cin) {
        this.cin = cin;
    }
    public StringFilter getTelephone() {
        return telephone;
    }
    public void setTelephone(StringFilter telephone) {
        this.telephone = telephone;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ChauffeurCriteria that = (ChauffeurCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(prenom, that.prenom) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(dateNaissance, that.dateNaissance) &&
            Objects.equals(lieuNaissance, that.lieuNaissance) &&
            Objects.equals(cin, that.cin) &&
            Objects.equals(telephone, that.telephone);
    }
    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        prenom,
        nom,
        dateNaissance,
        lieuNaissance,
        cin,
        telephone
        );
    }
    @Override
    public String toString() {
        return "ChauffeurCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (prenom != null ? "prenom=" + prenom + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (dateNaissance != null ? "dateNaissance=" + dateNaissance + ", " : "") +
                (lieuNaissance != null ? "lieuNaissance=" + lieuNaissance + ", " : "") +
                (cin != null ? "cin=" + cin + ", " : "") +
                (telephone != null ? "telephone=" + telephone + ", " : "") +
            "}";
    }

}
