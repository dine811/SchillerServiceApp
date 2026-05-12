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

/**
 *
 * @author MR
 */
@Entity
@Table(name = "jobsheetmaster")
@NamedQueries({
    @NamedQuery(name = "Jobsheetmaster.findAll", query = "SELECT j FROM Jobsheetmaster j")
    , @NamedQuery(name = "Jobsheetmaster.findByJobid", query = "SELECT j FROM Jobsheetmaster j WHERE j.jobid = :jobid")
    , @NamedQuery(name = "Jobsheetmaster.findByServiceid", query = "SELECT j FROM Jobsheetmaster j WHERE j.serviceid = :serviceid")
    , @NamedQuery(name = "Jobsheetmaster.findByRepDate", query = "SELECT j FROM Jobsheetmaster j WHERE j.repDate = :repDate")
    , @NamedQuery(name = "Jobsheetmaster.findByEngnrId", query = "SELECT j FROM Jobsheetmaster j WHERE j.engnrId = :engnrId")
    , @NamedQuery(name = "Jobsheetmaster.findByObservation", query = "SELECT j FROM Jobsheetmaster j WHERE j.observation = :observation")
    , @NamedQuery(name = "Jobsheetmaster.findByRepActivity", query = "SELECT j FROM Jobsheetmaster j WHERE j.repActivity = :repActivity")
    , @NamedQuery(name = "Jobsheetmaster.findByTimeSpent", query = "SELECT j FROM Jobsheetmaster j WHERE j.timeSpent = :timeSpent")
    , @NamedQuery(name = "Jobsheetmaster.findByRemarks", query = "SELECT j FROM Jobsheetmaster j WHERE j.remarks = :remarks")})
public class Jobsheetmaster implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "jobid")
    private Integer jobid;
    @Column(name = "serviceid")
    private Integer serviceid;
    @Column(name = "rep_date")
    @Temporal(TemporalType.DATE)
    private Date repDate;
    @Column(name = "engnr_id")
    private Integer engnrId;
    @Size(max = 200)
    @Column(name = "observation")
    private String observation;
    @Size(max = 200)
    @Column(name = "rep_activity")
    private String repActivity;
    @Size(max = 50)
    @Column(name = "time spent")
    private String timeSpent;
    @Size(max = 100)
    @Column(name = "remarks")
    private String remarks;

    public Jobsheetmaster() {
    }

    public Jobsheetmaster(Integer jobid) {
        this.jobid = jobid;
    }

    public Integer getJobid() {
        return jobid;
    }

    public void setJobid(Integer jobid) {
        this.jobid = jobid;
    }

    public Integer getServiceid() {
        return serviceid;
    }

    public void setServiceid(Integer serviceid) {
        this.serviceid = serviceid;
    }

    public Date getRepDate() {
        return repDate;
    }

    public void setRepDate(Date repDate) {
        this.repDate = repDate;
    }

    public Integer getEngnrId() {
        return engnrId;
    }

    public void setEngnrId(Integer engnrId) {
        this.engnrId = engnrId;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getRepActivity() {
        return repActivity;
    }

    public void setRepActivity(String repActivity) {
        this.repActivity = repActivity;
    }

    public String getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(String timeSpent) {
        this.timeSpent = timeSpent;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jobid != null ? jobid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Jobsheetmaster)) {
            return false;
        }
        Jobsheetmaster other = (Jobsheetmaster) object;
        if ((this.jobid == null && other.jobid != null) || (this.jobid != null && !this.jobid.equals(other.jobid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.schillerindiaservices.bean.Jobsheetmaster[ jobid=" + jobid + " ]";
    }
    
}
