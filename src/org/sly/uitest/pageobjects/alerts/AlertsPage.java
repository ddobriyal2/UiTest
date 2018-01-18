package org.sly.uitest.pageobjects.alerts;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.pageobjects.abstractpage.AdvancedSearchPage;
import org.sly.uitest.settings.Settings;

/**
 * This class represents the Alerts Page, which can be navigated by clicking
 * 'Manage' -> 'Alerts' or clicking the 'User Alert'image icon
 * 
 * @author Lynne Huang
 * @date : 20 Aug, 2015
 * @company Prive Financial
 * 
 */
public class AlertsPage extends AbstractPage {

	public AlertsPage(WebDriver webDriver) throws InterruptedException {

		super();
		this.webDriver = webDriver;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gwt-debug-MyMainMaterialView-mainPanel")));
		try {
			this.waitForElementPresent(By.id("gwt-debug-UserAlertOverviewView-advisorCompanyAlertsPanel"),
					Settings.WAIT_SECONDS * 4);
		} catch (TimeoutException e) {

		}
		assertTrue(pageContainsStr(" Triggered Alerts "));
		assertTrue(pageContainsStr(" Alert Definitions "));

	}

	/**
	 * On the Alerts page, click the "MY ALERTS" tab to navigate the page to the
	 * My Alerts subpage
	 * 
	 * @return {@link AlertsPage}
	 */
	public AlertsPage goToMyAlerts() {

		int size = this.getSizeOfElements(By.xpath("(//div[.='My Alerts'])"));
		clickElement(By.xpath("(//div[.='My Alerts'])[" + String.valueOf(size) + "]"));

		return this;
	}

	/**
	 * On the Alerts page, click the "COMPANY-WIDE" tab to navigate the page to
	 * the Company-Wide subpage
	 * 
	 * @return {@link AlertsPage}
	 */
	public AlertsPage goToCompanyWide() {

		int size = this.getSizeOfElements(By.xpath("(//div[.='Company-wide'])"));
		clickElement(By.xpath("(//div[.='Company-wide'])[" + String.valueOf(size) + "]"));

		return this;

	}

	/**
	 * When the page is navigated to "My Alerts", click the ADD ALERT button to
	 * add a new alert
	 * 
	 * @return {@link AlertsPage}
	 * 
	 * @throws InterruptedException
	 * 
	 */
	public AlertsPage clickAddAlertButtonInMyAlert() throws InterruptedException {

		waitForElementPresent(By.xpath(".//*[@id='gwt-debug-UserAlertOverviewView-addAlertButton']"), 30);
		int size = getSizeOfElements(By.id("gwt-debug-UserAlertOverviewView-addAlertButton"));
		clickElementByKeyboard(
				By.xpath("(.//*[@id='gwt-debug-UserAlertOverviewView-addAlertButton'])[" + String.valueOf(size) + "]"));

		return this;
	}

	/**
	 * When the page is navigated to "Company-Wide", click the ADD ALERT button
	 * to add a new alert
	 * 
	 * @return
	 */
	public AlertsPage clickAddAlertButtonInCompanyWide() {

		waitForElementPresent(By.xpath(".//*[@id='gwt-debug-UserAlertOverviewView-addAdvisorCompanyAlertButton']"), 30);
		int size = getSizeOfElements(By.id("gwt-debug-UserAlertOverviewView-addAdvisorCompanyAlertButton"));
		clickElementByKeyboard(By.xpath("(.//*[@id='gwt-debug-UserAlertOverviewView-addAdvisorCompanyAlertButton'])["
				+ String.valueOf(size) + "]"));
		return this;
	}

