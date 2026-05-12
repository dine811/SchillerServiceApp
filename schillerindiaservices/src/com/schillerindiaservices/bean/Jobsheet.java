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
 * @author SHINELOGICS
 */
@Entity
@Table(name = "jobsheet")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Jobsheet.findAll", query = "SELECT j FROM Jobsheet j"),
    @NamedQuery(name = "Jobsheet.findById", query = "SELECT j FROM Jobsheet j WHERE j.id = :id"),
    @NamedQuery(name = "Jobsheet.findByRepairDate", query = "SELECT j FROM Jobsheet j WHERE j.repairDate = :repairDate"),
    @NamedQuery(name = "Jobsheet.findByEnginnerName", query = "SELECT j FROM Jobsheet j WHERE j.enginnerName = :enginnerName"),
    @NamedQuery(name = "Jobsheet.findByObservation", query = "SELECT j FROM Jobsheet j WHERE j.observation = :observation"),
    @NamedQuery(name = "Jobsheet.findByRepairActivity", query = "SELECT j FROM Jobsheet j WHERE j.repairActivity = :repairActivity"),
    @NamedQuery(name = "Jobsheet.findByTimeSpent", query = "SELECT j FROM Jobsheet j WHERE j.timeSpent = :timeSpent"),
    @NamedQuery(name = "Jobsheet.findByRemark", query = "SELECT j FROM Jobsheet j WHERE j.remark = :remark"),
    @NamedQuery(name = "Jobsheet.findBySerId", query = "SELECT j FROM Jobsheet j WHERE j.serId = :serId"),
    @NamedQuery(name = "Jobsheet.findByRepairDate1", query = "SELECT j FROM Jobsheet j WHERE j.repairDate1 = :repairDate1"),
    @NamedQuery(name = "Jobsheet.findByRepairDate2", query = "SELECT j FROM Jobsheet j WHERE j.repairDate2 = :repairDate2"),
    @NamedQuery(name = "Jobsheet.findByRepairDate3", query = "SELECT j FROM Jobsheet j WHERE j.repairDate3 = :repairDate3"),
    @NamedQuery(name = "Jobsheet.findByRepairDate4", query = "SELECT j FROM Jobsheet j WHERE j.repairDate4 = :repairDate4"),
    @NamedQuery(name = "Jobsheet.findByEnginnerName1", query = "SELECT j FROM Jobsheet j WHERE j.enginnerName1 = :enginnerName1"),
    @NamedQuery(name = "Jobsheet.findByEnginnerName2", query = "SELECT j FROM Jobsheet j WHERE j.enginnerName2 = :enginnerName2"),
    @NamedQuery(name = "Jobsheet.findByEnginnerName3", query = "SELECT j FROM Jobsheet j WHERE j.enginnerName3 = :enginnerName3"),
    @NamedQuery(name = "Jobsheet.findByEnginnerName4", query = "SELECT j FROM Jobsheet j WHERE j.enginnerName4 = :enginnerName4"),
    @NamedQuery(name = "Jobsheet.findByObservation1", query = "SELECT j FROM Jobsheet j WHERE j.observation1 = :observation1"),
    @NamedQuery(name = "Jobsheet.findByObservation2", query = "SELECT j FROM Jobsheet j WHERE j.observation2 = :observation2"),
    @NamedQuery(name = "Jobsheet.findByObservation3", query = "SELECT j FROM Jobsheet j WHERE j.observation3 = :observation3"),
    @NamedQuery(name = "Jobsheet.findByObservation4", query = "SELECT j FROM Jobsheet j WHERE j.observation4 = :observation4"),
    @NamedQuery(name = "Jobsheet.findByRepairActivity1", query = "SELECT j FROM Jobsheet j WHERE j.repairActivity1 = :repairActivity1"),
    @NamedQuery(name = "Jobsheet.findByRepairActivity2", query = "SELECT j FROM Jobsheet j WHERE j.repairActivity2 = :repairActivity2"),
    @NamedQuery(name = "Jobsheet.findByRepairActivity3", query = "SELECT j FROM Jobsheet j WHERE j.repairActivity3 = :repairActivity3"),
    @NamedQuery(name = "Jobsheet.findByRepairActivity4", query = "SELECT j FROM Jobsheet j WHERE j.repairActivity4 = :repairActivity4"),
    @NamedQuery(name = "Jobsheet.findByTimeSpent1", query = "SELECT j FROM Jobsheet j WHERE j.timeSpent1 = :timeSpent1"),
    @NamedQuery(name = "Jobsheet.findByTimeSpent2", query = "SELECT j FROM Jobsheet j WHERE j.timeSpent2 = :timeSpent2"),
    @NamedQuery(name = "Jobsheet.findByTimeSpent3", query = "SELECT j FROM Jobsheet j WHERE j.timeSpent3 = :timeSpent3"),
    @NamedQuery(name = "Jobsheet.findByTimeSpent4", query = "SELECT j FROM Jobsheet j WHERE j.timeSpent4 = :timeSpent4"),
    @NamedQuery(name = "Jobsheet.findByRemark1", query = "SELECT j FROM Jobsheet j WHERE j.remark1 = :remark1"),
    @NamedQuery(name = "Jobsheet.findByRemark2", query = "SELECT j FROM Jobsheet j WHERE j.remark2 = :remark2"),
    @NamedQuery(name = "Jobsheet.findByRemark3", query = "SELECT j FROM Jobsheet j WHERE j.remark3 = :remark3"),
    @NamedQuery(name = "Jobsheet.findByRemark4", query = "SELECT j FROM Jobsheet j WHERE j.remark4 = :remark4")})
