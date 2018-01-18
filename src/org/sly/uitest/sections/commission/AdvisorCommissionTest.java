/**
 * 
 */
package org.sly.uitest.sections.commission;

import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.abstractpage.MenuBarPage;
import org.sly.uitest.pageobjects.commissioning.CommissionsFeesPage;
import org.sly.uitest.pageobjects.commissioning.EditFeeEntryPage;
import org.sly.uitest.pageobjects.commissioning.NewBusinessEventEditPage;
import org.sly.uitest.pageobjects.commissioning.NewBusinessEventPage;
import org.sly.uitest.settings.Settings;
import org.sly.uitest.utils.Utils;

/**
 * @author dsarkar
 * 
 */
public class AdvisorCommissionTest extends AbstractTest {

	/**
	 * This tests whether the "show paid items" checkbox works.
	 * 
	 * @throws InterruptedException
	 * 
	 * @see SLYAWS-4776
	 * @throws Exception
	 */

	@Test
	public void testAccessToAllCommissionPages() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		MenuBarPage commission = main.loginAs(Settings.CRM_USERNAME, Settings.CRM_PASSWORD);

		// Cashflow Overview
		commission.goToCashflowOverviewPage();

		// Client Fees
		commission.goToClientFeesPage();

		// Advisor Commissions
		commission.goToAdvisorCommissionPage();

		// Company Commissions
		commission.goToCompanyCommissionPage();

		// Indemnity Release
		commission.goToIndemnityReleasesPage();

