package org.sly.uitest.sections.salesprocess;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.framework.DevelopmentTest;
import org.sly.uitest.pageobjects.clientsandaccounts.AccountsPage;
import org.sly.uitest.pageobjects.commissioning.NewBusinessEventEditPage;
import org.sly.uitest.pageobjects.salesprocess.FSLoginPage;
import org.sly.uitest.pageobjects.salesprocess.FSMenubarPage;
import org.sly.uitest.pageobjects.salesprocess.FSTasksPage;
import org.sly.uitest.pageobjects.salesprocess.SalesWizardPage;
import org.sly.uitest.settings.Settings;

/**
 * This is the test for new business event approval. Check the URL below for
 * test plan https://docs.google.com/spreadsheets/d/1
 * QVu2GH0dvYKWa1dDoz5MSeGvHbOs_k3uAE0qlPDdCkA/edit#gid=0
 * 
 * @author Benny Leung
 * @date Oct 3,2016
 * @company Prive Financial
 *
 */
public class NewBusinessEventTest extends AbstractTest {
	String NBEstatus = "Fertig";

	@Ignore
	@Category(DevelopmentTest.class)
	public void testInformationForNewBusinessEvent() throws InterruptedException {
		String advisor = "Mitev, Miro";
		String client = "Frenchadclient Frenchadclient";
		String status = "Entwurf";
		String member = "1"; // 1 for blue and 2 for green
		String share = "10";
		String date = "9/10/2016";
		boolean payback = false;
		String paybackOption = "";
		String tarif = "A";
		String product = "Adamas Product";
		String membership = null;

		this.passAdamasSalesWizard(client, member, share, date, payback, paybackOption, tarif);

		FSLoginPage main = new FSLoginPage(webDriver);
		main.loginAs(Settings.FINANCESALES_ADMIN_USERNAME, Settings.FINANCESALES_ADMIN_PASSWORD)
				.goToNewBusinessEventPageFS();

		// Test case #51
		NewBusinessEventEditPage editNBE = this.clickEditNewBusinessEvent(client, "", status);

		waitForElementVisible(By.id("gwt-debug-BusinessEventEditWidgetView-clientNameLabel"), 10);

		// Test case 52 - checkProductAndPlatformAndCurrency()
		// Test case 53 - checkLumpsumAndBusinessValue()
		// check product and platform
		switch (member) {
		case "1":
			membership = "Blue";
			checkProductAndPlatformAndCurrency(product, membership + " - Tarif " + tarif);
			checkLumpsumAndBusinessValue(membership, share);
			break;
		case "2":
			membership = "Green";
			checkProductAndPlatformAndCurrency(product, membership + " - Tarif " + tarif);
			checkLumpsumAndBusinessValue(membership, share);
			break;
		}

		// Test case 53 - checkLumpsumAndBusinessValue()
		// checkCommissionMatrix(tarif);

		// Test case 54
		editNBE.clickCompleteNBEButton();

		wait(Settings.WAIT_SECONDS);

		assertTrue(getTextByLocator(By.xpath(".//*[.='" + client
				+ "']//ancestor::td//following-sibling::td[.='Ausstehend']//preceding-sibling::td[4]")).length() != 0);
	}

	// test case 55
	// 1. Advisor not registered with Adamas
	// 2. Not approve advisor application
	@Ignore
	@Category(DevelopmentTest.class)
	public void testNotRegisteredAndNotApproveApplication() throws InterruptedException {

		passCreateAndApproveNBE();

		FSMenubarPage fsMenu = new FSMenubarPage(webDriver);
		FSTasksPage tasks = fsMenu.goToTasklistPageFS().editShowOnlyMyTask(false).editTaskByName("FS muss bestätigen")
				.confirmAdvisorRegistration(false).editTaskStatus(NBEstatus).clickSaveButton();
		wait(10);
		tasks.editTaskByName("Interne Bewilligung der Berater Bewerbung").approveAdvisorApplication(false)
				.editTaskStatus(NBEstatus).clickSaveButton();

	}

	// test case 57
	// 1. Advisor not registered with Adamas
	// 2. Approve advisor application
	// 3. Not approve sales new business event
	@Ignore
	@Category(DevelopmentTest.class)
	public void testNotRegisteredAndApproveApplicationAndNotApproveNBE() throws InterruptedException {

		passCreateAndApproveNBE();

		FSMenubarPage fsMenu = new FSMenubarPage(webDriver);
		FSTasksPage tasks = fsMenu.goToTasklistPageFS().editShowOnlyMyTask(false).editTaskByName("FS muss bestätigen")
				.confirmAdvisorRegistration(false).editTaskStatus(NBEstatus).clickSaveButton();
		wait(10);
		tasks.editTaskByName("Interne Bewilligung der Berater Bewerbung").approveAdvisorApplication(true)
				.editTaskStatus(NBEstatus).clickSaveButton().editTaskByName("neuen Geschäftsvorfalls")
				.approveSalesNBE(false).editTaskStatus(NBEstatus).clickSaveButton();

	}

