package org.sly.uitest.sections.documentsdownload;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.AWTException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.abstractpage.MenuBarPage;
import org.sly.uitest.pageobjects.admin.AdminEditPage;
import org.sly.uitest.pageobjects.clientsandaccounts.AccountsPage;
import org.sly.uitest.pageobjects.clientsandaccounts.DetailPage;
import org.sly.uitest.pageobjects.clientsandaccounts.ReportPage;
import org.sly.uitest.pageobjects.crm.DocumentsPage;
import org.sly.uitest.pageobjects.crm.TasksPage;
import org.sly.uitest.pageobjects.investments.InvestmentsPage;
import org.sly.uitest.settings.Settings;

/**
 * This test is for documents downloading. Please find the test plan in the
 * following URL: https://docs.google.com/spreadsheets/d/1
 * ovDMSiR7FXdr6jWDqWqrdO8tSL7Owhcqx9G1K2K-FDQ/edit#gid=0
 * 
 * @edited by Benny Leung
 * @date Sept 2,2016
 * @company Prive Financial
 */
public class DocumentsDownloadTest extends AbstractTest {

	// test case #1
	@Test
	public void testIQFOXXFactsheet() throws InterruptedException {

		setScreenshotSuffix(DocumentsDownloadTest.class.getSimpleName());

		String url = Settings.BASE_URL + "#investmentDetails;strategyKey=9297;token=iQF";

		webDriver.get(url);
		handleAlert();

		waitForElementVisible(By.id("gwt-debug-ManagerDisplayWidgetView-strategyName"), 30);

		assertTrue(pageContainsStr("iQ-FOXX Asia Stocks Smart Beta TR USD Index"));

		// assertTrue(this
		// .checkElementExist(By
		// .xpath("//a[.='Index Methodology_iQ-FOXX Asia Stocks Smart Beta TR
		// USD Index']")));
		clickOkButtonIfVisible();
		waitForElementVisible((By.xpath("//a[.='Fund Fact Sheet ']")), 30);
		assertTrue(this.checkElementExist(By.xpath("//a[.='Fund Fact Sheet ']")));

	}

	@Test
	public void testFailedDownloadFactsheet() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		String testMutualFund = "Test Mutual Fund" + this.getRandName();