		// Trail Fee
		commission.goToTrailFeeAgreementPage();
	}

	@Test
	public void testSerachByReleaseDate() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		String sDate = "1-Sep-2014";
		String eDate = "30-Nov-2014";

		main.loginAs(Settings.CRM_USERNAME, Settings.CRM_PASSWORD).goToAdvisorCommissionPage()
				.findAccountOnThisPage("Sam Barrie").expandAccountByName("Sam Barrie")
				.goToIndemnityReservePage("Sam Barrie").goToAdvancedSearchPage().searchByReleaseDateStartDate(sDate)
				.searchByReleaseDateEndDate(eDate).clickSearchButton();

		SimpleDateFormat format = new SimpleDateFormat("d-MMM-yyyy");

		Date startDate = format.parse(sDate);
		Date endDate = format.parse(eDate);

		int size = getSizeOfElements(By.xpath(".//*[@id='gwt-debug-FeeListPresenter-checkbox-input']"));

		for (int i = 1; i < size + 1; i++) {
			scrollToTop();
			clickElement(By.xpath(".//*[@id='gwt-debug-SortableFlexTableAsync-table-" + i + "-13']/button"));
			scrollToTop();
			String releaseDate = getAttributeStringByLocator(By.id("gwt-debug-FeeEditWidget-releaseDateTimestamp"),
					"value");

			log(releaseDate);

			Date thisDate = format.parse(releaseDate);

			assertTrue(endDate.compareTo(thisDate) >= 0);
			assertTrue(startDate.compareTo(thisDate) <= 0);

			EditFeeEntryPage feeEntry = new EditFeeEntryPage(webDriver);
			feeEntry.clickCancelButtonForFeeEntry();

		}

	}

	@Test
	public void testSerachByCounterparty() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		main.loginAs(Settings.CRM_USERNAME, Settings.CRM_PASSWORD);
		// wait(2 * Settings.WAIT_SECONDS);

		main.goToCompanyCommissionPage().expandAccountByName("Infinity Financial Solutions")
				.goToOperationRevenuePage("Infinity Financial Solutions").goToAdvancedSearchPage()
				.searchByCounterparty("Reconciliation Acc").searchByStartDate("17-Sep-2015")
				.clickSearchButtonCounterparty();
		waitForElementVisible(By.xpath(".//*[@id='gwt-debug-FeeListPresenter-checkbox-input']"), 30);
		int size = getSizeOfElements(By.xpath(".//*[@id='gwt-debug-FeeListPresenter-checkbox-input']"));

		for (int i = 1; i < size + 1; i++) {

			String party = getTextByLocator(
					By.xpath(".//*[@id='gwt-debug-SortableFlexTableAsync-table-" + i + "-5']/span"));

			// String[] counterparty = party.split(" - ");
			//
			// log(counterparty[1]);

			assertTrue(party.equals("Reconciliation Acc"));

		}

	}

	@Test
	public void testShowPaidItems() throws Exception {

		String client = createNewBusinessEvent();

		NewBusinessEventPage nbe = new NewBusinessEventPage(webDriver);

		// check new commission entry is generated, serach by NBE refNo
		NewBusinessEventPage refNo = nbe.clickEditIconByClientName(client).clickCompleteNBEButton()
				.checkNewBusinessEventByClientName(client, true).clickApproveNewBusniessButton();

		String ref = refNo.extractRefNoByName(client);

		CommissionsFeesPage generated = refNo.goToCompanyCommissionPage()
				.expandAccountByName("Infinity Financial Solutions")
				.goToReconciliationAccPage("Infinity Financial Solutions").goToAdvancedSearchPage()
				.searchByNBERefNo(ref).clickSearchButton();

		// verified
		assertTrue(pageContainsStr("1 - 1 of 1 Results"));
		//
		// assertTrue(webDriver
		// .findElement(
		// By.xpath(".//*[@id='gwt-debug-SortableFlexTableAsync-table-1-10']/div"))
		// .getText().equals("2,000"));

		// log(client);

		// test the commission is downstream processed (test plan:
		// Fees/Commissioning Test Plan #2)
		CommissionsFeesPage payableAcc = generated.editFeeEntryByClientName(client)
				.editCreditToRecipientType("Advisor Account").editCreditToRecipient("Adon Beddoes")
				.editCreditToAccount("Payable Acc").editStatus("Settled").clickSaveButtonForFeeEntry();
		// .checkTheEntry(client, true)
		// .clickProcessButton().checkAllTheEntries(true);
		// wait(2);
		// assertTrue(pageContainsStr("Sum of selected entries: USD 0.00, (This
		// sum value is for current page. )"));

		payableAcc.goToAdvisorCommissionPage().findAccountOnThisPage("Adon Beddoes").expandAccountByName("Adon Beddoes")
				.goToPayableAccPage("Adon Beddoes").checkTheEntry(client, true).clickMakePaymentButton();

		// .checkTheEntry(client).clickProcessButton()
		this.waitForWaitingScreenNotVisible();
		assertTrue(!pageContainsStr(client));

		assertTrue("Show paid items label.", pageContainsStr("Include paid items"));

		CommissionsFeesPage checked = payableAcc.checkIncludePaidItems(true);

		assertTrue(pageContainsStr(client));

		// delete the account and client
		String firstName = "Testf-" + client.split("Testf  - ")[1].split(" Testl")[0];
		String lastName = "Testl-" + client.split("Testl - ")[1];
		String name = lastName + ", " + firstName;
		//
		// String[] names = client.split(" ");
		// String firstName = names[0];
		// String lastName = names[1];

		checked.goToClientOverviewPage().simpleSearchByString(name).goToClientDetailPageByName(name).goToAccountsTab()
				.goToTheOnlyOneAssetAccount().goToEditPageByField("Details")
				.clickDeleteButtonByLocator(By.id("gwt-debug-InvestorAccountDetailWidget-deleteBtn"))
				.goToClientOverviewPage().simpleSearchByString(name).goToClientDetailPageByName(name)
				.goToEditPageByField("Additional Details")
				.clickDeleteButtonByLocator(By.id("gwt-debug-UserDetailWidget-deleteBtn"));
		// checked.deleteTheEntry(date, client);

	}

	@Test
	public void testCreateAdvisorCommissionEntry() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		CommissionsFeesPage newEntry = main.loginAs(Settings.CRM_USERNAME, Settings.CRM_PASSWORD)
				.goToAdvisorCommissionPage().expandAccountByName("Ben Bennett").goToSalaryAccPage("Ben Bennett")
				.clickNewEntryButton().editCreditToRecipientType("Advisor Account").editCreditToRecipient("Ben Bennett")
				.editCreditToAccount("Salary Acc").editDebitFromRecipientType("Advisor Account")
				.editDebitFromRecipient("Ben Bennett").editDebitFromAccount("Payable Acc")
				.editValueDate("10-Aug-2015\n").editAmount("100").clickSaveButtonForFeeEntry();

		assertTrue(pageContainsStr("10-Aug-2015"));
		assertTrue(pageContainsStr("Ben Bennett, Payable Acc"));

		newEntry.deleteTheEntry("10-Aug-2015", "Ben Bennett, Payable Acc");

	}

	@SuppressWarnings("deprecation")
	public String createNewBusinessEvent() throws Exception {

		String module = "MODULE_ACCOUNTING_COMMISSIONS_NBE_APPROVE_BUTTON";
		LoginPage main2 = new LoginPage(webDriver);

		main2.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD).goToAdminEditPage()
				.editAdminThisField(Settings.Advisor_Company_Module_Permission)
				.jumpByKeyAndLoad(Settings.InfinityFinancialSolution_Key).editModuleToggle(module, false, true)
				.clickSubmitButton();

		checkLogout();
		handleAlert();

		LoginPage main = new LoginPage(webDriver);
		/* NBE Test Plan Case 1 */
		// create new client
		String firstName = "Testf" + (UUID.randomUUID().getLeastSignificantBits());

		String lastName = "Testl" + (UUID.randomUUID().getLeastSignificantBits());
		String ID = "test2ID";
		MenuBarPage mbPage = main.loginAs("InfinityAdmin", "InfinityAdmin");
		wait(5);

		NewBusinessEventPage newBusinessEventPage = null;
		newBusinessEventPage = mbPage.goToNewBusinessPage();
		NewBusinessEventEditPage newClient = newBusinessEventPage.clickNewButtonToCreateNewBusniess()
				.editMainAdvisor("Beddoes, Adon").editMainAdmin("Dahliana").createNewClient()
				.editClientFirstName(firstName).editClientLastName(lastName).editClientIDNumber(ID)
				.editClientYearOfBirth("1987").editClientIDType("Birth Certificate").editClientNationality("Benin")
				.editClientDayOfBirth("1").editClientMonthOfBirth("Jan").editClientOccupation("test123")
				.editClientPosition("test123").clickSaveButton_ClientAdditionalDetail();
		wait(Settings.WAIT_SECONDS);
		String[] firstNameArray = firstName.split("-");
		String[] lastNameArray = lastName.split("-");
		assertTrue(pageContainsStr(firstNameArray[0].trim()));
		assertTrue(pageContainsStr(firstNameArray[1].trim()));
		assertTrue(pageContainsStr(lastNameArray[0].trim()));
		assertTrue(pageContainsStr(lastNameArray[1].trim()));
		// continue to create new business event
		/* NBE Test Plan Case 2 */
		newClient.editPlatformCategory("Regular Premiums").editProductPlatform("Generali Singapore Vision")
				.editTermYears("2\n").editIndemnityMonths("3\n");

		String openDateString = getInputByLocator(
				By.id("gwt-debug-BusinessEventEditWidgetView-accountSinceDateTextBox"));
		String endDateString = getInputByLocator(By.id("gwt-debug-BusinessEventEditWidgetView-accountTillDateTextBox"));

		int openYear = Integer.parseInt(openDateString.substring(openDateString.length() - 4));
		int endYear = Integer.parseInt(endDateString.substring(endDateString.length() - 4));

		if (getTextByLocator(By.id("gwt-debug-BusinessEventEditWidgetView-accountSinceDateTextBox"))
				.contains("01-Jan")) {
			assertTrue((endYear - openYear) == 1);
		} else {
			assertTrue((endYear - openYear) == 2);
		}

		// test Indemnity Calculation Script
		/* NBE Test Plan Case 3 */
		newClient.calculateIndemnitybutton();
		wait(3);
		String indemnity = getInputByLocator(By.id("gwt-debug-BusinessEventEditWidgetView-indemnityTextBox"));

		int months = Integer.valueOf(indemnity);

		String indemnityStart = getInputByLocator(
				By.id("gwt-debug-BusinessEventEditWidgetView-indemnityDateStartTextBox"));

		String indemnityEnd = getInputByLocator(By.id("gwt-debug-BusinessEventEditWidgetView-indemnityDateEndTextBox"));

		Date dateStart = Utils.getDateFromStandardFormat(indemnityStart);
		Date dateEnd = Utils.getDateFromStandardFormat(indemnityEnd);

		int addMonths = months % 12;
		int addYears = months / 12;

		Calendar startCalender = Calendar.getInstance();
		startCalender.setTime(dateStart);

		int year = startCalender.get(Calendar.YEAR);
		int month = startCalender.get(Calendar.MONTH);

		if ((month + addMonths) > 11) {
			year = year + addYears + 1;
			month = addMonths + month - 12;

		} else {
			month = month + addMonths;
			year = year + addYears;
		}

		dateStart.setMonth(month);
		dateStart.setYear(year);

		Calendar cal = Calendar.getInstance();
		cal.setTime(dateStart);
		cal.set(Calendar.MONTH, (month));
		cal.set(Calendar.YEAR, year);
		cal.add(Calendar.DAY_OF_MONTH, -1);

		dateStart = cal.getTime();
		wait(2 * Settings.WAIT_SECONDS);
		System.out.println(dateStart + "::" + dateEnd);
		assertTrue(dateStart.compareTo(dateEnd) == 0);

		// test Calculation icon for Expected Initial Commission
		/* NBE Test Plan Case 4 */
		newClient.editRegularPremium("10\n").editExpectedInitialCommission("100\n");

		String initialCommission = getInputByLocator(
				By.id("gwt-debug-BusinessEventEditWidgetView-expectedInitialCommissionTextBox"));

		assertTrue(initialCommission != null && !initialCommission.isEmpty());
		Actions actions = new Actions(webDriver);
		actions.moveToElement(
				webDriver.findElement(By.id("gwt-debug-BusinessEventEditWidgetView-expectedInitialCommissionTextBox")));
		// Test Commission Split (Default Tab)
		/* NBE Test Plan Case 5 */
		waitForElementVisible(By.id("gwt-debug-CommissionSplitMappingInnerWidget-addButton-Default"), 150);
		newClient.addNewCommissionTo("Default").editCommissionSplitType("DFM Provider")
				.editCommissionSplitAllocation("40\n").clickSaveButtonForComSplitMapping();

		String allocationFirst = "0";
		waitForElementVisible(By.id("gwt-debug-Default-allocation-1"), 150);
		try {
			allocationFirst = getTextByLocator(By.id("gwt-debug-Default-allocation-1"));
		} catch (Exception t) {
			System.out.println(t.getMessage());
		}
		String lastAllocation = getTextByLocator(By.id("gwt-debug-Default-allocation-last"));
		wait(5);
		Double a1 = Utils.getDoubleFromPercentageString(allocationFirst);
		Double a2 = Utils.getDoubleFromPercentageString(lastAllocation);

		assertTrue(a1 + a2 == 100);

		// Test Commission Split - create new Split (Renewal Tab)
		/* NBE Test Plan Case 6 */
		((NewBusinessEventEditPage) newClient.addNewSplit("Renewal")).addNewCommissionTo("Renewal")
				.editCommissionSplitType("DFM Provider").editCommissionSplitAllocation("20\n")
				.clickSaveButtonForComSplitMapping();

		String allocationRenewalFirst = getTextByLocator(By.id("gwt-debug-Renewal-allocation-1"));

		String lastRenewalAllocation = getTextByLocator(By.id("gwt-debug-Renewal-allocation-last"));

		Double r1 = Utils.getDoubleFromPercentageString(allocationRenewalFirst);
		Double r3 = Utils.getDoubleFromPercentageString(lastRenewalAllocation);

		assertTrue(r1 + r3 == 100);

		// Test Check to see if draft saves properly
		/* NBE Test Plan Case 8 */
		newClient.clickSaveDraftButton();

		assertTrue(pageContainsStr(firstNameArray[0].trim()));
		assertTrue(pageContainsStr(firstNameArray[1].trim()));
		assertTrue(pageContainsStr(lastNameArray[0].trim()));
		assertTrue(pageContainsStr(lastNameArray[1].trim()));
		String firstName2 = firstName.split("-")[1];
		firstName = "Testf  - " + firstName2;

		String lastName2 = lastName.split("-")[1];
		lastName = "Testl - " + lastName2;
		log(firstName + " " + lastName);
		scrollToTop();
		return firstName + " " + lastName;

	}

}
