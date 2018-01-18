package org.sly.uitest.pageobjects.clientsandaccounts;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;

/**
 * This class represents the Analysis Page (tab) of an account, which can be
 * navigated by clicking 'Clients' -> 'Account Overview' -> choose any account
 * -> 'Cashflow (tab)'
 * 
 * 
 * URL:
 * "http://192.168.1.104:8080/SlyAWS/?locale=en_US#portfolioOverviewDw;investorAccountKey=12292;valueType=2"
 * 
 * @author Lynne Huang
 * @date : 20 Aug, 2015
 * @company Prive Financial
 */

public class CashflowPage extends AbstractPage {

	public CashflowPage(WebDriver webDriver) throws InterruptedException {

		super();
		this.webDriver = webDriver;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(60, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);
		handleAlert();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gwt-debug-MyMainMaterialView-mainPanel")));

		// assertTrue(pageContainsStr(" Cashflow Schedules "));

	}

	/**
	 * Click NEW SCHEDULE button to create a new schedule
	 * 
	 * @return {@link CashflowPage}
	 * @throws InterruptedException
	 */
	public CashflowPage clickNewScheduleButton() throws InterruptedException {
		By by = By.id("gwt-debug-NewBusinessEventWidget-newScheduleButton");
		this.waitForWaitingScreenNotVisible();
		waitForElementVisible(by, 30);
		clickElement(by);
		// WebElement element = webDriver.findElement(By
		// .id("gwt-debug-NewBusinessEventWidget-newScheduleButton"));
		// element.sendKeys(Keys.RETURN);
		// wait(2);
		return this;
	}

	/**
	 * In the Cashflow Schedule Edit page, edit the type of the schedule
	 * 
	 * @param type
	 * 
	 * @return {@link CashflowPage}
	 * @throws InterruptedException
	 */
	public CashflowPage editCashflowScheduleType(String type) throws InterruptedException {
		waitForElementVisible(By.id("gwt-debug-EditContributionScheduleEntryView-typeListBox"), 10);
		new Actions(webDriver)
				.moveToElement(webDriver.findElement(By.id("gwt-debug-EditContributionScheduleEntryView-typeListBox")))
				.perform();
		selectFromDropdown(By.id("gwt-debug-EditContributionScheduleEntryView-typeListBox"), type);

		return this;
	}

	/**
	 * In the Cashflow Schedule Edit page, edit the frequency of the schedule
	 * 
	 * @param frequency
	 * 
	 * @return {@link CashflowPage}
	 * @throws InterruptedException
	 */
	public CashflowPage editCashflowScheduleFrequency(String frequency) throws InterruptedException {
		new Actions(webDriver).moveToElement(
				webDriver.findElement(By.id("gwt-debug-EditContributionScheduleEntryView-frequencyListBox")));
		selectFromDropdown(By.id("gwt-debug-EditContributionScheduleEntryView-frequencyListBox"), frequency);

		this.waitForWaitingScreenNotVisible();

		return this;
	}

	/**
	 * In the Cashflow Schedule Edit page, edit the start date of the schedule
	 * 
	 * @param date
	 *            the start date
	 * 
	 * @return {@link CashflowPage}
	 * @throws InterruptedException
	 */
	public CashflowPage editCashflowScheduleStartDate(String date) throws InterruptedException {

		waitForElementVisible(By.id("gwt-debug-EditContributionScheduleEntryView-startDatePicker"), 10);

		sendKeysToElement(By.id("gwt-debug-EditContributionScheduleEntryView-startDatePicker"), date);

		return this;
	}

	/**
	 * In the Cashflow Schedule Edit page, edit the end date of the schedule
	 * 
	 * @param date
	 *            the end date
	 * 
	 * @return {@link CashflowPage}
	 * @throws InterruptedException
	 */
	public CashflowPage editCashflowScheduleEndDate(String date) throws InterruptedException {
		waitForElementVisible(By.id("gwt-debug-EditContributionScheduleEntryView-endDatePicker"), 10);

		sendKeysToElement(By.id("gwt-debug-EditContributionScheduleEntryView-endDatePicker"), date);
		// wait(Settings.WAIT_SECONDS);
		return this;
	}

