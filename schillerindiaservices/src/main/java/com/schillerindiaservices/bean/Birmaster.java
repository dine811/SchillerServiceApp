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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author ShineLoGics
 */
@Entity
@Table(name = "birmaster")
@NamedQueries({
    @NamedQuery(name = "Birmaster.findAll", query = "SELECT b FROM Birmaster b")
    , @NamedQuery(name = "Birmaster.findById", query = "SELECT b FROM Birmaster b WHERE b.id = :id")
    , @NamedQuery(name = "Birmaster.findByDivision", query = "SELECT b FROM Birmaster b WHERE b.division = :division")
    , @NamedQuery(name = "Birmaster.findByEntryDate", query = "SELECT b FROM Birmaster b WHERE b.entryDate = :entryDate")
    , @NamedQuery(name = "Birmaster.findByFqcInDate", query = "SELECT b FROM Birmaster b WHERE b.fqcInDate = :fqcInDate")
    , @NamedQuery(name = "Birmaster.findByModel", query = "SELECT b FROM Birmaster b WHERE b.model = :model")
    , @NamedQuery(name = "Birmaster.findByConfiguration", query = "SELECT b FROM Birmaster b WHERE b.configuration = :configuration")
    , @NamedQuery(name = "Birmaster.findByReceivedQty", query = "SELECT b FROM Birmaster b WHERE b.receivedQty = :receivedQty")
    , @NamedQuery(name = "Birmaster.findByUnitSerialNo", query = "SELECT b FROM Birmaster b WHERE b.unitSerialNo = :unitSerialNo")
    , @NamedQuery(name = "Birmaster.findByInvoiceNo", query = "SELECT b FROM Birmaster b WHERE b.invoiceNo = :invoiceNo")
    , @NamedQuery(name = "Birmaster.findByInvoiceDate", query = "SELECT b FROM Birmaster b WHERE b.invoiceDate = :invoiceDate")
    , @NamedQuery(name = "Birmaster.findBySoftwrChanges", query = "SELECT b FROM Birmaster b WHERE b.softwrChanges = :softwrChanges")
    , @NamedQuery(name = "Birmaster.findBySoftwrVersion", query = "SELECT b FROM Birmaster b WHERE b.softwrVersion = :softwrVersion")
    , @NamedQuery(name = "Birmaster.findByHardwrChanges", query = "SELECT b FROM Birmaster b WHERE b.hardwrChanges = :hardwrChanges")
    , @NamedQuery(name = "Birmaster.findByHardwareDetails", query = "SELECT b FROM Birmaster b WHERE b.hardwareDetails = :hardwareDetails")
    , @NamedQuery(name = "Birmaster.findByAccesoryChanges", query = "SELECT b FROM Birmaster b WHERE b.accesoryChanges = :accesoryChanges")
    , @NamedQuery(name = "Birmaster.findByAccesoryDetails", query = "SELECT b FROM Birmaster b WHERE b.accesoryDetails = :accesoryDetails")
    , @NamedQuery(name = "Birmaster.findByUserManualUpdate", query = "SELECT b FROM Birmaster b WHERE b.userManualUpdate = :userManualUpdate")
    , @NamedQuery(name = "Birmaster.findByServiceManualUpdate", query = "SELECT b FROM Birmaster b WHERE b.serviceManualUpdate = :serviceManualUpdate")
    , @NamedQuery(name = "Birmaster.findByFqcRemarks", query = "SELECT b FROM Birmaster b WHERE b.fqcRemarks = :fqcRemarks")
    , @NamedQuery(name = "Birmaster.findByCnrRefNo", query = "SELECT b FROM Birmaster b WHERE b.cnrRefNo = :cnrRefNo")
    , @NamedQuery(name = "Birmaster.findByTsTeamRemark", query = "SELECT b FROM Birmaster b WHERE b.tsTeamRemark = :tsTeamRemark")
    , @NamedQuery(name = "Birmaster.findByPsTeamRemark", query = "SELECT b FROM Birmaster b WHERE b.psTeamRemark = :psTeamRemark")
    , @NamedQuery(name = "Birmaster.findByFinalStatus", query = "SELECT b FROM Birmaster b WHERE b.finalStatus = :finalStatus")
    , @NamedQuery(name = "Birmaster.findByClosedDate", query = "SELECT b FROM Birmaster b WHERE b.closedDate = :closedDate")
    , @NamedQuery(name = "Birmaster.findByAccesChngRemark", query = "SELECT b FROM Birmaster b WHERE b.accesChngRemark = :accesChngRemark")
    , @NamedQuery(name = "Birmaster.findByHrdwrChangRemark", query = "SELECT b FROM Birmaster b WHERE b.hrdwrChangRemark = :hrdwrChangRemark")
    , @NamedQuery(name = "Birmaster.findBySftwrChngRemark", query = "SELECT b FROM Birmaster b WHERE b.sftwrChngRemark = :sftwrChngRemark")
    , @NamedQuery(name = "Birmaster.findByCnrReleseDate", query = "SELECT b FROM Birmaster b WHERE b.cnrReleseDate = :cnrReleseDate")
    , @NamedQuery(name = "Birmaster.findByBirRefNo", query = "SELECT b FROM Birmaster b WHERE b.birRefNo = :birRefNo")
    , @NamedQuery(name = "Birmaster.findBySupplier", query = "SELECT b FROM Birmaster b WHERE b.supplier = :supplier")
    , @NamedQuery(name = "Birmaster.findByScEngg", query = "SELECT b FROM Birmaster b WHERE b.scEngg = :scEngg")
    , @NamedQuery(name = "Birmaster.findByPsEngg", query = "SELECT b FROM Birmaster b WHERE b.psEngg = :psEngg")
    , @NamedQuery(name = "Birmaster.findByApprovedDate", query = "SELECT b FROM Birmaster b WHERE b.approvedDate = :approvedDate")
    , @NamedQuery(name = "Birmaster.findByCnrTecRefNum", query = "SELECT b FROM Birmaster b WHERE b.cnrTecRefNum = :cnrTecRefNum")
    , @NamedQuery(name = "Birmaster.findByUnitInDate", query = "SELECT b FROM Birmaster b WHERE b.unitInDate = :unitInDate")
    , @NamedQuery(name = "Birmaster.findByCurrentDate", query = "SELECT b FROM Birmaster b WHERE b.currentDate = :currentDate")


})
public class Birmaster implements Serializable {

