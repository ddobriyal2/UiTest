package org.sly.uitest.pageobjects.clientsandaccounts;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.pageobjects.investments.InvestmentsPage;
import org.sly.uitest.settings.Settings;

/**
 * This page appears after clicking the pencil button of Investment Order page
 * or performing trade transaction reconciliation.
 * 
 * @author Benny Leung
 * @company Prive Financial
 * @date : Oct 20, 2016
 */
public class TradeOrderFormPage extends AbstractPage {
	private Class<?> theClass = null;

	public TradeOrderFormPage(WebDriver webDriver, Class<?> theClass) {

		super();
		this.webDriver = webDriver;
		this.theClass = theClass;
		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		try {
			// wait.until(ExpectedConditions.visibilityOfElementLocated(By
			// .id("gwt-debug-TransactionEditPopupPresenterWidgetView-orderPanel")));

		} catch (Exception ex) {
			// wait.until(ExpectedConditions.visibilityOfElementLocated(By
			// .id("gwt-debug-MyMainMaterialView-mainPanel")));

		}

		// assertTrue(pageContainsStr("Reallocate"));
	}

	public TradeOrderFormPage(WebDriver webDriver) {

		super();
		this.webDriver = webDriver;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		try {
			// wait.until(ExpectedConditions.visibilityOfElementLocated(By
			// .id("gwt-debug-TransactionEditPopupPresenterWidgetView-orderPanel")));

		} catch (Exception ex) {
			// wait.until(ExpectedConditions.visibilityOfElementLocated(By
			// .id("gwt-debug-MyMainMaterialView-mainPanel")));

		}

		// assertTrue(pageContainsStr("Reallocate"));
	}

	/**
	 * choose edit trade side in Trade Order Form
	 * 
	 * @param side
	 *            Trade side: Buy/Sell
	 * @return {@link TradeOrderFormPage}
	 */
	public TradeOrderFormPage editOrderSide(String side) {

		waitForElementVisible(By.xpath(".//*[contains(@id,'sideOptionsListBox')]"), 10);

		selectFromDropdown(By.xpath(".//*[contains(@id,'sideOptionsListBox')]"), side);

		return this;
	}

	/**
	 * choose type of trade in Trade Order Form
	 * 
	 * @param type
	 *            Type of trade: Limit/Market
	 * 
	 * @return {@link TradeOrderFormPage}
	 */
	public TradeOrderFormPage editOrderType(String type) {

		waitForElementVisible(By.xpath(".//*[contains(@id,'orderTypeListBox')]"), 10);

		selectFromDropdown(By.xpath(".//*[contains(@id,'orderTypeListBox')]"), type);

		return this;
	}

	/**
	 * enter quantity(units) of tradeing Trade Order Form
	 * 
	 * @param quantity
	 * @return {@link TradeOrderFormPage}
	 */
	public TradeOrderFormPage editQuantityUnit(String quantity) {

		waitForElementVisible(By.xpath(".//*[contains(@id,'amountTextBox')]"), 10);
		sendKeysToElement(By.xpath(".//*[contains(@id,'amountTextBox')]"), quantity);

		return this;
	}

	/**
	 * enter quantity of tradeing Trade Order Form
	 * 
	 * @param quantity
	 * @return {@link TradeOrderFormPage}
	 */
	public TradeOrderFormPage editQuantity(String quantity) {

		waitForElementVisible(By.xpath(".//*[contains(@id,'amountFundsTextBox')]"), 10);
		sendKeysToElement(By.xpath(".//*[contains(@id,'amountFundsTextBox')]"), quantity);
		try {
			wait(1);
		} catch (InterruptedException e) {

		}
		return this;
	}

	/**
	 * enter filled quantity of tradeing Trade Order Form
	 * 
	 * @param quantity
	 * @return {@link TradeOrderFormPage}
	 */
	public TradeOrderFormPage editFilledQuantity(String quantity) {

		waitForElementVisible(By.xpath(".//*[contains(@id,'filledSharesTextBox')]"), 10);
		sendKeysToElement(By.xpath(".//*[contains(@id,'filledSharesTextBox')]"), quantity);

		return this;
	}

