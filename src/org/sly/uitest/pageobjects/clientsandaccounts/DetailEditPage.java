package org.sly.uitest.pageobjects.clientsandaccounts;

import java.lang.reflect.InvocationTargetException;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.pageobjects.companyadmin.EmployeePage;
import org.sly.uitest.settings.Settings;

/**
 * This class represents the edit page for the account, the client, or the
 * related party
 * 
 * @author Lynne Huang
 * @date : 13 Aug, 2015
 * @company Prive Financial
 */
public class DetailEditPage extends AbstractPage {

	private Class<?> theClass;

	/**
	 * @param webDriver
	 */
	public DetailEditPage(WebDriver webDriver, Class<?> theClass) {

		super();
		this.webDriver = webDriver;
		this.theClass = theClass;

	}

	/**
	 * Add the main advisor for the account or the client from the list box
	 * 
	 * @param advisorName
	 *            the name of the main advisor
	 * @return Edit Page
	 */
	public DetailEditPage editAddMainAdvisorByName(String advisorName) throws InterruptedException {
		log(advisorName);
		clickElement(By.xpath("//*[@id='gwt-debug-UserDetailWidget-advisorPanel']//img"));
		try {
			waitForElementVisible(By.id("gwt-debug-PairedListBoxSelector-targetList"), 150);
			this.editIncludeInActive(true);
			clickElement(By.xpath(".//select[@id='gwt-debug-PairedListBoxSelector-sourceList']/option[contains(text(),'"
					+ advisorName + "')]"));
			// selectFromDropdown(
			// By.id("gwt-debug-PairedListBoxSelector-targetList"),
			// advisorName);
			this.waitForElementVisible(By.id("gwt-debug-PairedListBoxSelector-removeImg"), Settings.WAIT_SECONDS);

			clickElement(By.id("gwt-debug-PairedListBoxSelector-removeImg"));
		} catch (Exception ex) {
		}

		// wait(Settings.WAIT_SECONDS);
		waitForElementVisible(By.id("gwt-debug-PairedListBoxSelector-sourceList"), 10);
		this.selectFromDropdown(By.id("gwt-debug-PairedListBoxSelector-sourceList"), advisorName);

		// selectFromDropdown(By.id("gwt-debug-PairedListBoxSelector-sourceList"),
		// advisorName);
		// wait(Settings.WAIT_SECONDS);
		clickElement(By.id("gwt-debug-PairedListBoxSelector-addImg"));

		clickElement(By.id("gwt-debug-SelectionUi-confirmButton"));

		return this;

	}

	/**
	 * Remove the main advisor for the account or the client from the list box
	 * 
	 * @param advisorName
	 *            the name of the main advisor. If advisorName == "All", all the
	 *            advisors would be removed; otherwise, only remove the given
	 *            advisor
	 * @return Edit Page
	 */
	public DetailEditPage editRemoveMainAdvisorByName(String advisorName) throws InterruptedException {
		this.waitForElementVisible(By.xpath("//td[.=' Main Advisor ']/following-sibling::td//img"),
				Settings.WAIT_SECONDS);
		// clickElement(By
		// .xpath("//td[.=' Main Advisor
		// ']/following-sibling::td[1]/table/tbody/tr/td/img"));
		clickElement(By.xpath("//td[.=' Main Advisor ']/following-sibling::td//img"));

		if (advisorName.equals("all")) {
			try {
				waitForElementVisible(By.xpath(".//*[@id='gwt-debug-PairedListBoxSelector-targetList']/option"), 10);
			} catch (TimeoutException e) {

			}
			int size = getSizeOfElements(By.xpath(".//*[@id='gwt-debug-PairedListBoxSelector-targetList']/option"));
			log(String.valueOf(size));
			for (int i = 0; i < size; i++) {
				this.waitForElementVisible(By.xpath(".//*[@id='gwt-debug-PairedListBoxSelector-targetList']/option"),
						Settings.WAIT_SECONDS);
				clickElement(By.xpath(".//*[@id='gwt-debug-PairedListBoxSelector-targetList']/option"));
				wait(Settings.WAIT_SECONDS);
				clickElement(By.id("gwt-debug-PairedListBoxSelector-removeImg"));
				System.out.println("i=" + i);
			}

		} else {
			selectFromDropdown(By.id("gwt-debug-PairedListBoxSelector-targetList"), advisorName);

			clickElement(By.id("gwt-debug-PairedListBoxSelector-removeImg"));
		}

		clickElement(By.id("gwt-debug-SelectionUi-confirmButton"));

		return this;

	}

