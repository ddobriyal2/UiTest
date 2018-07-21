package org.sly.uitest.framework;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import org.junit.rules.TestName;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;

import org.sly.uitest.settings.Settings;
//import org.sly.uitest.utils.TestBase;





/**
 * Abstract class to be extended by all test classes.
 * 
 * @author Brian Huie
 * @company Prive Financial
 */
public class AbstractTest {

	/** Browser driver. */
	protected WebDriver webDriver;

	/***/
	protected boolean acceptNextAlert = true;

	/***/
	protected StringBuffer verificationErrors = new StringBuffer();

	/** Flag to signal execution on SauceLabs. */
	protected final boolean executeOnSauceLabs = Settings.EXECUTE_ON_SAUCE_LABS;

	protected final int webDriverType = Settings.WEB_DRIVER;

	/** Base URL to load. */
	protected final String baseUrl = Settings.BASE_URL;

	/** URL to login */
	protected final String loginUrl = Settings.LOGIN_URL;
	/** NorthStart URL to load. */
	protected final String northstarUrl = Settings.NORTHSTAR_URL;

	/** NorthStart URL to load. */
	protected final String financesalesStaticURL = Settings.NORTHSTAR_URL;

	/** NorthStart URL to load. */
	protected final String financesalesURL = Settings.NORTHSTAR_URL;

	/** Suffix for screenshots. */
	
	protected String screenshotSuffix = "abstract";
	
	
	
	
	
	
	
	/**
	 * JUnit Rule that will record the test name of the current test. This is
	 * referenced when creating the {@link DesiredCapabilities}, so the Sauce
	 * Job is created with the test name.
	 */
	public @Rule TestName testName = new TestName();
	
	

