package org.sly.uitest.sections.pageframework;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.abstractpage.MenuBarPage;
import org.sly.uitest.pageobjects.clientsandaccounts.AccountOverviewPage;
import org.sly.uitest.pageobjects.mysettings.PayFeePage;
import org.sly.uitest.settings.Settings;

/**
 * Basic tests which ensure pages are coming up and menu bar works.
 * 
 * @see <a href=
 *      "https://docs.google.com/a/wismore.com/spreadsheets/d/1CuSpAEXyLu1SNdBFqHdWrSOkC06VJIAkEJMovFPo8RE/edit#gid=0">
 *      here</a>
 * @author Brian Huie
 * @company Prive Financial
 */
public class NavigationTest extends AbstractTest {

	@Test
	public void testIconMenuBarNavigation() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		MenuBarPage navigation = main.loginAs(Settings.USERNAME, Settings.PASSWORD);

		log("Navigation test for SeleniumTest: BEGIN");

		/*
		 * Icon Menu bar (top right)
		 */

		navigation.goToUserProfilePage();
		assertTrue(pageContainsStr("User Profile"));
		log("User Settings. User Profile Screen: Checked");

		navigation.goToUserPreferencePage(MenuBarPage.class);
		assertTrue(pageContainsStr("User Preference"));
		log("User Settings. User Preference Screen: Checked");

		navigation.goToCompanySettingsPage();
		assertTrue(pageContainsStr("Company Settings"));
		log("Company Settings. Company Settings Screen: Checked");

		/*
		 * navigation.goToDashboardPage();
		 * assertTrue(pageContainsStr("Dashboard")); log(
		 * "Company Settings. Dashboard Screen: Checked");
		 */

		navigation.goToCustomFieldPage();
		assertTrue(pageContainsStr("Custom Field Definition"));
		log("Company Settings. Custom Fields Screen: Checked");

		navigation.goToEmailTemplatePage();
		assertTrue(pageContainsStr("Email Template Overview"));
		log("Company Settings. Email Template Screen: Checked");

		navigation.goToCustomAssetPage();
		assertTrue(pageContainsStr("Custom Asset"));
		log("Company Settings. Custom Asset Screen: Checked");

		navigation.goToCustomTagPage();
		assertTrue(pageContainsStr("Custom Tag"));
		log("Company Settings. Custom Tag Screen: Checked");

		navigation.goToUserAlertPage();
		assertTrue(pageContainsStr("MY ALERTS") || pageContainsStr("Alerts"));
		log("User Alert. Alert Screen: Checked");