	public AdvancedSearchPage clickAdvancedSearchButtonForTriggeredAlertsInCompanyWide() throws InterruptedException {
		int size = this.getSizeOfElements(By.xpath(
				".//*[@id='gwt-debug-UserAlertOverviewView-advisorCompanyAlertsWidget']//img[@id='gwt-debug-TriggeredUserAlertListWidget-advancedSearchBtn']"));
		clickElement(By
				.xpath("(.//*[@id='gwt-debug-UserAlertOverviewView-advisorCompanyAlertsWidget']//img[@id='gwt-debug-TriggeredUserAlertListWidget-advancedSearchBtn'])["
						+ String.valueOf(size) + "]"));
		return new AdvancedSearchPage(webDriver, AlertsPage.class);
	}

	public AdvancedSearchPage clickAdvancedSearchButtonForTriggeredAlertsInMyAlerts() throws InterruptedException {
		int size = this.getSizeOfElements(By.xpath(
				".//*[@id='gwt-debug-UserAlertOverviewView-myAlertsWidget']//img[@id='gwt-debug-TriggeredUserAlertListWidget-advancedSearchBtn']"));
		clickElement(By
				.xpath("(.//*[@id='gwt-debug-UserAlertOverviewView-myAlertsWidget']//img[@id='gwt-debug-TriggeredUserAlertListWidget-advancedSearchBtn'])["
						+ String.valueOf(size) + "]"));

		return new AdvancedSearchPage(webDriver, AlertsPage.class);
	}

	// /**
	// * When the page is navigated to "Company-Wide", click the ADD ALERT
	// button
	// * to add a new alert
	// *
	// * @return {@link AlertsPage}
	// *
	// * @throws InterruptedException
	// *
	// * */
	// public AlertsPage clickCompanyAddAlertButton() throws
	// InterruptedException {
	// if (isElementDisplayed(By
	// .id("gwt-debug-UserAlertOverviewView-addAlertButton"))) {
	// clickElementByKeyboard(By
	// .id("gwt-debug-UserAlertOverviewView-addAlertButton"));
	// } else {
	// clickElementByKeyboard(By
	// .id("gwt-debug-UserAlertOverviewView-addAdvisorCompanyAlertButton"));
	// }
	//
	// /*
	// * testNonAssetOptionModulToggle trowing error with this code
	// * clickElement(By
	// * .id("gwt-debug-UserAlertOverviewView-addAlertButton"));
	// */
	//
	// return this;
	// }

	/**
	 * 
	 * After click ADD ALERT button or edit an existing alert, on the popup Edit
	 * Alert Definitions page, edit the alert type
	 * 
	 * @param String
	 *            type - Account Maturity, Account Overdraft, Client Birthday,
	 *            Deviation Portfolio Allocation, Investment Guideline, Overdue
	 *            Premium, Payment Expiration, Performance, Premium Renewal Due
	 * 
	 * @return {@link AlertsPage}
	 * @throws InterruptedException
	 */
	public AlertsPage editAlertType(String type) throws InterruptedException {

		waitForElementVisible(By.id("gwt-debug-EditUserAlertDefinitionView-alertTypeListBox"), 150);

		this.selectFromDropdown(By.id("gwt-debug-EditUserAlertDefinitionView-alertTypeListBox"), type);
		// clickElement(By.xpath(
		// ".//select[@id='gwt-debug-EditUserAlertDefinitionView-alertTypeListBox']/option[.='"
		// + type + "']"));

		return this;
	}

	/**
	 * After click ADD ALERT button or edit an existing alert, on the popup Edit
	 * Alert Definitions page, select the Account Maturity alert; edit all the
	 * following required fields
	 * 
	 * @param number
	 * @param timeFrame
	 * @param applyTo
	 * 
	 * @return {@link AlertsPage}
	 */
	public AlertsPage editAccountMaturity(String number, String timeFrame, String applyTo) {

		sendKeysToElement(By.id("gwt-debug-EditUserAlertAccountMaturity-timeFrameValueTextBox"), number);

		selectFromDropdown(By.id("gwt-debug-EditUserAlertAccountMaturity-timeFrameTypeListBox"), timeFrame);

		clickElement(By.xpath("//label[.='" + applyTo + "']"));

		return this;
	};

