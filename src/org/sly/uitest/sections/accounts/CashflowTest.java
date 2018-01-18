package org.sly.uitest.sections.accounts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;

import org.junit.Test;
import org.openqa.selenium.By;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.abstractpage.MenuBarPage;
import org.sly.uitest.pageobjects.admin.AdminEditPage;
import org.sly.uitest.pageobjects.clientsandaccounts.AccountOverviewPage;
import org.sly.uitest.pageobjects.clientsandaccounts.CashflowPage;
import org.sly.uitest.pageobjects.clientsandaccounts.DetailPage;
import org.sly.uitest.pageobjects.clientsandaccounts.HoldingsPage;
import org.sly.uitest.settings.Settings;

/**
 * @author Lynne Huang
 * @date : 22 Sep, 2015
 * @company Prive Financial
 */
public class CashflowTest extends AbstractPage {

	@Test
	public void testCashflowScheduleToCashflowDetails() throws InterruptedException, ParseException {

		LoginPage main = new LoginPage(webDriver);
		MenuBarPage mbPage = main.loginAs(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD);
		// wait(5);

		AccountOverviewPage accountOverviewPage = mbPage.goToAccountOverviewPage();

		DetailPage detail = accountOverviewPage.simpleSearchByString("Nate")
				.goToAccountHoldingsPageByName("Generali Vision - 3150581").goToDetailsPage();

		waitForElementVisible(By.xpath("//td[.='Contribution Information Source:']"), 30);

		assertTrue(
				this.getTextByLocator(By.xpath("//td[.='Contribution Information Source:']/following-sibling::td[1]"))
						.equals("Manual Cashflow Table"));

		assertTrue(this.getTextByLocator(By.xpath("//td[.='Withdrawal Information Source:']/following-sibling::td[1]"))
				.equals("Manual Cashflow Table"));

		String startDate = "01-Aug-2014";
		String endDate = "01-Aug-2032";
		String valid_endDate = "01-Aug-2029";
		String frequency = "Quarterly";
		String type = "Mandatory Contribution";
		String amount = "500.00";

		CashflowPage cashflow = detail.goToCashflowPage().clickNewScheduleButton().editCashflowScheduleType(type)
				.editCashflowScheduleFrequency(frequency).editCashflowScheduleAmount(amount)
				.editCashflowScheduleEndDate(endDate).editCashflowScheduleStartDate(startDate)
				.clickSaveButton_CashflowSchedule();
		// Might change according to the text in application.
		assertTrue(pageContainsStr("The end date must be before this date: 11-Sep-2030 (maturity date)"));
		clickOkButtonIfVisible();
		cashflow.editCashflowScheduleEndDate(valid_endDate).editCashflowScheduleStartDate(startDate)
				.clickSaveButton_CashflowSchedule();
		cashflow.deleteThisCashflowSchedule(startDate, valid_endDate, frequency, type, amount);

		// Previous code
		/*
		 * String today = getCurrentTimeInFormat("d-MMM-yyyy"); String startDate
		 * = getDateAfterDays(today, -35, "d-MMM-yyyy"); String endDate =
		 * getDateAfterDays(today, 35, "d-MMM-yyyy"); String frequency =
		 * "Monthly"; String type = "Mandatory Contribution"; String amount =
		 * "123.00";
		 * 
		 * CashflowPage cashflow = main .loginAs(Settings.USERNAME,
		 * Settings.PASSWORD) .goToAccountOverviewPage()
		 * .simpleSearchByString("Generali") .goToAccountHoldingsPageByName(
		 * "Generali Vision - NEW") .goToCashflowPage()
		 * 
		 * // delete old ones .filterForCashflowDetails("Completed")
		 * .deleteAllCashflowInCashflowDetails()
		 * .deleteAllCashflowInCashflowSchedules()
		 * .filterForCashflowDetails("All")
		 * 
		 * // create new schedule .clickNewScheduleButton()
		 * .editCashflowScheduleType(type)
		 * .editCashflowScheduleFrequency(frequency)
		 * .editCashflowScheduleStartDate(startDate)
		 * .editCashflowScheduleEndDate(endDate)
		 * .editCashflowScheduleAmount(amount)
		 * .clickSaveButton_CashflowSchedule() .checkAllCashflowSchedules(false)
		 * .checkOneCashflowSchedules(startDate, endDate, frequency, type,
		 * amount, true).filterForCashflowDetails("All"); SimpleDateFormat
		 * format = new SimpleDateFormat("d-MMM-yyyy"); int size =
		 * getSizeOfElements(By .xpath(
		 * ".//*[@id='gwt-debug-DepositWithdrawalEventTable-editDeletePanel']/button[2]"
		 * ));
		 * 
		 * for (int i = 2; i < size + 2; i++) { try { String date = webDriver
		 * .findElement( By.xpath(
		 * ".//*[@id='gwt-debug-DepositWithdrawalEventTable-cashFlowTable']/tbody/tr["
		 * + i + "]/td[2]")).getText(); Date thisDate = format.parse(date); Date
		 * sDate = format.parse(endDate); Date eDate = format.parse(startDate);
		 * 
		 * wait(Settings.WAIT_SECONDS); assertTrue(sDate.compareTo(thisDate) >=
		 * 0); wait(Settings.WAIT_SECONDS); assertTrue(eDate.compareTo(thisDate)
		 * <= 0); i++; } catch (Exception e) { break; } }
		 * cashflow.deleteThisCashflowSchedule(startDate, endDate, frequency,
		 * type, amount);
		 */

	}

