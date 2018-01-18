package org.sly.uitest.sections.transactionreconciliation;

/**
 * This is the test for format of txn history and editting txn
 * The flow is: create -> check history -> click edit txn -> check existing data
 * -> edit txn-> check history -> delete txn 
 * @author Benny Leung
 * @date Jan 23, 2017
 * @company Prive Financial
 */
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
import org.sly.uitest.pageobjects.clientsandaccounts.OtherTransactionPage;
import org.sly.uitest.pageobjects.clientsandaccounts.TradeOrderFormPage;
import org.sly.uitest.settings.Settings;

public class TransactionHistoryTest extends AbstractTest {

	@Ignore
	@Category(ThirdRock.class)
	public void testBondHistory() throws Exception {
		String instrument = "180 Plus Note";
		this.testSecurityHistory("Bond", instrument);
	}

	@Ignore
	@Category(ThirdRock.class)
	public void testEquityHistory() throws Exception {
		String instrument = "Tesla Motors Inc";
		this.testSecurityHistory("Stock/ETF", instrument);
	}

	@Ignore
	@Category(ThirdRock.class)
	public void testMutualFundHistory() throws Exception {
		String instrument = "Aberdeen China Opportunities USD";
		this.testSecurityHistory("Funds", instrument);
	}

	@Ignore
	@Category(ThirdRock.class)
	public void testLoanHistory() throws Exception {
		this.testLoanAndDepositHistory("Loan");
	}

	@Ignore
	@Category(ThirdRock.class)
	public void testDepositHistory() throws Exception {
		this.testLoanAndDepositHistory("Deposit");
	}

	@Ignore
	@Category(ThirdRock.class)
	public void testFXSpotHistory() throws Exception {
		String type = "Order";
		String currency = "USD";
		String counterCurrency = "EUR";
		String amount = "10000";
		String sellAmount = "11111.11";
		String orderType = "Market";
		String status = "Filled partially";
		String executionPrice = "1.11111111";
		String settlementDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String executionDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String commission = "123456.789";
		String transactionReference = "";
		String companyCommissionRate = "1.3215478";
		String instrument = "FX: EUR/USD";
		String buyValue = "USD 10,000.00";
		String sellValue = "EUR 11,111.11";
		String revertedExecutionPrice = "0.90000000";
		String flippedExecutionPriceLabel = "USD/EUR @ 1.11111111";
		String executionPriceLabel = "EUR/USD @ 0.90000000";
		String transaction = "Buy USD 10,000.00 FX: EUR/USD @ 0.90000000";
		HistoryPage history = this.init();
		TradeOrderFormPage form = history.addHistoricalTransaction(type, TradeOrderFormPage.class);
		form.selectInstrumentAndProceed("FX", "").editInstrumentCurrencyAndCounterCurrency(currency, counterCurrency)
				.editBuyCurrencyAndAmount(currency, amount).editOrderType(orderType).editOrderStatus(status)
				.editExecutionPrice(executionPrice).editSettlementDate(settlementDate).editCommission(commission)
				.editCompanyCommissionRate(companyCommissionRate).editExecutionDate(executionDate)
				.clickSaveButtonForTrade();

		history.clickExitEditModeButton().editTransactionType(type).editShowTransactionsHistoryFor("Last 30 days")
				.expandTransactionByInvestment(transaction);

		assertEquals(instrument, this.getTransactionHistoryByField(transaction, "Instrument"));
		assertEquals(buyValue, this.getTransactionHistoryByField(transaction, "Buy"));
		assertEquals(sellValue, this.getTransactionHistoryByField(transaction, "Sell"));

		assertEquals(settlementDate, this.getTransactionHistoryByField(transaction, "Execution Date"));
		assertEquals(settlementDate, this.getTransactionHistoryByField(transaction, "Settlement Date"));
		assertEquals(executionPriceLabel,
				getTextByLocator(By.id("gwt-debug-TransactionPortfolioFXOrderDetailsView-exePriceLabel")));
		assertEquals(flippedExecutionPriceLabel,
				getTextByLocator(By.id("gwt-debug-TransactionPortfolioFXOrderDetailsView-flippedExePriceLabel")));

		history.clickEditHistoryButton().clickEditTransactionButtonByName(transaction, TradeOrderFormPage.class);

		assertEquals(currency, this.getSelectedOptionNextToField("Buy Currency"));
		assertEquals(amount, this.getTextFromTextboxByField("Buy Amount"));
		assertEquals(counterCurrency, this.getSelectedOptionNextToField("Sell Currency"));
		assertEquals(sellAmount, this.getTextFromTextboxByField("Sell Amount"));
		assertEquals(status, this.getSelectedOptionNextToField("Order Status"));
		assertEquals(executionPrice, getTextByLocator(By.xpath(".//input[contains(@id,'exePriceTextBox')]")));
		assertEquals(settlementDate, this.getTextFromTextboxByField("Settlement Date"));
		assertTrue(companyCommissionRate.contains(this.getTextFromTextboxByField("Commission Rate").split("%")[0]));

		transactionReference = getTextByLocator(By.id("gwt-debug-RelatedReferenceWidget-txnRefLabel"));
		executionPrice = "2.22222222";

		form.editBuyCurrencyAndAmount(counterCurrency, amount).editExecutionPrice(executionPrice)
				.clickSaveButtonForTrade();

		transaction = "Buy EUR 10,000.00 FX: EUR/USD @ 0.45000000";
		buyValue = "EUR 10,000.00";
		executionPriceLabel = "EUR/USD @ 0.45000000";
		flippedExecutionPriceLabel = "USD/EUR @ 2.22222222";
		history.clickExitEditModeButton().expandTransactionByInvestment(transaction);

		assertEquals(instrument, this.getTransactionHistoryByField(transaction, "Instrument"));
		assertEquals(buyValue, this.getTransactionHistoryByField(transaction, "Buy"));
		// assertEquals(sellValue,
		// this.getTransactionHistoryByField(transaction, "Sell"));
		assertEquals(settlementDate, this.getTransactionHistoryByField(transaction, "Execution Date"));
		assertEquals(settlementDate, this.getTransactionHistoryByField(transaction, "Settlement Date"));
		assertEquals(executionPriceLabel,
				getTextByLocator(By.id("gwt-debug-TransactionPortfolioFXOrderDetailsView-exePriceLabel")));
		assertEquals(flippedExecutionPriceLabel,
				getTextByLocator(By.id("gwt-debug-TransactionPortfolioFXOrderDetailsView-flippedExePriceLabel")));
	}

