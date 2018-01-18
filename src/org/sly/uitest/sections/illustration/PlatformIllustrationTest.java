package org.sly.uitest.sections.illustration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.investments.InvestmentsPage;
import org.sly.uitest.pageobjects.planning.IllustrationPage;
import org.sly.uitest.settings.Settings;

public class PlatformIllustrationTest extends AbstractTest {
	String currentInvestmentAmount = "10,000";
	String proposedInvestmentAmount = "20,000";
	String illustrationName = "illustration " + getRandName();
	String stock = "3D Systems Corporation";
	String bond = "ABN AMRO BANK NV";
	String mutualFund = "3 Banken Portfolio-Mix T";
	String[] benchmarks = { "Citi World Broad Investment Grade Bond Index – USD", "MSCI ACWI NR USD",
			"S&P 500 Net TR USD" };
	String[] stocks = { "Aberdeen Australia Equity Fund, Inc.", "3D Systems Corporation",
			"Advanced Micro Devices, Inc." };

	@Test
	public void testGenerateInformationPage() throws InterruptedException {
		String hkPlatform = "Citi P360 - Hong Kong";
		int hkLang = 2;
		int hkBenchmark = 5;
		String koreaPlatform = "Citi P360 - Korea";
		int koreaLang = 2;
		int koreaBenchmark = 5;
		String singaporeGcgPlatform = "Citi P360 - Singapore GCG";
		int gcgLang = 1;
		int gcgBenchmark = 4;
		String singaporeIpbPlatform = "Citi P360 - Singapore IPB";
		int ipbLang = 1;
		int ipbBenchmark = 22;
		String taiwanPlatform = "Citi P360 - Taiwan";
		int twLang = 2;
		int twBenchmark = 3;
		String ukPlatform = "Citi P360 - United Kingdom";
		int ukLang = 1;
		int ukBenchmark = 22;
		String currentInvestmentAmount = "10000";

		IllustrationPage illustration = this.init();
		// 1. Platform and Report Language and Benchmark

		this.waitForWaitingScreenNotVisible();

		illustration.editIIProduct(koreaPlatform);
		assertEquals(koreaLang, this.getSizeOfReportLanguageSelect());
		assertEquals(koreaBenchmark, this.getSizeOfBenchmarkSelect());

		illustration.editIIProduct(hkPlatform);
		assertEquals(hkLang, this.getSizeOfReportLanguageSelect());
		assertEquals(hkBenchmark, this.getSizeOfBenchmarkSelect());

		illustration.editIIProduct(singaporeGcgPlatform);
		assertEquals(gcgLang, this.getSizeOfReportLanguageSelect());
		assertEquals(gcgBenchmark, this.getSizeOfBenchmarkSelect());

		illustration.editIIProduct(singaporeIpbPlatform);
		assertEquals(ipbLang, this.getSizeOfReportLanguageSelect());
		assertEquals(ipbBenchmark, this.getSizeOfBenchmarkSelect());

		illustration.editIIProduct(taiwanPlatform);
		assertEquals(twLang, this.getSizeOfReportLanguageSelect());
		assertEquals(twBenchmark, this.getSizeOfBenchmarkSelect());

		illustration.editIIProduct(ukPlatform);
		assertEquals(ukLang, this.getSizeOfReportLanguageSelect());
		assertEquals(ukBenchmark, this.getSizeOfBenchmarkSelect());

		// 2. 0 for current investment amount
		this.completeGeneralInformationPage();
		illustration.jumpToGITag().editIICurrentInvestmentAmount("0").clickNextButtonForCheckingField();
		// 3. 0 for proposed investment amount
		illustration.jumpToGITag().editIICurrentInvestmentAmount(currentInvestmentAmount)
				.editIIProposedInvestmentAmount("0").clickNextButtonForCheckingField();

		// 4. Add Max number of Benchmark as per platform
		illustration.editIIProduct(hkPlatform);
		addMaximumNumberOfBenchmarkAndAssertion(hkBenchmark);

		// illustration.editIIProduct(koreaPlatform);
		// addMaximumNumberOfBenchmarkAndAssertion(koreaBenchmark);

		// illustration.editIIProduct(singaporeGcgPlatform);
		// addMaximumNumberOfBenchmarkAndAssertion(gcgBenchmark);

		illustration.editIIProduct(singaporeIpbPlatform);
		addMaximumNumberOfBenchmarkAndAssertion(ipbBenchmark);

		illustration.editIIProduct(taiwanPlatform);
		addMaximumNumberOfBenchmarkAndAssertion(twBenchmark);

		// illustration.editIIProduct(ukPlatform);
		// addMaximumNumberOfBenchmarkAndAssertion(ukBenchmark);
	}

