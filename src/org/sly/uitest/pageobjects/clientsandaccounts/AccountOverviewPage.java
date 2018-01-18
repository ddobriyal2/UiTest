package org.sly.uitest.pageobjects.clientsandaccounts;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.pageobjects.abstractpage.AdvancedSearchPage;
import org.sly.uitest.pageobjects.companyadmin.EmailTemplatePage;
import org.sly.uitest.settings.Settings;

/**
 * This class represents the Accounts Overview Page, which can be navigated by
 * clicking 'Clients' -> 'Account Overview'
 * 
 * URL: http://192.168.1.104:8080/SlyAWS/?locale=en_US#invAccountList
 * 
 * @author Lynne Huang
 * @date : 6 Aug, 2015
 * @company Prive Financial
 * 
 */
public class AccountOverviewPage extends AbstractPage {

	/**
	 * @param webDriver
	 * @throws InterruptedException
	 */
	public AccountOverviewPage(WebDriver webDriver) {

		super();
		this.webDriver = webDriver;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> waitNoInfo = new FluentWait<WebDriver>(webDriver).withTimeout(5, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		try {
			FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(60, TimeUnit.SECONDS)
					.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gwt-debug-MyMainMaterialView-mainPanel")));

		} catch (Exception e) {
			waitNoInfo.until(
					ExpectedConditions.visibilityOfElementLocated(By.id("gwt-debug-InvestorAccountTable-noInfoPanel")));
		}
		// this.waitForWaitingScreenNotVisible();
	}

	/**
	 * Click the given client name, the page will be navigated to the Client
	 * Detail page by default
	 * 
	 * @param name
	 *            the name of the client
	 * 
	 * @return {@link DetailPage}
	 */
	public DetailPage goToClientDetailPageByName(String name) throws InterruptedException {
		scrollToTop();
		clickElement(By.xpath(".//*[@id='gwt-debug-InvestorAccountTable-linkPersonName' and .='" + name + "']"));
		this.waitForWaitingScreenNotVisible();
		return new DetailPage(webDriver);
	}

	/**
	 * Click the given account name, the page will be navigated to the account
	 * Detail page if set
	 * 
	 * @param name
	 *            the name of the account
	 * 
	 * @return DetailPage
	 */
	public DetailPage goToAccountDetailPageByName(String name) throws InterruptedException {
		this.waitForWaitingScreenNotVisible();
		clickNextPageUntilFindElement(By.id("gwt-debug-InvestorAccountTable-linkPortfolioName"),
				By.xpath(".//*[@id='gwt-debug-InvestorAccountTable-linkPortfolioName' and .='" + name + "']"));

		clickElement(By.xpath(".//*[@id='gwt-debug-InvestorAccountTable-linkPortfolioName' and .='" + name + "']"));

		return new DetailPage(webDriver);
	}

	/**
	 * Click the given account name, the page will be navigated to the page that
	 * is set by the User Preference
	 * 
	 * @param name
	 *            the name of the account
	 * @param returnClass
	 *            the page that will be navigated to, which is set by the
	 *            Account Detail Default Tab
	 * 
	 * @return DetailPage
	 */
	@SuppressWarnings("unchecked")
	public <T> T goToAccountDefaultPageByName(String name, Class<?> returnClass) throws Exception {

		clickElement(By.xpath(".//*[@id='gwt-debug-InvestorAccountTable-linkPortfolioName' and .='" + name + "']"));

		try {
			return (T) returnClass.getDeclaredConstructor(WebDriver.class).newInstance(webDriver);
		} catch (InvocationTargetException e) {
			return (T) returnClass.getDeclaredConstructor(WebDriver.class);
		}
	}

	/**
	 * Click the given account name, the page will be navigated to the account
	 * Holdings by default (most cases)
	 * 
	 * @param name
	 *            the name of the account
	 * 
	 * @return {@link HoldingsPage}
	 */
	public HoldingsPage goToAccountHoldingsPageByName(String name) throws InterruptedException {
		try {
			// this.checkIncludeInactiveAccounts(true);
			this.checkIncludeNonAssetAccounts(true);
		} catch (ElementNotVisibleException e) {

		}
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(60, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);
		try {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.id("gwt-debug-InvestorAccountTable-accountTablePanel")));
		} catch (TimeoutException e) {

		}
		scrollToTop();
		// waitForElementVisible(By.id("gwt-debug-InvestorAccountTable-linkPortfolioName"),60);
		clickNextPageUntilFindElement(By.id("gwt-debug-InvestorAccountTable-linkPortfolioName"),
				By.xpath(".//*[@id='gwt-debug-InvestorAccountTable-linkPortfolioName' and .='" + name + "']"));