	@Ignore
	@Category(ThirdRock.class)
	public void testAssetFlowHistory() throws Exception {
		String type = "Asset Flow";
		String executionDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String settlementDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String direction = "Inflow";
		String asset = "Tesla Motors Inc";
		String quantity = "100";
		String value = "100";
		String status = "Completed";
		String transaction = direction + " of 100.00 units of " + asset;
		HistoryPage history = init();
		AssetflowPage assetflow = history.addHistoricalTransaction("Asset Flow", AssetflowPage.class);

		assetflow.editDirection(direction).addAsset(asset).editValue(value).editQuantityUnit(quantity)
				.editStatus(status).editExecutionDate(executionDate).editSettlementDate(settlementDate)
				.clickSaveButton();

		history.clickExitEditModeButton().editTransactionType(type).editShowTransactionsHistoryFor("Last 30 days")
				.expandTransactionByInvestment(transaction);

		assertEquals(asset, this.getTransactionHistoryByField(transaction, "Instrument"));
		assertEquals("100.00", this.getTransactionHistoryByField(transaction, "Quantity"));
		assertEquals(settlementDate, this.getTransactionHistoryByField(transaction, "Transaction Date"));
		assertEquals(executionDate, this.getTransactionHistoryByField(transaction, "Value Date"));

		history.clickEditHistoryButton().clickEditTransactionButtonByName(transaction, AssetflowPage.class);

		assertEquals(direction, this.getSelectedOptionNextToField("Impact"));
		// assertEquals(asset, this.getTextValueNextToField("Asset"));
		assertEquals(quantity, this.getTextFromTextboxByField("Quantity"));
		assertEquals(status, this.getSelectedOptionNextToField("Status"));
		assertEquals(executionDate, this.getTextFromTextboxByField("Transaction Date"));
		assertEquals(settlementDate, this.getTextFromTextboxByField("Value Date"));

		assetflow.clickCancelButton();
		history.clickDeleteTransactionButtonByName(transaction);

	}

	@Ignore
	@Category(ThirdRock.class)
	public void testCashflowHistory() throws Exception {
		String type = "Cashflow";
		String cashflowType = "Deposit";
		String transactionDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String valueDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String currency = "USD";
		String amount = "10000";
		String status = "Completed";
		String transaction = "Inflow of USD 10,000.00 (Deposit)";
		String transactionReference = "";
		HistoryPage history = init();

		CashflowPage cashflow = history.addHistoricalTransaction(type, CashflowPage.class);

		cashflow.editCashflowAmount(amount).editCashflowType(cashflowType).editCashflowCurrency(currency)
				.editCashflowStatus(status).editTransactionDate(transactionDate).editCashflowValueDate(valueDate)
				.clickSaveButtonForTrade();

		history.clickExitEditModeButton().editTransactionType(type).editShowTransactionsHistoryFor("Last 30 days")
				.expandTransactionByInvestment(transaction);

		assertEquals("USD 10,000.00", this.getTransactionHistoryByField(transaction, "Amount"));
		assertEquals(valueDate, this.getTransactionHistoryByField(transaction, "Value Date"));
		assertEquals(cashflowType, this.getTransactionHistoryByField(transaction, "Cash Flow Type"));
		assertTrue(this.getTransactionHistoryByField(transaction, "Transaction Reference").contains("CF"));

		history.clickEditHistoryButton().clickEditTransactionButtonByName(transaction, CashflowPage.class);

		assertEquals(cashflowType, this.getSelectedOptionNextToField("Cash Flow Type"));
		assertEquals("Inflow", this.getTextValueNextToField("Cash Flow Impact"));
		assertEquals(transactionDate, this.getTextFromTextboxByField("Transaction Date"));
		assertEquals(valueDate, this.getTextFromTextboxByField("Value Date"));
		assertEquals(currency, this.getSelectedOptionNextToField("Currency"));
		assertEquals("10,000.00", this.getTextFromTextboxByField("Amount"));
		assertEquals("Completed", this.getSelectedOptionNextToField("Status"));

		transactionReference = getTextByLocator(By.id("gwt-debug-RelatedReferenceWidget-txnRefLabel"));

		cashflowType = "Interest Due";
		transaction = "Outflow of EUR 10,000.00 (Interest Due)";
		currency = "EUR";
		cashflow.editCashflowType(cashflowType).editCashflowCurrency(currency).clickSaveButtonForTrade()
				.expandTransactionByInvestment(transaction);

		assertEquals(transactionReference, this.getTransactionHistoryByField(transaction, "Transaction Reference"));
		assertEquals("EUR -10,000.00", this.getTransactionHistoryByField(transaction, "Amount"));
		assertEquals(cashflowType, this.getTransactionHistoryByField(transaction, "Cash Flow Type"));
		history.clickDeleteTransactionButtonByName(transaction);
	}

