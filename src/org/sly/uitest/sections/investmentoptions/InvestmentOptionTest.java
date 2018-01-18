package org.sly.uitest.sections.investmentoptions;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.abstractpage.MenuBarPage;
import org.sly.uitest.pageobjects.assetmanagement.ModelPortfoliosPage;
import org.sly.uitest.pageobjects.clientsandaccounts.DetailPage;
import org.sly.uitest.pageobjects.companyadmin.WhiteListsPage;
import org.sly.uitest.pageobjects.investments.InvestmentsPage;
import org.sly.uitest.settings.Settings;

/**
 * @author: Nandi Ouyang
 * @date: 23 Jan, 2015
 * @company: Prive Financial JIRA No.
 */
public class InvestmentOptionTest extends AbstractPage {

	@Test
	public void testLoadiQFOXXFactSheet() throws InterruptedException {
		setScreenshotSuffix(InvestmentOptionTest.class.getSimpleName());

		String url = Settings.BASE_URL + "#investmentDetails;strategyKey=9297;token=iQF";

		webDriver.get(url);
		handleAlert();

		waitForElementVisible(By.id("gwt-debug-ManagerDisplayWidgetView-strategyName"), 30);
		assertTrue(this.pageContainsStr("iQ-FOXX Asia Stocks Smart Beta TR USD"));

	}

	@Test
	public void testLoadiQFOXXInvestmentList() throws InterruptedException {
		setScreenshotSuffix(InvestmentOptionTest.class.getSimpleName());

		String url = "http://192.168.1.107:8080/SlyAWS/#investmentList;listonly=true;token=iQF";

		webDriver.get(url);
		handleAlert();
		this.waitForWaitingScreenNotVisible();
		waitForElementVisible(By.xpath("//*[contains(text(),'1: Assuming risk free rate of 0%')]"), 30);
		assertTrue(this.pageContainsStr("1: Assuming risk free rate of 0%"));

	}

	@Test
	public void testiQFPageTitles() throws InterruptedException {

		webDriver.get("https://www.privemanagers.com/?locale=en#investmentDetails;strategyKey=41636;token=iQF");

		this.waitForWaitingScreenNotVisible();

		/* Check Titles */
		waitForElementVisible(By.xpath(".//*[contains(text(),'Historical Performance')]"), Settings.WAIT_SECONDS);
		assertTrue(pageContainsStr(" Historical Performance "));
		assertTrue(pageContainsStr(" Risk Analysis "));
		assertTrue(pageContainsStr("Asset Class Exposure"));
		assertTrue(pageContainsStr("Sector Exposure"));
		// assertTrue(pageContainsStr("Current Holding"));
		assertTrue(pageContainsStr("Regional Exposure"));
		assertTrue(pageContainsStr(" About the Provider "));

		log("All Titles are Checked!");

	}

	@Test
	public void testFundByFullName() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToInvestmentsPage().simpleSearchByNameWithButton("HFF, Inc");

