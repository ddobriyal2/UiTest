package org.sly.uitest.sections.accounts;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.clientsandaccounts.HoldingsPage;
import org.sly.uitest.pageobjects.investments.InvestmentsPage;
import org.sly.uitest.settings.Settings;

/**
 * Tests functionality around the rebalancing screen for a portfolio. <br>
 * <br>
 * Test is described here: <a href=
 * "https://docs.google.com/a/wismore.com/spreadsheets/d/1WEEiIIiVHNix_W5frGNpT53Rlv8l_g5XkyivjkjsIj4/edit#gid=0"
 * >here</a>
 * 
 * @author Julian Schillinger
 * @date : Jan 3, 2014
 * @company Prive Financial
 */
public class PortfolioRebalanceTest extends AbstractTest {

	/**
	 * Tests manually rebalancing the portfolio, but does not execute the
	 * rebalancing: Add new investments, change allocation, check preview
	 * screen, ...
	 */

	@Test
	public void testManualRebalancing() throws Exception {

		// 1. get the name of the old investment and save the
		// return, and set the allocation 40%
		LoginPage main = new LoginPage(webDriver);
		String investmentName = "";
		String account = "Selenium Test";
		InvestmentsPage investment = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage()
				.simpleSearchByString(account).goToAccountHoldingsPageByName(account).goToHoldingsPage()
				.clickReallocateButton().clickAddInvestmentButton();

		investmentName = this.getTextByLocator(By.xpath("(.//*[@id='gwt-debug-ManagerListItem-strategyName'])[1]"));

		HoldingsPage holding = investment.simpleSearchByName(investmentName).openInvestmentByName(investmentName)
				.backToAddInvestment().selectInvestmentByName(investmentName).clickAddToPortfolioButton();

		// make sure investment shows in portfolio
		assertTrue(pageContainsStr(investmentName));

		// increase investment allocation
		holding.setNewAllocationForInvestment(investmentName, "0")
				.setNewAllocationForInvestmentByPlusButton(investmentName, 4)
				.setNewAllocationForInvestmentByMinusButton(investmentName, 1);

		String allocation = webDriver
				.findElement(By.xpath("//td[.='" + investmentName
						+ "']/following-sibling::td[//input[@id='gwt-debug-TextBoxPercentageSpinner-percentField']]//input"))
				.getAttribute("value");

		log(allocation);

		assertTrue(allocation.contains("15"));

		holding.openInvestmentByName(investmentName).closeCurrentOpenedInvestment()
				.setNewAllocationForInvestment(investmentName, "40").clickRebalancePreviewAndConfirm()
				.goToTransactionHistoryPage().confirmHistoryStatus().goToHoldingsPage().clickReallocateButton();

		// save return from risk reward widget for comparison later
		String origReturn = getTextByLocator(By.id("gwt-debug-RiskSummaryChart-portfolioReturn"));

		// log("Original Portfolio Return: " + origReturn);
		//
		// // 2. add the second investment as the new one, use plus button to
		// // increase the allocation
		//
		// // add the first investment
		// InvestmentsPage investment2 = holding.clickAddInvestmentButton();
		// waitForElementVisible(By.id("gwt-debug-ManagerListItem-strategyName"),
		// 30);
		//
		// String thisInvest =
		// getTextByLocator(By.id("gwt-debug-ManagerListItem-strategyName"));
		//
		// investment2.selectInvestmentByName(thisInvest).clickAddToPortfolioButton();
		//
		// // make sure investment shows in portfolio
		// assertTrue(pageContainsStr(thisInvest));
		//
		// // increase investment allocation
		// holding.setNewAllocationForInvestment(thisInvest,
		// "0").setNewAllocationForInvestmentByPlusButton(thisInvest, 4)
		// .setNewAllocationForInvestmentByMinusButton(thisInvest, 1);
		//
		// String allocation = webDriver
		// .findElement(By.xpath("//td[.='" + thisInvest
		// +
		// "']/following-sibling::td[//input[@id='gwt-debug-TextBoxPercentageSpinner-percentField']]//input"))
		// .getAttribute("value");
		//
		// log(allocation);
		//
		// assertTrue(allocation.contains("15"));
		//
		// HistoryPage history =
		// holding.clickRebalancePreviewAndConfirm(thisInvest).goToTransactionHistoryPage()
		// .confirmHistoryStatus();
		//
		// // 3. reduce the allocation of the old investment and check if the
		// // return is different from previous
		// this.refreshPage();
		//
		holding.setNewAllocationForInvestment(investmentName, "0");

		// wait for refresh
		wait(Settings.WAIT_SECONDS);

		// get return from risk reward widget
		String newReturn = getTextByLocator(By.id("gwt-debug-RiskSummaryChart-portfolioReturn"));

		log("New Portfolio Return: " + newReturn);

		// make sure return has changed
		assertFalse(newReturn.equals(origReturn));

		holding.clickRebalancePreviewAndCancel(investmentName);

		// 4. remove the new investment and reset the old one

		holding.setNewAllocationForInvestment(investmentName, "0").clickRebalancePreviewAndConfirm();

	}

	// IGONRE THE FOLLOWINGS FOR NOW!!!!!!!!!!!!!!

