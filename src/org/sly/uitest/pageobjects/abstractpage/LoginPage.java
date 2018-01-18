package org.sly.uitest.pageobjects.abstractpage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.sly.uitest.settings.Settings;

/**
 * 
 * This class represents the Login Page
 * 
 * @author Lynne Huang
 * @date : 6 Aug, 2015
 * @company Prive Financial
 */
public class LoginPage extends AbstractPage {
	WebDriver loginDriver;

	public LoginPage(WebDriver webDriver) throws InterruptedException {

		super();
		this.webDriver = webDriver;
		wait(1);
		webDriver.get(loginUrl);
		try {wait(1); //devesh
			//waitForElementVisible(By.xpath(".//*[.='log out' and @class='material-logout']"), Settings.WAIT_SECONDS);

			//checkLogout();
		} catch (TimeoutException e) {
		}

		// try {
		// waitForElementVisible(
		// By.id("gwt-debug-UserLoginView-emailAddress"), 60);
		// } catch (Exception e) {
		// checkLogout();
		// }
/*
		boolean isMaterial = checkIfMaterial();

		if (isMaterial) {
			waitForElementVisible(By.xpath("//span[.='Login']"), 300);
		} else {
			// wait until the login button visible
			waitForElementVisible(By.xpath("//*[@id='gwt-debug-LoginPanel-loginButton']"), 300);
			// FluentWait<WebDriver> waitLogin = new FluentWait<WebDriver>(
			// webDriver).withTimeout(60, TimeUnit.SECONDS)
			// .pollingEvery(2, TimeUnit.SECONDS)
			// .ignoring(org.openqa.selenium.NoSuchElementException.class);
			//
			// waitLogin.until(ExpectedConditions.visibilityOfElementLocated(By
			// .xpath("//*[@id=\"gwt-debug-LoginPanel-loginButton\"]")));

			// elem = waitGet(By
			// .xpath("//*[@id=\"gwt-debug-LoginPanel-loginButton\"]"));
		}
*/
		// elem.click();

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		// FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver)
		// .withTimeout(30, TimeUnit.SECONDS)
		// .pollingEvery(2, TimeUnit.SECONDS)
		// .ignoring(org.openqa.selenium.NoSuchElementException.class);
		//
		// wait.until(ExpectedConditions.visibilityOfElementLocated(By
		// .id("gwt-debug-UserLoginView-emailAddress")));
		
		
		//waitForElementVisible(By.id("gwt-debug-UserLoginView-emailAddress"), 120);

	}

	public LoginPage(WebDriver webDriver, String url) throws InterruptedException {

		super();
		this.webDriver = webDriver;

		webDriver.get(url);
		//
		// FluentWait<WebDriver> waitLogin = new
		// FluentWait<WebDriver>(webDriver)
		// .withTimeout(60, TimeUnit.SECONDS)
		// .pollingEvery(2, TimeUnit.SECONDS)
		// .ignoring(org.openqa.selenium.NoSuchElementException.class);
		// boolean isMaterial = isElementVisible(By
		// .xpath(".//*[@id='page-wrapper']/div[1]/nav/div/a/i"));
		// if (!isMaterial) {
		// waitLogin.until(ExpectedConditions.visibilityOfElementLocated(By
		// .xpath("//*[@id=\"gwt-debug-LoginPanel-loginButton\"]")));
		//
		// clickElement(By
		// .xpath("//*[@id=\"gwt-debug-LoginPanel-loginButton\"]"));
		// }

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		// FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver)
		// .withTimeout(60, TimeUnit.SECONDS).pollingEvery(2,
		// TimeUnit.SECONDS);
		//
		// wait.until(ExpectedConditions.visibilityOfElementLocated(By
		// .id("gwt-debug-UserLoginView-emailAddress")));

		waitForElementVisible(By.id("gwt-debug-UserLoginView-emailAddress"), 60);

	}

	/**
	 * @param username
	 *            the username to login in
	 * @return {@link LoginPage}
	 */
	public LoginPage typeUsername(String username) throws InterruptedException {
		wait(10);
		
		waitForElementVisible(By.id("gwt-debug-UserLoginView-emailAddress"), 10);
	
		

		sendKeysToElement(By.id("gwt-debug-UserLoginView-emailAddress"), username);
         
		
		try {
			assertEquals(username, getTextByLocator(By.id("gwt-debug-UserLoginView-emailAddress")));
		} catch (NullPointerException | AssertionError e) {
			for (int i = 0; i < Settings.ATTEMPT_LOOPING_NUMBER; i++) {
				if (!getTextByLocator(By.id("gwt-debug-UserLoginView-emailAddress")).equals(username)) {

					sendKeysToElement(By.id("gwt-debug-UserLoginView-emailAddress"), username);
					wait(1);
				}

			}
		} catch (TimeoutException ex) {

		}
		return this;
	}

	/**
	 * @param password
	 *            the password to login in
	 * @return {@link LoginPage}
	 */
	public LoginPage typePassword(String password) throws InterruptedException {

		waitForElementVisible(By.id("gwt-debug-UserLoginView-password"), 120);

		sendKeysToElement(By.id("gwt-debug-UserLoginView-password"), password);

		wait(2);
		try {
			assertEquals(password, getTextByLocator(By.id("gwt-debug-UserLoginView-password")));
		} catch (NullPointerException | AssertionError e) {
			for (int i = 0; i < Settings.ATTEMPT_LOOPING_NUMBER; i++) {
				if (!getTextByLocator(By.id("gwt-debug-UserLoginView-password")).equals(password)) {

					sendKeysToElement(By.id("gwt-debug-UserLoginView-password"), password);
					wait(1);
				}
			}

		} catch (TimeoutException ex) {

		}
		return this;
	}

	/**
	 * Click the LOGIN button
	 * 
	 * @param username
	 * @param password
	 * 
	 * @return {@link MenuBarPage}
	 * 
	 */
	public MenuBarPage submitLogin(String username, String password) throws InterruptedException {

		scrollToTop();

		if (!isElementDisplayed(By.id("gwt-debug-UserLoginView-loginButton"))) {

			try {
				waitForElementVisible(By.id("gwt-debug-UserLoginView-loginButton"), 300);
			} catch (TimeoutException e) {
				try {
					waitForElementVisible(By.xpath(".//*[.='log out' and @class='material-logout']"), 6);

					checkLogout();
					loginAs(username, password);
				} catch (TimeoutException ex) {

				}
			}
		}
		wait(2);
		clickElement(By.id("gwt-debug-UserLoginView-loginButton"));

		return new MenuBarPage(webDriver);

	}

	/**
	 * @param username
	 * @param password
	 * 
	 * @return {@link MenuBarPage}
	 */
	public MenuBarPage loginAs(String username, String password) throws InterruptedException {

		try {
			wait(1);
			
			WebElement elem = webDriver.findElement(By.xpath(".//*[.='log out' and @class='material-logout']"));
			return new MenuBarPage(webDriver);
		} catch (Exception e) {
			typeUsername(username);
			typePassword(password);
			return submitLogin(username, password);
		}

	}

	/**
	 * @param username
	 * @param password
	 * 
	 */
	public void loginIncorrectlyAs(String username, String password) throws InterruptedException {

		typeUsername(username);
		typePassword(password);

		clickElement(By.id("gwt-debug-UserLoginView-loginButton"));

		WebElement webEleButton = waitGet(By.id("gwt-debug-CustomDialog-okButton"));
		assertTrue(webEleButton.isDisplayed());

		clickOkButtonIfVisible();

	}

}