	@Ignore
	@Category(ThirdRock.class)
	public void testInstrumentDistributionHistory() throws Exception {

		String type = "Instrument Distribution";
		String instrumentType = "Cash";
		String asset = "";
		String currency = "USD";
		String amount = "100";
		String quantityToCheck = "USD 100.00";
		String status = "Completed";
		String executionDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String settlementDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String exDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String cumDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String transaction = "The distribution amount is  " + quantityToCheck;
		HistoryPage history = init();
		InstrumentDistributionPage distribution = history.addHistoricalTransaction(type,
				InstrumentDistributionPage.class);

		asset = getTextByLocator(
				By.xpath(".//select[@id='gwt-debug-TransactionInstDistEditPopupView-assetListBox']/option[2]"));
		log(asset);
		distribution.editType(instrumentType).editAsset(asset).editCurrency(currency).editAmount(amount)
				.editStatus(status).editExecutionDate(executionDate).editSettlementDate(settlementDate)
				.editExDate(exDate).editCumDate(cumDate).clickSaveButton().clickExitEditModeButton()
				.editHistorySource("System").editTransactionType(type).editShowTransactionsHistoryFor("Last 30 days")
				.expandTransactionByInvestment(transaction);

		log(transaction);
		assertEquals(exDate, this.getTransactionHistoryByField(transaction, "Ex Date"));
		assertEquals(cumDate, this.getTransactionHistoryByField(transaction, "Value Date"));
		assertEquals(asset, this.getTransactionHistoryByField(transaction, "Instrument"));

		assertEquals(quantityToCheck, this.getTransactionHistoryByField(transaction, "Amount"));

		history.clickEditHistoryButton().clickEditTransactionButtonByName(transaction,
				InstrumentDistributionPage.class);

		instrumentType = "Cash";
		currency = "EUR";
		quantityToCheck = "EUR 100.00";
		asset = "BMW";
		transaction = "The distribution amount is  " + quantityToCheck;
		this.waitForElementNotVisible(By.id("TransactionInstDistEditPopupView-waitScreen"), 30);

		distribution.editAsset("Other").addInvestmentForInstrumentDistribution(asset).editCurrency(currency)
				.clickSaveButton().expandTransactionByInvestment(transaction);

		log(transaction);
		log(asset);
		log(this.getTransactionHistoryByField(transaction, "Instrument"));
		assertEquals(asset, this.getTransactionHistoryByField(transaction, "Instrument"));

		assertTrue(this.getTransactionHistoryByField(transaction, "Amount").contains(amount));
		history.clickDeleteTransactionButtonByName(transaction);
	}

	@Ignore
	@Category(ThirdRock.class)
	public void testOtherTransactionHistory() throws Exception {
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
		String transaction = "2 Sub Transactions";

		HistoryPage history = init();
		OtherTransactionPage otherPage = history.addHistoricalTransaction(type, OtherTransactionPage.class);

		// add first txn
		otherPage.clickAddImpactButton().editImpactInstrument(instrumentType).addInstrumentForEquityImpact(instrument)
				.editImpactQuantity(quantity1).editImpactStatus(status).clickSaveImpactButton();

		// add second txn
		otherPage.clickAddImpactButton().editImpactInstrument(instrumentType2).editImpactCurrency(currency)
				.editImpactQuantity(quantity2).editImpactStatus(status).clickSaveImpactButton();

		// // add third txn
		// otherPage.clickAddImpactButton().editImpactInstrument(instrumentType3).editImpactCurrency(currency2)
		// .editImpactQuantity(quantity2).editImpactStatus(status).clickSaveImpactButton();

		otherPage.editTransactionDate(transactionDate).editExecutionDate(executionDate)
				.editSettlementDate(settlementDate).clickSaveButton();

		history.clickExitEditModeButton().editHistorySource("System").editTransactionType(type)
				.expandTransactionByInvestment(transaction);

		assertEquals(settlementDate, this.getTransactionHistoryByField(transactionDate, "Settlement Date"));
		assertEquals(executionDate, this.getTransactionHistoryByField(transactionDate, "Execution Date"));

		assertTrue(assertImpactIsVisible("2 Sub Transactions", instrument, "Units 1,000.00", status));
		assertTrue(assertImpactIsVisible("2 Sub Transactions", "Cash Balance", "USD 50,000.00", status));

		history.clickEditHistoryButton().clickEditTransactionButtonByName(transaction, OtherTransactionPage.class);
		transaction = "1 Sub Transactions";
		otherPage.clickDeleteButtonForImpact("50,000", "Cash: USD", "Settled").clickSaveButton()
				.expandTransactionByInvestment(transaction);

		assertFalse(assertImpactIsVisible("1 Sub Transactions", "Cash Balance", "USD 50,000.00", status));

		history.clickDeleteTransactionButtonByName(transaction);

	}

