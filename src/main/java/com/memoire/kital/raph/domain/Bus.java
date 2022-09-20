package com.memoire.kital.raph.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Bus.
 */
@Entity
@Table(name = "bus")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Bus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "matricule", nullable = false, unique = true)
    private String matricule;

    @NotNull
    @Column(name = "numero", nullable = false)
    private String numero;

    @NotNull
    @Column(name = "nombreplace", nullable = false)
    private Integer nombreplace;

    @OneToOne
    @JoinColumn(unique = true)
    private Chauffeur chauffeur;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricule() {
        return matricule;
    }

    public Bus matricule(String matricule) {
        this.matricule = matricule;
        return this;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNumero() {
        return numero;
    }

    public Bus numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Integer getNombreplace() {
        return nombreplace;
    }

    public Bus nombreplace(Integer nombreplace) {
        this.nombreplace = nombreplace;
        return this;
    }

    public void setNombreplace(Integer nombreplace) {
        this.nombreplace = nombreplace;
    }

    public Chauffeur getChauffeur() {
        return chauffeur;
    }

    public Bus chauffeur(Chauffeur chauffeur) {
        this.chauffeur = chauffeur;
        return this;
    }

    public void setChauffeur(Chauffeur chauffeur) {
        this.chauffeur = chauffeur;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bus)) {
            return false;
        }
        return id != null && id.equals(((Bus) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bus{" +
            "id=" + getId() +
            ", matricule='" + getMatricule() + "'" +
            ", numero='" + getNumero() + "'" +
            ", nombreplace=" + getNombreplace() +
            "}";
    }
}
