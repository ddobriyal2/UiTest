package org.sly.uitest.pageobjects.commissioning;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.pageobjects.clientsandaccounts.DetailEditPage;
import org.sly.uitest.settings.Settings;

/**
 * 
 * This class represents the Edit page when create or edit a new business event,
 * which can be navigated by clicking 'Clients' -> 'New Business' -> 'NEW'
 * 
 * URL:
 * "http://192.168.1.104:8080/SlyAWS/?locale=en#businesseventedit;isReadOnly=false"
 * 
 * @author Lynne Huang
 * @date : 26 Aug, 2015
 * @company Prive Financial
 */
public class NewBusinessEventEditPage extends AbstractPage {

	/**
	 * @param webDriver
	 */
	public NewBusinessEventEditPage(WebDriver webDriver) {

		super();
		this.webDriver = webDriver;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mainPanel")));

		} catch (Exception ex) {
			// wait.until(ExpectedConditions.visibilityOfElementLocated(By
			// .id("gwt-debug-MyMainClassicView-mainPanel")));

		}

		try {
			wait(5);
		} catch (InterruptedException e) {

		}
		// assertTrue(pageContainsStr(" Business Event "));
	}

	/**
	 * Under the Client information section, select an existing client
	 * 
	 * @name the name of the existing client
	 * 
	 * @return {@link NewBusinessEventEditPage}
	 * @throws InterruptedException
	 */
	public NewBusinessEventEditPage selectExistingClient(String name) throws InterruptedException {

		By by = By.id("gwt-debug-BusinessEventEditWidgetView-clientNameListBox");
		waitForElementVisible(by, 10);
		this.selectFromDropdown(by, name);
		// Select dropdown = new Select(
		// webDriver.findElement(By.id("gwt-debug-BusinessEventEditWidgetView-clientNameListBox")));
		// wait(Settings.WAIT_SECONDS);
		// dropdown.selectByValue(name);
		/*
		 * selectFromDropdown(
		 * By.id("gwt-debug-BusinessEventEditWidgetView-clientNameListBox"),
		 * name);
		 */

		return this;
	}

	/**
	 * Under the Basic information section, edit the main advisor
	 * 
	 * @param advisor
	 * @return {@link NewBusinessEventEditPage}
	 */
	public NewBusinessEventEditPage editMainAdvisor(String advisor) {

		waitForElementVisible(By.xpath(".//*[@id='gwt-debug-BusinessEventEditWidgetView-mainAdvisorListBox']/option"),
				30);

		selectFromDropdown(By.id("gwt-debug-BusinessEventEditWidgetView-mainAdvisorListBox"), advisor);

		return this;
	}

	/**
	 * Under the Basic information section, edit the main admin
	 * 
	 * @param admin
	 * @return {@link NewBusinessEventEditPage}
	 */
	public NewBusinessEventEditPage editMainAdmin(String admin) {

		waitForElementVisible(By.xpath(".//*[@id='gwt-debug-BusinessEventEditWidgetView-mainClericalListBox']/option"),
				30);

		selectFromDropdown(By.id("gwt-debug-BusinessEventEditWidgetView-mainClericalListBox"), admin);

		return this;
	}

	/**
	 * Under the Client information section, click "New Client" to create a new
	 * client
	 * 
	 * @return {@link DetailEditPage}
	 */
	public DetailEditPage createNewClient() {

		clickElement(By.id("gwt-debug-BusinessEventEditWidgetView-addNewClientLink"));

		return new DetailEditPage(webDriver, NewBusinessEventEditPage.class);
	}

	/**
	 * edit platform suffix of new business event
	 * 
	 * @param platform
	 * @return
	 */
	public NewBusinessEventEditPage editPlatformSuffix(String platform) {
		waitForElementVisible(By.id("gwt-debug-BusinessEventEditWidgetView-executionPlatformConfigListBox"), 30);
		selectFromDropdown(By.id("gwt-debug-BusinessEventEditWidgetView-executionPlatformConfigListBox"), platform);
		return this;
	}

	/**
	 * edit the clerial admin of new busines event
	 * 
	 * @param admin
	 * @return{@link NewBusinessEventEditPage}
	 */
	public NewBusinessEventEditPage editAdmin(String admin) {
		selectFromDropdown(By.id("gwt-debug-BusinessEventEditWidgetView-mainClericalListBox"), admin);
		return this;
	}

	/**
	 * Under the Product Informtaion section, edit the platform category
	 * 
	 * @param category
	 * @return {@link NewBusinessEventEditPage}
	 */
	public NewBusinessEventEditPage editPlatformCategory(String category) {

		selectFromDropdown(By.id("gwt-debug-BusinessEventEditWidgetView-exePlatCategoryListBox"), category);

		return this;
	}

	/**
	 * Under the Product Information section, edit the product platform
	 * 
	 * @param platform
	 *            the name of the product
	 * 
	 * @return {@link NewBusinessEventEditPage}
	 * @throws InterruptedException
	 */
	public NewBusinessEventEditPage editProductPlatform(String platform) throws InterruptedException {

		waitForElementVisible(By.id("gwt-debug-BusinessEventEditWidgetView-executionPlatformObjectListBox"), 10);
		wait(Settings.WAIT_SECONDS);
		selectFromDropdown(By.id("gwt-debug-BusinessEventEditWidgetView-executionPlatformObjectListBox"), platform);

		return this;

	}

	/**
	 * Under the Product Information section, edit the investor account
	 * 
	 * @param account
	 *            the investor account
	 * @return {@link NewBusinessEventEditPage}
	 */
	public NewBusinessEventEditPage editInvestorAccount(String account) {

		waitForElementVisible(By.id("gwt-debug-BusinessEventEditWidgetView-investorAccountListBox"), 10);

		selectFromDropdown(By.id("gwt-debug-BusinessEventEditWidgetView-investorAccountListBox"), account);

		return this;
	}

	/**
	 * Under the Dates section, edit the maturity date
	 * 
	 * @param date
	 * @return {@link NewBusinessEventEditPage}
	 */
	public NewBusinessEventEditPage editMaturityDate(String date) {

		sendKeysToElement(By.id("gwt-debug-BusinessEventEditWidgetView-accountTillDateTextBox"), date);

		return this;
	}

	/**
	 * Under the Dates section, edit the term years
	 * 
	 * @param year
	 * @return {@link NewBusinessEventEditPage}
	 */
	public NewBusinessEventEditPage editTermYears(String year) {

		waitForElementVisible(By.id("gwt-debug-BusinessEventEditWidgetView-accountTermTextBox"), 10);

		sendKeysToElement(By.id("gwt-debug-BusinessEventEditWidgetView-accountTermTextBox"), year);

		return this;
	}

	/**
	 * Under the Dates section, edit the indemnity start date
	 * 
	 * @param date
	 * @return {@link NewBusinessEventEditPage}
	 */
	public NewBusinessEventEditPage editIndemnityStartDate(String date) {

		sendKeysToElement(By.id("gwt-debug-BusinessEventEditWidgetView-indemnityDateStartTextBox"), date);

		return this;
	}

	/**
	 * Under the Dates section, edit the indemnity months
	 * 
	 * @param month
	 * @return {@link NewBusinessEventEditPage}
	 */
	public NewBusinessEventEditPage editIndemnityMonths(String month) {

		sendKeysToElement(By.id("gwt-debug-BusinessEventEditWidgetView-indemnityTextBox"), month);

		return this;
	}

	/**
	 * Under the Dates section, click the icon beside Indemnity(months) to
	 * calculate indemnity
	 * 
	 * @return {@link NewBusinessEventEditPage}
	 */
	public NewBusinessEventEditPage calculateIndemnitybutton() {

		clickElement(By.id("gwt-debug-BusinessEventEditWidgetView-calcIndemnityMonthsButton"));

		return this;

	}

	/**
	 * Under the Funding (expected) section, edit the regular premium
	 * 
	 * @param amount
	 * @return {@link NewBusinessEventEditPage}
	 */
	public NewBusinessEventEditPage editRegularPremium(String amount) {

		sendKeysToElement(By.id("gwt-debug-BusinessEventEditWidgetView-premiumTextBox"), amount);

		return this;
	}

	/**
	 * Under the Commission/Fees section, edit the amount for the expected
	 * initial commission
	 * 
	 * @param amount
	 * @return {@link NewBusinessEventEditPage}
	 */
	public NewBusinessEventEditPage editExpectedInitialCommission(String amount) {

		sendKeysToElement(By.id("gwt-debug-BusinessEventEditWidgetView-expectedInitialCommissionTextBox"), amount);

		return this;
	}

	/**
	 * Under Commission/Fees Sections, click the calculate BV button
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public NewBusinessEventEditPage clickCalculateBvButton() throws InterruptedException {
		clickElement(By.id("gwt-debug-BusinessEventEditWidgetView-autoPopulateBVBtn"));
		wait(5);
		return this;
	}

	/**
	 * Under the Commission Split section, click the populate button to split
	 * commission
	 * 
	 * @return {@link NewBusinessEventEditPage}
	 */
	public NewBusinessEventEditPage clickSplitAllocationButton() {

		clickElement(By.id("gwt-debug-BusinessEventEditWidgetView-autoPopulateSplitMappingBtn"));

		return this;
	}

	/**
	 * Under the Commission Split section, click the green plus icon outside the
	 * tab to to add new split(tab)
	 * 
	 * @param type
	 *            the type of the commission
	 * @return {@link NewBusinessEventEditPage}
	 */
	public NewBusinessEventEditPage addNewSplit(String type) {

		clickElement(By.id("gwt-debug-BusinessEventEditWidgetView-commSplitTypeAddBtn"));

		selectFromDropdown(By.id("gwt-debug-BusinessEventEditWidgetView-CustomDialog-typeList"), type);

		clickElement(By.id("gwt-debug-BusinessEventEditWidgetView-CustomDialog-okBtn"));

		return this;
	}

	/**
	 * Under the Commission Split section, click the green plus icon inside the
	 * tab to add or edit commission split mapping entry
	 * 
	 * @param tab
	 *            the tab under the commission split
	 * @return {@link NewBusinessEventEditPage}
	 */
	public NewBusinessEventEditPage addNewCommissionTo(String tab) {

		clickElement(By.id("gwt-debug-CommissionSplitMappingInnerWidget-addButton-" + tab));

		return this;
	}

	/**
	 * Under the Commission Split section, after the Add/Edit Commission Split
	 * Mapping Entry page popup, edit the type
	 * 
	 * @param type
	 * 
	 * @return {@link NewBusinessEventEditPage}
	 */
	public NewBusinessEventEditPage editCommissionSplitType(String type) {

		selectFromDropdown(By.id("gwt-debug-CommissionSplitMappingEditWidget-typeListBox"), type);

		return this;
	}

	/**
	 * Under the Commission Split section, after the Add/Edit Commission Split
	 * Mapping Entry page popup, edit the recepient
	 * 
	 * @param recipient
	 * 
	 * @return {@link NewBusinessEventEditPage}
	 */
	public NewBusinessEventEditPage editCommissionSplitRecipient(String recepient) {

		selectFromDropdown(By.id("gwt-debug-CommissionSplitMappingEditWidget-recipientListBox"), recepient);

		return this;
	}

	/**
	 * Under the Commission Split section, after the Add/Edit Commission Split
	 * Mapping Entry page popup, edit the allocation
	 * 
	 * @param allocation
	 * 
	 * @return {@link NewBusinessEventEditPage}
	 */
	public NewBusinessEventEditPage editCommissionSplitAllocation(String allocation) {
		waitForElementVisible(By.id("gwt-debug-CommissionSplitMappingEditWidget-allocationText"), 10);
		sendKeysToElement(By.id("gwt-debug-CommissionSplitMappingEditWidget-allocationText"), allocation);

		return this;
	}

	/**
	 * Under Commission Split section, click the pencil button next to specified
	 * recipient
	 * 
	 * @param recipient
	 * @return {@link NewBusinessEventEditPage}
	 */
	public NewBusinessEventEditPage clickEditSplitButtonByRecipient(String recipient) {

		clickElementByKeyboard(
				By.xpath(".//*[.='" + recipient + "']/following-sibling::td[4]//button[@class='settingButton']"));

		return this;
	}

	/**
	 * Under Commission Split section, click the delete button next to specified
	 * recipient
	 * 
	 * @param recipient
	 * @return {@link NewBusinessEventEditPage}
	 */
	public NewBusinessEventEditPage clickDeleteSplitButtonByRecipient(String recipient) {

		clickElementByKeyboard(By.xpath(".//*[.='" + recipient
				+ "']/following-sibling::td[4]//button[@class='fa fa-button fa-minus-circle size-16 remove-red']"));

		return this;
	}

	/**
	 * Under the Commission Split section, after the Add/Edit Commission Split
	 * Mapping Entry page popup and finish edit, click the OK button
	 * 
	 * @return {@link NewBusinessEventEditPage}
	 */
	public NewBusinessEventEditPage clickSaveButtonForComSplitMapping() {
		try {
			wait(3);
		} catch (InterruptedException e) {

		}
		clickElement(By.id("gwt-debug-CommissionSplitMappingEditWidget-submitBtn"));
		log("Save button: Clicked");
		return this;
	}

	/**
	 * On the edit Business Event page, click SAVE DRAFT button, this business
	 * event will be saved as draft
	 * 
	 * @return {@link NewBusinessEventPage}
	 */
	public NewBusinessEventPage clickSaveDraftButton() {

		clickElement(By.id("gwt-debug-BusinessEventEditWidgetView-draftBtn"));

		return new NewBusinessEventPage(webDriver);
	}

	/**
	 * On the edit Business Event page, click COMPLETE button, this business
	 * event will be marked as Completed status
	 * 
	 * @return {@link NewBusinessEventPage}
	 */
	public NewBusinessEventPage clickCompleteNBEButton() {

		waitForElementVisible(By.id("gwt-debug-BusinessEventEditWidgetView-completeBtn"), 10);

		clickElement(By.id("gwt-debug-BusinessEventEditWidgetView-completeBtn"));

		waitForElementVisible(By.id("gwt-debug-CustomDialog-yesButton"), 5);

		for (int i = 0; i < Settings.ATTEMPT_LOOPING_NUMBER; i++) {
			if (!isElementVisible(By.id("gwt-debug-CustomDialog-yesButton"))) {

				clickElementByKeyboard(By.id("gwt-debug-BusinessEventEditWidgetView-completeBtn"));

				waitForElementVisible(By.id("gwt-debug-CustomDialog-yesButton"), 5);
			}

		}

		clickElement(By.id("gwt-debug-CustomDialog-yesButton"));
		clickYesButtonIfVisible();
		return new NewBusinessEventPage(webDriver);
	}
}
