package org.sly.uitest.settings;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Settings to be used for testing.
 * 
 * @author Julian Schillinger
 * @date : Jan 2, 2014
 * @company Prive Financial
 */
public class Settings {

	/** The base URL to load at browser start. */

	// public final static String BASE_URL =
	// "http://203.142.91.70:8080/SlyAWS/?locale=en";
	// public final static String BASE_URL =
	// "http://192.168.1.142:8080/SlyAWS/?locale=en";
	public final static String BASE_URL = "http://192.168.1.107:8080/SlyAWS/";
	public final static String BASE_URL_107 = "http://192.168.1.107:8080/SlyAWS/";
	public final static String LOGIN_URL = "http://192.168.1.107:8080/SlyAWS/?locale=en&viewMode=MATERIAL";
	// public final static String BASE_URL =
	// "http://192.168.1.104:8080/SlyA
	// WS/?locale=en_US&viewMode=material#home";

	// public final static String BASE_URL =
	// "http://localhost:8080/SlyAWS/?locale=en";
	// public final static String BASE_URL = "http://ifstest.privemanagers.com";
	public final static String NORTHSTAR_URL = "http://wstest.privemanagers.com/?locale=en_US&viewMode=MATERIAL#home";

	public final static String FINANCESALES_URL = "https://fsuat.privemanagers.com/financesales/login_de.html";

	/** The username to login with. */
	public final static String USERNAME = "SeleniumTest";

	/** The password to use to log in. */
	public final static String PASSWORD = "SeleniumTest";

	public final static String INVESTOR_USERNAME = "SeleniumInvestor";

	public final static String INVESTOR_PASSWORD = "SeleniumInvestor";

	public final static String CONSULTANT_USERNAME = "SeleniumConsultant";

	public final static String CONSULTANT_PASSWORD = "SeleniumConsultant";

	public final static String JARL_CONSULTANT_USERNAME = "JarlConsultant";

	public final static String JARL_CONSULTANT_PASSWORD = "JarlConsultant";

	public final static String ADMIN_USERNAME = "NandiAdmin";

	public final static String ADMIN_PASSWORD = "NandiAdmin";

	public final static String ADVISOR_USERNAME = "JarlAdvisor";

	public final static String ADVISOR_PASSWORD = "JarlAdvisor";

	public final static String CRM_USERNAME = "InfinityAdmin";

	public final static String CRM_PASSWORD = "InfinityAdmin";

	public final static String TRADE_USERNAME = "CentiveoAdmin";

	public final static String TRADE_PASSWORD = "CentiveoAdmin";

	public final static String SySAdmin_USERNAME = "DevAdmin";

	public final static String SysAdmin_PASSWORD = "DevAdmin";

	public final static String ADVISOR_TRIAL_USERNAME = "TrialAdvisor";

	public final static String ADVISOR_TRIAL_PASSWORD = "TrialAdvisor";

	public final static String NORTHSTAR_USERNAME = "NorthstarAdmin";

	public final static String NORTHSTAR_PASSWORD = "NorthstarAdmin";

	public final static String NORTHSTAR_WHOLESALER_USERNAME = "AnaAroche";

	public final static String NORTHSTAR_WHOLESALER_PASSWORD = "AnaAroche";

	public final static String Citi_P360_USER = "CitiP360Admin";

	public final static String Citi_P360_PASSWORD = "CitiP360Admin";

	public final static String FINANCESALES_ADVISOR_USERNAME = "TestGerman1";

	public final static String FINANCESALES_ADVISOR_PASSWORD = "TestGerman1";

	public final static String FINANCESALES_ADMIN_USERNAME = "FinanceSalesAdmin";

	public final static String FINANCESALES_ADMIN_PASSWORD = "FinanceSalesAdmin";

	public final static String THIRDROCK_ADMIN_USERNAME = "BobDoe";

	public final static String THIRDROCK_ADMIN_PASSWORD = "BobDoe";

	public final static String THIRDROCK_CONSULTANT_USERNAME = "JohnSmith";

	public final static String THIRDROCK_CONSULTANT_PASSWORD = "JohnSmith";

	public final static String SIMPLE = "SIMPLE";

	public final static int WAIT_SECONDS = 5;

	public final static boolean material = false;

	public final static String Advisor_Company_Module_Permission = "Advisor Company Module Permission";

	public final static String Manager_Module_Permission = "Manager Module Permission";

	public final static String AdvisorABCCompany_Key = "291";

	public final static String SeleniumTest_Key = "561";

	public final static String InfinityFinancialSolution_Key = "402";

	public final static Integer TEST_LOOPING_NUMBER = 1;

	/**
	 * This is to replace the while loop, including the infinite one
	 */
	public final static Integer ATTEMPT_LOOPING_NUMBER = 3;
	/**
	 * The key to authenticate with Sauce Labs for remote testing on Sauce Labs
	 * servers.
	 */
	public final static String SAUCE_LABS_KEY = "http://brianhuie:31ecd99e-6f19-490f-85c0-95fe2d4c9225@ondemand.saucelabs.com:80/wd/hub";

	/**
	 * Set to true if remote testing should be done on Sauce Labs servers. If
	 * set to false local Firefox will be used.
	 */
	public static final boolean EXECUTE_ON_SAUCE_LABS = false;

	/**
	 * The screenshot directory to put the files in, relative to the main
	 * directory.
	 */
	public static final String SCREENSHOT_DIR = "screenshots";

	/** Whether or not to take screenshots. */
	public static final boolean TAKE_SCREENSHOTS = false;

	/** Use {@link FirefoxDriver}. */
	public static final int WEB_DRIVER_FIREFOX = 1;

	/** Use {@link ChromeDriver}. */
	public static final int WEB_DRIVER_CHROME = 2;

	/** Specific {@link WebDriver} implementation to use. */
	public static final int WEB_DRIVER = WEB_DRIVER_CHROME;

	/**
	 * File location for uploading. Please consider to change the path if you
	 * cannot access the file location below
	 */
	public static final String FILE_LOCATION = "/home/bleung/Downloads/abc.txt";

	/**
	 * This is the path of Download folder for any downloading tests .Please
	 * consider to change the suitable path for your own environment
	 */
	public static final String DOWNLOAD_FOLDER_PATH = "/home/bleung/Downloads/";
	// please change if you have other email address.
	public static final String EMAIL_FOR_TESTING = "benny.leung@wismore.com";
}