	/**
	 * In the Cashflow Schedule Edit page, edit the amount of the schedule
	 * 
	 * @param amount
	 * 
	 * @return {@link CashflowPage}
	 * @throws InterruptedException
	 */
	public CashflowPage editCashflowScheduleAmount(String amount) throws InterruptedException {
		waitForElementVisible(By.id("gwt-debug-EditContributionScheduleEntryView-amountTextBox"), 5);

		new Actions(webDriver).moveToElement(
				webDriver.findElement(By.id("gwt-debug-EditContributionScheduleEntryView-amountTextBox")));

		sendKeysToElement(By.id("gwt-debug-EditContributionScheduleEntryView-amountTextBox"), amount);
		return this;
	}

	/**
	 * Click NEW CASHFLOW buuton to create a new cashflow
	 * 
	 * @return {@link CashflowPage}
	 * @throws InterruptedException
	 */
	public CashflowPage clickNewCashflowButton() throws InterruptedException {
		waitForElementVisible(By.xpath(".//button[contains(text(),'New Cashflow')]"), 30);

		new Actions(webDriver)
				.moveToElement(webDriver.findElement(By.xpath(".//button[contains(text(),'New Cashflow')]"))).perform();

		clickElement(By.xpath(".//button[contains(text(),'New Cashflow')]"));

		return this;
	}

	/**
	 * In the Cashflow Edit page, edit the value date of the schedule
	 * 
	 * @param date
	 *            the value date
	 * 
	 * @return {@link CashflowPage}
	 * @throws InterruptedException
	 */
	public CashflowPage editCashflowValueDate(String date) throws InterruptedException {
		// wait(2 * Settings.WAIT_SECONDS);
		waitForElementVisible(By.xpath(".//*[contains(@id,'valueDatePicker')]"), 10);
		sendKeysToElement(By.xpath(".//*[contains(@id,'valueDatePicker')]"), date);

		return this;
	}

	/**
	 * In the Cashflow Edit page, edit the type of the schedule
	 * 
	 * @param type
	 * 
	 * @return {@link CashflowPage}
	 */
	public CashflowPage editCashflowType(String type) {
		waitForElementVisible(By.xpath(".//*[contains(@id,'entryType')]"), 10);
		selectFromDropdown(By.xpath(".//*[contains(@id,'entryType')]"), type);

		return this;

	}

	/**
	 * In the Cashflow Edit page, edit the currency of the schedule
	 * 
	 * @param currency
	 * 
	 * @return {@link CashflowPage}
	 */
	public CashflowPage editCashflowCurrency(String currency) {

		waitForElementVisible(By.xpath(".//select[contains(@id,'currency')]"), 30);
		selectFromDropdown(By.xpath(".//select[contains(@id,'currency')]"), currency);

		return this;
	}

	/**
	 * In the Cashflow Edit page, edit the status of the schedule
	 * 
	 * @param status
	 * 
	 * @return {@link CashflowPage}
	 */
	public CashflowPage editCashflowStatus(String status) {

		waitForElementVisible(By.xpath(".//select[contains(@id,'status')]"), 30);
		selectFromDropdown(By.xpath(".//select[contains(@id,'status')]"), status);

		return this;
	}

	/**
	 * In the Cashflow Edit page, edit the amount of the schedule
	 * 
	 * @param date
	 *            the value date
	 * 
	 * @return {@link CashflowPage}
	 */
	public CashflowPage editCashflowAmount(String amount) {

		waitForElementVisible(By.xpath(".//input[contains(@id,'amount')]"), 30);
		sendKeysToElement(By.xpath(".//input[contains(@id,'amount')]"), amount);

		return this;
	}

	public CashflowPage editTransactionDate(String txnDate) {
		sendKeysToElement(By.id("gwt-debug-CashflowEditPopupView-txnDatePicker"), txnDate);
		return this;
	}

	public CashflowPage editValueDate(String txnDate) {

		waitForElementVisible(By.xpath(".//input[contains(@id,'valueDatePicker')]"), 30);
		sendKeysToElement(By.xpath(".//input[contains(@id,'valueDatePicker')]"), txnDate);

		return this;
	}