	@Ignore
	@Category(ThirdRock.class)
	public void testEquityOptionHistory() throws Exception {
		String type = "Order";
		String positionType = "Open";
		String optionType = "Call";
		String optionStyle = "American";
		String strike = "1";
		String expirationDate = this.getDateAfterDays(this.getCurrentTimeInFormat("dd-MMM-yyyy"), 60, "dd-MMM-yyyy");
		String finalSettlementDate = this.getDateAfterDays(this.getCurrentTimeInFormat("dd-MMM-yyyy"), 60,
				"dd-MMM-yyyy");
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
		String commission = "100";
		String settlementAmount = "";
		String instrument = symbol + " " + this.getDateAfterDays(this.getCurrentTimeInFormat("yyMMdd"), 60, "yyMMdd")
				+ " " + optionType + " " + strike;
		String transaction = "Buy 10.00 " + instrument;
		String executionDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");

		HistoryPage history = init();
		TradeOrderFormPage form = history.addHistoricalTransaction(type, TradeOrderFormPage.class);
		form.selectInstrumentAndProceed("Option", "Equity Option").editPositionType(positionType)
				.clickAddUnderlyingButton().addTicker(exchangeForTicker, symbol);
		waitForElementVisible(By.id("gwt-debug-InstrumentOptionView-underlyingLabel"), 10);
		form.editOptionType(optionType).editOptionStyle(optionStyle).editStrike(strike)
				.editExpirationDate(expirationDate).editExchange(exchange).editOrderSide(side)
				.editQuantityUnit(quantity).editOrderType(orderType).editOrderStatus(orderStatus)
				.editFilledQuantity(filledQuantity).editExecutionPrice(executionPrice)
				.editSettlementDate(settlementDate).editExecutionDate(executionDate)
				.editFinalSettlementDate(finalSettlementDate);

		settlementAmount = getTextByLocator(By.id("gwt-debug-MyOrderEditPopupView-netSettlementAmountLabel"));

		form.clickSaveButtonForTrade();
		history.clickExitEditModeButton().editHistorySource("System").editTransactionType(type)
				.editShowTransactionsHistoryFor("Last 30 days").expandTransactionByInvestment(transaction);

		assertEquals(instrument, this.getTransactionHistoryByField(transaction, "Instrument"));
		assertEquals(optionType + " " + optionStyle, this.getTransactionHistoryByField(transaction, "Option"));
		assertEquals("USD 1.00", this.getTransactionHistoryByField(transaction, "Strike"));
		assertEquals(expirationDate, this.getTransactionHistoryByField(transaction, "Expiration Date"));
		assertEquals(side, this.getTransactionHistoryByField(transaction, "Side"));
		assertTrue(this.getTransactionHistoryByField(transaction, "Quantity (Units)").contains(filledQuantity));
		assertTrue(executionPrice.contains(this.getTransactionHistoryByField(transaction, "Execution Price")));
		assertEquals(executionDate, this.getTransactionHistoryByField(transaction, "Execution Date"));
		assertEquals(settlementDate, this.getTransactionHistoryByField(transaction, "Premium Settlement Date"));
		assertEquals("NA", this.getTransactionHistoryByField(transaction, "Total Commission and Fees"));
		assertTrue(this.getTransactionHistoryByField(transaction, "Settlement Amount").contains(settlementAmount));

		history.clickEditHistoryButton().clickEditTransactionButtonByName(transaction, TradeOrderFormPage.class);

		assertEquals("Option", this.getTextValueNextToField("Instrument Type"));
		assertEquals(positionType, this.getSelectedOptionNextToField("Position Type"));
		assertEquals(instrument, this.getTextValueNextToField("Instrument Name"));
		assertEquals(optionType, this.getSelectedOptionNextToField("Option Type"));
		assertEquals(optionStyle, this.getSelectedOptionNextToField("Option Style"));
		assertEquals(currency, this.getSelectedOptionNextToField("Currency"));
		assertEquals(strike, this.getTextFromTextboxByField("Strike"));
		assertEquals(expirationDate, this.getTextFromTextboxByField("Expiration Date"));
		assertEquals(strike, this.getTextFromTextboxByField("Strike"));
		assertEquals(exchange, this.getSelectedOptionNextToField("Exchange"));
		assertEquals(side, this.getSelectedOptionNextToField("Side"));
		assertEquals(quantity, this.getTextFromTextboxByField("Quantity (Units)"));
		assertEquals(orderType, this.getSelectedOptionNextToField("Order Type"));
		assertEquals(orderStatus, this.getSelectedOptionNextToField("Order Status"));
		assertEquals(settlementDate, this.getTextFromTextboxByField("Settlement Date"));
		assertEquals(executionPrice, this.getTextFromTextboxByField("Execution Price"));
		assertEquals(executionDate, this.getTextFromTextboxByField("Execution Date"));

		HistoryPage history2 = form.clickCancelButton();
		history2.clickDeleteTransactionButtonByName(transaction);
	}

