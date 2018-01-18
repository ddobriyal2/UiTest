package org.sly.uitest.sections.transactionreconciliation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.framework.ThirdRock;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.clientsandaccounts.CancelTransactionPage;
import org.sly.uitest.pageobjects.clientsandaccounts.CashflowPage;
import org.sly.uitest.pageobjects.clientsandaccounts.DetailPage;
import org.sly.uitest.pageobjects.clientsandaccounts.HistoryPage;
import org.sly.uitest.pageobjects.clientsandaccounts.LinkTransactionPage;
import org.sly.uitest.pageobjects.clientsandaccounts.TradeOrderFormPage;
import org.sly.uitest.settings.Settings;

/**
 * This suite of test is for UI things for transaction reconciliation
 * 
 * @author Benny Leung
 * @Date : Oct 25 ,2016
 * @company Prive Financial
 */
public class TransactionReconciliationUITest extends AbstractTest {

	String adminName = "BobDoe";
	String adminPassword = "BobDoe";

	@Ignore
	@Category(ThirdRock.class)
	public void testDateRange() throws Exception {

		HistoryPage history = init();

		history.editDateRangeToShowTransaction("1-Apr-2016", "31-Mar-2016");
		assertTrue(pageContainsStr("End date cannot be earlier than start date"));
		clickOkButtonIfVisible();

		this.changeDateRangeAndDoAssertion("01-Apr-2016", "01-Apr-2016", "02-Apr-2016");

		this.changeDateRangeAndDoAssertion("04-Apr-2016", "05-Apr-2016", "03-Apr-2016", "06-Apr-2016");

		this.changeDateRangeAndDoAssertion("04-Apr-2016", "11-Apr-2016", "03-Apr-2016", "12-Apr-2016");

		this.changeDateRangeAndDoAssertion("04-Apr-2016", "18-Apr-2016", "03-Apr-2016", "19-Apr-2016");

		this.changeDateRangeAndDoAssertion("01-Apr-2016", "29-Apr-2016", "31-Mar-2016", "30-Apr-2016");

		this.changeDateRangeAndDoAssertion("31-Mar-2016", "02-May-2016", "30-Mar-2016", "03-May-2016");

		this.changeDateRangeAndDoAssertion("01-Apr-2016", "31-May-2016", "31-Mar-2016", "01-Jun-2016");

		this.changeDateRangeAndDoAssertion("31-Mar-2016", "01-Jun-2016", "30-Mar-2016", "02-Jun-2016");

		history.editDateRangeToShowTransaction("1-Apr-2020", "1-Apr-2020");
		assertTrue(pageContainsStr("No transactions found."));

		history.editDateRangeToShowTransaction("1-Apr-2020", "7-Apr-2020");
		assertTrue(pageContainsStr("No transactions found."));

		history.editDateRangeToShowTransaction("1-Apr-2020", "30-Apr-2020");
		assertTrue(pageContainsStr("No transactions found."));
	}