	public CashflowPage editPositions(String position) {
		selectFromDropdown(By.xpath(".//select[contains(@id,'positionsListBox')]"), position);
		return this;
	}

	public CashflowPage editInstrumentType(String instrumentType) {
		selectFromDropdown(By.xpath(".//select[contains(@id,'instrumentTypeLB')]"), instrumentType);
		return this;
	}

	public CashflowPage editInterestPeriodFrom(String date) {
		sendKeysToElement(By.xpath(".//input[contains(@id,'interestPeriodFromDate')]"), date);
		return this;
	}

	public CashflowPage editInterestPeriodTo(String date) {
		sendKeysToElement(By.xpath(".//input[contains(@id,'interestPeriodToDate')]"), date);
		return this;
	}

	public CashflowPage clickCalculateInterestButton() {
		clickElement(By.xpath(".//button[contains(@id,'calculateInterestAmount')]"));
		return this;
	}

	public CashflowPage calculateInterestButton(String dateFrom, String dateTo) {
		this.editInterestPeriodFrom(dateFrom).editInterestPeriodTo(dateTo).clickCalculateInterestButton();
		return this;
	}

	/**
	 * Click SAVE buuton to save the cashflow schedule
	 * 
	 * @return {@link CashflowPage}
	 * @throws InterruptedException
	 */
	public CashflowPage clickSaveButton_CashflowSchedule() throws InterruptedException {

		waitForElementVisible(By.id("gwt-debug-EditContributionScheduleEntryView-submitBtn"), 10);

		clickElement(By.id("gwt-debug-EditContributionScheduleEntryView-submitBtn"));
		/*
		 * clickElement(By
		 * .id("gwt-debug-EditContributionScheduleEntryView-submitBtn")); try {
		 * clickElement(By
		 * .id("gwt-debug-EditContributionScheduleEntryView-submitBtn")); }
		 * catch (Exception ex) { }
		 */
		return this;
	}

	/**
	 * Click SAVE button to save the cashflow entry
	 * 
	 * @return {@link CashflowPage}
	 * @throws InterruptedException
	 */
	public CashflowPage clickSaveButton_CashflowEntry() throws InterruptedException {

		clickElement(By.xpath(".//*[contains(@id,'saveButton')]"));

		try {
			waitForElementVisible(By.id("gwt-debug-CustomDialog-okButton"), 20);

			clickElement(By.id("gwt-debug-CustomDialog-okButton"));
		} catch (TimeoutException e) {

		}

		return this;
	}

	public HistoryPage clickSaveButtonForTrade() throws InterruptedException {
		clickElement(By.xpath(".//*[contains(@id,'saveButton')]"));
		clickOkButtonIfVisible();
		return new HistoryPage(webDriver);
	}

	/**
	 * Click CANCEL button
	 * 
	 * @return {@link CashflowPage}
	 */
	public CashflowPage clickCancelButton() {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//button[contains(@id,'cancelButton')]")));

		clickElement(By.xpath(".//button[contains(@id,'cancelButton')]"));

		return this;
	}

	public CashflowPage clickRefreshButton() throws InterruptedException {

		clickElement(By.id("gwt-debug-PortfolioOverviewView-refreshBtn"));
		clickElement(By.xpath(".//button[.='Proceed']"));
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel"), 30);
		handleAlert();

		this.refreshPage();
		HoldingsPage holdings = new HoldingsPage(webDriver);
		clickOkButtonIfVisible();
		holdings.goToCashflowPage();
		handleAlert();
		return this;
	}

