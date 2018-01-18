package org.sly.uitest.sections.bulkoperation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.abstractpage.MenuBarPage;
import org.sly.uitest.pageobjects.admin.AdminReportingPage;
import org.sly.uitest.pageobjects.clientsandaccounts.AccountOverviewPage;
import org.sly.uitest.pageobjects.clientsandaccounts.BulkOperationPage;
import org.sly.uitest.pageobjects.clientsandaccounts.DetailEditPage;
import org.sly.uitest.pageobjects.clientsandaccounts.DetailPage;
import org.sly.uitest.settings.Settings;

public class BulkoperationsTest extends AbstractTest {

	@Test
	public void testChangeAccountStatusForAccounts() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);
		String account = "Rebalance Manual Account";
		main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage().clickBulkOperationsButton()
				.selectType("Accounts").selectAction("Change the account status").changeAccountStatus("Opened")
				.clickConfirmButton();

		// check accounts details Generali Vision - NEW=
		AccountOverviewPage overview = new AccountOverviewPage(webDriver);

		overview.simpleSearchByString(account).goToAccountHoldingsPageByName(account).goToDetailsPage();

		assertTrue(checkElementExist(
				By.xpath("//table[@id='gwt-debug-InvestorAccountDetailsSectionView-contentTable']//td[.='Opened']")));

	}

	@Test
	public void testChangeFrequencyForAccounts() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);
		String account = "Rebalance Manual Account";
		main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage().clickBulkOperationsButton()
				.selectType("Accounts").selectAction("Change the review frequency").editReviewFrequency("Annually")
				.clickConfirmButton();

		AccountOverviewPage overview = new AccountOverviewPage(webDriver);

		overview.simpleSearchByString(account).goToAccountHoldingsPageByName(account).goToDetailsPage();

		assertTrue(checkElementExist(
				By.xpath("//table[@id='gwt-debug-InvestorAccountDetailsSectionView-contentTable']//td[.='Annually']")));

		overview.goToAccountOverviewPage().simpleSearchByString(account).clickBulkOperationsButton()
				.selectType("Accounts").selectAction("Change the review frequency").editReviewFrequency("Monthly")
				.clickConfirmButton();

	}

	@Test
	public void testChangeBenchmark() throws InterruptedException {

		String account = "Portfolio Bond (Test Reconciliation)";
		LoginPage main = new LoginPage(webDriver);

		main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage().clickBulkOperationsButton()
				.selectType("Accounts").selectAction("Clear or set a new benchmark for the account")
				.editBenchmark("AMEX Biotechnology PR USD").clickConfirmButton();

		// check accounts details Generali Vision - NEW=
		AccountOverviewPage overview = new AccountOverviewPage(webDriver);

		overview.simpleSearchByString(account).goToAccountHoldingsPageByName(account).goToDetailsPage();
		clickOkButtonIfVisible();
		assertTrue(checkElementExist(By.xpath(
				"//table[@id='gwt-debug-InvestorAccountDetailsSectionView-contentTable']//td[.='AMEX Biotechnology PR USD']")));

		overview.goToAccountOverviewPage().simpleSearchByString("Generali").clickBulkOperationsButton()
				.selectType("Accounts").selectAction("Clear or set a new benchmark for the account")
				.editBenchmark("Hang Seng HSI PR HKD").clickConfirmButton();

	}

	@Test
	public void testChangeBaseCurrency() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage().clickBulkOperationsButton()
				.selectType("Clients").selectAction("Change the base currency for the client reports")
				.editBaseCurrency("JPY").clickConfirmButton();

		AccountOverviewPage overview = new AccountOverviewPage(webDriver);

		overview.simpleSearchByString("Doe").goToClientDetailPageByName("Doe, John");

		waitForElementVisible(By.xpath("//table[@id='gwt-debug-UserDetailsSectionView-contentTable']//td[.='JPY']"),
				30);

		assertTrue(checkElementExist(
				By.xpath("//table[@id='gwt-debug-UserDetailsSectionView-contentTable']//td[.='JPY']")));

		overview.goToAccountOverviewPage().simpleSearchByString("Generali").clickBulkOperationsButton()
				.selectType("Clients").selectAction("Change the base currency for the client reports")
				.editBaseCurrency("USD").clickConfirmButton();

		overview.simpleSearchByString("Doe").goToClientDetailPageByName("Doe, John");

		waitForElementVisible(By.xpath("//table[@id='gwt-debug-UserDetailsSectionView-contentTable']//td[.='USD']"),
				30);

		assertTrue(checkElementExist(
				By.xpath("//table[@id='gwt-debug-UserDetailsSectionView-contentTable']//td[.='USD']")));
	}

	@Test
	public void testChangeFrequencyForUser() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage().clickBulkOperationsButton()
				.selectType("Clients").selectAction("Change the review frequency").editReviewFrequency("Annually")
				.clickConfirmButton();

		AccountOverviewPage overview = new AccountOverviewPage(webDriver);

		overview.simpleSearchByString("Doe").goToClientDetailPageByName("Doe, John");

		waitForElementVisible(
				By.xpath("//table[@id='gwt-debug-UserDetailsSectionView-contentTable']//td[.='Annually']"), 30);

		assertTrue(checkElementExist(
				By.xpath("//table[@id='gwt-debug-UserDetailsSectionView-contentTable']//td[.='Annually']")));

		overview.goToAccountOverviewPage().simpleSearchByString("Generali Vision - NEW").clickBulkOperationsButton()
				.selectType("Clients").selectAction("Change the review frequency").editReviewFrequency("Monthly")
				.clickConfirmButton();

	}

	@Test
	public void testChangeAdmin() throws Exception {

		LoginPage main = new LoginPage(webDriver);
		String mainAdmin = "Dahliana";

		MenuBarPage mbPage = main.loginAs(Settings.CRM_USERNAME, Settings.CRM_PASSWORD);

		AccountOverviewPage accountOverviewPage = mbPage.goToAccountOverviewPage();

		DetailEditPage admin = ((AccountOverviewPage) accountOverviewPage.goToAdvancedSearchPage()
				.searchByAccountName("FUTURA").searchByClientName("5921").clickSearchButton())
						.goToClientDetailPageByName("5921, Client").goToEditPageByField("Additional Details")
						.editRemoveMainAdminByName("all").editAddMainAdminByName(mainAdmin);
		try {
			if (getAttributeStringByLocator(By.id("gwt-debug-EditUserPhoneWidget-preferredRadioButton-1"), "class")
					.equals("fa-star-o fa fa-button size-14 yellow-star")) {
				clickElement(By.id("gwt-debug-EditUserPhoneWidget-preferredRadioButton-1"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		DetailPage detail = admin.clickSaveButton_ClientAdditionalDetail();
		try {
			clickElementAndWait3(By.id("gwt-debug-MainAdvisorChangeView-okButton"));
		} catch (org.openqa.selenium.TimeoutException e) {
			e.printStackTrace();
		}

		this.waitForElementVisible(By.xpath("//td[.='Main Admins:']/following-sibling::td[1]/table/tbody/tr/td"), 30);

		assertTrue(getTextByLocator(By.xpath("//td[.='Main Admins:']/following-sibling::td[1]/table/tbody/tr/td"))
				.contains(mainAdmin));

		((AccountOverviewPage) detail.goToAccountOverviewPage().goToAdvancedSearchPage().searchByAccountName("FUTURA")
				.clickSearchButton()).selectAllAccounts(true).clickBulkOperationsButton().selectType("Clients")
						.selectAction("Change the main admin").editMainContact("Mimi").clickConfirmButton()
						.simpleSearchByString("5921").goToClientDetailPageByName("5921, Client");

		this.getTextByLocator(By.xpath("//td[.='Main Admins:']/following-sibling::td[1]/table/tbody/tr/td"));
		assertTrue(this.getTextByLocator(By.xpath("//td[.='Main Admins:']/following-sibling::td[1]/table/tbody/tr/td"))
				.contains("Mimi"));
		log("Test bulk operation change admin: done");

	}

	@Test
	public void testChangeAdvisor() throws Exception {

		LoginPage main = new LoginPage(webDriver);
		// DetailPage report =
		// wait(Settings.WAIT_SECONDS);
		DetailEditPage advisor = ((AccountOverviewPage) main.loginAs(Settings.CRM_USERNAME, Settings.CRM_PASSWORD)
				.goToAccountOverviewPage().goToAdvancedSearchPage().searchByAccountName("FUTURA")
				.searchByClientName("5921").clickSearchButton()).goToClientDetailPageByName("5921, Client")
						.goToEditPageByField("Additional Details").editRemoveMainAdvisorByName("all")
						.editAddMainAdvisorByName("Alex De Wit");

		try {
			if ((new WebDriverWait(webDriver, 30))
					.until(ExpectedConditions
							.presenceOfElementLocated(By.id("gwt-debug-EditUserPhoneWidget-preferredRadioButton-1")))
					.getAttribute("class").equals("fa-star-o fa fa-button size-14 yellow-star")) {
				clickElement(By.id("gwt-debug-EditUserPhoneWidget-preferredRadioButton-1"));
			}
		} catch (Exception e) {

		}

		DetailPage detail = advisor.clickSaveButton_ClientAdditionalDetail();

		try {
			clickElementAndWait3(By.id("gwt-debug-MainAdvisorChangeView-okButton"));
		} catch (org.openqa.selenium.TimeoutException e) {
			// TODO: handle exception
		}

		assertTrue(getTextByLocator(By.xpath("//td[.='Main Advisors:']/following-sibling::td[1]//a"))
				.contains("Alex De Wit"));

		((AccountOverviewPage) detail.goToAccountOverviewPage().goToAdvancedSearchPage().searchByAccountName("FUTURA")
				.clickSearchButton()).selectAllAccounts(true).clickBulkOperationsButton().selectType("Clients")
						.selectAction("Change the main advisor").editMainContact("Ying Zhou").clickConfirmButton()
						.simpleSearchByString("5921").goToClientDetailPageByName("5921, Client");
		this.waitForElementVisible(By.xpath("//td[.='Main Advisors:']/following-sibling::td[1]/table/tbody/tr/td"), 30);
		assertTrue(
				this.getTextByLocator(By.xpath("//td[.='Main Advisors:']/following-sibling::td[1]/table/tbody/tr/td"))
						.contains("Ying Zhou"));

		log("Test bulk operation change advisor: done");
	}

	@Test
	public void testReports() throws Exception {

		LoginPage main = new LoginPage(webDriver);
		String investment = "LMIM Currency Protected Australian Income - 32211";
		AccountOverviewPage report = main.loginAs(Settings.CRM_USERNAME, Settings.CRM_PASSWORD)
				.goToAccountOverviewPage().checkIncludeInactiveAccounts(true).goToAdvancedSearchPage()
				.searchByProductFundManager("LM").clickSearchButton();

		this.waitForElementVisible(By.xpath(".//*[contains(text(),'" + investment + "')]"), Settings.WAIT_SECONDS);

		report.goToAccountHoldingsPageByName(investment).goToDetailsPage().goToAddPageByField("Reports")
				.editInvestorReportDate("7-Feb-2015", "17-Jul-2015")
				.clickSaveButtonByLocator(By.id("gwt-debug-GenerateInvestorReport-submitBtn"));
		this.waitForElementVisible(By.id("gwt-debug-CustomDialog-message"), Settings.WAIT_SECONDS);
		// assertTrue(pageContainsStr("The consolidated client report is being
		// generated in the back end."));
		assertTrue(isElementVisible(By.id("gwt-debug-CustomDialog-message")));
		clickOkButtonIfVisible();

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(60, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);
		wait.until(ExpectedConditions.textToBePresentInElementLocated(
				By.id("gwt-debug-ReportsSectionPresenter-reportUrl"), "Client25511.ACC32211.20150207-20150717.pdf"));

		assertTrue(pageContainsStr("Client25511.ACC32211.20150207-20150717.pdf"));
		// wait(15);
		DetailPage detailPage = new DetailPage(webDriver);
		detailPage.deleteAccountReport("Client25511.ACC32211.20150207-20150717.pdf");

	}

	@Test
	public void testSetCalendarDate() throws Exception {

		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");

		Calendar c = Calendar.getInstance();

		c.add(Calendar.DATE, 5);

		String startDate = dateFormat.format(c.getTime());

		System.out.println(dateFormat.format(c.getTime()));

		LoginPage main = new LoginPage(webDriver);
		MenuBarPage mbPage = main.loginAs(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD);
		// wait(5);

		AccountOverviewPage accountOverviewPage = mbPage.goToAccountOverviewPageMaterialView();

		BulkOperationPage bulk = ((AccountOverviewPage) accountOverviewPage.goToAdvancedSearchPage()
				.searchByProductFundManager("ABERDEEN").clickSearchButton()).selectAllAccounts(true)
						.clickBulkOperationsButton().selectType("Accounts")
						.selectAction("Create calendar review entries").editStartDate(startDate);
		this.waitForElementVisible(
				By.xpath(
						".//*[@id='gwt-debug-BulkOperationSummaryPopupView-summaryPanel']/table/tbody/tr[3]/td/table/tbody/tr/td"),
				30);
		assertEquals(
				this.getTextByLocator(By
						.xpath(".//*[@id='gwt-debug-BulkOperationSummaryPopupView-summaryPanel']/table/tbody/tr[3]/td/table/tbody/tr/td")),
				"Start date: " + startDate);

		bulk.clickConfirmButton();

	}

	@Test
	public void testBulkReportGenerationWorksForSysAdmin() throws Exception {

		LoginPage main = new LoginPage(webDriver);
		MenuBarPage mbPage = main.loginAs(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD);
		// wait(5);

		mbPage.goToAccountOverviewPage().simpleSearchByString("Ageas Columbus - 00200754")
				.goToAccountHoldingsPageByName("Ageas Columbus - 00200754").goToDetailsPage();
		int size = getSizeOfElements(By.xpath(".//*[@id='gwt-debug-ReportsSectionPresenter-deleteImage']"));
		for (int i = 0; i < size; i++) {
			clickElement(By.id("gwt-debug-ReportsSectionPresenter-deleteImage"));
			clickYesButtonIfVisible();
		}
		this.checkLogout();
		// wait(2);
		handleAlert();

		LoginPage main1 = new LoginPage(webDriver);
		MenuBarPage menuPage = main1.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD);

		AdminReportingPage report1 = menuPage.goToAdminReportingPage().selectReportType("Investor Account PDF Report")
				.selectQualterlyReport("2015", "Q1").inputAccountKeys("39329").generatePdfReports();

		assertTrue(pageContainsStr("Successfully generated 0 report(s)"));
		clickElement(By.id("gwt-debug-CustomDialog-okButton"));
		log("The account disabled generating report: checked");

		// create the first one - 2015 Q1
		AdminReportingPage report2 = report1.selectReportType("Investor Account PDF Report")
				.selectQualterlyReport("2015", "Q1").inputAccountKeys("17136").generatePdfReports();
		assertTrue(pageContainsStr("Successfully generated 1 report(s)"));
		clickOkButtonIfVisible();

		// create the second one - 2014 Q2
		report2.selectReportType("Investor Account PDF Report").selectQualterlyReport("2014", "Q2")
				.inputAccountKeys("17136").generatePdfReports();
		assertTrue(pageContainsStr("Successfully generated 1 report(s)"));
		clickOkButtonIfVisible();
		this.waitForWaitingScreenNotVisible();
		this.checkLogout();
		// wait(Settings.WAIT_SECONDS);
		handleAlert();

		// check start
		handleAlert();
		LoginPage main2 = new LoginPage(webDriver);
		MenuBarPage menuBarPage = main2.loginAs(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD);
		// wait(5);

		menuBarPage.goToAccountOverviewPage().simpleSearchByString("Friends Provident Bond - 9688215")
				.goToAccountHoldingsPageByName("Friends Provident Bond - 9688215").goToDetailsPage();

		assertTrue(pageContainsStr("MiroslavBernarde.ACC17136.20140331-20140630.pdf"));
		// assertTrue(pageContainsStr("MiroslavBernarde.ACC17136.20141231-20150331.pdf"));
	}

	public void selectQualterlyReport(String year, String quater) {

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
	}

	public void inputAccountKeys(String... keys) {

		WebElement elem = waitGet(
				By.id("gwt-debug-MaintenanceReportingView-textAreaGenerateReportsInvestorAccountKeys"));

		for (String key : keys) {
			elem.sendKeys(key);
			elem.sendKeys("\n");
		}
	}

	public void generatePdfReports() {

		String keys = webDriver
				.findElement(By.id("gwt-debug-MaintenanceReportingView-textAreaGenerateReportsInvestorAccountKeys"))
				.getAttribute("value");

		// log(keys);

		if (!keys.equals("")) {
			clickElement(By.id("gwt-debug-MaintenanceReportingView-buttonBatchGenerateReport"));

			clickElement(By.id("gwt-debug-CustomDialog-okButton"));
		}

	}
}