	// test 59
	// 1. Advisor not registered with Adamas
	// 2. Approve advisor application
	// 3. Approve sales new business event
	@Ignore
	@Category(DevelopmentTest.class)
	public void testNotRegisteredAndApproveApplicationAndApproveNBE() throws InterruptedException {

		passCreateAndApproveNBE();

		FSMenubarPage fsMenu = new FSMenubarPage(webDriver);
		FSTasksPage tasks = fsMenu.goToTasklistPageFS().editShowOnlyMyTask(false).editTaskByName("FS muss bestätigen")
				.confirmAdvisorRegistration(false).editTaskStatus(NBEstatus).clickSaveButton();
		wait(10);
		tasks.editTaskByName("Interne Bewilligung der Berater Bewerbung").approveAdvisorApplication(true)
				.editTaskStatus(NBEstatus).clickSaveButton();
		wait(10);
		tasks.editTaskByName("neuen Geschäftsvorfalls").approveSalesNBE(true).editTaskStatus(NBEstatus)
				.clickSaveButton();
		wait(10);
		tasks.editTaskByName("Bestätige den Beleg der Zahlung des Kunden").editTaskStatus(NBEstatus).clickSaveButton();

	}

	// test 61
	// 1. Advisor registered with Adamas
	// 2. Not approve sales new business event
	@Ignore
	@Category(DevelopmentTest.class)
	public void testRegisteredAndNotApproveNBE() throws InterruptedException {

		passCreateAndApproveNBE();

		FSMenubarPage fsMenu = new FSMenubarPage(webDriver);
		FSTasksPage tasks = fsMenu.goToTasklistPageFS().editShowOnlyMyTask(false).editTaskByName("FS muss bestätigen")
				.confirmAdvisorRegistration(true).editTaskStatus(NBEstatus).clickSaveButton();
		tasks.editTaskByName("neuen Geschäftsvorfalls").approveSalesNBE(false).editTaskStatus(NBEstatus)
				.clickSaveButton();
	}

	// test 63
	// 1. Advisor registered with Adamas
	// 2. Approve sales new business event
	@Ignore
	@Category(DevelopmentTest.class)
	public void testRegisteredAndApproveNBE() throws InterruptedException {

		passCreateAndApproveNBE();

		FSMenubarPage fsMenu = new FSMenubarPage(webDriver);
		FSTasksPage task = fsMenu.goToTasklistPageFS().editShowOnlyMyTask(false).editTaskByName("FS muss bestätigen")
				.confirmAdvisorRegistration(true).editTaskStatus(NBEstatus).clickSaveButton()
				.editTaskByName("neuen Geschäftsvorfalls").approveSalesNBE(true).editTaskStatus(NBEstatus)
				.clickSaveButton();

		this.waitForWaitingScreenNotVisible();
		task.clickClearSearchButton();
		waitForElementVisible(By.xpath(".//*[contains(text(),'Bestätige den Beleg der Zahlung des Kunden')]"), 30);
		task.editTaskByName("Bestätige den Beleg der Zahlung des Kunden").editTaskStatus(NBEstatus).clickSaveButton();
		task.clickClearSearchButton();
	}

	/**
	 * To create a new sale quickly
	 * 
	 * @param client
	 *            client name
	 * @param membership
	 *            "1" for blue, "2" for green
	 * @param share
	 *            minimum share: blue:10, green:5
	 * @param date
	 *            subscription date (m/dd/yyyy)
	 * @param paybackoption
	 *            whether to turn on payback option
	 * @param numberOfPaybackOption
	 *            number of payback option, 1 - 3
	 * @param tarif
	 *            name of tarif, A / B
	 * @throws InterruptedException
	 */
	public void passAdamasSalesWizard(String client, String membership, String share, String date,
			boolean paybackoption, String numberOfPaybackOption, String tarif) throws InterruptedException {
		FSLoginPage main = new FSLoginPage(webDriver);

		String city = "NYC";

		SalesWizardPage salesWizard = main
				.loginAs(Settings.FINANCESALES_ADVISOR_USERNAME, Settings.FINANCESALES_ADVISOR_PASSWORD)
				.goToProductOverviewPageFS().goToAdamasPage().clickAddSalesButton().startAdamasSalesWizard(client);

		waitForElementVisible(By.id("gwt-debug-MiniSalesProcessEditTabOverviewView-titleListBox"), 30);

		salesWizard.clickNextPage().clickNextPage().clickNextPage();

		wait(5);

		if (isElementVisible(By.id("gwt-debug-OrbeonView-content"))) {
			webDriver.switchTo().frame(webDriver.findElement(By.id("gwt-debug-OrbeonView-content")));

		}

		salesWizard.editMembershipType(membership).editSalesLocation(city).editShares(share).editSalesDateManually(date)
				.editPaybackOption(paybackoption, numberOfPaybackOption).editLegalRequirementCheckbox(0)
				.editLegalRequirementCheckbox(1).editLegalRequirementCheckbox(2).editLegalRequirementCheckbox(3)
				.editLegalRequirementCheckbox(4).editTarif(tarif).clickNextPage().clickCompletePage().checkLogoutFS();
	}