	/**
	 * After click ADD ALERT button or edit an existing alert, on the popup Edit
	 * Alert Definitions page, select the Account Overdraft alert; edit all the
	 * following required fields
	 * 
	 * @param applyTo
	 * 
	 * @return {@link AlertsPage}
	 */
	public AlertsPage editAccountOverdraft(String applyTo) {

		clickElement(By.xpath("//label[.='" + applyTo + "']"));

		return this;
	}

	/**
	 * After click ADD ALERT button or edit an existing alert, on the popup Edit
	 * Alert Definitions page, select the Client Birthday alert; edit all the
	 * following required fields
	 * 
	 * @param number
	 * @param timeFrame
	 * @param applyTo
	 * 
	 * @return {@link AlertsPage}
	 */
	public AlertsPage editClientBirthday(String number, String timeFrame, String applyTo) {

		sendKeysToElement(By.id("gwt-debug-EditUserAlertClientBirthdayWidget-timeFrameValueTextBox"), number);

		selectFromDropdown(By.id("gwt-debug-EditUserAlertClientBirthdayWidget-timeFrameTypeListBox"), timeFrame);

		clickElement(By.xpath("//label[.='" + applyTo + "']"));

		return this;
	}

	/**
	 * After click ADD ALERT button or edit an existing alert, on the popup Edit
	 * Alert Definitions page, select the Overdue Premium alert; edit all the
	 * following required fields
	 * 
	 * @param number
	 * @param applyTo
	 * 
	 * @return {@link AlertsPage}
	 */
	public AlertsPage editOverduePremium(String number, String applyTo) {

		sendKeysToElement(By.id("gwt-debug-EditUserAlertOverduePremiumWidget-timeFrameValueTextBox"), number);

		clickElement(By.xpath("//label[.='" + applyTo + "']"));

		return this;
	}

	/**
	 * After click ADD ALERT button or edit an existing alert, on the popup Edit
	 * Alert Definitions page, select the Payment Expiration alert; edit all the
	 * following required fields
	 * 
	 * @param number
	 * @param timeFrame
	 * @param applyTo
	 * 
	 * @return {@link AlertsPage}
	 */
	public AlertsPage editPaymentExpiration(String number, String timeFrame, String applyTo) {

		sendKeysToElement(By.id("gwt-debug-EditUserAlertPaymentMethodExpirationWidget-timeFrameValueTextBox"), number);

		selectFromDropdown(By.id("gwt-debug-EditUserAlertPaymentMethodExpirationWidget-timeFrameTypeListBox"),
				timeFrame);

		clickElement(By.xpath("//label[.='" + applyTo + "']"));

		return this;
	}

	/**
	 * After click ADD ALERT button or edit an existing alert, on the popup Edit
	 * Alert Definitions page, select the Performance alert; edit all the
	 * following required fields
	 * 
	 * @param type
	 * @param direction
	 * @param threshold
	 * @param number
	 * @param timeFrame
	 * @param applyTo
	 * 
	 * @return {@link AlertsPage}
	 */
	public AlertsPage editPerformance(String type, String direction, String threshold, String number,
			String timeFrame) {

		selectFromDropdown(By.id("gwt-debug-EditUserAlertPerformanceWidget-typeListBox"), type);

		selectFromDropdown(By.id("gwt-debug-EditUserAlertPerformanceWidget-dirListBox"), direction);

		selectFromDropdown(By.id("gwt-debug-EditUserAlertPerformanceWidget-thresholdListBox"), threshold);

		sendKeysToElement(By.id("gwt-debug-EditUserAlertPerformanceWidget-timeFrameTextBox"), number);

		selectFromDropdown(By.id("gwt-debug-EditUserAlertPerformanceWidget-timeFrameTypeListBox"), timeFrame);

		return this;
	}

