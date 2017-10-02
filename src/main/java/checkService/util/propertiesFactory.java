package checkService.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

/**
 * 单例模式读取Properties属性文件
 * 
 */
public class propertiesFactory {

	/**
	 * 私有的属性防止外部引用
	 */
	private static propertiesFactory _instance = null;
	private Properties properties = new Properties();

	/**
	 * 私有的默认构造函数，防止使用构造函数进行实例化
	 */
	private propertiesFactory() {
		try {
			// InputStream inputStream = this.getClass().getResourceAsStream("jdbc.properties");
			InputStream inputStream = new FileInputStream(new File("./config.properties"));
			properties.load(inputStream);
			if (inputStream != null)
				inputStream.close();
		} catch (Exception e) {
			System.out.println(e + "file not found");
		}
	}

	/**
	 * 单例静态工厂方法，同步防止多线程环境同时执行
	 * 
	 * @date 2012-7-29
	 * @return
	 */
	synchronized public static propertiesFactory getInstance() {
		if (_instance == null) {
			_instance = new propertiesFactory();
		}
		return _instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public propertiesFactory clone() {
		return getInstance();
	}

	/**
	 * 读取配置信息key - value
	 * 
	 * @date 2012-7-29
	 * @param key
	 * @return
	 */
	public String getConfig(String key) {
		return properties.getProperty(key);
	}

	/**
	 * 判断键是否存在
	 * 
	 * @param key
	 * @return
	 */
	private boolean isKeyExist(String key) {
		boolean exist = false;
		Set<Object> keys = properties.keySet();
		for (Object object : keys) {
			if (object.toString().equals(key)) {
				exist = true;
				break;
			}
		}
		return exist;
	}

	/**
	 * 如果存在,读取,如果不存在,使用设置的默认值
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String getConfig(String key, String defaultValue) {
		if (isKeyExist(key))
			return properties.getProperty(key);
		else
			return defaultValue;
	}

}