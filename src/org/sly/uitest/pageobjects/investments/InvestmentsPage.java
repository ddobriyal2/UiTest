package org.sly.uitest.pageobjects.investments;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.pageobjects.abstractpage.AdvancedSearchPage;
import org.sly.uitest.pageobjects.clientsandaccounts.DetailEditPage;
import org.sly.uitest.pageobjects.clientsandaccounts.HoldingsPage;
import org.sly.uitest.settings.Settings;

/**
 * This class represents the Investments Page, which can be navigated by
 * clicking 'Explore' -> 'Investments' or adding investments from other page
 * 
 * URL: "http://192.168.1.104:8080/SlyAWS/?locale=en#investmentList"
 * 
 * @author Lynne Huang
 * @date : 18 Aug, 2015
 * @company Prive Financial
 * 
 */
public class InvestmentsPage extends AbstractPage {

	private Class<?> theClass;

	/**
	 * @param webDriver
	 */
	public InvestmentsPage(WebDriver webDriver) {

		super();
		this.webDriver = webDriver;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(60, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		/*
		 * wait.until(ExpectedConditions.visibilityOfElementLocated(By
		 * .id("gwt-debug-ManagerList-mainPanel")));
		 */
		try {
			waitForElementVisible(By.id("gwt-debug-ManagerListWidgetView-mainPanelLarge"), 90);
		} catch (TimeoutException e) {

		}

		// wait.until(ExpectedConditions.visibilityOfElementLocated(By
		// .id("gwt-debug-ManagerListWidgetView-mainPanel")));

		assertTrue(pageContainsStr("Investments"));
		// assertTrue(pageContainsStr("Sort by:"));
		// assertTrue(pageContainsStr("Selected Investments"));
		// assertTrue(pageContainsStr("Advanced Filters"));
		// assertTrue(pageContainsStr("Top Equity Investments"));

	}

	/**
	 * This constructor is triggered when the addToPortfolio() method is called,
	 * and the page will be navigated back to theClass
	 * 
	 * @param webDriver
	 */
	public InvestmentsPage(WebDriver webDriver, Class<?> theClass) {

		super();
		this.webDriver = webDriver;
		this.theClass = theClass;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gwt-debug-ManagerListWidgetView-mainPanel")));

		assertTrue(pageContainsStr("Investments"));
		// assertTrue(pageContainsStr("Sort by:"));
		// assertTrue(pageContainsStr("Selected Investments"));
		// assertTrue(pageContainsStr("Advanced Filters"));
		// assertTrue(pageContainsStr("Top Equity Investments"));

	}

	/**
	 * Click the downward triangle icon to open the Advanced Search Panel on the
	 * Investments Page
	 * 
	 * @return AdvancedSearchPage
	 */
	public AdvancedSearchPage goToAdvancedSearchPage() throws InterruptedException {

		this.waitForWaitingScreenNotVisible();
		scrollToTop();
		waitForElementVisible(By.id("gwt-debug-ManagerListWidgetView-advancedSearchBtn"), 10);
		clickElementByKeyboard(By.id("gwt-debug-ManagerListWidgetView-advancedSearchBtn"));

		return new AdvancedSearchPage(webDriver, InvestmentsPage.class);

	}

	/**
	 * Simply input the string into the search box
	 * 
	 * @param string
	 *            the string to search, such as client name, account name
	 * @return {@link InvestmentsPage}
	 */
	public InvestmentsPage simpleSearchByName(String name) {
		this.waitForWaitingScreenNotVisible();
		waitForElementPresent(By.id("gwt-debug-ManagerListWidgetView-searchBox"), 60);

		waitForElementVisible(By.id("gwt-debug-ManagerListWidgetView-searchBox"), 60);

		try {
			waitForElementVisible(By.xpath(".//*[contains(@id,'strategyName')]"), 60);
		} catch (Exception e) {

		} finally {
			sendKeysToElement(By.id("gwt-debug-ManagerListWidgetView-searchBox"), name);
		}

		waitForElementVisible(By.id("gwt-debug-ManagerListWidgetView-searchBtn"), Settings.WAIT_SECONDS);
		clickElement(By.id("gwt-debug-ManagerListWidgetView-searchBtn"));

		this.waitForElementVisible(By.id("gwt-debug-ManagerListWidgetView-searchBox"), 10);

		for (int i = 0; i < 3; i++) {
			if (!name.equals(getTextByLocator(By.id("gwt-debug-ManagerListWidgetView-searchBox")))) {
				sendKeysToElement(By.id("gwt-debug-ManagerListWidgetView-searchBox"), name);
				clickElement(By.id("gwt-debug-ManagerListWidgetView-searchBtn"));
			}
		}

		return this;

	}

	/**
	 * Click the cross icon besides the search box to clear the previous search
	 * 
	 * @return {@link InvestmentsPage}
	 */
	public InvestmentsPage clickClearSearchIcon() {

		try {

			waitForElementVisible(By.id("gwt-debug-ManagerListWidgetView-clearImg"), 15);

			if (isElementVisible(By.id("gwt-debug-ManagerListWidgetView-clearImg"))) {

				clickElement(By.id("gwt-debug-ManagerListWidgetView-clearImg"));
			}
		} catch (Exception e) {

		}

		return this;
	}

	/**
	 * On the Investments page, click the green plus icon to select the
	 * investment with the given name; this investment will be shown under the
	 * Selected Investments section
	 * 
	 * @param name
	 *            the investments name
	 * @return {@link InvestmentsPage}
	 * @throws InterruptedException
	 */
	public InvestmentsPage selectInvestmentByName(String name) throws InterruptedException {
		int size = this
				.getSizeOfElements(By.xpath(".//a[@id='gwt-debug-ManagerListItem-strategyName' and contains(text(),'"
						+ name + "')]//parent::div//parent::td//preceding::td//button[@class='ActionColumnButton']"));
		By by = By.xpath("(.//a[@id='gwt-debug-ManagerListItem-strategyName' and contains(text(),'" + name
				+ "')]//parent::div//parent::td//preceding::td//button[@class='ActionColumnButton'])["
				+ String.valueOf(size) + "]");
		if (size == 0) {
			this.simpleSearchByName(name);
		}

		this.waitForWaitingScreenNotVisible();

		if (!this.getTextByLocator(By.id("gwt-debug-ManagerListWidgetView-searchBox")).contains(name)) {
			this.simpleSearchByName(name);
		}
		try {
			this.waitForWaitingScreenNotVisible();
			waitForElementVisible(by, 30);
		} catch (TimeoutException e) {
			this.simpleSearchByName(name);

		}

		wait(3);
		WebElement elem;
		try {
			elem = webDriver.findElement(by);
		} catch (NoSuchElementException e) {
			this.simpleSearchByName(name);
			size = this.getSizeOfElements(
					By.xpath(".//a[@id='gwt-debug-ManagerListItem-strategyName' and contains(text(),'" + name
							+ "')]//parent::div//parent::td//preceding::td//button[@class='ActionColumnButton']"));
			by = By.xpath("(.//a[@id='gwt-debug-ManagerListItem-strategyName' and contains(text(),'" + name
					+ "')]//parent::div//parent::td//preceding::td//button[@class='ActionColumnButton'])["
					+ String.valueOf(size) + "]");
			this.waitForWaitingScreenNotVisible();
			try {
				waitForElementVisible(by, 30);
			} catch (TimeoutException ex) {
				this.simpleSearchByName(name);
				waitForElementVisible(by, 30);
			}

			elem = waitGet(by);
		}
		try {
			elem.sendKeys(Keys.SPACE, Keys.ENTER);
		} catch (ElementNotVisibleException ex) {
			waitForElementVisible(by, 30);
			clickElement(by);
		}
		// wait(3);

		try {
			FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(5, TimeUnit.SECONDS)
					.pollingEvery(1, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(.//a[.='Select Investment'])[1]")));
		} catch (TimeoutException e) {
			if (!isElementVisible(By.xpath(".//a[.='Remove Investment']"))) {
				for (int i = 0; i < 5; i++) {
					if (!isElementVisible(By.xpath("(.//a[.='Select Investment'])[1]"))) {

						elem.sendKeys(Keys.SPACE, Keys.ENTER);

						try {
							waitForElementVisible(By.xpath(".//a[.='Select Investment']"), Settings.WAIT_SECONDS);
						} catch (TimeoutException ex) {
							clickElement(by);
							waitForElementVisible(By.xpath(".//a[.='Select Investment']"), Settings.WAIT_SECONDS);
						}
					}

				}

			}

		}

		try {
			waitForElementVisible(By.id("gwt-debug-CustomDialog-okButton"), 3);
			clickOkButtonIfVisible();
		} catch (TimeoutException e) {

		}
		size = getSizeOfElements(By.xpath("(.//a[.='Select Investment'])"));

		clickElement(By.xpath("(.//a[.='Select Investment'])[" + String.valueOf(size) + "]"));

		// try {
		//
		// waitForElementVisible(
		// By.xpath(".//*[@id='gwt-debug-ManagerListWidgetView-selectedPairWidget']//*[.='"
		// + name + "']"), 5);
		// } catch (TimeoutException e) {
		// while (!isElementVisible(By
		// .xpath(".//*[@id='gwt-debug-ManagerListWidgetView-selectedPairWidget']//*[.='"
		// + name + "']"))) {
		// elem.sendKeys(Keys.SPACE, Keys.ENTER);
		// clickElement(By.xpath("(.//a[.='Select Investment'])["
		// + String.valueOf(size) + "]"));
		//
		// waitForElementVisible(
		// By.xpath(".//*[@id='gwt-debug-ManagerListWidgetView-selectedPairWidget']//*[.='"
		// + name + "']"), 5);
		// }
		//
		// }
		return this;
	}

	/**
	 * When trade investments from {@link HoldingsPage} and select an asset, the
	 * page will be navigated to {@link InvestmentsPage}; on the Investments
	 * page, click the green plus icon to select the investment with the given
	 * name; the page will be navigated back to {@link HoldingsPage} directly
	 * 
	 * @param name
	 *            the investments name
	 * @return {@link InvestmentsPage}
	 * @throws InterruptedException
	 */
	public HoldingsPage selectInvestmentByNameForInvestmentOrder(String name) throws InterruptedException {
		By by = By
				.xpath(".//td[.='" + name + "']//preceding-sibling::td[//button[@class='ActionColumnButton']]//button");
		try {
			waitForElementVisible(by, 10);
		} catch (TimeoutException e) {
			this.simpleSearchByName(name);
			waitForElementVisible(by, 10);
		}
		clickElement(by);

		waitForElementVisible(By.xpath("//a[.='Select Investment']"), 3);

		clickElement(By.xpath("//a[.='Select Investment']"));

		clickElement(By.id("gwt-debug-ManagerStrategySelectionWidget-addAllBtn"));

		clickOkButtonIfVisible();

		return new HoldingsPage(webDriver);
	}

	/**
	 * On the Investments page, click the downward double-arrow icon to expand
	 * the investment with the given name; the investment chart will be
	 * displayed below
	 * 
	 * @param name
	 *            the investments name
	 * @return {@link InvestmentsPage}
	 */
	public InvestmentsPage expandInvestmentByName(String name) {

		clickElement(By.xpath("//td[./a[contains(.,'" + name
				+ "')]]/following-sibling::td[div[@id='gwt-debug-ManagerListItem-tableDetailsToggle']]//i"));

		return this;
	}

	/**
	 * When select more than one investments, click the COMPARE button under the
	 * Selected Investments section to see the difference
	 * 
	 * @return {@link InvestmentsPage}
	 */
	public InvestmentsPage clickCompareButton() {

		clickElement(By.id("gwt-debug-ManagerStrategySelectionWidget-compareAllBtn"));
		try {
			waitForElementVisible(By.xpath(".//*[.='Investment Comparison']"), Settings.WAIT_SECONDS);
			assertTrue(pageContainsStr("Investment Comparison"));
			log("Click Compare Button: Done");
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * When select investments, click the ADD TO PORTFOLIO button under the
	 * Selected Investments section to add these investments to the portfolio;
	 * the page will be navigated back to the T class; when this method is
	 * called, the constructor InvestmentsPage(WebDriver webDriver, Class
	 * <?> theClass) is triggered
	 * 
	 * @return {@link InvestmentsPage}
	 */
	@SuppressWarnings("unchecked")
	public <T> T clickAddToPortfolioButton() throws Exception {

		if (waitGet(By.id("gwt-debug-ManagerStrategySelectionWidget-addAllBtn")).isEnabled()) {
			clickElementByKeyboard(By.id("gwt-debug-ManagerStrategySelectionWidget-addAllBtn"));
		} else {
			clickElement(By.id("gwt-debug-ManagerListDialogView-closeAnchor"));
		}

		return (T) this.theClass.getDeclaredConstructor(WebDriver.class).newInstance(webDriver);
	}

	/**
	 * When select investments, click the ADD TO PORTFOLIO button under the
	 * Selected Investments section to add these investments to the portfolio;
	 * the page will be navigated back to the {@link HoldingsPage}
	 * 
	 * @return {@link InvestmentsPage}
	 */
	public HoldingsPage clickAddToPortfolioButtonToHoldingPage() {

		if (waitGet(By.id("gwt-debug-ManagerStrategySelectionWidget-addAllBtn")).isEnabled()) {
			clickElement(By.id("gwt-debug-ManagerStrategySelectionWidget-addAllBtn"));
		} else {
			clickElement(By.id("gwt-debug-ManagerListDialogView-closeAnchor"));
		}
		return new HoldingsPage(webDriver);
	}

	/**
	 * The {@link InvestmentsPage} is navigated from the Explore directly; after
	 * select investments, click the ADD TO PORTFOLIO button, the pop-up page
	 * Choose Investor Account will be generated
	 * 
	 * @param name
	 * @return {@link InvestmentsPage}
	 */
	public InvestmentsPage clickAddToPortfolioButtonFromList() {

		if (webDriver.findElement(By.id("gwt-debug-ManagerStrategySelectionWidget-addAllBtn")).isEnabled()) {
			clickElement(By.id("gwt-debug-ManagerStrategySelectionWidget-addAllBtn"));
		} else {
			// clickElement(By
			// .xpath(".//*[@id='gwt-debug-PortfolioRebalanceView-addStrategyDialog']/button[@class='dialogBoxCloseBtn']"));

			clickElement(By.id("gwt-debug-ManagerListDialogView-closeAnchor"));
		}

		return this;

	}

	/**
	 * After open a specific investment information factsheet page, click the
	 * ADD TO PORTFOLIO button; and the pop-up Choose Investor Account page will
	 * be shown
	 * 
	 * @return {@link InvestmentsPage}
	 * @throws InterruptedException
	 */
	public InvestmentsPage clickAddToPortfolioButtonFromFactSheet() throws InterruptedException {
		By by = By.xpath(
				".//*[@id='gwt-debug-ManagerDisplayWidgetView-managerDetailTable']//button[.='Add to portfolio']");
		this.waitForElementVisible(by, Settings.WAIT_SECONDS);
		// clickElement(By.xpath("//button[.='Add to Portfolio']"));
		clickElement(by);
		return this;
	}

	/**
	 * (Login as a manager) The {@link InvestmentsPage} is navigated from the
	 * Explore directly; after select investments, click the ADD TO PORTFOLIO
	 * button, the pop-up page Choose Investor Account will be generated
	 * 
	 * @param modelPortfolio
	 *            the name of the model portfolio to be selected from the
	 *            investor account drop-down list
	 * @return {@link InvestmentsPage}
	 */
	public HoldingsPage clickAddToPortfolioButtonByManager(String modelPortfolio) {

		if (waitGet(By.id("gwt-debug-ManagerStrategySelectionWidget-addAllBtn")).isEnabled()) {
			clickElement(By.id("gwt-debug-ManagerStrategySelectionWidget-addAllBtn"));

			waitForElementVisible(By.id("gwt-debug-ManagerListWidgetView-invAccListBox"), 10);

			selectFromDropdown(By.id("gwt-debug-ManagerListWidgetView-invAccListBox"), modelPortfolio);

			scrollToTop();

			clickElement(By.id("gwt-debug-ManagerListWidgetView-okButton"));

		} else {
			// clickElement(By
			// .xpath(".//*[@id='gwt-debug-PortfolioRebalanceView-addStrategyDialog']/button[@class='dialogBoxCloseBtn']"));

			clickElement(By.id("gwt-debug-ManagerListDialogView-closeAnchor"));
		}

		return new HoldingsPage(webDriver);
	}

	/**
	 * When select more than one investments and click the COMPARE button under
	 * the Selected Investments section, on the pop-up Compare Investments page,
	 * click the green icon to select one investment to be added to the
	 * portfolio
	 * 
	 * @param investment
	 *            the name of the investment
	 * @return {@link InvestmentsPage}
	 * @throws InterruptedException
	 */
	public InvestmentsPage clickAddToPortfolioIconAfterCompare(String investment) throws InterruptedException {

		this.waitForElementVisible(By.id("gwt-debug-ManagerCompareView-addButton"), Settings.WAIT_SECONDS);
		clickElement(By.xpath("//td[a[.='" + investment
				+ "']]/following-sibling::td//button[@id='gwt-debug-ManagerCompareView-addButton']"));

		return this;
	}

	/**
	 * On the Investments Page, click the green plus Request missing investment
	 * icon
	 * 
	 * @return {@link InvestmentsPage}
	 */
	public InvestmentsPage clickRequestMissingInvestment() {

		clickElement(By.id("gwt-debug-ManagerListWidgetView-addMissingAssetLabel"));
		this.waitForWaitingScreenNotVisible();
		return this;
	}

	public InvestmentsPage clickInvestmentByName(String investment) {

		waitForElementVisible(By.xpath(".//*[@id='gwt-debug-ManagerListItem-strategyName' and .='" + investment + "']"),
				10);

		clickElement(By.xpath(".//*[@id='gwt-debug-ManagerListItem-strategyName' and .='" + investment + "']"));

		return this;
	}

	public void clickDetailButton() {

		waitForElementVisible(By.xpath(".//button[.='Details']"), 5);

		clickElement(By.xpath(".//button[.='Details']"));
	}

	/**
	 * After click the green plus Request missing investment icon, on the pop-up
	 * Select investment type page, select an investment type that you want to
	 * add; then click PROCEED button
	 * 
	 * @return {@link InvestmentsPage}
	 */
	public DetailEditPage selectInvestmentTypeToAdd(String type) {

		clickElement(By.xpath("//label[.='" + type + "']"));

		clickElement(By.id("gwt-debug-SimpleDlinkPopupWidget-proceedButton"));

		return new DetailEditPage(webDriver, InvestmentsPage.class);

	}

	/**
	 * On the {@link InvestmentsPage}, click the name of the investment with the
	 * given name, the page will be navigated to the specific investment
	 * information factsheet page
	 * 
	 * @param name
	 *            the investment name
	 * @return {@link InvestmentsPage}
	 * @throws InterruptedException
	 */
	public InvestmentsPage openInvestmentByName(String name) throws InterruptedException {
		waitForElementVisible(By.xpath(".//tr[@class='mat-table-body']//a[.='" + name + "']"), 10);
		clickElement(By.xpath(".//tr[@class='mat-table-body']//a[.='" + name + "']"));

		if (isElementVisible(By.xpath(
				".//*[@id='gwt-debug-ManagetListDetailsPopupView-dialogBody']/div/table/tbody/tr/td/button[.='Details']"))) {
			clickElement(By.xpath(
					".//*[@id='gwt-debug-ManagetListDetailsPopupView-dialogBody']/div/table/tbody/tr/td/button[.='Details']"));
		}

		if (pageContainsStr("The security details are not available for display currently.")) {
			clickOkButtonIfVisible();
		} else {
			// assertTrue(pageContainsStr("Objective"));
			assertTrue(pageContainsStr("Historical Performance"));
			assertTrue(pageContainsStr("Risk Analysis"));
		}

		return this;
	}

	/**
	 * After open a pop-up specific investment information factsheet page, click
	 * the Back icon to go back to the Add Investment page
	 * 
	 * @return {@link InvestmentsPage}
	 */
	public InvestmentsPage backToAddInvestment() {

		clickElement(By.id("gwt-debug-ManagerDisplayWidgetView-backButtonLabel"));

		return this;
	}

	/**
	 * After open the specific investment and click the ADD TO PORFOLIO button
	 * from the factsheet page, on the pop-up Choose Investor Account page,
	 * select an investor account
	 * 
	 * @return {@link InvestmentsPage}
	 * @throws InterruptedException
	 * 
	 * 
	 */
	public InvestmentsPage chooseInvestorAccountFromFactsheet(String account) throws InterruptedException {

		By by = By.id("gwt-debug-ManagerDisplayWidgetView-okButton");
		selectFromDropdown(By.id("gwt-debug-ManagerDisplayWidgetView-invAccListBox"), account);

		// this.waitForWaitingScreenNotVisible();

		// new Actions(webDriver)
		// .moveToElement(webDriver.findElement(By.id("gwt-debug-ManagerDisplayWidgetView-okButton"))).perform();

		clickElement(by);

		// make sure ok button is clicked
		if (isElementVisible(by)) {
			clickElement(by);
		}

		return this;

	}

	/**
	 * On the Investment list page, after click the ADD TO PORTFOLIO page, on
	 * the pop-up Choose Investor Account page, select an investor account
	 */
	public InvestmentsPage chooseInvestorAccountFromListPage(String account) {

		selectFromDropdown(By.id("gwt-debug-ManagerListWidgetView-invAccListBox"), account);

		clickElement(By.id("gwt-debug-ManagerListWidgetView-okButton"));

		if (isElementVisible(By.id("gwt-debug-ManagerListWidgetView-okButton"))) {
			clickElement(By.id("gwt-debug-ManagerListWidgetView-okButton"));
		}

		return this;

	}

	/**
	 * When select more than one investments and click the COMPARE button under
	 * the Selected Investments section, on the pop-up Compare Investments page,
	 * click the green icon to select one investment to be added to the
	 * portfolio, and on the pop-up Choose Investor Account page, select an
	 * investor account
	 * 
	 * @param account
	 *            the name of the investor account
	 * @return {@link InvestmentsPage}
	 */
	public InvestmentsPage chooseInvestorAccountAfterCompare(String account) {

		/*
		 * selectFromDropdown(
		 * By.xpath("//select[@id='gwt-debug-ManagerCompare-invAccdialogBox']"),
		 * //select[@id='gwt-debug-ManagerCompare-invAccdialogBox'] account);
		 */
		selectFromDropdown(By.xpath(".//select[@id='gwt-debug-ManagerCompareView-invAccdialogBox']"), account);

		clickElement(By.id("gwt-debug-ManagerCompareView-okButton"));

		if (isElementVisible(By.id("gwt-debug-ManagerCompareView-okButton"))) {
			clickElement(By.id("gwt-debug-ManagerCompareView-okButton"));
		}

		return this;

	}

	/**
	 * When select more than one investments and click the COMPARE button under
	 * the Selected Investments section, on the pop-up Compare Investments page,
	 * click the cross icon on the top right of the page
	 * 
	 * @return {@link InvestmentsPage}
	 */
	public InvestmentsPage closeCompareInvestmentsPage() {

		clickElement(By.id("gwt-debug-ManagerCompareDialogView-closeAnchor"));

		return this;
	}

	/**
	 * On the {@link InvestmentsPage}, get the string of the name of the
	 * index-th investment
	 * 
	 * @param index
	 * 
	 * @return the name of the investment
	 */
	public String getInvestmentNameByIndex(String index) {
		By by = By.xpath("(.//*[@id='gwt-debug-ManagerListItem-strategyName'])[" + index + "]");
		waitForElementVisible(by, 90);

		String investment = this.getTextByLocator(by);

		return investment;
	}

	/**
	 * On the {@link InvestmentsPage}, select an option for the Sort by criteria
	 * 
	 * @param criteria
	 * @return {@link InvestmentsPage}
	 * 
	 * @throws InterruptedException
	 * 
	 */
	public InvestmentsPage sortByCriteria(String criteria) {

		selectFromDropdown(By.id("gwt-debug-ManagerListWidgetView-sortedByCriteria"), criteria);

		return this;

	}

	/**
	 * On the {@link InvestmentsPage}, under the Advanced Filters section,
	 * filter the investments by the execution platforms
	 * 
	 * @param executionPlatform
	 * @return {@link InvestmentsPage}
	 * 
	 * @throws InterruptedException
	 * 
	 */
	public InvestmentsPage filterByExecutionPlatforms(String executionPlatform) throws InterruptedException {

		// try {
		// wait(Settings.WAIT_SECONDS);
		// clickElement(By
		// .xpath("//td[div[.='Execution
		// Platforms']]/preceding-sibling::td[1]/img[@id='gwt-debug-FilterEntryUi-toggleCloseImg']"));
		// } catch (Exception e) {
		// // TODO: handle exception
		// }

		// wait(Settings.WAIT_SECONDS);
		if (isElementVisible(By.xpath(
				"//td[div[.='Execution Platforms']]/preceding-sibling::td[1]/img[@id='gwt-debug-FilterEntryUi-toggleCloseImg']"))) {
			clickElement(By.xpath(
					"//td[div[.='Execution Platforms']]/preceding-sibling::td[1]/img[@id='gwt-debug-FilterEntryUi-toggleCloseImg']"));
		}

		this.waitForElementVisible(By.xpath("//tr[td[div[.='Execution Platforms']]]/following-sibling::tr[1]//select"),
				Settings.WAIT_SECONDS);
		selectFromDropdown(By.xpath("//tr[td[div[.='Execution Platforms']]]/following-sibling::tr[1]//select"),
				executionPlatform);

		return this;
	}

	/**
	 * On the {@link InvestmentsPage}, under the Advanced Filters section,
	 * filter the investments by the investment types
	 * 
	 * @param types
	 * @return {@link InvestmentsPage}
	 * 
	 * @throws InterruptedException
	 * 
	 */
	public InvestmentsPage filterByTypes(String... types) {

		for (String type : types) {

			clickElement(By.xpath("//span[label='" + type + "']/input"));

		}

		return this;
	}

	/**
	 * On the {@link InvestmentsPage}, under the Data Source section on the
	 * bottom right of the page, filter the investments by data source
	 * 
	 * @param source
	 * @return {@link InvestmentsPage}
	 * 
	 * @throws InterruptedException
	 * 
	 */
	public InvestmentsPage editDataSource(String source) {

		try {

			waitForElementVisible(By.id("gwt-debug-ManagerListWidgetView-dataSourceList"), 30);
			wait(2);

			selectFromDropdown(By.id("gwt-debug-ManagerListWidgetView-dataSourceList"), source);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return this;
	}

	public InvestmentsPage simpleSearchByNameWithButton(String name) {

		this.waitForWaitingScreenNotVisible();

		waitForElementVisible(By.id("gwt-debug-ManagerListWidgetView-searchBox"), 20);
		WebElement elem = webDriver.findElement(By.id("gwt-debug-ManagerListWidgetView-searchBox"));
		new Actions(webDriver).moveToElement(elem).perform();

		waitForElementVisible(By.id("gwt-debug-ManagerListWidgetView-searchBox"), 20);

		sendKeysToElement(By.id("gwt-debug-ManagerListWidgetView-searchBox"), name);

		clickElementByKeyboard(By.id("gwt-debug-ManagerListWidgetView-searchBtn"));

		this.waitForWaitingScreenNotVisible();

		return this;

	}

	public InvestmentsPage selectInvestmentByNameNewView(String name) throws InterruptedException {
		By by = By
				.xpath(".//td[.='" + name + "']//preceding-sibling::td[//button[@class='ActionColumnButton']]//button");
		waitForElementVisible(by, 15);
		clickElement(by);

		waitForElementVisible(By.xpath(".//a[.='Select Investment']"), Settings.WAIT_SECONDS);
		clickElement(By.xpath(".//a[.='Select Investment']"));

		clickOkButtonIfVisible();
		return this;
	}

	/*
	 * 
	 * Click the arrow button beside the investment and click add to portfolio.
	 * 
	 * @param investment the name of the investment
	 * 
	 * @return {@link InvestmentsPage}
	 * 
	 * @throws InterruptedException
	 */
	public InvestmentsPage addToPortfolioByNameNewView(String name) throws InterruptedException {
		By addToPortfolio = By.xpath(".//td/a[contains(text(),'Add to portfolio')]");
		clickElement(By.xpath(
				".//td[.='" + name + "']//preceding-sibling::td[//button[@class='ActionColumnButton']]//button"));
		this.waitForElementVisible(addToPortfolio, Settings.WAIT_SECONDS);
		clickElementByKeyboard(addToPortfolio);
		this.waitForElementNotVisible(addToPortfolio, Settings.WAIT_SECONDS);
		clickOkButtonIfVisible();
		return this;
	}

	@SuppressWarnings("unchecked")
	public <T> T clickAddToPortfolioButtonNewView() throws Exception {

		if (webDriver.findElement(By.xpath(".//*[@id='gwt-debug-ManagerStrategySelectionWidget-addAllBtn']"))
				.isEnabled()) {
			clickElement(By.xpath(".//*[@id='gwt-debug-ManagerStrategySelectionWidget-addAllBtn']"));
			wait(3);
		} else {
			clickElement(By.id("gwt-debug-ManagerListDialogView-closeAnchor"));
			wait(3);
		}

		return (T) this.theClass.getDeclaredConstructor(WebDriver.class).newInstance(webDriver);
	}

	/**
	 * Add filters to investments page
	 * 
	 * @param filterNumber
	 *            The number of filter in investments page: 1- 14 1= All Types
	 *            ,2= All Asset Classes ... Count from left to right, top to
	 *            bottom
	 * @param optionName
	 * @throws InterruptedException
	 */
	public void addFilterForInvestment(String filterNumber, String... optionNames) throws InterruptedException {

		scrollToTop();

		try {
			waitForElementPresent(By.xpath("(.//a[@id='gwt-debug-ManagerListItem-strategyName'])[2]"), 60);
		} catch (Exception e) {

		}
		String textOfFilterBeforeSorting = getTextByLocator((By
				.xpath("(.//div[@class='fundExplorerFilterDropdowns']//input[@id='gwt-debug-MultiSelectDropDownBox-textBox'])["
						+ filterNumber + "]")));

		clickElement(By.xpath("(.//div[@class='fundExplorerFilterDropdowns']//button)[" + filterNumber + "]/i"));
		scrollToTop();
		for (String optionName : optionNames) {
			clickElement(By.xpath("//span[label='" + optionName + "']/input"));
		}
		// wait 5 second for refreshing result
		wait(Settings.WAIT_SECONDS);
		this.waitForWaitingScreenNotVisible();
		try {
			waitForElementPresent(By.xpath("(.//a[@id='gwt-debug-ManagerListItem-strategyName'])[2]"), 60);
		} catch (Exception e) {

		}
		String textOfFilterAfterSorting = getTextByLocator((By
				.xpath("(.//div[@class='fundExplorerFilterDropdowns']//input[@id='gwt-debug-MultiSelectDropDownBox-textBox'])["
						+ filterNumber + "]")));

		assertFalse(textOfFilterBeforeSorting.equals(textOfFilterAfterSorting));
	}

	public void clickResetFilterButton() {

		clickElement(By.id("gwt-debug-FilterPanel-ResetButton"));
		// WebElement resetBtn = webDriver.findElement(locatorForResetButton);
		// new Actions(webDriver).moveToElement(resetBtn);
		// resetBtn.sendKeys(Keys.RETURN);
		// waitForElementVisible(locatorForResetButton, 30);
		// clickElement(locatorForResetButton);
		this.waitForWaitingScreenNotVisible();
		waitForElementVisible(By.id("gwt-debug-FilterPanel-ResetButton"), 30);
	}
}