	@Test
	public void testCurrentPortfolioPage() throws Exception {

		IllustrationPage illustration = this.init();
		this.completeGeneralInformationPage();

		// 0. assert current portfolio value is correctly reflected
		assertEquals(getTextByLocator(By.id("gwt-debug-IllustrationSettingsView-vioRemainingAmountLabelCompared")),
				"$" + currentInvestmentAmount
						+ ".00 will be allocated directly to the Current Holdings with in the Illustration platform -");

		// 1. multiple and Diff types of investment
		illustration.clickAddInvestmentButtonInCurrentPortfolio().simpleSearchByName(stock)
				.selectInvestmentByName(stock).clickClearSearchIcon().simpleSearchByName(bond)
				.selectInvestmentByName(bond).clickClearSearchIcon().simpleSearchByName(mutualFund)
				.selectInvestmentByName(mutualFund).clickAddToPortfolioButton();

		assertTrue(pageContainsStr(stock));
		assertTrue(pageContainsStr(bond));
		assertTrue(pageContainsStr(mutualFund));

		// 2. validation on percentage
		// negative %
		this.editAllocationForMultipleInvestmentInCurrentPortfolio("-10", stock, bond, mutualFund);

		// allocation of cash should be 100%
		assertEquals(
				this.getTextByLocator(By
						.xpath("(.//table[@id='gwt-debug-IllustrationSettingsView-vioAllocationTableCompared']//td[.='Cash']//following-sibling::td)[1]")),
				"100%");

		// exceed 100%
		this.editAllocationForMultipleInvestmentInCurrentPortfolio("200", stock, bond, mutualFund);

		// allocation of cash should be 100%
		assertEquals(
				this.getTextByLocator(By
						.xpath("(.//table[@id='gwt-debug-IllustrationSettingsView-vioAllocationTableCompared']//td[.='Cash']//following-sibling::td)[1]")),
				"100%");

		// all 0
		this.editAllocationForMultipleInvestmentInCurrentPortfolio("0", stock, bond, mutualFund);

		// allocation of cash should be 100%
		assertEquals(
				this.getTextByLocator(By
						.xpath("(.//table[@id='gwt-debug-IllustrationSettingsView-vioAllocationTableCompared']//td[.='Cash']//following-sibling::td)[1]")),
				"100%");

		// 3. validation on value/unit
		// negative value
		this.editMarketValueForMultipleInvestmentInCurrentPortfolio("-10", stock, bond, mutualFund);

		// there should be errors in cash value
		assertTrue(isElementVisible(By.xpath(
				"(.//table[@id='gwt-debug-IllustrationSettingsView-vioAllocationTableCompared']//td[.='Total*']//following-sibling::td)[1][@class='error']")));

		// exceed total value
		this.editMarketValueForMultipleInvestmentInCurrentPortfolio(currentInvestmentAmount, stock, bond, mutualFund);
		// there should be errors in cash value
		assertTrue(isElementVisible(By.xpath(
				"(.//table[@id='gwt-debug-IllustrationSettingsView-vioAllocationTableCompared']//td[.='Cash']//following-sibling::td)[3][@class='error']")));
		// all 0
		this.editMarketValueForMultipleInvestmentInCurrentPortfolio("0", stock, bond, mutualFund);
		this.editAllocationForMultipleInvestmentInCurrentPortfolio("0", stock, bond, mutualFund);

		// allocation of cash should be 100%
		assertTrue(this
				.getTextByLocator(By
						.xpath("(.//table[@id='gwt-debug-IllustrationSettingsView-vioAllocationTableCompared']//td[.='Cash']//following-sibling::td)[3]"))
				.contains(currentInvestmentAmount));

	}

