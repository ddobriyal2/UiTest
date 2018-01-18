package org.sly.uitest.sections.accounts;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.abstractpage.MenuBarPage;
import org.sly.uitest.pageobjects.clientsandaccounts.AnalysisPage;
import org.sly.uitest.pageobjects.clientsandaccounts.HoldingsPage;
import org.sly.uitest.pageobjects.investments.InvestmentsPage;
import org.sly.uitest.pageobjects.mysettings.UserPreferencePage;
import org.sly.uitest.settings.Settings;

/**
 * 
 * Tests the analysis tab for an account. <br>
 * <br>
 * Test is described here: <a href=
 * "https://docs.google.com/a/wismore.com/spreadsheets/d/1WEEiIIiVHNix_W5frGNpT53Rlv8l_g5XkyivjkjsIj4/edit#gid=0"
 * >here</a>
 * 
 * @author Julian Schillinger
 * @date : Jul 23, 2014
 * @company Prive Financial
 */
public class PortfolioAnalysisTest extends AbstractTest {

	@Test
	public void testAnalysisTab() throws Exception {
		setTabHoldingsAsDefault();

		// Init
		MenuBarPage menubar = new MenuBarPage(webDriver);
		boolean isLoggedAlready = true;

		HoldingsPage holdingsPage = menubar.goToAccountOverviewPage().simpleSearchByString("Selenium")
				.goToAccountHoldingsPageByName("Selenium Test");

		// clickElement(By.id("gwt-debug-PortfolioAllocationView-reallocateBtn"));
		if (pageContainsStr("This portfolio is linked to")) {

			holdingsPage.goToDetailsPage();
			if (pageContainsStr("Linked to: ")) {

				waitForElementVisible(By.xpath(".//*[@id='gwt-debug-ModelPortfolioSectionPresenter-image']"),
						Settings.WAIT_SECONDS * 2);
				clickElement(By.xpath(".//*[@id='gwt-debug-ModelPortfolioSectionPresenter-image']"));

				waitForElementVisible(By.xpath(".//*[@id='gwt-debug-ModelPortfolioWidget-modelUnLink']"),
						Settings.WAIT_SECONDS * 2);

				clickElement(By.xpath(".//*[@id='gwt-debug-ModelPortfolioWidget-modelUnLink']"));

				isLoggedAlready = false;
				this.waitForWaitingScreenNotVisible();
				checkLogout();
				handleAlert();

				LoginPage main = new LoginPage(webDriver);
				main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage()
						.simpleSearchByString("Selenium").goToAccountHoldingsPageByName("Selenium Test")
						.clickReallocateButton().clickAddInvestmentButton();
			}
		} else {
			menubar.goToAccountOverviewPage().simpleSearchByString("Selenium")
					.goToAccountHoldingsPageByName("Selenium Test").clickReallocateButton().clickAddInvestmentButton();
		}

		InvestmentsPage investments = new InvestmentsPage(webDriver, HoldingsPage.class);
		String investment = getTextByLocator(By.id("gwt-debug-ManagerListItem-strategyName"));

		HoldingsPage holding = ((HoldingsPage) investments.selectInvestmentByName(investment)
				.clickAddToPortfolioButton()).setNewAllocationForInvestment(investment, "10")
						.clickRebalancePreviewAndConfirm(investment).goToTransactionHistoryPage().confirmHistoryStatus()
						.goToHoldingsPage();
		// wait(30);
		// Find strategy names from holdings tab
		ArrayList<String> strategyNames = getStrategyNamesFromAccountsHoldingsTab();
		log("Found following startegy names on holdings page: " + strategyNames);
		log("Investment: " + investment);
		// change to analysis tab
		AnalysisPage analysis = holding.goToAnalysisPage();

		// make sure sections show up
		assertTrue(pageContainsStr("Selenium Test"));
		assertTrue(pageContainsStr("Sector Exposure"));
		assertTrue(pageContainsStr("Asset Class Exposure"));
		assertTrue(pageContainsStr("Regional Exposure"));

		assertTrue(pageContainsStr(investment));
		// assertTrue(pageContainsStr("Currency Exposure"));

		// make sure strategies show up in table in analysis tab

		String textInTable = this.getTextByLocator(By.id("gwt-debug-InvestorModelPortfolioPresenter-investmentTable"));
		for (String strategyName : strategyNames) {
			assertTrue("Strategy not found in table: " + strategyName, textInTable.contains(strategyName));
		}

		// reset allocation
		analysis.goToHoldingsPage().clickReallocateButton().setNewAllocationForInvestment(investment, "0")
				.clickRebalancePreviewAndConfirm(investment);
	}

	private void setTabHoldingsAsDefault() throws Exception {
		LoginPage main = new LoginPage(webDriver);
		main.loginAs(Settings.USERNAME, Settings.PASSWORD);
		UserPreferencePage userPreference = main.goToUserPreferencePage(MenuBarPage.class);
		waitForElementVisible(By.id("gwt-debug-UpdateUserSystemPreferenceView-landingPageListBox"), 10);

		userPreference.editLandingPageByName("Summary of Accounts").editAccountDetailDefaultTab("Holdings")
				.clickSubmitButton();

		this.waitForElementNotVisible(By.id("gwt-debug-UpdateUserSystemPreferenceView-submitButton"),
				Settings.WAIT_SECONDS);
	}

	/**
	 * Returns the strategy names in the accounts holdings tab. Expects the
	 * holdings tab to be already open (ie holdings table is already visible).
	 * Does not work in rebalance mode.
	 * gwt-debug-AllocationEditTableWidgetNormal-allocationTablePanel
	 */
	protected ArrayList<String> getStrategyNamesFromAccountsHoldingsTab() {
		ArrayList<String> strategyNames = new ArrayList<String>();
		List<WebElement> strategyLinks = waitGet(By.id("gwt-debug-PortfolioAllocationView-allocationTablePanel"))
				.findElements(By.className("allocationTableLink"));
		for (WebElement strategyLink : strategyLinks) {
			strategyNames.add(strategyLink.getText());
			// System.out.println(strategyNames);
		}
		return strategyNames;
	}
}
