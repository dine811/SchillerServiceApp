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
@Table(name = "sparepart_master")
@NamedQueries({
    @NamedQuery(name = "Sparepart_master.findAll", query = "SELECT b FROM Sparepart_master b")
    , @NamedQuery(name = "Sparepart_master.findByspareid", query = "SELECT b FROM Sparepart_master b WHERE b.spareid = :spareid")
    , @NamedQuery(name = "sparepart_master.findByPart_number", query = "SELECT b FROM sparepart_master b WHERE b.part_number = :part_number")
    , @NamedQuery(name = "sparepart_master.findByComp_models", query = "SELECT b FROM sparepart_master b WHERE b.comp_models = :comp_models")
    , @NamedQuery(name = "sparepart_master.findByDef_mod_brd_name", query = "SELECT b FROM sparepart_master b WHERE b.def_mod_brd_name = :def_mod_brd_name")
    , @NamedQuery(name = "sparepart_master.findByDef_type", query = "SELECT b FROM sparepart_master b WHERE b.def_type = :def_type")
    , @NamedQuery(name = "sparepart_master.findByDivision", query = "SELECT b FROM sparepart_master b WHERE b.division = :division")
   })
public class Sparepart_master implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "spare_id")
    private Integer spareid;
    @Size(max = 300)
    @Column(name = "part_number")
    private String part_number;
    @Size(max = 300)
    @Column(name = "comp_models")
    private String comp_models;
    @Size(max = 300)
    @Column(name = "def_mod_brd_name")
    private String def_mod_brd_name;
    @Size(max = 300)
    @Column(name = "def_type")
    private String def_type;
    @Size(max = 300)
    @Column(name = "division")
    private String division;
    
      
   
    public Integer getSpareid() {
		return spareid;
	}

	public void setSpareid(Integer spareid) {
		this.spareid = spareid;
	}

	public String getPart_number() {
		return part_number;
	}

	public void setPart_number(String part_number) {
		this.part_number = part_number;
	}

	public String getComp_models() {
		return comp_models;
	}

	public void setComp_models(String comp_models) {
		this.comp_models = comp_models;
	}

	public String getDef_mod_brd_name() {
		return def_mod_brd_name;
	}

	public void setDef_mod_brd_name(String def_mod_brd_name) {
		this.def_mod_brd_name = def_mod_brd_name;
	}

	public String getDef_type() {
		return def_type;
	}

	public void setDef_type(String def_type) {
		this.def_type = def_type;
	}
	
	

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (spareid != null ? spareid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sparepart_master)) {
            return false;
        }
        Sparepart_master other = (Sparepart_master) object;
        if ((this.spareid == null && other.spareid != null) || (this.spareid != null && !this.spareid.equals(other.spareid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.schillerindiaservices.bean.sparepart_master[ branchid=" + spareid + " ]";
    }
    
}