	/**
	 * enter filled amount of tradeing Trade Order Form
	 * 
	 * @param amount
	 * @return {@link TradeOrderFormPage}
	 */
	public TradeOrderFormPage editFilledAmount(String amount) {

		waitForElementVisible(By.xpath(".//*[contains(@id,'filledAmountTextBox')]"), 10);
		sendKeysToElement(By.xpath(".//*[contains(@id,'filledAmountTextBox')]"), amount);

		return this;
	}

	/**
	 * enter execution price for trading in trade order form
	 * 
	 * @param price
	 * @return
	 */
	public TradeOrderFormPage editExecutionPrice(String price) {

		waitForElementVisible(By.xpath(".//*[contains(@id,'exePriceTextBox')]"), 10);
		sendKeysToElement(By.xpath(".//*[contains(@id,'exePriceTextBox')]"), price);

		return this;
	}

	public TradeOrderFormPage editFuturesPrice(String price) {
		waitForElementVisible(By.xpath(".//*[contains(@id,'futuresPriceTextBox')]"), 10);
		sendKeysToElement(By.xpath(".//*[contains(@id,'futuresPriceTextBox')]"), price);
		return this;
	}

	public TradeOrderFormPage editExecutionPriceCurrency(String currency) {
		selectFromDropdown(By.id("gwt-debug-MyOrderEditPopupView-premiumCurrency"), currency);
		return this;
	}

	public TradeOrderFormPage editAccruedInterest(String interest) {

		waitForElementVisible(By.xpath(".//*[contains(@id,'accruedInterestTextBox')]"), 10);
		sendKeysToElement(By.xpath(".//*[contains(@id,'accruedInterestTextBox')]"), interest);

		return this;
	}

	/**
	 * enter settlement date for trading in trade order form
	 * 
	 * @param settlementDate
	 * @return
	 */
	public TradeOrderFormPage editSettlementDate(String settlementDate) {
		waitForElementVisible(By.xpath(".//*[contains(@id,'settlementDateTextBox')]"), 10);
		sendKeysToElement(By.xpath(".//*[contains(@id,'settlementDateTextBox')]"), settlementDate);

		try {
			wait(1);
		} catch (InterruptedException e) {

		}
		return this;
	}

	// /**
	// * edit commission for trading in trade order form
	// *
	// * @param commission
	// * @param rate
	// * @return
	// */
	// public TradeOrderFormPage editCommissionAmount(String commission,
	// String rate) {
	//
	// sendKeysToElement(
	// By.id("gwt-debug-TransactionEditPopupPresenterWidgetView-commissionAmountTextBox"),
	// commission);
	//
	// sendKeysToElement(
	// By.id("gwt-debug-TransactionEditPopupPresenterWidgetView-commissionAmountRateTextBox"),
	// rate);
	//
	// clickElement(By
	// .id("gwt-debug-TransactionEditPopupPresenterWidgetView-calculateCommissionButton"));
	//
	// return this;
	// }

	/**
	 * edit commission by rate, given gross settlement amount
	 * 
	 * @param rate
	 * @return
	 */
	public TradeOrderFormPage editCommissionByRate(String rate) {

		waitForElementVisible(By.xpath(".//*[contains(@id,'commissionAmountRateTextBox')]"), 10);
		sendKeysToElement(By.xpath(".//*[contains(@id,'commissionAmountRateTextBox')]"), rate);

		clickElement(By.xpath(".//*[contains(@id,'calculateCommissionButton')]"));

		return this;
	}

	public TradeOrderFormPage editPremiumAmount(String premiumAmount) {
		By by = By.xpath(".//*[contains(@id,'fxOptionPremiumAmountTextBox')]");
		waitForElementVisible(by, Settings.WAIT_SECONDS);
		sendKeysToElement(by, premiumAmount);

		WebElement elem = webDriver.findElement(by);
		elem.sendKeys(Keys.ENTER);
		return this;
	}

	/**
	 * edit commission
	 * 
	 * @param commission
	 * @return
	 */
	public TradeOrderFormPage editCommission(String commission) {

		waitForElementVisible(By.xpath(".//*[contains(@id,'commissionAmountTextBox')]"), 10);
		sendKeysToElement(By.xpath(".//*[contains(@id,'commissionAmountTextBox')]"), commission);
		if (isElementVisible(By.id("gwt-debug-MyOrderEditPopupView-calculateCommissionButton"))) {
			clickElement(By.id("gwt-debug-MyOrderEditPopupView-calculateCommissionButton"));
		}

		return this;
	}

