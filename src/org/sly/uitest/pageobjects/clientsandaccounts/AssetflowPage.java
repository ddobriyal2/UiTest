package org.sly.uitest.pageobjects.clientsandaccounts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.pageobjects.investments.InvestmentsPage;

/**
 * This page is for editing transaction where type = "Asset Flow"
 * 
 * @author Benny Leung
 * @date Nov 1, 2016
 * @company Prive Financial
 */
public class AssetflowPage extends AbstractPage {
	public AssetflowPage(WebDriver webDriver) {

		super();
		this.webDriver = webDriver;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.id("gwt-debug-AssetflowTransactionEditPopupView-dialogBox-content")));

		} catch (TimeoutException ex) {

		}

	}

	/**
	 * Edit quantity of assetflow, in terms of unit
	 * 
	 * @param quantity
	 * @return {@link AssetflowPage}
	 */
	public AssetflowPage editQuantityUnit(String quantity) {

		WebElement elem = webDriver.findElement(By.id("gwt-debug-AssetflowTransactionEditPopupView-quantityBox"));
		elem.clear();
		elem.sendKeys(Keys.BACK_SPACE, quantity, Keys.ENTER);
		clickElement(By.id("gwt-debug-AssetflowTransactionEditPopupView-statusBox"));
		return this;
	}

	/**
	 * Edit quantity of assetflow, in terms of value
	 * 
	 * @param quantity
	 * @return
	 */
	public AssetflowPage editQuantityValue(String quantity) {
		WebElement elem = webDriver.findElement(By.id("gwt-debug-AssetflowTransactionEditPopupView-valueBox"));
		elem.clear();
		elem.sendKeys(Keys.BACK_SPACE, quantity, Keys.ENTER);
		clickElement(By.id("gwt-debug-AssetflowTransactionEditPopupView-statusBox"));

		return this;
	}

	/**
	 * Click save button for assetflow dialog
	 * 
	 * @return {@HistoryPage}
	 * @throws InterruptedException
	 */
	public HistoryPage clickSaveButton() throws InterruptedException {
		clickElement(By.id("gwt-debug-AssetflowTransactionEditPopupView-saveBtn"));

		clickOkButtonIfVisible();
		return new HistoryPage(webDriver);
	}

	public HistoryPage clickCancelButton() throws InterruptedException {
		clickElement(By.id("gwt-debug-AssetflowTransactionEditPopupView-cancelBtn"));
		return new HistoryPage(webDriver);
	}

	/**
	 * Edit exeucation date of assetflow
	 * 
	 * @param executionDate
	 * @return {@link AssetflowPage}
	 */
	public AssetflowPage editExecutionDate(String executionDate) {
		waitForElementVisible(By.id("gwt-debug-AssetflowTransactionEditPopupView-executionDate"), 30);
		sendKeysToElement(By.id("gwt-debug-AssetflowTransactionEditPopupView-executionDate"), executionDate);
		return this;
	}

	/**
	 * Edit settlement date of assetflow
	 * 
	 * @param settlementDate
	 * @return {@link AssetflowPage}
	 */
	public AssetflowPage editSettlementDate(String settlementDate) {
		waitForElementVisible(By.id("gwt-debug-AssetflowTransactionEditPopupView-settlementDate"), 30);
		sendKeysToElement(By.id("gwt-debug-AssetflowTransactionEditPopupView-settlementDate"), settlementDate);
		return this;
	}

	/**
	 * Edit assetflow transaction
	 * 
	 * @param direction
	 * @return {@link AssetflowPage}
	 */
	public AssetflowPage editDirection(String direction) {
		waitForElementVisible(By.id("gwt-debug-AssetflowTransactionEditPopupView-InOrOutflow"), 30);
		selectFromDropdown(By.id("gwt-debug-AssetflowTransactionEditPopupView-InOrOutflow"), direction);
		return this;
	}

	/**
	 * Edit assetflow value
	 * 
	 * @param value
	 * @return
	 */
	public AssetflowPage editValue(String value) {
		waitForElementVisible(By.id("gwt-debug-AssetflowTransactionEditPopupView-valueBox"), 30);
		sendKeysToElement(By.id("gwt-debug-AssetflowTransactionEditPopupView-valueBox"), value);
		return this;
	}

	/**
	 * Add assetflow asset
	 * 
	 * @param investment
	 * @return {@link AssetflowPage}
	 * @throws InterruptedException
	 */
	public AssetflowPage addAsset(String investment) throws InterruptedException {
		clickElement(By.xpath(".//*[contains(@id,'assetFind')]"));
		InvestmentsPage investmentPage = new InvestmentsPage(webDriver);
		investmentPage.simpleSearchByName(investment).selectInvestmentByName(investment);
		waitForElementVisible(By.id("gwt-debug-AssetflowTransactionEditPopupView-assetLabel"), 30);
		return this;
	}

	public AssetflowPage editStatus(String status) {
		waitForElementVisible(By.id("gwt-debug-AssetflowTransactionEditPopupView-statusBox"), 10);
		selectFromDropdown(By.id("gwt-debug-AssetflowTransactionEditPopupView-statusBox"), status);
		return this;
	}
}
