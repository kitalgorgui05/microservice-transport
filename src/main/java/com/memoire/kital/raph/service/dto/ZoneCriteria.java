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
 * Criteria class for the {@link com.memoire.kital.raph.domain.Zone} entity. This class is used
 * in {@link com.memoire.kital.raph.web.rest.ZoneResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /zones?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ZoneCriteria implements Serializable, Criteria {
    private StringFilter id;
    private StringFilter libelle;
    private StringFilter groupetransportsId;
    public ZoneCriteria() {
    }
    public ZoneCriteria(ZoneCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.libelle = other.libelle == null ? null : other.libelle.copy();
        this.groupetransportsId = other.groupetransportsId == null ? null : other.groupetransportsId.copy();
    }
    @Override
    public ZoneCriteria copy() {
        return new ZoneCriteria(this);
    }
    public StringFilter getId() {
        return id;
    }
    public void setId(StringFilter id) {
        this.id = id;
    }
    public StringFilter getLibelle() {
        return libelle;
    }
    public void setLibelle(StringFilter libelle) {
        this.libelle = libelle;
    }
    public StringFilter getGroupetransportsId() {
        return groupetransportsId;
    }
    public void setGroupetransportsId(StringFilter groupetransportsId) {
        this.groupetransportsId = groupetransportsId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ZoneCriteria that = (ZoneCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(libelle, that.libelle) &&
            Objects.equals(groupetransportsId, that.groupetransportsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        libelle,
        groupetransportsId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ZoneCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (libelle != null ? "libelle=" + libelle + ", " : "") +
                (groupetransportsId != null ? "groupetransportsId=" + groupetransportsId + ", " : "") +
            "}";
    }

}