	/**
	 * edit commission currency
	 * 
	 * @param currency
	 * @return {@link TradeOrderFormPage}
	 */
	public TradeOrderFormPage editCommissionCurrency(String currency) {
		waitForElementVisible(By.xpath(".//*[contains(@id,'commissionAmountCurrency')]"), 10);

		selectFromDropdown(By.xpath(".//*[contains(@id,'commissionAmountCurrency')]"), currency);
		return this;
	}

	/**
	 * edit company commission rate
	 * 
	 * @param rate
	 * @return {@link TradeOrderFormPage}
	 */
	public TradeOrderFormPage editCompanyCommissionRate(String rate) {
		waitForElementVisible(By.xpath(".//*[contains(@id,'spreadRateToBank')]"), 10);

		sendKeysToElement(By.xpath(".//*[contains(@id,'spreadRateToBank')]"), rate);
		return this;
	}

	public TradeOrderFormPage editCompanyCommission(String commission) {
		waitForElementVisible(By.id("gwt-debug-MyOrderEditPopupView-commission"), 10);

		sendKeysToElement(By.id("gwt-debug-MyOrderEditPopupView-commission"), commission);
		return this;
	}

	/**
	 * edit settlementAmount of trading
	 * 
	 * @param settlementAmount
	 * @return
	 */
	public TradeOrderFormPage editSettlementAdjustmentAmount(String settlementAdjustmentAmount) {

		waitForElementVisible(By.xpath(".//*[contains(@id,'settlementAdjustmentTextBox')]"), 10);

		sendKeysToElement(By.xpath(".//*[contains(@id,'settlementAdjustmentTextBox')]"), settlementAdjustmentAmount);
		return this;
	}

	/**
	 * edit settlementAmount (Sell)
	 * 
	 * @param settlementAmount
	 * @return
	 */
	public TradeOrderFormPage editSettlementAdjustmentAmountSell(String settlementAdjustmentAmountSell) {
		waitForElementVisible(By.xpath(".//*[contains(@id,'settlementAdjustmentSellTextBox')]"), 10);

		sendKeysToElement(By.xpath(".//*[contains(@id,'settlementAdjustmentSellTextBox')]"),
				settlementAdjustmentAmountSell);
		return this;
	}

	/**
	 * edit settlementAmount currency
	 * 
	 * @param currency
	 * @return
	 */
	public TradeOrderFormPage editSettlementAdjustmentAmountCurrency(String currency) {
		selectFromDropdown(By.xpath(".//*[contains(@id,'settlementAdjustmentCurrency')]"), currency);
		return this;
	}

	public TradeOrderFormPage editOrderStatus(String status) {
		waitForElementVisible(By.xpath(".//*[contains(@id,'statusListBox')]"), 10);

		selectFromDropdown(By.xpath(".//*[contains(@id,'statusListBox')]"), status);
		return this;
	}

	/**
	 * edit fees for trading in trade order form
	 * 
	 * @param fees
	 * @return
	 */
	public TradeOrderFormPage editFees(String fees) {

		waitForElementVisible(By.xpath(".//*[contains(@id,'marketChargesTextBox')]"), 10);

		sendKeysToElement(By.xpath(".//*[contains(@id,'marketChargesTextBox')]"), fees);
		return this;
	}

	/**
	 * edit commission amount currency
	 * 
	 * @param currency
	 * @return
	 */
	public TradeOrderFormPage editCommissionAmountCurrency(String currency) {
		waitForElementVisible(By.xpath(".//*[contains(@id,'commissionAmountCurrency')]"), 10);

		selectFromDropdown(By.xpath(".//*[contains(@id,'commissionAmountCurrency')]"), currency);
		return this;
	}

	/**
	 * edit fees currency
	 * 
	 * @param currency
	 * @return
	 */
	public TradeOrderFormPage editFeesCurrency(String currency) {
		waitForElementVisible(By.xpath(".//*[contains(@id,'marketChargesCurrency')]"), 10);

		selectFromDropdown(By.xpath(".//*[contains(@id,'marketChargesCurrency')]"), currency);
		return this;
	}

	/**
	 * edit the currency of the instrument in instrument details
	 * 
	 * @param currency
	 * @return
	 */
	public TradeOrderFormPage editInstrumentCurrency(String currency) {

		selectFromDropdown(By.xpath(".//*[contains(@id,'currencyListBox')]"), currency);
		return this;
	}

