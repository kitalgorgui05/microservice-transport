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
 * Criteria class for the {@link com.memoire.kital.raph.domain.GroupeTransport} entity. This class is used
 * in {@link com.memoire.kital.raph.web.rest.GroupeTransportResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /groupe-transports?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class GroupeTransportCriteria implements Serializable, Criteria {
    private StringFilter id;
    private StringFilter nom;
    private IntegerFilter nombreEleves;
    private StringFilter zonesId;

    public GroupeTransportCriteria() {
    }
    public GroupeTransportCriteria(GroupeTransportCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.nombreEleves = other.nombreEleves == null ? null : other.nombreEleves.copy();
        this.zonesId = other.zonesId == null ? null : other.zonesId.copy();
    }
    @Override
    public GroupeTransportCriteria copy() {
        return new GroupeTransportCriteria(this);
    }
    public StringFilter getId() {
        return id;
    }
    public void setId(StringFilter id) {
        this.id = id;
    }
    public StringFilter getNom() {
        return nom;
    }
    public void setNom(StringFilter nom) {
        this.nom = nom;
    }
    public IntegerFilter getNombreEleves() {
        return nombreEleves;
    }
    public void setNombreEleves(IntegerFilter nombreEleves) {
        this.nombreEleves = nombreEleves;
    }
    public StringFilter getZonesId() {
        return zonesId;
    }
    public void setZonesId(StringFilter zonesId) {
        this.zonesId = zonesId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final GroupeTransportCriteria that = (GroupeTransportCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(nombreEleves, that.nombreEleves) &&
            Objects.equals(zonesId, that.zonesId);
    }
    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nom,
        nombreEleves,
        zonesId
        );
    }
    @Override
    public String toString() {
        return "GroupeTransportCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (nombreEleves != null ? "nombreEleves=" + nombreEleves + ", " : "") +
                (zonesId != null ? "zonesId=" + zonesId + ", " : "") +
            "}";
    }

}
