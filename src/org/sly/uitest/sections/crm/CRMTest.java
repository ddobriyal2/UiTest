package org.sly.uitest.sections.crm;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.abstractpage.MenuBarPage;
import org.sly.uitest.pageobjects.admin.AdminEditPage;
import org.sly.uitest.pageobjects.alerts.AlertsPage;
import org.sly.uitest.pageobjects.clientsandaccounts.AccountsPage;
import org.sly.uitest.pageobjects.clientsandaccounts.ClientOverviewPage;
import org.sly.uitest.pageobjects.clientsandaccounts.DetailEditPage;
import org.sly.uitest.pageobjects.clientsandaccounts.DetailPage;
import org.sly.uitest.settings.Settings;

public class CRMTest extends AbstractTest {

	@Test
	public void testTheCorrectAdvisorShowUp() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		String type1 = "Consultant";
		String owner1 = "Selenium Consultant";
		String type2 = "Super User";
		String owner2 = "Seleniumtestadmin Seleniumtestadmin";
		String type3 = "Admin";
		String owner3 = "Seleniumtestclerical Seleniumtestclerical";
		String type4 = "Finance";
		String owner4 = "Seleniumtestfinance Seleniumtestfinance";

		main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToDocumentsPage().clickCreateBinderButton();

		clickElementAndWait3(By.xpath("(//button[@class='selection-dialog-link'])[2]"));

		this.selectFromDropdown(By.id("gwt-debug-SelectionUi-typeListBox"), type1);

		assertTrue(checkDropdownOptionsContainText(By.id("gwt-debug-PairedListBoxSelector-sourceList"), owner1));

		this.selectFromDropdown(By.id("gwt-debug-SelectionUi-typeListBox"), type2);

		assertTrue(checkDropdownOptionsContainText(By.id("gwt-debug-PairedListBoxSelector-sourceList"), owner2));

		this.selectFromDropdown(By.id("gwt-debug-SelectionUi-typeListBox"), type3);

		assertTrue(checkDropdownOptionsContainText(By.id("gwt-debug-PairedListBoxSelector-sourceList"), owner3));

		this.selectFromDropdown(By.id("gwt-debug-SelectionUi-typeListBox"), type4);