	@Test
	public void testProposedPortfolioPage() throws Exception {
		IllustrationPage illustration = this.init();
		this.completeGeneralInformationPage();
		this.completeCurrentPortfolioPage();
		// 0. assert current portfolio value is correctly reflected
		assertEquals(this.getTextByLocator(By.id("gwt-debug-IllustrationSettingsView-vioRemainingAmountLabel")), "$"
				+ proposedInvestmentAmount
				+ ".00 will be allocated directly to the Proposed Holdings with in the Illustration platform -");

		// 1. Import dollar value from current portfolio
		illustration.clickImportDollarValueFromCurrentPortfolio();
		assertTrue(pageContainsStr(stock));
		assertTrue(pageContainsStr(bond));
		assertTrue(pageContainsStr(mutualFund));
		illustration.deleteAllInvestmentInProposedPortfolio();

		// 2. Import % value from current portfolio
		illustration.clickImportPercentageValueFromCurrentPortfolio();
		assertTrue(pageContainsStr(stock));
		assertTrue(pageContainsStr(bond));
		assertTrue(pageContainsStr(mutualFund));
		illustration.deleteAllInvestmentInProposedPortfolio();

		// 3. multiple and Diff types of investment
		illustration.clickAddInvestmentButtonInProposedPortfolio().simpleSearchByName(stock)
				.selectInvestmentByName(stock).clickClearSearchIcon().simpleSearchByName(bond)
				.selectInvestmentByName(bond).clickClearSearchIcon().simpleSearchByName(mutualFund)
				.selectInvestmentByName(mutualFund).clickAddToPortfolioButton();

		assertTrue(pageContainsStr(stock));
		assertTrue(pageContainsStr(bond));
		assertTrue(pageContainsStr(mutualFund));
		// 2. validation on percentage
		// negative %
		this.editAllocationForMultipleInvestmentInProposedPortfolio("-10", stock, bond, mutualFund);

		// allocation of cash should be 100%
		assertEquals(
				this.getTextByLocator(By
						.xpath("(.//table[@id='gwt-debug-IllustrationSettingsView-vioAllocationTable']//td[.='Cash']//following-sibling::td)[1]")),
				"100%");

		// exceed 100%
		this.editAllocationForMultipleInvestmentInProposedPortfolio("200", stock, bond, mutualFund);

		// allocation of cash should be 100%
		assertEquals(
				this.getTextByLocator(By
						.xpath("(.//table[@id='gwt-debug-IllustrationSettingsView-vioAllocationTable']//td[.='Cash']//following-sibling::td)[1]")),
				"100%");

		// all 0
		this.editAllocationForMultipleInvestmentInProposedPortfolio("0", stock, bond, mutualFund);

		// allocation of cash should be 100%
		assertEquals(
				this.getTextByLocator(By
						.xpath("(.//table[@id='gwt-debug-IllustrationSettingsView-vioAllocationTable']//td[.='Cash']//following-sibling::td)[1]")),
				"100%");

		// 3. validation on value/unit
		// negative value
		this.editMarketValueForMultipleInvestmentInProposedPortfolio("-10", stock, bond, mutualFund);

		// there should be errors in cash value
		assertTrue(isElementVisible(By.xpath(
				"(.//table[@id='gwt-debug-IllustrationSettingsView-vioAllocationTable']//td[.='Total*']//following-sibling::td)[1][@class='error']")));

		// exceed total value
		this.editMarketValueForMultipleInvestmentInProposedPortfolio(currentInvestmentAmount, stock, bond, mutualFund);
		// there should be errors in cash value
		assertTrue(isElementVisible(By.xpath(
				"(.//table[@id='gwt-debug-IllustrationSettingsView-vioAllocationTable']//td[.='Cash']//following-sibling::td)[3][@class='error']")));
		// all 0
		this.editMarketValueForMultipleInvestmentInProposedPortfolio("0", stock, bond, mutualFund);
		this.editAllocationForMultipleInvestmentInProposedPortfolio("0", stock, bond, mutualFund);

		// allocation of cash should be 100%
		assertTrue(this
				.getTextByLocator(By
						.xpath("(.//table[@id='gwt-debug-IllustrationSettingsView-vioAllocationTable']//td[.='Cash']//following-sibling::td)[3]"))
				.contains(proposedInvestmentAmount));
	}

	@Test
	public void testSensitivityAnalysisPage() throws Exception {

		IllustrationPage illustration = this.init();
		this.completeGeneralInformationPage();
		this.completeCurrentPortfolioPage();
		this.completeProposedPortfolioPage();
		// 1. Add few benchmarks
		for (String benchmark : benchmarks) {
			illustration.clickAddBenchmarkButtonInSensitivityAnalysis().editBenchmarkInSensitivityAnalysis(benchmark);
		}
		for (String benchmark : benchmarks) {
			assertTrue(pageContainsStr(benchmark));
		}
		// 2. Add few stocks
		InvestmentsPage invest = illustration.clickAddAnotherInvestmentInSensitivityAnalysis();

		for (String stock : stocks) {
			invest.simpleSearchByName(stock).selectInvestmentByName(stock).clickClearSearchIcon();
		}
		invest.clickAddToPortfolioButton();
		for (String stock : stocks) {
			assertTrue(pageContainsStr(stock));
		}
	}

