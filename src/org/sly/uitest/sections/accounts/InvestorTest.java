package org.sly.uitest.sections.accounts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.AWTException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.openqa.selenium.By;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.abstractpage.MenuBarPage;
import org.sly.uitest.pageobjects.clientsandaccounts.AccountOverviewPage;
import org.sly.uitest.pageobjects.clientsandaccounts.CRMPage;
import org.sly.uitest.pageobjects.clientsandaccounts.ClientOverviewPage;
import org.sly.uitest.pageobjects.clientsandaccounts.DetailPage;
import org.sly.uitest.pageobjects.clientsandaccounts.ReportPage;
import org.sly.uitest.pageobjects.planning.WealthPlanningOverviewPage;
import org.sly.uitest.settings.Settings;

/**
 * @author Lynne Huang
 * @date : 14 Sep, 2015
 * @company Prive Financial
 */
public class InvestorTest extends AbstractTest {

	@Test
	public void testInvestorTabs() throws InterruptedException, ParseException {

		LoginPage main = new LoginPage(webDriver);

		String currentTime = getCurrentTimeInFormat("d-MMM-yyyy HH:mm");

		log(currentTime);

		AccountOverviewPage investor = main.loginAs(Settings.INVESTOR_USERNAME, Settings.INVESTOR_PASSWORD)
				.goToAccountOverviewPage();

		// Test Detail Tab
		investor.goToClientDetailPageByName("Investor, Selenium");
		this.waitForElementVisible(By.xpath(".//*[.='Details']"), Settings.WAIT_SECONDS);
		assertTrue(this.elementIsActive("Details"));
		assertFalse(this.elementIsActive("Accounts"));
		assertFalse(this.elementIsActive("CRM"));
		assertFalse(this.elementIsActive("Reports"));

		assertTrue(this.infoIsCorrectForThisField("Title", "Mr."));
		assertTrue(this.infoIsCorrectForThisField("First Name", "Selenium"));
		assertTrue(this.infoIsCorrectForThisField("Last Name", "Investor"));
		assertTrue(this.infoIsCorrectForThisField("Username", "SeleniumInvestor"));
		// assertTrue(this.infoIsCorrectForThisField("Main Advisors",
		// "Selenium Test"));
		// assertTrue(this.infoIsCorrectForThisField("Country of Nationality",
		// "Bolivia"));// Bolivia
		// assertTrue(this.infoIsCorrectForThisField("Date of Birth",
		// "17-May-1991"));// 17-May-1991
		assertTrue(this.infoIsCorrectForThisField("Currency", "USD"));
		// assertTrue(this.infoIsCorrectForThisField("ID Number", "test123"));
		assertTrue(this.infoIsCorrectForThisField("Auto Email Monthly Report", "Disabled"));
		// assertTrue(this.infoIsCorrectForThisField("User Status", "Active"));
		// assertTrue(this.infoIsCorrectForThisField("Signature File",
		// "Not on file"));

		// Test Account Tab
		investor.goToAccountsTab();

		assertFalse(this.elementIsActive("Details"));
		assertTrue(this.elementIsActive("Accounts"));
		assertFalse(this.elementIsActive("CRM"));
		assertFalse(this.elementIsActive("Reports"));

		assertTrue(pageContainsStr("Selenium Test"));
		assertTrue(pageContainsStr("Test Account 2 (Don't Rebalance)"));
		assertTrue(pageContainsStr("Generali Vision - TWRR"));
		assertTrue(pageContainsStr("AXA Pulsar AccountTest"));

		assertTrue(webDriver
				.findElement(By
						.xpath(".//*[@id='gwt-debug-CurrentPortfolioSectionView-contentTable']/tbody/tr/td[1]/div/table/tbody/tr/td/canvas"))
				.isDisplayed());

		// Test CRM Tab
		investor.goToCRMPage();

		assertFalse(this.elementIsActive("Details"));
		assertFalse(this.elementIsActive("Accounts"));
		assertTrue(this.elementIsActive("CRM"));
		assertFalse(this.elementIsActive("Reports"));

		String loginTime = this.getTextByLocator(By.xpath("//td[.='Last Login Date:']/following-sibling::td[1]"))
				.replace("(UTC+8)", "");

		SimpleDateFormat format = new SimpleDateFormat("d-MMM-yyyy HH:mm");
		Date current = format.parse(currentTime);
		Date login = format.parse(loginTime);

		System.out.println(current.compareTo(login));

		assertTrue(current.compareTo(login) <= 0);

	}

