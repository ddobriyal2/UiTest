package org.sly.uitest.sections.commission;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
import org.sly.uitest.pageobjects.clientsandaccounts.AccountOverviewPage;
import org.sly.uitest.pageobjects.commissioning.NewBusinessEventEditPage;
import org.sly.uitest.pageobjects.commissioning.NewBusinessEventPage;
import org.sly.uitest.pageobjects.crm.TasksPage;
import org.sly.uitest.settings.Settings;
import org.sly.uitest.utils.Utils;

/**
 * Tests new business event related features.
 * 
 * @author Harry Chin
 * @date : Aug 3, 2014
 * @company Prive Financial
 */
public class NewBusinessEventTest extends AbstractTest {

	/**
	 * Tests opening the new business event page.
	 * 
	 * @throws Exception
	 */

	@Test
	public void testWorkflowGeneratedAndDeleted() throws Exception {

		String clientName = createNewBusinessEvent();

		String[] names = clientName.split("Testl - ");

		String lastName = "Testl-" + names[1];

		NewBusinessEventPage nbe = new NewBusinessEventPage(webDriver);
		// wait(3);
		scrollToTop();
		TasksPage workflow = nbe.clickEditIconByClientName(clientName).clickCompleteNBEButton().goToTasksPage()
				.checkShowOnlyMyTasks(false).simpleSearchByString(names[1]);

		waitForElementVisible(By.xpath(".//*[contains(text(),'" + lastName + "')]"), 30);

		assertTrue(pageContainsStr(lastName));

		workflow.goToNewBusinessPage().checkNewBusinessEventByClientName(clientName, true).clickDeleteButton()
				.goToTasksPage().checkShowOnlyMyTasks(false).simpleSearchByString(lastName);

		waitForElementVisible(By.id("gwt-debug-TaskOverviewTable-noInfoPanel"), 30);

		assertTrue(pageContainsStr(" No information found. "));

	}

	@Test
	public void testIndemnityCalculationScript() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		// check commencement date
		NewBusinessEventEditPage newBusiness = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToNewBusinessPage()
				.clickNewButtonToCreateNewBusniess().selectExistingClient("Investor, Selenium")
				.editProductPlatform("--none--").editInvestorAccount("Selenium Test")
				// .editMaturityDate("22-Jun-2015")
				.editTermYears("10\n");

		String value = this.getInputByLocator(By.id("gwt-debug-BusinessEventEditWidgetView-accountSinceDateTextBox"));

		assertTrue(value == null || value.isEmpty());

		// check indemnity end date
		NewBusinessEventEditPage editBusiness = newBusiness.editIndemnityStartDate("22-Jun-2015")
				.editIndemnityMonths("12\n");

		String indemnityEnd = getInputByLocator(By.id("gwt-debug-BusinessEventEditWidgetView-indemnityDateEndTextBox"));

		assertTrue("Expected indemnityEnd value.", indemnityEnd.equals("21-Jun-2016"));

		// check calculation
		editBusiness.editInvestorAccount("---NEW---").editProductPlatform("Generali Choice").editTermYears("2\n");

		String startDate = this
				.getInputByLocator(By.id("gwt-debug-BusinessEventEditWidgetView-accountSinceDateTextBox"));

		String endDate = this.getInputByLocator(By.id("gwt-debug-BusinessEventEditWidgetView-accountTillDateTextBox"));

		int openYear = Integer.parseInt(startDate.substring(startDate.length() - 4));
		int endYear = Integer.parseInt(endDate.substring(endDate.length() - 4));