	/**
	 * 
	 * Add the main admin for the account or the client from list box
	 * 
	 * @param adminName
	 *            the name of the main admin
	 * @return Edit Page
	 */
	public DetailEditPage editAddMainAdminByName(String adminName) throws InterruptedException {
		clickElement(By.xpath("//td[.=' Main Admin ']/following-sibling::td[1]/div/img"));

		waitForElementVisible(By.id("gwt-debug-PairedListBoxSelector-sourceList"), 10);

		// wait for refresh.
		wait(Settings.WAIT_SECONDS);

		this.editIncludeInActive(true);

		selectFromDropdown(By.id("gwt-debug-PairedListBoxSelector-sourceList"), adminName);

		waitForElementVisible(By.id("gwt-debug-PairedListBoxSelector-addImg"), Settings.WAIT_SECONDS);
		clickElement(By.id("gwt-debug-PairedListBoxSelector-addImg"));

		clickElement(By.id("gwt-debug-SelectionUi-confirmButton"));

		return this;

	}

	/**
	 * Remove the main admin for the account or the client from the list box
	 * 
	 * @param adminName
	 *            the name of the main admin. If adminName == "All", all the
	 *            admins would be removed; otherwise, only remove the given
	 *            admin
	 * @return Edit Page
	 */
	public DetailEditPage editRemoveMainAdminByName(String adminName) throws InterruptedException {

		// clickElement(By.xpath("//td[.=' Main Admin
		// ']/following-sibling::td[1]/table/tbody/tr/td/img"));
		clickElement(By.xpath("//td[.=' Main Admin ']/following-sibling::td[1]/div/img"));
		waitForElementVisible(By.xpath("//*[@id='gwt-debug-PairedListBoxSelector-targetList']"), 30);
		if (adminName.equals("all")) {
			int size = 0;
			try {
				waitForElementVisible(By.xpath(".//*[@id='gwt-debug-PairedListBoxSelector-targetList']/option"), 10);
				size = getSizeOfElements(By.xpath(".//*[@id='gwt-debug-PairedListBoxSelector-targetList']/option"));
			} catch (Exception ex) {
				System.out.println("No option present in target list");
				ex.printStackTrace();
			}
			log("size: " + String.valueOf(size));
			wait(3);
			for (int i = 0; i < size; i++) {
				clickElement(By.xpath(".//*[@id='gwt-debug-PairedListBoxSelector-targetList']/option"));
				wait(1);
				clickElement(By.id("gwt-debug-PairedListBoxSelector-removeImg"));
			}

		} else {
			selectFromDropdown(By.id("gwt-debug-PairedListBoxSelector-targetList"), adminName);

			clickElement(By.id("gwt-debug-PairedListBoxSelector-removeImg"));
		}

		clickElement(By.id("gwt-debug-SelectionUi-confirmButton"));

		return this;

	}

	/**
	 * 
	 * @param accountName
	 *            the name of the account
	 * @return Edit Page
	 */
	public DetailEditPage editAccountName(String accountName) throws InterruptedException {

		sendKeysToElement(By.id("gwt-debug-InvestorAccountDetailWidget-accountName"), accountName);

		return this;

	}

	/**
	 * @param accountType
	 *            the type of the account could be selected from the following -
	 *            Brokerage, Fund Platform, Private Bank, Insurance, Pension
	 *            Scheme, Other
	 * @return Edit Page
	 */
	public DetailEditPage editAccountType(String accountType) throws InterruptedException {

		selectFromDropdown(By.id("gwt-debug-InvestorAccountDetailWidget-typeListBox"), accountType);

		wait(Settings.WAIT_SECONDS);

		return this;

	}

	/**
	 * @param feed
	 *            the data feed account ID of the account
	 * 
	 * @return {@link DetailEditPage}
	 */
	public DetailEditPage editAccountDataFeedAccountId(String feed) throws InterruptedException {

		sendKeysToElement(By.id("gwt-debug-InvestorAccountDetailWidget-brokerAccIdBox"), feed);

		return this;

	}

	/**
	 * @param productValue
	 *            the value of the account product
	 * @return Edit Page
	 */
	public DetailEditPage editAccountProductByValue(String productValue) throws InterruptedException {

		selectFromDropdownByValue(By.id("gwt-debug-InvestorAccountDetailWidget-platformListBox"), productValue);

		return this;

	}

	/**
	 * @param platform
	 *            the platform/product provider selected for the account
	 * @return Edit Page
	 */
	public DetailEditPage editAccountProductProvider(String platform) throws InterruptedException {

		selectFromDropdown(By.id("gwt-debug-InvestorAccountDetailWidget-providerListBox"), platform);

		wait(Settings.WAIT_SECONDS);

		return this;

	}

