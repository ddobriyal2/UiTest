package org.sly.uitest.pageobjects.clientsandaccounts;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;

/**
 * 
 * This class represents when execute bulk operations after click 'BULK
 * OPERATIONS' button in the accout overview page
 * 
 * @author Lynne Huang
 * @date : 27 Aug, 2015
 * @company Prive Financial
 */
public class BulkOperationPage extends AbstractPage {

	/**
	 * @param webDriver
	 */
	public BulkOperationPage(WebDriver webDriver) {
		super();
		this.webDriver = webDriver;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.id("gwt-debug-BulkOperationView-dialogBox-caption")));

		assertTrue(pageContainsStr(" Bulk Operation "));
	}

	/**
	 * (Step 1 of 4) Select whether the bulk operation should apply to clients
	 * or accounts, and then click PROCEED button
	 * 
	 * @return {@link BulkOperationPage}
	 */
	public BulkOperationPage selectType(String type) {

		if (type.equals("Accounts")) {
			clickElement(By.id("gwt-debug-BulkOperationTypePopupView-accountsRadioButton-label"));
		} else if (type.equals("Clients")) {
			clickElement(By.id("gwt-debug-BulkOperationTypePopupView-clientsRadioButton-label"));
		}

		clickElement(By.id("gwt-debug-BulkOperationTypePopupView-nextButton"));

		/*
		 * clickElement(By
		 * .id("gwt-debug-BulkOperationTypePopupView-proceedButton"));
		 */

		return this;
	}

	/**
	 * (Step 2 of 4) Select a bulk operation to perform, and then click PROCEED
	 * button
	 * 
	 * @param action
	 *            the option of the bulk operation
	 * @return {@link BulkOperationPage}
	 * @throws InterruptedException
	 * 
	 */
	public BulkOperationPage selectAction(String action) throws InterruptedException {

		selectFromDropdown(By.id("gwt-debug-BulkOperationOptionPopupView-optionListBox"), action);

		clickElementAndWait3(By.id("gwt-debug-BulkOperationOptionPopupView-proceedButton"));

		return this;

	}

	/**
	 * (Step 3 of 4) When select 'Change account status' action for account bulk
	 * operation, select an account status and then click PROCEED button
	 * 
	 * @param status
	 *            the account status
	 * @return {@link BulkOperationPage}
	 */
	public BulkOperationPage changeAccountStatus(String status) {

		waitForElementVisible(By.id("gwt-debug-BulkOperationAccountStatusPopupView-statusListBox"), 10);

		selectFromDropdown(By.id("gwt-debug-BulkOperationAccountStatusPopupView-statusListBox"), status);

		clickElement(By.id("gwt-debug-BulkOperationAccountStatusPopupView-proceedButton"));

		return this;
	}

	/**
	 * (Step 3 of 4) When select 'Create calendar review entries' action for
	 * account bulk operation, input the start date of the account with format
	 * 'd-MMM-yyyy', and then click PROCEED button
	 * 
	 * @param status
	 *            the account status
	 * @return {@link BulkOperationPage}
	 */
	public BulkOperationPage editStartDate(String d_MMM_yyy) {

		sendKeysToElement(By.id("gwt-debug-BulkOperationCalendarReviewDatesPopupView-startDateTextBox"),
				d_MMM_yyy + "\n");

		clickElement(By.id("gwt-debug-BulkOperationCalendarReviewDatesPopupView-proceedButton"));

		return this;
	}

	/**
	 * (Step 3 of 4) When select 'Change the main admin' action for client bulk
	 * operation, select contact, and then click PROCEED button
	 * 
	 * @param contact
	 *            the name of the main admin
	 * @return {@link BulkOperationPage}
	 */
	public BulkOperationPage editMainContact(String contact) {

		selectFromDropdown(By.id("gwt-debug-BulkOperationMainAdvisorPopupView-advisorListBox"), contact);

		clickElement(By.id("gwt-debug-BulkOperationMainAdvisorPopupView-proceedButton"));

		return this;
	}

	/**
	 * /** (Step 3 of 4) When select 'Change the review frequency' action for
	 * account bulk operation, select review frequency, and then click PROCEED
	 * button
	 * 
	 * @param frequency
	 *            the review frequency of the account
	 * @return {@link BulkOperationPage}
	 */

	public BulkOperationPage editReviewFrequency(String frequency) {

		selectFromDropdown(By.id("gwt-debug-BulkOperationReviewFrequencyPopupView-reviewFrequencyListBox"), frequency);

		clickElement(By.id("gwt-debug-BulkOperationReviewFrequencyPopupView-proceedButton"));

		return this;
	}

	/**
	 * /** (Step 3 of 4) When select 'Clear or set a new benchmark for the
	 * account' action for account bulk operation, select benchmark, and then
	 * click PROCEED button
	 * 
	 * @param benchmark
	 * @return {@link BulkOperationPage}
	 */

	public BulkOperationPage editBenchmark(String benchmark) {

		selectFromDropdown(By.id("gwt-debug-BulkOperationBenchmarkPopupView-tickerListBox"), benchmark);

		clickElement(By.id("gwt-debug-BulkOperationBenchmarkPopupView-proceedButton"));

		return this;
	}

	/**
	 * /** (Step 3 of 4) When select 'Change the base currency for the client
	 * reports' action for client bulk operation, select currency, and then
	 * click PROCEED button
	 * 
	 * @param currency
	 * @return {@link BulkOperationPage}
	 */
	public BulkOperationPage editBaseCurrency(String currency) {

		selectFromDropdown(By.id("gwt-debug-BulkOperationInvestorBaseCurrencyPopupView-baseCurrencyListBox"), currency);

		clickElement(By.id("gwt-debug-BulkOperationInvestorBaseCurrencyPopupView-proceedButton"));

		return this;
	}

	/**
	 * /** (Step 3 of 4) When select 'Generate report' action for account bulk
	 * operation, select benchmark, and then click PROCEED button
	 * 
	 * @param range
	 * @param startDate
	 * @param endDate
	 * @param inception
	 * @return {@link BulkOperationPage}
	 */
	public BulkOperationPage editDateRangeForReport(String range, String startDate, String endDate, Boolean inception,
			Boolean updateReviewDate) {

		if (inception) {

			WebElement we = webDriver.findElement(By.id("gwt-debug-DateRangePanel-sinceInceptionCheckBox-input"));

			setCheckboxStatus2(we, true);

		} else {

			clickElement(By.xpath("//label[.='" + range + "']"));

			if (startDate != "") {
				sendKeysToElement(By.id("gwt-debug-DateRangePanel-startDateTextBox"), startDate);
			}

			if (endDate != "") {
				sendKeysToElement(By.id("gwt-debug-DateRangePanel-endDateTextBox"), endDate);
			}
		}

		WebElement elem = webDriver
				.findElement(By.id("gwt-debug-BulkOperationGenerateReportPopupView-updateReviewListBox"));
		if (updateReviewDate) {
			selectFromDropdown(elem, "Yes");
		} else {
			selectFromDropdown(elem, "No");
		}
		clickElement(By.id("gwt-debug-BulkOperationGenerateReportPopupView-proceedButton"));

		return this;
	}

	/**
	 * (Step 4 of 4) Click CONFIRM button
	 * 
	 * @return {@link AccountOverviewPage}
	 */
	public AccountOverviewPage clickConfirmButton() {

		clickElement(By.id("gwt-debug-BulkOperationSummaryPopupView-confirmButton"));

		return new AccountOverviewPage(webDriver);
	}

	public BulkOperationPage clickNextButton() {

		clickElement(By.id("gwt-debug-BulkOperationTypePopupView-nextButton"));
		return this;
	}
}
