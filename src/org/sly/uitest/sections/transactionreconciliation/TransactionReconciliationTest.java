package org.sly.uitest.sections.transactionreconciliation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.NumberFormat;
import java.util.Locale;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.framework.ThirdRock;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.clientsandaccounts.AssetflowPage;
import org.sly.uitest.pageobjects.clientsandaccounts.CashflowPage;
import org.sly.uitest.pageobjects.clientsandaccounts.HistoryPage;
import org.sly.uitest.pageobjects.clientsandaccounts.InstrumentDistributionPage;
import org.sly.uitest.pageobjects.clientsandaccounts.LinkTransactionPage;
import org.sly.uitest.pageobjects.clientsandaccounts.OtherTransactionPage;
import org.sly.uitest.pageobjects.clientsandaccounts.TradeOrderFormPage;
import org.sly.uitest.pageobjects.crm.TasksPage;
import org.sly.uitest.settings.Settings;

public class TransactionReconciliationTest extends AbstractTest {

	String dateFrom = "1-Jan-2017";
	String dateTo = this.getCurrentTimeInFormat("d-MMM-yyyy");
	String last2Years = "Last 2 years";
	String last30Days = "Last 30 days";

	@Ignore
	@Category(ThirdRock.class)
	public void testNotMatchTransaction() throws Exception {
		String transactionToBeLinked = "";
		String tmpTransaction = "";
		String type = "Order";
		HistoryPage history = init();
		this.retrieveTransactions("Datafeed", type, last2Years);
		LinkTransactionPage link = history.clickLinkSystemTransactionButton("Apple Inc", LinkTransactionPage.class)
				.clickShowOnlyUnmatchedTransactionsBox(true);
		waitForElementVisible(
				By.xpath(
						".//*[@id='gwt-debug-TransactionMatchPopupView-currentTransactionHolder']//*[contains(@id,'descLabel')]"),
				60);

		String transaction = getTextByLocator(By.xpath(
				".//*[@id='gwt-debug-TransactionMatchPopupView-currentTransactionHolder']//*[contains(@id,'descLabel')]"));

		int size = getSizeOfElements(By.xpath(
				".//*[@id='gwt-debug-TransactionMatchPopupView-optionTransactionsHolder']//*[contains(@id,'descLabel')]"));

		for (int i = size; i >= 1; i--) {
			tmpTransaction = getTextByLocator(By
					.xpath("(.//*[@id='gwt-debug-TransactionMatchPopupView-optionTransactionsHolder']//*[contains(@id,'descLabel')])["
							+ i + "]"));
			if (!transaction.equals(tmpTransaction)) {
				transactionToBeLinked = tmpTransaction;
				break;
			}
		}

		link.clickShowOnlyUnmatchedTransactionsBox(true).clickCheckboxForTransaction(transactionToBeLinked, true)
				.clickMatchButton();

		assertTrue(pageContainsStr(
				"The system transaction does not match the datafeed transaction and hence can't be matched"));
	}

	@Ignore
	@Category(ThirdRock.class)
	public void testExactMatch() throws Exception {
		String type = "Order";
		String description = "";
		HistoryPage history = this.init();

		// this.unlinkAllTransaction("System", type);
		this.retrieveTransactions("Datafeed", type, last2Years);

		description = this.getFirstUnlinkedTransactionDescriptionByType(type);
		log("description: " + description);
		LinkTransactionPage link = history.clickLinkSystemTransactionButton(description, TradeOrderFormPage.class);

		// test create and match function
		TradeOrderFormPage trade = link.clickCreateAndMatchButton();
		trade.clickSaveButtonForTrade();
		// clickOkButtonIfVisible();

		this.checkIfOperationDone(description, true);

		history.clickUnlinkSystemTransactionButton(description).clickUnmatchButton();
		this.checkIfOperationDone(description, false);

		TradeOrderFormPage trade2 = history.clickLinkSystemTransactionButton(description, TradeOrderFormPage.class)
				.clickCheckboxForTransaction(description, true).clickMatchButton();
		// clickOkButtonIfVisible();

		this.checkIfOperationDone(description, true);

		history.clickUnlinkSystemTransactionButton(description).clickUnmatchButton();
		this.checkIfOperationDone(description, false);
	}

	@Ignore
	@Category(ThirdRock.class)
	public void testCreateAndMatchSecurity() throws Exception {
		String instrument = "Tesla Motors Inc";
		this.testCreateAndMatchEquity(instrument);
	}

	@Ignore
	@Category(ThirdRock.class)
	public void testCreateAndMatchBond() throws Exception {
		String instrument = "180 Plus Note";
		this.testCreateAndMatchEquity(instrument);

	}

	@Ignore
	@Category(ThirdRock.class)
	public void testCreateAndMatchMutualFunds() throws Exception {
		String instrument = "Aberdeen China Opportunities USD";
		this.testCreateAndMatchEquity(instrument);
	}

	@Ignore
	@Category(ThirdRock.class)
	public void testCreateAndMatchLoan() throws Exception {
		this.testCreateAndMatchLoanAndDeposit("Loan");
	}

	@Ignore
	@Category(ThirdRock.class)
	public void testCreateAndMatchDeposit() throws Exception {
		this.testCreateAndMatchLoanAndDeposit("Deposit");
	}

	@Ignore
	@Category(ThirdRock.class)
	public void testCreateAndMatchFX() throws Exception {
		String type = "Order";
		String currency = "USD";
		String counterCurrency = "EUR";
		String amount = "10000";
		String orderType = "Market";
		String status = "Filled partially";
		String executionPrice = "1.11111111";
		String settlementDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String executionDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String commission = "123456.789";
		String companyCommissionRate = "1.3215478";
		String instrument = "FX: EUR/USD";
		String buyValue = "USD 10,000.00";
		String sellValue = "EUR 11,111.11";
		String flippedExecutionPriceLabel = "USD/EUR @ 1.11111111";
		String executionPriceLabel = "EUR/USD @ 0.90000000";
		String transaction = "Buy USD 10,000.00 FX: EUR/USD @ 0.90000000";

		HistoryPage history = init();
		TradeOrderFormPage form = history.clickEditHistoryButton().addHistoricalTransaction(type,
				TradeOrderFormPage.class);

		form.selectInstrumentAndProceed("FX", "").editInstrumentCurrencyAndCounterCurrency(currency, counterCurrency)
				.editBuyCurrencyAndAmount(currency, amount).editOrderType(orderType).editOrderStatus(status)
				.editExecutionPrice(executionPrice).editSettlementDate(settlementDate).editCommission(commission)
				.editCompanyCommissionRate(companyCommissionRate).editExecutionDate(executionDate)
				.clickSaveButtonForTrade();

		history.clickExitEditModeButton();

		this.retrieveTransactions("Datafeed", type, last30Days);
		LinkTransactionPage link = history.clickLinkSystemTransactionButton(transaction, LinkTransactionPage.class);

		link.clickCreateAndMatchButton();
		TradeOrderFormPage form2 = new TradeOrderFormPage(webDriver, HistoryPage.class);
		form2.clickSaveButtonForTrade();

		this.checkIfOperationDone(transaction, true);

		this.retrieveTransactions("System", type, last30Days);

		this.assertOnlyViewTransaction(transaction);

		history.clickEditHistoryButton();
		this.assertTransactionNotEditable(transaction);
		history.clickExitEditModeButton();

		LinkTransactionPage link2 = history.clickUnlinkSystemTransactionButton(transaction);

		assertEquals(instrument, this.getTransactionDetailsOfMatchedTransactionByField("Instrument"));
		assertEquals(buyValue, this.getTransactionDetailsOfMatchedTransactionByField("Buy"));
		assertEquals(sellValue, this.getTransactionDetailsOfMatchedTransactionByField("Sell"));
		assertEquals(executionPriceLabel, getTextByLocator(By.xpath(
				".//*[@id='gwt-debug-TransactionUnMatchPopupView-matchedTransactionHolder']//*[@id='gwt-debug-TransactionPortfolioFXOrderDetailsView-exePriceLabel']")));
		assertEquals(flippedExecutionPriceLabel, getTextByLocator(By.xpath(
				".//*[@id='gwt-debug-TransactionUnMatchPopupView-matchedTransactionHolder']//*[@id='gwt-debug-TransactionPortfolioFXOrderDetailsView-flippedExePriceLabel']")));
		assertEquals(executionDate, this.getTransactionDetailsOfMatchedTransactionByField("Execution Date"));
		assertEquals(settlementDate, this.getTransactionDetailsOfMatchedTransactionByField("Settlement Date"));

		link2.clickUnmatchButton();
		this.checkIfOperationDone(transaction, false);

		history.clickEditHistoryButton().clickDeleteTransactionButtonByName(transaction).clickExitEditModeButton();

		this.retrieveTransactions("Datafeed", orderType, "Last 30 days");
		history.clickEditHistoryButton().clickDeleteTransactionButtonByName(transaction);

	}