	@Test
	public void testLeveragedPortfolioPage() throws Exception {
		String maximumCreditLimit = "10000";
		String currentCreditLimit = "5000";
		String currentCreditLimit2 = "20000";
		String usedCreditLimit = "3000";
		String usedCreditLimit2 = "6000";
		double currentAvailablePower;
		double LTV30 = 100 / (1 - 0.3);
		double LTV50 = 100 / (1 - 0.5);
		double LTV70 = 100 / (1 - 0.7);
		DecimalFormat df = new DecimalFormat("#.##");
		IllustrationPage illustration = this.init();
		this.completeGeneralInformationPage();
		this.completeCurrentPortfolioPage();
		this.completeProposedPortfolioPage();
		this.completeSensitivityAnalysisPage();
		// 1. test "max limit >= current limit"

		// max<current
		illustration.editMaximumCreditLimit(maximumCreditLimit).editCurrentCreditLimit(currentCreditLimit2);
		assertTrue(isElementVisible(By.xpath(
				".//input[@id='gwt-debug-IllustrationSettingsView-currCreditTextBox' and contains(@class,'formTextBoxInvalid')]")));

		// max>current
		illustration.editCurrentCreditLimit(currentCreditLimit);
		assertFalse(isElementVisible(By.xpath(
				".//input[@id='gwt-debug-IllustrationSettingsView-currCreditTextBox' and contains(@class,'formTextBoxInvalid')]")));

		// 2. available credit limit = current credit limit - used credit limit
		// current<used
		illustration.editUsedCreditLimit(usedCreditLimit2);
		assertTrue(isElementVisible(By.xpath(
				".//input[@id='gwt-debug-IllustrationSettingsView-currCreditTextBox' and contains(@class,'formTextBoxInvalid')]")));
		assertTrue(isElementVisible(By.xpath(
				".//input[@id='gwt-debug-IllustrationSettingsView-usedCreditTextBox' and contains(@class,'formTextBoxInvalid')]")));

		// current<used
		illustration.editUsedCreditLimit(usedCreditLimit);
		assertFalse(isElementVisible(By.xpath(
				".//input[@id='gwt-debug-IllustrationSettingsView-currCreditTextBox' and contains(@class,'formTextBoxInvalid')]")));
		assertFalse(isElementVisible(By.xpath(
				".//input[@id='gwt-debug-IllustrationSettingsView-usedCreditTextBox' and contains(@class,'formTextBoxInvalid')]")));

		currentAvailablePower = Double.valueOf(currentCreditLimit) - Double.valueOf(usedCreditLimit);
		log("currentAvailablePower " + String.valueOf(currentAvailablePower));
		assertEquals(
				String.valueOf(NumberFormat.getNumberInstance(Locale.UK)
						.parse((String.valueOf(df.format(currentAvailablePower))))),
				String.valueOf(NumberFormat.getNumberInstance(Locale.UK).parse(this.getTextByLocator(By.xpath(
						".//td[*[contains(text(),'Current Available Credit Limit')]]//following-sibling::td//*")))));

		// Maximum purchasing power = current available* (purchasing power/100)
		// 3. 30% LTV purchasing power : 100 / (1-0.3)
		assertEquals(
				String.valueOf(NumberFormat.getNumberInstance(Locale.UK)
						.parse(this.getTextByLocator(
								By.xpath(".//td[b[contains(text(),'30% LTV')]]/following-sibling::td//div")))),
				String.valueOf(NumberFormat.getNumberInstance(Locale.UK)
						.parse(String.valueOf(df.format(currentAvailablePower * (LTV30 / 100))))));

		// 4. 50% LTV purchasing power : 100 / (1-0.5)
		assertEquals(
				String.valueOf(NumberFormat.getNumberInstance(Locale.UK)
						.parse(this.getTextByLocator(
								By.xpath(".//td[b[contains(text(),'50% LTV')]]/following-sibling::td//div")))),
				String.valueOf(NumberFormat.getNumberInstance(Locale.UK)
						.parse(String.valueOf(df.format(currentAvailablePower * (LTV50 / 100))))));

		// 5. 70% LTV purchasing power : 100 / (1-0.7)
		assertEquals(
				String.valueOf(NumberFormat.getNumberInstance(Locale.UK)
						.parse(this.getTextByLocator(
								By.xpath(".//td[b[contains(text(),'70% LTV')]]/following-sibling::td//div")))),
				String.valueOf(NumberFormat.getNumberInstance(Locale.UK)
						.parse(String.valueOf(df.format(currentAvailablePower * (LTV70 / 100))))));

	}