		assertTrue(checkDropdownOptionsContainText(By.id("gwt-debug-PairedListBoxSelector-sourceList"), owner4));

	}

	@Test
	public void checkClientDetailsPageShowUp() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage().simpleSearchByString("Selenium")
				.goToClientDetailPageByName("Investor, Selenium");

		assertTrue(this.pageContainsStr("Title:"));

		assertTrue(this.pageContainsStr("Mr."));

		assertTrue(this.pageContainsStr("Selenium"));

		assertTrue(this.pageContainsStr("Investor"));

		assertTrue(this.pageContainsStr("SeleniumInvestor"));

		assertTrue(this.pageContainsStr("USD"));

		assertTrue(this.pageContainsStr("Selenium Investor"));

	}

	@Test
	public void testCreateAndEditAndDeleteNewCorporateClient() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		String name = "c" + this.getRandName();

		DetailPage newClient = ((ClientOverviewPage) main.loginAs(Settings.USERNAME, Settings.PASSWORD)
				.goToAccountOverviewPage().clickNewClientAccountButton().editClientType("Corporate")
				.editClientCompanyName(name).editClientNationality("China").clickSaveButton_ClientAdditionalDetail())
						.goToClientOverviewPage().simpleSearchByString(name).goToClientDetailPageByName(name);

		assertTrue(this.pageContainsStr(name));

		assertTrue(!this.pageContainsStr("Business Reg. No.:"));

		// add business reg. no
		String busId = this.getRandName();

		DetailPage editClient = newClient.goToEditPageByField("Additional Details").editClientBusinessRegNo(busId)
				.clickSaveButton_ClientAdditionalDetail();

		assertTrue(this.pageContainsStr(busId));

		// delete client
		editClient.goToEditPageByField("Additional Details")
				.clickDeleteButtonByLocator(By.id("gwt-debug-UserDetailWidget-deleteBtn"));

	}

	@Test
	public void testAlertsCanBeSetForConsultant() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		String today = this.getCurrentTimeInFormat("dd-MMM-yyyy");
		String alertType = "Performance";

		main.loginAs(Settings.JARL_CONSULTANT_USERNAME, Settings.JARL_CONSULTANT_PASSWORD)
				.goToUserPreferencePage(ClientOverviewPage.class).editLoadAccountAndClients(true).clickSubmitButton();
		this.waitForWaitingScreenNotVisible();
		checkLogout();
		handleAlert();
		main = new LoginPage(webDriver);
		AlertsPage newAlert = main.loginAs(Settings.JARL_CONSULTANT_USERNAME, Settings.JARL_CONSULTANT_PASSWORD)
				.goToAccountOverviewPage().goToAccountHoldingsPageByName("Fund Account - 1218507")
				.goToAlertsPageFromManage().goToMyAlerts();

		newAlert.deleteAllMyAlerts().clickAddAlertButtonInMyAlert().editAlertType(alertType)
				.editPerformance("Account", "Gain", "15%", "1", "months").clickSaveButton().goToCompanyWide()
				.goToMyAlerts();

		assertTrue(pageContainsStr(alertType));

		// delete alert
		newAlert.deleteAlertByType(alertType).goToCompanyWide().goToMyAlerts();

		assertFalse(isElementDisplayed(By.xpath("//*[.='Performance']")));

	}

	@Test
	public void testCreateAndEditAndDeleteRelatedParty() throws Exception {

		// ensure the module toggle is on
		LoginPage main_module = new LoginPage(webDriver);

		String module = "MODULE_RELATED_PARTIES";
		// wait(2);
		MenuBarPage mbPage = main_module.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD);

		AdminEditPage adminEditPage = mbPage.goToAdminEditPage();
		adminEditPage.editAdminThisField(Settings.Advisor_Company_Module_Permission)
				.jumpByKeyAndLoad(Settings.SeleniumTest_Key).editModuleToggle(module, false, true).clickSubmitButton();

		this.checkLogout();

		handleAlert();

		// start testing

		String name = this.getRandName();

		String name2 = this.getRandName();
		// wait(Settings.WAIT_SECONDS);
		LoginPage main = new LoginPage(webDriver);
		// create related party
		main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage().simpleSearchByString("Selenium")
				.goToAccountHoldingsPageByName("Selenium Test").goToDetailsPage().createAccountRelatedParties()
				.editRelatedPartyType("Trust").editRelatedPartyRelationship("Other").editRelatedPartyFirstName(name)
				.clickSaveButtonByLocator(By.id("gwt-debug-RelatedPartyEditWidget-submitBtn"));

		assertTrue(this.pageContainsStr(name));

		assertTrue(this.getTextByLocator(By.xpath("//td[.='" + name + " ']/following-sibling::td[1]")).equals("Trust"));
		assertTrue(this.getTextByLocator(By.xpath("//td[.='" + name + " ']/following-sibling::td[2]")).equals("Other"));

		// edit related party
		DetailPage detail = new DetailPage(webDriver);

		detail.editAccountRelatedPartiesByName(name).editRelatedPartyFirstName(name2).editRelatedPartyType("Individual")
				.editRelatedPartyRelationship("Life Assured")
				.clickSaveButtonByLocator(By.id("gwt-debug-RelatedPartyEditWidget-submitBtn"));

		assertTrue(this.pageContainsStr(name2));
		assertTrue(this.getTextByLocator(By.xpath("//td[.='" + name2 + " ']/following-sibling::td[1]"))
				.equals("Individual"));
		assertTrue(this.getTextByLocator(By.xpath("//td[.='" + name2 + " ']/following-sibling::td[2]"))
				.equals("Life Assured"));

		// delete related party
		detail.deleteAccountRelatedPartiesByName(name2);

		assertTrue(!this.pageContainsStr(name));
	}

	@Test
	public void testLinkingModelPortfolioWithNoValue() throws Exception {

		log("Test Linking Model Portfolio with no Value: Begin");

		LoginPage main = new LoginPage(webDriver);

		DetailPage detail = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage()
				.simpleSearchByString("AXA Pulsar AccountTest")
				.goToAccountDefaultPageByName("AXA Pulsar AccountTest", DetailPage.class);

		detail.goToEditPageByField("Model Portfolio");

		assertTrue(pageContainsStr("Account that has no value cannot be link to a model portfolio"));

		clickElement(By.id("gwt-debug-CustomDialog-okButton"));

		log("Test Linking Model Portfolio with no Value: Done");
	}

	@Test
	public void testDuplicateBrokerAccountID() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		String accountName1 = "testDuplicate1" + getRandName();
		String accountName2 = "testDuplicate2" + getRandName();
		String type = "Insurance";
		String platform = "Generali International";
		String product = "Generali Choice";
		String dataFeed = "TestDuplicate" + this.getRandName();
		String updateSource = "Automatic";
		String advisor = "Selenium Consultant";
		String accountType = "REAL CLIENT ACCOUNT";

		// first account
		// wait(Settings.WAIT_SECONDS);
		AccountsPage account = ((DetailPage) main.loginAs(Settings.USERNAME, Settings.PASSWORD)
				.goToAccountOverviewPage().simpleSearchByString("John").goToClientDetailPageByName("Doe, John")
				.goToAccountsTab().createNewAccount_NonNBE(accountType).editAccountName(accountName1)
				.editAccountType(type).editAccountProductProvider(platform).editAccountProduct(product)
				.editAccountDataFeedAccountId(dataFeed).editAccountUpdateSource(updateSource).editAccountCurrency("AUD")
				.editAddMainAdvisorByNameInvDet(advisor).clickSaveButton_AccountDetail())
						.goToMainHolderDetailPageFromAccount().goToAccountsTab();

		assertTrue(pageContainsStr(accountName1));

		// second account
		account.createNewAccount_NonNBE(accountType).editAccountName(accountName2).editAccountType(type)
				.editAccountProductProvider(platform).editAccountProduct(product).editAccountDataFeedAccountId(dataFeed)
				.editAccountUpdateSource(updateSource).editAccountCurrency("AUD")
				.editAddMainAdvisorByNameInvDet(advisor).clickSaveButton_AccountDetail();

		assertTrue(pageContainsStr("This data-feed account ID already exists in system"));

		clickOkButtonIfVisible();

		DetailEditPage edit = new DetailEditPage(webDriver, AccountsPage.class);

		// delete the first one
		((DetailPage) ((AccountsPage) edit.clickCancelButton_AccountDetail()).showMoreAccounts()
				.selectAccountByName(accountName1).goToEditPageByField("Details").clickDeleteButton_AccountDetail()

				// create the second account again
				.simpleSearchByString("John").goToClientDetailPageByName("Doe, John").goToAccountsTab()
				.createNewAccount_NonNBE(accountType).editAccountName(accountName2).editAccountType(type)
				.editAccountProductProvider(platform).editAccountProduct(product).editAccountDataFeedAccountId(dataFeed)
				.editAccountUpdateSource(updateSource).editAccountCurrency("AUD")
				.editAddMainAdvisorByNameInvDet(advisor).clickSaveButton_AccountDetail())
						.goToMainHolderDetailPageFromAccount().goToAccountsTab();

		assertTrue(pageContainsStr(accountName2));

		// delete the second account
		account.showMoreAccounts().selectAccountByName(accountName2).goToEditPageByField("Details")
				.clickDeleteButton_AccountDetail();
	}
}
