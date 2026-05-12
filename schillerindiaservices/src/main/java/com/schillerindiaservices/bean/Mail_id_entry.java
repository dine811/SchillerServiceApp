package com.schillerindiaservices.bean;

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
@Table(name = "mail_id_entry")
@NamedQueries({
    @NamedQuery(name = "Mail_id_entry.findAll", query = "SELECT b FROM Mail_id_entry b")
    , @NamedQuery(name = "Mail_id_entry.findByMail_id_entry_id", query = "SELECT b FROM Mail_id_entry b WHERE b.mail_id_entry_id = :mail_id_entry_id")
    , @NamedQuery(name = "Mail_id_entry.findByReport_type", query = "SELECT b FROM Mail_id_entry b WHERE b.report_type = :report_type")
    , @NamedQuery(name = "Mail_id_entry.findByTemp1", query = "SELECT b FROM Mail_id_entry b WHERE b.temp1 = :temp1")
    , @NamedQuery(name = "Mail_id_entry.findByTemp2", query = "SELECT b FROM Mail_id_entry b WHERE b.temp2 = :temp2")
          })





public class Mail_id_entry {


    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "mail_id_entry_id")
    private Integer mail_id_entry_id;
   
    @Size(max = 255)
    @Column(name = "mail_id")
    private String mail_id;
    @Size(max = 255)
    @Column(name = "report_type")
    private String report_type;
    @Size(max = 150)
    @Column(name = "temp1")
    private String temp1;
    @Size(max = 255)
    @Column(name = "temp2")
    private String temp2;

    
    
    
    
    
    
   

    public Integer getMail_id_entry_id() {
		return mail_id_entry_id;
	}

	public void setMail_id_entry_id(Integer mail_id_entry_id) {
		this.mail_id_entry_id = mail_id_entry_id;
	}

	public String getMail_id() {
		return mail_id;
	}

	public void setMail_id(String mail_id) {
		this.mail_id = mail_id;
	}

	public String getReport_type() {
		return report_type;
	}

	public void setReport_type(String report_type) {
		this.report_type = report_type;
	}

	public String getTemp1() {
		return temp1;
	}

	public void setTemp1(String temp1) {
		this.temp1 = temp1;
	}

	public String getTemp2() {
		return temp2;
	}

	public void setTemp2(String temp2) {
		this.temp2 = temp2;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (mail_id_entry_id != null ? mail_id_entry_id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mail_id_entry)) {
            return false;
        }
        Mail_id_entry other = (Mail_id_entry) object;
        if ((this.mail_id_entry_id == null && other.mail_id_entry_id != null) || (this.mail_id_entry_id != null && !this.mail_id_entry_id.equals(other.mail_id_entry_id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.schillerindiaservices.bean.Mail_id_entry[ mail_id_entry_id=" + mail_id_entry_id + " ]";
    }
    

	

}
