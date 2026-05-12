package com.schillerindiaservices.Dao;


//import com.mysql.jdbc.Connection;
import com.schillerindiaservices.bean.Email;
import java.sql.Connection.*;

import com.schillerindiaservices.connection.DbConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import  com.schillerindiaservices.connection.DbConnection;
import java.sql.Connection;
import javax.servlet.http.HttpSession;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ShineLoGics
 */
public class EmailDao {
    
    public static List<Email> getEmail() throws SQLException, ClassNotFoundException
            {
                List<Email> mail=new ArrayList<Email>();
                Connection con=null;
               con=DbConnection.getConnection();   
                PreparedStatement ps=con.prepareStatement("SELECT * FROM email");
                ResultSet rs=ps.executeQuery();
                while(rs.next())
                {
                    Email m=new Email();
                    m.setMailid(rs.getString("mailid"));
                    m.setName(rs.getString("name"));
                    m.setSemail(rs.getString("semail"));
                    m.setPasswordF(rs.getString("password_f"));
                    m.setPortNo(rs.getString("port_no"));
                    m.setSslNo(rs.getString("ssl_no"));
                    m.setMsgBody(rs.getString("msg_body"));
                    m.setHosingId("hosing_id");
                }
                con.close();
                return mail;
            }
    public static Email getById(int id) throws ClassNotFoundException, SQLException
    {
       
        Email m=null;
        Connection con=DbConnection.getConnection();
        PreparedStatement ps=con.prepareStatement("SELECT * FROM email WHERE id='"+id+"'");
        ResultSet rs=ps.executeQuery();
        while(rs.next())
        {
            m=new Email();
            m.setId(id);
            m.setMailid(rs.getString("mailid"));
            m.setName(rs.getString("name"));
            m.setDirection(rs.getString("direction"));
            m.setSemail(rs.getString("semail"));
            m.setPasswordF(rs.getString("password_f"));
            m.setPortNo(rs.getString("port_no"));
            m.setSslNo(rs.getString("ssl_no"));
            m.setMsgBody(rs.getString("msg_body"));
            m.setHosingId(rs.getString("hosing_id"));
        }
        con.close();
        return m;
    }
    
    public void update(Email e) throws ClassNotFoundException, SQLException
    {
        
        Connection con=DbConnection.getConnection();
        PreparedStatement ps=null;
        ps=con.prepareStatement("UPDATE email SET mailid='"+e.getMailid()+"',name='"+e.getName()+"',semail='"+e.getSemail()+"',"
                + "password_f='"+e.getPasswordF()+"',port_no='"+e.getPortNo()+"',ssl_no='"+e.getSslNo()+"',msg_body='"+e.getMsgBody()+"',hosing_id='"+e.getHosingId()+"' WHERE id='"+e.getId()+"'");
        
        ps.executeUpdate();
        con.close();
    }
}