	@Before
	public void setUp() throws Exception {

		if (executeOnSauceLabs) {
			/*
			 * Run on SauceLabs.
			 */
			// DesiredCapabilities capabilities = DesiredCapabilities
			// .internetExplorer();
			// capabilities.setCapability("version", "9");
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			capabilities.setCapability("platform", Platform.VISTA);
			capabilities.setCapability("screen-resolution", "1280x1024");
			capabilities.setCapability("name", testName.getMethodName());
			capabilities.setCapability("build", "pre-0.0.90");
			capabilities.setCapability("tags", "pre-0.0.90");
			webDriver = new RemoteWebDriver(new URL(Settings.SAUCE_LABS_KEY), capabilities);
		} else {

			/*
			 * Run locally
			 */
			switch (webDriverType) {
			case Settings.WEB_DRIVER_FIREFOX:
				FirefoxProfile firefoxProfile = new FirefoxProfile();
				firefoxProfile.setPreference("browser.download.folderList", 2);
				firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
				firefoxProfile.setPreference("browser.download.manager.alertOnEXEOpen", false);
				firefoxProfile.setPreference("browser.helperApps.neverAsk.openFile",
						"application/msword,application/csv,text/csv,image/png ,image/jpeg, application/pdf, text/html,text/plain,application/octet-stream,text/html;charset=UTF-8");
				firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk",
						"application/msword,application/csv,text/csv,image/png ,image/jpeg, application/pdf, text/html,text/plain,application/octet-stream,text/html;charset=UTF-8");
				firefoxProfile.setPreference("browser.download.manager.alertOnEXEOpen", "false");
				firefoxProfile.setPreference("browser.download.dir", Settings.DOWNLOAD_FOLDER_PATH);
				wait(3);
				webDriver = new FirefoxDriver();
				break;

			case Settings.WEB_DRIVER_CHROME:

				System.setProperty("webdriver.chrome.driver", "src/chromedriver");
				ChromeOptions chromeOptions = new ChromeOptions();
				chromeOptions.addArguments("test-type");
				// chromeOptions.addArguments("user-data-dir=/src/chromeProfile");
				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

				webDriver = new ChromeDriver();
				// webDriver = new RemoteWebDriver(new URL(
				// "http://192.168.1.24:4444/wd/hub"), capabilities);
				break;

			default:
				throw new RuntimeException("Unrecognised web driver type.");
			}

			webDriver.manage().window().maximize();
			wait(2);
		}

	}

	/**
	 * This functions ensures the login of the website works well.
	 * 
	 * @throws InterruptedException
	 */
	protected void checkLogin() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		By by;
		try {
			waitForElementVisible(By.xpath(".//*[@id='page-wrapper']/div[1]/nav/div/a/i"), 10);
		} catch (TimeoutException e) {

		}

		if (isElementVisible(By.xpath(".//*[@id='page-wrapper']/div[1]/nav/div/a/i"))) {
			// make sure textbox for username appears.
			wait(2);
			//waitForElementVisible(By.id("gwt-debug-UserLoginView-emailAddress"), 30); //Devesh
		} else {
			// Find login button.
			by = By.xpath("//*[@id=\"gwt-debug-LoginPanel-loginButton\"]");
			{
				waitForElementVisible(by, 10);

				assertTrue("Expected login button.", isElementPresent(by));
				assertTrue("Expected text 'log in'.", getTextByLocator(by).equals("log in"));

				// click login button
				clickElement(by);
			}
		}

		main.loginAs(Settings.USERNAME, Settings.PASSWORD);

		// // Enter username.
		// sendKeysToElement(By.id("gwt-debug-UserLoginView-emailAddress"),
		// Settings.USERNAME);
		//
		// // Enter password.
		// sendKeysToElement(By.id("gwt-debug-UserLoginView-password"),
		// Settings.PASSWORD);
		//
		// // Login
		// clickElement(By.id("gwt-debug-UserLoginView-loginButton"));

		checkLanguage();

	}

	
	
	
	protected void checkLogin(String username, String password) throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);
		By by;
		wait(2);

		if (isElementVisible(By.xpath(".//*[@id='page-wrapper']/div[1]/nav/div/a/i"))) {
			// make sure textbox for username appears.
			wait(3);
			//waitForElementVisible(By.id("gwt-debug-UserLoginView-emailAddress"), 30);

		} else {
			// Find login button.
			{
				by = By.xpath("//*[@id='gwt-debug-UserLoginView-loginButton']");
				waitForElementVisible(by, 20);

				//assertTrue("Expected login button.", isElementPresent(by));
				//assertTrue("Expected text 'LOGIN'.", getTextByLocator(by).equals("LOGIN"));

				// click login button
				clickElement(by);
			}
		}

		main.loginAs(username, password);
		// // Enter username.
		// sendKeysToElement(By.id("gwt-debug-UserLoginView-emailAddress"),
		// username);
		//
		// // Enter password.
		// sendKeysToElement(By.id("gwt-debug-UserLoginView-password"),
		// password);
		//
		// // Login
		// clickElement(By.id("gwt-debug-UserLoginView-loginButton"));

		// wait(2);

		checkLanguage();
	}

	protected void checkLogout() throws InterruptedException {
		// wait(Settings.WAIT_SECONDS);
		try {
			checkLogoutMaterialView();

		} catch (TimeoutException e) {
			scrollToTop();

			clickElement(By.xpath("//a[@id='gwt-debug-ImageMenuButton-displayImageLink' and @title='User Settings']"));

		}

		handleAlert();

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		try {
			wait.until(ExpectedConditions
					.invisibilityOfElementLocated((By.xpath(".//*[.='log out' and @class='material-logout']")))); 
		} catch (TimeoutException e) {
			checkLogoutMaterialView();
		}

	}

	private void checkLanguage() throws InterruptedException {

		wait(Settings.WAIT_SECONDS);
		By welcomeMessage = By.xpath(
				".//*[@id='gwt-debug-MyMainMaterialView-myMaterialTopMenuBarPanel']//span[contains(@class,'welcome-message')]");
		if (!isElementPresent(welcomeMessage)) {
			return;
		}

		String text = null;

		for (int i = 0; i < 20; i++) {

			WebElement element = waitGet(welcomeMessage);

			try {
				if (element == null)
					return;
				text = element.getText();
			} catch (StaleElementReferenceException e) {
				getStaleElem(welcomeMessage, webDriver);
			}
		}
		// fail to login
		// if (element == null)
		// return;

		// String text = element.getText();

		if (text == null || text.isEmpty() || !text.contains("Welcome")) {

			this.waitForElementVisible(By.id("gwt-debug-ImageMenuButton-displayImageLink"), Settings.WAIT_SECONDS);

			// for admin login
			if (!isElementDisplayed(By.xpath(
					"//a[@id='gwt-debug-ImageMenuButton-displayImageLink']/img[@src='img/icon_person_blue.png' and @class='gwt-Image']"))) {
				return;
			} else {
				clickElement(By.xpath(
						"//a[@id='gwt-debug-ImageMenuButton-displayImageLink']/img[@src='img/icon_person_blue.png' and @class='gwt-Image']"));

				wait(1);

			}

			if (!isElementDisplayed(By.xpath("//a[@href='#usersystempreference']"))) {
				return;
			} else {
				clickElement(By.xpath("//a[@href='#usersystempreference']"));

				wait(1);
			}
			selectFromDropdownByValue(By.id("gwt-debug-UpdateUserSystemPreferenceView-landingPageListBox"), "100");

			selectFromDropdownByValue(By.id("gwt-debug-UpdateUserSystemPreferenceView-productLevelPageListBox"), "200");

			selectFromDropdown(By.id("gwt-debug-UpdateUserSystemPreferenceView-languageListBox"), "English");

			clickElement(By.id("gwt-debug-UpdateUserSystemPreferenceView-accountLoadRadio-input"));

			wait(1);
			clickElement(By.id("gwt-debug-UpdateUserSystemPreferenceView-GraphLoadRadio-input"));

			wait(1);

			clickElement(By.id("gwt-debug-UpdateUserSystemPreferenceView-submitButton"));

			this.waitForWaitingScreenNotVisible();
		}

	}

	@Rule
	public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule();

	class ScreenshotTestRule implements MethodRule {
		public Statement apply(final Statement statement, final FrameworkMethod frameworkMethod, final Object o) {
			return new Statement() {
				@Override
				public void evaluate() throws Throwable {
					Throwable caughtThrowable = null;

					// implement retry logic here
					for (int i = 0; i < Settings.TEST_LOOPING_NUMBER; i++) {
						try {
							statement.evaluate();
							return;
						} catch (Throwable t) {
							caughtThrowable = t;
							this.captureScreenshot(frameworkMethod.getName());
							// System.out.println(": run " + (i+1) + " failed");
							System.err.println(frameworkMethod.getName() + ": run " + (i + 1) + " failed");
						} finally {
							tearDown();
						}
					}
					System.err.println(frameworkMethod.getName() + ": giving up after " + 2 + " failures");
					throw caughtThrowable;
				}

				public void captureScreenshot(String fileName) throws Exception {
					try {
						String timestamp = getCurrentTimeInFormat("yyyy-MM-dd-HH:mm:ss");
						new File("target/surefire-reports/").mkdirs(); // Ensure
																		// directory
																		// is
																		// there
						FileOutputStream out = new FileOutputStream(
								"target/surefire-reports/screenshot-" + fileName + "-" + timestamp + ".png");
						out.write(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES));
						out.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				public void tearDown() throws Exception {
					webDriver.quit();
					String verificationErrorString = verificationErrors.toString();
					if (!"".equals(verificationErrorString)) {
						fail(verificationErrorString);
					}

				}
			};
		}
	}

	/**
	 * This navigates through a 2-level navigation at the top of prive managers
	 * to the desired page.
	 * 
	 * @param by1
	 *            The identifier of the top-level menu item.
	 * @param by2
	 *            The identifier of the second-level menu item. Leave null if
	 *            only one level is required.
	 * @throws InterruptedException
	 */
	protected void navigateToPage(By by1, By by2) throws InterruptedException {
		// wait(5);
		// screenshot();
		this.waitForWaitingScreenNotVisible();
		for (int x = 0; x < 15; x++) {

			try {
				if (by2 == null) {
					waitForElementVisible(by1, 10);
					clickElement(by1);
					screenshot();
				} else {
					Actions builder = new Actions(webDriver);
					WebElement clientMenuButton = waitGet(by1);
					builder.moveToElement(clientMenuButton).perform();
					waitForElementVisible(by2, 10);
					clickElement(by2);
					screenshot();
				}
				return;
			} catch (org.openqa.selenium.ElementNotVisibleException e) {
				screenshot();
				log("Element not visible...retrying");
				wait(1);
			} catch (NullPointerException n) {
				getStaleElem(by2, webDriver);
			}

		}

		wait(5);
	}

	/**
	 * This is used to navigate to a page in case of 1-level navigation.
	 * 
	 * @param by1
	 *            The identifier of the top-level menu item.
	 * @throws InterruptedException
	 */
	protected void navigateToPage(By by1) throws InterruptedException {
		navigateToPage(by1, null);
	}

	public WebElement getStaleElem(By by, WebDriver driver) {
		try {
			return driver.findElement(by);
		} catch (org.openqa.selenium.StaleElementReferenceException e) {
			System.out.println("Attempting");
			return getStaleElem(by, driver);
		}
	}

	/**
	 * Causes the executing thread to sleep for the given number of seconds.
	 * 
	 * @param seconds
	 * @throws InterruptedException
	 */
	protected void wait(int seconds) throws InterruptedException {
		Thread.sleep(seconds * 1000);
	}

	/**
	 * Returns the given element after waiting for it to be rendered.
	 * 
	 * @param locator
	 * @return
	 */
	public WebElement waitGet(final By locator) {
		waitForWaitingScreenNotVisible();
		for (int x = 0; x < 10; x++) {
			WebElement foo = (new WebDriverWait(webDriver, 30)).ignoring(org.openqa.selenium.TimeoutException.class)
					.until(ExpectedConditions.visibilityOfElementLocated(locator));
			try {
				if (foo == null) {
					continue;
				}
				if (foo.isDisplayed()) {
					return foo;
				}
			} catch (org.openqa.selenium.StaleElementReferenceException s) {
				return getStaleElem(locator, webDriver);
			} catch (NullPointerException e) {
				return getStaleElem(locator, webDriver);
			}

			/*
			 * Previous version, generated StaleElementReferenceException:
			 * Element is no longer attached to the DOM
			 * 
			 * if (foo.isDisplayed()) { screenshot(); return foo; }
			 * 
			 * try { wait(1); } catch (InterruptedException e) {
			 * e.printStackTrace(); }
			 */
		}
		screenshot();
		return null;
	}

	/**
	 * Wait until the given element is visible, and then continue to execute the
	 * following test
	 * 
	 * @param seconds
	 *            TODO
	 */
	public void waitForElementVisible(By locator, int seconds) {
		this.waitForWaitingScreenNotVisible();
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(seconds, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

	}

	/**
	 * Wait until the given element is visible, and then continue to execute the
	 * following test
	 * 
	 * @param seconds
	 *            TODO
	 */
	public void waitForElementNotVisible(By locator, int seconds) {
		this.waitForWaitingScreenNotVisible();
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(seconds, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));

	}

	/**
	 * Wait until the given element is present, and then continue to execute the
	 * following test
	 * 
	 * @param seconds
	 *            TODO
	 */
	public void waitForElementPresent(By locator, int seconds) {

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(seconds, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));

	}

	/**
	 * Wait until the given element is clickable, and then continue to execute
	 * the following test
	 * 
	 * @param locator
	 * @param seconds
	 */
	public void waitForElementClickable(By locator, int seconds) {

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(seconds, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);
		wait.until(ExpectedConditions.elementToBeClickable(locator));

	}

	/**
	 * Wait until the given text is visible, and then continue to execute the
	 * following test
	 */
	public void waitForTextVisible(By locator, String text) {

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(300, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
	}

	public void waitForWaitingScreenNotVisible() {

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(10, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);

		try {
			//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(".//*[contains(@class,'waitScreen')]")));
		} catch (TimeoutException e) {
			try {
				wait.until(ExpectedConditions
						.invisibilityOfElementLocated(By.xpath(".//*[contains(@id,'waitingImage')]")));

			} catch (TimeoutException e2) {
				try {
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath(".//*[contains(@class,'waitingImage')]")));
				} catch (TimeoutException e3) {
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath(".//*[contains(@id,'waitPanel')]")));
				}

			}
		}

	}

	/**
	 * Check if the element is present in the web page
	 * 
	 * @return true, if the element is present; false, if the element is not
	 *         present
	 */
	protected boolean isElementPresent(By by) {
		try {
			this.waitForWaitingScreenNotVisible();
			webDriver.findElement(by);
			return true;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

	/**
	 * Check if the element is displayed in the web page
	 * 
	 * @return true, if the element is displayed; false, if the element is not
	 *         displayed
	 */
	public boolean isElementDisplayed(By locator) {

		try {
			waitForElementVisible(locator, 30);
			WebElement icon = webDriver.findElement(locator);
			if (icon.isDisplayed()) {
				return true;
			}
			return false;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		} catch (org.openqa.selenium.TimeoutException e) {
			return false;
		}

	}

	/**
	 * Check if the element is visible in the web page
	 * 
	 * @return true, if the element is visible
	 * @return false, if the element is not visible
	 * @throws InterruptedException
	 */

	public boolean isElementVisible(By locator) {

		try {
			this.waitForWaitingScreenNotVisible();
		} catch (TimeoutException e) {

		}
		try {
			waitForElementVisible(locator, 3);
			String visibility = webDriver.findElement(locator).getCssValue("display");
			if (visibility.contains("none")) {
				log("display: none");
				return false;
			} else {
				log("displayed");
				return true;
			}
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		} catch (org.openqa.selenium.TimeoutException e) {
			return false;
		}
	}

	/**
	 * Returns <code>true</code> if page contains given search string, otherwise
	 * <code>false</code>.
	 * 
	 * @throws InterruptedException
	 */
	public boolean pageContainsStr(String searchStr) {
		for (int x = 0; x < 5; x++) {
			screenshot();
			this.waitForWaitingScreenNotVisible();
			String pageContent = webDriver.getPageSource();
			if (searchStr.contains("&"))
				searchStr = searchStr.substring(0, searchStr.indexOf("&"));
			boolean result = pageContent.contains(searchStr);
			if (result)
				return true;
		}
		return false;
	}

	/**
	 * Returns <code>true</code> if page contains given search string, otherwise
	 * <code>false</code>.
	 * 
	 * @throws InterruptedException
	 */
	public boolean pageContainsStrMultiPages(String searchStr, int page) {

		for (int i = 0; i < page; i++) {

			for (int x = 0; x < 15; x++) {
				screenshot();
				String pageContent = webDriver.getPageSource();
				if (searchStr.contains("&"))
					searchStr = searchStr.substring(0, searchStr.indexOf("&"));
				boolean result = pageContent.contains(searchStr);
				if (result)
					return true;

			}
			waitForElementVisible(By.id("gwt-debug-PagerToolWidget-nextPageImg"), 3);

			clickElement(By.id("gwt-debug-PagerToolWidget-nextPageImg"));
		}
		return false;
	}

	/**
	 * Returns <code>true</code> if page contains given search string, otherwise
	 * <code>false</code>.
	 * 
	 * @throws InterruptedException
	 */
	public boolean pageContainsStrMultiPagesForInvestmentList(String searchStr, int page) {

		for (int i = 0; i < page; i++) {
			waitForElementVisible(By.id("gwt-debug-ManagerListItem-strategyName"), 30);
			for (int x = 0; x < 15; x++) {
				screenshot();
				String pageContent = webDriver.getPageSource();
				if (searchStr.contains("&"))
					searchStr = searchStr.substring(0, searchStr.indexOf("&"));
				boolean result = pageContent.contains(searchStr);
				if (result)
					return true;
				try {
					wait(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			waitForElementVisible(By.id("gwt-debug-ManagerListWidgetView-nextPageBtn"), 3);

			clickElement(By.id("gwt-debug-ManagerListWidgetView-nextPageBtn"));
		}
		return false;
	}

	/**
	 * Logs a message to STDOUT.
	 * 
	 * @param msg
	 */
	public void log(String msg) {
		System.out.println(msg);
	}

	public void log(Float msg) {
		System.out.println(msg);
	}

	/**
	 * Extract the string shown on the page of the given element. (Mostly used
	 * for compare if the string is correct or not.)
	 * 
	 */
	public String getTextByLocator(By locator) {

		this.waitForWaitingScreenNotVisible();
		try {
			waitForElementVisible(locator, 30);
		} catch (TimeoutException e) {
			waitForElementPresent(locator, 30);
		}
		String thisString = webDriver.findElement(locator).getText();

		if (thisString.equals("")) {

			thisString = webDriver.findElement(locator).getAttribute("value");

		}
		try {
			if (thisString.equals("")) {

				thisString = webDriver.findElement(locator).getAttribute("originalvalue");

			}

			log(thisString);
		} catch (NullPointerException e) {
			return null;
		}

		return thisString;

	}

	/**
	 * Extract the string that is input in the text box
	 */
	protected String getInputByLocator(By locator) {

		waitForElementVisible(locator, 30);
		WebElement elem = waitGet(locator);

		String value = elem.getAttribute("value");

		log(value);
		return value;

	}

	protected String getAttributeStringByLocator(By locator, String attribute) {

		waitForElementVisible(locator, 30);
		WebElement elem = waitGet(locator);
		return elem.getAttribute(attribute);

	}

	/**
	 * Get the number of elements which share the same locator on the same page
	 */
	public int getSizeOfElements(By locator) {

		waitForWaitingScreenNotVisible();

		int size = webDriver.findElements(locator).size();

		System.out.println("size of the given element: " + size);

		return size;
	}

	/**
	 * Get the pages of the list
	 */
	public int getPagesOfElements(By locator) {
		double page = 1;

		try {

			waitForElementVisible(By.id("gwt-debug-PagerToolWidget-pageInfoLabel"), 30);

			System.out.println(getTextByLocator(By.id("gwt-debug-PagerToolWidget-pageInfoLabel")));

			String total = (getTextByLocator(By.id("gwt-debug-PagerToolWidget-pageInfoLabel")).split(" "))[4];

			double size = (double) getSizeOfElements(locator);

			page = Double.valueOf(total) / size;

			return (int) Math.ceil(page);
		} catch (org.openqa.selenium.TimeoutException e) {

			return 1;
		}

	}

	/**
	 * Get the pages of the investment list
	 */
	public int getPagesOfElementsForInvestmentList(By locator) {

		try {

			waitForElementVisible(By.id("gwt-debug-ManagerListWidgetView-pageInfoLabel"), 30);

			System.out.println(getTextByLocator(By.id("gwt-debug-ManagerListWidgetView-pageInfoLabel")));

			String total = (getTextByLocator(By.id("gwt-debug-ManagerListWidgetView-pageInfoLabel")).split(" "))[4];
			int size = getSizeOfElements(locator);
			int page = Integer.valueOf(total) / size + 1;
			return page;
		} catch (org.openqa.selenium.TimeoutException e) {

			return 1;
		}

	}

	/**
	 * Clicks the given element.
	 * 
	 * @param locator
	 */
	public void clickElement(By locator) {
		for (int i = 0; i <= 5; i++) {
			try {
				waitForElementVisible(locator, 10);
				waitForElementClickable(locator, 10);
				WebElement elem = waitGet(locator);
				scrollToTop();
				new Actions(webDriver).moveToElement(elem).build().perform();
				wait(1);
				elem.click();
				break;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

	/**
	 * Clicks the given element by keyboard
	 * 
	 * @param locator
	 */
	public void clickElementByKeyboard(By locator) {

		waitForElementVisible(locator, 60);
		WebElement elem = webDriver.findElement(locator);
		// new Actions(webDriver).moveToElement(elem).perform();
		try {
			wait(2);
		} catch (InterruptedException e) {

		}

		elem.sendKeys("", Keys.ENTER);

	}

	/**
	 * Right click the given element
	 * 
	 * @param locator
	 * @param times
	 *            how many times for clicking arrow down to choose desired item
	 * @throws AWTException
	 */
	public void rightClickElement(By locator, int times) throws AWTException {
		WebElement elem = waitGet(locator);

		Actions action = new Actions(webDriver);
		action.contextClick(elem).build().perform();
		Robot robot = new Robot();
		for (int i = 0; i < times; i++) {
			robot.keyPress(KeyEvent.VK_DOWN);
		}

		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyPress(KeyEvent.VK_ENTER);
	}

	/**
	 * Clicks the given element and wait 3 seconds.
	 * 
	 * @param locator
	 * @throws InterruptedException
	 */
	public void clickElementAndWait3(By locator) throws InterruptedException {

		waitForElementVisible(locator, 30);
		WebElement elem = waitGet(locator);

		wait(Settings.WAIT_SECONDS);
		elem.click();
		wait(Settings.WAIT_SECONDS);
	}

	/**
	 * Send key strokes to the given element.
	 * 
	 * @param locator
	 * @param charSequences
	 */
	public void sendKeysToElement(By locator, CharSequence... charSequences) {

		waitForElementVisible(locator, 30);

		WebElement elem = waitGet(locator);
		/*
		 * try { wait(10); elem.click(); } catch(Exception ex) {
		 * ex.printStackTrace(); }
		 */
		elem.clear();
		elem.sendKeys(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END), Keys.BACK_SPACE);
		elem.sendKeys(charSequences);

	}

	/**
	 * Selects the entry where given text occurs from a dropdown.
	 * 
	 * @param dropdownElement
	 *            the dropdown.
	 * @param textToSelect
	 *            The text of the list entry to select.
	 */
	protected void selectFromDropdown(WebElement dropdownElement, String textToSelect) {

		Select select = new Select(dropdownElement);
		List<WebElement> list = select.getOptions();

		try {
			for (int i = 0; i < list.size(); i++) {

				if (list.get(i).getText().equalsIgnoreCase(textToSelect)) {
					select.selectByVisibleText(textToSelect);
					break;
				}
			}
		} catch (StaleElementReferenceException e) {
			select.selectByVisibleText(textToSelect);
		}

	}

	/**
	 * Selects the entry where given text occurs from a dropdown.
	 * 
	 * @param dropdownElement
	 *            the dropdown.
	 * @param valueToSelect
	 *            The value of the list entry to select.
	 */
	protected void selectFromDropdownByValue(WebElement dropdownElement, String valueToSelect) {

		Select select = new Select(dropdownElement);
		select.selectByValue(valueToSelect);

		// log(select.getFirstSelectedOption().getText());

	}

	/**
	 * Check if the given text is shown in the dropdown list
	 * 
	 * @return true, the given value is found
	 * @return false, the given value was not found
	 */
	protected boolean checkDropdownOptionsContainText(By locator, String value) {
		WebElement elem = waitGet(locator);
		Select select = new Select(elem);

		List<WebElement> list = select.getOptions();
		for (WebElement listElem : list) {
			if (listElem.getText().equals(value)) {
				return true;
			}
		}
		return false;

	}

	/**
	 * @return the first option of the dropdown
	 */
	protected String getSelectedTextFromDropDown(By locator) {
		WebElement elem = waitGet(locator);
		Select select = new Select(elem);
		return select.getFirstSelectedOption().getText();
	}

	/**
	 * Selects teh entry where given text occurs from a dropdown.
	 * 
	 * @param locator
	 *            teh dropdown's ID.
	 * @param textToSelect
	 *            The text of the list entry to select.
	 */
	protected void selectFromDropdown(By locator, String textToSelect) {

		waitForElementVisible(locator, 30);
		WebElement elem = waitGet(locator);
		// waitForElementVisible(locator, 5);
		selectFromDropdown(elem, textToSelect);
	}

	/**
	 * Selects teh entry where given text occurs from a dropdown.
	 * 
	 * @param locator
	 *            teh dropdown's ID.
	 * @param textToSelect
	 *            The text of the list entry to select.
	 */
	protected void selectFromDropdownByValue(By locator, String valueToSelect) {
		waitForElementVisible(locator, 30);
		WebElement elem = waitGet(locator);
		selectFromDropdownByValue(elem, valueToSelect);
	}

	/**
	 * Sets a checkbox to checked/unchecked.
	 * 
	 * @param checked
	 *            True if checkbox is supposed to be checked, false otherwise
	 */
	protected void setCheckboxStatus(WebElement we, boolean checked) {

		boolean selected = false;
		if (we.getAttribute("value") != null
				&& (we.getAttribute("value").equals("true") || we.getAttribute("value").equals("on")))
			selected = true;
		if (we.getAttribute("checked") != null
				&& (we.getAttribute("checked").equals("true") || we.getAttribute("checked").equals("on")))
			selected = true;
		if (we.isSelected())
			selected = true;
		if (selected && !checked)
			we.click();
		if (!selected && checked)
			we.click();
	}

	/**
	 * Sets a checkbox to checked/unchecked.
	 * 
	 * @param checked
	 *            True if checkbox is supposed to be checked, false otherwise
	 * 
	 * @return boolean - if it's changed, return true; otherwise, return false
	 * @throws InterruptedException
	 */

	protected boolean setCheckboxStatus2(WebElement we, boolean checked) {

		boolean selected = false;
		boolean changed = false;

		// for (int i = 0; i < 5; i++) {
		try {
			if (we.getAttribute("checked") != null
					&& (we.getAttribute("checked").equals("true") || we.getAttribute("checked").equals("on")))
				selected = true;
			if (we.isSelected())
				selected = true;

			if (selected && !checked) {
				new Actions(webDriver).moveToElement(we).build().perform();
				try {
					we.click();
				} catch (WebDriverException e) {
					we.sendKeys("", Keys.ENTER);
				}
				changed = true;
			}
			if (!selected && checked) {
				scrollToTop();
				new Actions(webDriver).moveToElement(we).build().perform();
				try {
					we.click();
				} catch (WebDriverException e) {
					we.sendKeys("", Keys.ENTER);
				}
				changed = true;
			}
		} catch (org.openqa.selenium.StaleElementReferenceException e) {
			e.printStackTrace();
			;
		}
		// }

		return changed;

	}

	/**
	 * If the alert dialogue is invoked, use this method to handle
	 */
	public void handleAlert() {

		try {
			Alert alert = webDriver.switchTo().alert();
			alert.accept();
		} catch (org.openqa.selenium.NoAlertPresentException e) {
			System.out.println();
		}

	}

	/**
	 * To refresh the current page
	 */
	public void refreshPage() {
		webDriver.navigate().refresh();
		this.handleAlert();
		this.handleAlert();
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 60);
		this.waitForWaitingScreenNotVisible();
	}

	/**
	 * @return the current time
	 * @param format
	 *            could be like "d-MMM-yyyy", "yyyyMMdd HH:mm", etc.
	 */
	public String getCurrentTimeInFormat(String format) {

		DateFormat dateFormat = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();

		String today = dateFormat.format(cal.getTime());

		return today;
	}

	/**
	 * Change the format of the given date
	 * 
	 * @return the given date with new format
	 * 
	 * @throws ParseException
	 * 
	 */
	public String changeDateFormat(String oldFormat, String newFormat, String date) throws ParseException {

		SimpleDateFormat format = new SimpleDateFormat(oldFormat);
		Date d = format.parse(date);
		format.applyPattern(newFormat);
		String newDate = format.format(d);

		return newDate;
	}

	/**
	 * Get the new date
	 * 
	 * @throws ParseException
	 * 
	 */
	public String getDateAfterDays(String currentDate, int days, String format) throws ParseException {

		SimpleDateFormat dateFormat = new SimpleDateFormat(format);

		Calendar cal = Calendar.getInstance();
		cal.setTime(dateFormat.parse(currentDate));

		cal.add(Calendar.DATE, days);

		String newDate = dateFormat.format(cal.getTime());

		return newDate;
	}

	/**
	 * @throws ParseException
	 * 
	 * @return == 0: day1 is equal to day2
	 * @return < 0 : day1 is before day2
	 * @return > 0 : day1 is after day2
	 */
	public int compareTwoDays(String day1, String day2, String format) throws ParseException {
		if (day1.equals("N/A") || day2.equals("N/A")) {
			return 0;
		} else {
			DateFormat df = new SimpleDateFormat(format);
			Date date1 = df.parse(day1);
			Date date2 = df.parse(day2);

			return date1.compareTo(date2);

		}

	}

	/**
	 * Returns a random number string.
	 * 
	 * @return
	 */
	public String getRandName() {
		Random rn = new Random();
		return "" + rn.nextInt();
	}

	/**
	 * Finds an element of type elementType (inside elem) which contains
	 * containingText.
	 * 
	 * @param elem
	 * @param elementType
	 * @param containingText
	 * @return
	 */
	public WebElement findElementContainsText(WebElement elem, By elementType, String containingText) {
		screenshot();
		List<WebElement> rows = elem.findElements(elementType);
		for (WebElement row : rows)
			if (row.getText().contains(containingText))
				return row;
		return null;
	}

	/** Takes a screenshot of the current page and saves it with a datestamp. */
	
	public void screenshot() {
		screenshot(screenshotSuffix);
	}

	/**
	 * Takes a screenshot of the current page and saves it with a datestamp.
	 * 
	 * @param label
	 */
	public void screenshot(String label) {
		
		if (!Settings.TAKE_SCREENSHOTS)
			return;

		try {
			// create output dir
			File theDir = new File(Settings.SCREENSHOT_DIR);

			// if the directory does not exist, create it
			if (!theDir.exists()) {
				log("creating directory: " + theDir.getAbsolutePath());
				boolean result = theDir.mkdir();
				if (result)
					log("DIR created");
			}

			// generate file name
			Date date = new Date();
			String filename = date.toString() + "_" + label + ".png";

			// take screenshot and copy it to folder
			File scrFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(Settings.SCREENSHOT_DIR + "/" + filename));

			// log
			log("Saved screenshot: " + filename);
		} catch (Exception e) {
			log("Exception occured while taking screenshot:" + e.getMessage());
		}
	}

	protected boolean compareInputValue(By locator, String value) {

		if (value == null)
			return false;

		String locatorValue = getInputByLocator(locator);

		if (value.equals(locatorValue)) {
			return true;
		}
		return false;

	}

	public void setScreenshotSuffix(String screenshotSuffix) {
		this.screenshotSuffix = screenshotSuffix;
	}

	// /**
	// * Changes to a tab with given name. This works for the "orange" tab bars.
	// *
	// * @param string
	// * @throws InterruptedException
	// */
	// protected void changeTab(String header) throws InterruptedException {
	// header = header.toUpperCase();
	// scrollToTop();
	// try {
	// wait(5);
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// WebElement we = findElementContainsText(
	// webDriver.findElement(By.tagName("html")),
	// By.id("gwt-debug-PortfolioOverviewTab-hyperlink"), header);
	//
	// if (we == null) {
	// try {
	// wait(15);
	// scrollToTop();
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	//
	// }
	// throw new RuntimeException("Could not find tab: " + header);
	//
	// }
	// we.click();
	// clickOkButtonIfVisible();
	// }

	/**
	 * change the format: FirstName LastName to new format LastName, FirstName
	 */
	public String changeNameFormat(String name) {

		String[] tokens = name.split(" ");
		if (tokens.length != 2) {
			throw new IllegalArgumentException();
		}
		String firstName = tokens[0];
		String lastName = tokens[1];
		String name2 = lastName + ", " + firstName;

		return name2;
	}

	public static boolean isNullOrEmptyColl(Collection<?> coll) {
		// return coll == null || CollectionUtils.isEmpty(coll);
		return coll == null || coll.isEmpty();
	}

	protected boolean getRadioButtonOrCheckBoxStatus(By locator) {
		WebElement elem = waitGet(locator);
		return elem.isSelected();
	}

	protected boolean checkElementExist(By locator) {
		return webDriver.findElements(locator).size() > 0;

	}

	protected void accountOverviewPageSearch(String text) throws InterruptedException {
		sendKeysToElement(By.id("gwt-debug-InvestorAccountOverviewView-searchBox"), text);

		// wait(2);

		clickElementAndWait3(By.id("gwt-debug-InvestorAccountOverviewView-searchImg"));

		// wait(5);
	}

	/**
	 * 
	 * */
	public void clickNextPageUntilFindElement(By locator, By targetElement) {

		waitForElementVisible(locator, 10);

		int page = getPagesOfElements(locator);

		for (int i = 0; i < page; i++) {
			try {
				webDriver.findElement(targetElement);
				break;
			} catch (Exception e) {
				log(e.getMessage());
				clickElement(By.id("gwt-debug-PagerToolWidget-nextPageImg"));
				// TODO: handle exception
			}
		}

	}

	/**
	 * @throws InterruptedException
	 * 
	 */
	public void clickOkButtonIfVisible() {
		//
		// try {
		// waitForElementVisible(By.id("gwt-debug-CustomDialog-okButton"), 30);
		//
		// if (isElementPresent(By.id("gwt-debug-CustomDialog-okButton"))) {
		//
		// clickElement(By.id("gwt-debug-CustomDialog-okButton"));
		// }
		// } catch (Exception e) {
		// // TODO: handle exception
		// }
		for (int i = 0; i < 2; i++) {
			try {
				waitForElementVisible(By.id("gwt-debug-CustomDialog-okButton"), 5);
				if (isElementVisible(By.id("gwt-debug-CustomDialog-okButton"))
						|| isElementPresent(By.id("gwt-debug-CustomDialog-okButton"))) {

					clickElement(By.id("gwt-debug-CustomDialog-okButton"));
					break;
				}
			} catch (org.openqa.selenium.TimeoutException e) {
				// TODO: handle exception
			}

		}

	}

	/**
	 * 
	 * */
	public void clickYesButtonIfVisible() {

		// if (isElementVisible(By.id("gwt-debug-CustomDialog-yesButton"))
		// || isElementPresent(By.id("gwt-debug-CustomDialog-yesButton"))) {
		//
		// clickElement(By.id("gwt-debug-CustomDialog-yesButton"));
		//
		// }
		try {
			wait(3);
		} catch (InterruptedException e) {

		}
		for (int i = 0; i < 3; i++) {
			try {
				waitForElementVisible(By.id("gwt-debug-CustomDialog-yesButton"), 5);
				if (isElementVisible(By.id("gwt-debug-CustomDialog-yesButton"))
						|| isElementPresent(By.id("gwt-debug-CustomDialog-yesButton"))) {

					clickElement(By.id("gwt-debug-CustomDialog-yesButton"));
					break;
				}
			} catch (org.openqa.selenium.TimeoutException e) {
				// TODO: handle exception
			}
		}

	}

	/**
	 * 
	 * */
	public void clickNoButtonIfVisible() {

		// if (isElementVisible(By.id("gwt-debug-CustomDialog-noButton"))
		// || isElementPresent(By.id("gwt-debug-CustomDialog-noButton"))) {
		//
		// clickElement(By.id("gwt-debug-CustomDialog-noButton"));
		//
		// }
		this.waitForWaitingScreenNotVisible();
		for (int i = 0; i < 2; i++) {
			try {
				waitForElementVisible(By.id("gwt-debug-CustomDialog-noButton"), 3);
				if (isElementVisible(By.id("gwt-debug-CustomDialog-noButton"))
						|| isElementPresent(By.id("gwt-debug-CustomDialog-noButton"))) {

					clickElement(By.id("gwt-debug-CustomDialog-noButton"));
					break;
				}
			} catch (org.openqa.selenium.TimeoutException e) {
				// TODO: handle exception
			}
		}

		// try {
		// waitForElementVisible(By.id("gwt-debug-CustomDialog-noButton"), 30);
		//
		// if (isElementPresent(By.id("gwt-debug-CustomDialog-noButton"))) {
		//
		// clickElement(By.id("gwt-debug-CustomDialog-noButton"));
		// }
		// } catch (Exception e) {
		// // TODO: handle exception
		// }

	}

	/**
	 * 
	 * */
	public void clickCancelButtonIfVisible() {

		// if (isElementVisible(By.id("gwt-debug-CustomDialog-cancelButton"))
		// || isElementPresent(By
		// .id("gwt-debug-CustomDialog-cancelButton"))) {
		//
		// clickElement(By.id("gwt-debug-CustomDialog-cancelButton"));
		//
		// }
		for (int i = 0; i < 2; i++) {
			try {
				waitForElementVisible(By.id("gwt-debug-CustomDialog-cancelButton"), 10);
				if (isElementVisible(By.id("gwt-debug-CustomDialog-cancelButton"))
						|| isElementPresent(By.id("gwt-debug-CustomDialog-cancelButton"))) {

					clickElement(By.id("gwt-debug-CustomDialog-cancelButton"));
					break;
				}
			} catch (org.openqa.selenium.TimeoutException e) {
				// TODO: handle exception
			}
		}

		// try {
		// waitForElementVisible(By.id("gwt-debug-CustomDialog-cancelButton"),
		// 30);
		//
		// if (isElementPresent(By.id("gwt-debug-CustomDialog-cancelButton"))) {
		//
		// clickElement(By.id("gwt-debug-CustomDialog-cancelButton"));
		// }
		// } catch (Exception e) {
		// // TODO: handle exception
		// }

	}

	/**
	 * 
	 * */
	public void clickProceedButtonIfVisible() throws InterruptedException {

		waitForElementVisible(By.xpath("//button[@id='gwt-debug-CustomDialog-customButton' and .='Proceed']"), 10);
		for (int i = 0; i < 2; i++) {
			try {
				if (isElementVisible(By.xpath("//button[@id='gwt-debug-CustomDialog-customButton' and .='Proceed']"))
						|| isElementPresent(
								By.xpath("//button[@id='gwt-debug-CustomDialog-customButton' and .='Proceed']"))) {

					clickElement(By.xpath("//button[@id='gwt-debug-CustomDialog-customButton' and .='Proceed']"));
					break;
				}
			} catch (org.openqa.selenium.TimeoutException e) {
				// TODO: handle exception
			}
		}

	}

	public void scrollToTop() {

		JavascriptExecutor javascript = (JavascriptExecutor) webDriver;
		javascript.executeScript("scroll(0,0)");

	}

	public void scrollToBottom() {

		JavascriptExecutor javascript = (JavascriptExecutor) webDriver;
		javascript.executeScript("scroll(0,2500)");

	}

	protected void checkLogoutMaterialView() throws InterruptedException {

		waitForElementVisible(By.xpath(".//*[.='log out' and @class='material-logout']"), 20);
		waitForElementClickable(By.xpath(".//*[.='log out' and @class='material-logout']"), 20);

		assertTrue(isElementVisible(By.xpath(".//*[.='log out' and @class='material-logout']")));
		// wait(2);
		clickElement(By.xpath(".//*[.='log out' and @class='material-logout']"));

		this.handleAlert();

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(10, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);
		wait.until(ExpectedConditions
				.invisibilityOfElementLocated(By.xpath(".//*[.='log out' and @class='material-logout']")));

		this.waitForWaitingScreenNotVisible();
	}

	protected void navigateToPageMaterialView(By by1, By by2) throws InterruptedException {
		// wait(5);
		// screenshot();
		this.waitForWaitingScreenNotVisible();
		for (int x = 0; x < 15; x++) {

			try {
				if (by2 == null) {
					clickElement(by1);
					screenshot();
				} else {
					Actions builder = new Actions(webDriver);
					WebElement clientMenuButton = waitGet(by1);
					builder.click(clientMenuButton).perform();
					wait(3);
					clickElement(by2);
					screenshot();
				}
				return;
			} catch (org.openqa.selenium.ElementNotVisibleException e) {
				screenshot();
				log("Element not visible...retrying");
				wait(1);
			} catch (NullPointerException n) {
				getStaleElem(by2, webDriver);
			}

		}

		wait(5);
	}

	public void sendKeysToElementByAction(By locator, CharSequence... charSequences) throws InterruptedException {

		WebElement elem = waitGet(locator);
		// wait(5);
		elem.click();
		elem.clear();
		elem.sendKeys(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END), Keys.BACK_SPACE);
		elem.sendKeys(charSequences);

	}

	public void clickElementByEnterKeyAndWait3(By locator) throws InterruptedException {

		WebElement elem = waitGet(locator);
		elem.sendKeys(Keys.ENTER);
		wait(Settings.WAIT_SECONDS);
	}

	/*
	 * check the number format. For example: from 123456.12 to 123,456.12
	 * 
	 * @param price
	 * 
	 * @return the string of price in number format
	 */
	public static String convertToCurrency(Double price) {

		DecimalFormat formatter;

		formatter = new DecimalFormat("###,###,##0.00");

		return formatter.format(price);
	}

	/**
	 * Make element visible on the screen and click it.
	 * 
	 * @param locator
	 * @throws InterruptedException
	 */
	public void makeElementVisibleAndClick(By locator) {
		waitForElementVisible(locator, 30);
		WebElement elem = webDriver.findElement(locator);

		Actions action = new Actions(webDriver);
		action.moveToElement(elem).perform();

		elem.sendKeys(Keys.ARROW_UP);
		clickElement(locator);

	}

	/**
	 * 
	 * @param url
	 *            url to be validated
	 * @return boolean true - if the url is valid false - if the url is invalid
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public boolean checkUrlValid(String url) throws MalformedURLException, IOException {
		log(url);
		HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();

		con.connect();
		// for (String header : con.getHeaderFields().keySet()) {
		// if (header != null) {
		// for (String value : con.getHeaderFields().get(header)) {
		// System.out.println(header + ":" + value);
		// }
		// }
		// }

		return (con.getResponseCode() < 300);
	}

	/**
	 * This method uses robot to simulate the action of clicking enter in
	 * download dialog
	 * 
	 * @throws AWTException
	 * @throws InterruptedException
	 */
	public void clickEnterButtonForDownload() throws AWTException, InterruptedException {
		wait(10);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyPress(KeyEvent.VK_ENTER);
		log("document downloaded");
	}

	/**
	 * This method checks whether the file is downloaded and readable. After
	 * that, the file will be deleted.
	 * 
	 * @param fileName
	 *            name of the file
	 * @throws InterruptedException
	 */
	public void checkDownloadedFile(String fileName) throws InterruptedException {
		wait(Settings.WAIT_SECONDS * 3);
		File dir = new File(Settings.DOWNLOAD_FOLDER_PATH);
		File[] dir_contents = dir.listFiles();
		String filename = fileName;

		for (int i = 0; i < dir_contents.length; i++) {
			if (dir_contents[i].getName().contains(fileName)) {
				filename = dir_contents[i].getName();
				
				
			}
		}

		File file = new File(Settings.DOWNLOAD_FOLDER_PATH + filename);

		for (int i = 0; i < 6; i++) {
			if (!file.exists()) {
				wait(Settings.WAIT_SECONDS * 2);
			}
		}

		assertTrue(file.exists());
		assertTrue(file.canRead());
		assertTrue(file.delete());
	}

	public void checkDownloadedExcelFile(String fileName, String... searchWords)
			throws InterruptedException, InvalidFormatException, IOException {

		wait(Settings.WAIT_SECONDS * 2);
		File dir = new File(Settings.DOWNLOAD_FOLDER_PATH);
		File[] dir_contents = dir.listFiles();
		String filename = fileName;

		for (int i = 0; i < dir_contents.length; i++) {
			if (dir_contents[i].getName().contains(fileName)) {
				filename = dir_contents[i].getName();
			}
		}

		File file = new File(Settings.DOWNLOAD_FOLDER_PATH + filename);

		for (int i = 0; i < 6; i++) {
			if (!file.exists()) {
				wait(Settings.WAIT_SECONDS * 2);
				break;
			}
		}

		assertTrue(file.exists());
		assertTrue(file.canRead());

		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheetAt(0);

		for (String searchWord : searchWords) {
			Boolean found = false;
			Iterator<Row> rowIterator = sheet.iterator();
			log(searchWord);
			while (rowIterator.hasNext()) {

				Row row = rowIterator.next();

				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_ERROR:
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						break;
					case Cell.CELL_TYPE_NUMERIC:
						if (HSSFDateUtil.isCellDateFormatted(cell)) {
							if (String.valueOf(cell.getDateCellValue()).toLowerCase()
									.contains(searchWord.toLowerCase())) {
								found = true;
							}
						}
						break;
					case Cell.CELL_TYPE_STRING:
						if (cell.getStringCellValue().toLowerCase().contains(searchWord.toLowerCase())) {
							found = true;
						}
						break;
					}
				}
			}
			assertEquals(true, found);
		}

		assertTrue(file.delete());

	}

	/**
	 * Add attribute for an element
	 * 
	 * @param locator
	 *            - element locator
	 * @param attribute
	 *            - attribute to add
	 * @param value
	 *            - value to add
	 */
	public void addElementAttributeByLocator(By locator, String attribute, String value) {
		WebElement elem = webDriver.findElement(locator);
		((JavascriptExecutor) webDriver).executeScript("arguments[0].setAttribute('" + attribute + "','" + value + "')",
				elem);
	}

	/**
	 * Edit attribute for an element
	 * 
	 * @param locator
	 *            - element locator
	 * @param attribute
	 *            - attribute to edit
	 * @param value
	 *            - value to edit
	 */
	public void editElementAttributeByLocator(By locator, String attribute, String value) {
		WebElement elem = webDriver.findElement(locator);
		((JavascriptExecutor) webDriver).executeScript("arguments[0].setAttribute('" + attribute + "','" + value + "')",
				elem);
	}

	/**
	 * delete attribute for an element
	 * 
	 * @param locator
	 *            - element locator
	 * @param attribute
	 *            - attribute to edit
	 */
	public void deleteElementAttributeByLocator(By locator, String attribute) {
		WebElement elem = webDriver.findElement(locator);
		((JavascriptExecutor) webDriver).executeScript("arguments[0].removeAttribute('" + attribute + "')", elem);
	}
}