	/**
	 * Check or uncheck the checkbox of all the cashflow schedules
	 * 
	 * @param checked
	 *            if true, check all cashflow schedules; if false uncheck
	 */
	public CashflowPage checkAllCashflowSchedules(boolean checked) {

		waitForElementVisible(By.id("gwt-debug-NewBusinessEventWidget-allCheckBox-input"), 30);
		try {
			WebElement we2 = webDriver.findElement(By.id("gwt-debug-NewBusinessEventWidget-allCheckBox-input"));

			setCheckboxStatus2(we2, checked);
		} catch (org.openqa.selenium.StaleElementReferenceException e) {

		}
		/*
		 * for (int i = 0; i < 5; i++) {
		 * 
		 * try { WebElement we1 = webDriver .findElement(By
		 * .id("gwt-debug-NewBusinessEventWidget-allCheckBox-input"));
		 * 
		 * setCheckboxStatus2(we1, true); break; } catch
		 * (org.openqa.selenium.StaleElementReferenceException e) { // TODO:
		 * handle exception } }
		 * 
		 * for (int i = 0; i < 5; i++) { try { WebElement we2 = webDriver
		 * .findElement(By
		 * .id("gwt-debug-NewBusinessEventWidget-allCheckBox-input"));
		 * 
		 * setCheckboxStatus2(we2, checked); break; } catch
		 * (org.openqa.selenium.StaleElementReferenceException e) { // TODO:
		 * handle exception } }
		 */

		return this;

	}

	/**
	 * Check or uncheck the checkbox of Other Cashflows
	 * 
	 * @param checked
	 *            if true, check other cashflow schedules; if false uncheck
	 */
	public CashflowPage checkOtherCashflows(boolean checked) {
		waitForElementVisible(By.id("gwt-debug-DepositWithdrawalHistoryView-otherCheckBox-input"), 10);
		WebElement we = webDriver.findElement(By.id("gwt-debug-DepositWithdrawalHistoryView-otherCheckBox-input"));

		setCheckboxStatus2(we, checked);

		return this;

	}

	/**
	 * Check or uncheck the checkbox of a certain cashflow schedule with the
	 * given start date, end date, frequency, type, and amount
	 * 
	 * @param startDate
	 * @param endDate
	 * @param frequency
	 * @param type
	 * @param amount
	 * @param checked
	 *            if true, check this cashflow schedule; if false uncheck
	 * @throws InterruptedException
	 */
	public CashflowPage checkOneCashflowSchedules(String startDate, String endDate, String frequency, String type,
			String amount, boolean checked) throws InterruptedException {

		waitForElementVisible(By.xpath(
				"//td[.='" + startDate + "']/following-sibling::td[.='" + frequency + "']/following-sibling::td[.='"
						+ type + "']/following-sibling::td[.='" + amount + "']/preceding-sibling::td[6]/span/input"),
				30);

		WebElement we = webDriver.findElement(By.xpath(
				"//td[.='" + startDate + "']/following-sibling::td[.='" + frequency + "']/following-sibling::td[.='"
						+ type + "']/following-sibling::td[.='" + amount + "']/preceding-sibling::td[6]/span/input"));

		setCheckboxStatus2(we, checked);
		return this;

	}

	/**
	 * Check or uncheck the checkbox of a certain cashflow entry with the given
	 * value date, creation date, type, status, currency, and amount
	 * 
	 * @param valueDate
	 * @param creationDate
	 * @param type
	 * @param status
	 * @param currency
	 * @param amount
	 * @param checked
	 *            if true, check this cashflow entry; if false uncheck
	 */
	public CashflowPage checkOneCashflowEntry(String valueDate, String creationDate, String type, String status,
			String currency, String amount, boolean checked) {
		waitForElementVisible(By.xpath("//td[.='" + valueDate + "']/following-sibling::td[.='" + creationDate
				+ "']/following-sibling::td[.='" + type + "']/following-sibling::td[.='" + status
				+ "']/following-sibling::td[.='" + currency + "']/following-sibling::td[.='" + amount
				+ "']/following-sibling::td[1]/preceding-sibling::td[7]//input"), 10);
		WebElement we = webDriver.findElement(By.xpath("//td[.='" + valueDate + "']/following-sibling::td[.='"
				+ creationDate + "']/following-sibling::td[.='" + type + "']/following-sibling::td[.='" + status
				+ "']/following-sibling::td[.='" + currency + "']/following-sibling::td[.='" + amount
				+ "']/following-sibling::td[1]/preceding-sibling::td[7]//input"));

		setCheckboxStatus2(we, checked);

		return this;

	}

