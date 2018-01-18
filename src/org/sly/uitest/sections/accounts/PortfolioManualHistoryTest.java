package org.sly.uitest.sections.accounts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.abstractpage.MenuBarPage;
import org.sly.uitest.pageobjects.clientsandaccounts.DetailPage;
import org.sly.uitest.pageobjects.clientsandaccounts.HistoryPage;
import org.sly.uitest.pageobjects.clientsandaccounts.HoldingsPage;
import org.sly.uitest.pageobjects.investments.InvestmentsPage;
import org.sly.uitest.settings.Settings;

/**
 * Makes sure the manual portfolio update works
 * 
 * @author Julian Schillinger
 * @date : Jan 8, 2015
 * @company Prive Financial
 */
public class PortfolioManualHistoryTest extends AbstractTest {

	@Test
	public void testDeleteManualPortfolioHistory() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		HoldingsPage holdingsPage = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage()
				.simpleSearchByString("Generali").goToAccountHoldingsPageByName("Generali Vision - NEW");
		clickOkButtonIfVisible();
		DetailPage detailPage = holdingsPage.goToDetailsPage();

		this.switchUpdateSource("Manual");
		this.waitForWaitingScreenNotVisible();
		HistoryPage history = detailPage.goToPortfolioHistoryPage().addManualPortfolioHistory();

		this.waitForElementVisible(By.id("gwt-debug-ManualPortfolioHistoryEditPopupView-effectiveDate"),
				Settings.WAIT_SECONDS);
		String date = getInputByLocator(By.id("gwt-debug-ManualPortfolioHistoryEditPopupView-effectiveDate"));

		history.clickManualHistoryDeleteAssetIcon().clickManualHistoryAddAssetIcon().clickManualHistorySelectAssetIcon()
				.addTickerInSearchTab("Other", "DE000A0KEYG6");

		assertEquals(getTextByLocator(By.id("gwt-debug-ManualAssetWidgetView-assetLabel")),
				"RP Immobilienanlagen & Infrastruktur T");

		((HistoryPage) history.clickManualHistoryDeleteAssetIcon().clickManualHistoryAddAssetIcon()
				.clickManualHistorySelectAssetIcon().addTicker("Hong Kong Exchanges and Clearing Ltd", "5"))
						.editManualHistoryAssetQuantity("10").clickManualHistoryRefreshIcon();

		waitForElementVisible(By.id("gwt-debug-ManualPortfolioHistoryEditPopupView-portfolioValueTextBox"), 5);
		String currency = getTextByLocator(By.id("gwt-debug-ManualPortfolioHistoryEditPopupView-baseCurrencyLabel"));
		String portfolioValue = getTextByLocator(
				By.id("gwt-debug-ManualPortfolioHistoryEditPopupView-portfolioValueTextBox"));
		log(portfolioValue);
		history.clickManualHistorySaveButton();

		int size = getSizeOfElements(By.xpath(".//*[@id='gwt-debug-ManualPortfolioHistoryPresenter-deleteButton']"));

		for (int i = 0; i <= size; i++) {
			try {

				assertEquals(getTextByLocator(By.xpath("//td[.='" + date + "']/following-sibling::td[1]")),
						currency + " " + portfolioValue);

				break;
			} catch (Exception e) {
				continue;
			}
		}