	@Ignore
	@Category(ThirdRock.class)
	public void testCreateAndMatchCashflow() throws Exception {

		String type = "Cashflow";
		String cashflowType = "Deposit";
		String transactionDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String valueDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String currency = "USD";
		String amount = "10000";
		String status = "Completed";
		String transaction = "Inflow of USD 10,000.00 (Deposit)";
		String transactionReference = "";

		HistoryPage history = this.init();

		CashflowPage cashflow = history.clickEditHistoryButton().addHistoricalTransaction(type, CashflowPage.class);

		cashflow.editCashflowAmount(amount).editCashflowType(cashflowType).editCashflowCurrency(currency)
				.editCashflowStatus(status).editTransactionDate(transactionDate).editCashflowValueDate(valueDate)
				.clickSaveButtonForTrade().clickExitEditModeButton();

		this.retrieveTransactions("Datafeed", type, last30Days);

		history.clickLinkSystemTransactionButton(transaction, HistoryPage.class).clickCreateAndMatchButton();

		this.checkIfOperationDone(transaction, true);

		this.retrieveTransactions("System", type, last30Days);

		this.assertOnlyViewTransaction(transaction);

		history.clickEditHistoryButton();
		this.assertTransactionNotEditable(transaction);
		history.clickExitEditModeButton();

		LinkTransactionPage link2 = history.clickUnlinkSystemTransactionButton(transaction).clickUnmatchButton();

		history.clickEditHistoryButton().clickDeleteTransactionButtonByName(transaction);

		this.retrieveTransactions("Datafeed", type, "Last 30 days");
		history.clickEditHistoryButton().clickDeleteTransactionButtonByName(transaction);

	}

	@Ignore
	@Category(ThirdRock.class)
	public void testCreateAndMatchAssetFlow() throws Exception {
		String type = "Assetflow";
		String executionDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String settlementDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String direction = "Inflow";
		String asset = "Tesla Motors Inc";
		String quantity = "100";
		String value = "100";
		String status = "Completed";
		String transaction = direction + " of 100.00 units of " + asset;

		HistoryPage history = this.init();

		AssetflowPage assetflow = history.clickEditHistoryButton().addHistoricalTransaction("Asset Flow",
				AssetflowPage.class);

		assetflow.editDirection(direction).addAsset(asset).editValue(value).editQuantityUnit(quantity)
				.editStatus(status).editExecutionDate(executionDate).editSettlementDate(settlementDate)
				.clickSaveButton().clickExitEditModeButton();

		this.retrieveTransactions("Datafeed", type, last30Days);

		LinkTransactionPage link = history.clickLinkSystemTransactionButton(transaction, HistoryPage.class);

		link.clickCreateAndMatchButton();

		this.checkIfOperationDone(transaction, true);

		this.retrieveTransactions("System", type, last30Days);

		log(transaction);
		this.assertOnlyViewTransaction(transaction);

		history.clickEditHistoryButton();
		this.assertTransactionNotEditable(transaction);
		history.clickExitEditModeButton();

		LinkTransactionPage link2 = history.clickUnlinkSystemTransactionButton(transaction);

		assertEquals(asset, this.getTransactionDetailsOfMatchedTransactionByField("Instrument"));
		assertTrue(this.getTransactionDetailsOfMatchedTransactionByField("Quantity").contains(quantity));
		assertEquals(settlementDate, this.getTransactionDetailsOfMatchedTransactionByField("Transaction Date"));
		assertEquals(executionDate, this.getTransactionDetailsOfMatchedTransactionByField("Value Date"));

		link2.clickUnmatchButton();

		this.checkIfOperationDone(transaction, false);

		history.clickEditHistoryButton().clickDeleteTransactionButtonByName(transaction).clickExitEditModeButton();

		this.retrieveTransactions("Datafeed", type, "Last 30 days");
		history.clickEditHistoryButton().clickDeleteTransactionButtonByName(transaction);
	}

	@Ignore
	@Category(ThirdRock.class)
	public void testCreateAndMatchInstrumentDistribution() throws Exception {
		String type = "Instrument Distribution";
		String instrumentType = "Cash";
		String investment = "Tesla Motors Inc";
		String currency = "USD";
		String amount = "100";
		String quantityToCheck = "100.00";
		String status = "Completed";
		String executionDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String settlementDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String exDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String cumDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String transaction = "The distribution amount is  " + currency + " " + quantityToCheck;

		HistoryPage history = this.init();

		InstrumentDistributionPage distribution = history.clickEditHistoryButton().addHistoricalTransaction(type,
				InstrumentDistributionPage.class);

		distribution.editType(instrumentType).addInvestmentForInstrumentDistribution(investment).editCurrency(currency)
				.editAmount(amount).editStatus(status).editExecutionDate(executionDate)
				.editSettlementDate(settlementDate).editExDate(exDate).editCumDate(cumDate).clickSaveButton()
				.clickExitEditModeButton();

		this.retrieveTransactions("Datafeed", type, last30Days);

		LinkTransactionPage link = history.clickLinkSystemTransactionButton(transaction, HistoryPage.class);

		link.clickCreateAndMatchButton();

		this.checkIfOperationDone(transaction, true);

		this.retrieveTransactions("System", type, last30Days);

		this.assertOnlyViewTransaction(transaction);

		history.clickEditHistoryButton();
		this.assertTransactionNotEditable(transaction);
		history.clickExitEditModeButton();

		LinkTransactionPage link2 = history.clickUnlinkSystemTransactionButton(transaction);

		assertEquals(executionDate, this.getTransactionDetailsOfMatchedTransactionByField("Ex Date"));
		assertEquals(investment, this.getTransactionDetailsOfMatchedTransactionByField("Instrument"));

		assertEquals(settlementDate, this.getTransactionDetailsOfMatchedTransactionByField("Value Date"));
		assertTrue(this.getTransactionDetailsOfMatchedTransactionByField("Amount").contains(quantityToCheck));

		assertTrue(this.getTransactionDetailsOfMatchedTransactionByField("Transaction Reference").contains("ID"));

		link2.clickUnmatchButton();

		this.checkIfOperationDone(transaction, false);

		history.clickEditHistoryButton().clickDeleteTransactionButtonByName(transaction).clickExitEditModeButton();

		this.retrieveTransactions("Datafeed", type, "Last 30 days");
		history.clickEditHistoryButton().clickDeleteTransactionButtonByName(transaction);
	}