	@Ignore
	@Category(ThirdRock.class)
	public void testFilteringForShowingTransaction() throws Exception {

		ArrayList<String> types = new ArrayList<String>() {
			{
				add("All");
				add("Asset Flow");
				add("Cancel");
				add("Cashflow");
				add("Deposit");
				add("Instrument Distribution");
				add("Instrument Expiration");
				add("Loan");
				add("Option Exercise");
				add("Order");
				add("Other");
				add("PortFolio Inventory");
				add("Rebalancings");
				add("Split");

			}
		};
		ArrayList<String> toCheck = new ArrayList<String>();

		HistoryPage history = init();

		history.clickAutoRefreshBox(true).editHistorySource("Datafeed").editShowTransactionsHistoryFor("Last 2 years");

		Integer size = getSizeOfElements(By.id("gwt-debug-TransactionPortfolioOrderView-descLabel"));
		history.editHistorySource("Datafeed").checkShowOnlyUnmatchedBox(true);
		wait(Settings.WAIT_SECONDS * 2);

		history.checkIncludeCancelTransactionBox(true);
		wait(5);
		// assertTrue(isElementVisible(By
		// .xpath(".//*[contains(@class,'line-through')]")));

		history.clickAutoRefreshBox(true).checkIncludeCancelTransactionBox(false).checkShowOnlyUnmatchedBox(false)
				.editHistorySource("System");

		Select typeslist = new Select(webDriver.findElement(By.id("gwt-debug-TransactionListView-tranTypeListBox")));

		for (WebElement type : typeslist.getOptions()) {
			type.click();
			String optionName = type.getText();
			try {

				waitForElementVisible(By.id("gwt-debug-TransactionPortfolioOrderView-descLabel"), 3);

				if (!optionName.equals("Order")) {
					assertTrue(pageContainsStr(optionName));
				}
			} catch (TimeoutException e) {

				assertTrue(pageContainsStr("No transactions found."));
			}

			assertEquals(typeslist.getFirstSelectedOption().getText(), optionName);

			toCheck.add(optionName);
		}
		Collections.sort(types);
		Collections.sort(toCheck);

		assertTrue(types.equals(toCheck));

		toCheck.clear();

		history.editHistorySource("Datafeed");
		Select typeslist2 = new Select(webDriver.findElement(By.id("gwt-debug-TransactionListView-tranTypeListBox")));

		for (WebElement type : typeslist2.getOptions()) {
			type.click();
			String optionName = type.getText();

			try {
				waitForElementVisible(By.id("gwt-debug-TransactionPortfolioOrderView-descLabel"), 3);

				if (!optionName.equals("Order")) {
					assertTrue(pageContainsStr(optionName));
				}

			} catch (TimeoutException e) {
				assertTrue(pageContainsStr("No transactions found."));
			}

			assertEquals(typeslist2.getFirstSelectedOption().getText(), optionName);
			toCheck.add(type.getText());
		}

		Collections.sort(types);
		Collections.sort(toCheck);

		assertTrue(types.equals(toCheck));
		wait(Settings.WAIT_SECONDS * 2);
		history.editDateRangeToShowTransaction("04-Apr-2016", "04-Apr-2016");
		wait(1);

		Select select = new Select(webDriver.findElement(By.id("gwt-debug-TransactionListView-lastxdaysListBox")));
		assertEquals(select.getFirstSelectedOption().getText(), "");
	}

	@Ignore
	@Category(ThirdRock.class)
	public void testSelectTransactionToReconcile() throws Exception {

		HistoryPage history = init();

		LinkTransactionPage link = history.editHistorySource("Datafeed").editShowTransactionsHistoryFor("All")
				.editTransactionType("Order").clickLinkSystemTransactionButton("Order", LinkTransactionPage.class)
				.editDateForTransaction("01-Jan-2016", "31-Dec-2016");

		assertTrue(isElementVisible(
				By.xpath(".//button[@id='gwt-debug-TransactionMatchPopupView-matchButton' and @disabled]")));
		String transaction1 = getTextByLocator(By.xpath(
				"(.//div[@id='gwt-debug-TransactionMatchPopupView-optionTransactionsHolder']//*[contains(@id,'descLabel')])[1]"));
		String transaction2 = getTextByLocator(By.xpath(
				"(.//div[@id='gwt-debug-TransactionMatchPopupView-optionTransactionsHolder']//*[contains(@id,'descLabel')])[2]"));

		link.clickCheckboxForTransaction(transaction1, true).clickCheckboxForTransaction(transaction2, true);
		clickElement(By.id("gwt-debug-TransactionMatchPopupView-matchButton"));

		assertTrue(pageContainsStr("Only one selection is allowed"));
	}

