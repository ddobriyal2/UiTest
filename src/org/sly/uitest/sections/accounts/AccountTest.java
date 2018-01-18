package org.sly.uitest.sections.accounts;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.abstractpage.MenuBarPage;
import org.sly.uitest.pageobjects.admin.AdminEditPage;
import org.sly.uitest.pageobjects.clientsandaccounts.AccountOverviewPage;
import org.sly.uitest.pageobjects.clientsandaccounts.DetailPage;
import org.sly.uitest.settings.Settings;

/**
 * @author Lynne Huang
 * @date : 16 Sep, 2015
 * @company Prive Financial
 */
public class AccountTest extends AbstractPage {

	@Test
	public void testSystemCreationInInvestorView() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		main.loginAs(Settings.INVESTOR_USERNAME, Settings.INVESTOR_PASSWORD).goToAccountOverviewPage()
				.simpleSearchByString("Generali").goToAccountHoldingsPageByName("Generali Vision - TWRR")
				.goToDetailsPage();

		assertTrue(!pageContainsStr("System Creation Date"));

		assertTrue(!pageContainsStr("Created By"));

	}

	@Test
	public void testSystemCreationInAdminView() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage().simpleSearchByString("Generali")
				.goToAccountHoldingsPageByName("Generali Vision - TWRR").goToDetailsPage();

		assertTrue(this
				.getTextByLocator(By
						.xpath(".//*[@id='gwt-debug-LogsSectionView-contentTable']//td[@class='overviewSectionLabel']"))
				.equals("System Creation Date"));

		assertTrue(this
				.getTextByLocator(
						By.xpath("//tr[.='Other Info']/following-sibling::tr[contains(., 'Created By:')]/td[2]"))
				.contains("Selenium"));

	}

	@Test
	public void testOverridePolicyDetails() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);
		MenuBarPage mbPage = main.loginAs(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD);
		// wait(5);

		AccountOverviewPage accountOverviewPage = mbPage.goToAccountOverviewPage();

		accountOverviewPage.simpleSearchByString("Moventum - A6946841")
				.goToAccountHoldingsPageByName("Moventum - A6946841").goToDetailsPage()
				.goToEditPageByField("Policy Details");

		for (int i = 1; i <= 24; i++) {
			try {
				String checked = webDriver.findElement(
						By.xpath("(.//*[@id='gwt-debug-ProductDetailsWidget-dialogBox-content']//tbody)[1]/tr[" + i
								+ "]//span/input"))
						.getAttribute("checked");

				String disabled = webDriver.findElement(
						By.xpath("((.//*[@id='gwt-debug-ProductDetailsWidget-dialogBox-content']//tbody)[1]/tr[" + i
								+ "]//td/*)[contains(@class,'formTextBox')]"))
						.getAttribute("disabled");

				System.out.print(i + ": ");

				if (checked == null) {

					assertTrue(disabled.equals("true"));

					log("disabled");

				} else if (checked.equals("true")) {

					assertTrue(disabled == null);

					log("enabled");

				}

			} catch (org.openqa.selenium.NoSuchElementException e) {
				// TODO: handle exception
			}

		}

	}

	@Test
	public void testCreatedByFieldIsHidden() throws Exception {

		String field = "Investor Accounts/Model Portfolios";
		String name_field = " Name: ";
		String name = "Test" + getRandName();
		String owner_field = " Owned by user: ";
		String owner = "Selenium Investor (key:8462) (SeleniumTest)";
		String type = "Insurance";
		String platform = "Generali Vision";
		String currency = "USD";
		String status = "In Force";
		LoginPage main = new LoginPage(webDriver);
		MenuBarPage mbPage = main.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD);
		wait(5);

		mbPage.goToAdminEditPage().editAdminThisField(field).clickNewButtonToCreateNewToken();
		AdminEditPage adEdit = new AdminEditPage(webDriver);

		waitForElementVisible(By.xpath("//button[.='Submit']"), 600);

		adEdit.inputValueToCertainField(name_field, name)
				// .selectOptionForCertainField(owner_field, owner)
				.selectOptionValues(".//*[@id='gwt-debug-AdminInvestorAccountEdit-accountType']", type)
				.selectOptionValues(".//*[@id='gwt-debug-AdminInvestorAccountEdit-platformListBox']", platform)
				.selectOptionValues(".//*[@id='gwt-debug-AdminInvestorAccountEdit-accountStatusListBox']", status)
				.selectOptionValues(".//*[@id='gwt-debug-AdminInvestorAccountEdit-accountCurrency']", currency)
				.clickSubmitButton().clickOkButton();
		wait(Settings.WAIT_SECONDS);
		this.checkLogout();
		handleAlert();

		// check Selenium Test
		LoginPage main2 = new LoginPage(webDriver);
		DetailPage detail = main2.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage()
				.checkIncludeNonAssetAccounts(true).simpleSearchByString(name).goToAccountDetailPageByName(name);
		assertFalse(pageContainsStr("Created By"));
		detail.goToEditPageByField("Details").clickDeleteButton_AccountDetail();
	}
}
