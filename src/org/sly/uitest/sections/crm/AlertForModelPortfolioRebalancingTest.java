package org.sly.uitest.sections.crm;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.alerts.AlertsPage;
import org.sly.uitest.pageobjects.clientsandaccounts.HoldingsPage;
import org.sly.uitest.pageobjects.investments.InvestmentsPage;
import org.sly.uitest.settings.Settings;

/**
 * This is for alerts triggered by Model Portfolio rebalancing
 * 
 * Details:SLYAWS-10252 [MODEL PORTFOLIO] Alert when model portfolio is
 * rebalanced
 * 
 * @author Benny Leung
 * @date : 23 Jun, 2017
 * @company Prive Financial
 *
 */
public class AlertForModelPortfolioRebalancingTest extends AbstractTest {

	@Test
	public void testModuleToggleForModelPortfolioRebalancing() throws InterruptedException {
		String module = "MODULE_USER_ALERT_DISABLE_TYPE_MODELPORTFOLIO_MANUALLY_REBALANCED";

		LoginPage main = new LoginPage(webDriver);
		main.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD).goToAdminEditPage()
				.editAdminThisField(Settings.Advisor_Company_Module_Permission)
				.jumpByKeyAndLoad(Settings.SeleniumTest_Key).editModuleToggle(module, true, true).clickSubmitButton();

		checkLogout();
		handleAlert();

		LoginPage main2 = new LoginPage(webDriver);
		AlertsPage alert = main2.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAlertsPageFromManage()
				.goToCompanyWide().clickAddAlertButtonInCompanyWide();

		Select select = new Select(
				webDriver.findElement(By.id("gwt-debug-EditUserAlertDefinitionView-alertTypeListBox")));

		assertFalse(isElementVisible(By.xpath(
				"./*[@id='gwt-debug-EditUserAlertDefinitionView-alertTypeListBox']//option[.='Model Portfolio Rebalance']")));

		alert.clickCancelButton();
		checkLogout();
		handleAlert();

