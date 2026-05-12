/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schillerindiaservices.bean;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author MR
 */
@Entity
@Table(name = "productmaster")
@NamedQueries({
    @NamedQuery(name = "Productmaster.findAll", query = "SELECT p FROM Productmaster p")
    , @NamedQuery(name = "Productmaster.findByProdId", query = "SELECT p FROM Productmaster p WHERE p.prodId = :prodId")
    , @NamedQuery(name = "Productmaster.findByProdName", query = "SELECT p FROM Productmaster p WHERE p.prodName = :prodName")
    , @NamedQuery(name = "Productmaster.findByProdDescription", query = "SELECT p FROM Productmaster p WHERE p.prodDescription = :prodDescription")})
public class Productmaster implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "prod_id")
    private Integer prodId;
    @Size(max = 100)
    @Column(name = "prod_name")
    private String prodName;
    @Size(max = 100)
    @Column(name = "prod_description")
    private String prodDescription;

    public Productmaster() {
    }

    public Productmaster(Integer prodId) {
        this.prodId = prodId;
    }

    public Integer getProdId() {
        return prodId;
    }

    public void setProdId(Integer prodId) {
        this.prodId = prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdDescription() {
        return prodDescription;
    }

    public void setProdDescription(String prodDescription) {
        this.prodDescription = prodDescription;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prodId != null ? prodId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Productmaster)) {
            return false;
        }
        Productmaster other = (Productmaster) object;
        if ((this.prodId == null && other.prodId != null) || (this.prodId != null && !this.prodId.equals(other.prodId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.schillerindiaservices.bean.Productmaster[ prodId=" + prodId + " ]";
    }
    
}
