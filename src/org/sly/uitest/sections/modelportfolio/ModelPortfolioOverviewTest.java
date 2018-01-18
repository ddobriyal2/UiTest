package org.sly.uitest.sections.modelportfolio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.abstractpage.MenuBarPage;
import org.sly.uitest.pageobjects.assetmanagement.ModelPortfoliosPage;
import org.sly.uitest.pageobjects.clientsandaccounts.AnalysisPage;
import org.sly.uitest.pageobjects.clientsandaccounts.HistoryPage;
import org.sly.uitest.pageobjects.clientsandaccounts.HoldingsPage;
import org.sly.uitest.pageobjects.investments.InvestmentsPage;
import org.sly.uitest.settings.Settings;

/**
 * @author Jackie Lee
 * @date : Jul 31, 2014
 * @company Prive Financial
 */
public class ModelPortfolioOverviewTest extends AbstractTest {

	@Test
	public void testModelPortfolioBenchmark() throws Exception {

		LoginPage main = new LoginPage(webDriver);
		String benchmark = "";
		ModelPortfoliosPage model = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToModelPortfoliosPage()
				.clickManageModelPortfolios().editModelPortfolioByName("Sample Model Portfolio");
		waitForElementVisible(
				By.xpath(
						".//*[@id='gwt-debug-ModelPortfolioEditView-modelBenchmark']//select[@id='gwt-debug-PairedListBoxSelector-sourceList']"),
				10);

		Select targetList = new Select(webDriver.findElement(By.xpath(
				".//*[@id='gwt-debug-ModelPortfolioEditView-modelBenchmark']//select[@id='gwt-debug-PairedListBoxSelector-targetList']")));

		if (targetList.getOptions().size() > 0) {
			benchmark = targetList.getOptions().get(0).getText();
		} else {
			Select sourceList = new Select(webDriver.findElement(By.xpath(
					".//*[@id='gwt-debug-ModelPortfolioEditView-modelBenchmark']//select[@id='gwt-debug-PairedListBoxSelector-sourceList']")));

			benchmark = sourceList.getOptions().get(0).getText();
			model.editModelPortfolioAddBenchmark(benchmark);
		}

		log("benchmark: " + benchmark);
		model.clickSaveButton();
		AnalysisPage addBenchmark = model.goToAnalysisPage();

		assertTrue("Expected to see the model portfolio to have benchmark - " + benchmark, pageContainsStr(benchmark));

		try {
			assertTrue(this
					.getTextByLocator(By
							.xpath(".//*[@id='gwt-debug-InvestorModelPortfolioView-benchmarkPanel']/div[1]/table/tbody/tr[3]/td[1]"))
					.equals(benchmark));
		} catch (org.openqa.selenium.NoSuchElementException e) {
			// TODO: handle exception
		}

		// Reset Absolute Return 8% to source list
		((HoldingsPage) addBenchmark.goToModelPortfoliosPage().clickManageModelPortfolios()
				.editModelPortfolioByName("Sample Model Portfolio").editModelPortfolioRemoveBenchmark(benchmark)
				.clickSaveButton()).goToAnalysisPage();

		assertFalse(isElementVisible(By.xpath(
				".//*[@id='gwt-debug-InvestorModelPortfolioView-benchmarkPanel']/div[1]/table/tbody/tr[3]/td[1]")));

		assertFalse("Not Expected to see the model portfolio to have benchmark - " + benchmark,
				pageContainsStr(benchmark));

	}

	@Test
	public void testModelPortfolioCrud() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		String random = getRandName();
		String name = "Model Portfolio " + random;
		String description = "This is my model portfolio " + random;
		Random rand = new Random();
		int perc1 = rand.nextInt(25) + 1;
		int perc2 = rand.nextInt(25) + 1;
		int perc3 = rand.nextInt(100 - perc1 - perc2) + 1;

		/*
		 * Create new model portfolio
		 */

		HoldingsPage newPortfolio = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToModelPortfoliosPage()
				.clickManageModelPortfolios().clickCreatePortfolioButton().editModelPortfolioName(name)
				.editModelPortfolioDescription(description)
				.editModelPortfolioAddPlatform("Friends Provident Bond - JarlAdvisor").clickSaveButton();

		/*
		 * Fill out history 1
		 */

		InvestmentsPage addHistory = newPortfolio.goToTransactionHistoryPage().editModelPortfolioHistory()
				.addModelPortfolioNewHistoricalRebalancing().addModelPortfolioInvestments();

