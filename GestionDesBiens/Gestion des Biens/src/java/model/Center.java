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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Hayat
 */
@Entity
@Table(name = "center")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Center.findAll", query = "SELECT c FROM Center c"),
    @NamedQuery(name = "Center.findByCenterId", query = "SELECT c FROM Center c WHERE c.centerId = :centerId"),
    @NamedQuery(name = "Center.findByCenterName", query = "SELECT c FROM Center c WHERE c.centerName = :centerName")})
public class Center implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "center_id")
    private Integer centerId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    
    @Column(name = "center_name")
    private String centerName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "centerId")
    private Collection<Location> locationCollection;

    public Center() {
    }

    public Center(Integer centerId) {
        this.centerId = centerId;
    }

    public Center(Integer centerId, String centerName) {
        this.centerId = centerId;
        this.centerName = centerName;
    }

    public Integer getCenterId() {
        return centerId;
    }

    public void setCenterId(Integer centerId) {
        this.centerId = centerId;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    @XmlTransient
    public Collection<Location> getLocationCollection() {
        return locationCollection;
    }

    public void setLocationCollection(Collection<Location> locationCollection) {
        this.locationCollection = locationCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (centerId != null ? centerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Center)) {
            return false;
        }
        Center other = (Center) object;
        if ((this.centerId == null && other.centerId != null) || (this.centerId != null && !this.centerId.equals(other.centerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Center[ centerId=" + centerId + " ]";
    }
    
}