	@Test
	public void testNewContributionCashflowEntryInCRM() throws Exception {

		LoginPage main1 = new LoginPage(webDriver);

		String module1 = "MODULE_PORTFOLIO_OVERVIEW_CASHFLOW_DISPLAY";

		MenuBarPage mbPage1 = main1.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD);
		// wait(5);

		AdminEditPage adminEditPage = mbPage1.goToAdminEditPage();

		adminEditPage.editAdminThisField(Settings.Advisor_Company_Module_Permission)
				.jumpByKeyAndLoad(Settings.AdvisorABCCompany_Key).editModuleToggle(module1, false, true)
				.clickSubmitButton();

		checkLogout();
		// wait(2);
		handleAlert();

		LoginPage main = new LoginPage(webDriver);
		String today = getCurrentTimeInFormat("dd-MMM-yyyy");
		String type = "Scheme Contribution";
		String currency = "USD";
		String status = "Completed";
		String amount = "1,500.00";

		MenuBarPage mbPage = main.loginAs(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD);
		// wait(5);

		AccountOverviewPage accountOverviewPage = mbPage.goToAccountOverviewPage();

		DetailPage detail = accountOverviewPage.simpleSearchByString("Moventum - A6946841")
				.goToAccountHoldingsPageByName("Moventum - A6946841").goToDetailsPage();

		waitForElementVisible(By.xpath("//td[.='Contribution Information Source:']"), 30);

		assertTrue(
				this.getTextByLocator(By.xpath("//td[.='Contribution Information Source:']/following-sibling::td[1]"))
						.equals("Manual Cashflow Table"));

		assertTrue(this.getTextByLocator(By.xpath("//td[.='Withdrawal Information Source:']/following-sibling::td[1]"))
				.equals("Manual Cashflow Table"));

		CashflowPage cashflow = detail.goToCashflowPage().uncheckNewBusiness(false).checkOtherCashflows(true)
				.clickNewCashflowButton().editCashflowCurrency(currency).editCashflowStatus(status)
				.editCashflowType(type).editTransactionDate(today).editCashflowAmount(amount).editValueDate(today)
				.clickSaveButton_CashflowEntry();

		this.waitForWaitingScreenNotVisible();

		cashflow.goToCashflowPage();

