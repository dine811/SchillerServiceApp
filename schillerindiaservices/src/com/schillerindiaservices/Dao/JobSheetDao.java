/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schillerindiaservices.Dao;

import com.schillerindiaservices.bean.Jobsheet;
import com.schillerindiaservices.connection.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SHINELOGICS
 */
public class JobSheetDao {
       public int save(Jobsheet js) throws SQLException, ClassNotFoundException
    {  
        int s=0;  
        Connection con=null;
        con =DbConnection.getConnection();
        PreparedStatement ps=con.prepareStatement("insert into jobsheet (repair_date,enginner_name,observation,repair_activity,time_spent,remark,ser_id,repair_date1,enginner_name1,observation1,repair_activity1,time_spent1,remark1,repair_date2,enginner_name2,observation2,repair_activity2,time_spent2,remark2,repair_date3,enginner_name3,observation3,repair_activity3,time_spent3,remark3,repair_date4,enginner_name4,observation4,repair_activity4,time_spent4,remark4) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1,js.getRepairDate());
        ps.setString(2,js.getEnginnerName());
        ps.setString(3,js.getObservation());
       
       
        ps.setString(4,js.getRepairActivity());
        ps.setString(5,js.getTimeSpent());
        ps.setString(6,js.getRemark());
        ps.setInt(7,js.getSerId());
        ps.setString(8,js.getRepairDate1());
        ps.setString(9,js.getEnginnerName1());
        ps.setString(10,js.getObservation1());
        ps.setString(11,js.getTimeSpent1());
        ps.setString(12,js.getRepairActivity1());
        ps.setString(13,js.getRemark1());
        ps.setString(14,js.getRepairDate2());
        ps.setString(15,js.getEnginnerName2());
        ps.setString(16,js.getObservation2());
        ps.setString(17,js.getTimeSpent2());
        ps.setString(18,js.getRepairActivity2());
        ps.setString(19,js.getRemark3());
        ps.setString(20,js.getRepairDate3());
        ps.setString(21,js.getEnginnerName3());
        ps.setString(22,js.getObservation3());
        ps.setString(23,js.getTimeSpent3());
        ps.setString(24,js.getRepairActivity3());
        ps.setString(25,js.getRemark3());
        ps.setString(26,js.getRepairDate4());
        ps.setString(27,js.getEnginnerName4());
        ps.setString(28,js.getObservation4());
        ps.setString(29,js.getTimeSpent4());
        ps.setString(30,js.getRepairActivity4());
        ps.setString(31,js.getRemark4());
        s=ps.executeUpdate();
        ps.close();
        con.close();
        return s;  
    }  
     
         public static List<Jobsheet> getAllRecords() throws SQLException, ClassNotFoundException
    {  
        List<Jobsheet> list=new ArrayList<>();  
        Connection con=null;
            con=DbConnection.getConnection();   
            PreparedStatement ps=con.prepareStatement("select * from jobsheet");  
            ResultSet rs=ps.executeQuery();  
            while(rs.next()){  
                Jobsheet s=new Jobsheet();  
                s.setId(rs.getInt("id"));
                s.setRepairDate(rs.getString("repair_date"));
                s.setEnginnerName(rs.getString("enginner_name"));
                s.setObservation(rs.getString("observation"));
                s.setTimeSpent(rs.getString("repair_activity"));
                s.setRepairActivity(rs.getString("time_spent"));
                s.setRemark(rs.getString("remark"));
              
                list.add(s);  
            }  
            con.close();
        return list;  
    } 
         
    public static  List<Jobsheet> getById(int id) throws SQLException, ClassNotFoundException
{
    List<Jobsheet> list=new ArrayList<>();  
    Connection con=null;
       con=DbConnection.getConnection();   
            PreparedStatement ps=con.prepareStatement("select * from jobsheet where ser_id="+id);  
            ResultSet rs=ps.executeQuery();  
            while(rs.next())
            { 
            	Jobsheet s=new Jobsheet();  
                s.setId(rs.getInt("id"));
                System.out.println(rs.getInt("id")+"dfssrgsggdfg");
                s.setRepairDate(rs.getString("repair_date"));
                s.setEnginnerName(rs.getString("enginner_name"));
                s.setObservation(rs.getString("observation"));
                s.setTimeSpent(rs.getString("time_spent"));
                s.setRepairActivity(rs.getString("repair_activity"));
                s.setRemark(rs.getString("remark"));
                s.setRepairDate1(rs.getString("repair_date1"));
                s.setEnginnerName1(rs.getString("enginner_name1"));
                s.setObservation1(rs.getString("observation1"));
                s.setTimeSpent1(rs.getString("time_spent1"));
                s.setRepairActivity1(rs.getString("repair_activity1"));
                s.setRemark1(rs.getString("remark1"));
                s.setRepairDate2(rs.getString("repair_date2"));
                s.setEnginnerName2(rs.getString("enginner_name2"));
                s.setObservation2(rs.getString("observation2"));
                s.setTimeSpent2(rs.getString("time_spent2"));
                s.setRepairActivity2(rs.getString("repair_activity2"));
                s.setRemark2(rs.getString("remark2"));
                s.setRepairDate3(rs.getString("repair_date3"));
                s.setEnginnerName3(rs.getString("enginner_name3"));
                s.setObservation3(rs.getString("observation3"));
                s.setTimeSpent3(rs.getString("time_spent3"));
                s.setRepairActivity3(rs.getString("repair_activity3"));
                s.setRemark3(rs.getString("remark3"));
                s.setRepairDate4(rs.getString("repair_date4"));
                s.setEnginnerName4(rs.getString("enginner_name4"));
                s.setObservation4(rs.getString("observation4"));
                s.setTimeSpent4(rs.getString("time_spent4"));
                s.setRepairActivity4(rs.getString("repair_activity4"));
                s.setRemark4(rs.getString("remark4"));
               list.add(s);  
            }  
            con.close();
        return list;
   }  
    
 
public void update(Jobsheet d) throws ClassNotFoundException, SQLException{
	System.out.println("insideee updateee"+d.getId());
         Connection con=DbConnection.getConnection();
         PreparedStatement st=con.prepareStatement("UPDATE jobsheet SET repair_date='"+d.getRepairDate()+"',enginner_name='"+d.getEnginnerName()+"',observation='"+d.getObservation()+"',repair_activity='"+d.getRepairActivity()+"', time_spent ='"+d.getTimeSpent()+"',remark='"+d.getRemark()+"',repair_date1='"+d.getRepairDate1()+"', enginner_name1='"+d.getEnginnerName1()+"',observation1='"+d.getObservation1()+"', repair_activity1 ='"+d.getRepairActivity1()+"', time_spent1='"+d.getTimeSpent1()+"',remark1='"+d.getRemark1()+"',repair_date2='"+d.getRepairDate2()+"', enginner_name2='"+d.getEnginnerName2()+"',observation2='"+d.getObservation2()+"', repair_activity2 ='"+d.getRepairActivity2()+"', time_spent2='"+d.getTimeSpent2()+"',remark2='"+d.getRemark2()+"',repair_date3='"+d.getRepairDate3()+"', enginner_name3='"+d.getEnginnerName3()+"',observation3='"+d.getObservation3()+"', repair_activity3 ='"+d.getRepairActivity3()+"', time_spent3='"+d.getTimeSpent3()+"',remark3='"+d.getRemark3()+"',repair_date4='"+d.getRepairDate4()+"', enginner_name4='"+d.getEnginnerName4()+"',observation4='"+d.getObservation4()+"', repair_activity4 ='"+d.getRepairActivity4()+"', time_spent4='"+d.getTimeSpent4()+"',remark4='"+d.getRemark4()+"' where id="+d.getId());
         st.executeUpdate();
         con.close();
    }  
}
