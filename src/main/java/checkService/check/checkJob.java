package checkService.check;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import checkService.sendmail.mailInfo;
import checkService.sendmail.mailUser;
import checkService.sendmail.send;
import checkService.util.config;
import checkService.util.propertiesFactory;

public class checkJob implements Job {

	static Logger logger = Logger.getLogger(checkJob.class);
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
//		
		String shpath = propertiesFactory.getInstance().getConfig("shpath");   
//	
		String pro = processSh(shpath);
		
		
		Map<String,List<String>> service = service(pro);
		
		List<String> runList = service.get("runList");
		
		logger.info(runList);
		
		System.out.println(runList);
	
		
		List<String> noRunList = service.get("noRunList");
		
		System.out.println(noRunList);
		
		String ip="localhost";
		try {
			ip = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
    	
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	
    	System.out.println(noRunList.size());
    	System.out.println("========");
    	
        //有未执行的程序
		//执行重启程序
		if(null != noRunList && noRunList.size() !=0) {
			System.out.println("not empty ===");
			logger.warn("noRunList not empty restart service");
			String restartSH = propertiesFactory.getInstance().getConfig("restartSH");
			String noRunListString = noRunList.toString();
			
			noRunListString = " <h3> not running service list </h3> <br/> \n <font color=red >" + noRunListString +" </font>";
			noRunListString+= "<br/><h3>check machine is :"+ ip +"</h3>";
		    noRunListString+= "<br/><h3>check time is :"+df.format(new Date()) +"</h3>";
			
			if(!restartSH.equals("")) {
				 noRunListString += "\n <br/> <h3> auto restart service complete </h3>";
			}else {
				 noRunListString += "\n <br/> <h3> No auto restart service</h3>";
			}
			   sendEmailForUser(noRunListString);
	
			 processSh(restartSH);
			
		}else {
			logger.info("service is running");
		}
		
		logger.info(noRunList);
		System.out.println(noRunList);
		    
	}
	
	
	/**
	 * 执行sh
	 * @param shpath
	 * @return
	 */
	private String processSh(String shpath ) {
		   
		   Process process =null;

		   InputStream in = null; 
		   
		   String result = "";
		   
		    try {
				process = Runtime.getRuntime().exec(shpath);
			    process.waitFor();
			      in = process.getInputStream();  
		            BufferedReader read = new BufferedReader(new InputStreamReader(in));  
		            StringBuffer buffer = new StringBuffer();  
		            String line = " ";  
		            while ((line = read.readLine()) != null){  
		                 buffer.append(line);  
		            }  
		             result = buffer.toString(); 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		return result;
	}
	
	
	/**
	 * 获取要检测的服务名称
	 * @return
	 */
	private String[] getServiceNames() {
		
		  String serviceNames = propertiesFactory.getInstance().getConfig("serviceNames");   //程序路径
		
		  String[] names = serviceNames.split(",");
		
		  return names;
	}
	
	
	
	private Map<String,List<String>> service(String pro) {
		
		List<String> runList = new ArrayList<String>();
		List<String>  noRunList = new ArrayList<String>();
		
		String[] serviceNames = getServiceNames();
		
		for(int i=0;i<serviceNames.length;i++){
			   if( pro.indexOf(serviceNames[i]) !=-1 )
				   runList.add(serviceNames[i]);
			   else
				   noRunList.add(serviceNames[i]);
		}
		
		Map<String, List<String>> serviceMap = new HashMap<String, List<String>>();
		serviceMap.put("runList", runList);
		serviceMap.put("noRunList", noRunList);
		
		return serviceMap;
	}
	
	
	
	
	
	public String sendEmailForUser(String content) {
		String emailusers = config.getConfig("emailusers");
		if(emailusers.equals("")) return "";
		String[] users = emailusers.split(",");
		for(int i=0;i<users.length;i++){
			mailUser mailuser = new mailUser();
			String useremail = users[0];
			logger.info("sending mail to " + useremail);
			mailuser.setUserEmail(useremail);
			mailuser.setUsername(useremail.split("@")[0]);
			sendEmail(mailuser,content);
		}
		return "";
	}
	
    public String sendEmail(mailUser mailuser , String norunString){

		send send =new send();
		mailInfo mailinfo=new mailInfo();
		mailinfo.setSendusername(config.getConfig("sendMail_Sendusername"));
		mailinfo.setSendMail(config.getConfig("sendMail_SendMail"));
	    mailinfo.setTitle(config.getConfig("sendMail_Title"));
		mailinfo.setSubject(config.getConfig("sendMail_Subject"));
		String content=config.getConfig("sendMail_emailContent");
		content +=norunString;
		
		mailinfo.setContent(content);
		send.mailInfo=mailinfo;
		send.mailUser=mailuser;
		return send.send();
    }

}
