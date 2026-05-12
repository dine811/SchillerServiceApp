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
@Table(name = "nonsaleablemaster")
@NamedQueries({
    @NamedQuery(name = "Nonsaleablemaster.findAll", query = "SELECT n FROM Nonsaleablemaster n")
    , @NamedQuery(name = "Nonsaleablemaster.findById", query = "SELECT n FROM Nonsaleablemaster n WHERE n.id = :id")
    , @NamedQuery(name = "Nonsaleablemaster.findByUnitDetails", query = "SELECT n FROM Nonsaleablemaster n WHERE n.unitDetails = :unitDetails")
    , @NamedQuery(name = "Nonsaleablemaster.findByFqcInDate", query = "SELECT n FROM Nonsaleablemaster n WHERE n.fqcInDate = :fqcInDate")
    , @NamedQuery(name = "Nonsaleablemaster.findByRegion", query = "SELECT n FROM Nonsaleablemaster n WHERE n.region = :region")
    , @NamedQuery(name = "Nonsaleablemaster.findByBranch", query = "SELECT n FROM Nonsaleablemaster n WHERE n.branch = :branch")
    , @NamedQuery(name = "Nonsaleablemaster.findByEngineer", query = "SELECT n FROM Nonsaleablemaster n WHERE n.engineer = :engineer")
    , @NamedQuery(name = "Nonsaleablemaster.findByDealer", query = "SELECT n FROM Nonsaleablemaster n WHERE n.dealer = :dealer")
    , @NamedQuery(name = "Nonsaleablemaster.findBySupplier", query = "SELECT n FROM Nonsaleablemaster n WHERE n.supplier = :supplier")
    , @NamedQuery(name = "Nonsaleablemaster.findByModel", query = "SELECT n FROM Nonsaleablemaster n WHERE n.model = :model")
    , @NamedQuery(name = "Nonsaleablemaster.findByModelSN", query = "SELECT n FROM Nonsaleablemaster n WHERE n.modelSN = :modelSN")
    , @NamedQuery(name = "Nonsaleablemaster.findByUnitConfig", query = "SELECT n FROM Nonsaleablemaster n WHERE n.unitConfig = :unitConfig")
    , @NamedQuery(name = "Nonsaleablemaster.findByCustName", query = "SELECT n FROM Nonsaleablemaster n WHERE n.custName = :custName")
    , @NamedQuery(name = "Nonsaleablemaster.findByReportedProblm", query = "SELECT n FROM Nonsaleablemaster n WHERE n.reportedProblm = :reportedProblm")
    , @NamedQuery(name = "Nonsaleablemaster.findByReplacedUnitSN", query = "SELECT n FROM Nonsaleablemaster n WHERE n.replacedUnitSN = :replacedUnitSN")
    , @NamedQuery(name = "Nonsaleablemaster.findByReplaceShipDate", query = "SELECT n FROM Nonsaleablemaster n WHERE n.replaceShipDate = :replaceShipDate")
    , @NamedQuery(name = "Nonsaleablemaster.findByDefectUnitRecivedDate", query = "SELECT n FROM Nonsaleablemaster n WHERE n.defectUnitRecivedDate = :defectUnitRecivedDate")
    , @NamedQuery(name = "Nonsaleablemaster.findByFqcObservation", query = "SELECT n FROM Nonsaleablemaster n WHERE n.fqcObservation = :fqcObservation")
    , @NamedQuery(name = "Nonsaleablemaster.findByScInwardDate", query = "SELECT n FROM Nonsaleablemaster n WHERE n.scInwardDate = :scInwardDate")
    , @NamedQuery(name = "Nonsaleablemaster.findByScEngg", query = "SELECT n FROM Nonsaleablemaster n WHERE n.scEngg = :scEngg")
    , @NamedQuery(name = "Nonsaleablemaster.findByScObservation", query = "SELECT n FROM Nonsaleablemaster n WHERE n.scObservation = :scObservation")
    , @NamedQuery(name = "Nonsaleablemaster.findByRequiredParts", query = "SELECT n FROM Nonsaleablemaster n WHERE n.requiredParts = :requiredParts")
    , @NamedQuery(name = "Nonsaleablemaster.findByRootCause", query = "SELECT n FROM Nonsaleablemaster n WHERE n.rootCause = :rootCause")
    , @NamedQuery(name = "Nonsaleablemaster.findByActionPlan", query = "SELECT n FROM Nonsaleablemaster n WHERE n.actionPlan = :actionPlan")
    , @NamedQuery(name = "Nonsaleablemaster.findByTentDateShipDate", query = "SELECT n FROM Nonsaleablemaster n WHERE n.tentDateShipDate = :tentDateShipDate")
    , @NamedQuery(name = "Nonsaleablemaster.findByFinalStatus", query = "SELECT n FROM Nonsaleablemaster n WHERE n.finalStatus = :finalStatus")
    , @NamedQuery(name = "Nonsaleablemaster.findByFinalCurrentDate", query = "SELECT n FROM Nonsaleablemaster n WHERE n.currentDate = :currentDate")
})
public class Nonsaleablemaster implements Serializable {

    @Size(max = 50)
    @Column(name = "ship_date_fqc")
    private String shipDateFqc;
    @Size(max = 500)
    @Column(name = "fqc_final_remark")
    private String fqcFinalRemark;

    @Size(max = 50)
    @Column(name = "entry_date")
    private String entryDate;

