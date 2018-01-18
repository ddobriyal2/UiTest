package org.sly.uitest.pageobjects.clientsandaccounts;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;

/**
 * This class is for adding or editing Sub Accounts which can be assessed by
 * clicking 'Clients' -> 'Account Overview' -> choose any account -> Details ->
 * Sub Accounts
 * 
 * @author Benny Leung
 * @date : 16 Aug, 2016
 * @company Prive Financial
 */
public class SubAccountsPage extends AbstractPage {

	public SubAccountsPage(WebDriver webDriver) {

		super();
		this.webDriver = webDriver;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gwt-debug-SubAccountEditWidget-submitBtn")));

		assertTrue(pageContainsStr("Sub account type"));

	}

	/**
	 * Edit the type of Sub Account
	 * 
	 * @param type
	 *            two type of Sub Account: Securities or Cash
	 * @return {@link SubAccountPage)
	 */
	public SubAccountsPage editSubAccountType(String type) {

		selectFromDropdown(By.id("gwt-debug-SubAccountEditWidget-typeListBox"), type);
		return this;
	}

	/**
	 * Edit the name of Sub Account
	 * 
	 * @param name
	 * @return {@link SubAccountsPage}
	 */
	public SubAccountsPage editName(String name) {

		sendKeysToElement(By.id("gwt-debug-SubAccountEditWidget-labelTextBox"), name);
		return this;
	}

	/**
	 * Edit the account number of Sub Account
	 * 
	 * @param number
	 *            account number of Sub Account
	 * @return {@link SubAccountsPage}
	 */
	public SubAccountsPage editAccountNumber(String number) {

		sendKeysToElement(By.id("gwt-debug-SubAccountEditWidget-subAccountNumberTextBox"), number);
		return this;
	}

	/**
	 * Edit the currency of Sub Account
	 * 
	 * @param currency
	 * @return {@link SubAccountsPage}
	 */
	public SubAccountsPage editCurrency(String currency) {

		selectFromDropdown(By.id("gwt-debug-SubAccountEditWidget-currencyListBox"), currency);
		return this;
	}

	/**
	 * Edit the Open date of Sub Account
	 * 
	 * @param day
	 * @param month
	 * @param year
	 * @return {@link SubAccountsPage}
	 * @throws InterruptedException
	 */
	public SubAccountsPage editOpenDate(String day, String month, String year) throws InterruptedException {

		wait(2);

		selectFromDropdown(
				By.xpath(
						".//div[@id='gwt-debug-SubAccountEditWidget-openDateCompositeDatePicker']//select[@id='gwt-debug-CompositeDatePicker-dayListBox']"),
				day);
		wait(1);
		selectFromDropdown(
				By.xpath(
						".//div[@id='gwt-debug-SubAccountEditWidget-openDateCompositeDatePicker']//select[@id='gwt-debug-CompositeDatePicker-monthListBox']"),
				month);

		sendKeysToElement(
				By.xpath(
						".//div[@id='gwt-debug-SubAccountEditWidget-openDateCompositeDatePicker']//*[@id='gwt-debug-CompositeDatePicker-yearTextBox']"),
				year);

		return this;
	}

	/**
	 * Edit the close date of Sub Account
	 * 
	 * @param day
	 * @param month
	 * @param year
	 * @return {@link SubAccountsPage}
	 * @throws InterruptedException
	 */
	public SubAccountsPage editEndDate(String day, String month, String year) throws InterruptedException {

		// wait(2);
		selectFromDropdown(
				By.xpath(
						".//div[@id='gwt-debug-SubAccountEditWidget-closeDateCompositeDatePicker']//select[@id='gwt-debug-CompositeDatePicker-dayListBox']"),
				day);
		// wait(1);
		selectFromDropdown(
				By.xpath(
						".//div[@id='gwt-debug-SubAccountEditWidget-closeDateCompositeDatePicker']//select[@id='gwt-debug-CompositeDatePicker-monthListBox']"),
				month);

		// wait(1);
		sendKeysToElement(
				By.xpath(
						".//div[@id='gwt-debug-SubAccountEditWidget-closeDateCompositeDatePicker']//*[@id='gwt-debug-CompositeDatePicker-yearTextBox']"),
				year);

		return this;
	}

	public void clickSaveButton() throws Exception {
		wait(1);
		clickElementAndWait3(By.id("gwt-debug-SubAccountEditWidget-submitBtn"));

	}

	public void clickCancelButton() throws InterruptedException {
		clickElementAndWait3(By.id("gwt-debug-SubAccountEditWidget-cancelBtn"));
	}

}
