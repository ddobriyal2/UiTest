package org.sly.uitest.sections.crm;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.alerts.AlertsPage;
import org.sly.uitest.pageobjects.assetmanagement.ModelPortfoliosPage;
import org.sly.uitest.pageobjects.clientsandaccounts.AccountOverviewPage;
import org.sly.uitest.settings.Settings;

/**
 * This is for alerts triggered by Deviation Portfolio Allocation
 * 
 * 
 * @author Benny Leung
 * @date 13 Jul 2017
 * @company Prive Financial
 */
public class AlertForDeviationPortfolioAllocationTest extends AbstractTest {

	@Test
	public void testAdvisorCompanyAccessToDeviationPortfolioAllocationAlert() throws Exception {
		String account = "DJEAdmin";
		String investorAccount = "";
		String alertType = "Deviation Portfolio Allocation";
		String modelPortfolio = "";
		LoginPage main = new LoginPage(webDriver);

		AlertsPage alerts = main.loginAs(account, account).goToAlertsPageFromManage()
				.clickAddAlertButtonInCompanyWide();
		assertTrue(this.checkIfAlertTypeVisibleInAlertPage(alertType));

		AccountOverviewPage accounts = alerts.clickCancelButton().goToAccountOverviewPage()
				.simpleSearchByString("Baader Bank");
		investorAccount = this
				.getTextByLocator(By.xpath("(.//*[@id='gwt-debug-InvestorAccountTable-linkPortfolioName'])[1]"));
		AlertsPage alerts2 = accounts.goToAccountHoldingsPageByName(investorAccount).goToAlertsPage()
				.clickAddAlertButtonInCompanyWide();
		assertTrue(this.checkIfAlertTypeVisibleInAlertPage(alertType));
		alerts2.clickCancelButton();

		ModelPortfoliosPage modelPortfolios = accounts.goToModelPortfoliosPage().clickManageModelPortfolios();
		modelPortfolio = this
				.getTextByLocator(By.xpath("(.//*[@id='gwt-debug-InvestorAccountTable-linkPortfolioName'])[1]"));

		modelPortfolios.goToModelPortfolio(modelPortfolio).goToAlertsPage().clickAddAlertButtonInCompanyWide();
		assertTrue(this.checkIfAlertTypeVisibleInAlertPage(alertType));
	}

	@Test
	public void testManagerCompanyAccessToDeviationPortfolioAllocationAlert() throws Exception {
		String account = "JarlManager";
		String investorAccount = "";
		String alertType = "Deviation Portfolio Allocation";
		String modelPortfolio = "";

		LoginPage main = new LoginPage(webDriver);
		AlertsPage alerts = main.loginAs(account, account).goToAlertsPageFromManage()
				.clickAddAlertButtonInCompanyWide();
		assertFalse(this.checkIfAlertTypeVisibleInAlertPage(alertType));

		ModelPortfoliosPage modelPortfolios = alerts.clickCancelButton().goToModelPortfoliosPage()
				.clickManageModelPortfolios();
		modelPortfolio = this
				.getTextByLocator(By.xpath("(.//*[@id='gwt-debug-InvestorAccountTable-linkPortfolioName'])[1]"));

		modelPortfolios.goToModelPortfolio(modelPortfolio).goToAlertsPage().clickAddAlertButtonInCompanyWide();
		assertFalse(this.checkIfAlertTypeVisibleInAlertPage(alertType));

	}

	@Test
	public void testCreateDeviationPortfolioAllocationAlertInCompanyWide() throws Exception {
		Boolean isCompanyWide = true;
		this.createDifferentDeviationPortfolioAlert(isCompanyWide);
	}

	@Test
	public void testCreateDeviationPortfolioAllocationInMyAlert() throws InterruptedException, Exception {
		Boolean isCompanyWide = false;
		this.createDifferentDeviationPortfolioAlert(isCompanyWide);
	}

	public Boolean checkIfAlertTypeVisibleInAlertPage(String alertType) {
		Select select = new Select(
				webDriver.findElement(By.id("gwt-debug-EditUserAlertDefinitionView-alertTypeListBox")));

		return isElementVisible(By.xpath(
				".//*[@id='gwt-debug-EditUserAlertDefinitionView-alertTypeListBox']//option[.='" + alertType + "']"));

	}

	public void createDifferentDeviationPortfolioAlert(boolean isCompanyWide) throws InterruptedException, Exception {
		LoginPage main = new LoginPage(webDriver);
		String alertAppliesTo = "All";
		String[] values = { " 1%", " 2%", " 3%", " 4%", " 5%", "10%" };
		String alertType = "Deviation Portfolio Allocation";

		AlertsPage alerts = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAlertsPageFromManage();

		if (isCompanyWide) {
			alerts.goToCompanyWide().deleteAllCompanyAlerts();
		} else {
			alerts.goToMyAlerts().deleteAllMyAlerts();
		}

		for (int i = values.length - 1; i >= 0; i--) {
			String percentage = values[i];
			By by = By
					.xpath("(.//*[@id='gwt-debug-UserAlertDefinitionListWidget-tablePanel'])[1]//div[.='If current weight of an investment deviates by more than "
							+ percentage + " from the target weight assigned to this investment.']");

			if (isCompanyWide) {
				alerts.clickAddAlertButtonInCompanyWide();
			} else {
				alerts.clickAddAlertButtonInMyAlert();
			}

			alerts.editAlertType(alertType).editDeviationPortfolioAllocation(percentage, alertAppliesTo)
					.clickSaveButton();
			this.waitForElementPresent(by, Settings.WAIT_SECONDS);
			assertTrue(isElementPresent(by));

		}

		if (isCompanyWide) {
			alerts.deleteAllCompanyAlerts();
		} else {
			alerts.deleteAllMyAlerts();
		}

	}
}
