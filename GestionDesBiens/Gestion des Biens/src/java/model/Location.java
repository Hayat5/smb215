/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Hayat
 */
@Entity
@Table(name = "location")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Location.findAll", query = "SELECT l FROM Location l"),
    @NamedQuery(name = "Location.findByLocationId", query = "SELECT l FROM Location l WHERE l.locationId = :locationId")})
public class Location implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "location_id")
    private Integer locationId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "locationId")
    private Collection<Item> itemCollection;
    @JoinColumn(name = "personnel_id", referencedColumnName = "personnel_id")
    @ManyToOne(optional = false)
    private Personnel personnelId;
    @JoinColumn(name = "salle_id", referencedColumnName = "salle_id")
    @ManyToOne(optional = false)
    private Salle salleId;
    @JoinColumn(name = "center_id", referencedColumnName = "center_id")
    @ManyToOne(optional = false)
    private Center centerId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "locationIdSrc")
    private Collection<Transaction> transactionCollection;
    @OneToMany(mappedBy = "locationIdNow")
    private Collection<Transaction> transactionCollection1;
    @OneToMany(mappedBy = "locationIdDest")
    private Collection<Transaction> transactionCollection2;

    public Location() {
    }

    public Location(Integer locationId) {
        this.locationId = locationId;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    @XmlTransient
    public Collection<Item> getItemCollection() {
        return itemCollection;
    }

    public void setItemCollection(Collection<Item> itemCollection) {
        this.itemCollection = itemCollection;
    }

    public Personnel getPersonnelId() {
        return personnelId;
    }

    public void setPersonnelId(Personnel personnelId) {
        this.personnelId = personnelId;
    }

    public Salle getSalleId() {
        return salleId;
    }

    public void setSalleId(Salle salleId) {
        this.salleId = salleId;
    }

    public Center getCenterId() {
        return centerId;
    }

    public void setCenterId(Center centerId) {
        this.centerId = centerId;
    }

    @XmlTransient
    public Collection<Transaction> getTransactionCollection() {
        return transactionCollection;
    }

    public void setTransactionCollection(Collection<Transaction> transactionCollection) {
        this.transactionCollection = transactionCollection;
    }

    @XmlTransient
    public Collection<Transaction> getTransactionCollection1() {
        return transactionCollection1;
    }

    public void setTransactionCollection1(Collection<Transaction> transactionCollection1) {
        this.transactionCollection1 = transactionCollection1;
    }

    @XmlTransient
    public Collection<Transaction> getTransactionCollection2() {
        return transactionCollection2;
    }

    public void setTransactionCollection2(Collection<Transaction> transactionCollection2) {
        this.transactionCollection2 = transactionCollection2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (locationId != null ? locationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Location)) {
            return false;
        }
        Location other = (Location) object;
        if ((this.locationId == null && other.locationId != null) || (this.locationId != null && !this.locationId.equals(other.locationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Location[ locationId=" + locationId + " ]";
    }
    
}