		// Add the first 4 strategies and mark down their names

		waitForElementVisible(By.xpath("(//*[@id=\"gwt-debug-ManagerListItem-strategyName\"])[1]"),
				Settings.WAIT_SECONDS);

		String strategyName1 = this
				.getTextByLocator(By.xpath("(//*[@id=\"gwt-debug-ManagerListItem-strategyName\"])[1]"));
		String strategyName2 = this
				.getTextByLocator(By.xpath("(//*[@id=\"gwt-debug-ManagerListItem-strategyName\"])[2]"));
		String strategyName3 = this
				.getTextByLocator(By.xpath("(//*[@id=\"gwt-debug-ManagerListItem-strategyName\"])[3]"));

		HistoryPage addHistory1 = ((HistoryPage) addHistory.selectInvestmentByName(strategyName1)
				.selectInvestmentByName(strategyName2).selectInvestmentByName(strategyName3)
				.clickAddToPortfolioButton()).setNewAllocationForInvestment(strategyName1, perc1 + "\n")
						.setNewAllocationForInvestment(strategyName2, perc2 + "\n")
						.setNewAllocationForInvestment(strategyName3, perc3 + "\n")
						.clickModelPortfolioAllocationSaveButton().saveModelPortfolioHistory();

		waitForElementVisible(By.id("gwt-debug-PortfolioAllocationView-mainPanel"), 120);

		addHistory1.goToTransactionHistoryPage().confirmHistoryStatus().goToHoldingsPage();

		// Check if the investments are added to the model portfolio

		assertTrue("Expected to see investment of name: " + strategyName1, pageContainsStr(strategyName1));
		assertTrue("Expected to see investment of name: " + strategyName2, pageContainsStr(strategyName2));
		assertTrue("Expected to see investment of name: " + strategyName3, pageContainsStr(strategyName3));

		/*
		 * Fill out history 2
		 */

		HistoryPage addHistory2 = ((HistoryPage) addHistory1.goToTransactionHistoryPage().editModelPortfolioHistory()
				.addModelPortfolioNewHistoricalRebalancing().editModelPortfolioAllocationDate("4-Aug-2013\n")
				.addModelPortfolioInvestments().selectInvestmentByName(strategyName1)
				.selectInvestmentByName(strategyName2).selectInvestmentByName(strategyName3)
				.clickAddToPortfolioButton()).setNewAllocationForInvestment(strategyName1, perc1 + "\n")
						.setNewAllocationForInvestment(strategyName2, perc2 + "\n")
						.setNewAllocationForInvestment(strategyName3, perc3 + "\n")
						.clickModelPortfolioAllocationSaveButton().saveModelPortfolioHistory();

		waitForElementVisible(By.id("gwt-debug-PortfolioAllocationView-mainPanel"), 90);

		addHistory2.goToTransactionHistoryPage().confirmHistoryStatus().goToHoldingsPage();

		// Check if the investments are added to the model portfolio
		assertTrue("Expected to see investment of name: " + strategyName1, pageContainsStr(strategyName1));
		assertTrue("Expected to see investment of name: " + strategyName2, pageContainsStr(strategyName2));
		assertTrue("Expected to see investment of name: " + strategyName3, pageContainsStr(strategyName3));

		// delete the model portfolio

		addHistory2.goToModelPortfoliosPage().clickManageModelPortfolios().deleteModelPortfolioByName(name);

	}

	// SLYAWS-7055
	@Test
	public void testModelPortfolioDuplication() throws Exception {

		LoginPage main = new LoginPage(webDriver);
		MenuBarPage mbPage = main.loginAs(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD);
		// wait(5);

		ModelPortfoliosPage modelPortfoliosPage = mbPage.goToModelPortfoliosPage();

		((ModelPortfoliosPage) modelPortfoliosPage.goToModelPortfolioInfoPage("Allans Top 4")
				.goBackToPreviousPage(webDriver)).goToModelPortfolioInfoPage("Test New Code");
		waitForElementVisible(By.id("gwt-debug-InvestorModelPortfolioView-modelName"), 10);
		scrollToTop();
		/*
		 * There is still "Allans Top 4 in page source. Check New Title instead"
		 */
		assertEquals(getTextByLocator(By.id("gwt-debug-InvestorModelPortfolioView-modelName")), "Test New Code");

	}
}