	/**
	 * @param product
	 *            the product selected for the account
	 * @return {@link DetailEditPage}
	 */
	public DetailEditPage editAccountProduct(String product) throws InterruptedException {

		selectFromDropdown(By.id("gwt-debug-InvestorAccountDetailWidget-platformListBox"), product);

		return this;

	}

	/**
	 * @param product
	 *            the product selected for the account
	 * @return {@link DetailEditPage}
	 */
	public DetailEditPage editAccountCurrency(String currency) throws InterruptedException {

		selectFromDropdown(By.id("gwt-debug-InvestorAccountDetailWidget-currencyListBox"), currency);

		return this;

	}

	/**
	 * @param source
	 *            the update source selected for the account
	 * @return Edit Page
	 */
	public DetailEditPage editAccountUpdateSource(String source) throws InterruptedException {

		selectFromDropdown(By.id("gwt-debug-InvestorAccountDetailWidget-updateSourceList"), source);

		return this;

	}

	public DetailEditPage editAccountBenchmark(String benchmark) throws InterruptedException {
		log("benchMark " + benchmark);

		waitForElementVisible(By.id("gwt-debug-InvestorAccountDetailWidget-benchmarkList"), 10);
		wait(5);
		selectFromDropdown(By.id("gwt-debug-InvestorAccountDetailWidget-benchmarkList"), benchmark);

		return this;
	}

	/**
	 * @param tagName
	 *            the name of the tag selected by the account or the client
	 * @return Edit Page
	 */
	public DetailEditPage editTagByTagname(String tagName) throws InterruptedException {
		this.waitForWaitingScreenNotVisible();
		if (isElementVisible(By.xpath(
				"//div[@id='gwt-debug-UserDetailWidget-tagListBox']/table/tbody/tr/td/button[@id='gwt-debug-MultiSelectDropDownBox-dropDown']/i"))) {
			clickElement(By.xpath(
					"//div[@id='gwt-debug-UserDetailWidget-tagListBox']/table/tbody/tr/td/button[@id='gwt-debug-MultiSelectDropDownBox-dropDown']/i"));
		} else {
			clickElement(By.xpath(
					"//div[@id='gwt-debug-InvestorAccountDetailWidget-tagListBox']/table/tbody/tr/td/button[@id='gwt-debug-MultiSelectDropDownBox-dropDown']/i"));
		}

		clickElement(By.xpath("//td[.='" + tagName + "']/div/span/input"));
		return this;

	}

	/**
	 * @param type
	 *            the client type that could be selected from the followings:
	 *            Individual, Corporate
	 * @return Edit Page
	 */
	public DetailEditPage editClientType(String type) throws InterruptedException {

		selectFromDropdown(By.id("gwt-debug-UserDetailWidget-clientTypeListBox"), type);

		return this;

	}

	/**
	 * @param category
	 *            the category of the client
	 * @return Edit Page
	 */
	public DetailEditPage editClientCategory(String category) throws InterruptedException {

		selectFromDropdown(By.id("gwt-debug-UserDetailWidget-clientCategoryListBox"), category);

		return this;

	}

	/**
	 * @param titleName
	 *            the title of the client: Mr., Mrs., Miss, Ms., Dr.
	 * @return Edit Page
	 */
	public DetailEditPage editClientTitle(String titleName) throws InterruptedException {

		selectFromDropdown(By.id("gwt-debug-UserDetailWidget-titleListBox"), titleName);

		return this;

	}

	/**
	 * @param firstName
	 *            the first name of the client
	 * @return Edit Page
	 */
	public DetailEditPage editClientFirstName(String firstName) throws InterruptedException {

		sendKeysToElement(By.id("gwt-debug-UserDetailWidget-firstNameTextBox"), firstName);

		return this;

	}

	/**
	 * @param lastName
	 *            the last name of the client
	 * @return Edit Page
	 */
	public DetailEditPage editClientLastName(String lastName) throws InterruptedException {

		sendKeysToElement(By.id("gwt-debug-UserDetailWidget-lastNameTextBox"), lastName);

		return this;

	}

	/**
	 * @param name
	 *            the company name of the client
	 * @return Edit Page
	 */
	public DetailEditPage editClientCompanyName(String name) throws InterruptedException {

		sendKeysToElement(By.id("gwt-debug-UserDetailWidget-nameTextBox"), name);

		return this;

	}

	/**
	 * @param countryName
	 *            the name of the country that the client comes from
	 * @return Edit Page
	 */
	public DetailEditPage editClientNationality(String countryName) throws InterruptedException {

		selectFromDropdown(By.id("gwt-debug-UserDetailWidget-nationalityListBox"), countryName);

		return this;

	}

	/**
	 * @param countryName
	 *            the name of the country that the client resides
	 * @return Edit Page
	 */
	public DetailEditPage editClientResidence(String countryName) throws InterruptedException {

		selectFromDropdown(By.id("gwt-debug-UserDetailWidget-CountryResidenceListBox"), countryName);

		return this;

	}

