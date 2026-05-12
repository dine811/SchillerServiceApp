package com.schillerindiaservices.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.schillerindiaservices.bean.partnumber_Entry;
import com.schillerindiaservices.connection.DbConnection;

public class partentrydao {
        public int savepart_entry(partnumber_Entry pn) throws ClassNotFoundException, SQLException{
        	System.out.println("inside part entry dao");
        	 int id=0;
             Connection con=null;
             con=DbConnection.getConnection();

             PreparedStatement ps=con.prepareStatement("INSERT INTO partnumber_entry(Part_number,brd_Name,compatible_models,"
                     + "cost)\n" +
     "VALUES(?,?,?,?)");
        	
             ps.setString(1, pn.getPartNumber());
             ps.setString(2, pn.getBrdName());
             ps.setString(3, pn.getCompatiblemodels());
             ps.setDouble(4, pn.getCost());
            // ps.setString(4, pam.getInitiateDate());
        	
        	
             id=ps.executeUpdate();
        	
        	
        	
        	
        	
        	
        	
        	return id;
        }
}
