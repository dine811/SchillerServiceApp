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
 * @author SHINELOGICS
 */
@Entity
@Table(name = "emploeemaster")
@NamedQueries({
    @NamedQuery(name = "Emploeemaster.findAll", query = "SELECT e FROM Emploeemaster e"),
    @NamedQuery(name = "Emploeemaster.findByEmpId", query = "SELECT e FROM Emploeemaster e WHERE e.empId = :empId"),
    @NamedQuery(name = "Emploeemaster.findByEmpName", query = "SELECT e FROM Emploeemaster e WHERE e.empName = :empName"),
    @NamedQuery(name = "Emploeemaster.findByEmpAddress", query = "SELECT e FROM Emploeemaster e WHERE e.empAddress = :empAddress"),
    @NamedQuery(name = "Emploeemaster.findByEmpEmail", query = "SELECT e FROM Emploeemaster e WHERE e.empEmail = :empEmail"),
    @NamedQuery(name = "Emploeemaster.findByEmpPassword", query = "SELECT e FROM Emploeemaster e WHERE e.empPassword = :empPassword"),
    @NamedQuery(name = "Emploeemaster.findByEmpMobile", query = "SELECT e FROM Emploeemaster e WHERE e.empMobile = :empMobile"),
    @NamedQuery(name = "Emploeemaster.findByEmpRole", query = "SELECT e FROM Emploeemaster e WHERE e.empRole = :empRole"),
    @NamedQuery(name = "Emploeemaster.findByEmpActive", query = "SELECT e FROM Emploeemaster e WHERE e.empActive = :empActive"),
    @NamedQuery(name = "Emploeemaster.findByEmpBranch", query = "SELECT e FROM Emploeemaster e WHERE e.empBranch = :empBranch"),
    @NamedQuery(name = "Emploeemaster.findByEmpDept", query = "SELECT e FROM Emploeemaster e WHERE e.empDept = :empDept"),
    @NamedQuery(name = "Emploeemaster.findByEmpDivision", query = "SELECT e FROM Emploeemaster e WHERE e.empDivision = :empDivision"),
    @NamedQuery(name = "Emploeemaster.findByRefId", query = "SELECT e FROM Emploeemaster e WHERE e.refId = :refId")})
public class Emploeemaster implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "emp_id")
    private Integer empId;
    @Size(max = 100)
    @Column(name = "emp_name")
    private String empName;
    @Size(max = 100)
    @Column(name = "emp_address")
    private String empAddress;
    @Size(max = 100)
    @Column(name = "emp_email")
    private String empEmail;
    @Size(max = 100)
    @Column(name = "emp_password")
    private String empPassword;
    @Size(max = 100)
    @Column(name = "emp_mobile")
    private String empMobile;
    @Size(max = 100)
    @Column(name = "emp_role")
    private String empRole;
    @Size(max = 50)
    @Column(name = "emp_active")
    private String empActive;
    @Size(max = 100)
    @Column(name = "emp_branch")
    private String empBranch;
    @Size(max = 100)
    @Column(name = "emp_dept")
    private String empDept;
    @Size(max = 100)
    @Column(name = "emp_division")
    private String empDivision;
    @Size(max = 11)
    @Column(name = "refId")
    private String refId;

    public Emploeemaster() {
    }

    public Emploeemaster(Integer empId) {
        this.empId = empId;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpAddress() {
        return empAddress;
    }

    public void setEmpAddress(String empAddress) {
        this.empAddress = empAddress;
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }

    public String getEmpPassword() {
        return empPassword;
    }

    public void setEmpPassword(String empPassword) {
        this.empPassword = empPassword;
    }

    public String getEmpMobile() {
        return empMobile;
    }

    public void setEmpMobile(String empMobile) {
        this.empMobile = empMobile;
    }

    public String getEmpRole() {
        return empRole;
    }

    public void setEmpRole(String empRole) {
        this.empRole = empRole;
    }

    public String getEmpActive() {
        return empActive;
    }

    public void setEmpActive(String empActive) {
        this.empActive = empActive;
    }

    public String getEmpBranch() {
        return empBranch;
    }

    public void setEmpBranch(String empBranch) {
        this.empBranch = empBranch;
    }

    public String getEmpDept() {
        return empDept;
    }

    public void setEmpDept(String empDept) {
        this.empDept = empDept;
    }

    public String getEmpDivision() {
        return empDivision;
    }

    public void setEmpDivision(String empDivision) {
        this.empDivision = empDivision;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (empId != null ? empId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Emploeemaster)) {
            return false;
        }
        Emploeemaster other = (Emploeemaster) object;
        if ((this.empId == null && other.empId != null) || (this.empId != null && !this.empId.equals(other.empId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.schillerindiaservices.bean.Emploeemaster[ empId=" + empId + " ]";
    }
    
}
