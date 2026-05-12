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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ShineLoGics
 */
@Entity
@Table(name = "email")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Email.findAll", query = "SELECT e FROM Email e")
    , @NamedQuery(name = "Email.findByMailid", query = "SELECT e FROM Email e WHERE e.mailid = :mailid")
    , @NamedQuery(name = "Email.findByName", query = "SELECT e FROM Email e WHERE e.name = :name")
    , @NamedQuery(name = "Email.findByDirection", query = "SELECT e FROM Email e WHERE e.direction = :direction")})
public class Email implements Serializable {

    @Size(max = 100)
    @Column(name = "hosing_id")
    private String hosingId;

    @Size(max = 100)
    @Column(name = "host_id")
    private String hostId;

    @Size(max = 300)
    @Column(name = "msg_body")
    private String msgBody;

    @Size(max = 50)
    @Column(name = "password_f")
    private String passwordF;
    @Size(max = 50)
    @Column(name = "port_no")
    private String portNo;
    @Size(max = 50)
    @Column(name = "ssl_no")
    private String sslNo;

    @Size(max = 50)
    @Column(name = "semail")
    private String semail;
    @Size(max = 50)
    @Column(name = "password")
    private String password;
    @Size(max = 50)
    @Column(name = "port")
    private String port;
    @Size(max = 50)
    @Column(name = "ssl")
    private String ssl;

    @Basic(optional = false)
    @Column(name = "id")
    private int id;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "mailid")
    private String mailid;
    @Size(max = 50)
    @Column(name = "name")
    private String name;
    @Size(max = 50)
    @Column(name = "direction")
    private String direction;

    public Email() {
    }

    public Email(String mailid) {
        this.mailid = mailid;
    }

    public String getMailid() {
        return mailid;
    }

    public void setMailid(String mailid) {
        this.mailid = mailid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mailid != null ? mailid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Email)) {
            return false;
        }
        Email other = (Email) object;
        if ((this.mailid == null && other.mailid != null) || (this.mailid != null && !this.mailid.equals(other.mailid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.schillerindiaservices.bean.Email[ mailid=" + mailid + " ]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSemail() {
        return semail;
    }

    public void setSemail(String semail) {
        this.semail = semail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getSsl() {
        return ssl;
    }

    public void setSsl(String ssl) {
        this.ssl = ssl;
    }

    public String getPasswordF() {
        return passwordF;
    }

    public void setPasswordF(String passwordF) {
        this.passwordF = passwordF;
    }

    public String getPortNo() {
        return portNo;
    }

    public void setPortNo(String portNo) {
        this.portNo = portNo;
    }

    public String getSslNo() {
        return sslNo;
    }

    public void setSslNo(String sslNo) {
        this.sslNo = sslNo;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getHosingId() {
        return hosingId;
    }

    public void setHosingId(String hosingId) {
        this.hosingId = hosingId;
    }
    
}
