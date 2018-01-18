package org.sly.uitest.pageobjects.mysettings;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.pageobjects.clientsandaccounts.AccountOverviewPage;

/**
 * This class represents the User Profile page, which can be navigated by
 * clicking 'User Settings' -> 'User Profile'
 * 
 * @author Lynne Huang
 * @date : 27 Aug, 2015
 * @company Prive Financial
 * 
 */
public class UserProfilePage extends AbstractPage

{
	/**
	 * @param webDriver
	 */
	public UserProfilePage(WebDriver webDriver) {

		super();
		this.webDriver = webDriver;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		try {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.id("gwt-debug-UpdateUserDetailsView-userDetailsWidgetPanel")));

		} catch (Exception ex) {
			// wait.until(ExpectedConditions.visibilityOfElementLocated(By
			// .id("gwt-debug-MyMainMaterialView-mainPanel")));

		}

		assertTrue(pageContainsStr(" Login Credential "));
		assertTrue(pageContainsStr(" 2-Step Verification "));
		assertTrue(pageContainsStr(" General Information "));
		assertTrue(pageContainsStr(" External Account Settings "));

	}

	/**
	 * On the User Profile page, edit the nickname
	 * 
	 * @param nickname
	 * 
	 * @return {@link UserProfilePage}
	 */
	public UserProfilePage editNickname(String nickname) {

		sendKeysToElement(By.id("gwt-debug-UpdateUserDetailsWidget-nicknameTextBox"), nickname);

		return this;
	}

	/**
	 * On the User Profile page, edit the firstname
	 * 
	 * @param firstname
	 * 
	 * @return {@link UserProfilePage}
	 */
	public UserProfilePage editFisrtname(String firstname) {

		sendKeysToElement(By.xpath(".//*[@id='gwt-debug-UpdateUserDetailsWidget-firstNameTextBox']//input"), firstname);

		return this;
	}

	/**
	 * On the User Profile page, edit the lastname
	 * 
	 * @param lastname
	 * 
	 * @return {@link UserProfilePage}
	 */
	public UserProfilePage editLastname(String lastname) {

		sendKeysToElement(By.xpath(".//*[@id='gwt-debug-UpdateUserDetailsWidget-lastNameTextBox']//input"), lastname);

		return this;
	}

	/**
	 * On the User Profile page, edit the othername
	 * 
	 * @param othername
	 * 
	 * @return {@link UserProfilePage}
	 */
	public UserProfilePage editOthername(String othername) {

		sendKeysToElement(By.xpath(".//*[@id='gwt-debug-UpdateUserDetailsWidget-otherNameTextBox']//input"), othername);

		return this;
	}

	/**
	 * On the User Profile page, edit the serving office
	 * 
	 * @param office
	 * 
	 * @return {@link UserProfilePage}
	 */
	public UserProfilePage editServingOffice(String office) {

		selectFromDropdown(By.id("gwt-debug-UpdateUserDetailsWidget-officeListBox"), office);

		return this;
	}

	public UserProfilePage editTimeZone(String timezone) {
		waitForElementVisible(By.id("gwt-debug-UpdateUserDetailsWidget-timeZoneListBox"), 10);
		selectFromDropdown(By.id("gwt-debug-UpdateUserDetailsWidget-timeZoneListBox"), timezone);
		return this;
	}

	/**
	 * On the User Profile page, add new email address
	 * 
	 * @param email
	 * @param type
	 * 
	 * @return {@link UserProfilePage}
	 */
	public UserProfilePage addNewEmailAddress(String email, String type) {

		clickElement(By.id("gwt-debug-UpdateUserDetailsWidget-addEmailButton"));

		int size = getSizeOfElements(By.xpath(
				"//div[@id='gwt-debug-EditUserEmailWidget-deletePanel']/button[@id='gwt-debug-EditUserDetailListWidget-deleteButton']"));

		System.out.println(size);

		sendKeysToElement(By
				.xpath("(//td[div[@id='gwt-debug-EditUserEmailWidget-deletePanel']/button[@id='gwt-debug-EditUserDetailListWidget-deleteButton']])["
						+ size + "]/preceding-sibling::td[4]/input"),
				email);

		selectFromDropdown(By
				.xpath("(//td[div[@id='gwt-debug-EditUserEmailWidget-deletePanel']/button[@id='gwt-debug-EditUserDetailListWidget-deleteButton']])["
						+ size + "]/preceding-sibling::td[3]/select"),
				type);

		return this;
	}

	/**
	 * On the User Profile page, delete the email address
	 * 
	 * @param email
	 *            the email to delete
	 * 
	 * @return {@link UserProfilePage}
	 */
	public UserProfilePage deleteEmailAddress(String email) {

		int size = getSizeOfElements(By.xpath(
				"//div[@id='gwt-debug-EditUserEmailWidget-deletePanel']/button[@id='gwt-debug-EditUserDetailListWidget-deleteButton']"));

		for (int i = 1; i <= size; i++) {

			String thisEmail = webDriver.findElement(By
					.xpath("(//td[div[@id='gwt-debug-EditUserEmailWidget-deletePanel']/button[@id='gwt-debug-EditUserDetailListWidget-deleteButton']])["
							+ i + "]/preceding-sibling::td[4]/input"))
					.getAttribute("value");

			log(thisEmail);

			if (thisEmail.equals(email)) {

				clickElement(By
						.xpath("(//td[div[@id='gwt-debug-EditUserEmailWidget-deletePanel']/button[@id='gwt-debug-EditUserDetailListWidget-deleteButton']])["
								+ i + "]//button"));

				break;

			}

		}

		return this;
	}

	public UserProfilePage editOldPassword(String oldPassword) {
		sendKeysToElement(By.id("gwt-debug-UpdateUserDetailsWidget-oldPassword"), oldPassword);

		return this;
	}

	public UserProfilePage editNewPassword(String newPassword) {
		sendKeysToElement(By.id("gwt-debug-UpdateUserDetailsWidget-newPassword"), newPassword);
		return this;
	}

	public UserProfilePage editConfirmPassword(String confirmPassword) {
		sendKeysToElement(By.id("gwt-debug-UpdateUserDetailsWidget-confirmPassword"), confirmPassword);
		return this;
	}

	public AccountOverviewPage clickUpdatePassword() {
		clickElement(By.id("gwt-debug-UpdateUserDetailsWidget-savePswButton"));

		return new AccountOverviewPage(webDriver);
	}

	/**
	 * On the User Profile page, click the UPDATE button under the General
	 * Information section
	 * 
	 * @return {@link AccountOverviewPage}
	 */
	public AccountOverviewPage updateGeneralInformation() {

		clickElement(By.id("gwt-debug-UpdateUserDetailsWidget-submitButton"));

		return new AccountOverviewPage(webDriver);
	}

}
