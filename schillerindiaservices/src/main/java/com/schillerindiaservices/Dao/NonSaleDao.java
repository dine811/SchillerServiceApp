/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schillerindiaservices.Dao;

import com.schillerindiaservices.bean.Nonsaleablemaster;
import com.schillerindiaservices.common.UtilFunctions;
import com.schillerindiaservices.connection.DbConnection;
import com.schillerindiaservices.controller.NonSaleControler;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ShineLoGics
 */
public class NonSaleDao {
	
    public static Nonsaleablemaster getById(int id) throws ClassNotFoundException, SQLException
    {
        Nonsaleablemaster nsm=null;
        UtilFunctions utilfn = new UtilFunctions();
        Connection con=DbConnection.getConnection();
        PreparedStatement ps=con.prepareStatement("SELECT * FROM nonsaleablemaster WHERE id='"+id+"'");
        ResultSet rs=ps.executeQuery();
        while(rs.next())
        {
            nsm=new Nonsaleablemaster();
            nsm.setId(id);
            nsm.setDivision(rs.getString("division"));
            nsm.setUnitDetails(rs.getString("unit_details"));
            nsm.setFqcInDate(utilfn.getUIDateFormat(rs.getString("fqc_in_date")));
            nsm.setRegion(rs.getString("region"));
            nsm.setBranch(rs.getString("branch"));
            nsm.setEngineer(rs.getString("engineer"));
            nsm.setDealer(rs.getString("dealer"));
            nsm.setSupplier(rs.getString("supplier"));
            nsm.setModel(rs.getString("model"));
            nsm.setModelSN(rs.getString("model_s_n"));
            nsm.setUnitConfig(rs.getString("unit_config"));
            nsm.setCustName(rs.getString("cust_name"));
            nsm.setReportedProblm(rs.getString("reported_problm"));
            nsm.setReplacedUnitSN(rs.getString("replaced_unit_s_n"));
            nsm.setReplaceShipDate(utilfn.getUIDateFormat(rs.getString("replace_ship_date")));
            nsm.setDefectUnitRecivedDate(utilfn.getUIDateFormat(rs.getString("defect_unit_recived_date")));
            nsm.setFqcObservation(rs.getString("fqc_observation"));
            nsm.setScInwardDate(utilfn.getUIDateFormat(rs.getString("sc_inward_date")));
            nsm.setScEngg(rs.getString("sc_engg"));
            nsm.setScObservation(rs.getString("sc_observation"));
            nsm.setRequiredParts(rs.getString("required_parts"));
            nsm.setRootCause(rs.getString("root_cause"));
            nsm.setActionPlan(rs.getString("action_plan"));
            nsm.setTentDateShipDate(utilfn.getUIDateFormat(rs.getString("tent_date_ship_date")));
            nsm.setFinalStatus(rs.getString("final_status"));
            nsm.setShipDateFqc(utilfn.getUIDateFormat(rs.getString("ship_date_fqc")));
            nsm.setFqcFinalRemark(rs.getString("fqc_final_remark"));
        }
        con.close();
        return nsm;
    }
    
