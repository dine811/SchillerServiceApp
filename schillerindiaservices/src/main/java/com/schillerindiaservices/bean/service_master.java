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
@Table(name = "service_master")
@NamedQueries({
    @NamedQuery(name = "service_master.findAll", query = "SELECT s FROM service_master s")
    , @NamedQuery(name = "service_master.findBySerId", query = "SELECT s FROM service_master s WHERE s.serId = :serId")
    , @NamedQuery(name = "service_master.findByDivision", query = "SELECT s FROM service_master s WHERE s.division = :division")
    , @NamedQuery(name = "service_master.findByEntryDate", query = "SELECT s FROM service_master s WHERE s.entryDate = :entryDate")
    , @NamedQuery(name = "service_master.findByScRefNo", query = "SELECT s FROM service_master s WHERE s.scRefNo = :scRefNo")
    , @NamedQuery(name = "service_master.findByScEngnr", query = "SELECT s FROM service_master s WHERE s.scEngnr = :scEngnr")
    , @NamedQuery(name = "service_master.findByRaEngnr", query = "SELECT s FROM service_master s WHERE s.raEngnr = :raEngnr")
    , @NamedQuery(name = "service_master.findByFrnNo", query = "SELECT s FROM service_master s WHERE s.frnNo = :frnNo")
    , @NamedQuery(name = "service_master.findByFrnDate", query = "SELECT s FROM service_master s WHERE s.frnDate = :frnDate")
    , @NamedQuery(name = "service_master.findByDocketNo", query = "SELECT s FROM service_master s WHERE s.docketNo = :docketNo")
    , @NamedQuery(name = "service_master.findByDespatchDate", query = "SELECT s FROM service_master s WHERE s.despatchDate = :despatchDate")
    , @NamedQuery(name = "service_master.findBySerCommInwardDate", query = "SELECT s FROM service_master s WHERE s.serCommInwardDate = :serCommInwardDate")
    , @NamedQuery(name = "service_master.findBySerCentreReceivedDate", query = "SELECT s FROM service_master s WHERE s.serCentreReceivedDate = :serCentreReceivedDate")
    , @NamedQuery(name = "service_master.findByStkCust", query = "SELECT s FROM service_master s WHERE s.stkCust = :stkCust")
    , @NamedQuery(name = "service_master.findByRegion", query = "SELECT s FROM service_master s WHERE s.region = :region")
    , @NamedQuery(name = "service_master.findByBranch", query = "SELECT s FROM service_master s WHERE s.branch = :branch")
    , @NamedQuery(name = "service_master.findByEngineerId", query = "SELECT s FROM service_master s WHERE s.engineerId = :engineerId")
    , @NamedQuery(name = "service_master.findByDealerName", query = "SELECT s FROM service_master s WHERE s.dealerName = :dealerName")
    , @NamedQuery(name = "service_master.findByCustName", query = "SELECT s FROM service_master s WHERE s.custName = :custName")
    , @NamedQuery(name = "service_master.findBySupplierName", query = "SELECT s FROM service_master s WHERE s.supplierName = :supplierName")
    , @NamedQuery(name = "service_master.findByProductModel", query = "SELECT s FROM service_master s WHERE s.productModel = :productModel")
    , @NamedQuery(name = "service_master.findByUnitSlno", query = "SELECT s FROM service_master s WHERE s.unitSlno = :unitSlno")
    , @NamedQuery(name = "service_master.findByDod", query = "SELECT s FROM service_master s WHERE s.dod = :dod")
    , @NamedQuery(name = "service_master.findByUnitStatus", query = "SELECT s FROM service_master s WHERE s.unitStatus = :unitStatus")
    , @NamedQuery(name = "service_master.findByModBrdName", query = "SELECT s FROM service_master s WHERE s.modBrdName = :modBrdName")
    , @NamedQuery(name = "service_master.findByDefModBrdName", query = "SELECT s FROM service_master s WHERE s.defModBrdName = :defModBrdName")
    , @NamedQuery(name = "service_master.findByDefType", query = "SELECT s FROM service_master s WHERE s.defType = :defType")
    , @NamedQuery(name = "service_master.findByTypeOfAcc", query = "SELECT s FROM service_master s WHERE s.typeOfAcc = :typeOfAcc")
    , @NamedQuery(name = "service_master.findByDefPartSn", query = "SELECT s FROM service_master s WHERE s.defPartSn = :defPartSn")
    , @NamedQuery(name = "service_master.findByDefGirNo", query = "SELECT s FROM service_master s WHERE s.defGirNo = :defGirNo")
    , @NamedQuery(name = "service_master.findByRepType", query = "SELECT s FROM service_master s WHERE s.repType = :repType")
    , @NamedQuery(name = "service_master.findByRepGirNo", query = "SELECT s FROM service_master s WHERE s.repGirNo = :repGirNo")
    , @NamedQuery(name = "service_master.findByDefUnitGirNo", query = "SELECT s FROM service_master s WHERE s.defUnitGirNo = :defUnitGirNo")
    , @NamedQuery(name = "service_master.findByFieldRemarks", query = "SELECT s FROM service_master s WHERE s.fieldRemarks = :fieldRemarks")
    , @NamedQuery(name = "service_master.findByTechnicalRemarks", query = "SELECT s FROM service_master s WHERE s.technicalRemarks = :technicalRemarks")
    , @NamedQuery(name = "service_master.findByComponentsUsedforRepair", query = "SELECT s FROM service_master s WHERE s.componentsUsedforRepair = :componentsUsedforRepair")
    , @NamedQuery(name = "service_master.findByRepairedBrdStkDate", query = "SELECT s FROM service_master s WHERE s.repairedBrdStkDate = :repairedBrdStkDate")
    , @NamedQuery(name = "service_master.findByFinalRemarks", query = "SELECT s FROM service_master s WHERE s.finalRemarks = :finalRemarks")
    , @NamedQuery(name = "service_master.findByTypeOfWork", query = "SELECT s FROM service_master s WHERE s.typeOfWork = :typeOfWork")
    , @NamedQuery(name = "service_master.findByShipDtFrmSerCntr", query = "SELECT s FROM service_master s WHERE s.shipDtFrmSerCntr = :shipDtFrmSerCntr")
    , @NamedQuery(name = "service_master.findByDispAdvNo", query = "SELECT s FROM service_master s WHERE s.dispAdvNo = :dispAdvNo")
    , @NamedQuery(name = "service_master.findByShipDateFromCommercial", query = "SELECT s FROM service_master s WHERE s.shipDateFromCommercial = :shipDateFromCommercial")
    , @NamedQuery(name = "service_master.findByDcNo", query = "SELECT s FROM service_master s WHERE s.dcNo = :dcNo")
    , @NamedQuery(name = "service_master.findByComrclDtlInvRmrk", query = "SELECT s FROM service_master s WHERE s.comrclDtlInvRmrk = :comrclDtlInvRmrk")
    , @NamedQuery(name = "service_master.findByDivisionName", query = "SELECT s FROM service_master s WHERE s.divisionName = :divisionName")
    , @NamedQuery(name = "service_master.findByMonth", query = "SELECT s FROM service_master s WHERE s.month = :month")
    , @NamedQuery(name = "service_master.findByYear", query = "SELECT s FROM service_master s WHERE s.year = :year")
    , @NamedQuery(name = "service_master.findByRepairedBrdAdvNo", query = "SELECT s FROM service_master s WHERE s.repairedBrdAdvNo = :repairedBrdAdvNo")
    , @NamedQuery(name = "service_master.findByModelConfig", query = "SELECT s FROM service_master s WHERE s.modelConfig = :modelConfig")
    , @NamedQuery(name = "service_master.findByStatus", query = "SELECT s FROM service_master s WHERE s.status = :status")
    , @NamedQuery(name = "service_master.findByPart_Number", query = "SELECT s FROM service_master s WHERE s.part_Number = :part_Number")
    , @NamedQuery(name = "service_master.findByCompatibleModels", query = "SELECT s FROM service_master s WHERE s.compatibleModels = :compatibleModels")
    , @NamedQuery(name = "service_master.findByCost", query = "SELECT s FROM service_master s WHERE s.cost = :cost")
    , @NamedQuery(name = "service_master.findByCurrentdate", query = "SELECT s FROM service_master s WHERE s.currentdate = :currentdate")
    , @NamedQuery(name = "service_master.findByReport_Type", query = "SELECT s FROM service_master s WHERE s.report_type = :report_type")
    , @NamedQuery(name = "service_master.findByDestination", query = "SELECT s FROM service_master s WHERE s.destination = :destination")
    , @NamedQuery(name = "service_master.findByRepair_status", query = "SELECT s FROM service_master s WHERE s.repair_status = :repair_status")


})
public class service_master implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ser_id")
    private Integer serId;
    @Column(name = "division")
    private Integer division;
    @Size(max = 50)
    @Column(name = "entry_date")
    private String entryDate;
    @Size(max = 100)
    @Column(name = "sc_ref_no")
    private String scRefNo;
    @Column(name = "sc_engnr")
    private Integer scEngnr;
    @Column(name = "ra_engnr")
    private Integer raEngnr;
    @Size(max = 100)
    @Column(name = "frn_no")
    private String frnNo;
    @Size(max = 50)
    @Column(name = "frn_date")
    private String frnDate;
    @Size(max = 100)
    @Column(name = "docket_no")
    private String docketNo;
    @Size(max = 50)
    @Column(name = "despatch_date")
    private String despatchDate;
    @Size(max = 50)
    @Column(name = "ser_comm_inward_date")
    private String serCommInwardDate;
    @Size(max = 50)
    @Column(name = "ser_centre_received_date")
    private String serCentreReceivedDate;
    @Size(max = 100)
    @Column(name = "stk_cust")
    private String stkCust;
    @Size(max = 100)
    @Column(name = "region")
    private String region;
    @Size(max = 100)
    @Column(name = "branch")
    private String branch;
    @Column(name = "engineer_id")
    private Integer engineerId;
    @Size(max = 100)
    @Column(name = "dealer_name")
    private String dealerName;
    @Size(max = 100)
    @Column(name = "cust_name")
    private String custName;
    @Size(max = 100)
    @Column(name = "supplier_name")
    private String supplierName;
    @Size(max = 55)
    @Column(name = "product_model")
    private String productModel;
    @Size(max = 100)
    @Column(name = "unit_slno")
    private String unitSlno;
    @Size(max = 50)
    @Column(name = "dod")
    private String dod;
    @Size(max = 100)
    @Column(name = "unit_status")
    private String unitStatus;
    @Size(max = 100)
    @Column(name = "mod_brd_name")
    private String modBrdName;
    @Size(max = 100)
    @Column(name = "def_mod_brd_name")
    private String defModBrdName;
    @Size(max = 100)
    @Column(name = "def_type")
    private String defType;
    @Size(max = 100)
    @Column(name = "type_of_acc")
    private String typeOfAcc;
    @Size(max = 100)
    @Column(name = "def_part_sn")
    private String defPartSn;
    @Size(max = 100)
    @Column(name = "def_gir_no")
    private String defGirNo;
    @Size(max = 100)
    @Column(name = "rep_type")
    private String repType;
    @Size(max = 100)
    @Column(name = "rep_gir_no")
    private String repGirNo;
    @Size(max = 100)
    @Column(name = "def_unit_gir_no")
    private String defUnitGirNo;
    @Size(max = 100)
    @Column(name = "field_remarks")
    private String fieldRemarks;
    @Size(max = 100)
    @Column(name = "technical_remarks")
    private String technicalRemarks;
    @Size(max = 200)
    @Column(name = "components_usedfor_repair")
    private String componentsUsedforRepair;
    @Size(max = 50)
    @Column(name = "repaired_brd_stk_date")
    private String repairedBrdStkDate;
    @Size(max = 100)
    @Column(name = "final_remarks")
    private String finalRemarks;
    @Size(max = 100)
    @Column(name = "type_of_work")
    private String typeOfWork;
    @Size(max = 50)
    @Column(name = "ship_dt_frm_ser_cntr")
    private String shipDtFrmSerCntr;
    @Size(max = 100)
    @Column(name = "disp_adv_no")
    private String dispAdvNo;
    @Size(max = 50)
    @Column(name = "ship_date_from_commercial")
    private String shipDateFromCommercial;
    @Size(max = 100)
    @Column(name = "dc_no")
    private String dcNo;
    @Size(max = 100)
    @Column(name = "comrcl_dtl_inv_rmrk")
    private String comrclDtlInvRmrk;
    @Size(max = 100)
    @Column(name = "division_name")
    private String divisionName;
    @Size(max = 50)
    @Column(name = "month")
    private String month;
    @Size(max = 50)
    @Column(name = "year")
    private String year;
    @Size(max = 100)
    @Column(name = "repaired_brd_adv_no")
    private String repairedBrdAdvNo;
    @Size(max = 1000)
    @Column(name = "model_config")
    private String modelConfig;
    @Size(max = 1000)
    @Column(name = "status")
    private String status;
    
    @Size(max = 100)
    @Column(name = "part_number")
    private String part_Number;
    @Size(max = 100)
    @Column(name = "compatible_models")
    private String compatibleModels;
    @Column(name = "cost")
    private double cost;
    @Column(name = "currentDate")
    private Date currentdate;
    @Size(max = 100)
    @Column(name = "report_type")
    private String report_type;
    @Size(max = 100)
    @Column(name = "destination")
    private String destination;
    @Size(max = 100)
    @Column(name = "repair_status")
    private String repair_status;

    public service_master() {
    }

    public service_master(Integer serId) {
        this.serId = serId;
    }

    public Integer getSerId() {
        return serId;
    }

    public void setSerId(Integer serId) {
        this.serId = serId;
    }

    public Integer getDivision() {
        return division;
    }

    public void setDivision(Integer division) {
        this.division = division;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getScRefNo() {
        return scRefNo;
    }

    public void setScRefNo(String scRefNo) {
        this.scRefNo = scRefNo;
    }

    public Integer getScEngnr() {
        return scEngnr;
    }

    public void setScEngnr(Integer scEngnr) {
        this.scEngnr = scEngnr;
    }

    public Integer getRaEngnr() {
        return raEngnr;
    }

    public void setRaEngnr(Integer raEngnr) {
        this.raEngnr = raEngnr;
    }

    public String getFrnNo() {
        return frnNo;
    }

    public void setFrnNo(String frnNo) {
        this.frnNo = frnNo;
    }

    public String getFrnDate() {
        return frnDate;
    }

    public void setFrnDate(String frnDate) {
        this.frnDate = frnDate;
    }

    public String getDocketNo() {
        return docketNo;
    }

    public void setDocketNo(String docketNo) {
        this.docketNo = docketNo;
    }

    public String getDespatchDate() {
        return despatchDate;
    }

    public void setDespatchDate(String despatchDate) {
        this.despatchDate = despatchDate;
    }

    public String getSerCommInwardDate() {
        return serCommInwardDate;
    }

    public void setSerCommInwardDate(String serCommInwardDate) {
        this.serCommInwardDate = serCommInwardDate;
    }

    public String getSerCentreReceivedDate() {
        return serCentreReceivedDate;
    }

    public void setSerCentreReceivedDate(String serCentreReceivedDate) {
        this.serCentreReceivedDate = serCentreReceivedDate;
    }

    public String getStkCust() {
        return stkCust;
    }

    public void setStkCust(String stkCust) {
        this.stkCust = stkCust;
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

    public Integer getEngineerId() {
        return engineerId;
    }

    public void setEngineerId(Integer engineerId) {
        this.engineerId = engineerId;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public String getUnitSlno() {
        return unitSlno;
    }

    public void setUnitSlno(String unitSlno) {
        this.unitSlno = unitSlno;
    }

    public String getDod() {
        return dod;
    }

    public void setDod(String dod) {
        this.dod = dod;
    }

    public String getUnitStatus() {
        return unitStatus;
    }

    public void setUnitStatus(String unitStatus) {
        this.unitStatus = unitStatus;
    }

    public String getModBrdName() {
        return modBrdName;
    }

    public void setModBrdName(String modBrdName) {
        this.modBrdName = modBrdName;
    }

    public String getDefModBrdName() {
        return defModBrdName;
    }

    public void setDefModBrdName(String defModBrdName) {
        this.defModBrdName = defModBrdName;
    }

    public String getDefType() {
        return defType;
    }

    public void setDefType(String defType) {
        this.defType = defType;
    }

    public String getTypeOfAcc() {
        return typeOfAcc;
    }

    public void setTypeOfAcc(String typeOfAcc) {
        this.typeOfAcc = typeOfAcc;
    }

    public String getDefPartSn() {
        return defPartSn;
    }

    public void setDefPartSn(String defPartSn) {
        this.defPartSn = defPartSn;
    }

    public String getDefGirNo() {
        return defGirNo;
    }

    public void setDefGirNo(String defGirNo) {
        this.defGirNo = defGirNo;
    }

    public String getRepType() {
        return repType;
    }

    public void setRepType(String repType) {
        this.repType = repType;
    }

    public String getRepGirNo() {
        return repGirNo;
    }

    public void setRepGirNo(String repGirNo) {
        this.repGirNo = repGirNo;
    }

    public String getDefUnitGirNo() {
        return defUnitGirNo;
    }

    public void setDefUnitGirNo(String defUnitGirNo) {
        this.defUnitGirNo = defUnitGirNo;
    }

    public String getFieldRemarks() {
        return fieldRemarks;
    }

    public void setFieldRemarks(String fieldRemarks) {
        this.fieldRemarks = fieldRemarks;
    }

    public String getTechnicalRemarks() {
        return technicalRemarks;
    }

    public void setTechnicalRemarks(String technicalRemarks) {
        this.technicalRemarks = technicalRemarks;
    }

    public String getComponentsUsedforRepair() {
        return componentsUsedforRepair;
    }

    public void setComponentsUsedforRepair(String componentsUsedforRepair) {
        this.componentsUsedforRepair = componentsUsedforRepair;
    }

    public String getRepairedBrdStkDate() {
        return repairedBrdStkDate;
    }

    public void setRepairedBrdStkDate(String repairedBrdStkDate) {
        this.repairedBrdStkDate = repairedBrdStkDate;
    }

    public String getFinalRemarks() {
        return finalRemarks;
    }

    public void setFinalRemarks(String finalRemarks) {
        this.finalRemarks = finalRemarks;
    }

    public String getTypeOfWork() {
        return typeOfWork;
    }

    public void setTypeOfWork(String typeOfWork) {
        this.typeOfWork = typeOfWork;
    }

    public String getShipDtFrmSerCntr() {
        return shipDtFrmSerCntr;
    }

    public void setShipDtFrmSerCntr(String shipDtFrmSerCntr) {
        this.shipDtFrmSerCntr = shipDtFrmSerCntr;
    }

    public String getDispAdvNo() {
        return dispAdvNo;
    }

    public void setDispAdvNo(String dispAdvNo) {
        this.dispAdvNo = dispAdvNo;
    }

    public String getShipDateFromCommercial() {
        return shipDateFromCommercial;
    }

    public void setShipDateFromCommercial(String shipDateFromCommercial) {
        this.shipDateFromCommercial = shipDateFromCommercial;
    }

    public String getDcNo() {
        return dcNo;
    }

    public void setDcNo(String dcNo) {
        this.dcNo = dcNo;
    }

    public String getComrclDtlInvRmrk() {
        return comrclDtlInvRmrk;
    }

    public void setComrclDtlInvRmrk(String comrclDtlInvRmrk) {
        this.comrclDtlInvRmrk = comrclDtlInvRmrk;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRepairedBrdAdvNo() {
        return repairedBrdAdvNo;
    }

    public void setRepairedBrdAdvNo(String repairedBrdAdvNo) {
        this.repairedBrdAdvNo = repairedBrdAdvNo;
    }

    public String getModelConfig() {
        return modelConfig;
    }

    public void setModelConfig(String modelConfig) {
        this.modelConfig = modelConfig;
    }
    
    
    

    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

	public String getPart_Number() {
		return part_Number;
	}

	public void setPart_Number(String part_Number) {
		this.part_Number = part_Number;
	}

	public String getCompatibleModels() {
		return compatibleModels;
	}

	public void setCompatibleModels(String compatibleModels) {
		this.compatibleModels = compatibleModels;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}
	
	
	
	
	
	
	

	public Date getCurrentdate() {
		return currentdate;
	}

	public void setCurrentdate(Date currentdate) {
		this.currentdate = currentdate;
	}

	
	
	
	
	
	
	
	
	
	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getReport_type() {
		return report_type;
	}

	public void setReport_type(String report_type) {
		this.report_type = report_type;
	}
	
	

	public String getRepair_status() {
		return repair_status;
	}

	public void setRepair_status(String repair_status) {
		this.repair_status = repair_status;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (serId != null ? serId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof service_master)) {
            return false;
        }
        service_master other = (service_master) object;
        if ((this.serId == null && other.serId != null) || (this.serId != null && !this.serId.equals(other.serId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.schillerindiaservices.bean.service_master[ serId=" + serId + " ]";
    }
    
}