	@Ignore
	@Category(ThirdRock.class)
	public void testCreateAndMatchOtherTransaction() throws Exception {
		String type = "Other";
		String transactionDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String executionDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String settlementDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String instrumentType = "Equity";
		String instrumentType2 = "Cash";
		String instrumentType3 = "Option";
		String quantity1 = "1000";
		String instrument = "Tesla Motors Inc";
		String quantity2 = "50000";
		String currency = "USD";
		String currency2 = "EUR";
		String status = "Settled";
		String transaction = "3 Sub Transactions";

		HistoryPage history = this.init();
		OtherTransactionPage otherPage = history.clickEditHistoryButton().addHistoricalTransaction(type,
				OtherTransactionPage.class);

		// add first txn
		otherPage.clickAddImpactButton().editImpactInstrument(instrumentType).addInstrumentForEquityImpact(instrument)
				.editImpactQuantity(quantity1).editImpactStatus(status).clickSaveImpactButton();

		// add second txn
		otherPage.clickAddImpactButton().editImpactInstrument(instrumentType2).editImpactCurrency(currency)
				.editImpactQuantity(quantity2).editImpactStatus(status).clickSaveImpactButton();

		// add third txn
		otherPage.clickAddImpactButton().editImpactInstrument(instrumentType3).editImpactCurrency(currency2)
				.editImpactQuantity(quantity2).editImpactStatus(status).clickSaveImpactButton();

		otherPage.editTransactionDate(transactionDate).editExecutionDate(executionDate)
				.editSettlementDate(settlementDate).clickSaveButton();

		history.clickExitEditModeButton();

		this.retrieveTransactions("Datafeed", type, last30Days);

		LinkTransactionPage link = history.clickLinkSystemTransactionButton(transaction, HistoryPage.class);

		link.clickCreateAndMatchButton();

		this.checkIfOperationDone(transaction, true);

		this.retrieveTransactions("System", type, last30Days);

		this.assertOnlyViewTransaction(transaction);

		history.clickEditHistoryButton();
		this.assertTransactionNotEditable(transaction);
		history.clickExitEditModeButton();

		LinkTransactionPage link2 = history.clickUnlinkSystemTransactionButton(transaction);

		link2.clickUnmatchButton();

		this.checkIfOperationDone(transaction, false);

		history.clickEditHistoryButton().clickDeleteTransactionButtonByName(transaction).clickExitEditModeButton();

		this.retrieveTransactions("Datafeed", type, "Last 30 days");
		history.clickEditHistoryButton().clickDeleteTransactionButtonByName(transaction);
	}

	@Ignore
	@Category(ThirdRock.class)
	public void testCreateAndMatchEquityOption() throws Exception {
		String type = "Order";
		String positionType = "Open";
		String optionType = "Call";
		String optionStyle = "American";
		String strike = "1";
		String expirationDate = this.getDateAfterDays(this.getCurrentTimeInFormat("dd-MMM-yyyy"), 60, "dd-MMM-yyyy");
		String symbol = "TSLA";
		String currency = "USD";
		String exchangeForTicker = "NYSE";
		String exchange = "Over-The-Counter (OTC)";
		String isin = "";
		String side = "Buy";
		String quantity = "10";
		String orderType = "Market";
		String orderStatus = "Filled fully";
		String filledQuantity = "10";
		String executionPrice = "10.87654321";
		String settlementDate = expirationDate;
		String executionDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String commission = "100";
		String settlementAmount = "";
		String instrument = symbol + " " + this.getDateAfterDays(this.getCurrentTimeInFormat("yyMMdd"), 60, "yyMMdd")
				+ " " + optionType + " " + strike;

		HistoryPage history = this.init();

		TradeOrderFormPage form = history.clickEditHistoryButton().addHistoricalTransaction(type,
				TradeOrderFormPage.class);

		form.selectInstrumentAndProceed("Option", "Equity Option").editPositionType(positionType)
				.clickAddUnderlyingButton().addTicker(exchangeForTicker, symbol);

		waitForElementVisible(By.id("gwt-debug-InstrumentOptionView-underlyingLabel"), 10);
		form.editOptionType(optionType).editOptionStyle(optionStyle).editStrike(strike)
				.editExpirationDate(expirationDate).editExchange(exchange).editOrderSide(side)
				.editQuantityUnit(quantity).editOrderType(orderType).editOrderStatus(orderStatus)
				.editFilledQuantity(filledQuantity).editExecutionPrice(executionPrice)
				.editSettlementDate(settlementDate).editExecutionDate(executionDate);

		settlementAmount = getTextByLocator(By.id("gwt-debug-MyOrderEditPopupView-netSettlementAmountLabel"));

		form.clickSaveButtonForTrade();
		history.clickExitEditModeButton();

		this.retrieveTransactions("Datafeed", type, last30Days);

		TradeOrderFormPage form2 = history.clickLinkSystemTransactionButton(instrument, TradeOrderFormPage.class)
				.clickCreateAndMatchButton();

		form2.clickSaveButtonForTrade();

		this.checkIfOperationDone(instrument, true);

		this.retrieveTransactions("System", type, last30Days);

		this.assertOnlyViewTransaction(instrument);

		history.clickEditHistoryButton();
		this.assertTransactionNotEditable(instrument);
		history.clickExitEditModeButton();

		LinkTransactionPage link2 = history.clickUnlinkSystemTransactionButton(instrument);

		assertEquals(instrument, this.getTransactionDetailsOfMatchedTransactionByField("Instrument"));

		assertEquals(optionType + " " + optionStyle, this.getTransactionDetailsOfMatchedTransactionByField("Option"));
		assertEquals(currency + " " + strike + ".00", this.getTransactionDetailsOfMatchedTransactionByField("Strike"));
		assertEquals(expirationDate, this.getTransactionDetailsOfMatchedTransactionByField("Expiration Date"));
		assertEquals(side, this.getTransactionDetailsOfMatchedTransactionByField("Side"));
		assertTrue(executionPrice.contains(this.getTransactionDetailsOfMatchedTransactionByField("Execution Price")));
		assertEquals(expirationDate, this.getTransactionDetailsOfMatchedTransactionByField("Premium Settlement Date"));
		link2.clickUnmatchButton();

		this.checkIfOperationDone(instrument, false);

		history.clickEditHistoryButton().clickDeleteTransactionButtonByName(instrument).clickExitEditModeButton();

		this.retrieveTransactions("Datafeed", type, "Last 30 days");
		history.clickEditHistoryButton().clickDeleteTransactionButtonByName(instrument);
	}

	@Ignore
	@Category(ThirdRock.class)
	public void testCreateAndMatchFXOption() throws Exception {
		String type = "Order";
		String positionType = "Open";
		String optionType = "Call";
		String optionStyle = "European";
		String strike = "100";
		String expirationDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String exchange = "Over-The-Counter (OTC)";
		String currency = "USD";
		String counterCurrency = "EUR";
		String side = "Buy";
		String quantity = "100.00";
		String orderType = "Market";
		String orderStatus = "Settled";
		String executionDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String filledQuantity = "100";
		String executionPrice = "10";
		String marketCut = "Hong Kong";
		String settlementDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String instrument = counterCurrency + "/" + currency + " " + this.getCurrentTimeInFormat("yyMMdd") + " "
				+ optionType + " " + strike;
		String transaction = side + " " + instrument;

		HistoryPage history = this.init();

		TradeOrderFormPage form = history.clickEditHistoryButton().addHistoricalTransaction(type,
				TradeOrderFormPage.class);

		form.selectInstrumentAndProceed("Option", "FX Option").editPositionType(positionType).editOptionType(optionType)
				.editOptionStyle(optionStyle).editStrike(strike).editStrikeCurrency(currency, counterCurrency)
				.editMarketCut(marketCut).editExpirationDate(expirationDate).editExchange(exchange).editOrderSide(side)
				.editQuantityUnit(quantity).editOrderType(orderType).editOrderStatus(orderStatus)
				.editExecutionDate(executionDate).editFilledQuantity(filledQuantity).editExecutionPrice(executionPrice)
				.editExecutionPriceCurrency(currency).editSettlementDate(settlementDate).clickSaveButtonForTrade();
		history.clickExitEditModeButton();

		this.retrieveTransactions("Datafeed", type, last30Days);
		log(transaction);
		TradeOrderFormPage form2 = history.clickLinkSystemTransactionButton(transaction, TradeOrderFormPage.class)
				.clickCreateAndMatchButton();

		form2.clickSaveButtonForTrade();

		this.checkIfOperationDone(instrument, true);

		this.retrieveTransactions("System", type, last30Days);

		this.assertOnlyViewTransaction(instrument);

		history.clickEditHistoryButton();
		this.assertTransactionNotEditable(instrument);
		history.clickExitEditModeButton();

		LinkTransactionPage link2 = history.clickUnlinkSystemTransactionButton(instrument);

		assertEquals(instrument, this.getTransactionDetailsOfMatchedTransactionByField("Instrument"));
		assertEquals(optionType + " " + optionStyle, this.getTransactionDetailsOfMatchedTransactionByField("Option"));
		assertTrue(this.getTransactionDetailsOfMatchedTransactionByField("Strike").contains(strike));
		assertEquals(executionDate, this.getTransactionDetailsOfMatchedTransactionByField("Expiration Date"));
		assertEquals(side, this.getTransactionDetailsOfMatchedTransactionByField("Side"));
		assertEquals("EUR 10,000.00", this.getTransactionDetailsOfMatchedTransactionByField("Counter Amount"));
		assertEquals(executionDate, this.getTransactionDetailsOfMatchedTransactionByField("Execution Date"));
		assertEquals(settlementDate, this.getTransactionDetailsOfMatchedTransactionByField("Premium Settlement Date"));

		link2.clickUnmatchButton();

		this.checkIfOperationDone(instrument, false);

		history.clickEditHistoryButton().clickDeleteTransactionButtonByName(instrument).clickExitEditModeButton();

		this.retrieveTransactions("Datafeed", type, "Last 30 days");
		history.clickEditHistoryButton().clickDeleteTransactionButtonByName(instrument);

	}