	@Test
	public void testFinishIllustrationAndCheckReport() throws Exception {
		String pdfLink = "";
		IllustrationPage illustration = this.init();
		this.completeGeneralInformationPage();
		this.completeCurrentPortfolioPage();
		this.completeProposedPortfolioPage();
		this.completeSensitivityAnalysisPage();
		this.completeLeveragedPortfolio();
		checkLogout();
		handleAlert();

		LoginPage main2 = new LoginPage(webDriver);
		main2.loginAs(Settings.Citi_P360_USER, Settings.Citi_P360_PASSWORD).goToIllustrationPage()
				.simpleSearch(illustrationName);
		try {
			pdfLink = this.getAttributeStringByLocator(By.id("gwt-debug-IllustrationListPresenter-ownerReportLink"),
					"href");
		} catch (TimeoutException e) {
			this.refreshPage();
			illustration.simpleSearch(illustrationName);
			pdfLink = this.getAttributeStringByLocator(By.id("gwt-debug-IllustrationListPresenter-ownerReportLink"),
					"href");
		}

		log(pdfLink);
		this.checkUrlValid(pdfLink);
	}

	public IllustrationPage init() throws InterruptedException {
		LoginPage main = new LoginPage(webDriver);
		return main.loginAs(Settings.Citi_P360_USER, Settings.Citi_P360_PASSWORD).goToIllustrationPage()
				.createNewIllustration();
	}

	public int getSizeOfReportLanguageSelect() {
		waitForElementVisible(
				By.xpath(".//*[@id='gwt-debug-IllustrationSettingsView-reportLangListBox']//option[.='English']"), 30);

		return this.getSizeOfElements(
				By.xpath(".//select[@id='gwt-debug-IllustrationSettingsView-reportLangListBox']//option"));
	}

	public int getSizeOfBenchmarkSelect() {
		waitForElementVisible(By.xpath(".//td[.='Benchmark*']//following-sibling::td//select"), 30);
		this.waitForWaitingScreenNotVisible();
		return this.getSizeOfElements(By.xpath(".//td[.='Benchmark*']//following-sibling::td//select/option"));
	}

	/**
	 * To complete general information page
	 * 
	 * @throws InterruptedException
	 */
	public void completeGeneralInformationPage() throws InterruptedException {
		String hkPlatform = "Citi P360 - Singapore GCG";
		String benchmark = "MSCI ACWI NR USD";
		String riskProfile = "Very Aggressive (P5)";
		String modelPortfolio = "Regional Model Portfolio";

		IllustrationPage illustration = new IllustrationPage(webDriver);

		illustration.editIIProduct(hkPlatform).editIIName(illustrationName).editIIInvestorRiskProfile(riskProfile)
				// .editIIModelPortfolioType(modelPortfolio)
				.editIICurrentInvestmentAmount(currentInvestmentAmount)
				.editIIProposedInvestmentAmount(proposedInvestmentAmount).editIIChartBenchmark(benchmark)
				.clickNextButton_onGI();
	}

	/**
	 * To complete current portfolio page
	 * 
	 * @throws Exception
	 */
	public void completeCurrentPortfolioPage() throws Exception {

		IllustrationPage illustration = new IllustrationPage(webDriver);

		illustration.deleteAllInvestmentInCurrentPortfolio();
		illustration.clickAddInvestmentButtonInCurrentPortfolio().simpleSearchByName(stock)
				.selectInvestmentByName(stock).clickClearSearchIcon().simpleSearchByName(bond)
				.selectInvestmentByName(bond).clickClearSearchIcon().simpleSearchByName(mutualFund)
				.selectInvestmentByName(mutualFund).clickAddToPortfolioButton();

		this.editAllocationForMultipleInvestmentInCurrentPortfolio("30", stock, bond, mutualFund);

		illustration.clickNextButton_onCurrentPortfolio();
	}

