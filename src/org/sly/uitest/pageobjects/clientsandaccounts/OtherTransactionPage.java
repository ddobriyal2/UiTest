package org.sly.uitest.pageobjects.clientsandaccounts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.pageobjects.investments.InvestmentsPage;

/**
 * This page is for editing transaction where type = "Other"
 * 
 * @author Benny Leung
 * @date Nov 1, 2016
 * @company Prive Financial
 */
public class OtherTransactionPage extends AbstractPage {

	public OtherTransactionPage(WebDriver webDriver) {

		super();
		this.webDriver = webDriver;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		try {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.id("gwt-debug-TransactionOtherEditPopupView-dialogBox-content")));

		} catch (TimeoutException ex) {

		}

	}

	/**
	 * click pencil next to a given asset
	 * 
	 * @param asset
	 *            asset name
	 * @return {@link OtherTransactionPage}
	 */
	public OtherTransactionPage editImpactByAsset(String asset) {
		clickElement(By.xpath(".//td[div[.='" + asset
				+ "']]/following-sibling::td//button[@id='gwt-debug-TransactionImpactRowWidgetView-editBtn']"));
		return this;
	}

	/**
	 * To edit quantity of an impact
	 * 
	 * @param quantity
	 * @return {@link OtherTransactionPage}
	 */
	public OtherTransactionPage editImpactQuantity(String quantity) {

		sendKeysToElement(By.id("gwt-debug-TransactionImpactEditPresenterWidgetView-quantityBox"), quantity);

		return this;
	}

	/**
	 * To edit static of an impact
	 * 
	 * @param status
	 * @return
	 */
	public OtherTransactionPage editImpactStatus(String status) {
		selectFromDropdown(By.id("gwt-debug-TransactionImpactEditPresenterWidgetView-statusBox"), status);
		return this;
	}

	/**
	 * To edit impact contract number of an impact
	 * 
	 * @param number
	 * @return {@link OtherTransactionPage}
	 */
	public OtherTransactionPage editImpactContractNumber(String number) {

		sendKeysToElement(By.id("gwt-debug-InstrumentLoanView-contractNumber"), number);

		return this;
	}

	/**
	 * Click the "+" button to add impact
	 * 
	 * @return {@link OtherTransactionPage}
	 */
	public OtherTransactionPage clickAddImpactButton() {
		clickElement(By.id("gwt-debug-TransactionOtherEditPopupView-addImpacts"));
		return this;
	}

	/**
	 * To save the change on impact
	 * 
	 * @return {@link OtherTransactionPage}
	 */
	public OtherTransactionPage clickSaveImpactButton() {

		clickElement(By.id("gwt-debug-TransactionImpactEditPresenterWidgetView-saveBtn"));
		if (isElementVisible(By.id("gwt-debug-TransactionImpactEditPresenterWidgetView-saveBtn"))) {
			clickElementByKeyboard(By.id("gwt-debug-TransactionImpactEditPresenterWidgetView-saveBtn"));
		}
		return this;
	}

	/**
	 * click save button of other transaction detail window
	 * 
	 * @return {@link OtherTransactionPage}
	 * @throws InterruptedException
	 */
	public HistoryPage clickSaveButton() throws InterruptedException {

		clickElement(By.id("gwt-debug-TransactionOtherEditPopupView-saveBtn"));

		clickOkButtonIfVisible();
		return new HistoryPage(webDriver);
	}

	public OtherTransactionPage clickDeleteButtonForImpact(String amount, String asset, String status) {
		clickElement(By
				.xpath(".//table[@id='gwt-debug-TransactionOtherEditPopupView-ContentTable']//td[div[contains(text(),'"
						+ amount + "')]]//following-sibling::td[div[contains(text(),'" + asset
						+ "')]]//following-sibling::td[div[contains(text(),'" + status
						+ "')]]//following-sibling::td//button[@id='gwt-debug-TransactionImpactRowWidgetView-delBtn']"));
		return this;
	}

	/**
	 * to edit transaction date of transaction
	 * 
	 * @param date
	 * @return {@link OtherTransactionPage}
	 */
	public OtherTransactionPage editTransactionDate(String date) {
		sendKeysToElement(By.id("gwt-debug-TransactionOtherEditPopupView-transactionDate"), date);

		return this;
	}

	/**
	 * to edit transaction date of transaction
	 * 
	 * @param date
	 * @return {@link OtherTransactionPage}
	 */
	public OtherTransactionPage editExecutionDate(String date) {
		sendKeysToElement(By.id("gwt-debug-TransactionOtherEditPopupView-executionDate"), date);

		return this;
	}

	/**
	 * edit transaction date of transaction
	 * 
	 * @param date
	 * @return {@link OtherTransactionPage}
	 */
	public OtherTransactionPage editSettlementDate(String date) {
		sendKeysToElement(By.id("gwt-debug-TransactionOtherEditPopupView-settlementDate"), date);

		return this;
	}

	/**
	 * edit instrument type in edit transaction impact
	 * 
	 * @param instrument
	 * @return
	 */
	public OtherTransactionPage editImpactInstrument(String instrument) {
		selectFromDropdown(By.id("gwt-debug-TransactionImpactEditPresenterWidgetView-instrumentsListBox"), instrument);
		return this;
	}

	/**
	 * for equity impact, add instrument to the impact
	 * 
	 * @param instrument
	 * @return
	 * @throws InterruptedException
	 */
	public OtherTransactionPage addInstrumentForEquityImpact(String instrument) throws InterruptedException {

		clickElement(By.id("gwt-debug-InstrumentSecuritiesView-assetFind"));

		InvestmentsPage iPage = new InvestmentsPage(webDriver);

		iPage.simpleSearchByName(instrument).selectInvestmentByName(instrument);
		waitForElementVisible(By.id("gwt-debug-InstrumentSecuritiesView-assetLabel"), 10);

		return this;
	}

	/**
	 * edit currency for the impact
	 * 
	 * @param currency
	 * @return
	 */
	public OtherTransactionPage editImpactCurrency(String currency) {
		selectFromDropdown(By.xpath(".//div[.='Currency']//following-sibling::select"), currency);
		return this;
	}
}
