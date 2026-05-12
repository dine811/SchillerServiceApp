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
@Table(name = "branch")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Branch.findAll", query = "SELECT b FROM Branch b")
    , @NamedQuery(name = "Branch.findByBranchid", query = "SELECT b FROM Branch b WHERE b.branchid = :branchid")
    , @NamedQuery(name = "Branch.findByCompanyid", query = "SELECT b FROM Branch b WHERE b.companyid = :companyid")
    , @NamedQuery(name = "Branch.findByRegionid", query = "SELECT b FROM Branch b WHERE b.regionid = :regionid")
    , @NamedQuery(name = "Branch.findByBranchname", query = "SELECT b FROM Branch b WHERE b.branchname = :branchname")
    , @NamedQuery(name = "Branch.findByBranchlocation", query = "SELECT b FROM Branch b WHERE b.branchlocation = :branchlocation")
    , @NamedQuery(name = "Branch.findByBranchaddress", query = "SELECT b FROM Branch b WHERE b.branchaddress = :branchaddress")
    , @NamedQuery(name = "Branch.findByBranchphone", query = "SELECT b FROM Branch b WHERE b.branchphone = :branchphone")})
public class Branch implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "branchid")
    private Integer branchid;
    @Column(name = "companyid")
    private Integer companyid;
    @Column(name = "regionid")
    private Integer regionid;
    @Size(max = 30)
    @Column(name = "branchname")
    private String branchname;
    @Size(max = 30)
    @Column(name = "branchlocation")
    private String branchlocation;
    @Size(max = 150)
    @Column(name = "branchaddress")
    private String branchaddress;
    @Size(max = 20)
    @Column(name = "branchphone")
    private String branchphone;

    public Branch() {
    }

    public Branch(Integer branchid) {
        this.branchid = branchid;
    }

    public Integer getBranchid() {
        return branchid;
    }

    public void setBranchid(Integer branchid) {
        this.branchid = branchid;
    }

    public Integer getCompanyid() {
        return companyid;
    }

    public void setCompanyid(Integer companyid) {
        this.companyid = companyid;
    }

    public Integer getRegionid() {
        return regionid;
    }

    public void setRegionid(Integer regionid) {
        this.regionid = regionid;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

    public String getBranchlocation() {
        return branchlocation;
    }

    public void setBranchlocation(String branchlocation) {
        this.branchlocation = branchlocation;
    }

    public String getBranchaddress() {
        return branchaddress;
    }

    public void setBranchaddress(String branchaddress) {
        this.branchaddress = branchaddress;
    }

    public String getBranchphone() {
        return branchphone;
    }

    public void setBranchphone(String branchphone) {
        this.branchphone = branchphone;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (branchid != null ? branchid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Branch)) {
            return false;
        }
        Branch other = (Branch) object;
        if ((this.branchid == null && other.branchid != null) || (this.branchid != null && !this.branchid.equals(other.branchid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.schillerindiaservices.bean.Branch[ branchid=" + branchid + " ]";
    }
    
}
