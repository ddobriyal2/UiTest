package org.sly.uitest.sections.accounts;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.clientsandaccounts.AccountOverviewPage;
import org.sly.uitest.pageobjects.clientsandaccounts.AnalysisPage;
import org.sly.uitest.pageobjects.clientsandaccounts.CRMPage;
import org.sly.uitest.pageobjects.clientsandaccounts.CashflowPage;
import org.sly.uitest.pageobjects.clientsandaccounts.DetailPage;
import org.sly.uitest.pageobjects.clientsandaccounts.HistoryPage;
import org.sly.uitest.pageobjects.clientsandaccounts.HoldingsPage;
import org.sly.uitest.settings.Settings;

/**
 * Tests the page once you clicked into an account. * <br>
 * <br>
 * Test is described here: <a href=
 * "https://docs.google.com/a/wismore.com/spreadsheets/d/1WEEiIIiVHNix_W5frGNpT53Rlv8l_g5XkyivjkjsIj4/edit#gid=0"
 * >here</a>
 * 
 * @author Julian Schillinger
 * @date : Jan 2, 2014
 * @company Prive Financial
 */
public class PortfolioOverviewTest extends AbstractTest {

	// @Before
	// public void wait10() throws InterruptedException {
	// wait(10);
	//
	// }

	/** Tests the tabs and ensures that the content for each tab shows up. */
	@Test
	public void testTabs() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		AccountOverviewPage overview = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage()
				.simpleSearchByString("Selenium Test");

		assertTrue("Expected specific investor result.", pageContainsStr("Investor, Selenium"));
		assertTrue("Expected specific account result.", pageContainsStr("Selenium Test"));

		assertTrue("Only expected one client result.", pageContainsStr("1 - 1 of 1 Results"));

		HoldingsPage holdings = overview.goToAccountHoldingsPageByName("Selenium Test");

		// holdings tab
		assertTrue(this.getTextByLocator(By.className("TabLayoutPanelTabNormal")).equals("HOLDINGS"));
		assertTrue(pageContainsStr("Cash"));
		assertTrue(pageContainsStr("Total"));
		assertTrue(pageContainsStr("Historical Valuation of Portfolio"));

		// analysis tab
		AnalysisPage analysis = holdings.goToAnalysisPage();
		assertTrue(pageContainsStr("Performance"));
		assertTrue(pageContainsStr("Volatility"));

		// history tab
		HistoryPage history = analysis.goToTransactionHistoryPage();
		assertTrue(pageContainsStr("Date"));
		assertTrue(pageContainsStr("Status"));

		// details
		DetailPage detail = history.goToDetailsPage();
		assertTrue(pageContainsStr("Details"));
		assertTrue(pageContainsStr("Advisor Info"));
		assertTrue(pageContainsStr("Account Info"));
		assertTrue(pageContainsStr("Advisory Fees"));
		assertTrue(pageContainsStr("Workflows And Actions"));
		assertTrue(pageContainsStr("Model Portfolio"));
		// assertTrue(pageContainsStr("Currently, this portfolio is not linked
		// to a model portfolio"));
		assertTrue(pageContainsStr("Access Rights"));
		assertTrue(pageContainsStr("Risk Profile"));
		assertTrue(pageContainsStr("Workflows And Actions"));
		assertTrue(pageContainsStr("Payment Method"));
		assertTrue(pageContainsStr("Confirmations"));
		assertTrue(pageContainsStr("Wealth Planning"));

		// CRM
		CRMPage crm = detail.goToCRMPage();
		assertTrue(pageContainsStr("Compliance"));
		assertTrue(pageContainsStr("Tasks"));
		assertTrue(pageContainsStr("Upcoming Calendar Events"));
		assertTrue(pageContainsStr("Emails"));
		assertTrue(pageContainsStr("Binders"));

		// Cashflows
		CashflowPage cashflow = crm.goToCashflowPage();
		assertTrue(pageContainsStr("Cashflow Schedules"));
		assertTrue(pageContainsStr("Single Cashflows"));
		waitGet(By.id("gwt-debug-DepositWithdrawalHistoryPresenter-customButton"));

		// Alerts
		cashflow.goToAlertsPageFromManage();
		assertTrue(pageContainsStr(" Triggered Alerts "));
		assertTrue(pageContainsStr("Alert Definitions"));
		waitGet(By.id("gwt-debug-UserAlertOverviewView-addAdvisorCompanyAlertButton"));
		assertTrue(pageContainsStr("My Alerts"));

	}

}
