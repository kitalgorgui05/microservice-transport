package com.memoire.kital.raph.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * A Zone.
 */
@Entity
@Table(name = "zones")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Zone implements Serializable {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    @Column(name = "id",unique = true)
    private String id;

    @NotNull
    @Size(min = SizeMapper.SizeZone.MIN, max = SizeMapper.SizeZone.MAX)
    @Column(name = "libelle", length = SizeMapper.SizeZone.MAX, nullable = false)
    private String libelle;


    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @ManyToMany(mappedBy = "zones")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<GroupeTransport> groupetransports = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public Zone libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public Zone description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<GroupeTransport> getGroupetransports() {
        return groupetransports;
    }

    public Zone groupetransports(Set<GroupeTransport> groupeTransports) {
        this.groupetransports = groupeTransports;
        return this;
    }

    public Zone addGroupetransports(GroupeTransport groupeTransport) {
        this.groupetransports.add(groupeTransport);
        groupeTransport.getZones().add(this);
        return this;
    }

    public Zone removeGroupetransports(GroupeTransport groupeTransport) {
        this.groupetransports.remove(groupeTransport);
        groupeTransport.getZones().remove(this);
        return this;
    }

    public void setGroupetransports(Set<GroupeTransport> groupeTransports) {
        this.groupetransports = groupeTransports;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Zone)) {
            return false;
        }
        return id != null && id.equals(((Zone) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Zone{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
