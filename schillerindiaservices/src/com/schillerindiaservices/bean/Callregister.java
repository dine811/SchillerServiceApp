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
 * @author ShineLoGics
 */
@Entity
@Table(name = "callregister")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Callregister.findAll", query = "SELECT c FROM Callregister c")
    , @NamedQuery(name = "Callregister.findById", query = "SELECT c FROM Callregister c WHERE c.id = :id")
    , @NamedQuery(name = "Callregister.findByDivision", query = "SELECT c FROM Callregister c WHERE c.division = :division")
    , @NamedQuery(name = "Callregister.findByScEngg", query = "SELECT c FROM Callregister c WHERE c.scEngg = :scEngg")
    , @NamedQuery(name = "Callregister.findByCallDate", query = "SELECT c FROM Callregister c WHERE c.callDate = :callDate")
    , @NamedQuery(name = "Callregister.findByCall", query = "SELECT c FROM Callregister c WHERE c.call = :call")
    , @NamedQuery(name = "Callregister.findByRegion", query = "SELECT c FROM Callregister c WHERE c.region = :region")
    , @NamedQuery(name = "Callregister.findByBranch", query = "SELECT c FROM Callregister c WHERE c.branch = :branch")
    , @NamedQuery(name = "Callregister.findByDealer", query = "SELECT c FROM Callregister c WHERE c.dealer = :dealer")
    , @NamedQuery(name = "Callregister.findByEngineer", query = "SELECT c FROM Callregister c WHERE c.engineer = :engineer")
    , @NamedQuery(name = "Callregister.findByModel", query = "SELECT c FROM Callregister c WHERE c.model = :model")
    , @NamedQuery(name = "Callregister.findByTypeOfCall", query = "SELECT c FROM Callregister c WHERE c.typeOfCall = :typeOfCall")
    , @NamedQuery(name = "Callregister.findByTypeOfCommunication", query = "SELECT c FROM Callregister c WHERE c.typeOfCommunication = :typeOfCommunication")
    , @NamedQuery(name = "Callregister.findByRemarks", query = "SELECT c FROM Callregister c WHERE c.remarks = :remarks")
    , @NamedQuery(name = "Callregister.findByDuration", query = "SELECT c FROM Callregister c WHERE c.duration = :duration")
    , @NamedQuery(name = "Callregister.findByStatus", query = "SELECT c FROM Callregister c WHERE c.status = :status")})
public class Callregister implements Serializable {

    @Size(max = 50)
    @Column(name = "entry_date")
    private String entryDate;

    @Size(max = 100)
    @Column(name = "call_type")
    private String callType;
    @Size(max = 100)
    @Column(name = "status_type")
    private String statusType;

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
    @Column(name = "call_date")
    private String callDate;
    @Size(max = 100)
    @Column(name = "call")
    private String call;
    @Size(max = 100)
    @Column(name = "region")
    private String region;
    @Size(max = 100)
    @Column(name = "branch")
    private String branch;
    @Size(max = 100)
    @Column(name = "dealer")
    private String dealer;
    @Size(max = 100)
    @Column(name = "engineer")
    private String engineer;
    @Size(max = 100)
    @Column(name = "model")
    private String model;
    @Size(max = 100)
    @Column(name = "type_of_call")
    private String typeOfCall;
    @Size(max = 100)
    @Column(name = "type_of_communication")
    private String typeOfCommunication;
    @Size(max = 100)
    @Column(name = "remarks")
    private String remarks;
    @Size(max = 100)
    @Column(name = "duration")
    private String duration;
    @Size(max = 100)
    @Column(name = "status")
    private String status;

    public Callregister() {
    }

    public Callregister(Integer id) {
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

    public String getCallDate() {
        return callDate;
    }

    public void setCallDate(String callDate) {
        this.callDate = callDate;
    }

    public String getCall() {
        return call;
    }

    public void setCall(String call) {
        this.call = call;
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

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }

    public String getEngineer() {
        return engineer;
    }

    public void setEngineer(String engineer) {
        this.engineer = engineer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getTypeOfCall() {
        return typeOfCall;
    }

    public void setTypeOfCall(String typeOfCall) {
        this.typeOfCall = typeOfCall;
    }

    public String getTypeOfCommunication() {
        return typeOfCommunication;
    }

    public void setTypeOfCommunication(String typeOfCommunication) {
        this.typeOfCommunication = typeOfCommunication;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        if (!(object instanceof Callregister)) {
            return false;
        }
        Callregister other = (Callregister) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.schillerindiaservices.bean.Callregister[ id=" + id + " ]";
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }
    
}
