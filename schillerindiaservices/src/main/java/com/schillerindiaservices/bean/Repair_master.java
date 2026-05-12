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
@Table(name = "Repair_master")
@NamedQueries({
    @NamedQuery(name = "Repairmaster.findAll", query = "SELECT p FROM Repairmaster p")
    , @NamedQuery(name = "Repair_master.findById", query = "SELECT p FROM Repair_master p WHERE p.id = :id")
    , @NamedQuery(name = "Repair_master.findByDivision", query = "SELECT p FROM Repair_master p WHERE p.division = :division")
    , @NamedQuery(name = "Repair_master.findByEntryDate", query = "SELECT p FROM Repair_master p WHERE p.entryDate = :entryDate")
    , @NamedQuery(name = "Repair_master.findByModel", query = "SELECT p FROM Repair_master p WHERE p.model = :model")
    , @NamedQuery(name = "Repair_master.findBySc_ref_no", query = "SELECT p FROM Repair_master p WHERE p.sc_ref_no = :sc_ref_no")
    , @NamedQuery(name = "Repair_master.findByDef_gir_no", query = "SELECT p FROM Repair_master p WHERE p.def_gir_no = :def_gir_no")
    , @NamedQuery(name = "Repair_master.findByCategory", query = "SELECT p FROM Repair_master p WHERE p.category = :category")
    , @NamedQuery(name = "Repair_master.findByDef_brd_mod_name", query = "SELECT p FROM Repair_master p WHERE p.def_brd_mod_name = :def_brd_mod_name")
    , @NamedQuery(name = "Repair_master.findByUnit_status", query = "SELECT p FROM Repair_master p WHERE p.unit_status = :unit_status")
    , @NamedQuery(name = "Repair_master.findByTech_remarks", query = "SELECT p FROM Repair_master p WHERE p.tech_remarks = :tech_remarks")
    , @NamedQuery(name = "Repair_master.findByCurrentDate", query = "SELECT p FROM Repair_master p WHERE p.currentDate = :currentDate")
    , @NamedQuery(name = "Repair_master.findByComp_used_to_repair", query = "SELECT p FROM Repair_master p WHERE p.comp_used_to_repair = :comp_used_to_repair")
    , @NamedQuery(name = "Repair_master.findByFinished_date", query = "SELECT p FROM Repair_master p WHERE p.finished_date = :finished_date")
    , @NamedQuery(name = "Repair_master.findByRepaired_by", query = "SELECT p FROM Repair_master p WHERE p.repaired_by = :repaired_by")
    , @NamedQuery(name = "Repair_master.findByFinal_remarks", query = "SELECT p FROM Repair_master p WHERE p.final_remarks = :final_remarks")
    , @NamedQuery(name = "Repair_master.findByStatusType", query = "SELECT p FROM Repair_master p WHERE p.statusType = :statusType")
    , @NamedQuery(name = "Repair_master.findByNo_of_days", query = "SELECT p FROM Repair_master p WHERE p.no_of_days = :no_of_days")



})
public class Repair_master implements Serializable {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Basic(optional = false)
	    @Column(name = "id")
	    private Integer id;
	
    @Size(max = 50)
    @Column(name = "entry_date")
    private String entry_date;

    @Size(max = 100)
    @Column(name = "division")
    private String division;
    @Size(max = 100)
    @Column(name = "sc_ref_no")
    private String sc_ref_no;

    private static final long serialVersionUID = 1L;
   
    @Size(max = 100)
    @Column(name = "def_gir_no")
    private String def_gir_no;
    @Size(max = 100)
    @Column(name = "category")
    private String category;
    @Size(max = 100)
    @Column(name = "model")
    private String model;
    @Size(max = 100)
    @Column(name = "def_brd_mod_name")
    private String def_brd_mod_name;
    @Size(max = 100)
    @Column(name = "unit_status")
    private String unit_status;
    
    @Size(max = 1000)
    @Column(name = "tech_remarks")
    private String tech_remarks;
    @Size(max = 1000)
    @Column(name = "comp_used_to_repair")
    private String comp_used_to_repair;
    
    @Size(max = 100)
    @Column(name = "finished_date")
    private String finished_date;
    @Size(max = 100)
    @Column(name = "repaired_by")
    private String repaired_by;
    @Size(max = 1000)
    @Column(name = "final_remarks")
    private String final_remarks;
    @Size(max = 1000)
    @Column(name = "ser_id")
    private String ser_id;
    @Size(max = 100)
    @Column(name = "status")
    private String statusType;
    @Size(max = 100)
    @Column(name = "no_of_days")
    private int no_of_days;
    @Column(name = "currentDate")
    private Date currentdate;

    public Repair_master() {
    }
         
    
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getEntry_date() {
		return entry_date;
	}


	public void setEntry_date(String entry_date) {
		this.entry_date = entry_date;
	}


	public String getDivision() {
		return division;
	}


	public void setDivision(String division) {
		this.division = division;
	}


	public String getSc_ref_no() {
		return sc_ref_no;
	}


	public void setSc_ref_no(String sc_ref_no) {
		this.sc_ref_no = sc_ref_no;
	}


	public String getDef_gir_no() {
		return def_gir_no;
	}


	public void setDef_gir_no(String def_gir_no) {
		this.def_gir_no = def_gir_no;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public String getModel() {
		return model;
	}


	public void setModel(String model) {
		this.model = model;
	}


	public String getDef_brd_mod_name() {
		return def_brd_mod_name;
	}


	public void setDef_brd_mod_name(String def_brd_mod_name) {
		this.def_brd_mod_name = def_brd_mod_name;
	}


	public String getUnit_status() {
		return unit_status;
	}


	public void setUnit_status(String unit_status) {
		this.unit_status = unit_status;
	}


	public String getTech_remarks() {
		return tech_remarks;
	}


	public void setTech_remarks(String tech_remarks) {
		this.tech_remarks = tech_remarks;
	}


	public String getComp_used_to_repair() {
		return comp_used_to_repair;
	}


	public void setComp_used_to_repair(String comp_used_to_repair) {
		this.comp_used_to_repair = comp_used_to_repair;
	}


	public String getFinished_date() {
		return finished_date;
	}


	public void setFinished_date(String finished_date) {
		this.finished_date = finished_date;
	}


	public String getRepaired_by() {
		return repaired_by;
	}


	public void setRepaired_by(String repaired_by) {
		this.repaired_by = repaired_by;
	}


	public String getFinal_remarks() {
		return final_remarks;
	}


	public void setFinal_remarks(String final_remarks) {
		this.final_remarks = final_remarks;
	}


	public String getStatusType() {
		return statusType;
	}


	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}


	public int getNo_of_days() {
		return no_of_days;
	}


	public void setNo_of_days(int no_of_days) {
		this.no_of_days = no_of_days;
	}


	public Date getCurrentdate() {
		return currentdate;
	}


	public void setCurrentdate(Date currentdate) {
		this.currentdate = currentdate;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	


	public String getSer_id() {
		return ser_id;
	}


	public void setSer_id(String ser_id) {
		this.ser_id = ser_id;
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
        if (!(object instanceof Repair_master)) {
            return false;
        }
        Repair_master other = (Repair_master) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.schillerindiaservices.bean.Repairmaster[ id=" + id + " ]";
    }

   
    
}
