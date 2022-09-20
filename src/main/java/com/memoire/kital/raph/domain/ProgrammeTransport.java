package com.memoire.kital.raph.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * A ProgrammeTransport.
 */
@Entity
@Table(name = "programme_transports")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProgrammeTransport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "heur_depart", nullable = false)
    private Instant heurDepart;

    @NotNull
    @Column(name = "date_depart", nullable = false)
    private LocalDate dateDepart;

    @ManyToOne
    @JsonIgnoreProperties(value = "programmeTransports", allowSetters = true)
    private GroupeTransport groupeTransport;

    @ManyToOne
    @JsonIgnoreProperties(value = "programmeTransports", allowSetters = true)
    private Bus bus;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getHeurDepart() {
        return heurDepart;
    }

    public ProgrammeTransport heurDepart(Instant heurDepart) {
        this.heurDepart = heurDepart;
        return this;
    }

    public void setHeurDepart(Instant heurDepart) {
        this.heurDepart = heurDepart;
    }

    public LocalDate getDateDepart() {
        return dateDepart;
    }

    public ProgrammeTransport dateDepart(LocalDate dateDepart) {
        this.dateDepart = dateDepart;
        return this;
    }

    public void setDateDepart(LocalDate dateDepart) {
        this.dateDepart = dateDepart;
    }

    public GroupeTransport getGroupeTransport() {
        return groupeTransport;
    }

    public ProgrammeTransport groupeTransport(GroupeTransport groupeTransport) {
        this.groupeTransport = groupeTransport;
        return this;
    }

    public void setGroupeTransport(GroupeTransport groupeTransport) {
        this.groupeTransport = groupeTransport;
    }

    public Bus getBus() {
        return bus;
    }

    public ProgrammeTransport bus(Bus bus) {
        this.bus = bus;
        return this;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProgrammeTransport)) {
            return false;
        }
        return id != null && id.equals(((ProgrammeTransport) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProgrammeTransport{" +
            "id=" + getId() +
            ", heurDepart='" + getHeurDepart() + "'" +
            ", dateDepart='" + getDateDepart() + "'" +
            "}";
    }
}