	/**
	 * edit Order Validity
	 * 
	 * @param validity
	 * @return
	 */
	public TradeOrderFormPage editOrderValidity(String validity) {
		waitForElementVisible(By.xpath(".//*[contains(@id,'validityListBox')]"), 10);

		selectFromDropdown(By.xpath(".//*[contains(@id,'validityListBox')]"), validity);
		return this;
	}

	public TradeOrderFormPage editExecutionDate(String date) {
		waitForElementVisible(By.xpath(".//*[contains(@id,'executionDateTextBox')]"), 10);

		sendKeysToElement(By.xpath(".//*[contains(@id,'executionDateTextBox')]"), date);
		return this;
	}

	public TradeOrderFormPage editFinalSettlementDate(String date) {
		By by = By.xpath(".//*[contains(@debugid,'finalSettlementDateRow')]//*[contains(@id,'expirationDateTextBox')]");
		waitForElementVisible(by, Settings.WAIT_SECONDS * 2);
		sendKeysToElement(by, date);
		return this;
	}

	/**
	 * edit Instrument currency and counter currency for instrument
	 * 
	 * @param currency
	 * @param counterCurrency
	 * @return
	 */
	public TradeOrderFormPage editInstrumentCurrencyAndCounterCurrency(String currency, String counterCurrency) {
		try {
			waitForElementVisible(By.id("gwt-debug-InstrumentFxSpotView-currencyListBox"), 10);
			selectFromDropdown(By.id("gwt-debug-InstrumentFxSpotView-currencyListBox"), currency);
		} catch (TimeoutException e) {
			int size = getSizeOfElements(By.id("gwt-debug-InstrumentOptionView-currencyListBox"));
			selectFromDropdown(By.xpath(
					"(.//*[@id='gwt-debug-InstrumentOptionView-currencyListBox'])[" + String.valueOf(size) + "]"),
					currency);
		}

		try {
			waitForElementVisible(By.id("gwt-debug-InstrumentFxSpotView-counterCurrencyListBox"), 10);
			selectFromDropdown(By.id("gwt-debug-InstrumentFxSpotView-counterCurrencyListBox"), counterCurrency);
		} catch (TimeoutException e) {
			selectFromDropdown(By.id("gwt-debug-InstrumentOptionView-counterCurrencyListBox"), counterCurrency);
		}

		return this;
	}

	/**
	 * edit Buy currency and buy amount
	 * 
	 * @param currency
	 * @param amount
	 * @return
	 */
	public TradeOrderFormPage editBuyCurrencyAndAmount(String currency, String amount) {
		int size1 = this.getSizeOfElements(By.xpath(".//*[contains(@id,'buyCurrencyListBox')]"));
		int size2 = this.getSizeOfElements(By.xpath(".//*[contains(@id,'buyAmountTextBox')]"));

		selectFromDropdown(By.xpath("(.//*[contains(@id,'buyCurrencyListBox')])[" + String.valueOf(size1) + "]"),
				currency);
		sendKeysToElement(By.xpath("(.//*[contains(@id,'buyAmountTextBox')])[" + String.valueOf(size2) + "]"), amount);
		return this;
	}

	/**
	 * Edit buy currency and buy amount for FX forward
	 * 
	 * @param currency
	 * @param amount
	 * @return
	 */
	public TradeOrderFormPage editBuyCurrencyAndAmountForFXForward(String currency, String amount) {
		int size1 = this.getSizeOfElements(By.xpath(".//*[contains(@id,'buyCurrencyListBoxSingleRow')]"));
		int size2 = this.getSizeOfElements(By.xpath(".//*[contains(@id,'buyAmountTextBoxSingleRow')]"));

		selectFromDropdown(
				By.xpath("(.//*[contains(@id,'buyCurrencyListBoxSingleRow')])[" + String.valueOf(size1) + "]"),
				currency);
		sendKeysToElement(By.xpath("(.//*[contains(@id,'buyAmountTextBoxSingleRow')])[" + String.valueOf(size2) + "]"),
				amount);
		return this;
	}