	@Test
	@Category(ThirdRock.class)
	public void testCreateAndMatchFutures() throws Exception {

		String assetClass = "Equity";
		String name = "Futures-" + this.getRandName();
		String currency = "USD";
		String symbol = this.getRandName();
		String exchange = "NYSE (XNYS)";
		String futuresPrice = "1";
		String expirationDate = this.getDateAfterDays(this.getCurrentTimeInFormat("dd-MMM-yyyy"), 1, "dd-MMM-yyyy");
		String executionDate = expirationDate;
		String side = "Buy";
		String quantity = "1";
		String filledQuantity = quantity;
		String executionPrice = futuresPrice;
		String settlementDate = expirationDate;
		String orderStatus = "Settled";
		String transaction = "Buy USD 1.00 " + name + " @ 1.0000";

		HistoryPage history = this.init();
		TradeOrderFormPage form = history.clickEditHistoryButton().addHistoricalTransaction("Order",
				TradeOrderFormPage.class);

		HistoryPage history2 = form.selectInstrumentAndProceed("OTC", "Futures").editAssetClass(assetClass)
				.editInstrumentCurrency(currency).editTransactionName(name).editTransactionSymbol(symbol)
				.editExchange(exchange).editFuturesPrice(futuresPrice).editExpirationDate(expirationDate)
				.editOrderSide(side).editQuantity(quantity).editOrderStatus(orderStatus)
				.editExecutionDate(executionDate).editFilledAmount(filledQuantity).editExecutionPrice(executionPrice)
				.editSettlementDate(settlementDate).clickSaveButtonForTrade();

		history2.clickExitEditModeButton().clickLinkSystemTransactionButton(transaction, HistoryPage.class)
				.clickCreateAndMatchButton();
		form.clickSaveButtonForTrade();
		history2.clickAutoRefreshBox(true);

		this.checkIfOperationDone(transaction, true);

		this.retrieveTransactions("System", "Order", "Last 30 days");

		this.checkIfOperationDone(transaction, true);

		history2.clickUnlinkSystemTransactionButton(transaction).clickUnmatchButton();

		this.checkIfOperationDone(transaction, false);

		history.clickEditHistoryButton().clickDeleteTransactionButtonByName(transaction).clickExitEditModeButton();

		this.retrieveTransactions("Datafeed", "Order", "Last 30 days");
		history.clickEditHistoryButton().clickDeleteTransactionButtonByName(transaction);

	}

	@Test
	@Category(ThirdRock.class)
	public void testCreateAndMatchFXForward() throws Exception {
		HistoryPage history = this.init();
		String forwardType = "Non-Deliverable Forward";
		String forwardExchangeRate = "0.01";
		String forwardCounterCurrency = "USD";
		String forwardCurrency = "EUR";
		String settlementCurrency = "USD";
		String buyCurrency = "USD";
		String buyAmount = "100";
		String orderStatus = "Settled";
		String executionDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String valueDate = executionDate;
		String transaction = "Sell EUR 10,000.00 FX Forward EUR/USD " + this.getCurrentTimeInFormat("yyyyMMdd") + " @"
				+ forwardExchangeRate;
		TradeOrderFormPage form = history.clickEditHistoryButton().addHistoricalTransaction("Order",
				TradeOrderFormPage.class);

		HistoryPage history2 = form.selectInstrumentAndProceed("OTC", "FX Forward").editForwardType(forwardType)
				.editForwardExchangeRate(forwardExchangeRate, forwardCounterCurrency, forwardCurrency)
				.editSettlementCurrency(settlementCurrency).editBuyCurrencyAndAmountForFXForward(buyCurrency, buyAmount)
				.editOrderStatus(orderStatus).editExecutionDate(executionDate).editValueDate(valueDate)
				.clickSaveButtonForTrade();
		log("transaction: " + transaction);
		history2.clickExitEditModeButton().clickLinkSystemTransactionButton(transaction, HistoryPage.class)
				.clickCreateAndMatchButton();
		form.clickSaveButtonForTrade();
		history2.clickAutoRefreshBox(true);

		this.checkIfOperationDone(transaction, true);

		this.retrieveTransactions("System", "Order", "Last 30 days");

		this.checkIfOperationDone(transaction, true);

		history2.clickUnlinkSystemTransactionButton(transaction).clickUnmatchButton();

		this.checkIfOperationDone(transaction, false);

		history.clickEditHistoryButton().clickDeleteTransactionButtonByName(transaction).clickExitEditModeButton();

		this.retrieveTransactions("Datafeed", "Order", "Last 30 days");
		history.clickEditHistoryButton().clickDeleteTransactionButtonByName(transaction);

	}

	public void testCreateAndMatchLoanAndDeposit(String type) throws Exception {

		String depositType = "Time Deposit";
		String positionType = "Open";
		String side = "Buy";
		String currency = "USD";
		String interestRate = "36.499999999999";
		String startDate = "01-Dec-2016";
		String maturityDate = "31-Dec-2017";
		String principalAmount = "10000";
		String amountToVerify = "10,000.00";
		String orderStatus = "Settled";
		String valueDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String executionDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String settlementAdjustmentAmount = "10000";
		String instrument = type + " " + currency + " 36.50% MAT " + maturityDate;
		String transaction = side + " " + currency + " " + amountToVerify + " " + instrument;

		HistoryPage history = init();
		TradeOrderFormPage form = history.clickEditHistoryButton().addHistoricalTransaction(type,
				TradeOrderFormPage.class);

		if (type.equals("Deposit")) {
			form.editDepositType(depositType);
			instrument = depositType + " " + currency + " 36.50% MAT " + maturityDate;
			transaction = side + " " + currency + " " + amountToVerify + " " + instrument;
		}

		form.editPositionType(positionType).editInstrumentCurrency(currency).editInterestRate(interestRate)
				.editStartDate(startDate).editMaturityDate(maturityDate).editExecutionDate(executionDate)
				.editQuantity(principalAmount).editOrderStatus(orderStatus).editSettlementDate(valueDate)
				.clickCalculateInterestAmountButton().clickSaveButtonForTrade();

		history.clickExitEditModeButton();

		this.retrieveTransactions("Datafeed", type, last30Days);

		LinkTransactionPage link = history.clickLinkSystemTransactionButton(transaction, LinkTransactionPage.class);

		link.clickCreateAndMatchButton();
		TradeOrderFormPage form2 = new TradeOrderFormPage(webDriver, HistoryPage.class);
		form2.clickSaveButtonForTrade();

		this.checkIfOperationDone(transaction, true);

		this.retrieveTransactions("System", type, last30Days);
		log("transaction :" + transaction);

		this.assertOnlyViewTransaction(transaction);

		history.clickEditHistoryButton();
		this.assertTransactionNotEditable(transaction);
		history.clickExitEditModeButton();

		LinkTransactionPage link2 = history.clickUnlinkSystemTransactionButton(transaction);

		assertEquals(instrument, this.getTransactionDetailsOfMatchedTransactionByField("Instrument"));
		assertEquals(interestRate + "%", this.getTransactionDetailsOfMatchedTransactionByField("Interest rate"));
		assertEquals(maturityDate, this.getTransactionDetailsOfMatchedTransactionByField("Maturity date"));
		assertEquals(side, this.getTransactionDetailsOfMatchedTransactionByField("Side"));
		assertTrue(this.getTransactionDetailsOfMatchedTransactionByField("Amount").contains(amountToVerify));
		assertEquals(executionDate, this.getTransactionDetailsOfMatchedTransactionByField("Execution date"));
		assertEquals(valueDate, this.getTransactionDetailsOfMatchedTransactionByField("Value date"));
		link2.clickUnmatchButton();
		this.checkIfOperationDone(transaction, false);

		history.clickEditHistoryButton().clickDeleteTransactionButtonByName(transaction);

		this.retrieveTransactions("Datafeed", type, "Last 30 days");
		history.clickEditHistoryButton().clickDeleteTransactionButtonByName(transaction);
	}