	public NewBusinessEventEditPage clickEditNewBusinessEvent(String refNo, String clientName, String status) {

		waitForElementVisible(By.xpath(".//*[.='" + clientName + "']//ancestor::td//preceding-sibling::td[.='" + refNo
				+ "']//following-sibling::td[.='" + status
				+ "']//following-sibling::td//button[contains(@id ,'editButton')]"), 20);

		clickElement(By.xpath(".//*[.='" + clientName + "']//ancestor::td//preceding-sibling::td[.='" + refNo
				+ "']//following-sibling::td[.='" + status
				+ "']//following-sibling::td//button[contains(@id ,'editButton')]"));

		return new NewBusinessEventEditPage(webDriver);
	}

	public void checkProductAndPlatformAndCurrency(String product, String platform) {

		log("check Product, Platform And Currency: BEGIN");

		String currency = "EUR";

		Select productList = new Select(
				webDriver.findElement(By.id("gwt-debug-BusinessEventEditWidgetView-executionPlatformObjectListBox")));

		Select platformList = new Select(
				webDriver.findElement(By.id("gwt-debug-BusinessEventEditWidgetView-executionPlatformConfigListBox")));

		Select currencyList = new Select(
				webDriver.findElement(By.id("gwt-debug-BusinessEventEditWidgetView-accountCurrency")));

		assertEquals("assertion of product", productList.getFirstSelectedOption().getText(), product);
		assertEquals("assertion of platform", platformList.getFirstSelectedOption().getText(), platform);
		assertEquals("assertion of currency", currencyList.getFirstSelectedOption().getText(), currency);

		log("check Product, Platform And Currency: DONE");
	}

	public void checkLumpsumAndBusinessValue(String member, String share) throws InterruptedException {

		NewBusinessEventEditPage edit = new NewBusinessEventEditPage(webDriver);

		Integer numberOfShare = Integer.valueOf(share);

		Integer lumpsum = Integer
				.valueOf(webDriver.findElement(By.id("gwt-debug-BusinessEventEditWidgetView-lumpSumTextBox"))
						.getAttribute("originalvalue"));
		Integer price = null;
		Integer BVvalue = null;
		Double BVfactor = null;

		switch (member) {
		case "Blue":
			price = 1150;
			BVfactor = 0.8695652173913;
			break;
		case "Green":
			price = 1050;
			BVfactor = 0.95238095238095;
			break;
		default:
			log("Neither Blue nor Green is chosen!");
			price = 1050;
			BVfactor = 0.95238095238095;
			break;
		}

		assertTrue(lumpsum.equals(price * numberOfShare));

		edit.clickCalculateBvButton();

		assertTrue(getTextByLocator(By.id("gwt-debug-BusinessEventEditWidgetView-bvFormula"))
				.contains(String.valueOf(BVfactor)));

		BVvalue = Integer.valueOf(webDriver.findElement(By.id("gwt-debug-BusinessEventEditWidgetView-bvTextBox"))
				.getAttribute("originalvalue"));

		Integer BVbyCalculation = (int) Math.round(BVfactor * lumpsum);
		assertEquals(BVvalue, BVbyCalculation);

	}

	public void checkCommissionMatrix(String tarif) {

		NewBusinessEventEditPage edit = new NewBusinessEventEditPage(webDriver);

		edit.clickSplitAllocationButton();

		switch (tarif) {
		case "A":
			checkCommissionToGroups("Consultant", "10,00%");
			break;
		case "B":
			checkCommissionToGroups("Consultant", "8,00%");
			break;
		default:
			log("Neither Tarif A nor Tarif B is chosen");
		}

		checkCommissionToGroups("Regional Manager", "1,50%");
		checkCommissionToGroups("Country Manager", "0,50%");
		checkCommissionToGroups("Head Manager ", "1,00%");
		checkCommissionToGroups("Finance Sales", "1,00%");

	}

