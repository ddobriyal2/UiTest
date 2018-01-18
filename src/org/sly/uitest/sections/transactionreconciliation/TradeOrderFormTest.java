package org.sly.uitest.sections.transactionreconciliation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.framework.ThirdRock;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.clientsandaccounts.CashflowPage;
import org.sly.uitest.pageobjects.clientsandaccounts.HistoryPage;
import org.sly.uitest.pageobjects.clientsandaccounts.InstrumentDistributionPage;
import org.sly.uitest.pageobjects.clientsandaccounts.TradeOrderFormPage;
import org.sly.uitest.settings.Settings;

public class TradeOrderFormTest extends AbstractTest {

	ArrayList<String> filledOrderStatus = new ArrayList<String>() {
		{
			add("Filled fully");
			add("Filled partially");
			add("Settled");
		}
	};

	ArrayList<String> unfilledOrderStatus = new ArrayList<String>() {
		{
			add("Cancelled");
			add("Error");
			add("Lapsed");
			add("Pending");
			add("Submitted");
			add("Transmitted");
		}
	};
	ArrayList<String> currencyCategory1 = new ArrayList<String>() {
		{
			add("SGD");
			add("HKD");
			add("GBP");
			add("RMB");
			add("MYR");
		}
	};
	ArrayList<String> currencyCategory2 = new ArrayList<String>() {
		{
			add("EUR");
			add("USD");
			add("JPY");
			add("AUD");
			add("CAD");
			add("CHF");
			add("NZD");
			add("PHP");
		}
	};

	@Ignore
	@Category(ThirdRock.class)
	public void testTradeOrderFormSecurity() throws InterruptedException, Exception {
		HistoryPage history = this.init();
		String security = "Tesla Motors Inc";
		String exchange = "NYSE ARCA (ARCA)";
		String currency = "USD";
		String isin = "US88160R1014";
		TradeOrderFormPage form = history.editHistorySource("System").clickEditHistoryButton()
				.addHistoricalTransaction("Order", TradeOrderFormPage.class);

		// make sure the field for security is there.
		form.selectInstrumentAndProceed("Stock/ETF", "").addInstrument(security);
		assertTrue(isElementVisible(
				By.xpath(".//td[contains(text(),'Instrument Name')]//following-sibling::td[*[contains(text(),'"
						+ security + "')]]")));
		assertTrue(isElementVisible(By.xpath(
				".//td[contains(text(),'Exchange')]//following-sibling::td[*[contains(text(),'" + exchange + "')]]")));
		assertTrue(isElementVisible(By.xpath(
				".//td[contains(text(),'Currency')]//following-sibling::td[*[contains(text(),'" + currency + "')]]")));
		assertTrue(isElementVisible(
				By.xpath(".//td[contains(text(),'ISIN')]//following-sibling::td[*[contains(text(),'" + isin + "')]]")));

		// Check Timestamp and timezone
		this.checkTimestampAndTimezone();

		// Check Time In Force field
		this.checkTimeInForce(security, "06", "AIA Group Limited", "16");

		// Check Order Type field
		this.checkOrderType();

		// Check the relationship between status and compulsory field
		this.checkOrderStatusAndCompulsoryField();

		// Check Order status and Filled Quantity
		this.checkOrderStatusAndFilledQuantity(security);

		// Check the currency logic
		this.checkCurrencyLogic(security, currency, "Power Assets Holdings Ltd", "HKD");

		// Check the calculation and formula
		this.checkCalculation(security);

	}

	@Ignore
	@Category(ThirdRock.class)
	public void testTradeOrderFormFund() throws InterruptedException, Exception {
		HistoryPage history = this.init();
		String security = "Aberdeen Global - Asia Pacific Equity Fund A2";
		String exchange = "MutualFunds (MutualFunds)";
		String currency = "USD";
		String isin = "LU0011963245";
		TradeOrderFormPage form = history.editHistorySource("System").clickEditHistoryButton()
				.addHistoricalTransaction("Order", TradeOrderFormPage.class);

		// make sure the field for fund is there.
		form.selectInstrumentAndProceed("Stock/ETF", "").addInstrument(security);
		assertTrue(isElementVisible(
				By.xpath(".//td[contains(text(),'Instrument Name')]//following-sibling::td[*[contains(text(),'"
						+ security + "')]]")));
		assertTrue(isElementPresent(By.xpath(
				".//td[contains(text(),'Exchange')]//following-sibling::td//*[contains(text(),'" + exchange + "')]")));
		assertTrue(isElementVisible(By.xpath(
				".//td[contains(text(),'Currency')]//following-sibling::td[*[contains(text(),'" + currency + "')]]")));
		assertTrue(isElementVisible(
				By.xpath(".//td[contains(text(),'ISIN')]//following-sibling::td[*[contains(text(),'" + isin + "')]]")));

		// Check Timestamp and timezone
		this.checkTimestampAndTimezone();

		// Check Time In Force field
		this.checkTimeInForce(security, "08", "AIA MPF - Basic Value Choice American Fund", "00");

		// Check Order Type field
		this.checkOrderType();

		// Check the relationship between status and compulsory field
		this.checkOrderStatusAndCompulsoryField();

		// Check Order status and Filled Quantity
		this.checkOrderStatusAndFilledQuantity(security);

		// Check the currency logic
		this.checkCurrencyLogic(security, currency, "AIA MPF - Basic Value Choice American Fund", "HKD");

		// Check the calculation and formula
		this.checkCalculation(security);
	}

	@Ignore
	@Category(ThirdRock.class)
	public void testTradeOrderFormBond() throws InterruptedException, Exception {
		HistoryPage history = this.init();
		String security = "180 Plus Note";
		String exchange = "OTC Bulletin Board (OTCBB)";
		String currency = "USD";
		String isin = "XS0220209604";
		TradeOrderFormPage form = history.editHistorySource("System").clickEditHistoryButton()
				.addHistoricalTransaction("Order", TradeOrderFormPage.class);

		// make sure the field for bond is there.
		form.selectInstrumentAndProceed("Bond", "").addInstrument(security);
		assertTrue(isElementVisible(
				By.xpath(".//td[contains(text(),'Instrument Name')]//following-sibling::td[*[contains(text(),'"
						+ security + "')]]")));
		assertTrue(isElementVisible(By.xpath(
				".//td[contains(text(),'Exchange')]//following-sibling::td[*[contains(text(),'" + exchange + "')]]")));
		assertTrue(isElementVisible(By.xpath(
				".//td[contains(text(),'Currency')]//following-sibling::td[*[contains(text(),'" + currency + "')]]")));
		assertTrue(isElementVisible(
				By.xpath(".//td[contains(text(),'ISIN')]//following-sibling::td[*[contains(text(),'" + isin + "')]]")));

		// Check Timestamp and timezone
		this.checkTimestampAndTimezone();

		// Check Time In Force field
		this.checkTimeInForce(security, "04", "GLOBAL BRANDS GROU HKD0.0125 HKD", "16");

		// Check Order Type field
		this.checkOrderType();

		// Check the relationship between status and compulsory field
		this.checkOrderStatusAndCompulsoryField();

		// Check Order status and Filled Quantity
		this.checkOrderStatusAndFilledQuantity(security);

		// Check the currency logic
		this.checkCurrencyLogic(security, currency, "RABOBANK NEDERLAND 5% AUD", "AUD");

		// Check the calculation and formula
		this.checkCalculation(security);
	}

	@Ignore
	@Category(ThirdRock.class)
	public void testTradeOrderFormFX() throws InterruptedException, Exception {
		String buyCurrency = "USD";
		String sellCurrency = "EUR";
		HistoryPage history = this.init();
		TradeOrderFormPage form = history.editHistorySource("System").clickEditHistoryButton()
				.addHistoricalTransaction("Order", TradeOrderFormPage.class);

		form.selectInstrumentAndProceed("FX", "");

		// Check Timestamp and timezone
		this.checkTimestampAndTimezone();
		//
		// // Check Time In Force field
		this.checkTimeInForce("", "", "", "");

		// // Check Order Type field
		form.editInstrumentCurrencyAndCounterCurrency(buyCurrency, sellCurrency);
		this.checkOrderType();
		form.editInstrumentCurrencyAndCounterCurrency("", "");
		//
		// // Check the relationship between status and compulsory field
		form.editInstrumentCurrencyAndCounterCurrency(buyCurrency, sellCurrency);
		this.checkOrderStatusAndCompulsoryField();
		form.editInstrumentCurrencyAndCounterCurrency("", "");

		this.checkCurrencyLogicFX(buyCurrency, sellCurrency);

		this.checkDecimalPlaceFX(buyCurrency, sellCurrency);

		this.checkCalculationFX(buyCurrency, sellCurrency);

	}

	@Ignore
	@Category(ThirdRock.class)
	public void testTradeOrderFormDeposit() throws InterruptedException, Exception {

		String type = "Deposit";
		HistoryPage history = this.init();
		TradeOrderFormPage form = history.editHistorySource("System").clickEditHistoryButton()
				.addHistoricalTransaction(type, TradeOrderFormPage.class);

		// Check calculation of interest
		this.checkCalculationForLoanAndDeposit(type);

		// Check order status and compulsory fields in the window
		this.checkOrderStatusAndCompulsoryFieldForDepositAndLoan();

		// Check Deposit Type and Maturity Date
		this.checkDepositTypeAndMaturityDate();

		// Check Timestamp and timezone
		this.checkTimestampAndTimezone();

		// Check Postion type and Position/Side
		this.checkPositionTypeAndPositionAndSide();

		// Check currency display and their daycount convention
		this.checkCurrencyAndCurrencyDisplayAndDayCount();

	}

