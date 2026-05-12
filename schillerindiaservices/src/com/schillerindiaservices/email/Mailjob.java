package com.schillerindiaservices.email;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.schillerindiaservices.Dao.Autoescalation;
import com.schillerindiaservices.Dao.AutomaticmailScarplistDao;
import com.schillerindiaservices.Dao.PendingFRNEscalutionDao;

public class Mailjob implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		Date dt=new Date();
		
		//System.out.println("yesss itss workingggg"+dt);
		
		LocalDate today=LocalDate.now();
		//System.out.println("today date issss");
		LocalDate yeserday=today.withDayOfMonth(1);
		LocalDate yeserday2=today.minusMonths(1);
		LocalDate yeserday3=yeserday2.withDayOfMonth(15);
		LocalDate yeserday4=yeserday2.withDayOfMonth(yeserday2.lengthOfMonth());
		//System.out.println("yesterday date isssss" +yeserday);
		
		//System.out.println("the 15nth of previous month"+yeserday3);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedString = yeserday.format(formatter);
		String formattedString2 = yeserday3.format(formatter);
		//System.out.println(formattedString+"formatted stringg11");
		//System.out.println(formattedString2+"formatted stringg22");
		
		String fdate1=formattedString2.concat(" 21:00:00");
		//System.out.println(fdate1+"fdate 1 issss  mailjobbb");
		String ldate1=formattedString.concat(" 20:59:59");
		//System.out.println(ldate1+"ldate 2 issss");
		
		String report_type="scraplist";
		
		
		
		
	 try {
		AutomaticmailScarplistDao.scarpList_mailExport(fdate1, ldate1, report_type);
	} catch (MessagingException e) {
		// TODO Auto-generated catch block
		System.out.println(e);
		e.printStackTrace();
	}
	
	
		
		
		
		
		
		
		/* try {
			//Autoescalation.OBPendingAutoEscalationExcel();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
		}*/
		
		
	}
           
}
