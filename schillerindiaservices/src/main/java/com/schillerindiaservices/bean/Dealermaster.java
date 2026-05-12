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
@Table(name = "dealermaster")
@NamedQueries({
    @NamedQuery(name = "Dealermaster.findAll", query = "SELECT d FROM Dealermaster d")
    , @NamedQuery(name = "Dealermaster.findByDealerId", query = "SELECT d FROM Dealermaster d WHERE d.dealerId = :dealerId")
    , @NamedQuery(name = "Dealermaster.findByDealerName", query = "SELECT d FROM Dealermaster d WHERE d.dealerName = :dealerName")
    , @NamedQuery(name = "Dealermaster.findByDealerAddress", query = "SELECT d FROM Dealermaster d WHERE d.dealerAddress = :dealerAddress")
    , @NamedQuery(name = "Dealermaster.findByDealerMail", query = "SELECT d FROM Dealermaster d WHERE d.dealerMail = :dealerMail")
    , @NamedQuery(name = "Dealermaster.findByDealerPhone", query = "SELECT d FROM Dealermaster d WHERE d.dealerPhone = :dealerPhone")
    , @NamedQuery(name = "Dealermaster.findByDealerRegion", query = "SELECT d FROM Dealermaster d WHERE d.dealerRegion = :dealerRegion")})
public class Dealermaster implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "dealer_id")
    private Integer dealerId;
    @Size(max = 50)
    @Column(name = "dealer_name")
    private String dealerName;
    @Size(max = 100)
    @Column(name = "dealer_address")
    private String dealerAddress;
    @Size(max = 50)
    @Column(name = "dealer_mail")
    private String dealerMail;
    @Size(max = 20)
    @Column(name = "dealer_phone")
    private String dealerPhone;
    @Column(name = "dealer_region")
    private Integer dealerRegion;

    public Dealermaster() {
    }

    public Dealermaster(Integer dealerId) {
        this.dealerId = dealerId;
    }

    public Integer getDealerId() {
        return dealerId;
    }

    public void setDealerId(Integer dealerId) {
        this.dealerId = dealerId;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getDealerAddress() {
        return dealerAddress;
    }

    public void setDealerAddress(String dealerAddress) {
        this.dealerAddress = dealerAddress;
    }

    public String getDealerMail() {
        return dealerMail;
    }

    public void setDealerMail(String dealerMail) {
        this.dealerMail = dealerMail;
    }

    public String getDealerPhone() {
        return dealerPhone;
    }

    public void setDealerPhone(String dealerPhone) {
        this.dealerPhone = dealerPhone;
    }

    public Integer getDealerRegion() {
        return dealerRegion;
    }

    public void setDealerRegion(Integer dealerRegion) {
        this.dealerRegion = dealerRegion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dealerId != null ? dealerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dealermaster)) {
            return false;
        }
        Dealermaster other = (Dealermaster) object;
        if ((this.dealerId == null && other.dealerId != null) || (this.dealerId != null && !this.dealerId.equals(other.dealerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.schillerindiaservices.bean.Dealermaster[ dealerId=" + dealerId + " ]";
    }
    
}