	@Ignore
	@Category(ThirdRock.class)
	public void testTradeOrderFormLoan() throws InterruptedException, Exception {

		String type = "Loan";
		HistoryPage history = this.init();
		TradeOrderFormPage form = history.editHistorySource("System").clickEditHistoryButton()
				.addHistoricalTransaction(type, TradeOrderFormPage.class);

		// Check calculation of interest
		this.checkCalculationForLoanAndDeposit(type);

		// Check order status and compulsory fields in the window
		this.checkOrderStatusAndCompulsoryFieldForDepositAndLoan();

		// Check Timestamp and timezone
		this.checkTimestampAndTimezone();

		// Check Postion type and Position/Side
		this.checkPositionTypeAndPositionAndSide();

		// Check currency display and their daycount convention
		this.checkCurrencyAndCurrencyDisplayAndDayCount();

	}

	@Ignore
	@Category(ThirdRock.class)
	public void testTraeOrderFormEquityOption() throws InterruptedException, Exception {

		String type = "Order";
		String symbol = "TSLA";
		String instrument = "Tesla";
		String isin = "US88160R1014";

		HistoryPage history = this.init();
		TradeOrderFormPage form = history.editHistorySource("System").clickEditHistoryButton()
				.addHistoricalTransaction(type, TradeOrderFormPage.class);

		// assert field in equity option window
		form.selectInstrumentAndProceed("Option", "Equity Option").clickAddUnderlyingButton().addTicker("NYSE", symbol);
		assertTrue(pageContainsStr(instrument));
		assertEquals(isin, this.getTextValueNextToField("Underlying ISIN"));

		// Check Timestamp and timezone
		// this.checkTimestampAndTimezone();
		//
		// // Check Time In Force field
		// this.checkTimeInForce(symbol, "", "", "");
		//
		// // Check Order Type field
		// this.checkOrderType();

		// Check the relationship between status and compulsory field
		this.checkOrderStatusAndCompulsoryField();

		// Check the currency logic
		// this.checkCurrencyLogic(symbol, "USD", "0006", "HKD");

		// Check the calculation and formula
		this.checkCalculation(symbol);
	}

	@Ignore
	@Category(ThirdRock.class)
	public void testTradeOrderFormFXOption() throws InterruptedException, Exception {
		HistoryPage history = this.init();
		TradeOrderFormPage form = history.editHistorySource("System").clickEditHistoryButton()
				.addHistoricalTransaction("Order", TradeOrderFormPage.class);

		form.selectInstrumentAndProceed("Option", "FX Option");

		// Check Timestamp and timezone
		this.checkTimestampAndTimezone();

		// Check Order Type field
		this.checkOrderType();

		// Check the relationship between status and compulsory field
		this.checkOrderStatusAndCompulsoryField();

		// this.checkCalculation("");
	}

	// for SLYAWS-9589
	// @Ignore
	// @Category(ThirdRock.class)
	// public void testSettlementDateCalculation() throws InterruptedException,
	// Exception {
	//
	// String exchange = "Singapore Exchange (XSES)";
	// String today = this.getCurrentTimeInFormat("dd-MMM-yyyy");
	// HistoryPage history = this.init();
	//
	// TradeOrderFormPage form = history.editHistorySource("System")
	// .clickEditHistoryButton()
	// .addHistoricalTransaction("Order", TradeOrderFormPage.class);
	//
	// form.selectInstrumentAndProceed("Stock/ETF", "");
	//
	// waitForElementVisible(
	// By.id("gwt-debug-MyOrderEditPopupView-dialogBox"), 30);
	//
	// form.addInstrument("Singapore Telecommunications Limited")
	// .clickFindSettlementDateButton()
	// .editExecutionDateInSettlementDateWindow(today)
	// .editExchangeForSettlementDate(exchange);
	//
	// Calendar myCal = Calendar.getInstance();
	// String weekday = myCal.getDisplayName(Calendar.DAY_OF_WEEK,
	// Calendar.SHORT, Locale.US);
	//
	// log(weekday);
	//
	// // Test the logic that, for Singapore exchange, settlement date is T + 3
	// switch (weekday) {
	// case "Wed":
	// assertEquals(
	// getTextByLocator(By
	// .id("gwt-debug-SettlementDatePopupView-settlementDateLabel")),
	// this.getDateAfterDays(today, 5, "dd-MMM-yyyy"));
	// break;
	// case "Thu":
	// assertEquals(
	// getTextByLocator(By
	// .id("gwt-debug-SettlementDatePopupView-settlementDateLabel")),
	// this.getDateAfterDays(today, 5, "dd-MMM-yyyy"));
	// break;
	// case "Fri":
	// assertEquals(
	// getTextByLocator(By
	// .id("gwt-debug-SettlementDatePopupView-settlementDateLabel")),
	// this.getDateAfterDays(today, 5, "dd-MMM-yyyy"));
	// break;
	// case "Sat":
	// assertEquals(
	// getTextByLocator(By
	// .id("gwt-debug-SettlementDatePopupView-settlementDateLabel")),
	// this.getDateAfterDays(today, 5, "dd-MMM-yyyy"));
	// break;
	// case "Sun":
	// assertEquals(
	// getTextByLocator(By
	// .id("gwt-debug-SettlementDatePopupView-settlementDateLabel")),
	// this.getDateAfterDays(today, 4, "dd-MMM-yyyy"));
	// break;
	// default:
	// assertEquals(
	// getTextByLocator(By
	// .id("gwt-debug-SettlementDatePopupView-settlementDateLabel")),
	// this.getDateAfterDays(today, 3, "dd-MMM-yyyy"));
	// break;
	// }
	//
	// }

	@Ignore
	@Category(ThirdRock.class)
	public void testCashflowAndLoan() throws InterruptedException, Exception {
		this.testCashflowAndInterest("Loan");
	}

	@Ignore
	@Category(ThirdRock.class)
	public void testCashflowAndDeposit() throws InterruptedException, Exception {
		this.testCashflowAndInterest("Deposit");
	}

