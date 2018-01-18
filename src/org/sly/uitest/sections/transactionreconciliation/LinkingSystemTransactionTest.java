package org.sly.uitest.sections.transactionreconciliation;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.admin.AdminEditPage;
import org.sly.uitest.pageobjects.clientsandaccounts.CashflowPage;
import org.sly.uitest.pageobjects.clientsandaccounts.HistoryPage;
import org.sly.uitest.settings.Settings;

/**
 * Test plan: https://docs.google.com/spreadsheets/d/1n9ONypbeFUWPupV8-
 * kninFvd_fntHsCNEjFNtBebbv0/edit#gid=0
 * 
 * @author Benny Leung
 * @date 31.08.2016
 * @company Prive financial
 */
public class LinkingSystemTransactionTest extends AbstractTest {
	String executionPlatformKey = "2016";

	@Test
	public void testLinkingSystemTransaction() throws Exception {

		String investorAccount = "Solange Test CS";
		String transactionName = "Outflow of USD 1.00 (Withdrawal)";
		String type = "Cashflow";
		String amount = "1";
		String cashflowType = "Withdrawal";
		String currency = "USD";
		String status = "Completed";
		String source = "Datafeed";
		String period = "Last 30 days";

		String transactionDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String valueDate = transactionDate;

		LoginPage main = new LoginPage(webDriver, "http://192.168.1.104:8080/SlyAWS/?locale=en#login");
		HistoryPage history = main.loginAs("BobDoe", "BobDoe").goToAccountOverviewPage()
				.simpleSearchByString(investorAccount).goToAccountHoldingsPageByName(investorAccount)
				.goToTransactionHistoryPage();

		CashflowPage cashflow = history.clickEditHistoryButton().addHistoricalTransaction(type, CashflowPage.class);

		cashflow.editCashflowAmount(amount).editCashflowType(cashflowType).editCashflowCurrency(currency)
				.editCashflowStatus(status).editTransactionDate(transactionDate).editCashflowValueDate(valueDate)
				.clickSaveButtonForTrade().clickExitEditModeButton();

		history.editTransactionType(type).clickLinkSystemTransactionButton(transactionName, HistoryPage.class)
				.clickCreateAndMatchButton();

		this.checkIfOperationDone(transactionName, true);

		history.clickUnlinkSystemTransactionButton(transactionName).clickUnmatchButton();

		this.checkIfOperationDone(transactionName, false);
		history.clickEditHistoryButton().clickDeleteTransactionButtonByName(transactionName).clickExitEditModeButton()
				.editHistorySource("System").clickEditHistoryButton()
				.clickDeleteTransactionButtonByName(transactionName);
	}

	public void goToAdminPageAndChangeDataFeed(String datafeed) throws InterruptedException {
		LoginPage main = new LoginPage(webDriver);

		AdminEditPage adminEdit = main.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD)
				.goToAdminEditPage();

		adminEdit.editAdminThisField("Execution Platform Object").jumpByKeyAndLoad(executionPlatformKey);

		wait(Settings.WAIT_SECONDS);

		waitForElementVisible(By.id("gwt-debug-AdminExecutionPlatformObjectEdit-dataFeedListBox"), 10);

		selectFromDropdown(By.id("gwt-debug-AdminExecutionPlatformObjectEdit-dataFeedListBox"), datafeed);

		adminEdit.clickSubmitButton();

		checkLogout();
		handleAlert();
	}

	/**
	 * Check the transaction is linked or not. Before calling this method, make
	 * sure you know if the desired transaction should be linked or not
	 * 
	 * @param description
	 *            description of transaction
	 * @param linkTransaction
	 *            boolean that whether the transaction should be linked.
	 * @throws InterruptedException
	 */
	public void checkIfOperationDone(String description, boolean linkTransaction) throws InterruptedException {

		HistoryPage history = new HistoryPage(webDriver);

		waitForElementVisible(By.id("gwt-debug-TransactionListView-tableContentsPanel"), 10);

		// history.clickAutoRefreshBox(false);
		history.clickAutoRefreshBox(true);
		String xpath = "";

		if (linkTransaction) {
			xpath = ".//*[contains(text(),'" + description
					+ "')]/parent::td/following-sibling::td//*[@id='gwt-debug-TransactionDetailsView-transactionLink' and not(@aria-hidden='true')]";
		} else {
			xpath = ".//*[contains(text(),'" + description
					+ "')]/parent::td/following-sibling::td//*[contains(@id,'transactionLinkBroken') and not(@aria-hidden='true')]";
		}

		try {
			waitForElementVisible(By.xpath(xpath), 30);
			new Actions(webDriver).moveToElement(webDriver.findElement(By.xpath(xpath))).build().perform();
		} finally {
			assertTrue(isElementVisible(By.xpath(xpath)));
		}

	}
}
