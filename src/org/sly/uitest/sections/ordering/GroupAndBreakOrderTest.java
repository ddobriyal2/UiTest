package org.sly.uitest.sections.ordering;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.abstractpage.MenuBarPage;
import org.sly.uitest.pageobjects.clientsandaccounts.RecentOrdersPage;
import org.sly.uitest.settings.Settings;

/**
 * @author Benny Leung
 * @date : 27 April, 2017
 * @company Prive Financial
 */
public class GroupAndBreakOrderTest extends AbstractTest {
	String account = "Benny Test";
	String mode = "Calculated System Transaction";

	@Test
	public void testGroupOrdersFromSameAccount() throws InterruptedException {
		LoginPage main = new LoginPage(webDriver);
		String investment = "Porsche Automobil Holding SE Pref";
		String isin = "DE000PAH0038";

		int numberOfOrders = 3;
		MenuBarPage menubar = main.loginAs(Settings.TRADE_USERNAME, Settings.TRADE_PASSWORD);

		this.clearExistingOrder();

		for (int i = 0; i < numberOfOrders; i++) {
			menubar.goToAccountOverviewPage().goToAccountHoldingsPageByName(account).editViewModeOfAccount(mode)
					.clickTradeButton().editInvestmentOrderIsin(isin).editInvestmentOrderAmount("3")
					.clickPreviewButtonForInvestmentOrder().goToRecentOrderPage();

			this.ensureOrdersAreGenerated("Buy", "Single Order", investment, account, i + 1);
		}
		RecentOrdersPage orders = menubar.goToAccountOverviewPage().goToAccountHoldingsPageByName(account)
				.goToRecentOrderPage().selectAllOrders(true).clickCombineToBlockorderButton();

		assertTrue(orders.isOrderVisibleInPreTradeQueue("Buy", "Block Order", investment,
				"(block of " + String.valueOf(numberOfOrders) + ")", 1));

		orders.selectAllOrders(true).clickCancelOrderButton();
	}

	@Test
	public void testGroupOrdersFromDifferentAccount() throws InterruptedException {
		LoginPage main = new LoginPage(webDriver);
		String investment = "Porsche Automobil Holding SE Pref";
		String isin = "DE000PAH0038";
		String[] accounts = { account, "0025738-03 (CHF) - 46917", "0025738-04 (EUR) - 64221" };
		String account = "";
		int numberOfOrders = 3;

		MenuBarPage menubar = main.loginAs(Settings.TRADE_USERNAME, Settings.TRADE_PASSWORD);

		this.clearExistingOrder();

		for (int i = 0; i < numberOfOrders; i++) {
			account = accounts[i];
			menubar.goToAccountOverviewPage().goToAccountHoldingsPageByName(account).editViewModeOfAccount(mode)
					.clickTradeButton().editInvestmentOrderIsin(isin).editInvestmentOrderAmount("3")
					.clickPreviewButtonForInvestmentOrder().goToRecentOrderPage();

			this.ensureOrdersAreGenerated("Buy", "Single Order", investment, account, i + 1);
		}
		RecentOrdersPage orders = menubar.goToOrderBookPage().selectAllOrders(true).clickCombineToBlockorderButton();

		assertTrue(orders.isOrderVisibleInPreTradeQueue("Buy", "Block Order", investment,
				"(block of " + String.valueOf(numberOfOrders) + ")", 1));

		orders.selectAllOrders(true).clickCancelOrderButton();
	}

	@Test
	public void testGroupOrdersFromDifferentAccountAfterEdit() throws Exception {
		LoginPage main = new LoginPage(webDriver);
		String investment = "Porsche Automobil Holding SE Pref";
		String isin = "DE000PAH0038";
		String[] accounts = { account, "0025738-03 (CHF) - 46917", "0025738-04 (EUR) - 64221" };
		String account = "";
		int numberOfOrders = 3;
		MenuBarPage menubar = main.loginAs(Settings.TRADE_USERNAME, Settings.TRADE_PASSWORD);

		this.clearExistingOrder();

		for (int i = 0; i < numberOfOrders; i++) {
			account = accounts[i];
			menubar.goToAccountOverviewPage().goToAccountHoldingsPageByName(account).editViewModeOfAccount(mode)
					.clickTradeButton().editInvestmentOrderIsin(isin).editInvestmentOrderAmount("3")
					.clickPreviewButtonForInvestmentOrder().goToRecentOrderPage();

			this.ensureOrdersAreGenerated("Buy", "Single Order", investment, account, i + 1);
		}
		RecentOrdersPage orders = menubar.goToOrderBookPage();
		for (int i = 0; i < 3; i++) {
			orders.clickEditButtonOfOrder("Buy", "Single Order", investment, accounts[i]).editQuantityUnit("4")
					.clickSaveButtonForTrade();
		}
		orders.selectAllOrders(true).clickCombineToBlockorderButton();

		assertTrue(orders.isOrderVisibleInPreTradeQueue("Buy", "Block Order", investment,
				"(block of " + String.valueOf(numberOfOrders) + ")", 1));

		orders.selectAllOrders(true).clickCancelOrderButton();

	}