		history.clickManualHistoryDeleteButton(date, portfolioValue);

	}

	/**
	 * Runs the test sated under "Ensure Manual Portfolio History Edit work" in
	 * below sheet of the "portfolio Management test plan". <br>
	 * https://docs.google.com/a/wismore.com/spreadsheets/d/1
	 * WEEiIIiVHNix_W5frGNpT53Rlv8l_g5XkyivjkjsIj4/edit#gid=0
	 */
	@Test
	public void testManualPortfolioUpdate() throws Exception {

		LoginPage main = new LoginPage(webDriver);
		//
		// waitForElementVisible(By.id("gwt-debug-SlyUser-localeListBox"),
		// Settings.WAIT_SECONDS);
		// selectFromDropdown(By.id("gwt-debug-SlyUser-localeListBox"),
		// "English");
		// wait(Settings.WAIT_SECONDS);

		main.loginAs(Settings.USERNAME, Settings.PASSWORD);

		HoldingsPage holdingsPage = main.goToAccountOverviewPage().simpleSearchByString("Generali")
				.goToAccountHoldingsPageByName("Generali Vision - NEW");
		clickOkButtonIfVisible();
		holdingsPage.goToDetailsPage();
		// clickElement(By.xpath(".//*[@id='gwt-debug-InvestorAccountDetailsSectionPresenter-image']"));
		switchUpdateSource("Manual");
		this.waitForWaitingScreenNotVisible();

		checkLogout();
		handleAlert();

		// Init
		init();
		MenuBarPage menu = new MenuBarPage(webDriver);
		DetailPage details = menu.goToDetailsPage().goToEditPageByField("Details").editAccountUpdateSource("Automatic")
				.clickSaveButton_AccountDetail();
		details.goToTransactionHistoryPage();

		details.goToDetailsPage().goToEditPageByField("Details").editAccountUpdateSource("Manual")
				.clickSaveButton_AccountDetail();

		details.goToPortfolioHistoryPage();
		assertTrue(pageContainsStr("Portfolio History"));

		// delete all existing entries
		deleteAllManualHistEntries();

		// add entry
		clickElement(By.id("gwt-debug-ManualPortfolioHistoryView-addButton"));

		// add asset
		clickElement(By.id("gwt-debug-ManualPortfolioHistoryEditPopupView-addAssetButton"));

		// click search icon to pick asset
		clickElement(By.id("gwt-debug-ManualAssetWidgetView-exploreImg"));

		this.waitForWaitingScreenNotVisible();
		// add first asset
		InvestmentsPage investmentsPage = new InvestmentsPage(webDriver);

		String investment = getTextByLocator(By.xpath("(.//*[@id='gwt-debug-ManagerListItem-strategyName'])[1]"));

		investmentsPage.selectInvestmentByNameNewView(investment);

		// make sure price shows up
		// wait(Settings.WAIT_SECONDS);
		if (waitGet(By.id("gwt-debug-ManualAssetWidgetView-priceTextBox")).getAttribute("value").trim().equals(""))
			throw new RuntimeException("No price showed up");

		// fill in quantity
		sendKeysToElement(By.id("gwt-debug-ManualAssetWidgetView-quantityTextBox"), "10");
		// waitGet(By.id("gwt-debug-ManualAssetWidgetView-quantityTextBox"))
		// .sendKeys("10");

		// make sure portfolio value
		if (waitGet(By.id("gwt-debug-ManualPortfolioHistoryEditPopupView-portfolioValueTextBox"))
				.getAttribute("value") != null)
			throw new RuntimeException("Portfolio Value should be blank before hitting refresh");

		// hit refresh
		clickElement(By.id("gwt-debug-ManualPortfolioHistoryEditPopupView-recalcButton"));

		this.waitForElementVisible(By.id("gwt-debug-ManualPortfolioHistoryEditPopupView-portfolioValueTextBox"),
				Settings.WAIT_SECONDS);

		// make sure portfolio value is not blank

		String currentPortValue = this
				.getTextByLocator(By.id("gwt-debug-ManualPortfolioHistoryEditPopupView-portfolioValueTextBox")).trim();
		if (currentPortValue.equals(""))
			throw new RuntimeException("Portfolio Value should NOT be blank after hitting refresh");

		// change quantity and make sure that portfolio value changed
		sendKeysToElement(By.id("gwt-debug-ManualAssetWidgetView-quantityTextBox"), "11");
		// waitGet(By.id("gwt-debug-ManualAssetWidgetView-quantityTextBox"))
		// .sendKeys("11");

		clickElement(By.id("gwt-debug-ManualPortfolioHistoryEditPopupView-recalcButton"));

		this.waitForElementVisible(By.id("gwt-debug-ManualPortfolioHistoryEditPopupView-portfolioValueTextBox"),
				Settings.WAIT_SECONDS);
		String currentPortValue2 = this
				.getTextByLocator(By.id("gwt-debug-ManualPortfolioHistoryEditPopupView-portfolioValueTextBox")).trim();
		log("currentPortValue2 " + currentPortValue2);

		if (currentPortValue.equals(currentPortValue2))
			throw new RuntimeException("Portfolio Value should hava changed after updating quantity");

		// add cash
		clickElement(By.id("gwt-debug-ManualPortfolioHistoryEditPopupView-addCashButton"));

		selectFromDropdown(By.id("gwt-debug-ManualCashWidgetView-currencyListBox"), "USD");
		sendKeysToElement(By.id("gwt-debug-ManualCashWidgetView-quantityTextBox"), "100");
		// waitGet(By.id("gwt-debug-ManualCashWidgetView-quantityTextBox"))
		// .sendKeys("100");

		// make sure that portfolio value changed after adding cash
		clickElement(By.id("gwt-debug-ManualPortfolioHistoryEditPopupView-recalcButton"));

		this.waitForElementVisible(By.id("gwt-debug-ManualPortfolioHistoryEditPopupView-portfolioValueTextBox"),
				Settings.WAIT_SECONDS);
		String currentPortValue3 = this
				.getTextByLocator(By.id("gwt-debug-ManualPortfolioHistoryEditPopupView-portfolioValueTextBox")).trim();

		if (currentPortValue2.equals(currentPortValue3))
			throw new RuntimeException("Portfolio Value should hava changed after adding cash");

		// save entry
		clickElement(By.id("gwt-debug-ManualPortfolioHistoryEditPopupView-submitButton"));

		clickElement(By.id("gwt-debug-CustomDialog-okButton"));

		// make sure new entry shows up
		log(currentPortValue3);
		waitForElementVisible(By.id("gwt-debug-SortableFlexTableAsync-table"), 10);
		assertTrue(pageContainsStr("Portfolio Value"));
		assertTrue(pageContainsStr(convertToCurrency(Double.valueOf(currentPortValue3))));

		// delete all existing entries
		deleteAllManualHistEntries();

	}

	/**
	 * Deletes all the manual portfolio update entries from page.
	 * 
	 * @throws InterruptedException
	 */
	private void deleteAllManualHistEntries() throws InterruptedException {
		boolean br = false;
		this.waitForWaitingScreenNotVisible();
		while (!br) {

			try {
				waitForElementVisible(By.id("gwt-debug-ManualPortfolioHistoryView-contentPanel"), 20);
				WebElement container = waitGet(By.id("gwt-debug-ManualPortfolioHistoryView-contentPanel"));
				if (container == null)
					break;
				waitForElementVisible(By.id("gwt-debug-ManualPortfolioHistoryPresenter-deleteButton"), 10);
				WebElement deleteButton = waitGet(By.id("gwt-debug-ManualPortfolioHistoryView-contentPanel"))
						.findElement(By.id("gwt-debug-ManualPortfolioHistoryPresenter-deleteButton"));
				if (deleteButton == null) {
					br = true;
				} else {
					log("Clicking delete button");
					clickElement(By.id("gwt-debug-ManualPortfolioHistoryPresenter-deleteButton"));
					clickYesButtonIfVisible();
				}
			} catch (org.openqa.selenium.TimeoutException e) {
				break;
			}

		}
		assertTrue(pageContainsStr("No entries found."));
	}

	/**
	 * Switch update source to given string, eg "Automatic" or "Manual".
	 * 
	 * @throws InterruptedException
	 */
	private void switchUpdateSource(String sourceStr) throws InterruptedException {
		// open edit screen.
		try {
			clickElement(By.id("gwt-debug-InvestorAccountDetailsSectionPresenter-image"));
			boolean flag = pageContainsStr("Investor Account Details");
			if (!flag) {
				clickElement(By.id("gwt-debug-InvestorAccountDetailsSectionPresenter-image"));
			}
		} catch (Exception ex) {
			System.out.println("Edit image not found");
			throw ex;
		}
		// switch update source to manual
		waitForElementVisible(By.xpath(".//*[contains(text(),'Investor Account Details')]"), Settings.WAIT_SECONDS);

		assertTrue(pageContainsStr("Investor Account Details"));

		Select select = new Select(
				webDriver.findElement(By.id("gwt-debug-InvestorAccountDetailWidget-updateSourceList")));
		select.selectByVisibleText(sourceStr);

		// selectFromDropdown(
		// By.id("gwt-debug-InvestorAccountDetailWidget-updateSourceList"),
		// sourceStr);

		// save
		clickElement(By.id("gwt-debug-InvestorAccountDetailWidget-saveBtn"));

		// confirm
		// waitGet(By.id("gwt-debug-CustomDialog-okButton")).click();
	}

	protected void init() throws Exception {
		String account = "Generali Vision - NEW";

		LoginPage main = new LoginPage(webDriver);
		main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage().simpleSearchByString(account)
				.goToAccountDefaultPageByName(account, DetailPage.class);

	}

}
