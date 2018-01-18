package org.sly.uitest.sections.companysettings;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.abstractpage.MenuBarPage;
import org.sly.uitest.pageobjects.clientsandaccounts.DetailPage;
import org.sly.uitest.pageobjects.companyadmin.CustomTagPage;
import org.sly.uitest.settings.Settings;

/**
 * @author Lynne Huang
 * @date : 22 Sep, 2015
 * @company Prive Financial
 */
public class CustomTagTest extends AbstractPage {

	@Test
	public void testCreateAndEditAndDeleteCustomTag() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		// create client and account tags
		String investorName = "Client" + this.getRandName();
		String investorType = "Client";
		String investorDescription = "Description" + this.getRandName();

		String accountName = "Account" + this.getRandName();
		String accountType = "Account";
		String accountDescription = "Description" + this.getRandName();

		String creationDate = getCurrentTimeInFormat("d-MMM-yyyy");

		CustomTagPage tags = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToCustomTagPage()
				.clickCreateCustomTagButton().editTagName(investorName).editTagColor()
				.editTagDescription(investorDescription).editTagTargetType(investorType).clickSaveButton();

		waitForElementNotVisible(By.id("gwt-debug-EditCustomTagPopupPresenterWidgetView-saveBtn"),
				Settings.WAIT_SECONDS);

		tags.clickCreateCustomTagButton().editTagColor().editTagDescription(accountDescription).editTagName(accountName)
				.editTagTargetType(accountType).clickSaveButton();

		clickNextPageUntilFindElement(
				By.xpath(".//*[@align='left' and contains(@id,'gwt-debug-SortableFlexTableAsync-table')]"),
				By.xpath(
						".//*[@align='left' and contains(@id,'gwt-debug-SortableFlexTableAsync-table') and contains(text(),'"
								+ investorName + "')]"));
		assertTrue(pageContainsStr(investorName));
		assertTrue(pageContainsStr(creationDate));

		main.goToAccountOverviewPage().goToCustomTagPage();
		clickNextPageUntilFindElement(
				By.xpath(".//*[@align='left' and contains(@id,'gwt-debug-SortableFlexTableAsync-table')]"),
				By.xpath(
						".//*[@align='left' and contains(@id,'gwt-debug-SortableFlexTableAsync-table') and contains(text(),'"
								+ accountName + "')]"));
		assertTrue(pageContainsStr(accountName));
		assertTrue(pageContainsStr(creationDate));
		// edit client and account tags

		String editInvestorName = "Client" + this.getRandName();
		String editAccountName = "Account" + this.getRandName();

		this.clickNextPageUntilFindElement(
				By.xpath(
						".//*[@id='gwt-debug-SortableFlexTableAsync-table']//tbody//tr//button[@id='gwt-debug-CustomTagOverviewPresenter-editButton']"),
				By.xpath(".//*[.='" + investorName + "']"));

		tags.editCustomTagByName(investorName)
				// .editTagName(investorName)
				.editTagName(editInvestorName).clickSaveButton();

		waitForElementVisible(By.id("gwt-debug-CustomTagOverviewView-createCustomTagButton"), 10);

		log(accountName);

		tags.editCustomTagByName(accountName).editTagName(editAccountName).clickSaveButton();

		tags.clickNextPageUntilFindElement(
				By.xpath(".//*[contains(@id,'gwt-debug-SortableFlexTableAsync-table-') and @align='left']"),
				By.xpath(".//*[contains(text(),'" + editInvestorName + "')]"));
		assertTrue(pageContainsStr(editInvestorName));
		tags.goToCustomTagPage();
		tags.clickNextPageUntilFindElement(
				By.xpath(".//*[contains(@id,'gwt-debug-SortableFlexTableAsync-table-') and @align='left']"),
				By.xpath(".//*[contains(text(),'" + editAccountName + "')]"));
		assertTrue(pageContainsStr(editAccountName));

		log("investorName: " + investorName);
		assertFalse(isElementVisible(By
				.xpath(".//*[@align='left' and contains(@id,'gwt-debug-SortableFlexTableAsync-table') and contains(text(),'"
						+ investorName + "')]")));
		assertFalse(isElementVisible(By
				.xpath(".//*[@align='left' and contains(@id,'gwt-debug-SortableFlexTableAsync-table') and contains(text(),'"
						+ accountName + "')]")));

		// delete client and account tags
		tags.deleteCustomTagByName(editInvestorName);
		assertFalse(isElementVisible(By
				.xpath(".//*[@align='left' and contains(@id,'gwt-debug-SortableFlexTableAsync-table') and contains(text(),'"
						+ editInvestorName + "')]")));
		// wait(3);
		tags.deleteCustomTagByName(editAccountName);