    public int Insert(Nonsaleablemaster nsm) throws ClassNotFoundException, SQLException
    {
        int id=0;
        Connection con=DbConnection.getConnection();
        PreparedStatement ps=con.prepareStatement("INSERT INTO nonsaleablemaster(division,entry_date,"
                + "unit_details,fqc_in_date,region,branch,engineer,dealer,supplier,model,model_s_n,unit_config,cust_name,\n" +
"reported_problm,replaced_unit_s_n,replace_ship_date,defect_unit_recived_date\n" +
",fqc_observation,sc_inward_date,sc_engg,sc_observation,required_parts,root_cause,\n" +
"action_plan,tent_date_ship_date,final_status,ship_date_fqc,fqc_final_remark) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, nsm.getDivision());
        ps.setDate(2, Date.valueOf(nsm.getEntryDate()));
        ps.setString(3, nsm.getUnitDetails());
        ps.setDate(4, Date.valueOf(nsm.getFqcInDate()));
        ps.setString(5, nsm.getRegion());
        ps.setString(6, nsm.getBranch());
        ps.setString(7, nsm.getEngineer());
        ps.setString(8, nsm.getDealer());
        ps.setString(9, nsm.getSupplier());
        ps.setString(10, nsm.getModel());
        ps.setString(11, nsm.getModelSN());
        ps.setString(12, nsm.getUnitConfig());
        ps.setString(13, nsm.getCustName());
        ps.setString(14, nsm.getReportedProblm());
        ps.setString(15, nsm.getReplacedUnitSN());
        ps.setDate(16, this.getSqlDateFormat(nsm.getReplaceShipDate()));
        ps.setDate(17, this.getSqlDateFormat(nsm.getDefectUnitRecivedDate()));
        ps.setString(18, nsm.getFqcObservation());
       // ps.setDate(19, Date.valueOf(nsm.getScInwardDate()));
        ps.setDate(19, this.getSqlDateFormat(nsm.getScInwardDate()));
        ps.setString(20, nsm.getScEngg());
        ps.setString(21, nsm.getScObservation());
        ps.setString(22, nsm.getRequiredParts());
        ps.setString(23, nsm.getRootCause());
        ps.setString(24, nsm.getActionPlan());
     //   ps.setDate(25, Date.valueOf(nsm.getTentDateShipDate()));
        ps.setDate(25,this.getSqlDateFormat(nsm.getTentDateShipDate()));
        ps.setString(26, nsm.getFinalStatus());
     //   ps.setDate(27, Date.valueOf(nsm.getShipDateFqc()));
        ps.setDate(27, this.getSqlDateFormat(nsm.getShipDateFqc()));
        ps.setString(28,nsm.getFqcFinalRemark());
        id=ps.executeUpdate();
        
        con.close();
        return id;
    }
    
    public static String getDivName(int d) throws ClassNotFoundException, SQLException
    {
        String div="";
        Connection con=null;
        con=DbConnection.getConnection();
        PreparedStatement ps=con.prepareStatement("SELECT division FROM nonsaleablemaster WHERE sc_engg='"+d+"'");
        
        ResultSet rs=ps.executeQuery();
        while(rs.next())
        {
            div=rs.getString("division");
        }
        con.close();
        return div;
    }

    public void update(Nonsaleablemaster nsm) throws ClassNotFoundException, SQLException {
            UtilFunctions utilfn = new UtilFunctions();
    	Connection con=DbConnection.getConnection();
                     
        PreparedStatement ps=con.prepareStatement("UPDATE nonsaleablemaster SET sc_engg=?,"
         + "sc_inward_date=?,sc_observation=?,"
         + "required_parts=?,root_cause=?"
         + ",action_plan=?,tent_date_ship_date=?,"
         + "final_status=?,unit_details=?,fqc_in_date=?"
         + ",model_s_n=?,unit_config=?,cust_name=?"
         + ",reported_problm=?,replaced_unit_s_n=?,replace_ship_date=?"
        + ",defect_unit_recived_date=?,fqc_observation=?,ship_date_fqc=?,"
                + "fqc_final_remark=? WHERE id=?");
        
        ps.setString(1, nsm.getScEngg());
        ps.setDate(2, utilfn.getDbDateFormat(nsm.getScInwardDate()));
        ps.setString(3, nsm.getScObservation());
        ps.setString(4, nsm.getRequiredParts());
        ps.setString(5, nsm.getRootCause());
        ps.setString(6, nsm.getActionPlan());
        ps.setDate(7, utilfn.getDbDateFormat(nsm.getTentDateShipDate()));
        ps.setString(8, nsm.getFinalStatus());
        ps.setString(9, nsm.getUnitDetails());
        ps.setDate(10, utilfn.getDbDateFormat(nsm.getFqcInDate()));
        ps.setString(11, nsm.getModelSN());
        ps.setString(12, nsm.getUnitConfig());
        ps.setString(13, nsm.getCustName());
        ps.setString(14, nsm.getReportedProblm());
        ps.setString(15, nsm.getReplacedUnitSN());
        ps.setDate(16, utilfn.getDbDateFormat(nsm.getReplaceShipDate()));
        ps.setDate(17, utilfn.getDbDateFormat(nsm.getDefectUnitRecivedDate()));
        ps.setString(18, nsm.getFqcObservation());
        ps.setDate(19, utilfn.getDbDateFormat(nsm.getShipDateFqc()));
        ps.setString(20, nsm.getFqcFinalRemark());
        ps.setInt(21, nsm.getId());
        
        ps.executeUpdate();
        con.close();
    }
    public static String getDivision(String lid) throws ClassNotFoundException, SQLException
    {
        String name="";
        Connection con=DbConnection.getConnection();
        PreparedStatement ps=con.prepareStatement("SELECT division FROM nonsaleablemaster WHERE sc_engg='"+lid+"'");
        ResultSet rs=ps.executeQuery();
        while(rs.next())
        {
            name=rs.getString("division");
        }
        con.close();
        return name;
    }
    public static String getDivision_name(int lid) throws ClassNotFoundException, SQLException
    {
    	System.out.println("inside get divisionnn");
        String name="";
        Connection con=DbConnection.getConnection();
        PreparedStatement ps=con.prepareStatement("SELECT emp_division FROM emploeemaster WHERE emp_id='"+lid+"'");
        ResultSet rs=ps.executeQuery();
        while(rs.next())
        {
            name=rs.getString("emp_division");
        }
        con.close();
        System.out.println("the sysout name isss"+name);
        return name;
    }
    public void delete(int id) throws ClassNotFoundException, SQLException
    {
        Connection con=DbConnection.getConnection();
        PreparedStatement ps=con.prepareStatement("DELETE FROM nonsaleablemaster WHERE id="+id+"");
        ps.executeUpdate();
        con.close();
    }
    
    public Date getSqlDateFormat(String date) {
    	
    	System.out.println("input dateee --- >"+date+"   ");
    	
    	
    	if(null != date && !date.equals("null")) {
    		System.out.println(" converted date__>"+Date.valueOf(date));
    		
    	}else {
    		date = null;
    		System.out.println("date is null");
    	}
    	
    	try {
    		return date != null  ? Date.valueOf(date):null;
		} catch (IllegalArgumentException e) {
			  Logger.getLogger(NonSaleControler.class.getName()).log(Level.SEVERE, null, e);
			return null;
			// TODO: handle exception
		}
	
    	
    }
}
