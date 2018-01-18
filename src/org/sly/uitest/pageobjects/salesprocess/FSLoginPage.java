package org.sly.uitest.pageobjects.salesprocess;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.ComparisonFailure;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.settings.Settings;

/**
 * This class represents the Static HomePage, which can be navigated by
 * http://fsuat.privemanagers.com/financesales/custom_login_page.html
 * 
 * @author Benny Leung
 * @date : 13 Sept, 2016
 * @company Prive Financial
 * 
 */
public class FSLoginPage extends AbstractPage {
	public FSLoginPage(WebDriver webDriver) {
		super();
		this.webDriver = webDriver;

		webDriver.get(Settings.FINANCESALES_URL);

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@class='financeSalesContainerPanel']")));

		assertTrue(isElementVisible(By.xpath(".//*[@class='financeSalesLoginInput']")));
	}

	/**
	 * Edit the username in login page
	 * 
	 * @param username
	 *            username of the account
	 * @return {@link FSLoginPage}
	 * @throws InterruptedException
	 */
	public FSLoginPage editUsername(String username) throws InterruptedException {

		waitForElementVisible(By.xpath(".//input[@class='financeSalesLoginInput' and @name='log']"), 10);

		sendKeysToElement(By.xpath(".//input[@class='financeSalesLoginInput' and @name='log']"), username);
		wait(2);

		try {
			assertEquals(username,
					getTextByLocator(By.xpath(".//input[@class='financeSalesLoginInput' and @name='log']")));
		} catch (ComparisonFailure e) {

			while (!getTextByLocator(By.xpath(".//input[@class='financeSalesLoginInput' and @name='log']"))
					.equals(username)) {

				sendKeysToElement(By.xpath(".//input[@class='financeSalesLoginInput' and @name='log']"), username);
				wait(1);
			}
		} catch (TimeoutException ex) {

		}
		return this;
	}

	/**
	 * Edit the password in login page
	 * 
	 * @param password
	 *            password of the account
	 * @return {@link FSLoginPage}
	 * @throws InterruptedException
	 */
	public FSLoginPage editPassword(String password) throws InterruptedException {

		waitForElementVisible(By.xpath(".//input[@class='financeSalesLoginInput' and @type = 'password']"), 10);
		sendKeysToElement(By.xpath(".//input[@class='financeSalesLoginInput' and @type = 'password']"), password);

		try {
			assertEquals(password,
					getTextByLocator(By.xpath(".//input[@class='financeSalesLoginInput' and @type = 'password']")));
		} catch (ComparisonFailure e) {

			while (!getTextByLocator(By.xpath(".//input[@class='financeSalesLoginInput' and @type = 'password']"))
					.equals(password)) {

				sendKeysToElement(By.xpath(".//input[@class='financeSalesLoginInput' and @type = 'password']"),
						password);
				wait(1);
			}
		} catch (TimeoutException ex) {

		}

		return this;
	}

	/**
	 * click login button in login page
	 * 
	 * @return {@link FSLoginPage}
	 */
	public FSMenubarPage clickLoginButton() {
		clickElement(By.xpath(".//input[@class='coloredBtn' and @title='Login']"));
		return new FSMenubarPage(webDriver);
	}

	/**
	 * click forget password link in login page
	 * 
	 * @return {@link FSLoginPage}
	 */
	public ForgetPasswordPage gotoForgetPasswordPage() {
		clickElement(By.xpath("//a[contains(text(), 'Passwort')]"));
		return new ForgetPasswordPage(webDriver, LoginPage.class);
	}

	/**
	 * click register button in login page
	 * 
	 * @return {@link FSLoginPage}
	 * @throws InterruptedException
	 */
	public AdvisorRegisterPage goToAdvisorRegisterPage() throws InterruptedException {
		clickElement(By.xpath("//button[contains(text(), 'Registrierung')]"));
		return new AdvisorRegisterPage(webDriver);
	}

	/**
	 * click trial advisor button in login page
	 * 
	 * @return {@link FSLoginPage}
	 * @throws InterruptedException
	 */
	public TrialAdvisorPage goToTrialAdvisorPage() throws InterruptedException {
		clickElement(By.xpath("//button[contains(text(), 'TESTZUGANG')]"));
		// webDriver.get("https://fsuat.privemanagers.com/?locale=fr_FR&viewMode=financesales#pre_login_register;token=12345;demo=true");
		return new TrialAdvisorPage(webDriver, LoginPage.class);
	}

	/**
	 * login in with username and password
	 * 
	 * @param username
	 * @param password
	 * @return {@link FSMenubarPage}
	 * @throws InterruptedException
	 */
	public FSMenubarPage loginAs(String username, String password) throws InterruptedException {

		this.editUsername(username);
		this.editPassword(password);

		return clickLoginButton();
	}
}
