package org.sly.uitest.sections.commission;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.commissioning.FeeOverviewPage;
import org.sly.uitest.pageobjects.commissioning.NewBusinessEventEditPage;
import org.sly.uitest.settings.Settings;

/**
 * Test the commission related screens.
 * 
 * @author Jackie Lee,edited by nandi
 * @date : Jul 28, 2014
 * @company Prive Financial
 */
public class CommissionTest extends AbstractTest {

	@Test
	public void testCommissionPageLoad() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		FeeOverviewPage commissionPage = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToClientFeesPage()
				.expandAccountByName("Generali Vision - NEW").goToProductProviderAccPage("Generali Vision - NEW")
				.goToAdvisorCommissionPage().goToCompanyCommissionPage().expandAccountByName("SeleniumTest");

		assertTrue("Expected specific accounts.", pageContainsStr("Via Product Provider Acc"));

		assertTrue("Expected specific account.", pageContainsStr("Outside Product Provider Acc"));

		assertTrue("Expected specific accounts.", pageContainsStr("Reconciliation Acc"));

		assertTrue("Expected specific account.", pageContainsStr("Operating Revenues"));

		commissionPage.goToReconciliationAccPage("SeleniumTest").goToIndemnityReleasesPage().goToCashflowOverviewPage()
				.goToTrailFeeAgreementPage();

	}

	@Test
	public void testCreatingNewEntry() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToCompanyCommissionPage()
				.expandAccountByName("SeleniumTest").goToReconciliationAccPage("SeleniumTest");

		String date = "26-Sep-2014";

		String value = "50.00";

		this.waitForWaitingScreenNotVisible();

		if (!pageContainsStr("No Fee Entries")) {
			// test delete the new entry
			this.clickElement(By.xpath("//input[@id='gwt-debug-FeeListPresenter-titleBox-input']"));

			clickElement(By.id("gwt-debug-FeeListView-delEntryBtn"));

			clickElement(By.id("gwt-debug-CustomDialog-yesButton"));
		}

		clickElement(By.id("gwt-debug-FeeListView-newEntryBtn"));

		this.sendKeysToElement(By.id("gwt-debug-FeeEditWidget-feeEffectiveDate"), date);

		this.sendKeysToElement(By.id("gwt-debug-FeeEditWidget-feeAmount"), value);

		clickElement(By.id("gwt-debug-FeeEditWidget-submitBtn"));

		this.waitForWaitingScreenNotVisible();

		assertTrue("Expected specific date.",
				date.equals(this.getTextByLocator(By.id("gwt-debug-SortableFlexTableAsync-table-1-4"))));

		assertTrue("Expected specific currency.",
				"USD".equals(this.getTextByLocator(By.id("gwt-debug-SortableFlexTableAsync-table-1-9"))));

		clickElement(By.id("gwt-debug-FeeListView-recalculateBtn"));

		clickElement(By.id("gwt-debug-CustomDialog-yesButton"));

		clickElement(By.id("gwt-debug-CustomDialog-okButton"));

		this.waitForWaitingScreenNotVisible();

		this.refreshPage();

		assertTrue("Expected specific amount.",
				("USD " + value).equals(this.getTextByLocator(By.id("gwt-debug-FeeListView-totalFeesOutstanding"))));

		assertTrue("Expected specific amount.", pageContainsStr(value));

		// test delete the new entry
		this.clickElement(By.xpath("//input[@id='gwt-debug-FeeListPresenter-titleBox-input']"));

		// clickElement(By.xpath("//button[@title='Delete Entry']"));
		clickElement(By.id("gwt-debug-FeeListView-delEntryBtn"));

		clickElement(By.id("gwt-debug-CustomDialog-yesButton"));

		assertTrue("Expected no entry", pageContainsStr("No Fee Entries"));

	}

	@Test
	public void testAdvancedSearchInCashflowOverview() throws Exception {
		String nameForSavedSearch = "default" + getRandName();
		LoginPage main = new LoginPage(webDriver);

		main.loginAs(Settings.CRM_USERNAME, Settings.CRM_PASSWORD).goToCashflowOverviewPage();

		sendKeysToElement(By.id("gwt-debug-CashflowListView-searchBox"),
				"Provider:\"Friends Provident\" StartDate:\"1-Sep-2014\" EndDate:\"26-Sep-2014\"\n");

		waitForElementVisible(By.id("gwt-debug-DepositWithdrawalEventTable-tablePanel"), 30);

		assertTrue("Expected a item ", pageContainsStr("Scheme Renewal"));

		clickElement(By.id("gwt-debug-CashflowListView-advancedSearchBtn"));

		clickElement(By.id("gwt-debug-AdvancedSearchPanel-saveButton"));
		sendKeysToElement(By.id("gwt-debug-AdvancedSearchPanel-presetNameTextBox"), nameForSavedSearch);
		clickElement(By.xpath(".//*[@id='gwt-debug-CustomDialog-customButton' and .='Save']"));

		// go to other page and go back this page to test the "Preset"
		scrollToTop();

		// wait(2);

		// test Cashflow Overview
		main.goToCashflowOverviewPage();

		// wait(2);

		clickElement(By.id("gwt-debug-CashflowListView-advancedSearchBtn"));

		clickElement(By.xpath("//div[@class='gwt-HTML' and .='Saved Searches']"));

		selectFromDropdown(By.id("gwt-debug-AdvancedSearchPanel-presetsListBox"), nameForSavedSearch);

		clickElement(By.id("gwt-debug-AdvancedSearchPanel-searchPresetButton"));

		// wait(2);

		assertTrue("Expected a item ", pageContainsStr("Scheme Renewal"));

		clickElement(By.id("gwt-debug-CashflowListView-advancedSearchBtn"));

		clickElement(By.xpath("//div[@class='gwt-HTML' and .='Saved Searches']"));

		selectFromDropdown(By.id("gwt-debug-AdvancedSearchPanel-presetsListBox"), nameForSavedSearch);

		clickElement(By.id("gwt-debug-AdvancedSearchPanel-deleteButton"));

		clickYesButtonIfVisible();

	}

	@Test
	public void testRegularPremiumAndIndemnityGrayedOut() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		// 1. check Platform Category = Baby Bonds
		NewBusinessEventEditPage babyBonds = main.loginAs(Settings.CRM_USERNAME, Settings.CRM_PASSWORD)
				.goToNewBusinessPage().clickNewButtonToCreateNewBusniess().selectExistingClient("12042")
				.editPlatformCategory("Baby Bonds").editProductPlatform("AXA Honey");

		// Indemnity Start
		assertTrue(!webDriver.findElement(By.id("gwt-debug-BusinessEventEditWidgetView-indemnityDateStartTextBox"))
				.isEnabled());

		// Indemnity (months)
		assertTrue(!webDriver.findElement(By.id("gwt-debug-BusinessEventEditWidgetView-indemnityTextBox")).isEnabled());

		// Indemnity End
		assertTrue(!webDriver.findElement(By.id("gwt-debug-BusinessEventEditWidgetView-indemnityDateEndTextBox"))
				.isEnabled());

		// Regular Premium

		assertTrue(!waitGet(By.id("gwt-debug-BusinessEventEditWidgetView-premiumTextBox")).isEnabled());

		// 2. check Platform Category = Portfolio Bonds
		babyBonds.editPlatformCategory("Portfolio Bonds").editProductPlatform("AXA Evolution");

		waitGet(By.id("gwt-debug-BusinessEventEditWidgetView-indemnityDateStartTextBox"));
		// Indemnity Start
		assertTrue(!waitGet(By.id("gwt-debug-BusinessEventEditWidgetView-indemnityDateStartTextBox")).isEnabled());

		// Indemnity (months)
		assertTrue(!waitGet(By.id("gwt-debug-BusinessEventEditWidgetView-indemnityTextBox")).isEnabled());

		// Indemnity End
		assertTrue(!waitGet(By.id("gwt-debug-BusinessEventEditWidgetView-indemnityDateEndTextBox")).isEnabled());

		// Regular Premium
		assertTrue(!waitGet(By.id("gwt-debug-BusinessEventEditWidgetView-premiumTextBox")).isEnabled());

	}
}