	/**
	 * To complete Proposed Portfolio Page
	 * 
	 * @throws Exception
	 */
	public void completeProposedPortfolioPage() throws Exception {
		IllustrationPage illustration = new IllustrationPage(webDriver);

		illustration.deleteAllInvestmentInProposedPortfolio();
		illustration.clickAddInvestmentButtonInProposedPortfolio().simpleSearchByName(stock)
				.selectInvestmentByName(stock).clickClearSearchIcon().simpleSearchByName(bond)
				.selectInvestmentByName(bond).clickClearSearchIcon().simpleSearchByName(mutualFund)
				.selectInvestmentByName(mutualFund).clickAddToPortfolioButton();

		this.editAllocationForMultipleInvestmentInProposedPortfolio("30", stock, bond, mutualFund);
		illustration.clickNextButton_onProposedPortfolio();
	}

	public void completeSensitivityAnalysisPage() throws Exception {

		IllustrationPage illustration = new IllustrationPage(webDriver);

		// add benchmark

		illustration.clickAddBenchmarkButtonInSensitivityAnalysis();

		// add stock
		InvestmentsPage invest = illustration.clickAddAnotherInvestmentInSensitivityAnalysis();

		for (String stock : stocks) {
			invest.simpleSearchByName(stock).selectInvestmentByName(stock).clickClearSearchIcon();
		}
		IllustrationPage illustration2 = invest.clickAddToPortfolioButton();
		illustration2.clickNextButton_onSensitivityAnalysis();
	}

	public void completeLeveragedPortfolio() throws InterruptedException {
		String maximumCreditLimit = "10000";
		String currentCreditLimit = "5000";
		String usedCreditLimit = "3000";

		IllustrationPage illustration = new IllustrationPage(webDriver);
		illustration.editMaximumCreditLimit(maximumCreditLimit).editCurrentCreditLimit(currentCreditLimit)
				.editUsedCreditLimit(usedCreditLimit).clickSaveAndCalculate();
		clickOkButtonIfVisible();
	}

	/**
	 * Add maximum number of benchmark and ensure the max number of benchmark
	 * does not exceed the limit
	 * 
	 * @param numberOfBenchmark
	 */
	public void addMaximumNumberOfBenchmarkAndAssertion(int numberOfBenchmark) {
		IllustrationPage illustration = new IllustrationPage(webDriver);
		// Benchmark has to -1 since one of the options is empty.
		log("This platform only supports " + String.valueOf(numberOfBenchmark - 1) + " benchmarks");
		for (int i = 0; i < numberOfBenchmark; i++) {
			if (!pageContainsStr(
					"This platform only supports " + String.valueOf(numberOfBenchmark - 1) + " benchmarks")) {
				illustration.clickAddBenchmarkButtonInII();
			}
		}

		clickOkButtonIfVisible();

		assertEquals(numberOfBenchmark - 1, this.getSizeOfElements(By.xpath(
				".//table[@id='gwt-debug-IllustrationSettingsView-benchmarkTable']//td[contains(text(),'Benchmark')]//following-sibling::td/select")));

		illustration.deleteAllBenchmark();
	}

	/**
	 * edit market value for multiple investment in current portfolio
	 * 
	 * @param value
	 * @param investments
	 */
	public void editMarketValueForMultipleInvestmentInCurrentPortfolio(String value, String... investments) {
		IllustrationPage illustration = new IllustrationPage(webDriver);
		for (String investment : investments) {
			illustration.editMarketValueByInvestmentInCurrentPorfolio(investment, value);
		}

	}

	/**
	 * edit allocation for multiple investments in current portfolio
	 * 
	 * @param allocation
	 * @param investments
	 */
	public void editAllocationForMultipleInvestmentInCurrentPortfolio(String allocation, String... investments) {
		IllustrationPage illustration = new IllustrationPage(webDriver);
		for (String investment : investments) {
			illustration.editAllocationByInvestmentInCurrentPorfolio(investment, allocation);
		}

	}

	/**
	 * edit market value for multiple investments in proposed portfolio
	 * 
	 * @param value
	 * @param investments
	 */
	public void editMarketValueForMultipleInvestmentInProposedPortfolio(String value, String... investments) {
		IllustrationPage illustration = new IllustrationPage(webDriver);
		for (String investment : investments) {
			illustration.editMarketValueByInvestmentInProposedPorfolio(investment, value);
		}
	}

	/**
	 * edit allocation for multiple investments in proposed portfolio
	 * 
	 * @param allocation
	 * @param investments
	 */
	public void editAllocationForMultipleInvestmentInProposedPortfolio(String allocation, String... investments) {
		IllustrationPage illustration = new IllustrationPage(webDriver);
		for (String investment : investments) {
			illustration.editAllocationByInvestmentInProposedPorfolio(investment, allocation);
		}
	}
}