	@Ignore
	@Category(ThirdRock.class)
	public void testFXOptionHistory() throws Exception {
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
		String premiumAmount = "10";
		String marketCut = "Hong Kong";
		String settlementDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String finalSettlementDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String instrument = counterCurrency + "/" + currency + " " + this.getCurrentTimeInFormat("yyMMdd") + " "
				+ optionType + " " + strike;
		String transaction = side + " " + instrument;
		HistoryPage history = init();

		TradeOrderFormPage form = history.addHistoricalTransaction(type, TradeOrderFormPage.class);
		form.selectInstrumentAndProceed("Option", "FX Option").editPositionType(positionType).editOptionType(optionType)
				.editOptionStyle(optionStyle).editStrike(strike).editStrikeCurrency(currency, counterCurrency)
				.editMarketCut(marketCut).editExpirationDate(expirationDate).editExchange(exchange).editOrderSide(side)
				.editQuantityUnit(quantity).editOrderType(orderType).editOrderStatus(orderStatus)
				.editExecutionDate(executionDate).editFilledQuantity(filledQuantity).editPremiumAmount(premiumAmount)
				.editSettlementDate(settlementDate).editFinalSettlementDate(finalSettlementDate)
				.clickSaveButtonForTrade();

		log(transaction);

		history.clickExitEditModeButton().expandTransactionByInvestment(transaction);

		assertTrue(this.getTransactionHistoryByField(transaction, "Instrument").contains(instrument));
		assertEquals(optionType + " " + optionStyle, this.getTransactionHistoryByField(transaction, "Option"));
		assertEquals(expirationDate, this.getTransactionHistoryByField(transaction, "Expiration Date"));

		assertEquals(side, this.getTransactionHistoryByField(transaction, "Side"));
		assertTrue(
				this.getTransactionHistoryByField(transaction, "Counter").contains(counterCurrency + " " + "10,000"));

		assertTrue(this.getTransactionHistoryByField(transaction, "Transaction Reference").contains("O"));
		// assertEquals(executionDate,
		// this.getTransactionHistoryByField(transaction, "Execution Date"));
		assertEquals(settlementDate, this.getTransactionHistoryByField(transaction, "Premium Settlement Date"));

		history.clickEditHistoryButton().clickDeleteTransactionButtonByName(transaction);

		history.addHistoricalTransaction(type, TradeOrderFormPage.class);

		optionType = "Put";
		optionStyle = "European";
		side = "Sell";
		quantity = "200.00";
		filledQuantity = "200.00";
		currency = "EUR";
		counterCurrency = "USD";
		instrument = currency + "/" + counterCurrency + " " + this.getCurrentTimeInFormat("yyMMdd") + " " + optionType
				+ " " + strike;
		transaction = side + " " + instrument;
		log("transaction2: " + transaction);
		form.selectInstrumentAndProceed("Option", "FX Option").editPositionType(positionType).editOptionType(optionType)
				.editOptionStyle(optionStyle).editStrike(strike).editStrikeCurrency(currency, counterCurrency)
				.editExpirationDate(expirationDate).editExchange(exchange).editOrderSide(side)
				.editQuantityUnit(quantity).editOrderType(orderType).editOrderStatus(orderStatus)
				.editExecutionDate(executionDate).editFilledQuantity(filledQuantity).editPremiumAmount(premiumAmount)
				.editSettlementDate(settlementDate).editFinalSettlementDate(finalSettlementDate)
				.editFinalSettlementDate(finalSettlementDate).editMarketCut(marketCut).clickSaveButtonForTrade();

		history.clickExitEditModeButton().expandTransactionByInvestment(transaction);

		assertTrue(this.getTransactionHistoryByField(transaction, "Instrument").contains(instrument));
		assertEquals(optionType + " " + optionStyle, this.getTransactionHistoryByField(transaction, "Option"));
		assertEquals(side, this.getTransactionHistoryByField(transaction, "Side"));

		history.clickEditHistoryButton().clickDeleteTransactionButtonByName(transaction);
	}

	@Test
	public void testFuturesHistory() {

	}

