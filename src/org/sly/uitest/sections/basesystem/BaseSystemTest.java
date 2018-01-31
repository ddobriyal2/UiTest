package org.sly.uitest.sections.basesystem;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.abstractpage.MenuBarPage;
import org.sly.uitest.pageobjects.admin.AdminEditPage;
import org.sly.uitest.pageobjects.clientsandaccounts.AccountOverviewPage;
import org.sly.uitest.pageobjects.clientsandaccounts.AccountsPage;
import org.sly.uitest.pageobjects.clientsandaccounts.CRMPage;
import org.sly.uitest.pageobjects.clientsandaccounts.DetailPage;
import org.sly.uitest.pageobjects.clientsandaccounts.HistoryPage;
import org.sly.uitest.pageobjects.companyadmin.EmployeePage;
import org.sly.uitest.pageobjects.mysettings.UserProfilePage;
import org.sly.uitest.settings.Settings;

/**
 * @author: Nandi Ouyang
 * @date: 12 Jan, 2015
 * @company: Prive Financial JIRA No.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BaseSystemTest extends AbstractTest {

	private boolean checkOneDisplayElement(String xpathStr) {

		List<WebElement> elements = webDriver.findElements(By.xpath(xpathStr));

		if (elements != null)
			for (WebElement e : elements) {
				if (e.isDisplayed())
					return true;
			}

		return false;

	}

	@Test
	public void testInvestorAccountReadAccess() throws Exception {

		// TODO - Use SeleniumTest

		LoginPage main = new LoginPage(webDriver);
		 wait(2);
		
		
		main.loginAs(Settings.USERNAME, Settings.PASSWORD);
		main.goToAccountOverviewPage()
				.simpleSearchByString("Selenium Test").goToAccountHoldingsPageByName("Selenium Test").goToDetailsPage()
				.goToEditPageByField("Access Rights");
		Thread.sleep(1000);
		
		try {

			waitForElementVisible(
					By.xpath(
							"//div[.='Selenium Consultant']/following::input[@id='gwt-debug-AccessRightsPresenter-writeAccessCheckBox-input'][1]"),
					Settings.WAIT_SECONDS);
		} catch (Exception ex) {
			selectFromDropdown(By.id("gwt-debug-AccessRightsNewWidget-userListBox"), "Selenium Consultant");
			clickElement(By.xpath(".//*[@id='gwt-debug-AccessRightsNewWidget-addButton']"));

			// wait(2);
			if (pageContainsStr("The selected user already has access rights.")) {
				clickOkButtonIfVisible();
			}
		}

		boolean isChanged1 = setCheckboxStatus2(
				webDriver.findElement(By
						.xpath("//div[.='Selenium Consultant']/following::input[@id='gwt-debug-AccessRightsPresenter-writeAccessCheckBox-input'][1]")),
				false);
		waitForElementVisible(
				By.xpath(
						"//div[.='Selenium Consultant']/following::input[@id='gwt-debug-AccessRightsPresenter-readAccessCheckBox-input'][1]"),
				150);
		boolean isChanged2 = setCheckboxStatus2(
				webDriver.findElement(By
						.xpath("//div[.='Selenium Consultant']/following::input[@id='gwt-debug-AccessRightsPresenter-readAccessCheckBox-input'][1]")),
				true);

		System.out.print(isChanged2);

		if (isChanged1 || isChanged2) {
			clickElementAndWait3(By.id("gwt-debug-AccessRightsNewWidget-saveBtn"));

			clickYesButtonIfVisible();

			clickOkButtonIfVisible();
		} else {
			clickElementAndWait3(By.id("gwt-debug-AccessRightsNewWidget-saveBtn"));
		}
		// wait(2 * Settings.WAIT_SECONDS);
		this.checkLogout();
		handleAlert();
		LoginPage main2 = new LoginPage(webDriver);
		// wait(2);
		DetailPage detail = main2.loginAs(Settings.CONSULTANT_USERNAME, Settings.CONSULTANT_PASSWORD)
				.goToAccountOverviewPage().simpleSearchByString("Selenium Test")
				.goToAccountHoldingsPageByName("Selenium Test").goToDetailsPage();

		assertTrue(!checkOneDisplayElement("//img[@src='img/glyphicons_030_pencil.png']"));
		assertTrue(!checkOneDisplayElement("//img[@src='img/glyphicons_190_circle_plus.png']"));

		CRMPage crm = detail.goToCRMPage();
		assertTrue(!checkOneDisplayElement("//img[@src='img/glyphicons_190_circle_plus.png']"));

		HistoryPage history = crm.goToTransactionHistoryPage();
		assertTrue(!checkOneDisplayElement("//img[@id='gwt-debug-RebalancingHistoryListView-editHistoryBtn']"));

		history.goToCashflowPage();
		assertTrue(!checkOneDisplayElement("//img[@id='gwt-debug-DepositWithdrawalHistoryPresenter-customButton']"));

	}

	@Test
	public void testInvestorAccountWriteAccess() throws Exception {
		// TODO - Use SeleniumTest

		LoginPage main = new LoginPage(webDriver);
		wait(2);
		main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage()
				.simpleSearchByString("Selenium Test").goToAccountHoldingsPageByName("Selenium Test").goToDetailsPage()
				.goToEditPageByField("Access Rights");
		/**//**
		 * Changes added due to new Access Right view. Related Jira OUTIND-27
		 * Changes made by Kailash
		 *//*
*/		try {
			waitForElementVisible(
					By.xpath(
							"//div[.='Selenium Consultant']/following::input[@id='gwt-debug-AccessRightsPresenter-writeAccessCheckBox-input'][1]"),
					2);
		} catch (Exception ex) {
			selectFromDropdown(By.id("gwt-debug-AccessRightsNewWidget-userListBox"), "Selenium Consultant");
			// webDriver.findElement(
			// By.id("gwt-debug-AccessRightsNewWidget-addButton"))
			// .sendKeys(Keys.RETURN);
			clickElement(By.id("gwt-debug-AccessRightsNewWidget-addButton"));

			wait(2);
			if (pageContainsStr("The selected user already has access rights.")) {
				clickOkButtonIfVisible();
			}
		}
		wait(1);
		boolean isChanged = setCheckboxStatus2(
				webDriver.findElement(By
						.xpath("//div[.='Selenium Consultant']/following::input[@id='gwt-debug-AccessRightsPresenter-writeAccessCheckBox-input'][1]")),
				true);

		if (isChanged) {
			clickElement(By.id("gwt-debug-AccessRightsNewWidget-saveBtn"));
			wait(2);
			clickElement(By.id("gwt-debug-CustomDialog-yesButton"));
			wait(2);
			clickElement(By.id("gwt-debug-CustomDialog-okButton"));
		} else {
			clickElement(By.id("gwt-debug-AccessRightsNewWidget-cancelBtn"));
		}
		this.checkLogout();
		wait(Settings.WAIT_SECONDS);
		handleAlert();

		LoginPage main2 = new LoginPage(webDriver);
		wait(2);
		DetailPage detail = main2.loginAs("SeleniumConsultant", "SeleniumConsultant").goToAccountOverviewPage()
				.simpleSearchByString("Selenium Test").goToAccountHoldingsPageByName("Selenium Test").goToDetailsPage();

		waitForElementVisible(By.id("gwt-debug-InvestorAccountDetailsSectionPresenter-image"), 30);

		assertTrue(detail.isElementDisplayed(By.id("gwt-debug-InvestorAccountDetailsSectionPresenter-image")));
		assertTrue(detail.isElementDisplayed(By.id("gwt-debug-CustomFieldsSectionView-editAccountImage")));
		assertTrue(detail.isElementDisplayed(By.id("gwt-debug-CustomFieldsSectionView-editAssetImage")));
		assertTrue(detail.isElementDisplayed(By.id("gwt-debug-ModelPortfolioSectionPresenter-image")));
		assertTrue(detail.isElementDisplayed(By.id("gwt-debug-RiskProfileSectionPresenter-image")));
		assertTrue(detail.isElementDisplayed(By.id("gwt-debug-AdvisorFeesSectionView-editInsideImg")));
		assertTrue(detail.isElementDisplayed(By.id("gwt-debug-PaymentMethodSectionView-editProviderImg")));
		assertTrue(detail.isElementDisplayed(By.id("gwt-debug-AdvisorFeesSectionView-editOutsideImg")));
		assertTrue(detail.isElementDisplayed(By.id("gwt-debug-PaymentMethodSectionView-editCompanyImg")));

		CRMPage crm = detail.goToCRMPage();

		waitForElementVisible(By.id("gwt-debug-TasksSectionPresenter-image"), 30);

		assertTrue(crm.isElementDisplayed(By.id("gwt-debug-TasksSectionPresenter-image")));
		assertTrue(crm.isElementDisplayed(By.id("gwt-debug-ActivitiesSectionPresenter-image")));
		assertTrue(crm.isElementDisplayed(By.id("gwt-debug-NotesSectionPresenter-image")));
		assertTrue(crm.isElementDisplayed(By.id("gwt-debug-ActivitiesSectionPresenter-image")));
		assertTrue(crm.isElementDisplayed(By.id("gwt-debug-BindersSectionPresenter-addBinderImage")));

		HistoryPage history = crm.goToTransactionHistoryPage();
		// assertTrue(checkAllDisplayElement("//img[@id='gwt-debug-RebalancingHistoryListView-editHistoryBtn']"));

		history.goToCashflowPage();

		waitForElementVisible(By.id("gwt-debug-DepositWithdrawalHistoryPresenter-customButton"), 30);

		assertTrue(history.isElementDisplayed(By.id("gwt-debug-DepositWithdrawalHistoryPresenter-customButton")));

	}

	@Test
	public void testInvestorAccountNoAccess() throws Exception {
		LoginPage main = new LoginPage(webDriver);
		// wait(2);
		main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage()
				.simpleSearchByString("Selenium Test").goToAccountHoldingsPageByName("Selenium Test").goToDetailsPage()
				.goToEditPageByField("Access Rights");
		/**//**
		 * Changes added due to new Access Right view. Related Jira OUTIND-27
		 * Changes made by Kailash
		 *//*
*/		try {
			waitForElementVisible(
					By.xpath(
							"//div[.='Selenium Consultant']/following::input[@id='gwt-debug-AccessRightsPresenter-writeAccessCheckBox-input'][1]"),
					5);
		} catch (Exception ex) {
			selectFromDropdown(By.id("gwt-debug-AccessRightsNewWidget-userListBox"), "Selenium Consultant");
			clickElement(By.xpath(".//*[@id='gwt-debug-AccessRightsNewWidget-addButton']"));

			wait(2);
			if (pageContainsStr("The selected user already has access rights.")) {
				clickOkButtonIfVisible();
			}
		}

		boolean isChanged1 = setCheckboxStatus2(
				webDriver.findElement(By
						.xpath("//div[.='Selenium Consultant']/following::input[@id='gwt-debug-AccessRightsPresenter-writeAccessCheckBox-input'][1]")),
				false);
		System.out.print(isChanged1);
		boolean isChanged2 = setCheckboxStatus2(
				webDriver.findElement(By
						.xpath("//div[.='Selenium Consultant']/following::input[@id='gwt-debug-AccessRightsPresenter-readAccessCheckBox-input'][1]")),
				false);
		System.out.print(isChanged2);
		if (isChanged1 || isChanged2) {
			clickElementAndWait3(By.id("gwt-debug-AccessRightsNewWidget-saveBtn"));
			clickElementAndWait3(By.id("gwt-debug-CustomDialog-yesButton"));
			clickElementAndWait3(By.id("gwt-debug-CustomDialog-okButton"));
		} else if (!isChanged1 && !isChanged2) {
			clickElementAndWait3(By.id("gwt-debug-AccessRightsNewWidget-cancelBtn"));
		}
		// wait(Settings.WAIT_SECONDS);

		checkLogout();
		handleAlert();

		LoginPage main2 = new LoginPage(webDriver);
		// wait(2);
		main2.loginAs(Settings.CONSULTANT_USERNAME, Settings.CONSULTANT_PASSWORD).goToAccountOverviewPage()
				.simpleSearchByString("Selenium Test");

		assertTrue(pageContainsStr("No information found"));

	}

	@Test
	public void test1EnsureInActiveUserCannotLogin() throws Exception {

		setScreenshotSuffix(BaseSystemTest.class.getSimpleName());

		// webDriver.get(loginUrl);

		checkLogin(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD);
		// wait(5);

		AdminEditPage adminEditPage = new AdminEditPage(webDriver);

		adminEditPage.goToAdminEditPage();

		navigateCommon();

		checkLogout();
		handleAlert();

		LoginPage main = new LoginPage(webDriver);
		main.loginIncorrectlyAs(Settings.INVESTOR_USERNAME, Settings.INVESTOR_PASSWORD);
		test3EnsureActiveUserLogin();
	}

	private void navigateCommon() throws InterruptedException {
		this.clickElementAndWait3(By.id("gwt-debug-AdminEditScreenView-editUserLink"));
		this.sendKeysToElement(By.id("gwt-debug-AdminSelect-keyTextBox"), "8462");
		this.clickElementAndWait3(By.id("gwt-debug-AdminSelect-selectManagerLoadSpecific"));

		waitForWaitingScreenNotVisible();

		waitForElementVisible(By.id("gwt-debug-AdminUserEdit-active-input"), 10);

		WebElement webEle = waitGet(By.id("gwt-debug-AdminUserEdit-active-input"));

		setCheckboxStatus2(webEle, false);

		this.clickElementAndWait3(By.id("gwt-debug-AdminUserEdit-editManagerSubmit"));

		waitForWaitingScreenNotVisible();
	}

	@Test
	public void test2EnsureDeletedUserCannotLogin() throws Exception {

		setScreenshotSuffix(BaseSystemTest.class.getSimpleName());

		// webDriver.get(loginUrl);

		checkLogin(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD);

		// wait(5);

		AdminEditPage adminEditPage = new AdminEditPage(webDriver);

		waitForElementVisible(By.id("gwt-debug-AdminEditScreenView-editUserLink"), 10);
		this.clickElementAndWait3(By.id("gwt-debug-AdminEditScreenView-editUserLink"));

		adminEditPage.jumpByKeyAndLoad("8462");

		waitForWaitingScreenNotVisible();

		WebElement webEle = waitGet(By.id("gwt-debug-AdminUserEdit-deleted-input"));

		setCheckboxStatus2(webEle, true);
		clickOkButtonIfVisible();

		waitForElementVisible(By.id("gwt-debug-AdminUserEdit-editManagerSubmit"), 10);

		this.clickElementAndWait3(By.id("gwt-debug-AdminUserEdit-editManagerSubmit"));

		waitForWaitingScreenNotVisible();

		this.checkLogout();
		handleAlert();

		LoginPage main = new LoginPage(webDriver);

		main.loginIncorrectlyAs(Settings.INVESTOR_USERNAME, Settings.INVESTOR_PASSWORD);

		test3EnsureActiveUserLogin();
	}

	@Test
	public void testCheckCorrectModuleIsLoad() throws Exception {

		// consultant
		LoginPage main_consultant = new LoginPage(webDriver);

		main_consultant.loginAs(Settings.CONSULTANT_USERNAME, Settings.CONSULTANT_PASSWORD);

		if (main_consultant.checkIfMaterial()) {
			assertTrue(getTabsInMaterialView("Clients & Accounts").isDisplayed());

			assertTrue(getTabsInMaterialView("Asset Management").isDisplayed());

			assertTrue(getTabsInMaterialView("Commissioning").isDisplayed());

			assertTrue(getTabsInMaterialView("Workflow").isDisplayed());

			assertTrue(getTabsInMaterialView("CRM").isDisplayed());

			assertTrue(getTabsInMaterialView("Investments").isDisplayed());

		} else {

			assertTrue(getTabsInClassicView("Clients").isDisplayed());

			assertTrue(getTabsInClassicView("Explore").isDisplayed());

			assertTrue(getTabsInClassicView("Manage").isDisplayed());

			assertTrue(getTabsInClassicView("Workflow").isDisplayed());

			assertTrue(getTabsInClassicView("Accounting").isDisplayed());

		}
		this.checkLogout();
		handleAlert();
		// investor
		LoginPage main_investor = new LoginPage(webDriver);

		main_investor.loginAs(Settings.INVESTOR_USERNAME, Settings.INVESTOR_PASSWORD);

		if (main_investor.checkIfMaterial()) {

			assertTrue(getTabsInMaterialView("Accounts").isDisplayed());

			assertTrue(getTabsInMaterialView("Investments").isDisplayed());

			assertTrue(getTabsInMaterialView("Plan").isDisplayed());

			assertTrue(getTabsInMaterialView("Asset Management").isDisplayed());

		} else {

			assertTrue(getTabsInClassicView("Accounts").isDisplayed());

			assertTrue(getTabsInClassicView("Explore").isDisplayed());

			assertTrue(getTabsInClassicView("Plan").isDisplayed());

		}

		this.checkLogout();
		// wait(5);
		handleAlert();
		// advisor

		// wait(Settings.WAIT_SECONDS);

		LoginPage main_advisor = new LoginPage(webDriver);

		main_advisor.loginAs(Settings.USERNAME, Settings.PASSWORD);
		// wait(5);

		if (main_advisor.checkIfMaterial()) {

			assertTrue(getTabsInMaterialView("Clients & Accounts").isDisplayed());

			assertTrue(getTabsInMaterialView("Investments").isDisplayed());

			assertTrue(getTabsInMaterialView("CRM").isDisplayed());

			assertTrue(getTabsInMaterialView("Asset Management").isDisplayed());

			assertTrue(getTabsInMaterialView("Commissioning").isDisplayed());

			assertTrue(getTabsInMaterialView("Operations").isDisplayed());

			assertTrue(getTabsInMaterialView("Workflow").isDisplayed());

			assertTrue(getTabsInMaterialView("Company Admin").isDisplayed());

		} else {
			assertTrue(getTabsInClassicView("Clients").isDisplayed());

			assertTrue(getTabsInClassicView("Explore").isDisplayed());

			assertTrue(getTabsInClassicView("Manage").isDisplayed());

			assertTrue(getTabsInClassicView("Workflow").isDisplayed());

			assertTrue(getTabsInClassicView("Accounting").isDisplayed());

			assertTrue(getTabsInClassicView("Operations").isDisplayed());

			assertTrue(getTabsInClassicView("Build").isDisplayed());

		}
		this.checkLogout();
		handleAlert();
		// admin
		LoginPage main_admin = new LoginPage(webDriver);

		main_admin.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD);

		if (main_admin.checkIfMaterial()) {

			assertTrue(getTabsInMaterialView("Investments").isDisplayed());

			assertTrue(getTabsInMaterialView("Workflow").isDisplayed());

			assertTrue(getTabsInMaterialView("Administration").isDisplayed());

		} else {

			assertTrue(getTabsInClassicView("Investments").isDisplayed());

			assertTrue(getTabsInClassicView("Workflow").isDisplayed());

			assertTrue(getTabsInClassicView("Administration").isDisplayed());

		}
		this.checkLogout();
		handleAlert();
		// manager
		LoginPage main_manager = new LoginPage(webDriver);

		main_manager.loginAs("JarlManager", "JarlManager");
		// wait(5);
		if (main_manager.checkIfMaterial()) {

			assertTrue(getTabsInMaterialView("Investments").isDisplayed());

			assertTrue(getTabsInMaterialView("Asset Management").isDisplayed());

		} else {
			assertTrue(getTabsInClassicView("Explore").isDisplayed());

			assertTrue(getTabsInClassicView("Build").isDisplayed());
		}

		this.checkLogout();
		handleAlert();
		// TrevorBestinvest
		LoginPage main = new LoginPage(webDriver);

		main.loginAs("TrevorBestinvest", "TrevorBestinvest");
		// wait(Settings.WAIT_SECONDS * 2);

		if (main.checkIfMaterial()) {

			waitForElementVisible(By.xpath(".//*[contains(text(),'Asset Management')]"), 10);

			assertTrue(getTabsInMaterialView("Asset Management").isDisplayed());

		} else {

			waitForElementVisible(By.xpath(".//*[contains(text(),'Build')]"), 10);

			assertTrue(getTabsInClassicView("Build").isDisplayed());
		}
		this.checkLogout();
		handleAlert();
	}

	@Test
	public void test3EnsureActiveUserLogin() throws Exception {

		setScreenshotSuffix(BaseSystemTest.class.getSimpleName());

		// webDriver.get(loginUrl);

		checkLogin(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD);
		// wait(5);
		AdminEditPage adminEditPage = new AdminEditPage(webDriver);
		adminEditPage.goToAdminEditPage();
		// .//*[@id='gwt-debug-AdminUserEdit-active-input' and @checked=""]
		// if (!isMaterialViewFlag) {
		// navigateToPage(
		// By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Administration"),
		// By.id("gwt-debug-MenuButtonHorizontal-Edit"));
		this.clickElementAndWait3(By.id("gwt-debug-AdminEditScreenView-editUserLink"));
		this.sendKeysToElement(By.id("gwt-debug-AdminSelect-keyTextBox"), "8462");
		this.clickElementAndWait3(By.id("gwt-debug-AdminSelect-selectManagerLoadSpecific"));
		waitForWaitingScreenNotVisible();

		WebElement webEle = waitGet(By.id("gwt-debug-AdminUserEdit-active-input"));

		setCheckboxStatus2(webEle, true);
		clickOkButtonIfVisible();
		// wait(5);

		if (waitGet(By.id("gwt-debug-AdminUserEdit-unlockBtn")).isEnabled()) {
			this.clickElementAndWait3(By.id("gwt-debug-AdminUserEdit-unlockBtn"));
			clickYesButtonIfVisible();
		}

		this.clickElementAndWait3(By.id("gwt-debug-AdminUserEdit-editManagerSubmit"));
		// } else {
		// loadAdminEditPageMaterialView();
		// this.clickElementAndWait3(By
		// .id("gwt-debug-AdminEditScreenView-editUserLink"));
		// this.sendKeysToElement(By.id("gwt-debug-AdminSelect-keyTextBox"),
		// "8462");
		// this.clickElementAndWait3(By
		// .id("gwt-debug-AdminSelect-selectManagerLoadSpecific"));
		// wait(20);
		// WebElement webEle = waitGet(By
		// .id("gwt-debug-AdminUserEdit-active-input"));
		// if (!webEle.isSelected()) {
		// webEle.click();
		// this.clickElementAndWait3(By
		// .id("gwt-debug-CustomDialog-okButton"));
		// wait(5);
		// if (waitGet(By.id("gwt-debug-AdminUserEdit-unlockBtn"))
		// .isEnabled()) {
		// this.clickElementAndWait3(By
		// .id("gwt-debug-AdminUserEdit-unlockBtn"));
		// wait(5);
		// clickYesButtonIfVisible();
		// /*
		// * if (waitGet(By.id("gwt-debug-CustomDialog-yesButton"))
		// * .isDisplayed()) { this.clickElementAndWait3(By
		// * .id("gwt-debug-CustomDialog-yesButton")); }
		// */
		// }
		// }
		// this.clickElementAndWait3(By
		// .id("gwt-debug-AdminUserEdit-editManagerSubmit"));
		// wait(5);
		// }

		this.checkLogout();
		handleAlert();

		// wait(5);
		LoginPage main = new LoginPage(webDriver);
		main.loginAs(Settings.INVESTOR_USERNAME, Settings.INVESTOR_PASSWORD);

		// wait(5);
		boolean isMaterialViewFlag = isElementVisible(By.xpath(".//*[@id='page-wrapper']/div[1]/nav/div/a/i"));
		WebElement webEleButton = null;
		if (isMaterialViewFlag || (webDriver.getCurrentUrl().indexOf("MATERIAL") > 0)) {
			webEleButton = getTabsInMaterialView("Accounts");
		} else {
			webEleButton = getTabsInClassicView("Accounts");
		}

		assertTrue(webEleButton.isDisplayed());

	}

	@Test
	public void testInvestorReadAccess() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		String client = "Investor, Selenium";
		String user = "Selenium Consultant";
		String name = "Selenium Consultant";

		AccountsPage accounts = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage()
				.simpleSearchByString("Selenium").goToClientDetailPageByName(client).goToAccountsTab()
				.showMoreAccounts();

		List<String> assetAccounts = accounts.getAllAssetAccounts();

		int size = assetAccounts.size();

		for (int i = 0; i < size; i++) {

			log(i + ": " + assetAccounts.get(i));

			accounts.goToAccountHoldingsPageByNameFromClient(assetAccounts.get(i)).goToDetailsPage()
					.editInvestorAccessRightsFromAccount(user, name, true, false).goToMainHolderDetailPageFromAccount()
					.goToAccountsTab().showMoreAccounts();
		}

		this.checkLogout();
		handleAlert();
		LoginPage main2 = new LoginPage(webDriver);
		wait(2);
		main2.loginAs(Settings.CONSULTANT_USERNAME, Settings.CONSULTANT_PASSWORD).goToAccountOverviewPage()
				.goToClientDetailPageByName(client);
		// Make sure Detail page has been loaded.
		waitForTextVisible(By.xpath(".//*[@class='generalOverviewSectionTitle generalOverviewSectionTitleUnderline']"),
				"Details");
		assertTrue(!isElementVisible(By.id("gwt-debug-UserDetailsSectionPresenter-image")));

		// try {
		// clickElement(By.id("gwt-debug-UserDetailsSectionPresenter-image"));
		// } catch (org.openqa.selenium.TimeoutException e) {
		// // TODO: handle exception
		// }

	}

	@Test
	public void testInvestorReadAndWriteAccess() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		String client = "Investor, Selenium";
		String user = "Selenium Consultant";
		String name = "Selenium Consultant";
		// wait(2);
		AccountsPage accounts = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage()
				.simpleSearchByString("Selenium").goToClientDetailPageByName(client).goToAccountsTab()
				.showMoreAccounts();

		List<String> assetAccounts = accounts.getAllAssetAccounts();

		int size = assetAccounts.size();

		for (int i = 0; i < size; i++) {

			log(i + ": " + assetAccounts.get(i));

			accounts.goToAccountHoldingsPageByNameFromClient(assetAccounts.get(i)).goToDetailsPage()
					.editInvestorAccessRightsFromAccount(user, name, true, true).goToMainHolderDetailPageFromAccount()
					.goToAccountsTab().showMoreAccounts();
		}

		this.checkLogout();
		handleAlert();
		LoginPage main2 = new LoginPage(webDriver);
		// wait(2);
		main2.loginAs(Settings.CONSULTANT_USERNAME, Settings.CONSULTANT_PASSWORD).goToAccountOverviewPage()
				.goToClientDetailPageByName(client);

		clickElement(By.id("gwt-debug-UserDetailsSectionPresenter-image"));

	}

	@Test
	public void testInvestorNoReadOrWriteAccess() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		String client = "Investor, Selenium";
		String user = "Selenium Consultant";
		String name = "Selenium Consultant";

		AccountsPage accounts = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage()
				.simpleSearchByString("Selenium").goToClientDetailPageByName(client).goToAccountsTab()
				.showMoreAccounts();

		List<String> assetAccounts = accounts.getAllAssetAccounts();

		int size = assetAccounts.size();
		log(String.valueOf(size));
		for (int i = 0; i < size; i++) {

			log(i + ": " + assetAccounts.get(i));

			accounts.goToAccountHoldingsPageByNameFromClient(assetAccounts.get(i)).goToDetailsPage()
					.editInvestorAccessRightsFromAccount(user, name, false, false).goToMainHolderDetailPageFromAccount()
					.goToAccountsTab().showMoreAccounts();
		}

		this.checkLogout();
		handleAlert();
		LoginPage main2 = new LoginPage(webDriver);
		wait(2);
		main2.loginAs(Settings.CONSULTANT_USERNAME, Settings.CONSULTANT_PASSWORD).goToAccountOverviewPage();

		assertTrue(!pageContainsStr(client));

	}

	@Test
	public void ensureOtherClientProfileCannotBeAccessed() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		String url = "?locale=en#generalUserDetailsClientDetail;userKey=10048;detailType=1";

		main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage().simpleSearchByString("Selenium")
				.goToClientDetailPageByName("Investor, Selenium");
		// wait(Settings.WAIT_SECONDS);
		webDriver.get(Settings.BASE_URL + url);

		try {
			handleAlert();
		} catch (Exception e) {
			// TODO: handle exception
		}

		assertTrue(pageContainsStr("Error"));
		assertTrue(pageContainsStr("You have no permission to access this user details"));

	}

	@Test
	public void ensureOtherAccountDetailsCannotBeAccessed() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);
		String url = "?locale=en&viewMode=MATERIAL#portfolioOverviewHoldings;investorAccountKey=12083;valueType=2";
		main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage().simpleSearchByString("Selenium")
				.goToAccountHoldingsPageByName("Selenium Test");

		try {
			webDriver.navigate().to(Settings.BASE_URL + url);
			handleAlert();
		} catch (WebDriverException e) {

		} finally {
			handleAlert();
		}

		waitForElementVisible(By.xpath(".//*[contains(text(),'Access is Denied')]"), 30);
		assertTrue(pageContainsStr("Access is Denied"));
		assertTrue(pageContainsStr("No Access Permission"));
	}

	@Test
	public void testCurrentUserCanSaveEmailAddress() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		String email = this.getRandName() + "@test.com";
		String type = "Other";

		UserProfilePage profile = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToUserProfilePage()
				.addNewEmailAddress(email, type).updateGeneralInformation().goToUserProfilePage();

		profile.deleteEmailAddress(email).updateGeneralInformation();

	}

	@Test
	public void testNewUserEnteringNonAssetAccount() throws Exception {

		// ensure the module toggle is on
		LoginPage main_module = new LoginPage(webDriver);

		String module = "MODULE_MANAGE_EMPLOYEE";
		// wait(3);
		MenuBarPage mbPage = main_module.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD);
		// wait(5);

		mbPage.goToAdminEditPage().editAdminThisField(Settings.Advisor_Company_Module_Permission)
				.jumpByKeyAndLoad(Settings.SeleniumTest_Key).editModuleToggle(module, false, true).clickSubmitButton();
		this.checkLogout();
		// wait(Settings.WAIT_SECONDS);
		handleAlert();

		// start testing
		LoginPage main = new LoginPage(webDriver);

		String title = "Ms.";
		String firstName = "Firstname" + this.getRandName();
		String lastName = "Lastname" + this.getRandName();
		String occupation = "Occupation" + this.getRandName();
		String position = "Position" + this.getRandName();
		String username = ("user" + this.getRandName()).replace("-", "").substring(0, 8);
		String password = ("code" + this.getRandName()).replace("-", "");
		String client = "Chiu, Nathan";
		String nonAsset = "Blackfish Property";

		// 1: Login as SeleniumTest
		// 2: Create a new Advisor Consultant by following these steps
		firstName = firstName.replaceAll(" ", "");
		lastName = lastName.replaceAll(" ", "");
		log(firstName);
		log(lastName);
		EmployeePage employee = ((EmployeePage) main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToEmployeePage()
				.clickNewButton().editClientTitle(title).editClientFirstName(firstName).editClientLastName(lastName)
				.editClientOccupation(occupation).editClientPosition(position).clickSaveButton_ClientAdditionalDetail())
						.editEmployee(lastName + ", " + firstName).editClientUserName(true, username)
						.editClientPassword(true, password).editClientChangePassword(false)
						.clickSaveButton_ClientAdditionalDetail();

		// 3: Assign Nathan Chiu to the newly created consultant
		AccountOverviewPage accounts = employee.goToAccountOverviewPage().checkIncludeNonAssetAccounts(true)
				.simpleSearchByString(nonAsset);

		assertTrue(pageContainsStr(nonAsset));
		// wait(5);
		String firstNameForAddition;
		// To make sure the format of string that pass to
		// editAddMainAdvisorByName() is correct.
		if (firstName.contains("-")) {
			firstNameForAddition = firstName.split("-")[1];
		} else {
			firstNameForAddition = firstName.split("e")[1];
		}

		accounts.goToClientDetailPageByName(client).goToEditPageByField("Additional Details")
				.editRemoveMainAdvisorByName("all").editAddMainAdvisorByName(firstNameForAddition)
				.editClientOccupation(occupation).editClientPosition(position)
				.clickSaveButton_ClientAdditionalDetail_MainAdvisorChange("Yes", "Read/write");

		// 4: To test whether a user preference setting encounters a n error or
		// not
		// wait(Settings.WAIT_SECONDS);
		this.checkLogout();
		handleAlert();

		LoginPage main2 = new LoginPage(webDriver);
		main2.loginAs(username, password).goToAccountOverviewPage().checkIncludeNonAssetAccounts(true)
				.checkIncludeInactiveAccounts(true).simpleSearchByString(nonAsset)
				.goToAccountDetailPageByName(nonAsset);

		assertTrue(pageContainsStr("Details"));

		this.checkLogout();
		handleAlert();

		LoginPage main3 = new LoginPage(webDriver);
		// reset to delete the new created client
		main3.loginAs(Settings.USERNAME, Settings.PASSWORD).goToEmployeePage().editEmployee(lastName + ", " + firstName)
				.clickDeleteButton_EmployeePage();

		assertFalse(pageContainsStr(firstName));

	}

	@Test
	public void testGhostLogin() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		String key = "24133";
		String field1_1 = "Edit Username";
		String field1_2 = " Username: ";
		String field2_1 = "Edit Password";
		String field2_2 = " Password: ";
		String value = "";

		MenuBarPage mbPage = main.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD);
		// wait(5);

		AdminEditPage adminEditPage = mbPage.goToAdminEditPage();

		AdminEditPage edituser = adminEditPage.editAdminThisField("User").jumpByKeyAndLoad(key)
				.checkTheCheckboxBeforeCertainField(field1_1, true).inputValueToCertainField(field1_2, value)
				.checkTheCheckboxBeforeCertainField(field2_1, true).inputValueToCertainField(field2_2, value)
				.checkTheCheckboxBeforeCertainField(field2_1, false).clickSubmitButton();
		// wait(5);
		edituser.jumpByKeyAndLoad(key);

		try {
			edituser.clickLoginAsThisUserButton();
			handleAlert();
		} catch (org.openqa.selenium.TimeoutException e) {
			assertTrue(pageContainsStr(
					"You have attempted to log-in as a user who does not have a username or password set-up."));
			edituser.clickOkButton();
		}

	} 

	// private void loadAdminEditPageMaterialView() {
	// clickElement(By
	// .xpath(".//*[.='Administration' and @class='navmat-label']"));
	// clickElement(By.xpath("//li/ul/li/a[.='Edit']"));
	// }

	private WebElement getTabsInMaterialView(String TabName) {

		WebElement elem = waitGet(
				By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='" + TabName + "']"));

		return elem;
	}

	private WebElement getTabsInClassicView(String TabName) {

		WebElement elem = waitGet(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-" + TabName));

		return elem;
	}
}
