package checkService.sendmail;
public class mailInfo {
	
	
	public String title=null;

	public String Subject=null;
	
	public String Content=null;
	
	public String encoding="text/html;charset=UTF-8";
	
	public String sendMail=null;

	public String sendusername=null;
	
	public String getSendusername() {
		return sendusername;
	}


	public void setSendusername(String sendusername) {
		this.sendusername = sendusername;
	}

	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getSubject() {
		return Subject;
	}


	public void setSubject(String subject) {
		Subject = subject;
	}


	public String getContent() {
		return Content;
	}


	public void setContent(String content) {
		Content = content;
	}


	public String getEncoding() {
		return encoding;
	}


	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}


	public String getSendMail() {
		return sendMail;
	}


	public void setSendMail(String sendMail) {
		this.sendMail = sendMail;
	}

}
