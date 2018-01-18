package org.sly.uitest.pageobjects.salesprocess;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;

/**
 * This class represents the Trial Advisor Page, which can be navigated by
 * clicking 'TESTZUGANG' in
 * http://fsuat.privemanagers.com/financesales/custom_login_page.html
 * 
 * @author Benny Leung
 * @date : 12 Sept, 2016
 * @company Prive Financial
 * 
 */
public class TrialAdvisorPage extends AbstractPage {
	private Class<?> theClass = null;

	public TrialAdvisorPage(WebDriver webDriver, Class<?> theClass) throws InterruptedException {

		super();
		this.webDriver = webDriver;
		this.theClass = theClass;
		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("gwt-debug-TrialAdvisorRegistrationPopupView-dialogBox-content")));

		assertTrue(isElementVisible(By.id("gwt-debug-TrialAdvisorRegistrationPopupView-dialogBox-caption")));

	}

	/**
	 * Edit the "Vorname" field in trial advisor window
	 * 
	 * @param firstName
	 * @return {@link TrialAdvisorPage}
	 */
	public TrialAdvisorPage editFirstName(String firstName) {
		sendKeysToElement(By.id("gwt-debug-TrialAdvisorRegistrationPopupView-firstNameTextBox"), firstName);
		return this;
	}

	/**
	 * Edit the "Nachname" field in trial advisor window
	 * 
	 * @param lastName
	 * @return {@link TrialAdvisorPage}
	 */
	public TrialAdvisorPage editLastName(String lastName) {
		sendKeysToElement(By.id("gwt-debug-TrialAdvisorRegistrationPopupView-lastNameTextBox"), lastName);
		return this;
	}

	/**
	 * Edit the "Telefonnummer" field in trial advisor window
	 * 
	 * @param telephone
	 * @return {@link TrialAdvisorPage}
	 */
	public TrialAdvisorPage editTelephone(String telephone) {
		sendKeysToElement(By.id("gwt-debug-TrialAdvisorRegistrationPopupView-phoneNumberTextBox"), telephone);
		return this;
	}

	/**
	 * Edit the "Email" field in trial advisor window
	 * 
	 * @param email
	 * @return {@link TrialAdvisorPage}
	 */
	public TrialAdvisorPage editEmail(String email) {
		sendKeysToElement(By.id("gwt-debug-TrialAdvisorRegistrationPopupView-emailTextBox"), email);
		return this;
	}

	public void clickSubmitButton() {

		clickElement(By.id("gwt-debug-TrialAdvisorRegistrationPopupView-submitButton"));
	}
}
