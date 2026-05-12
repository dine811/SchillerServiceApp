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
@Table(name = "dropdownmaster")
@NamedQueries({
    @NamedQuery(name = "Dropdownmaster.findAll", query = "SELECT d FROM Dropdownmaster d")
    , @NamedQuery(name = "Dropdownmaster.findById", query = "SELECT d FROM Dropdownmaster d WHERE d.id = :id")
    , @NamedQuery(name = "Dropdownmaster.findByDdname", query = "SELECT d FROM Dropdownmaster d WHERE d.ddname = :ddname")
    , @NamedQuery(name = "Dropdownmaster.findByDdvalue", query = "SELECT d FROM Dropdownmaster d WHERE d.ddvalue = :ddvalue")})
public class Dropdownmaster implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "ddname")
    private String ddname;
    @Size(max = 255)
    @Column(name = "ddvalue")
    private String ddvalue;

    public Dropdownmaster() {
    }

    public Dropdownmaster(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDdname() {
        return ddname;
    }

    public void setDdname(String ddname) {
        this.ddname = ddname;
    }

    public String getDdvalue() {
        return ddvalue;
    }

    public void setDdvalue(String ddvalue) {
        this.ddvalue = ddvalue;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dropdownmaster)) {
            return false;
        }
        Dropdownmaster other = (Dropdownmaster) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.schillerindiaservices.bean.Dropdownmaster[ id=" + id + " ]";
    }
    
}
