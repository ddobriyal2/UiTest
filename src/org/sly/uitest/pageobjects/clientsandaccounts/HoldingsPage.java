package org.sly.uitest.pageobjects.clientsandaccounts;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.pageobjects.investments.InvestmentsPage;
import org.sly.uitest.settings.Settings;

/**
 * 
 * This class represents the Detail Page (tab) of an account or a client, which
 * can be navigated by clicking 'Clients' -> 'Account Overview' -> choose any
 * account -> 'Holdings (tab)' or 'Build' -> 'Model Portfolio' -> choose any
 * model portfolio -> 'Holdings (tab)'.
 * 
 * URL:
 * "http://192.168.1.104:8080/SlyAWS/?locale=en#portfolioOverviewHoldings;investorAccountKey=12292;valueType=2"
 * or
 * "http://192.168.1.104:8080/SlyAWS/?locale=en#portfolioOverviewHoldings;investorAccountKey=12452;valueType=1"
 * 
 * @author Lynne Huang
 * @date : 13 Aug, 2015
 * @company Prive Financial f
 */
public class HoldingsPage extends AbstractPage {

	/**
	 * @param webDriver
	 */
	public HoldingsPage(WebDriver webDriver) {

		super();
		this.webDriver = webDriver;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gwt-debug-MyMainMaterialView-mainPanel")));

		} catch (Exception ex) {
			// wait.until(ExpectedConditions.visibilityOfElementLocated(By
			// .id("gwt-debug-MyMainMaterialView-mainPanel")));

		}