		clickElement(By.xpath(".//*[@id='gwt-debug-InvestorAccountTable-linkPortfolioName' and .='" + name + "']"));
		handleAlert();
		if (pageContainsStr("Portfolio Holding Information Not Available")) {
			clickOkButtonIfVisible();
		} else {
			scrollToTop();
			clickElement(By.xpath("//a[@id='gwt-debug-PortfolioOverviewTab-hyperlink' and .='Holdings']"));
			handleAlert();
			clickOkButtonIfVisible();
			this.waitForWaitingScreenNotVisible();
		}

		return new HoldingsPage(webDriver);
	}

	/**
	 * Click the downward triangle icon to open the Advanced Search Panel in the
	 * Account Overview page
	 * 
	 * @return AdvancedSearchPage
	 */
	public AdvancedSearchPage goToAdvancedSearchPage() throws InterruptedException {
		scrollToTop();
		waitForElementVisible(By.id("gwt-debug-InvestorAccountOverviewView-advancedSearchBtn"), 30);

		clickElement(By.id("gwt-debug-InvestorAccountOverviewView-advancedSearchBtn"));

		waitForElementVisible(By.id("gwt-debug-AdvancedSearchPanel-tabPanel"), 60);

		return new AdvancedSearchPage(webDriver, AccountOverviewPage.class);

	}

	/**
	 * Simply input the string into the search box
	 * 
	 * @param string
	 *            the string to search, such as client name, account name
	 * @return {@link AccountOverviewPage}
	 * @throws InterruptedException
	 */
	public AccountOverviewPage simpleSearchByString(String string) throws InterruptedException {
		scrollToTop();

		waitForElementVisible(By.id("gwt-debug-InvestorAccountOverviewView-searchBox"), 10);

		// try {
		// this.checkIncludeInactiveAccounts(true);
		// } catch (ElementNotVisibleException e) {
		//
		// }

		sendKeysToElement(By.id("gwt-debug-InvestorAccountOverviewView-searchBox"), string);
		clickElement(By.id("gwt-debug-InvestorAccountOverviewView-searchImg"));

		return this;

	}

	/**
	 * Click the cross icon besides the search box to clear the previous search
	 * 
	 * @return {@link AccountOverviewPage}
	 * @throws InterruptedException
	 */
	public AccountOverviewPage clickClearSearchIcon() throws InterruptedException {

		wait(Settings.WAIT_SECONDS);

		try {

			waitForElementVisible(By.id("gwt-debug-InvestorAccountOverviewView-clearImg"), 15);

			if (isElementVisible(By.id("gwt-debug-InvestorAccountOverviewView-clearImg"))) {

				clickElement(By.id("gwt-debug-InvestorAccountOverviewView-clearImg"));
			}
		} catch (Exception e) {

		}

		wait(Settings.WAIT_SECONDS);

		return this;
	}

	/**
	 * Click the EMAIL CLIENT button if the module toggle is turned on, and the
	 * page will be navigated to {@link EmailTemplatePage}
	 * 
	 * @return {@link EmailTemplatePage}
	 */
	public EmailTemplatePage clickEmailClientButton() throws InterruptedException {

		clickElement(By.id("gwt-debug-InvestorAccountOverviewView-emailBtn"));

		return new EmailTemplatePage(webDriver);
	}

	/**
	 * Click BULK OPERATIONS button to do the bulk operation, and the page will
	 * be navigated to the Bulk Operation Page
	 * 
	 * @return EditPage
	 */
	public BulkOperationPage clickBulkOperationsButton() throws InterruptedException {

		waitForElementVisible(By.id("gwt-debug-InvestorAccountOverviewView-doBulkOperationBtn"), 30);

		clickElement(By.id("gwt-debug-InvestorAccountOverviewView-doBulkOperationBtn"));

		// clickElement(By.id("gwt-debug-NewClientAccountView-clientButton"));
		clickYesButtonIfVisible();

		return new BulkOperationPage(webDriver);
	}

	/**
	 * Click NEW CLIENT/ACCOUNT button and click NEW CLIENT in the popup dialog,
	 * and the page will be navigated to Edit Page
	 * 
	 * @return EditPage
	 */
	public DetailEditPage clickNewClientAccountButton() throws InterruptedException {

		clickElement(By.id("gwt-debug-InvestorAccountOverviewView-addInvestorBtn"));

		try {
			clickElement(By.id("gwt-debug-NewClientAccountView-clientButton"));
		} catch (Exception e) {
		}

		return new DetailEditPage(webDriver, ClientOverviewPage.class);
	}

	/**
	 * Click NEW CLIENT/ACCOUNT button, click NEW ACCOUNT in the popup dialog,
	 * and click the accountType The page will be navigated to Edit Page
	 * 
	 * @return DetailEditPage
	 */
	public DetailEditPage clickNewAccountButton(String accountType) throws InterruptedException {

		waitForElementVisible(By.id("gwt-debug-InvestorAccountOverviewView-addInvestorBtn"), 10);
		wait(1);
		clickElement(By.id("gwt-debug-InvestorAccountOverviewView-addInvestorBtn"));

		try {
			clickElement(By.id("gwt-debug-NewClientAccountView-accountButton"));

			clickElement(By.xpath("//button[.='" + accountType + "']"));

			clickNoButtonIfVisible();

		} catch (Exception e) {
		}

		return new DetailEditPage(webDriver, DetailPage.class);
	}

	/**
	 * click Trade button to enter MultiOrderScreenPage
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public MultiOrderScreenPage clickTradeButton() throws InterruptedException {
		clickElement(By.id("gwt-debug-InvestorAccountOverviewView-doTradeBtn"));
		return new MultiOrderScreenPage(webDriver);
	}

	/**
	 * Check or uncheck the checkbox besides the account
	 * 
	 * @param account
	 *            the name of the account
	 * 
	 * @return {@link AccountOverviewPage}
	 */
	public AccountOverviewPage selectSingleAccount(String account) throws InterruptedException {

		WebElement accountCheckbox = waitGet(
				By.xpath("//*[.='" + account + "']//ancestor::td/preceding-sibling::td[2]/span/input"));

		setCheckboxStatus2(accountCheckbox, true);

		return this;

	}

	/**
	 * Check or uncheck the checkbox besides 'Client Name' to select or deselect
	 * all accounts
	 * 
	 * @param selected
	 *            if true, select all accounts; if false deselect all accounts
	 * 
	 * @return {@link AccountOverviewPage}
	 */
	public AccountOverviewPage selectAllAccounts(Boolean selected) throws InterruptedException {

		By by = By.xpath("//td[.='Client Name']/preceding-sibling::td[1]/span/input");

		waitForElementVisible(by, Settings.WAIT_SECONDS * 2);
		WebElement accountCheckbox = waitGet(by);
		clickElement(by);
		setCheckboxStatus2(accountCheckbox, selected);

		try {
			waitForElementVisible(By.xpath(".//input[contains(@id,'checkbox') and @checked]"), Settings.WAIT_SECONDS);
		} catch (TimeoutException e) {
			setCheckboxStatus2(accountCheckbox, selected);
		}
		return this;

	}

	/**
	 * Check or uncheck the checkbox of Include Non-Asset Accounts
	 * 
	 * @param included
	 *            if true, include non-asset accounts; if false, not include
	 * 
	 * @return {@link AccountOverviewPage}
	 */
	public AccountOverviewPage checkIncludeNonAssetAccounts(Boolean included) {

		WebElement we = webDriver
				.findElement(By.id("gwt-debug-InvestorAccountOverviewView-showNonAssetAccounts-input"));

		setCheckboxStatus2(we, included);

		return this;

	}

	/**
	 * Check or uncheck the checkbox of Include Inactive Accounts
	 * 
	 * @param toInclude
	 *            if true, include inactive accounts; if false, not include
	 * 
	 * @return {@link AccountOverviewPage}
	 */
	public AccountOverviewPage checkIncludeInactiveAccounts(Boolean toInclude) {

		WebElement we = webDriver.findElement(By.id("gwt-debug-InvestorAccountOverviewView-showAllAccounts-input"));

		setCheckboxStatus2(we, toInclude);

		return this;

	}

	/**
	 * Select a format for exporting the search result, and then click the XLS
	 * icon beside
	 * 
	 * @param select
	 *            the format of the search result
	 * @param file
	 *            the name of the search result
	 * 
	 * @return {@link AccountOverviewPage}
	 * @throws InterruptedException
	 */
	public AccountOverviewPage exportSearchResult(String type, String file) throws InterruptedException {

		selectFromDropdown(By.id("gwt-debug-ExportFileWidget-exportFormatListBox"), type);

		clickElement(By.id("gwt-debug-ExportFileWidget-iconXls"));
		clickYesButtonIfVisible();

		this.waitForElementVisible(By.xpath("//button[.='Proceed']"), Settings.WAIT_SECONDS);

		clickYesButtonIfVisible();

		waitForElementVisible(By.id("gwt-debug-ExportSearchResultPopupView-fileNameTextBox"), 10);
		for (int i = 0; i < Settings.ATTEMPT_LOOPING_NUMBER; i++) {
			if (!isElementVisible(By.id("gwt-debug-ExportSearchResultPopupView-fileNameTextBox"))) {
				clickElement(By.id("gwt-debug-ExportFileWidget-iconXls"));

				this.waitForElementVisible(By.id("gwt-debug-CustomDialog-yesButton"), Settings.WAIT_SECONDS);

				clickYesButtonIfVisible();
			}
		}

		sendKeysToElement(By.id("gwt-debug-ExportSearchResultPopupView-fileNameTextBox"), file);

		clickElement(By.id("gwt-debug-ExportSearchResultPopupView-proceedButton"));

		clickOkButtonIfVisible();

		return this;

	}

	/**
	 * Test if the chart panel is displayed in the account overview page
	 * 
	 * @return true: if the chart is displayed; false: if the chart is not
	 *         displayed
	 */
	public boolean chartPanelIsNotDisplayed() {

		try {

			WebElement wel = waitGet(By.id("gwt-debug-InvestorAccountOverviewView-chartPanel"));

			return wel.getAttribute("style").contains("display: none");

		} catch (org.openqa.selenium.TimeoutException e) {

			return true;
		}

	}

	public HoldingsPage goToAccountHoldingsPageByNameHoldingPageNoData(String name) throws InterruptedException {

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(300, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("gwt-debug-InvestorAccountTable-accountTablePanel")));

		clickNextPageUntilFindElement(By.id("gwt-debug-InvestorAccountTable-linkPortfolioName"),
				By.xpath(".//*[@id='gwt-debug-InvestorAccountTable-linkPortfolioName' and .='" + name + "']"));

		clickElement(By.xpath(".//*[@id='gwt-debug-InvestorAccountTable-linkPortfolioName' and .='" + name + "']"));

		if (pageContainsStr("Portfolio Holding Information Not Available")) {
			clickOkButtonIfVisible();
		} else {
			goToDetailsPage();
		}

		return new HoldingsPage(webDriver);
	}

	public AccountOverviewPage simpleSearchByStringByAction(String string) throws InterruptedException {
		scrollToTop();
		sendKeysToElementByAction(By.id("gwt-debug-InvestorAccountOverviewView-searchBox"), string);
		clickElement(By.id("gwt-debug-InvestorAccountOverviewView-searchImg"));

		return this;

	}

	/**
	 * This is to select the mode of account overview page
	 * 
	 * @param mode
	 * @return
	 */
	public AccountOverviewPage editViewMode(String mode) {
		selectFromDropdown(By.id("gwt-debug-InvestorAccountOverviewView-showValueListBox"), mode);
		return this;
	}
}