	/**
	 * @param number
	 *            the business reg number of the client
	 * @return Edit Page
	 */
	public DetailEditPage editClientBusinessRegNo(String number) throws InterruptedException {

		sendKeysToElement(By.id("gwt-debug-UserDetailWidget-idNumberTextBox"), number);

		return this;
	}

	// /**
	// * @param number
	// * the business reg number of the client
	// * @return Edit Page
	// * */
	// public DetailEditPage editClientBusinessRegNo(String number)
	// throws InterruptedException {
	//
	// sendKeysToElement(By.id("gwt-debug-UserDetailWidget-idNumberTextBox"),
	// number);
	//
	// return this;
	// }

	/**
	 * @param frequency
	 *            the review frequency of the client
	 * @return Edit Page
	 */
	public DetailEditPage editClientReviewFrequency(String frequency) throws InterruptedException {

		selectFromDropdown(By.id("gwt-debug-UserDetailWidget-reviewFrequencyListBox"), frequency);

		return this;
	}

	/**
	 * @param day
	 *            the day of the client birthday
	 * @return Edit Page
	 */
	public DetailEditPage editClientDayOfBirth(String day) throws InterruptedException {

		selectFromDropdown(By.id("gwt-debug-CompositeDatePicker-dayListBox"), day);

		return this;

	}

	/**
	 * @param month
	 *            the month of the client birthday
	 * @return Edit Page
	 */
	public DetailEditPage editClientMonthOfBirth(String month) throws InterruptedException {

		selectFromDropdown(By.id("gwt-debug-CompositeDatePicker-monthListBox"), month);

		return this;

	}

	/**
	 * @param year
	 *            the year of the client birthday
	 * @return Edit Page
	 */
	public DetailEditPage editClientYearOfBirth(String year) throws InterruptedException {

		sendKeysToElement(By.id("gwt-debug-CompositeDatePicker-yearTextBox"), year);

		return this;

	}

	/**
	 * @param idType
	 *            the ID type of the client, which could be one of the
	 *            followings: Birth Certificate, Business Registration Number,
	 *            Drivers License, International Drivers License, National
	 *            Identity Card, National Medical Insurance, Other, Passport
	 * @return Edit Page
	 */
	public DetailEditPage editClientIDType(String idType) throws InterruptedException {

		selectFromDropdown(By.id("gwt-debug-UserDetailWidget-idTypeListBox"), idType);

		return this;

	}

	/**
	 * @param idNumber
	 *            the number of the ID
	 * @return Edit Page
	 */
	public DetailEditPage editClientIDNumber(String idNumber) throws InterruptedException {

		sendKeysToElement(By.id("gwt-debug-UserDetailWidget-idNumberTextBox"), idNumber);

		return this;

	}

	/**
	 * @param employerName
	 *            the name of the client employer
	 * @return Edit Page
	 */
	public DetailEditPage editClientEmployerName(String employerName) throws InterruptedException {

		sendKeysToElement(By.id("gwt-debug-UserDetailWidget-companyNameTextBox"), employerName);

		return this;

	}

	/**
	 * @param occupation
	 *            the occupation of the client
	 * @return Edit Page
	 */
	public DetailEditPage editClientOccupation(String occupation) throws InterruptedException {

		sendKeysToElement(By.id("gwt-debug-UserDetailWidget-occupationTextBox"), occupation);

		return this;

	}

	/**
	 * @param position
	 *            the position of the client
	 * @return Edit Page
	 */
	public DetailEditPage editClientPosition(String position) throws InterruptedException {

		sendKeysToElement(By.id("gwt-debug-UserDetailWidget-positionTextBox"), position);

		return this;

	}

