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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ShineLoGics
 */
@Entity
@Table(name = "sparemaster")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sparemaster.findAll", query = "SELECT n FROM Sparemaster n")
    , @NamedQuery(name = "Sparemaster.findById", query = "SELECT n FROM Sparemaster n WHERE n.id = :id")
    , @NamedQuery(name = "Sparemaster.findByDivision", query = "SELECT n FROM Sparemaster n WHERE n.Division = :Division")
    , @NamedQuery(name = "Sparemaster.findBysc_engg", query = "SELECT n FROM Sparemaster n WHERE n.sc_engg = :sc_engg")
    , @NamedQuery(name = "Sparemaster.findByentryDate", query = "SELECT n FROM Sparemaster n WHERE n.entryDate = :entryDate")
    , @NamedQuery(name = "Sparemaster.findByModel", query = "SELECT n FROM Sparemaster n WHERE n.model = :model")
    , @NamedQuery(name = "Sparemaster.findByPartNumber", query = "SELECT n FROM Sparemaster n WHERE n.partNumber = :partNumber")
    , @NamedQuery(name = "Sparemaster.findByDef_Mod_Brd_name", query = "SELECT n FROM Sparemaster n WHERE n.def_Mod_Brd_name = :def_Mod_Brd_name")
    , @NamedQuery(name = "Sparemaster.findBySupplier", query = "SELECT n FROM Sparemaster n WHERE n.supplier = :supplier")
    , @NamedQuery(name = "Sparemaster.findByReason", query = "SELECT n FROM Sparemaster n WHERE n.reason = :reason")
    , @NamedQuery(name = "Sparemaster.findByReference", query = "SELECT n FROM Sparemaster n WHERE n.reference = :reference")
    , @NamedQuery(name = "Sparemaster.findByGir_no", query = "SELECT n FROM Sparemaster n WHERE n.gir_no = :gir_no")
    , @NamedQuery(name = "Sparemaster.findByIssued_by", query = "SELECT n FROM Sparemaster n WHERE n.issued_by = :issued_by")
    , @NamedQuery(name = "Sparemaster.findByReturnable_status", query = "SELECT n FROM Sparemaster n WHERE n.returnable_status = :returnable_status")
    , @NamedQuery(name = "Sparemaster.findByRemarks", query = "SELECT n FROM Sparemaster n WHERE n.remarks = :remarks")
    , @NamedQuery(name = "Sparemaster.findByReturned_date", query = "SELECT n FROM Sparemaster n WHERE n.returned_date = :returned_date")
    , @NamedQuery(name = "Sparemaster.findByFinalStatus", query = "SELECT n FROM Sparemaster n WHERE n.finalStatus = :finalStatus")
    , @NamedQuery(name = "Sparemaster.findByReq_date_time", query = "SELECT n FROM Sparemaster n WHERE n.req_date_time = :req_date_time")
    , @NamedQuery(name = "Sparemaster.findByIssued_date_time", query = "SELECT n FROM Sparemaster n WHERE n.issued_date_time = :issued_date_time")
    , @NamedQuery(name = "Sparemaster.findByReturned_date_time", query = "SELECT n FROM Sparemaster n WHERE n.returned_date_time = :returned_date_time")
    , @NamedQuery(name = "Sparemaster.findByCompleted_date_time", query = "SELECT n FROM Sparemaster n WHERE n.completed_date_time = :completed_date_time")
    
})
public class Sparemaster implements Serializable {
   
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
	    private String sc_engg;
	    @Size(max = 100)
	    @Column(name = "entry_date")
	    private String entryDate;
	    @Size(max = 100)
	    @Column(name = "supplier")
	    private String supplier;
	    @Size(max = 100)
	    @Column(name = "model")
	    private String model;
	    @Size(max = 100)
       @Column(name = "partnumber")
       private String partNumber;
	    @Size(max = 100)
       @Column(name = "def_Mod_Brd_name")
       private String Def_Mod_Brd_name;
	    @Size(max = 100)
       @Column(name = "reason")
       private String reason;
	    @Size(max = 100)
       @Column(name = "reference")
       private String reference;
	    @Size(max = 100)
       @Column(name = "gir_no")
       private String gir_no;
	    @Size(max = 100)
       @Column(name = "issued_by")
       private String issued_by;
       @Size(max = 100)
       @Column(name = "returnable_status")
       private String Returnable_status;
       @Size(max = 150)
       @Column(name = "remarks")
       private String remarks;
       @Size(max = 150)
       @Column(name = "returned_date")
       private String returned_date;
      
       @Size(max = 150)
       @Column(name = "final_status")
       private String finalStatus;
       @Size(max = 150)
       @Column(name = "req_date_time")
       private Date req_date_time;
       @Size(max = 150)
       @Column(name = "issued_date_time")
       private Date issued_date_time;
       @Size(max = 150)
       @Column(name = "returned_date_time")
       private Date returned_date_time;
       @Size(max = 150)
       @Column(name = "completed_date_time")
       @Temporal(TemporalType.TIMESTAMP)
       private Date completed_date_time;
       @Size(max = 255)
       @Column(name = "qty")
       private String qty;
    
   
   
   
  
   
    
    


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

	public String getSc_engg() {
		return sc_engg;
	}

	public void setSc_engg(String sc_engg) {
		this.sc_engg = sc_engg;
	}

	public String getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
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

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getDef_Mod_Brd_name() {
		return Def_Mod_Brd_name;
	}

	public void setDef_Mod_Brd_name(String def_Mod_Brd_name) {
		Def_Mod_Brd_name = def_Mod_Brd_name;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getGir_no() {
		return gir_no;
	}

	public void setGir_no(String gir_no) {
		this.gir_no = gir_no;
	}

	public String getIssued_by() {
		return issued_by;
	}

	public void setIssued_by(String issued_by) {
		this.issued_by = issued_by;
	}

	public String getReturnable_status() {
		return Returnable_status;
	}

	public void setReturnable_status(String returnable_status) {
		Returnable_status = returnable_status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getReturned_date() {
		return returned_date;
	}

	public void setReturned_date(String returned_date) {
		this.returned_date = returned_date;
	}

	public String getFinalStatus() {
		return finalStatus;
	}

	public void setFinalStatus(String finalStatus) {
		this.finalStatus = finalStatus;
	}

	public Date getReq_date_time() {
		return req_date_time;
	}

	public void setReq_date_time(Date req_date_time) {
		this.req_date_time = req_date_time;
	}

	public Date getIssued_date_time() {
		return issued_date_time;
	}

	public void setIssued_date_time(Date issued_date_time) {
		this.issued_date_time = issued_date_time;
	}

	public Date getReturned_date_time() {
		return returned_date_time;
	}

	public void setReturned_date_time(Date returned_date_time) {
		this.returned_date_time = returned_date_time;
	}

	public Date getCompleted_date_time() {
		return completed_date_time;
	}

	public void setCompleted_date_time(Date completed_date_time) {
		this.completed_date_time = completed_date_time;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
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
        if (!(object instanceof Sparemaster)) {
            return false;
        }
        Sparemaster other = (Sparemaster) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.schillerindiaservices.bean.Sparemaster[ id=" + id + " ]";
    }

  
    
}
