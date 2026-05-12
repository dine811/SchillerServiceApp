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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SHINELOGICS
 */
@Entity
@Table(name = "suppliermaster")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Suppliermaster.findAll", query = "SELECT s FROM Suppliermaster s"),
    @NamedQuery(name = "Suppliermaster.findBySupplierId", query = "SELECT s FROM Suppliermaster s WHERE s.supplierId = :supplierId"),
    @NamedQuery(name = "Suppliermaster.findBySupplierName", query = "SELECT s FROM Suppliermaster s WHERE s.supplierName = :supplierName"),
    @NamedQuery(name = "Suppliermaster.findByDivision", query = "SELECT s FROM Suppliermaster s WHERE s.division = :division"),
    @NamedQuery(name = "Suppliermaster.findByModel", query = "SELECT s FROM Suppliermaster s WHERE s.model = :model")})
public class Suppliermaster implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "supplier_id")
    private Integer supplierId;
    @Size(max = 50)
    @Column(name = "supplier_name")
    private String supplierName;
    @Size(max = 100)
    @Column(name = "division")
    private String division;
    @Size(max = 100)
    @Column(name = "model")
    private String model;

    public Suppliermaster() {
    }

    public Suppliermaster(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (supplierId != null ? supplierId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Suppliermaster)) {
            return false;
        }
        Suppliermaster other = (Suppliermaster) object;
        if ((this.supplierId == null && other.supplierId != null) || (this.supplierId != null && !this.supplierId.equals(other.supplierId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.schillerindiaservices.bean.Suppliermaster[ supplierId=" + supplierId + " ]";
    }
    
}
