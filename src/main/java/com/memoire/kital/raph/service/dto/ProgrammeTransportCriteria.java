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
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.memoire.kital.raph.domain.ProgrammeTransport} entity. This class is used
 * in {@link com.memoire.kital.raph.web.rest.ProgrammeTransportResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /programme-transports?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProgrammeTransportCriteria implements Serializable, Criteria {
    private StringFilter id;
    private InstantFilter heurDepart;
    private LocalDateFilter dateDepart;
    private StringFilter groupeTransportId;
    private StringFilter busId;
    public ProgrammeTransportCriteria() {
    }
    public ProgrammeTransportCriteria(ProgrammeTransportCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.heurDepart = other.heurDepart == null ? null : other.heurDepart.copy();
        this.dateDepart = other.dateDepart == null ? null : other.dateDepart.copy();
        this.groupeTransportId = other.groupeTransportId == null ? null : other.groupeTransportId.copy();
        this.busId = other.busId == null ? null : other.busId.copy();
    }

    @Override
    public ProgrammeTransportCriteria copy() {
        return new ProgrammeTransportCriteria(this);
    }

    public StringFilter getId() {
        return id;
    }

    public void setId(StringFilter id) {
        this.id = id;
    }

    public InstantFilter getHeurDepart() {
        return heurDepart;
    }

    public void setHeurDepart(InstantFilter heurDepart) {
        this.heurDepart = heurDepart;
    }

    public LocalDateFilter getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(LocalDateFilter dateDepart) {
        this.dateDepart = dateDepart;
    }

    public StringFilter getGroupeTransportId() {
        return groupeTransportId;
    }

    public void setGroupeTransportId(StringFilter groupeTransportId) {
        this.groupeTransportId = groupeTransportId;
    }

    public StringFilter getBusId() {
        return busId;
    }

    public void setBusId(StringFilter busId) {
        this.busId = busId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProgrammeTransportCriteria that = (ProgrammeTransportCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(heurDepart, that.heurDepart) &&
            Objects.equals(dateDepart, that.dateDepart) &&
            Objects.equals(groupeTransportId, that.groupeTransportId) &&
            Objects.equals(busId, that.busId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        heurDepart,
        dateDepart,
        groupeTransportId,
        busId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProgrammeTransportCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (heurDepart != null ? "heurDepart=" + heurDepart + ", " : "") +
                (dateDepart != null ? "dateDepart=" + dateDepart + ", " : "") +
                (groupeTransportId != null ? "groupeTransportId=" + groupeTransportId + ", " : "") +
                (busId != null ? "busId=" + busId + ", " : "") +
            "}";
    }

}
