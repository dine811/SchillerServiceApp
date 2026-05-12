package com.schillerindiaservices.email;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.mail.MessagingException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.schillerindiaservices.Dao.AutomailServicecenter_list;
import com.schillerindiaservices.Dao.AutomaticmailScarplistDao;
import com.schillerindiaservices.Dao.ExportNonSaleDao2_Auotesclation;
import com.schillerindiaservices.Dao.PendingFRNEscalutionDao2_Automail;

public class Mailjob7 implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		
		//coding for pending-frn auto esclation
		
		Date dt=new Date();
		
		//System.out.println("yesss itss workingggg"+dt);
		
		LocalDate today=LocalDate.now();
		//System.out.println("today date issss");
		LocalDate yeserday=today;
		LocalDate yeserday2=today.minusDays(7);
		//LocalDate yeserday3=yeserday2.withDayOfMonth(15);
		//LocalDate yeserday4=yeserday2.withDayOfMonth(yeserday3.lengthOfMonth());
		//System.out.println("yesterday date isssss" +yeserday);
		
		////System.out.println("the 15nth of previous month"+yeserday3);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedString = today.format(formatter);
		String formattedString2 = yeserday2.format(formatter);
		//System.out.println(formattedString+"formatted stringg11");
		////System.out.println(formattedString2+"formatted stringg22");
		
		String fdate1=formattedString2.concat(" 10:30:00");
		//System.out.println(fdate1+"fdate 1 issss");
		String ldate1=formattedString.concat(" 10:29:59");
		//System.out.println(ldate1+"ldate 2 issss");
		
		String report_type="pendingfrn";
		
		
		
		
	 try {
		   PendingFRNEscalutionDao2_Automail.pendingFRNEscalationExcel(fdate1, ldate1, report_type);
		// PendingFRNEscalutionDao2_Automail.pendingFRNEscalationExcel_Local(fdate1, ldate1, report_type);
	} catch (MessagingException e) {
		// TODO Auto-generated catch block
		//System.out.println(e);
		e.printStackTrace();
	}
	
	
		
		
		
		
		
		
		/* try {
			//Autoescalation.OBPendingAutoEscalationExcel();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			//System.out.println(e);
			e.printStackTrace();
		}*/
		
		
	}

}
