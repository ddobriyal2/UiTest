package org.sly.uitest.sections.dashboard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.admin.AdminEditPage;
import org.sly.uitest.pageobjects.clientsandaccounts.AccountOverviewPage;
import org.sly.uitest.pageobjects.companyadmin.DashboardPage;
import org.sly.uitest.pageobjects.companyadmin.EmployeePage;
import org.sly.uitest.pageobjects.mysettings.UserPreferencePage;
import org.sly.uitest.settings.Settings;

/**
 * Test for dashboard Test details is described <a href=
 * "https://docs.google.com/spreadsheets/d/16jRQBVjBvMpActzAZaWScQdtJWm67Ax9lkJyhcHMDts/edit#gid=0"
 * >here</a>
 * 
 * @author Benny Leung
 * @date Aug 19 2016
 * @company Prive Financial
 */
public class DashboardTest extends AbstractTest {
	@Test
	public void test1LandingPage() throws Exception {
		String module = "MODULE_DASHBOARD";

		LoginPage main = new LoginPage(webDriver);
		AdminEditPage adminEdit = main.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD)
				.goToAdminEditPage();

		adminEdit.editAdminThisField("Advisor Company Module Permission").jumpByKeyAndLoad(Settings.SeleniumTest_Key)
				.editModuleToggle(module, true, true).clickSubmitButton();

		checkLogout();
		handleAlert();

		LoginPage main2 = new LoginPage(webDriver);
		UserPreferencePage userPreference = main2.loginAs(Settings.USERNAME, Settings.PASSWORD)
				.goToUserPreferencePage(DashboardPage.class);

		userPreference.editLandingPageByName("Dashboard").clickSubmitButton();
		assertTrue(pageContainsStr("Dashboard"));
		checkLogout();
		handleAlert();

		LoginPage main3 = new LoginPage(webDriver);
		main3.loginAs(Settings.USERNAME, Settings.PASSWORD);

		waitForElementVisible(By.id("gwt-debug-DashBoardView-addWidgets"), 30);
		assertTrue(pageContainsStr("Dashboard"));

		main3.goToUserPreferencePage(AccountOverviewPage.class).editLandingPageByName("Summary of Accounts")
				.clickSubmitButton();

		assertTrue(pageContainsStr("Summary of Accounts"));
	}

	@Test
	public void testAddWidget() throws InterruptedException {
		LoginPage main = new LoginPage(webDriver);
		DashboardPage dashBoard = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToDashboardPage();
		// Test case 3
		dashBoard.clickAddWidgetsButton();
		assertTrue(isElementVisible(
				By.xpath(".//*[@class='fa fa-button fa-exclamation-circle info-blue size-64 padding-top-20px']")));

		// Test case 4
		dashBoard.goToClientOverviewPage();
		assertTrue(!isElementVisible(
				By.xpath(".//*[@class='fa fa-button fa-exclamation-circle info-blue size-64 padding-top-20px']")));

		// Test case 5 and 7
		dashBoard.goToDashboardPage();

		for (int i = 0; i < 3; i++) {
			dashBoard.clickAddWindowButton().clickAddWidgetsButton()
					.dragAndDropFavouriteInvestmentsWidget("investments")
					.dragAndDropFavouriteInvestmentsWidget("clients").closeAddWidgetWindow();

			assertTrue(this.getSizeOfElements(By.xpath(".//img[@src='img/widget/dashboard/fav_investments.png']")) > 1);
			assertTrue(this.getSizeOfElements(By.xpath(".//img[@src='img/widget/dashboard/latest_clients.png']")) > 1);
		}

		dashBoard.clickSaveButton();
		// wait(5);
		for (int j = 3; 0 < j; j--) {
			dashBoard.clickDeleteWindowButton();
			// wait(3);
		}
		dashBoard.clickSaveButton();
	}

	@Test
	public void testDashboardForNewUser() throws Exception {
		LoginPage main = new LoginPage(webDriver);
		String firstName = "First" + getRandName();
		String lastName = "Last" + getRandName();
		String username = ("newTestUser" + this.getRandName()).replace("-", "");
		String password = ("code" + this.getRandName()).replace("-", "");

		EmployeePage employee = ((EmployeePage) main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToEmployeePage()
				.clickNewButton().editClientTitle("Mr.").editClientFirstName(firstName).editClientLastName(lastName)
				.clickSaveButton_ClientAdditionalDetail())

						.editEmployee(lastName + ", " + firstName).editClientUserName(true, username)
						.editClientPassword(true, password).editClientChangePassword(false)
						.clickSaveButton_ClientAdditionalDetail();

		checkLogout();
		handleAlert();

		LoginPage main2 = new LoginPage(webDriver);
		main2.loginAs(username, password).goToUserPreferencePage(DashboardPage.class).editTheme("Material")
				.editLandingPageByName("Dashboard").clickSubmitButton();
		try {
			assertTrue(isElementVisible(By.xpath(".//img[@src='img/widget/dashboard/fav_investments.png']")));
		} catch (Exception e) {
			try {
				assertTrue(isElementVisible(By.xpath(".//img[@src='img/widget/dashboard/latest_clients.png']")));
			} catch (Exception ex) {
				assertTrue(isElementVisible(By.xpath(".//img[@src='img/widget/dashboard/alerts.png']")));
			}
		}

		// wait(3);
		checkLogout();
		handleAlert();

		LoginPage main3 = new LoginPage(webDriver);
		main3.loginAs(Settings.USERNAME, Settings.PASSWORD).goToEmployeePage().editEmployee(lastName + ", " + firstName)
				.clickDeleteButton_EmployeePage();
	}

	@Test
	public void testNoExtraWidgetFromOtherWindow() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		DashboardPage dashboard = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToDashboardPage();

		int investmentWidgetSize = getSizeOfElements(By.xpath(
				"//img[@src='img/widget/dashboard/fav_investments.png']//following-sibling::span[.='Investments']"));

		int clientWidgetSize = getSizeOfElements(By.xpath(
				"//img[@src='img/widget/dashboard/latest_clients.png']//following-sibling::span[.='Latest Clients']"));

		int alertWidgetSize = getSizeOfElements(
				By.xpath("//img[@src='img/widget/dashboard/alerts.png']//following-sibling::span[.='Alerts']"));

		int size = getSizeOfElements(By.xpath("//div[contains(@eventproxy,'isc_DashBoardTab') and @role='tab']"));

		if (size < 2) {
			dashboard.clickAddWindowButton().clickAddWidgetsButton()
					.dragAndDropFavouriteInvestmentsWidget("investments")
					.dragAndDropFavouriteInvestmentsWidget("clients").dragAndDropFavouriteInvestmentsWidget("alerts")
					.clickSaveButton();
		}

		clickElement(By.xpath("(//div[contains(@eventproxy,'isc_DashBoardTab') and @role='tab'])[" + size + "]"));

		main.goToAccountOverviewPage().goToDashboardPage();
		scrollToBottom();

		assertEquals(investmentWidgetSize, getSizeOfElements(By.xpath(
				"//img[@src='img/widget/dashboard/fav_investments.png']//following-sibling::span[.='Investments']")));

		assertEquals(clientWidgetSize, getSizeOfElements(By.xpath(
				"//img[@src='img/widget/dashboard/latest_clients.png']//following-sibling::span[.='Latest Clients']")));

		assertEquals(alertWidgetSize, getSizeOfElements(
				By.xpath("//img[@src='img/widget/dashboard/alerts.png']//following-sibling::span[.='Alerts']")));
	}
}