	// for SLYAWS-9564 and SLYAWS-9585
	@Ignore
	@Category(ThirdRock.class)
	public void testPageFlowOfCashflowAndTransactionDate() throws Exception {
		String type = "Cashflow";
		String txnDate = this.getCurrentTimeInFormat("d-MMM-yyyy");
		String cashflowType = "Deposit";
		String cashflowAmount = String.valueOf(new Random().nextInt(1000) + 1);
		String cashflowStatus = "Completed";

		HistoryPage history = init();
		CashflowPage cashflow = history.editHistorySource("System").clickEditHistoryButton()
				.addHistoricalTransaction(type, CashflowPage.class);

		// create new cashflow should not lead to cashflow tab.
		cashflow.clickCancelButton();
		assertFalse(pageContainsStr("Cashflow Schedules"));
		assertFalse(isElementVisible(By.id("gwt-debug-DepositWithdrawalHistoryView-cashflowDetailsPanel")));

		cashflow = history.addHistoricalTransaction(type, CashflowPage.class);

		cashflow.editTransactionDate(txnDate).editValueDate(txnDate).editCashflowType(cashflowType)
				.editCashflowAmount(cashflowAmount).editCashflowStatus(cashflowStatus).editCashflowCurrency("USD")
				.clickSaveButton_CashflowEntry();

		history.editHistorySource("System").editTransactionType("Cashflow").clickEditHistoryButton()
				.editTransactionByInvestment("Inflow of USD " + cashflowAmount, CashflowPage.class);
		// transaction date should appear when editing cashflow
		assertTrue(pageContainsStr("Transaction Date"));

		// edit cashflow should not lead to cashflow tab.
		cashflow.clickCancelButton();
		assertFalse(pageContainsStr("Cashflow Schedules"));
		assertFalse(isElementVisible(By.id("gwt-debug-DepositWithdrawalHistoryView-cashflowDetailsPanel")));
		log("Inflow of USD " + cashflowAmount);
		history.clickDeleteTransactionButtonByName("Inflow of USD " + cashflowAmount);
	}

	@Ignore
	// For SLYAWS-9629
	public void testDecimalNumberForFX() throws Exception {
		String currency = "USD";
		String counterCurrency = "EUR";
		String amount = "1000";
		String status = "Submitted";
		String type = "Market";

		HistoryPage history = init();

		TradeOrderFormPage tradeOrderForm = history.editHistorySource("System").clickEditHistoryButton()
				.addHistoricalTransaction("Order", TradeOrderFormPage.class);

		tradeOrderForm.selectInstrumentAndProceed("FX", "")
				.editInstrumentCurrencyAndCounterCurrency(currency, counterCurrency)
				.editBuyCurrencyAndAmount(currency, amount).editOrderStatus(status).editOrderType(type);

	}

	@Ignore
	@Category(ThirdRock.class)
	public void testDecimalForInterestRate() throws Exception {
		HistoryPage history = init();
		String today = getCurrentTimeInFormat("dd-MMM-yyyy");
		TradeOrderFormPage tradeOrderForm = history.editHistorySource("System").clickEditHistoryButton()
				.addHistoricalTransaction("Loan", TradeOrderFormPage.class);
		tradeOrderForm.editPositionType("Open").editInstrumentCurrency("USD").editQuantity("10000")
				.editInterestRate("1.65571").editStartDate("1-Dec-2016").editMaturityDate("31-Dec-2017")
				.editExecutionDate(today).editSettlementDate("31-Dec-2017").clickSaveButtonForTrade();

		history.editTransactionType("Loan").clickEditHistoryButton()
				.editTransactionByInvestment("Buy USD 10,000.00 Loan", TradeOrderFormPage.class);

		assertEquals(getTextByLocator(By.id("gwt-debug-InstrumentMoneyMarketView-interestRate")), "1.65571");

		tradeOrderForm.clickCancelButton();
		history.clickDeleteTransactionButtonByName("Buy USD 10,000.00 Loan");
	}

