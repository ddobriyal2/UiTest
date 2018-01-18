package org.sly.uitest.sections.crm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.alerts.AlertsPage;
import org.sly.uitest.pageobjects.clientsandaccounts.DetailPage;
import org.sly.uitest.pageobjects.clientsandaccounts.HoldingsPage;
import org.sly.uitest.settings.Settings;

/**
 * This is for alerts triggered by link/unlink account with Model Portfolio
 * 
 * Details:SLYAWS-10246 [MANAGER COMPANY] To enable the Alert function
 * 
 * 
 * @author Benny Leung
 * @date : 23 Jun, 2017
 * @company Prive Financial
 */
public class AlertForModelPortfolioLinkChangeTest extends AbstractTest {
	String linkAlertType = "Account Linked to Model Portfolio";
	String unlinkAlertType = "Account Unlinked from Model Portfolio";

	@Test
	public void testModuleToggleForModelPortfolioLinkChange() throws InterruptedException {
		String account = "LyonsPriveManager";
		String lyonsKey = "566";
		String module = "MODULE_USER_ALERT_DISABLE_TYPE_ACCOUNT_MODELPORTFOLIO_LINK_CHANGE";
		String alertType1 = linkAlertType;
		String alertType2 = unlinkAlertType;
		LoginPage main = new LoginPage(webDriver);
		main.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD).goToAdminEditPage()
				.editAdminThisField(Settings.Manager_Module_Permission).jumpByKeyAndLoad(lyonsKey)
				.editSingleModuleToggle(module, true).clickSubmitButton();

		checkLogout();
		handleAlert();

		LoginPage main2 = new LoginPage(webDriver);
		AlertsPage alert = main2.loginAs(account, account).goToAlertsPageFromManage().goToCompanyWide()
				.clickAddAlertButtonInCompanyWide();

		Select select = new Select(
				webDriver.findElement(By.id("gwt-debug-EditUserAlertDefinitionView-alertTypeListBox")));

		assertFalse(isElementPresent(By.xpath(
				".//*[@id='gwt-debug-EditUserAlertDefinitionView-alertTypeListBox']//option[.='" + alertType1 + "']")));
		assertFalse(isElementPresent(By.xpath(
				".//*[@id='gwt-debug-EditUserAlertDefinitionView-alertTypeListBox']//option[.='" + alertType2 + "']")));
		alert.clickCancelButton();
		checkLogout();
		handleAlert();