	/**
	 * Edit a certain cashflow schedule with the given start date, end date,
	 * frequency, type, and amount
	 * 
	 * @param startDate
	 * @param endDate
	 * @param frequency
	 * @param type
	 * @param amount
	 * 
	 * @return {@link CashflowPage}
	 * 
	 */
	public CashflowPage editThisCashflowSchedule(String startDate, String endDate, String frequency, String type,
			String amount) {

		clickElement(By.xpath("//td[.='" + startDate + "']/following-sibling::td[.='" + endDate
				+ "']/following-sibling::td[.='" + frequency + "']/following-sibling::td[.='" + type
				+ "']/following-sibling::td[.='" + amount + "']/following-sibling::td[1]//button[@title='Edit']"));

		return this;

	}

	/**
	 * Delete a certain cashflow schedule with the given start date, end date,
	 * frequency, type, and amount
	 * 
	 * @param startDate
	 * @param endDate
	 * @param frequency
	 * @param type
	 * @param amount
	 * 
	 * @return {@link CashflowPage}
	 * @throws InterruptedException
	 * 
	 */
	public CashflowPage deleteThisCashflowSchedule(String startDate, String endDate, String frequency, String type,
			String amount) throws InterruptedException {

		waitForElementVisible(By.xpath("//td[.='" + startDate + "']/following-sibling::td[.='" + endDate
				+ "']/following-sibling::td[.='" + frequency + "']/following-sibling::td[.='" + type
				+ "']/following-sibling::td[.='" + amount + "']/following-sibling::td[1]//button[@title='Delete']"),
				10);
		new Actions(webDriver)
				.moveToElement(webDriver.findElement(By.xpath("//td[.='" + startDate + "']/following-sibling::td[.='"
						+ endDate + "']/following-sibling::td[.='" + frequency + "']/following-sibling::td[.='" + type
						+ "']/following-sibling::td[.='" + amount
						+ "']/following-sibling::td[1]//button[@title='Delete']")))
				.build().perform();

		clickElementByKeyboard(By.xpath("//td[.='" + startDate + "']/following-sibling::td[.='" + endDate
				+ "']/following-sibling::td[.='" + frequency + "']/following-sibling::td[.='" + type
				+ "']/following-sibling::td[.='" + amount + "']/following-sibling::td[1]//button[@title='Delete']"));

		clickYesButtonIfVisible();
		clickOkButtonIfVisible();
		return this;

	}

	/**
	 * Edit a certain cashflow entry with the given value date, creation date,
	 * type, status, currency, and amount
	 * 
	 * @param valueDate
	 * @param creationDate
	 * @param type
	 * @param status
	 * @param currency
	 * @param amount
	 */
	public CashflowPage editCashflowEntry(String valueDate, String creationDate, String type, String status,
			String currency, String amount) {

		clickElement(By.xpath("(//td[contains(text(),'" + valueDate + "')]/following-sibling::td[contains(text(),'"
				+ creationDate + "')]/following-sibling::td[contains(text(),'" + type
				+ "')]/following-sibling::td[contains(text(),'" + status + "')]/following-sibling::td[contains(text(),'"
				+ currency + "')]/following-sibling::td[contains(text(),'" + amount
				+ "')]/following-sibling::td[1]//button[1])[1]"));

		return this;

	}

	/**
	 * Delete a certain cashflow entry with the given value date, creation date,
	 * type, status, currency, and amount
	 * 
	 * @param valueDate
	 * @param creationDate
	 * @param type
	 * @param status
	 * @param currency
	 * @param amount
	 */
	public CashflowPage deleteThisCashflowEntry(String valueDate, String creationDate, String type, String status,
			String currency, String amount) throws InterruptedException {
		scrollToBottom();
		clickElement(By.xpath("//td[.='" + valueDate + "']/following-sibling::td[.='" + creationDate
				+ "']/following-sibling::td[.='" + type + "']/following-sibling::td[.='" + status
				+ "']/following-sibling::td[.='" + currency + "']/following-sibling::td[.='" + amount
				+ "']/following-sibling::td[1]//button[@title='Delete']"));

		clickYesButtonIfVisible();
		clickOkButtonIfVisible();
		return this;

	}