	/**
	 * @param edit
	 *            if true, the user name field is editable; if false, then not
	 * @param username
	 *            the username set for login
	 * @return {@link DetailEditPage}
	 * @throws InterruptedException
	 */
	public DetailEditPage editClientUserName(Boolean edit, String username) throws InterruptedException {

		waitForElementVisible(By.id("gwt-debug-UserDetailWidget-usernameEditChkBox-input"), 30);

		WebElement we = webDriver.findElement(By.xpath(
				".//*[@id='gwt-debug-UserDetailWidget-communicationPanel']//*[@id='gwt-debug-UserDetailWidget-usernameEditChkBox-input']"));

		if (edit) {

			setCheckboxStatus2(we, edit);
			clickYesButtonIfVisible();
			for (int i = 0; i < Settings.ATTEMPT_LOOPING_NUMBER; i++) {
				if (!edit.equals(we.isSelected())) {
					setCheckboxStatus2(we, edit);
					clickYesButtonIfVisible();
				}
			}

			clickYesButtonIfVisible();
			wait(2);

			try {
				sendKeysToElement(By.id("gwt-debug-UserDetailWidget-usernameTextBox"), username);
			} catch (InvalidElementStateException e) {

				for (int i = 0; i < Settings.ATTEMPT_LOOPING_NUMBER; i++) {
					try {
						if (isElementVisible(
								By.xpath(".//*[@id='gwt-debug-UserDetailWidget-usernameTextBox' and @disabled='']"))) {
							setCheckboxStatus2(we, edit);
							clickYesButtonIfVisible();
						}
					} catch (Exception ex) {
						throw ex;
					}
				}

				sendKeysToElement(By.id("gwt-debug-UserDetailWidget-usernameTextBox"), username);
			}

		} else {

			setCheckboxStatus2(we, edit);

		}

		return this;
	}

	/**
	 * @param edit
	 *            if true, the password field is editable; if false, then not
	 * @param username
	 *            the password set for login
	 * @return {@link DetailEditPage}
	 * @throws InterruptedException
	 */
	public DetailEditPage editClientPassword(Boolean edit, String password) throws InterruptedException {

		WebElement we = webDriver.findElement(By.xpath(
				".//*[@id='gwt-debug-UserDetailWidget-communicationPanel']//*[@id='gwt-debug-UserDetailWidget-passwordEditChkBox-input']"));

		if (edit) {

			setCheckboxStatus2(we, edit);

			clickYesButtonIfVisible();
			for (int i = 0; i < Settings.ATTEMPT_LOOPING_NUMBER; i++) {
				try {
					if (!edit.equals(we.isSelected())) {

						setCheckboxStatus2(we, edit);
						clickYesButtonIfVisible();
					}
				} catch (Exception e) {
					throw e;
				}
			}

			wait(3);

			try {
				sendKeysToElement(By.id("gwt-debug-UserDetailWidget-passwordTextBox"), password);
			} catch (InvalidElementStateException e) {
				for (int i = 0; i < Settings.ATTEMPT_LOOPING_NUMBER; i++) {
					try {
						if (isElementVisible(
								By.xpath(".//*[@id='gwt-debug-UserDetailWidget-passwordTextBox' and @disabled='']"))) {
							setCheckboxStatus2(we, edit);
							clickYesButtonIfVisible();
						}

					} catch (Exception ex) {
						throw ex;
					}
				}
				sendKeysToElement(By.id("gwt-debug-UserDetailWidget-passwordTextBox"), password);

			}
		} else {

			setCheckboxStatus2(we, edit);

		}

		return this;
	}

	/**
	 * @param change
	 *            if true, the client password has to be changed
	 */
	public DetailEditPage editClientChangePassword(Boolean change) {

		WebElement we = webDriver.findElement(By.id("gwt-debug-UserDetailWidget-mustChangePswChkBox-input"));

		setCheckboxStatus2(we, change);

		return this;
	}

	/**
	 * @param externalSystemID
	 *            the external system ID of the client
	 * @return Edit Page
	 */
	public DetailEditPage editClientExternalSystemID(String externalSystemID) throws InterruptedException {

		sendKeysToElement(By.id("gwt-debug-ExternalSystemReferenceWidget-systemIdTextBox"), externalSystemID);

		return this;

	}

	/**
	 * @param emailFunction
	 *            the email function could be either Enabled or Disabled
	 * @return Edit Page
	 */
	public DetailEditPage editClientEmailFunction(String emailFunction) throws InterruptedException {

		if (emailFunction.equals("Enabled")) {
			clickElement(By.id("gwt-debug-UserDetailWidget-clientLiveRadio-label"));
		} else if (emailFunction.equals("Disabled")) {
			clickElement(By.id("gwt-debug-UserDetailWidget-clientUnderReviewRadio-label"));
		}

		return this;

	}

	/**
	 * @param emailAddress
	 *            the email address of the client
	 * @param emailType
	 *            the type of the email: Home, Office, Other
	 * @return Edit Page
	 */
	public DetailEditPage addClientEmailByAddressAndType(String emailAddress, String emailType)
			throws InterruptedException {

		clickElement(By.id("gwt-debug-UserDetailWidget-addEmailButton"));

		int size = getSizeOfElements(By.xpath(".//*[@id='gwt-debug-EditUserDetailListWidget-deleteButton']"));

		sendKeysToElement(By.id("gwt-debug-EditUserEmailWidget-emailTextBox-" + size), emailAddress);

		selectFromDropdown(By.id("gwt-debug-EditUserEmailWidget-typeListBox-" + size), emailType);

		return this;

	}