		MenuBarPage mbPage = main.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD);

		AdminEditPage access = mbPage.goToAdminEditPage().editAdminThisField("Access Token");
		try {
			selectFromDropdown(By.id("gwt-debug-AdminSelect-listBox"), "TestMutualFund (Key: 11)");
		} catch (Exception e) {
			access.clickNewButtonToCreateNewToken().editAccessToken(testMutualFund, "White List", "All Mutual Funds")
					.clickSubmitButton();
		}
		clickOkButtonIfVisible();
		checkLogout();
		// wait(Settings.WAIT_SECONDS);
		handleAlert();

		// test 9
		String url = Settings.BASE_URL + "DownloadFundFactsheet?isin=DE0008962&type=3&token="
				+ testMutualFund.replace(" ", "");

		webDriver.get(url);
		handleAlert();
		assertTrue(pageContainsStr("Error"));

		// test 9
		url = "https://www.privemanagers.com/DownloadFundFactsheet?isin=DE0008962&type=3&token=qPTdfweRT";

		webDriver.get(url);

		assertTrue(pageContainsStr("Error"));

		// test 10
		url = Settings.BASE_URL + "DownloadFundFactsheet?isin=DE0008962&type=3&token=NoSuchToken";

		webDriver.get(url);

		assertTrue(pageContainsStr("Error"));

		// test 10
		url = "https://www.privemanagers.com/DownloadFundFactsheet?isin=DE0008962&type=3&token=NoSuchToken";

		webDriver.get(url);

		assertTrue(pageContainsStr("Error"));

	}

	// test case #2
	@Test
	public void testInvestorConsolidatedReport()
			throws InterruptedException, AWTException, MalformedURLException, IOException {
		LoginPage main = new LoginPage(webDriver);
		AccountsPage accounts = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage()
				.simpleSearchByString("Investor, Selenium").goToClientDetailPageByName("Investor, Selenium")
				.goToAccountsTab();
		List<String> accountList = accounts.showMoreAccounts().getAccountsWithValidValue();

		ReportPage report = accounts.goToReportPage();
		report.deleteAllReports();

		report.addReport();

		for (String account : accountList) {
			report.editAddAccounts(account);
		}

		report.editDateRange("1 Year").editDate("31-July-2014", "31-July-2015").clickGenerateReportButton();

		clickOkButtonIfVisible();

		report.confirmReportGenerated("Report - Completed");

		assertTrue(pageContainsStr("SeleniumInvestor.CONSOLIDATED.20140731-20150731.pdf"));

		checkUrlValid(getAttributeStringByLocator(By.xpath(".//a[@id='gwt-debug-ReportsSectionPresenter-reportUrl']"),
				"href"));

		waitForElementVisible(By.xpath(".//*[.='SeleniumInvestor.CONSOLIDATED.20140731-20150731.pdf']"), 60);

		clickElement(By.id("gwt-debug-ReportsSectionPresenter-reportUrl"));

		// clickEnterButtonForDownload();
		wait(10);
		checkDownloadedFile("SeleniumInvestor");

	}

	// test case #3
	@Test
	public void testInvestorAccountReport()
			throws InterruptedException, MalformedURLException, IOException, AWTException {

		String accountName = "Generali Vision - TWRR";
		LoginPage main = new LoginPage(webDriver);
		DetailPage detailPage = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage()
				.simpleSearchByString(accountName).goToAccountHoldingsPageByName(accountName).goToDetailsPage();

		Integer size = getSizeOfElements(By.id("gwt-debug-ReportsSectionPresenter-reportUrl"));
		// if there is no existing report, generate one.
		if (size.equals(0)) {
			detailPage.goToAddPageByField("Reports");
			clickElement(By.id("gwt-debug-GenerateInvestorReport-submitBtn"));
			detailPage.goToProcess();
			waitForTextVisible(By.xpath(".//*[@class='processImageMenuPopupSubtitle']"), "Report - Completed");
		}

		String fileName = getTextByLocator(By.id("gwt-debug-ReportsSectionPresenter-reportUrl"));

		checkUrlValid(getAttributeStringByLocator(By.xpath(".//a[@id='gwt-debug-ReportsSectionPresenter-reportUrl']"),
				"href"));

		clickElement(By.id("gwt-debug-ReportsSectionPresenter-reportUrl"));

		// clickEnterButtonForDownload();
		wait(10);
		log(fileName);
		// check the name of the report
		assertTrue(fileName.contains("SeleniumInvestor") && fileName.contains("ACC32181") && fileName.contains(".pdf"));
		checkDownloadedFile(fileName);
	}

	// Test case 4
	@Test
	public void testAuthenticatedUserDownloadDocument() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		InvestmentsPage iPage = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToInvestmentsPage();

		String investment = "BANK OF AMERICA CORP";

		iPage.simpleSearchByName(investment).clickInvestmentByName(investment).clickDetailButton();

		waitForElementVisible(
				(By.xpath("//table[@id='gwt-debug-ManagerDisplay-docPanel']//a[contains(text(),'Fact')]")), 30);

		clickElement(By.xpath("//table[@id='gwt-debug-ManagerDisplay-docPanel']//a[contains(text(),'Fact')]"));

		for (int i = 0; i < 5; i++) {
			if (webDriver.getWindowHandles().size() < 2) {
				wait(Settings.WAIT_SECONDS);
			}
		}
		assertFalse(pageContainsStr("Error happened while running the report"));
		for (String winHandle : webDriver.getWindowHandles()) {
			webDriver.switchTo().window(winHandle);
		}

		assertTrue(isElementVisible(By.xpath(".//embed[@type='application/pdf']")));
		/*
		 * use the code below to enable checking downloaded files
		 */
		// String fileName = "CA";
		// checkDownloadedFile(fileName);
	}

	// test case #5
	@Test
	public void testDownloadDocumentBinderFile() throws InterruptedException, AWTException {
		String binderName = "testBinder" + getRandName();
		LoginPage main = new LoginPage(webDriver);

		DocumentsPage document = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToDocumentsPage()
				.clickCreateBinderButton().editDocumentBinderName(binderName).addAttachmentForDoucmentBinder()
				.clickSaveButton();

		this.waitForWaitingScreenNotVisible();

		document.simpleSearchInDocumentPage(binderName).editBinderByName(binderName);

		waitForElementVisible(By.id("gwt-debug-MultiUploaderWidget-anchorDoc"), 5);

		// String fileName = getTextByLocator(By
		// .id("gwt-debug-MultiUploaderWidget-anchorDoc"));

		clickElement(By.id("gwt-debug-MultiUploaderWidget-anchorDoc"));

		wait(5);

		checkDownloadedFile("abc (1)");

		document.clickCancelButton().deleteBinderByName(binderName);
		waitForElementVisible(By.xpath(".//*[contains(text(),' No Document Binder Found.')]"), 30);
		assertTrue(isElementVisible(By.xpath(".//*[contains(text(),' No Document Binder Found.')]")));
	}

	// test case #6
	@Test
	public void testDownloadTaskDocument() throws InterruptedException, AWTException {

		String taskname = "Task" + getRandName();
		String bindername = "Binder" + getRandName();
		LoginPage main = new LoginPage(webDriver);

		TasksPage task = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToTasksPage();

		task.clickCreateTaskButton().editTaskName(taskname).clickAddBindersButton().editBinderName(bindername)
				.clickAddAttachmentButton().uploadFileForAttachment().clickSaveButton();

		this.waitForWaitingScreenNotVisible();

		task.simpleSearchByString(taskname).editTaskByName(taskname);

		waitForElementVisible(By.id("gwt-debug-MultiUploaderWidget-anchorDoc"), 15);

		// String fileName = getTextByLocator(By
		// .id("gwt-debug-MultiUploaderWidget-anchorDoc"));

		clickElement(By.id("gwt-debug-MultiUploaderWidget-anchorDoc"));

		// wait(5);

		checkDownloadedFile("abc (1)");
	}

	// test case #7
	@Test
	public void testInvestorDownloadDocument()
			throws InterruptedException, MalformedURLException, IOException, AWTException {

		String accountName = "Generali Vision - TWRR";
		LoginPage main = new LoginPage(webDriver);

		main.loginAs(Settings.INVESTOR_USERNAME, Settings.INVESTOR_PASSWORD).goToAccountOverviewPage()
				.simpleSearchByString(accountName).goToAccountHoldingsPageByName(accountName).goToDetailsPage();

		String fileName = getTextByLocator(By.id("gwt-debug-ReportsSectionPresenter-reportUrl"));

		checkUrlValid(getAttributeStringByLocator(By.xpath(".//a[@id='gwt-debug-ReportsSectionPresenter-reportUrl']"),
				"href"));

		clickElement(By.id("gwt-debug-ReportsSectionPresenter-reportUrl"));

		// clickEnterButtonForDownload();
		wait(10);

		log(fileName);
		// check the name of the report
		assertTrue(fileName.contains("SeleniumInvestor") && fileName.contains("ACC32181") && fileName.contains(".pdf"));
		checkDownloadedFile(fileName);
	}

	// test case #8
	@Test
	public void testDownloadFactsheet() throws InterruptedException, MalformedURLException, IOException {
		LoginPage main = new LoginPage(webDriver);
		Boolean accessToken = false;
		String testMutualFund = "TestMutualFund" + getRandName();
		String validUrl = "";
		log(testMutualFund);
		// wait(2);
		MenuBarPage mbPage = main.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD);
		// wait(5);

		AdminEditPage access = mbPage.goToAdminEditPage().editAdminThisField("Access Token");

		waitForElementVisible(By.id("gwt-debug-AdminSelect-listBox"), 10);

		Select select = new Select(webDriver.findElement(By.id("gwt-debug-AdminSelect-listBox")));

		for (WebElement elem : select.getOptions()) {
			if (elem.getText().equals("TestMutualFund (Key: 505)")) {
				accessToken = true;
			}
		}

		if (accessToken) {
			selectFromDropdown(By.id("gwt-debug-AdminSelect-listBox"), "TestMutualFund (Key: 136)");
			validUrl = "http://192.168.1.107:8080/SlyAWS/DownloadFundFactsheet?isin=AT0000765128&type=1&token=TestMutualFund1463936291";
		} else {
			access.clickNewButtonToCreateNewToken().editAccessToken(testMutualFund, "White List", "All Mutual Funds")
					.activateAccessToken().clickSubmitButton();

			clickOkButtonIfVisible();
			validUrl = "http://192.168.1.107:8080/SlyAWS/DownloadFundFactsheet?isin=AT0000765128&type=1&token="
					+ testMutualFund;
		}

		checkLogout();
		// wait(Settings.WAIT_SECONDS);
		handleAlert();
		waitForElementVisible(By.id("gwt-debug-UserLoginView-loginButton"), 10);

		log("validUrl: " + validUrl);
		// checkUrlValid(validUrl);
		webDriver.get(validUrl);
		assertTrue(!pageContainsStr("Error"));
	}
}
