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

public class Mailjob3 implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		// export code for 2nd dispatch list
		Date dt=new Date();
		//System.out.println("its worki8nggg 3333"+ dt);
		
		LocalDate today=LocalDate.now();
		//System.out.println("today date issss");
		LocalDate yeserday=today.minusDays(1);
		//System.out.println("yesterday date isssss" +yeserday);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedString = today.format(formatter);
		//String formattedString2 = yeserday.format(formatter);
		//System.out.println(formattedString+"formatted stringg11");
		////System.out.println(formattedString2+"formatted stringg22");
		
		String fdate1=formattedString.concat(" 11:30:00");
		//System.out.println(fdate1+"fdate 1 issss");
		String ldate1=formattedString.concat(" 15:59:59");
		//System.out.println(ldate1+"ldate 2 issss");
		
		String report_type="dispatchlist";
		try {
			AutomatciListEsclation_Dispatch.dispatchList_mailExport(fdate1,ldate1,report_type);
			//AutomatciListEsclation_Dispatch.dispatchList_mailExport_Local(fdate1,ldate1,report_type);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
