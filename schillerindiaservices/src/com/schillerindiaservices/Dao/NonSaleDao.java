/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schillerindiaservices.Dao;

import com.schillerindiaservices.bean.Nonsaleablemaster;
import com.schillerindiaservices.connection.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ShineLoGics
 */
public class NonSaleDao {
    public static Nonsaleablemaster getById(int id) throws ClassNotFoundException, SQLException
    {
        Nonsaleablemaster nsm=null;
        Connection con=DbConnection.getConnection();
        PreparedStatement ps=con.prepareStatement("SELECT * FROM nonsaleablemaster WHERE id='"+id+"'");
        ResultSet rs=ps.executeQuery();
        while(rs.next())
        {
            nsm=new Nonsaleablemaster();
            nsm.setId(id);
            nsm.setDivision(rs.getString("division"));
            nsm.setUnitDetails(rs.getString("unit_details"));
            nsm.setFqcInDate(rs.getString("fqc_in_date"));
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
            nsm.setReplaceShipDate(rs.getString("replace_ship_date"));
            nsm.setDefectUnitRecivedDate(rs.getString("defect_unit_recived_date"));
            nsm.setFqcObservation(rs.getString("fqc_observation"));
            nsm.setScInwardDate(rs.getString("sc_inward_date"));
            nsm.setScEngg(rs.getString("sc_engg"));
            nsm.setScObservation(rs.getString("sc_observation"));
            nsm.setRequiredParts(rs.getString("required_parts"));
            nsm.setRootCause(rs.getString("root_cause"));
            nsm.setActionPlan(rs.getString("action_plan"));
            nsm.setTentDateShipDate(rs.getString("tent_date_ship_date"));
            nsm.setFinalStatus(rs.getString("final_status"));
            nsm.setShipDateFqc(rs.getString("ship_date_fqc"));
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
        ps.setString(2, nsm.getEntryDate());
        ps.setString(3, nsm.getUnitDetails());
        ps.setString(4, nsm.getFqcInDate());
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
        ps.setString(16, nsm.getReplaceShipDate());
        ps.setString(17, nsm.getDefectUnitRecivedDate());
        ps.setString(18, nsm.getFqcObservation());
        ps.setString(19, nsm.getScInwardDate());
        ps.setString(20, nsm.getScEngg());
        ps.setString(21, nsm.getScObservation());
        ps.setString(22, nsm.getRequiredParts());
        ps.setString(23, nsm.getRootCause());
        ps.setString(24, nsm.getActionPlan());
        ps.setString(25, nsm.getTentDateShipDate());
        ps.setString(26, nsm.getFinalStatus());
        ps.setString(27,nsm.getShipDateFqc());
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
        Connection con=DbConnection.getConnection();
                     
        PreparedStatement ps=con.prepareStatement("UPDATE nonsaleablemaster SET sc_engg='"+nsm.getScEngg()+"',"
         + "sc_inward_date='"+nsm.getScInwardDate()+"',sc_observation='"+nsm.getScObservation()+"',"
         + "required_parts='"+nsm.getRequiredParts()+"',root_cause='"+nsm.getRootCause()+"'"
         + ",action_plan='"+nsm.getActionPlan()+"',tent_date_ship_date='"+nsm.getTentDateShipDate()+"',"
         + "final_status='"+nsm.getFinalStatus()+"',unit_details='"+nsm.getUnitDetails()+"',fqc_in_date='"+nsm.getFqcInDate()+"'"
         + ",model_s_n='"+nsm.getModelSN()+"',unit_config='"+nsm.getUnitConfig()+"',cust_name='"+nsm.getCustName()+"'"
         + ",reported_problm='"+nsm.getReportedProblm()+"',replaced_unit_s_n='"+nsm.getReplacedUnitSN()+"',replace_ship_date='"+nsm.getReplaceShipDate()+"'"
        + ",defect_unit_recived_date='"+nsm.getDefectUnitRecivedDate()+"',fqc_observation='"+nsm.getFqcObservation()+"',ship_date_fqc='"+nsm.getShipDateFqc()+"',"
                + "fqc_final_remark='"+nsm.getFqcFinalRemark()+"' WHERE id='"+nsm.getId()+"'");
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
}
