package org.sly.uitest.pageobjects.clientsandaccounts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.settings.Settings;

/**
 * 
 * This class represents the Recent Orders Page (tab) of an account, which can
 * be navigated by clicking 'Clients' -> 'Account Overview' -> choose any
 * account -> 'Recent Orders (tab)'
 * 
 * @author Lynne Huang
 * @date : 7 Oct, 2015
 * @company Prive Financial
 */
public class RecentOrdersPage extends AbstractPage {

	/**
	 * @param webDriver
	 */
	public RecentOrdersPage(WebDriver webDriver) {

		super();
		this.webDriver = webDriver;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("gwt-debug-OrdersTableWidgetView-preTradeOrdersButtonsPanel")));

		// assertTrue(pageContainsStr(" Cashflow Schedules "));
	}

	/**
	 * Click Lock button for the given open order
	 * 
	 * @return {@link RecentOrdersPage}
	 */
	public RecentOrdersPage clickLockButtonOfTheOpenOrder(String order) {

		clickElement(By
				.xpath("//td[div[contains(text(),'" + order + "')]]/following-sibling::td[button]//button[.='Lock']"));

		return this;
	}

	/**
	 * Click Unlock button for the given open order
	 * 
	 * @return {@link RecentOrdersPage}
	 */
	public RecentOrdersPage clickUnlockButtonOfTheOpenOrder(String order) {

		clickElement(By.xpath("//td[.='" + order + "']/following-sibling::td[button]//button[.='Unlock']"));

		return this;
	}

	/**
	 * Click Confirm button for the given open order
	 * 
	 * @return {@link RecentOrdersPage}
	 */
	public RecentOrdersPage clickConfirmButtonOfTheOpenOrder(String order) {

		clickElement(By.xpath(
				"//td[div[contains(text(),'" + order + "')]]/following-sibling::td[button]//button[.='Confirm']"));

		return this;
	}

	/**
	 * Enter a price for the given open order
	 * 
	 * @return {@link RecentOrdersPage}
	 */
	public RecentOrdersPage clickEnterButtonOfTheOrder(String order) {

		waitForElementVisible(
				By.xpath(
						"//td[div[contains(text(),'" + order + "')]]/following-sibling::td[button]//button[.='Enter']"),
				10);

		clickElement(By
				.xpath("//td[div[contains(text(),'" + order + "')]]/following-sibling::td[button]//button[.='Enter']"));

		// clickElement(By.xpath("//td[contains(text(),'" + order
		// + "')]/following-sibling::td[button]//button[.='Enter']"));

		return this;
	}

	/**
	 * Click Confirm Order Filled button for the given order
	 * 
	 * @return {@link RecentOrdersPage}
	 */
	public RecentOrdersPage editPriceAndClickConfirmOrderFilledButton(String order, String price) {
		String transaction = "";
		int locator = 1;

		int size = getSizeOfElements(
				By.xpath(".//*[@id='gwt-debug-OrdersForInvestorAccountSingleView-ordersTable']//td[4]"));

		for (int i = 1; i <= size; i++) {
			transaction = getTextByLocator(
					By.xpath("(.//*[@id='gwt-debug-OrdersForInvestorAccountSingleView-ordersTable']//td[4])["
							+ String.valueOf(i) + "]"));
			if (transaction.contains(order)) {
				locator = i;
			}
		}

		sendKeysToElement(By.xpath("(//table[//td[contains(text(),'" + order
				+ "')]]/following-sibling::div[@id='gwt-debug-OrdersForInvestorAccountSingleView-manualPanel']//input)["
				+ String.valueOf(locator) + "]"), price);

		clickElement(By.xpath("(//table[//td[contains(text(),'" + order
				+ "')]]/following-sibling::div[@id='gwt-debug-OrdersForInvestorAccountSingleView-manualPanel']//button[.='Confirm Order Filled'])["
				+ String.valueOf(locator) + "]"));

		return this;
	}

	public RecentOrdersPage goToHistoricalOrders() {

		waitForElementVisible(By.xpath(".//*[contains(text(),'Historical Orders')]"), 10);

		clickElement(By.xpath(".//*[contains(text(),'Historical Orders')]"));

		return this;
	}

	/**
	 * click the button refresh icon beside Open Orders to refresh orders
	 * 
	 * @return {@RecentOrdersPage}
	 */
	public RecentOrdersPage clickRefereshOrderButton() {
		clickElement(By.id("gwt-debug-OrdersTableWidgetView-refreshBtn"));
		this.waitForWaitingScreenNotVisible();
		return this;
	}

	/**
	 * click cancel order button in recent orders page
	 * 
	 * @return {@RecentOrdersPage}
	 */
	public RecentOrdersPage clickCancelOrderButton() {
		By by = By.xpath(".//*[contains(@id,'waitScreen') and contains(@style,'display: none')]");

		if (isElementVisible(By.id("gwt-debug-OrdersTableWidgetView-preTradeOrdersNoInfoPanel"))) {
			clickElement(By.id("gwt-debug-OrdersTableWidgetView-cancelBtn"));

			clickYesButtonIfVisible();
			clickYesButtonIfVisible();
			clickOkButtonIfVisible();

			for (int i = 0; i < Settings.ATTEMPT_LOOPING_NUMBER; i++) {
				if (!isElementPresent(by)) {
					this.waitForWaitingScreenNotVisible();
				}

			}

		} else {
			log("Orders not found");
		}

		return this;
	}

	/**
	 * Click Combine To Blockorder button in recent orders page
	 * 
	 * @return {@RecentOrdersPage}
	 */
	public RecentOrdersPage clickCombineToBlockorderButton() {
		clickElement(By.id("gwt-debug-OrdersTableWidgetView-combineToBlockOrderBtn"));
		clickYesButtonIfVisible();
		clickElement(By.id("gwt-debug-BlockOrdersConfirmationDialogView-okBtn"));
		this.waitForWaitingScreenNotVisible();
		return this;
	}

	public RecentOrdersPage clickCombineToBlockorderButtonForCheckingFields() {
		clickElement(By.id("gwt-debug-OrdersTableWidgetView-combineToBlockOrderBtn"));
		return this;
	}

	/**
	 * Click Break-up Blockorder button in recent orders page
	 * 
	 * @return {@RecentOrdersPage}
	 */
	public RecentOrdersPage clickBreakupBlockorderButton() {
		clickElement(By.id("gwt-debug-OrdersTableWidgetView-splitBlockOrderBtn"));
		clickYesButtonIfVisible();
		return this;
	}

	/**
	 * Click Place For Execution button in recents orders page
	 * 
	 * @return {@RecentOrdersPage}
	 */
	public RecentOrdersPage clickPlaceForExecution() {
		clickElement(By.id("gwt-debug-OrdersTableWidgetView-placeExecutionBtn"));
		return this;
	}

	public TradeOrderFormPage clickEditButtonOfOrder(String side, String orderType, String instrumentName,
			String clientAcct) {
		clickElement(By.xpath(".//td[*[*[.='" + side + "']]]/following-sibling::td[*[*[.='" + orderType
				+ "']]]/following-sibling::td[*[*[contains(text(),'" + instrumentName
				+ "')]]]/following-sibling::td[*[*[.='" + clientAcct
				+ "']]]//following::td[button[@id='gwt-debug-PreTradeOrdersRowWidgetView-editBtn']]"));

		return new TradeOrderFormPage(webDriver, RecentOrdersPage.class);

	}

	/**
	 * Return if the order exists in Pre Trade Queue
	 * 
	 * @param side
	 * @param orderType
	 * @param instrumentName
	 * @param clientAcct
	 * @return
	 */
	public boolean isOrderVisibleInPreTradeQueue(String side, String orderType, String instrumentName,
			String clientAcct, int size) {
		this.waitForWaitingScreenNotVisible();
		return isElementVisible(By.xpath("(.//td[*[*[.='" + side + "']]]/following-sibling::td[*[*[.='" + orderType
				+ "']]]/following-sibling::td[*[*[contains(text(),'" + instrumentName
				+ "')]]]/following-sibling::td[*[*[.='" + clientAcct + "']]])[" + String.valueOf(size) + "]"));
	}

	/**
	 * Check the check box next to order
	 * 
	 * @param side
	 * @param orderType
	 * @param instrumentName
	 * @param clientAcct
	 * @return
	 */
	public RecentOrdersPage selectOrder(String side, String orderType, String instrumentName, String clientAcct) {

		By by = By.xpath(".//td[*[*[.='" + side + "']]]/following-sibling::td[*[*[.='" + orderType
				+ "']]]/following-sibling::td[*[*[contains(text(),'" + instrumentName
				+ "')]]]/following-sibling::td[*[*[.='" + clientAcct + "']]]//parent::tr//td//span//input");
		waitForElementVisible(by, 30);
		WebElement elem = webDriver.findElement(by);

		this.setCheckboxStatus2(elem, true);
		return this;
	}

	public RecentOrdersPage selectAllOrders(boolean select) {
		this.waitForWaitingScreenNotVisible();
		try {
			this.waitForWaitingScreenNotVisible();
			waitForElementVisible(By.id("gwt-debug-OrdersTableWidgetView-checkAllBox-input"), 5);
			WebElement elem = webDriver.findElement(By.id("gwt-debug-OrdersTableWidgetView-checkAllBox-input"));
			elem.click();
			this.setCheckboxStatus2(elem, select);
		} catch (TimeoutException e) {

		}

		return this;
	}
}