	public void testSecurityHistory(String intrumentType, String instrument) throws Exception {

		String status = "Filled fully";
		String transaction = "Buy 10.00 Units " + instrument + " @ 1.1111";
		String isin = "";
		String currency = "";
		String exchange = "";
		String side = "Buy";
		String type = "Order";
		String orderType = "Market";
		String quantity = "10";
		String amount = "";
		String executionDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String filledQuantity = "10";
		String filledAmount = "";
		String settlementDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String executionPrice = "1.11111111";
		double commission = 100.32112345;
		double fee = 100.68;
		String settlementAdjustmentAmount = "1000000.00000001";
		double companyCommissionRate = 1.63214578;
		String companyCommission = "1000000";

		HistoryPage history = init();

		TradeOrderFormPage form = history.addHistoricalTransaction(type, TradeOrderFormPage.class);

		form.selectInstrumentAndProceed(intrumentType, "").addInstrument(instrument).editOrderSide(side)
				.editQuantityUnit(quantity).editOrderType(orderType).editOrderStatus(status)
				.editFilledQuantity(filledQuantity).editExecutionPrice(executionPrice)
				.editSettlementDate(settlementDate).editExecutionDate(executionDate)
				.editCommission(String.valueOf(commission)).editFees(String.valueOf(fee))
				.editSettlementAdjustmentAmount(settlementAdjustmentAmount)
				.editCompanyCommissionRate(String.valueOf(companyCommissionRate))
				.editCompanyCommission(companyCommission);

		isin = getTextByLocator(By.id("gwt-debug-InstrumentSecuritiesView-assetIsinLabel"));
		currency = getTextByLocator(By.id("gwt-debug-InstrumentSecuritiesView-assetCurrencyLabel"));

		if (this.getTextByLocator(By.id("gwt-debug-MyOrderEditPopupView-instrumentTypelabel"))
				.contains("Mutual Funds")) {
			exchange = "";
		} else {
			exchange = getTextByLocator(By.id("gwt-debug-InstrumentSecuritiesView-exchangeLabel"));
		}

		form.clickSaveButtonForTrade();

		history.clickExitEditModeButton().editTransactionType("Order").editShowTransactionsHistoryFor("Last 30 days")
				.expandTransactionByInvestment(transaction);

		String totalCommission = "USD 201.00";

		String settlementAmount = "USD 1,000,005.56";
		String transactionReference = "";

		this.assertFieldsForSecurityInTransactionHistory(transaction, instrument, isin, currency, exchange, side,
				executionPrice, settlementDate, totalCommission, settlementAmount, transactionReference);

		history.clickEditHistoryButton().editTransactionByInvestment(transaction, TradeOrderFormPage.class);

		this.assertFieldsForSecurityInTradeOrderForm("", instrument, exchange, currency, isin, side, quantity, amount,
				orderType, status, filledQuantity, filledAmount, executionPrice, String.valueOf(commission),
				String.valueOf(fee), settlementAdjustmentAmount, String.valueOf(companyCommissionRate));

		side = "Sell";
		status = "Filled fully";

		transactionReference = getTextByLocator(By.id("gwt-debug-RelatedReferenceWidget-txnRefLabel"));
		form.editOrderSide(side).editOrderStatus(status).clickSaveButtonForTrade();

		transaction = "Sell 10.00 Units " + instrument + " @ 1.1111";

		history.clickExitEditModeButton().editTransactionType("Order").editShowTransactionsHistoryFor("Last 30 days")
				.expandTransactionByInvestment(transaction);

		this.assertFieldsForSecurityInTransactionHistory(transaction, instrument, isin, currency, exchange, side,
				executionPrice, settlementDate, totalCommission, settlementAmount, transactionReference);

		history.clickEditHistoryButton().clickDeleteTransactionButtonByName(transaction);
	}

	public void testLoanAndDepositHistory(String type) throws Exception {

		String depositType = "Time Deposit";
		String positionType = "Open";
		String side = "Buy";
		String currency = "USD";
		String interestRate = "36.5";
		String startDate = "01-Dec-2016";
		String maturityDate = "31-Dec-2017";
		String principalAmount = "10000";
		String amountToVerify = "10,000.00";
		String orderStatus = "Settled";
		String valueDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String executionDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String interestAmount = "3,467.50";
		String settlementAdjustmentAmount = "10000";
		String transactionReference = "";
		String instrument = type + " " + currency + " 36.50% MAT " + maturityDate;
		String transaction = side + " " + currency + " " + amountToVerify + " " + instrument;
		String dayCountConvention = "ACT/360";
		HistoryPage history = init();
		TradeOrderFormPage form = history.addHistoricalTransaction(type, TradeOrderFormPage.class);
		log(transaction);
		if (type.equals("Deposit")) {
			form.editDepositType(depositType);
			instrument = depositType + " " + currency + " 36.50% MAT " + maturityDate;
			transaction = side + " " + currency + " " + amountToVerify + " " + instrument;
		}
		form.editPositionType(positionType).editInstrumentCurrency(currency).editInterestRate(interestRate)
				.editStartDate(startDate).editMaturityDate(maturityDate).editQuantity(principalAmount)
				.editOrderStatus(orderStatus).editSettlementDate(valueDate).editExecutionDate(executionDate)
				.editSettlementAdjustmentAmount(settlementAdjustmentAmount).clickCalculateInterestAmountButton()
				.clickSaveButtonForTrade();

		history.clickExitEditModeButton().editTransactionType(type).editShowTransactionsHistoryFor("Last 30 days")
				.expandTransactionByInvestment(transaction);

		assertEquals(instrument, this.getTransactionHistoryByField(transaction, "Instrument"));
		assertEquals(interestRate + "%", this.getTransactionHistoryByField(transaction, "Interest rate"));
		assertEquals(maturityDate, this.getTransactionHistoryByField(transaction, "Maturity date"));
		assertEquals(side, this.getTransactionHistoryByField(transaction, "Side"));
		assertEquals(currency + " " + amountToVerify, this.getTransactionHistoryByField(transaction, "Amount"));
		assertEquals(currency + " " + amountToVerify,
				this.getTransactionHistoryByField(transaction, "Settlement Amount"));
		assertEquals(executionDate, this.getTransactionHistoryByField(transaction, "Execution date"));
		assertEquals(valueDate, this.getTransactionHistoryByField(transaction, "Value date"));

		history.clickEditHistoryButton().editTransactionByInvestment(transaction, TradeOrderFormPage.class);

		if (type.equals("Deposit")) {
			assertEquals(depositType, this.getSelectedOptionNextToField("Deposit Type"));
		}

		assertEquals(positionType, this.getSelectedOptionNextToField("Position Type"));
		assertEquals(instrument, this.getTextValueNextToField("Instrument Name"));
		assertEquals(currency, this.getSelectedOptionNextToField("Currency"));
		assertEquals(interestRate, this.getTextFromTextboxByField("Interest Rate p.a."));
		assertEquals(startDate, this.getTextFromTextboxByField("Start Date"));
		assertEquals(maturityDate, this.getTextFromTextboxByField("Maturity Date"));
		assertEquals(side, this.getSelectedOptionNextToField("Side"));
		assertEquals(principalAmount, this.getTextFromTextboxByField("Principal Amount"));
		assertEquals(dayCountConvention, this.getTextValueNextToField("Daycount Convention"));

		assertEquals(orderStatus, this.getSelectedOptionNextToField("Order Status"));
		assertEquals(executionDate, this.getTextFromTextboxByField("Execution Date"));
		assertEquals(valueDate, this.getTextFromTextboxByField("Value Date"));
		assertEquals(settlementAdjustmentAmount, this.getTextFromTextboxByField("Settlement Adjustment Amount"));

		transactionReference = getTextByLocator(By.id("gwt-debug-RelatedReferenceWidget-txnRefLabel"));
		HistoryPage history2 = form.clickCancelButton();
		history2.expandTransactionByInvestment(transaction);

		log(transaction);
		assertEquals(transactionReference, this.getTransactionHistoryByField(transaction, "Transaction Reference"));

		history2.clickDeleteTransactionButtonByName(transaction);
	}

