package org.sly.uitest.sections.companysettings;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;

import org.junit.Test;
import org.openqa.selenium.By;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.abstractpage.MenuBarPage;
import org.sly.uitest.pageobjects.clientsandaccounts.AccountOverviewPage;
import org.sly.uitest.pageobjects.companyadmin.CustomAssetPage;
import org.sly.uitest.pageobjects.investments.InvestmentsPage;
import org.sly.uitest.settings.Settings;

/**
 * Test custom asset under company settings
 * 
 * @author Jerry Yu
 * @date : Jul 28, 2014
 * @company Prive Financial
 */
public class CustomAssetTest extends AbstractTest {

	@Test
	public void createAndEditAndDeleteAlternative() throws InterruptedException, ParseException {

		LoginPage main = new LoginPage(webDriver);
		// create Alternative
		String type = "Alternative";
		String name = "Alternative" + this.getRandName();
		String currency = "CAD";
		String description = "Description" + this.getRandName();
		String benchmark = "AMEX Biotechnology PR USD";
		// Data not found in drop down.
		// String manager = "ABC Manager";
		String manager = "AMTD";
		String timezone = "GMT+12 (Auckland, Suva)";
		String platform = "ABN Amro TBA";
		String isin = "ISIN" + this.getRandName();
		String region = "Global";
		String date = getCurrentTimeInFormat("dd-MMM-yyyy");

		String format = "MMM dd, yyyy";
		String currentTime = getCurrentTimeInFormat(format);
		String yesterday = getDateAfterDays(currentTime, -1, format);

		String systemTimezone = "GMT+8 (Perth, Beijing, Manila, Singapore, Kuala Lumpur, Denpasar, Krasnoyarsk)";
		AccountOverviewPage account = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToUserProfilePage()
				.editTimeZone(systemTimezone).updateGeneralInformation();
		CustomAssetPage asset = account.goToCustomAssetPage().clickCreateAssetButton().editAssetType(type)
				.clickProceedButton().editAssetName(name).editAssetRegion(region).editAssetMaturityDate(date)
				.editAssetDescription(description).editAssetCurrency(currency).editAssetBenchmark(benchmark)
				.editAssetManager(manager).editAssetTimeZone(timezone).editAddExecutionPlatform(platform)
				.editAssetISIN(isin).clickSaveButton().simpleSearch(name);

		assertTrue(pageContainsStr(name));
		assertTrue(pageContainsStr(currency));
		assertTrue(pageContainsStr(isin));

		try {
			assertTrue(pageContainsStr(currentTime));
		} catch (AssertionError e) {
			assertTrue(pageContainsStr(yesterday));
		}

		// edit Alternative
		String name_edit = "Alternative" + this.getRandName();
		String currency_edit = "USD";
		String isin_edit = "ISIN" + this.getRandName();

		asset.editAssetByName(name).editAssetName(name_edit).editAssetCurrency(currency_edit).editAssetISIN(isin_edit)
				.clickSaveButton().simpleSearch(name_edit);

		waitForElementVisible(By.id("gwt-debug-TabCustomAssetView-customAssetName"), 30);

		int page = getPagesOfElements(By.id("gwt-debug-TabCustomAssetView-customAssetName"));

		assertTrue(pageContainsStrMultiPages(name_edit, page));
		assertTrue(pageContainsStr(currency_edit));
		assertTrue(pageContainsStr(isin_edit));

		assertFalse(pageContainsStr(name));

		try {
			assertTrue(pageContainsStr(currentTime));
		} catch (AssertionError e) {
			assertTrue(pageContainsStr(yesterday));
		}

		// delete Alternative

		asset.deleteAssetByName(name_edit);
	}

