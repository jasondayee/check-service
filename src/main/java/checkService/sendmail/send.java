package checkService.sendmail;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import checkService.util.config;


public class send {
	
    public String myEmailAccount = config.getConfig("sendMail_myEmailAccount");
    public String myEmailPassword = config.getConfig("sendMail_myEmailPassword");
    public String myEmailSMTPHost = config.getConfig("sendMail_myEmailSMTPHost");
    public String protocol = config.getConfig("sendMail_protocol");
    public String smtpPort =config.getConfig("sendMail_smtpPort");
    
    public String auth=config.getConfig("sendMail_auth");//"true";
    public String authType=config.getConfig("sendMail_authType");//"tls";
    
    public  boolean debug =Boolean.parseBoolean(config.getConfig("sendMail_debug"));//ture
	//邮件内容	    
	public mailInfo mailInfo;
	//收件人
	public mailUser mailUser;
	    
	public  List<mailUser> mailUserList=null;
	    
	public String send() {
    	String message="";
    	sendMail.myEmailAccount=this.myEmailAccount;
    	sendMail.myEmailPassword=this.myEmailPassword;
    	sendMail.myEmailSMTPHost=this.myEmailSMTPHost;
    	sendMail.protocol=this.protocol;
    	sendMail.smtpPort=this.smtpPort;
    	sendMail.debug=this.debug;
    	sendMail.mailInfo=this.mailInfo;
    	sendMail.mailUser=this.mailUser;
    	sendMail.mailUserList=this.mailUserList;
    	sendMail.auth=this.auth;
    	sendMail.authType=this.authType;
    	try {
    		System.out.println("==========邮件创建中==========="+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    		sendMail.send();
    		message ="success";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		return message;
    } 


}
