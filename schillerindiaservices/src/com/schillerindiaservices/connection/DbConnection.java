/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.schillerindiaservices.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author MR
 */
public class DbConnection {
     public static Connection getConnection() throws ClassNotFoundException, SQLException
    {
    	 System.out.println("insidee db connectionnn");
        Connection con=null;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
//            con=DriverManager.getConnection("jdbc:mysql://35.160.131.242:3306/schillerindiaservices","DBUser","user123");//live
//             con=DriverManager.getConnection("jdbc:mysql://localhost:3306/schillerindiaservices","root","root"); //for client mechine
          //    con=DriverManager.getConnection("jdbc:mysql://localhost:3306/schillerindiaservices","root","root");//local
      con=DriverManager.getConnection("jdbc:mysql://localhost:3306/schillerindiaservices_2019","root","root");//local
           //   con=DriverManager.getConnection("jdbc:mysql://localhost:3306/schillerindiaservices","shine","Shine@123");//local
            
             System.out.println("database connected");  
        }
        catch(ClassNotFoundException | SQLException e)
        {
        	
        	
           e.printStackTrace();
           System.out.println(e);
        } 
        
        return con;
    }
}
