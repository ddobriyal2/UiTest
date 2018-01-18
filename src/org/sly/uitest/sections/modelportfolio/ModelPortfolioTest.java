package org.sly.uitest.sections.modelportfolio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.abstractpage.MenuBarPage;
import org.sly.uitest.pageobjects.admin.AdminEditPage;
import org.sly.uitest.pageobjects.assetmanagement.ModelPortfoliosPage;
import org.sly.uitest.pageobjects.clientsandaccounts.AnalysisPage;
import org.sly.uitest.pageobjects.clientsandaccounts.HistoryPage;
import org.sly.uitest.pageobjects.clientsandaccounts.HoldingsPage;
import org.sly.uitest.pageobjects.commissioning.AdvisoryFeesPage;
import org.sly.uitest.pageobjects.investments.InvestmentsPage;
import org.sly.uitest.settings.Settings;

/**
 * @author Lynne Huang
 * @date : 9 Sep, 2015
 * @company Prive Financial
 */

public class ModelPortfolioTest extends AbstractTest {

	@Test
	public void testModelPortfolioAndHistoricalRebalancing() throws Exception {

		String name = "Test Model Portfolio" + UUID.randomUUID().getLeastSignificantBits();
		String description = "This model portfolio aims to out-perform the S&P 500";
		String startValue = "100";
		String currency = "USD";
		String providerPlatform = "Generali Vision - Advisor ABC";
		String visibility = "Visible to selected advisors";
		String benchmark = "S&P 500 TR USD";
		String module = "MODULE_MODELPORTFOLIO_EDIT_HIDE_ADVANCED_INFOMATION";
		Boolean investor = false;
		Boolean advisor = false;

		/*
		 * LoginPage mainAdmin = new LoginPage(webDriver); MenuBarPage mbPage =
		 * mainAdmin.loginAs(Settings.SysAdmin_PASSWORD,
		 * Settings.SysAdmin_PASSWORD);
		 *
		 * wait(10); boolean isMaterial1 = isElementVisible(By
		 * .xpath(".//*[@id='page-wrapper']/div[1]/nav/div/a/i"));
		 *
		 * if ((isMaterial1 == true) ||
		 * (webDriver.getCurrentUrl().indexOf("MATERIAL") > -1)) {
		 * mbPage.goToAdminEditPageMaterialView() .editAdminThisField(
		 * Settings.Advisor_Company_Module_Permission)
		 * .jumpByKeyAndLoad(Settings.AdvisorABCCompany_Key)
		 * .editModuleToggle(module, investor, advisor) .clickSubmitButton();
		 * clickElement(By .xpath(
		 * ".//*[.='log out' and @class='material-logout']")); wait(2);
		 * handleAlert(); } else { mbPage.goToAdminEditPage()
		 * .editAdminThisField( Settings.Advisor_Company_Module_Permission)
		 * .jumpByKeyAndLoad(Settings.AdvisorABCCompany_Key)
		 * .editModuleToggle(module, investor, advisor) .clickSubmitButton();
		 * wait(Settings.WAIT_SECONDS); this.checkLogout(); handleAlert(); }
		 */
		// create a new model portfolio
		LoginPage main = new LoginPage(webDriver);
		MenuBarPage menuBarPage = main.loginAs(Settings.USERNAME, Settings.PASSWORD);
		// wait(5);

		ModelPortfoliosPage modelPortfoliosPage = menuBarPage.goToModelPortfoliosPage().clickManageModelPortfolios()
				.clickCreatePortfolioButton();

		HoldingsPage newMP = modelPortfoliosPage.editModelPortfolioName(name).editModelPortfolioDescription(description)
				.editModelPortfolioStartValue(startValue).editModelPortfolioCurrency(currency)
				.editModelPortfolioAddPlatform(providerPlatform).editModelPortfolioAddBenchmark(benchmark)
				.editModelPortfolioVisibility(visibility).clickSaveButton();
		waitForElementVisible(By.id("gwt-debug-PortfolioAllocationView-satelliteWrapperDiv"), 60);

		scrollToTop();
		// wait(3);
		InvestmentsPage investments = newMP.goToTransactionHistoryPage().editModelPortfolioHistory()
				.addModelPortfolioNewHistoricalRebalancing().addModelPortfolioInvestments()
				.simpleSearchByNameWithButton("Invesco");

		this.waitForElementVisible(By.id("gwt-debug-ManagerListItem-strategyName"), Settings.WAIT_SECONDS);
		ArrayList<String> invests = new ArrayList<String>();
		for (int i = 1; i <= 5; i++) {

			String investment = getTextByLocator(
					By.xpath("(.//*[@id='gwt-debug-ManagerListItem-strategyName'])[" + i + "]"));
			invests.add(investment);
		}
		System.out.println(invests);
		String investment1 = invests.get(0);
		String investment2 = invests.get(1);
		String investment3 = invests.get(2);
		String investment4 = invests.get(3);
		String investment5 = invests.get(4);

		HistoryPage historyPage = ((HistoryPage) investments.selectInvestmentByName(investment1)
				.selectInvestmentByName(investment2).selectInvestmentByName(investment3)
				.selectInvestmentByName(investment4).selectInvestmentByName(investment5)
				.clickAddToPortfolioButtonNewView()).setNewAllocationForInvestment(investment1, "10")
						.setNewAllocationForInvestment(investment2, "20")
						.setNewAllocationForInvestment(investment3, "30")
						.setNewAllocationForInvestment(investment4, "15")
						.setNewAllocationForInvestment(investment5, "25").clickModelPortfolioAllocationSaveButton();

		this.waitForWaitingScreenNotVisible();

		HoldingsPage holdingPage = historyPage.clickModelPortfolioRebalancingSaveButton().goToTransactionHistoryPage()
				.confirmHistoryStatus().goToHoldingsPage();
		this.refreshPage();
		// element.sendKeys(Keys.RETURN);
		// clickElement(By.xpath(".//*[@id='gwt-debug-PortfolioOverviewTab-hyperlink'
		// and .='Holdings']"));
		waitForElementVisible(By.id("gwt-debug-PortfolioAllocationView-allocationTablePanel"), 30);
		assertTrue(pageContainsStr(investment1));
		assertTrue(pageContainsStr(investment2));
		assertTrue(pageContainsStr(investment3));
		assertTrue(pageContainsStr(investment4));
		assertTrue(pageContainsStr(investment5));
		assertTrue(pageContainsStr(name));

		// Model Portfolio test plan #4 ...... test analysis page
		AnalysisPage analysis = newMP.goToAnalysisPage();

		waitForElementVisible(By.xpath(".//*[@id='gwt-debug-InvestorModelPortfolioPresenter-allocationTable']//canvas"),
				Settings.WAIT_SECONDS);

		assertTrue(webDriver
				.findElement(By.xpath(".//*[@id='gwt-debug-InvestorModelPortfolioPresenter-allocationTable']//canvas"))
				.isDisplayed());
		assertTrue(pageContainsStr(investment1));
		assertTrue(pageContainsStr(investment2));
		assertTrue(pageContainsStr(investment3));
		assertTrue(pageContainsStr(investment4));
		assertTrue(pageContainsStr(investment5));

		// Model Portfolio test plan #5 ...... test backfill historical data
		HistoryPage history = analysis.goToTransactionHistoryPage().editModelPortfolioHistory();

		String date = getTextByLocator(By.id("gwt-debug-RebalancingHistorySingleView-dateLabel"));
		String today = getCurrentTimeInFormat("d-MMM-yyyy");
		String lastYear = today.substring(0, today.length() - 1)
				+ String.valueOf(Integer.valueOf(today.substring(today.length() - 1, today.length())) - 1);

		history.editModelPortfolioHistoryByDate(date).editModelPortfolioAllocationDate(lastYear)
				.clickModelPortfolioAllocationSaveButton().clickModelPortfolioRebalancingSaveButton();

		clickOkButtonIfVisible();
		// wait(2 * Settings.WAIT_SECONDS);
		history.goToTransactionHistoryPage();
		// wait(2 * Settings.WAIT_SECONDS);
		assertTrue(this.getTextByLocator(By.id("gwt-debug-RebalancingHistorySingleView-dateLabel")).contains(lastYear));

		history.goToAnalysisPage();

		String[] dates = lastYear.split("-");
		String day = dates[0];
		String month = dates[1];
		String year = dates[2];
		String lastYearReformat = month + " " + day + ", " + year;

		/* svg */

		// WebElement svgElement = webDriver.findElement(By.cssSelector("svg"));
		//
		// List<WebElement> gElements = svgElement.findElements(By
		// .cssSelector("g"));
		//
		// WebElement start = gElements.get(2)
		// .findElement(By.cssSelector("tspan"));
		// String startDate = start.getText();

		// assertTrue(startDate.equals(lastYearReformat));

		// Model Portfolio test plan #3 ......edit name and benchmarkF
		String newName = "New Model Portfolio" + UUID.randomUUID().getLeastSignificantBits();
		String newBenchmark = "Absolute Return 8%";

		newMP.goToModelPortfoliosPage().clickManageModelPortfolios();
		assertTrue(pageContainsStr(name));
		scrollToTop();
		ModelPortfoliosPage modelPro = new ModelPortfoliosPage(webDriver);
		// modelPro.goToPortfolioManagement();

		modelPro.editModelPortfolioByName(name).editModelPortfolioName(newName)
				.editModelPortfolioAddBenchmark(newBenchmark).clickSaveButton();

		this.refreshPage();
		assertTrue(pageContainsStr(newName));
		// assertTrue(!pageContainsStr(name));

		// delete modelportfolio
		newMP.goToModelPortfoliosPage().clickManageModelPortfolios();

		ModelPortfoliosPage modelPro2 = new ModelPortfoliosPage(webDriver);

		modelPro2.deleteModelPortfolioByName(newName);

		// assertTrue(!pageContainsStr(newName));

	}

