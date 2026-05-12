/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schillerindiaservices.Dao;

import com.schillerindiaservices.bean.Birmaster;
import com.schillerindiaservices.common.UtilFunctions;
import com.schillerindiaservices.connection.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ShineLoGics
 */
public class BIRDao {

	public static Birmaster getById(int id) throws ClassNotFoundException, SQLException {
		Birmaster m = null;
		Connection con = DbConnection.getConnection();
		PreparedStatement ps = con.prepareStatement("SELECT * FROM birmaster WHERE id='" + id + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			m = new Birmaster();
			m.setId(id);
			m.setDivision(rs.getString("division"));
			m.setEntryDate(rs.getString("entry_date"));
			m.setFqcInDate(rs.getString("fqc_in_date"));
			m.setModel(rs.getString("model"));
			m.setConfiguration(rs.getString("configuration"));
			m.setReceivedQty(rs.getString("received_qty"));
			m.setUnitSerialNo(rs.getString("unit_serial_no"));
			m.setInvoiceNo(rs.getString("invoice_no"));
			m.setInvoiceDate(rs.getString("invoice_date"));
			m.setSoftwrChanges(rs.getString("softwr_changes"));
			m.setSoftwrVersion(rs.getString("softwr_version"));
			m.setHardwrChanges(rs.getString("hardwr_changes"));
			m.setHardwareDetails(rs.getString("hardware_details"));
			m.setAccesoryChanges(rs.getString("accesory_changes"));
			m.setAccesoryDetails(rs.getString("accesory_details"));
			m.setUserManualUpdate(rs.getString("user_manual_update"));
			m.setServiceManualUpdate(rs.getString("service_manual_update"));
			m.setFqcRemarks(rs.getString("fqc_remarks"));
			m.setCnrRefNo(rs.getString("cnr_ref_no"));
			m.setTsTeamRemark(rs.getString("ts_team_ver_date")==null ? "":rs.getString("ts_team_ver_date"));
			m.setPsTeamRemark(rs.getString("ps_team_ver_date")==null ? "":rs.getString("ps_team_ver_date"));
			m.setFinalStatus(rs.getString("final_status"));
			m.setClosedDate(rs.getString("closed_date"));
			m.setAccesChngRemark(rs.getString("acces_chng_remark"));
			m.setHrdwrChangRemark(rs.getString("hrdwr_chang_remark"));
			m.setSftwrChngRemark(rs.getString("sftwr_chng_remark"));
			m.setCnrReleseDate(rs.getString("cnr_relese_date")==null ? "":rs.getString("cnr_relese_date"));
			System.out.println("cnr_relese_date ---->"+m.getCnrReleseDate());
			m.setBirRefNo(rs.getString("bir_ref_no"));
			m.setSupplier(rs.getString("supplier"));
			m.setScEngg(rs.getString("sc_engg"));
			m.setPsEngg(rs.getString("ps_engg"));
			m.setApprovedDate(rs.getString("approved_date")==null ? "":rs.getString("approved_date"));
			m.setCnrTecRefNum(rs.getString("cnr_tec_ref_num"));
			m.setUnitInDate(rs.getString("unit_in_date")==null ? "":rs.getString("unit_in_date"));
			m.setTechRemarks(rs.getString("tech_remarks"));
		}
		con.close();
		return m;

	}