	public void checkCommissionToGroups(String role, String percent) {

		String commissionPercent = getTextByLocator(By.xpath(
				".//td[.='" + role + "']//following-sibling::td//div[contains(@id,'gwt-debug-Standard-allocation')]"));

		assertEquals(commissionPercent, percent);
	}

	public void passCreateAndApproveNBE() throws InterruptedException {

		String client = "Frenchadclient Frenchadclient";
		String status = "Entwurf";
		String member = "1"; // 1 for blue and 2 for green
		String share = "10";
		String date = "9/10/2016";
		boolean payback = false;
		String paybackOption = "";
		String tarif = "A";

		this.passAdamasSalesWizard(client, member, share, date, payback, paybackOption, tarif);

		FSLoginPage main = new FSLoginPage(webDriver);
		main.loginAs(Settings.FINANCESALES_ADMIN_USERNAME, Settings.FINANCESALES_ADMIN_PASSWORD)
				.goToNewBusinessEventPageFS();

		// Test case #51
		NewBusinessEventEditPage editNBE = this.clickEditNewBusinessEvent(client, "", status);

		waitForElementVisible(By.id("gwt-debug-BusinessEventEditWidgetView-clientNameLabel"), 60);

		if (getTextByLocator(By.id("gwt-debug-BusinessEventEditWidgetView-executionPlatformConfigListBox"))
				.contains("keine")) {
			editNBE.editPlatformSuffix("Blue - Tarif A");
		}
		editNBE.editAdmin("Chan, Birthe-Verena").clickCalculateBvButton().clickSplitAllocationButton()
				.clickCompleteNBEButton();
		clickYesButtonIfVisible();
	}

	@Ignore
	@Category(DevelopmentTest.class)
	public void testClientHistoryAfterNBEapproved() throws InterruptedException {

		String client = "Frenchadclient, Frenchadclient";

		this.testRegisteredAndApproveNBE();

		FSMenubarPage menubar = new FSMenubarPage(webDriver);

		AccountsPage accounts = menubar.goToClientOverviewPageFS().simpleSearchByString(client)
				.goToClientDetailPageByName(client).goToAccountsTabFS();

		this.waitForWaitingScreenNotVisible();

		waitForElementVisible(By.id("gwt-debug-InvestmentAccountsSectionView-assetAccountTable"), 30);

		if (isElementVisible(By.xpath(".//*[contains(text(),'Mehr anzeigen...')]"))) {
			clickElement(By.xpath("(.//*[contains(text(),'Mehr anzeigen...')])[1]"));
		}
		waitForElementVisible(
				By.xpath(".//table[@id = 'gwt-debug-InvestmentAccountsSectionView-assetAccountTable']//tr//div"), 30);
		int size = getSizeOfElements(
				By.xpath(".//table[@id = 'gwt-debug-InvestmentAccountsSectionView-assetAccountTable']//tr//div"));

		accounts.selectAccountByName(
				getTextByLocator(By.xpath("(.//table[@id='gwt-debug-InvestmentAccountsSectionView-assetAccountTable']"
						+ "//a[@id = 'gwt-debug-InvestmentAccountsSectionPresenter-accountName'])["
						+ String.valueOf(size) + "]")));

		assertTrue(pageContainsStr("Adamas"));
	}

	@Ignore
	@Category(DevelopmentTest.class)
	public void testClientHistoryAfterNBErejected() throws InterruptedException {

		String client = "Frenchadclient, Frenchadclient";

		this.testRegisteredAndApproveNBE();

		FSMenubarPage menubar = new FSMenubarPage(webDriver);

		AccountsPage accounts = menubar.goToClientOverviewPageFS().simpleSearchByString(client)
				.goToClientDetailPageByName(client).goToAccountsTabFS();

		this.waitForWaitingScreenNotVisible();
		accounts.showMoreInactiveAccounts();

		int size = getSizeOfElements(
				By.xpath(".//table[@id = 'gwt-debug-InvestmentAccountsSectionView-inactiveAccountsTable']//tr//div"));
		scrollToBottom();
		accounts.selectAccountByName(getTextByLocator(
				By.xpath("(.//table[@id = 'gwt-debug-InvestmentAccountsSectionView-inactiveAccountsTable']"
						+ "//a[@id = 'gwt-debug-InvestmentAccountsSectionPresenter-accountName'])["
						+ String.valueOf(size) + "]")));

		clickElement(By.xpath(".//button[.='Fortfahren']"));

		assertTrue(pageContainsStr("Inaktiv"));
	}
}