		LoginPage main3 = new LoginPage(webDriver);
		main3.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD).goToAdminEditPage()
				.editAdminThisField(Settings.Advisor_Company_Module_Permission)
				.jumpByKeyAndLoad(Settings.SeleniumTest_Key).editModuleToggle(module, false, false).clickSubmitButton();

		checkLogout();
		handleAlert();

		LoginPage main4 = new LoginPage(webDriver);
		main4.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAlertsPageFromManage().goToCompanyWide()
				.clickAddAlertButtonInCompanyWide();

		Select select2 = new Select(
				webDriver.findElement(By.id("gwt-debug-EditUserAlertDefinitionView-alertTypeListBox")));

		assertTrue(isElementVisible(By.xpath(
				".//*[@id='gwt-debug-EditUserAlertDefinitionView-alertTypeListBox']//option[.='Model Portfolio Rebalance']")));

	}

	@Test
	public void testAdvisorCompanyAccessToModelPortfolioRebalanceAlert() throws InterruptedException {
		LoginPage main = new LoginPage(webDriver);
		AlertsPage alert = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAlertsPageFromManage()
				.goToCompanyWide().clickAddAlertButtonInCompanyWide();

		Select select = new Select(
				webDriver.findElement(By.id("gwt-debug-EditUserAlertDefinitionView-alertTypeListBox")));

		assertTrue(select.getOptions().contains(webDriver.findElement(By.xpath(
				".//*[@id='gwt-debug-EditUserAlertDefinitionView-alertTypeListBox']//option[.='Model Portfolio Rebalance']"))));

	}

	@Test
	public void testManagerCompanyCannotAccessToModelPortfolioRebalanceAlert() throws InterruptedException {
		LoginPage main = new LoginPage(webDriver);
		String account = "LyonsPriveManager";
		String alertType = "Model Portfolio Rebalance";
		AlertsPage alert = main.loginAs(account, account).goToAlertsPageFromManage().goToCompanyWide()
				.clickAddAlertButtonInCompanyWide();

		Select select = new Select(
				webDriver.findElement(By.id("gwt-debug-EditUserAlertDefinitionView-alertTypeListBox")));

		assertFalse(isElementPresent(By.xpath(
				".//*[@id='gwt-debug-EditUserAlertDefinitionView-alertTypeListBox']//option[.='" + alertType + "']")));
	}

	@Test
	public void testCreateModelPortfolioRebalanceAlertInCompanyWide() throws Exception {
		String level = "company";
		this.createModelPortfolioRebalanceAlertForTest(level);
	}

	@Test
	public void testCreateModelPortfolioRebalanceAlertInMyAlert() throws Exception {
		String level = "myAlert";
		this.createModelPortfolioRebalanceAlertForTest(level);
	}

	@Test
	public void testTriggerModelPortfolioRebalanceInCompanyWide() throws Exception {
		this.triggerModelPortfolioRebalance("company");
	}

	@Test
	public void testTriggerModelPortfolioRebalanceInMyAlert() throws Exception {
		this.triggerModelPortfolioRebalance("myAlert");
	}

	@Test
	public void testTriggerModelPortfolioRebalancingByRemoveAsset() throws Exception {
		String portfolio = "Sample Model Portfolio";
		String investment = "";
		String level = "company";
		LoginPage main = new LoginPage(webDriver);
		AlertsPage alerts = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAlertsPageFromManage()
				.goToCompanyWide().deleteAllCompanyAlerts();

		investment = this.reallocateModelPortfolio();

		this.createModelPortfolioRebalanceAlert(level);

		this.removeAssetFromModelPortfolio(investment);

		assertTrue(isElementPresent(By.xpath(
				".//*[@id='gwt-debug-TriggeredUserAlertListWidget-tablePanel']//td[.='Model Portfolio Rebalance']")));

		alerts.goToCompanyWide().deleteAllCompanyAlerts();

	}

	@Test
	public void testTriggerModelPortfolioRebalancingByReplacingAsset() throws InterruptedException, Exception {
		String portfolio = "Sample Model Portfolio";
		String investment = "";
		String investment2 = "";
		String level = "company";
		LoginPage main = new LoginPage(webDriver);
		AlertsPage alerts = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAlertsPageFromManage()
				.goToCompanyWide().deleteAllCompanyAlerts();

		investment = this.reallocateModelPortfolio();

		this.createModelPortfolioRebalanceAlert(level);

		InvestmentsPage ipage = alerts.goToModelPortfoliosPage().clickManageModelPortfolios()
				.goToModelPortfolio(portfolio).clickReallocateButton().clickAddInvestmentButton();

		investment = this.getTextByLocator(By.xpath("(.//*[@id='gwt-debug-ManagerListItem-strategyName'])[1]"));

		HoldingsPage holdings = ipage.selectInvestmentByName(investment).clickAddToPortfolioButton();

		holdings.setNewAllocationForInvestment(investment2, "5").setNewAllocationForInvestment(investment, "0")
				.clickRebalancePreviewAndConfirm(investment2).goToAlertsPageFromManage();

		assertTrue(isElementPresent(By.xpath(
				".//*[@id='gwt-debug-TriggeredUserAlertListWidget-tablePanel']//td[.='Model Portfolio Rebalance']")));

		alerts.goToCompanyWide().deleteAllCompanyAlerts();

		this.removeAssetFromModelPortfolio(investment2);

	}

	@Test
	public void testViewAndSearchModelPortfolioRebalanceAlertInCompanyWide() throws Exception {
		String level = "company";
		this.viewAndSearchModelPortfolioRebalanceAlert(level);
	}

	@Test
	public void testViewAndSearchModelPortfolioRebalanceAlertInMyAlert() throws Exception {
		String level = "myAlert";
		this.viewAndSearchModelPortfolioRebalanceAlert(level);
	}

	@Test
	public void testDeleteMultipleTriggeredModelPortfolioRebalanceAlert() throws Exception {
		int alertNumber = 3;
		int size;
		String level = "company";
		String portfolio = "Sample Model Portfolio";
		String[] investments = new String[alertNumber];

		LoginPage main = new LoginPage(webDriver);
		AlertsPage alerts = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAlertsPageFromManage()
				.goToCompanyWide();
		alerts.deleteAllCompanyAlerts();
		this.createModelPortfolioRebalanceAlert(level);
		log(String.valueOf(investments.length));
		for (int i = 0; i <= investments.length - 1; i++) {
			investments[i] = this.reallocateModelPortfolio();
		}

		alerts.goToCompanyWide();

		assertTrue(isElementPresent(By.xpath(
				".//*[@id='gwt-debug-TriggeredUserAlertListWidget-tablePanel']//td[.='Model Portfolio Rebalance']")));

		alerts.deleteAllCompanyAlerts();
		this.refreshPage();
		assertFalse(isElementPresent(By.xpath(
				".//*[@id='gwt-debug-TriggeredUserAlertListWidget-tablePanel']//td[.='Model Portfolio Rebalance']")));

		HoldingsPage holdings = alerts.goToModelPortfoliosPage().clickManageModelPortfolios()
				.goToModelPortfolio(portfolio).clickReallocateButton();

		for (String investment : investments) {

			holdings.setNewAllocationForInvestment(investment, "0");

		}

		holdings.clickRebalancePreviewAndConfirm("").goToAlertsPageFromManage();
	}

	public void viewAndSearchModelPortfolioRebalanceAlert(String level) throws Exception {
		Boolean isCompany = level.equals("company");
		String portfolio = "Sample Model Portfolio";
		String investment = "";
		String alertType = "Model Portfolio Rebalance";
		LoginPage main = new LoginPage(webDriver);
		AlertsPage alerts = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAlertsPageFromManage()
				.goToCompanyWide().deleteAllCompanyAlerts();

		this.createModelPortfolioRebalanceAlert(level);

		investment = this.reallocateModelPortfolio();

		if (isCompany) {
			alerts.goToCompanyWide();
		} else {
			alerts.goToMyAlerts();
		}

		assertTrue(isElementPresent(By.xpath(
				".//*[@id='gwt-debug-TriggeredUserAlertListWidget-tablePanel']//td[.='Model Portfolio Rebalance']")));

		if (isCompany) {
			alerts.goToCompanyWide().clickAdvancedSearchButtonForTriggeredAlertsInCompanyWide()
					.searchByAlertTypeInCompanyWide(alertType).clickSearchButton();
		} else {
			alerts.goToMyAlerts().clickAdvancedSearchButtonForTriggeredAlertsInMyAlerts()
					.searchByAlertTypeInMyAlerts(alertType).clickSearchButton();

		}

		assertTrue(isElementPresent(By.xpath(
				".//*[@id='gwt-debug-TriggeredUserAlertListWidget-tablePanel']//td[.='Model Portfolio Rebalance']")));

		alerts.goToModelPortfoliosPage().clickManageModelPortfolios().goToModelPortfolio(portfolio).goToAlertsPage();

		if (isCompany) {
			alerts.goToCompanyWide();
		} else {
			alerts.goToMyAlerts();
		}
		this.waitForWaitingScreenNotVisible();
		this.waitForElementPresent(
				By.xpath(
						".//*[@id='gwt-debug-TriggeredUserAlertListWidget-tablePanel']//td[.='Model Portfolio Rebalance']"),
				Settings.WAIT_SECONDS * 2);

		assertTrue(isElementPresent(By.xpath(
				".//*[@id='gwt-debug-TriggeredUserAlertListWidget-tablePanel']//td[.='Model Portfolio Rebalance']")));

		if (isCompany) {
			alerts.clickAdvancedSearchButtonForTriggeredAlertsInCompanyWide().searchByAlertTypeInCompanyWide(alertType)
					.clickSearchButton();
		} else {
			alerts.clickAdvancedSearchButtonForTriggeredAlertsInMyAlerts().searchByAlertTypeInMyAlerts(alertType)
					.clickSearchButton();

		}
		assertTrue(isElementPresent(By.xpath(
				".//*[@id='gwt-debug-TriggeredUserAlertListWidget-tablePanel']//td[.='Model Portfolio Rebalance']")));

		if (isCompany) {
			alerts.goToCompanyWide().deleteAllCompanyAlerts();
		} else {
			alerts.goToMyAlerts().deleteAllMyAlerts();
		}

		this.waitForWaitingScreenNotVisible();

		try {
			assertFalse(isElementPresent(By.xpath(
					".//*[@id='gwt-debug-TriggeredUserAlertListWidget-tablePanel']//td[.='Model Portfolio Rebalance']")));
		} catch (AssertionError e) {
			this.refreshPage();
			assertFalse(isElementPresent(By.xpath(
					".//*[@id='gwt-debug-TriggeredUserAlertListWidget-tablePanel']//td[.='Model Portfolio Rebalance']")));
		}

		alerts.goToAlertsPageFromManage();

		if (isCompany) {
			alerts.goToCompanyWide();
		} else {
			alerts.goToMyAlerts();
		}

		this.waitForWaitingScreenNotVisible();

		assertFalse(isElementPresent(By.xpath(
				".//*[@id='gwt-debug-TriggeredUserAlertListWidget-tablePanel']//td[.='Model Portfolio Rebalance']")));

		this.removeAssetFromModelPortfolio(investment);
	}

	public void triggerModelPortfolioRebalance(String level) throws Exception {
		Boolean isCompany = level.equals("company");
		String portfolio = "Sample Model Portfolio";
		String investment = "";
		LoginPage main = new LoginPage(webDriver);

		AlertsPage alerts = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAlertsPageFromManage();
		if (isCompany) {
			alerts.goToCompanyWide().deleteAllCompanyAlerts();
		} else {
			alerts.goToMyAlerts().deleteAllMyAlerts();
		}

		this.createModelPortfolioRebalanceAlert(level);

		investment = this.reallocateModelPortfolio();

		if (isCompany) {
			alerts.goToCompanyWide();
		} else {
			alerts.goToMyAlerts();
		}

		assertTrue(isElementPresent(By.xpath(
				".//*[@id='gwt-debug-TriggeredUserAlertListWidget-tablePanel']//td[.='Model Portfolio Rebalance']")));

		if (isCompany) {
			alerts.deleteAllCompanyAlerts();
		} else {
			alerts.deleteAllMyAlerts();
		}

		this.removeAssetFromModelPortfolio(investment);
	}

	/**
	 * For testCreateModelPortfolioRebalanceAlertInCompanyWide and
	 * testCreateModelPortfolioRebalanceAlertInMyAlert
	 * 
	 * @param level
	 * @throws Exception
	 */
	public void createModelPortfolioRebalanceAlertForTest(String level) throws Exception {
		Boolean isCompany = level.equals("company");
		LoginPage main = new LoginPage(webDriver);
		AlertsPage alerts = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAlertsPageFromManage();
		if (isCompany) {
			alerts.goToCompanyWide().deleteAllCompanyAlerts();
		} else {
			alerts.goToMyAlerts().deleteAllMyAlerts();
		}

		this.createModelPortfolioRebalanceAlert(level);

		if (isCompany) {
			alerts.goToCompanyWide();
			assertTrue(isElementVisible(By.xpath(
					".//*[@id='gwt-debug-UserAlertOverviewView-advisorCompanyAlertsWidget']//td[.='Model Portfolio Rebalance']")));
			alerts.deleteAllCompanyAlerts();
		} else {
			alerts.goToMyAlerts();
			assertTrue(isElementVisible(By.xpath(
					".//*[@id='gwt-debug-UserAlertOverviewView-myAlertsWidget']//td[.='Model Portfolio Rebalance']")));
			alerts.deleteAllMyAlerts();
		}

	}

	public void createModelPortfolioRebalanceAlert(String level) throws Exception {
		Boolean isCompany = level.equals("company");
		String type = "Model Portfolio Rebalance";
		AlertsPage alerts = new AlertsPage(webDriver);

		if (isCompany) {
			alerts.goToCompanyWide().clickAddAlertButtonInCompanyWide();
		} else {
			alerts.goToMyAlerts().clickAddAlertButtonInMyAlert();
		}

		alerts.editAlertType(type).clickSaveButton();
	}

	public String reallocateModelPortfolio() throws Exception {
		String investment = "";
		String portfolio = "Sample Model Portfolio";
		AlertsPage alerts = new AlertsPage(webDriver);
		InvestmentsPage iPage = alerts.goToModelPortfoliosPage().clickManageModelPortfolios()
				.goToModelPortfolio(portfolio).clickReallocateButton().clickAddInvestmentButton();

		investment = this.getTextByLocator(By.xpath("(.//*[@id='gwt-debug-ManagerListItem-strategyName'])[1]"));

		AlertsPage alerts2 = ((HoldingsPage) iPage.selectInvestmentByName(investment).clickAddToPortfolioButton())
				.setNewAllocationForInvestment(investment, "5").clickRebalancePreviewAndConfirm(investment)
				.goToAlertsPageFromManage();

		return investment;
	}

	public void removeAssetFromModelPortfolio(String investment) throws Exception {
		AlertsPage alerts = new AlertsPage(webDriver);
		String portfolio = "Sample Model Portfolio";

		alerts.goToModelPortfoliosPage().clickManageModelPortfolios().goToModelPortfolio(portfolio)
				.clickReallocateButton().setNewAllocationForInvestment(investment, "0")
				.clickRebalancePreviewAndConfirm("").goToAlertsPageFromManage();
	}
}