	@Test
	public void testReportTab() throws Exception {

		LoginPage main1 = new LoginPage(webDriver);

		// delete all reports by the seleniumTest advisor
		main1.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage().simpleSearchByString("Selenium")
				.goToClientDetailPageByName("Investor, Selenium").goToReportPage().deleteAllReports();

		// check selenium investor
		// wait(Settings.WAIT_SECONDS);
		this.checkLogout();
		handleAlert();

		LoginPage main2 = new LoginPage(webDriver);

		main2.loginAs(Settings.INVESTOR_USERNAME, Settings.INVESTOR_PASSWORD).goToAccountOverviewPage()
				.goToClientDetailPageByName("Investor, Selenium").goToReportPage();

		assertTrue(pageContainsStr("No reports were found."));

		// check selenium test and generate a report
		// wait(Settings.WAIT_SECONDS);
		this.checkLogout();
		handleAlert();

		LoginPage main3 = new LoginPage(webDriver);

		ReportPage reportPage = main3.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage()
				.simpleSearchByString("Selenium").goToClientDetailPageByName("Investor, Selenium").goToReportPage();

		assertTrue(pageContainsStr("No reports were found."));

		reportPage.addReport().editAddAccounts("Generali Vision - TWRR").editDateRange("1 Year")
				.editDate("31-July-2014", "31-July-2015").clickGenerateReportButton();

		clickOkButtonIfVisible();

		reportPage.confirmReportGenerated("Report - Completed");
		waitForElementVisible(By.xpath(".//*[contains(text(),'SeleniumInvestor.CONSOLIDATED.20140731-20150731.pdf')]"),
				60);

		assertTrue(pageContainsStr("SeleniumInvestor.CONSOLIDATED.20140731-20150731.pdf"));

		// go back to check selenium investor
		// wait(Settings.WAIT_SECONDS);
		this.checkLogout();
		handleAlert();

		LoginPage main4 = new LoginPage(webDriver);

		main4.loginAs(Settings.INVESTOR_USERNAME, Settings.INVESTOR_PASSWORD).goToAccountOverviewPage()
				.goToClientDetailPageByName("Investor, Selenium").goToReportPage();

		assertTrue(pageContainsStr("SeleniumInvestor.CONSOLIDATED.20140731-20150731.pdf"));

	}

	private boolean elementIsActive(String tab) {

		String classElement = webDriver
				.findElement(By
						.xpath("//div[div[a[@id='gwt-debug-GeneralUserDetailViewTab-hyperlink' and .='" + tab + "']]]"))
				.getAttribute("class");

		// log(classElement);

		return classElement.contains("portfolioOverviewTabActive");
	}

	private boolean infoIsCorrectForThisField(String field, String info) {

		waitForElementVisible(By.xpath("//td[.='" + field + ":']/following-sibling::td[1]"), 30);

		String info_existing = getTextByLocator(By.xpath("//td[.='" + field + ":']/following-sibling::td[1]"));

		return info_existing.contains(info);
	}

	@Test
	public void testEmailIconNotShown() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		main.loginAs(Settings.USERNAME, Settings.PASSWORD);
		// wait(2 * Settings.WAIT_SECONDS);
		AccountOverviewPage accountOverviewPage = main.goToAccountOverviewPage();