    @Size(max = 60)
    @Column(name = "division")
    private String division;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 50)
    @Column(name = "unit_details")
    private String unitDetails;
    @Size(max = 50)
    @Column(name = "fqc_in_date")
    private String fqcInDate;
    @Size(max = 50)
    @Column(name = "region")
    private String region;
    @Size(max = 50)
    @Column(name = "branch")
    private String branch;
    @Size(max = 50)
    @Column(name = "engineer")
    private String engineer;
    @Size(max = 50)
    @Column(name = "dealer")
    private String dealer;
    @Size(max = 50)
    @Column(name = "supplier")
    private String supplier;
    @Size(max = 50)
    @Column(name = "model")
    private String model;
    @Size(max = 50)
    @Column(name = "model_s_n")
    private String modelSN;
    @Size(max = 50)
    @Column(name = "unit_config")
    private String unitConfig;
    @Size(max = 50)
    @Column(name = "cust_name")
    private String custName;
    @Size(max = 250)
    @Column(name = "reported_problm")
    private String reportedProblm;
    @Size(max = 250)
    @Column(name = "replaced_unit_s_n")
    private String replacedUnitSN;
    @Size(max = 50)
    @Column(name = "replace_ship_date")
    private String replaceShipDate;
    @Size(max = 50)
    @Column(name = "defect_unit_recived_date")
    private String defectUnitRecivedDate;
    @Size(max = 250)
    @Column(name = "fqc_observation")
    private String fqcObservation;
    @Size(max = 50)
    @Column(name = "sc_inward_date")
    private String scInwardDate;
    @Size(max = 50)
    @Column(name = "sc_engg")
    private String scEngg;
    @Size(max = 250)
    @Column(name = "sc_observation")
    private String scObservation;
    @Size(max = 250)
    @Column(name = "required_parts")
    private String requiredParts;
    @Size(max = 250)
    @Column(name = "root_cause")
    private String rootCause;
    @Size(max = 600)
    @Column(name = "action_plan")
    private String actionPlan;
    @Size(max = 50)
    @Column(name = "tent_date_ship_date")
    private String tentDateShipDate;
    @Size(max = 50)
    @Column(name = "final_status")
    private String finalStatus;
    @Size(max = 50)
    @Column(name = "current_date")
    private Date currentDate;
    
    

    public Nonsaleablemaster() {
    }

    public Nonsaleablemaster(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUnitDetails() {
        return unitDetails;
    }

    public void setUnitDetails(String unitDetails) {
        this.unitDetails = unitDetails;
    }

    public String getFqcInDate() {
        return fqcInDate;
    }

    public void setFqcInDate(String fqcInDate) {
        this.fqcInDate = fqcInDate;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getEngineer() {
        return engineer;
    }

    public void setEngineer(String engineer) {
        this.engineer = engineer;
    }

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModelSN() {
        return modelSN;
    }

    public void setModelSN(String modelSN) {
        this.modelSN = modelSN;
    }

    public String getUnitConfig() {
        return unitConfig;
    }

    public void setUnitConfig(String unitConfig) {
        this.unitConfig = unitConfig;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getReportedProblm() {
        return reportedProblm;
    }

    public void setReportedProblm(String reportedProblm) {
        this.reportedProblm = reportedProblm;
    }

    public String getReplacedUnitSN() {
        return replacedUnitSN;
    }

    public void setReplacedUnitSN(String replacedUnitSN) {
        this.replacedUnitSN = replacedUnitSN;
    }

    public String getReplaceShipDate() {
        return replaceShipDate;
    }

    public void setReplaceShipDate(String replaceShipDate) {
        this.replaceShipDate = replaceShipDate;
    }

    public String getDefectUnitRecivedDate() {
        return defectUnitRecivedDate;
    }

    public void setDefectUnitRecivedDate(String defectUnitRecivedDate) {
        this.defectUnitRecivedDate = defectUnitRecivedDate;
    }

    public String getFqcObservation() {
        return fqcObservation;
    }

    public void setFqcObservation(String fqcObservation) {
        this.fqcObservation = fqcObservation;
    }

    public String getScInwardDate() {
        return scInwardDate;
    }

    public void setScInwardDate(String scInwardDate) {
        this.scInwardDate = scInwardDate;
    }

    public String getScEngg() {
        return scEngg;
    }

    public void setScEngg(String scEngg) {
        this.scEngg = scEngg;
    }

    public String getScObservation() {
        return scObservation;
    }

    public void setScObservation(String scObservation) {
        this.scObservation = scObservation;
    }

    public String getRequiredParts() {
        return requiredParts;
    }

    public void setRequiredParts(String requiredParts) {
        this.requiredParts = requiredParts;
    }

    public String getRootCause() {
        return rootCause;
    }

    public void setRootCause(String rootCause) {
        this.rootCause = rootCause;
    }

    public String getActionPlan() {
        return actionPlan;
    }

    public void setActionPlan(String actionPlan) {
        this.actionPlan = actionPlan;
    }

    public String getTentDateShipDate() {
        return tentDateShipDate;
    }

    public void setTentDateShipDate(String tentDateShipDate) {
        this.tentDateShipDate = tentDateShipDate;
    }

    public String getFinalStatus() {
        return finalStatus;
    }

    public void setFinalStatus(String finalStatus) {
        this.finalStatus = finalStatus;
    }
    
    
    
    
    
    
    

    public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
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
        if (!(object instanceof Nonsaleablemaster)) {
            return false;
        }
        Nonsaleablemaster other = (Nonsaleablemaster) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.schillerindiaservices.bean.Nonsaleablemaster[ id=" + id + " ]";
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getShipDateFqc() {
        return shipDateFqc;
    }

    public void setShipDateFqc(String shipDateFqc) {
        this.shipDateFqc = shipDateFqc;
    }

    public String getFqcFinalRemark() {
        return fqcFinalRemark;
    }

    public void setFqcFinalRemark(String fqcFinalRemark) {
        this.fqcFinalRemark = fqcFinalRemark;
    }
    
}