		// assertTrue(pageContainsStr("Reallocate"));
	}

	/**
	 * On the Holdings page, open the investment with the given name in the
	 * investment list to check the information of this investment
	 * 
	 * @name the name of the investment
	 * 
	 * @return {@link HoldingsPage}
	 * @throws InterruptedException
	 */
	public HoldingsPage openInvestmentByName(String name) throws InterruptedException {

		clickElement(By.xpath("//div[.='" + name + "']"));
		assertTrue(pageContainsStr("Objective"));
		assertTrue(pageContainsStr("Historical Performance"));
		assertTrue(pageContainsStr("Risk Analysis"));

		return this;
	}

	/**
	 * Click the close icon on the top right of the pop-up page to close the
	 * current opened investment information page
	 * 
	 * @return {@link HoldingsPage}
	 */
	public HoldingsPage closeCurrentOpenedInvestment() {

		clickElement(By.id("gwt-debug-ManagerDisplayDialogView-closeAnchor"));

		return this;
	}

	/**
	 * On the Holdings page, click the TRADE button to trade the investments
	 * 
	 * @return {@link HoldingsPage}
	 */
	public HoldingsPage clickTradeButton() {

		clickElement(By.id("gwt-debug-PortfolioAllocationView-tradeBtn"));

		return this;
	}

	/**
	 * On the Holdings page, click the REALLOCATE button to reallocate the
	 * investments
	 * 
	 * @return {@link HoldingsPage}
	 * @throws InterruptedException
	 */
	public HoldingsPage clickReallocateButton() throws InterruptedException {

		this.waitForElementVisible(By.id("gwt-debug-PortfolioAllocationView-reallocateBtn"), Settings.WAIT_SECONDS * 2);
		clickElement(By.id("gwt-debug-PortfolioAllocationView-reallocateBtn"));

		// ensure buttons shows up
		try {
			waitForElementVisible(By.id("gwt-debug-PortfolioRebalanceView-addInvestmentBtn"), 30);
		} catch (TimeoutException e) {
			clickElementByKeyboard(By.id("gwt-debug-PortfolioAllocationView-reallocateBtn"));
		}

		try {
			waitGet(By.id("gwt-debug-PortfolioRebalanceView-addInvestmentBtn"));
		} catch (TimeoutException ex) {
			clickElement(By.id("gwt-debug-PortfolioAllocationView-reallocateBtn"));
		}

		waitGet(By.id("gwt-debug-PortfolioRebalanceView-optimizeBtn"));
		// waitGet(By.id("gwt-debug-PortfolioRebalanceView-resetNominalBtn"));
		waitGet(By.id("gwt-debug-PortfolioRebalanceView-cancelEditButton"));
		// waitGet(By
		// .id("gwt-debug-PortfolioRebalanceView-submitAllocationButton"));

		// ensure table shows up
		// assertTrue(pageContainsStr("Target Weight"));
		assertTrue(pageContainsStr("Current Weight"));
		assertTrue(pageContainsStr("New Allocation"));
		assertTrue(pageContainsStr("Total"));
		assertTrue(pageContainsStr("100.00%"));

		return this;
	}

	/**
	 * After click REALLOCATE button, you can click ADD INVESTMENT button, and
	 * the page will be navigated to {@link InvestmentsPage}
	 * 
	 * @return {@link InvestmentsPage}
	 */
	public InvestmentsPage clickAddInvestmentButton() {

		clickElement(By.id("gwt-debug-PortfolioRebalanceView-addInvestmentBtn"));

		try {
			this.waitForElementVisible(By.id("gwt-debug-ManagerListItem-strategyName"), Settings.WAIT_SECONDS * 2);
		} catch (TimeoutException e) {
			log("No investments are loaded in fund explorer!");
		}
		return new InvestmentsPage(webDriver, HoldingsPage.class);
	}

	/**
	 * click the OPTIMIZE button on the top right of the page
	 * 
	 * @return {@link HoldingsPage}
	 */
	public HoldingsPage clickOptimizeInvestmentsButton() {

		clickElement(By.id("gwt-debug-PortfolioRebalanceView-optimizeBtn"));

		return this;
	}

	/**
	 * click the cancel to exit re-allocate mode
	 * 
	 * @return {@link HoldingsPage}
	 */
	public HoldingsPage clickCancelEditButton() {
		clickElement(By.id("gwt-debug-PortfolioRebalanceView-cancelEditButton"));
		return this;
	}

	public HoldingsPage clickCancelTradeButton() {
		clickElement(By.xpath(".//*[contains(@id,'cancelBtn') and contains(text(),'Cancel')]"));
		return this;
	}

	/**
	 * After click REALLOCATE button, the page is in the edit allocation mode,
	 * and then it's ready to input the new allocation for the given investment
	 * 
	 * @param investmentName
	 *            the name of the investment to be reallocated
	 * @param allocation
	 * 
	 * @return {@link HoldingsPage}
	 * @throws InterruptedException
	 */
	public HoldingsPage setNewAllocationForInvestment(String investmentName, String allocation)
			throws InterruptedException {
		wait(1);
		sendKeysToElement(
				By.xpath("//td[.='" + investmentName
						+ "']/following-sibling::td[//input[@id='gwt-debug-TextBoxPercentageSpinner-percentField']]//input"),
				allocation + "\n");

		return this;
	}

	/**
	 * After click REALLOCATE button, the page is in the edit allocation mode,
	 * and then it's ready to click the plus button to set the new allocation
	 * for the given investment
	 * 
	 * @param investmentName
	 *            the name of the investment to be reallocated
	 * @param times
	 *            the number of times to click the plus button; normally, each
	 *            time the allocation add 5%
	 * 
	 * @return {@link HoldingsPage}
	 * @throws InterruptedException
	 * 
	 */
	public HoldingsPage setNewAllocationForInvestmentByPlusButton(String investmentName, int times)
			throws InterruptedException {

		waitForElementVisible(
				By.xpath("//td[.='" + investmentName
						+ "']/following-sibling::td[//input[@id='gwt-debug-TextBoxPercentageSpinner-percentField']]//button[contains(@id,'plusButton')]"),
				10);
		for (int i = 0; i < times; i++) {
			clickElement(By.xpath("//td[.='" + investmentName
					+ "']/following-sibling::td[//input[@id='gwt-debug-TextBoxPercentageSpinner-percentField']]//button[contains(@id,'plusButton')]"));
		}

		wait(Settings.WAIT_SECONDS);

		return this;
	}

	/**
	 * After click REALLOCATE button, the page is in the edit allocation mode,
	 * and then it's ready to click the minus button to set the new allocation
	 * for the given investment
	 * 
	 * @param investmentName
	 *            the name of the investment to be reallocated
	 * @param times
	 *            the number of times to click the plus button; normally, each
	 *            time the allocation minus 5%
	 * 
	 * @return {@link HoldingsPage}
	 * @throws InterruptedException
	 * 
	 */
	public HoldingsPage setNewAllocationForInvestmentByMinusButton(String investmentName, int times) {
		waitForElementVisible(
				By.xpath("//td[.='" + investmentName
						+ "']/following-sibling::td[//input[@id='gwt-debug-TextBoxPercentageSpinner-percentField']]//button[contains(@id,'minusButton')]"),
				20);
		for (int i = 0; i < times; i++) {
			clickElement(By.xpath("//td[.='" + investmentName
					+ "']/following-sibling::td[//input[@id='gwt-debug-TextBoxPercentageSpinner-percentField']]//button[contains(@id,'minusButton')]"));
		}

		return this;
	}

	/**
	 * After click REALLOCATE button, the page is in the edit allocation mode,
	 * click the REBALANCE PREVIEW button and confirm the new reallocation. If
	 * allocation not changed, it will generate an error message, saying: Please
	 * change the allocation first before you can rebalance the portfolio
	 * 
	 * @param investments
	 *            the name of investments; check if these investments are in the
	 *            list
	 * 
	 * @return {@link HistoryPage}
	 */
	public HistoryPage clickRebalancePreviewAndConfirm(String... investments) throws InterruptedException {

		wait(Settings.WAIT_SECONDS);
		try {
			clickElement(By.id("gwt-debug-PortfolioRebalanceView-submitAllocationButton"));

			waitForElementVisible(By.id("gwt-debug-RebalanceConfirmationDialog-confirmBtn"), 10);
		} catch (TimeoutException e) {
			clickElement(By.id("gwt-debug-PortfolioRebalanceView-submitAllocationButton"));

			waitForElementVisible(By.id("gwt-debug-RebalanceConfirmationDialog-confirmBtn"), 10);
		}
		waitForWaitingScreenNotVisible();
		assertTrue(pageContainsStr("Investment"));

		assertTrue(pageContainsStr("Target Allocation"));

		for (String investment : investments) {

			assertTrue(pageContainsStr(investment));
		}

		wait(3);

		waitForElementVisible(By.id("gwt-debug-RebalanceConfirmationDialog-confirmBtn"), 10);

		wait(3);
		int size = getSizeOfElements(By.xpath("(.//*[@id='gwt-debug-CustomDialog-okButton'])"));
		for (int i = size; i > 0; i--) {
			clickElement(By.xpath("(.//*[@id='gwt-debug-CustomDialog-okButton'])[" + String.valueOf(i) + "]"));
		}

		// clickElementByKeyboard(By
		// .id("gwt-debug-RebalanceConfirmationDialog-confirmBtn"));
		wait(3);
		int size2 = getSizeOfElements(By.id("gwt-debug-RebalanceConfirmationDialog-confirmBtn"));

		clickElementByKeyboard(By.xpath(
				"(.//*[@id='gwt-debug-RebalanceConfirmationDialog-confirmBtn'])[" + String.valueOf(size2) + "]"));
		wait(2);
		for (int i = 0; i < 5; i++) {
			if (isElementVisible(By.xpath(
					"(.//*[@id='gwt-debug-RebalanceConfirmationDialog-confirmBtn'])[" + String.valueOf(size2) + "]"))) {
				clickElement(By.xpath("(.//*[@id='gwt-debug-RebalanceConfirmationDialog-confirmBtn'])["
						+ String.valueOf(size2) + "]"));
			}
		}
		handleAlert();

		this.waitForWaitingScreenNotVisible();
		AccountOverviewPage overview = new AccountOverviewPage(webDriver);
		overview.goToTransactionHistoryPage();

		return new HistoryPage(webDriver);
	}

	/**
	 * After click REALLOCATE button, the page is in the edit allocation mode,
	 * click the REBALANCE PREVIEW button but cancel the new reallocation.
	 * 
	 * @param investments
	 *            the name of investments; check if these investments are in the
	 *            list
	 * 
	 * @return {@link HoldingsPage}
	 */
	public HoldingsPage clickRebalancePreviewAndCancel(String... investments) throws InterruptedException {

		clickElement(By.id("gwt-debug-PortfolioRebalanceView-submitAllocationButton"));

		assertTrue(pageContainsStr("Investment"));

		assertTrue(pageContainsStr("Target Allocation"));

		for (String investment : investments) {

			assertTrue(pageContainsStr(investment));
		}

		clickElement(By.id("gwt-debug-RebalanceConfirmationDialog-cancelBtn"));

		return this;
	}

	public HoldingsPage clickRebalancePreviewAndChooseRebalanceTypeAndConfirm(String rebalanceType,
			String... investments) {

		clickElement(By.id("gwt-debug-PortfolioRebalanceView-submitAllocationButton"));

		clickElement(By.xpath(".//label[contains(text(),'" + rebalanceType + "')]"));

		clickElement(By.id("gwt-debug-PortfolioRebalanceView-okButton"));

		if (rebalanceType.contains("Bulk")) {
			waitForElementVisible(By.id("gwt-debug-PairedListBoxSelector-sourceList"), 5);

			Select select = new Select(webDriver.findElement(By.id("gwt-debug-PairedListBoxSelector-sourceList")));

			for (WebElement elem : select.getOptions()) {
				elem.click();
			}

			clickElement(By.id("gwt-debug-PairedListBoxSelector-addImg"));

			clickElement(By.id("gwt-debug-BulkRebalanceWidget-continueBtn"));
		}

		waitForElementVisible(By.xpath(".//*[contains(@id,'confirmBtn')]"), 10);

		for (String investment : investments) {

			assertTrue(pageContainsStr(investment));
		}
		waitForElementVisible(By.xpath(".//*[contains(@id,'confirmBtn')]"), 10);
		clickElement(By.xpath(".//*[contains(@id,'confirmBtn')]"));

		return this;
	}

	/**
	 * After click OPTIMIZE button, on the pop-up Choose Optimization Method
	 * page, select an optimization method
	 * 
	 * @param method
	 *            the optimization method
	 * 
	 * @return {@link HoldingsPage}
	 */
	public HoldingsPage chooseOptimizationMethod(String method) {

		clickElement(By.xpath("//label[contains(text(),'" + method + "')]"));

		if (method.equals("Selected Investments")) {

			assertTrue(pageContainsStr("Keep all existing assets"));
		}

		return this;
	}

	/**
	 * After click OPTIMIZE button, on the pop-up Choose Optimization Method
	 * page, edit the time horizon (years)
	 * 
	 * @param year
	 * 
	 * @return {@link HoldingsPage}
	 */
	public HoldingsPage timeHorizaon(String year) {

		selectFromDropdown(By.id("gwt-debug-OptimizeWizard-timeHorizonList"), year);

		return this;
	}

	/**
	 * After click OPTIMIZE button, on the pop-up Choose Optimization Method
	 * page, edit the min total core weight
	 * 
	 * @param weight
	 * 
	 * @return {@link HoldingsPage}
	 */
	public HoldingsPage minTotalCoreWeight(String weight) {

		sendKeysToElement(By.id("gwt-debug-OptimizeWizard-minCoreWeightTextBox"), weight);

		return this;
	}

	/**
	 * After click OPTIMIZE button, on the pop-up Choose Optimization Method
	 * page, edit the max single strategy weight
	 * 
	 * @param weight
	 * 
	 * @return {@link HoldingsPage}
	 */
	public HoldingsPage maxSingleStrategyWeight(String weight) {

		sendKeysToElement(By.id("gwt-debug-OptimizeWizard-maxStrategyWeightTextBox"), weight);

		return this;
	}

	/**
	 * After click OPTIMIZE button, on the pop-up Choose Optimization Method
	 * page, choose White List optimization method and select a white list
	 * 
	 * @param list
	 *            white list
	 * 
	 * @return {@link HoldingsPage}
	 */
	public HoldingsPage selectWhiteList(String list) {

		selectFromDropdown(By.id("gwt-debug-OptimizeWizard-iuList"), list);

		assertTrue(pageContainsStr(list));

		return this;
	}

	/**
	 * After click OPTIMIZE button, on the pop-up Choose Optimization Method
	 * page, click the CANCEL button
	 * 
	 * @return {@link HoldingsPage}
	 */
	public HoldingsPage clickOptimizeCancelButton() {

		clickElement(By.id("gwt-debug-OptimizeWizard-closeButton"));

		return this;
	}

	/**
	 * After click OPTIMIZE button, on the pop-up Choose Optimization Method
	 * page, click the DEFINE WHITE LIST button
	 * 
	 * @return {@link HoldingsPage}
	 */
	public HoldingsPage clickOptimizeDefineFavoritesButton() {

		clickElement(By.id("gwt-debug-OptimizeWizard-defineFavouriteButton"));

		return this;
	}

	/**
	 * After click OPTIMIZE button, on the pop-up Choose Optimization Method
	 * page, click the DEFINE WHITE LIST button
	 * 
	 * @return {@link HoldingsPage}
	 */
	public HoldingsPage clickOptimizeDefineWhiteListButton() {

		clickElement(By.id("gwt-debug-OptimizeWizard-defineWhiteListButton"));

		return this;
	}

	/**
	 * After click OPTIMIZE button and chosse Risk Profile optimization method,
	 * on the pop-up Choose Optimization Method page, click the GO TO RISK
	 * PROFILE button
	 * 
	 * @return {@link HoldingsPage}
	 */
	public HoldingsPage clickOptimizeRiskProfileButton() {

		clickElement(By.id("gwt-debug-OptimizeWizard-riskProfileButton"));

		return this;
	}

	/**
	 * After click OPTIMIZE button, on the pop-up Choose Optimization Method
	 * page, click the OPTIMIZE button
	 * 
	 * @return {@link HoldingsPage}
	 */
	public HoldingsPage clickOptimizeButton() {

		clickElement(By.id("gwt-debug-OptimizeWizard-optimizeButton"));

		return this;
	}

	/**
	 * Aftre click TRADE button, on the Investment Order page, click the green
	 * icon to add order
	 * 
	 * @return {@link HoldingsPage}
	 */
	public HoldingsPage clickAddOrderIcon() {

		clickElement(By.id("gwt-debug-PortfolioTradeView-addLabel"));

		return this;
	}

	/**
	 * Click the "+" button next to investment in holdings page
	 * 
	 * @param investment
	 * @return {@link HoldingsPage}
	 */
	public HoldingsPage clickAddButtonForInvestment(String investment) {
		clickElement(
				By.xpath(".//td//div[contains(text(),'" + investment + "')]//following-sibling::button[@title='Buy']"));
		return this;
	}

	/**
	 * Click the "-" button next to investment in holdings page
	 * 
	 * @param investment
	 * @return {@link HoldingsPage}
	 */
	public HoldingsPage clickMinusButtonForInvestment(String investment) {
		clickElement(By
				.xpath(".//td//div[contains(text(),'" + investment + "')]//following-sibling::button[@title='Sell']"));
		return this;
	}

	/**
	 * click edit button for order
	 * 
	 * @return {@link TradeOrderFormPage}
	 */
	public TradeOrderFormPage clickEditButtonForOrder() {

		int size = this.getSizeOfElements(By.xpath(".//*[@id='gwt-debug-PortfolioTradeOrderRowWidgetView-deleteImg']"));

		clickElement(By.xpath(
				"(.//*[@id='gwt-debug-PortfolioTradeOrderRowWidgetView-additionalFieldsButton'])[" + size + "]"));
		return new TradeOrderFormPage(webDriver, HistoryPage.class);
	}

	/**
	 * After click the TRADE button and the Add Order icon, select investment
	 * order side
	 * 
	 * @param side
	 * @return {@link HoldingsPage}
	 */
	public HoldingsPage editInvestmentOrderSide(String side) {

		try {
			waitForElementVisible(By.xpath(".//*[@id='gwt-debug-PortfolioTradeOrderRowWidgetView-deleteImg']"), 10);
		} catch (TimeoutException e) {

		}

		int size = this.getSizeOfElements(By.xpath(".//*[@id='gwt-debug-PortfolioTradeOrderRowWidgetView-deleteImg']"));

		selectFromDropdown(By.xpath("(.//*[@id='gwt-debug-PortfolioTradeOrderRowWidgetView-sideList'])[" + size + "]"),
				side);

		return this;
	}

	/**
	 * After click the TRADE button and the Add Order icon, if choose Sell as
	 * the side, select an existing asset from the drop-down
	 * 
	 * @param asset
	 *            the asset to sell
	 * @return {@link HoldingsPage}
	 */
	public HoldingsPage editInvestmentOrderAssetToSell(String asset) {

		int size = this.getSizeOfElements(By.id("gwt-debug-PortfolioTradeOrderRowWidgetView-deleteImg"));

		selectFromDropdown(
				By.xpath("(.//*[@id='gwt-debug-PortfolioTradeOrderRowWidgetView-assetListBox'])[" + size + "]"), asset);

		return this;
	}

	/**
	 * After click the TRADE button and the Add Order icon, if choose Buy as the
	 * side, click the magnifying icon to select a new investment; the page will
	 * be navigated to the {@link InvestmentsPage}
	 * 
	 * @return {@link InvestmentsPage}
	 */
	public InvestmentsPage editInvestmentOrderAssetToBuy() {

		waitForElementVisible(By.xpath(".//*[@id='gwt-debug-PortfolioTradeOrderRowWidgetView-deleteImg']"), 15);

		int size = this.getSizeOfElements(By.xpath(".//*[@id='gwt-debug-PortfolioTradeOrderRowWidgetView-deleteImg']"));

		clickElement(By.xpath("(.//*[@id='gwt-debug-PortfolioTradeOrderRowWidgetView-exploreImg'])[" + size + "]"));

		return new InvestmentsPage(webDriver);
	}

	/**
	 * After click the TRADE button and the Add Order icon, edit the amount for
	 * the Buy or the Sell side
	 * 
	 * @param amount
	 * 
	 * @return {@link InvestmentsPage}
	 */
	public HoldingsPage editInvestmentOrderAmount(String amount) {

		waitForElementVisible(By.id("gwt-debug-PortfolioTradeOrderRowWidgetView-deleteImg"), 10);

		int size = this.getSizeOfElements(By.id("gwt-debug-PortfolioTradeOrderRowWidgetView-deleteImg"));

		sendKeysToElement(
				By.xpath("(.//*[@id='gwt-debug-PortfolioTradeOrderRowWidgetView-amountTextBox'])[" + size + "]"),
				amount);

		return this;
	}

	/**
	 * After click the TRADE button and the Add Order icon, edit the ISIN for
	 * the Buy or the Sell side
	 * 
	 * @param ISIN
	 * 
	 * @return {@link HoldingsPage}
	 */
	public HoldingsPage editInvestmentOrderIsin(String ISIN) {
		waitForElementVisible(
				By.xpath(
						".//div[@id='gwt-debug-PortfolioTradeOrderRowWidgetView-buyAssetPanel' and not(@aria-hidden='true')]//input"),
				10);

		int size = this.getSizeOfElements(By.xpath(
				".//div[@id='gwt-debug-PortfolioTradeOrderRowWidgetView-buyAssetPanel' and not(@aria-hidden='true')]//input"));

		WebElement elem = webDriver.findElement(By
				.xpath("(.//div[@id='gwt-debug-PortfolioTradeOrderRowWidgetView-buyAssetPanel' and not(@aria-hidden='true')]//input)["
						+ String.valueOf(size) + "]"));

		log(String.valueOf(size));
		clickElement(By
				.xpath("(.//div[@id='gwt-debug-PortfolioTradeOrderRowWidgetView-buyAssetPanel' and not(@aria-hidden='true')]//input)["
						+ String.valueOf(size) + "]"));
		elem.clear();
		elem.sendKeys(ISIN);

		clickElement(By.id("gwt-debug-PortfolioTradeOrderRowWidgetView-lastPriceLabel"));
		return this;
	}

	public HoldingsPage editCurrencyForCreateInvestment(String currency) {
		selectFromDropdown(By.xpath(".//div[.='Currency']//following-sibling::select"), currency);
		return this;
	}

	public HoldingsPage editViewModeOfAccount(String mode) {
		selectFromDropdown(By.id("gwt-debug-PortfolioOverviewView-valueTypeListBox"), mode);
		this.waitForWaitingScreenNotVisible();
		return this;
	}

	public HoldingsPage clickCreateButtonForCreateInvestment() {
		clickElement(By.xpath(".//button[.='Create']"));
		clickNoButtonIfVisible();
		return this;
	}

	/**
	 * After click the TRADE button and finish adding investment orders, click
	 * the PREVIEW button; the page will be navigated to the {@link HistoryPage}
	 * 
	 * @return {@link InvestmentsPage}
	 */
	public RecentOrdersPage clickPreviewButtonForInvestmentOrder() throws InterruptedException {

		waitForElementVisible(By.id("gwt-debug-PortfolioTradeView-previewBtn"), 10);

		clickElementByKeyboard(By.id("gwt-debug-PortfolioTradeView-previewBtn"));

		// clickElementByKeyboard(By
		// .id("gwt-debug-PortfolioTradeConfirmationDialogView-confirmBtn"));

		return new RecentOrdersPage(webDriver);

	}

	public HoldingsPage clickPreviewButtonForCheckingFields() {
		waitForElementVisible(By.id("gwt-debug-PortfolioTradeView-previewBtn"), 10);

		clickElementByKeyboard(By.id("gwt-debug-PortfolioTradeView-previewBtn"));

		return this;
	}

	public HoldingsPage deleteTransactionByInvestment(String investment) {

		clickElement(By.xpath(".//*[contains(text(),'" + investment
				+ "')]//ancestor::td//following-sibling::td[*[contains(@id,'deleteImg')]]"));
		return this;
	}

}