		LoginPage main3 = new LoginPage(webDriver);
		main3.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD).goToAdminEditPage()
				.editAdminThisField(Settings.Manager_Module_Permission).jumpByKeyAndLoad(lyonsKey)
				.editSingleModuleToggle(module, false).clickSubmitButton();

		checkLogout();
		handleAlert();

		LoginPage main4 = new LoginPage(webDriver);
		main4.loginAs(account, account).goToAlertsPageFromManage().goToCompanyWide().clickAddAlertButtonInCompanyWide();

		Select select2 = new Select(
				webDriver.findElement(By.id("gwt-debug-EditUserAlertDefinitionView-alertTypeListBox")));

		assertTrue(select2.getOptions()
				.contains(webDriver.findElement(
						By.xpath(".//*[@id='gwt-debug-EditUserAlertDefinitionView-alertTypeListBox']//option[.='"
								+ alertType1 + "']"))));
		assertTrue(select2.getOptions()
				.contains(webDriver.findElement(
						By.xpath(".//*[@id='gwt-debug-EditUserAlertDefinitionView-alertTypeListBox']//option[.='"
								+ alertType2 + "']"))));

	}

	@Test
	public void testManagerCompanyAccessToModelPortfolioRebalanceAlert() throws InterruptedException {
		LoginPage main = new LoginPage(webDriver);
		String account = "LyonsPriveManager";
		String alertType1 = linkAlertType;
		String alertType2 = unlinkAlertType;
		AlertsPage alert = main.loginAs(account, account).goToAlertsPageFromManage().goToCompanyWide()
				.clickAddAlertButtonInCompanyWide();

		Select select = new Select(
				webDriver.findElement(By.id("gwt-debug-EditUserAlertDefinitionView-alertTypeListBox")));

		assertTrue(select.getOptions()
				.contains(webDriver.findElement(
						By.xpath(".//*[@id='gwt-debug-EditUserAlertDefinitionView-alertTypeListBox']//option[.='"
								+ alertType1 + "']"))));
		assertTrue(select.getOptions()
				.contains(webDriver.findElement(
						By.xpath(".//*[@id='gwt-debug-EditUserAlertDefinitionView-alertTypeListBox']//option[.='"
								+ alertType2 + "']"))));
	}

	@Test
	public void testAdvisorCompanyCannotAccessToModelPortfolioRebalanceAlert() throws InterruptedException {
		String alertType1 = linkAlertType;
		String alertType2 = unlinkAlertType;
		LoginPage main = new LoginPage(webDriver);
		AlertsPage alert = main.loginAs(Settings.CRM_USERNAME, Settings.CRM_PASSWORD).goToAlertsPageFromManage()
				.goToCompanyWide().clickAddAlertButtonInCompanyWide();

		Select select = new Select(
				webDriver.findElement(By.id("gwt-debug-EditUserAlertDefinitionView-alertTypeListBox")));

		assertFalse(isElementPresent(By.xpath(
				".//*[@id='gwt-debug-EditUserAlertDefinitionView-alertTypeListBox']//option[.='" + alertType1 + "']")));
		assertFalse(isElementPresent(By.xpath(
				".//*[@id='gwt-debug-EditUserAlertDefinitionView-alertTypeListBox']//option[.='" + alertType2 + "']")));
	}

	@Test
	public void testCreateAndDeleteModelPortfolioLinkAlert() throws Exception {
		String account = "JarlManager";
		String alertType = linkAlertType;
		String[] levels = { "company", "myAlert" };
		int size;
		LoginPage main = new LoginPage(webDriver);

		AlertsPage alerts = main.loginAs(account, account).goToAlertsPageFromManage().goToMyAlerts()
				.deleteAllMyAlerts();

		for (String level : levels) {
			this.createLinkModelPortfolio(level);
			assertTrue(this.isAlertDefinitionVisible(level, alertType));

		}
		alerts.goToMyAlerts().deleteAllMyAlerts();
	}

	@Test
	public void testCreateAndDeleteModelPortfolioUnlinkAlert() throws Exception {
		String account = "JarlManager";
		String alertType = unlinkAlertType;
		String[] levels = { "company", "myAlert" };
		int size;
		LoginPage main = new LoginPage(webDriver);

		AlertsPage alerts = main.loginAs(account, account).goToAlertsPageFromManage().goToMyAlerts()
				.deleteAllMyAlerts();

		for (String level : levels) {
			this.createUnlinkModelPortfolio(level);

			assertTrue(this.isAlertDefinitionVisible(level, alertType));
		}
		alerts.goToMyAlerts().deleteAllMyAlerts();
	}

	@Test
	public void testTriggerAccountLinkAndUnlinkModelPortfolioAlertFromAccount() throws Exception {
		String account = "JarlManager";
		String investorAccount = "Art Collection";
		String modelPortfolioName = "123";
		String linkModelPortfolioAlert = linkAlertType;
		String unlinkModelPortfolioAlert = unlinkAlertType;

		LoginPage main = new LoginPage(webDriver);
		AlertsPage alerts = main.loginAs(account, account).goToAlertsPageFromManage().goToMyAlerts()
				.deleteAllMyAlerts();
		this.createLinkModelPortfolio("company");
		this.createUnlinkModelPortfolio("company");
		this.checkLogout();
		this.handleAlert();

		LoginPage main2 = new LoginPage(webDriver);
		main2.loginAs(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD).goToAccountOverviewPage()
				.simpleSearchByString(investorAccount).goToAccountHoldingsPageByName(investorAccount).goToDetailsPage()
				.linkModelPortfolioForAccount(modelPortfolioName, true);

		this.checkLogout();
		this.handleAlert();

		LoginPage main3 = new LoginPage(webDriver);
		AlertsPage alerts2 = main3.loginAs(account, account).goToAlertsPageFromManage().goToCompanyWide()
				.expandTriggeredAlerts("company", linkModelPortfolioAlert);
		assertEquals(this.getSizeOfTriggeredAlert("company", linkModelPortfolioAlert), 1);

		alerts2.clickAdvancedSearchButtonForTriggeredAlertsInCompanyWide()
				.searchByAlertTypeInCompanyWide(linkModelPortfolioAlert).clickSearchButton();
		alerts2.expandTriggeredAlerts("company", linkModelPortfolioAlert);
		assertEquals(this.getSizeOfTriggeredAlert("company", linkModelPortfolioAlert), 1);

		alerts2.goToMyAlerts().expandTriggeredAlerts("myAlert", linkModelPortfolioAlert);
		assertEquals(this.getSizeOfTriggeredAlert("myAlert", linkModelPortfolioAlert), 1);

		alerts2.clickAdvancedSearchButtonForTriggeredAlertsInMyAlerts()
				.searchByAlertTypeInMyAlerts(linkModelPortfolioAlert).clickSearchButton();
		alerts2.expandTriggeredAlerts("myAlert", linkModelPortfolioAlert);
		assertEquals(this.getSizeOfTriggeredAlert("myAlert", linkModelPortfolioAlert), 1);

		this.checkLogout();
		this.handleAlert();

		LoginPage main4 = new LoginPage(webDriver);
		main4.loginAs(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD).goToAccountOverviewPage()
				.simpleSearchByString(investorAccount).goToAccountHoldingsPageByName(investorAccount).goToDetailsPage()
				.unlinkModelPortfolioForAccount();

		this.checkLogout();
		this.handleAlert();

		LoginPage main5 = new LoginPage(webDriver);
		AlertsPage alerts3 = main5.loginAs(account, account).goToAlertsPageFromManage().goToCompanyWide()
				.expandTriggeredAlerts("company", unlinkModelPortfolioAlert);
		assertEquals(this.getSizeOfTriggeredAlert("company", unlinkModelPortfolioAlert), 1);

		alerts3.clickAdvancedSearchButtonForTriggeredAlertsInCompanyWide()
				.searchByAlertTypeInCompanyWide(unlinkModelPortfolioAlert).clickSearchButton();
		alerts3.expandTriggeredAlerts("company", unlinkModelPortfolioAlert);
		assertEquals(this.getSizeOfTriggeredAlert("company", unlinkModelPortfolioAlert), 1);

		alerts3.goToMyAlerts().expandTriggeredAlerts("myAlert", unlinkModelPortfolioAlert);
		assertEquals(this.getSizeOfTriggeredAlert("myAlert", unlinkModelPortfolioAlert), 1);

		alerts3.clickAdvancedSearchButtonForTriggeredAlertsInMyAlerts()
				.searchByAlertTypeInMyAlerts(unlinkModelPortfolioAlert).clickSearchButton();
		alerts3.expandTriggeredAlerts("myAlert", unlinkModelPortfolioAlert);
		assertEquals(this.getSizeOfTriggeredAlert("myAlert", unlinkModelPortfolioAlert), 1);

		alerts3.goToMyAlerts().deleteAllMyAlerts();
	}

	@Test
	public void testTriggerAccountLinkModelPortfolioFromModelPortfolio() throws Exception {
		String account = "JarlManager";
		String modelPortfolio = "123";
		String accountToLink = "Art Collection (Brian Huie)";
		String investorAccount = "Art Collection";
		String level = "company";
		String linkModelPortfolioAlert = linkAlertType;

		LoginPage main = new LoginPage(webDriver);
		AlertsPage alerts = main.loginAs(account, account).goToAlertsPageFromManage().goToMyAlerts()
				.deleteAllMyAlerts();

		this.createLinkModelPortfolio(level);

		this.checkLogout();
		this.handleAlert();

		LoginPage main2 = new LoginPage(webDriver);
		HoldingsPage holdings = main2.loginAs(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD)
				.goToAccountOverviewPage().simpleSearchByString(investorAccount)
				.goToAccountHoldingsPageByName(investorAccount);

		if (pageContainsStr("Linked to:")) {
			holdings.goToDetailsPage().unlinkModelPortfolioForAccount();
		}

		holdings.goToModelPortfoliosPage().clickAddButtonByName(modelPortfolio)
				.addModelPortfolioToAccount(accountToLink);

		this.checkLogout();
		this.handleAlert();

		AlertsPage alerts2 = main.loginAs(account, account).goToAlertsPageFromManage().goToCompanyWide()
				.expandTriggeredAlerts("company", linkModelPortfolioAlert);

		assertEquals(this.getSizeOfTriggeredAlert("company", linkModelPortfolioAlert), 1);

		alerts2.goToMyAlerts().expandTriggeredAlerts("myAlert", linkModelPortfolioAlert);
		assertEquals(this.getSizeOfTriggeredAlert("myAlert", linkModelPortfolioAlert), 1);

		alerts2.goToMyAlerts().deleteAllMyAlerts();

		this.checkLogout();
		this.handleAlert();

		LoginPage main3 = new LoginPage(webDriver);
		main3.loginAs(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD).goToAccountOverviewPage()
				.simpleSearchByString(investorAccount).goToAccountHoldingsPageByName(investorAccount).goToDetailsPage()
				.unlinkModelPortfolioForAccount();

	}

	@Test
	public void testTriggerMultipleAccountLinkAndUnlinkAlert() throws Exception {
		String account = "JarlManager";
		String investorAccount = "Art Collection";
		String modelPortfolio = "123";
		String linkModelPortfolioAlert = linkAlertType;
		String unlinkModelPortfolioAlert = unlinkAlertType;
		int numberOfAlerts = 3;

		LoginPage main = new LoginPage(webDriver);
		AlertsPage alerts = main.loginAs(account, account).goToAlertsPageFromManage().goToMyAlerts()
				.deleteAllMyAlerts();

		this.createLinkModelPortfolio("company");
		this.createUnlinkModelPortfolio("company");

		this.checkLogout();
		this.handleAlert();

		LoginPage main2 = new LoginPage(webDriver);
		DetailPage details = main2.loginAs(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD)
				.goToAccountOverviewPage().simpleSearchByString(investorAccount)
				.goToAccountHoldingsPageByName(investorAccount).goToDetailsPage();

		for (int i = 0; i < numberOfAlerts; i++) {
			details.linkModelPortfolioForAccount(modelPortfolio, true).unlinkModelPortfolioForAccount();
		}

		this.checkLogout();
		this.handleAlert();

		LoginPage main3 = new LoginPage(webDriver);
		AlertsPage alerts3 = main3.loginAs(account, account).goToAlertsPageFromManage().goToCompanyWide()
				.expandTriggeredAlerts("company", linkModelPortfolioAlert);
		assertEquals(this.getSizeOfTriggeredAlert("company", linkModelPortfolioAlert), numberOfAlerts);

		alerts3.expandTriggeredAlerts("company", unlinkModelPortfolioAlert);
		assertEquals(this.getSizeOfTriggeredAlert("company", unlinkModelPortfolioAlert), numberOfAlerts);

		alerts3.goToMyAlerts().expandTriggeredAlerts("myAlert", linkModelPortfolioAlert);
		assertEquals(this.getSizeOfTriggeredAlert("myAlert", linkModelPortfolioAlert), numberOfAlerts);

		alerts3.expandTriggeredAlerts("myAlert", unlinkModelPortfolioAlert);
		assertEquals(this.getSizeOfTriggeredAlert("myAlert", unlinkModelPortfolioAlert), numberOfAlerts);

		alerts3.deleteAllMyAlerts();
	}

	public void createLinkModelPortfolio(String level) throws Exception {
		Boolean isCompany = level.equals("company");
		String type = linkAlertType;
		AlertsPage alerts = new AlertsPage(webDriver);

		if (isCompany) {
			alerts.goToCompanyWide().clickAddAlertButtonInCompanyWide();
		} else {
			alerts.goToMyAlerts().clickAddAlertButtonInMyAlert();
		}

		alerts.editAlertType(type).clickSaveButton();
	}

	public void createUnlinkModelPortfolio(String level) throws Exception {
		Boolean isCompany = level.equals("company");
		String type = unlinkAlertType;
		AlertsPage alerts = new AlertsPage(webDriver);

		if (isCompany) {
			alerts.goToCompanyWide().clickAddAlertButtonInCompanyWide();
		} else {
			alerts.goToMyAlerts().clickAddAlertButtonInMyAlert();
		}

		alerts.editAlertType(type).clickSaveButton();
	}

	public boolean isAlertDefinitionVisible(String level, String alertType) throws InterruptedException {
		int size;
		AlertsPage alerts = new AlertsPage(webDriver);
		if (level.equals("company")) {
			alerts.goToCompanyWide();
			size = this.getSizeOfElements(By
					.xpath(".//*[@id='gwt-debug-UserAlertOverviewView-advisorCompanyAlertsWidget']//*[@id='gwt-debug-UserAlertDefinitionListWidget-tablePanel']//*[.='"
							+ alertType + "']"));
			return isElementVisible(By
					.xpath("(.//*[@id='gwt-debug-UserAlertOverviewView-advisorCompanyAlertsWidget']//*[@id='gwt-debug-UserAlertDefinitionListWidget-tablePanel']//*[.='"
							+ alertType + "'])[" + String.valueOf(size) + "]"));
		} else {
			alerts.goToMyAlerts();
			size = this.getSizeOfElements(By
					.xpath(".//*[@id='gwt-debug-UserAlertOverviewView-myAlertsWidget']//*[@id='gwt-debug-UserAlertDefinitionListWidget-tablePanel']//*[.='"
							+ alertType + "']"));
			return isElementVisible(By
					.xpath("(.//*[@id='gwt-debug-UserAlertOverviewView-myAlertsWidget']//*[@id='gwt-debug-UserAlertDefinitionListWidget-tablePanel']//*[.='"
							+ alertType + "'])[" + String.valueOf(size) + "]"));

		}
	}

	public int getSizeOfTriggeredAlert(String level, String alertType) throws InterruptedException {
		int size = 0;
		AlertsPage alerts = new AlertsPage(webDriver);
		if (level.equals("company")) {
			alerts.goToCompanyWide();
			size = this.getSizeOfElements(By
					.xpath(".//*[@id='gwt-debug-UserAlertOverviewView-advisorCompanyAlertsPanel']//*[@id='gwt-debug-TriggeredUserAlertListWidget-tablePanel']//tr[td[.='"
							+ alertType
							+ "']]//following-sibling::tr//div[@id='gwt-debug-UserAlertTriggerListTable-triggerTablePanel']//tr[@class='mat-table-body']"));
		} else {
			alerts.goToMyAlerts();
			size = this.getSizeOfElements(By
					.xpath(".//*[@id='gwt-debug-UserAlertOverviewView-myAlertsPanel']//*[@id='gwt-debug-TriggeredUserAlertListWidget-tablePanel']//tr[td[.='"
							+ alertType
							+ "']]//following-sibling::tr//div[@id='gwt-debug-UserAlertTriggerListTable-triggerTablePanel']//tr[@class='mat-table-body']"));
		}
		return size;
	}
}
