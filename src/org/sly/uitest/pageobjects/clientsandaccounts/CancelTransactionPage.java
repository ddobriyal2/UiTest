package org.sly.uitest.pageobjects.clientsandaccounts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;

/**
 * This page is for creating Cancel transaction
 * 
 * @author Benny Leung
 * @company Prive Financial
 * @date : Jan 12, 2017
 */
public class CancelTransactionPage extends AbstractPage {

	public CancelTransactionPage(WebDriver webDriver) {
		super();
		this.webDriver = webDriver;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("gwt-debug-TransactionCancelEditPopupView-dialogBox-content")));

	}

	/**
	 * Edit the transaction date
	 * 
	 * @param date
	 */
	public CancelTransactionPage editTransactionDate(String date) {
		sendKeysToElement(By.id("gwt-debug-TransactionCancelEditPopupView-transactionDate"), date);
		return this;
	}

	/**
	 * Edit the transaction Reference
	 * 
	 * @param reference
	 */
	public CancelTransactionPage editTransactionReference(String reference) {
		sendKeysToElement(By.id("gwt-debug-TransactionCancelEditPopupView-transRefTextBox"), reference);
		return this;
	}

	/**
	 * Edit the comment of the Cancel Transaction
	 * 
	 * @param comment
	 * @return
	 */
	public CancelTransactionPage editComment(String comment) {
		sendKeysToElement(By.id("gwt-debug-TransactionOtherEditPopupView-commentsTextArea"), comment);
		return this;
	}

	public HistoryPage clickSaveButton() throws InterruptedException {
		clickElement(By.id("gwt-debug-TransactionCancelEditPopupView-saveBtn"));
		clickYesButtonIfVisible();
		clickOkButtonIfVisible();
		return new HistoryPage(webDriver);
	}

	public HistoryPage clickCancelButton() throws InterruptedException {
		clickElement(By.id("gwt-debug-TransactionCancelEditPopupView-cancelBtn"));
		return new HistoryPage(webDriver);
	}
}