	public void testCreateAndMatchEquity(String instrument) throws Exception {
		String type = "Order";
		String txnType = "";
		String status = "Filled fully";
		String transaction = "Buy 10.00 Units " + instrument + " @ 1.1111";
		Boolean isBond = false;
		String side = "Buy";
		String orderType = "Market";
		String quantity = "10";
		String filledQuantity = "10";
		String settlementDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String executionDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String executionPrice = "1.11111111";
		double commission = 100.32112345;
		double fee = 100.68;
		String settlementAdjustmentAmount = "1000000.00000001";
		double companyCommissionRate = 1.63214578;
		String companyCommission = "1000000";

		HistoryPage history = init();
		TradeOrderFormPage form = history.clickEditHistoryButton().addHistoricalTransaction(type,
				TradeOrderFormPage.class);

		form.selectInstrumentAndProceed("Stock/ETF", "").addInstrument(instrument);
		txnType = getTextByLocator(By.id("gwt-debug-InstrumentSecuritiesView-exchangeLabel"));

		if (txnType.contains("OTC")) {
			transaction = "Buy 10.00 Units " + instrument + " @ 111.1111";
			isBond = true;
		}

		form.editOrderSide(side).editQuantityUnit(quantity).editOrderType(orderType).editOrderStatus(status)
				.editFilledQuantity(filledQuantity).editExecutionPrice(executionPrice)
				.editSettlementDate(settlementDate).editExecutionDate(executionDate)
				.editCommission(String.valueOf(commission)).editFees(String.valueOf(fee))
				.editCompanyCommissionRate(String.valueOf(companyCommissionRate))
				.editCompanyCommission(companyCommission).clickSaveButtonForTrade();

		history.clickExitEditModeButton();

		this.retrieveTransactions("Datafeed", type, last30Days);
		LinkTransactionPage link = history.clickLinkSystemTransactionButton(transaction, LinkTransactionPage.class);

		link.clickCreateAndMatchButton();
		TradeOrderFormPage form2 = new TradeOrderFormPage(webDriver, HistoryPage.class);
		form2.clickSaveButtonForTrade();

		this.checkIfOperationDone(transaction, true);

		this.retrieveTransactions("System", type, last30Days);

		this.assertOnlyViewTransaction(transaction);

		history.clickEditHistoryButton();
		this.assertTransactionNotEditable(transaction);
		history.clickExitEditModeButton();

		LinkTransactionPage link2 = history.clickUnlinkSystemTransactionButton(transaction);

		assertEquals(instrument, this.getTransactionDetailsOfMatchedTransactionByField("Instrument"));
		assertEquals(side, this.getTransactionDetailsOfMatchedTransactionByField("Side"));
		assertTrue(this.getTransactionDetailsOfMatchedTransactionByField("Quantity (Units)").contains(quantity));
		if (isBond) {
			executionPrice = "111.1111";
		}
		assertTrue(executionPrice.contains(this.getTransactionDetailsOfMatchedTransactionByField("Execution Price")));
		assertEquals(executionDate, this.getTransactionDetailsOfMatchedTransactionByField("Execution Date"));
		assertEquals(settlementDate, this.getTransactionDetailsOfMatchedTransactionByField("Settlement Date"));
		link2.clickUnmatchButton();
		this.checkIfOperationDone(transaction, false);

		history.clickEditHistoryButton().clickDeleteTransactionButtonByName(transaction).clickExitEditModeButton();

		this.retrieveTransactions("Datafeed", orderType, "Last 30 days");
		history.clickEditHistoryButton().clickDeleteTransactionButtonByName(transaction);

	}

	@Ignore
	@Category(ThirdRock.class)
	public void testReconcileDifferentTransaction() throws Exception {
		String description = "";
		String type = "Loan";
		String executionDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		// the source has to be System since we want button for manually editing
		// history
		HistoryPage history = this.init();

		TradeOrderFormPage order = history.clickEditHistoryButton().addHistoricalTransaction("Loan",
				TradeOrderFormPage.class);

		order.editPositionType("Open").editInstrumentCurrency("HKD").editInterestRate("36.5")
				.editStartDate("1-Dec-2016").editMaturityDate("15-Dec-2017").editSettlementDate("05-Dec-2016")
				.editQuantity("10000").editExecutionDate(executionDate).clickSaveButtonForTrade();
		history.clickExitEditModeButton();
		this.retrieveTransactions("Datafeed", type, last2Years);

		description = this.getFirstUnlinkedTransactionDescriptionByType(type);

		LinkTransactionPage link = history.clickLinkSystemTransactionButton(description, LinkTransactionPage.class);
		wait(Settings.WAIT_SECONDS);
		link.editDateForTransaction("01-Dec-2016", "31-Dec-2016").clickCheckboxForTransaction("Loan", true)
				.clickMatchButton();

		assertTrue(pageContainsStr("You need to amend the System transaction to match."));
		link.clickCancelButton();
		description = "Buy HKD 10,000.00 Loan HKD 36.50% MAT 15-Dec-2017";

		this.retrieveTransactions("System", type, last2Years);

		history.clickEditHistoryButton().clickDeleteTransactionButtonByName(description);
	}

	@Ignore
	@Category(ThirdRock.class)
	public void testManualAmendCashflow() throws Exception {
		String type = "Cashflow";
		String cashflowType = "Deposit";
		String transactionDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String valueDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String currency = "USD";
		String amount = "10000";
		String status = "Completed";
		String transaction = "Inflow of USD 10,000.00 (Deposit)";
		String transactionReference = "";

		HistoryPage history = this.init();

		CashflowPage cashflow = history.clickEditHistoryButton().addHistoricalTransaction(type, CashflowPage.class);

		cashflow.editCashflowAmount(amount).editCashflowType(cashflowType).editCashflowCurrency(currency)
				.editCashflowStatus(status).editTransactionDate(transactionDate).editCashflowValueDate(valueDate)
				.clickSaveButtonForTrade().clickExitEditModeButton();

		this.retrieveTransactions("Datafeed", type, last30Days);

		LinkTransactionPage link = history.clickLinkSystemTransactionButton(transaction, HistoryPage.class);

		amount = getTextByLocator(By.xpath(
				".//*[@id='gwt-debug-TransactionMatchPopupView-currentTransactionHolder']//*[@id='gwt-debug-TransactionDepositWithdrawalEventView-amountLabel']"));
		if (amount.contains("-")) {
			amount = amount.split("-")[1];
		}
		link.clickCreateAndMatchButton();
		clickOkButtonIfVisible();

		checkIfOperationDone(transaction, true);
		this.retrieveTransactions("System", type, last2Years);

		history.clickUnlinkSystemTransactionButton(transaction).clickUnmatchButton();

		this.checkIfOperationDone(transaction, false);
		log("amount: " + amount);
		this.editAndMatchCashflow(transaction, amount, type, transactionDate);

		LinkTransactionPage link2 = new LinkTransactionPage(webDriver, HistoryPage.class);

		link2.clickAmendTransactionButton();

		checkIfOperationDone(transaction, true);

		this.retrieveTransactions("System", type, last2Years);
		history.clickUnlinkSystemTransactionButton(transaction).clickUnmatchButton();

		this.retrieveTransactions("System", type, last2Years);
		history.clickEditHistoryButton().clickDeleteTransactionButtonByName(transaction);
	}

