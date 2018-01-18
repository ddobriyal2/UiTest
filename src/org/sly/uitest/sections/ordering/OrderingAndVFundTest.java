package org.sly.uitest.sections.ordering;

import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;
import org.openqa.selenium.By;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.abstractpage.MenuBarPage;
import org.sly.uitest.pageobjects.assetmanagement.VFundsPage;
import org.sly.uitest.pageobjects.clientsandaccounts.HoldingsPage;
import org.sly.uitest.pageobjects.clientsandaccounts.RecentOrdersPage;
import org.sly.uitest.pageobjects.investments.InvestmentsPage;
import org.sly.uitest.settings.Settings;

/**
 * @author Benny Leung
 * @date : 27 April, 2017
 * @company Prive Financial
 */
public class OrderingAndVFundTest extends AbstractTest {
	static Random rn = new Random();
	static String randomName = "" + rn.nextInt();
	private final static String VFUND_EUR_NAME = "Test EUR VFund" + randomName;
	private final static String VFUND_USD_NAME = "Test USD VFund" + randomName;
	String account = "Benny Test";

	@Test
	public void testGenerateOrderFromVFundOfSameCurrency() throws Exception {

		LoginPage main = new LoginPage(webDriver);
		String[] intruments = { "Adecco Group AG", "Alpha Bank AE" };
		String mode = "Calculated System Transaction";
		String vfund = VFUND_EUR_NAME;

		main.loginAs(Settings.TRADE_USERNAME, Settings.TRADE_PASSWORD);

		this.clearExistingOrder();

		this.createVFund(vfund);

		this.createTickerVFund(vfund, intruments);

		MenuBarPage menubar = new MenuBarPage(webDriver);
		HoldingsPage holdings = menubar.goToAccountOverviewPage().simpleSearchByString(account)
				.goToAccountHoldingsPageByName(account).editViewModeOfAccount(mode).clickReallocateButton()
				.clickAddInvestmentButton().simpleSearchByName(vfund).selectInvestmentByName(vfund)
				.clickAddToPortfolioButton();

		RecentOrdersPage orders = holdings.setNewAllocationForInvestment(vfund, "1")
				.clickRebalancePreviewAndConfirm(vfund).goToRecentOrderPage();

		for (String intrument : intruments) {
			this.ensureOrdersAreGenerated("Buy", "Single Order", intrument, account, 1);
		}

		orders.selectAllOrders(true).clickCancelOrderButton().goToVFundsPage().deleteVfundByName(vfund);
	}

	@Test
	public void testGenerateOrderFromVfundOfDifferentCurrency() throws Exception {
		String[] intruments = { "180 Plus Note", "A Shares USD" };
		String mode = "Calculated System Transaction";
		LoginPage main = new LoginPage(webDriver);
		String vfund = VFUND_EUR_NAME;
		main.loginAs(Settings.TRADE_USERNAME, Settings.TRADE_PASSWORD);

		this.clearExistingOrder();

		this.createVFund(vfund);

		this.createTickerVFund(vfund, intruments);

		MenuBarPage menubar = new MenuBarPage(webDriver);
		HoldingsPage holdings = menubar.goToAccountOverviewPage().simpleSearchByString(account)
				.goToAccountHoldingsPageByName(account).editViewModeOfAccount(mode).clickReallocateButton()
				.clickAddInvestmentButton().simpleSearchByName(vfund).selectInvestmentByName(vfund)
				.clickAddToPortfolioButton();

		RecentOrdersPage orders = holdings.setNewAllocationForInvestment(vfund, "1")
				.clickRebalancePreviewAndConfirm(vfund).goToRecentOrderPage();
		int i = 0;

		for (String intrument : intruments) {
			i += 1;
			this.ensureOrdersAreGenerated("Buy", "Single Order", "FX: HKD/USD", account, i);
			this.ensureOrdersAreGenerated("Buy", "Single Order", intrument, account, 1);
		}
		orders.selectAllOrders(true).clickCancelOrderButton().goToVFundsPage().deleteVfundByName(vfund);
	}

	public void createVFund(String vfund) throws Exception {

		System.out.println("test1: " + vfund);

		MenuBarPage mbPage = new MenuBarPage(webDriver);
		mbPage.goToVFundsPage().clickCreateVfundButton().editVfundname(vfund).editVfundStrategyFee("10")
				.editVfundStrategyMinInvestment("10000").clickSubmitButton().goToVFundsPage();

		this.waitForWaitingScreenNotVisible();
		assertTrue(pageContainsStr(vfund));

	}

	public void createTickerVFund(String vfund, String[] intruments) throws Exception {

		String[] investments = intruments;

		MenuBarPage mbPage = new MenuBarPage(webDriver);

		InvestmentsPage iPage = mbPage.goToVFundsPage().clickAllocateVfundsIconByName(vfund)
				.clickReallocateVfundsButton().clickAddButton();

		for (String investment : investments) {
			iPage.simpleSearchByName(investment).selectInvestmentByName(investment);
		}

		VFundsPage vFundTemp = iPage.clickAddToPortfolioButton();
		for (String investment : investments) {
			vFundTemp.editNewAllocationByPlusButton(investment, 1);
		}

		vFundTemp.clickPublishButton();

		clickOkButtonIfVisible();

		waitForElementVisible(By.id("gwt-debug-StrategyRebalanceHistoryView-tableContentsPanel"), 60);

		clickOkButtonIfVisible();

		vFundTemp.goToInvestmentsPage().simpleSearchByNameWithButton(vfund);

		waitForElementVisible(By.xpath(".//*[contains(text(),'" + vfund + "')]"), 30);

		assertTrue(pageContainsStr(vfund));

	}

	// Clear the existing orders in recent orders page
	public void clearExistingOrder() throws InterruptedException {
		MenuBarPage menubar = new MenuBarPage(webDriver);
		RecentOrdersPage order = menubar.goToOrderBookPage();
		if (isElementVisible(By.id("gwt-debug-OrdersTableWidgetView-preTradeOrdersNoInfoPanel"))) {
			order.selectAllOrders(true).clickCancelOrderButton();
		}

	}

	public void ensureOrdersAreGenerated(String side, String orderType, String investment, String account, int size) {
		RecentOrdersPage orders = new RecentOrdersPage(webDriver);

		for (int i = 0; i < 5; i++) {
			if (!orders.isOrderVisibleInPreTradeQueue("Buy", orderType, investment, account, size)) {
				orders.clickRefereshOrderButton();

			}
		}
	}
}