		if (getTextByLocator(By.id("gwt-debug-BusinessEventEditWidgetView-accountSinceDateTextBox"))
				.contains("01-Jan")) {
			assertTrue((endYear - openYear) == 1);
		} else {
			assertTrue((endYear - openYear) == 2);
		}

	}

	// on the pending new business page, simply click the delete button
	@Test
	public void testSoftDeleteDraftNBE() throws Exception {
		String clientName = createNewBusinessEvent();
		System.out.println("clientName: " + clientName);
		NewBusinessEventPage nbe = new NewBusinessEventPage(webDriver);
		nbe.clickDeleteIconByClientName(clientName);

		assertFalse(pageContainsStr(clientName));

		String firstName = "Testf-" + clientName.split("Testf  - ")[1].split(" Testl")[0];
		String lastName = "Testl-" + clientName.split("Testl - ")[1];
		clientName = firstName + " " + lastName;
		nbe.goToAccountOverviewPage();
		clientName = lastName + ", " + firstName;
		deleteClient(clientName);
	}

	@Test
	public void testSoftDeleteApprovedNBE() throws Exception {

		String clientName = createNewBusinessEvent();

		NewBusinessEventPage nbe = new NewBusinessEventPage(webDriver);

		nbe.clickEditIconByClientName(clientName).clickCompleteNBEButton().clickCheckboxByClientName(clientName)
				.clickApproveNewBusniessButton();

		assertFalse(isElementVisible(By.xpath(".//td[div[a[contains(text(),'" + clientName
				+ "')]]]//following-sibling::td[button[@title='Delete']]")));

	}

	// extract refNo, and delete using HARD DELETE NBE button
	@Test
	public void testHardDeleteDraftNBE() throws Exception {

		String clientName = createNewBusinessEvent();

		NewBusinessEventPage nbe = new NewBusinessEventPage(webDriver);

		// hard delete draft by refNo
		nbe.clickHardDeleteNBEButtonByRefNo(nbe.extractRefNoByName(clientName));

		assertTrue(!pageContainsStr(clientName));

		String firstName = "Testf-" + clientName.split("Testf  - ")[1].split(" Testl")[0];
		String lastName = "Testl-" + clientName.split("Testl - ")[1];
		clientName = firstName + " " + lastName;
		nbe.goToAccountOverviewPage();
		clientName = lastName + ", " + firstName;
		deleteClient(clientName);
	}

	@Test
	public void testHardDeleteApprovedNBE() throws Exception {

		String clientName = createNewBusinessEvent();

		NewBusinessEventPage nbe = new NewBusinessEventPage(webDriver);

		// hard delete approved one by refNo
		nbe.clickEditIconByClientName(clientName).clickCompleteNBEButton()
				.clickHardDeleteNBEButtonByRefNo(nbe.extractRefNoByName(clientName));

		assertTrue(!pageContainsStr(clientName));
		String firstName = "Testf-" + clientName.split("Testf  - ")[1].split(" Testl")[0];
		String lastName = "Testl-" + clientName.split("Testl - ")[1];
		clientName = firstName + " " + lastName;
		nbe.goToAccountOverviewPage();
		deleteAccount(clientName);
		clientName = lastName + ", " + firstName;
		deleteClient(clientName);
	}

	@SuppressWarnings("deprecation")
	public String createNewBusinessEvent() throws Exception {

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

	private void deleteAccount(String clientName) throws InterruptedException {
		// wait(3);
		AccountOverviewPage main = new AccountOverviewPage(webDriver);
		String clientName2 = changeNameFormat(clientName);
		// delete account
		main.goToAccountOverviewPage().simpleSearchByString(clientName2).checkIncludeInactiveAccounts(true);
		this.waitForElementVisible(By.id("gwt-debug-InvestorAccountTable-linkPortfolioName"), Settings.WAIT_SECONDS);
		main.goToAccountDetailPageByName(
				this.getTextByLocator(By.id("gwt-debug-InvestorAccountTable-linkPortfolioName")))
				.goToEditPageByField("Details")
				.clickDeleteButtonByLocator(By.id("gwt-debug-InvestorAccountDetailWidget-deleteBtn"));

		main.goToAccountOverviewPage().simpleSearchByString(clientName);

		assertTrue(pageContainsStr("No information found"));

	}

	private void deleteClient(String clientName) throws InterruptedException {

		MenuBarPage main = new MenuBarPage(webDriver);

		String clientName2 = changeNameFormat(clientName);

		// delete client
		main.goToClientOverviewPage().simpleSearchByString(clientName).goToClientDetailPageByName(clientName)
				.goToEditPageByField("Additional Details")
				.clickDeleteButtonByLocator(By.id("gwt-debug-UserDetailWidget-deleteBtn"));

		main.goToClientOverviewPage().simpleSearchByString(clientName);

		assertTrue(pageContainsStr("No information found"));

	}

	@Test
	public void testAdvancedSearchPanel() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		NewBusinessEventPage nbe = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToNewBusinessPage();
		// search without typing anything
		clickElement(By.id("gwt-debug-BusinessEventListView-searchImg"));

		assertEquals(this.getAttributeStringByLocator(By.id("gwt-debug-BusinessEventListView-searchBox"), "value"),
				"Please enter search term");
		// check simple search feature
		nbe.simpleSearchByString("John");

		assertTrue(pageContainsStr("John Doe (963)"));

		// check simple search with invalid text
		nbe.simpleSearchByString("a");

		assertTrue(pageContainsStr("gwt-debug-CustomDialog-message"));

		clickOkButtonIfVisible();

		// check advanced search panel without any input
		nbe.clickClearSearchIcon().goToAdvancedSearchPage().clickSearchButton();

		assertTrue(isElementDisplayed(By.id("gwt-debug-SortableFlexTableAsync-table")));

		// check advanced search panel - Status
		nbe.goToAdvancedSearchPage().searchByNBEApproved("Yes").clickSearchButton();

		int size = getSizeOfElements(By.xpath(".//*[@id='gwt-debug-BusinessEventListPresenter-checkbox-input']"));

		for (int i = 1; i < size + 1; i++) {

			assertTrue(
					getTextByLocator(By.id("gwt-debug-SortableFlexTableAsync-table-" + i + "-6")).equals("Approved"));

		}

		// check advaced search panel - start date

		SimpleDateFormat format = new SimpleDateFormat("d-MMM-yyyy");

		String sDate = "1-Mar-2014";

		nbe.clickClearSearchIcon().goToAdvancedSearchPage().searchByStartDate(sDate).clickSearchButton();

		size = getSizeOfElements(By.xpath(".//*[@id='gwt-debug-BusinessEventListPresenter-checkbox-input']"));

		for (int i = 1; i < size + 1; i++) {

			String date = getTextByLocator(By.id("gwt-debug-SortableFlexTableAsync-table-" + i + "-9"));

			Date thisDate = format.parse(date);
			Date startDate = format.parse(sDate);

			assertTrue(startDate.compareTo(thisDate) <= 0);

		}

		// check advaced search panel - end date

		String eDate = "5-Feb-2014";

		nbe.clickClearSearchIcon().goToAdvancedSearchPage().searchByEndDate(eDate).clickSearchButton();

		size = getSizeOfElements(By.xpath(".//*[@id='gwt-debug-BusinessEventListPresenter-checkbox-input']"));

		for (int i = 1; i < size + 1; i++) {

			String date = getTextByLocator(By.id("gwt-debug-SortableFlexTableAsync-table-" + i + "-9"));

			Date thisDate = format.parse(date);
			Date endDate = format.parse(eDate);

			assertTrue(endDate.compareTo(thisDate) >= 0);

		}

		// check advaced search panel - start date, end date

		sDate = "1-Feb-2014";
		eDate = "5-Mar-2014";

		nbe.clickClearSearchIcon().goToAdvancedSearchPage().searchByStartDate(sDate).searchByEndDate(eDate)
				.clickSearchButton();

		size = getSizeOfElements(By.xpath(".//*[@id='gwt-debug-BusinessEventListPresenter-checkbox-input']"));

		for (int i = 1; i < size + 1; i++) {

			String date = getTextByLocator(By.id("gwt-debug-SortableFlexTableAsync-table-" + i + "-9"));

			Date thisDate = format.parse(date);
			Date endDate = format.parse(eDate);
			Date startDate = format.parse(sDate);

			assertTrue(endDate.compareTo(thisDate) >= 0);
			assertTrue(startDate.compareTo(thisDate) <= 0);

		}

		// check advaced search panel - start date, end date, status

		sDate = "1-Feb-2014";
		eDate = "5-Mar-2014";

		nbe.clickClearSearchIcon().goToAdvancedSearchPage().searchByStartDate(sDate).searchByEndDate(eDate)
				.searchByNBEApproved("Yes").clickSearchButton();

		size = getSizeOfElements(By.xpath(".//*[@id='gwt-debug-BusinessEventListPresenter-checkbox-input']"));

		for (int i = 1; i < size + 1; i++) {

			String date = getTextByLocator(By.id("gwt-debug-SortableFlexTableAsync-table-" + i + "-9"));

			Date thisDate = format.parse(date);
			Date endDate = format.parse(eDate);
			Date startDate = format.parse(sDate);

			assertTrue(endDate.compareTo(thisDate) >= 0);
			assertTrue(startDate.compareTo(thisDate) <= 0);

			assertTrue(this.getTextByLocator(By.id("gwt-debug-SortableFlexTableAsync-table-" + i + "-6"))
					.equals("Approved"));

		}

	}

	@Test
	public void testCreateNBEAndEditNBE() throws Exception {

		String clientName = createNewBusinessEvent();
		String advisor = "Bennett, Ben";
		String advisorForAssertion = "Ben Bennett";
		String admin = "Dahliana";

		NewBusinessEventPage nbe = new NewBusinessEventPage(webDriver);

		nbe.clickEditIconByClientName(clientName).editMainAdvisor(advisor).editMainAdmin(admin).clickSaveDraftButton();

		assertTrue(getTextByLocator(By.xpath("//td[.='" + clientName + "']/following-sibling::td[4]"))
				.equals(advisorForAssertion));

		assertTrue(getTextByLocator(By.xpath("//td[.='" + clientName + "']/following-sibling::td[5]")).equals(admin));

		nbe.clickDeleteIconByClientName(clientName);

	}
}