	/**
	 * Test manual amend instrument distribution after linking two
	 * distributions.
	 * 
	 * @throws Exception
	 */

	@Ignore
	@Category(ThirdRock.class)
	public void testManualAmendInstrumentDistribution() throws Exception {
		String description = "";
		String date = "";
		String type = "Instrument Distribution";
		HistoryPage history = this.init();

		this.unlinkAllTransaction("Datafeed", type);

		description = this.getFirstUnlinkedTransactionDescriptionByType(type);

		String description2 = description.split(" is ")[0] + " is  " + description.split(" is ")[1];

		date = this.getDateOfDescriptionByOrder(description2);

		LinkTransactionPage link = history.clickLinkSystemTransactionButton(description2, LinkTransactionPage.class);
		waitForElementVisible(
				By.xpath(
						".//*[@id='gwt-debug-TransactionMatchPopupView-currentTransactionHolder']//*[@id='gwt-debug-TransactionInstrumentDistView-amountLabel']"),
				10);

		String amount = String.valueOf(NumberFormat.getNumberInstance(Locale.UK)
				.parse(getTextByLocator(By
						.xpath(".//*[@id='gwt-debug-TransactionMatchPopupView-currentTransactionHolder']//*[@id='gwt-debug-TransactionInstrumentDistView-amountLabel']"))
								.split(" ")[1]));

		String currency = getTextByLocator(By
				.xpath("(//*[@id='gwt-debug-TransactionMatchPopupView-currentTransactionHolder']//*[@id='gwt-debug-TransactionInstrumentDistView-amountLabel'])[1]"))
						.split(" ")[0];

		link.clickCreateAndMatchButton();
		clickOkButtonIfVisible();
		this.retrieveTransactions("Datafeed", type, last2Years);
		this.checkIfOperationDone(description2, true);

		history.clickUnlinkSystemTransactionButton(description2).clickUnmatchButton();

		this.retrieveTransactions("System", type, last2Years);
		InstrumentDistributionPage instrument2 = history.clickEditHistoryButton()
				.clickEditTransactionButtonByName(description2, InstrumentDistributionPage.class);

		instrument2.editAmount("12345").clickSaveButton();
		history.clickExitEditModeButton();

		this.retrieveTransactions("Datafeed", type, last2Years);

		LinkTransactionPage link2 = history.clickLinkSystemTransactionButton(description2,
				InstrumentDistributionPage.class);

		link2.editDateForTransaction(date, date).clickCheckboxForTransaction("12,345.00", true).clickMatchButton();

		InstrumentDistributionPage instrument = link2.clickAmendTransactionButton();

		instrument.editAmount(amount).editCurrency(currency).clickSaveButton().goToDetailsPage()
				.goToTransactionHistoryPage();

		this.retrieveTransactions("Datafeed", type, last2Years);
		checkIfOperationDone(description2, true);

		history.clickUnlinkSystemTransactionButton(description2).clickUnmatchButton().goToDetailsPage()
				.goToTransactionHistoryPage();

		this.retrieveTransactions("System", type, last2Years);
		checkIfOperationDone(description2, false);

		history.clickEditHistoryButton().clickDeleteTransactionButtonByName(description2);

	}

	@Ignore
	@Category(ThirdRock.class)
	public void testManualAmendOtherTransaction() throws Exception {
		String type = "Other";
		String asset = "";
		String date = "12-Oct-2016";
		String transactionDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String executionDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String settlementDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String instrumentType = "Equity";
		String instrumentType2 = "Cash";
		String instrumentType3 = "Option";
		String quantity1 = "1000";
		String instrument = "Tesla Motors Inc";
		String quantity2 = "50000";
		String currency = "USD";
		String currency2 = "EUR";
		String status = "Settled";
		String description = "3 Sub Transactions";

		HistoryPage history = this.init();

		OtherTransactionPage otherPage = history.clickEditHistoryButton().addHistoricalTransaction(type,
				OtherTransactionPage.class);

		// add first txn
		otherPage.clickAddImpactButton().editImpactInstrument(instrumentType).addInstrumentForEquityImpact(instrument)
				.editImpactQuantity(quantity1).editImpactStatus(status).clickSaveImpactButton();

		// add second txn
		otherPage.clickAddImpactButton().editImpactInstrument(instrumentType2).editImpactCurrency(currency)
				.editImpactQuantity(quantity2).editImpactStatus(status).clickSaveImpactButton();

		// add third txn
		otherPage.clickAddImpactButton().editImpactInstrument(instrumentType3).editImpactCurrency(currency2)
				.editImpactQuantity(quantity2).editImpactStatus(status).clickSaveImpactButton();

		otherPage.editTransactionDate(transactionDate).editExecutionDate(executionDate)
				.editSettlementDate(settlementDate).clickSaveButton();

		history.clickExitEditModeButton();

		this.retrieveTransactions("Datafeed", type, last30Days);

		LinkTransactionPage link = history.clickLinkSystemTransactionButton(description, HistoryPage.class);

		link.clickCreateAndMatchButton();
		clickOkButtonIfVisible();
		this.checkIfOperationDone(description, true);

		history.clickUnlinkSystemTransactionButton(description).clickUnmatchButton();

		this.checkIfOperationDone(description, false);

		retrieveTransactions("System", type, last30Days);

		OtherTransactionPage other = history.clickEditHistoryButton().clickEditTransactionButtonByName(description,
				OtherTransactionPage.class);

		asset = "Cash: USD";

		other.editImpactByAsset(asset).editImpactQuantity("12345").clickSaveImpactButton().clickSaveButton()
				.clickExitEditModeButton().editHistorySource("Datafeed").editTransactionType(type)
				.editShowTransactionsHistoryFor(last30Days).clickAutoRefreshBox(true)
				.clickLinkSystemTransactionButton(description, LinkTransactionPage.class)
				.editDateForTransaction(executionDate, executionDate).clickShowOnlyUnmatchedTransactionsBox(true)
				.clickCheckboxForTransaction(description, true).clickMatchButton();

		LinkTransactionPage link2 = new LinkTransactionPage(webDriver, OtherTransactionPage.class);

		OtherTransactionPage other2 = link2.clickAmendTransactionButton();
		HistoryPage history3 = other2.editImpactByAsset(asset).editImpactQuantity(quantity2).clickSaveImpactButton()
				.clickSaveButton().goToDetailsPage().goToTransactionHistoryPage();

		this.retrieveTransactions("System", type, last30Days);

		this.checkIfOperationDone(description, true);

		history3.clickUnlinkSystemTransactionButton(description).clickUnmatchButton();

		this.retrieveTransactions("System", type, last30Days);

		this.checkIfOperationDone(description, false);

		history.clickEditHistoryButton().clickDeleteTransactionButtonByName(description);
	}

