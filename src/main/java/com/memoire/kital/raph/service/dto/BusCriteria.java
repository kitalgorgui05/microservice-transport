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

/**
 * Criteria class for the {@link com.memoire.kital.raph.domain.Bus} entity. This class is used
 * in {@link com.memoire.kital.raph.web.rest.BusResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /buses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BusCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter matricule;

    private StringFilter numero;

    private IntegerFilter nombreplace;

    private LongFilter chauffeurId;

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

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
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

    public LongFilter getChauffeurId() {
        return chauffeurId;
    }

    public void setChauffeurId(LongFilter chauffeurId) {
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

    // prettier-ignore
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
