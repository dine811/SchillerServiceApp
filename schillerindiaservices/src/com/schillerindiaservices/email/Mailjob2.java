package com.schillerindiaservices.email;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.mail.MessagingException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.schillerindiaservices.Dao.AutomatciListEsclation_Dispatch;
import com.schillerindiaservices.Dao.AutomaticmailScarplistDao;

public class Mailjob2 implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		
		// method for sending dispatch list in auto esclation
		// timing for this report is 12:45 everyday 
		/*Date dt=new Date();
		//System.out.println("its worinkingg 2222" +dt);
		LocalDate today=LocalDate.now();
		//System.out.println("today date issss");
		LocalDate yeserday=today.minusDays(1);
		//System.out.println("yesterday date isssss" +yeserday);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedString = today.format(formatter);
		String formattedString2 = yeserday.format(formatter);
		//System.out.println(formattedString+"formatted stringg11");
		//System.out.println(formattedString2+"formatted stringg22");
		
		String fdate1=formattedString2.concat(" 16:45:00");
		//System.out.println(fdate1+"fdate 1 issss");
		String ldate1=formattedString.concat(" 12:29:59");
		//System.out.println(ldate1+"ldate 2 issss");
		
		String report_type="dispatchlist";
		try {
			AutomatciListEsclation_Dispatch.dispatchList_mailExport(fdate1,ldate1,report_type);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		

		// TODO Auto-generated method stub
		// export code for 2nd dispatch list
		Date dt=new Date();
		//System.out.println("its worki8nggg 22222"+ dt);
		
		LocalDate today=LocalDate.now();
		//System.out.println("today date issss");
		LocalDate yeserday=today.minusDays(1);
		//System.out.println("yesterday date isssss" +yeserday);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedString = today.format(formatter);
		String formattedString2 = yeserday.format(formatter);
		//System.out.println(formattedString+"formatted stringg11");
		////System.out.println(formattedString2+"formatted stringg22");
		
		String fdate1=formattedString2.concat(" 16:45:00");
		//System.out.println(fdate1+"fdate 1 issss");
		String ldate1=formattedString.concat(" 12:29:59");
		//System.out.println(ldate1+"ldate 2 issss");
		
		String report_type="dispatchlist";
		try {
			AutomatciListEsclation_Dispatch.dispatchList_mailExport(fdate1,ldate1,report_type);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		
		
		
	}
       
	
	
	
	
	
	
	
}
