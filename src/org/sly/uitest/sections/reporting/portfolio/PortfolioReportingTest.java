package org.sly.uitest.sections.reporting.portfolio;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.abstractpage.MenuBarPage;
import org.sly.uitest.pageobjects.clientsandaccounts.AccountOverviewPage;
import org.sly.uitest.pageobjects.clientsandaccounts.DetailEditPage;
import org.sly.uitest.pageobjects.clientsandaccounts.DetailPage;
import org.sly.uitest.pageobjects.clientsandaccounts.ReportPage;
import org.sly.uitest.pageobjects.commissioning.CashflowOverviewPage;
import org.sly.uitest.settings.Settings;

/**
 * @author Lynne Huang
 * @date : 5 Oct, 2015
 * @company Prive Financial
 */

public class PortfolioReportingTest extends AbstractTest {

	@Test
	public void testInvestorAccountReportGeneration() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		String oldFormat = "d-MMM-yyyy";
		String newFormat = "yMMdd";

		String account = "AVIVA Global Savings A/C - 9155";

		DetailEditPage edit = main.loginAs(Settings.CRM_USERNAME, Settings.CRM_PASSWORD).goToAccountOverviewPage()
				.simpleSearchByString("6261").goToAccountHoldingsPageByName(account).goToDetailsPage()
				.goToAddPageByField("Reports").editInvestorReportDateRange("1 Year");

		String startDate = getTextByLocator(By.id("gwt-debug-DateRangePanel-startDateTextBox"));

		String endDate = getTextByLocator(By.id("gwt-debug-DateRangePanel-endDateTextBox"));

		String newStartDate = changeDateFormat(oldFormat, newFormat, startDate);
		String newEndDate = changeDateFormat(oldFormat, newFormat, endDate);

		String report = newStartDate + "-" + newEndDate + ".pdf";

		log(report);

		// generate the report
		DetailPage detail = edit.clickSaveButtonByLocator(By.id("gwt-debug-GenerateInvestorReport-submitBtn"));

		clickOkButtonIfVisible();

		detail.confirmAccountReportGenerated("Report - Completed");

		String newReport = getTextByLocator(By.id("gwt-debug-ReportsSectionPresenter-reportUrl"));

		assertTrue(newReport.contains(report));

