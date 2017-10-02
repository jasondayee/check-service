package checkService.util;
import java.io.UnsupportedEncodingException;

public class config {

	
	/**
	 * get config value use properties
	 * filename is config.properties
	 * @param key
	 * @return
	 */
	public static String getConfig(String key){
		String value = null;
		value=propertiesFactory.getInstance().getConfig(key,"["+key+"]");
		try {
			value=new String(value.getBytes("ISO8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return value;
	}
}