	/**
	 * edit Sell currency and sell amount
	 * 
	 * @param currency
	 * @param amount
	 * @return
	 */
	public TradeOrderFormPage editSellCurrencyAndAmount(String currency, String amount) {
		int size1 = this.getSizeOfElements(By.xpath(".//*[contains(@id,'sellCurrencyListBox')]"));
		int size2 = this.getSizeOfElements(By.xpath(".//*[contains(@id,'sellAmountTextBox')]"));

		selectFromDropdown(By.xpath("(.//*[contains(@id,'sellCurrencyListBox')])[" + String.valueOf(size1) + "]"),
				currency);
		sendKeysToElement(By.xpath("(.//*[contains(@id,'sellAmountTextBox')])[" + String.valueOf(size2) + "]"), amount);
		return this;
	}

	public TradeOrderFormPage editPositionType(String type) {
		selectFromDropdown(By.xpath(".//*[contains(@id,'positionTypeListBox')]"), type);
		try {
			wait(1);
		} catch (InterruptedException e) {

		}
		return this;
	}

	public TradeOrderFormPage editDepositType(String type) {
		selectFromDropdown(By.xpath(".//*[contains(@id,'depositTypeListBox')]"), type);
		try {
			wait(1);
		} catch (InterruptedException e) {

		}
		return this;
	}

	public TradeOrderFormPage editInterestRate(String rate) {
		sendKeysToElement(By.xpath(".//input[contains(@id,'interestRate')]"), rate);
		// try {
		// wait(1);
		// } catch (InterruptedException e) {
		//
		// }
		return this;
	}

	public TradeOrderFormPage editStartDate(String date) {
		sendKeysToElement(By.id("gwt-debug-InstrumentMoneyMarketView-startDate"), date);
		return this;
	}

	public TradeOrderFormPage editMaturityDate(String date) {
		sendKeysToElement(By.id("gwt-debug-InstrumentMoneyMarketView-maturityDate"), date);
		try {
			wait(1);
		} catch (InterruptedException e) {

		}
		return this;
	}

	public TradeOrderFormPage editAssetClass(String assetClass) {
		this.selectFromDropdown(By.xpath(".//*[contains(@id,'assetClassListBox')]"), assetClass);
		return this;
	}

	public TradeOrderFormPage editTransactionName(String name) {
		this.sendKeysToElement(By.xpath(".//*[contains(@id,'nameTextBox')]"), name);
		return this;
	}

	public TradeOrderFormPage editTransactionSymbol(String symbol) {
		this.sendKeysToElement(By.xpath(".//*[contains(@id,'symbolTextBox')]"), symbol);
		return this;
	}

	public TradeOrderFormPage editForwardType(String forwardType) {
		this.selectFromDropdown(By.xpath(".//*[contains(@id,'forwardTypeListBox')]"), forwardType);
		return this;
	}

	public TradeOrderFormPage editForwardExchangeRate(String rate, String counterCurrency, String currency) {
		this.sendKeysToElement(By.xpath(".//*[contains(@id,'forwardExchangeRateTextBox')]"), rate);
		this.selectFromDropdown(By.xpath(".//*[contains(@id,'counterCurrencyListBox')]"), counterCurrency);
		this.selectFromDropdown(By.xpath(".//*[contains(@id,'currencyListBox')]"), currency);
		return this;
	}

	public TradeOrderFormPage editSettlementCurrency(String settlementCurrency) {
		this.selectFromDropdown(By.xpath(".//*[contains(@id,'settlementCurrencyListBox')]"), settlementCurrency);
		return this;
	}

	/**
	 * click the save button in Trade Order Form page
	 * 
	 * @return
	 * 
	 * @return {@link TradeOrderFormPage}
	 * @throws InterruptedException
	 */
	public HistoryPage clickSaveButtonForTrade() throws Exception {

		int size = this.getSizeOfElements(By.xpath(".//*[contains(@id,'saveButton')]"));
		waitForElementVisible(By.xpath("(.//*[contains(@id,'saveButton')])[" + String.valueOf(size) + "]"), 10);

		clickElement(By.xpath("(.//*[contains(@id,'saveButton')])[" + String.valueOf(size) + "]"));

		clickOkButtonIfVisible();

		clickYesButtonIfVisible();

		clickNoButtonIfVisible();

		clickOkButtonIfVisible();

		return new HistoryPage(webDriver);
	}

	public TradeOrderFormPage clickSaveButtonForCheckingFields() {

		waitForElementVisible(By.xpath(".//*[contains(@id,'saveButton')]"), 10);

		clickElement(By.xpath(".//*[contains(@id,'saveButton')]"));

		return this;
	}