	/**
	 * After click ADD ALERT button or edit an existing alert, on the popup Edit
	 * Alert Definitions page, select the Premium Renewal Due alert; edit all
	 * the following required fields
	 * 
	 * @param number
	 * @param timeFrame
	 * @param applyTo
	 * 
	 * @return {@link AlertsPage}
	 */
	public AlertsPage editPremiumRenewalDue(String number, String applyTo) {

		sendKeysToElement(By.id("gwt-debug-EditUserAlertPremiumRenewalDueWidget-timeFrameValueTextBox"), number);

		clickElement(By.xpath("//label[.='" + applyTo + "']"));

		return this;
	}

	/**
	 * After click ADD ALERT button or edit an existing alert, on the popup Edit
	 * Alert Definitions page, select the Deviation Portfolio Allocation alert;
	 * edit all the following required fields
	 * 
	 * @param number
	 * @param applyTo
	 * 
	 * @return {@link AlertsPage}
	 */
	public AlertsPage editDeviationPortfolioAllocation(String number, String applyTo) {

		selectFromDropdown(By.id("gwt-debug-EditUserAlertOverduePremiumWidget-thresholdListBox"), number);

		clickElement(By.xpath("//label[.='" + applyTo + "']"));

		return this;
	}

	/**
	 * 
	 * Click the red-white minus icon to delete the first alert that matches the
	 * given name
	 *
	 * @param type
	 *            the alert type will be deleted
	 * 
	 * @return {@link AlertsPage}
	 * @throws InterruptedException
	 * 
	 */
	public AlertsPage deleteAlertByType(String type) throws InterruptedException {

		clickElement(By.xpath("//td[.='" + type
				+ "']/following-sibling::td//button[@id='gwt-debug-UserAlertDefinitionListWidget-deleteButton']"));

		clickYesButtonIfVisible();
		// clickElementAndWait3(By.id("gwt-debug-CustomDialog-yesButton"));

		return this;
	}

	/**
	 * Click the red-white minus icon to delete the first alert that matches the
	 * given name the given description
	 *
	 * @param type
	 *            the alert type will be deleted
	 * @param description
	 *            the description of the alert
	 * 
	 * @return {@link AlertsPage}
	 * @throws InterruptedException
	 */
	public AlertsPage deleteAlertByTypeAndDescription(String alertType, String description)
			throws InterruptedException {

		try {
			clickElement(By.xpath("(//td[.='" + alertType + "'])[2]/following-sibling::td[div[.='" + description
					+ "']]/following-sibling::td[2]/button[@title='Delete']"));
		} catch (org.openqa.selenium.TimeoutException e) {
			clickElement(By.xpath("(//td[.='" + alertType
					+ "'])[2]/following-sibling::td[div[1]]/following-sibling::td[2]/button[@title='Delete']"));
		}

		clickElement(By.id("gwt-debug-CustomDialog-yesButton"));

		return this;
	}

	/**
	 * Click the red-white minus icon to delete all alerts under the
	 * Company-Wide tab
	 * 
	 * @return {@link AlertsPage}
	 * @throws InterruptedException
	 */
	public AlertsPage deleteAllCompanyAlerts() {
		By by = By.xpath(
				".//*[@id='gwt-debug-UserAlertOverviewView-advisorCompanyAlertsWidget']//*[@id='gwt-debug-UserAlertDefinitionListWidget-deleteButton']");
		By by2;
		try {
			waitForElementVisible(by, 10);
		} catch (TimeoutException e) {

		}

		int size = getSizeOfElements(by);

		if (size > 1) {
			for (int i = size; i >= 1; i--) {
				by2 = By.xpath(
						"(.//*[@id='gwt-debug-UserAlertOverviewView-advisorCompanyAlertsWidget']//*[@id='gwt-debug-UserAlertDefinitionListWidget-deleteButton'])["
								+ String.valueOf(i) + "]");
				try {
					waitForElementVisible(by2, 5);

					clickElement(by2);
					clickYesButtonIfVisible();
				} catch (TimeoutException e) {

					// this.refreshPage();
					assertFalse(this.isElementDisplayed(by2));
				}

			}

		} else {
			if (size == 0) {
				return this;
			} else {
				by2 = By.xpath(
						"(.//*[@id='gwt-debug-UserAlertOverviewView-advisorCompanyAlertsWidget']//*[@id='gwt-debug-UserAlertDefinitionListWidget-deleteButton'])[1]");
				waitForElementVisible(by2, 30);

				clickElement(by2);

				clickYesButtonIfVisible();

			}

		}

		return this;
	}

