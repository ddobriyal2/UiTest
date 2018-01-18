package org.sly.uitest.pageobjects.companyadmin;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.settings.Settings;

/**
 * This class represents the Custom Asset page, which can be navigated by
 * clicking 'Company Settings' -> 'Custom Asset', including the Edit Custom
 * Asset page
 * 
 * URL: "http://192.168.1.104:8080/SlyAWS/?locale=en#customAssetList"
 * 
 * @author Lynne Huang
 * @date : 26 Aug, 2015
 * @company Prive Financial
 * 
 */
public class CustomAssetPage extends AbstractPage {

	/**
	 * @param webDriver
	 */
	public CustomAssetPage(WebDriver webDriver) {
		super();
		this.webDriver = webDriver;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);
		try {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.id("gwt-debug-TabCustomAssetView-customAssetTablePanel")));

		} catch (Exception ex) {
			// wait.until(ExpectedConditions.visibilityOfElementLocated(By
			// .id("gwt-debug-MyMainMaterialView-mainPanel")));

		}
	}

	/**
	 * On the Custom Asset page, click the CREATE ASSET button
	 * 
	 * @return {@link CustomAssetPage}
	 * @throws InterruptedException
	 */
	public CustomAssetPage clickCreateAssetButton() throws InterruptedException {
		this.waitForElementVisible(By.id("gwt-debug-TabCustomAssetView-createCustomAssetButton"),
				Settings.WAIT_SECONDS);
		clickElement(By.id("gwt-debug-TabCustomAssetView-createCustomAssetButton"));

		return this;
	}

	public CustomAssetPage clickProceedButton() {
		clickElement(By.xpath(".//button[.='Proceed']"));
		return this;
	}

	/**
	 * On the Custom Asset page, click the pencil icon to edit the custom asset
	 * with the given name
	 * 
	 * @param name
	 *            the custom asset name
	 * 
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage editAssetByName(String name) {
		scrollToTop();
		clickElement(By.xpath("//td[.='" + name
				+ "']/following-sibling::td[//button]//button[@id='gwt-debug-TabCustomAssetView-editButton']"));

		return this;
	}

	/**
	 * On the Custom Asset page, click the red-white minus icon to delete the
	 * custom asset with the given name
	 * 
	 * @param name
	 *            the custom asset name
	 * 
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage deleteAssetByName(String name) {

		// waitForElementVisible(
		// By.id("gwt-debug-TabCustomAssetView-customAssetName"), 30);
		//
		// int page = getPagesOfElements(By
		// .id("gwt-debug-TabCustomAssetView-customAssetName"));
		//
		// for (int i = 0; i < page; i++) {
		// try {
		// webDriver.findElement(By.xpath("//td[.='" + name + "']"));
		// break;
		// } catch (Exception e) {
		// clickElement(By.id("gwt-debug-PagerToolWidget-nextPageImg"));
		// // TODO: handle exception
		// }
		// }

		this.simpleSearch(name);
		clickElement(By.xpath("//td[.='" + name
				+ "']/following-sibling::td[//button]//button[@id='gwt-debug-TabCustomAssetView-deleteButton']"));

		clickYesButtonIfVisible();

		clickYesButtonIfVisible();
		return this;
	}

	/**
	 * On the Custom Asset page, after click the CREATE ASSET button or the edit
	 * icon, on the pop-up Edit Custom Asset page, edit the asset type
	 * 
	 * @param type
	 * 
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage editAssetType(String type) {

		// waitForElementVisible(
		// By.xpath("//td[.='Type']/following-sibling::td[1]/select"), 30);

		// waitForElementVisible(
		// By.id("gwt-debug-EditCustomAssetView-customAssetType"), 30);

		waitForElementVisible(By.id("gwt-debug-TabCustomAssetView-customPopup-assetTypeList"), 30);
		selectFromDropdown(By.id("gwt-debug-TabCustomAssetView-customPopup-assetTypeList"), type);

		return this;
	}

	/**
	 * On the Custom Asset page, after click the CREATE ASSET button or the edit
	 * icon, on the pop-up Edit Custom Asset page, edit the asset name
	 * 
	 * @param name
	 * 
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage editAssetName(String name) {

		// sendKeysToElement(
		// By.xpath("//td[.='Name']/following-sibling::td//input"), name);

		/*
		 * sendKeysToElement(
		 * By.id("gwt-debug-EditCustomAssetPresenterWidgetView-customAssetName"
		 * ), name);
		 */
		scrollToTop();
		sendKeysToElement(By.id("gwt-debug-EditCustomAssetView-customAssetName"), name);

		return this;
	}

	/**
	 * On the Custom Asset page, after click the CREATE ASSET button or the edit
	 * icon, on the pop-up Edit Custom Asset page, edit the asset currency
	 * 
	 * @param currency
	 * 
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage editAssetCurrency(String currency) {

		// selectFromDropdown(
		// By.xpath("//td[.='Currency']/following-sibling::td//select"),
		// currency);

		/*
		 * selectFromDropdown( By.id(
		 * "gwt-debug-EditCustomAssetPresenterWidgetView-customAssetCurrency" ),
		 * currency);
		 */
		selectFromDropdown(By.id("gwt-debug-EditCustomAssetView-customAssetCurrency"), currency);

		return this;
	}

	/**
	 * On the Custom Asset page, after click the CREATE ASSET button or the edit
	 * icon, on the pop-up Edit Custom Asset page, edit the asset type
	 * 
	 * @param type
	 * 
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage editAssetDescription(String description) {

		// sendKeysToElement(
		// By.id("gwt-debug-EditCustomAssetPresenterWidgetView-customAssetDescription"),
		// description);

		/*
		 * sendKeysToElement( By.id(
		 * "gwt-debug-EditCustomAssetPresenterWidgetView-customAssetDescription"
		 * ), description);
		 */
		sendKeysToElement(By.id("gwt-debug-EditCustomAssetView-customAssetDescription"), description);

		return this;
	}

	/**
	 * On the Custom Asset page, after click the CREATE ASSET button or the edit
	 * icon, on the pop-up Edit Custom Asset page, edit the asset manager
	 * 
	 * @param manager
	 * 
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage editAssetManager(String manager) {

		/*
		 * selectFromDropdown( By.id(
		 * "gwt-debug-EditCustomAssetPresenterWidgetView-customAssetManager" ),
		 * manager);
		 */
		selectFromDropdown(By.id("gwt-debug-EditCustomAssetView-customAssetManager"), manager);

		return this;
	}

	/**
	 * On the Custom Asset page, after click the CREATE ASSET button or the edit
	 * icon, on the pop-up Edit Custom Asset page, edit the asset benchmark
	 * 
	 * @param benchmark
	 * 
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage editAssetBenchmark(String benchmark) {

		// selectFromDropdown(
		// By.id("gwt-debug-EditCustomAssetPresenterWidgetView-customAssetBenchmark"),
		// benchmark);

		/*
		 * selectFromDropdown( By.id(
		 * "gwt-debug-EditCustomAssetPresenterWidgetView-customAssetBenchmark"
		 * ), benchmark);
		 */
		selectFromDropdown(By.id("gwt-debug-EditCustomAssetView-customAssetBenchmark"), benchmark);

		return this;
	}

	/**
	 * On the Custom Asset page, after click the CREATE ASSET button or the edit
	 * icon, on the pop-up Edit Custom Asset page, edit the asset region
	 * 
	 * @param region
	 * 
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage editAssetRegion(String region) {

		// selectFromDropdown(
		// By.id("gwt-debug-EditCustomAssetPresenterWidgetView-customAssetRegion"),
		// region);

		selectFromDropdown(By.id("gwt-debug-EditCustomAssetView-customAssetRegion"), region);
		return this;
	}

	/**
	 * On the Custom Asset page, after click the CREATE ASSET button or the edit
	 * icon, on the pop-up Edit Custom Asset page, edit the asset class
	 * 
	 * @param class
	 * 
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage editAssetClass(String assetClass) {

		selectFromDropdown(By.id("gwt-debug-EditCustomAssetPresenterWidgetView-customAssetAssetClass"), assetClass);

		return this;
	}

	/**
	 * On the Custom Asset page, after click the CREATE ASSET button or the edit
	 * icon, on the pop-up Edit Custom Asset page, edit the asset sector
	 * 
	 * @param sector
	 * 
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage editAssetSector(String sector) {

		selectFromDropdown(By.id("gwt-debug-EditCustomAssetPresenterWidgetView-customAssetSector"), sector);

		return this;
	}

	/**
	 * On the Custom Asset page, after click the CREATE ASSET button or the edit
	 * icon, on the pop-up Edit Custom Asset page, edit the asset symbol
	 * 
	 * @param symbol
	 * 
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage editAssetSymbol(String symbol) {

		sendKeysToElement(By.id("gwt-debug-EditCustomAssetPresenterWidgetView-customAssetSymbol"), symbol);

		return this;

	}

	/**
	 * On the Custom Asset page, after click the CREATE ASSET button or the edit
	 * icon, on the pop-up Edit Custom Asset page, edit the asset time zone
	 * 
	 * @param timezone
	 * 
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage editAssetTimeZone(String timezone) {

		/*
		 * selectFromDropdown( By.id(
		 * "gwt-debug-EditCustomAssetPresenterWidgetView-customAssetTimezone" ),
		 * timezone);
		 */
		selectFromDropdown(By.id("gwt-debug-EditCustomAssetView-customAssetTimezone"), timezone);

		return this;
	}

	/**
	 * On the Custom Asset page, after click the CREATE ASSET button or the edit
	 * icon, on the pop-up Edit Custom Asset page, edit the asset exchange
	 * 
	 * @param exchange
	 * 
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage editAssetExchange(String exchange) {

		selectFromDropdown(By.id("gwt-debug-EditCustomAssetPresenterWidgetView-customAssetExchange"), exchange);

		return this;
	}

	/**
	 * On the Custom Asset page, after click the CREATE ASSET button or the edit
	 * icon, on the pop-up Edit Custom Asset page, edit the asset order multiple
	 * 
	 * @param order
	 * 
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage editAssetOrderMultiple(String order) {

		sendKeysToElement(By.id("gwt-debug-EditCustomAssetPresenterWidgetView-customAssetOrderMult"), order);

		return this;
	}

	/**
	 * On the Custom Asset page, after click the CREATE ASSET button or the edit
	 * icon, on the pop-up Edit Custom Asset page, edit the asset Bloomberg ID
	 * 
	 * @param BID
	 * 
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage editAssetBloombergID(String BID) {

		sendKeysToElement(By.id("gwt-debug-EditCustomAssetPresenterWidgetView-customAssetBbgID"), BID);

		return this;
	}

	/**
	 * On the Custom Asset page, after click the CREATE ASSET button or the edit
	 * icon, on the pop-up Edit Custom Asset page, edit the asset Composite
	 * Bloomberg ID
	 * 
	 * @param CBID
	 * 
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage editAssetCompositeBloombergID(String CBID) {

		sendKeysToElement(By.id("gwt-debug-EditCustomAssetPresenterWidgetView-customAssetCompositeBbgID"), CBID);

		return this;
	}

	/**
	 * On the Custom Asset page, after click the CREATE ASSET button or the edit
	 * icon, on the pop-up Edit Custom Asset page, edit the asset Risk Level
	 * 
	 * @param level
	 * 
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage editAssetRiskLevel(String level) {

		selectFromDropdown(By.xpath("//td[.='Risk Level']/following-sibling::td[1]/select"), level);

		return this;
	}

	/**
	 * On the Custom Asset page, after click the CREATE ASSET button or the edit
	 * icon, on the pop-up Edit Custom Asset page, edit the asset Reuters RIC
	 * 
	 * @param RIC
	 * 
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage editAssetReutersRIC(String RIC) {

		sendKeysToElement(By.id("gwt-debug-EditCustomAssetPresenterWidgetView-customAssetRic"), RIC);

		return this;
	}

	/**
	 * On the Custom Asset page, after click the CREATE ASSET button or the edit
	 * icon, on the pop-up Edit Custom Asset page, edit the asset ISIN
	 * 
	 * @param isin
	 * 
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage editAssetISIN(String isin) {

		/*
		 * sendKeysToElement(
		 * By.id("gwt-debug-EditCustomAssetPresenterWidgetView-customAssetIsin"
		 * ), isin);
		 */
		sendKeysToElement(By.id("gwt-debug-EditCustomAssetView-customAssetIsin"), isin);

		return this;
	}

	/**
	 * On the Custom Asset page, after click the CREATE ASSET button or the edit
	 * icon, on the pop-up Edit Custom Asset page, edit the asset sedol
	 * 
	 * @param sedol
	 * 
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage editAssetSedol(String sedol) {

		sendKeysToElement(By.id("gwt-debug-EditCustomAssetPresenterWidgetView-customAssetSedol"), sedol);

		return this;
	}

	/**
	 * On the Custom Asset page, after click the CREATE ASSET button or the edit
	 * icon, on the pop-up Edit Custom Asset page, edit the asset Citi Code
	 * 
	 * @param citicode
	 * 
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage editAssetCiticode(String citicode) {

		sendKeysToElement(By.id("gwt-debug-EditCustomAssetPresenterWidgetView-customAssetCitiCode"), citicode);

		return this;
	}

	/**
	 * On the Custom Asset page, after click the CREATE ASSET button or the edit
	 * icon, on the pop-up Edit Custom Asset page, edit the asset notional
	 * 
	 * @param notional
	 * 
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage editAssetNotional(String notional) {

		sendKeysToElement(By.id("gwt-debug-EditCustomAssetPresenterWidgetView-customAssetNotional"), notional);

		return this;
	}

	/**
	 * On the Custom Asset page, after click the CREATE ASSET button or the edit
	 * icon, on the pop-up Edit Custom Asset page, edit the asset fixed rate
	 * 
	 * @param rate
	 * 
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage editAssetFixedRate(String rate) {

		sendKeysToElement(By.id("gwt-debug-EditCustomAssetView-customAssetFixedRate"), rate);

		return this;
	}

	/**
	 * On the Custom Asset page, after click the CREATE ASSET button or the edit
	 * icon, on the pop-up Edit Custom Asset page, edit the asset value
	 * 
	 * @param value
	 * 
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage editAssetPurchaseValue(String value) {

		sendKeysToElement(By.id("gwt-debug-EditCustomAssetPresenterWidgetView-customAssetPurchaseValue"), value);

		return this;
	}

	/**
	 * On the Custom Asset page, after click the CREATE ASSET button or the edit
	 * icon, on the pop-up Edit Custom Asset page, edit the asset maturity date
	 * 
	 * @param date
	 * 
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage editAssetMaturityDate(String date) {

		sendKeysToElement(By.id("gwt-debug-EditCustomAssetView-customAssetMaturityDate"), date);

		return this;
	}

	/**
	 * On the Custom Asset page, after click the CREATE ASSET button or the edit
	 * icon, on the pop-up Edit Custom Asset page, edit the asset underlying by
	 * entering symbol
	 * 
	 * @param country
	 * @param symbol
	 * 
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage editAssetUnderyingByEnterSymbol(String country, String symbol) {

		clickElement(By.id("gwt-debug-EditCustomAssetPresenterWidgetView-customAssetUnderlyingsSelector"));

		clickElement(By.xpath("//div[@class='TabLayoutPanelTabHighlight' and .='Enter Symbol']"));

		selectFromDropdown(By.id("gwt-debug-TickerAddDialog-addTickerExchangeList"), country);

		sendKeysToElement(By.id("gwt-debug-TickerAddDialog-exchangeTickerList"), symbol);

		clickElement(By.id("gwt-debug-TickerAddDialog-tickerAddButton"));

		return this;
	}

	/**
	 * On the Custom Asset page, after click the CREATE ASSET button or the edit
	 * icon, on the pop-up Edit Custom Asset page, edit the asset type by
	 * searching
	 * 
	 * @param country
	 * @param symbol
	 * @param certainSymbol
	 * 
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage editAssetUnderylingBySearch(String country, String symbol, String certainSymbol) {

		clickElement(By.id("gwt-debug-EditCustomAssetPresenterWidgetView-customAssetUnderlyingsSelector"));

		clickElement(By.xpath("//div[@class='TabLayoutPanelTabHighlight' and .='Search']"));

		selectFromDropdown(By.id("gwt-debug-TickerAddDialog-addTickerExchangeSearchList"), country);

		sendKeysToElement(By.id("gwt-debug-TickerAddDialog-searchTextField"), symbol);

		clickElement(By.id("gwt-debug-TickerAddDialog-tickerSearchButton"));

		clickElement(By.id("gwt-debug-TickerAddDialog-tickerAddButton"));

		if (certainSymbol.equals("")) {
			clickElement(By
					.xpath(".//*[@id='gwt-debug-TickerAddDialog-searchResultsPanel']/table/tbody/tr[2]/td[1]//label"));
		} else {
			clickElement(By.xpath("//td[.='" + certainSymbol + "']/preceding-sibling::td[1]//input"));
		}

		return this;
	}

	/**
	 * On the Custom Asset page, after click the CREATE ASSET button or the edit
	 * icon, on the pop-up Edit Custom Asset page, edit the asset option type
	 * 
	 * @param type
	 * 
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage editAssetOptionType(String type) {

		selectFromDropdown(By.id("gwt-debug-EditCustomAssetPresenterWidgetView-customAssetOptionType"), type);

		return this;
	}

	/**
	 * On the Custom Asset page, after click the CREATE ASSET button or the edit
	 * icon, on the pop-up Edit Custom Asset page, edit the asset strike
	 * 
	 * @param strike
	 * 
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage editAssetStrike(String strike) {

		sendKeysToElement(By.id("gwt-debug-EditCustomAssetPresenterWidgetView-customAssetStrike"), strike);

		return this;
	}

	/**
	 * On the Custom Asset page, after click the CREATE ASSET button or the edit
	 * icon, on the pop-up Edit Custom Asset page, edit the asset expiration
	 * date
	 * 
	 * @param date
	 * 
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage editAssetExpirationDate(String date) {

		sendKeysToElement(By.id("gwt-debug-EditCustomAssetPresenterWidgetView-customAssetExpDate"), date);

		return this;
	}

	/**
	 * On the Custom Asset page, after click the CREATE ASSET button or the edit
	 * icon, on the pop-up Edit Custom Asset page, edit the asset illiquid
	 * 
	 * @param illiquid
	 * 
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage editAssetIlliquid(Boolean checked) {

		WebElement we = webDriver
				.findElement(By.id("gwt-debug-EditCustomAssetPresenterWidgetView-customAssetIlliquid-input"));

		setCheckboxStatus2(we, checked);

		return this;
	}

	/**
	 * On the Custom Asset page, after click the CREATE ASSET button or the edit
	 * icon, on the pop-up Edit Custom Asset page, edit the asset main execution
	 * platform
	 * 
	 * @param platform
	 * 
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage editAssetMainExecutionPlatform(String platform) {

		selectFromDropdown(By.id("gwt-debug-EditCustomAssetPresenterWidgetView-customAssetMainExecutionPlatform"),
				platform);

		return this;
	}

	/**
	 * On the Custom Asset page, after click the CREATE ASSET button or the edit
	 * icon, on the pop-up Edit Custom Asset page, edit the tenor value
	 * 
	 * @param tenorPeriod
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage editAssetTenor(int tenorPeriod) {

		sendKeysToElement(By.id("gwt-debug-EditCustomAssetView-customAssetFixedRateTenorPeriodValue"),
				String.valueOf(tenorPeriod));
		return this;
	}

	/**
	 * On the Custom Asset page, after click the CREATE ASSET button or the edit
	 * icon, on the pop-up Edit Custom Asset page, edit the asset base rate
	 * 
	 * @param rate
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage editAssetBaseRate(int rate) {
		sendKeysToElement(By.id("gwt-debug-EditCustomAssetView-customAssetFixedRateBaseRateValue"),
				String.valueOf(rate));
		// addElementAttributeByLocator(
		// By.id("gwt-debug-EditCustomAssetView-customAssetFixedRateBaseRateValue"),
		// "originalvalue", String.valueOf((double)rate/100));

		return this;
	}

	/**
	 * On the Custom Asset page, after click the CREATE ASSET button or the edit
	 * icon, on the pop-up Edit Custom Asset page, edit the asset execute
	 * platforms
	 * 
	 * @param platforms
	 * 
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage editAddExecutionPlatform(String... platforms) {

		for (String platform : platforms) {
			selectFromDropdown(By.id("gwt-debug-PairedListBoxSelector-sourceList"), platform);
		}

		clickElement(By.id("gwt-debug-PairedListBoxSelector-addImg"));

		return this;

	}

	/**
	 * 
	 * */
	public CustomAssetPage editRemoveExecutionPlatform(String... platforms) {

		for (String platform : platforms) {
			selectFromDropdown(By.id("gwt-debug-PairedListBoxSelector-sourceList"), platform);
		}

		clickElement(By.id("gwt-debug-PairedListBoxSelector-removeImg"));

		return this;

	}

	/**
	 * On the Custom Asset page, after click the CREATE ASSET button or the edit
	 * icon, on the pop-up Edit Custom Asset page, edit the asset
	 * currency/counter currency
	 * 
	 * @param currency
	 * @param counterCurrency
	 * 
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage editAssetCurrencyAndCounterCurrency(String currency, String counterCurrency) {

		selectFromDropdown(By.id("gwt-debug-EditCustomAssetView-fxForwardCounterCurrencyListBox"), currency);

		selectFromDropdown(By.id("gwt-debug-EditCustomAssetView-fxForwardCurrencyListBox"), counterCurrency);

		// selectFromDropdown(
		// By.xpath("(//td[contains(text(),'Currency/Counter
		// Currency')]/following-sibling::td//select)[1]"),
		// currency);
		//
		// selectFromDropdown(
		// By.xpath("(//td[contains(text(),'Currency/Counter
		// Currency')]/following-sibling::td//select)[2]"),
		// counterCurrency);

		return this;
	}

	/**
	 * On the Custom Asset page, after click the CREATE ASSET button or the edit
	 * icon, on the pop-up Edit Custom Asset page, edit the asset forward
	 * exchange rage
	 * 
	 * @param rate
	 * 
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage editAssetForwardExchangeRate(String rate) {

		sendKeysToElement(By.id("gwt-debug-EditCustomAssetView-fxForwardExchangeRateTextBox"), rate);

		return this;
	}

	/**
	 * For FX Forward, this is to select forward type
	 * 
	 * @param type
	 * @return
	 */
	public CustomAssetPage editForwardType(String type) {
		WebElement elem = webDriver.findElement(By.id("gwt-debug-EditCustomAssetView-forwardTypeListBox"));
		selectFromDropdown(elem, type);
		return this;
	}

	/**
	 * For FX Forward, this is to edit Settlement currency
	 * 
	 * @param method
	 * @return
	 */
	public CustomAssetPage editSettlementCurrency(String currency) {
		WebElement elem = webDriver.findElement(By.id("gwt-debug-EditCustomAssetView-settlementCurrencyListBox"));
		selectFromDropdown(elem, currency);
		return this;
	}

	/**
	 * For FX Forward, this is to edit Value Date
	 * 
	 * @param date
	 * @return
	 */
	public CustomAssetPage editValueDate(String date) {
		sendKeysToElement(By.id("gwt-debug-EditCustomAssetView-valueDateTextBox"), date);
		return this;
	}

	/**
	 * On the Custom Asset page, after click the CREATE ASSET button or the edit
	 * icon, on the pop-up Edit Custom Asset page, click the SAVE button
	 * 
	 * 
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage clickSaveButton() {

		clickElement(By.id("gwt-debug-EditCustomAssetPopupView-saveBtn"));

		return this;
	}

	/**
	 * On the Custom Asset page, after click the CREATE ASSET button or the edit
	 * icon, on the pop-up Edit Custom Asset page, click the CANCEL button
	 * 
	 * 
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage clickCancelButton() {

		clickElement(By.id("gwt-debug-EditCustomAssetPopupView-cancelBtn"));

		return this;
	}

	/**
	 * On the Custom Asset page, after click the CREATE ASSET button or the edit
	 * icon, on the pop-up Edit Custom Asset page, switch to the History Data
	 * tab
	 * 
	 * 
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage goToHistoryData() {

		clickElement(By.xpath("//div[@class='gwt-HTML' and .='History Data']"));

		return this;

	}

	/**
	 * On the Custom Asset page, after click the CREATE ASSET button or the edit
	 * icon, on the pop-up Edit Custom Asset pages and switch to the History
	 * Data tab, check the checkbox of the Perpetuate Value
	 * 
	 * @param checked
	 * 
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage editPerpetuateValue(Boolean checked) {

		WebElement we = webDriver
				.findElement(By.id("gwt-debug-EditCustomAssetHistoryDataView-tickerPerpetuateValue-input"));

		setCheckboxStatus2(we, checked);

		return this;

	}

	/**
	 * On the Custom Asset page, after click the CREATE ASSET button or the edit
	 * icon, on the pop-up Edit Custom Asset pages and switch to the History
	 * Data tab, edit the historical data
	 * 
	 * @param date
	 * @param value
	 * 
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage editHistoricalData(String date, String value) {

		String input = date + "," + value;

		sendKeysToElement(By.id("gwt-debug-EditCustomAssetHistoryDataView-tickerHistData"), input);

		return this;
	}

	/**
	 * On the Custom Asset page, after click the CREATE ASSET button or the edit
	 * icon, on the pop-up Edit Custom Asset pages and switch to the History
	 * Data tab, edit the divident event
	 * 
	 * @param date
	 * @param value
	 * 
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage editDividentEvent(String date, String value) {

		String input = date + "," + value;

		sendKeysToElement(By.id("gwt-debug-EditCustomAssetHistoryDataView-tickerDividendEvent"), input);

		return this;
	}

	/**
	 * On the Custom Asset page, after click the CREATE ASSET button or the edit
	 * icon, on the pop-up Edit Custom Asset pages and switch to the History
	 * Data tab, edit the split event
	 * 
	 * @param date
	 * @param value
	 * 
	 * @return {@link CustomAssetPage}
	 */
	public CustomAssetPage editSplitEvent(String date, String value) {

		String input = date + "," + value;

		sendKeysToElement(By.id("gwt-debug-EditCustomAssetHistoryDataView-tickerSplitEvent"), input);

		return this;
	}

	/**
	 * Do simple search in Custom Asset page
	 * 
	 * @param searchString
	 * @return
	 */
	public CustomAssetPage simpleSearch(String searchString) {
		sendKeysToElement(By.id("gwt-debug-TabCustomAssetView-searchBox"), searchString);
		clickElement(By.id("gwt-debug-TabCustomAssetView-searchBtn"));
		return this;
	}
}
