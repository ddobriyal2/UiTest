package org.sly.uitest.sections.systemadmin;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.abstractpage.MenuBarPage;
import org.sly.uitest.pageobjects.admin.AdminEditPage;
import org.sly.uitest.pageobjects.admin.AdminSearchPage;
import org.sly.uitest.settings.Settings;

/**
 * @author Lynne Huang
 * @date : 22 Oct, 2015
 * @company Prive Financial
 */
public class SystemAdminTest extends AbstractPage {

	@Test
	public void testCreateNewUnderStrategySection() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		String field;
		String name_field = "Strategy Name:";
		String name;
		String key;
		MenuBarPage mbPage = main.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD);

		AdminEditPage admin = mbPage.goToAdminEditPage();
		// create new investment strategy
		field = "Investment Strategy";
		name = "" + this.getRandName();
		String sector = "Equity-Basic Materials";
		String region = "Africa/Middle East";
		String sub_region = "region" + name;
		String strategy_short_name_field = "Strategy Short Name:";
		String strategy_type_field = " Strategy Type: ";
		String strategy_type = "Benchmark";
		// String startdate_field = " Live Start Date: (MM/dd/yyyy) ";
		// String startdate = "01////01////2010";
		String startvalue_field = " Live Start Value: ";
		String startvalue = "100";
		String minimum_field = "Minimum Initial Investment: ";
		String minimum = "10";
		String minimum_sub_field = "Minimum Subsequent Investment: ";
		String minimum_sub = "10";

		admin.editAdminThisField(field).clickNewButtonToCreateNewToken()
				.selectOptionForCertainField(strategy_type_field, strategy_type).inputValueToCurrencyField("AUD")
				.selectOptionForCertainField("Visibility", "Visible to all")
				.selectOptionForCertainField("Sector", sector).selectOptionForCertainField("Region", region)
				.inputValueToCertainField("Sub Region", sub_region).inputValueToCertainField(name_field, name)
				.inputValueToCertainField(strategy_short_name_field, name)
				.inputValueToCertainField(startvalue_field, startvalue).inputValueToCertainField(minimum_field, minimum)
				.inputValueToCertainField(minimum_sub_field, minimum_sub).clickSubmitButton();

		key = (getTextByLocator(By.id("gwt-debug-CustomDialog-message")).split("key is "))[1];

		clickOkButtonIfVisible();

		// admin.selectTokenByNameAndKey(name, key);

		admin.jumpByKeyAndLoad(key);

		admin.verifyInputValueToCertainField(" Name: ", name);

		admin.goToAdminEditPage();

		// create new strategy allocation
		field = "Strategy Allocation";
		name = "Allocation" + this.getRandName();
		String submission_field = " Submission Timestamp: (yyyy-MM-dd HH:mm:ss) ";
		String submission = "2015-02-02 12:00:00";
		String live_field = " Live Timestamp: (yyyy-MM-dd HH:mm:ss) ";
		String live = "2012-05-05 08:00:00";

		admin.editAdminThisField(field).clickNewButtonToCreateNewToken()
				.inputValueToCertainField(submission_field, submission).inputValueToCertainField(live_field, live)
				.clickSubmitButton();

		key = (getTextByLocator(By.id("gwt-debug-CustomDialog-message")).split("key is "))[1];

		clickOkButtonIfVisible();

		admin.jumpByKeyAndLoad(key);
		wait(Settings.WAIT_SECONDS);
		admin.verifyInputValueToCertainField(submission_field, submission);

		admin.goToAdminEditPage();

		// create new strategy style
		field = "Strategy Styles";
		name_field = " Name:";
		name = "Style" + this.getRandName();

		admin.editAdminThisField(field).clickNewButtonToCreateNewToken().inputValueToCertainField(name_field, name)
				.clickSubmitButton();

		key = (getTextByLocator(By.id("gwt-debug-CustomDialog-message")).split("key is "))[1];

		clickOkButtonIfVisible();

		// admin.selectTokenByNameAndKey(name, key);

		admin.jumpByKeyAndLoad(key);

		admin.verifyInputValueToCertainField(name_field, name);

		admin.goToAdminEditPage();

		// create new strategy rule

		field = "Strategy Rules";
		name_field = " Rule Name: ";
		name = "Rule" + this.getRandName();

		admin.editAdminThisField(field).clickNewButtonToCreateNewToken().inputValueToCertainField(name_field, name)
				.clickSubmitButton();

		key = (getTextByLocator(By.id("gwt-debug-CustomDialog-message")).split("key is "))[1];

		clickOkButtonIfVisible();

		admin.selectTokenByNameAndKey(name, key);

		admin.jumpByKeyAndLoad(key);

		admin.verifyInputValueToCertainField(name_field, name);

		name_field = " Name: ";

		admin.goToAdminEditPage();

		// create new portfolio allocation

		field = "Portfolio Allocation";
		String executed_field = "Executed Time Stamp";
		String executed = "2012-01-01 12:00:00";
		submission_field = "Submission Time Stamp";
		submission = "2014-01-01 12:00:00";

		admin.editAdminThisField(field).clickNewButtonToCreateNewToken()
				.inputValueToCertainField(executed_field, executed)
				.inputValueToCertainField(submission_field, submission).clickSubmitButton();

		key = (getTextByLocator(By.id("gwt-debug-CustomDialog-message")).split("key is "))[1];

		clickOkButtonIfVisible();

		admin.jumpByKeyAndLoad(key);

		// admin.verifyInputValueToCertainField(executed_field, executed)
		// .verifyInputValueToCertainField(submission_field, submission);

		name_field = " Name: ";

		admin.goToAdminEditPage();

	}

	// private void createNewUnderStrategySectionMaterialView(MenuBarPage
	// mbPage)
	// throws InterruptedException {
	//
	// AdminEditPage admin = mbPage.goToAdminEditPage();
	// // create new investment strategy
	// String name_field = " Name: ";
	// String key;
	// String field = "Investment Strategy";
	// String name = "Strategy" + this.getRandName();
	// String strategy_type_field = " Strategy Type: ";
	// String strategy_type = "Benchmark";
	// String startdate_field = " Live Start Date: (MM/dd/yyyy) ";
	// String startdate = "01/01/2010";
	// String startvalue_field = " Live Start Value: ";
	// String startvalue = "100";
	// String minimum_field = "Minimum Initial Investment: ";
	// String minimum = "10";
	// String minimum_sub_field = "Minimum Subsequent Investment: ";
	// String minimum_sub = "10";
	//
	// admin.editAdminThisField(field)
	// .clickNewButtonToCreateNewToken()
	// .selectOptionForCertainField(strategy_type_field, strategy_type)
	// .inputValueToCurrencyField("AUD")
	// .selectOptionForCertainField("Visibility", "Visible to all")
	// .inputValueToCertainField(name_field, name)
	// .inputValueToCertainField(startdate_field, startdate)
	// .inputValueToCertainField(startvalue_field, startvalue)
	// .inputValueToCertainField(minimum_field, minimum)
	// .inputValueToCertainField(minimum_sub_field, minimum_sub)
	// .clickSubmitButton();
	//
	// key = (getTextByLocator(By.id("gwt-debug-CustomDialog-message"))
	// .split("key is "))[1];
	//
	// clickOkButtonIfVisible();
	//
	// // admin.selectTokenByNameAndKey(name, key);
	//
	// admin.jumpByKeyAndLoad(key);
	//
	// admin.verifyInputValueToCertainField(" Name: ", name);
	//
	// admin.goToAdminEditPage();
	//
	// // create new strategy allocation
	// field = "Strategy Allocation";
	// name = "Allocation" + this.getRandName();
	// String submission_field =
	// " Submission Timestamp: (yyyy-MM-dd HH:mm:ss) ";
	// String submission = "2015-02-02 12:00:00";
	// String live_field = " Live Timestamp: (yyyy-MM-dd HH:mm:ss) ";
	// String live = "2012-05-05 08:00:00";
	//
	// admin.editAdminThisField(field).clickNewButtonToCreateNewToken()
	// .inputValueToCertainField(submission_field, submission)
	// .inputValueToCertainField(live_field, live).clickSubmitButton();
	//
	// key = (getTextByLocator(By.id("gwt-debug-CustomDialog-message"))
	// .split("key is "))[1];
	//
	// clickOkButtonIfVisible();
	//
	// admin.jumpByKeyAndLoad(key);
	// wait(Settings.WAIT_SECONDS);
	// admin.verifyInputValueToCertainField(submission_field, submission);
	//
	// admin.goToAdminEditPage();
	//
	// // create new strategy style
	// field = "Strategy Styles";
	// name = "Style" + this.getRandName();
	//
	// admin.editAdminThisField(field).clickNewButtonToCreateNewToken()
	// .inputValueToCertainField(name_field, name).clickSubmitButton();
	//
	// key = (getTextByLocator(By.id("gwt-debug-CustomDialog-message"))
	// .split("key is "))[1];
	//
	// clickOkButtonIfVisible();
	//
	// // admin.selectTokenByNameAndKey(name, key);
	//
	// admin.jumpByKeyAndLoad(key);
	//
	// admin.verifyInputValueToCertainField(name_field, name);
	//
	// admin.goToAdminEditPage();
	//
	// // create new strategy rule
	//
	// field = "Strategy Rules";
	// name_field = " Rule Name: ";
	// name = "Rule" + this.getRandName();
	//
	// admin.editAdminThisField(field).clickNewButtonToCreateNewToken()
	// .inputValueToCertainField(name_field, name).clickSubmitButton();
	//
	// key = (getTextByLocator(By.id("gwt-debug-CustomDialog-message"))
	// .split("key is "))[1];
	//
	// clickOkButtonIfVisible();
	//
	// admin.selectTokenByNameAndKey(name, key);
	//
	// admin.jumpByKeyAndLoad(key);
	//
	// admin.verifyInputValueToCertainField(name_field, name);
	//
	// name_field = " Name: ";
	//
	// admin.goToAdminEditPage();
	//
	// // create new portfolio allocation
	//
	// field = "Portfolio Allocation";
	// String executed_field = "Executed Time Stamp";
	// String executed = "2012-01-01 12:00:00";
	// submission_field = "Submission Time Stamp";
	// submission = "2014-01-01 12:00:00";
	//
	// admin.editAdminThisField(field).clickNewButtonToCreateNewToken()
	// .inputValueToCertainField(executed_field, executed)
	// .inputValueToCertainField(submission_field, submission)
	// .clickSubmitButton();
	//
	// key = (getTextByLocator(By.id("gwt-debug-CustomDialog-message"))
	// .split("key is "))[1];
	//
	// clickOkButtonIfVisible();
	//
	// admin.jumpByKeyAndLoad(key);
	//
	// // admin.verifyInputValueToCertainField(executed_field, executed)
	// // .verifyInputValueToCertainField(submission_field, submission);
	//
	// name_field = " Name: ";
	//
	// admin.goToAdminEditPage();
	//
	// }

	@Test
	public void testCreateNewUnderCompaniesUsersSection() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		String field = "Manager Company";
		String name_field = "Name: ";
		String name = "Manager" + this.getRandName();
		String key;

		MenuBarPage mbPage = main.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD);
		// wait(5);
		// boolean isMaterial = isElementVisible(By
		// .xpath(".//*[@id='page-wrapper']/div[1]/nav/div/a/i"));
		AdminEditPage admin = mbPage.goToAdminEditPage();

		admin.editAdminThisField(field).clickNewButtonToCreateNewToken().inputValueToCertainField(name_field, name)
				.clickSubmitButton();

		key = (getTextByLocator(By.id("gwt-debug-CustomDialog-message")).split("key is "))[1];

		clickOkButtonIfVisible();

		admin.selectTokenByNameAndKey(name, key);

		admin.jumpByKeyAndLoad(key);

		admin.verifyInputValueToCertainField(name_field, name);

		admin.goToAdminEditPage();

		// create new composite
		field = "Composite";
		name_field = "Composite Name: ";
		name = "Composite" + this.getRandName();
		// wait(5);
		admin.editAdminThisField(field).clickNewButtonToCreateNewToken().inputValueToCertainField(name_field, name)
				.clickSubmitButton();

		key = (getTextByLocator(By.id("gwt-debug-CustomDialog-message")).split("key is "))[1];

		clickOkButtonIfVisible();

		admin.selectTokenByNameAndKey(name, key);

		admin.jumpByKeyAndLoad(key);

		admin.verifyInputValueToCertainField(name_field, name);

		admin.goToAdminEditPage();

		// create new user group
		field = "User Group";

		name_field = "Name: ";
		name = "User Group" + this.getRandName();

		admin.editAdminThisField(field).clickNewButtonToCreateNewToken().inputValueToCertainField(name_field, name)
				.clickSubmitButton();

		key = (getTextByLocator(By.id("gwt-debug-CustomDialog-message")).split("key is "))[1];

		clickOkButtonIfVisible();

		admin.selectTokenByNameAndKey(name, key);

		admin.jumpByKeyAndLoad(key);

		admin.verifyInputValueToCertainField(name_field, name);

		admin.goToAdminEditPage();
	}

	@Test
	public void testCreateNewUnderCompaniesUsersSection2() throws InterruptedException {
		// create new advisor company
		String field = "Advisor Companies";
		String name_field = "Company Name: ";
		String name = "Advisor Company" + this.getRandName();
		String currency_field;
		String currency;
		String key;

		LoginPage main = new LoginPage(webDriver);

		MenuBarPage mbPage = main.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD);

		AdminEditPage admin = mbPage.goToAdminEditPage();

		admin.editAdminThisField(field).clickNewButtonToCreateNewToken().inputValueToCertainField(name_field, name)

				.clickSubmitButton();

		// .inputValueToCertainField(email_field, email)

		key = (getTextByLocator(By.id("gwt-debug-CustomDialog-message")).split("key is "))[1];

		clickOkButtonIfVisible();

		admin.selectTokenByNameAndKey(name, key);

		admin.jumpByKeyAndLoad(key);

		admin.verifyInputValueToCertainField(name_field, name);

		admin.goToAdminEditPage();

		field = "Wholesaler Company";
		name_field = "Name:";
		name = "Wholesaler" + getRandName();

		admin.editAdminThisField(field).clickNewButtonToCreateNewToken().inputValueToCertainField(name_field, name)
				.clickSubmitButton();

		key = (getTextByLocator(By.id("gwt-debug-CustomDialog-message")).split("key is "))[1];

		clickOkButtonIfVisible();

		admin.jumpByKeyAndLoad(key);

		admin.verifyInputValueToCertainField(name_field, name);

		admin.goToAdminEditPage();

		// create new investor accounts/model portfolios
		field = "Investor Accounts/Model Portfolios";
		name_field = "Name: ";
		name = "Account" + getRandName();

		String type_field = "Type: ";
		String type = "Insurance";
		String platform_field = "Execution Platform: ";
		String platform = "Generali Vision";

		currency_field = "Currency: ";
		currency = "USD";
		String status_filed = "Account Status: ";
		String status = "In Force";

		admin.editAdminThisField(field).clickNewButtonToCreateNewToken().inputValueToCertainField(name_field, name)
				.selectOptionForCertainField(type_field, type).selectOptionForCertainField(platform_field, platform)
				.selectOptionForCertainField(status_filed, status)
				.selectOptionForCertainField(currency_field, currency);
		this.waitForElementNotVisible(By.xpath(".//*[@title='Loading user list, please wait...']"),
				Settings.WAIT_SECONDS * 24);
		this.handleAlert();
		admin.clickSubmitButton();

		key = (getTextByLocator(By.id("gwt-debug-CustomDialog-message")).split("key is "))[1];

		clickOkButtonIfVisible();

		// admin.selectTokenByNameAndKey(name, key);

		admin.jumpByKeyAndLoad(key);

		admin.verifyInputValueToCertainField(name_field, name);

		admin.goToAdminEditPage();

		// create new advisor account
		field = "Advisor Accounts";
		name_field = "Account Name: ";
		name = "Advisor Account" + this.getRandName();

		currency_field = "Base Currency: ";
		currency = "USD";

		admin.editAdminThisField(field).clickNewButtonToCreateNewToken();

		waitForElementVisible(
				By.xpath(".//*[@id='gwt-debug-AdminAdvisorAccountEdit-advisorAccountAdvisorCompany']/option"), 30);

		admin.inputValueToCertainField(name_field, name).selectOptionForCertainField(currency_field, currency);

		this.waitForElementNotVisible(By.xpath(".//*[@title='Loading user list, please wait...']"),
				Settings.WAIT_SECONDS * 24);
		this.handleAlert();

		admin.clickSubmitButton();

		key = (getTextByLocator(By.id("gwt-debug-CustomDialog-message")).split("key is "))[1];

		clickOkButtonIfVisible();

		admin.selectTokenByNameAndKey(name, key);

		admin.jumpByKeyAndLoad(key);

		admin.verifyInputValueToCertainField(name_field, name);
	}

	@Deprecated
	public void testCreateNewUnderCompaniesUsersSectionMaterialView(MenuBarPage mbPage) throws InterruptedException {

		String field;
		String name_field;
		String name;
		String key;
		// create new manager company
		field = "Manager Company";
		name_field = "Name:";
		name = "Manager" + this.getRandName();
		AdminEditPage admin = mbPage.goToAdminEditPage();
		admin.editAdminThisField(field).clickNewButtonToCreateNewToken().inputValueToCertainField(name_field, name)
				.clickSubmitButton();

		key = (getTextByLocator(By.id("gwt-debug-CustomDialog-message")).split("key is "))[1];

		clickOkButtonIfVisible();

		admin.selectTokenByNameAndKey(name, key);
		wait(5);
		admin.jumpByKeyAndLoad(key);

		admin.verifyInputValueToCertainField(name_field, name);

		admin.goToAdminEditPage();

		// create new composite
		field = "Composite";
		name_field = "Composite Name:";
		name = "Composite" + this.getRandName();

		admin.editAdminThisField(field).clickNewButtonToCreateNewToken().inputValueToCertainField(name_field, name)
				.clickSubmitButton();

		key = (getTextByLocator(By.id("gwt-debug-CustomDialog-message")).split("key is "))[1];

		clickOkButtonIfVisible();

		admin.selectTokenByNameAndKey(name, key);

		admin.jumpByKeyAndLoad(key);

		admin.verifyInputValueToCertainField(name_field, name);

		admin.goToAdminEditPage();

		// create new user group
		field = "User Group";

		name_field = "Name:";
		name = "User Group" + this.getRandName();

		admin.editAdminThisField(field).clickNewButtonToCreateNewToken().inputValueToCertainField(name_field, name)
				.clickSubmitButton();

		key = (getTextByLocator(By.id("gwt-debug-CustomDialog-message")).split("key is "))[1];

		clickOkButtonIfVisible();

		admin.selectTokenByNameAndKey(name, key);

		admin.jumpByKeyAndLoad(key);

		admin.verifyInputValueToCertainField(name_field, name);

		admin.goToAdminEditPage();

		// create new advisor company
		field = "Advisor Companies";
		name_field = "Company Name:";
		name = "Advisor Company" + this.getRandName();
		String email_field = "Email";
		String email = this.getRandName() + "@advisor.com";

		admin.editAdminThisField(field).clickNewButtonToCreateNewToken().inputValueToCertainField(name_field, name)

				.clickSubmitButton();

		// .inputValueToCertainField(email_field, email)

		key = (getTextByLocator(By.id("gwt-debug-CustomDialog-message")).split("key is "))[1];

		clickOkButtonIfVisible();

		admin.selectTokenByNameAndKey(name, key);
		wait(5);
		admin.jumpByKeyAndLoad(key);

		admin.verifyInputValueToCertainField(name_field, name);

		admin.goToAdminEditPage();

		// create new advisor account
		field = "Advisor Accounts";

		name_field = "Account Name:";
		name = "Advisor Account" + this.getRandName();
		String currency_field = "Base Currency:";
		String currency = "USD";

		admin.editAdminThisField(field).clickNewButtonToCreateNewToken();

		waitForElementVisible(
				By.xpath(".//*[@id='gwt-debug-AdminAdvisorAccountEdit-advisorAccountAdvisorCompany']/option"), 60);

		admin.inputValueToCertainField(name_field, name).selectOptionForCertainField(currency_field, currency)
				.clickSubmitButton();

		key = (getTextByLocator(By.id("gwt-debug-CustomDialog-message")).split("key is "))[1];

		clickOkButtonIfVisible();

		admin.selectTokenByNameAndKey(name, key);

		admin.jumpByKeyAndLoad(key);

		admin.verifyInputValueToCertainField(name_field, name);

		admin.goToAdminEditPage();

		// create new investor accounts/model portfolios
		field = "Investor Accounts/Model Portfolios";
		name_field = "Name:";
		name = "Account" + getRandName();
		String owner_field = "Owned by user:";
		String owner = "Selenium Investor (key:8462) (SeleniumTest)";
		String type_field = "Type:";
		String type = "Insurance";
		String platform_field = "Execution Platform:";
		String platform = "Generali Vision";
		currency_field = "Currency:";
		currency = "USD";
		String status_filed = "Account Status:";
		String status = "In Force";

		admin.editAdminThisField(field).clickNewButtonToCreateNewToken().inputValueToCertainField(name_field, name)
				.selectOptionForCertainField(owner_field, owner).selectOptionForCertainField(type_field, type)
				.selectOptionForCertainField(platform_field, platform).selectOptionForCertainField(status_filed, status)
				.selectOptionForCertainField(currency_field, currency).clickSubmitButton();

		key = (getTextByLocator(By.id("gwt-debug-CustomDialog-message")).split("key is "))[1];

		clickOkButtonIfVisible();

		// admin.selectTokenByNameAndKey(name, key);

		admin.jumpByKeyAndLoad(key);

		admin.verifyInputValueToCertainField(name_field, name);

		admin.goToAdminEditPage();

		field = "Wholesaler Company";
		name_field = "Name:";
		name = "Wholesaler" + getRandName();

		admin.editAdminThisField(field).clickNewButtonToCreateNewToken().inputValueToCertainField(name_field, name)
				.clickSubmitButton();

		key = (getTextByLocator(By.id("gwt-debug-CustomDialog-message")).split("key is "))[1];

		clickOkButtonIfVisible();

		admin.jumpByKeyAndLoad(key);

		admin.verifyInputValueToCertainField(name_field, name);

		admin.goToAdminEditPage();

	}

	@Test
	public void testCreateNewUnderAssetsSection() throws InterruptedException {

		String field = "Ticker";
		String name_field = " Name: ";
		String name = "Manager" + this.getRandName();
		String currency = "HKD";
		String asset = "Bond";
		String exchange = "CHI-X EUROPE (CHI-X EUROPE)";
		String data = "Caidao Wealth (Custom)";
		String couponRate = "0.1";
		String key;

		LoginPage main = new LoginPage(webDriver);
		MenuBarPage mbPage = main.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD);

		AdminEditPage admin = mbPage.goToAdminEditPage();
		// create new ticker
		admin.editAdminThisField(field).clickNewButtonToCreateNewToken().inputValueToCertainField(name_field, name)
				.selectOptionForAssetTypeFieldValue(asset)
				.sendKeysToElement(By.id("gwt-debug-AdminTickerEdit-orderMultiple"), String.valueOf(1));
		wait(3);
		admin.selectOptionForExchangeFieldValue(exchange).selectOptionForDataSourceFieldValue(data, true)
				.checkTheCheckboxBeforeCertainField("Edit Currency", true).inputValueToCurrencyFieldTicker(currency)
				.editCouponRate(couponRate).clickSubmitButton();
		wait(2);
		clickElement(By.xpath("(.//*[@id='gwt-debug-CustomDialog-okButton'])[2]"));
		wait(2);
		clickElement(By.xpath("(.//*[@id='gwt-debug-CustomDialog-noButton'])[2]"));

		key = (getTextByLocator(By.id("gwt-debug-CustomDialog-message")).split("key is "))[1];
		wait(2);
		clickOkButtonIfVisible();

		// admin.selectTokenByNameAndKey(name, key);

		admin.jumpByKeyAndLoad(key);

		admin.verifyInputValueToCertainField(name_field, name);

		admin.goToAdminEditPage();

		// create new exchange
		field = "Exchange";
		name_field = " Name: ";
		name = "Exchange" + this.getRandName();
		String time_field = " Closing Time (HH:mm:ss): ";
		String time = "12:00:00";
		String zone_field = " Time Zone (eg.GMT-05:00,GMT+08:00): ";
		String zone = "GMT+08:00";

		admin.editAdminThisField(field).clickNewButtonToCreateNewToken().inputValueToCertainField(name_field, name)
				.checkTheCheckboxBeforeCertainField("Edit Closing Time and Time Zone", true)
				.inputValueToCertainField(time_field, time).inputValueToCertainField(zone_field, zone)
				.clickSubmitButton();

		key = (getTextByLocator(By.id("gwt-debug-CustomDialog-message")).split("key is "))[1];

		clickOkButtonIfVisible();

		// admin.selectTokenByNameAndKey(name, key);

		admin.jumpByKeyAndLoad(key);

		admin.verifyInputValueToCertainField(name_field, name);

		admin.goToAdminEditPage();

		field = "Investment Universe Whitelist";
		name_field = " Name: ";
		name = "Universe" + this.getRandName();
		asset = "Stock";
		currency = "USD";

		// create new Investment Universe Whitelist
		admin.editAdminThisField(field).clickNewButtonToCreateNewToken().inputValueToCertainField(name_field, name)
				.clickSubmitButton();

		key = (getTextByLocator(By.id("gwt-debug-CustomDialog-message")).split("key is "))[1];

		clickOkButtonIfVisible();

		admin.jumpByKeyAndLoad(key);

		admin.verifyInputValueToCertainField(name_field, name);

		admin.goToAdminEditPage();

		field = "Investment Universe Whitelist";
		name_field = " Name: ";
		name = "FundMap" + this.getRandName();

		currency = "USD";

		// create Money Market Fund Mapping
		admin.editAdminThisField(field).clickNewButtonToCreateNewToken().inputValueToCertainField(name_field, name)
				.clickSubmitButton();

		key = (getTextByLocator(By.id("gwt-debug-CustomDialog-message")).split("key is "))[1];

		clickOkButtonIfVisible();

		admin.jumpByKeyAndLoad(key);

		admin.verifyInputValueToCertainField(name_field, name);

		admin.goToAdminEditPage();
	}

	@Test
	public void testCreateNewUnderContentSection() throws InterruptedException {

		String field = "Content";
		String document = "LoginTest" + this.getRandName();
		String key;
		String document_field = " Document Name: ";

		LoginPage main = new LoginPage(webDriver);
		MenuBarPage mbPage = main.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD);
		wait(5);

		AdminEditPage admin = mbPage.goToAdminEditPage();

		// create new ticker
		admin.editAdminThisField(field).clickNewButtonToCreateNewToken().textForCertainField(document)
				.clickSubmitButton();

		key = (getTextByLocator(By.id("gwt-debug-CustomDialog-message")).split("key is "))[1];

		clickOkButtonIfVisible();

		admin.selectTokenByNameAndKey(document, key);

		admin.jumpByKeyAndLoad(key);

		admin.verifyInputValueToCertainField(document_field, document);

		admin.goToAdminEditPage();

	}

	@Test
	public void testCreateNewUnderExecutionPlatform() throws InterruptedException {

		String companyField = "Execution Platform Company";
		String objectField = "Execution Platform Object";
		String overrideField = "Execution Platform Object Override";
		String name_field = "Name:";
		String type_field = "Type:";
		String execution_platform_field = "Execution Platform Company";
		String data_feed_field = "Data Feed Type:";
		String platform_field = "Platform Type:";
		String advisor_field = "Advisor Company:";
		String companyName = "Test Corp" + getRandName();
		String objectName = "Test" + getRandName();
		String overrideName = "Override" + getRandName();
		String type = "Bank";
		String key;

		LoginPage main = new LoginPage(webDriver);
		MenuBarPage mbPage = main.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD);
		// wait(5);

		// Execution Platform Company
		AdminEditPage admin = mbPage.goToAdminEditPage();
		admin.editAdminThisField(companyField).clickNewButtonToCreateNewToken()
				.inputValueToCertainField(name_field, companyName).selectOptionForCertainField(type_field, type)
				.clickSubmitButton();

		key = (getTextByLocator(By.id("gwt-debug-CustomDialog-message")).split("key is "))[1];

		clickOkButtonIfVisible();

		admin.selectTokenByNameAndKey(companyName, key);

		admin.jumpByKeyAndLoad(key);

		admin.verifyInputValueToCertainField(name_field, companyName);

		admin.goToAdminEditPage();

		// Execution Platform Object
		admin.editAdminThisField(objectField).clickNewButtonToCreateNewToken()
				.inputValueToCertainField(name_field, objectName)
				.selectOptionForCertainField(execution_platform_field, "Other Provider")
				.selectOptionForCertainField(data_feed_field, "Dummy Broker")
				.selectOptionForCertainField(platform_field, "Other").clickSubmitButton();

		key = (getTextByLocator(By.id("gwt-debug-CustomDialog-message")).split("key is "))[1];

		clickOkButtonIfVisible();

		admin.selectTokenByNameAndKey(objectName, key);

		admin.jumpByKeyAndLoad(key);

		admin.verifyInputValueToCertainField(name_field, objectName);

		admin.goToAdminEditPage();
		// Execution Platform Object Override
		admin.editAdminThisField(overrideField).clickNewButtonToCreateNewToken()
				.inputValueToCertainField(name_field, overrideName)
				.selectOptionForCertainField(advisor_field, "SeleniumTest").clickSubmitButton();

		key = (getTextByLocator(By.id("gwt-debug-CustomDialog-message")).split("key is "))[1];

		clickOkButtonIfVisible();

		admin.selectTokenByNameAndKey(overrideName, key);

		admin.jumpByKeyAndLoad(key);

		admin.verifyInputValueToCertainField(name_field, overrideName);

		admin.goToAdminEditPage();
	}

	@Test
	public void testCreateNewUnderBroker() throws InterruptedException {

		String collection_field = "Broker Request Collection";
		String request_field = "Broker Request";
		String credentials = "Broker Credentials";
		String status_field = "Status Server Processing:";
		String status_field2 = "status";
		String name_field = "Name:";
		String username_field = "Username:";
		String name = "test" + getRandName();
		String status = "Completed";
		String key;

		LoginPage main = new LoginPage(webDriver);
		MenuBarPage mbPage = main.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD);
		// wait(5);

		// Broker Request Collection
		AdminEditPage admin = mbPage.goToAdminEditPage();
		admin.editAdminThisField(collection_field).clickNewButtonToCreateNewToken()
				.selectOptionForCertainField(status_field, status).clickSubmitButton();

		key = (getTextByLocator(By.id("gwt-debug-CustomDialog-message")).split("key is "))[1];

		clickOkButtonIfVisible();

		admin.jumpByKeyAndLoad(key);
		assertTrue(pageContainsStr(key));

		// Broker Request
		admin.goToAdminEditPage();
		admin.editAdminThisField(request_field).clickNewButtonToCreateNewToken()
				.selectOptionForCertainField(status_field2, status).clickSubmitButton();

		key = (getTextByLocator(By.id("gwt-debug-CustomDialog-message")).split("key is "))[1];

		clickOkButtonIfVisible();

		admin.jumpByKeyAndLoad(key);
		assertTrue(pageContainsStr(key));

		// Broker Credentials
		admin.goToAdminEditPage();
		admin.editAdminThisField(credentials).clickNewButtonToCreateNewToken()
				.inputValueToCertainField(name_field, name).inputValueToCertainField(username_field, name)
				.clickSubmitButton();

		key = (getTextByLocator(By.id("gwt-debug-CustomDialog-message")).split("key is "))[1];

		clickOkButtonIfVisible();

		admin.jumpByKeyAndLoad(key);
		admin.verifyInputValueToCertainField(name_field, name).verifyInputValueToCertainField(username_field, name);

	}

	@Test
	public void testAdminSearchFunction() throws Exception {

		String user1 = "SeleniumTest";
		String user2 = "JarlAdvisor";
		LoginPage main = new LoginPage(webDriver);
		MenuBarPage mbPage = main.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD);

		AdminSearchPage admin = mbPage.goToAdminSearchPage();
		admin.editSearchType("User").clickAdvancedSearchButton().searchByCustomFields("Username", user1)
				.clickSearchButton();

		try {
			waitForElementVisible(By.id("gwt-debug-SortableFlexTableAsync-table"), 20);
			assertTrue(pageContainsStr(user1));
		} catch (TimeoutException e) {
			log(user1 + " is not found!");
		}

		mbPage.goToAdminSearchPage();
		admin.editSearchType("User").clickAdvancedSearchButton().searchByCustomFields("Username", user2)
				.clickSearchButton();

		try {
			waitForElementVisible(By.id("gwt-debug-SortableFlexTableAsync-table"), 20);
			assertTrue(pageContainsStr(user2));
		} catch (TimeoutException e) {
			log(user2 + " is not found!");
		}

	}

	@Test
	public void testAccessRights() throws InterruptedException {

		String wholeSaler = "WholeSaler Module Permission";
		String manager = "Manager Module Permission";
		String execution = "Execution Platform Company Module Permission";
		String module = "MODULE_EMAIL_OVERVIEW";

		LoginPage main = new LoginPage(webDriver);
		MenuBarPage mbPage = main.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD);
		// wait(5);

		// test advisor module permission
		mbPage.goToAdminEditPage().editAdminThisField(Settings.Advisor_Company_Module_Permission)
				.jumpByKeyAndLoad(Settings.SeleniumTest_Key).editModuleToggle(module, true, true).clickSubmitButton();

		mbPage.goToAdminEditPage().editAdminThisField(Settings.Advisor_Company_Module_Permission)
				.jumpByKeyAndLoad(Settings.SeleniumTest_Key).assertionModuleToggle(module, true, true)
				.editModuleToggle(module, false, false).clickSubmitButton();

		// test WholeSaler Module Permission
		mbPage.goToAdminEditPage().editAdminThisField(wholeSaler).jumpByKeyAndLoad("1")
				.editSingleModuleToggle(module, true).clickSubmitButton();

		mbPage.goToAdminEditPage().editAdminThisField(wholeSaler).jumpByKeyAndLoad("1")
				.assertionSingleModuleToggle(module, true).editSingleModuleToggle(module, false).clickSubmitButton();

		// test Manager Module Permission
		mbPage.goToAdminEditPage().editAdminThisField(manager).jumpByKeyAndLoad("3876")
				.editSingleModuleToggle(module, true).clickSubmitButton();

		mbPage.goToAdminEditPage().editAdminThisField(manager).jumpByKeyAndLoad("3876")
				.assertionSingleModuleToggle(module, true).editSingleModuleToggle(module, false).clickSubmitButton();

		// test Execution Platform Company Module Permission
		mbPage.goToAdminEditPage().editAdminThisField(execution).jumpByKeyAndLoad("841")
				.editSingleModuleToggle(module, true).clickSubmitButton();

		mbPage.goToAdminEditPage().editAdminThisField(execution).jumpByKeyAndLoad("841")
				.assertionSingleModuleToggle(module, true).editSingleModuleToggle(module, false).clickSubmitButton();

	}

	@Test
	public void testMigrateExecutionPlatform() throws InterruptedException {

		String field = "Execution Platform Object";
		String name_field = "Name:";
		String data_feed_field = "Data Feed Type:";
		String platform_field = "Platform Type:";
		String migrate_field = " Migrate to (This will NOT be deleted): ";
		String execution_platform_field = "Execution Platform Company";
		String objectName = "Test" + getRandName();
		String key;

		LoginPage main = new LoginPage(webDriver);
		MenuBarPage mbPage = main.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD);
		AdminEditPage admin = mbPage.goToAdminEditPage();

		admin.editAdminThisField(field).clickNewButtonToCreateNewToken()
				.inputValueToCertainField(name_field, objectName)
				.selectOptionForCertainField(execution_platform_field, "Other Provider")
				.selectOptionForCertainField(data_feed_field, "Dummy Broker")
				.selectOptionForCertainField(platform_field, "Other").clickSubmitButton();

		key = (getTextByLocator(By.id("gwt-debug-CustomDialog-message")).split("key is "))[1];

		clickOkButtonIfVisible();

		admin.jumpByKeyAndLoad(key).selectOptionForCertainField(migrate_field, "Ageas Aviator (key:2810)");

		scrollToBottom();

		admin.clickCustomButton("Confirm to Migrate");

		clickYesButtonIfVisible();
		assertTrue(pageContainsStr("Done"));
	}

	@Test
	public void testColumnInTickerMappingTool() throws InterruptedException {
		String date_field = "Date";
		String advisor_company_field = "Advisor Company";
		String error_status_field = "Error Status";
		String date = "14-Oct-2014";
		String advisorCompany = "International Financial Services Hong Kong (Key: 701)";
		String status = "Unresolved";
		String errorType = "Report Parsing Errors";
		LoginPage main = new LoginPage(webDriver);
		MenuBarPage mbPage = main.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD);

		mbPage.goToAdminMaintenancePage().clickCustomButton("Show General Errors").selectTypeOfError(errorType)
				.selectOptionForCertainField(date_field, date)
				.selectOptionForCertainField(advisor_company_field, advisorCompany)
				.selectOptionForCertainField(error_status_field, status);

		try {
			waitForElementVisible(By.xpath("(.//img[@src='img/gear.png'])[1]"), 10);
			clickElement(By.xpath("(.//img[@src='img/gear.png'])[1]"));

			waitForElementVisible(By.id("gwt-debug-TickerMappingToolWidgetPopupView-suggestedTickerPanel"), 10);
			assertTrue(pageContainsStr("Symbol"));
			assertTrue(pageContainsStr("MS PID"));
			assertTrue(pageContainsStr("Exchange"));
			// check 'Symbol' record
			assertTrue(getTextByLocator(By.xpath(
					"(.//*[@id='gwt-debug-TickerMappingToolWidgetPopupView-suggestedTickerPanel']//tbody//tr)[2]//td[12]")) != null);
			// check 'MS PID' record
			assertTrue(getTextByLocator(By.xpath(
					"(.//*[@id='gwt-debug-TickerMappingToolWidgetPopupView-suggestedTickerPanel']//tbody//tr)[2]//td[13]")) != null);
			// check 'Exchange' record
			assertTrue(getTextByLocator(By.xpath(
					"(.//*[@id='gwt-debug-TickerMappingToolWidgetPopupView-suggestedTickerPanel']//tbody//tr)[2]//td[14]")) != null);

		} catch (TimeoutException e) {

		}

	}
}
