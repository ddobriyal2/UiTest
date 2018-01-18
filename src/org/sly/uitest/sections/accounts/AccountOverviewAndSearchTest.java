package org.sly.uitest.sections.accounts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.AWTException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.pageobjects.abstractpage.AdvancedSearchPage;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.abstractpage.MenuBarPage;
import org.sly.uitest.pageobjects.admin.AdminEditPage;
import org.sly.uitest.pageobjects.clientsandaccounts.AccountOverviewPage;
import org.sly.uitest.pageobjects.clientsandaccounts.BulkOperationPage;
import org.sly.uitest.pageobjects.clientsandaccounts.ClientOverviewPage;
import org.sly.uitest.pageobjects.clientsandaccounts.DetailPage;
import org.sly.uitest.pageobjects.clientsandaccounts.HoldingsPage;
import org.sly.uitest.pageobjects.companyadmin.CompanySettingsPage;
import org.sly.uitest.pageobjects.investments.InvestmentsPage;
import org.sly.uitest.settings.Settings;

/**
 * Checks the account overview page.
 * 
 * @author Julian Schillinger
 * @date : Jan 2, 2014
 * @company Prive Financial
 */
public class AccountOverviewAndSearchTest extends AbstractPage {

	@Test
	public void testCreateNewInvestorAccount() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		String firstName = "First" + getRandName();
		String lastName = "Last" + getRandName();
		String accountName = "Account" + getRandName();
		String feed = "broker" + getRandName();
		String accountType = "REAL CLIENT ACCOUNT";

		ClientOverviewPage newClientCreated = ((AbstractPage) main.loginAs(Settings.USERNAME, Settings.PASSWORD)
				.goToAccountOverviewPage().clickNewClientAccountButton().editClientTitle("Mr.")
				.editClientFirstName(firstName).editClientLastName(lastName).editClientNationality("China")
				.editClientResidence("Hong Kong").editClientDayOfBirth("17").editClientMonthOfBirth("May")
				.editClientYearOfBirth("1990").editClientIDNumber("1234567").editClientOccupation("TestOccupation")
				.editClientPosition("TestPosition").clickSaveButton_ClientAdditionalDetail()).goToClientOverviewPage();

		ClientOverviewPage searchClient = newClientCreated.goToAdvancedSearchPage().searchByClientName(firstName)
				.clickSearchButton();

		assertTrue(pageContainsStr(firstName));
		assertTrue(pageContainsStr(lastName));

		AccountOverviewPage newAccountCreated = ((AbstractPage) searchClient
				.goToClientDetailPageByName(lastName + ", " + firstName).goToAccountsTab()
				.createNewAccount_NonNBE(accountType).editAccountName(accountName)
				.editAddMainAdvisorByNameInvDet("Selenium Consultant").editAccountType("Insurance")
				.editAccountDataFeedAccountId(feed).editAccountProductProvider("AIG")
				.editAccountProduct("AIG Working Holiday Protection").editAccountUpdateSource("Automatic")
				.editAccountCurrency("EUR").clickSaveButton_AccountDetail()).goToAccountOverviewPage();

		((AccountOverviewPage) newAccountCreated.simpleSearchByString(firstName)).checkIncludeInactiveAccounts(true);

		assertTrue(pageContainsStr("No information found"));

		((AccountOverviewPage) newAccountCreated.simpleSearchByString(firstName)).checkIncludeNonAssetAccounts(true);

		assertTrue(pageContainsStr(accountName));
		assertTrue("Only expected one client result.", pageContainsStr("1 - 1 of 1 Results"));