		// delete the report
		detail.deleteAccountReport(newReport);

	}

	@Test
	public void testConsolidatedReportGeneration() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		String oldFormat = "d-MMM-yyyy";
		String newFormat = "yMMdd";

		String client = "6261, Client";
		String account = "AVIVA Global Savings A/C - 9155";

		ReportPage reportPage = main.loginAs(Settings.CRM_USERNAME, Settings.CRM_PASSWORD).goToAccountOverviewPage()
				.simpleSearchByString("6261").goToClientDetailPageByName(client).goToReportPage().addReport()
				.editDateRange("Quarter").editAddAccounts(account);

		String startDate = getTextByLocator(By.id("gwt-debug-DateRangePanel-startDateTextBox"));

		String endDate = getTextByLocator(By.id("gwt-debug-DateRangePanel-endDateTextBox"));

		String newStartDate = changeDateFormat(oldFormat, newFormat, startDate);
		String newEndDate = changeDateFormat(oldFormat, newFormat, endDate);

		String report = "Client6261.CONSOLIDATED." + newStartDate + "-" + newEndDate + ".pdf";

		log(report);

		reportPage.clickGenerateReportButton();

		clickOkButtonIfVisible();

		reportPage.confirmReportGenerated("Report - Completed");

		String newReport = getTextByLocator(By.id("gwt-debug-ReportsSectionPresenter-reportUrl"));

		assertTrue(newReport.contains(report));

		// delete the report
		reportPage.deleteReport(newReport);

	}

	@Test
	public void testCashflowScreenSorting() throws Exception {
		LoginPage main = new LoginPage(webDriver);
		CashflowOverviewPage cashflowOverview = main.loginAs(Settings.CRM_USERNAME, Settings.CRM_PASSWORD)
				.goToCashflowOverviewPage();

		cashflowOverview.goToAdvancedSearchPage().searchByProvider("Generali").searchByStartDate("01-Jun-2014")
				.searchByEndDate("01-Jul-2014").clickSearchButton();

		waitForWaitingScreenNotVisible();

		checkSortingForCashflowScreen("Value Date", 1, cashflowOverview);
		checkSortingForCashflowScreen("Creation Date", 2, cashflowOverview);
		checkSortingForCashflowScreen("Type", 3, cashflowOverview);
		checkSortingForCashflowScreen("Status", 4, cashflowOverview);
		checkSortingForCashflowScreen("Account Name", 5, cashflowOverview);
	}

	public void checkSortingForCashflowScreen(String columnName, Integer columnNumber, CashflowOverviewPage coPage) {

		this.waitForWaitingScreenNotVisible();
		coPage.sortColumn(columnName);
		waitForElementVisible(
				By.xpath(".//div[.='" + columnName
						+ "']/following-sibling::div/button[@class='fa fa-button fa fa-caret-up size-12 darkgrey']"),
				Settings.WAIT_SECONDS * 2);
		// make sure that the result is in descending order.
		assertTrue(isElementVisible(By.xpath(".//div[.='" + columnName
				+ "']/following-sibling::div/button[@class='fa fa-button fa fa-caret-up size-12 darkgrey']")));

		// get the first result under different columns. The column number
		// reflect which column is compared in the test.
		String firstSorting = getTextByLocator(By
				.xpath(".//table[@id='gwt-debug-SortableFlexTableAsync-table']//td[@id='gwt-debug-SortableFlexTableAsync-table-1-"
						+ columnNumber + "']"));

		coPage.sortColumn(columnName);

		waitForElementVisible(
				By.xpath(".//div[.='" + columnName
						+ "']/following-sibling::div/button[@class='fa fa-button fa fa-caret-down size-12 darkgrey']"),
				Settings.WAIT_SECONDS * 2);

		// make sure that the result is in ascending order.
		assertTrue(isElementVisible(By.xpath(".//div[.='" + columnName
				+ "']/following-sibling::div/button[@class='fa fa-button fa fa-caret-down size-12 darkgrey']")));

		// get the first result under different columns. The column number
		// reflect which column is compared in the test.
		String secondSorting = getTextByLocator(By
				.xpath(".//table[@id='gwt-debug-SortableFlexTableAsync-table']//td[@id='gwt-debug-SortableFlexTableAsync-table-1-"
						+ columnNumber + "']"));

		// assert the result in descending order is different to that in
		// ascending order if any of them is not null
		if (firstSorting != null || secondSorting != null)
			assertThat(firstSorting, not(equalTo(secondSorting)));

	}

	@Test
	public void testDownloadUrl() throws InterruptedException, ParseException {

		LoginPage main = new LoginPage(webDriver);
		String client = "Miroslav Bernarde";
		String account = "Friends Provident Bond - 9688215";
		String currentYear = this.getCurrentTimeInFormat("YYYY");
		ReportPage report = main.loginAs(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD).goToAccountOverviewPage()
				.simpleSearchByString(client).goToClientDetailPageByName("Bernarde, Miroslav").goToReportPage()
				.addReport();

		waitForElementVisible(By.id("gwt-debug-PairedListBoxSelector-sourceList"), 10);

		// get all account names
		Select select = new Select(webDriver.findElement(By.id("gwt-debug-PairedListBoxSelector-sourceList")));

		List<WebElement> list = select.getOptions();

		report.editAddAllAccounts();

		report.clickGenerateReportButton();

		clickOkButtonIfVisible();

		report.goToProcess();

		By reportUrl = By.xpath(".//table[contains(@title,'" + currentYear + "')]//div[.='" + client + " - " + account
				+ "']//ancestor::tr//following-sibling::tr[2]//a[@name='reportUrl' and .='Report']");

		waitForElementVisible(reportUrl, 300);

		clickElement(reportUrl);
		wait(30);
		checkDownloadedFile("MiroslavBernarde");
		scrollToTop();

	}

	@Test
	public void testFieldInClientListReport() throws InterruptedException, InvalidFormatException, IOException {
		String accountName = "94042881";
		LoginPage main = new LoginPage(webDriver);

		AccountOverviewPage account = main.loginAs(Settings.CRM_USERNAME, Settings.CRM_PASSWORD)
				.goToAccountOverviewPage().simpleSearchByString("94042881");

		MenuBarPage menu = account.exportSearchResult("Account List", accountName).goToProcess();

		menu.downloadExportFile(accountName);
		// checkDownloadedExcelFile(accountName, "Maturity Date",
		// "Commencement Date", "Final Payment Date", accountName, "Aug",
		// "18", "2014", "2019");
	}

}