	public int Insert(Birmaster bim) throws ClassNotFoundException, SQLException {
		int id = 0;
		UtilFunctions utilfn = new UtilFunctions();
		Connection con = DbConnection.getConnection();
		PreparedStatement ps = con.prepareStatement("INSERT INTO birmaster(division,entry_date,fqc_in_date,model,"
				+ "configuration,received_qty,unit_serial_no,invoice_no,invoice_date,softwr_changes,softwr_version,\n"
				+ "hardwr_changes,hardware_details,accesory_changes,accesory_details,user_manual_update,service_manual_update,"
				+ "fqc_remarks,cnr_ref_no,ts_team_ver_date,\n"
				+ "ps_team_ver_date,final_status,closed_date,acces_chng_remark,hrdwr_chang_remark,sftwr_chng_remark,"
				+ "cnr_relese_date,bir_ref_no,supplier,ps_engg,cnr_tec_ref_num,approved_date,sc_engg,unit_in_date,tech_remarks) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		ps.setString(1, bim.getDivision());
		ps.setDate(2, utilfn.getDbDateFormat(bim.getEntryDate()));
		// ps.setString(3,bim.getFqcInDate());
		ps.setDate(3, utilfn.getDbDateFormat(bim.getFqcInDate()));
		ps.setString(4, bim.getModel());
		ps.setString(5, bim.getConfiguration());
		ps.setString(6, bim.getReceivedQty());
		ps.setString(7, bim.getUnitSerialNo());
		ps.setString(8, bim.getInvoiceNo());
		// ps.setString(9, bim.getInvoiceDate());
		ps.setDate(9, utilfn.getDbDateFormat(bim.getInvoiceDate()));
		ps.setString(10, bim.getSoftwrChanges());
		ps.setString(11, bim.getSoftwrVersion());
		ps.setString(12, bim.getHardwrChanges());
		ps.setString(13, bim.getHardwareDetails());
		ps.setString(14, bim.getAccesoryChanges());
		ps.setString(15, bim.getAccesoryDetails());
		ps.setString(16, bim.getUserManualUpdate());
		ps.setString(17, bim.getServiceManualUpdate());
		ps.setString(18, bim.getFqcRemarks());
		ps.setString(19, bim.getCnrRefNo());
		// ps.setString(20,bim.getTsTeamRemark());
		ps.setDate(20, utilfn.getDbDateFormat(bim.getTsTeamRemark()));
		// ps.setString(21,bim.getPsTeamRemark());
		ps.setDate(21, utilfn.getDbDateFormat(bim.getPsTeamRemark()));
		ps.setString(22, bim.getFinalStatus());
		// ps.setString(23, bim.getClosedDate());
		ps.setDate(23, utilfn.getDbDateFormat(bim.getClosedDate()));
		ps.setString(24, bim.getAccesChngRemark());
		ps.setString(25, bim.getHrdwrChangRemark());
		ps.setString(26, bim.getSftwrChngRemark());
		// ps.setString(27, bim.getCnrReleseDate());
		ps.setDate(27, utilfn.getDbDateFormat(bim.getCnrReleseDate()));
		ps.setString(28, bim.getBirRefNo());
		ps.setString(29, bim.getSupplier());
		ps.setString(30, bim.getPsEngg());
		ps.setString(31, bim.getCnrTecRefNum());
		// ps.setString(32, bim.getApprovedDate());
		ps.setDate(32, utilfn.getDbDateFormat(bim.getApprovedDate()));
		ps.setString(33, bim.getScEngg());
		// ps.setString(34, bim.getUnitInDate());
		ps.setDate(34, utilfn.getDbDateFormat(bim.getUnitInDate()));
		ps.setString(35, bim.getTechRemarks());
		id = ps.executeUpdate();
		con.close();
		return id;
	}

	public static String getDivision(String lid) throws ClassNotFoundException, SQLException {
		String name = "";
		Connection con = DbConnection.getConnection();
		PreparedStatement ps = con
				.prepareStatement("SELECT emp_division FROM emploeemaster WHERE emp_id='" + lid + "'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			name = rs.getString("emp_division");
		}
		con.close();
		return name;
	}

	public void delete(int id) throws ClassNotFoundException, SQLException {
		Connection con = DbConnection.getConnection();
		PreparedStatement ps = con.prepareStatement("DELETE FROM birmaster WHERE id='" + id + "'");
		ps.executeUpdate();
		con.close();
	}

	public void update(Birmaster bim) throws ClassNotFoundException, SQLException {

		UtilFunctions utilfn = new UtilFunctions();
		Connection con = DbConnection.getConnection();
		PreparedStatement ps = null;
		// ps=con.prepareStatement("UPDATE birmaster SET
		// fqc_in_date='"+bim.getFqcInDate()+"',configuration='"+bim.getConfiguration()+"',received_qty='"+bim.getReceivedQty()+"',unit_serial_no='"+bim.getUnitSerialNo()+"',invoice_no='"+bim.getInvoiceNo()+"',invoice_date='"+bim.getInvoiceDate()+"',\n"
		// +
		// "softwr_changes='"+bim.getSoftwrChanges()+"',hardwr_changes='"+bim.getHardwrChanges()+"',hardware_details='"+bim.getHardwareDetails()+"',accesory_changes='"+bim.getAccesoryChanges()+"',accesory_details='"+bim.getAccesoryDetails()+"',user_manual_update='"+bim.getUserManualUpdate()+"',\n"
		// +
		// "
		// service_manual_update='"+bim.getServiceManualUpdate()+"',fqc_remarks='"+bim.getFqcRemarks()+"',cnr_ref_no='"+bim.getCnrRefNo()+"',ts_team_verification_date='"+bim.getTsTeamRemark()+"',ps_team_verification_date='"+bim.getPsTeamRemark()+"',final_status='"+bim.getFinalStatus()+"',\n"
		// +
		// "
		// closed_date='"+bim.getClosedDate()+"',acces_chng_remark='"+bim.getAccesChngRemark()+"',hrdwr_chang_remark='"+bim.getHrdwrChangRemark()+"',sftwr_chng_remark='"+bim.getSftwrChngRemark()+"',cnr_relese_date='"+bim.getCnrReleseDate()+"',bir_ref_no='"+bim.getBirRefNo()+"'"
		// +
		// ",supplier='"+bim.getSupplier()+"',sc_engg='"+bim.getScEngg()+"',ps_engg='"+bim.getPsEngg()+"',approved_date='"+bim.getApprovedDate()+"',cnr_tec_ref_num='"+bim.getCnrTecRefNum()+"',unit_in_date='"+bim.getUnitInDate()+"',tech_remarks='"+bim.getTechRemarks()+"'
		// WHERE id='"+bim.getId()+"'");
		ps = con.prepareStatement("UPDATE birmaster SET fqc_in_date=?," + "configuration=?,"
				+ "received_qty=?,"
				+ "unit_serial_no=?,invoice_no=?" + ",invoice_date=?,softwr_changes=?,"
				+ "hardwr_changes=?,hardware_details=?,accesory_changes=?,accesory_details=?,"
				+ "user_manual_update=?,service_manual_update=?,fqc_remarks=?,cnr_ref_no=?"
				+ ",ts_team_ver_date=?,ps_team_ver_date=?,final_status=?"
				+ ",closed_date=?,acces_chng_remark=?,hrdwr_chang_remark=?,"
				+ "sftwr_chng_remark=?,cnr_relese_date=?,bir_ref_no=?"
				+ ",supplier=?,sc_engg=?,ps_engg=?,approved_date=?," + "cnr_tec_ref_num=?,"
				+ "unit_in_date=?,tech_remarks=? WHERE id=?");

		// ps.setString(1,bim.getDivision() );
		// ps.setDate(2,utilfn.getDbDateFormat(bim.getEntryDate()));
		// ps.setString(3,bim.getFqcInDate());
		ps.setDate(1, utilfn.getDbDateFormat(bim.getFqcInDate()));
           System.out.println("fqc inward date final ----> "+utilfn.getDbDateFormat(bim.getFqcInDate()));
		// ps.setString(4,bim.getModel() );
		ps.setString(2, bim.getConfiguration());
		ps.setString(3, bim.getReceivedQty());
		ps.setString(4, bim.getUnitSerialNo());
		ps.setString(5, bim.getInvoiceNo());
		// ps.setString(9, bim.getInvoiceDate());
		ps.setDate(6, utilfn.getDbDateFormat(bim.getInvoiceDate()));
		ps.setString(7, bim.getSoftwrChanges());
		// ps.setString(11,bim.getSoftwrVersion() );
		ps.setString(8, bim.getHardwrChanges());
		ps.setString(9, bim.getHardwareDetails());
		ps.setString(10, bim.getAccesoryChanges());
		ps.setString(11, bim.getAccesoryDetails());
		ps.setString(12, bim.getUserManualUpdate());
		ps.setString(13, bim.getServiceManualUpdate());
		ps.setString(14, bim.getFqcRemarks());
		ps.setString(15, bim.getCnrRefNo());
		// ps.setString(20,bim.getTsTeamRemark());
		ps.setDate(16, utilfn.getDbDateFormat(bim.getTsTeamRemark()));
		// ps.setString(21,bim.getPsTeamRemark());
		ps.setDate(17, utilfn.getDbDateFormat(bim.getPsTeamRemark()));
		ps.setString(18, bim.getFinalStatus());
		// ps.setString(23, bim.getClosedDate());
		ps.setDate(19, utilfn.getDbDateFormat(bim.getClosedDate()));
		ps.setString(20, bim.getAccesChngRemark());
		ps.setString(21, bim.getHrdwrChangRemark());
		ps.setString(22, bim.getSftwrChngRemark());
		// ps.setString(27, bim.getCnrReleseDate());
		ps.setDate(23, utilfn.getDbDateFormat(bim.getCnrReleseDate()));
		ps.setString(24, bim.getBirRefNo());
		ps.setString(25, bim.getSupplier());
		ps.setString(26, bim.getScEngg());
		ps.setString(27, bim.getPsEngg());
		ps.setDate(28, utilfn.getDbDateFormat(bim.getApprovedDate()));
		ps.setString(29, bim.getCnrTecRefNum());
		// ps.setString(32, bim.getApprovedDate());
		
		
		// ps.setString(34, bim.getUnitInDate());
		ps.setDate(30, utilfn.getDbDateFormat(bim.getUnitInDate()));
		ps.setString(31, bim.getTechRemarks());
		ps.setInt(32, bim.getId());

		ps.executeUpdate();
		con.close();
	}
}
