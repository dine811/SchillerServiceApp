//package com.schillerindiaservices.controller;
//
//import java.io.IOException;
//import java.time.Duration;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.time.ZonedDateTime;
//import java.util.Timer;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//import javax.servlet.ServletContext;
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebListener;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.quartz.CronScheduleBuilder;
//import org.quartz.JobBuilder;
//import org.quartz.JobDetail;
//import org.quartz.Scheduler;
//import org.quartz.Trigger;
//import org.quartz.TriggerBuilder;
//import org.quartz.ee.servlet.QuartzInitializerListener;
//import org.quartz.impl.StdSchedulerFactory;
//
//import com.schillerindiaservices.email.Mailjob;
//import com.schillerindiaservices.email.Mailjob10;
//import com.schillerindiaservices.email.Mailjob11;
//import com.schillerindiaservices.email.Mailjob12;
//import com.schillerindiaservices.email.Mailjob13;
//import com.schillerindiaservices.email.Mailjob2;
//import com.schillerindiaservices.email.Mailjob3;
//import com.schillerindiaservices.email.Mailjob4;
//import com.schillerindiaservices.email.Mailjob5;
//import com.schillerindiaservices.email.Mailjob6_nonsale;
//import com.schillerindiaservices.email.Mailjob7;
//import com.schillerindiaservices.email.Mailjob8;
//import com.schillerindiaservices.email.Mailjob9;
//import com.schillerindiaservices.email.Mailjob_stocklist;
//
///**
// * Servlet implementation class AutomaticMail
// */
//
//@WebListener
//public class AutomaticMail extends QuartzInitializerListener  {
//	
//	
//		
//		
//		 @Override
//		    public void contextInitialized(ServletContextEvent sce) {
//		        super.contextInitialized( sce);
//		        ServletContext ctx = sce.getServletContext();
//		        StdSchedulerFactory factory = (StdSchedulerFactory) ctx.getAttribute(QUARTZ_FACTORY_KEY);
//		        try {
//		            Scheduler scheduler = factory.getScheduler();
//		            JobDetail jobDetail = JobBuilder.newJob(Mailjob.class).build();
//		            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("simple").withSchedule(
//		               CronScheduleBuilder.cronSchedule("0 10 21 4 1/1 ? *")).startNow().build();
//		         //  CronScheduleBuilder.cronSchedule("0 00 16 1/1 * ? *")).startNow().build();
//		            scheduler.scheduleJob(jobDetail, trigger);
//		            scheduler.start();
//		        } catch (Exception e) {
//		            ctx.log("There was an error scheduling the job.", e);
//		        }
//		        
//		        try {
//		            Scheduler scheduler1 = factory.getScheduler();
//		            JobDetail jobDetail1 = JobBuilder.newJob(Mailjob2.class).build();
//		            Trigger trigger2 = TriggerBuilder.newTrigger().withIdentity("simple1").withSchedule(
//		                CronScheduleBuilder.cronSchedule("0 35 11 1/1 * ? *")).startNow().build();
//		            	//	CronScheduleBuilder.cronSchedule("0 02 16 1/1 * ? *")).startNow().build();
//		            scheduler1.scheduleJob(jobDetail1, trigger2);
//		            scheduler1.start();
//		        } catch (Exception e) {
//		            ctx.log("There was an error scheduling the job.", e);
//		        }
//		        
//		        try {
//		            Scheduler scheduler2 = factory.getScheduler();
//		            JobDetail jobDetail2 = JobBuilder.newJob(Mailjob3.class).build();
//		            Trigger trigger3 = TriggerBuilder.newTrigger().withIdentity("simple2").withSchedule(
//		                    CronScheduleBuilder.cronSchedule("0 16 16 1/1 * ? *")).startNow().build();
//		            	//	CronScheduleBuilder.cronSchedule("0 05 16 1/1 * ? *")).startNow().build();
//		            scheduler2.scheduleJob(jobDetail2, trigger3);
//		            scheduler2.start();
//		        } catch (Exception e) {
//		            ctx.log("There was an error scheduling the job.", e);
//		        }
//		        
//		        try {
//		            Scheduler scheduler3 = factory.getScheduler();
//		            JobDetail jobDetail3 = JobBuilder.newJob(Mailjob4.class).build();
//		            Trigger trigger4 = TriggerBuilder.newTrigger().withIdentity("simple3").withSchedule(
//		                   CronScheduleBuilder.cronSchedule("0 10 21 16 1/1 ? *")).startNow().build();
//		            		// CronScheduleBuilder.cronSchedule("0 05 20 1/1 * ? *")).startNow().build();
//		            scheduler3.scheduleJob(jobDetail3, trigger4);
//		            scheduler3.start();
//		        } catch (Exception e) {
//		            ctx.log("There was an error scheduling the job.", e);
//		        }
//		        
//		   
//		        
//		        try {
//		            Scheduler scheduler4 = factory.getScheduler();
//		            JobDetail jobDetail4 = JobBuilder.newJob(Mailjob_stocklist.class).build();
//		            Trigger trigger5 = TriggerBuilder.newTrigger().withIdentity("simple4").withSchedule(
//		                    CronScheduleBuilder.cronSchedule("0 10 20 1/1 * ? *")).startNow().build();
//		            scheduler4.scheduleJob(jobDetail4, trigger5);
//		            scheduler4.start();
//		        } catch (Exception e) {
//		            ctx.log("There was an error scheduling the job.", e);
//		        }
//		 
//		 
//		        try {
//		            Scheduler scheduler5 = factory.getScheduler();
//		            JobDetail jobDetail5 = JobBuilder.newJob(Mailjob6_nonsale.class).build();
//		            Trigger trigger6 = TriggerBuilder.newTrigger().withIdentity("simple5").withSchedule(
//		                   CronScheduleBuilder.cronSchedule("0 10 19 ? * MON *")).startNow().build();
//		            		//CronScheduleBuilder.cronSchedule("0 50 18 1/1 * ? *")).startNow().build();
//		            scheduler5.scheduleJob(jobDetail5, trigger6);
//		            scheduler5.start();
//		        } catch (Exception e) {
//		            ctx.log("There was an error scheduling the job.", e);
//		        }
//		 
//		        try {
//		            Scheduler scheduler6 = factory.getScheduler();
//		            JobDetail jobDetail6 = JobBuilder.newJob(Mailjob7.class).build();
//		            Trigger trigger7 = TriggerBuilder.newTrigger().withIdentity("simple6").withSchedule(
//		                    CronScheduleBuilder.cronSchedule("0 35 10 ? * MON *")).startNow().build();
//		            scheduler6.scheduleJob(jobDetail6, trigger7);
//		            scheduler6.start();
//		        } catch (Exception e) {
//		            ctx.log("There was an error scheduling the job.", e);
//		        }
//		      
//		        try {
//		            Scheduler scheduler7 = factory.getScheduler();
//		            JobDetail jobDetail7 = JobBuilder.newJob(Mailjob8.class).build();
//		            Trigger trigger8 = TriggerBuilder.newTrigger().withIdentity("simple7").withSchedule(
//		                    CronScheduleBuilder.cronSchedule("0 10 10 ? * MON *")).startNow().build();
//		            scheduler7.scheduleJob(jobDetail7, trigger8);
//		            scheduler7.start();
//		        } catch (Exception e) {
//		            ctx.log("There was an error scheduling the job.", e);
//		        }
//		        
//		        try {
//		            Scheduler scheduler8 = factory.getScheduler();
//		            JobDetail jobDetail8 = JobBuilder.newJob(Mailjob9.class).build();
//		            Trigger trigger9 = TriggerBuilder.newTrigger().withIdentity("simple8").withSchedule(
//		                    CronScheduleBuilder.cronSchedule("0 10 11 ? * MON *")).startNow().build();
//		            scheduler8.scheduleJob(jobDetail8, trigger9);
//		            scheduler8.start();
//		        } catch (Exception e) {
//		            ctx.log("There was an error scheduling the job.", e);
//		        }
//		        
//		        try {
//		            Scheduler scheduler9 = factory.getScheduler();
//		            JobDetail jobDetail9 = JobBuilder.newJob(Mailjob10.class).build();
//		            Trigger trigger10 = TriggerBuilder.newTrigger().withIdentity("simple9").withSchedule(
//		                   CronScheduleBuilder.cronSchedule("0 10 9 ? * SAT *")).startNow().build();
//		            	//	CronScheduleBuilder.cronSchedule("0 45 18,19 1/1 * ? *")).startNow().build();
//		            scheduler9.scheduleJob(jobDetail9, trigger10);
//		            scheduler9.start();
//		        } catch (Exception e) {
//		            ctx.log("There was an error scheduling the job.", e);
//		        }
//		        
//		        try {
//		            Scheduler scheduler10 = factory.getScheduler();
//		            JobDetail jobDetail10 = JobBuilder.newJob(Mailjob11.class).build();
//		            Trigger trigger11 = TriggerBuilder.newTrigger().withIdentity("simple10").withSchedule(
//		                    CronScheduleBuilder.cronSchedule("0 50 9 1/1 * ? *")).startNow().build();
//		            scheduler10.scheduleJob(jobDetail10, trigger11);
//		            scheduler10.start();
//		        } catch (Exception e) {
//		            ctx.log("There was an error scheduling the job.", e);
//		        }
//		        
//		        
//		        
//		        try {
//		            Scheduler scheduler11 = factory.getScheduler();
//		            JobDetail jobDetail11 = JobBuilder.newJob(Mailjob12.class).build();
//		            Trigger trigger12 = TriggerBuilder.newTrigger().withIdentity("simple11").withSchedule(
//		                   CronScheduleBuilder.cronSchedule("0 40 18 1/1 * ? *")).startNow().build();
//		            		// CronScheduleBuilder.cronSchedule("0 10 18,19 1/1 * ? *")).startNow().build();
//
//		            scheduler11.scheduleJob(jobDetail11, trigger12);
//		            scheduler11.start();
//		        } catch (Exception e) {
//		            ctx.log("There was an error scheduling the job.", e);
//		        }
//		        
//		        try {
//		            Scheduler scheduler12 = factory.getScheduler();
//		            JobDetail jobDetail12 = JobBuilder.newJob(Mailjob13.class).build();
//		            Trigger trigger13 = TriggerBuilder.newTrigger().withIdentity("simple12").withSchedule(
//		                    CronScheduleBuilder.cronSchedule("0 10 21 ? * FRI *")).startNow().build();
//		            	//	CronScheduleBuilder.cronSchedule("0 0/3 * * * ?")).startNow().build();
//		            scheduler12.scheduleJob(jobDetail12, trigger13);
//		            scheduler12.start();
//		        } catch (Exception e) {
//		            ctx.log("There was an error scheduling the job.", e);
//		        }
//		 
//		 }
//		
//		
//		
//		
//		
//		
//		
//	}
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//