	/**
	 * Delete all cashflow in the cashflow details
	 * 
	 * @return {@link CashflowPage}
	 * @throws InterruptedException
	 */
	public CashflowPage deleteAllCashflowInCashflowDetails() throws InterruptedException {

		waitForWaitingScreenNotVisible();

		WebElement elem = waitGet(By.id("gwt-debug-CashflowListPresenter-titleBox-input"));

		this.setCheckboxStatus2(elem, true);

		clickElement(By.id("gwt-debug-DepositWithdrawalEventTable-bulkDeleteButton"));

		clickYesButtonIfVisible();
		this.clickRefreshButton();

		return this;
	}

	/**
	 * Delete all cashflow in the cashflow schedules
	 * 
	 * @return {@link CashflowPage}
	 * @throws InterruptedException
	 */
	public CashflowPage deleteAllCashflowInCashflowSchedules() throws InterruptedException {

		int size = this.getSizeOfElements(By.xpath(".//*[@id='gwt-debug-NewBusinessEventWidget-deleteButton']"));

		for (int i = 0; i < size; i++) {

			clickElement(By.xpath(".//*[@id='gwt-debug-NewBusinessEventWidget-deleteButton']"));

			clickYesButtonIfVisible();
			clickOkButtonIfVisible();
		}

		this.clickRefreshButton();

		return this;
	}

	/**
	 * Select a option to filter the cashflow entries to be displayed
	 * 
	 * @param filter
	 * 
	 * @return {@link CashflowPage}
	 */
	public CashflowPage filterForCashflowDetails(String filter) {

		selectFromDropdown(By.id("gwt-debug-DepositWithdrawalHistoryView-filteredCashflowsListBox"), filter);

		return this;
	}

	/**
	 * Mark the selected cashflow entry completed
	 * 
	 * @return {@link CashflowPage}
	 */
	public CashflowPage markCompleted() {

		waitForElementVisible(By.id("gwt-debug-DepositWithdrawalEventTable-markButton"), 10);

		clickElement(By.id("gwt-debug-DepositWithdrawalEventTable-markButton"));

		waitForElementVisible(By.xpath("//div[contains(text(), 'completed')]"), 10);

		assertTrue(isElementVisible(By.xpath("//div[contains(text(), 'completed')]")));

		clickYesButtonIfVisible();
		scrollToTop();
		return this;
	}

	public CashflowPage uncheckNewBusiness(boolean unchecked) {

		waitForElementVisible(By.id("gwt-debug-NewBusinessEventWidget-allCheckBox-input"), 10);
		WebElement we = webDriver.findElement(By.id("gwt-debug-NewBusinessEventWidget-allCheckBox-input"));

		setCheckboxStatus2(we, unchecked);

		return this;

	}

	/**
	 * add related reference for cashflow
	 * 
	 * @param reference
	 * @return
	 */
	public CashflowPage addRelatedReference(String reference) {

		clickElement(By.id("gwt-debug-RelatedReferenceWidget-addRelatedRef"));
		int size = getSizeOfElements(By.xpath("//*[@id='gwt-debug-RelatedReferenceWidget-relatedRefPanel']//input"));

		sendKeysToElement(By.xpath(
				"(//*[@id='gwt-debug-RelatedReferenceWidget-relatedRefPanel']//input)[" + String.valueOf(size) + "]"),
				reference);
		// to focus on other elements
		clickElement(By.id("gwt-debug-MyOrderEditPopupView-commentsTextArea"));

		return this;
	}

	public CashflowPage deleteAllRelatedReference() {
		int size = getSizeOfElements(By.xpath(
				"//*[@id='gwt-debug-RelatedReferenceWidget-relatedRefPanel']//button[contains(@class,'remove')]"));
		for (int i = size; i > 0; i--) {
			clickElement(By
					.xpath("(//*[@id='gwt-debug-RelatedReferenceWidget-relatedRefPanel']//button[contains(@class,'remove')])["
							+ String.valueOf(i) + "]"));
		}

		return this;
	}
}