	@Ignore
	@Category(ThirdRock.class)
	public void testManualAmendAssetFlow() throws Exception {
		String type = "Asset Flow";
		String description = "";
		String quantity = "";

		HistoryPage history = init();

		// this.unlinkAllTransaction("Datafeed", type);

		this.retrieveTransactions("Datafeed", type, last2Years);

		description = this.getFirstUnlinkedTransactionDescriptionByType(type);

		LinkTransactionPage link = history.clickLinkSystemTransactionButton(description, HistoryPage.class);

		link.clickCreateAndMatchButton();
		clickOkButtonIfVisible();
		this.checkIfOperationDone(description, true);

		history.clickUnlinkSystemTransactionButton(description).clickUnmatchButton();

		this.checkIfOperationDone(description, false);

		this.retrieveTransactions("System", type, last2Years);

		AssetflowPage assetflow = history.clickEditHistoryButton().clickEditTransactionButtonByName(description,
				AssetflowPage.class);

		quantity = String.valueOf(NumberFormat.getNumberInstance(Locale.UK)
				.parse(getTextByLocator(By.id("gwt-debug-AssetflowTransactionEditPopupView-quantityBox"))));

		assetflow.editQuantityUnit("12345").clickSaveButton().clickExitEditModeButton().editHistorySource("Datafeed")
				.editTransactionType(type).editShowTransactionsHistoryFor("Last 2 years").clickAutoRefreshBox(true)
				.clickLinkSystemTransactionButton(description, LinkTransactionPage.class)
				.clickCheckboxForTransaction("12,345.00 units", true).clickMatchButton();

		LinkTransactionPage link2 = new LinkTransactionPage(webDriver, AssetflowPage.class);

		AssetflowPage assetflow2 = link2.clickAmendTransactionButton();

		HistoryPage history3 = assetflow2.editQuantityUnit(quantity).clickSaveButton().goToDetailsPage()
				.goToTransactionHistoryPage();

		this.retrieveTransactions("System", type, last2Years);

		this.checkIfOperationDone(description, true);

		history3.clickUnlinkSystemTransactionButton(description).clickUnmatchButton();

		this.retrieveTransactions("System", type, last2Years);

		this.checkIfOperationDone(description, false);

		this.retrieveTransactions("System", type, last2Years);
		history.clickEditHistoryButton().clickDeleteTransactionButtonByName(description);

	}

	// @Ignore
	public void testManualAmendCashflowConsultant() throws Exception {

		String type = "Cashflow";
		String description = "";
		String amount = "";
		String date = "01-Oct-2016";
		String amountLabel = "";
		HistoryPage history = this.init();

		// this.unlinkAndRemoveAllSystemTransaction("Datafeed", type);
		this.retrieveTransactions("Datafeed", type, last2Years);
		description = this.getFirstUnlinkedTransactionDescriptionByType(type);
		description = description.concat(" ");

		LinkTransactionPage link = history.clickLinkSystemTransactionButton(description, HistoryPage.class);
		amountLabel = getTextByLocator(By.xpath(
				".//*[@id='gwt-debug-TransactionMatchPopupView-currentTransactionHolder']//*[@id='gwt-debug-TransactionDepositWithdrawalEventView-amountLabel']"));
		amount = String.valueOf(Math.abs((Double) NumberFormat.getNumberInstance(Locale.UK).parse(getTextByLocator(By
				.xpath(".//*[@id='gwt-debug-TransactionMatchPopupView-currentTransactionHolder']//*[@id='gwt-debug-TransactionDepositWithdrawalEventView-amountLabel']")))));

		link.clickCreateAndMatchButton();

		// initiate break workflow and hence the transaction should not be
		// linked at this stage
		assertTrue(pageContainsStr(
				"Successfully updated Transaction match/unmatch operation successfully initiated a break workflow."));
		clickOkButtonIfVisible();
		this.checkIfOperationDone(description, false);

		this.approveTransactions();

		HistoryPage history2 = init();

		this.retrieveTransactions("Datafeed", type, last2Years);

		// after approval, the transaction should be done.
		this.checkIfOperationDone(description, true);

		history2.clickUnlinkSystemTransactionButton(description).clickUnmatchButton();

		LinkTransactionPage link2 = history2.clickUnlinkSystemTransactionButton(description).clickUnmatchButton();

		link2.clickCancelButton();

		this.retrieveTransactions("Datafeed", type, last2Years);
		// before approval, the transaction should not be unmatched.
		this.checkIfOperationDone(description, true);

		this.approveTransactions();

		HistoryPage history3 = init();

		this.retrieveTransactions("System", type, last2Years);

		// after approval, the transaction should be unmatched.
		this.checkIfOperationDone(description, false);
		log("amount :" + amount);
		this.editAndMatchCashflow(description, amountLabel, type, date);

		LinkTransactionPage link3 = new LinkTransactionPage(webDriver, HistoryPage.class);

		link3.startBreakWorkflow("test");

		assertTrue(pageContainsStr(
				"Successfully updated Transaction match/unmatch operation successfully initiated a break workflow."));
		clickOkButtonIfVisible();

		this.approveTransactions();

		HistoryPage history4 = init();

		this.retrieveTransactions("System", type, last2Years);

		// after approval, the transaction should be matched.
		this.checkIfOperationDone(description, true);

		history4.clickUnlinkSystemTransactionButton(description).clickUnmatchButton();

		LinkTransactionPage link4 = history2.clickUnlinkSystemTransactionButton(description).clickUnmatchButton();

		link4.clickCancelButton();

		this.approveTransactions();
	}

	// SLYAWS-9638
	@Ignore
	@Category(ThirdRock.class)
	public void testMatchTransactionswithFilledShare() throws Exception {
		String type = "Order";
		String instrument = "Facebook Inc";
		String description = "Buy 10.00 Units " + instrument + " @ 1.1111";
		String status = "Filled fully";
		String side = "Buy";
		String orderType = "Market";
		String quantity = "10";
		String filledQuantity = "10";
		String settlementDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String executionDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String executionPrice = "1.11111111";
		double commission = 100.32112345;
		double fee = 100.68;
		String settlementAdjustmentAmount = "1000000.00000001";
		double companyCommissionRate = 1.63214578;
		String companyCommission = "1000000";
		HistoryPage history = this.init();

		TradeOrderFormPage form = history.clickEditHistoryButton().addHistoricalTransaction(type,
				TradeOrderFormPage.class);

		form.selectInstrumentAndProceed("Stock/ETF", "").addInstrument(instrument).editOrderSide(side)
				.editQuantityUnit(quantity).editOrderType(orderType).editOrderStatus(status)
				.editFilledQuantity(filledQuantity).editExecutionPrice(executionPrice)
				.editSettlementDate(settlementDate).editCommission(String.valueOf(commission))
				.editFees(String.valueOf(fee)).editSettlementAdjustmentAmount(settlementAdjustmentAmount)
				.editCompanyCommissionRate(String.valueOf(companyCommissionRate))
				.editCompanyCommission(companyCommission).editExecutionDate(executionDate).clickSaveButtonForTrade();

		history.clickExitEditModeButton();

		this.retrieveTransactions("Datafeed", type, last30Days);

		LinkTransactionPage link = history.clickLinkSystemTransactionButton(description, TradeOrderFormPage.class);

		TradeOrderFormPage trade = link.clickCreateAndMatchButton();

		trade.clickSaveButtonForReconciliation();

		history.editHistorySource("System").editTransactionType("Order").clickUnlinkSystemTransactionButton(description)
				.clickUnmatchButton();

		LinkTransactionPage link2 = history.editHistorySource("System").clickLinkSystemTransactionButton(description,
				LinkTransactionPage.class);

		link2.clickCheckboxForTransaction(description, true).clickMatchButton();

		// the transaction should be matched if they have same impact
		this.checkIfOperationDone(description, true);

		history.editHistorySource("System").clickUnlinkSystemTransactionButton(description).clickUnmatchButton();

		TradeOrderFormPage trade2 = history.clickEditHistoryButton().editTransactionByInvestment(description,
				TradeOrderFormPage.class);

		trade2.editOrderStatus("Filled partially").editFilledQuantity("5").clickSaveButtonForTrade();
		String description2 = "Buy 5.00 Units Facebook Inc @ 1.1111";
		history.clickExitEditModeButton().clickLinkSystemTransactionButton(description2, LinkTransactionPage.class);

		link2.editDateForTransaction(executionDate, executionDate).clickShowOnlyUnmatchedTransactionsBox(true)
				.clickCheckboxForTransaction(description, true).clickMatchButton();

		assertTrue(pageContainsStr(
				"The system transaction does not match the datafeed transaction and hence can't be matched"));
		link2.clickCancelButton();
		history.clickEditHistoryButton().clickDeleteTransactionButtonByName(description2);
	}