		assertTrue(this
				.getTextByLocator(By.xpath("//td[.='" + today + "']/following-sibling::td[.='" + today
						+ "']/following-sibling::td[.='" + type + "']/following-sibling::td[.='" + status
						+ "']/following-sibling::td[.='" + currency + "']/following-sibling::td[1]"))
				.equals(amount));

		// delete this cashflow
		cashflow.deleteThisCashflowEntry(today, today, type, status, currency, amount);

	}

	@Test
	public void testNewWithdrawlCashflowEntryInCRM() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		String today = getCurrentTimeInFormat("dd-MMM-yyyy");
		String type = "Withdrawal";
		String currency = "USD";
		String status = "Completed";
		String amount = "500.00";
		MenuBarPage mbPage = main.loginAs(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD);
		// wait(5);

		AccountOverviewPage accountOverviewPage = mbPage.goToAccountOverviewPage();

		DetailPage detail = accountOverviewPage.simpleSearchByString("Moventum - A6946841")
				.goToAccountHoldingsPageByName("Moventum - A6946841").goToDetailsPage();

		waitForElementVisible(By.xpath("//td[.='Contribution Information Source:']"), 30);

		assertTrue(
				this.getTextByLocator(By.xpath("//td[.='Contribution Information Source:']/following-sibling::td[1]"))
						.equals("Manual Cashflow Table"));

		assertTrue(this.getTextByLocator(By.xpath("//td[.='Withdrawal Information Source:']/following-sibling::td[1]"))
				.equals("Manual Cashflow Table"));

		CashflowPage cashflow = detail.goToCashflowPage().uncheckNewBusiness(false).checkOtherCashflows(true)
				.clickNewCashflowButton().editCashflowCurrency(currency).editCashflowStatus(status)
				.editCashflowType(type).editTransactionDate(today).editCashflowAmount(amount).editValueDate(today)
				.clickSaveButton_CashflowEntry();

		waitForElementVisible(By.xpath("//td[.='" + today + "']/following-sibling::td[.='" + today
				+ "']/following-sibling::td[.='" + type + "']/following-sibling::td[.='" + status
				+ "']/following-sibling::td[.='" + currency + "']/following-sibling::td[1]"), 30);

		assertTrue(this
				.getTextByLocator(By.xpath("//td[.='" + today + "']/following-sibling::td[.='" + today
						+ "']/following-sibling::td[.='" + type + "']/following-sibling::td[.='" + status
						+ "']/following-sibling::td[.='" + currency + "']/following-sibling::td[1]"))
				.equals(amount));

		// delete this cashflow
		cashflow.deleteThisCashflowEntry(today, today, type, status, currency, amount);

	}

	@Test
	public void testNewCashflowScheduleWithInvalidDate() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		String startDate = "01-Aug-2014";
		String endDate = "01-Aug-2032";
		String type = "Mandatory Contribution";
		String frequency = "Quarterly";
		String amount = "500.00";
		MenuBarPage mbPage = main.loginAs(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD);

		AccountOverviewPage accountOverviewPage = mbPage.goToAccountOverviewPage();

		DetailPage detail = accountOverviewPage.simpleSearchByString("Nate")
				.goToAccountHoldingsPageByName("Generali Vision - 3150581").goToDetailsPage();

		assertTrue(pageContainsStr("Contribution Information Source:"));
		assertTrue(pageContainsStr("Manual Cashflow Table"));

		detail.goToCashflowPage().clickNewScheduleButton().editCashflowScheduleType(type)
				.editCashflowScheduleFrequency(frequency).editCashflowScheduleAmount(amount)
				.editCashflowScheduleEndDate(endDate).editCashflowScheduleStartDate(startDate)
				.clickSaveButton_CashflowSchedule();
		this.waitForElementVisible(By.xpath(".//*[@id='gwt-debug-CustomDialog-message']"), Settings.WAIT_SECONDS);
		assertTrue(pageContainsStr("Some entries are invalid."));
		assertTrue(pageContainsStr("The end date must be before this date: 11-Sep-2030 (maturity date)."));

		clickOkButtonIfVisible();

	}

	@Test
	public void testNewCashflowCompleteInCRM() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		String today = getCurrentTimeInFormat("dd-MMM-yyyy");
		String startDate = "01-Aug-2014";
		String endDate = "01-Aug-2017";
		String type = "Mandatory Contribution";
		String frequency = "Quarterly";
		String amount = "500.00";
		String currency = "USD";

		MenuBarPage mbPage = main.loginAs(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD);

		AccountOverviewPage aop = mbPage.goToAccountOverviewPage();

		aop.simpleSearchByString("Nate").goToAccountHoldingsPageByName("Generali Vision - 3150581").goToDetailsPage();

		waitForElementVisible(By.xpath("//td[.='Contribution Information Source:']/following-sibling::td[1]"), 30);

		assertTrue(
				this.getTextByLocator(By.xpath("//td[.='Contribution Information Source:']/following-sibling::td[1]"))
						.equals("Manual Cashflow Table"));

		assertTrue(this.getTextByLocator(By.xpath("//td[.='Withdrawal Information Source:']/following-sibling::td[1]"))
				.equals("Manual Cashflow Table"));

		CashflowPage cashflow = aop.goToCashflowPage().checkAllCashflowSchedules(true)
				.filterForCashflowDetails("Completed").deleteAllCashflowInCashflowDetails()
				.deleteAllCashflowInCashflowSchedules().filterForCashflowDetails("All").clickNewScheduleButton()
				.editCashflowScheduleType(type).editCashflowScheduleFrequency(frequency)
				.editCashflowScheduleAmount(amount).editCashflowScheduleEndDate(endDate)
				.editCashflowScheduleStartDate(startDate).clickSaveButton_CashflowSchedule();
		// 1. check five new "Scheme Contribution" entries
		cashflow.goToCashflowPage().filterForCashflowDetails("All").checkAllCashflowSchedules(false)
				.checkOneCashflowSchedules(startDate, endDate, frequency, type, amount, true)
				.checkOtherCashflows(false);
		for (int i = 0; i < 5; i++) {
			try {
				assertTrue(this.getTextByLocator(
						By.xpath(".//*[@id='gwt-debug-DepositWithdrawalEventTable-cashFlowTable']/tbody/tr[" + (i + 2)
								+ "]/td[3]"))
						.equals(today));
			} catch (Exception e) {

			}

		}

		// 2. check total contributions reflected
		String contribution = this.getTextByLocator(By.id("gwt-debug-PortfolioOverviewView-totalContributionLabel"));

		int oldContribution = Integer.parseInt(contribution.split(" ")[0].replace(",", "").replace(".00", ""));

		System.out.println("old: " + oldContribution);

		cashflow.checkOneCashflowEntry(startDate, today, "Scheme Contribution", "Pending", currency, amount, true)
				.markCompleted().clickRefreshButton();

		contribution = getTextByLocator(By.id("gwt-debug-PortfolioOverviewView-totalContributionLabel"));

		int newContribution = Integer.parseInt(contribution.split(" ")[0].replace(",", "").replace(".00", ""));

		System.out.println("new: " + newContribution);

		System.out.println(newContribution - oldContribution);

		assertTrue(newContribution - oldContribution == 500);

		// 3. check another payment at the same day

		String amount2 = "100.00";

		cashflow.goToCashflowPage().clickNewScheduleButton().editCashflowScheduleType(type)
				.editCashflowScheduleFrequency(frequency).editCashflowScheduleAmount(amount2)
				.editCashflowScheduleEndDate(endDate).editCashflowScheduleStartDate(startDate)
				.clickSaveButton_CashflowSchedule().clickRefreshButton();

		contribution = this.getTextByLocator(By.id("gwt-debug-PortfolioOverviewView-totalContributionLabel"));

		int oldContribution2 = Integer.parseInt(contribution.split(" ")[0].replace(",", "").replace(".00", ""));

		System.out.println(oldContribution2);

		cashflow.goToCashflowPage().checkAllCashflowSchedules(false).checkOneCashflowSchedules(startDate, endDate,
				frequency, type, amount2, true);

		this.waitForWaitingScreenNotVisible();

		cashflow.checkOneCashflowEntry(startDate, today, "Scheme Contribution", "Pending", currency, amount2, true)
				.markCompleted().clickRefreshButton();

		contribution = getTextByLocator(By.id("gwt-debug-PortfolioOverviewView-totalContributionLabel"));

		int newContribution2 = Integer.parseInt(contribution.split(" ")[0].replace(",", "").replace(".00", ""));

		System.out.println(newContribution2);

		System.out.println(newContribution2 - oldContribution2);

		assertTrue(newContribution2 - oldContribution2 == 100);

		// delete cashflow
		cashflow.goToCashflowPage().checkAllCashflowSchedules(false)
				.checkOneCashflowSchedules(startDate, endDate, frequency, type, amount, true)
				.deleteThisCashflowEntry(startDate, today, "Scheme Contribution", "Completed", currency, amount)
				.deleteThisCashflowSchedule(startDate, endDate, frequency, type, amount)
				.checkAllCashflowSchedules(false)
				.checkOneCashflowSchedules(startDate, endDate, frequency, type, amount2, true)
				.deleteThisCashflowEntry(startDate, today, "Scheme Contribution", "Completed", currency, amount2)
				.deleteThisCashflowSchedule(startDate, endDate, frequency, type, amount2);

	}

	@Test
	public void testChangeCashflowFrequency() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		String today = getCurrentTimeInFormat("dd-MMM-yyyy");
		String startDate = "12-Sep-2013";
		String endDate = "01-May-2015";
		String type = "Mandatory Contribution";
		String frequency = "Lump Sum";
		String amount = "500.00";

		AccountOverviewPage accountOverviewPage = main.loginAs(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD)
				.goToAccountOverviewPage();

		CashflowPage cashflow = accountOverviewPage.simpleSearchByString("Nate")
				.goToAccountHoldingsPageByName("Generali Vision - 3150581").goToCashflowPage()
				.deleteAllCashflowInCashflowDetails().clickNewScheduleButton().editCashflowScheduleStartDate(startDate)
				.editCashflowScheduleType(type).editCashflowScheduleFrequency(frequency)
				.editCashflowScheduleAmount(amount).clickSaveButton_CashflowSchedule();

		waitForElementVisible(By.id("gwt-debug-NewBusinessEventWidget-allCheckBox-input"), 30);

		// check Lump Sum Cashflow
		cashflow.checkAllCashflowSchedules(false)
				.checkOneCashflowSchedules(startDate, "", frequency, type, amount, true).checkOtherCashflows(false)
				.clickRefreshButton().goToCashflowPage();

		waitForElementVisible(By.xpath(".//*[@id='gwt-debug-DepositWithdrawalEventTable-cashFlowTable']"), 30);
		log("Today: " + today);
		log("Start Date: " + startDate);
		assertTrue(isElementPresent(
				By.xpath(".//*[@id='gwt-debug-DepositWithdrawalEventTable-cashFlowTable']/tbody/tr/td[.='" + today
						+ "']/preceding-sibling::td[.='" + startDate + "']")));

		// check Quarterly
		String frequency2 = "Monthly";
		cashflow.editThisCashflowSchedule(startDate, "", frequency, type, amount)
				.editCashflowScheduleFrequency(frequency2).editCashflowScheduleEndDate(endDate)
				.editCashflowScheduleStartDate(startDate).editCashflowScheduleFrequency(frequency2)
				.clickSaveButton_CashflowSchedule().checkAllCashflowSchedules(false).checkOtherCashflows(false)
				.checkOneCashflowSchedules(startDate, endDate, frequency2, type, amount, true);

		for (int i = 0; i < 3; i++) {
			if (!isElementVisible(
					By.xpath(".//*[@id='gwt-debug-DepositWithdrawalEventTable-cashFlowTable']/tbody/tr[1]/td[3]"))) {
				this.refreshPage();
				waitForElementVisible(By.id("gwt-debug-NewBusinessEventWidget-contentTable"), 10);
			}
		}

		for (int i = 0; i < 13; i++) {
			wait(3);
			log(String.valueOf(i + 1));

			assertEquals(getTextByLocator(By.xpath(
					".//*[@id='gwt-debug-DepositWithdrawalEventTable-cashFlowTable']/tbody/tr[" + (i + 2) + "]/td[3]")),
					today);

		}

		// delete cashflow schedule
		cashflow.deleteThisCashflowSchedule(startDate, endDate, frequency2, type, amount);
	}

	@Test
	public void testCashflowManual() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		String date1 = "01-Aug-2013";
		String date2 = "01-Aug-2018";
		String type1 = "Withdrawal";
		String type2 = "Lump Sum";
		String currency1 = "SGD";
		String currency2 = "GBP";
		String amount1 = "100.00";
		String amount2 = "200.00";
		String status = "Pending";

		String creationDate = getCurrentTimeInFormat("dd-MMM-yyyy");

		HoldingsPage holdingsPage = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage()
				.simpleSearchByString("John").goToAccountHoldingsPageByName("Generali Vision - NEW");

		clickOkButtonIfVisible();

		CashflowPage cashflow = holdingsPage.goToCashflowPage().checkAllCashflowSchedules(false)
				.checkOtherCashflows(true).filterForCashflowDetails("All").deleteAllCashflowInCashflowDetails();

		// add one for Aug-1-2013 and one for Aug-1-2018
		cashflow.goToCashflowPage().checkAllCashflowSchedules(false).clickNewCashflowButton()
				.editCashflowValueDate(date1).editCashflowType(type1).editCashflowCurrency(currency1)
				.editCashflowAmount(amount1).editCashflowStatus(status).editTransactionDate(creationDate)
				.clickSaveButton_CashflowEntry().filterForCashflowDetails(status);

		waitForElementVisible(By.xpath("//td[.='" + date1 + "']/following-sibling::td[5]"), 60);

		assertTrue(this.getTextByLocator(By.xpath("//td[.='" + date1 + "']/following-sibling::td[5]")).equals(amount1));
		cashflow.clickNewCashflowButton().editCashflowValueDate(date2).editCashflowType(type2)
				.editCashflowCurrency(currency2).editCashflowAmount(amount2).editCashflowStatus(status)
				.editTransactionDate(creationDate).clickSaveButton_CashflowEntry().filterForCashflowDetails(status);

		waitForElementVisible(By.xpath("//td[.='" + date2 + "']/following-sibling::td[5]"), Settings.WAIT_SECONDS * 3);

		assertTrue(this.getTextByLocator(By.xpath("//td[.='" + date2 + "']/following-sibling::td[5]")).equals(amount2));

		// filter: Past
		cashflow.filterForCashflowDetails("Past");

		assertTrue(pageContainsStr(date1));
		assertFalse(pageContainsStr(date2));

		// filter: Future
		cashflow.filterForCashflowDetails("Futures");

		assertFalse(pageContainsStr(date1));
		assertTrue(pageContainsStr(date2));

		// filter: All
		cashflow.filterForCashflowDetails("All");

		assertTrue(pageContainsStr(date1));
		assertTrue(pageContainsStr(date2));

		// edit 2013 amount
		String amount1_edit = "150.00";

		cashflow.editCashflowEntry(date1, creationDate, type1, status, currency1, amount1)
				.editCashflowAmount(amount1_edit).editCashflowCurrency(currency1).editCashflowStatus(status)
				.editCashflowType(type1).editCashflowAmount(amount1_edit).clickSaveButton_CashflowEntry();

		this.waitForWaitingScreenNotVisible();
		assertTrue(this.getTextByLocator(By.xpath("//td[.='" + date1 + "']/following-sibling::td[5]"))
				.equals(amount1_edit));

		// completed
		cashflow.checkOneCashflowEntry(date1, creationDate, type1, status, currency1, amount1_edit, true)
				.checkOneCashflowEntry(date2, creationDate, type2, status, currency2, amount2, true).markCompleted();

		cashflow.filterForCashflowDetails("Completed");

		assertTrue(pageContainsStr(date1));
		assertTrue(pageContainsStr(date2));

		cashflow.filterForCashflowDetails("Pending");

		assertFalse(pageContainsStr(date1));
		assertFalse(pageContainsStr(date2));

	}

	@Test
	public void testNewTransactionTabWork() throws Exception {

		String module = "MODULE_PORTFOLIO_HISTORY_TRANSACTIONS";
		Boolean investor = false;
		Boolean advisor = true;
		String account = "Moventum - A6946841";
		String source = "Automatic";
		LoginPage main = new LoginPage(webDriver);
		MenuBarPage mbPage = main.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD);
		// wait(10);

		// Turn on MODULE_PORTFOLIO_HISTORY_TRANSACTIONS and SUBMIT
		mbPage.goToAdminEditPage().editAdminThisField(Settings.Advisor_Company_Module_Permission)
				.jumpByKeyAndLoad(Settings.AdvisorABCCompany_Key).editModuleToggle(module, investor, advisor)
				.clickSubmitButton();

		// wait(Settings.WAIT_SECONDS);
		this.checkLogout();
		handleAlert();

		// Check as JarlAdvisor
		LoginPage main2 = new LoginPage(webDriver);
		MenuBarPage mbPage2 = main2.loginAs(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD);
		// wait(5);

		AccountOverviewPage accountOverviewPage = main2.goToAccountOverviewPage();

		((DetailPage) accountOverviewPage.simpleSearchByString(account).goToAccountHoldingsPageByName(account)
				.goToDetailsPage().goToEditPageByField("Details").editAccountUpdateSource(source)
				.clickSaveButton_AccountDetail()).goToTransactionHistoryPage();

		assertTrue(pageContainsStr("Show transactions"));
		assertTrue(pageContainsStr("Show transactions history for"));
		assertTrue(pageContainsStr("Show only unmatched transactions"));

		// DOES NOT include check transactions

	}

	@Test
	public void testCashflowHasNoNullValue() throws InterruptedException {

		String account = "Generali Vision - 6921612";

		LoginPage main = new LoginPage(webDriver);
		HoldingsPage holdingsPage = main.loginAs(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD)
				.goToAccountOverviewPage().simpleSearchByString(account).goToAccountHoldingsPageByName(account);
		clickOkButtonIfVisible();

		CashflowPage cashflow = holdingsPage.goToCashflowPage();
		assertFalse(getTextByLocator(By.id("gwt-debug-NewBusinessEventWidget-typeLabel")).contains("null"));

	}
	//
	// private void loadAdminEditMaterialView() {
	// clickElement(By
	// .xpath(".//*[.='Administration' and @class='navmat-label']"));
	// clickElement(By.xpath("//li/ul/li/a[.='Edit']"));
	// }
	//
	// private void makeClassicView() throws InterruptedException {
	// clickElement(By
	// .xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']/div/div/li/a[.='My
	// Settings']"));
	// clickElement(By
	// .xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']/div/div/li/ul/li/a[.='User
	// Preference']"));
	// wait(2);
	// clickElement(By
	// .id("gwt-debug-UpdateUserSystemPreferenceView-ClassicViewRadio"));
	// clickElement(By
	// .id("gwt-debug-UpdateUserSystemPreferenceView-submitButton"));
	// }
}
