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
@Entity
@Table(name = "partnumber_entry")

public class partnumber_Entry implements Serializable {
      
	

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "part_id")
    private Integer partId;
    @Column(name = "part_number")
    private String partNumber;
    @Column(name = "brd_Name")
    private String brdName;
    @Size(max = 50)
    @Column(name = "compatible_models")
    private String compatiblemodels;
  
    @Column(name = "cost")
    private Double cost;
    
    
	



	public Integer getPartId() {
		return partId;
	}



	public void setPartId(Integer partId) {
		this.partId = partId;
	}



	


	public String getPartNumber() {
		return partNumber;
	}



	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}



	public String getBrdName() {
		return brdName;
	}



	public void setBrdName(String brdName) {
		this.brdName = brdName;
	}



	public String getCompatiblemodels() {
		return compatiblemodels;
	}



	public void setCompatiblemodels(String compatiblemodels) {
		this.compatiblemodels = compatiblemodels;
	}



	



	public Double getCost() {
		return cost;
	}



	public void setCost(Double cost) {
		this.cost = cost;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
	  @Override
	    public int hashCode() {
	        int hash = 0;
	        hash += (partId != null ? partId.hashCode() : 0);
	        return hash;
	    }

	    @Override
	    public boolean equals(Object object) {
	        // TODO: Warning - this method won't work in the case the id fields are not set
	        if (!(object instanceof Region)) {
	            return false;
	        }
	        partnumber_Entry other = (partnumber_Entry) object;
	        if ((this.partId == null && other.partId != null) || (this.partId != null && !this.partId.equals(other.partId))) {
	            return false;
	        }
	        return true;
	    }

	    @Override
	    public String toString() {
	        return "com.schillerindiaservices.bean.partnumber_Entry[ Part_id=" + partId + " ]";
	    }
    
    
	
	
	
}
