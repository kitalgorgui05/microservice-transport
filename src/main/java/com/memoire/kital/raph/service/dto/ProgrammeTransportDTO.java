package com.memoire.kital.raph.service.dto;

import java.time.Instant;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

public class ProgrammeTransportDTO implements Serializable {
    private String id;
    @NotNull
    private Instant heurDepart;
    @NotNull
    private LocalDate dateDepart;
    private GroupeTransportDTO groupeTransport;
    private BusDTO bus;

    //getter and setter
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
    public GroupeTransportDTO getGroupeTransport() {
        return groupeTransport;
    }
    public void setGroupeTransport(GroupeTransportDTO groupeTransport) {
        this.groupeTransport = groupeTransport;
    }
    public BusDTO getBus() {
        return bus;
    }
    public void setBus(BusDTO bus) {
        this.bus = bus;
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
    @Override
    public String toString() {
        return "ProgrammeTransportDTO{" +
            "id=" + getId() +
            ", heurDepart='" + getHeurDepart() + "'" +
            ", dateDepart='" + getDateDepart() + "'" +
            ", groupeTransportId=" + getGroupeTransport() +
            ", busId=" + getBus() +
            "}";
    }
}