	/**
	 * @param type
	 *            the type of the related party: Corporate, Individual, Other,
	 *            Trust
	 * @return {@link DetailEditPage}
	 */
	public DetailEditPage editRelatedPartyType(String type) {

		selectFromDropdown(By.id("gwt-debug-RelatedPartyEditWidget-typeListBox"), type);

		return this;
	}

	/**
	 * @param relationship
	 *            the relationship of the related party:Beneficiary, Life
	 *            Assured, Other, Previous Owner, Secondary Holder, Trustee
	 * @return {@link DetailEditPage}
	 */
	public DetailEditPage editRelatedPartyRelationship(String relationship) {

		selectFromDropdown(By.id("gwt-debug-RelatedPartyEditWidget-relationshipListBox"), relationship);

		return this;
	}

	/**
	 * @param title
	 *            the title of the related party: Dr., Miss, Mr., Mrs., Ms.
	 * @return {@link DetailEditPage}
	 */
	public DetailEditPage editRelatedPartyTitle(String title) {

		selectFromDropdown(By.id("gwt-debug-RelatedPartyEditWidget-titleListBox"), title);

		return this;
	}

	/**
	 * @param firstName
	 *            the first name of the related party
	 * @return {@link DetailEditPage}
	 */
	public DetailEditPage editRelatedPartyFirstName(String firstName) {

		sendKeysToElement(By.id("gwt-debug-RelatedPartyEditWidget-firstNameTextBox"), firstName);

		return this;
	}

	/**
	 * @param lastName
	 *            the last name of the related party
	 * @return {@link DetailEditPage}
	 */
	public DetailEditPage editRelatedPartyLastName(String lastName) {

		sendKeysToElement(By.id("gwt-debug-RelatedPartyEditWidget-lastNameTextBox"), lastName);

		return this;
	}

	/**
	 * @param gender
	 *            the gender of the related party: Female, Male, Other
	 * @return {@link DetailEditPage}
	 */
	public DetailEditPage editRelatedPartyGender(String gender) {

		selectFromDropdown(By.id("gwt-debug-RelatedPartyEditWidget-genderListBox"), gender);

		return this;
	}

	/**
	 * @param day
	 *            the day of the related party: from 1 to 31
	 * @return {@link DetailEditPage}
	 */
	public DetailEditPage editRelatedPartyDay(String day) {

		selectFromDropdown(By.id("gwt-debug-CompositeDatePicker-dayListBox"), day);

		return this;
	}

	/**
	 * @param month
	 *            the month of the related party: from Jan to Dec
	 * @return {@link DetailEditPage}
	 */
	public DetailEditPage editRelatedPartyMonth(String month) {

		selectFromDropdown(By.id("gwt-debug-CompositeDatePicker-monthListBox"), month);

		return this;
	}

	/**
	 * @param idType
	 *            the ID type of the related party: Birth Certificate, Business
	 *            Registration Number, DriversLicense, International Drivers
	 *            License, National Identity Card, National Medical Insurance,
	 *            Other, Passport
	 * @return {@link DetailEditPage}
	 */
	public DetailEditPage editRelatedPartyIDType(String idType) {

		selectFromDropdown(By.id("gwt-debug-RelatedPartyEditWidget-idTypeListBox"), idType);

		return this;
	}

	/**
	 * @param idNumber
	 *            the ID number of the related party
	 * @return {@link DetailEditPage}
	 */
	public DetailEditPage editRelatedPartyidNumber(String idNumber) {

		sendKeysToElement(By.id("gwt-debug-RelatedPartyEditWidget-idTextBox"), idNumber);

		return this;
	}

	/**
	 * @param note
	 *            the note of the related party
	 * @return {@link DetailEditPage}
	 */
	public DetailEditPage editRelatedPartyNote(String note) {

		sendKeysToElement(By.id("gwt-debug-RelatedPartyEditWidget-noteTextArea"), note);

		return this;
	}

	/**
	 * Edit the investor report date range
	 *
	 * @param range
	 *
	 * @return {@link DetailEditPage}
	 */
	public DetailEditPage editInvestorReportDateRange(String range) {

		clickElement(By.xpath("//label[.='" + range + "']"));

		return this;
	}