	@Test
	public void testGroupOrdersOfDifferentInvestments() throws InterruptedException {
		LoginPage main = new LoginPage(webDriver);
		String[] investments = { "Porsche Automobil Holding SE Pref", "Bayerische Motoren Werke AG", "Volkswagen AG," };
		String[] isins = { "DE000PAH0038", "DE0005190003", "DE0007664005" };
		String[] accounts = { account, "0025738-03 (CHF) - 46917", "0025738-04 (EUR) - 64221" };
		String account = "";
		String isin = "";
		String investment = "";
		int numberOfOrders = 3;
		MenuBarPage menubar = main.loginAs(Settings.TRADE_USERNAME, Settings.TRADE_PASSWORD);

		this.clearExistingOrder();

		for (int i = 0; i < numberOfOrders; i++) {
			account = accounts[i];
			isin = isins[i];
			investment = investments[i];
			menubar.goToAccountOverviewPage().goToAccountHoldingsPageByName(account).editViewModeOfAccount(mode)
					.clickTradeButton().editInvestmentOrderIsin(isin).editInvestmentOrderAmount("3")
					.clickPreviewButtonForInvestmentOrder().goToRecentOrderPage();

			this.ensureOrdersAreGenerated("Buy", "Single Order", investment, account, i + 1);
		}
		RecentOrdersPage orders = menubar.goToOrderBookPage();

		orders.selectAllOrders(true).clickCombineToBlockorderButtonForCheckingFields();

		clickYesButtonIfVisible();
		assertTrue(pageContainsStr(String.valueOf(numberOfOrders) + " single orders were not grouped."));
		clickElement(By.id("gwt-debug-BlockOrdersConfirmationDialogView-okBtn"));

		for (String invest : investments) {
			assertFalse(orders.isOrderVisibleInPreTradeQueue("Buy", "Block Order", invest,
					"(block of " + String.valueOf(numberOfOrders) + ")", 1));
		}

		orders.selectAllOrders(true).clickCancelOrderButton();
	}