	public HistoryPage init() throws Exception {

		String account = "Solange Test CS";
		String url = "http://192.168.1.104:8080/SlyAWS/?locale=en&viewMode=MATERIAL#login";
		LoginPage main = new LoginPage(webDriver, url);

		HistoryPage history = main.loginAs(Settings.THIRDROCK_ADMIN_USERNAME, Settings.THIRDROCK_ADMIN_PASSWORD)
				.goToAccountOverviewPage().simpleSearchByString(account).goToAccountHoldingsPageByName(account)
				.goToTransactionHistoryPage();

		return history;
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
		} finally {
			assertTrue(isElementPresent(By.xpath(xpath)));
		}

	}

	/**
	 * This is to retrieve the transaction history in history page
	 * 
	 * @param source
	 * @param type
	 * @param period
	 * @throws InterruptedException
	 */
	public void retrieveTransactions(String source, String type, String period) throws InterruptedException {
		HistoryPage history = new HistoryPage(webDriver);
		history.editShowTransactionsHistoryFor(period).editHistorySource(source).editTransactionType(type)
				.clickAutoRefreshBox(true);
		try {
			waitForElementVisible(By.id("gwt-debug-TransactionDetailsView-descLabel"), 30);
		} catch (TimeoutException e) {
			waitForElementVisible(By.id("gwt-debug-TransactionListView-noEntriesMessagePanel"), 30);
		}

	}

	/**
	 * This is to approve transaction reconciliation which is initiated by
	 * non-admin role
	 * 
	 * @throws InterruptedException
	 */
	public void approveTransactions() throws InterruptedException {

		this.checkLogout();
		handleAlert();

		String url = "http://192.168.1.104:8080/SlyAWS/?locale=en&viewMode=MATERIAL#login";
		LoginPage main = new LoginPage(webDriver, url);

		TasksPage task = main.loginAs(Settings.THIRDROCK_ADMIN_USERNAME, Settings.THIRDROCK_ADMIN_PASSWORD)
				.goToTasksPage();

		task.checkShowOnlyMyTasks(false);

		int size = getSizeOfElements(By.xpath(".//*[@id='gwt-debug-TaskOverviewTable-tablePanel']//a[.='User Task']"));

		clickElement(By.xpath("(.//*[@id='gwt-debug-TaskOverviewTable-tablePanel']//a[.='User Task'])" + "["
				+ String.valueOf(size) + "]"));

		task.editTaskStatus("Completed").editApproveTransaction("Yes").clickSaveButton();

		this.checkLogout();
		handleAlert();

	}

	public void unlinkAndRemoveAllSystemTransaction(String source, String type) throws InterruptedException {

		HistoryPage history = new HistoryPage(webDriver);

		if (!source.equals("System")) {
			this.unlinkAllTransaction(source, type);
		}

		this.unlinkAllTransaction("System", type);

		history.clickEditHistoryButton();

		int size = getSizeOfElements(By.xpath(".//*[contains(@id,'deleteTransactionImg')]"));

		for (int i = 1; i <= size; i++) {
			clickElement(By.xpath("(.//*[contains(@id,'deleteTransactionImg')])[" + i + "]"));
			clickYesButtonIfVisible();
			clickOkButtonIfVisible();
		}
		history.clickExitEditModeButton();
		this.retrieveTransactions(source, type, last2Years);
	}

	public void unlinkAllTransaction(String source, String type) throws InterruptedException {

		HistoryPage history = new HistoryPage(webDriver);

		this.retrieveTransactions(source, type, last2Years);

		int size = getSizeOfElements(By.xpath(".//*[contains(text(),'" + type
				+ "')]/parent::td/following-sibling::td//*[@id='gwt-debug-TransactionDetailsView-transactionLink' and not(@aria-hidden='true')]"));

		for (int i = 0; i < size; i++) {
			history.clickUnlinkSystemTransactionButton(type).clickUnmatchButton();
			this.retrieveTransactions(source, type, last2Years);
		}

		if (size > 1) {
			this.checkIfOperationDone(type, false);
		}

	}

	public void editAndMatchCashflow(String description, String amount, String type, String date) throws Exception {
		String transactionDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		HistoryPage history = new HistoryPage(webDriver);

		history.clickEditHistoryButton().clickEditTransactionButtonByName(description, HistoryPage.class);

		log("amount2 :" + amount);
		CashflowPage cashflow = new CashflowPage(webDriver);
		cashflow.editCashflowAmount("123").editCashflowValueDate(date).editTransactionDate(date)
				.editCashflowValueDate(date).clickSaveButton_CashflowEntry();
		history.clickExitEditModeButton().goToTransactionHistoryPage();

		this.retrieveTransactions("Datafeed", type, last2Years);

		history.clickLinkSystemTransactionButton(description, LinkTransactionPage.class)
				.editDateForTransaction(date, date).clickCheckboxForTransaction("123", true).clickMatchButton();
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
				.xpath("//*[@id='gwt-debug-TransactionDetailsView-transactionLinkBroken' and not(@aria-hidden='true')]//parent::td//preceding-sibling::td[div[contains(text(),'"
						+ type + "')]and a[@aria-hidden]]//following-sibling::td[1]//*"));
	}

	/**
	 * After clicking unlink button of transaction,this can return get the
	 * transaction details of match transaction by given field.
	 * 
	 * @param field
	 * @return
	 */
	public String getTransactionDetailsOfMatchedTransactionByField(String field) {
		WebElement elem;
		String text = "";
		try {
			elem = webDriver.findElement(By
					.xpath(".//div[@id='gwt-debug-TransactionUnMatchPopupView-matchedTransactionHolder']//div[@id='gwt-debug-TransactionDetailsView-detailsPanelA']//td[contains(text(),'"
							+ field + "')]//following-sibling::td"));
			text = getTextByLocator(By
					.xpath(".//div[@id='gwt-debug-TransactionUnMatchPopupView-matchedTransactionHolder']//div[@id='gwt-debug-TransactionDetailsView-detailsPanelA']//td[contains(text(),'"
							+ field + "')]//following-sibling::td"));
		} catch (NoSuchElementException e) {
			text = getTextByLocator(By
					.xpath(".//div[@id='gwt-debug-TransactionUnMatchPopupView-matchedTransactionHolder']//div[@id='gwt-debug-TransactionDetailsView-detailsPanelA']//td[div[contains(text(),'"
							+ field + "')]]//following-sibling::td"));
		}
		return text;
	}

	public String getDateOfDescriptionByOrder(String order) {
		return getTextByLocator(By.xpath(".//div[@id='gwt-debug-TransactionListView-tableContentsPanel']//*[.='" + order
				+ "']//parent::td//preceding-sibling::td//*[@id='gwt-debug-TransactionDetailsView-dateLabel']"));
	}

	public void assertTransactionNotEditable(String transaction) {
		assertTrue(isElementPresent(By.xpath(".//td[a[contains(text(),'" + transaction
				+ "')]]//following-sibling::td//button[@id='gwt-debug-TransactionDetailsView-editTransactionImg' and @aria-hidden='true']")));

		assertTrue(isElementPresent(By.xpath(".//td[a[contains(text(),'" + transaction
				+ "')]]//following-sibling::td//button[@id='gwt-debug-TransactionDetailsView-deleteTransactionImg' and @aria-hidden='true']")));
	}

	public void assertOnlyViewTransaction(String transaction) {
		clickElement(By.xpath(".//*[contains(text(),'" + transaction + "')]"));
		assertTrue(isElementVisible(By.xpath(".//*[@class='disableChild']")));
		clickElement(By.xpath(".//button[.='Cancel']"));
	}
}