	public HistoryPage init() throws InterruptedException, Exception {
		String url = "http://192.168.1.104:8080/SlyAWS/?locale=en&viewMode=MATERIAL#login";
		String account = "Solange Test CS";
		LoginPage main = new LoginPage(webDriver, url);

		HistoryPage history = main.loginAs(Settings.THIRDROCK_ADMIN_USERNAME, Settings.THIRDROCK_ADMIN_PASSWORD)
				.goToAccountOverviewPage().simpleSearchByString(account).goToAccountHoldingsPageByName(account)
				.goToTransactionHistoryPage();

		return history;
	}

	/**
	 * In history page, this returns the transaction history by given field
	 * 
	 * @param transaction
	 * @param field
	 * @return
	 */
	public String getTransactionHistoryByField(String transaction, String field) {
		String info = "";
		WebElement elem;
		try {
			elem = webDriver.findElement(By.xpath("(//*[contains(text(),'" + transaction
					+ "')]//ancestor::div[@id='gwt-debug-TransactionPortfolioOrderView-summaryPanel']//following-sibling::div//td[contains(text(),'"
					+ field + "')]//following-sibling::td//*)[1]"));
			info = getTextByLocator(By.xpath("(//*[contains(text(),'" + transaction
					+ "')]//ancestor::div[@id='gwt-debug-TransactionPortfolioOrderView-summaryPanel']//following-sibling::div//td[contains(text(),'"
					+ field + "')]//following-sibling::td//*)[1]"));
		} catch (NoSuchElementException e) {
			info = getTextByLocator(By.xpath("(//*[contains(text(),'" + transaction
					+ "')]//ancestor::div[@id='gwt-debug-TransactionPortfolioOrderView-summaryPanel']//following-sibling::div//td[div[contains(text(),'"
					+ field + "')]]//following-sibling::td//*)[1]"));
		}
		log(info);
		return info;
	}

	/**
	 * In TradeOrderForm page, this returns get text value next to the given
	 * field
	 * 
	 * @param field
	 * @return
	 */
	public String getTextValueNextToField(String field) {
		String text = "";
		int size = 1;
		try {

			size = getSizeOfElements(By.xpath("(.//td[contains(text(),'" + field + "')]//following-sibling::td//div)"));

			waitForElementPresent(By.xpath("(.//td[contains(text(),'" + field + "')]//following-sibling::td//div)["
					+ String.valueOf(size) + "]"), 10);

			text = getTextByLocator(By.xpath("(.//td[contains(text(),'" + field + "')]//following-sibling::td//div)["
					+ String.valueOf(size) + "]"));
		} catch (TimeoutException e) {

			size = getSizeOfElements(
					By.xpath(".//td[*[contains(text(),'" + field + "')]]//following-sibling::td//div"));

			text = getTextByLocator(By.xpath("(.//td[*[contains(text(),'" + field + "')]]//following-sibling::td//div)["
					+ String.valueOf(size) + "]"));
		}

		return text;
	}

	/**
	 * In TradeOrderForm page, this returns the first selected option next to
	 * the given field
	 * 
	 * @param field
	 * @return
	 */
	public String getSelectedOptionNextToField(String field) {

		String text = getSelectedTextFromDropDown(
				By.xpath(".//td[contains(text(),'" + field + "')]//following-sibling::td//select"));
		return text;
	}

	/**
	 * In TradeOrderForm page, this returns the text value of textbox next to
	 * the given field
	 * 
	 * @param field
	 * @return
	 */
	public String getTextFromTextboxByField(String field) {
		String text = "";

		try {
			waitForElementVisible(By.xpath(".//td[.='" + field + "']//following-sibling::td//input"), 10);
			text = getTextByLocator(By.xpath(".//td[.='" + field + "']//following-sibling::td//input"));
		} catch (TimeoutException e) {
			try {
				waitForElementVisible(By.xpath(".//td[contains(text(),'" + field + "')]//following-sibling::td//input"),
						10);

				text = getTextByLocator(
						By.xpath(".//td[contains(text(),'" + field + "')]//following-sibling::td//input"));
			} catch (TimeoutException ex) {
				text = getTextByLocator(
						By.xpath(".//td[*[contains(text(),'" + field + "')]]//following-sibling::td//input"));
			}
		}

		return text;
	}