	// /**
	// * Pulls up the optimization based on risk profile.
	// *
	// * @throws InterruptedException
	// */
	// @Test
	// public void testPorfolioOptimizationRiskProfile()
	// throws InterruptedException {
	//
	// LoginPage main = new LoginPage(webDriver);
	//
	// main.loginAs(Settings.USERNAME, Settings.PASSWORD)
	// .goToClientOverviewPage().simpleSearchByString("Selenium")
	// .goToClientDetailPageByName("Investor, Selenium")
	// .goToAccountsPage()
	// .goToAccountHoldingsPageByNameFromClient("Selenium Test")
	// .reallocateInvestment().optimizeInvestments()
	// .chooseOptimizationMethod("Risk Profile").clickOptimizeButton();
	//
	// assertTrue(pageContainsStr("Portfolio Risk Level: 8"));
	//
	// // make sure more/new assets have been added to table
	// int numberOfAssets = webDriver.findElements(
	// By.xpath("//*[@class=\"allocationTableLink\"]")).size();
	// log("Number of assets found: " + numberOfAssets);
	// assertTrue("Not enough assets were added to table",
	// numberOfAssets > (EXISTING_STRATEGY_NAMES.length + 1));
	// }
	//
	// /**
	// * Pulls up the optimization based on white list.
	// *
	// * @throws InterruptedException
	// */
	// @Test
	// public void testPorfolioOptimizationWhiteList() throws
	// InterruptedException {
	//
	// LoginPage main = new LoginPage(webDriver);
	//
	// main.loginAs(Settings.USERNAME, Settings.PASSWORD)
	// .goToClientOverviewPage().simpleSearchByString("Selenium")
	// .goToClientDetailPageByName("Investor, Selenium")
	// .goToAccountsPage()
	// .goToAccountHoldingsPageByNameFromClient("Selenium Test")
	// .reallocateInvestment().optimizeInvestments()
	// .chooseOptimizationMethod("White List")
	// .selectWhiteList("Sample White List").clickOptimizeButton();
	//
	// // wait for waitscreen to disappear
	// waitForWaitScreenToDiappear(300);
	// log("waitScreen is gone");
	//
	// // make sure risk slider shows up correctly
	// assertTrue(pageContainsStr("Portfolio Risk Level:"));
	//
	// // make sure more/new assets have been added to table
	// int numberOfAssets = webDriver
	// .findElements(
	// By.xpath("//*[@class=\"allocationTable\"]/tbody/tr[@class=\"allocationTable-td\"]"))
	// .size();
	// log("Number of assets found: " + numberOfAssets);
	// assertTrue("Not enough assets were added to table",
	// numberOfAssets > (EXISTING_STRATEGY_NAMES.length + 1));
	//
	// }
	//
	// /**
	// * Pulls up the optimization based on current investment.
	// *
	// * @throws InterruptedException
	// */
	// @Test
	// public void testPorfolioOptimizationCurrentInvestments()
	// throws InterruptedException {
	//
	// LoginPage main = new LoginPage(webDriver);
	//
	// HoldingsPage holding = main
	// .loginAs(Settings.USERNAME, Settings.PASSWORD)
	// .goToClientOverviewPage().simpleSearchByString("Selenium")
	// .goToClientDetailPageByName("Investor, Selenium")
	// .goToAccountsPage()
	// .goToAccountHoldingsPageByNameFromClient("Selenium Test")
	// .reallocateInvestment().optimizeInvestments()
	// .chooseOptimizationMethod("Selected Investments")
	// .clickOptimizeButton();
	//
	// // ensure error message re minimum investments available shows up
	// assertTrue(pageContainsStr("A minimum number of investments (3) are
	// required for the optimizer to derive suitable results."));
	//
	// // click ok in error message
	// try {
	// clickOk();
	// } catch (Exception e) {
	// // TODO: handle exception
	// }
	//
	// /*
	// * Add more assets
	// */
	//
	// // click add-investments button
	// InvestmentsPage investment = holding.addInvestmentsByLocator(
	// By.id("gwt-debug-PortfolioRebalanceView-addInvestmentBtn"))
	// .sortBy("Highest Sharpe Ratio");
	//
	// List<String> investments = new ArrayList<String>();
	//
	// for (int i = 2; i < 10; i++) {
	// String newInvest = webDriver
	// .findElement(
	// By.xpath("(.//*[@id='gwt-debug-ManagerListItem-strategyName'])["
	// + i + "]")).getText();
	//
	// investments.add(newInvest);
	// }
	//
	// for (String newInvest : investments) {
	//
	// investment = new InvestmentsPage(webDriver);
	//
	// investment.selectInvestmentByName(newInvest);
	//
	// }
	//
	// investment.addToPortfolio().optimizeInvestments()
	// .chooseOptimizationMethod("Selected Investments")
	// .clickOptimizeButton();
	//
	// // wait for waitscreen to disappear
	// waitForWaitScreenToDiappear(300);
	// log("waitScreen is gone");
	//
	// // make sure risk slider shows up correctly
	// assertTrue(pageContainsStr("Portfolio Risk Level:"));
	//
	// // make sure more/new assets have been added to table
	// WebElement allocationTable = waitGet(By
	// .id("gwt-debug-AllocationEditTableWidgetNormal-allocationTablePanel"));
	// int numberOfAssets = allocationTable.findElements(
	// By.className("standardTable-td")).size();
	// log("Number of assets found: " + numberOfAssets);
	// assertTrue("Not enough assets were added to table",
	// numberOfAssets > (EXISTING_STRATEGY_NAMES.length + 1));
	//
	// }
	public void unlinkModelPortfolio() throws InterruptedException {

		if (pageContainsStr("Linked to:")) {
			wait(Settings.WAIT_SECONDS);
			clickElement(By.id("gwt-debug-ModelPortfolioSectionPresenter-image"));
			wait(Settings.WAIT_SECONDS);
			clickElement(By.id("gwt-debug-ModelPortfolioWidget-modelUnLink"));

		}
	}
}