	@Test
	public void createAndEditAndDeleteFX() throws InterruptedException, ParseException {

		LoginPage main = new LoginPage(webDriver);

		// create Alternative
		String type = "FX Forward";
		String name = "FX Forward" + this.getRandName();
		String forwardType1 = "Non-Deliverable Forward";
		String forwardType2 = "Outright Forward";
		String currency = "AUD";
		String counterCurrency = "HKD";
		String rate = "5\n";
		String platform = "ABN Amro TBA";
		String systemTimezone = "GMT+8 (Perth, Beijing, Manila, Singapore, Kuala Lumpur, Denpasar, Krasnoyarsk)";
		String format = "MMM dd, yyyy";
		String currentTime = getCurrentTimeInFormat(format);
		String yesterday = getDateAfterDays(currentTime, -1, format);

		AccountOverviewPage account = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToUserProfilePage()
				.editTimeZone(systemTimezone).updateGeneralInformation();
		CustomAssetPage asset = account.goToCustomAssetPage().goToCustomAssetPage().clickCreateAssetButton()
				.editAssetType(type).clickProceedButton().editAssetName(name).editForwardType(forwardType1)
				.editAssetCurrencyAndCounterCurrency(currency, counterCurrency).editAssetForwardExchangeRate(rate)
				.editSettlementCurrency(currency).editAddExecutionPlatform(platform).editValueDate(currentTime)
				.clickSaveButton();

		log(currentTime);
		scrollToTop();
		int page = getPagesOfElements(By.xpath(".//*[@id='gwt-debug-TabCustomAssetView-customAssetName']"));

		assertTrue(pageContainsStrMultiPages(name, page));
		assertTrue(pageContainsStr(currency));
		assertTrue(pageContainsStr("FX"));

		try {
			assertTrue(pageContainsStr(currentTime));
		} catch (AssertionError e) {
			assertTrue(pageContainsStr(yesterday));
		}
		// edit Alternative
		String name_edit = "FX" + this.getRandName();
		String currency_edit = "USD";
		this.waitForElementVisible(By.id("gwt-debug-TabCustomAssetView-editButton"), Settings.WAIT_SECONDS);
		asset.editAssetByName(name).editAssetName(name_edit)
				.editAssetCurrencyAndCounterCurrency(currency_edit, counterCurrency).editForwardType(forwardType2)
				.editValueDate(currentTime).clickSaveButton();

		page = getPagesOfElements(By.xpath(".//*[@id='gwt-debug-TabCustomAssetView-customAssetName']"));

		assertTrue(pageContainsStrMultiPages(name_edit, page));
		assertTrue(pageContainsStr(currency_edit));

		assertFalse(pageContainsStr(name));
		try {
			assertTrue(pageContainsStr(currentTime));
		} catch (AssertionError e) {
			assertTrue(pageContainsStr(yesterday));
		}

		// delete Alternative
		scrollToTop();
		asset.deleteAssetByName(name_edit);
	}

	@Test
	public void createAndEditAndDeleteAssetPlaceholder() throws InterruptedException, ParseException {
		LoginPage main = new LoginPage(webDriver);

		String module = "MODULE_ILLUSTRATION";
		String assetType = "Asset Placeholder Fixed Rate";
		String name1 = "testPlaceholder" + getRandName();
		String name2 = "testPlaceholder" + getRandName();
		String currency1 = "USD";
		String currency2 = "EUR";
		String systemTimezone = "GMT+8 (Perth, Beijing, Manila, Singapore, Kuala Lumpur, Denpasar, Krasnoyarsk)";
		String format = "MMM dd, yyyy";
		String currentTime = getCurrentTimeInFormat(format);
		String yesterday = getDateAfterDays(currentTime, -1, format);

		// turn on module_toggle
		main.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD).goToAdminEditPage()
				.editAdminThisField(Settings.Advisor_Company_Module_Permission)
				.jumpByKeyAndLoad(Settings.SeleniumTest_Key).editModuleToggle(module, false, true).clickSubmitButton();

		this.checkLogout();

		// login as JarlAdvisor
		LoginPage main2 = new LoginPage(webDriver);
		AccountOverviewPage account = main2.loginAs(Settings.USERNAME, Settings.PASSWORD).goToUserProfilePage()
				.editTimeZone(systemTimezone).updateGeneralInformation();

		CustomAssetPage customAsset = account.goToCustomAssetPage().clickCreateAssetButton().editAssetType(assetType)
				.clickProceedButton().editAssetName(name1).editAssetCurrency(currency1).editAssetBaseRate(1)
				.editAssetTenor(1);

		String platform = getTextByLocator(
				By.xpath("(.//select[@id='gwt-debug-PairedListBoxSelector-sourceList']/option)[1]"));

		customAsset.editAddExecutionPlatform(platform).clickSaveButton();

		scrollToTop();
		int page = getPagesOfElements(By.xpath(".//*[@id='gwt-debug-TabCustomAssetView-customAssetName']"));

		assertTrue(pageContainsStrMultiPages(name1, page));
		assertTrue(pageContainsStr(currency1));
		assertTrue(pageContainsStr("IRTO"));

		try {
			assertTrue(pageContainsStr(currentTime));
		} catch (AssertionError e) {
			assertTrue(pageContainsStr(yesterday));
		}