	/**
	 * This is to assert the transaction information for security in
	 * TradeOrderForm page.
	 * 
	 * @param accountNumber
	 * @param instrument
	 * @param exchange
	 * @param currency
	 * @param isin
	 * @param side
	 * @param quantity
	 * @param amount
	 * @param orderType
	 * @param status
	 * @param filledQuantity
	 * @param filledAmount
	 * @param executionPrice
	 * @param commission
	 * @param fee
	 * @param settlementAdjustmentAmount
	 * @param companyCommissionRate
	 */
	public void assertFieldsForSecurityInTradeOrderForm(String accountNumber, String instrument, String exchange,
			String currency, String isin, String side, String quantity, String amount, String orderType, String status,
			String filledQuantity, String filledAmount, String executionPrice, String commission, String fee,
			String settlementAdjustmentAmount, String companyCommissionRate) {

		if (instrument != "") {
			assertEquals(instrument, this.getTextValueNextToField("Instrument Name"));
		}
		// if (exchange != "") {
		// assertEquals(exchange, this.getTextValueNextToField("Exchange"));
		// }
		if (currency != "") {
			assertEquals(currency, this.getTextValueNextToField("Currency"));
		}
		if (isin != "") {
			assertEquals(isin, this.getTextValueNextToField("ISIN"));
		}
		if (side != "") {
			assertEquals(side, this.getSelectedOptionNextToField("Side"));
		}
		if (quantity != "") {
			assertEquals(quantity, this.getTextFromTextboxByField("Quantity"));
		}
		if (amount != "") {
			assertEquals(amount, this.getTextFromTextboxByField("Amount"));
		}
		if (orderType != "") {
			assertEquals(orderType, this.getSelectedOptionNextToField("Order Type"));
		}

		if (status != "") {
			assertEquals(status, this.getSelectedOptionNextToField("Order Status"));
		}
		if (filledQuantity != "") {
			assertEquals(filledQuantity, this.getTextFromTextboxByField("Filled Quantity"));
		}
		if (filledAmount != "") {
			assertEquals(filledAmount, this.getTextFromTextboxByField("Filled Amount"));
		}
		if (executionPrice != "") {
			assertEquals(executionPrice, this.getTextFromTextboxByField("Execution Price"));
		}
		if (commission != "") {
			assertEquals(String.valueOf(commission), this.getTextFromTextboxByField("Commission Amount"));
		}
		if (fee != "") {
			assertEquals(String.valueOf(fee), this.getTextFromTextboxByField("Fees"));
		}
		if (settlementAdjustmentAmount != "") {
			assertEquals(settlementAdjustmentAmount, this.getTextFromTextboxByField("Settlement Adjustment Amount"));
		}
		if (companyCommissionRate != "") {
			assertTrue(companyCommissionRate.contains(this.getTextFromTextboxByField("Commission Rate").split("%")[0]));
		}

	}

	/**
	 * This is to assert the transaction information for security in History
	 * page.
	 * 
	 * @param transaction
	 * @param instrument
	 * @param isin
	 * @param currency
	 * @param exchange
	 * @param side
	 * @param executionPrice
	 * @param settlementDate
	 * @param totalCommission
	 * @param settlementAmount
	 * @param transactionReference
	 */
	public void assertFieldsForSecurityInTransactionHistory(String transaction, String instrument, String isin,
			String currency, String exchange, String side, String executionPrice, String settlementDate,
			String totalCommission, String settlementAmount, String transactionReference) {

		if (transaction != "") {
			assertEquals(instrument, this.getTransactionHistoryByField(transaction, "Instrument"));
		}

		if (isin != "") {
			assertEquals(isin, this.getTransactionHistoryByField(transaction, "ISIN"));
		}
		if (currency != "") {
			assertEquals(currency, this.getTransactionHistoryByField(transaction, "Currency"));
		}

		if (side != "") {
			assertEquals(side, this.getTransactionHistoryByField(transaction, "Side"));
		}
		if (executionPrice != "") {
			assertTrue(executionPrice.contains(this.getTransactionHistoryByField(transaction, "Execution Price")));
		}
		if (settlementDate != "") {
			assertEquals(settlementDate, this.getTransactionHistoryByField(transaction, "Settlement Date"));
		}
		if (totalCommission != "") {
			assertEquals(totalCommission, this.getTransactionHistoryByField(transaction, "Total Commission and Fees"));
		}
		// For settlement amount, we only have to check it is not empty
		if (settlementAmount != "") {
			assertFalse(this.getTransactionHistoryByField(transaction, "Settlement Amount").isEmpty());
		}
		if (transactionReference != "") {
			assertEquals(transactionReference, this.getTransactionHistoryByField(transaction, "Transaction Reference"));
		}

	}

	/**
	 * This is for Other transaction. This returns whether the given impact is
	 * in the window.
	 * 
	 * @param instrument
	 * @param quantity
	 * @param status
	 * @return
	 */
	public boolean assertImpactIsVisible(String transaction, String instrument, String quantity, String status) {

		return isElementVisible(By.xpath("(.//td[*[contains(text(),'" + transaction
				+ "')]]//ancestor::div[@id='gwt-debug-TransactionPortfolioOrderView-summaryPanel']//following-sibling::div[contains(@id,'detailsPanelA')])[1]//div[contains(text(),'Impacts')]//following-sibling::div//tr//td[contains(text(),'"
				+ instrument + "')]//following-sibling::td[div[.='" + quantity
				+ "']]//following-sibling::td[contains(text(),'" + status + "')]"));
	}
}
