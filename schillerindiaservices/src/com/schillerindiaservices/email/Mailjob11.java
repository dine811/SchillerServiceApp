package com.schillerindiaservices.email;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.mail.MessagingException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.schillerindiaservices.Dao.Autoesclation_OBpending_dao;
import com.schillerindiaservices.Dao.AutomailServicecenter_list;
import com.schillerindiaservices.Dao.AutomaticmailScarplistDao;
import com.schillerindiaservices.Dao.EscalutionUnderRepairDao;
import com.schillerindiaservices.Dao.ExportNonSaleDao2_Auotesclation;
import com.schillerindiaservices.Dao.Export_BIRDAO_Auto_Esclation;
import com.schillerindiaservices.Dao.Export_PRFOB_Auto_Esclation2;
import com.schillerindiaservices.Dao.PendingFRNEscalutionDao2_Automail;

public class Mailjob11 implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		
		//coding for Bir pending auto esclation
		
		Date dt=new Date();
		
		////System.out.println("yesss itss workingggg"+dt);
		
		LocalDate today=LocalDate.now();
	//	//System.out.println("today date issss");
		LocalDate yeserday=today.minusDays(1);
		LocalDate yeserday2=today.minusDays(7);
		//LocalDate yeserday3=yeserday2.withDayOfMonth(15);
		//LocalDate yeserday4=yeserday2.withDayOfMonth(yeserday3.lengthOfMonth());
	//	//System.out.println("yesterday date isssss" +yeserday);
		
		////System.out.println("the 15nth of previous month"+yeserday3);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedString = today.format(formatter);
		String formattedString2 = yeserday.format(formatter);
		//System.out.println(formattedString+"formatted stringg11");
		////System.out.println(formattedString2+"formatted stringg22");
		
		String fdate1=formattedString2.concat(" 18:00:00");
		//System.out.println(fdate1+"fdate 1 issss");
		String ldate1=formattedString.concat(" 17:59:59");
		//System.out.println(ldate1+"ldate 2 issss");
		
		String report_type="prf/ob_pending";
		String status="Pending";
		
		
		
	 try {
		 Export_PRFOB_Auto_Esclation2.PRFOB_AutoEsclation(formattedString2, formattedString, report_type, status);
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
