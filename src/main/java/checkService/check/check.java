package checkService.check;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

import checkService.util.propertiesFactory;

public class check {
	
	public static void main(String[] args) {

	try {
	    int runInterval = Integer.parseInt(propertiesFactory.getInstance().getConfig("runInterval"));
		
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler scheduler = sf.getScheduler();
		JobDetail jobDetail = JobBuilder.newJob(checkJob.class).withIdentity("job1", "jGroup1").build();
	//	jobDetail.getJobDataMap().put("type","1");
		SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger()
				.withIdentity(new TriggerKey("trigger1", "tgroup1")).startNow()
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(runInterval).repeatForever())
				.build();
//		 CronTrigger simpleTrigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
//		 .withSchedule(CronScheduleBuilder.cronSchedule(agCronStr)).build();
		scheduler.scheduleJob(jobDetail, simpleTrigger);
		scheduler.start();
	} catch (Exception e) {
		e.printStackTrace();
	}
 
	}

}
