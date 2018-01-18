package org.sly.uitest.sections.vfund;

import static org.junit.Assert.assertTrue;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.abstractpage.MenuBarPage;
import org.sly.uitest.pageobjects.assetmanagement.StrategyRulesPage;
import org.sly.uitest.pageobjects.assetmanagement.VFundsPage;
import org.sly.uitest.pageobjects.clientsandaccounts.HoldingsPage;
import org.sly.uitest.pageobjects.investments.InvestmentsPage;
import org.sly.uitest.settings.Settings;

/**
 * Test Vfund
 * 
 * @author Nandi Ouyang
 * @date : April 25, 2015
 * 
 * @company Prive Financial
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VFundTest extends AbstractTest {

	static Random rn = new Random();
	static String randomName = "" + rn.nextInt();
	private final static String VFUND_NAME = "Test VFund" + randomName;

	@Test
	public void testAllTests() throws Exception {
		LoginPage main = new LoginPage(webDriver);
		MenuBarPage mbPage = main.loginAs(Settings.USERNAME, Settings.PASSWORD);
		test1CreateVFund(mbPage);
		wait(Settings.WAIT_SECONDS);

		// not applicable for now
		// test2FailToCreateTickerVFund(mbPage);
		// wait(5);

		test3CreateTickerVFund(mbPage);
		wait(Settings.WAIT_SECONDS);

		test4EditVFund(mbPage);
		wait(Settings.WAIT_SECONDS);

		test5DeleteVFund(mbPage);
		wait(Settings.WAIT_SECONDS);

		test6StrategyDesigner(mbPage);
		wait(Settings.WAIT_SECONDS);

	}

	public void test1CreateVFund(MenuBarPage mbPage) throws Exception {

		System.out.println("test1: " + VFUND_NAME);
		mbPage.goToVFundsPage().clickCreateVfundButton().editVfundname(VFUND_NAME).editVfundStrategyFee("10")
				.editVfundStrategyMinInvestment("10000").clickSubmitButton().goToVFundsPage();

		assertTrue(pageContainsStr(VFUND_NAME));

	}

	public void test2FailToCreateTickerVFund(MenuBarPage mbPage) throws Exception {

		System.out.println("test2: " + VFUND_NAME);

		mbPage.goToVFundsPage().clickAllocateVfundsIconByName(VFUND_NAME).clickReallocateVfundsButton()
				.clickAddButton();
		// clickOkButtonIfVisible();

		waitForElementVisible(By.id("gwt-debug-CustomDialog-okButton"), 20);
		assertTrue(pageContainsStr("Ticker not found for given symbol"));

		try {

			clickElement(By.xpath("(//button[@id='gwt-debug-CustomDialog-okButton'])[2]"));
		} catch (Exception e) {

		}

		// wait(3);

		clickOkButtonIfVisible();
		// wait(3);

	}

	public void test3CreateTickerVFund(MenuBarPage mbPage) throws Exception {

		System.out.println("test3: " + VFUND_NAME);

		String investment = "PCCW Limited";

		Boolean available = false;
		InvestmentsPage iPage = mbPage.goToVFundsPage().clickAllocateVfundsIconByName(VFUND_NAME)
				.clickReallocateVfundsButton().clickAddButton();
		VFundsPage vFundTemp = (VFundsPage) iPage.simpleSearchByName(investment).selectInvestmentByName(investment)
				.clickAddToPortfolioButton();

		VFundsPage vFund = vFundTemp.editNewAllocationByPlusButton(investment, 2).clickPublishButton();

		clickOkButtonIfVisible();

		try {
			waitForElementVisible(By.id("gwt-debug-StrategyRebalanceHistoryView-tableContentsPanel"), 60);
			available = true;
			clickOkButtonIfVisible();

		} catch (Exception e) {
			available = false;
			clickOkButtonIfVisible();
		}

		if (available) {
			scrollToTop();
			vFund.expandVfundRebalancingHistory("");
			this.waitForElementVisible(By.xpath(".//div[.='10%']"), Settings.WAIT_SECONDS);
			assertTrue(pageContainsStr("10%"));

		}

		vFund.goToInvestmentsPage().simpleSearchByNameWithButton(VFUND_NAME);

		assertTrue(getTextByLocator(By.id("gwt-debug-ManagerListItem-strategyName")).equals(VFUND_NAME));

	}

	public void test4EditVFund(MenuBarPage mbPage) throws InterruptedException {
		System.out.println("test4: " + VFUND_NAME);
		mbPage.goToVFundsPage().editVfundByName(VFUND_NAME);

		sendKeysToElement(By.id("gwt-debug-StrategyEditManagerView-strategyDesc"), "testDesc");

		selectFromDropdown(By.id("gwt-debug-StrategyEditManagerView-strategyCurrency"), "AUD");

		selectFromDropdown(By.id("gwt-debug-StrategyEditManagerView-strategyAssetClass"), "Fixed Income");

		selectFromDropdown(By.id("gwt-debug-StrategyEditManagerView-strategyType"), "vFund, Benchmark");

		selectFromDropdown(By.id("gwt-debug-StrategyEditManagerView-strategyStyle"), "Sector");

		selectFromDropdown(By.id("gwt-debug-StrategyEditManagerView-strategySector"), "Equity-Consumer Goods");

		selectFromDropdown(By.id("gwt-debug-StrategyEditManagerView-strategyRegion"), "Global");

		sendKeysToElement(By.id("gwt-debug-StrategyEditManagerView-strategyFees"), "11");

		sendKeysToElement(By.id("gwt-debug-StrategyEditManagerView-strategyMinInvestment"), "10001");

		WebElement element = webDriver.findElement(By.id("gwt-debug-StrategyEditManagerView-submitBtn"));
		// element.sendKeys(Keys.RETURN);
		element.click();
		// clickElement(By.id("gwt-debug-StrategyEditManagerView-submitBtn"));

		clickElementAndWait3(By.xpath("//td[a='" + VFUND_NAME + "']/following-sibling::td[8]/button"));

		assertTrue("testDesc".equals(getInputByLocator(By.id("gwt-debug-StrategyEditManagerView-strategyDesc"))));

		assertTrue("11".equals(getInputByLocator(By.id("gwt-debug-StrategyEditManagerView-strategyFees"))));

		assertTrue("10,001.00"
				.equals(getInputByLocator(By.id("gwt-debug-StrategyEditManagerView-strategyMinInvestment"))));

		assertTrue(
				"AUD".equals(getSelectedTextFromDropDown(By.id("gwt-debug-StrategyEditManagerView-strategyCurrency"))));

		assertTrue("Fixed Income"
				.equals(getSelectedTextFromDropDown(By.id("gwt-debug-StrategyEditManagerView-strategyAssetClass"))));

		assertTrue("vFund, Benchmark"
				.equals(getSelectedTextFromDropDown(By.id("gwt-debug-StrategyEditManagerView-strategyType"))));

		assertTrue(
				"Sector".equals(getSelectedTextFromDropDown(By.id("gwt-debug-StrategyEditManagerView-strategyStyle"))));

		assertTrue("Equity-Consumer Goods"
				.equals(getSelectedTextFromDropDown(By.id("gwt-debug-StrategyEditManagerView-strategySector"))));

		assertTrue("Global"
				.equals(getSelectedTextFromDropDown(By.id("gwt-debug-StrategyEditManagerView-strategyRegion"))));

	}

	public void test5DeleteVFund(MenuBarPage mbPage) throws InterruptedException {
		System.out.println("test5: " + VFUND_NAME);
		mbPage.goToVFundsPage().deleteVfundByName(VFUND_NAME);

		// assertTrue(!pageContainsStr(VFUND_NAME));
	}

	public void test6StrategyDesigner(MenuBarPage mbPage) throws InterruptedException {
		System.out.println("test6: " + VFUND_NAME);
		String name = "Sample Strategy Rule";
		clickOkButtonIfVisible();
		StrategyRulesPage strategyRulesPage = mbPage.goToStrategyRulesPage();
		// wait(5);
		strategyRulesPage.clickSetupByName(name);

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		// TODO make sure risk reward panel works
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gwt-debug-RiskRewardWidget-mainPanel")));

			assertTrue(pageContainsStr("Hypothetical historical performance of selected portfolio."));
		} catch (TimeoutException e) {
			assertTrue(pageContainsStr("Service exception executing"));
		}

	}

	@Test
	public void testAllocatingVFund() throws Exception {
		String investment1 = "Power Assets Holdings Limited";
		String investment2 = "HSBC Holdings plc";
		String account = "Selenium Test";
		String modelPortfolio = "Sample Model Portfolio";
		String VFUND_NAME2 = "Test VFund" + randomName;
		String tmp_investment = "";

		LoginPage main = new LoginPage(webDriver);

		MenuBarPage mbPage = main.loginAs(Settings.USERNAME, Settings.PASSWORD);
		this.test1CreateVFund(mbPage);
		this.test3CreateTickerVFund(mbPage);

		HoldingsPage holdings = mbPage.goToAccountOverviewPage().simpleSearchByString(account)
				.goToAccountHoldingsPageByName(account).clickReallocateButton().clickAddInvestmentButton()
				.simpleSearchByName(VFUND_NAME).selectInvestmentByName(VFUND_NAME).clickAddToPortfolioButton();

		int size = getSizeOfElements(By.xpath(
				".//div[@id='gwt-debug-AllocationEditTableWidgetNormal-allocationTablePanel']//tr//td//div[@class='allocationTableLink']"));

		for (int i = 1; i <= size; i++) {
			tmp_investment = getTextByLocator(By
					.xpath("(.//div[@id='gwt-debug-AllocationEditTableWidgetNormal-allocationTablePanel']//tr//td//div[@class='allocationTableLink'])["
							+ String.valueOf(i) + "]"));
			holdings.setNewAllocationForInvestment(tmp_investment, "0");
		}
		// rebalance account
		holdings.setNewAllocationForInvestment(VFUND_NAME, "5").clickRebalancePreviewAndConfirm("")
				.confirmHistoryStatus().goToModelPortfoliosPage().clickManageModelPortfolios()
				.goToModelPortfolio(modelPortfolio).clickReallocateButton().clickAddInvestmentButton()
				.simpleSearchByName(VFUND_NAME).selectInvestmentByName(VFUND_NAME).clickAddToPortfolioButton();
		// rebalance vfund
		holdings.setNewAllocationForInvestment(VFUND_NAME, "5").clickRebalancePreviewAndConfirm("")
				.confirmHistoryStatus();

		VFundsPage vfunds = mbPage.goToVFundsPage().clickAllocateVfundsIconByName(VFUND_NAME)
				.clickReallocateVfundsButton().clickAddButton().simpleSearchByName(investment1)
				.selectInvestmentByName(investment1).simpleSearchByName(investment2).selectInvestmentByName(investment2)
				.clickAddToPortfolioButton();

		vfunds.editNewAllocationByMinusButton("PCCW", 2).editNewAllocationByPlusButton(investment1, 2)
				.editNewAllocationByPlusButton(investment2, 3).clickPublishButton();

		clickOkButtonIfVisible();

		HoldingsPage holdings2 = vfunds.goToModelPortfoliosPage().clickManageModelPortfolios()
				.goToModelPortfolio(modelPortfolio).clickReallocateButton();

		try {
			assertTrue(pageContainsStr(investment1));
			assertTrue(pageContainsStr(investment2));
		} catch (AssertionError e) {
			this.refreshPage();
			assertTrue(pageContainsStr(investment1));
			assertTrue(pageContainsStr(investment2));
		}
		holdings2.goToAccountOverviewPage().simpleSearchByString(account).goToAccountHoldingsPageByName(account)
				.clickReallocateButton();

		try {
			assertTrue(pageContainsStr(investment1));
			assertTrue(pageContainsStr(investment2));
		} catch (AssertionError e) {
			this.refreshPage();
			assertTrue(pageContainsStr(investment1));
			assertTrue(pageContainsStr(investment2));
		}

		holdings2.goToVFundsPage().editVfundByName(VFUND_NAME).editVfundname(VFUND_NAME2).clickSubmitButton();

		vfunds.goToModelPortfoliosPage().clickManageModelPortfolios().goToModelPortfolio(modelPortfolio)
				.clickReallocateButton();

		assertTrue(pageContainsStr(VFUND_NAME2));

		holdings2.goToVFundsPage().deleteVfundByName(VFUND_NAME2);

	}
}
