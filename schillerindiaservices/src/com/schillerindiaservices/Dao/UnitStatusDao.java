/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schillerindiaservices.Dao;

import com.schillerindiaservices.connection.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ShineLoGics
 */
public class UnitStatusDao {
    public static String getUnitName(int uid) throws ClassNotFoundException, SQLException
    {
        String name="";
        Connection con=null;
        con=DbConnection.getConnection();
        PreparedStatement ps=con.prepareStatement("SELECT ddvalue FROM dropdownmaster WHERE dd_id="+uid);
        ResultSet rs=ps.executeQuery();
        if(rs.next())
        {
            name=rs.getString("ddvalue");
        }
        con.close();
        return name;
        
    }
    
}