	/**
	 * click save button in trade order form
	 * 
	 * @return {@link TradeOrderFormPage}
	 */
	public TradeOrderFormPage clickSaveButtonForReconciliation() {

		waitForElementVisible(By.xpath(".//*[contains(@id,'saveButton')]"), 10);

		clickElement(By.xpath(".//*[contains(@id,'saveButton')]"));

		clickYesButtonIfVisible();
		clickNoButtonIfVisible();
		clickOkButtonIfVisible();
		return this;
	}

	/**
	 * Click cancel button in the trade order form
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public HistoryPage clickCancelButton() throws Exception {

		clickElement(By.xpath(".//*[contains(@id,'cancelButton')]"));

		return new HistoryPage(webDriver);
	}

	/**
	 * select instrument and proceed to trade order form
	 * 
	 * @param instrument
	 * @return
	 */
	public TradeOrderFormPage selectInstrumentAndProceed(String instrument, String subtype) {
		waitForElementVisible(
				By.xpath(".//*[@id='gwt-debug-CustomDialog-enhancedPanel']//select[@class='formTextBox']"), 10);
		selectFromDropdown(
				By.xpath("(//*[@id='gwt-debug-CustomDialog-enhancedPanel']//select[@class='formTextBox'])[1]"),
				instrument);

		if (isElementVisible(
				By.xpath("(//*[@id='gwt-debug-CustomDialog-enhancedPanel']//select[@class='formTextBox'])[2]"))) {
			selectFromDropdown(
					By.xpath("(//*[@id='gwt-debug-CustomDialog-enhancedPanel']//select[@class='formTextBox'])[2]"),
					subtype);
		}

		clickElement(By.xpath(".//*[.='Proceed']"));
		try {
			waitForElementVisible(By.xpath(".//*[contains(text(),'Trade Order Form')]"), 10);
		} catch (TimeoutException e) {
			clickElementByKeyboard(By.xpath(".//*[.='Proceed']"));
		}
		return this;
	}

	public TradeOrderFormPage addInstrument(String investment) throws InterruptedException {

		clickElement(By.xpath(".//*[contains(@id,'assetFind')]"));

		InvestmentsPage invest = new InvestmentsPage(webDriver);

		invest.simpleSearchByName(investment).selectInvestmentByName(investment);

		assertTrue(pageContainsStr(investment));
		return this;
	}

	public TradeOrderFormPage clickCalculateCommissionAmountButton() {
		clickElement(By.xpath(".//*[contains(@id,'calculateCommissionButton')]"));
		return this;
	}

	public TradeOrderFormPage editInterestPeriodFrom(String date) {
		sendKeysToElement(By.xpath(".//input[contains(@id,'interestPeriodFromDate')]"), date);
		return this;
	}

	public TradeOrderFormPage editInterestPeriodTo(String date) {
		sendKeysToElement(By.xpath(".//input[contains(@id,'interestPeriodToDate')]"), date);
		return this;
	}

	public TradeOrderFormPage clickCalculateInterestAmountButton() {
		clickElement(By.xpath(".//*[contains(@id,'calculateInterestAmount')]"));
		return this;
	}

	public TradeOrderFormPage clickFindSettlementDateButton() {
		clickElement(By.id("gwt-debug-MyOrderEditPopupView-findButton"));
		waitForElementVisible(By.id("gwt-debug-SettlementDatePopupView-exchangeListBox"), 30);
		return this;
	}

	public TradeOrderFormPage clickCancelButtonForSettlementDateWindow() {
		clickElement(By.id("gwt-debug-SettlementDatePopupView-cancelButton"));
		return this;
	}

	public TradeOrderFormPage editExchangeForSettlementDate(String exchange) {
		selectFromDropdown(By.id("gwt-debug-SettlementDatePopupView-exchangeListBox"), exchange);
		waitForElementVisible(By.xpath(".//*[@id='gwt-debug-SettlementDatePopupView-settlementDateLabel' and .!='NA']"),
				30);
		return this;
	}

	public TradeOrderFormPage editExecutionDateInSettlementDateWindow(String executionDate) {
		sendKeysToElement(By.id("gwt-debug-SettlementDatePopupView-executionDateTextBox"), executionDate);
		return this;
	}

	// Click "+" button next to Underlying
	public DetailEditPage clickAddUnderlyingButton() {
		clickElement(By.id("gwt-debug-InstrumentOptionView-underlyingButton"));
		return new DetailEditPage(webDriver, TradeOrderFormPage.class);
	}

