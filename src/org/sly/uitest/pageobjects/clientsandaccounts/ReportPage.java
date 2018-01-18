package org.sly.uitest.pageobjects.clientsandaccounts;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.settings.Settings;

/**
 * 
 * This class represents the Reports Page (tab) of a client, which can be
 * navigated by clicking 'Clients' -> 'Client Overview' -> choose any client ->
 * 'Reports (tab)'
 * 
 * URL:
 * "http://192.168.1.104:8080/SlyAWS/?locale=en#generalUserDetailsReports;userKey=8462;detailType=1"
 * 
 * @author Lynne Huang
 * @date : 14 Sep, 2015
 * @company Prive Financial
 */
public class ReportPage extends AbstractPage {

	/**
	 * @param webDriver
	 */
	public ReportPage(WebDriver webDriver) {

		super();
		this.webDriver = webDriver;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("gwt-debug-GeneralOverviewReportsView-contentPanel")));

		assertTrue(pageContainsStr("Reports"));
	}

	/**
	 * Click the black-white plus icon to add a new report
	 * 
	 * @return {@link ReportPage}
	 */
	public ReportPage addReport() {

		clickElement(By.xpath(".//*[@id='gwt-debug-ReportsSectionPresenter-image' and @title='Add']"));

		return this;
	}

	/**
	 * Click the red-white minus icons to delete all the reports
	 * 
	 * @return {@link ReportPage}
	 * @throws InterruptedException
	 */
	public ReportPage deleteAllReports() throws InterruptedException {

		try {
			waitForElementVisible(By.xpath(".//*[@id='gwt-debug-ReportsSectionPresenter-deleteImage']"), 10);
		} catch (TimeoutException e) {

		}
		int size = getSizeOfElements(By.xpath(".//*[@id='gwt-debug-ReportsSectionPresenter-deleteImage']"));

		for (int i = 0; i <= size; i++) {

			if (isElementVisible(By.id("gwt-debug-ReportsSectionPresenter-deleteImage"))) {

				clickElement(By.id("gwt-debug-ReportsSectionPresenter-deleteImage"));

				clickYesButtonIfVisible();

				this.waitForElementNotVisible(By.id("gwt-debug-CustomDialog-yesButton"), Settings.WAIT_SECONDS);

			}

		}
		return this;
	}

	/**
	 * Click the red-white minus icon to delete the given report
	 * 
	 * @param report
	 *            the file name of the report
	 * @return {@link ReportPage}
	 */
	public ReportPage deleteReport(String report) {

		clickElement(By.xpath("//td[.='" + report + "']/following-sibling::td[1]/img[@title='Delete']"));

		clickYesButtonIfVisible();

		return this;
	}

	/**
	 * After click the add report icon, on the pop-up Generate Consolidated
	 * Report page, choose accounts to add into the report
	 * 
	 * @param account
	 * 
	 * @return {@link ReportPage}
	 */
	public ReportPage editAddAccounts(String... accounts) {

		for (String account : accounts) {
			selectFromDropdown(By.id("gwt-debug-PairedListBoxSelector-sourceList"), account);
		}
		clickElement(By.id("gwt-debug-PairedListBoxSelector-addImg"));

		return this;
	}

	public ReportPage editAddAllAccounts() throws InterruptedException {
		this.waitForElementVisible(By.id("gwt-debug-PairedListBoxSelector-sourceList"), Settings.WAIT_SECONDS);
		WebElement elem = webDriver.findElement(By.id("gwt-debug-PairedListBoxSelector-sourceList"));
		Select select = new Select(elem);

		List<WebElement> elemList = select.getOptions();

		for (WebElement option : elemList) {
			editAddAccounts(option.getText());
		}
		return this;
	}

	/**
	 * After click the add report icon, on the pop-up Generate Consolidated
	 * Report page, choose a date rage
	 * 
	 * @param range
	 *            the date range
	 * @return {@link ReportPage}
	 */
	public ReportPage editDateRange(String range) {

		clickElement(By.xpath("//label[.='" + range + "']"));

		return this;
	}

	/**
	 * After click the add report icon and select the Custom date range, on the
	 * pop-up Generate Consolidated Report page, input the start date and the
	 * end date
	 * 
	 * @param startDate
	 * @param endDate
	 * 
	 * @return {@link ReportPage}
	 */
	public ReportPage editDate(String startDate, String endDate) {

		try {
			sendKeysToElement(By.id("gwt-debug-DateRangePanel-startDateTextBox"), startDate);
		} catch (Exception e) {
			// TODO: handle exception
		}

		try {
			sendKeysToElement(By.id("gwt-debug-DateRangePanel-endDateTextBox"), endDate);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return this;
	}

	/**
	 * After click the add report icon, on the pop-up Generate Consolidated
	 * Report page, edit the report type
	 * 
	 * @param type
	 *            the report type
	 * 
	 * @return {@link ReportPage}
	 */
	public ReportPage editReportType(String type) {

		selectFromDropdown(By.id("gwt-debug-GenerateConsolidatedUserReport-reportDesignFile"), type);

		return this;
	}

	/**
	 * After click the add report icon, on the pop-up Generate Consolidated
	 * Report page, edit the report language
	 * 
	 * @param language
	 *            the report language
	 * @return {@link ReportPage}
	 */
	public ReportPage editReportLanguage(String language) {

		selectFromDropdown(By.id("gwt-debug-GenerateConsolidatedUserReport-languageListBox"), language);

		return this;
	}

	/**
	 * After click the add report icon, on the pop-up Generate Consolidated
	 * Report page, click the GENERATE button to generate a report
	 * 
	 * @return {@link ReportPage}
	 */
	public ReportPage clickGenerateReportButton() {

		clickElement(By.id("gwt-debug-GenerateConsolidatedUserReport-submitBtn"));

		return this;
	}

	/**
	 * Click the Process icon on the top right of the page, and confirm the
	 * report is generated when the report status is Completed
	 * 
	 * @param completed
	 *            the process status
	 * @return {@link ReportPage}
	 * @throws InterruptedException
	 * 
	 */
	public ReportPage confirmReportGenerated(String completed) throws InterruptedException {

		this.goToProcess();

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(120, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		wait.until(ExpectedConditions
				.textToBePresentInElementLocated(By.xpath(".//*[@class='processImageMenuPopupSubtitle']"), completed));

		wait(Settings.WAIT_SECONDS);

		return this;
	}

}