	// SLYAWS-9503
	@Ignore
	@Category(ThirdRock.class)
	public void testRelatedReference() throws Exception {
		String description = "";
		String date = "";
		String transactionReference = "";
		String transactionReference2 = "";
		String type1 = "Order";
		String type2 = "Loan";

		HistoryPage history = init();

		this.retrieveTransactions("System", type1);

		// get description and date of the first date before opening it
		description = this.getFirstUnlinkedTransactionDescriptionByType("Order");

		date = this.getDateOfDescriptionByOrder(description);

		TradeOrderFormPage trade = history.clickEditHistoryButton().clickEditTransactionButtonByName(description,
				TradeOrderFormPage.class);

		transactionReference = getTextByLocator(By.xpath("//*[@id='gwt-debug-RelatedReferenceWidget-txnRefLabel']"));

		trade.clickCancelButton();
		CancelTransactionPage cancelPage = history.addHistoricalTransaction("Cancel", CancelTransactionPage.class);

		cancelPage.editTransactionReference(transactionReference).editTransactionDate(date).clickSaveButton();

		history.clickExitEditModeButton().editTransactionType(type2);
		description = this.getFirstUnlinkedTransactionDescriptionByType(type2);

		TradeOrderFormPage form2 = history.clickEditHistoryButton().clickEditTransactionButtonByName(description,
				TradeOrderFormPage.class);

		transactionReference2 = getTextByLocator(By.xpath("//*[@id='gwt-debug-RelatedReferenceWidget-txnRefLabel']"));

		form2.addRelatedReference(transactionReference).addRelatedReference(transactionReference2)
				.clickSaveButtonForTrade();

		history.clickEditTransactionButtonByName(description, CashflowPage.class);

		assertTrue(pageContainsStr(transactionReference) && pageContainsStr(transactionReference2));

		form2.clickInfoButtonForRelatedReference(transactionReference);
		assertEquals(transactionReference, getTextByLocator(
				By.xpath("//*[@id='gwt-debug-CustomDialog-mainPanel']//*[contains(@id,'transactionRef')]")));
		int size = getSizeOfElements(By.id("gwt-debug-CustomDialog-okButton"));
		clickElement(By.xpath("(.//button[@id='gwt-debug-CustomDialog-okButton'])[" + String.valueOf(size) + "]"));

		form2.clickInfoButtonForRelatedReference(transactionReference2);
		assertEquals(transactionReference2, getTextByLocator(
				By.xpath("//*[@id='gwt-debug-CustomDialog-mainPanel']//*[contains(@id,'transactionRef')]")));
		size = getSizeOfElements(By.id("gwt-debug-CustomDialog-okButton"));
		clickElement(By.xpath("(.//button[@id='gwt-debug-CustomDialog-okButton'])[" + String.valueOf(size) + "]"));

		form2.deleteAllRelatedReference();

		assertFalse(pageContainsStr(transactionReference));
		assertFalse(isElementVisible(
				By.xpath(".//div[@id='gwt-debug-RelatedReferenceWidget-relatedRefPanel']//*[contains(text(),'')]")));
		form2.clickSaveButtonForTrade();
		history.clickEditTransactionButtonByName(description, CashflowPage.class);

		assertFalse(pageContainsStr(transactionReference));
		assertFalse(isElementVisible(
				By.xpath(".//div[@id='gwt-debug-RelatedReferenceWidget-relatedRefPanel']//*[contains(text(),'')]")));
	}

	// SLYAWS-9503
	@Ignore
	@Category(ThirdRock.class)
	public void testReverseReference() throws Exception {
		String type = "Order";
		String date = "";
		String reference = "";
		String instrument = "Tesla Motors Inc";
		String description = "Buy 10.00 Units " + instrument + " @ 1.1111";
		String side = "Buy";
		String orderType = "Market";
		String status = "Filled fully";
		String quantity = "10";
		String executionDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String filledQuantity = "10";
		String settlementDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String executionPrice = "1.11111111";
		HistoryPage history = init();

		this.retrieveTransactions("System", type);

		TradeOrderFormPage form = history.clickEditHistoryButton().addHistoricalTransaction(type,
				TradeOrderFormPage.class);

		form.selectInstrumentAndProceed("Stock/ETF", "").addInstrument(instrument).editOrderSide(side)
				.editQuantityUnit(quantity).editOrderType(orderType).editOrderStatus(status)
				.editFilledQuantity(filledQuantity).editExecutionPrice(executionPrice)
				.editSettlementDate(settlementDate).editExecutionDate(executionDate).clickSaveButtonForTrade();
		history.clickExitEditModeButton().editTransactionType("Order").editShowTransactionsHistoryFor("Last 30 days");

		date = this.getDateOfDescriptionByOrder(description);

		history.clickEditHistoryButton().clickEditTransactionButtonByName(description, TradeOrderFormPage.class);

		reference = getTextByLocator(By.id("gwt-debug-RelatedReferenceWidget-txnRefLabel"));
		form.clickCancelButton();
		history.clickExitEditModeButton();

		CancelTransactionPage cancelPage = history.clickEditHistoryButton().addHistoricalTransaction("Cancel",
				CancelTransactionPage.class);
		cancelPage.editTransactionReference(reference).editTransactionDate(date).clickSaveButton();

		history.clickExitEditModeButton().editTransactionType(type).checkIncludeCancelTransactionBox(true)
				.clickEditHistoryButton().editTransactionByInvestment(description, TradeOrderFormPage.class);
		assertTrue(pageContainsStr("Reversed Reference"));

		form.clickCancelButton();
		history.clickDeleteTransactionButtonByName(description);
	}

