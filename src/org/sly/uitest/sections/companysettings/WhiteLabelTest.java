package org.sly.uitest.sections.companysettings;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.abstractpage.MenuBarPage;
import org.sly.uitest.pageobjects.alerts.AlertsPage;
import org.sly.uitest.pageobjects.clientsandaccounts.AccountOverviewPage;
import org.sly.uitest.pageobjects.clientsandaccounts.AnalysisPage;
import org.sly.uitest.pageobjects.clientsandaccounts.CRMPage;
import org.sly.uitest.pageobjects.clientsandaccounts.CashflowPage;
import org.sly.uitest.pageobjects.clientsandaccounts.ComparePage;
import org.sly.uitest.pageobjects.clientsandaccounts.DetailPage;
import org.sly.uitest.pageobjects.clientsandaccounts.HistoryPage;
import org.sly.uitest.pageobjects.clientsandaccounts.HoldingsPage;
import org.sly.uitest.pageobjects.commissioning.NewBusinessEventPage;
import org.sly.uitest.pageobjects.companyadmin.CompanySettingsPage;
import org.sly.uitest.pageobjects.mysettings.UserPreferencePage;
import org.sly.uitest.pageobjects.mysettings.UserProfilePage;
import org.sly.uitest.sections.accounts.AccountOverviewAndSearchTest;
import org.sly.uitest.settings.Settings;

/**
 * Test Company name, Logo, brand,link...etc
 * 
 * @author Nandi Ouyang
 * @date : Sep 24, 2014
 * @company Prive Financial
 */

public class WhiteLabelTest extends AbstractTest {

	@Test
	public void testLogoutRedirect() throws Exception {
		setScreenshotSuffix(AccountOverviewAndSearchTest.class.getSimpleName());

		LoginPage main = new LoginPage(webDriver);

		String random = this.getRandName();

		String redirectUrl = "www.google.com";

		CompanySettingsPage companySettingPage = main.loginAs(Settings.USERNAME, Settings.PASSWORD)
				.goToCompanySettingsPage();
		companySettingPage.editRedirectURL(redirectUrl);

		// wait(3);
		log("New URL: " + getTextByLocator(By.id("gwt-debug-CompanySettingsView-redirectURLTextBox")));

		assertTrue(redirectUrl.equals(getInputByLocator(By.id("gwt-debug-CompanySettingsView-redirectURLTextBox"))));

		companySettingPage.clickSubmitButton().goToCompanySettingsPage();

		log("New URL: " + getTextByLocator(By.id("gwt-debug-CompanySettingsView-redirectURLTextBox")));

		assertEquals(redirectUrl, getInputByLocator(By.id("gwt-debug-CompanySettingsView-redirectURLTextBox")));

		this.checkLogout();
		this.handleAlert();

		// wait for google.com
		wait(Settings.WAIT_SECONDS * 2);

		String url = webDriver.getCurrentUrl();

		assertTrue(url.contains("google"));

		LoginPage main2 = new LoginPage(webDriver);
		main2.loginAs(Settings.USERNAME, Settings.PASSWORD).goToCompanySettingsPage().editRedirectURL("")
				.clickSubmitButton();

		this.checkLogout();
		this.handleAlert();
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 30);

		url = webDriver.getCurrentUrl();

		System.out.println(url);

