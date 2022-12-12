package com.memoire.kital.raph.service.dto;

import java.time.Instant;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.memoire.kital.raph.domain.ProgrammeTransport} entity.
 */
public class ProgrammeTransportDTO implements Serializable {

    private String id;

    @NotNull
    private Instant heurDepart;

    @NotNull
    private LocalDate dateDepart;


    private String groupeTransportId;

    private String busId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getHeurDepart() {
        return heurDepart;
    }

    public void setHeurDepart(Instant heurDepart) {
        this.heurDepart = heurDepart;
    }

    public LocalDate getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(LocalDate dateDepart) {
        this.dateDepart = dateDepart;
    }

    public String getGroupeTransportId() {
        return groupeTransportId;
    }

    public void setGroupeTransportId(String groupeTransportId) {
        this.groupeTransportId = groupeTransportId;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProgrammeTransportDTO)) {
            return false;
        }

        return id != null && id.equals(((ProgrammeTransportDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProgrammeTransportDTO{" +
            "id=" + getId() +
            ", heurDepart='" + getHeurDepart() + "'" +
            ", dateDepart='" + getDateDepart() + "'" +
            ", groupeTransportId=" + getGroupeTransportId() +
            ", busId=" + getBusId() +
            "}";
    }
}
