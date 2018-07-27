package org.dqms.config;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.dqms.util.Print;
public class SystemSetting {

	//Logging
	public static boolean LOG_INCL_DATE = true;
	public static boolean LOG_INCL_STACKFRAME = true;
	public static boolean LOG_EMAIL_EXCEPTIONS = false;
	public static boolean LOG_JAVA_LOGGER =false;
	public static String LOG_NAME = "Print";
	public static String LOG_REDIRECT_LOG = "";	//CachedLogOutputStream
	public static boolean LOG_FILE_ENABLE = true;
	//public static String LOG_FILE = "/opt/tomcat/webapps/dqms/logs/" + LOG_NAME+ ".log";
	public static String LOG_FILE = "D:\\" + LOG_NAME+ ".log";
	
	//public static String LOG_FILE = StaticVariables.getLog_path_name();
	public static String LOG_FILE_ROTATE_SIZE = "5000000";
	public static String LOG_FILE_ROTATE_DELETE_AGE = "0";
	public static String LOG_FILE_ROTATE_EXTN = "yyyyMMddHHmmss'.log'";
	public static boolean isDebugMode = false;
	public static String LOG_LEVEL="info";
	public static String LOG_LEVEL_HEADER ="all";
	
	
	//Database
	public static String DB_URL = "jdbc:mysql://localhost:3306/dqms";
	public static String DB_NAME = "dqms";
	public static String DB_DRIVER = "com.mysql.jdbc.Driver";
	public static String DB_USER = "root";
	public static String DB_PASSWORD = "rootroot";
	
	// API URL
	
	public static String apiUrl = "http://localhost:8080/dqms/DCUKeyFunction?";
	
	public static String apiUrlService = "http://localhost:8080/dqms/ServiceData?";
	
	//Android Image Path
		public static String ANDROID_IMAGE_PATH = "login_image\\";
		
   // Mdu wdu Image path
		public static String mdu_IMAGE_PATH = "";
		
		
		public static String getIP(){
			InetAddress ip;
			String strIP="";
	        try {
	            ip = InetAddress.getLocalHost();
				strIP=""+ip;
				int i=strIP.lastIndexOf("/");
				strIP=strIP.substring(i+1);	 
	        } catch (UnknownHostException e) {
	 
	        	Print.logException("Exception " ,e);
	        }
			
			return strIP;
		}
		
		
}