		// assertTrue(url.contains(baseUrl + "?logout="));
		assertTrue(url.contains("#login"));
	}

	@Test
	public void testCompanySetting() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		String random = this.getRandName();

		String description = "TestDesc" + random;

		String email = random + "@example.com";

		String tel1 = "0086" + random;
		String tel2 = "567890" + random;

		String address1 = "TestAddress1" + random;
		String address2 = "TestAddress2" + random;

		String city = "TestCity" + random;
		String country = "Benin";

		String faceBookPage = "www.facebook.com" + random;

		String twitterPage = "twitter.com" + random;

		String linkedIn = "www.linkedin.com" + random;

		String paypalAccount = "test" + random;

		String documentList = "Login";

		String documentEdit = "testEdit" + random;

		// test case
		main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToCompanySettingsPage().editDescription(description)
				.deleteAllEmails().deleteAllTels().deleteAllAddresses().addEmailWithAddress(email, "new email")
				.addTelWithNumber("852", tel1, "tel1").addTelWithNumber("86", tel2, "tel2")
				.selectDefaultTelByNumber("2").addAddress(address1, address2, "", city, "", "", country, "new address");

		CompanySettingsPage companySettingPage = new CompanySettingsPage(webDriver);
		companySettingPage.editPaypalAccount(paypalAccount).editDocumentEdit(documentEdit)
				.editDocumentList(documentList)
				// .editTwitterPage(twitterPage).editFacebookPage(faceBookPage)
				// .editLinkedInPage(linkedIn)
				.clickSubmitButton();

		main.goToCompanySettingsPage();

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(300, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gwt-debug-CompanySettingsView-twitter")));

		assertTrue(description.equals(getInputByLocator(By.id("gwt-debug-CompanySettingsView-companyDesc"))));
		assertTrue(email.equals(getInputByLocator(By.id("gwt-debug-EditUserEmailWidget-emailTextBox-1"))));
		assertTrue(tel1.equals(getInputByLocator(By.id("gwt-debug-EditUserPhoneWidget-numberTextBox-1"))));
		assertTrue(tel2.equals(getInputByLocator(By.id("gwt-debug-EditUserPhoneWidget-numberTextBox-2"))));
		assertTrue(address1.equals(getInputByLocator(By.id("gwt-debug-EditUserAddressWidget-address1TextBox-1"))));
		assertTrue(address2.equals(getInputByLocator(By.id("gwt-debug-EditUserAddressWidget-address2TextBox-1"))));
		assertTrue(city.equals(getInputByLocator(By.id("gwt-debug-EditUserAddressWidget-cityTextBox-1"))));
		assertTrue(
				country.equals(getSelectedTextFromDropDown(By.id("gwt-debug-EditUserAddressWidget-countryListBox-1"))));
		// wait(3);

		// assertEquals(twitterPage,
		// getInputByLocator(By
		// .id("gwt-debug-CompanySettingsView-twitter")));
		// assertTrue(faceBookPage.equals(getInputByLocator(By
		// .id("gwt-debug-CompanySettingsView-facebookPage"))));
		// assertTrue(linkedIn.equals(getInputByLocator(By
		// .id("gwt-debug-CompanySettingsView-linkedIn"))));
		assertTrue(paypalAccount.equals(getInputByLocator(By.id("gwt-debug-CompanySettingsView-paypalAccount"))));

	}

	@Test
	public void testCompanySettingAutomaticLogout() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		AccountOverviewPage security = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToCompanySettingsPage()
				.checkEnableAutomaticLogout(true).editLogoutTimerUserRole("Advisor Consultant")
				.editLogoutTimerValue("1").editLogoutTimerUserRole("Advisor Clerical").editLogoutTimerValue("1")
				.editLogoutTimerUserRole("Advisor Finance").editLogoutTimerValue("1")
				.editLogoutTimerUserRole("Advisor Admin").editLogoutTimerValue("1").editLogoutTimerUserRole("Investor")
				.clickSubmitButton();

		for (int i = 0; i < 10; i++) {
			// wait(10);
			try {
				this.handleAlert();
				waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 30);
				break;
			} catch (Exception e) {
			}

		}

		security.goToCompanySettingsPage().checkEnableAutomaticLogout(false).clickSubmitButton();

	}

	@Test
	public void testUserPreference() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		NewBusinessEventPage editPreference = main.loginAs(Settings.USERNAME, Settings.USERNAME)
				.goToUserPreferencePage(NewBusinessEventPage.class).editLandingPageByName("New Business")
				.editAccountDetailDefaultTab("Details").editLoadAccountAndClients(true).editShowGraph("yes")
				.clickSubmitButton();

		String url = webDriver.getCurrentUrl();

		assertTrue(url.indexOf("businesseventlist") > 0);

		// comment this line,because it tests language setting. Please uncomment
		// this line after
		// you can make sure the system support different language
		// assertTrue(pageContainsStr("Kunden"));

		AccountOverviewPage overview = editPreference.goToAccountOverviewPage();

		assertTrue(!overview.chartPanelIsNotDisplayed());

		DetailPage editPreference2 = overview.simpleSearchByString("Selenium")
				.goToAccountDetailPageByName("Selenium Test");

		url = webDriver.getCurrentUrl();

		assertTrue(url.indexOf("portfolioOverviewDetails") > 0);

		// change back
		AccountOverviewPage overview2 = editPreference2.goToUserPreferencePage(AccountOverviewPage.class)
				.editLandingPageByName("Summary of Accounts").editAccountDetailDefaultTab("Holdings")
				.editLanguage("English").editLoadAccountAndClients(true).editShowGraph("no").clickSubmitButton();

		// check no highchart
		assertTrue(overview2.chartPanelIsNotDisplayed());

	}

	@Test
	public void testDefaultTabSettingForAccountOverviewPage() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		main.loginAs(Settings.USERNAME, Settings.PASSWORD);

		String accountName = "Selenium Test";

		String clientName = "Investor, Selenium";

		setDefaultAndTestDefaultTab("History", "Transactions", accountName, clientName, HistoryPage.class);

		setDefaultAndTestDefaultTab("Cashflow", "Cashflow", accountName, clientName, CashflowPage.class);

		setDefaultAndTestDefaultTab("Holdings", "Holdings", accountName, clientName, HoldingsPage.class);

		setDefaultAndTestDefaultTab("Analysis", "Analysis", accountName, clientName, AnalysisPage.class);

		setDefaultAndTestDefaultTab("Details", "Details", accountName, clientName, DetailPage.class);

		setDefaultAndTestDefaultTab("CRM", "CRM", accountName, clientName, CRMPage.class);

		setDefaultAndTestDefaultTab("Alerts", "Alerts", accountName, clientName, AlertsPage.class);

		setDefaultAndTestDefaultTab("Compare", "Compare", "Generali Vision - TWRR", clientName, ComparePage.class);
		// wait(Settings.WAIT_SECONDS);
		setDefaultTab("Holdings");

	}

	private void setDefaultAndTestDefaultTab(String tabNameInSetting, String tabNameInAccountOverview,
			String accountName, String clientName, Class<?> returnClass) throws Exception {
		wait(Settings.WAIT_SECONDS);
		setDefaultTab(tabNameInSetting);

		// from overview -> account
		AccountOverviewPage overview = new AccountOverviewPage(webDriver);

		overview.goToAccountOverviewPage().simpleSearchByString(accountName).goToAccountDefaultPageByName(accountName,
				returnClass);

		// to see if there are any hidden tabs
		if (tabNameInAccountOverview.equals("Transactions") || tabNameInAccountOverview.equals("Cashflow")) {
			clickElement(By.xpath("//*[.='History']"));
		}

		WebElement element = waitGet(By.xpath("//a[@id='gwt-debug-PortfolioOverviewTab-hyperlink' and .='"
				+ tabNameInAccountOverview + "']/parent::div/parent::div"));

		String className = element.getAttribute("class");

		assertTrue(className.contains("portfolioOverviewTabActive"));

		// from overview -> client -> account
		MenuBarPage newPage = new MenuBarPage(webDriver);

		newPage.goToAccountOverviewPage().simpleSearchByString(accountName).goToClientDetailPageByName(clientName)
				.goToAccountsTab().goToAccountDefaultPageByName(accountName, returnClass);

		// to see if there are any hidden tabs
		if (tabNameInAccountOverview.equals("Transactions") || tabNameInAccountOverview.equals("Cashflow")) {
			clickElement(By.xpath("//*[.='History']"));
		}

		WebElement element2 = waitGet(By.xpath("//a[@id='gwt-debug-PortfolioOverviewTab-hyperlink' and .='"
				+ tabNameInAccountOverview + "']/parent::div/parent::div"));

		String className2 = element2.getAttribute("class");

		assertTrue(className2.contains("portfolioOverviewTabActive"));

	}

	@Test
	public void testDefaultTabForBuildModelPortfolioPage() throws Exception {

		LoginPage main = new LoginPage(webDriver);
		String modelName = "Sample Model Portfolio";

		main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToModelPortfoliosPage().clickManageModelPortfolios()
				.goToModelPortfolio(modelName);

		// String modelName = "Model Portfolio 884546735";
		setDefaultTab("Holdings");
		testTabNameForModelPortfolioPage(modelName);

		setDefaultTab("Analysis");
		testTabNameForModelPortfolioPage(modelName);

		setDefaultTab("History");
		testTabNameForModelPortfolioPage(modelName);

		setDefaultTab("Compare");
		testTabNameForModelPortfolioPage(modelName);

		setDefaultTab("Holdings");
	}

	private void testTabNameForModelPortfolioPage(String modelName) throws Exception {

		AccountOverviewPage portfolio = new AccountOverviewPage(webDriver);

		portfolio.goToModelPortfoliosPage().clickManageModelPortfolios().goToModelPortfolio(modelName);

		WebElement element = waitGet(By
				.xpath("//a[@id='gwt-debug-PortfolioOverviewTab-hyperlink' and .='Holdings']/parent::div/parent::div"));

		String className = element.getAttribute("class");

		assertTrue(className.contains("portfolioOverviewTabActive"));
	}

	public void setDefaultTab(String tabName) throws Exception {
		scrollToTop();

		AccountOverviewPage accountOverviewPage = new MenuBarPage(webDriver).goToAccountOverviewPage();

		UserPreferencePage userPreferencePage = accountOverviewPage.goToUserPreferencePage(UserPreferencePage.class)
				.editLandingPageByName("Summary of Accounts");

		// special handling for CashFlow tab
		if (tabName.equals("Cashflow")) {
			userPreferencePage.editAccountDetailDefaultTab("CashFlow");
		} else {
			userPreferencePage.editAccountDetailDefaultTab(tabName);
		}

		clickElement(By.id("gwt-debug-UpdateUserSystemPreferenceView-submitButton"));
		wait(3);

	}

	// please do this test before check the web system supports different
	// language
	@Test
	public void testLanguageSetting() throws Exception {

		setScreenshotSuffix(AccountOverviewAndSearchTest.class.getSimpleName());

		webDriver.get(loginUrl);

		if (webDriver.getCurrentUrl().indexOf("MATERIAL") > -1) {

			waitForElementVisible(By.xpath(".//*[@id='page-wrapper']/div[1]/nav/div/a/i"), 30);

			clickElement(By.xpath(".//*[@title='Language']"));

			clickElement(By.xpath(".//*[.='Deutsch' and @class='gwt-Anchor']"));

			waitForElementVisible(By.xpath(".//*[@id='page-wrapper']/div[1]/nav/div/a/i"), 30);

			// this.refreshPage();
			assertTrue(pageContainsStr("Anmelden"));

		} else {

			waitForElementVisible(By.id("gwt-debug-LoginPanel-loginButton"), 30);

			waitForElementVisible(By.xpath("//select[@class='formTextBox']"), 30);

			WebElement dropBox = webDriver.findElement(By.xpath("//select[@class='formTextBox']"));

			Select select = new Select(dropBox);
			select.selectByVisibleText("Deutsch");

			// wait(5);

			this.refreshPage();

			assertEquals("Anmelden", getTextByLocator(By.id("gwt-debug-LoginPanel-loginButton")));
		}

	}

	@Test
	public void testPasswordChange() throws Exception {
		//
		LoginPage main = new LoginPage(webDriver);

		String oldPassword = Settings.INVESTOR_PASSWORD;
		String newPassword = "SeleniumTest2";
		main.loginAs(Settings.INVESTOR_USERNAME, Settings.INVESTOR_PASSWORD).goToUserProfilePage()
				.editOldPassword(oldPassword).editNewPassword(newPassword).editConfirmPassword(newPassword)
				.clickUpdatePassword();

		assertTrue(pageContainsStr("Password updated successfully!"));
		clickElement(By.id("gwt-debug-CustomDialog-okButton"));

		checkLogout();

		LoginPage main2 = new LoginPage(webDriver);

		main2.loginIncorrectlyAs(Settings.INVESTOR_USERNAME, oldPassword);
		assertFalse(isElementVisible(By.xpath(".//span[contains(text(),'Welcome')]")));

		MenuBarPage menu = main2.loginAs(Settings.INVESTOR_USERNAME, newPassword);
		waitForElementVisible(By.xpath(".//span[contains(text(),'Welcome')]"), 60);
		assertTrue(isElementVisible(By.xpath(".//span[contains(text(),'Welcome')]")));

		UserProfilePage profile = menu.goToUserProfilePage();
		// change the password back

		profile.editOldPassword(newPassword).editNewPassword(oldPassword).editConfirmPassword(oldPassword)
				.clickUpdatePassword();

		assertTrue(pageContainsStr("Password updated successfully!"));
		clickElement(By.id("gwt-debug-CustomDialog-okButton"));

		checkLogout();

		MenuBarPage menu2 = main.loginAs(Settings.INVESTOR_USERNAME, oldPassword);
		waitForElementVisible(By.xpath(".//span[contains(text(),'Welcome')]"), 60);
		assertTrue(isElementVisible(By.xpath(".//span[contains(text(),'Welcome')]")));

	}

	/**
	 * https://drive.google.com/a/wismore.com/folderview?id=
	 * 0B2_-nwy69cMicTB2RTVJU3MzX2M&usp=drive_web#list
	 * 
	 * Test Number : 16
	 * 
	 * @throws Exception
	 */
	@Test
	public void testUserProfileSetting() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		Double r = Math.random() * 10;
		String randNum = r.intValue() + "";

		String nickName = "test1" + randNum;
		String email1 = "testEmail" + randNum + "@nouyang.com";
		String email2 = "testEmail" + randNum + "@nouyang.com";

		String firstName = "Selenium" + randNum;
		String lastName = "Test" + randNum;
		String otherName = "otherName" + randNum;

		String regionAndCountry = "Hong Kong";

		String DOB = "25-jan-1987";

		String phoneCC1 = "0086";
		String phoneCC2 = "0087";

		String phone1 = "123456" + randNum;
		String phone2 = "654321" + randNum;

		String address1 = "testaddress" + randNum;
		String address2 = "testaddress2" + randNum;

		String timeZone = "GMT-11 (Hawaii)";

		UserProfilePage userProfilePage = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToUserProfilePage();

		// try {
		// userProfilePage.editNickname(nickName);
		// } catch (Exception ex) {
		// System.out.println("Nick Name field not found.");
		// }
		// wait(3);
		userProfilePage.editFisrtname(firstName).editLastname(lastName).editOthername(otherName);

		selectFromDropdown(By.id("gwt-debug-UpdateUserDetailsWidget-regionListBox"), regionAndCountry);

		selectFromDropdown(By.id("gwt-debug-UpdateUserDetailsWidget-nationalityListBox"), regionAndCountry);

		sendKeysToElement(By.id("gwt-debug-UpdateUserDetailsWidget-birthdayTextBox"), DOB);

		// selectFromDropdown(
		// By.id("gwt-debug-UpdateUserDetailsWidget-originResponseListBox"),
		// hearAboutUs);

		selectFromDropdown(By.id("gwt-debug-UpdateUserDetailsWidget-timeZoneListBox"), timeZone);

		int num = getSizeOfElements(By.id("gwt-debug-EditUserDetailListWidget-deleteButton"));

		// clear all phone, email and address and save
		for (int i = 1; i <= num; i++) {
			String path = "//button[@id='gwt-debug-EditUserDetailListWidget-deleteButton']";
			clickElement(By.xpath(path));
			wait(1);
		}

		clickElement(By.id("gwt-debug-UpdateUserDetailsWidget-submitButton"));

		this.waitForWaitingScreenNotVisible();

		userProfilePage.goToUserProfilePage();

		// add two emails without preference
		clickElement(By.id("gwt-debug-UpdateUserDetailsWidget-addEmailButton"));
		// wait(5);
		clickElement(By.id("gwt-debug-UpdateUserDetailsWidget-addEmailButton"));

		// wait(3);
		// Send key to first email textbox
		sendKeysToElement(
				By.xpath(
						"//div[@id='gwt-debug-EditUesrEmailListWidget-contentPanel']/div[1]/table/tbody/tr/td[1]/input[@class='formTextBox']"),
				email1);
		// Send key to second email textbox
		sendKeysToElement(
				By.xpath(
						"//div[@id='gwt-debug-EditUesrEmailListWidget-contentPanel']/div[2]/table/tbody/tr/td[1]/input[@class='formTextBox']"),
				"invalidEmail");

		// check invalid email
		clickElement(By.id("gwt-debug-UpdateUserDetailsWidget-submitButton"));

		clickElement(By.xpath("(//button[@id='gwt-debug-CustomDialog-okButton'])[2]"));
		this.waitForElementNotVisible(By.xpath("(//button[@id='gwt-debug-CustomDialog-okButton'])[2]"),
				Settings.WAIT_SECONDS);

		clickElement(By.id("gwt-debug-CustomDialog-okButton"));

		// wait(2);
		scrollToTop();
		new Actions(webDriver)
				.moveToElement(webDriver.findElement(By
						.xpath("//div[@id='gwt-debug-EditUesrEmailListWidget-contentPanel']/div[2]/table/tbody/tr/td[1]/input[@class='formTextBox formTextBoxInvalid']")))
				.perform();

		String cssClassName = this.getAttributeStringByLocator(
				By.xpath(
						"//div[@id='gwt-debug-EditUesrEmailListWidget-contentPanel']/div[2]/table/tbody/tr/td[1]/input[@class='formTextBox formTextBoxInvalid']"),
				"class");
		assertTrue(cssClassName.indexOf("formTextBoxInvalid") > 1);
		// wait(2);

		// check preference email function
		sendKeysToElement(
				By.xpath(
						"//div[@id='gwt-debug-EditUesrEmailListWidget-contentPanel']/div[2]/table/tbody/tr/td[1]/input[@class='formTextBox formTextBoxInvalid']"),
				email2);
		clickElement(By.id("gwt-debug-UpdateUserDetailsWidget-submitButton"));
		this.waitForElementVisible(By.xpath("(//button[@id='gwt-debug-CustomDialog-okButton'])[2]"),
				Settings.WAIT_SECONDS);

		clickElement(By.xpath("(//button[@id='gwt-debug-CustomDialog-okButton'])[2]"));
		this.waitForElementNotVisible(By.xpath("(//button[@id='gwt-debug-CustomDialog-okButton'])[2]"),
				Settings.WAIT_SECONDS);

		clickElement(By.id("gwt-debug-CustomDialog-okButton"));
		this.waitForElementNotVisible(By.id("gwt-debug-CustomDialog-okButton"), Settings.WAIT_SECONDS);

		// set preferred email
		clickElement(By.id("gwt-debug-EditUserEmailWidget-preferredRadioButton-1"));

		// add two phones without preference
		clickElement(By.id("gwt-debug-UpdateUserDetailsWidget-addPhoneButton"));
		clickElement(By.id("gwt-debug-UpdateUserDetailsWidget-addPhoneButton"));
		this.waitForElementVisible(By.xpath("//input[@id='gwt-debug-EditUserPhoneWidget-codeTextBox-1']"),
				Settings.WAIT_SECONDS);

		sendKeysToElement(By.xpath("//input[@id='gwt-debug-EditUserPhoneWidget-codeTextBox-1']"), phoneCC1);

		sendKeysToElement(By.xpath("//input[@id='gwt-debug-EditUserPhoneWidget-numberTextBox-1']"), phone1);

		sendKeysToElement(By.xpath("//input[@id='gwt-debug-EditUserPhoneWidget-codeTextBox-2']"), phoneCC2);

		sendKeysToElement(By.xpath("//input[@id='gwt-debug-EditUserPhoneWidget-numberTextBox-2']"), phone2);

		// check preference phone function
		clickElement(By.id("gwt-debug-UpdateUserDetailsWidget-submitButton"));
		this.waitForElementVisible(By.xpath("(//button[@id='gwt-debug-CustomDialog-okButton'])[2]"),
				Settings.WAIT_SECONDS);

		// clickElement(By
		// .xpath("(//button[@id='gwt-debug-CustomDialog-okButton'])[3]"));
		// wait(3);

		clickElement(By.xpath("(//button[@id='gwt-debug-CustomDialog-okButton'])[2]"));
		this.waitForElementNotVisible(By.xpath("(//button[@id='gwt-debug-CustomDialog-okButton'])[2]"),
				Settings.WAIT_SECONDS);

		clickElement(By.id("gwt-debug-CustomDialog-okButton"));
		this.waitForElementNotVisible(By.xpath("(//button[@id='gwt-debug-CustomDialog-okButton'])[1]"),
				Settings.WAIT_SECONDS);

		// set preferred phone
		clickElement(By.id("gwt-debug-EditUserPhoneWidget-preferredRadioButton-1"));

		// add a address with default preference
		clickElement(By.id("gwt-debug-UpdateUserDetailsWidget-addAddressButton"));
		this.waitForElementVisible(By.xpath("//input[@id='gwt-debug-EditUserAddressWidget-address1TextBox-1']"),
				Settings.WAIT_SECONDS);

		sendKeysToElement(By.xpath("//input[@id='gwt-debug-EditUserAddressWidget-address1TextBox-1']"), address1);
		sendKeysToElement(By.xpath("//input[@id='gwt-debug-EditUserAddressWidget-address2TextBox-1']"), address2);
		clickElement(By.id("gwt-debug-UpdateUserDetailsWidget-submitButton"));

	}

	@Test
	public void testCheckPermissionToViewUserDetailsPage() throws InterruptedException {

		setScreenshotSuffix(AccountOverviewAndSearchTest.class.getSimpleName());

		checkLogin("InfinityAdmin", "InfinityAdmin");

		webDriver.get(baseUrl
				+ "?locale=en&viewMode=MATERIAL#generalUserDetails;userKey=4598;detailType=1;rebuildbdcm=false;rmLastLnk=false");
		this.waitForWaitingScreenNotVisible();
		this.handleAlert();
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 30);

		assertTrue(this.pageContainsStr("Error"));

		// assertTrue(this
		// .pageContainsStr("You have no permission to access this user
		// details"));
	}

	@Test
	public void testCheckOptionNumberInCompanySetting() throws InterruptedException {
		LoginPage main = new LoginPage(webDriver);

		CompanySettingsPage csPage = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToCompanySettingsPage();

		clickElement(By.id("gwt-debug-AdminAdvisorCompanyEdit-addAddrButton"));

		Integer countryDropdown = getDropdownOptionNumber("Country");
		Integer defaultPriceViewDropdown = getDropdownOptionNumber("Default Price View");
		Integer themeDropdown = getDropdownOptionNumber("Theme");
		Integer editDropdown = getDropdownOptionNumber("Edit");
		Integer localeDropdown = getDropdownOptionNumber("Locale");
		Integer marketCommentaryPageDropdown = getDropdownOptionNumber("Market Commentary Page");
		Integer reportLanguageDropdown = getDropdownOptionNumber("Report Language");
		Integer disclaimerDropdown = getDropdownOptionNumber("Disclaimer");
		Integer signUpEnableDropdown = getDropdownOptionNumber("Sign Up Enable");
		Integer registerEmailValidityDropdown = getDropdownOptionNumber("Registration Email Validity");
		Integer currencyDropdown = getDropdownOptionNumber("Currency");

		csPage.goToUserProfilePage().goToCompanySettingsPage();

		assertEquals(countryDropdown, getDropdownOptionNumber("Country:"));
		assertEquals(defaultPriceViewDropdown, getDropdownOptionNumber("Default Price View"));
		assertEquals(themeDropdown, getDropdownOptionNumber("Theme"));
		assertEquals(editDropdown, getDropdownOptionNumber("Edit"));
		assertEquals(localeDropdown, getDropdownOptionNumber("Locale"));
		assertEquals(marketCommentaryPageDropdown, getDropdownOptionNumber("Market Commentary Page"));
		assertEquals(reportLanguageDropdown, getDropdownOptionNumber("Report Language"));
		assertEquals(disclaimerDropdown, getDropdownOptionNumber("Disclaimer"));
		assertEquals(signUpEnableDropdown, getDropdownOptionNumber("Sign Up Enable"));
		assertEquals(registerEmailValidityDropdown, getDropdownOptionNumber("Registration Email Validity"));
		assertEquals(currencyDropdown, getDropdownOptionNumber("Currency"));

	}

	public Integer getDropdownOptionNumber(String field) {
		WebElement elem = null;

		try {
			elem = webDriver
					.findElement(By.xpath(".//*[contains(text(),'" + field + "')]/following-sibling::td//select"));
		} catch (org.openqa.selenium.NoSuchElementException e) {
			elem = webDriver.findElement(By.xpath(".//*[.='" + field + "']/following-sibling::select"));
		}

		Select select = new Select(elem);
		Integer totalNumber = select.getOptions().size();
		return totalNumber;
	}
}
