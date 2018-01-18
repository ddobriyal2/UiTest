package org.sly.uitest.pageobjects.admin;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;

/**
 * This class represents the Admin Reporting page when login as system admin
 * 
 * @author Lynne Huang
 * @date : 28 Aug, 2015
 * @company Prive Financial
 */
public class AdminReportingPage extends AbstractPage {

	public AdminReportingPage(WebDriver webDriver) {
		super();
		this.webDriver = webDriver;
		// TODO Auto-generated constructor stub

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gwt-debug-MyMainMaterialView-mainPanel")));

		} catch (Exception ex) {
			// wait.until(ExpectedConditions.visibilityOfElementLocated(By
			// .id("gwt-debug-MyMainMaterialView-mainPanel")));

		}
		assertTrue(pageContainsStr(" Reporting "));

	}

	/**
	 * @param String
	 *            type - Consolidated User PDF Report, Investor Account PDF
	 *            Report
	 */
	public AdminReportingPage selectReportType(String type) {

		selectFromDropdown(By.id("gwt-debug-MaintenanceReportingView-listBoxGenReportType"), type);

		return this;
	}

	/**
	 * 
	 * */
	public AdminReportingPage selectMonthlyReport(String yyyy_mm) {

		clickElement(By.id("gwt-debug-MaintenanceReportingView-textboxGenInvestorAccountReportsMonthly-label"));

		sendKeysToElement(By.id("gwt-debug-MaintenanceReportingView-textboxBatchGenerateReportYearMonth"), yyyy_mm);

		return this;
	}

	/**
	 * 
	 * */
	public AdminReportingPage selectQualterlyReport(String year, String quater) {

		clickElement(By.id("gwt-debug-MaintenanceReportingView-textboxGenInvestorAccountReportsQuarterly-label"));

		selectFromDropdown(By.id("gwt-debug-MaintenanceReportingView-textboxBatchGenerateReportYear"), year);

		if (quater.equals("Q1")) {
			clickElement(By.id("gwt-debug-MaintenanceReportingView-textboxBatchGenerateReportQ1-label"));
		} else if (quater.equals("Q2")) {
			clickElement(By.id("gwt-debug-MaintenanceReportingView-textboxBatchGenerateReportQ2-label"));
		} else if (quater.equals("Q3")) {
			clickElement(By.id("gwt-debug-MaintenanceReportingView-textboxBatchGenerateReportQ3-label"));
		} else {
			clickElement(By.id("gwt-debug-MaintenanceReportingView-textboxBatchGenerateReportQ4-label"));
		}

		return this;
	}

	public AdminReportingPage inputAccountKeys(String... keys) {

		waitForElementVisible(By.id("gwt-debug-MaintenanceReportingView-textAreaGenerateReportsInvestorAccountKeys"),
				10);
		WebElement elem = waitGet(
				By.id("gwt-debug-MaintenanceReportingView-textAreaGenerateReportsInvestorAccountKeys"));
		elem.clear();
		for (String key : keys) {

			elem.sendKeys(key);
			elem.sendKeys("\n");
		}

		return this;
	}

	public AdminReportingPage generatePdfReports() {
		waitForElementVisible(By.id("gwt-debug-MaintenanceReportingView-textAreaGenerateReportsInvestorAccountKeys"),
				10);

		String keys = webDriver
				.findElement(By.id("gwt-debug-MaintenanceReportingView-textAreaGenerateReportsInvestorAccountKeys"))
				.getAttribute("value");

		// log(keys);

		if (!keys.equals("")) {

			clickElementByKeyboard(By.id("gwt-debug-MaintenanceReportingView-buttonBatchGenerateReport"));

			clickElement(By.id("gwt-debug-CustomDialog-okButton"));
		}

		return this;
	}
}