		this.waitForElementVisible(By.id("gwt-debug-TabCustomAssetView-editButton"), Settings.WAIT_SECONDS);
		customAsset.editAssetByName(name1).editAssetName(name2).editAssetCurrency(currency2).clickSaveButton();

		assertTrue(pageContainsStrMultiPages(name2, page));
		assertTrue(pageContainsStr(currency2));
		assertTrue(pageContainsStr("IRTO"));

		try {
			assertTrue(pageContainsStr(currentTime));
		} catch (AssertionError e) {
			assertTrue(pageContainsStr(yesterday));
		}

		customAsset.goToCustomAssetPage().deleteAssetByName(name2);
	}

	@Test
	public void testCustomAssetInInvestmentList() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		String type = "Property & Land";
		String name = "Property & Land" + this.getRandName();
		String currency = "CNY";
		String manager = "AMTD";
		String platform = "Ageas Aviator";
		String date = "05/07/2015";
		String value = "603123";
		String region = "Global";
		String systemTimezone = "GMT+8 (Perth, Beijing, Manila, Singapore, Kuala Lumpur, Denpasar, Krasnoyarsk)";

		// wait(3);

		AccountOverviewPage account = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToUserProfilePage()
				.editTimeZone(systemTimezone).updateGeneralInformation();

		InvestmentsPage invest = account.goToCustomAssetPage().clickCreateAssetButton().editAssetType(type)
				.clickProceedButton().editAssetName(name).editAssetRegion(region).editAssetCurrency(currency)
				.editAssetManager(manager).editAddExecutionPlatform(platform).goToHistoryData()
				.editHistoricalData(date, value).clickSaveButton().goToInvestmentsPage()
				.simpleSearchByNameWithButton(name);

		waitForElementVisible(By.id("gwt-debug-ManagerListWidgetView-managerListPanel"), 120);

		// delete the custom asset
		invest.goToCustomAssetPage().deleteAssetByName(name);

		log("Test custom asset is in the Investment page: Checked");
	}

	@Test
	public void testCustomAssetModuleToggle() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		String module = "MODULE_CUSTOMASSET";

		// turn on for JarlAdvisor; turn off for InfinityAdmin
		// wait(3);
		MenuBarPage menuBarPage = main.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD);
		// wait(5);

		menuBarPage.goToAdminEditPage().editAdminThisField(Settings.Advisor_Company_Module_Permission)
				.jumpByKeyAndLoad(Settings.AdvisorABCCompany_Key).editModuleToggle(module, false, true)
				.clickSubmitButton().jumpByKeyAndLoad(Settings.InfinityFinancialSolution_Key)
				.editModuleToggle(module, false, false).clickSubmitButton();
		// wait(Settings.WAIT_SECONDS);
		checkLogout();
		handleAlert();

		// check JarlAdvisor
		LoginPage main2 = new LoginPage(webDriver);
		// wait(3);
		MenuBarPage page2 = main2.loginAs(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD);
		// wait(5);

		page2.goToCustomAssetPage();
		this.checkLogout();
		// wait(Settings.WAIT_SECONDS);
		handleAlert();

		// check InfinityAdmin
		LoginPage main3 = new LoginPage(webDriver);

		// wait(3);
		MenuBarPage mbp3 = main3.loginAs(Settings.CRM_USERNAME, Settings.CRM_PASSWORD);
		// wait(5);

		mbp3.noCustomAssetPage();
		this.checkLogout();
		// wait(Settings.WAIT_SECONDS);
		handleAlert();

		// turn on for Infinity Admin
		LoginPage main4 = new LoginPage(webDriver);
		// wait(3);
		MenuBarPage menuBarPage2 = main4.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD);
		// wait(5);

		menuBarPage2.goToAdminEditPage().editAdminThisField(Settings.Advisor_Company_Module_Permission)
				.jumpByKeyAndLoad(Settings.InfinityFinancialSolution_Key).editModuleToggle(module, false, true)
				.clickSubmitButton();
		this.checkLogout();
		// wait(Settings.WAIT_SECONDS);
		handleAlert();

		// check InfinityAdmin
		LoginPage main5 = new LoginPage(webDriver);
		// wait(3);
		main5.loginAs(Settings.CRM_USERNAME, Settings.CRM_PASSWORD).goToCustomAssetPage();

		// wait(Settings.WAIT_SECONDS);
		this.checkLogout();
		handleAlert();
	}

}
