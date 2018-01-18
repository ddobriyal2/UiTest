package org.sly.uitest.sections.crm;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.abstractpage.MenuBarPage;
import org.sly.uitest.pageobjects.admin.AdminEditPage;
import org.sly.uitest.pageobjects.admin.AdminMaintenancePage;
import org.sly.uitest.pageobjects.alerts.AlertsPage;
import org.sly.uitest.settings.Settings;

public class AlertTest extends AbstractTest {

	@Test
	public void testAlertOverviewLoaded() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAlertsPageFromManage().goToMyAlerts().goToCompanyWide();
	}

	@Test
	public void testAlertOverviewLoadedByConsultant() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		main.loginAs("JarlConsultant", "JarlConsultant").goToUserAlertPage().goToMyAlerts().goToCompanyWide();

		// check non-advisor admin cannot edit company-wide alerts
		assertTrue(!isElementDisplayed(By.id("gwt-debug-UserAlertDefinitionListWidget-deleteButton")));
	}

	@Test
	public void testCreateAlerts() throws Exception {

		LoginPage main = new LoginPage(webDriver);
		AlertsPage alert1 = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToUserAlertPage();
		// delete All
		alert1.deleteAllCompanyAlerts();

		checkLogout();
		handleAlert();

		String today = this.getCurrentTimeInFormat("dd-MMM-yyyy");

		LoginPage main2 = new LoginPage(webDriver);
		AlertsPage alert = main2.loginAs(Settings.CONSULTANT_USERNAME, Settings.CONSULTANT_PASSWORD).goToUserAlertPage()
				.goToMyAlerts();

		assertTrue(pageContainsStr("Company-wide"));

		String alertType;

		alert.deleteAllMyAlerts();

		// 1. create account maturity alert
		alertType = "Account Maturity";
		alert.clickAddAlertButtonInMyAlert().editAlertType(alertType)
				.editAccountMaturity("1", "years", "All Asset Accounts").clickSaveButton();

		assertTrue(pageContainsStr(alertType));

		alert.deleteAllMyAlerts();

		// 2. create overdraft alert
		alertType = "Account Overdraft";
		alert.clickAddAlertButtonInMyAlert().editAlertType(alertType).editAccountOverdraft("All").clickSaveButton();

		assertTrue(pageContainsStr(alertType));

		alert.deleteAllMyAlerts();

		// 3. create client birthday alert
		alertType = "Client Birthday";
		alert.clickAddAlertButtonInMyAlert().editAlertType(alertType)
				.editClientBirthday("5", "days", "All Non-Asset Accounts").clickSaveButton();

		assertTrue(pageContainsStr(alertType));

		alert.deleteAllMyAlerts();

		// 4.create overdue contribution alert
		alertType = "Overdue Contribution";
		alert.clickAddAlertButtonInMyAlert().editAlertType(alertType).editOverduePremium("10", "All").clickSaveButton();

		assertTrue(pageContainsStr(alertType));

		alert.deleteAllMyAlerts();

		// 5.create payment expiration alert
		alertType = "Payment Expiration";
		alert.clickAddAlertButtonInMyAlert().editAlertType(alertType).editPaymentExpiration("7", "months", "All")
				.clickSaveButton();

		assertTrue(pageContainsStr(alertType));

		alert.deleteAllMyAlerts();

		// 6.create account performance alert
		alertType = "Performance";
		alert.clickAddAlertButtonInMyAlert().editAlertType(alertType)
				.editPerformance("Account", "Gain", "15%", "1", "months").clickSaveButton();

		assertTrue(pageContainsStr(alertType));

		alert.deleteAllMyAlerts();

		// 7.create investment performance alert
		alertType = "Performance";
		alert.clickAddAlertButtonInMyAlert().editAlertType(alertType)
				.editPerformance("Investment", "Drop", "20%", "15", "days").clickSaveButton();

		assertTrue(pageContainsStr(alertType));

		alert.deleteAllMyAlerts();

		// 8.create Contribution renewal due alert
		alertType = "Contribution Renewal Due";
		alert.clickAddAlertButtonInMyAlert().editAlertType(alertType).editPremiumRenewalDue("17", "All Asset Accounts")
				.clickSaveButton();

		assertTrue(pageContainsStr(alertType));

		alert.deleteAllMyAlerts();

		// 9.create deviation portfolio allocation alert
		alertType = "Deviation Portfolio Allocation";
		alert.clickAddAlertButtonInMyAlert().editAlertType(alertType)
				.editDeviationPortfolioAllocation("10%", "All Non-Asset Accounts").clickSaveButton();

		assertTrue(pageContainsStr(alertType));

		alert.deleteAllMyAlerts();

	}

	@Test
	public void testDuplicateAlertsNotAllowed() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		AlertsPage alert = main.loginAs(Settings.CONSULTANT_USERNAME, Settings.CONSULTANT_PASSWORD).goToUserAlertPage()
				.goToMyAlerts();

		// create the first contribution renewal alert
		String alertType = "Contribution Renewal Due";

		alert.clickAddAlertButtonInMyAlert().editAlertType(alertType).editPremiumRenewalDue("17", "All Asset Accounts")
				.clickSaveButton();

		// create the second contribution renewal alert
		alert.clickAddAlertButtonInMyAlert().editAlertType(alertType).editPremiumRenewalDue("17", "All Asset Accounts")
				.clickSaveButton();

		assertTrue(pageContainsStr(
				"An alert with the same settings already exists. This alert definition will not be saved."));

		clickOkButtonIfVisible();

		alert.clickCancelButton();

		// verify SLYAWS-7478
		alert.deleteAllMyAlerts().clickAddAlertButtonInMyAlert().editAlertType(alertType)
				.editPremiumRenewalDue("1", "All Asset Accounts").clickSaveButton();

		// assertFalse(pageContainsStr("An alert with the same settings already
		// exists. This alert definition will not be saved."));

	}

	@Test
	public void testAlertTypeModuleToggle() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		String module1 = "MODULE_USER_ALERT_DISABLE_TYPE_ACCOUNT_MATURITY";
		String module2 = "MODULE_USER_ALERT_DISABLE_TYPE_CLIENT_BIRTHDAY";
		String module3 = "MODULE_USER_ALERT_DISABLE_TYPE_OVERDUE_PREMIUM";
		String module4 = "MODULE_USER_ALERT_DISABLE_TYPE_PAYMENT_EXPIRATION";
		String module5 = "MODULE_USER_ALERT_DISABLE_TYPE_PORTFOLIO_BOND_OVERDRAFT";
		String module6 = "MODULE_USER_ALERT_DISABLE_TYPE_REBALANCE_PORTFOLIO";
		String module7 = "MODULE_USER_ALERT_DISABLE_TYPE_RENEWAL_DUE";
		String module8 = "MODULE_ALLOCATION_RULE";
		String module9 = "MODULE_USER_ALERT_DISABLE_TYPE_MODELPORTFOLIO_MANUALLY_REBALANCED";
		String type1 = "Investment Guideline";
		String type2 = "Performance";

		// turn on the module toggle
		MenuBarPage mbPage = main.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD);

		AdminEditPage adminEditPage = mbPage.goToAdminEditPage();

		adminEditPage.editAdminThisField(Settings.Advisor_Company_Module_Permission);
		adminEditPage.jumpByKeyAndLoad(Settings.InfinityFinancialSolution_Key).editModuleToggle(module1, false, true)
				.editModuleToggle(module2, false, true).editModuleToggle(module3, false, true)
				.editModuleToggle(module4, false, true).editModuleToggle(module5, false, true)
				.editModuleToggle(module6, false, true).editModuleToggle(module7, false, true)
				.editModuleToggle(module8, false, true).editModuleToggle(module9, false, true).clickSubmitButton();
		this.checkLogout();
		handleAlert();

		// check login as InfinityAdmin

		LoginPage main2 = new LoginPage(webDriver);

		AlertsPage alert = main2.loginAs(Settings.CRM_USERNAME, Settings.CRM_PASSWORD).goToAlertsPageFromManage()
				.goToCompanyWide().clickAddAlertButtonInCompanyWide();

		assertTrue(getTextByLocator(
				By.xpath(".//*[@id='gwt-debug-EditUserAlertDefinitionView-alertTypeListBox']/option[2]"))
						.equals(type1));

		assertTrue(getTextByLocator(
				By.xpath(".//*[@id='gwt-debug-EditUserAlertDefinitionView-alertTypeListBox']/option[3]"))
						.equals(type2));

		assertFalse(isElementDisplayed(
				By.xpath(".//*[@id='gwt-debug-EditUserAlertDefinitionView-alertTypeListBox']/option[4]")));

		alert.clickCancelButton();
		this.waitForWaitingScreenNotVisible();
		this.checkLogout();
		handleAlert();

		// turn off the module toggle
		LoginPage main3 = new LoginPage(webDriver);
		MenuBarPage mbPage1 = main3.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD);

		AdminEditPage adminEditPage1 = mbPage1.goToAdminEditPage();
		adminEditPage1.editAdminThisField(Settings.Advisor_Company_Module_Permission)
				.jumpByKeyAndLoad(Settings.InfinityFinancialSolution_Key).editModuleToggle(module1, false, false)
				.editModuleToggle(module2, false, false).editModuleToggle(module3, false, false)
				.editModuleToggle(module4, false, false).editModuleToggle(module5, false, false)
				.editModuleToggle(module6, false, false).editModuleToggle(module7, false, false)
				.editModuleToggle(module9, false, false).clickSubmitButton();

		checkLogout();
		handleAlert();

		// check login as InfinityAdmin

		LoginPage main4 = new LoginPage(webDriver);

		String type3 = "Account Maturity";
		String type4 = "Account Overdraft";
		String type5 = "Client Birthday";
		String type6 = "Deviation Portfolio Allocation";
		String type7 = "Overdue Contribution";
		String type8 = "Payment Expiration";
		String type9 = "Contribution Renewal Due";
		String type10 = "Model Portfolio Rebalance";
		main4.loginAs(Settings.CRM_USERNAME, Settings.CRM_PASSWORD).goToAlertsPageFromManage().goToCompanyWide()
				.clickAddAlertButtonInCompanyWide().editAlertType(type1).editAlertType(type2).editAlertType(type3)
				.editAlertType(type4).editAlertType(type5).editAlertType(type6).editAlertType(type7)
				.editAlertType(type8).editAlertType(type9).editAlertType(type10);

	}

	@Test
	public void testCompanyWideModuleToggle() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		String module = "MODULE_USER_ALERT_DISABLE_COMPANY_WIDE_ALERT";

		String companyWide = "COMPANY-WIDE";
		String myAlerts = "MY ALERTS";

		// turn on the module toggle
		// wait(2);
		MenuBarPage mbPage = main.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD);

		AdminEditPage adminEditPage = mbPage.goToAdminEditPage();

		adminEditPage.editAdminThisField(Settings.Advisor_Company_Module_Permission);
		// wait(5);
		adminEditPage.jumpByKeyAndLoad(Settings.InfinityFinancialSolution_Key).editModuleToggle(module, false, true)
				.clickSubmitButton();

		this.checkLogout();
		// wait(2);
		handleAlert();

		// check login as InfinityAdmin
		LoginPage main2 = new LoginPage(webDriver);

		MenuBarPage mbPage3 = main2.loginAs(Settings.CRM_USERNAME, Settings.CRM_PASSWORD);

		mbPage3.goToAlertsPageFromManage();

		assertFalse(getTextByLocator(
				By.xpath(".//*[@id='gwt-debug-UserAlertOverviewView-tabLayoutPanel']/div[2]/div/div[1]/div/div"))
						.equals(companyWide));

		assertTrue(getTextByLocator(
				By.xpath(".//*[@id='gwt-debug-UserAlertOverviewView-tabLayoutPanel']/div[2]/div/div[1]/div/div"))
						.equals(myAlerts));

		// wait(Settings.WAIT_SECONDS);
		this.checkLogout();
		handleAlert();

		// turn off the module toggle
		LoginPage main3 = new LoginPage(webDriver);
		// wait(2);
		MenuBarPage mbPage1 = main3.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD);

		mbPage1.goToAdminEditPage().editAdminThisField(Settings.Advisor_Company_Module_Permission)
				.jumpByKeyAndLoad(Settings.InfinityFinancialSolution_Key).editModuleToggle(module, false, false)
				.clickSubmitButton();

		this.checkLogout();
		// wait(2);
		handleAlert();

		// check login as InfinityAdmin

		LoginPage main4 = new LoginPage(webDriver);
		main4.loginAs(Settings.CRM_USERNAME, Settings.CRM_PASSWORD).goToAlertsPageFromManage();

		assertTrue(getTextByLocator(
				By.xpath(".//*[@id='gwt-debug-UserAlertOverviewView-tabLayoutPanel']/div[2]/div/div[1]/div/div"))
						.equals(companyWide));

	}

	@Test
	public void testNonAssetOptionModuleToggle() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		String module = "MODULE_USER_ALERT_DISABLE_NON_ASSET_ACCOUNTS_OPTION";

		String type = "Performance";
		// wait(2);
		MenuBarPage mbPage = main.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD);
		// wait(5);

		AdminEditPage adminEditPage = mbPage.goToAdminEditPage();

		adminEditPage.editAdminThisField(Settings.Advisor_Company_Module_Permission);
		// wait(5);
		adminEditPage.jumpByKeyAndLoad(Settings.InfinityFinancialSolution_Key).editModuleToggle(module, false, true)
				.clickSubmitButton();

		// wait(2);
		this.checkLogout();
		// wait(2);
		handleAlert();

		// check login as InfinityAdmin

		LoginPage main2 = new LoginPage(webDriver);

		MenuBarPage mbPage3 = main2.loginAs(Settings.CRM_USERNAME, Settings.CRM_PASSWORD);

		mbPage3.goToAlertsPageFromManage().goToCompanyWide().clickAddAlertButtonInCompanyWide().editAlertType(type);

		assertFalse(isElementVisible(By.id("gwt-debug-UserAlertAppliesToWidget-appliesToNonAssetAccountRadioButton")));
		if (isElementVisible(By.id("gwt-debug-EditUserAlertDefinitionView-cancelBtn"))
				|| isElementPresent(By.id("gwt-debug-EditUserAlertDefinitionView-cancelBtn"))) {

			clickElement(By.id("gwt-debug-EditUserAlertDefinitionView-cancelBtn"));
		}
		if (isElementVisible(By.id("gwt-debug-EditUserAlertDefinitionView-cancelBtn"))
				|| isElementPresent(By.id("gwt-debug-EditUserAlertDefinitionView-cancelBtn"))) {

			clickElement(By.id("gwt-debug-EditUserAlertDefinitionView-cancelBtn"));
		}

		// wait(Settings.WAIT_SECONDS * 2);
		this.checkLogout();
		handleAlert();

		// turn off the module toggle
		LoginPage main3 = new LoginPage(webDriver);

		// wait(2);
		MenuBarPage mbPage1 = main3.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD);
		// wait(5);

		AdminEditPage adminEditPage1 = mbPage1.goToAdminEditPage();

		adminEditPage1.editAdminThisField(Settings.Advisor_Company_Module_Permission)
				.jumpByKeyAndLoad(Settings.InfinityFinancialSolution_Key).editModuleToggle(module, false, false)
				.clickSubmitButton();

		// wait(2);
		this.checkLogout();
		// wait(2);
		handleAlert();

		// check login as InfinityAdmin
		LoginPage main4 = new LoginPage(webDriver);
		main4.loginAs(Settings.CRM_USERNAME, Settings.CRM_PASSWORD).goToAlertsPageFromManage().goToCompanyWide()
				.clickAddAlertButtonInCompanyWide().editAlertType(type);

		waitForElementVisible(By.id("gwt-debug-UserAlertAppliesToWidget-appliesToNonAssetAccountRadioButton"), 10);
		assertTrue(isElementVisible(By.id("gwt-debug-UserAlertAppliesToWidget-appliesToNonAssetAccountRadioButton")));

		clickElement(By.id("gwt-debug-EditUserAlertDefinitionView-cancelBtn"));

	}

	@Test
	public void testTriggeredAlertList() throws InterruptedException {
		LoginPage main = new LoginPage(webDriver);
		main.loginAs(Settings.CRM_USERNAME, Settings.CRM_PASSWORD).goToAlertsPageFromManage();
		int size = getSizeOfElements(By.id("gwt-debug-TriggeredUserAlertListWidget-expandCollapseButton"));
		for (int i = 1; i <= size; i++) {
			clickElement(By.xpath(
					"(.//button[@id='gwt-debug-TriggeredUserAlertListWidget-expandCollapseButton'])[" + i + "]"));
			waitForElementVisible(By.id("gwt-debug-UserAlertTriggerListTable-triggerTablePanel"), 10);
			assertTrue(isElementVisible(By.id("gwt-debug-UserAlertTriggerListTable-triggerTablePanel")));
		}
	}

	@Test
	public void testConfirmTriggeredAlerts() throws InterruptedException {
		String alertKey = "461";

		LoginPage main = new LoginPage(webDriver);
		AlertsPage alert = main.loginAs(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD)
				.goToAlertsPageFromManage();
		// if alert "If the credit card is expiring within 6 months" exists,
		// delete it.
		if (isElementVisible(By.xpath(
				"id('gwt-debug-TriggeredUserAlertListWidget-tablePanel')//td[.=\"If a client's birthday is within this period: 6 months \"]"))) {
			clickElement(By.xpath(
					"id('gwt-debug-TriggeredUserAlertListWidget-tablePanel')//td[.=\"If a client's birthday is within this period: 6 months \"]/following-sibling::td/button[@id='gwt-debug-TriggeredUserAlertListWidget-removeButton']"));
			clickYesButtonIfVisible();
		}

		checkLogout();
		handleAlert();

		LoginPage main2 = new LoginPage(webDriver);
		AdminMaintenancePage adminMain = main2.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD)
				.goToAdminMaintenancePage();
		adminMain.editAlertKeys(alertKey).clickProcessSingleUserAlertBtn();

		checkLogout();
		handleAlert();

		LoginPage main3 = new LoginPage(webDriver);
		AlertsPage alert2 = main3.loginAs(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD)
				.goToAlertsPageFromManage();
		assertTrue(isElementVisible(By.xpath(
				"id('gwt-debug-TriggeredUserAlertListWidget-tablePanel')//td[.=\"If a client's birthday is within this period: 6 months \"]")));

	}
}