	@Ignore
	@Category(ThirdRock.class)
	public void testReconciledTransactionCannotEdit() throws Exception {
		String type = "Order";
		String description = "";
		String date = "";

		HistoryPage history = init();

		this.retrieveTransactions("Datafeed", type);

		description = this.getFirstUnlinkedTransactionDescriptionByType(type);

		date = this.getDateOfDescriptionByOrder(description);
		log(description);

		TradeOrderFormPage trade = history.clickLinkSystemTransactionButton(description, TradeOrderFormPage.class)
				.clickCreateAndMatchButton();

		trade.clickSaveButtonForReconciliation();

		this.retrieveTransactions("System", type);

		assertTrue(
				isElementPresent(By.xpath("(.//div[@id='gwt-debug-TransactionListView-tableContentsPanel']//td[//*[.='"
						+ description + "']]//following-sibling::td)[6]//*[@aria-hidden='true']")));

		history.clickEditHistoryButton().clickExitEditModeButton().clickUnlinkSystemTransactionButton(description)
				.clickUnmatchButton();

		history.clickEditHistoryButton().clickDeleteTransactionButtonByName(description);
	}

	public HistoryPage init() throws Exception {

		String username = Settings.THIRDROCK_ADMIN_USERNAME;
		String password = Settings.THIRDROCK_ADMIN_PASSWORD;
		String account = "Solange Test CS";
		String url = "http://192.168.1.104:8080/SlyAWS/?locale=en&viewMode=MATERIAL#login";
		LoginPage main = new LoginPage(webDriver, url);

		DetailPage details = main.loginAs(username, password).goToAccountOverviewPage().simpleSearchByString(account)
				.goToAccountHoldingsPageByName(account).goToDetailsPage().goToEditPageByField("Details")
				.editAccountUpdateSource("Automatic").clickSaveButton_AccountDetail();

		HistoryPage history = details.goToTransactionHistoryPage();
		return history;
	}

	public void changeDateRangeAndDoAssertion(String startDate, String endDate, String... unexpectedDate)
			throws InterruptedException, ParseException {

		HistoryPage history = new HistoryPage(webDriver);
		history.editDateRangeToShowTransaction(startDate, endDate);

		if (!pageContainsStr("No transactions found.")) {
			assertTrue(pageContainsStr(startDate));
			assertTrue(pageContainsStr(endDate));
		}

		for (String date : unexpectedDate) {

			assertFalse(isElementDisplayed(By.xpath(".//div[.='" + date + "']")));

		}
	}

	public void retrieveTransactions(String source, String type) throws InterruptedException {
		HistoryPage history = new HistoryPage(webDriver);
		history.editShowTransactionsHistoryFor("Last 30 days").editHistorySource(source).editTransactionType(type)
				.clickAutoRefreshBox(true);
	}

	/**
	 * In history page, this returns the description of first unlinked
	 * transaction
	 * 
	 * @param type
	 * @return
	 */
	public String getFirstUnlinkedTransactionDescriptionByType(String type) {
		return getTextByLocator(By
				.xpath("//*[contains(@id,'transactionLinkBroken') and not(@aria-hidden='true')]//parent::td//preceding-sibling::td[div[contains(text(),'"
						+ type + "')]and a[@aria-hidden]]//following-sibling::td[1]//*"));
	}

	public String getDateOfDescriptionByOrder(String order) {
		log("order: " + order);
		return getTextByLocator(By.xpath(".//div[@id='gwt-debug-TransactionListView-tableContentsPanel']//*[.='" + order
				+ "']//parent::td//preceding-sibling::td//*[@id='gwt-debug-TransactionDetailsView-dateLabel']"));
	}
}
