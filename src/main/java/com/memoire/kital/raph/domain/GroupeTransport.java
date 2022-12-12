package com.memoire.kital.raph.domain;

import com.memoire.kital.raph.utils.SizeMapper;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A GroupeTransport.
 */
@Entity
@Table(name = "groupe_transports")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GroupeTransport implements Serializable {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    @Column(name = "id",unique = true)
    private String id;

    @NotNull
    @Size(min = SizeMapper.SizeGroupeTransport.MIN, max = SizeMapper.SizeGroupeTransport.MAX)
    @Column(name = "nom", length = SizeMapper.SizeGroupeTransport.MAX, nullable = false, unique = true)
    private String nom;

    @NotNull
    @Column(name = "nombre_eleves", nullable = false)
    private Integer nombreEleves;
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @NotNull
    @JoinTable(name = "groupe_transports_zones",
               joinColumns = @JoinColumn(name = "groupe_transport_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "zones_id", referencedColumnName = "id"))
    private Set<Zone> zones = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public GroupeTransport nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getNombreEleves() {
        return nombreEleves;
    }

    public GroupeTransport nombreEleves(Integer nombreEleves) {
        this.nombreEleves = nombreEleves;
        return this;
    }

    public void setNombreEleves(Integer nombreEleves) {
        this.nombreEleves = nombreEleves;
    }

    public Set<Zone> getZones() {
        return zones;
    }

    public GroupeTransport zones(Set<Zone> zones) {
        this.zones = zones;
        return this;
    }

    public GroupeTransport addZones(Zone zone) {
        this.zones.add(zone);
        zone.getGroupetransports().add(this);
        return this;
    }

    public GroupeTransport removeZones(Zone zone) {
        this.zones.remove(zone);
        zone.getGroupetransports().remove(this);
        return this;
    }

    public void setZones(Set<Zone> zones) {
        this.zones = zones;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GroupeTransport)) {
            return false;
        }
        return id != null && id.equals(((GroupeTransport) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GroupeTransport{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", nombreEleves=" + getNombreEleves() +
            "}";
    }
}