		assertFalse(isElementVisible(By
				.xpath(".//*[@align='left' and contains(@id,'gwt-debug-SortableFlexTableAsync-table') and contains(text(),'"
						+ editAccountName + "')]")));

	}

	@Test
	public void testInvestorTagCanBeTagged() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		// create client and account tags
		String investorName = "Investor" + this.getRandName();
		String investorType = "Client";
		String investorDescription = "Description" + this.getRandName();

		String creationDate = getCurrentTimeInFormat("d-MMM-yyyy");

		CustomTagPage tags = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToCustomTagPage()
				.clickCreateCustomTagButton().editTagName(investorName).editTagColor()
				.editTagDescription(investorDescription).editTagTargetType(investorType).clickSaveButton();

		clickNextPageUntilFindElement(
				By.xpath(".//*[@align='left' and contains(@id,'gwt-debug-SortableFlexTableAsync-table')]"),
				By.xpath(
						".//*[@align='left' and contains(@id,'gwt-debug-SortableFlexTableAsync-table') and contains(text(),'"
								+ investorName + "')]"));
		assertTrue(pageContainsStr(investorName));
		assertTrue(pageContainsStr(creationDate));

		// check if it's tagged
		DetailPage detail = tags.goToAccountOverviewPage().simpleSearchByString("Selenium")
				.goToClientDetailPageByName("Investor, Selenium").goToEditPageByField("Additional Details")
				.editTagByTagname(investorName).clickSaveButton_ClientAdditionalDetail();
		this.waitForElementVisible(By.xpath(".//*[contains(text(),'" + investorName + "')]"), Settings.WAIT_SECONDS);
		assertTrue(pageContainsStr(investorName));

		detail.goToCustomTagPage().deleteCustomTagInUseByName(investorName);
	}

	@Test
	public void testAccountTagCanBeTagged() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		// create client and account tags
		String accountName = "Account" + this.getRandName();
		String accountType = "Account";
		String accountDescription = "Description" + this.getRandName();

		String creationDate = getCurrentTimeInFormat("d-MMM-yyyy");

		CustomTagPage tags = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToCustomTagPage()
				.clickCreateCustomTagButton().editTagName(accountName).editTagColor()
				.editTagDescription(accountDescription).editTagTargetType(accountType).clickSaveButton();

		assertTrue(pageContainsStr(accountName));
		assertTrue(pageContainsStr(creationDate));

		// check if it's tagged
		DetailPage detail = tags.goToAccountOverviewPage().simpleSearchByString("Selenium")
				.goToAccountHoldingsPageByName("Selenium Test").goToDetailsPage().goToEditPageByField("Details")
				.editTagByTagname(accountName).clickSaveButton_AccountDetail();

		assertTrue(pageContainsStr(accountName));

		detail.goToCustomTagPage().deleteCustomTagInUseByName(accountName);
	}

	@Test
	public void testCustomTagModuleToggle() throws Exception {

		LoginPage main = new LoginPage(webDriver);
		String module = "MODULE_CUSTOMTAG";

		MenuBarPage menuBarPage = main.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD);

		menuBarPage.goToAdminEditPage().editAdminThisField(Settings.Advisor_Company_Module_Permission)
				.jumpByKeyAndLoad(Settings.AdvisorABCCompany_Key).editModuleToggle(module, false, true)
				.clickSubmitButton().jumpByKeyAndLoad(Settings.InfinityFinancialSolution_Key)
				.editModuleToggle(module, false, false).clickSubmitButton();
		// wait(Settings.WAIT_SECONDS);
		this.checkLogout();
		handleAlert();

		// check JarlAdvisor
		LoginPage main2 = new LoginPage(webDriver);
		MenuBarPage menuBarpage2 = main2.loginAs(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD);

		menuBarpage2.goToCustomTagPage();
		this.checkLogout();
		handleAlert();

		// check InfinityAdmin
		LoginPage main3 = new LoginPage(webDriver);
		main3.loginAs(Settings.CRM_USERNAME, Settings.CRM_PASSWORD);
		// the custom tag page should not be reached as it is disabled in
		// the previous steps so here we use try-catch
		try {
			main3.goToCustomTagPage();
		} catch (Exception e) {
			assertFalse(isElementVisible(By.id("gwt-debug-CustomTagOverviewView-customTagTablePanel")));
		}
		this.checkLogout();
		handleAlert();
		// turn on for Infinity Admin
		LoginPage main4 = new LoginPage(webDriver);
		MenuBarPage menuBarPage2 = main4.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD);
		menuBarPage2.goToAdminEditPage().editAdminThisField(Settings.Advisor_Company_Module_Permission)
				.jumpByKeyAndLoad(Settings.InfinityFinancialSolution_Key).editModuleToggle(module, false, true)
				.clickSubmitButton();
		this.checkLogout();
		handleAlert();

		// check InfinityAdmin
		LoginPage main5 = new LoginPage(webDriver);

		main5.loginAs(Settings.CRM_USERNAME, Settings.CRM_PASSWORD).goToCustomTagPage();
		assertTrue(isElementVisible(By.id("gwt-debug-CustomTagOverviewView-customTagTablePanel")));
		this.checkLogout();
		handleAlert();
	}

	// // To handle failure of going to custom tag page
	// public void goToCustomTagPageForCustomTagPage() throws
	// InterruptedException {
	// boolean isMaterial = checkIfMaterial();
	// try {
	// if (isMaterial) {
	// this.goToCustomTagPageMaterialViewForCustomTagPage();
	// } else {
	// navigateToPage(By.xpath("//a[@title='Company Settings']/img"));
	//
	// assertTrue(pageContainsStr("Custom Tag"));
	//
	// navigateToPage(By.id("gwt-debug-ImageMenuButton-CustomTag"));
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// }

	// public void goToCustomTagPageMaterialViewForCustomTagPage()
	// throws InterruptedException {
	// wait(4);
	// try {
	// if (isElementVisible(By
	// .xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Company
	// Admin']/following-sibling::ul[@class='navmat navmat-second-level collapse
	// in']")))
	// {
	// navigateToPage(By
	// .xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Custom
	// Tag']"));
	//
	// } else {
	// navigateToPageMaterialView(
	// By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Company
	// Admin']"),
	// By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Custom
	// Tag']"));
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// }
}
