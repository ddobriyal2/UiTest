package org.sly.uitest.pageobjects.clientsandaccounts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.pageobjects.investments.InvestmentsPage;
import org.sly.uitest.settings.Settings;

/**
 * This is the page for instrument distribution transaction details.
 * 
 * @author Benny Leung
 * @date Oct 28, 2016
 * @company Prive Financial
 */
public class InstrumentDistributionPage extends AbstractPage {

	public InstrumentDistributionPage(WebDriver webDriver) {

		super();
		this.webDriver = webDriver;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		try {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.id("gwt-debug-TransactionInstDistEditPopupView-dialogBox-content")));

		} catch (TimeoutException ex) {

		}

	}

	/**
	 * edit type of instrument distribution
	 * 
	 * @param type
	 * @return {@link InstrumentDistributionPage}
	 */
	public InstrumentDistributionPage editType(String type) {

		waitForElementVisible(By.id("gwt-debug-TransactionInstDistEditPopupView-typeListBox"), 10);

		selectFromDropdown(By.id("gwt-debug-TransactionInstDistEditPopupView-typeListBox"), type);
		return this;
	}

	/**
	 * edit asset of instrument distribution
	 * 
	 * @param asset
	 * @return {@link InstrumentDistributionPage}
	 * @throws InterruptedException
	 */
	public InstrumentDistributionPage editAsset(String asset) throws InterruptedException {
		wait(Settings.WAIT_SECONDS);
		waitForElementVisible(By.id("gwt-debug-TransactionInstDistEditPopupView-assetListBox"), 10);

		selectFromDropdown(By.id("gwt-debug-TransactionInstDistEditPopupView-assetListBox"), asset);

		return this;
	}

	/**
	 * edit status of instrument distribution
	 * 
	 * @param status
	 * @return {@link InstrumentDistributionPage}
	 */
	public InstrumentDistributionPage editStatus(String status) {
		waitForElementVisible(By.id("gwt-debug-TransactionOtherEditPopupView-statusBox"), 10);

		selectFromDropdown(By.id("gwt-debug-TransactionOtherEditPopupView-statusBox"), status);

		return this;
	}

	/**
	 * edit currency of instrument distribution
	 * 
	 * @param status
	 * @return {@link InstrumentDistributionPage}
	 */
	public InstrumentDistributionPage editCurrency(String currency) {
		waitForElementVisible(By.id("gwt-debug-TransactionInstDistEditPopupView-currencyBox"), 10);

		selectFromDropdown(By.id("gwt-debug-TransactionInstDistEditPopupView-currencyBox"), currency);

		return this;
	}

	/**
	 * edit amount of instrument distribution
	 * 
	 * @param amount
	 * @return {@link InstrumentDistributionPage}
	 */
	public InstrumentDistributionPage editAmount(String amount) {

		waitForElementVisible(By.id("gwt-debug-TransactionInstDistEditPopupView-amountBox"), 10);
		sendKeysToElement(By.id("gwt-debug-TransactionInstDistEditPopupView-amountBox"), amount);
		return this;
	}

	/**
	 * edit quantity of instrument distribution
	 * 
	 * @param amount
	 * @return {@link InstrumentDistributionPage}
	 */
	public InstrumentDistributionPage editQuantity(String quantity) {

		waitForElementVisible(By.id("gwt-debug-TransactionInstDistEditPopupView-unitsBox"), 10);
		sendKeysToElement(By.id("gwt-debug-TransactionInstDistEditPopupView-unitsBox"), quantity);
		return this;
	}

	/**
	 * edit settlement date of instrument distribution
	 * 
	 * @param date
	 * @return {@link InstrumentDistributionPage}
	 */
	public InstrumentDistributionPage editSettlementDate(String date) {

		waitForElementVisible(By.id("gwt-debug-TransactionInstDistEditPopupView-settlementDate"), 10);
		sendKeysToElement(By.id("gwt-debug-TransactionInstDistEditPopupView-settlementDate"), date);
		return this;
	}

	/**
	 * edit execution date of instrument distribution
	 * 
	 * @param date
	 * @return {@link InstrumentDistributionPage}
	 */
	public InstrumentDistributionPage editExecutionDate(String date) {

		waitForElementVisible(By.id("gwt-debug-TransactionInstDistEditPopupView-executionDate"), 10);
		sendKeysToElement(By.id("gwt-debug-TransactionInstDistEditPopupView-executionDate"), date);
		return this;
	}

	/**
	 * edit ex date of instrument distribution
	 * 
	 * @param date
	 * @return {@link InstrumentDistributionPage}
	 */
	public InstrumentDistributionPage editExDate(String date) {

		waitForElementVisible(By.id("gwt-debug-TransactionInstDistEditPopupView-exDateBox"), 10);
		sendKeysToElement(By.id("gwt-debug-TransactionInstDistEditPopupView-exDateBox"), date);
		return this;
	}

	/**
	 * edit cum date of instrument distribution
	 * 
	 * @param date
	 * @return {@link InstrumentDistributionPage}
	 */
	public InstrumentDistributionPage editCumDate(String date) {

		waitForElementVisible(By.id("gwt-debug-TransactionInstDistEditPopupView-cumDateBox"), 10);
		sendKeysToElement(By.id("gwt-debug-TransactionInstDistEditPopupView-cumDateBox"), date);
		return this;
	}

	/**
	 * click save button
	 * 
	 * @return {@link HistoryPage}
	 * @throws InterruptedException
	 */
	public HistoryPage clickSaveButton() throws InterruptedException {

		clickElement(By.id("gwt-debug-TransactionInstDistEditPopupView-saveBtn"));
		wait(2);
		clickOkButtonIfVisible();
		return new HistoryPage(webDriver);
	}

	public InstrumentDistributionPage clickSaveButtonForCheckingFields() {
		clickElement(By.id("gwt-debug-TransactionInstDistEditPopupView-saveBtn"));
		return this;
	}

	public InstrumentDistributionPage addInvestmentForInstrumentDistribution(String investment)
			throws InterruptedException {

		clickElement(By.id("gwt-debug-TransactionInstDistEditPopupView-assetFind"));
		InvestmentsPage investmentPage = new InvestmentsPage(webDriver);

		investmentPage.simpleSearchByName(investment).selectInvestmentByName(investment);
		return this;
	}
}
