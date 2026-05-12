/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.schillerindiaservices.email;
import com.schillerindiaservices.connection.DbConnection;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
/**
 *
 * @author MR
 */


public class SendMailwithAttachment {
        static String sendMail;
        static String password;
        static String port;
        static String ssl_no;
        static String host;
     public static void sendEmailAttachment( String from,  String to,  String sub,  String msgBody,  String bcc,  String cc, String path) throws MessagingException
     {
    	  //System.out.println("sending mail with attachmentssssssssss");
          sendMail="";
          password="";
          port="";
          ssl_no="";
          host="";
         
         try {
             Connection con=DbConnection.getConnection();
             PreparedStatement ps=con.prepareStatement("select * from email where id='1'");
             ResultSet rs=ps.executeQuery();
             while(rs.next()){
            	 //System.out.println("inside whileeeee in mail ");
             sendMail=rs.getString("semail");
             password=rs.getString("password_f");
             //System.out.println(password+"the passworddd isssssss");
             port=rs.getString("port_no");
             ssl_no=rs.getString("ssl_no");
             host=rs.getString("hosing_id");
             }
         } catch (ClassNotFoundException | SQLException ex) {
             ex.printStackTrace();
         } 
         final String Mail=sendMail;
         //System.out.println(Mail+"the mail issssss");
         final String pass=password;
         //System.out.println(pass+"thee passs  issssss");
         final String portID=port;
         final String HostNo=host;
    Properties props = new Properties();
	      props.put("mail.smtp.auth", "true");
	      props.put("mail.smtp.starttls.enable", "true");
	      props.put("mail.smtp.host", HostNo);
	      props.put("mail.smtp.port", portID);
	      props.put("mail.debug", "true");
	      Session session = Session.getInstance(props, new Authenticator() {
	  			protected PasswordAuthentication getPasswordAuthentication() {
	  				return new PasswordAuthentication(Mail, pass);
	  			}
	  		  });
	    
	       try {
		        MimeMessage msg = new MimeMessage(session);
		        msg.setFrom(new InternetAddress(Mail));
		        msg.setRecipients(Message.RecipientType.TO, to);
                        msg.setRecipients(Message.RecipientType.CC, cc);
                        msg.setRecipients(Message.RecipientType.BCC, bcc);
		        msg.setSubject(sub);
		        msg.setSentDate(new Date());
		        
		        Multipart multipart = new MimeMultipart();
		        
		        MimeBodyPart textPart = new MimeBodyPart();
		       // msgBody="hi";
		       //System.out.println("the msgbodyy isss"+msgBody);
		        textPart.setContent(msgBody,"text/html");
		        multipart.addBodyPart(textPart);
		        //System.out.println("the path issss"+path);
		        File f=new File(path);
		        MimeBodyPart attachementPart = new MimeBodyPart();
		        attachementPart.attachFile(f);
		        multipart.addBodyPart(attachementPart);
                   //System.out.println("multipart isss"+multipart);
		        msg.setContent(multipart);
		        //System.out.println(msg.getContent()+" content iss");
		        Transport.send(msg);
                        f.delete();
	       }    catch (Exception ex) {
	    	    ex.printStackTrace();
	       }
     }       

    public static void sendEmail(String string, String to, String sub, String msgBody, String bcc, String cc) 
    {
        
//        calling the values
         final String Mail=sendMail;
         final String pass=password;
         final String portID=port;
         final String HostNo=host;
        //To change body of generated methods, choose Tools | Templates.
        
          Properties props = new Properties();
	      props.put("mail.smtp.auth", "true");
	      props.put("mail.smtp.starttls.enable", "true");
	      props.put("mail.smtp.host", HostNo);
	      props.put("mail.smtp.port", portID);
	      props.put("mail.debug", "true");
	      Session session = Session.getInstance(props, new Authenticator() {
	  			protected PasswordAuthentication getPasswordAuthentication() {
	  				return new PasswordAuthentication(Mail,pass);
	  			}
	  		  });
	    
	       try {
		        MimeMessage msg = new MimeMessage(session);
		        msg.setFrom(new InternetAddress(Mail));
                        
		        msg.setRecipients(Message.RecipientType.TO, to);
                        msg.setRecipients(Message.RecipientType.CC, cc);
                        msg.setRecipients(Message.RecipientType.BCC, bcc);
		        msg.setSubject(sub);
		        msg.setSentDate(new Date());
		        
		        Multipart multipart = new MimeMultipart();
		        
		        MimeBodyPart textPart = new MimeBodyPart();
		       
		        textPart.setContent(msgBody,"text/html");
		        multipart.addBodyPart(textPart);
		       
		        msg.setContent(multipart);
		        Transport.send(msg);
	       }    catch (Exception ex) {
	    	    ex.printStackTrace();
	       }
    }

    
  }
    