public class Jobsheet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "repair_date")
    private String repairDate;
    @Size(max = 255)
    @Column(name = "enginner_name")
    private String enginnerName;
    @Size(max = 255)
    @Column(name = "observation")
    private String observation;
    @Size(max = 255)
    @Column(name = "repair_activity")
    private String repairActivity;
    @Size(max = 255)
    @Column(name = "time_spent")
    private String timeSpent;
    @Size(max = 255)
    @Column(name = "remark")
    private String remark;
    @Column(name = "ser_id")
    private Integer serId;
    @Size(max = 255)
    @Column(name = "repair_date1")
    private String repairDate1;
    @Size(max = 255)
    @Column(name = "repair_date2")
    private String repairDate2;
    @Size(max = 255)
    @Column(name = "repair_date3")
    private String repairDate3;
    @Size(max = 255)
    @Column(name = "repair_date4")
    private String repairDate4;
    @Size(max = 255)
    @Column(name = "enginner_name1")
    private String enginnerName1;
    @Size(max = 255)
    @Column(name = "enginner_name2")
    private String enginnerName2;
    @Size(max = 255)
    @Column(name = "enginner_name3")
    private String enginnerName3;
    @Size(max = 255)
    @Column(name = "enginner_name4")
    private String enginnerName4;
    @Size(max = 255)
    @Column(name = "observation1")
    private String observation1;
    @Size(max = 255)
    @Column(name = "observation2")
    private String observation2;
    @Size(max = 255)
    @Column(name = "observation3")
    private String observation3;
    @Size(max = 255)
    @Column(name = "observation4")
    private String observation4;
    @Size(max = 255)
    @Column(name = "repair_activity1")
    private String repairActivity1;
    @Size(max = 255)
    @Column(name = "repair_activity2")
    private String repairActivity2;
    @Size(max = 255)
    @Column(name = "repair_activity3")
    private String repairActivity3;
    @Size(max = 255)
    @Column(name = "repair_activity4")
    private String repairActivity4;
    @Size(max = 255)
    @Column(name = "time_spent1")
    private String timeSpent1;
    @Size(max = 255)
    @Column(name = "time_spent2")
    private String timeSpent2;
    @Size(max = 255)
    @Column(name = "time_spent3")
    private String timeSpent3;
    @Size(max = 255)
    @Column(name = "time_spent4")
    private String timeSpent4;
    @Size(max = 255)
    @Column(name = "remark1")
    private String remark1;
    @Size(max = 255)
    @Column(name = "remark2")
    private String remark2;
    @Size(max = 255)
    @Column(name = "remark3")
    private String remark3;
    @Size(max = 255)
    @Column(name = "remark4")
    private String remark4;

    public Jobsheet() {
    }

    public Jobsheet(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRepairDate() {
        return repairDate;
    }

    public void setRepairDate(String repairDate) {
        this.repairDate = repairDate;
    }

    public String getEnginnerName() {
        return enginnerName;
    }

    public void setEnginnerName(String enginnerName) {
        this.enginnerName = enginnerName;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getRepairActivity() {
        return repairActivity;
    }

    public void setRepairActivity(String repairActivity) {
        this.repairActivity = repairActivity;
    }

    public String getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(String timeSpent) {
        this.timeSpent = timeSpent;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSerId() {
        return serId;
    }

    public void setSerId(Integer serId) {
        this.serId = serId;
    }

    public String getRepairDate1() {
        return repairDate1;
    }

    public void setRepairDate1(String repairDate1) {
        this.repairDate1 = repairDate1;
    }

    public String getRepairDate2() {
        return repairDate2;
    }

    public void setRepairDate2(String repairDate2) {
        this.repairDate2 = repairDate2;
    }

    public String getRepairDate3() {
        return repairDate3;
    }

    public void setRepairDate3(String repairDate3) {
        this.repairDate3 = repairDate3;
    }

    public String getRepairDate4() {
        return repairDate4;
    }

    public void setRepairDate4(String repairDate4) {
        this.repairDate4 = repairDate4;
    }

    public String getEnginnerName1() {
        return enginnerName1;
    }

    public void setEnginnerName1(String enginnerName1) {
        this.enginnerName1 = enginnerName1;
    }

    public String getEnginnerName2() {
        return enginnerName2;
    }

    public void setEnginnerName2(String enginnerName2) {
        this.enginnerName2 = enginnerName2;
    }

    public String getEnginnerName3() {
        return enginnerName3;
    }

    public void setEnginnerName3(String enginnerName3) {
        this.enginnerName3 = enginnerName3;
    }

    public String getEnginnerName4() {
        return enginnerName4;
    }

    public void setEnginnerName4(String enginnerName4) {
        this.enginnerName4 = enginnerName4;
    }

    public String getObservation1() {
        return observation1;
    }

    public void setObservation1(String observation1) {
        this.observation1 = observation1;
    }

    public String getObservation2() {
        return observation2;
    }

    public void setObservation2(String observation2) {
        this.observation2 = observation2;
    }

    public String getObservation3() {
        return observation3;
    }

    public void setObservation3(String observation3) {
        this.observation3 = observation3;
    }

    public String getObservation4() {
        return observation4;
    }

    public void setObservation4(String observation4) {
        this.observation4 = observation4;
    }

    public String getRepairActivity1() {
        return repairActivity1;
    }

    public void setRepairActivity1(String repairActivity1) {
        this.repairActivity1 = repairActivity1;
    }

    public String getRepairActivity2() {
        return repairActivity2;
    }

    public void setRepairActivity2(String repairActivity2) {
        this.repairActivity2 = repairActivity2;
    }

    public String getRepairActivity3() {
        return repairActivity3;
    }

    public void setRepairActivity3(String repairActivity3) {
        this.repairActivity3 = repairActivity3;
    }

    public String getRepairActivity4() {
        return repairActivity4;
    }

    public void setRepairActivity4(String repairActivity4) {
        this.repairActivity4 = repairActivity4;
    }

    public String getTimeSpent1() {
        return timeSpent1;
    }

    public void setTimeSpent1(String timeSpent1) {
        this.timeSpent1 = timeSpent1;
    }

    public String getTimeSpent2() {
        return timeSpent2;
    }

    public void setTimeSpent2(String timeSpent2) {
        this.timeSpent2 = timeSpent2;
    }

    public String getTimeSpent3() {
        return timeSpent3;
    }

    public void setTimeSpent3(String timeSpent3) {
        this.timeSpent3 = timeSpent3;
    }

    public String getTimeSpent4() {
        return timeSpent4;
    }

    public void setTimeSpent4(String timeSpent4) {
        this.timeSpent4 = timeSpent4;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    public String getRemark3() {
        return remark3;
    }

    public void setRemark3(String remark3) {
        this.remark3 = remark3;
    }

    public String getRemark4() {
        return remark4;
    }

    public void setRemark4(String remark4) {
        this.remark4 = remark4;
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
        if (!(object instanceof Jobsheet)) {
            return false;
        }
        Jobsheet other = (Jobsheet) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.schillerindiaservices.bean.Jobsheet[ id=" + id + " ]";
    }
    
}