	/**
	 * select option type for option txn
	 * 
	 * @param optionType
	 * @return
	 */
	public TradeOrderFormPage editOptionType(String optionType) {
		selectFromDropdown(By.xpath(".//select[contains(@id,'optionTypeListBox')]"), optionType);
		return this;
	}

	/**
	 * select option style for option txn
	 * 
	 * @param optionStyle
	 * @return
	 */
	public TradeOrderFormPage editOptionStyle(String optionStyle) {
		log(optionStyle);
		selectFromDropdown(By.xpath(".//select[contains(@id,'optionStyleListBox')]"), optionStyle);
		return this;
	}

	/**
	 * edit strike for equity option txn
	 * 
	 * @param strike
	 * @return
	 */
	public TradeOrderFormPage editStrike(String strike) {
		sendKeysToElement(By.xpath(".//input[contains(@id,'strikeTextBox')]"), strike);
		return this;
	}

	/**
	 * edit strike for fx option txn
	 * 
	 * @param currency
	 * @param counterCurrency
	 * @return
	 */
	public TradeOrderFormPage editStrikeCurrency(String currency, String counterCurrency) {
		selectFromDropdown(By.id("gwt-debug-InstrumentOptionView-counterCurrencyFXListBox"), counterCurrency);
		selectFromDropdown(By.id("gwt-debug-InstrumentOptionView-currencyFXListBox"), currency);
		return this;
	}

	/**
	 * edit expiration date for option txn
	 * 
	 * @param expirationDate
	 * @return
	 */
	public TradeOrderFormPage editExpirationDate(String expirationDate) {
		sendKeysToElement(By.xpath(".//input[contains(@id,'expirationDateTextBox')]"), expirationDate);
		return this;
	}

	public TradeOrderFormPage editValueDate(String valueDate) {
		this.sendKeysToElement(By.xpath(".//*[contains(@id,'valueDateTextBox')]"), valueDate);
		return this;
	}

	/**
	 * edit exchange for txn
	 * 
	 * @param exchange
	 * @return
	 */
	public TradeOrderFormPage editExchange(String exchange) {
		selectFromDropdown(By.xpath(".//select[contains(@id,'exchangeListBox')]"), exchange);

		return this;
	}

	public TradeOrderFormPage editMarketCut(String marketCut) {
		selectFromDropdown(By.id("gwt-debug-InstrumentOptionView-marketCutLocationListBox"), marketCut);
		return this;
	}

	public TradeOrderFormPage editIsin(String isin) {
		sendKeysToElement(By.xpath(".//input[contains(@id,'isinTextBox')]"), isin);
		return this;
	}

	/**
	 * add related reference
	 * 
	 * @param reference
	 * @return
	 */
	public TradeOrderFormPage addRelatedReference(String reference) {

		clickElement(By.id("gwt-debug-RelatedReferenceWidget-addRelatedRef"));
		int size = getSizeOfElements(By.xpath("//*[@id='gwt-debug-RelatedReferenceWidget-relatedRefPanel']//input"));

		sendKeysToElement(By.xpath(
				"(//*[@id='gwt-debug-RelatedReferenceWidget-relatedRefPanel']//input)[" + String.valueOf(size) + "]"),
				reference);
		// to focus on other elements
		clickElement(By.id("gwt-debug-MyOrderEditPopupView-commentsTextArea"));

		return this;
	}

	public TradeOrderFormPage deleteAllRelatedReference() {
		int size = getSizeOfElements(By.xpath(
				"//*[@id='gwt-debug-RelatedReferenceWidget-relatedRefPanel']//button[contains(@class,'remove')]"));
		for (int i = size; i > 0; i--) {
			clickElement(By
					.xpath("(//*[@id='gwt-debug-RelatedReferenceWidget-relatedRefPanel']//button[contains(@class,'remove')])["
							+ String.valueOf(i) + "]"));
		}

		return this;
	}

	public TradeOrderFormPage clickInfoButtonForRelatedReference(String relatedReference) {
		clickElement(By.xpath("//*[@id='gwt-debug-RelatedReferenceWidget-relatedRefPanel']//td[*[*[contains(text(),'"
				+ relatedReference + "')]]]//following-sibling::td//button[contains(@class,'refreshBtn')]"));
		return this;
	}

}