		/* Icon: Processes */
		log("Processes. Process Popup: In");
		navigation.goToProcess();
		assertTrue(pageContainsStr("There are no background processes running at the moment")
				|| pageContainsStr("Remove") || pageContainsStr("Download"));
		log("Processes. Process Popup: Checked");

	}

	@Test
	public void testNormalMenuBarNavigation() throws Exception {
		// wait(3);
		LoginPage main = new LoginPage(webDriver);
		// wait(3);
		MenuBarPage navigation = main.loginAs(Settings.USERNAME, Settings.PASSWORD);

		log("Navigation test for SeleniumTest: BEGIN");

		/*
		 * Normal Menu Bar
		 */
		/* Normal: Clients */
		// wait(2);
		// navigation.goToClientsDEFAULTPage();
		// assertTrue(pageContainsStr(" Summary of Clients "));
		// log("Clients. Default Screen: Checked");
		// wait(2);
		navigation.goToClientOverviewPage();
		assertTrue(pageContainsStr(" Summary of Clients "));
		log("Clients. Overview Screen: Checked");
		// wait(2);
		navigation.goToAccountOverviewPage();
		assertTrue(pageContainsStr(" Summary of Accounts "));
		log("Clients. Overview Screen: Checked");
		// wait(2);
		navigation.goToOpportunitiesPage();
		assertTrue(pageContainsStr(" Opportunities "));
		assertTrue(pageContainsStr("New Opportunity"));
		log("Clients. Opportunity Screen: Checked");
		// wait(2);
		navigation.goToNewBusinessPage();
		assertTrue(pageContainsStr("Pending New Business Processes"));
		log("Clients. New Business Screen: Checked");
		// wait(2);
		/* Normal: Explore */
		// navigation.goToExploreDEFAULTPage();
		// assertTrue(pageContainsStr("Investments"));
		// log("Explore. Default Page: checked");
		// wait(2);
		navigation.goToInvestmentsPage();
		assertTrue(pageContainsStr("Investments"));
		log("Explore. Investment Page: checked");
		// wait(2);
		navigation.goToModelPortfoliosPage();
		assertTrue(pageContainsStr(" Model Portfolios "));
		log("Explore. Model Portfolio Screen: Checked");

		/* Normal: Manage */
		// Tasks screen
		// log("Manage. Default Screen: In");
		// wait(2);
		// navigation.goToManageDEFAULTPage();
		// assertTrue(pageContainsStr(" Tasks "));
		// assertTrue(pageContainsStr("Include completed tasks"));
		// log("Manage. Default Screen: Checked");

		log("Manage. Tasks Screen: In");
		// wait(2);
		navigation.goToTasksPage();
		assertTrue(pageContainsStr(" Tasks "));
		assertTrue(pageContainsStr("Include completed tasks"));
		log("Manage. Tasks Screen: Checked");

		// Calendar Screen
		log("Manage. Calendar Screen: In");
		// wait(2);
		navigation.goToCalendarPage();
		assertTrue(pageContainsStr("Calendar Events"));
		log("Manage. Calendar Screen: Checked");

		// advisor performance screen
		log("Manage. Advisor Performance Screen: In");
		// wait(2);
		navigation.goToAdvisorPerformancePage();
		assertTrue(pageContainsStr("Overview"));
		assertTrue(pageContainsStr("Indemnification Release"));
		log("Manage. Advisor PerformanceScreen: Checked");

		// white lists
		log("Manage. White List Screen: In");
		// wait(2);
		navigation.goToWhiteListsPage();
		assertTrue(pageContainsStr("White Lists"));
		assertTrue(pageContainsStr("Create White List"));
		log("Manage. White List Screen: Checked");

		// Employee
		log("Manage. Employee Screen: In");
		// wait(2);
		navigation.goToEmployeePage();
		assertTrue(pageContainsStr(" Employee "));

		// document screen
		log("Manage. Document Screen: In");
		// wait(2);
		navigation.goToDocumentsPage();
		assertTrue(pageContainsStr("Document Binder Name") || pageContainsStr("Documents"));
		log("Manage. Document Screen: Checked");

		// alerts screen
		log("Manage. Alert Screen: In");
		// wait(2);
		navigation.goToAlertsPageFromManage();
		assertTrue(pageContainsStr("Alert Definitions"));
		log("Manage. Alert Screen: Checked");

		// rebalancing screen
		log("Manage. Rebalancings Screen: In");
		// wait(2);
		navigation.goToRebalancingsPage();
		assertTrue(pageContainsStr("Model Portfolio Bulk Rebalance Overview"));
		log("Manage. Rebalancings Screen: Checked");

		/* Normal: ` */

		/* Normal: Accounting */
		// wait(2);
		// navigation.goToAccountingDEFAULTPage();
		// log("Accounting. Default Screen: Checked");
		// wait(2);
		navigation.goToClientFeesPage();
		log("Accounting. Client Fees Screen: Checked");
		// wait(2);
		navigation.goToAdvisorCommissionPage();
		log("Accounting. Advisor Commissions: Checked");
		// wait(2);
		navigation.goToCompanyCommissionPage();
		log("Accounting. Advisor Commissions: Checked");
		// wait(2);
		navigation.goToIndemnityReleasesPage();
		log("Accounting. Idemnity Screen: Checked");
		// wait(2);
		navigation.goToCashflowOverviewPage();
		log("Accounting. Cashflow Overview Screen: Checked");
		// wait(2);
		navigation.goToTrailFeeAgreementPage();
		log("Accounting. Trail Fees: Checked");

		/* Normal: Operations */
		// wait(2);
		// navigation.goToOperationsDEFAULTPage();
		// log("Operations. Default Screen: Checked");
		// wait(2);
		navigation.goToBulkEmailPage();
		log("Operations. Bulk Email: Checked");

		// Reporting Overview
		log("Operations. Check Reporting Overview: In");
		// wait(2);
		navigation.goToReportingOverviewPage();
		assertTrue(pageContainsStr("Reporting Overview"));
		log("Operations. Check Reporting Overview: Out");

		// check value spikes screen
		log("Operations. Check Value Spikes: In");
		// wait(2);
		navigation.goToAccountValueSpikesPage();
		assertTrue(pageContainsStr("Check Account Value Spike"));
		log("Operations. Check Value Spikes: Checked");

		// data reconciliation screen
		log("Operations. Data Reconciliation: In");
		// wait(2);
		navigation.goToDataReconciliationPage();
		assertTrue(pageContainsStr("Data Reconciliation"));
		log("Operations. Data Reconciliation: Checked");

		// user activity screen
		log("Operations. User Activity: In");
		// wait(2);
		navigation.goToUserActivityPage();
		assertTrue(pageContainsStr("User Activity"));
		log("Operations. User Activity: Checked");

		/* Normal: Build */
		// wait(2);
		// navigation.goToBuildDEFAULTPage();
		// log("Build. Default Screen: Checked");
		// wait(2);
		navigation.goToModelPortfoliosPage().clickManageModelPortfolios();
		log("Build. Model Portfolios Screen: Checked");
		// wait(2);
		navigation.goToVFundsPage();
		log("Build. vFunds Screen: Checked");

		// strategy rules screen
		log("Build. Strategy Rules Screen: In");
		// wait(2);
		navigation.goToStrategyRulesPage();
		assertTrue(pageContainsStr("My Strategy Rules"));
		log("Build. Strategy Rules: Checked");
		// wait(2);
		navigation.goToStructuredProductPage();
		log("Build. Structured Products Screen: Checked");

		// market equities screen
		log("Build. Market Equities Screen: In");
		// wait(2);
		navigation.goToMarketEquitiesPage();
		assertTrue(pageContainsStr("Market Environment Equities"));
		log("Build. Market Equities Screen: Checked");

		// market rates screen
		log("Build. Market Rates Screen: In");
		// wait(2);
		navigation.goToMarketRatesPage();
		assertTrue(pageContainsStr("Market Environment Rates"));
		log("Build. Market Rates Screen: Checked");

		/*
		 * Test Log Out
		 */

		// hit log out button
		this.checkLogout();
		// wait(Settings.WAIT_SECONDS);
		handleAlert();
		// wait(3);
		// make sure login page shows again
		if (webDriver.getCurrentUrl().indexOf("MATERIAL") > -1) {
			waitForElementVisible(By.xpath(".//*[@id='page-wrapper']/div[1]/nav/div/a/i"), 30);
		} else {
			waitGet(By.id("gwt-debug-LoginPanel-loginButton"));
		}

		log("Navigation test for SeleniumTest: DONE");
	}

	@Test
	public void testNavigationForInvestor() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		MenuBarPage navigation = main.loginAs(Settings.INVESTOR_USERNAME, Settings.INVESTOR_PASSWORD);

		log("Navigation test for SeleniumInvestor: BEGIN");

		/*
		 * Icon Menu bar (top right)
		 */

		/* Icon: User Settings */

		// user profile screen
		// wait(2);
		navigation.goToUserProfilePage();
		assertTrue(pageContainsStr("Login Credential"));
		log("User Settings. User Profile Screen: Checked");

		// user preference screen
		// wait(2);
		navigation.goToUserPreferencePage(AccountOverviewPage.class);
		assertTrue(pageContainsStr("User Preference"));
		log("User Settings. User Preference Screen: Checked");

		// personal vFund screen
		// wait(2);
		navigation.goToPersonalvFundPage();
		assertTrue(pageContainsStr("My Personal vFunds"));
		log("User Settings. Personal vFund Screen: Checked");

		// pay fees screen
		// wait(2);
		PayFeePage pay = navigation.goToPayFeePage();
		assertTrue(pageContainsStr("Pay Fee"));
		log("User Settings. Pay Fees Screen: Checked");
		pay.clickCancelButton();

		/* Icon: Your Financial Advisor */
		// your financial advisor menu popup
		log("Your Financial Advisor. Popup: In");
		// wait(2);
		navigation.goToYourFinancialAdvisor();
		assertTrue(this.getTextByLocator(By.className("advisorImageMenuPopupTitle")).equals("Your Financial Advisor"));
		log("Your Financial Advisor. Popup: Checked");

		/* Icon: Processes */
		log("Processes. Process Popup: In");
		// wait(2);
		navigation.goToProcess();
		assertTrue(pageContainsStr("There are no background processes running at the moment"));
		log("Processes. Process Popup: Checked");

		// remove the process dialog
		if (isElementVisible(By.xpath(".//*[@id='page-wrapper']/div[1]/nav/div/a/i"))) {

			clickElement(By.xpath("//*[@class='dropdown-toggle count-info' and @title='Processes']"));
		} else {

			clickElementAndWait3(By.xpath(".//*[@id='gwt-debug-ProcessImageMenuButton-displayImageLink']/img"));
		}
		/*
		 * Normal Menu Bar
		 */
		/* Normal: Accounts */
		// log("Accounts. Default Screen: In");
		// wait(2);
		// navigation.goToAccountsDEFAULTPage();
		// assertTrue(pageContainsStr("Summary of Accounts"));
		// log("Accounts. Default Screen: Checked");

		log("Accounts. Overview Screen: In");
		// wait(3);
		navigation.goToAccountOverviewPage();
		assertTrue(pageContainsStr("Summary of Accounts"));
		log("Accounts. Overview Screen: Checked");

		/* Normal: Explore */

		// log("Explore. Default Page: In");
		// wait(2);
		// navigation.goToExploreDEFAULTPage();
		// assertTrue(pageContainsStr("Investments"));
		// // assertTrue(pageContainsStr("1Y Volatility"));
		// log("Explore. Default Page: checked");

		// investments page
		log("Explore. Investments Page: In");
		navigation.goToInvestmentsPage();
		assertTrue(pageContainsStr("Investments"));
		// assertTrue(pageContainsStr("1Y Volatility"));
		log("Explore. Investment Page: checked");

		// explore model portfolio screen
		log("Explore. Model Portfolio Screen: In");
		// wait(2);
		navigation.goToModelPortfoliosPage();
		assertTrue(pageContainsStr("Model Portfolios"));
		log("Explore. Model Portfolio Screen: Checked");

		/* Normal: Plan */

		// log("Plan. Default Page: In");
		// wait(2);
		// navigation.goToPlanDEFAULTPage();
		// assertTrue(pageContainsStr("Risk Exposure"));
		// log("Plan. Default Page: checked");

		// risk profile page
		log("Plan. Risk Profile Screen: In");
		// wait(2);
		navigation.goToRiskProfilePage();
		assertTrue(pageContainsStr("Risk Exposure"));
		log("Plan. Risk Profile Screen: checked");

		// wealth planning screen

		/*
		 * Test Log Out
		 */
		this.checkLogout();
		// wait(Settings.WAIT_SECONDS);
		handleAlert();

		// make sure login page shows again
		if (webDriver.getCurrentUrl().indexOf("MATERIAL") > -1) {
			waitForElementVisible(By.xpath(".//*[@id='page-wrapper']/div[1]/nav/div/a/i"), 30);
		} else {
			waitGet(By.id("gwt-debug-LoginPanel-loginButton"));
		}

		log("Navigation test for SeleniumInvestor: DONE");
	}

	@Test
	public void testHelpPage() throws Exception {

		goToHelpPageAndDoAssertion(Settings.CONSULTANT_USERNAME, Settings.CONSULTANT_PASSWORD);

		goToHelpPageAndDoAssertion(Settings.USERNAME, Settings.PASSWORD);
	}

	public void goToHelpPageAndDoAssertion(String username, String password) throws Exception {

		LoginPage main = new LoginPage(webDriver);
		main.loginAs(username, password);

		String winHandleBefore = webDriver.getWindowHandle();

		main.goToHelpPage();
		handleAlert();
		for (String winHandle : webDriver.getWindowHandles()) {
			webDriver.switchTo().window(winHandle);
		}

		waitForElementVisible(By.id("sites-chrome-userheader-title"), 10);

		assertTrue(pageContainsStr("Help Center"));

		webDriver.close();

		webDriver.switchTo().window(winHandleBefore);

		checkLogout();
		handleAlert();
	}
}