	/**
	 * @param start
	 *            the start date of the investor report
	 * @param end
	 *            the end date of the investor report
	 * @return {@link DetailEditPage}
	 * @throws InterruptedException
	 */
	public DetailEditPage editInvestorReportDate(String start, String end) throws InterruptedException {
		// wait(5);
		this.waitForElementVisible(By.id("gwt-debug-DateRangePanel-startDateTextBox"), Settings.WAIT_SECONDS);
		WebElement ele1 = webDriver.findElement(By.id("gwt-debug-DateRangePanel-startDateTextBox"));
		ele1.sendKeys(Keys.RETURN);
		ele1.clear();
		ele1.sendKeys(start);
		// wait(5);
		this.waitForElementVisible(By.id("gwt-debug-DateRangePanel-endDateTextBox"), Settings.WAIT_SECONDS);
		WebElement ele2 = webDriver.findElement(By.id("gwt-debug-DateRangePanel-endDateTextBox"));
		ele2.sendKeys(Keys.RETURN);
		ele2.clear();
		ele2.sendKeys(end);
		// wait(5);

		/*
		 * sendKeysToElement(By.id("gwt-debug-DateRangePanel-startDateTextBox"),
		 * start);
		 * 
		 * sendKeysToElement(By.id("gwt-debug-DateRangePanel-endDateTextBox"),
		 * end); wait(5);
		 */

		return this;
	}

	/**
	 * Unlink the model portfolio from the account
	 * 
	 * @return {@link DetailEditPage}
	 * @throws InterruptedException
	 * 
	 */
	public DetailPage unlinkModelPortfolio() throws InterruptedException {

		waitForElementVisible(By.id("gwt-debug-ModelPortfolioWidget-modelUnLink"), 10);
		clickElementAndWait3(By.id("gwt-debug-ModelPortfolioWidget-modelUnLink"));

		return new DetailPage(webDriver);
	}

	/**
	 * click the SAVE button
	 * 
	 * @return return to the previous page
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("unchecked")
	public <T> T clickSaveButtonByLocator(By locator) throws Exception {

		clickElementByEnterKeyAndWait3(locator);

		return (T) this.theClass.getDeclaredConstructor(WebDriver.class).newInstance(webDriver);

	}

	/**
	 * Click the SAVE button when edit the client additional details
	 * 
	 * @return return to the previous page
	 */
	@SuppressWarnings("unchecked")
	public <T> T clickSaveButton_ClientAdditionalDetail() throws Exception {

		waitForElementVisible(By.id("gwt-debug-UserDetailWidget-saveBtn"), 10);
		wait(1);
		clickElement(By.id("gwt-debug-UserDetailWidget-saveBtn"));
		this.waitForWaitingScreenNotVisible();
		return (T) this.theClass.getDeclaredConstructor(WebDriver.class).newInstance(webDriver);

	}

	/**
	 * Click the SAVE button when edit the client additional details, AND the
	 * main advisor is changed
	 * 
	 * @return return to the previous page
	 */
	@SuppressWarnings("unchecked")
	public <T> T clickSaveButton_ClientAdditionalDetail_MainAdvisorChange(String apply, String access)
			throws Exception {

		clickElementAndWait3(By.id("gwt-debug-UserDetailWidget-saveBtn"));

		clickElement(By.xpath("//label[.='" + apply + "']"));

		clickElement(By.xpath("//label[.='" + access + "']"));

		clickElement(By.id("gwt-debug-MainAdvisorChangeView-okButton"));

		return (T) this.theClass.getDeclaredConstructor(WebDriver.class).newInstance(webDriver);

	}

	/**
	 * Click the SAVE button when edit the account details
	 * 
	 * @return return to the previous page
	 */
	@SuppressWarnings("unchecked")
	public <T> T clickSaveButton_AccountDetail() throws Exception {

		clickElementAndWait3(By.id("gwt-debug-InvestorAccountDetailWidget-saveBtn"));

		return (T) this.theClass.getDeclaredConstructor(WebDriver.class).newInstance(webDriver);

	}

	/**
	 * click the CANCEL button
	 * 
	 * @return return to the previous page
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("unchecked")
	public <T> T clickCancelButtonByLocator(By locator) throws Exception {

		clickElementAndWait3(locator);

		return (T) this.theClass.getDeclaredConstructor(WebDriver.class).newInstance(webDriver);

	}

	/**
	 * click the CANCEL button when edit the account detail
	 * 
	 * @return return to the previous page
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("unchecked")
	public <T> T clickCancelButton_AccountDetail() throws Exception {

		clickElementAndWait3(By.id("gwt-debug-InvestorAccountDetailWidget-cancelBtn"));

		return (T) this.theClass.getDeclaredConstructor(WebDriver.class).newInstance(webDriver);

	}

	/**
	 * @param locator
	 *            click the DELETE button with the given locator
	 * @return {@link AccountOverviewPage}
	 */
	public AccountOverviewPage clickDeleteButtonByLocator(By locator) throws InterruptedException {

		clickElement(locator);

		clickYesButtonIfVisible();

		clickYesButtonIfVisible();

		clickOkButtonIfVisible();

		return new AccountOverviewPage(webDriver);
	}

