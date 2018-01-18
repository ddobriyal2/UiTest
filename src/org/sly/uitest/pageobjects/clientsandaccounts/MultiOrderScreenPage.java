package org.sly.uitest.pageobjects.clientsandaccounts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;

/**
 * 
 * @author Benny Leung
 * @date : 03 Mar, 2017
 * @company Prive Financial
 *
 */
public class MultiOrderScreenPage extends AbstractPage {

	public MultiOrderScreenPage(WebDriver webDriver) throws InterruptedException {
		super();
		this.webDriver = webDriver;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(60, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("gwt-debug-TradeForSelectedAccountsView-searchPanel")));

	}

	public MultiOrderScreenPage editISIN(String isin) {
		sendKeysToElement(By.id("gwt-debug-SearchStrategyWidgetView-assetIdentifierBox"), isin);

		clickElement(By.id("gwt-debug-SearchStrategyWidgetView-assetNameLabel"));
		return this;
	}

	/**
	 * Input the buy value by account
	 * 
	 * @param account
	 * @param buyValue
	 * @return
	 */
	public MultiOrderScreenPage editBuyValueByAccount(String account, String buyValue) {
		sendKeysToElement(By.xpath(".//*[contains(text(),'" + account + "')]//following-sibling::td[5]//input"),
				buyValue);

		clickElement(By.xpath(".//*[contains(text(),'" + account + "')]"));
		return this;
	}

	/**
	 * Click the increase button to increase buy value by account
	 * 
	 * @param account
	 * @param clicks
	 * @return
	 */
	public MultiOrderScreenPage clickIncreaseButtonForBuyValueByAccount(String account, int clicks) {
		for (int i = 0; i < clicks; i++) {
			clickElement(By.xpath(".//td[*[*[contains(text(),'" + account
					+ "')]]]//following-sibling::td[6]//button[@title='more']"));
		}
		return this;
	}

	/**
	 * Click the decrease button to decrease buy value by account
	 * 
	 * @param account
	 * @param clicks
	 * @return
	 */
	public MultiOrderScreenPage clickDecreaseButtonForSellValueByAcconut(String account, int clicks) {
		for (int i = 0; i < clicks; i++) {
			clickElement(By.xpath(
					".//*[contains(text(),'" + account + "')]//following-sibling::td[6]//button[@title='less']"));
		}
		return this;
	}

	/**
	 * Input the sell value by account
	 * 
	 * @param account
	 * @param sellValue
	 * @return
	 */
	public MultiOrderScreenPage editSellValueByAccount(String account, String sellValue) {
		sendKeysToElement(By.xpath(".//*[contains(text(),'" + account + "')]//following-sibling::td[7]//input"),
				sellValue);
		clickElement(By.xpath(".//*[contains(text(),'" + account + "')]"));
		return this;
	}

	/**
	 * Click the increase button to increase sell value by account
	 * 
	 * @param account
	 * @param clicks
	 * @return
	 */
	public MultiOrderScreenPage clickIncreaseButtonForSellValueByAccount(String account, int clicks) {
		for (int i = 0; i < clicks; i++) {
			clickElement(By.xpath(
					".//*[contains(text(),'" + account + "')]//following-sibling::td[8]//button[@title='more']"));
		}
		return this;
	}

	/**
	 * Click the decrease button to decrease sell value by account
	 * 
	 * @param account
	 * @param clicks
	 * @return
	 */
	public MultiOrderScreenPage clickDecreaseButtonForSellValueByAccount(String account, int clicks) {
		for (int i = 0; i < clicks; i++) {
			clickElement(By.xpath(
					".//*[contains(text(),'" + account + "')]//following-sibling::td[8]//button[@title='less']"));
		}
		return this;
	}

	/**
	 * Edit Order Quantity Type of Multi Orders
	 * 
	 * @param type
	 * @return
	 */
	public MultiOrderScreenPage editOrderQuatityType(String type) {
		selectFromDropdown(By.id("gwt-debug-TradeForSelectedAccountsView-qtyTypeBox"), type);
		return this;
	}

	/**
	 * Edit Order Type of Multi Orders
	 * 
	 * @param type
	 * @return
	 */
	public MultiOrderScreenPage editOrderType(String type) {
		selectFromDropdown(By.id("gwt-debug-TradeForSelectedAccountsView-orderTypeBox"), type);
		return this;
	}

	/**
	 * Edit validity of Multi Orders
	 * 
	 * @param validity
	 * @return
	 */
	public MultiOrderScreenPage editValidFor(String validity) {
		selectFromDropdown(By.id("gwt-debug-TradeForSelectedAccountsView-orderValidityBox"), validity);
		return this;
	}

	/**
	 * Click button to send orders to pre trade queue
	 * 
	 * @return
	 */
	public RecentOrdersPage clickSendOrdersToPreTradeQueue() {
		clickElement(By.id("gwt-debug-TradeForSelectedAccountsView-toRecentOrdersOverviewButton"));

		clickYesButtonIfVisible();
		clickYesButtonIfVisible();
		clickYesButtonIfVisible();
		return new RecentOrdersPage(webDriver);
	}

	/**
	 * Click button to send orders to pre trade queue for check field
	 * 
	 * @return
	 */
	public MultiOrderScreenPage clickSendOrdersToPreTradeQueueForCheckField() {
		clickElement(By.id("gwt-debug-TradeForSelectedAccountsView-toRecentOrdersOverviewButton"));
		return this;
	}

	public MultiOrderScreenPage checkAllAccounts(boolean checked) {
		By by = By.xpath(".//td[contains(text(),'Client')]//preceding-sibling::td//span//input");
		WebElement elem = webDriver.findElement(by);
		clickElement(by);

		setCheckboxStatus2(elem, checked);

		return this;
	}

	/**
	 * check the checkbox by account
	 * 
	 * @param account
	 * @param checked
	 * @return
	 */
	public MultiOrderScreenPage clickCheckboxByAccount(String account, boolean checked) {
		WebElement elem = webDriver
				.findElement(By.xpath(".//*[contains(text(),'" + account + "')]//preceding-sibling::td[2]//input"));

		setCheckboxStatus(elem, checked);
		return this;
	}

}