	public void testCashflowAndInterest(String type) throws InterruptedException, Exception {

		String depositType = "Time Deposit";
		String positionType = "Open";
		String currency = "USD";
		String interestRate = "36.5";
		String startDate = "01-Dec-2016";
		String maturityDate = "31-Dec-2017";
		String currency2 = "";
		String interestRate2 = "";
		String startDate2 = "";
		String maturityDate2 = "";
		String principalAmount = "10000";
		String orderStatus = "Settled";
		String valueDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String executionDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String settlementAdjustmentAmount = "10000";
		String instrument = type + " " + currency + " 36.50% MAT " + maturityDate;
		String instrument2 = "";
		String relatedReference = "";
		String impact = "";
		String interestType = "";
		String cashflowAmount = String.format("%." + 2 + "f", Math.random() * 999 + 1);
		String description = "";

		HistoryPage history = this.init();

		// first step is to create transaction for cashflow
		history.editHistorySource("System").editShowTransactionsHistoryFor("Last 30 days")
				.editTransactionType("Cashflow").clickEditHistoryButton();

		int size = getSizeOfElements(By.id("gwt-debug-TransactionDetailsView-deleteTransactionImg"));

		// delete all cashflow transaction
		for (int i = size; i > 0; i--) {
			clickElement(By.id("gwt-debug-TransactionDetailsView-deleteTransactionImg'])[" + String.valueOf(i) + "]"));
			clickYesButtonIfVisible();
			clickOkButtonIfVisible();
		}

		history.clickExitEditModeButton().editTransactionType(type).clickEditHistoryButton();

		size = getSizeOfElements(By.id("gwt-debug-TransactionDetailsView-deleteTransactionImg"));

		for (int i = size; i > 0; i--) {
			try {
				clickElement(By.xpath(".//*[@id='gwt-debug-TransactionDetailsView-deleteTransactionImg']"));
				clickYesButtonIfVisible();
				clickOkButtonIfVisible();
			} catch (TimeoutException e) {

			}
		}

		// create first transaction
		TradeOrderFormPage form = history.addHistoricalTransaction(type, TradeOrderFormPage.class);

		if (type.equals("Deposit")) {
			form.editDepositType(depositType);
			instrument = depositType + " " + currency + " 36.50% MAT " + maturityDate;

		}

		HistoryPage history2 = form.editPositionType(positionType).editInstrumentCurrency(currency)
				.editInterestRate(interestRate).editStartDate(startDate).editMaturityDate(maturityDate)
				.editQuantity(principalAmount).editOrderStatus(orderStatus).editSettlementDate(valueDate)
				.editSettlementAdjustmentAmount(settlementAdjustmentAmount).clickCalculateInterestAmountButton()
				.editExecutionDate(executionDate).clickSaveButtonForTrade();

		// create one more transaction for checking logic in cashflow window
		history2.addHistoricalTransaction(type, TradeOrderFormPage.class);
		interestRate2 = String.format("%." + 2 + "f", Math.random() * 50 + 1);
		startDate2 = "01-Nov-2016";
		maturityDate2 = "30-Nov-2017";
		currency2 = "EUR";
		depositType = "Call Deposit";
		instrument2 = type + " " + currency2 + " " + interestRate2 + "% MAT " + maturityDate2;

		if (type.equals("Deposit")) {
			form.editDepositType(depositType);
			instrument2 = depositType + " " + currency2 + " " + interestRate2 + "% MAT " + maturityDate2;
			if (depositType.equals("Call Deposit")) {
				instrument2 = depositType + " " + currency2 + " " + interestRate2 + "%";
			}
		}

		log("instrument2 :" + instrument2);
		form.editPositionType(positionType).editInstrumentCurrency(currency2).editInterestRate(interestRate2)
				.editStartDate(startDate2);

		if (type.equals("Loan")) {
			form.editMaturityDate(maturityDate2);
		}
		form.editQuantity(principalAmount).editOrderStatus(orderStatus).editSettlementDate(valueDate)
				.editSettlementAdjustmentAmount(settlementAdjustmentAmount).clickCalculateInterestAmountButton()
				.editExecutionDate(executionDate).clickSaveButtonForTrade();

		// create a new cashflow transaction for the above transaction
		CashflowPage cashflow = history.addHistoricalTransaction("Cashflow", CashflowPage.class);

		// before picking any position, the interest cal button must not appear
		waitForElementPresent(By.xpath(".//*[contains(@debugid,'interestPeriodRow') and @style='display: none;']"), 10);
		assertFalse(isElementVisible(By.xpath(".//*[contains(@debugid,'interestPeriodRow')]")));

		if (type.equals("Deposit")) {
			cashflow.editCashflowType("Interest");
			interestType = "Interest";
			impact = "Inflow";
			assertEquals(impact, getTextByLocator(By.xpath(".//*[contains(@id,'cashFlowImpact')]")));
		} else if (type.equals("Loan")) {
			interestType = "Interest Due";
			impact = "Outflow";
			cashflow.editCashflowType("Interest Due");
			assertEquals(impact, getTextByLocator(By.xpath(".//*[contains(@id,'cashFlowImpact')]")));
		}

		cashflow.editInstrumentType(type).editPositions(instrument);

		// Related reference must not be null.
		relatedReference = getTextByLocator(By.xpath(
				".//*[contains(@id,'gwt-debug-RelatedReferenceWidget-relatedRefPanel')]//*[@class='gwt-Label']"));
		assertFalse(relatedReference.isEmpty());

		// assert details of txn
		// assertEquals("Credit Suisse",
		// this.getCashflowInstrumentDetailByField("Issuer"));

		assertTrue(this.getCashflowInstrumentDetailByField("Interest Rate").contains(interestRate));

		assertEquals("None", this.getCashflowInstrumentDetailByField("Interest Frequency"));

		assertEquals(startDate, this.getCashflowInstrumentDetailByField("Start Date"));

		assertEquals(maturityDate, this.getCashflowInstrumentDetailByField("Maturity Date"));

		assertEquals(currency, getSelectedTextFromDropDown(By.xpath(".//select[contains(@id,'currency')]")));

		// check logic of changing position
		cashflow.editPositions(instrument2);
		// assert details of another txn
		// assertEquals("Credit Suisse",
		// this.getCashflowInstrumentDetailByField("Issuer"));
		try {
			assertTrue(this.getCashflowInstrumentDetailByField("Interest Rate").contains(interestRate2));
		} catch (AssertionError e) {
			assertTrue(this.getCashflowInstrumentDetailByField("Interest Rate").contains(interestRate2.concat("0")));
		}

		assertEquals("None", this.getCashflowInstrumentDetailByField("Interest Frequency"));

		assertEquals(startDate2, this.getCashflowInstrumentDetailByField("Start Date"));

		if (!type.equals("Deposit")) {
			assertEquals(maturityDate2, this.getCashflowInstrumentDetailByField("Maturity Date"));

		}

		assertEquals(currency2, getSelectedTextFromDropDown(By.xpath(".//select[contains(@id,'currency')]")));

		// comparison between new and old related reference
		assertFalse(relatedReference.equals(getTextByLocator(
				By.xpath(".//*[@id='gwt-debug-RelatedReferenceWidget-relatedRefPanel']//*[@class='gwt-Label']"))));

		// check mandatory fields
		cashflow.editCashflowCurrency("").editTransactionDate("").editCashflowValueDate("")
				.editCashflowStatus("Pending").clickElement(By.xpath(".//*[contains(@id,'saveButton')]"));

		assertFalse(pageContainsStr("Value Date is a mandatory field"));
		assertFalse(pageContainsStr("Transaction Date is a mandatory field"));
		clickOkButtonIfVisible();

		cashflow.editCashflowStatus("Completed").clickElement(By.xpath(".//*[contains(@id,'saveButton')]"));

		assertTrue(pageContainsStr("Amount is a mandatory field"));
		assertTrue(pageContainsStr("Transaction Date is a mandatory field"));
		clickOkButtonIfVisible();
		description = impact + " of " + currency2 + " " + cashflowAmount + " (" + interestType + ")";
		cashflow.editCashflowCurrency(currency2).editTransactionDate(executionDate).editValueDate(executionDate)
				.editCashflowAmount(cashflowAmount).clickSaveButtonForTrade().clickExitEditModeButton()
				.editTransactionType(type).clickEditHistoryButton().clickElement(By.xpath("(.//td[*[contains(text(),'"
						+ instrument2 + "')]]/following-sibling::td//*[contains(@id,'deleteTransactionImg')])[1]"));

		assertTrue(pageContainsStr("This transaction cannot be deleted because it is linked to transaction"));
		clickOkButtonIfVisible();

		history.clickExitEditModeButton().editTransactionType("Cashflow").clickEditHistoryButton()
				.clickElement(By.xpath("(.//td[*[contains(text(),'" + description
						+ "')]]/following-sibling::td//*[contains(@id,'deleteTransactionImg')])[1]"));

		assertFalse(pageContainsStr(
				"This transaction cannot be deleted because it is linked to transactions with transaction reference"));
		clickOkButtonIfVisible();

		CashflowPage cashflow2 = history.clickEditTransactionButtonByName(description, CashflowPage.class);

		// assertEquals("Credit Suisse",
		// this.getCashflowInstrumentDetailByField("Issuer"));

		try {
			assertTrue(this.getCashflowInstrumentDetailByField("Interest Rate").contains(interestRate2));
		} catch (AssertionError e) {
			assertTrue(this.getCashflowInstrumentDetailByField("Interest Rate").contains(interestRate2.concat("0")));
		}

		assertEquals("None", this.getCashflowInstrumentDetailByField("Interest Frequency"));

		assertEquals(startDate2, this.getCashflowInstrumentDetailByField("Start Date"));

		if (!type.equals("Deposit")) {
			assertEquals(maturityDate2, this.getCashflowInstrumentDetailByField("Maturity Date"));

		}
		assertEquals(currency2, getSelectedTextFromDropDown(By.xpath(".//select[contains(@id,'currency')]")));

		size = getSizeOfElements(By.xpath(".//button[contains(@id,'RelatedReferenceRowWidget-deleteButton')]"));

		for (int i = 0; i < size; i++) {
			clickElement(By.xpath(".//button[contains(@id,'RelatedReferenceRowWidget-deleteButton')]"));
		}
		cashflow2.clickSaveButtonForTrade().clickExitEditModeButton().editTransactionType(type).clickEditHistoryButton()
				.clickEditTransactionButtonByName(instrument2, TradeOrderFormPage.class);
		size = getSizeOfElements(By.xpath(".//button[contains(@id,'RelatedReferenceRowWidget-deleteButton')]"));

		for (int i = 0; i < size; i++) {
			clickElement(By.xpath(".//button[contains(@id,'RelatedReferenceRowWidget-deleteButton')]"));
		}
		HistoryPage history3 = form.clickSaveButtonForTrade();
		history3.clickDeleteTransactionButtonByName(instrument).clickDeleteTransactionButtonByName(instrument2)
				.editTransactionType("Cashflow").clickEditHistoryButton()
				.clickDeleteTransactionButtonByName(description);
	}

	@Ignore
	@Category(ThirdRock.class)
	public void testInstrumentDistributionWindow() throws InterruptedException, Exception {
		String type = "Instrument Distribution";
		String type_cash = "Cash";
		String type_stock = "Shares";
		String executionDate = "";
		String settlementDate = "";

		ArrayList<String> completedStatus = new ArrayList<String>() {
			{
				add("Completed");
				add("Settled");
			}
		};
		ArrayList<String> pendingStatus = new ArrayList<String>() {
			{
				add("Cancelled");
				add("Error");
				add("Open");
			}
		};
		HistoryPage history = this.init();

		InstrumentDistributionPage instrument = history.editHistorySource("System").clickEditHistoryButton()
				.addHistoricalTransaction(type, InstrumentDistributionPage.class);

		// Check logic between Distribution Type and other fields

		// when type = cash, amount row and calculate withholding tax appear
		instrument.editType(type_cash);

		assertTrue(isElementVisible(By.xpath(".//*[contains(@debugid,'TransactionInstDistEditPopupView-amountRow')]")));
		assertFalse(isElementVisible(By.xpath(".//*[contains(@debugid,'TransactionInstDistEditPopupView-unitsRow')]")));
		assertTrue(isElementVisible(By.xpath(".//*[contains(@id,'calculateTaxButton')]")));

		// when type = stock, quantity row appear and calculate withholding tax
		// not appear
		instrument.editType(type_stock);

		assertFalse(
				isElementVisible(By.xpath(".//*[contains(@debugid,'TransactionInstDistEditPopupView-amountRow')]")));
		assertTrue(isElementVisible(By.xpath(".//*[contains(@debugid,'TransactionInstDistEditPopupView-unitsRow')]")));

		instrument.editType("");

		// check logic between status and compulsory fields
		for (String status : completedStatus) {
			instrument.editStatus(status).clickSaveButtonForCheckingFields();
			assertTrue(pageContainsStr("Invalid Execution Date"));
			assertTrue(pageContainsStr("Invalid Settlement Date"));
			clickOkButtonIfVisible();
			assertTrue(this.isFieldMarkedCompulsory("Execution Date"));
			assertTrue(this.isFieldMarkedCompulsory("Value Date"));
		}

		for (String status : pendingStatus) {
			instrument.editStatus(status).clickSaveButtonForCheckingFields();
			assertFalse(pageContainsStr("Invalid Execution Date"));
			assertFalse(pageContainsStr("Invalid Value Date"));
			clickOkButtonIfVisible();
			assertFalse(this.isFieldMarkedCompulsory("Execution Date"));
			assertFalse(this.isFieldMarkedCompulsory("Settlement Date"));
		}

		instrument.editStatus("");

		// check logic that execution date has to be earlier than settlement
		// date
		executionDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		settlementDate = this.getDateAfterDays(executionDate, 5, "dd-MMM-yyyy");

		instrument.editExecutionDate(executionDate).editSettlementDate(settlementDate)
				.clickSaveButtonForCheckingFields();
		assertFalse(pageContainsStr("Execution date cannot be after settlement date"));
		clickOkButtonIfVisible();

		settlementDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		executionDate = this.getDateAfterDays(settlementDate, 5, "dd-MMM-yyyy");
		instrument.editExecutionDate(executionDate).editSettlementDate(settlementDate)
				.clickSaveButtonForCheckingFields();
		assertTrue(pageContainsStr("Execution date cannot be after settlement date"));
		clickOkButtonIfVisible();
	}