	@Test
	public void testExploreInvestmentFromManager() throws InterruptedException, Exception {

		String name = "Test Model Portfolio" + UUID.randomUUID().getLeastSignificantBits();
		String description = "This model portfolio aims to out-perform the S&P 500";
		String startValue = "100";
		String currency = "USD";
		String providerPlatform = "Paper Trading";
		String visibility = "Visible to selected advisors";
		String benchmark = "Absolute Return 8%";

		// create a new model portfolio
		LoginPage main = new LoginPage(webDriver);
		MenuBarPage mbPage = main.loginAs(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD);
		// wait(5);

		HoldingsPage newMP = mbPage.goToModelPortfoliosPage().clickManageModelPortfolios().clickCreatePortfolioButton()
				.editModelPortfolioName(name).editModelPortfolioDescription(description)
				.editModelPortfolioStartValue(startValue).editModelPortfolioCurrency(currency).removeProvider("all")
				.editModelPortfolioAddPlatform(providerPlatform).editModelPortfolioAddBenchmark(benchmark)
				.editModelPortfolioVisibility(visibility).clickSaveButton();

		this.waitForWaitingScreenNotVisible();
		this.checkLogout();
		this.handleAlert();

		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 30);

		this.handleAlert();
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 30);
		LoginPage main2 = new LoginPage(webDriver);
		InvestmentsPage investments = main2.loginAs("JarlManager", "JarlManager").goToInvestmentsPage();
		// .filterByExecutionPlatforms("Generali Vision");

		String investment1 = investments.getInvestmentNameByIndex("1");
		String investment2 = investments.getInvestmentNameByIndex("2");
		investments.selectInvestmentByName(investment1).selectInvestmentByName(investment2);

		investments.clickAddToPortfolioButtonByManager(name + " - Jarl Olsen");

		assertTrue(pageContainsStr(investment1));
		assertTrue(pageContainsStr(investment2));

		// test if performance chart shows
		// wait(Settings.WAIT_SECONDS);
		log(name);
		newMP.goToModelPortfoliosPage().goToModelPortfolioInfoPage(name);
		waitForElementVisible(By.id("gwt-debug-InvestorModelPortfolioView-chartPanel"), 30);

		assertTrue(isElementDisplayed(By.id("gwt-debug-InvestorModelPortfolioView-chartPanel")));

		newMP.goToModelPortfoliosPage().goToModelPortfolioInfoPage(name);

		waitForElementVisible(By.id("gwt-debug-InvestorModelPortfolioView-chartPanel"), 30);

		assertTrue(isElementDisplayed(By.id("gwt-debug-InvestorModelPortfolioView-chartPanel")));
		// delete
		newMP.goToModelPortfoliosPage().clickManageModelPortfolios().deleteModelPortfolioByName(name);

	}

	@Test
	public void testManagerCreateModelPortfolio() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		String name = "Model Portfolio-12345";
		String benchmark = "Absolute Return 8%";

		ModelPortfoliosPage portfolios = main.loginAs("JarlManager", "JarlManager").goToModelPortfoliosPage()
				.clickManageModelPortfolios();

		waitForElementVisible(By.xpath(".//*[.='Account Linked']"), 30);
		assertTrue(pageContainsStr("Account Linked"));

		portfolios.clickCreatePortfolioButton();

		// check visibiligy, white list, owner, benchmark
		assertTrue(pageContainsStr("Visibility"));
		assertTrue(pageContainsStr("White List"));
		assertTrue(pageContainsStr("Owner"));
		assertTrue(pageContainsStr("Benchmark"));

		// delete the model portfolio
		portfolios.editModelPortfolioName(name).editModelPortfolioAddBenchmark(benchmark).clickSaveButton()
				.goToModelPortfoliosPage().clickManageModelPortfolios().deleteModelPortfolioByName(name);

	}

	@Test
	public void testModelPortfolioModuleToggles() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		String module1 = "MODULE_MODELPORTFOLIO_EDIT_HIDE_ADVANCED_INFOMATION";
		String module2 = "MODULE_MODELPORTFOLIO_TABLE_HIDE_ACCOUNT_LINKED";
		// String module3 = "MODULE_MODELPORTFOLIO_REBALANCE_HIDE_ORDER_TAB";

		// enable modules
		// wait(2);
		MenuBarPage mbPage = main.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD);
		// wait(5);

		AdminEditPage adminEditPage = mbPage.goToAdminEditPage();

		adminEditPage.editAdminThisField(Settings.Advisor_Company_Module_Permission)
				.jumpByKeyAndLoad(Settings.AdvisorABCCompany_Key).editModuleToggle(module1, false, true)
				.editModuleToggle(module2, false, true).clickSubmitButton();

		// wait(Settings.WAIT_SECONDS);
		this.checkLogout();
		// wait(2);
		this.handleAlert();
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 30);

		// check as JarlAdvisor
		LoginPage main2 = new LoginPage(webDriver);

		// check hide account_linked
		MenuBarPage mbPage3 = main2.loginAs(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD);
		// wait(5);

		ModelPortfoliosPage portfolios = mbPage3.goToModelPortfoliosPage().clickManageModelPortfolios();
		checkAsserts(portfolios);

		assertFalse(pageContainsStr("Account Linked"));

		// check hide advanced information
		portfolios.clickCreatePortfolioButton();

		assertFalse(isElementVisible(By.xpath("//tr[td[text()=' Provider Platform ']]")));
		assertFalse(isElementVisible(By.xpath("//tr[td[text()=' Visibility ']]")));
		assertFalse(isElementVisible(By.xpath("//tr[td[text()=' White List ']]")));
		assertFalse(isElementVisible(By.xpath("//tr[td/div[text()='Owner']]")));
		assertFalse(isElementVisible(By.xpath("//tr[td[text()=' Benchmark ']]")));

		portfolios.clickCancelButton();
		// wait(Settings.WAIT_SECONDS);
		this.checkLogout();
		// wait(Settings.WAIT_SECONDS);
		this.handleAlert();
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 30);

		// disable modules
		LoginPage main3 = new LoginPage(webDriver);

		// enable modules
		// wait(2);
		MenuBarPage mbPage1 = main3.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD);
		// wait(5);

		AdminEditPage adminEditPage1 = mbPage1.goToAdminEditPage();
		// wait(2);

		adminEditPage1.editAdminThisField(Settings.Advisor_Company_Module_Permission)
				.jumpByKeyAndLoad(Settings.AdvisorABCCompany_Key).editModuleToggle(module1, false, false)
				.editModuleToggle(module2, false, false).clickSubmitButton();

		// wait(Settings.WAIT_SECONDS);
		this.checkLogout();
		// wait(2);
		this.handleAlert();
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 30);

		// check as JarlAdvisor
		LoginPage main4 = new LoginPage(webDriver);

		// check hide account_linked
		MenuBarPage mbPage4 = main4.loginAs(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD);
		// wait(5);

		ModelPortfoliosPage portfolios2 = mbPage4.goToModelPortfoliosPage().clickManageModelPortfolios();
		checkAssertsModuleToggles(portfolios2);
		this.checkLogout();
		// wait(Settings.WAIT_SECONDS);
		this.handleAlert();
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 30);

	}

	@Test
	public void testManagerOnlyAccountCreateModelPortfolio() throws Exception {
		LoginPage main = new LoginPage(webDriver);

		String name = "Model Portfolio-123456";
		String benchmark = "UK CPI";

		ModelPortfoliosPage portfolios = main.loginAs("MatrixAdmin", "MatrixAdmin").goToModelPortfoliosPage()
				.clickManageModelPortfolios();

		portfolios.clickCreatePortfolioButton();

		// Save model portfolio
		portfolios.editModelPortfolioName(name).editModelPortfolioAddBenchmark(benchmark).clickSaveButton();

		// make sure that holding page is loaded.
		HoldingsPage holdings = new HoldingsPage(webDriver);

		// delete model portfolio
		holdings.goToModelPortfoliosPage().clickManageModelPortfolios().deleteModelPortfolioByName(name);
	}

	@Test
	public void testModelPortfolioReporting() throws Exception {

		String name = "testFactSheet";

		LoginPage main = new LoginPage(webDriver);

		String modelPortfolio = "Aggressive";

		ModelPortfoliosPage portfolios = main.loginAs("JarlManager", "JarlManager").goToModelPortfoliosPage()
				.goToModelPortfolioInfoPage(modelPortfolio);
		// clickElement(By.xpath(".//*[.='" + modelPortfolio + "']"));

		waitForElementVisible(By.xpath(".//*[contains(text(),'Download Fact Sheet:')]"), Settings.WAIT_SECONDS);

		assertTrue(pageContainsStr("Download Fact Sheet:"));

		portfolios.clickDownloadFactsheetButton().editNameOfExportFile(name).clickProceedButtonForReporting();

		checkDownloadedFile(name + ".pdf");
	}

	@Test
	public void testLinkingModelPortfolioInModelPortfoliopageAndEditAndDelete() throws Exception {
		String modelPortfolio = "Test Model Portfolio" + getRandName();
		String investment1 = "Walter Energy, Inc.";
		String investment2 = "Tesla Inc";
		String investment3 = "Vista Gold Corp.";
		String accountToAdd = "Fund Account (Nate Ko)";
		String account = "Fund Account";
		LoginPage main = new LoginPage(webDriver);
		HoldingsPage holdings = main.loginAs(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD)
				.goToAccountOverviewPage().simpleSearchByString(account).goToAccountHoldingsPageByName(account);
		if (pageContainsStr("is linked")) {
			holdings.goToDetailsPage().unlinkModelPortfolioForAccount();
		}
		holdings.goToModelPortfoliosPage().clickManageModelPortfolios().clickCreatePortfolioButton()
				.editModelPortfolioName(modelPortfolio).clickSaveButton().clickReallocateButton()
				.clickAddInvestmentButton().simpleSearchByName(investment1).selectInvestmentByName(investment1)
				.simpleSearchByName(investment2).selectInvestmentByName(investment2).clickAddToPortfolioButton();

		holdings.setNewAllocationForInvestment(investment1, "5").setNewAllocationForInvestment(investment2, "5")
				.clickRebalancePreviewAndConfirm(investment1, investment2).confirmHistoryStatus()
				.goToModelPortfoliosPage().clickAddButtonByName(modelPortfolio).addModelPortfolioToAccount(accountToAdd)
				.goToAccountOverviewPage().simpleSearchByString(account).goToAccountHoldingsPageByName(account);

		assertTrue(pageContainsStr("This portfolio is linked to " + modelPortfolio));

		ModelPortfoliosPage model = holdings.goToModelPortfoliosPage().clickManageModelPortfolios();

		assertFalse(isElementVisible(By.xpath(".//td[*[*[contains(text(),'" + modelPortfolio
				+ "')]]]//following-sibling::td//button[@id='gwt-debug-InvestorAccountTable-deleteImage']")));

		HoldingsPage holdings2 = model.goToModelPortfolio(modelPortfolio).clickReallocateButton()
				.clickAddInvestmentButton().simpleSearchByName(investment3).selectInvestmentByName(investment3)
				.clickAddToPortfolioButton();

		holdings2.setNewAllocationForInvestment(investment3, "10").clickRebalancePreviewAndConfirm(investment3)
				.confirmHistoryStatus().goToAccountOverviewPage().simpleSearchByString(account)
				.goToAccountHoldingsPageByName(account);
		log(investment3);
		log(modelPortfolio);
		try {
			assertTrue(pageContainsStr(investment3));
		} catch (AssertionError e) {
			this.refreshPage();
			holdings.goToHoldingsPage();
			assertTrue(pageContainsStr(investment3));
		}
		holdings.goToDetailsPage().goToEditPageByField("Model Portfolio").unlinkModelPortfolio()
				.goToModelPortfoliosPage().clickManageModelPortfolios().deleteModelPortfolioByName(modelPortfolio);
	}

	@Test
	public void testAdvisoryFeesOfModelPortfolio() throws Exception {
		String name = "model portfolio " + this.getRandName();
		String feesType = "Percentage of AUM";
		String amount = "2.35";
		String amount2 = "34.56";
		String timeFormat = "dd-MMM-yyyy";
		String tomorrow = this.getDateAfterDays(this.getCurrentTimeInFormat(timeFormat), 1, timeFormat);
		String specificDate = "31-Dec-2017";
		String modelPortfolio = "Sample Model Portfolio";
		LoginPage main = new LoginPage(webDriver);
		AdvisoryFeesPage advisoryFees = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToModelPortfoliosPage()
				.clickManageModelPortfolios().clickCreatePortfolioButton().editModelPortfolioName(name)
				.clickSetAumFees().editCurrentFeesByType(feesType).editAmountOfAdvisoryFees(amount).clickFromTomorrow()
				.clickToUntilChanged().clickSaveButttonForFees();

		// assertEquals("2.35% p.a.",
		// this.getTextByLocator(By.id("gwt-debug-SingleFeeUi-amountLabel")));

		AdvisoryFeesPage advisoryFees2 = ((ModelPortfoliosPage) advisoryFees.clickOkButtonForAdvisoryFees())
				.clickSaveButton().goToModelPortfoliosPage().clickManageModelPortfolios().editModelPortfolioByName(name)
				.clickSetAumFees();

		assertEquals("USD", this.getTextByLocator(By.id("gwt-debug-SingleFeeUi-currencyLabel")));

		assertTrue(this.getTextByLocator(By.id("gwt-debug-SingleFeeUi-timeframeLabel")).contains(tomorrow));
		assertTrue(this.getTextByLocator(By.id("gwt-debug-SingleFeeUi-timeframeLabel")).contains("Until Changed"));

		advisoryFees2.editCurrentFeesByType(feesType).editAmountOfAdvisoryFees(amount2)
				.editReasonOfAdvisoryFees("Model Portfolio").editModelPortfolioOfAdvisoryFees(modelPortfolio)
				.editToSpecificDate(specificDate).clickSaveButttonForFees();

		// assertEquals("34.56% p.a.",
		// this.getTextByLocator(By.id("gwt-debug-SingleFeeUi-amountLabel")));

		assertTrue(this.getTextByLocator(By.id("gwt-debug-SingleFeeUi-timeframeLabel")).contains(tomorrow));
		assertTrue(this.getTextByLocator(By.id("gwt-debug-SingleFeeUi-timeframeLabel")).contains(specificDate));

		ModelPortfoliosPage mpPage2 = advisoryFees2.clickOkButtonForAdvisoryFees();
		mpPage2.clickCancelButton().deleteModelPortfolioByName(name);
	}

	private void checkAssertsModuleToggles(ModelPortfoliosPage portfolios2) throws Exception {
		try {
			waitForElementVisible(By.xpath(".//*[.='Account Linked']"), Settings.WAIT_SECONDS * 2);
		} catch (TimeoutException e) {

		}
		assertTrue(pageContainsStr("Account Linked"));
		// check hide advanced information
		portfolios2.clickCreatePortfolioButton();
		this.waitForElementVisible(By.xpath(".//*[contains(text(),'Provider Platform')]"), Settings.WAIT_SECONDS);
		assertTrue(pageContainsStr("Provider Platform"));
		assertTrue(pageContainsStr("Visibility"));
		assertTrue(pageContainsStr("White List"));
		assertTrue(pageContainsStr("Owner"));
		assertTrue(pageContainsStr("Benchmark"));
		portfolios2.clickCancelButton();

		// this.checkLogout();

		this.handleAlert();
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 30);
	}

	private void checkAsserts(ModelPortfoliosPage portfolios) throws Exception {
		this.waitForWaitingScreenNotVisible();
		assertFalse(pageContainsStr("Account Linked"));
		// check hide advanced information
		this.waitForElementVisible(By.id("gwt-debug-InvestorAccountTable-createModelPortBtn"), Settings.WAIT_SECONDS);
		portfolios.clickCreatePortfolioButton();
		assertFalse(isElementVisible(By.xpath("//tr[td[text()=' Provider Platform ']]")));
		assertFalse(isElementVisible(By.xpath("//tr[td[text()=' Visibility ']]")));
		assertFalse(isElementVisible(By.xpath("//tr[td[text()=' White List ']]")));
		assertFalse(isElementVisible(By.xpath("//tr[td/div[text()='Owner']]")));
		assertFalse(isElementVisible(By.xpath("//tr[td[text()=' Benchmark ']]")));
		portfolios.clickCancelButton();
	}

}
