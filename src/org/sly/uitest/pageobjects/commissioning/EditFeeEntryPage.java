package org.sly.uitest.pageobjects.commissioning;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.settings.Settings;

/**
 * This class represents the Edit Fee Entry page, which can be navigated by
 * creating a new entry or editing an existing entry
 * 
 * @author Lynne Huang
 * @date : 11 Aug, 2015
 * @company Prive Financial
 * 
 *          PAGE NAVIGATION: Accounting -> Client Fees/Advisor
 *          Commissions/Company Commissions -> *** Acc -> NEW Entry
 */
public class EditFeeEntryPage extends AbstractPage {

	/**
	 * @param webDriver
	 */
	public EditFeeEntryPage(WebDriver webDriver) {

		super();
		this.webDriver = webDriver;

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);
		scrollToTop();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gwt-debug-FeeEditWidget-dialogBox-caption")));

		assertTrue(pageContainsStr(" Edit Fee Entry "));
	}

	/**
	 * Choose one recipient type for the 'Credit To'
	 * 
	 * @param recipientType
	 *            the type of the recipient: Advisor Account, Advisor Company,
	 *            Investor Account
	 * @return {@link EditFeeEntryPage}
	 */
	public EditFeeEntryPage editCreditToRecipientType(String recipientType) throws InterruptedException {

		selectFromDropdown(By.id("gwt-debug-FeeEditWidget-creditFeeRecipientType"), recipientType);

		// wait for refreshing
		wait(Settings.WAIT_SECONDS);

		return this;

	}

	/**
	 * Choose one recipient for the 'Credit To'
	 * 
	 * @param recipient
	 *            the recipient
	 * @return {@link EditFeeEntryPage}
	 * 
	 */
	public EditFeeEntryPage editCreditToRecipient(String recipient) {

		selectFromDropdown(By.id("gwt-debug-FeeEditWidget-creditFeeRecipients"), recipient);

		return this;

	}

	/**
	 * Choose one account for the 'Credit to'
	 * 
	 * @param creditAcc
	 *            the credit accounts: *** Acc
	 * @return {@link EditFeeEntryPage}
	 */
	public EditFeeEntryPage editCreditToAccount(String creditAcc) {

		selectFromDropdown(By.id("gwt-debug-FeeEditWidget-creditAccounts"), creditAcc);

		return this;

	}

	/**
	 * Choose one recipient type for the 'Debit From'
	 * 
	 * @param recipientType
	 *            the type of the recipient: Advisor Account, Advisor Company,
	 *            Investor Account
	 * @return {@link EditFeeEntryPage}
	 */
	public EditFeeEntryPage editDebitFromRecipientType(String recipientType) throws InterruptedException {

		selectFromDropdown(By.id("gwt-debug-FeeEditWidget-debitFeeRecipientType"), recipientType);

		// wait for refreshing
		wait(Settings.WAIT_SECONDS);

		return this;

	}

	/**
	 * Choose one recipient for the 'Debit From'
	 * 
	 * @param recipient
	 *            the recipient
	 * @return {@link EditFeeEntryPage}
	 * 
	 */
	public EditFeeEntryPage editDebitFromRecipient(String recipient) {

		selectFromDropdown(By.id("gwt-debug-FeeEditWidget-debitFeeRecipients"), recipient);

		return this;

	}

	/**
	 * Choose one account for the 'Debit From'
	 * 
	 * @param creditAcc
	 *            the credit accounts: *** Acc
	 * @return {@link EditFeeEntryPage}
	 * @throws InterruptedException
	 */
	public EditFeeEntryPage editDebitFromAccount(String creditAcc) throws InterruptedException {
		this.waitForElementVisible(By.id("gwt-debug-FeeEditWidget-debitAccounts"), Settings.WAIT_SECONDS);
		selectFromDropdown(By.id("gwt-debug-FeeEditWidget-debitAccounts"), creditAcc);

		return this;

	}

	/**
	 * Select a value date with the format 'd-MMM-yyyy'
	 * 
	 * @param date
	 *            the value date
	 * @return {@link EditFeeEntryPage}
	 */
	public EditFeeEntryPage editValueDate(String date) {

		sendKeysToElement(By.id("gwt-debug-FeeEditWidget-feeEffectiveDate"), date);

		return this;

	}

	/**
	 * Edit the status of the fee entry
	 * 
	 * @param status
	 *            the status of the fee entry
	 * @return {@link EditFeeEntryPage}
	 */
	public EditFeeEntryPage editStatus(String status) {

		selectFromDropdown(By.id("gwt-debug-FeeEditWidget-feeStatus"), status);

		return this;
	}

	/**
	 * Edit the amount of the fee entry
	 * 
	 * @param amount
	 *            the amount of the fee entry
	 * @return {@link EditFeeEntryPage}
	 */
	public EditFeeEntryPage editAmount(String amount) {

		sendKeysToElement(By.id("gwt-debug-FeeEditWidget-feeAmount"), amount);

		return this;
	}

	/**
	 * Click the SAVE button to save the fee entry and return to the commissions
	 * fee page
	 * 
	 * @return {@link CommissionsFeesPage}
	 */
	public CommissionsFeesPage clickSaveButtonForFeeEntry() {

		clickElement(By.id("gwt-debug-FeeEditWidget-submitBtn"));

		return new CommissionsFeesPage(webDriver);
	}

	/**
	 * click the CANCEL button and return to the commissions fee page
	 * 
	 * @return {@link CommissionsFeesPage}
	 */
	public CommissionsFeesPage clickCancelButtonForFeeEntry() {

		clickElement(By.id("gwt-debug-FeeEditWidget-cancelBtn"));

		return new CommissionsFeesPage(webDriver);
	}
}
