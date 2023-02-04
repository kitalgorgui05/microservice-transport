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

public class BusCriteria implements Serializable, Criteria {
    private StringFilter id;
    private StringFilter matricule;
    private StringFilter numero;
    private IntegerFilter nombreplace;
    private StringFilter chauffeurId;

    public BusCriteria() {
    }
    public BusCriteria(BusCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.matricule = other.matricule == null ? null : other.matricule.copy();
        this.numero = other.numero == null ? null : other.numero.copy();
        this.nombreplace = other.nombreplace == null ? null : other.nombreplace.copy();
        this.chauffeurId = other.chauffeurId == null ? null : other.chauffeurId.copy();
    }
    @Override
    public BusCriteria copy() {
        return new BusCriteria(this);
    }
    public StringFilter getId() {
        return id;
    }
    public void setId(StringFilter id) {
        this.id = id;
    }
    public StringFilter getMatricule() {
        return matricule;
    }
    public void setMatricule(StringFilter matricule) {
        this.matricule = matricule;
    }
    public StringFilter getNumero() {
        return numero;
    }
    public void setNumero(StringFilter numero) {
        this.numero = numero;
    }
    public IntegerFilter getNombreplace() {
        return nombreplace;
    }
    public void setNombreplace(IntegerFilter nombreplace) {
        this.nombreplace = nombreplace;
    }
    public StringFilter getChauffeurId() {
        return chauffeurId;
    }
    public void setChauffeurId(StringFilter chauffeurId) {
        this.chauffeurId = chauffeurId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BusCriteria that = (BusCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(matricule, that.matricule) &&
            Objects.equals(numero, that.numero) &&
            Objects.equals(nombreplace, that.nombreplace) &&
            Objects.equals(chauffeurId, that.chauffeurId);
    }
    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        matricule,
        numero,
        nombreplace,
        chauffeurId
        );
    }
    @Override
    public String toString() {
        return "BusCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (matricule != null ? "matricule=" + matricule + ", " : "") +
                (numero != null ? "numero=" + numero + ", " : "") +
                (nombreplace != null ? "nombreplace=" + nombreplace + ", " : "") +
                (chauffeurId != null ? "chauffeurId=" + chauffeurId + ", " : "") +
            "}";
    }
}
