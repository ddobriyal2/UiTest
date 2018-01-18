package org.sly.uitest.pageobjects.mysettings;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;

/**
 * This class represents the User Profile page, which can be navigated by
 * clicking 'User Settings' -> 'User Preference'
 * 
 * @author Lynne Huang
 * @date : 19 Aug, 2015
 * @company Prive Financial
 */
public class UserPreferencePage extends AbstractPage

{

	private Class<?> theClass = null;

	/**
	 * @param webDriver
	 */
	public UserPreferencePage(WebDriver webDriver, Class<?> theClass) {

		super();
		this.webDriver = webDriver;
		this.theClass = theClass;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		try {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.id("gwt-debug-UpdateUserSystemPreferenceView-title")));

		} catch (Exception ex) {
			// wait.until(ExpectedConditions.visibilityOfElementLocated(By
			// .id("gwt-debug-MyMainMaterialView-mainPanel")));

		}

		assertTrue(pageContainsStr("User Preference"));
	}

	/**
	 * On the User Preference page, edit the landing page.
	 * 
	 * @param String
	 *            name - Account List, New Business, Company Commissions, Manage
	 *            Advisors, Investment List, Model Portfolio Overview
	 * 
	 * @return {@link UserPreferencePage}
	 */
	public UserPreferencePage editLandingPageByName(String name) {

		selectFromDropdown(By.id("gwt-debug-UpdateUserSystemPreferenceView-landingPageListBox"), name);

		return this;
	}

	/**
	 * On the User Prefrence page, edit the account detail default tab
	 * 
	 * @param String
	 *            name - Holdings, Compare, Analysis, History, Details, CRM,
	 *            CashFlow, Alerts
	 * 
	 * @return {@link UserPreferencePage}
	 */
	public UserPreferencePage editAccountDetailDefaultTab(String name) {

		selectFromDropdown(By.id("gwt-debug-UpdateUserSystemPreferenceView-productLevelPageListBox"), name);

		return this;
	}

	/**
	 * On the User Prerence page, edit the language
	 * 
	 * @param String
	 *            language - Deutsch, English, Francais, Indonesia, 日本語,
	 *            简体中文(中国大陆), 繁體中文(香港)
	 * 
	 * @return {@link UserPreferencePage}
	 */
	public UserPreferencePage editLanguage(String language) {

		selectFromDropdown(By.id("gwt-debug-UpdateUserSystemPreferenceView-languageListBox"), language);

		return this;
	}

	/**
	 * On the User Prerence page, select if load clients or not
	 * 
	 * @param boolean
	 *            option - true for yes, false for no
	 * 
	 * @return {@link UserPreferencePage}
	 * 
	 */
	public UserPreferencePage editLoadAccountAndClients(boolean option) {

		if (option) {

			clickElement(By.id("gwt-debug-UpdateUserSystemPreferenceView-accountLoadRadio-label"));

		} else if (option) {

			clickElement(By.id("gwt-debug-UpdateUserSystemPreferenceView-accountUnLoadRadio-label"));

		}

		return this;
	}

	/**
	 * 
	 * On the User Prerence page, select if show graph or not
	 * 
	 * @param String
	 *            option - yes, no
	 * 
	 * @return {@link UserPreferencePage}
	 */
	public UserPreferencePage editShowGraph(String option) {

		if (option.equals("yes")) {

			clickElement(By.id("gwt-debug-UpdateUserSystemPreferenceView-GraphLoadRadio-label"));

		} else if (option.equals("no")) {

			clickElement(By.id("gwt-debug-UpdateUserSystemPreferenceView-GraphUnLoadRadio-label"));

		}

		return this;
	}

	/**
	 * On the User Prerence page, click the radiobutton of themes
	 * 
	 * @param theme
	 * @return
	 */
	public UserPreferencePage editTheme(String theme) {
		clickElement(By.xpath(".//*[@id='gwt-debug-UpdateUserSystemPreferenceView-" + theme + "ViewRadio']"));
		return this;
	}

	/**
	 * 
	 * On the User Prerence page, click the SUBMIT button
	 * 
	 * @throws Exception
	 * 
	 * @return theClass
	 */
	@SuppressWarnings("unchecked")
	public <T> T clickSubmitButton() throws Exception {

		clickElement(By.id("gwt-debug-UpdateUserSystemPreferenceView-submitButton"));

		return (T) this.theClass.getDeclaredConstructor(WebDriver.class).newInstance(webDriver);

	}

}