	/**
	 * 
	 * Click the DELETE button when edit the employee
	 * 
	 * @return {@link EmployeePage}
	 */
	public EmployeePage clickDeleteButton_EmployeePage() throws InterruptedException {

		clickElement(By.id("gwt-debug-UserDetailWidget-deleteBtn"));

		clickYesButtonIfVisible();

		clickYesButtonIfVisible();

		clickOkButtonIfVisible();

		return new EmployeePage(webDriver);
	}

	/**
	 * 
	 * Click the delete button when edit account
	 * 
	 * @return {@link AccountOverviewPage}
	 */
	public AccountOverviewPage clickDeleteButton_AccountDetail() throws InterruptedException {

		waitForElementVisible(By.id("gwt-debug-InvestorAccountDetailWidget-deleteBtn"), 10);

		clickElement(By.id("gwt-debug-InvestorAccountDetailWidget-deleteBtn"));

		clickYesButtonIfVisible();

		clickYesButtonIfVisible();

		clickOkButtonIfVisible();

		clickOkButtonIfVisible();

		return new AccountOverviewPage(webDriver);
	}

	/**
	 * Add ticker with the given country and symbol
	 * 
	 * @param country
	 *            the country of the ticker
	 * @param the
	 *            symbol of the ticker
	 * @return the previous page
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public <T> T addTicker(String country, String symbol) throws Exception {

		waitForElementVisible(By.id("gwt-debug-TickerAddDialog-addTickerExchangeList"), 150);
		selectFromDropdown(By.id("gwt-debug-TickerAddDialog-addTickerExchangeList"), country);

		sendKeysToElement(By.id("gwt-debug-TickerAddDialog-tickerSymbolBox"), symbol);

		clickElement(By.id("gwt-debug-TickerAddDialog-tickerAddButton"));
		clickOkButtonIfVisible();
		return (T) this.theClass.getDeclaredConstructor(WebDriver.class).newInstance(webDriver);
	}

	/**
	 * Add ticker with given country and security name in search tab
	 * 
	 * @param country
	 *            the country of the ticker
	 * @param symbol
	 *            symbol to search
	 * @return the previous page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public <T> T addTickerInSearchTab(String country, String symbol) throws Exception {

		waitForElementVisible(By.xpath(".//div[@class='TabLayoutPanelTabNormal' and .='Search']"), 10);

		clickElement(By.xpath(".//div[@class='TabLayoutPanelTabNormal' and .='Search']"));

		waitForElementVisible(By.id("gwt-debug-TickerAddDialog-addTickerExchangeSearchList"), 150);
		selectFromDropdown(By.id("gwt-debug-TickerAddDialog-addTickerExchangeSearchList"), country);

		waitForElementVisible(By.id("gwt-debug-TickerAddDialog-isinTextField"), 5);
		sendKeysToElement(By.id("gwt-debug-TickerAddDialog-isinTextField"), symbol);

		clickElement(By.id("gwt-debug-TickerAddDialog-tickerSearchButton"));

		waitForElementVisible(By.xpath(".//table[@class='tickerSearchResultTable']"), 10);

		clickElement(By.xpath(".//td[.='DE000A0KEYG6']/preceding-sibling::td[3]/span"));

		clickElement(By.id("gwt-debug-TickerAddDialog-tickerAddButton"));

		clickOkButtonIfVisible();

		return (T) this.theClass.getDeclaredConstructor(WebDriver.class).newInstance(webDriver);
	}

	public DetailEditPage editAddMainAdvisorByNameInvDet(String advisorName) throws InterruptedException {

		/*
		 * clickElement(By .xpath(
		 * "//table[@id='gwt-debug-InvestorAccountDetailWidget-advisorPanel']//img"
		 * ));
		 */
		clickElement(By.xpath(".//*[@id='gwt-debug-InvestorAccountDetailWidget-advisorPanel']//img"));
		try {
			selectFromDropdown(By.id("gwt-debug-PairedListBoxSelector-targetList"), advisorName);
			clickElement(By.id("gwt-debug-PairedListBoxSelector-removeImg"));
		} catch (Exception ex) {
		}
		wait(2 * Settings.WAIT_SECONDS);
		selectFromDropdown(By.id("gwt-debug-PairedListBoxSelector-sourceList"), advisorName);

		clickElement(By.id("gwt-debug-PairedListBoxSelector-addImg"));

		clickElement(By.id("gwt-debug-SelectionUi-confirmButton"));

		return this;

	}

	public DetailEditPage editIncludeInActive(boolean checked) {
		WebElement elem = webDriver.findElement(By.id("gwt-debug-SelectionUi-includeInactive-label"));
		this.setCheckboxStatus2(elem, checked);
		return this;
	}
}