	/**
	 * Click the red-white minus icon to delete all alerts under the My Alerts
	 * tab
	 * 
	 * @return {@link AlertsPage}
	 * @throws InterruptedException
	 */
	public AlertsPage deleteAllMyAlerts() {
		By by = By.xpath(
				".//*[@id='gwt-debug-UserAlertOverviewView-myAlertsWidget']//*[@id='gwt-debug-UserAlertDefinitionListWidget-deleteButton']");
		By by2;
		try {
			waitForElementVisible(by, 10);
		} catch (TimeoutException e) {

		}

		int size = getSizeOfElements(by);

		if (size > 1) {
			for (int i = size; i >= 1; i--) {
				by2 = By.xpath(
						"(.//*[@id='gwt-debug-UserAlertOverviewView-myAlertsWidget']//*[@id='gwt-debug-UserAlertDefinitionListWidget-deleteButton'])["
								+ String.valueOf(i) + "]");
				try {
					waitForElementVisible(by2, 5);

					clickElement(by2);
					clickYesButtonIfVisible();
				} catch (TimeoutException e) {

					this.refreshPage();
					assertFalse(this.isElementDisplayed(by2));
				}
			}

		} else {
			if (size == 0) {
				return this;
			} else {
				by2 = By.xpath(
						"(.//*[@id='gwt-debug-UserAlertOverviewView-myAlertsWidget']//*[@id='gwt-debug-UserAlertDefinitionListWidget-deleteButton'])[1]");
				waitForElementVisible(by2, 5);

				clickElement(by2);

				clickYesButtonIfVisible();

			}

		}
		return this;
	}

	/**
	 * After click ADD ALERT button or edit an existing alert, on the popup Edit
	 * Alert Definitions page, click the SAVE button
	 * 
	 * @return {@link AlertsPage}
	 */
	public AlertsPage clickSaveButton() throws Exception {

		clickElement(By.id("gwt-debug-EditUserAlertDefinitionView-submitBtn"));

		return this;

	}

	/**
	 * After click ADD ALERT button or edit an existing alert, on the popup Edit
	 * Alert Definitions page, click the CANCEL button
	 * 
	 * @return {@link AlertsPage}
	 */
	public AlertsPage clickCancelButton() {

		clickElement(By.id("gwt-debug-EditUserAlertDefinitionView-cancelBtn"));

		return this;
	}

	public AlertsPage expandTriggeredAlerts(String level, String alertType) {
		int size;
		String xpath = "";
		if (level.equals("company")) {
			xpath = "(.//*[@id='gwt-debug-UserAlertOverviewView-advisorCompanyAlertsPanel']//*[@id='gwt-debug-TriggeredUserAlertListWidget-tablePanel']//tr//td[.='"
					+ alertType
					+ "']//following-sibling::td//button[@id='gwt-debug-TriggeredUserAlertListWidget-expandCollapseButton'])";
		} else {
			xpath = "(.//*[@id='gwt-debug-UserAlertOverviewView-myAlertsPanel']//*[@id='gwt-debug-TriggeredUserAlertListWidget-tablePanel']//tr//td[.='"
					+ alertType
					+ "']//following-sibling::td//button[@id='gwt-debug-TriggeredUserAlertListWidget-expandCollapseButton'])";
		}
		size = this.getSizeOfElements(By.xpath(xpath));

		clickElement(By.xpath(xpath + "[" + size + "]"));

		return this;
	}
}
