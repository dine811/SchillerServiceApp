/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schillerindiaservices.bean;

import java.io.Serializable;
import java.util.Date;

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
 * @author ShineLoGics
 */
@Entity
@Table(name = "pendingactmaster")
@NamedQueries({
    @NamedQuery(name = "Pendingactmaster.findAll", query = "SELECT p FROM Pendingactmaster p")
    , @NamedQuery(name = "Pendingactmaster.findById", query = "SELECT p FROM Pendingactmaster p WHERE p.id = :id")
    , @NamedQuery(name = "Pendingactmaster.findByDivision", query = "SELECT p FROM Pendingactmaster p WHERE p.division = :division")
    , @NamedQuery(name = "Pendingactmaster.findByScEngg", query = "SELECT p FROM Pendingactmaster p WHERE p.scEngg = :scEngg")
    , @NamedQuery(name = "Pendingactmaster.findByEntryDate", query = "SELECT p FROM Pendingactmaster p WHERE p.entryDate = :entryDate")
    , @NamedQuery(name = "Pendingactmaster.findByInitiateDate", query = "SELECT p FROM Pendingactmaster p WHERE p.initiateDate = :initiateDate")
    , @NamedQuery(name = "Pendingactmaster.findByModel", query = "SELECT p FROM Pendingactmaster p WHERE p.model = :model")
    , @NamedQuery(name = "Pendingactmaster.findByPendingActivity", query = "SELECT p FROM Pendingactmaster p WHERE p.pendingActivity = :pendingActivity")
    , @NamedQuery(name = "Pendingactmaster.findByResponsible", query = "SELECT p FROM Pendingactmaster p WHERE p.responsible = :responsible")
    , @NamedQuery(name = "Pendingactmaster.findByPendingForm", query = "SELECT p FROM Pendingactmaster p WHERE p.pendingForm = :pendingForm")
    , @NamedQuery(name = "Pendingactmaster.findByTarClosedDate", query = "SELECT p FROM Pendingactmaster p WHERE p.tarClosedDate = :tarClosedDate")
    , @NamedQuery(name = "Pendingactmaster.findByRemarks", query = "SELECT p FROM Pendingactmaster p WHERE p.remarks = :remarks")
    , @NamedQuery(name = "Pendingactmaster.findByStatusType", query = "SELECT p FROM Pendingactmaster p WHERE p.statusType = :statusType")
    , @NamedQuery(name = "Pendingactmaster.findByCurrentDate", query = "SELECT p FROM Pendingactmaster p WHERE p.currentDate = :currentDate")



})
public class Pendingactmaster implements Serializable {

    @Size(max = 50)
    @Column(name = "closed_date")
    private String closedDate;
    @Size(max = 600)
    @Column(name = "sc_incharge_remark")
    private String scInchargeRemark;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 100)
    @Column(name = "division")
    private String division;
    @Size(max = 100)
    @Column(name = "sc_engg")
    private String scEngg;
    @Size(max = 100)
    @Column(name = "entry_date")
    private String entryDate;
    @Size(max = 100)
    @Column(name = "initiate_date")
    private String initiateDate;
    @Size(max = 100)
    @Column(name = "model")
    private String model;
    @Size(max = 100)
    @Column(name = "pending_activity")
    private String pendingActivity;
    @Size(max = 100)
    @Column(name = "responsible")
    private String responsible;
    @Size(max = 100)
    @Column(name = "pending_form")
    private String pendingForm;
    @Size(max = 100)
    @Column(name = "tar_closed_date")
    private String tarClosedDate;
    @Size(max = 100)
    @Column(name = "remarks")
    private String remarks;
    @Size(max = 100)
    @Column(name = "status_type")
    private String statusType;
    @Column(name = "currentDate")
    private Date currentdate;

    public Pendingactmaster() {
    }

    public Pendingactmaster(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getScEngg() {
        return scEngg;
    }

    public void setScEngg(String scEngg) {
        this.scEngg = scEngg;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getInitiateDate() {
        return initiateDate;
    }

    public void setInitiateDate(String initiateDate) {
        this.initiateDate = initiateDate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPendingActivity() {
        return pendingActivity;
    }

    public void setPendingActivity(String pendingActivity) {
        this.pendingActivity = pendingActivity;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public String getPendingForm() {
        return pendingForm;
    }

    public void setPendingForm(String pendingForm) {
        this.pendingForm = pendingForm;
    }

    public String getTarClosedDate() {
        return tarClosedDate;
    }

    public void setTarClosedDate(String tarClosedDate) {
        this.tarClosedDate = tarClosedDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }
    
    

    public Date getCurrentdate() {
		return currentdate;
	}

	public void setCurrentdate(Date currentdate) {
		this.currentdate = currentdate;
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
        if (!(object instanceof Pendingactmaster)) {
            return false;
        }
        Pendingactmaster other = (Pendingactmaster) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.schillerindiaservices.bean.Pendingactmaster[ id=" + id + " ]";
    }

    public String getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(String closedDate) {
        this.closedDate = closedDate;
    }

    public String getScInchargeRemark() {
        return scInchargeRemark;
    }

    public void setScInchargeRemark(String scInchargeRemark) {
        this.scInchargeRemark = scInchargeRemark;
    }
    
}
