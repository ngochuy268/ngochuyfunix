package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {
	public static String applicationConfigReader(String key){
		try {
			File f = new File("./configuration/configs.properties");
			FileReader fr = new FileReader(f);
			Properties prop = new Properties();
			prop.load(fr);
			return prop.get(key).toString();
		}
		catch(Exception e) {
			return null;
		}
	}
	
	public static void saveToken(String key, String value) {
		Properties properties= new Properties();
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream("./configuration/token.properties");
			properties.setProperty(key, value);
			properties.store(fileOutputStream, "Set new value in properties");
			System.out.println("Set new value in file properties success.");
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static String tokenConfigReader(String token){
		try {
			File f = new File("./configuration/token.properties");
			FileReader fr = new FileReader(f);
			Properties prop = new Properties();
			prop.load(fr);
			return prop.get(token).toString();
		}
		catch(Exception e) {
			return null;
		}
	}
}
