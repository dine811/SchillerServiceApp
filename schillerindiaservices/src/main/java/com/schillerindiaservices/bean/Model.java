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
@Table(name = "model")
@NamedQueries({
    @NamedQuery(name = "Model.findAll", query = "SELECT m FROM Model m"),
    @NamedQuery(name = "Model.findByModelId", query = "SELECT m FROM Model m WHERE m.modelId = :modelId"),
    @NamedQuery(name = "Model.findByProductId", query = "SELECT m FROM Model m WHERE m.productId = :productId"),
    @NamedQuery(name = "Model.findBySupId", query = "SELECT m FROM Model m WHERE m.supId = :supId"),
    @NamedQuery(name = "Model.findByModelName", query = "SELECT m FROM Model m WHERE m.modelName = :modelName"),
    @NamedQuery(name = "Model.findByModelDesc", query = "SELECT m FROM Model m WHERE m.modelDesc = :modelDesc"),
    @NamedQuery(name = "Model.findBySupName", query = "SELECT m FROM Model m WHERE m.supName = :supName")})
public class Model implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "model_id")
    private Integer modelId;
    @Column(name = "product_id")
    private Integer productId;
    @Column(name = "sup_id")
    private Integer supId;
    @Size(max = 100)
    @Column(name = "model_name")
    private String modelName;
    @Size(max = 250)
    @Column(name = "model_desc")
    private String modelDesc;
    @Size(max = 100)
    @Column(name = "sup_name")
    private String supName;

    public Model() {
    }

    public Model(Integer modelId) {
        this.modelId = modelId;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getSupId() {
        return supId;
    }

    public void setSupId(Integer supId) {
        this.supId = supId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelDesc() {
        return modelDesc;
    }

    public void setModelDesc(String modelDesc) {
        this.modelDesc = modelDesc;
    }

    public String getSupName() {
        return supName;
    }

    public void setSupName(String supName) {
        this.supName = supName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (modelId != null ? modelId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Model)) {
            return false;
        }
        Model other = (Model) object;
        if ((this.modelId == null && other.modelId != null) || (this.modelId != null && !this.modelId.equals(other.modelId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.schillerindiaservices.bean.Model[ modelId=" + modelId + " ]";
    }
    
}