    @Size(max = 1000)
    @Column(name = "tech_remarks")
    private String techRemarks;

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
    @Column(name = "entry_date")
    private String entryDate;
    @Size(max = 100)
    @Column(name = "fqc_in_date")
    private String fqcInDate;
    @Size(max = 100)
    @Column(name = "model")
    private String model;
    @Size(max = 100)
    @Column(name = "configuration")
    private String configuration;
    @Size(max = 100)
    @Column(name = "received_qty")
    private String receivedQty;
    @Size(max = 100)
    @Column(name = "unit_serial_no")
    private String unitSerialNo;
    @Size(max = 100)
    @Column(name = "invoice_no")
    private String invoiceNo;
    @Size(max = 100)
    @Column(name = "invoice_date")
    private String invoiceDate;
    @Size(max = 100)
    @Column(name = "softwr_changes")
    private String softwrChanges;
    @Size(max = 100)
    @Column(name = "softwr_version")
    private String softwrVersion;
    @Size(max = 100)
    @Column(name = "hardwr_changes")
    private String hardwrChanges;
    @Size(max = 100)
    @Column(name = "hardware_details")
    private String hardwareDetails;
    @Size(max = 100)
    @Column(name = "accesory_changes")
    private String accesoryChanges;
    @Size(max = 100)
    @Column(name = "accesory_details")
    private String accesoryDetails;
    @Size(max = 100)
    @Column(name = "user_manual_update")
    private String userManualUpdate;
    @Size(max = 100)
    @Column(name = "service_manual_update")
    private String serviceManualUpdate;
    @Size(max = 100)
    @Column(name = "fqc_remarks")
    private String fqcRemarks;
    @Size(max = 100)
    @Column(name = "cnr_ref_no")
    private String cnrRefNo;
    @Size(max = 100)
    @Column(name = "ts_team_verification_date")
    private String tsTeamRemark;
    @Size(max = 100)
    @Column(name = "ps_team_verification_date")
    private String psTeamRemark;
    @Size(max = 100)
    @Column(name = "final_status")
    private String finalStatus;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "closed_date")
    private String closedDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "acces_chng_remark")
    private String accesChngRemark;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "hrdwr_chang_remark")
    private String hrdwrChangRemark;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "sftwr_chng_remark")
    private String sftwrChngRemark;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "cnr_relese_date")
    private String cnrReleseDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "bir_ref_no")
    private String birRefNo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "supplier")
    private String supplier;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "sc_engg")
    private String scEngg;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ps_engg")
    private String psEngg;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "approved_date")
    private String approvedDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "cnr_tec_ref_num")
    private String cnrTecRefNum;
    @Size(max = 100)
    @Column(name = "unit_in_date")
    private String unitInDate;
    @Size(max = 100)
    @Column(name = "current_date")
    private Date currentDate;
    
    

    public Birmaster() {
    }

    public Birmaster(Integer id) {
        this.id = id;
    }

    public Birmaster(Integer id, String closedDate, String accesChngRemark, String hrdwrChangRemark, String sftwrChngRemark, String cnrReleseDate, String birRefNo, String supplier, String scEngg, String psEngg, String approvedDate, String cnrTecRefNum) {
        this.id = id;
        this.closedDate = closedDate;
        this.accesChngRemark = accesChngRemark;
        this.hrdwrChangRemark = hrdwrChangRemark;
        this.sftwrChngRemark = sftwrChngRemark;
        this.cnrReleseDate = cnrReleseDate;
        this.birRefNo = birRefNo;
        this.supplier = supplier;
        this.scEngg = scEngg;
        this.psEngg = psEngg;
        this.approvedDate = approvedDate;
        this.cnrTecRefNum = cnrTecRefNum;
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

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getFqcInDate() {
        return fqcInDate;
    }

    public void setFqcInDate(String fqcInDate) {
        this.fqcInDate = fqcInDate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    public String getReceivedQty() {
        return receivedQty;
    }

    public void setReceivedQty(String receivedQty) {
        this.receivedQty = receivedQty;
    }

    public String getUnitSerialNo() {
        return unitSerialNo;
    }

    public void setUnitSerialNo(String unitSerialNo) {
        this.unitSerialNo = unitSerialNo;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getSoftwrChanges() {
        return softwrChanges;
    }

    public void setSoftwrChanges(String softwrChanges) {
        this.softwrChanges = softwrChanges;
    }

    public String getSoftwrVersion() {
        return softwrVersion;
    }

    public void setSoftwrVersion(String softwrVersion) {
        this.softwrVersion = softwrVersion;
    }

    public String getHardwrChanges() {
        return hardwrChanges;
    }

    public void setHardwrChanges(String hardwrChanges) {
        this.hardwrChanges = hardwrChanges;
    }

    public String getHardwareDetails() {
        return hardwareDetails;
    }

    public void setHardwareDetails(String hardwareDetails) {
        this.hardwareDetails = hardwareDetails;
    }

    public String getAccesoryChanges() {
        return accesoryChanges;
    }

    public void setAccesoryChanges(String accesoryChanges) {
        this.accesoryChanges = accesoryChanges;
    }

    public String getAccesoryDetails() {
        return accesoryDetails;
    }

    public void setAccesoryDetails(String accesoryDetails) {
        this.accesoryDetails = accesoryDetails;
    }

    public String getUserManualUpdate() {
        return userManualUpdate;
    }

    public void setUserManualUpdate(String userManualUpdate) {
        this.userManualUpdate = userManualUpdate;
    }

    public String getServiceManualUpdate() {
        return serviceManualUpdate;
    }

    public void setServiceManualUpdate(String serviceManualUpdate) {
        this.serviceManualUpdate = serviceManualUpdate;
    }

    public String getFqcRemarks() {
        return fqcRemarks;
    }

    public void setFqcRemarks(String fqcRemarks) {
        this.fqcRemarks = fqcRemarks;
    }

    public String getCnrRefNo() {
        return cnrRefNo;
    }

    public void setCnrRefNo(String cnrRefNo) {
        this.cnrRefNo = cnrRefNo;
    }

    public String getTsTeamRemark() {
        return tsTeamRemark;
    }

    public void setTsTeamRemark(String tsTeamRemark) {
        this.tsTeamRemark = tsTeamRemark;
    }

    public String getPsTeamRemark() {
        return psTeamRemark;
    }

    public void setPsTeamRemark(String psTeamRemark) {
        this.psTeamRemark = psTeamRemark;
    }

    public String getFinalStatus() {
        return finalStatus;
    }

    public void setFinalStatus(String finalStatus) {
        this.finalStatus = finalStatus;
    }

    public String getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(String closedDate) {
        this.closedDate = closedDate;
    }

    public String getAccesChngRemark() {
        return accesChngRemark;
    }

    public void setAccesChngRemark(String accesChngRemark) {
        this.accesChngRemark = accesChngRemark;
    }

    public String getHrdwrChangRemark() {
        return hrdwrChangRemark;
    }

    public void setHrdwrChangRemark(String hrdwrChangRemark) {
        this.hrdwrChangRemark = hrdwrChangRemark;
    }

    public String getSftwrChngRemark() {
        return sftwrChngRemark;
    }

    public void setSftwrChngRemark(String sftwrChngRemark) {
        this.sftwrChngRemark = sftwrChngRemark;
    }

    public String getCnrReleseDate() {
        return cnrReleseDate;
    }

    public void setCnrReleseDate(String cnrReleseDate) {
        this.cnrReleseDate = cnrReleseDate;
    }

    public String getBirRefNo() {
        return birRefNo;
    }

    public void setBirRefNo(String birRefNo) {
        this.birRefNo = birRefNo;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getScEngg() {
        return scEngg;
    }

    public void setScEngg(String scEngg) {
        this.scEngg = scEngg;
    }

    public String getPsEngg() {
        return psEngg;
    }

    public void setPsEngg(String psEngg) {
        this.psEngg = psEngg;
    }

    public String getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(String approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getCnrTecRefNum() {
        return cnrTecRefNum;
    }

    public void setCnrTecRefNum(String cnrTecRefNum) {
        this.cnrTecRefNum = cnrTecRefNum;
    }

    public String getUnitInDate() {
        return unitInDate;
    }

    public void setUnitInDate(String unitInDate) {
        this.unitInDate = unitInDate;
    }
      
    
    
    
    
    public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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
        if (!(object instanceof Birmaster)) {
            return false;
        }
        Birmaster other = (Birmaster) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.schillerindiaservices.bean.Birmaster[ id=" + id + " ]";
    }

    public String getTechRemarks() {
        return techRemarks;
    }

    public void setTechRemarks(String techRemarks) {
        this.techRemarks = techRemarks;
    }
    
}