	@Test
	public void testGroupOrdersOfSameAndDifferentInvestments() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);
		String[] investments = { "Porsche Automobil Holding SE Pref", "Bayerische Motoren Werke AG" };
		String[] isins = { "DE000PAH0038", "DE0005190003" };
		String[] accounts = { account, "0025738-03 (CHF) - 46917" };
		String investment = "";
		String isin = "";
		String account = "";
		int numberOfOrders = 2;
		int numberOfInvestments = 2;
		MenuBarPage menubar = main.loginAs(Settings.TRADE_USERNAME, Settings.TRADE_PASSWORD);

		this.clearExistingOrder();

		for (int j = 0; j < numberOfInvestments; j++) {
			investment = investments[j];
			isin = isins[j];
			for (int i = 0; i < numberOfOrders; i++) {
				account = accounts[i];
				menubar.goToAccountOverviewPage().goToAccountHoldingsPageByName(account).editViewModeOfAccount(mode)
						.clickTradeButton().editInvestmentOrderIsin(isin).editInvestmentOrderAmount("3")
						.clickPreviewButtonForInvestmentOrder().goToRecentOrderPage();

				this.ensureOrdersAreGenerated("Buy", "Single Order", investment, account, i + 1);
			}

		}
		RecentOrdersPage orders = menubar.goToOrderBookPage();
		orders.selectAllOrders(true).clickCombineToBlockorderButton();

		for (String invest : investments) {
			assertTrue(orders.isOrderVisibleInPreTradeQueue("Buy", "Block Order", invest,
					"(block of " + String.valueOf(numberOfOrders) + ")", 1));
		}
		orders.selectAllOrders(true).clickCancelOrderButton();
	}

	@Test
	public void testGroupBlockOrder() throws InterruptedException {
		LoginPage main = new LoginPage(webDriver);
		String[] investments = { "Porsche Automobil Holding SE Pref", "Bayerische Motoren Werke AG" };
		String[] isins = { "DE000PAH0038", "DE0005190003" };
		String[] accounts = { account, "0025738-03 (CHF) - 46917" };
		String investment = "";
		String isin = "";
		String account = "";
		int numberOfOrders = 2;
		int numberOfInvestments = 2;
		MenuBarPage menubar = main.loginAs(Settings.TRADE_USERNAME, Settings.TRADE_PASSWORD);

		this.clearExistingOrder();

		for (int j = 0; j < numberOfInvestments; j++) {
			investment = investments[j];
			isin = isins[j];
			for (int i = 0; i < numberOfOrders; i++) {
				account = accounts[i];
				menubar.goToAccountOverviewPage().goToAccountHoldingsPageByName(account).editViewModeOfAccount(mode)
						.clickTradeButton().editInvestmentOrderIsin(isin).editInvestmentOrderAmount("3")
						.clickPreviewButtonForInvestmentOrder().goToRecentOrderPage();

				this.ensureOrdersAreGenerated("Buy", "Single Order", investment, account, i + 1);
			}

		}
		RecentOrdersPage orders = menubar.goToOrderBookPage().clickRefereshOrderButton();
		orders.selectAllOrders(true).clickCombineToBlockorderButton();

		for (String invest : investments) {
			try {
				assertTrue(orders.isOrderVisibleInPreTradeQueue("Buy", "Block Order", invest,
						"(block of " + String.valueOf(numberOfOrders) + ")", 1));
			} catch (AssertionError e) {
				orders.selectAllOrders(true).clickCombineToBlockorderButton();
				assertTrue(orders.isOrderVisibleInPreTradeQueue("Buy", "Block Order", invest,
						"(block of " + String.valueOf(numberOfOrders) + ")", 1));
			}

		}
		// TO:DO
		orders.selectAllOrders(true).clickCombineToBlockorderButtonForCheckingFields();

		assertTrue(pageContainsStr("Block order can not be combined."));
		clickOkButtonIfVisible();

		orders.selectAllOrders(true).clickCancelOrderButton();
	}

	@Test
	public void testBreakBlockOrder() throws InterruptedException {
		LoginPage main = new LoginPage(webDriver);
		String investment = "Porsche Automobil Holding SE Pref";
		String isin = "DE000PAH0038";
		int numberOfOrders = 3;
		MenuBarPage menubar = main.loginAs(Settings.TRADE_USERNAME, Settings.TRADE_PASSWORD);

		this.clearExistingOrder();
		for (int i = 0; i < numberOfOrders; i++) {
			menubar.goToAccountOverviewPage().goToAccountHoldingsPageByName(account).editViewModeOfAccount(mode)
					.clickTradeButton().editInvestmentOrderIsin(isin).editInvestmentOrderAmount("3")
					.clickPreviewButtonForInvestmentOrder().goToRecentOrderPage();

			this.ensureOrdersAreGenerated("Buy", "Single Order", investment, account, i + 1);
		}
		RecentOrdersPage orders = menubar.goToAccountOverviewPage().goToAccountHoldingsPageByName(account)
				.goToRecentOrderPage().selectAllOrders(true).clickCombineToBlockorderButton();

		assertTrue(orders.isOrderVisibleInPreTradeQueue("Buy", "Block Order", investment,
				"(block of " + String.valueOf(numberOfOrders) + ")", 1));

		orders.selectAllOrders(true).clickBreakupBlockorderButton();

		for (int i = 0; i < numberOfOrders; i++) {
			assertTrue(orders.isOrderVisibleInPreTradeQueue("Buy", "Single Order", investment, account, i + 1));
		}
		orders.selectAllOrders(true).clickCancelOrderButton();
	}

	@Test
	public void testBreakMultipleBlockOrder() throws InterruptedException {
		LoginPage main = new LoginPage(webDriver);
		String[] investments = { "Porsche Automobil Holding SE Pref", "Bayerische Motoren Werke AG" };
		String[] isins = { "DE000PAH0038", "DE0005190003" };
		String[] accounts = { account, "0025738-03 (CHF) - 46917" };
		String investment = "";
		String isin = "";
		String account = "";
		int numberOfOrders = 2;
		int numberOfInvestments = 2;
		MenuBarPage menubar = main.loginAs(Settings.TRADE_USERNAME, Settings.TRADE_PASSWORD);

		this.clearExistingOrder();

		for (int j = 0; j < numberOfInvestments; j++) {
			investment = investments[j];
			isin = isins[j];
			for (int i = 0; i < numberOfOrders; i++) {
				account = accounts[i];
				menubar.goToAccountOverviewPage().goToAccountHoldingsPageByName(account).editViewModeOfAccount(mode)
						.clickTradeButton().editInvestmentOrderIsin(isin).editInvestmentOrderAmount("3")
						.clickPreviewButtonForInvestmentOrder().goToRecentOrderPage();

				this.ensureOrdersAreGenerated("Buy", "Single Order", investment, account, i + 1);
			}

		}
		RecentOrdersPage orders = menubar.goToOrderBookPage().clickRefereshOrderButton();
		orders.selectAllOrders(true).clickCombineToBlockorderButton();

		for (String invest : investments) {
			assertTrue(orders.isOrderVisibleInPreTradeQueue("Buy", "Block Order", invest,
					"(block of " + String.valueOf(numberOfOrders) + ")", 1));
		}

		orders.selectAllOrders(true).clickBreakupBlockorderButton();

		for (String invest : investments) {
			for (int i = 0; i < numberOfOrders; i++) {
				account = accounts[i];
				assertTrue(orders.isOrderVisibleInPreTradeQueue("Buy", "Single Order", invest, account, 1));
			}
		}

		orders.selectAllOrders(true).clickCancelOrderButton();
	}

	@Test
	public void testGroupSingleOrder() throws InterruptedException {
		String investment = "Porsche Automobil Holding SE Pref";
		String isin = "DE000PAH0038";
		String single = "Single Order";
		LoginPage main = new LoginPage(webDriver);

		MenuBarPage menubar = main.loginAs(Settings.TRADE_USERNAME, Settings.TRADE_PASSWORD);
		this.clearExistingOrder();
		RecentOrdersPage orders = menubar.goToAccountOverviewPage().simpleSearchByString(account)
				.goToAccountHoldingsPageByName(account).editViewModeOfAccount(mode).clickTradeButton()
				.editInvestmentOrderIsin(isin).editInvestmentOrderAmount("3").clickPreviewButtonForInvestmentOrder()
				.goToRecentOrderPage();

		this.ensureOrdersAreGenerated("Buy", "Single Order", investment, account, 1);

		orders.selectAllOrders(true).clickCombineToBlockorderButtonForCheckingFields();
		assertTrue(pageContainsStr("In order to combine, at least two entries need to be selected."));
		clickOkButtonIfVisible();

		orders.selectAllOrders(true).clickCancelOrderButton();
	}

	@Test
	public void testBreakSingleOrder() throws InterruptedException {
		String investment = "Porsche Automobil Holding SE Pref";
		String isin = "DE000PAH0038";
		String single = "Single Order";
		LoginPage main = new LoginPage(webDriver);

		MenuBarPage menubar = main.loginAs(Settings.TRADE_USERNAME, Settings.TRADE_PASSWORD);
		this.clearExistingOrder();
		RecentOrdersPage orders = menubar.goToAccountOverviewPage().simpleSearchByString(account)
				.goToAccountHoldingsPageByName(account).editViewModeOfAccount(mode).clickTradeButton()
				.editInvestmentOrderIsin(isin).editInvestmentOrderAmount("3").clickPreviewButtonForInvestmentOrder()
				.goToRecentOrderPage();

		this.ensureOrdersAreGenerated("Buy", "Single Order", investment, account, 1);

		orders.selectAllOrders(true).clickBreakupBlockorderButton();
		assertTrue(pageContainsStr("Single order can not be split"));
		clickOkButtonIfVisible();

		orders.selectAllOrders(true).clickCancelOrderButton();
	}

	public void ensureOrdersAreGenerated(String side, String orderType, String investment, String account, int size) {
		RecentOrdersPage orders = new RecentOrdersPage(webDriver);

		for (int i = 0; i < 5; i++) {
			if (!orders.isOrderVisibleInPreTradeQueue("Buy", orderType, investment, account, size)) {
				orders.clickRefereshOrderButton();

			}
		}
	}

	// Clear the existing orders in recent orders page
	public void clearExistingOrder() throws InterruptedException {
		MenuBarPage menubar = new MenuBarPage(webDriver);
		RecentOrdersPage order = menubar.goToOrderBookPage();

		try {
			waitForElementVisible(By.id("gwt-debug-OrdersTableWidgetView-preTradeOrdersNoInfoPanel"), 5);
		} catch (TimeoutException e) {

		}
		if (isElementVisible(By.id("gwt-debug-OrdersTableWidgetView-preTradeOrdersNoInfoPanel"))) {
			order.selectAllOrders(true).clickCancelOrderButton();
		}

	}
}