		accountOverviewPage.goToClientDetailPageByName("Doe, John");
		assertTrue(this.infoIsCorrectForThisField("Email Function", "Disabled"));
		assertFalse(isElementVisible(By.id("gwt-debug-UserDetailsSectionPresenter-emailImage")));
	}

	@Test
	public void testSystemIDPosition() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		String exSystemID = "testSystemID";
		MenuBarPage mbPage = main.loginAs(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD);

		AccountOverviewPage accountOverviewPage = mbPage.goToAccountOverviewPageMaterialView();

		DetailPage exSysID = accountOverviewPage.simpleSearchByString("Miroslav")
				.goToClientDetailPageByName("Bernarde, Miroslav").goToEditPageByField("Additional Details")
				.editClientExternalSystemID(exSystemID).clickSaveButton_ClientAdditionalDetail();

		// add external system ID
		assertTrue(getTextByLocator(By.xpath("(//tr[td[.='Signature File:']]/following-sibling::tr[1]/td)[2]"))
				.equals(exSystemID));

		// delete external system ID
		exSysID.goToEditPageByField("Additional Details").editClientExternalSystemID("")
				.clickSaveButton_ClientAdditionalDetail();

		assertFalse(isElementVisible(By.xpath("(//tr[td[.='Signature File:']]/following-sibling::tr[1]/td)[2]")));
	}

	@Test
	public void checkNoSystemCreationDate() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		main.loginAs(Settings.INVESTOR_USERNAME, Settings.INVESTOR_PASSWORD).goToAccountOverviewPage()
				.goToAccountHoldingsPageByName("Generali Vision - TWRR").goToDetailsPage();

		assertFalse(pageContainsStr("System Creation Date"));
	}

	@Test
	public void ensureLastLoginDateIsCorrect() throws Exception {

		String format = "d-MMM-yyyy HH:mm";
		String client = "Investor, Selenium";

		// Login as SeleniumTest
		LoginPage main1 = new LoginPage(webDriver);

		MenuBarPage seleniumTest = main1.loginAs(Settings.USERNAME, Settings.PASSWORD);

		String currentTime1 = getCurrentTimeInFormat(format) + " (UTC+8)";

		log(currentTime1);

		seleniumTest.goToAccountOverviewPage().simpleSearchByString("Selenium").goToClientDetailPageByName(client)
				.goToCRMPage();

		handleAlert();
		this.checkLogout();
		handleAlert();
		wait(60);

		// Login as SeleniumInvestor
		LoginPage main2 = new LoginPage(webDriver);

		main2.loginAs(Settings.INVESTOR_USERNAME, Settings.INVESTOR_PASSWORD);

		handleAlert();
		String currentTime2 = getCurrentTimeInFormat(format) + " (UTC+8)";

		log(currentTime2);
		this.checkLogout();
		handleAlert();
		// Login as SeleniumTest again
		LoginPage main3 = new LoginPage(webDriver);
		wait(5);

		main3.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage().simpleSearchByString("Selenium")
				.goToClientDetailPageByName(client).goToCRMPage();

		assertTrue(!currentTime1.equals(currentTime2));
		waitForElementVisible(By.xpath("//td[.='Last Login Date:']/following-sibling::td"), 30);
		log(getTextByLocator(By.xpath("//td[.='Last Login Date:']/following-sibling::td")));
		log("currentTime2 " + currentTime2);

		assertEquals(getTextByLocator(By.xpath("//td[.='Last Login Date:']/following-sibling::td")), currentTime2);

	}

	// Test plan url:
	// "https://docs.google.com/spreadsheets/d/1qfGvoy9pTSFWoKa7J2Ny5Yjizwr4TA3_H-5KJrH-BlI/edit#gid=0"
	// Test case: Client/advisor account test plan - Investor - #11
	@Test
	public void testBreadcrumbsShowCorrectInformation() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		String client1_fn = "Miroslav";
		String client1_ln = "Bernarde";
		String client2_fn = "Peter";
		String client2_ln = "Chao";
		String client1 = client1_ln + ", " + client1_fn;
		String client2 = client2_ln + ", " + client2_fn;
		MenuBarPage mbPage = main.loginAs(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD);
		// wait(5);
		ClientOverviewPage clientOverviewPage = mbPage.goToClientOverviewPage();

		DetailPage detail = clientOverviewPage.simpleSearchByString(client1_fn).goToClientDetailPageByName(client1);

		// check breadcrumb
		String client1_breadcrumb = getTextByLocator(
				By.xpath("//a[contains(@id, 'gwt-debug-Breadcrumb-generalUserDetailsClientDetail')]"));

		assertTrue(client1_breadcrumb.equals(client1_fn + " " + client1_ln));

		// check Details Section
		assertTrue(getTextByLocator(By.xpath("//td[.='First Name:']/following-sibling::td")).equals(client1_fn));

		assertTrue(getTextByLocator(By.xpath("//td[.='Last Name:']/following-sibling::td")).equals(client1_ln));

		// go to Wealth Planning page
		CRMPage crm = detail.goToCRMPageGeneralUserDetailViewTab();
		waitForElementVisible(
				By.xpath(".//*[@id='gwt-debug-WealthPlanningSectionView-contentTable']/tbody/tr[1]/td[1]/div"), 10);
		String date = getTextByLocator(
				By.xpath(".//*[@id='gwt-debug-WealthPlanningSectionView-contentTable']/tbody/tr[1]/td[1]/div"));

		WealthPlanningOverviewPage wealthPlanningOverviewPage = crm.clickViewUnderWealthPlanning(date);

		clickOkButtonIfVisible();

		ClientOverviewPage clientOverviewPageNew = wealthPlanningOverviewPage.goToClientOverviewPage();

		clientOverviewPageNew.simpleSearchByString(client2_fn).goToClientDetailPageByName(client2);

		// check breadcrumb
		String client2_breadcrumb = getTextByLocator(
				By.xpath("//a[contains(@id, 'gwt-debug-Breadcrumb-generalUserDetailsClientDetail')]"));

		assertTrue(client2_breadcrumb.equals(client2_fn + " " + client2_ln));

		// check Details Section
		assertTrue(getTextByLocator(By.xpath("//td[.='First Name:']/following-sibling::td")).equals(client2_fn));

		assertTrue(getTextByLocator(By.xpath("//td[.='Last Name:']/following-sibling::td")).equals(client2_ln));
	}

	@Test
	public void testAddnRemoveSignature() throws InterruptedException, AWTException {
		LoginPage main = new LoginPage(webDriver);

		main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage()
				.goToClientDetailPageByName("Investor, Selenium").uploadSignature().deleteSignature();

	}

}
