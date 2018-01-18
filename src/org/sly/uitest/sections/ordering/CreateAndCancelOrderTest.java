package org.sly.uitest.sections.ordering;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.clientsandaccounts.HoldingsPage;
import org.sly.uitest.pageobjects.clientsandaccounts.RecentOrdersPage;
import org.sly.uitest.settings.Settings;

/**
 * @author Benny Leung
 * @date : 27 April, 2017
 * @company Prive Financial
 */
public class CreateAndCancelOrderTest extends AbstractTest {
	String account = "Benny Test";

	@Test
	public void testCreateAndCancelOrderFromRebalancing() throws Exception {

		String investment = "Apple Inc.";
		String single = "Single Order";
		String mode = "Calculated System Transaction";
		LoginPage main = new LoginPage(webDriver);

		RecentOrdersPage order = ((HoldingsPage) main.loginAs(Settings.TRADE_USERNAME, Settings.TRADE_PASSWORD)
				.goToAccountOverviewPage().simpleSearchByString(account).goToAccountHoldingsPageByName(account)
				.goToRecentOrderPage().selectAllOrders(true).clickCancelOrderButton().goToHoldingsPage()
				.editViewModeOfAccount(mode).clickReallocateButton().clickAddInvestmentButton()
				.simpleSearchByName(investment).selectInvestmentByName(investment).clickAddToPortfolioButton())
						.setNewAllocationForInvestment(investment, "5").clickRebalancePreviewAndConfirm(investment)
						.goToRecentOrderPage();

		HoldingsPage holdings = order.goToAccountOverviewPage().simpleSearchByString(account)
				.goToAccountHoldingsPageByName(account).editViewModeOfAccount(mode).clickReallocateButton();
		assertTrue(pageContainsStr(investment));

		holdings.goToRecentOrderPage();
		this.checkOrderAndDeleteOrder("Buy", single, investment, account);

		order.goToAccountOverviewPage().simpleSearchByString(account).goToAccountHoldingsPageByName(account)
				.editViewModeOfAccount(mode).clickReallocateButton();
		assertFalse(pageContainsStr(investment));
	}

	@Test
	public void testCreateAndCancelOrderFromTrade() throws InterruptedException {

		String investment = "Apple Inc.";
		String isin = "US0378331005";
		String single = "Single Order";
		String mode = "Calculated System Transaction";
		LoginPage main = new LoginPage(webDriver);

		main.loginAs(Settings.TRADE_USERNAME, Settings.TRADE_PASSWORD).goToAccountOverviewPage()
				.simpleSearchByString(account).goToAccountHoldingsPageByName(account).goToRecentOrderPage()
				.selectAllOrders(true).clickCancelOrderButton().goToHoldingsPage().editViewModeOfAccount(mode)
				.clickTradeButton().editInvestmentOrderIsin(isin).editInvestmentOrderAmount("3")
				.clickPreviewButtonForInvestmentOrder().goToRecentOrderPage();

		this.checkOrderAndDeleteOrder("Buy", single, investment, account);
	}

	@Test
	public void testCreateAndCancelOrderFromMultiTrade() throws InterruptedException {
		String investment = "Porsche Automobil Holding SE Pref";
		String isin = "DE000PAH0038";
		String single = "Single Order";
		String mode = "Calculated System Transaction";

		LoginPage main = new LoginPage(webDriver);
		main.loginAs(Settings.TRADE_USERNAME, Settings.TRADE_PASSWORD).goToAccountOverviewPage()
				.goToAccountHoldingsPageByName(account).goToRecentOrderPage().selectAllOrders(true)
				.clickCancelOrderButton().goToAccountOverviewPage().selectAllAccounts(false)
				.selectSingleAccount(account).clickTradeButton().editISIN(isin)
				.clickIncreaseButtonForBuyValueByAccount(account, 3).clickSendOrdersToPreTradeQueue()
				.goToAccountOverviewPage().simpleSearchByString(account).goToAccountHoldingsPageByName(account)
				.goToRecentOrderPage();

		this.checkOrderAndDeleteOrder("Buy", single, investment, account);
	}

	public void checkOrderAndDeleteOrder(String side, String orderType, String investment, String account)
			throws InterruptedException {

		RecentOrdersPage orders = new RecentOrdersPage(webDriver);

		for (int i = 0; i < 5; i++) {
			if (!orders.isOrderVisibleInPreTradeQueue("Buy", orderType, investment, account, 1)) {
				orders.clickRefereshOrderButton();

			}
		}
		assertTrue(orders.isOrderVisibleInPreTradeQueue("Buy", orderType, investment, account, 1));

		orders.selectAllOrders(true).clickCancelOrderButton();

		assertFalse(orders.isOrderVisibleInPreTradeQueue("Buy", orderType, investment, account, 1));

	}
}
