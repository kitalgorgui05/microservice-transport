package com.memoire.kital.raph.domain;

import com.memoire.kital.raph.utils.SizeMapper;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Chauffeur.
 */
@Entity
@Table(name = "chauffeurs")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Chauffeur implements Serializable {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    @Column(name = "id", unique = true)
    private String id;
    @NotNull
    @Size(min = SizeMapper.SizChauffeurNomAndPrenom.MIN, max = SizeMapper.SizChauffeurNomAndPrenom.MAX)
    @Column(name = "prenom", length = SizeMapper.SizChauffeurNomAndPrenom.MAX, nullable = false)
    private String prenom;

    @NotNull
    @Size(min = SizeMapper.SizChauffeurNomAndPrenom.MIN, max = SizeMapper.SizChauffeurNomAndPrenom.MAX)
    @Column(name = "nom", length = SizeMapper.SizChauffeurNomAndPrenom.MAX, nullable = false)
    private String nom;

    @NotNull
    @Column(name = "date_naissance", nullable = false)
    private Instant dateNaissance;

    @NotNull
    @Size(min = SizeMapper.SizChauffeurLieuNaissance.MIN, max = SizeMapper.SizChauffeurLieuNaissance.MAX)
    @Column(name = "lieu_naissance", length = SizeMapper.SizChauffeurLieuNaissance.MAX, nullable = false)
    private String lieuNaissance;

    @NotNull
    @Size(min = SizeMapper.SizChauffeurCin.MIN, max = SizeMapper.SizChauffeurCin.MAX)
    @Column(name = "cin", length = SizeMapper.SizChauffeurCin.MAX, nullable = false)
    private String cin;

    @NotNull
    @Size(min = SizeMapper.SizChauffeurTelephone.MIN, max = SizeMapper.SizChauffeurTelephone.MAX)
    @Column(name = "telephone", length = 12, nullable = false)
    private String telephone;


    @Lob
    @Column(name = "adresse", nullable = false)
    private String adresse;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public Chauffeur prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public Chauffeur nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Instant getDateNaissance() {
        return dateNaissance;
    }

    public Chauffeur dateNaissance(Instant dateNaissance) {
        this.dateNaissance = dateNaissance;
        return this;
    }

    public void setDateNaissance(Instant dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getLieuNaissance() {
        return lieuNaissance;
    }

    public Chauffeur lieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
        return this;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public String getCin() {
        return cin;
    }

    public Chauffeur cin(String cin) {
        this.cin = cin;
        return this;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getTelephone() {
        return telephone;
    }

    public Chauffeur telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public Chauffeur adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Chauffeur)) {
            return false;
        }
        return id != null && id.equals(((Chauffeur) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Chauffeur{" +
            "id=" + getId() +
            ", prenom='" + getPrenom() + "'" +
            ", nom='" + getNom() + "'" +
            ", dateNaissance='" + getDateNaissance() + "'" +
            ", lieuNaissance='" + getLieuNaissance() + "'" +
            ", cin='" + getCin() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", adresse='" + getAdresse() + "'" +
            "}";
    }
}
