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
 * @author MR
 */
@Entity
@Table(name = "companymaster")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Companymaster.findAll", query = "SELECT c FROM Companymaster c")
    , @NamedQuery(name = "Companymaster.findByCompId", query = "SELECT c FROM Companymaster c WHERE c.compId = :compId")
    , @NamedQuery(name = "Companymaster.findByCompName", query = "SELECT c FROM Companymaster c WHERE c.compName = :compName")
    , @NamedQuery(name = "Companymaster.findByCompAddress", query = "SELECT c FROM Companymaster c WHERE c.compAddress = :compAddress")
    , @NamedQuery(name = "Companymaster.findByCompPhone", query = "SELECT c FROM Companymaster c WHERE c.compPhone = :compPhone")
    , @NamedQuery(name = "Companymaster.findByCompEmail", query = "SELECT c FROM Companymaster c WHERE c.compEmail = :compEmail")})
public class Companymaster implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "comp_id")
    private Integer compId;
    @Size(max = 100)
    @Column(name = "comp_name")
    private String compName;
    @Size(max = 200)
    @Column(name = "comp_address")
    private String compAddress;
    @Size(max = 20)
    @Column(name = "comp_phone")
    private String compPhone;
    @Size(max = 20)
    @Column(name = "comp_email")
    private String compEmail;

    public Companymaster() {
    }

    public Companymaster(Integer compId) {
        this.compId = compId;
    }

    public Integer getCompId() {
        return compId;
    }

    public void setCompId(Integer compId) {
        this.compId = compId;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getCompAddress() {
        return compAddress;
    }

    public void setCompAddress(String compAddress) {
        this.compAddress = compAddress;
    }

    public String getCompPhone() {
        return compPhone;
    }

    public void setCompPhone(String compPhone) {
        this.compPhone = compPhone;
    }

    public String getCompEmail() {
        return compEmail;
    }

    public void setCompEmail(String compEmail) {
        this.compEmail = compEmail;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (compId != null ? compId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Companymaster)) {
            return false;
        }
        Companymaster other = (Companymaster) object;
        if ((this.compId == null && other.compId != null) || (this.compId != null && !this.compId.equals(other.compId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.schillerindiaservices.bean.Companymaster[ compId=" + compId + " ]";
    }
    
}