		assertTrue(pageContainsStr("HFF, Inc. Class A"));
		assertTrue(pageContainsStr("1 - 1 of 1 Results"));

	}

	@Test
	public void testFundWithDifferentLanguage() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		main.loginAs(Settings.CRM_USERNAME, Settings.CRM_PASSWORD).goToAccountOverviewPage();

		webDriver.get(Settings.BASE_URL
				+ "?locale=en&viewMode=MATERIAL#investmentDetails;strategyKey=10502;hideBackButton=true;dataSource=");
		handleAlert();
		waitForElementVisible(By.id("gwt-debug-ManagerDisplayWidgetView-strategyName"), 30);

		// Get description in English
		String description = getTextByLocator(By.id("gwt-debug-ManagerDisplayWidgetView-strategyDescription"));

		webDriver.get(Settings.BASE_URL
				+ "?locale=de_DE&viewMode=MATERIAL#investmentDetails;strategyKey=10502;hideBackButton=true;dataSource=");
		handleAlert();
		waitForElementVisible(By.id("gwt-debug-ManagerDisplayWidgetView-strategyName"), 30);

		// asserFalse for English = German
		assertFalse(
				description.equals(getTextByLocator(By.id("gwt-debug-ManagerDisplayWidgetView-strategyDescription"))));

	}

	@Deprecated
	public void testDefaultInvestmentOptionPage() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToInvestmentsPage();

		assertTrue(pageContainsStr("Top Equity Investments"));
		assertTrue(pageContainsStr("Top FX Investments"));
		assertTrue(pageContainsStr("Top Fixed Income Investments"));
		assertTrue(pageContainsStr("Top Commodity Investments"));
		assertTrue(pageContainsStr("Top Alternatives Investments"));
		assertTrue(pageContainsStr("Top Hybrid Investments"));
		assertTrue(pageContainsStr("Top Other Investments"));

	}

	@Test
	public void testIndividualInvestmentEntry() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		InvestmentsPage investment = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToInvestmentsPage();

		wait(Settings.WAIT_SECONDS);

		String investName = getTextByLocator(By.id("gwt-debug-ManagerListItem-strategyName"));

		investment.openInvestmentByName(investName);

		assertTrue(pageContainsStr(" Historical Performance "));
		assertTrue(pageContainsStr(" Risk Analysis "));
		assertTrue(pageContainsStr(" Return Analysis "));
		assertTrue(pageContainsStr("Facts"));
		assertTrue(pageContainsStr("Identifiers"));
		assertTrue(pageContainsStr("Category"));
		assertTrue(pageContainsStr("Documents"));
		assertTrue(pageContainsStr(" Data Source "));

	}

	@Test
	public void testCreateAndEditAndDeleteWhiteList() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		String name = "White List Name" + this.getRandName();
		String newName = "White Lis New Namet" + this.getRandName();
		String description = "Description Des" + this.getRandName();
		String newDescription = "New Description Des" + this.getRandName();
		String asset1 = "Bond";
		String asset2 = "Stock";
		String currency = "AUD";
		String provider1 = "AIG";
		String provider2 = "Acordias";
		String provider3 = "Agilent Limited";
		String product1 = "ABN Amro TBA";
		String product2 = "AIG Working Holiday Protection";
		String investment1 = "Nordea 1 Global Value Fd.BP NOK";
		String investment2 = "Quali-Smart Holdings Ltd.";
		String country1 = "China";
		String country2 = "Canada";

		// create white list
		// wait(1);
		WhiteListsPage whitelist = ((WhiteListsPage) ((WhiteListsPage) main
				.loginAs(Settings.USERNAME, Settings.PASSWORD).goToWhiteListsPage().clickCreateWhiteListButton()
				.editWhiteListProductProviders(true, provider1, provider2).editWhiteListName(name)
				.editWhiteListDescription(description).editWhiteListAssets(true, asset1, asset2)
				.editWhiteListCurrency(true, currency).editWhiteListProducts(true, product1)
				.editInvestmentsToInclude_Add().simpleSearchByNameWithButton(investment1)
				.selectInvestmentByNameNewView(investment1).clickAddToPortfolioButtonNewView())
						.editInvestmentsToExclude_Add().simpleSearchByNameWithButton(investment2)
						.selectInvestmentByNameNewView(investment2).clickAddToPortfolioButtonNewView())
								.editWhiteListApprovedForSale(true, country1).clickSubmitButton();

		assertTrue(pageContainsStr(name));
		assertTrue(pageContainsStr(description));

		// edit white list
		whitelist.editWhiteListByName(name).editWhiteListDescription(newDescription).editWhiteListName(newName)
				.editWhiteListAssets(false, asset2).editWhiteListProductProviders(false, provider1)
				.editWhiteListProductProviders(true, provider3).editWhiteListProducts(true, product2)
				.editInvestmentsToInclude_Remove(investment1).editInvestmentsToExclude_Remove(investment2)
				.editWhiteListApprovedForSale(false, country1).editWhiteListApprovedForSale(true, country2)
				.clickSubmitButton();

		this.waitForWaitingScreenNotVisible();
		scrollToTop();
		assertFalse(pageContainsStr(name));
		assertFalse(pageContainsStr(description));
		assertTrue(pageContainsStr(newName));
		assertTrue(pageContainsStr(newDescription));

		// delete white list
		whitelist.deleteWhiteListByName(newName);

		this.waitForWaitingScreenNotVisible();
		scrollToTop();
		assertFalse(pageContainsStr(newName));
		assertFalse(pageContainsStr(newDescription));
	}

	@Test
	public void testAdvancedSearchFunction() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		String searchName = "Scottish Widows";
		String investment = "Scottish Widows Cautious A Inc";

		main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToInvestmentsPage().goToAdvancedSearchPage()
				.searchByName(searchName).clickSearchButton();

		waitForElementVisible(By.xpath(".//*[@id='gwt-debug-ManagerListItem-strategyName']"), 10);
		investment = getTextByLocator(By.xpath("(.//*[@id='gwt-debug-ManagerListItem-strategyName'])[1]"));

		assertTrue(investment.contains(searchName));
	}

	@Test
	public void testPresetSearchFunction() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		String searchName = "Scottish Widows";
		String investment = "";
		String search = "Search" + this.getRandName();

		((InvestmentsPage) main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToInvestmentsPage()
				.goToAdvancedSearchPage().searchByName(searchName).clickSaveButtonToSaveThisSearch(search)
				.clickSearchButton()).clickClearSearchIcon().goToAdvancedSearchPage().goToSavedSearchesTab()
						.selectSavedSearchName(search).clickSearchPresetButton();
		waitForElementVisible(By.xpath(".//*[@id='gwt-debug-ManagerListItem-strategyName']"), 10);
		investment = getTextByLocator(By.xpath("(.//*[@id='gwt-debug-ManagerListItem-strategyName'])[1]"));
		assertTrue(investment.contains(searchName));
	}

	@Test
	public void testFilterByDataSource() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		String investment = "Tencent Holdings";
		String source1 = "All";
		String source2 = "Xignite";
		String source3 = "Morningstar";

		// data source = all
		InvestmentsPage investments = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToInvestmentsPage()
				.simpleSearchByNameWithButton("tencent").editDataSource(source1);
		// wait(2);
		assertTrue(pageContainsStr(investment));
		// assertTrue(pageContainsStr("1 - 3 of 3 Results"));
		// data source = xignite
		investments.editDataSource(source2);

		// assertTrue(pageContainsStr("1 - 3 of 3 Results"));
		assertTrue(pageContainsStr(investment));

		// data source = morningstar
		investments.editDataSource(source3);

		assertTrue(pageContainsStr("No investments match the filter criteria."));
	}

	@Test
	public void testInvestmentsLoadedWithAllDataSource() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		String source = "Xignite";
		String source1 = "All";

		// data source = all
		main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToInvestmentsPage().simpleSearchByName("tencent")
				.editDataSource(source).editDataSource(source1);

		waitForElementVisible(By.id("gwt-debug-ManagerListItem-strategyName"), 60);

		// data source URL parameter is not present
		String currentURL = webDriver.getCurrentUrl();

		log(currentURL);

		assertTrue(currentURL.contains(";dataSource="));

	}

	@Test
	public void testAddInvestmentToAccountWithLinkedModelPortfolio() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		// link a model portfolio to the account
		ModelPortfoliosPage portfolios = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToModelPortfoliosPage()
				.clickManageModelPortfolios();

		String account = "Selenium Test";
		String portfolio = getTextByLocator(By.id("gwt-debug-InvestorAccountTable-linkPortfolioName"));

		// add investment to the account
		InvestmentsPage investments = portfolios.goToAccountOverviewPage().simpleSearchByString("Selenium")
				.goToAccountHoldingsPageByName(account).goToDetailsPage().linkModelPortfolioForAccount(portfolio, true)
				.goToInvestmentsPage().simpleSearchByName("Fund");

		waitForElementVisible(By.id("gwt-debug-ManagerListItem-strategyName"), 30);

		String investment = getTextByLocator(By.id("gwt-debug-ManagerListItem-strategyName"));

		investments.openInvestmentByName(investment).clickAddToPortfolioButtonFromFactSheet()
				.chooseInvestorAccountFromFactsheet(account);

		assertTrue(pageContainsStr("This portfolio is linked to a model portfolio"));

		clickElement(By.id("gwt-debug-ManagerDisplayWidgetView-cancelButton"));

		// unlink the model portfolio from the account
		investments.goToAccountOverviewPage().simpleSearchByString("Selenium").goToAccountHoldingsPageByName(account)
				.goToDetailsPage().unlinkModelPortfolioForAccount();

	}

	@Test
	public void testAddInvestmentToAccountWithLinkedModelPortfolio_ListnCompare() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		String account = "Selenium Test";
		String portfolio = "Sample Model Portfolio";

		// link a model portfolio to the account
		DetailPage detailPage = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage()
				.simpleSearchByString("Selenium").goToAccountHoldingsPageByName(account).goToDetailsPage()
				.linkModelPortfolioForAccount(portfolio, true);
		wait(Settings.WAIT_SECONDS);
		InvestmentsPage investments = detailPage.goToInvestmentsPage();

		// .simpleSearchByName("Fund")

		waitForElementVisible(By.id("gwt-debug-ManagerListItem-strategyName"), 30);

		String investment1 = getTextByLocator(By.xpath("(.//*[@id='gwt-debug-ManagerListItem-strategyName'])[1]"));
		String investment2 = getTextByLocator(By.xpath("(.//*[@id='gwt-debug-ManagerListItem-strategyName'])[2]"));
		String investment3 = getTextByLocator(By.xpath("(.//*[@id='gwt-debug-ManagerListItem-strategyName'])[3]"));

		investments.selectInvestmentByNameNewView(investment1).selectInvestmentByNameNewView(investment2)
				.selectInvestmentByNameNewView(investment3);

		// 1. add to portfolio
		investments.clickAddToPortfolioButtonFromList()
				.chooseInvestorAccountFromListPage("Selenium Test - Selenium Investor");

		assertTrue(pageContainsStr("This portfolio is linked to a model portfolio"));

		clickElement(By.id("gwt-debug-ManagerListWidgetView-cancelButton"));
		this.waitForElementVisible(By.id("gwt-debug-ManagerStrategySelectionWidget-compareAllBtn"),
				Settings.WAIT_SECONDS);
		// 2. compare
		investments.clickCompareButton().addToPortfolioByNameNewView(investment2)
				.chooseInvestorAccountAfterCompare(account);

		assertTrue(pageContainsStr("This portfolio is linked to a model portfolio"));

		clickElement(By.id("gwt-debug-ManagerCompareView-cancelButton"));

		// unlink the model portfolio from the account
		// .closeCompareInvestmentsPage()
		investments.goToAccountOverviewPage().simpleSearchByString("Selenium").goToAccountHoldingsPageByName(account)
				.goToDetailsPage().unlinkModelPortfolioForAccount();
	}

	@Test
	public void testOpenAssetUniverse() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		String account = "Private Bank (Open Asset Universe)";

		InvestmentsPage investments = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage()
				.simpleSearchByString("private").goToAccountHoldingsPageByName(account).clickReallocateButton()
				.clickAddInvestmentButton();

		String[] infoLabel = getTextByLocator(By.id("gwt-debug-ManagerListWidgetView-pageInfoLabel")).split(" ");

		int result = Integer.valueOf(infoLabel[4]);

		System.out.println(result);

		assertTrue(result >= 10000);

		investments.simpleSearchByNameWithButton("Apple").filterByTypes("Stock");

		// waitForElementVisible(By.id("gwt-debug-ManagerListItem-contentTable"),
		// 30);
		waitForElementVisible(By.id("gwt-debug-ManagerListWidgetView-contentTable"), 30);

		int size = getSizeOfElements(By.id("gwt-debug-ManagerListItem-contentTable"));

		List<String> returnInvestments = new ArrayList<String>();

		for (int i = 1; i <= size; i++) {

			String thisInvestment = getTextByLocator(
					By.xpath("(.//*[@id='gwt-debug-ManagerListItem-strategyName'])[" + i + "]"));

			returnInvestments.add(thisInvestment);

		}

		for (String thisInvestment : returnInvestments) {

			investments.openInvestmentByName(thisInvestment);

			try {

				if (pageContainsStr("Historical Performance")) {

					waitForElementVisible(By.xpath("//td[.='Investment Type']/following-sibling::td"), 30);

					assertTrue(getTextByLocator(By.xpath("//td[.='Investment Type']/following-sibling::td"))
							.equals("Stock"));

					investments.backToAddInvestment();
				}

			} catch (java.lang.NullPointerException e1) {
				// TODO: handle exception
			} catch (org.openqa.selenium.TimeoutException e2) {
				// TODO: handle exception
			}

		}

		assertTrue(pageContainsStr("Apple Inc"));

	}

	@Test
	public void testMutualFundTop10HoldingsTotal() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		// String investment = "Franklin Biotechnology Discv A SGD Acc";

		String investment = "Franklin Biotechnology Discovery Fund A(acc)SGD";
		// wait(Settings.WAIT_SECONDS);
		main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToInvestmentsPage()
				.simpleSearchByNameWithButton(investment).openInvestmentByName(investment);

		assertTrue(getTextByLocator(By.xpath("//td[.='Investment Type']/following-sibling::td")).equals("Mutual Fund"));

		String[] total = getTextByLocator(By.xpath("//td[.='Total']/following-sibling::td[3]")).split("%");

		Double result = Double.valueOf(total[0]);

		assertTrue(result <= 100);

	}

	@Test
	public void testNonMutualFundTop10HoldingsTotal() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		String searchInvestment = "SPDR® S&P";
		String investment = "";
		InvestmentsPage investmentsPage = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToInvestmentsPage()
				.simpleSearchByNameWithButton(searchInvestment);
		investment = getTextByLocator(By.xpath("(.//*[contains(@id,'gwt-debug-ManagerListItem-strategyName')])[1]"));
		investmentsPage.openInvestmentByName(investment);
		waitGet(By.xpath("//td[.='Investment Type']/following-sibling::td"));
		assertFalse(
				getTextByLocator(By.xpath("//td[.='Investment Type']/following-sibling::td")).equals("Mutual Fund"));

		String[] total = getTextByLocator(By.xpath("//td[.='Total']/following-sibling::td[3]")).split("%");

		Double result = Double.valueOf(total[0]);

		assertTrue(result <= 100);

	}

	@Test
	public void testAddMissingAssetFeature() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		String module = "MODULE_INVESTMENTLIST_STRATEGY_ADD_MISSING_ASSET";

		String type = "Stock/ETF";
		String country = "Hong Kong Exchanges and Clearing Ltd";
		String symbol = "5";
		String strategy = "HSBC Holdings plc";

		String account = "Selenium Test - Selenium Investor";

		// turn on the module toggle
		MenuBarPage mbPage = main.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD);

		mbPage.goToAdminEditPage().editAdminThisField(Settings.Advisor_Company_Module_Permission)
				.jumpByKeyAndLoad(Settings.SeleniumTest_Key).editModuleToggle(module, false, true).clickSubmitButton();
		this.checkLogout();
		handleAlert();

		// check as SeleniumTest
		LoginPage main2 = new LoginPage(webDriver);

		InvestmentsPage investments = main2.loginAs(Settings.USERNAME, Settings.PASSWORD).goToInvestmentsPage()
				.clickRequestMissingInvestment().selectInvestmentTypeToAdd(type).addTicker(country, symbol);
		clickOkButtonIfVisible();
		String selected = getTextByLocator(
				By.xpath(".//*[@id='gwt-debug-SortableFlexTableAsync-table']/tbody/tr/td[2]/div/a"));
		assertTrue(selected.equals(strategy));

		// add the investment
		investments.clickAddToPortfolioButtonFromList().chooseInvestorAccountFromListPage(account);

		assertTrue(pageContainsStr("HSBC"));
		assertTrue(pageContainsStr(strategy));
	}

	@Test
	public void testManagerCannotAddProductToAccount() throws Exception {
		String investment = "Citi China Select HKD";

		LoginPage main = new LoginPage(webDriver);
		// InvestmentsPage ipage =
		main.loginAs("CitiDemo", "CitiDemo").goToInvestmentsPage().simpleSearchByName(investment)
				.selectInvestmentByName(investment).clickAddToPortfolioButtonFromList();

		assertTrue(pageContainsStr("No accounts found to add investments to"));
	}

	@Test
	public void testInvestmentsPageSorting() throws InterruptedException {
		LoginPage main = new LoginPage(webDriver);
		InvestmentsPage iPage = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToInvestmentsPage();

		iPage.simpleSearchByName("abc").clickClearSearchIcon();

		String resultBeforeSorting = getTextByLocator(By.id("gwt-debug-ManagerListWidgetView-pageInfoLabel"));

		// All Types
		iPage.addFilterForInvestment("1", "Bond");
		assertFalse(
				resultBeforeSorting.equals(getTextByLocator(By.id("gwt-debug-ManagerListWidgetView-pageInfoLabel"))));
		iPage.clickResetFilterButton();

		// All Asset Classes
		iPage.addFilterForInvestment("2", "FX");
		assertFalse(
				resultBeforeSorting.equals(getTextByLocator(By.id("gwt-debug-ManagerListWidgetView-pageInfoLabel"))));
		iPage.clickResetFilterButton();

		// All Regions
		iPage.addFilterForInvestment("3", "Europe Dev.");
		assertFalse(
				resultBeforeSorting.equals(getTextByLocator(By.id("gwt-debug-ManagerListWidgetView-pageInfoLabel"))));
		iPage.clickResetFilterButton();

		// All Currencies
		iPage.addFilterForInvestment("4", "HKD");
		assertFalse(
				resultBeforeSorting.equals(getTextByLocator(By.id("gwt-debug-ManagerListWidgetView-pageInfoLabel"))));
		iPage.clickResetFilterButton();

		// All Products
		iPage.addFilterForInvestment("5", "Ageas Aviator");
		assertFalse(
				resultBeforeSorting.equals(getTextByLocator(By.id("gwt-debug-ManagerListWidgetView-pageInfoLabel"))));
		iPage.clickResetFilterButton();

		// All Portfolio Classifications
		iPage.addFilterForInvestment("6", "Core");
		assertFalse(
				resultBeforeSorting.equals(getTextByLocator(By.id("gwt-debug-ManagerListWidgetView-pageInfoLabel"))));
		iPage.clickResetFilterButton();

		// All Equity Sectors
		iPage.addFilterForInvestment("7", "Equity-Industrial Goods");
		assertFalse(
				resultBeforeSorting.equals(getTextByLocator(By.id("gwt-debug-ManagerListWidgetView-pageInfoLabel"))));
		iPage.clickResetFilterButton();

		// All Currency Sectors
		iPage.addFilterForInvestment("8", "Alternatives-FX");
		assertFalse(
				resultBeforeSorting.equals(getTextByLocator(By.id("gwt-debug-ManagerListWidgetView-pageInfoLabel"))));
		iPage.clickResetFilterButton();

		// All Commodity Sectors
		iPage.addFilterForInvestment("9", "Commodity-Agriculture");
		assertFalse(
				resultBeforeSorting.equals(getTextByLocator(By.id("gwt-debug-ManagerListWidgetView-pageInfoLabel"))));
		iPage.clickResetFilterButton();

		// All Fixed Income Sectors
		iPage.addFilterForInvestment("10", "Fixed Income-Broad");
		assertFalse(
				resultBeforeSorting.equals(getTextByLocator(By.id("gwt-debug-ManagerListWidgetView-pageInfoLabel"))));
		iPage.clickResetFilterButton();

		// All Alternatives Sectors
		iPage.addFilterForInvestment("11", "Alternatives-Broad");
		assertFalse(
				resultBeforeSorting.equals(getTextByLocator(By.id("gwt-debug-ManagerListWidgetView-pageInfoLabel"))));
		iPage.clickResetFilterButton();

		// All Real Estate Sectors
		iPage.addFilterForInvestment("12", "Real Estate-Broad");
		assertFalse(
				resultBeforeSorting.equals(getTextByLocator(By.id("gwt-debug-ManagerListWidgetView-pageInfoLabel"))));
		iPage.clickResetFilterButton();

		// All Approved for Sale
		iPage.addFilterForInvestment("13", "Hong Kong");
		assertFalse(
				resultBeforeSorting.equals(getTextByLocator(By.id("gwt-debug-ManagerListWidgetView-pageInfoLabel"))));
		iPage.clickResetFilterButton();

		// All Restrictions
		iPage.addFilterForInvestment("14", "Illiquid");
		assertFalse(
				resultBeforeSorting.equals(getTextByLocator(By.id("gwt-debug-ManagerListWidgetView-pageInfoLabel"))));
		iPage.clickResetFilterButton();
	}

	@Test
	public void testAdvancedSearchPanelInInvestmentPage() throws Exception {
		LoginPage main = new LoginPage(webDriver);
		InvestmentsPage iPage = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToInvestmentsPage();

		// Investment Name
		searchInvestmentByCustomAdvancedSearch(iPage, "Investment Name", "Tesla");
		assertTrue(pageContainsStr("Tesla"));
		iPage.clickClearSearchIcon();

		// Region
		searchInvestmentByCustomAdvancedSearch(iPage, "Region", "Global");
		int page = getPagesOfElementsForInvestmentList(By.id("gwt-debug-ManagerListItem-strategyName"));
		log(getTextByLocator(By.id("gwt-debug-ManagerListWidgetView-searchBox")));
		assertTrue(getTextByLocator(By.id("gwt-debug-ManagerListWidgetView-searchBox")).contains("Global"));
		iPage.clickClearSearchIcon();

		// Symbol
		searchInvestmentByCustomAdvancedSearch(iPage, "Symbol", "TSLA");
		assertTrue(pageContainsStr("Tesla"));
		iPage.clickClearSearchIcon();

		// ISIN
		searchInvestmentByCustomAdvancedSearch(iPage, "ISIN", "US88160R1014");
		assertTrue(pageContainsStr("Tesla"));
		iPage.clickClearSearchIcon();

		// SEDOL
		searchInvestmentByCustomAdvancedSearch(iPage, "SEDOL", "13");
		assertTrue(pageContainsStr("Aberdeen Global Indian Equity D2"));
		iPage.clickClearSearchIcon();

		// Fund Code
		searchInvestmentByCustomAdvancedSearch(iPage, "Fund Code", "123");
		assertTrue(pageContainsStr("AllianceBernstein"));
		iPage.clickClearSearchIcon();

		// Reuters RIC
		searchInvestmentByCustomAdvancedSearch(iPage, "Reuters RIC", "T");
		assertTrue(pageContainsStr("No investments match the filter criteria."));
		iPage.clickClearSearchIcon();

		// Bloomberg ID
		searchInvestmentByCustomAdvancedSearch(iPage, "Bloomberg ID", "G");
		assertTrue(pageContainsStr("No investments match the filter criteria."));
		iPage.clickClearSearchIcon();

		// WKN
		searchInvestmentByCustomAdvancedSearch(iPage, "WKN", "A1CX3T");
		assertTrue(pageContainsStr("Tesla"));
		iPage.clickClearSearchIcon();

	}

	@Test
	public void testHelvetiaLink() throws InterruptedException {

		String url = Settings.BASE_URL + "#investmentList;token=qPTdfweRT";

		initForHelvetia(url);

		InvestmentsPage iPage = new InvestmentsPage(webDriver);

		assertTrue(isElementVisible(By.id("gwt-debug-ManagerListWidgetView-managerListPanel")));

		// Search For CleVesto Allcas
		iPage.addFilterForInvestment("3", "CleVesto Allcase");
		assertTrue(pageContainsStr("DWS Deutschland LC"));
		iPage.clickResetFilterButton();

		// Search For CleVesto Favorites
		iPage.addFilterForInvestment("3", "CleVesto Favorites");
		assertTrue(pageContainsStr("AC Risk Parity 7 Fund EUR B"));
		iPage.clickResetFilterButton();

		// Search For CleVesto Select
		iPage.addFilterForInvestment("3", "CleVesto Select");
		assertTrue(pageContainsStr("Accura - AF1 A"));
		iPage.clickResetFilterButton();

	}

	@Test
	public void testHelvetiaFactsheet() throws InterruptedException {
		String url = Settings.BASE_URL + "?locale=de_DE#investmentList;token=qPTdfweRT";

		initForHelvetia(url);

		InvestmentsPage iPage = new InvestmentsPage(webDriver);

		assertTrue(isElementVisible(By.id("gwt-debug-ManagerListWidgetView-managerListPanel")));

		String investment = getTextByLocator(By.id("gwt-debug-ManagerListItem-strategyName"));

		String investmentStringToCheck = investment.split(" ")[0];

		iPage.simpleSearchByName(investment);
		waitForElementVisible(By.id("gwt-debug-ManagerListItem-strategyName"), 20);

		iPage.clickInvestmentByName(investment).clickDetailButton();

		waitForElementVisible(By.id("gwt-debug-ManagerDisplayWidgetView-strategyName"), 30);

		clickElement(By.xpath(".//*[@id='gwt-debug-ManagerDisplay-docPanel']//a[contains(text(), 'KIID')]"));

		// for (String winHandle : webDriver.getWindowHandles()) {
		// webDriver.switchTo().window(winHandle);
		// }
		wait(30);
		log(investmentStringToCheck);
		assertTrue(pageContainsStr(investmentStringToCheck));
		log("done");
	}

	// @Test
	// public void testHelvetiaDatesOfReporting() throws InterruptedException {
	//
	// ArrayList<String> fundsToCheck = new ArrayList<String>();
	// ArrayList<String> fundsWithoutDate = new ArrayList<String>();
	// String url = Settings.BASE_URL
	// + "?locale=de_DE#investmentList;token=qPTdfweRT";
	//
	// initForHelvetia(url);
	//
	// InvestmentsPage iPage = new InvestmentsPage(webDriver);
	//
	// iPage.simpleSearchByName("JPMorgan");
	// waitForElementVisible(By.id("gwt-debug-ManagerListItem-strategyName"),
	// 20);
	//
	// iPage.clickClearSearchIcon();
	// waitForElementVisible(By.id("gwt-debug-ManagerListItem-strategyName"),
	// 20);
	//
	// clickElement(By.xpath(".//button[.='15']"));
	// waitForElementVisible(By.id("gwt-debug-ManagerListItem-strategyName"),
	// 20);
	//
	// int pageSize = getPagesOfElementsForInvestmentList(By
	// .id("gwt-debug-ManagerListItem-strategyName"));
	//
	// for (int i = 1; i <= pageSize; i++) {
	//
	// waitForElementVisible(
	// By.id("gwt-debug-ManagerListItem-strategyName"), 20);
	//
	// int elementSize = getSizeOfElements(By
	// .id("gwt-debug-ManagerListItem-strategyName"));
	//
	// for (int j = 1; j <= elementSize; j++) {
	// clickElement(By
	// .xpath("(.//a[@id='gwt-debug-ManagerListItem-strategyName'])["
	// + String.valueOf(j) + "]"));
	// String fundName = null;
	// String fund = null;
	// String fundYear = null;
	//
	// try {
	// waitForElementVisible(
	// By.xpath(".//*[.='Auflagedatum']//following-sibling::td"),
	// 20);
	//
	// fundName = getTextByLocator(By
	// .id("gwt-debug-ManagerDisplayWidgetView-strategyName"));
	//
	// fund = getTextByLocator(By
	// .xpath(".//*[.='Auflagedatum']//following-sibling::td"));
	//
	// fundYear = fund.substring(Math.max(fund.length() - 4, 0));
	// } catch (TimeoutException e) {
	// fundsWithoutDate.add(fund);
	// break;
	// }
	//
	// //
	// // clickElement(By
	// //
	// .xpath("(.//*[@style='font-family:Trebuchet MS, Verdana,
	// sans-serif;font-size:11px;font-weight:bold;color:#1E1E1E;fill:#1E1E1E;'])[1]"));
	//
	// String display = getTextByLocator(By
	// .xpath(".//*[@class='highcharts-range-selector' and @name = 'min']"));
	//
	// String displayYear = display.substring(0, 4);
	//
	// if (Integer.valueOf(fundYear) < 2005) {
	// try {
	// assertTrue(Integer.valueOf(displayYear) == 2005);
	// } catch (AssertionError e) {
	// fundsToCheck.add(fundName);
	// }
	// } else {
	// try {
	// assertTrue(Integer.valueOf(fundYear).equals(
	// Integer.valueOf(displayYear)));
	// } catch (AssertionError e) {
	// fundsToCheck.add(fundName);
	// }
	// }
	//
	// clickElement(By
	// .id("gwt-debug-ManagerDisplayWidgetView-backButtonLabel"));
	// waitForElementVisible(
	// By.id("gwt-debug-ManagerListItem-strategyName"), 20);
	// }
	// clickElement(By.id("gwt-debug-ManagerListWidgetView-nextPageBtn"));
	// }
	// log("********Funds with date problem*********");
	// for (String fund : fundsToCheck) {
	// System.out.println(fund);
	// }
	// log("********Funds without inception date*********");
	// for (String fund : fundsWithoutDate) {
	// System.out.println(fund);
	// }
	// }

	public void searchInvestmentByCustomAdvancedSearch(InvestmentsPage investments, String fieldName, String keyword)
			throws Exception {

		waitForElementVisible(By.id("gwt-debug-ManagerListWidgetView-advancedSearchBtn"), 30);
		investments.goToAdvancedSearchPage().searchByCustomFields(fieldName, keyword).clickSearchButton();
	}

	public void initForHelvetia(String platform) throws InterruptedException {

		webDriver.get(platform);
		// wait for Help Overlay
		waitForElementVisible(By.xpath(".//*[@class='introjs-button introjs-skipbutton']"), 30);
		clickElement(By.xpath(".//*[@class='introjs-button introjs-skipbutton']"));
		clickOkButtonIfVisible();
		waitForElementVisible(By.id("gwt-debug-ManagerListItem-strategyName"), 10);

	}
}
