package org.sly.uitest.pageobjects.companyadmin;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.pageobjects.clientsandaccounts.AccountOverviewPage;
import org.sly.uitest.settings.Settings;

/**
 * This class represents the Company Settings Page, including some pop-up edit
 * page, which can be navigated by clicking 'Company Settings' -> 'Company
 * Settings'
 * 
 * URL: "http://192.168.1.104:8080/SlyAWS/?locale=en#settings"
 * 
 * @author Lynne Huang
 * @date : 20 Aug, 2015
 * @company Prive Financial
 * 
 */
public class CompanySettingsPage extends AbstractPage {

	/**
	 * @param webDriver
	 */
	public CompanySettingsPage(WebDriver webDriver) {

		super();
		this.webDriver = webDriver;

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		try {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.id("gwt-debug-CompanySettingsView-companyInformationSection")));

		} catch (Exception ex) {
			// wait.until(ExpectedConditions.visibilityOfElementLocated(By
			// .id("gwt-debug-MyMainMaterialView-mainPanel")));

		}

		// assertTrue(pageContainsStr(" Update Company Information "));
	}

	/**
	 * On the Company Settings Page, under the Company Information section, edit
	 * the description
	 * 
	 * @param description
	 * 
	 * @return {@link CompanySettingsPage}
	 */
	public CompanySettingsPage editDescription(String description) {

		sendKeysToElement(By.id("gwt-debug-CompanySettingsView-companyDesc"), description);

		return this;
	}

	/**
	 * On the Company Settings Page, under the Company Information section, add
	 * a new email with the email address and the email description
	 * 
	 * @param emailAddress
	 * @param description
	 * 
	 * @return {@link CompanySettingsPage}
	 */
	public CompanySettingsPage addEmailWithAddress(String emailAddress, String description) {

		clickElement(By.id("gwt-debug-AdminAdvisorCompanyEdit-addEmailButton"));

		int size = this
				.getSizeOfElements(By.xpath("//td[div[@id='gwt-debug-EditUserEmailWidget-deletePanel']/button]"));

		sendKeysToElement(By.xpath("(//td[div[@id='gwt-debug-EditUserEmailWidget-deletePanel']/button])[" + size
				+ "]/preceding-sibling::td[4]/input"), emailAddress);

		sendKeysToElement(By.xpath("(//td[div[@id='gwt-debug-EditUserEmailWidget-deletePanel']/button])[" + size
				+ "]/preceding-sibling::td[2]/input"), description);

		return this;
	}

	/**
	 * On the Company Settings Page, under the Company Information section,
	 * delete the i-th email
	 * 
	 * @param i
	 *            delete the i-th email
	 * 
	 * @return {@link CompanySettingsPage}
	 */
	public CompanySettingsPage deleteEmailByNumber(String i) {

		clickElement(By.xpath(".//*[@id='gwt-debug-EditUesrEmailListWidget-contentPanel']/div[" + i
				+ "]//*[@id='gwt-debug-EditUserDetailListWidget-deleteButton']"));

		return this;
	}

	/**
	 * On the Company Settings Page, under the Company Information section,
	 * delete all the emails
	 * 
	 * @return {@link CompanySettingsPage}
	 */
	public CompanySettingsPage deleteAllEmails() {

		int size = this.getSizeOfElements(By.xpath("//div[@id='gwt-debug-EditUserEmailWidget-deletePanel']/button"));

		System.out.println(size);

		for (int i = 0; i < size; i++) {

			clickElement(By.xpath("//div[@id='gwt-debug-EditUserEmailWidget-deletePanel']/button"));
		}

		return this;
	}

	/**
	 * On the Company Settings Page, under the Company Information section,
	 * select the i-th email as the default email
	 * 
	 * @param i
	 *            the i-th email to be the default email
	 * 
	 * @return {@link CompanySettingsPage}
	 */
	public CompanySettingsPage selectDefaultEmailByNumber(String i) {

		int size = this
				.getSizeOfElements(By.xpath("//td[div[@id='gwt-debug-EditUserEmailWidget-deletePanel']/button]"));

		WebElement we = webDriver
				.findElement(By.id("(//td[div[@id='gwt-debug-EditUserEmailWidget-deletePanel']/button])[" + size
						+ "]//preceding-sibling::td[1]/button"));

		if (we.getAttribute("class").contains("fa-star-o")) {
			setCheckboxStatus(we, true);
		}

		return this;
	}

	/**
	 * On the Company Settings Page, under the Company Information section, add
	 * a tel with the given code, number and the tel description
	 * 
	 * @param code
	 * @param number
	 * @param description
	 * 
	 * @return {@link CompanySettingsPage}
	 */
	public CompanySettingsPage addTelWithNumber(String code, String number, String description) {

		clickElement(By.id("gwt-debug-AdminAdvisorCompanyEdit-addTelButton"));

		int size = this.getSizeOfElements(By.xpath("//div[@id='gwt-debug-EditUserPhoneWidget-deletePanel']/button"));

		sendKeysToElement(By.xpath("(//td[div[@id='gwt-debug-EditUserPhoneWidget-deletePanel']/button])[" + size
				+ "]//preceding-sibling::td[6]/input"), code);

		sendKeysToElement(By.xpath("(//td[div[@id='gwt-debug-EditUserPhoneWidget-deletePanel']/button])[" + size
				+ "]//preceding-sibling::td[4]/input"), number);

		sendKeysToElement(By.xpath("(//td[div[@id='gwt-debug-EditUserPhoneWidget-deletePanel']/button])[" + size
				+ "]//preceding-sibling::td[2]/input"), description);

		return this;
	}

	/**
	 * On the Company Settings Page, under the Company Information section,
	 * delete the i-th tel
	 * 
	 * @param i
	 *            delete the i-th tel
	 * 
	 * @return {@link CompanySettingsPage}
	 */
	public CompanySettingsPage deleteTelByNumber(String i) {

		clickElement(By.xpath(".//*[@id='gwt-debug-EditUserPhoneListWidget-contentPanel']/div[" + i
				+ "]//*[@id='gwt-debug-EditUserDetailListWidget-deleteButton']"));

		return this;
	}

	/**
	 * On the Company Settings Page, under the Company Information section,
	 * delete all the tels
	 * 
	 * @return {@link CompanySettingsPage}
	 */
	public CompanySettingsPage deleteAllTels() {

		int size = this.getSizeOfElements(By.xpath("//div[@id='gwt-debug-EditUserPhoneWidget-deletePanel']/button"));

		for (int i = 0; i < size; i++) {

			clickElement(By.xpath("//div[@id='gwt-debug-EditUserPhoneWidget-deletePanel']/button"));
		}

		return this;
	}

	/**
	 * On the Company Settings Page, under the Company Information section,
	 * select the i-th tel to be the default telephone number
	 * 
	 * @return {@link CompanySettingsPage}
	 */
	public CompanySettingsPage selectDefaultTelByNumber(String i) {

		int size = this
				.getSizeOfElements(By.xpath("//td[div[@id='gwt-debug-EditUserPhoneWidget-deletePanel']/button]"));

		WebElement we = webDriver
				.findElement(By.xpath("(//td[div[@id='gwt-debug-EditUserPhoneWidget-deletePanel']/button])[" + size
						+ "]//preceding-sibling::td[1]/button"));

		if (we.getAttribute("class").contains("fa-star-o")) {
			setCheckboxStatus(we, true);
		}

		return this;
	}

	/**
	 * On the Company Settings Page, under the Company Information section, add
	 * a new address with the given street, city, state, zip, country and the
	 * address description
	 * 
	 * @param line1
	 * @param line2
	 * @param line3
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 * @param description
	 * 
	 * @return {@link CompanySettingsPage}
	 */
	public CompanySettingsPage addAddress(String line1, String line2, String line3, String city, String state,
			String zip, String country, String description) {

		clickElement(By.id("gwt-debug-AdminAdvisorCompanyEdit-addAddrButton"));

		int size = this.getSizeOfElements(By.xpath("//div[@id='gwt-debug-EditUserAddressWidget-deletePanel']/button"));

		sendKeysToElement(By.xpath("(//td[div[@id='gwt-debug-EditUserAddressWidget-deletePanel']/button])[" + size
				+ "]/preceding::td[4]//input"), line1);

		sendKeysToElement(By.xpath("(//tr[td[div[@id='gwt-debug-EditUserAddressWidget-deletePanel']/button]])[" + size
				+ "]/following-sibling::tr[1]//input"), line2);

		sendKeysToElement(By.xpath("(//tr[td[div[@id='gwt-debug-EditUserAddressWidget-deletePanel']/button]])[" + size
				+ "]/following-sibling::tr[2]//input"), line3);

		sendKeysToElement(By.xpath("(//tr[td[div[@id='gwt-debug-EditUserAddressWidget-deletePanel']/button]])[" + size
				+ "]/following-sibling::tr[3]//input"), city);

		sendKeysToElement(By.xpath("(//tr[td[div[@id='gwt-debug-EditUserAddressWidget-deletePanel']/button]])[" + size
				+ "]/following-sibling::tr[4]//input"), state);

		sendKeysToElement(By.xpath("(//tr[td[div[@id='gwt-debug-EditUserAddressWidget-deletePanel']/button]])[" + size
				+ "]/following-sibling::tr[5]//input"), zip);

		selectFromDropdown(By.xpath("(//tr[td[div[@id='gwt-debug-EditUserAddressWidget-deletePanel']/button]])[" + size
				+ "]/following-sibling::tr[6]//select"), country);

		sendKeysToElement(By.xpath("(//td[div[@id='gwt-debug-EditUserAddressWidget-deletePanel']/button])[" + size
				+ "]/preceding::td[2]//input"), description);

		return this;
	}

	/**
	 * On the Company Settings Page, under the Company Information section,
	 * delete the i-th email address
	 * 
	 * @param i
	 *            delete the i-th address
	 * 
	 * @return {@link CompanySettingsPage}
	 */
	public CompanySettingsPage deleteAddressByNumber(String i) {

		clickElement(By.xpath(".//*[@id='gwt-debug-EditUesrAddressListWidget-contentPanel']/div[ " + i
				+ "]//*[@id='gwt-debug-EditUserDetailListWidget-deleteButton']"));

		return this;
	}

	/**
	 * On the Company Settings Page, under the Company Information section,
	 * delete all the address
	 * 
	 * @return {@link CompanySettingsPage}
	 */
	public CompanySettingsPage deleteAllAddresses() {

		int size = this.getSizeOfElements(By.xpath("//div[@id='gwt-debug-EditUserAddressWidget-deletePanel']/button"));

		System.out.println(size);

		for (int i = 0; i < size; i++) {

			clickElement(By.xpath("//div[@id='gwt-debug-EditUserAddressWidget-deletePanel']/button"));
		}

		return this;
	}

	/**
	 * On the Company Settings Page, under the Company Information section,
	 * click the Edit button to edit offices
	 * 
	 * @param description
	 * 
	 * @return {@link CompanySettingsPage}
	 */
	public CompanySettingsPage clickEditOfficesButton() {

		clickElementByKeyboard(By.id("gwt-debug-CompanySettingsView-editOfficeBtn"));

		return this;
	}

	/**
	 * On the Company Settings Page, under the Company Information section,
	 * after click the Edit button to edit offices, on the pop-up Advisor
	 * Company Office page, click ADD OFFICE button and add a new office with
	 * the given office name and the admin person
	 * 
	 * @param office
	 *            office name
	 * @param person
	 *            select a main admin/clerical person
	 * 
	 * @return {@link CompanySettingsPage}
	 */
	public CompanySettingsPage clickAddOfficeButton(String office, String person) {

		waitForElementVisible(By.id("gwt-debug-AdvisorCompanyOfficeEdit-addOfficeBtn"), 10);
		clickElementByKeyboard(By.id("gwt-debug-AdvisorCompanyOfficeEdit-addOfficeBtn"));
		sendKeysToElement(By.id("gwt-debug-AdvisorCompanyOfficeEdit-officeNameTextBox"), office);

		selectFromDropdown(By.id("gwt-debug-AdvisorCompanyOfficeEdit-mainClericalListBox"), person);

		clickElement(By.id("gwt-debug-AdvisorCompanyOfficeEdit-addOfficeBtn"));

		return this;
	}

	/**
	 * On the Company Settings Page, under the Company Information section,
	 * after click the Edit button to edit offices and click ADD OFFICE button,
	 * on the pop-up Add/Edit Office page, click the SAVE button
	 * 
	 * @return {@link CompanySettingsPage}
	 * 
	 * @throws InterruptedException
	 * 
	 */
	public CompanySettingsPage clickSaveOfficeButton() throws InterruptedException {

		waitForElementVisible(By.id("gwt-debug-AdvisorCompanyOfficeEdit-saveBtn"), 10);

		clickElement(By.id("gwt-debug-AdvisorCompanyOfficeEdit-saveBtn"));

		return new CompanySettingsPage(webDriver);
	}

	/**
	 * On the Company Settings Page, under the Company Information section,
	 * after click the Edit button to edit offices and click ADD OFFICE button,
	 * on the pop-up Add/Edit Office page, click the CANCEL button
	 * 
	 * @return {@link CompanySettingsPage}
	 * @throws InterruptedException
	 * 
	 */
	public CompanySettingsPage clickCloseOfficeButton() throws InterruptedException {

		// wait(3);

		waitForElementVisible(By.id("gwt-debug-AdvisorCompanyOfficeEdit-closeBtn"), 10);

		clickElementByKeyboard(By.id("gwt-debug-AdvisorCompanyOfficeEdit-closeBtn"));

		return new CompanySettingsPage(webDriver);
	}

	/**
	 * On the Company Settings Page, under the Company Information section,
	 * after click the Edit button to edit offices, on the pop-up Advisor
	 * Company Office page, click the red-white minus icon to delete the office
	 * with the given name
	 * 
	 * @param office
	 *            the name of the office
	 * 
	 * @return {@link CompanySettingsPage}
	 */
	public CompanySettingsPage clickDeleteOfficeIcon(String office) {

		clickElement(By.xpath("//td[.='" + office + "']/following-sibling::td[2]/button[@title='Delete']"));

		return this;
	}

	/**
	 * On the Company Settings Page, under the Company Information section,
	 * click the Edit button to edit offices, on the pop-up Advisor Company
	 * Office page, click CLOSE button
	 * 
	 * @return {@link CompanySettingsPage}
	 */
	public CompanySettingsPage clickCloseAdvisorCompanyOfficeButton() {

		clickElement(By.id("gwt-debug-AdvisorCompanyOfficeEdit-closeBtn"));

		return this;
	}

	/**
	 * On the Company Settings Page, under the Company Information section, edit
	 * the Facebook Page
	 * 
	 * @param facebook
	 * 
	 * @return {@link CompanySettingsPage}
	 */
	public CompanySettingsPage editFacebookPage(String facebook) {

		sendKeysToElement(By.xpath(".//input[@id='gwt-debug-CompanySettingsView-facebookPage']"), facebook);

		return this;
	}

	/**
	 * On the Company Settings Page, under the Company Information section, edit
	 * the Twitter
	 * 
	 * @param twitter
	 * 
	 * @return {@link CompanySettingsPage}
	 */
	public CompanySettingsPage editTwitterPage(String twitter) {

		sendKeysToElement(By.xpath(".//input[@id='gwt-debug-CompanySettingsView-twitter']"), twitter);

		return this;
	}

	/**
	 * On the Company Settings Page, under the Company Information section, edit
	 * the LinkedIn
	 * 
	 * @param linkedIn
	 * 
	 * @return {@link CompanySettingsPage}
	 */
	public CompanySettingsPage editLinkedInPage(String linkedIn) {

		sendKeysToElement(By.xpath(".//input[@id='gwt-debug-CompanySettingsView-linkedIn']"), linkedIn);

		return this;
	}

	/**
	 * On the Company Settings Page, under the Appearance section, edit the
	 * Redirect URL(log out)
	 * 
	 * @param url
	 *            the url that the page will be redirected to when log out
	 * 
	 * @return {@link CompanySettingsPage}
	 */
	public CompanySettingsPage editRedirectURL(String url) {
		log("URL :" + url);
		WebElement elem = webDriver.findElement(By.id("gwt-debug-CompanySettingsView-redirectURLTextBox"));
		elem.clear();
		elem.sendKeys(url);
		// sendKeysToElement(
		// By.id("gwt-debug-CompanySettingsView-redirectURLTextBox"), url);

		return this;
	}

	/**
	 * On the Company Settings Page, under the Appearance section, edit the
	 * Document List that is located beside the "Edit"
	 * 
	 * @param documentList
	 *            the list including Disclaimers, News, Login, Login Sidebar,
	 *            Terms Of Use
	 * 
	 * @return {@link CompanySettingsPage}
	 */

	public CompanySettingsPage editDocumentList(String documentList) {

		selectFromDropdown(By.id("gwt-debug-CompanySettingsView-documentList"), documentList);

		return this;
	}

	/**
	 * On the Company Settings Page, under the Appearance section, edit the
	 * Document edit area
	 * 
	 * @param documentEdit
	 *            the text of the document
	 * 
	 * @return {@link CompanySettingsPage}
	 */
	public CompanySettingsPage editDocumentEdit(String documentEdit) {

		sendKeysToElement(By.id("gwt-debug-CompanySettingsView-documentEditArea"), documentEdit);

		return this;
	}

	/**
	 * On the Company Settings Page, under the Email section, edit the Document
	 * check the checkbox of the Master Email Switch
	 * 
	 * @param trueORfalse
	 *            if true, allow email to be sent; if false not allow email to
	 *            be sent
	 * 
	 * @return {@link CompanySettingsPage}
	 * @throws InterruptedException
	 */
	public CompanySettingsPage checkMasterEmailSwitch(boolean trueORfalse) throws InterruptedException {

		// enable email switch
		WebElement emailCheckMasterBox = waitGet(By.id("gwt-debug-CompanySettingsView-emailSwitchCheckBox-input"));
		/*
		 * WebElement emailCheckMasterBox = waitGet(By
		 * .id("gwt-debug-AdvisorAdminSettingsView-emailSwitchCheckBox-input"));
		 */
		setCheckboxStatus2(emailCheckMasterBox, trueORfalse);

		return this;
	}

	/**
	 * On the Company Settings Page, under the Email section, click the Edit
	 * button beside the Default Email Templates
	 * 
	 * @return {@link CompanySettingsPage}
	 * @throws InterruptedException
	 */
	public CompanySettingsPage clickEditDefaultEmailTemplateButton() throws InterruptedException {

		wait(Settings.WAIT_SECONDS);
		/*
		 * clickElement(By
		 * .id("gwt-debug-AdvisorAdminSettingsView-editEmailTemplates"));
		 */
		scrollToTop();
		WebElement elem = webDriver.findElement(By.id("gwt-debug-CompanySettingsView-editEmailTemplates"));
		elem.sendKeys("", Keys.RETURN);
		return this;
	}

	/**
	 * On the Company Settings Page, under the Email section, after click the
	 * Edit button beside the Default Email Templates, on the pop-up Default
	 * Email Templates Settings page, add email function and template
	 * 
	 * @param emailFunction
	 *            the email function
	 * @param emailTemplate
	 *            the email template
	 * @return {@link CompanySettingsPage}
	 * @throws InterruptedException
	 */
	public CompanySettingsPage addEmailFunctionAndTemplate(String emailFunction, String emailTemplate)
			throws InterruptedException {
		scrollToTop();
		if (pageContainsStr("No item has been set yet, please create one.")
				|| !this.checkDropdownOptionsContainText(By.id("gwt-debug-MapSelectorView-keyListBox"),
						"User Invitation Template")) {
			this.waitForElementVisible(By.id("gwt-debug-MapSelectorView-addButton"), Settings.WAIT_SECONDS);
			this.clickElementAndWait3(By.id("gwt-debug-MapSelectorView-addButton"));
			// wait(Settings.WAIT_SECONDS);
			this.selectFromDropdownByValue(By.id("gwt-debug-MapSelectorView-keyListBox"), "2");
			this.waitForElementVisible(By.id("gwt-debug-MapSelectorView-valueListBox"), Settings.WAIT_SECONDS);
			this.selectFromDropdown(By.id("gwt-debug-MapSelectorView-valueListBox"), emailTemplate);
		}

		return this;
	}

	/**
	 * On the Company Settings Page, under the Email section, after click the
	 * Edit button beside the Default Email Templates, on the pop-up Default
	 * Email Templates Settings page, remove all the email function and email
	 * template
	 * 
	 * @return {@link CompanySettingsPage}
	 * @throws InterruptedException
	 */
	public CompanySettingsPage deleteAllEmailFunctionAndTemplate() throws InterruptedException {

		int size = getSizeOfElements(By.xpath(".//*[@id='gwt-debug-MapSelectorView-deleteButton']"));

		for (int i = 0; i < size; i++) {
			this.clickElementAndWait3(By.id("gwt-debug-MapSelectorView-deleteButton"));
			wait(3);
		}

		return this;
	}

	/**
	 * On the Company Settings Page, under the Email section, after click the
	 * Edit button beside the Default Email Templates, on the pop-up Default
	 * Email Templates Settings page, click the SAVE button
	 * 
	 * @return {@link CompanySettingsPage}
	 * @throws InterruptedException
	 *
	 */
	public CompanySettingsPage clickSaveEmailTemplateButton() throws InterruptedException {
		wait(Settings.WAIT_SECONDS);
		clickElement(By.id("gwt-debug-MapSelectorView-saveBtn"));

		return this;
	}

	/**
	 * On the Company Settings Page, under the Commissioning section, edit the
	 * Paypal Account
	 * 
	 * @param paypal
	 *            paypal account
	 * @return {@link CompanySettingsPage}
	 * 
	 */
	public CompanySettingsPage editPaypalAccount(String paypal) {

		sendKeysToElement(By.id("gwt-debug-CompanySettingsView-paypalAccount"), paypal);

		return this;
	}

	/**
	 * On the Company Settings Page, under the Security section, check the
	 * checkbox of Enable automatic Logout
	 * 
	 * @param enabled
	 *            if true, enable automatic logout; if false, disable automatic
	 *            logout
	 * @return {@link CompanySettingsPage}
	 * 
	 */
	public CompanySettingsPage checkEnableAutomaticLogout(boolean enabled) {

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(150, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath(".//*[@id='gwt-debug-CompanySettingsView-enableAutoLogoutAlertCheckBox-input']")));

		WebElement elem = webDriver
				.findElement(By.xpath(".//*[@id='gwt-debug-CompanySettingsView-enableAutoLogoutAlertCheckBox-input']"));

		setCheckboxStatus2(elem, enabled);

		return this;
	}

	/**
	 * On the Company Settings Page, under the Security section, if enable the
	 * automatic logout, edit the Logout Timer Setting - User role
	 * 
	 * @param user
	 *            the user role including Advisor Consultant, Advisor Clerical,
	 *            Advisor Finance, Advisor Admin, Investor
	 * @return {@link CompanySettingsPage}
	 * 
	 */
	public CompanySettingsPage editLogoutTimerUserRole(String user) {

		selectFromDropdown(By.id("gwt-debug-CompanySettingsView-logoutTimerUserRole"), user);
		return this;
	}

	/**
	 * On the Company Settings Page, under the Security section, if enable the
	 * automatic logout, edit the Logout Timer Setting - Timer value
	 * 
	 * @param value
	 *            the number of minutes
	 * @return {@link CompanySettingsPage}
	 * 
	 */
	public CompanySettingsPage editLogoutTimerValue(String value) {

		sendKeysToElement(By.id("gwt-debug-CompanySettingsView-logoutTimerValue"), value);

		return this;
	}

	/**
	 * On the Company Settings Page, click the SUBMIT button; the page will be
	 * navigated to the {@link AccountOverviewPage}
	 * 
	 * @return {@link AccountOverviewPage}
	 * 
	 * @throws InterruptedException
	 * 
	 */
	public AccountOverviewPage clickSubmitButton() throws InterruptedException {

		waitForElementVisible(By.id("gwt-debug-CompanySettingsView-submitBtn"), 10);
		clickElement(By.id("gwt-debug-CompanySettingsView-submitBtn"));

		wait(2 * Settings.WAIT_SECONDS);
		return new AccountOverviewPage(webDriver);
	}

}