		// delete the account and client
		newAccountCreated.goToClientDetailPageByName(lastName + ", " + firstName).goToAccountsTab()
				.selectAccountByName(accountName).goToEditPageByField("Details")
				.clickDeleteButtonByLocator(By.id("gwt-debug-InvestorAccountDetailWidget-deleteBtn"))
				.goToClientOverviewPage().simpleSearchByString(lastName)
				.goToClientDetailPageByName(lastName + ", " + firstName).goToEditPageByField("Additional Details")
				.clickDeleteButtonByLocator(By.id("gwt-debug-UserDetailWidget-deleteBtn"));

	}

	@Test
	public void testAccountOverviewAndMakeSureNoModeProtfolioResult() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		AccountOverviewPage login = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage();

		/*
		 * Base page
		 */
		int page = getPagesOfElements(By.id("gwt-debug-InvestorAccountTable-linkPortfolioName"));

		assertTrue("Expected specific account.", pageContainsStrMultiPages("Selenium Test", page));

		assertTrue("Expected specific investor.", pageContainsStrMultiPages("Investor, Selenium", page));

		/*
		 * Search
		 */

		login.goToAdvancedSearchPage().searchByClientName("Selenium").clickSearchButton();

		assertTrue("Expected specific account.", pageContainsStrMultiPages("Selenium Test", page));

		assertTrue("Expected specific investor result.", pageContainsStrMultiPages("Investor, Selenium", page));
		// assertTrue("Expected specific account result.",
		// pageContainsStr("Selenium Test"));

		// ensure model portfolios do NOT show up - see SLYAWS-2976
		assertFalse("Did not expect model portfolio in results.", pageContainsStr("Sample Model Portfolio"));

	}

	@Test
	public void testSearchAccountByAccountName() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage().goToAdvancedSearchPage()
				.searchByAccountName("Generali Vision").clickSearchButton();

		assertTrue(pageContainsStr("Generali Vision - NEW"));

		checkLogout();
		handleAlert();

		LoginPage main1 = new LoginPage(webDriver);
		main1.loginAs(Settings.CRM_USERNAME, Settings.CRM_PASSWORD).goToAccountOverviewPage().goToAdvancedSearchPage()
				.searchByAccountName("FPIL").clickSearchButton();

		assertTrue(pageContainsStr("FPIL Premier Advance - 16711"));

		main1.goToAccountOverviewPage().goToAdvancedSearchPage().searchByAccountName("FUTURA").clickSearchButton();

		int page = getPagesOfElements(By.id("gwt-debug-InvestorAccountTable-linkPortfolioName"));

		assertTrue(pageContainsStrMultiPages("ZIL Futura II - 7735", page));

	}

	@Test
	public void testSearchAccountByFundRegion() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		handleAlert();
		HoldingsPage investmentsPage = (HoldingsPage) main.loginAs(Settings.USERNAME, Settings.PASSWORD)
				.goToAccountOverviewPage().simpleSearchByString("Selenium Test")
				.goToAccountHoldingsPageByName("Selenium Test");
		clickElement(By.id("gwt-debug-PortfolioAllocationView-reallocateBtn"));
		if (pageContainsStr("This account cannot be manually rebalanced as it follows a model portfolio.")) {
			clickOkButtonIfVisible();
			DetailPage detailPage = (DetailPage) investmentsPage.goToDetailsPage()
					.goToEditPageByField("Model Portfolio").unlinkModelPortfolio();
		}
		checkLogout();
		// wait(Settings.WAIT_SECONDS);
		handleAlert();

		InvestmentsPage findInvestment = ((InvestmentsPage) main.loginAs(Settings.USERNAME, Settings.PASSWORD)
				.goToAccountOverviewPage().simpleSearchByString("Selenium Test")
				.goToAccountHoldingsPageByName("Selenium Test").clickReallocateButton().clickAddInvestmentButton()
				.goToAdvancedSearchPage().searchByAccountFundRegion("Europe Developed").clickSearchButton());

		String investment = getTextByLocator(By.id("gwt-debug-ManagerListItem-strategyName"));
		AccountOverviewPage newInvestment = ((HoldingsPage) findInvestment.selectInvestmentByName(investment)
				.clickAddToPortfolioButtonToHoldingPage()).setNewAllocationForInvestment(investment, "10")
						.clickRebalancePreviewAndConfirm().goToHoldingsPage().goToTransactionHistoryPage()
						.confirmHistoryStatus().goToAccountOverviewPage();

		// wait(Settings.WAIT_SECONDS);

		newInvestment.checkIncludeInactiveAccounts(true).checkIncludeNonAssetAccounts(true).goToAdvancedSearchPage()
				.searchByAccountFundRegion("Europe Developed").clickSearchButton();

		assertTrue(pageContainsStr("Selenium Test"));
		scrollToTop();
		// delete the investment
		newInvestment.goToAccountHoldingsPageByName("Selenium Test").clickReallocateButton()
				.setNewAllocationForInvestment(investment, "0").clickRebalancePreviewAndConfirm();

	}

	@Test
	public void testSearchAccountByFundManagerName() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage().goToAdvancedSearchPage()
				.searchByProductFundManager("hsbc").clickSearchButton();

		scrollToTop();
		assertTrue(pageContainsStr("Generali Vision - TWRR"));

		checkLogout();
		handleAlert();

		LoginPage main2 = new LoginPage(webDriver);
		main2.loginAs(Settings.CRM_USERNAME, Settings.CRM_PASSWORD).goToAccountOverviewPage()
				.checkIncludeInactiveAccounts(true).goToAdvancedSearchPage().searchByProductFundManager("LM")
				.clickSearchButton();
		scrollToTop();
		assertTrue(pageContainsStr("LMIM Currency Protected Australian Income - 32211"));
	}

	@Test
	public void testSearchAccountByAdvisor() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage().goToAdvancedSearchPage()
				.searchByAccountAdvisor("Selenium").clickSearchButton();

		assertTrue(pageContainsStr("Selenium Test"));

	}

	@Test
	public void testSearchByAdvisorAndAccountName() throws Exception {
		By by = By.id("gwt-debug-InvestorAccountTable-linkPortfolioName");
		LoginPage main = new LoginPage(webDriver);
		main.loginAs(Settings.CRM_USERNAME, Settings.CRM_PASSWORD).goToAccountOverviewPage().goToAdvancedSearchPage()
				.searchByAccountAdvisor("Adon").searchByAccountName("Generali").clickSearchButton();

		this.waitForElementVisible(by, Settings.WAIT_SECONDS);
		assertTrue(this.getSizeOfElements(by) >= 1);
	}

	@Test
	public void testSearchAccountByAssetClass() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage().goToAdvancedSearchPage()
				.searchByProductAssetClass("Equity").clickSearchButton();

		assertTrue(pageContainsStr("Generali Vision"));

	}

	@Test
	public void testSearchAccountByInvestmentType() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage().goToAdvancedSearchPage()
				.searchByProductInvestmentType("Stock").clickSearchButton();

		assertTrue(pageContainsStr("Private Bank"));

	}

	@Test
	public void testSearchAccountByClientNationality() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		((DetailPage) main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage()
				.simpleSearchByString("Selenium").goToClientDetailPageByName("Investor, Selenium")
				.goToEditPageByField("Additional Details").editClientNationality("Bolivia")
				.clickSaveButton_ClientAdditionalDetail()).goToAccountOverviewPage().goToAdvancedSearchPage()
						.searchByClientNationality("Bolivia").clickSearchButton();
		waitForElementVisible(By.id("gwt-debug-InvestorAccountTable-linkPortfolioName"), 10);
		int page = getPagesOfElements(By.id("gwt-debug-InvestorAccountTable-linkPortfolioName"));
		assertTrue(pageContainsStrMultiPages("Investor, Selenium", page));
		assertTrue(pageContainsStrMultiPages("Selenium Test", page));

	}

	@Test
	public void testSearchAccountByClientCountryOfResidence() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		((DetailPage) main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage()
				.simpleSearchByString("Selenium").goToClientDetailPageByName("Investor, Selenium")
				.goToEditPageByField("Additional Details").editClientResidence("Cook Islands")
				.clickSaveButton_ClientAdditionalDetail()).goToAccountOverviewPage().goToAdvancedSearchPage()
						.searchByClientCountryOfResidence("Cook Islands").clickSearchButton();
		this.waitForElementVisible(By.id("gwt-debug-InvestorAccountTable-linkPortfolioName"), Settings.WAIT_SECONDS);
		int page = getPagesOfElements(By.id("gwt-debug-InvestorAccountTable-linkPortfolioName"));
		assertTrue(pageContainsStrMultiPages("Investor, Selenium", page));
		assertTrue(pageContainsStrMultiPages("Selenium Test", page));
	}

	@Test
	public void testSearchByInvestorTag() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		String investorTagName = "A" + getRandName();
		String type = "Client";

		// log(investorTagName);

		AccountOverviewPage addNewTagToClient = ((AbstractPage) main.loginAs(Settings.USERNAME, Settings.PASSWORD)
				.goToCustomTagPage().clickCreateCustomTagButton().editTagName(investorTagName).editTagColor()
				.editTagTargetType(type).clickSaveButton().goToAccountOverviewPage().simpleSearchByString("Selenium")
				.goToClientDetailPageByName("Investor, Selenium").goToEditPageByField("Additional Details")
				.editClientNationality("Bolivia").editClientDayOfBirth("17").editClientMonthOfBirth("May")
				.editClientYearOfBirth("1991").editClientIDNumber("test123").editClientOccupation("test_Occupation")
				.editTagByTagname(investorTagName).editClientPosition("test_Position")
				.clickSaveButton_ClientAdditionalDetail()).goToAccountOverviewPage();

		this.waitForElementVisible(By.xpath(".//*[contains(text(),'Summary of Accounts')]"), Settings.WAIT_SECONDS);
		// log(investorTagName);
		AccountOverviewPage afterSearch = addNewTagToClient.goToAdvancedSearchPage().searchByClientTag(investorTagName)
				.clickSearchButton();
		assertTrue(pageContainsStr("Investor, Selenium"));

		afterSearch.goToCustomTagPage().deleteCustomTagInUseByName(investorTagName);

	}

	@Test
	public void testSearchByInvestmentTag() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		String investmentTagName = "B" + getRandName();
		String type = "Account";

		DetailPage addNewTagToInvestment = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToCustomTagPage()
				.clickCreateCustomTagButton().editTagName(investmentTagName).editTagTargetType(type).editTagColor()
				.clickSaveButton().goToAccountOverviewPage().simpleSearchByString("Selenium Test")
				.goToAccountHoldingsPageByName("Selenium Test").goToDetailsPage().goToEditPageByField("Details")
				.editTagByTagname(investmentTagName).clickSaveButton_AccountDetail();

		AccountOverviewPage afterSearch = addNewTagToInvestment.goToAccountOverviewPage().goToAdvancedSearchPage()
				.searchByAccountTag(investmentTagName).clickSearchButton();

		assertTrue(pageContainsStr("Selenium Test"));

		afterSearch.goToCustomTagPage().deleteCustomTagInUseByName(investmentTagName);

	}

	@Test
	public void testSearchClientExternalReference() throws Exception {

		String exRefID = getRandName();

		LoginPage main = new LoginPage(webDriver);

		AccountOverviewPage editExRefID = ((AbstractPage) main.loginAs(Settings.USERNAME, Settings.PASSWORD)
				.goToAccountOverviewPage().simpleSearchByString("Selenium")
				.goToClientDetailPageByName("Investor, Selenium").goToEditPageByField("Additional Details")
				.editClientExternalSystemID(exRefID).clickSaveButton_ClientAdditionalDetail())
						.goToAccountOverviewPage();

		editExRefID.goToAdvancedSearchPage().searchByClientExternalReferenceID(exRefID).clickSearchButton();

		assertTrue(pageContainsStr("Investor, Selenium"));

	}

	@Test
	public void testSearchAccountByClientNameAndCommunicationStatus() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		AccountOverviewPage clientFound = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage()
				.goToAdvancedSearchPage().searchByClientName("Doe").clickSearchButton();
		scrollToTop();
		assertTrue(pageContainsStr("Doe, John"));

		// Enable Email Function
		AccountOverviewPage emailEnabled = ((AbstractPage) clientFound.goToClientDetailPageByName("Doe, John")
				.goToEditPageByField("Additional Details").editClientEmailFunction("Enabled")
				.clickSaveButton_ClientAdditionalDetail()).goToAccountOverviewPage();

		// test 1
		emailEnabled.goToAccountOverviewPage().goToAdvancedSearchPage().searchByClientEmailFunction("Disabled")
				.searchByClientName("Doe").clickSearchButton();
		scrollToTop();
		assertTrue(pageContainsStr("No information found"));
		assertFalse(pageContainsStr("Doe, John"));

		// test 2
		emailEnabled.goToAccountOverviewPage().goToAdvancedSearchPage().searchByClientEmailFunction("Enabled")
				.searchByClientName("Doe").clickSearchButton();
		scrollToTop();
		assertTrue(pageContainsStr("Doe, John"));

		// Disable EmailFunction
		AccountOverviewPage emailDisabled = ((AbstractPage) emailEnabled.goToClientDetailPageByName("Doe, John")
				.goToEditPageByField("Additional Details").editClientEmailFunction("Disabled")
				.clickSaveButton_ClientAdditionalDetail()).goToAccountOverviewPage();

		// test 3
		emailDisabled.goToAccountOverviewPage().goToAdvancedSearchPage().searchByClientEmailFunction("Disabled")
				.searchByClientName("Doe").clickSearchButton();
		scrollToTop();
		assertTrue(pageContainsStr("Doe, John"));

		// test 4
		emailDisabled.goToAccountOverviewPage().goToAdvancedSearchPage().searchByClientEmailFunction("Enabled")
				.searchByClientName("Doe").clickSearchButton();
		scrollToTop();
		assertTrue(pageContainsStr("No information found"));
		assertFalse(pageContainsStr("Doe, John"));

	}

	@Test
	// CANNOT CLICK THE CHECK BOX
	public void testSearchAccountByAccountStatus() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage().checkIncludeNonAssetAccounts(true)
				.checkIncludeInactiveAccounts(true).goToAdvancedSearchPage().searchByAccountStatus("Opened", "In Force")
				.clickSearchButton();

		waitForElementVisible(By.id("gwt-debug-InvestorAccountTable-linkPortfolioName"), 30);

		int page = getPagesOfElements(By.id("gwt-debug-InvestorAccountTable-linkPortfolioName"));

		assertTrue(pageContainsStrMultiPages("Selenium Test", page));
	}

	@Test
	public void testSearchServingOffice() throws Exception {

		LoginPage main = new LoginPage(webDriver);
		String oldOffice = "Hong Kong";
		String office = "TestServingOffice";
		String person = "Support Admin";

		CompanySettingsPage setting = main.loginAs(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD)
				.goToCompanySettingsPage().clickEditOfficesButton();

		if (!pageContainsStr(office)) {
			setting.clickAddOfficeButton(office, person).clickSaveOfficeButton();
			this.waitForWaitingScreenNotVisible();
		}

		setting.clickCloseOfficeButton().goToUserProfilePage().editServingOffice(office).updateGeneralInformation();

		this.waitForWaitingScreenNotVisible();
		this.checkLogout();
		handleAlert();

		LoginPage main2 = new LoginPage(webDriver);

		AccountOverviewPage accounts = main2.loginAs(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD)
				.goToAccountOverviewPage().goToAdvancedSearchPage().searchByClientServingOffice(office)
				.clickSearchButton();

		accounts.checkIncludeInactiveAccounts(true);
		waitForElementVisible(By.id("gwt-debug-InvestorAccountTable-linkPortfolioName"), 10);

		assertTrue(pageContainsStr("KO, Nate"));

		accounts.goToUserProfilePage().editServingOffice(oldOffice).updateGeneralInformation().goToCompanySettingsPage()
				.clickEditOfficesButton().clickDeleteOfficeIcon(office).clickCloseAdvisorCompanyOfficeButton()
				.clickSubmitButton();

	}

	@Test
	public void testSearchByCommunicationStatus() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		String platform = "Aviva SG Global Savings Account";
		String account = "Aviva Global Savings Account - 20315";
		String access = "Live";

		AccountOverviewPage accounts = ((AccountOverviewPage) main.loginAs(Settings.CRM_USERNAME, Settings.CRM_PASSWORD)
				.goToAccountOverviewPage().goToAdvancedSearchPage().searchByProductPlatform(platform, false)
				.clickSearchButton()).goToAccountHoldingsPageByName(account).goToDetailsPage().deleteAllAccountReports()
						.goToAccountOverviewPage();

		BulkOperationPage bulk = ((AccountOverviewPage) accounts.goToAdvancedSearchPage()
				.searchByAccountClientAccess(access).searchByProductPlatform(platform, false).clickSearchButton())
						.clickBulkOperationsButton().selectType("Accounts").selectAction("Generate Report");

		String lastDay = this.getAttributeStringByLocator(By.id("gwt-debug-DateRangePanel-endDateTextBox"), "value");

		DateFormat fromFormat = new SimpleDateFormat("d-MMM-yyyy");
		DateFormat toFormat = new SimpleDateFormat("yyyyMMdd");

		String lastDay_new = toFormat.format(fromFormat.parse(lastDay));

		log(lastDay);
		log(lastDay_new);

		AccountOverviewPage accounts2 = bulk.editDateRangeForReport("", "", "", true, true).clickConfirmButton()
				.simpleSearchByString("Aviva Global Savings Account - 20315");

		this.waitForElementVisible(By.xpath(".//*[.='" + account + "']"), Settings.WAIT_SECONDS * 2);

		accounts2.goToAccountHoldingsPageByName(account).goToDetailsPage();

		for (int i = 0; i < 20; i++) {

			// waitForElementVisible(By.id("gwt-debug-ReportsSectionPresenter-reportUrl"),
			// 10);

			if (this.getTextByLocator(By.id("gwt-debug-ReportsSectionPresenter-reportUrl"))
					.contains(lastDay_new + ".pdf")) {
				assertTrue(this.getTextByLocator(By.id("gwt-debug-ReportsSectionPresenter-reportUrl"))
						.contains(lastDay_new + ".pdf"));

				break;
			} else {
				continue;
			}

		}

	}

	@Test
	public void testSearchAccountByProductPlatform() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		String platform = "ZIL Futura";

		// directly
		AccountOverviewPage accounts = main.loginAs(Settings.CRM_USERNAME, Settings.CRM_PASSWORD)
				.goToAccountOverviewPage().goToAdvancedSearchPage().searchByProductPlatform(platform, false)
				.clickSearchButton();

		assertTrue(getTextByLocator(By.id("gwt-debug-InvestorAccountTable-linkPortfolioName")).contains(platform));

		// using magnifying glass
		accounts.clickClearSearchIcon().goToAdvancedSearchPage().searchByProductPlatform(platform, true)
				.clickSearchButton();

		assertTrue(getTextByLocator(By.id("gwt-debug-InvestorAccountTable-linkPortfolioName")).contains(platform));
	}

	@Test
	public void testSearchAccountByProductProvider() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		String provider = "Generali";

		main.loginAs(Settings.CRM_USERNAME, Settings.CRM_PASSWORD).goToAccountOverviewPage().goToAdvancedSearchPage()
				.searchByProductProvider(provider).clickSearchButton();

		assertTrue(this.getTextByLocator(By.xpath("(.//*[@id='gwt-debug-InvestorAccountTable-linkPortfolioName'])[7]"))
				.contains(provider));

	}

	@Test
	public void testSearchAccountByPolicyStatus() throws Exception {

		LoginPage main = new LoginPage(webDriver);
		By policyStatus = By.xpath("//td[.='Policy Status']/following-sibling::td");
		String policyStatus1 = "Surr";
		String policyStatus2 = "Reinstate";

		// policy status = "Surr"
		AccountOverviewPage accounts1 = ((AccountOverviewPage) main
				.loginAs(Settings.CRM_USERNAME, Settings.CRM_PASSWORD).goToAccountOverviewPage()
				.goToAdvancedSearchPage().searchByAccountPolicyStatus(policyStatus1).clickSearchButton())
						.checkIncludeInactiveAccounts(true);

		String accountName = getTextByLocator(By.id("gwt-debug-InvestorAccountTable-linkPortfolioName"));

		DetailPage surr = accounts1.goToAccountHoldingsPageByName(accountName).goToDetailsPage();

		waitForElementVisible(policyStatus, 60);

		assertTrue(this.getTextByLocator(policyStatus).contains("Surrender"));

		// policy status = "Reinstate"
		AccountOverviewPage accounts2 = surr.goToAccountOverviewPage().goToAdvancedSearchPage()
				.searchByAccountPolicyStatus(policyStatus2).clickSearchButton();

		accountName = getTextByLocator(By.id("gwt-debug-InvestorAccountTable-linkPortfolioName"));

		accounts2.goToAccountHoldingsPageByName(accountName).goToDetailsPage();

		waitForElementVisible(policyStatus, 60);

		assertTrue(this.getTextByLocator(policyStatus).contains("Reinstated"));

	}

	@Test
	public void testSearchSpecialCharacters() throws Exception {

		String account = "Selenium Test";
		String investment = "SPDR® S&P/ASX 200 Listed Property Fund";

		LoginPage main = new LoginPage(webDriver);

		// String investment = "Vanguard S&P 500 (Inst) GBP";
		// handleAlert();
		HoldingsPage holdingPage = (HoldingsPage) main.loginAs(Settings.USERNAME, Settings.PASSWORD)
				.goToAccountOverviewPage().simpleSearchByString(account).goToAccountHoldingsPageByName(account);
		clickElement(By.id("gwt-debug-PortfolioAllocationView-reallocateBtn"));
		if (pageContainsStr("This account cannot be manually rebalanced as it follows a model portfolio.")) {
			clickOkButtonIfVisible();
			DetailPage detailPage = (DetailPage) holdingPage.goToDetailsPage().goToEditPageByField("Model Portfolio")
					.unlinkModelPortfolio();
		}

		if (pageContainsStr(investment)) {
			holdingPage.setNewAllocationForInvestment(investment, "0").clickRebalancePreviewAndConfirm()
					.goToTransactionHistoryPage().confirmHistoryStatus();
		}

		AccountOverviewPage accounts = ((HoldingsPage) holdingPage.goToAccountOverviewPage()
				.simpleSearchByString(account).goToAccountHoldingsPageByName(account).goToHoldingsPage()
				.clickReallocateButton().clickAddInvestmentButton().simpleSearchByNameWithButton(investment)
				.selectInvestmentByName(investment).clickAddToPortfolioButton())
						.setNewAllocationForInvestment(investment, "5").clickRebalancePreviewAndConfirm()
						.goToTransactionHistoryPage().confirmHistoryStatus().goToAccountOverviewPage()
						.checkIncludeNonAssetAccounts(true).checkIncludeInactiveAccounts(true);

		this.waitForWaitingScreenNotVisible();

		accounts.goToAdvancedSearchPage().searchByProductInvestmentName(investment).clickSearchButton();

		assertTrue(pageContainsStr(account));

		// wait(Settings.WAIT_SECONDS);

		accounts.goToAccountHoldingsPageByName(account).clickReallocateButton()
				.setNewAllocationForInvestment(investment, "0").clickRebalancePreviewAndConfirm();
		// igore the following since there is no investments with special
		// character for JarlAdvisor account
		// wait(Settings.WAIT_SECONDS);
		//
		// checkLogout();
		// handleAlert();
		//
		// LoginPage main2 = new LoginPage(webDriver);
		//
		// AdvancedSearchPage advancedSearch = main2
		// .loginAs(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD)
		// .goToAccountOverviewPage().goToAdvancedSearchPage();
		//
		// advancedSearch.searchByProductInvestmentNameAndMagnifyingGlass(
		// investment).clickSearchButton();
		// scrollToTop();
		// WebElement elem = webDriver.findElement(By
		// .id("gwt-debug-InvestorAccountOverviewView-searchBox"));
		// String searchResult = elem.getAttribute("value").toString();
		// assertTrue(searchResult.contains(investment));

	}

	@Test
	public void testSavedSearches() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		// Create the first search criteria
		String clientName = "Investor";
		String nationality = "Austria";
		String residence = "Hong Kong";
		String emailFunction = "Enabled";
		String advisor = "Selenium Consultant";
		String accountStatus1 = "In Force";
		String accountStatus2 = "Opened";
		String policyStatus = "Surr";
		String provider = "AIA";
		String platform = "ABPI TBA";
		String saved = "TestSaveSearch" + this.getRandName();

		AccountOverviewPage account = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage()
				.goToAdvancedSearchPage().searchByClientName(clientName).searchByClientNationality(nationality)
				.searchByClientCountryOfResidence(residence).searchByClientEmailFunction(emailFunction)
				.searchByAccountAdvisor(advisor).searchByAccountStatus(accountStatus1, accountStatus2)
				.searchByAccountPolicyStatus(policyStatus).searchByProductProvider(provider)
				.searchByProductPlatform(platform, false).clickSaveButtonToSaveThisSearch(saved).clickSearchButton();
		this.waitForWaitingScreenNotVisible();
		scrollToTop();
		AdvancedSearchPage search = account.clickClearSearchIcon().goToAdvancedSearchPage().goToSavedSearchesTab()
				.selectSavedSearchName(saved).clickEditButtonForThisSearch();
		scrollToTop();
		this.waitForElementNotVisible(By.xpath(".//*[@id='gwt-debug-AdvancedSearchPanel-Client' and @disabled]"),
				Settings.WAIT_SECONDS);
		assertTrue(testSearchCriteria("Client Name", "3", clientName));
		assertTrue(testSearchCriteria("Client Nationality", "2", "16"));
		assertTrue(testSearchCriteria("Client Country of Residence", "2", "108"));
		assertTrue(testSearchCriteria("Advisor", "2", advisor));
		assertTrue(testSearchCriteria("Policy Status", "2", policyStatus));
		assertTrue(testSearchCriteria("Product Provider", "2", provider));
		assertTrue(testSearchCriteria("Product Platform", "2", platform));

		// create the second search criteria
		String residence2 = "United States";
		String provider2 = "Acordias";
		String saved2 = "TestSaveSearch" + this.getRandName();

		((AccountOverviewPage) search.goToSearchTab().clickSearchButton()).clickClearSearchIcon()
				.goToAdvancedSearchPage().searchByClientCountryOfResidence(residence2)
				.searchByProductProvider(provider2).clickSaveButtonToSaveThisSearch(saved);

		// Test duplicate name
		assertTrue(pageContainsStr("Duplicate Name"));

		clickElement(By.xpath(".//*[@id='gwt-debug-CustomDialog-okButton' and @style='']"));

		this.waitForElementVisible(By.xpath("//button[.='Cancel' and @id='gwt-debug-CustomDialog-customButton']"),
				Settings.WAIT_SECONDS);

		clickElement(By.xpath("//button[.='Cancel' and @id='gwt-debug-CustomDialog-customButton']"));

		this.waitForWaitingScreenNotVisible();
		// clickOkButtonIfVisible();
		// clickOkButtonIfVisible();
		search.clickSaveButtonForThisSearch(saved2);
		clickElement(By.xpath("//button[.='Save' and @id='gwt-debug-CustomDialog-customButton']"));
		search.goToSavedSearchesTab().goToSavedSearchesTab().clickEditButtonForThisSearch();
		// Test the search criteria match the search name
		assertTrue(testSearchCriteria("Client Country of Residence", "2", "244"));
		assertTrue(testSearchCriteria("Product Provider", "2", provider2));
		scrollToTop();
		// Test the search criteria is correct, no merge from each other
		search.goToSavedSearchesTab().selectSavedSearchName(saved2).clickEditButtonForThisSearch();
		scrollToTop();
		this.waitForElementNotVisible(By.xpath(".//*[@id='gwt-debug-AdvancedSearchPanel-Client' and @disabled]"),
				Settings.WAIT_SECONDS);
		assertFalse(testSearchCriteria("Client Name", "3", clientName));
		assertFalse(testSearchCriteria("Client Nationality", "2", "16"));
		assertTrue(testSearchCriteria("Client Country of Residence", "2", "244"));
		assertFalse(testSearchCriteria("Advisor", "2", advisor));
		assertFalse(testSearchCriteria("Policy Status", "2", policyStatus));
		assertTrue(testSearchCriteria("Product Provider", "2", provider2));
		assertFalse(testSearchCriteria("Product Platform", "2", platform));

		search.clickDeleteButtonForThisSearch().selectSavedSearchName(saved).clickDeleteButtonForThisSearch();

	}

	@Test
	public void testSearchAccountByInvestmentName() throws Exception {

		LoginPage main1 = new LoginPage(webDriver);

		main1.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage().goToAdvancedSearchPage()
				.searchByProductInvestmentName("JP").clickSearchButton();

		try {
			waitForElementVisible(By.id("gwt-debug-InvestorAccountTable-linkPortfolioName"), 10);
		} catch (TimeoutException e) {
			throw e;
		}

		assertTrue(isElementVisible(By.id("gwt-debug-InvestorAccountTable-linkPortfolioName")));
	}

	@Test
	public void testSearchAccountByModelPortfolioProvider() throws Exception {

		LoginPage main1 = new LoginPage(webDriver);

		main1.loginAs(Settings.USERNAME, Settings.PASSWORD)
				// .goToCompanySettingsPage()
				// .editRedirectURL("")
				// .clickSubmitButton()
				.goToAccountOverviewPage().simpleSearchByString("Selenium")
				.goToAccountHoldingsPageByName("Selenium Test").goToDetailsPage()
				.goToEditPageByField("Model Portfolio");

		// wait(2);

		if (pageContainsStr("Linked to")) {
			clickElement(By.id("gwt-debug-ModelPortfolioWidget-modelUnLink"));
			main1.goToDetailsPage().goToEditPageByField("Model Portfolio");
		}

		selectFromDropdown(By.id("gwt-debug-ModelPortfolioWidget-modelPortList"), "Sample Model Portfolio");

		clickElement(By.id("gwt-debug-ModelPortfolioWidget-modelLink"));

		clickElement(By.id("gwt-debug-CustomDialog-yesButton"));

		this.waitForWaitingScreenNotVisible();
		this.checkLogout();
		handleAlert();
		// wait(Settings.WAIT_SECONDS);
		LoginPage main = new LoginPage(webDriver);

		((AccountOverviewPage) main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage()
				.goToAdvancedSearchPage().searchByProductModelPortfolioProvider("SeleniumTest").clickSearchButton())
						.goToAccountHoldingsPageByName("Selenium Test").goToDetailsPage()
						.goToEditPageByField("Model Portfolio").unlinkModelPortfolio();

		assertTrue(pageContainsStr("Currently, this portfolio is not linked to a model portfolio"));
	}

	@Test
	public void testSearchAccountByModelPortfolioName() throws Exception {

		LoginPage main1 = new LoginPage(webDriver);

		main1.loginAs(Settings.USERNAME, Settings.PASSWORD).goToCompanySettingsPage().editRedirectURL("")
				.clickSubmitButton().simpleSearchByString("Selenium").goToAccountHoldingsPageByName("Selenium Test")
				.goToDetailsPage().goToEditPageByField("Model Portfolio");

		if (pageContainsStr("Linked to")) {

			return;

		} else {
			selectFromDropdown(By.id("gwt-debug-ModelPortfolioWidget-modelPortList"), "Sample Model Portfolio");

			clickElementAndWait3(By.id("gwt-debug-ModelPortfolioWidget-modelLink"));

			clickElement(By.id("gwt-debug-CustomDialog-yesButton"));

			this.waitForWaitingScreenNotVisible();

		}

		this.checkLogout();

		// wait(2);
		handleAlert();
		LoginPage main = new LoginPage(webDriver);

		((AccountOverviewPage) main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage()
				.goToAdvancedSearchPage().searchByProductModelPortfolioName("Sample Model Portfolio")
				.clickSearchButton()).goToAccountHoldingsPageByName("Selenium Test").goToDetailsPage()
						.goToEditPageByField("Model Portfolio").unlinkModelPortfolio();

		assertTrue(pageContainsStr("Currently, this portfolio is not linked to a model portfolio"));

		checkLogout();
		handleAlert();

		LoginPage main2 = new LoginPage(webDriver);
		AccountOverviewPage accountOverview = main2.loginAs(Settings.CRM_USERNAME, Settings.CRM_PASSWORD)
				.goToAccountOverviewPage();
		accountOverview.goToAdvancedSearchPage().searchByProductModelPortfolioName("Cautious").clickSearchButton();

		int page = getPagesOfElements(By.id("gwt-debug-InvestorAccountTable-linkPortfolioName"));

		assertTrue(pageContainsStrMultiPages("STLA Wealth Amplifier - 27541", page));

		accountOverview.goToAdvancedSearchPage().searchByProductModelPortfolioName("Balanced").clickSearchButton();

		page = getPagesOfElements(By.id("gwt-debug-InvestorAccountTable-linkPortfolioName"));

		assertTrue(pageContainsStrMultiPages("FPIL Premier - 16714", page));

		accountOverview.goToAdvancedSearchPage().searchByProductModelPortfolioName("Adventurous").clickSearchButton();

		page = getPagesOfElements(By.id("gwt-debug-InvestorAccountTable-linkPortfolioName"));

		assertTrue(pageContainsStrMultiPages("ZIL Vista - 16943", page));

	}

	@Test
	public void testSearchAccountByISINandWKN() throws Exception {
		LoginPage main = new LoginPage(webDriver);
		String account = "Selenium Test";
		String investment = "3 Banken Absolute Return-Mix T";
		String isin = "AT0000619051";
		String wkn = "A0ETCE";
		HoldingsPage holdings = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage()
				.simpleSearchByString(account).goToAccountHoldingsPageByName(account).clickReallocateButton()
				.clickAddInvestmentButton().simpleSearchByName(investment).selectInvestmentByName(investment)
				.clickAddToPortfolioButton();

		AccountOverviewPage accountOverview = holdings.setNewAllocationForInvestment(investment, "10")
				.clickRebalancePreviewAndConfirm(investment).confirmHistoryStatus().goToAccountOverviewPage();

		accountOverview.goToAdvancedSearchPage().searchByCustomFields("ISIN", isin).clickSearchButton();

		assertTrue(pageContainsStr(account));

		accountOverview.clickClearSearchIcon().goToAdvancedSearchPage().searchByCustomFields("WKN", wkn)
				.clickSearchButton();

		assertTrue(pageContainsStr(account));

		accountOverview.clickClearSearchIcon().goToAdvancedSearchPage().searchByCustomFields("ISIN", isin)
				.searchByCustomFields("WKN", wkn).clickSearchButton();

		assertTrue(pageContainsStr(account));

		accountOverview.goToAccountHoldingsPageByName(account).clickReallocateButton()
				.setNewAllocationForInvestment(investment, "0").clickRebalancePreviewAndConfirm("")
				.confirmHistoryStatus();
	}

	@Test
	public void testGoToClientTagInAccountOverviewPage() throws InterruptedException {
		LoginPage main = new LoginPage(webDriver);

		AdvancedSearchPage advancedSearch = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage()
				.goToAdvancedSearchPage();

		advancedSearch.clickCheckMoreCustomClientTag();

		assertTrue(this.pageContainsStr("Custom Tag Overview"));
		assertTrue(this.isElementVisible(By.xpath(".//*[contains(text(),'Custom Tag Overview')]")));
		assertTrue(this.isElementDisplayed(By.xpath(".//*[contains(text(),'Custom Tag Overview')]")));
		assertTrue(this.isElementPresent(By.xpath(".//*[contains(text(),'Custom Tag Overview')]")));

	}

	@Test
	public void testGoToAccountTagInAccountOverviewPage() throws InterruptedException {
		LoginPage main = new LoginPage(webDriver);

		AdvancedSearchPage advancedSearch = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage()
				.goToAdvancedSearchPage();

		advancedSearch.clickCheckMoreAccountTag();

		assertTrue(this.pageContainsStr("Custom Tag Overview"));
		assertTrue(this.isElementVisible(By.xpath(".//*[contains(text(),'Custom Tag Overview')]")));
		assertTrue(this.isElementDisplayed(By.xpath(".//*[contains(text(),'Custom Tag Overview')]")));
		assertTrue(this.isElementPresent(By.xpath(".//*[contains(text(),'Custom Tag Overview')]")));

	}

	@Test
	public void testAccountOverviewPageInDifferentMode() throws InterruptedException {
		String mode1 = "Report";
		String mode2 = "Market";
		String investment = "BlackRock European Dynamic Fund A Accumulation";
		LoginPage main = new LoginPage(webDriver);
		main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage();
	}

	@Test
	public void testAddModelPortfolioForAccount() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToCompanySettingsPage().editRedirectURL("")
				.clickSubmitButton().simpleSearchByString("Selenium").goToAccountHoldingsPageByName("Selenium Test")
				.goToDetailsPage().goToEditPageByField("Model Portfolio");

		if (pageContainsStr("Linked to")) {
			return;
		} else {
			selectFromDropdown(By.id("gwt-debug-ModelPortfolioWidget-modelPortList"), "Sample Model Portfolio");

			clickElementAndWait3(By.id("gwt-debug-ModelPortfolioWidget-modelLink"));

			clickElement(By.id("gwt-debug-CustomDialog-yesButton"));
		}
	}

	@Test
	public void testSearchClientByName() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		main.loginAs(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD).goToAccountOverviewPage()
				.simpleSearchByString("Bob");

		assertTrue(pageContainsStr("Smith, Bob"));
	}

	@Test
	public void testAllSelectorInAdvancedSearch() throws InterruptedException {

		String clientNationality = "Hong Kong";
		String countryOfResidence = "Cuba";
		String clientTag = "TestClientTag" + getRandName();
		String emailFunction = "Enabled";
		String accountStatus = "Opened";
		String reportGeneration = "Enabled";
		String nonAssetAccount = "Yes";
		String accountTag = "testtag";
		String clientAccess = "Live";
		String region = "Other";
		String investmentType = "Stock";
		String assetClass = "FX";

		LoginPage main = new LoginPage(webDriver);
		AdvancedSearchPage advancedSearch = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToCustomTagPage()
				.clickCreateCustomTagButton().editTagName(clientTag).editTagColor().editTagTargetType("Client")
				.clickSaveButton().goToAccountOverviewPage().goToAdvancedSearchPage();

		advancedSearch.searchByClientNationality(clientNationality);
		pageContainsStr(clientNationality);

		advancedSearch.searchByClientCountryOfResidence(countryOfResidence);
		pageContainsStr(countryOfResidence);

		advancedSearch.searchByClientTag(clientTag);
		assertEquals(
				getTextByLocator(
						By.xpath(".//td[.='Client Tag']/following-sibling::td[1]/div/table/tbody/tr/td[1]/input")),
				"1 item selected");
		clickElement(By.xpath("//td[.='Client Tag']/following-sibling::td[1]/div/table/tbody/tr/td[2]/button"));

		advancedSearch.searchByClientEmailFunction(emailFunction);
		pageContainsStr(emailFunction);

		advancedSearch.searchByAccountStatus(accountStatus);
		assertEquals(
				getTextByLocator(
						By.xpath(".//td[.='Account Status']/following-sibling::td[1]/div/table/tbody/tr/td[1]/input")),
				"1 item selected");
		clickElement(By.xpath("//td[.='Account Status']/following-sibling::td[1]/div/table/tbody/tr/td[2]/button/i"));

		advancedSearch.searchByAccountReportGeneration(reportGeneration);
		pageContainsStr(reportGeneration);

		advancedSearch.searchByNonAssetAccount(nonAssetAccount);
		pageContainsStr(nonAssetAccount);

		advancedSearch.searchByAccountTag(accountTag);
		assertEquals(
				getTextByLocator(
						By.xpath(".//td[.='Account Tag']/following-sibling::td[1]/div/table/tbody/tr/td[1]/input")),
				"1 item selected");
		clickElement(By.xpath("//td[.='Account Tag']/following-sibling::td[1]/div/table/tbody/tr/td[2]/button"));

		advancedSearch.searchByAccountClientAccess(clientAccess);
		pageContainsStr(clientAccess);

		advancedSearch.searchByAccountFundRegion(region);
		pageContainsStr(region);

		advancedSearch.searchByProductInvestmentType(investmentType);
		pageContainsStr(investmentType);

		advancedSearch.searchByProductAssetClass(assetClass);
		pageContainsStr(assetClass);

		MenuBarPage menu = new MenuBarPage(webDriver);
		menu.goToCustomTagPage().deleteCustomTagInUseByName(clientTag);

	}

	@Test
	public void testRightClickOnInvestor() throws InterruptedException, AWTException {
		LoginPage main = new LoginPage(webDriver);

		main.loginAs(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD).goToAccountOverviewPage();

		String winHandleBefore = webDriver.getWindowHandle();

		String investorName = getTextByLocator(By.id("gwt-debug-InvestorAccountTable-linkPersonName")).split(",")[0];

		rightClickElement(By.id("gwt-debug-InvestorAccountTable-linkPersonName"), 2);

		wait(1);

		for (String winHandle : webDriver.getWindowHandles()) {
			wait(1);
			webDriver.switchTo().window(winHandle);
		}
		waitForElementVisible(By.xpath(".//*[contains(text(),'" + investorName + "')]"), 30);
		assertTrue(pageContainsStr(investorName));
		// wait(5);
		// webDriver.close();
		//
		// handleAlert();
		// webDriver.switchTo().window(winHandleBefore);

	}

	@Test
	public void testPopulatingWhiteList() throws InterruptedException {

		String benchmark1 = "UK CPI";
		String benchmark2 = "Hang Seng HSI PR HKD";

		this.populatingWhiteList("", true);
		this.populatingWhiteList(benchmark2, true);

	}

	@Test
	public void testDownloadAccountSearchResultByManagerInvestment() throws Exception {

		AccountOverviewPage account = this.init();
		String type = "By Manager, Investment/Fund";
		String file = "testByManagerInvestment" + getRandName();

		MenuBarPage menu = account.exportSearchResult(type, file).goToProcess();

		menu.downloadExportFile(file);
		checkDownloadedExcelFile(file, "Manager", "Investment Name", "Advisor", "Price", "Selenium");

	}

	@Test
	public void testDownloadAccountSearchResultByModelPortfolio() throws Exception {

		AccountOverviewPage account = this.init();
		String type = "By Model Portfolio";
		String file = "testByModelPortfolio" + getRandName();

		MenuBarPage menu = account.exportSearchResult(type, file).goToProcess();

		menu.downloadExportFile(file);
		checkDownloadedExcelFile(file, "Model Portfolio Provider", "Model Portfolio Name", "Advisor", "Selenium");

	}

	@Test
	public void testDownloadAccountSearchResultByAdvisor() throws Exception {

		AccountOverviewPage account = this.init();
		String type = "By Office, Advisor";
		String file = "testByAdvisor" + getRandName();

		MenuBarPage menu = account.exportSearchResult(type, file).goToProcess();

		menu.downloadExportFile(file);
		checkDownloadedExcelFile(file, "Office", "Advisor", "Account Name", "Selenium");

	}

	@Test
	public void testDownloadAccountSearchSpecificResultByManagerInvestment() throws Exception {

		String investment = "IFSL Tilney Bestinvest Aggressive Growth Portfolio Retail Acc";
		String type = "By Manager, Investment/Fund";
		String file = "testByManagerInvestmentSpecific" + getRandName();
		String accountName = "Selenium Test";
		AccountOverviewPage account = this.init();

		HoldingsPage holdings = account.goToAccountOverviewPage().simpleSearchByString(accountName)
				.goToAccountHoldingsPageByName(accountName);

		if (pageContainsStr("This portfolio is linked to")) {
			holdings.goToDetailsPage();
			clickElement(By.id("gwt-debug-ModelPortfolioSectionPresenter-image"));
			wait(Settings.WAIT_SECONDS);
			clickElement(By.id("gwt-debug-ModelPortfolioWidget-modelUnLink"));
			wait(Settings.WAIT_SECONDS);
			holdings.goToHoldingsPage();
		}

		holdings.clickReallocateButton();

		AccountOverviewPage account2;
		if (!pageContainsStr(investment)) {
			HoldingsPage holdings2 = holdings.clickAddInvestmentButton().simpleSearchByName(investment)
					.selectInvestmentByName(investment).clickAddToPortfolioButton();
			account2 = account;
			holdings2.setNewAllocationForInvestment(investment, "20").clickRebalancePreviewAndConfirm(investment)
					.goToTransactionHistoryPage().confirmHistoryStatus().goToAccountOverviewPage();

		} else {
			account2 = holdings.clickCancelEditButton().goToAccountOverviewPage();
		}

		account2.goToAdvancedSearchPage().searchByProductInvestmentName(investment).clickSearchButton();

		MenuBarPage menu = account.exportSearchResult(type, file).goToProcess();

		menu.downloadExportFile(file);
		checkDownloadedExcelFile(file);

		account2.goToAccountHoldingsPageByName(accountName).clickReallocateButton()
				.setNewAllocationForInvestment(investment, "0").clickRebalancePreviewAndConfirm(investment)
				.goToTransactionHistoryPage().confirmHistoryStatus();
	}

	@Test
	public void testChangeSettingOfAccountOverviewPage() throws InterruptedException {
		String valueInReportMode = "";
		String valueInMarketMode = "";
		String accountName = "Portfolio Bond (Test Reconciliation)";
		String inactiveAccount = "Test account 150327";
		By currentValueOfAccount = By.xpath(".//td[*[*[*[contains(text(),'" + accountName
				+ "')]]]]//following-sibling::td[*[*[@id='gwt-debug-TimestampLabel-valueSpan']]]");
		LoginPage main = new LoginPage(webDriver);
		AccountOverviewPage account = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage();

		account.editViewMode("Report");

		valueInReportMode = getTextByLocator(currentValueOfAccount);

		account.editViewMode("Market");

		valueInMarketMode = getTextByLocator(currentValueOfAccount);

		assertFalse(valueInReportMode.matches(valueInMarketMode));

		account.checkIncludeInactiveAccounts(true).checkIncludeNonAssetAccounts(true);

		waitForElementVisible(By.id("gwt-debug-InvestorAccountTable-linkPortfolioName"), 30);

		assertTrue(isElementVisible(By.xpath(".//*[.='" + inactiveAccount + "']")));

		account.goToAccountDetailPageByName(inactiveAccount);

		waitForElementVisible(By.xpath(".//*[contains(@href,'investorAccountKey')]"), 30);

		assertTrue(isElementVisible(By.xpath(".//*[.='" + inactiveAccount + " (Inactive)']")));
	}

	public AccountOverviewPage init() throws Exception {
		LoginPage main = new LoginPage(webDriver);

		AccountOverviewPage account = main.loginAs(Settings.USERNAME, Settings.PASSWORD)
				.goToUserPreferencePage(AccountOverviewPage.class).editLoadAccountAndClients(true).clickSubmitButton();

		return account;
	}

	private boolean testSearchCriteria(String field, String num, String value) {

		Boolean checked = false;

		String returnValue = this.getAttributeStringByLocator(
				By.xpath("(//td[.='" + field + "'])[" + num + "]/following-sibling::td[1]/*"), "value");

		checked = returnValue.equals(value);
		return checked;
	}

	public void populatingWhiteList(String benchmark, boolean addBenchmark) throws InterruptedException {

		boolean add = addBenchmark;
		String field1 = "Advisor Companies";
		String field2 = "Investment Universe Whitelist";
		String universe = "SeleniumTest global investment universe";
		String benchmark1 = benchmark;
		String accountType = "REAL CLIENT ACCOUNT";

		LoginPage main = new LoginPage(webDriver);
		AdminEditPage adminEdit = main.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD)
				.goToAdminEditPage();

		adminEdit.editAdminThisField(field1).jumpByKeyAndLoad(Settings.SeleniumTest_Key);

		selectFromDropdown(By.id("gwt-debug-AdminAdvisorCompanyEdit-globalInvestmentUniverseList"), universe);

		adminEdit.clickSubmitButton();
		this.waitForWaitingScreenNotVisible();

		adminEdit.goToAdminEditPage().editAdminThisField(field2).jumpByKeyAndLoad("466");

		waitForElementVisible(By.id("gwt-debug-AdminInvestmentUniverseEdit-iuKey"), 10);

		adminEdit.deleteBenchmarkInUniverse();

		this.waitForWaitingScreenNotVisible();

		if (add) {
			adminEdit.addBenchmarkInUniverse(benchmark1);
		}
		adminEdit.clickSubmitButton();
		this.waitForWaitingScreenNotVisible();
		this.checkLogout();
		handleAlert();

		LoginPage main1 = new LoginPage(webDriver);
		AccountOverviewPage accountOverview = main1.loginAs(Settings.USERNAME, Settings.PASSWORD)
				.goToAccountOverviewPage();

		By locatorOfBenchmarkList = By.id("gwt-debug-InvestorAccountDetailWidget-benchmarkList");

		accountOverview.clickNewAccountButton(accountType).editAccountBenchmark(benchmark1);
		waitForElementVisible(locatorOfBenchmarkList, 30);
		this.waitForWaitingScreenNotVisible();

		WebElement elem = webDriver.findElement(locatorOfBenchmarkList);
		Select select = new Select(elem);
		if (benchmark1.equals("")) {
			assertTrue(select.getOptions().size() > 1);
		} else {
			assertEquals(select.getFirstSelectedOption().getText(), benchmark1);
		}

		int size = getSizeOfElements(By.xpath("(.//button[.='Cancel'])"));

		clickElementByKeyboard(By.xpath("(.//button[.='Cancel'])[" + String.valueOf(size) + "]"));

		checkLogout();
		handleAlert();
	}
}