	public void checkDisabledField(By by) {
		waitForElementVisible(by, 10);
		assertEquals(this.getAttributeStringByLocator(by, "disabled"), "true");
	}

	public void checkCurrencyAndDayCountAndInterest() throws InterruptedException {

		ArrayList<String> currencyList = new ArrayList<String>() {
			{
				add("SGD");
				add("HKD");
				add("GBP");
				add("RMB");
				add("MYR");
				add("EUR");
				add("USD");
				add("JPY");
				add("AUD");
				add("CAD");
				add("CHF");
				add("NZD");
				add("PHP");
				add("CLP");
				add("EGL");
			}
		};

		for (String currency : currencyList) {
			checkDaycountLogic(currency);
		}
	}

	public void checkDaycountLogic(String currency) throws InterruptedException {
		String act365 = "ACT/365";
		String act360 = "ACT/360";
		String act365Interest = "39,300.00";
		String act360Interest = "39,845.83";
		ArrayList<String> category1 = new ArrayList<String>() {
			{
				add("SGD");
				add("HKD");
				add("GBP");
				add("RMB");
				add("MYR");
			}
		};
		ArrayList<String> category2 = new ArrayList<String>() {
			{
				add("EUR");
				add("USD");
				add("JPY");
				add("AUD");
				add("CAD");
				add("CHF");
				add("NZD");
				add("PHP");
			}
		};

		TradeOrderFormPage form = new TradeOrderFormPage(webDriver, HistoryPage.class);

		form.editInstrumentCurrency(currency).editPositionType("Close").editStartDate("1-Dec-2016")
				.editMaturityDate("29-Dec-2017").editSettlementDate("19-Dec-2016").editInterestRate("36.5")
				.editQuantity("100000");

		wait(3);
		form.clickCalculateInterestAmountButton();
		String dayCount = getTextByLocator(By.xpath(".//*[contains(@id,'dayCountConventionLabel')]"));
		String interest = getTextByLocator(By.xpath(".//*[contains(@id,'interestAmountLabel')]"));

		if (category1.contains(currency)) {
			assertEquals(act365, dayCount);
			assertEquals(act365Interest, interest);

		} else if (category2.contains(currency)) {
			assertEquals(act360, dayCount);
			assertEquals(act360Interest, interest);

		} else {

			assertEquals("NA", dayCount);
			assertEquals("NA", interest);
		}
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

	public String getCashflowInstrumentDetailByField(String field) {
		return getTextByLocator(By.xpath(".//*[contains(@id,'instrumentDetailsPanel')]//tr//td[contains(text(),'"
				+ field + "')]//following-sibling::td//div"));

	}

	/**
	 * Check timestamp and timezone compulsory for every transaction
	 */
	public void checkTimestampAndTimezone() {
		String format = "dd-MMM-yyyy";
		String today = getCurrentTimeInFormat(format);
		String timezone = "GMT+08:00";
		log("-----checkTimestampAndTimezone-----");
		// check timestamp =today and timezone = GMT +08:00

		assertTrue(getTextByLocator(By
				.xpath(".//td[contains(text(),'Order Placement')]//following-sibling::td//input[@id='gwt-debug-DateTimePicker-dateTextBox']"))
						.contains(today));

		assertTrue(getTextByLocator(By
				.xpath(".//td[contains(text(),'Order Taken Timestamp')]//following-sibling::td//input[@id='gwt-debug-DateTimePicker-dateTextBox']"))
						.contains(today));

		assertEquals(timezone, getTextByLocator(By.xpath(
				".//td[contains(text(),'Order Placement')]//following-sibling::td//*[@id='gwt-debug-DateTimePicker-timezoneLabel']")));

		assertEquals(timezone, getTextByLocator(By.xpath(
				".//td[contains(text(),'Order Taken Timestamp')]//following-sibling::td//*[@id='gwt-debug-DateTimePicker-timezoneLabel']")));

	}

	public void checkorderPlacementTimestampAndExecutionDate() throws ParseException {
		TradeOrderFormPage form = new TradeOrderFormPage(webDriver, HistoryPage.class);
		String orderPlacementTimestamp = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String executionDate = this.getDateAfterDays(orderPlacementTimestamp, -1, "dd-MMM-yyyy");

		form.editExecutionDate(executionDate).clickSaveButtonForCheckingFields();
		assertTrue(pageContainsStr("Execution Date cannot be before Order Placement Date"));
		clickOkButtonIfVisible();

	}

	/**
	 * Check Time In Force field Compulsory for txn with Time In Force field
	 * Please follow the rule that the first investment must be from US and the
	 * second from hk
	 * 
	 * @throws Exception
	 */
	public void checkTimeInForce(String investment1, String closingTime1, String investment2, String closingTime2)
			throws Exception {
		TradeOrderFormPage form = new TradeOrderFormPage(webDriver, HistoryPage.class);
		String validity = "Day";
		String validity2 = "GTT";
		String validity3 = "GTC";
		Boolean isEquity = isElementVisible(By.id("gwt-debug-InstrumentSecuritiesView-assetFind"));
		Boolean isEquityOption = isElementVisible(
				By.xpath(".//*[contains(@debugid,'InstrumentOptionView-underlyingIsinLabelRow')]"));
		String format = "dd-MMM-yyyy";
		String today = getCurrentTimeInFormat(format);

		log("-----checkTimeInForce-----");

		form.editOrderValidity(validity);
		if (isEquity) {
			form.addInstrument(investment1);

		} else {
			if (isEquityOption) {
				form.clickAddUnderlyingButton().addTicker("NYSE", investment1);
			}
			closingTime1 = "00";
		}

		// check the date of order validity, default date is today
		assertTrue(getTextByLocator(
				By.xpath("//*[contains(@id,'gttLinkedDateTimePicker')]//*[@id='gwt-debug-DateTimePicker-dateTextBox']"))
						.contains(today));

		// check the closing time and see if it matchs the market closing time
		// USA market = 17:00
		assertEquals(closingTime1, getTextByLocator(
				By.xpath("//*[contains(@id,'gttLinkedDateTimePicker')]//*[@id='gwt-debug-TimePicker-hhTextBox']")));

		form.editOrderValidity(validity2);

		assertEquals(closingTime1, getTextByLocator(
				By.xpath("//*[contains(@id,'gttLinkedDateTimePicker')]//*[@id='gwt-debug-TimePicker-hhTextBox']")));

		if (isEquity) {
			form.addInstrument(investment2);
			// check the closing time and see if it matchs the market closing
			// time
			// HK market = 16:00

			form.editOrderValidity(validity);
			wait(1);
			assertEquals(closingTime2, getTextByLocator(By.xpath(
					"//*[contains(@id,'exchangeLocalClosingTimeDateTimePicker')]//*[@id='gwt-debug-TimePicker-hhTextBox']")));

			form.editOrderValidity(validity2);
			wait(1);
			assertEquals(closingTime2, getTextByLocator(By.xpath(
					"//*[contains(@id,'exchangeLocalClosingTimeDateTimePicker')]//*[@id='gwt-debug-TimePicker-hhTextBox']")));

		}

		// if user choose "Good until cancelled", no timestamp box should appear
		form.editOrderValidity(validity3);
		assertTrue(isElementPresent(By.xpath(
				".//*[@id='gwt-debug-MyOrderEditPopupView-gttLinkedDateTimePicker' and @style='display: none;']")));

		// if user don'tchoose for time in force, no timestamp box should appear
		form.editOrderValidity("");
		assertTrue(isElementPresent(By.xpath(
				".//*[@id='gwt-debug-MyOrderEditPopupView-gttLinkedDateTimePicker' and @style='display: none;']")));
	}

	/**
	 * check Order Type field Compulsory for transaction with Order Type field
	 */
	public void checkOrderType() {

		log("-----checkOrderType-----");

		TradeOrderFormPage form = new TradeOrderFormPage(webDriver, HistoryPage.class);
		Boolean ifFX = isElementVisible(By.xpath(".//*[contains(text(),'Currency/Counter Currency')]"));

		// check if order type = Market
		form.editOrderType("Market").clickSaveButtonForCheckingFields();
		assertFalse(pageContainsStr("Select an order type."));
		clickOkButtonIfVisible();

		// check if order type = Limit
		form.editOrderType("Limit");
		assertTrue(isElementVisible(By.xpath(".//*[contains(@id,'limitPriceTextBox')]")));

		form.clickSaveButtonForCheckingFields();
		assertTrue(pageContainsStr("Please enter a valid amount"));
		clickOkButtonIfVisible();

		// check if order type = Other
		form.editOrderType("Other");
		assertTrue(isElementVisible(By.xpath(".//*[contains(@id,'orderTypeOtherComments')]")));

		// check if user don't select order type
		form.editOrderType("").clickSaveButtonForCheckingFields();
		assertTrue(pageContainsStr("Select an order type."));
		clickOkButtonIfVisible();

	}

	/**
	 * Check the relationship between order status and compulsory fields
	 * Compulsory for those transaction have order status(except loan/deposit)
	 */
	public void checkOrderStatusAndCompulsoryField() {

		log("-----checkOrderStatusAndCompulsoryField-----");

		TradeOrderFormPage form = new TradeOrderFormPage(webDriver, HistoryPage.class);
		Boolean isEquity = isElementVisible(By.id("gwt-debug-InstrumentSecuritiesView-assetFind"));

		// for the filled order status, there are compulsory field for them.
		for (String status : filledOrderStatus) {
			form.editOrderStatus(status);

			if (isElementVisible(By.xpath(".//tr[@debugid='MyOrderEditPopupView-filledSharesRow']"))) {
				try {
					assertTrue(this.isFieldMarkedCompulsory("Filled Quantity"));
				} catch (AssertionError e) {
					assertTrue(this.isFieldMarkedCompulsory("Filled Amount"));
				}

				if (isElementVisible(By.xpath((".//tr[@debugid='MyOrderEditPopupView-filledAmountRow']")))) {
					assertTrue(this.isFieldMarkedCompulsory("Filled Amount"));
				}
			}

			assertTrue(this.isFieldMarkedCompulsory("Execution Date"));
			assertTrue(this.isFieldMarkedCompulsory("Execution Price"));
			assertTrue(this.isFieldMarkedCompulsory("Settlement Date"));
			form.clickSaveButtonForCheckingFields();
			if (isElementVisible(By.xpath(".//tr[@debugid='MyOrderEditPopupView-filledAmountRow']"))) {
				assertTrue(pageContainsStr("Please enter either Filled Quantity (units) or Filled Amount"));

			} else if (isElementVisible(By.xpath((".//tr[@debugid='MyOrderEditPopupView-filledShareRow']")))) {
				assertTrue(pageContainsStr("Filled Quantity (Units) is mandatory"));
			}

			assertTrue(pageContainsStr("Please enter a valid amount"));
			assertTrue(pageContainsStr("Settlement Date is mandatory."));
			clickOkButtonIfVisible();

		}

		// for the filled order status, there are no compulsory field for them.
		for (String status : unfilledOrderStatus) {
			form.editOrderStatus(status);
			assertFalse(this.isFieldMarkedCompulsory("Execution Date"));
			assertFalse(this.isFieldMarkedCompulsory("Filled Quantity"));
			assertFalse(this.isFieldMarkedCompulsory("Filled Amount"));
			assertFalse(this.isFieldMarkedCompulsory("Execution Price"));
			// assertFalse(this.isFieldMarkedCompulsory("Settlement Date"));
			form.clickSaveButtonForCheckingFields();
			assertFalse(pageContainsStr("Please enter either Filled Quantity (units) or Filled Amount"));
			assertFalse(pageContainsStr("Please enter a valid amount"));
			assertFalse(pageContainsStr("Settlement Date is mandatory."));
			clickOkButtonIfVisible();
		}
		form.editOrderStatus("");
		if (isEquity) {
			form.editOrderSide("").editOrderType("").editQuantityUnit("");
		}

	}

	/**
	 * Check the relationship between Order status and filled quantity
	 * Compulsory for all transaction
	 * 
	 * @throws InterruptedException
	 */
	public void checkOrderStatusAndFilledQuantity(String instrument) throws InterruptedException {
		String quantity = "10";
		String statusPartially = "Filled partially";
		String statusSettled = "Settled";
		String statusPending = "Pending";
		String statusFully = "Filled fully";
		Boolean isEquity = isElementVisible(By.id("gwt-debug-InstrumentSecuritiesView-assetFind"));

		log("-----checkOrderStatusAndFilledQuantity-----");
		TradeOrderFormPage form = new TradeOrderFormPage(webDriver, HistoryPage.class);
		form.addInstrument(instrument).editQuantityUnit(quantity).editOrderSide("Buy").editOrderStatus(statusPartially);

		assertNull(getTextByLocator(By.id("gwt-debug-MyOrderEditPopupView-filledSharesTextBox")));

		form.editOrderStatus(statusSettled);
		assertNull(getTextByLocator(By.id("gwt-debug-MyOrderEditPopupView-filledSharesTextBox")));

		form.editOrderStatus(statusPending);
		assertNull(getTextByLocator(By.id("gwt-debug-MyOrderEditPopupView-filledSharesTextBox")));

		form.editOrderStatus(statusFully);
		assertNotNull(getTextByLocator(By.id("gwt-debug-MyOrderEditPopupView-filledSharesTextBox")));

		form.editOrderStatus("");
		if (isEquity) {
			form.editQuantityUnit("");
		}
	}

	/**
	 * This is to check the logic behind currency. Compulsory for transaction
	 * with single instrument currency
	 * 
	 * @throws Exception
	 */
	/*
	 * Logic checked: /1. The currency of fields change with the intrument
	 * currency 2. Change on Side does not lead to change of instrument currency
	 * 3. Only consistent currency lead to correct calculation./
	 */
	public void checkCurrencyLogic(String investment1, String currency1, String investment2, String currency2)
			throws Exception {

		String side = "Buy";
		String quantity = "10";
		String status = "Filled fully";
		String executionPrice = "10";
		String commission = "10";
		String fees = "10";
		String settlementDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		Boolean isEquity = isElementVisible(By.id("gwt-debug-InstrumentSecuritiesView-assetFind"));
		Boolean isEquityOption = isElementVisible(
				By.xpath(".//*[contains(@debugid,'InstrumentOptionView-underlyingIsinLabelRow')]"));
		log("-----checkCurrencyLogic-----");
		TradeOrderFormPage form = new TradeOrderFormPage(webDriver, HistoryPage.class);

		// Logic 1 and 2

		// currency of instruement = currency of other fields
		if (isEquity) {
			form.addInstrument(investment1);
		}
		if (isEquityOption) {
			form.clickAddUnderlyingButton().addTicker("NYSE", investment1);
		}
		form.editOrderSide(side).editQuantityUnit(quantity).editOrderStatus(status).editExecutionPrice(executionPrice)
				.editSettlementDate(settlementDate).editCommission(commission);

		// For some intrument, there is no filled amount/amount
		if (isElementVisible(By.xpath(".//*[@id='MyOrderEditPopupView-filledAmountRow']"))) {
			this.isCurrencyBesideGivenField("Amount", currency1);
			this.isCurrencyBesideGivenField("Filled Amount", currency1);
		}

		// For some instrument, there is no accrued interest
		if (isElementVisible(By.xpath(".//*[@id='MyOrderEditPopupView-accruedInterestRow']"))) {
			this.isCurrencyBesideGivenField("Accrued Interest", currency1);
		}

		this.isCurrencyBesideGivenField("Execution Price", currency1);
		this.isCurrencyBesideGivenField("Gross Settlement Amount", currency1);
		// TODO
		// this.isCurrencyBesideGivenField("Total Commission and Fees",
		// currency_usd);
		this.isCurrencyBesideGivenField("Net Settlement Amount", currency1);
		this.isCurrencyBesideGivenField("Commission Amount", currency1);
		this.isCurrencyBesideGivenField("Fees", currency1);
		this.isCurrencyBesideGivenField("Settlement Adjustment Amount", currency1);

		if (isEquity) {
			form.addInstrument(investment2);
		}
		if (isEquityOption) {
			form.clickAddUnderlyingButton().addTicker("Hong Kong Exchanges and Clearing Ltd", investment2);
		}

		form.editOrderSide("Sell");

		// For some intrument, there is no filled amount/amount
		if (isElementVisible(By.xpath(".//*[@id='MyOrderEditPopupView-filledAmountRow']"))) {
			this.isCurrencyBesideGivenField("Amount", currency2);
			this.isCurrencyBesideGivenField("Filled Amount", currency2);
		}

		// For some instrument, there is no accrued interest
		if (isElementVisible(By.xpath(".//*[@id='MyOrderEditPopupView-accruedInterestRow']"))) {
			this.isCurrencyBesideGivenField("Accrued Interest", currency2);
		}

		this.isCurrencyBesideGivenField("Execution Price", currency2);
		this.isCurrencyBesideGivenField("Gross Settlement Amount", currency2);
		// TODO
		// this.isCurrencyBesideGivenField("Total Commission and Fees",
		// currency_hkd);
		this.isCurrencyBesideGivenField("Net Settlement Amount", currency2);
		this.isCurrencyBesideGivenField("Commission Amount", currency2);
		this.isCurrencyBesideGivenField("Fees", currency2);
		this.isCurrencyBesideGivenField("Settlement Adjustment Amount", currency2);
		form.addInstrument(investment1);

		// Logic 3
		// The below will check Total Commission and Fees and Net Settlement
		// Amount

		form.editCommission(commission).editFees(fees).editCommissionCurrency(currency2);
		assertTrue(getTextByLocator(By.id("gwt-debug-MyOrderEditPopupView-totalFeeCurrency")).contains("NA"));
		assertTrue(
				getTextByLocator(By.id("gwt-debug-MyOrderEditPopupView-netSettlementAmountCurrency")).contains("NA"));

		form.editCommissionCurrency(currency1).editFeesCurrency(currency2);
		assertTrue(getTextByLocator(By.id("gwt-debug-MyOrderEditPopupView-totalFeeCurrency")).contains("NA"));
		assertTrue(
				getTextByLocator(By.id("gwt-debug-MyOrderEditPopupView-netSettlementAmountCurrency")).contains("NA"));

		form.editCommissionCurrency(currency1).editFeesCurrency(currency1);
		assertFalse(getTextByLocator(By.id("gwt-debug-MyOrderEditPopupView-totalFeeCurrency")).contains("NA"));
		assertFalse(
				getTextByLocator(By.id("gwt-debug-MyOrderEditPopupView-netSettlementAmountCurrency")).contains("NA"));

		form.editOrderSide("").editQuantityUnit("").editOrderStatus("").editFilledQuantity("").editExecutionPrice("")
				.editSettlementDate("").editCommission("").editFees("");
	}

	/**
	 * Check currency logic of FX form
	 * 
	 * @param buyCurrency
	 * @param sellCurrency
	 */
	public void checkCurrencyLogicFX(String buyCurrency, String sellCurrency) {

		String tmpCurrency = "";
		List<String> currencyListCompare = Arrays.asList("", buyCurrency, sellCurrency);
		List<String> currencyListToCompare = new ArrayList<String>();
		log("-----checkCurrencyLogicFX-----");

		TradeOrderFormPage form = new TradeOrderFormPage(webDriver, HistoryPage.class);
		form.editInstrumentCurrencyAndCounterCurrency(buyCurrency, sellCurrency);

		// check currency cannot equal to counter currency
		form.editInstrumentCurrencyAndCounterCurrency(buyCurrency, buyCurrency);
		assertTrue(pageContainsStr("Currency and Counter Currency cannot be the same"));
		clickOkButtonIfVisible();

		form.editInstrumentCurrencyAndCounterCurrency(buyCurrency, sellCurrency);
		// check counterCurrency = commission currency
		assertEquals(sellCurrency,
				getSelectedTextFromDropDown(By.id("gwt-debug-MyOrderEditPopupView-commissionAmountCurrency")));

		assertEquals(sellCurrency, getSelectedTextFromDropDown(By.id("gwt-debug-MyOrderEditPopupView-commisionCcy")));

		// check available option for buy/sell currency only include currency
		// and counter currency
		Select buyCurrencyList = new Select(
				webDriver.findElement(By.id("gwt-debug-MyOrderEditPopupView-buyCurrencyListBox")));
		Select sellCurrencyList = new Select(
				webDriver.findElement(By.id("gwt-debug-MyOrderEditPopupView-sellCurrencyListBox")));

		// currency/counterCurrency are listed in Buy Currency
		for (WebElement elem : buyCurrencyList.getOptions()) {
			currencyListToCompare.add(elem.getText());
		}
		assertEquals(currencyListCompare, currencyListToCompare);

		currencyListToCompare.clear();
		// currencyListToCompare.add("");

		// currency/counterCurrency are listed in Sell Currency
		for (WebElement elem : sellCurrencyList.getOptions()) {
			currencyListToCompare.add(elem.getText());
		}
		assertEquals(currencyListCompare, currencyListToCompare);

		// check logic of Buy Currency and Sell Currency
		// when former is selected, the latter automatically pops up,vice versa
		form.editInstrumentCurrencyAndCounterCurrency(buyCurrency, sellCurrency).editBuyCurrencyAndAmount(buyCurrency,
				"");
		assertEquals(sellCurrency, getSelectedTextFromDropDown(
				By.xpath(".//select[@id='gwt-debug-MyOrderEditPopupView-sellCurrencyListBox' and @disabled]")));

		form.editBuyCurrencyAndAmount("", "").editSellCurrencyAndAmount(sellCurrency, "");
		assertEquals(buyCurrency, getSelectedTextFromDropDown(
				By.xpath(".//select[@id='gwt-debug-MyOrderEditPopupView-buyCurrencyListBox' and @disabled]")));

		// execution price is buyCurrency/sellCurrency
		assertEquals(buyCurrency + "/" + sellCurrency,
				getTextByLocator(By.id("gwt-debug-MyOrderEditPopupView-originalExeCurrencyLabel")));

		// flipped execution price is sellCurrency/buyCurrency
		assertEquals(sellCurrency + "/" + buyCurrency,
				getTextByLocator(By.id("gwt-debug-MyOrderEditPopupView-flippedExeCurrencyLabel")));

		this.checkNetSettlementAmountCurrency(buyCurrency, sellCurrency);

		// swap buyCurrency and sellCurrency
		tmpCurrency = buyCurrency;
		buyCurrency = sellCurrency;
		sellCurrency = tmpCurrency;

		form.editSellCurrencyAndAmount(sellCurrency, "");
		assertEquals(buyCurrency, getSelectedTextFromDropDown(
				By.xpath(".//select[@id='gwt-debug-MyOrderEditPopupView-buyCurrencyListBox' and @disabled]")));

		this.checkNetSettlementAmountCurrency(buyCurrency, sellCurrency);

		form.editSellCurrencyAndAmount("", "").editInstrumentCurrencyAndCounterCurrency("", "");
	}

	public void checkNetSettlementAmountCurrency(String buyCurrency, String sellCurrency) {

		// netSettlementAmount currency for buy side is buyCurrency
		assertEquals(buyCurrency,
				getTextByLocator(By.id("gwt-debug-MyOrderEditPopupView-netSettlementAmountCurrency")));

		// netSettlementAmount currency for buy side is sellCurrency
		assertEquals(sellCurrency,
				getTextByLocator(By.id("gwt-debug-MyOrderEditPopupView-netSettlementAmountSellCurrency")));

	}

	/**
	 * Check calculation of Trade Order Form Compulsory for transaction with
	 * Amount section
	 * 
	 * @throws Exception
	 */
	/*
	 * 1. Gross Settlement Amount = Filled quantity * price
	 */
	/*
	 * 2. Commission = Commission % * Gross Settlement Amount
	 */
	/*
	 * 3. For net settlement amount, IF(side=BUY, gross settlement amount +
	 * commission amount + fees + tax + settlement adjustment amount).
	 * IF(side=SELL, gross settlement amount - commission amount - fees - tax +
	 * settlement adjustment amount). Formula applies only if all currencies
	 * match. CCY not editable
	 */
	public void checkCalculation(String instrument) throws Exception {
		DecimalFormat f = new DecimalFormat("##.00");
		String quantity = "10";
		String status = "Filled fully";
		Double executionPrice = (double) 10;
		Double executionPrice2 = (Double) 12.345678;
		Double filledQuantity = (double) 10;
		Double filledQuantity2 = (double) 8;
		Integer commissionRate1 = 10;
		Integer commissionRate2 = 1000;
		Integer fees = 10;
		Integer settlementAdjustmentAmount = 10;
		Double commission = (double) 10;
		Integer accruedInterest = 100;
		String settlementDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String side_buy = "Buy";
		String side_sell = "Sell";
		String netSettlementAmount = "";
		Double grossSettlementAmount;
		Boolean isEquity = isElementVisible(By.id("gwt-debug-InstrumentSecuritiesView-assetFind"));
		Boolean isEquityOption = isElementVisible(
				By.xpath(".//*[contains(@debugid,'InstrumentOptionView-underlyingIsinLabelRow')]"));

		log("-----checkCalculation-----");
		TradeOrderFormPage form = new TradeOrderFormPage(webDriver, HistoryPage.class);
		if (isEquity) {
			form.addInstrument(instrument);
		}
		if (isEquityOption) {
			form.clickAddUnderlyingButton().addTicker("NYSE", instrument);
		}
		form.editOrderSide(side_buy).editQuantityUnit(quantity).editOrderStatus(status)
				.editSettlementDate(settlementDate);

		// if accrued interest row is visible, it implies the intrument is bond
		if (isElementVisible(By.xpath(".//tr[@debugid='MyOrderEditPopupView-accruedInterestRow']"))) {

			// 1.Gross Settlement Amount =[(Quantity * (Price/100)] + Accrued
			// Interest
			form.editAccruedInterest(String.valueOf(accruedInterest)).editFilledQuantity(String.valueOf(filledQuantity))
					.editExecutionPrice(String.valueOf(executionPrice)).clickCalculateCommissionAmountButton();
			grossSettlementAmount = (filledQuantity * (executionPrice / 100)) + accruedInterest;
			log(String.valueOf(grossSettlementAmount));
			assertTrue(getTextByLocator(By.id("gwt-debug-MyOrderEditPopupView-grossSettlementAmountLabel"))
					.contains(String.valueOf(grossSettlementAmount)));
		} else {
			// 1. Gross Settlement Amount = Filled quantity * price
			form.editFilledQuantity(String.valueOf(filledQuantity2)).editExecutionPrice(String.valueOf(executionPrice2))
					.clickCalculateCommissionAmountButton();
			grossSettlementAmount = (filledQuantity2 * executionPrice2);
			log("grossSettlementAmount :" + String.valueOf(grossSettlementAmount));
			assertTrue(getTextByLocator(By.id("gwt-debug-MyOrderEditPopupView-grossSettlementAmountLabel"))
					.contains(String.valueOf(f.format(grossSettlementAmount))));

			form.editFilledQuantity(String.valueOf(filledQuantity)).editExecutionPrice(String.valueOf(executionPrice))
					.clickCalculateCommissionAmountButton();

			log("grossSettlementAmount :" + String.valueOf(grossSettlementAmount));
			grossSettlementAmount = (filledQuantity * executionPrice);

			assertTrue(getTextByLocator(By.id("gwt-debug-MyOrderEditPopupView-grossSettlementAmountLabel"))
					.contains(String.valueOf(f.format(grossSettlementAmount))));

		}

		// 2. Commission = Commission % * Gross Settlement Amount

		form.editCommissionByRate(String.valueOf(commissionRate2));

		assertTrue(String.valueOf(grossSettlementAmount * commissionRate2 / 100)
				.contains(getTextByLocator(By.id("gwt-debug-MyOrderEditPopupView-commissionAmountTextBox"))));

		form.editCommissionByRate(String.valueOf(commissionRate1));
		commission = grossSettlementAmount * commissionRate1 / 100;
		assertTrue(String.valueOf(commission)
				.contains(getTextByLocator(By.id("gwt-debug-MyOrderEditPopupView-commissionAmountTextBox"))));

		// 3. Calculation for net settlement amount
		form.editOrderSide(side_buy).editCommissionAmountCurrency("USD").editFeesCurrency("USD")
				.editSettlementAdjustmentAmountCurrency("USD").editFilledQuantity(String.valueOf(filledQuantity))
				.editExecutionPrice(String.valueOf(executionPrice)).clickCalculateCommissionAmountButton()
				.editCommissionByRate(String.valueOf(commissionRate1)).editFees(String.valueOf(fees))
				.editCommission(String.valueOf(commission))
				.editSettlementAdjustmentAmount(String.valueOf(settlementAdjustmentAmount))
				.clickCalculateCommissionAmountButton();
		log("grossSettlementAmount: " + grossSettlementAmount);
		log("fees: " + fees);
		log("commission: " + commission);
		log("settlementAdjustmentAmount: " + settlementAdjustmentAmount);
		log("netSettlementAmount: "
				+ String.valueOf(grossSettlementAmount + fees + commission + settlementAdjustmentAmount));
		log(getTextByLocator(By.id("gwt-debug-MyOrderEditPopupView-netSettlementAmountLabel")));
		netSettlementAmount = String
				.valueOf(f.format(grossSettlementAmount + fees + commission + settlementAdjustmentAmount));

		assertEquals(getTextByLocator(By.id("gwt-debug-MyOrderEditPopupView-netSettlementAmountLabel")),
				netSettlementAmount);

		form.editOrderSide(side_sell);

		netSettlementAmount = String
				.valueOf(f.format(grossSettlementAmount - fees - commission + settlementAdjustmentAmount));

		assertEquals(getTextByLocator(By.id("gwt-debug-MyOrderEditPopupView-netSettlementAmountLabel")),
				netSettlementAmount);

		form.editCommissionAmountCurrency("").editFeesCurrency("").editSettlementAdjustmentAmountCurrency("")
				.editOrderSide("").editQuantityUnit("").editOrderStatus("").editFilledQuantity("")
				.editExecutionPrice("").editSettlementDate("").editCommissionByRate("").editCommission("").editFees("")
				.editSettlementAdjustmentAmount("");

	}

	public void checkCalculationFX(String buyCurrency, String sellCurrency) {
		String executionPrice = "1";
		String orderType = "Market";
		DecimalFormat f = new DecimalFormat("##.00");
		Double amount = (double) 100;
		Double commission = (double) 40;
		Double settlementAdjustmentAmount = (double) 30;
		Double sellSettlementAdjustmentAmount = (double) 20;

		log("-----checkCalculationFX-----");
		TradeOrderFormPage form = new TradeOrderFormPage(webDriver, HistoryPage.class);
		form.editInstrumentCurrencyAndCounterCurrency(buyCurrency, sellCurrency);

		// IF(commission CCY = sell currency, settlement amount of the sell
		// currency + commission ccy + settlement adjustment amount)
		form.editExecutionPrice(executionPrice).editBuyCurrencyAndAmount(buyCurrency, String.valueOf(amount))
				.editCommission(String.valueOf(commission))
				.editSettlementAdjustmentAmount(String.valueOf(settlementAdjustmentAmount))
				.editSettlementAdjustmentAmountSell(String.valueOf(sellSettlementAdjustmentAmount))
				.editCommissionCurrency(sellCurrency).editOrderType(orderType);

		log("netSettlementAmountSell: "
				+ String.valueOf(f.format(amount + commission + sellSettlementAdjustmentAmount)));
		// net settlement amount (sell)
		assertEquals(String.valueOf(f.format(amount + commission + sellSettlementAdjustmentAmount)),
				getTextByLocator(By.id("gwt-debug-MyOrderEditPopupView-netSettlementAmountSellLabel")));

		// net settlement amount (buy)
		assertEquals(String.valueOf(f.format(amount + settlementAdjustmentAmount)),
				getTextByLocator(By.id("gwt-debug-MyOrderEditPopupView-netSettlementAmountLabel")));

		// IF(commission CCY = buy currency, settlement amount of the buy amount
		// - commission ccy + settlement adjustment amount).

		// net settlement amount (sell)
		form.editCommissionCurrency(buyCurrency);
		assertEquals(String.valueOf(f.format(amount + sellSettlementAdjustmentAmount)),
				getTextByLocator(By.id("gwt-debug-MyOrderEditPopupView-netSettlementAmountSellLabel")));

		// net settlement amount (buy)
		assertEquals(String.valueOf(f.format(amount - commission + settlementAdjustmentAmount)),
				getTextByLocator(By.id("gwt-debug-MyOrderEditPopupView-netSettlementAmountLabel")));

		form.editExecutionPrice("").editSettlementAdjustmentAmount("").editSettlementAdjustmentAmountSell("")
				.editCommission("").editBuyCurrencyAndAmount("", "").editInstrumentCurrencyAndCounterCurrency("", "");
	}

	/**
	 * Check the decimal place for FX window
	 * 
	 * @param buyCurrency
	 * @param sellCurrency
	 * @throws InterruptedException
	 */
	public void checkDecimalPlaceFX(String buyCurrency, String sellCurrency) throws InterruptedException {
		String executionPrice = "0";
		String amount = "";
		log("-----checkDecimalPlaceFX-----");

		TradeOrderFormPage form = new TradeOrderFormPage(webDriver, HistoryPage.class);
		form.editInstrumentCurrencyAndCounterCurrency(buyCurrency, sellCurrency);

		// check Execution Price decimal place
		// if execution price = 0, execution price is empty
		form.editExecutionPrice(executionPrice);
		// for shifting focus
		clickElement(By.id("gwt-debug-MyOrderEditPopupView-commentsTextArea"));

		assertEquals(executionPrice, getTextByLocator(By.id("gwt-debug-MyOrderEditPopupView-exePriceTextBox")));

		assertNull(getTextByLocator(By.id("gwt-debug-MyOrderEditPopupView-flippedExePriceLabel")));

		// the maximum decimal place of execution price is 8.
		executionPrice = "1.2";
		form.editExecutionPrice(executionPrice);
		// for shifting focus
		clickElement(By.id("gwt-debug-MyOrderEditPopupView-commentsTextArea"));
		assertEquals("0.83333333", getTextByLocator(By.id("gwt-debug-MyOrderEditPopupView-flippedExePriceLabel")));

		executionPrice = "0.0000010001";
		form.editExecutionPrice(executionPrice);
		// for shifting focus
		clickElement(By.id("gwt-debug-MyOrderEditPopupView-commentsTextArea"));
		assertEquals("999,900.00999900",
				getTextByLocator(By.id("gwt-debug-MyOrderEditPopupView-flippedExePriceLabel")));

		executionPrice = "1";
		form.editExecutionPrice(executionPrice);
		// for shifting focus
		clickElement(By.id("gwt-debug-MyOrderEditPopupView-commentsTextArea"));
		assertEquals("1.00000000", getTextByLocator(By.id("gwt-debug-MyOrderEditPopupView-flippedExePriceLabel")));

		// check maximum decimal place of Amount field, which is 2
		amount = "1";
		this.checkAmountFieldDecimalPlace(buyCurrency, amount, amount);

		amount = "1.1";
		this.checkAmountFieldDecimalPlace(buyCurrency, amount, amount);

		amount = "1.21";
		this.checkAmountFieldDecimalPlace(buyCurrency, amount, amount);

		this.checkAmountFieldDecimalPlace(buyCurrency, "1.2132461", amount);

		form.editExecutionPrice("").editBuyCurrencyAndAmount("", "").editInstrumentCurrencyAndCounterCurrency("", "");
	}

	/**
	 * This function is under the assumption that execution price is 1
	 * 
	 * @param buyCurrency
	 * @param amountToInput
	 * @param amountToCheck
	 * @throws InterruptedException
	 */
	public void checkAmountFieldDecimalPlace(String buyCurrency, String amountToInput, String amountToCheck)
			throws InterruptedException {
		log("-----checkAmountFieldDecimalPlace-----");
		Double netSettlementAmount = (double) Double.valueOf(amountToCheck);
		DecimalFormat f = new DecimalFormat("##.00");

		TradeOrderFormPage form = new TradeOrderFormPage(webDriver, HistoryPage.class);

		form.editBuyCurrencyAndAmount(buyCurrency, amountToInput);
		// for shifting focus
		clickElement(By.id("gwt-debug-MyOrderEditPopupView-commentsTextArea"));

		assertEquals(amountToCheck, getTextByLocator(By.id("gwt-debug-MyOrderEditPopupView-sellAmountTextBox")));

		assertEquals(String.valueOf(f.format(netSettlementAmount)),
				getTextByLocator(By.id("gwt-debug-MyOrderEditPopupView-netSettlementAmountLabel")));

		assertEquals(String.valueOf(f.format(netSettlementAmount)),
				getTextByLocator(By.id("gwt-debug-MyOrderEditPopupView-netSettlementAmountSellLabel")));

	}

	/**
	 * This is to check the logic behind deposit type and maturity date Only for
	 * Deposit transaction
	 */
	public void checkDepositTypeAndMaturityDate() {
		String timeDeposit = "Time Deposit";
		String callDeposit = "Call Deposit";

		log("-----checkDepositTypeAndMaturityDate-----");

		TradeOrderFormPage form = new TradeOrderFormPage(webDriver, HistoryPage.class);
		form.editDepositType(timeDeposit);
		assertTrue(isElementVisible(By.xpath(".//*[contains(@debugid,'InstrumentMoneyMarketView-maturityDateRow')]")));

		form.editDepositType(callDeposit);
		assertFalse(isElementVisible(By.xpath(".//*[contains(@debugid,'InstrumentMoneyMarketView-maturityDateRow')]")));

		form.editDepositType("");
	}

	/**
	 * This is to check the logic behind positionType and postion/side. For Loan
	 * and Deposit transaction
	 */
	public void checkPositionTypeAndPositionAndSide() {
		String close = "Close";
		String open = "Open";
		String sell = "Sell";
		String buy = "Buy";
		log("-----checkPositionTypeAndPositionAndSide-----");

		TradeOrderFormPage form = new TradeOrderFormPage(webDriver, HistoryPage.class);
		Select select = new Select(webDriver.findElement(By.xpath(".//*[contains(@id,'sideOptionsListBox')]")));

		form.editPositionType(close);
		assertTrue(isElementVisible(By.xpath(".//*[contains(@debugid,'InstrumentMoneyMarketView-positionsRow')]")));
		assertEquals(sell, select.getFirstSelectedOption().getText());

		form.editPositionType(open);
		assertFalse(isElementVisible(By.xpath(".//*[contains(@debugid,'InstrumentMoneyMarketView-positionsRow')]")));
		assertEquals(buy, select.getFirstSelectedOption().getText());

		form.editPositionType("");
	}

	/**
	 * Check the currency display and their daycount convention. For loan and
	 * deposit transaction
	 * 
	 * @throws InterruptedException
	 */
	public void checkCurrencyAndCurrencyDisplayAndDayCount() throws InterruptedException {
		log("-----checkCurrencyAndCurrencyDisplayAndDayCount-----");
		TradeOrderFormPage form = new TradeOrderFormPage(webDriver, HistoryPage.class);
		String act365 = "ACT/365";
		String act360 = "ACT/360";
		ArrayList<String> category1 = new ArrayList<String>() {
			{
				add("SGD");
				add("HKD");
				add("GBP");
				// add("RMB");
				add("MYR");
			}
		};

		// Only check category1 currency since all currency has the same effect
		for (String currency : category1) {
			form.editInstrumentCurrency(currency);
			assertEquals(act365, getTextByLocator(By.xpath(".//*[contains(@id,'dayCountConventionLabel')]")));
			wait(3);
			assertEquals(currency, this.getTextValueNextToField("Principal Amount"));
			assertEquals(currency, this.getTextValueNextToField("Interest Amount"));
			assertEquals(currency, this.getTextValueNextToField("Gross Settlement Amount"));
			assertEquals(currency, this.getTextValueNextToField("Net Settlement Amount"));
			assertEquals(currency, this.getSelectedOptionNextToField("Commission"));
			// assertEquals(currency,
			// this.getSelectedOptionNextToField("Settlement Adjustment
			// Amount"));

		}
		ArrayList<String> category2 = new ArrayList<String>() {
			{
				add("EUR");
				add("USD");
				add("JPY");
				add("AUD");
				add("CAD");
				add("CHF");
				add("NZD");
				add("PHP");
			}
		};
		for (String currency : category2) {
			form.editInstrumentCurrency(currency);
			assertEquals(act360, getTextByLocator(By.xpath(".//*[contains(@id,'dayCountConventionLabel')]")));

		}
		ArrayList<String> category3 = new ArrayList<String>() {
			{
				add("EEK");
				add("EGL");
			}
		};
		for (String currency : category3) {
			form.editInstrumentCurrency(currency);
			assertEquals("NA", getTextByLocator(By.xpath(".//*[contains(@id,'dayCountConventionLabel')]")));

		}

		form.editInstrumentCurrency("");
	}

	/**
	 * Check the logic behind status and compulsory fields. Only for loan and
	 * deposit transaction
	 */
	public void checkOrderStatusAndCompulsoryFieldForDepositAndLoan() {
		log("-----checkOrderStatusAndCompulsoryFieldForDepositAndLoan-----");
		ArrayList<String> statuses = new ArrayList<String>() {
			{
				add("Pending");
				add("Cancelled");
				add("Error");
				add("Settled");
			}

		};
		TradeOrderFormPage form = new TradeOrderFormPage(webDriver, HistoryPage.class);
		form.editPositionType("").editInstrumentCurrency("").editInterestRate("").editOrderSide("").editQuantity("")
				.editExecutionDate("").editSettlementDate("").editStartDate("").editMaturityDate("");

		for (String status : statuses) {
			form.editOrderStatus(status).clickSaveButtonForCheckingFields();
			// assertTrue(pageContainsStr("Position Type is mandatory"));
			// assertTrue(pageContainsStr("Currency is a mandatory field"));
			// assertTrue(pageContainsStr("Interest Rate is a mandatory
			// field"));
			// assertTrue(pageContainsStr("Please enter a valid maturity
			// date"));
			// assertTrue(pageContainsStr("Please enter a valid start date"));
			// assertTrue(pageContainsStr("Select 'Buy' or 'Sell'"));
			// assertTrue(pageContainsStr("Principal amount is mandatory"));
			// assertTrue(pageContainsStr("Value Date is mandatory"));
			// assertTrue(pageContainsStr("Execution Date is mandatory"));
			clickOkButtonIfVisible();
			assertTrue(getSizeOfElements(By.xpath(".//*[contains(@class,'formTextBoxInvalid')]")) > 0);

		}

	}

	/**
	 * Check Calculation of interest for loan and interest Only for Loan and
	 * Interest
	 * 
	 * @param type
	 * @throws ParseException
	 */
	public void checkCalculationForLoanAndDeposit(String type) throws ParseException {
		log("-----checkCalculationForLoanAndDeposit-----");
		String depositType = "Time Deposit";
		String positionType = "Open";
		String currency = "USD";
		String currency2 = "HKD";
		Double dayCount360 = (double) 360;
		Double interestRate = (double) 36;
		Double dayCount365 = (double) 365;
		String startDate = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		Integer daysInterval = 10;
		String maturityDate = this.getDateAfterDays(startDate, daysInterval, "dd-MMM-yyyy");
		DecimalFormat f = new DecimalFormat("##.00");
		Double interestAmount;
		Double principalAmount = (double) 10000;
		TradeOrderFormPage form = new TradeOrderFormPage(webDriver, HistoryPage.class);
		if (type.equals("Deposit")) {
			form.editDepositType(depositType);
		}
		form.editPositionType(positionType).editInstrumentCurrency(currency)
				.editInterestRate(String.valueOf(interestRate)).editStartDate(startDate).editMaturityDate(maturityDate)
				.editQuantity(String.valueOf(principalAmount)).editInterestPeriodFrom(startDate)
				.editInterestPeriodTo(maturityDate).clickCalculateInterestAmountButton();

		// interestAmount = (Maturity Date - Value Date)/Daycount]*Interest
		// Rate*Quantity
		interestAmount = (daysInterval / dayCount360) * (interestRate / 100) * principalAmount;
		assertEquals(String.valueOf(f.format(interestAmount)),
				getTextByLocator(By.xpath(".//*[contains(@id,'interestAmountLabel')]")));

		form.editInstrumentCurrency(currency2).clickCalculateInterestAmountButton();

		interestAmount = (daysInterval / dayCount365) * (interestRate / 100) * principalAmount;
		assertEquals(String.valueOf(f.format(interestAmount)),
				getTextByLocator(By.xpath(".//*[contains(@id,'interestAmountLabel')]")));

		if (type.equals("Deposit")) {
			form.editDepositType("");
		}
		form.editPositionType("").editInstrumentCurrency("").editInterestRate("").editStartDate("").editMaturityDate("")
				.editQuantity("").editInterestPeriodFrom("").editInterestPeriodTo("")
				.clickCalculateInterestAmountButton();
	}

	/**
	 * Return if the given field is marked as compulsory in TradeOrderForm
	 * 
	 * @param field
	 *            field in TradeOrderForm
	 * @return
	 */
	public Boolean isFieldMarkedCompulsory(String field) {
		return isElementVisible(By.xpath(".//*[*[contains(text(),'" + field + "')]]//span[contains(text(),'*')]"));
	}

	public boolean isCurrencyBesideGivenField(String field, String currency) {

		try {
			return isElementVisible(By.xpath("(.//*[*[contains(text(),'" + field
					+ "')]]//following-sibling::td//div[.='" + currency + "'])[1]"));
		} catch (NoSuchElementException e) {
			return currency.equals(getSelectedTextFromDropDown(
					(By.xpath(".//*[*[contains(text(),'" + field + "')]]//following-sibling::td//select"))));
		}

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
		int size;
		try {
			waitForElementVisible(By.xpath(".//td[contains(text(),'" + field + "')]//following-sibling::td//div"), 10);
			size = getSizeOfElements(By.xpath(".//td[contains(text(),'" + field + "')]//following-sibling::td//div"));
			text = getTextByLocator(
					By.xpath("(.//td[contains(text(),'" + field + "')]//following-sibling::td//div)[1]"));
		} catch (TimeoutException e) {
			size = getSizeOfElements(
					By.xpath(".//td[*[contains(text(),'" + field + "')]]//following-sibling::td//div"));
			text = getTextByLocator(
					By.xpath("(.//td[*[contains(text(),'" + field + "')]]//following-sibling::td//div)[1]"));
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
		int size = getSizeOfElements(
				By.xpath(".//td[contains(text(),'" + field + "')]//following-sibling::td//select"));
		String text = getSelectedTextFromDropDown(By.xpath("(.//td[contains(text(),'" + field
				+ "')]//following-sibling::td//select)[" + String.valueOf(size) + "]"));
		return text;
	}
}
