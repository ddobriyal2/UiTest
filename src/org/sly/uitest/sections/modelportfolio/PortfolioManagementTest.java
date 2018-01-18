package org.sly.uitest.sections.modelportfolio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.admin.AdminEditPage;
import org.sly.uitest.pageobjects.clientsandaccounts.AccountOverviewPage;
import org.sly.uitest.pageobjects.clientsandaccounts.HoldingsPage;
import org.sly.uitest.pageobjects.clientsandaccounts.MultiOrderScreenPage;
import org.sly.uitest.pageobjects.clientsandaccounts.RecentOrdersPage;
import org.sly.uitest.pageobjects.investments.InvestmentsPage;
import org.sly.uitest.settings.Settings;

/**
 * @author Lynne Huang
 * @date : 7 Oct, 2015
 * @company Prive Financial
 */
public class PortfolioManagementTest extends AbstractTest {

	@Ignore
	public void checkExecutionModeReconfirmTrade() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		String account = "Rebalance Reconfim Accocunt";
		String format = "d-MMM-yyyy";

		HoldingsPage holdings = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage()
				.simpleSearchByString(account).goToAccountHoldingsPageByName(account).clickTradeButton()
				.editInvestmentOrderSide("Sell");

		String asset1 = getTextByLocator(
				By.xpath(".//*[@id='gwt-debug-PortfolioTradeOrderRowWidgetView-assetListBox']/option[2]"));
		String asset2 = "GOLDMAN SACHS GROUP INC";
		String amount1 = "1000";
		String amount2 = "500";

		RecentOrdersPage orders = holdings.editInvestmentOrderAssetToSell(asset1).editInvestmentOrderAmount(amount1)
				.clickAddOrderIcon().editInvestmentOrderSide("Buy").editInvestmentOrderAssetToBuy()
				.simpleSearchByName(asset2).selectInvestmentByNameForInvestmentOrder(asset2)
				.editInvestmentOrderAmount(amount2).clickPreviewButtonForInvestmentOrder();
		if (!pageContainsStr("Portfolio switch cannot be process")) {
			orders.goToRecentOrderPage();

			String currentTime = getCurrentTimeInFormat(format);
			// wait(Settings.WAIT_SECONDS);
			// waitForElementVisible(
			// By.xpath("//button[contains(@id,'confirmBtn')]"), 60);

			// currentTime = getTextByLocator(By
			// .xpath(".//*[@id='gwt-debug-OrdersForInvestorAccountSingleView-ordersTable']/tbody/tr/td[1]"));

			log("current: " + currentTime);
			for (int i = 0; i < Settings.ATTEMPT_LOOPING_NUMBER; i++) {
				if (!isElementDisplayed(By.xpath(
						".//*[@id='gwt-debug-OrdersForInvestorAccountView-openOrdersFlexTable' and .='No open orders found.']"))) {

					try {
						orders.clickConfirmButtonOfTheOpenOrder(currentTime);
					} catch (Exception e) {
						// TODO: handle exception
					}

					if (isElementVisible(By.xpath(".//*[contains(text(),'No open orders found.')]"))) {
						break;
					}

				}

			}

			orders.goToTransactionHistoryPage().confirmHistoryStatus().goToHoldingsPage();

			assertTrue(pageContainsStr(asset2));

			// delete the asset2
			holdings.clickReallocateButton().setNewAllocationForInvestment(asset2, "0")
					.clickRebalancePreviewAndConfirm(asset2).goToRecentOrderPage();
			// wait(Settings.WAIT_SECONDS);
			waitForElementVisible(By.xpath("//button[contains(@id,'confirmBtn')]"), 60);

			// currentTime = getTextByLocator(By
			// .xpath(".//*[@id='gwt-debug-OrdersForInvestorAccountSingleView-ordersTable']/tbody/tr/td[1]"));

			for (int i = 0; i < Settings.ATTEMPT_LOOPING_NUMBER; i++) {
				if (!isElementDisplayed(By.xpath(
						".//*[@id='gwt-debug-OrdersForInvestorAccountView-openOrdersFlexTable' and .='No open orders found.']"))) {

					try {
						orders.clickConfirmButtonOfTheOpenOrder(currentTime);
					} catch (Exception e) {
						// TODO: handle exception
					}

					if (isElementVisible(By.xpath(".//*[contains(text(),'No open orders found.')]"))) {
						break;
					}

				}

			}
		}

	}

	@Test
	public void testTradeFunction() throws Exception {

		// 5. Navigate to the holdings page of the account and click on
		// Rebalance
		// 6. The portfolio should show all 3 asset in the rebalance view

		LoginPage main = new LoginPage(webDriver);

		String account = "Selenium Test";

		// 1. Rebalance Account 'SeleniumTest' to contain more than 2 assets
		// (A,B)
		// (paper trading account)
		HoldingsPage holdings = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage()
				.simpleSearchByString("Selenium").goToAccountHoldingsPageByName(account);

		if (pageContainsStr("This portfolio is linked")) {
			holdings.goToDetailsPage().goToEditPageByField("Model Portfolio");
			clickElement(By.id("gwt-debug-ModelPortfolioWidget-modelUnLink"));
			main.goToHoldingsPage();
		}

		// get the existing investments
		List<String> investments1 = new ArrayList<String>();

		int size = (getSizeOfElements(
				By.xpath(".//*[@id='gwt-debug-PortfolioAllocationView-allocationTablePanel']/table/tbody/tr")) - 3) / 2;

		for (int i = 1; i <= size; i++) {

			int index = i * 2;

			String thisInvestment = getTextByLocator(
					By.xpath(".//*[@id='gwt-debug-PortfolioAllocationView-allocationTablePanel']/table/tbody/tr["
							+ index + "]/td[3]"));

			investments1.add(thisInvestment);

		}

		// add a new investment
		InvestmentsPage investments = holdings.clickReallocateButton().clickAddInvestmentButton();

		String investment2 = getTextByLocator(By.id("gwt-debug-ManagerListItem-strategyName"));

		holdings = ((HoldingsPage) investments.selectInvestmentByName(investment2).clickAddToPortfolioButton())
				.setNewAllocationForInvestment(investment2, "10").clickRebalancePreviewAndConfirm(investment2)
				.goToTransactionHistoryPage().confirmHistoryStatus().goToHoldingsPage();

		// 2. Create a trade order to buy an adittional asset (C)
		// 3. Create a trade order to sell all of asset A
		// 4. Wait until rebalance is done
		String unit = "";
		try {
			unit = (getTextByLocator(By.xpath("(//td[.='" + investment2 + "'])[2]/following-sibling::td[2]"))
					.replace(",", "").split(Pattern.quote(".")))[0];

		} catch (TimeoutException e) {
			this.refreshPage();
			unit = (getTextByLocator(By.xpath("(//td[.='" + investment2 + "'])[2]/following-sibling::td[2]"))
					.replace(",", "").split(Pattern.quote(".")))[0];

		}
		log(unit);

		holdings.clickTradeButton().editInvestmentOrderSide("Buy").editInvestmentOrderAssetToBuy();

		String investment3 = getTextByLocator(By.xpath("(.//*[@id='gwt-debug-ManagerListItem-strategyName'])[3]"));

		RecentOrdersPage order = investments.selectInvestmentByNameForInvestmentOrder(investment3)
				.editInvestmentOrderAmount("100").deleteTransactionByInvestment(investment3).clickAddOrderIcon()
				.editInvestmentOrderSide("Sell").editInvestmentOrderAssetToSell(investment2)
				.editInvestmentOrderAmount(unit).clickPreviewButtonForInvestmentOrder();

		// 5. Navigate to the holdings page of the account and click on
		// Rebalance
		// 6. The portfolio should show all 3 asset in the rebalance view
		order.goToHoldingsPage().clickReallocateButton();

		assertTrue(pageContainsStr(investment2));

		for (String thisInvestment : investments1) {

			log(thisInvestment);
			assertTrue(pageContainsStr(thisInvestment));

		}

		// Reset
		holdings.setNewAllocationForInvestment(investment2, "0").clickRebalancePreviewAndConfirm(investment2)
				.confirmHistoryStatus();

	}

	@Test
	public void testTradeFunctionWithIsin() throws Exception {

		String account = "Selenium Test";
		String investment1 = "Power Assets Holdings Limited";
		String investment2 = "China Gas Holdings Limited";
		String isin = "HK0006000050";
		String amountBeforeSell = "";
		Boolean investmentExist = true;
		List<String> investmentList = new ArrayList<String>();

		LoginPage main = new LoginPage(webDriver);
		HoldingsPage holdings = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage()
				.simpleSearchByString(account).goToAccountHoldingsPageByName(account).clickReallocateButton();

		if (!pageContainsStr(investment1)) {
			investmentList.add(investment1);
			investmentExist = false;
		}
		if (!pageContainsStr(investment2)) {
			investmentList.add(investment2);
			investmentExist = false;
		}

		for (String investment : investmentList) {
			holdings = ((HoldingsPage) holdings.clickAddInvestmentButton().simpleSearchByName(investment)
					.selectInvestmentByName(investment).clickAddToPortfolioButton())
							.setNewAllocationForInvestment(investment, "5");
		}

		if (investmentExist) {
			holdings.clickCancelEditButton();
		} else {
			holdings.clickRebalancePreviewAndConfirm("").goToTransactionHistoryPage().confirmHistoryStatus()
					.goToHoldingsPage();
		}

		amountBeforeSell = getTextByLocator(
				By.xpath(".//td[div[div[contains(text(),'" + investment2 + "')]]]//following-sibling::td[2]//div"));

		holdings.clickAddButtonForInvestment(investment2);

		// after clicking add button, there should be buy order for investment2
		assertEquals(investment2, getTextByLocator(By.id("gwt-debug-SearchStrategyWidgetView-assetNameLabel")));

		holdings.clickCancelTradeButton().clickMinusButtonForInvestment(investment2);

		// after clicking minus button, the first option of dropdown should be
		// investment2
		assertEquals(investment2,
				getSelectedTextFromDropDown(By.id("gwt-debug-PortfolioTradeOrderRowWidgetView-assetListBox")));

		holdings.editInvestmentOrderAmount("10").clickAddOrderIcon().editInvestmentOrderIsin("ABCDEFG")
				.editCurrencyForCreateInvestment("USD").clickCreateButtonForCreateInvestment();

		// if ISIN is invalid, there should be no investment name
		this.waitForWaitingScreenNotVisible();

		assertEquals("",
				webDriver.findElement((By.xpath("(.//*[@id='gwt-debug-SearchStrategyWidgetView-assetNameLabel'])[2]")))
						.getText());

		clickNoButtonIfVisible();

		holdings.editInvestmentOrderIsin(isin);

		// if ISIN is valid, there should be investment name
		assertEquals(investment1,
				getTextByLocator(By.xpath("(.//*[@id='gwt-debug-SearchStrategyWidgetView-assetNameLabel'])[2]")));

		holdings.editInvestmentOrderSide("Buy").editInvestmentOrderAmount("100000000")
				.clickPreviewButtonForCheckingFields();

		// if the buy amount exceeds buying power, there should be error
		// message.
		assertTrue(pageContainsStr("There is insufficient cash available to process this trade"));
		clickOkButtonIfVisible();

		holdings.editInvestmentOrderAmount("10").clickAddOrderIcon().editInvestmentOrderSide("Sell")
				.editInvestmentOrderAssetToSell(investment1).editInvestmentOrderAmount("10")
				.clickPreviewButtonForCheckingFields();

		// only one trade order per asset is allowed.
		assertTrue(pageContainsStr("Only one trade order per asset is allowed"));
		clickOkButtonIfVisible();

		clickElement(By.xpath("(.//*[@id='gwt-debug-PortfolioTradeOrderRowWidgetView-deleteImg'])[3]"));

		RecentOrdersPage orders = holdings.clickPreviewButtonForInvestmentOrder().goToRecentOrderPage()
				.goToHistoricalOrders();
		for (int i = 0; i < Settings.ATTEMPT_LOOPING_NUMBER; i++) {
			try {
				waitForElementVisible(By.xpath(".//td[div[contains(text(),'" + investment1
						+ "')]]//following-sibling::td[div[.='Filled fully']]"), 20);

				waitForElementVisible(By.xpath(".//td[div[contains(text(),'" + investment2
						+ "')]]//following-sibling::td[div[.='Filled fully']]"), 20);
				return;
			} catch (TimeoutException e) {
				orders.clickRefereshOrderButton().goToHistoricalOrders();
			}
		}

		assertTrue(isElementVisible(By.xpath(
				".//td[div[contains(text(),'" + investment1 + "')]]//following-sibling::td[div[.='Filled fully']]")));

		assertTrue(isElementVisible(By.xpath(
				".//td[div[contains(text(),'" + investment2 + "')]]//following-sibling::td[div[.='Filled fully']]")));

		orders.goToHoldingsPage().clickReallocateButton();
		for (String investment : investmentList) {
			assertTrue(pageContainsStr(investment));
		}
		holdings.setNewAllocationForInvestment(investment1, "0").setNewAllocationForInvestment(investment2, "0")
				.clickRebalancePreviewAndConfirm("").confirmHistoryStatus();
	}

	@Ignore
	public void testMultiOrderScreen() throws Exception {

		LoginPage main = new LoginPage(webDriver);
		String module = "MODULE_ALLOW_TRADE_BUTTON_ON_SUMMARY_OF_ACCOUNTS";
		String account = "Selenium Test";
		String investment = "Power Assets Holdings Limited";
		String isin = "HK0006000050";
		String timestamp = "";
		main.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD).goToAdminEditPage()
				.editAdminThisField("Advisor Company Module Permission").jumpByKeyAndLoad("561")
				.editModuleToggle(module, true, true).clickSubmitButton();

		checkLogout();
		this.handleAlert();
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 30);

		LoginPage main2 = new LoginPage(webDriver);

		HoldingsPage holdings = main2.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage()
				.simpleSearchByString(account).goToAccountHoldingsPageByName(account).clickReallocateButton();

		AccountOverviewPage accounts;

		if (!pageContainsStr(investment)) {
			holdings.clickAddInvestmentButton().simpleSearchByName(investment).selectInvestmentByName(investment)
					.clickAddToPortfolioButton();
			accounts = holdings.setNewAllocationForInvestment(investment, "5")
					.clickRebalancePreviewAndConfirm(investment).confirmHistoryStatus().goToAccountOverviewPage();
		} else {
			holdings.clickCancelEditButton();
			accounts = holdings.goToAccountOverviewPage();
		}

		accounts.simpleSearchByString("Investor, Selenium").selectAllAccounts(true);

		assertTrue(isElementVisible(By.id("gwt-debug-InvestorAccountOverviewView-doTradeBtn")));

		MultiOrderScreenPage multiOrder = accounts.clickTradeButton();

		assertTrue(pageContainsStr("Investor, Selenium"));
		assertTrue(pageContainsStr("Selenium Test"));

		multiOrder.clickSendOrdersToPreTradeQueueForCheckField();

		assertTrue(pageContainsStr("No asset is selected."));
		clickOkButtonIfVisible();

		multiOrder.editISIN("DE0001104610").clickSendOrdersToPreTradeQueueForCheckField();

		assertTrue(pageContainsStr("No asset is selected."));
		clickOkButtonIfVisible();

		multiOrder.editISIN(isin).editBuyValueByAccount(account, "10").editSellValueByAccount(account, "10");

		assertTrue(pageContainsStr("SEHK"));
		assertTrue(pageContainsStr("HKD"));
		assertEquals("0", getTextByLocator(
				By.xpath(".//*[contains(text(),'" + account + "')]//following-sibling::td[5]//input")));

		multiOrder.editSellValueByAccount(account, "10").editBuyValueByAccount(account, "10");

		assertEquals("0", getTextByLocator(
				By.xpath(".//*[contains(text(),'" + account + "')]//following-sibling::td[7]//input")));

		// multiOrder.editOrderQuatityType("Portfolio Percentage Alignment");
		// assertTrue(isElementVisible(By.xpath(".//*[contains(text(),'Buy(%)')]")));
		//
		// multiOrder.editOrderQuatityType("Units").editOrderType("Limit");
		// assertTrue(isElementVisible(By
		// .id("gwt-debug-TradeForSelectedAccountsView-orderTypeBox")));

		multiOrder.editOrderType("Market").editValidFor("GTC");
		assertEquals("Day",
				getSelectedTextFromDropDown(By.id("gwt-debug-TradeForSelectedAccountsView-orderValidityBox")));

		multiOrder.editOrderType("Limit").editValidFor("GTC");
		assertEquals("GTC",
				getSelectedTextFromDropDown(By.id("gwt-debug-TradeForSelectedAccountsView-orderValidityBox")));

		multiOrder.editOrderType("Market").editValidFor("Day").checkAllAccounts(false).editBuyValueByAccount(account,
				"10");

		log("current timestamp: " + timestamp);
		RecentOrdersPage orders = multiOrder.clickSendOrdersToPreTradeQueue();
		timestamp = this.getCurrentTimeInFormat("dd-MMM-yyyy HH:mm");
		if (timestamp.startsWith("0")) {
			timestamp = timestamp.substring(1, timestamp.length());
		}
		orders.goToHistoricalOrders();
		for (int i = 0; i < 6; i++) {
			try {
				waitForElementVisible(By.xpath(".//*[contains(text(),'" + timestamp + "')]"), 10);
			} catch (TimeoutException e) {
				orders.clickRefereshOrderButton().goToHistoricalOrders();
			}
		}

		assertTrue(pageContainsStr(timestamp));
		orders.goToAccountOverviewPage().simpleSearchByString(account).goToAccountHoldingsPageByName(account)
				.clickReallocateButton().setNewAllocationForInvestment(investment, "0")
				.clickRebalancePreviewAndConfirm(investment).confirmHistoryStatus();

		checkLogout();
		this.handleAlert();
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 30);

		LoginPage main3 = new LoginPage(webDriver);
		main3.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD).goToAdminEditPage()
				.editAdminThisField("Advisor Company Module Permission").jumpByKeyAndLoad("561")
				.editModuleToggle(module, false, false).clickSubmitButton();

		checkLogout();
		this.handleAlert();
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 30);

		LoginPage main4 = new LoginPage(webDriver);
		main4.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage();

		assertFalse(isElementVisible(By.id("gwt-debug-InvestorAccountOverviewView-doTradeBtn")));
	}

	@Test
	public void testOrderingInAdvisor() throws InterruptedException {
		LoginPage main = new LoginPage(webDriver);
		String module = "MODULE_ALLOW_TRADE_BUTTON_ON_SUMMARY_OF_ACCOUNTS";
		String account = "";
		String isin = "HK0006000050";
		// the role of the user has to be Advisor/Consultant
		// Advsior company: Either Centiveo or Privalor
		AccountOverviewPage accountPage = main.loginAs("CentiveoAdmin", "CentiveoAdmin").goToAccountOverviewPage();
		account = getTextByLocator(By.xpath("(.//*[@id='gwt-debug-InvestorAccountTable-linkPortfolioName'])[1]"));
		MultiOrderScreenPage multi = accountPage.selectAllAccounts(true).clickTradeButton();

		RecentOrdersPage order = multi.editISIN(isin).clickIncreaseButtonForBuyValueByAccount(account, 1)
				.clickSendOrdersToPreTradeQueue().goToOrderBookPage();

		assertTrue(isElementVisible(By.xpath(".//*[contains(text(),'Recent Orders')]")));

		order.selectAllOrders(true).clickCancelOrderButton();
	}

	@Test
	public void testPresenceOfErrorPanel() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		main.loginAs("PhilStocktonAdmin", "PhilStocktonAdmin");

		webDriver.get(
				Settings.BASE_URL + "#generalUserDetails;userKey=4598;detailType=1;rebuildbdcm=false;rmLastLnk=false");

		try {
			this.handleAlert();
			waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 30);
		} catch (Exception e) {
			// TODO: handle exception
		}
		this.waitForElementVisible(By.xpath(".//*[contains(text(),'Error')]"), Settings.WAIT_SECONDS * 2);
		assertTrue(pageContainsStr("Error"));

	}

	@Test
	public void testBabyTWRRSinceInceptionModuleToggle() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		String module = "MODULE_CALCULATION_BABYTWRR_SHOW_INCEPTION_RETURN";
		String account = "Generali Vision - TWRR";

		// turn off the module toggle
		main.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD).goToAdminEditPage()
				.editAdminThisField(Settings.Advisor_Company_Module_Permission)
				.jumpByKeyAndLoad(Settings.SeleniumTest_Key).editModuleToggle(module, false, false).clickSubmitButton();

		this.checkLogout();

		// check login as SeleniumTest
		LoginPage main2 = new LoginPage(webDriver);

		main2.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage().simpleSearchByString("Generali")
				.goToAccountHoldingsPageByName(account).goToAnalysisPage();

		assertTrue(getTextByLocator(By
				.xpath(".//*[@id='gwt-debug-InvestorModelPortfolioView-benchmarkPanel']//*[@class='floatleftPerformance']"))
						.equals("Performance (1 Y)"));

		this.checkLogout();

		// turn on the module toggle
		LoginPage main3 = new LoginPage(webDriver);

		main3.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD).goToAdminEditPage()
				.editAdminThisField(Settings.Advisor_Company_Module_Permission)
				.jumpByKeyAndLoad(Settings.SeleniumTest_Key).editModuleToggle(module, false, true).clickSubmitButton();

		this.checkLogout();

		// check login as SeleniumTest
		LoginPage main4 = new LoginPage(webDriver);

		main4.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage().simpleSearchByString("Generali")
				.goToAccountHoldingsPageByName(account).goToAnalysisPage();

		assertTrue(getTextByLocator(By
				.xpath(".//*[@id='gwt-debug-InvestorModelPortfolioView-benchmarkPanel']//*[@class='floatleftPerformance']"))
						.equals("Performance Since Inception"));

		this.checkLogout();
	}

	@Test
	public void testSwitchFormForPB() throws Exception {
		String account = "Private Bank (Open Asset Universe)";
		String investment = "";
		LoginPage main = new LoginPage(webDriver);

		HoldingsPage holdings = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage()
				.simpleSearchByString(account).goToAccountHoldingsPageByName(account).clickReallocateButton();

		investment = getTextByLocator(By.xpath(
				".//div[@id='gwt-debug-AllocationEditTableWidgetNormal-allocationTablePanel']//td//div[@class='allocationTableLink']"));

		holdings.setNewAllocationForInvestment(investment, "40")
				.clickRebalancePreviewAndChooseRebalanceTypeAndConfirm("Single Rebalance", investment);

		checkDownloadedFile("32221.TBA");
		holdings.goToDetailsPage().goToEditPageByField("Details").editAccountUpdateSource("Automatic")
				.clickSaveButton_AccountDetail();
		holdings.goToTransactionHistoryPage().editHistoryAction("Download");
		checkDownloadedFile("32221.TBA");

		// .setNewAllocationForInvestment(investment, "0")
	}

	@Test
	public void testBulkSwitchForm() throws InterruptedException {
		String account = "Private Bank (Open Asset Universe)";
		String investment = "";
		LoginPage main = new LoginPage(webDriver);

		HoldingsPage holdings = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage()
				.simpleSearchByString(account).goToAccountHoldingsPageByName(account).clickReallocateButton();
		investment = getTextByLocator(By.xpath(
				".//div[@id='gwt-debug-AllocationEditTableWidgetNormal-allocationTablePanel']//td//div[@class='allocationTableLink']"));
		holdings.setNewAllocationForInvestment(investment, "35")
				.clickRebalancePreviewAndChooseRebalanceTypeAndConfirm("Bulk Rebalance", investment);

		checkDownloadedFile("bulk");
	}

	@Test
	public void testSwitchFormOverride() throws InterruptedException {

		String account = "Generali Vision - TWRR";
		String investment = "Templeton Asian Bond Fund A(acc)EUR";
		String investment2 = "JPMorgan Funds - America Equity Fund A (dist) - USD";
		String overrideField = "Execution Platform Object Override";
		String name_field = "Name:";
		String advisor_field = "Advisor Company:";
		String platform_field = "Execution Platform:";
		String form_field = "Switch Form Type";
		LoginPage main = new LoginPage(webDriver);

		HoldingsPage holdings = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage()
				.simpleSearchByString(account).goToAccountHoldingsPageByName(account);

		if (pageContainsStr("This portfolio is linked to")) {
			holdings.goToDetailsPage();
			clickElement(By.id("gwt-debug-ModelPortfolioSectionPresenter-image"));
			this.waitForElementVisible(By.id("gwt-debug-ModelPortfolioWidget-modelUnLink"), Settings.WAIT_SECONDS);
			clickElement(By.id("gwt-debug-ModelPortfolioWidget-modelUnLink"));
			this.waitForWaitingScreenNotVisible();
			holdings.goToHoldingsPage();
		}

		holdings.clickReallocateButton().clickAddInvestmentButton().simpleSearchByName(investment)
				.selectInvestmentByName(investment).clickAddToPortfolioButtonToHoldingPage();

		int size = getSizeOfElements(By.xpath(".//*[@class='mat-table-body']//*[@class='allocationTableLink']"));
		double percent = 100 / (size - 1);
		String name = "";
		for (int i = 1; i < size; i++) {

			name = getTextByLocator(
					By.xpath("(.//*[@class='mat-table-body']//*[@class='allocationTableLink'])[" + i + "]"));
			if (name != "null" && name != null) {
				holdings.setNewAllocationForInvestment(name, String.valueOf(percent));
				if (i == size - 1) {
					percent += Double
							.valueOf(getTextByLocator(By.xpath(".//*[.='Cash']//parent::td//following-sibling::td[2]"))
									.split("%")[0]);
					holdings.setNewAllocationForInvestment(name, String.valueOf(percent));
				}
			}

		}

		holdings.clickRebalancePreviewAndChooseRebalanceTypeAndConfirm("Single Rebalance", investment);

		waitForElementVisible(By.id("gwt-debug-TabPortfolioTransactionHistoryView-contentPanel"), 60);

		checkDownloadedFile("32181.TBA");

		checkLogout();
		this.handleAlert();

		LoginPage main2 = new LoginPage(webDriver);
		main2.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD);

		AdminEditPage admin = main2.goToAdminEditPage();

		admin.goToAdminEditPage();
		// Execution Platform Object Override
		admin.editAdminThisField(overrideField).clickNewButtonToCreateNewToken()
				.inputValueToCertainField(name_field, "Friends Provident Test")
				.selectOptionForCertainField(advisor_field, "SeleniumTest")
				.selectOptionForCertainField(platform_field, "Generali Vision - Advisor ABC")
				.selectOptionForCertainField(form_field, "SwitchForm Friends Provident HK").clickSubmitButton();
		clickOkButtonIfVisible();
		checkLogout();
		this.handleAlert();
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 30);

		LoginPage main3 = new LoginPage(webDriver);
		HoldingsPage holdings2 = main3.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage()
				.simpleSearchByString(account).goToAccountHoldingsPageByName(account).clickReallocateButton()
				.clickAddInvestmentButton().simpleSearchByName(investment2).selectInvestmentByName(investment2)
				.clickAddToPortfolioButtonToHoldingPage();

		int size2 = getSizeOfElements(By.xpath(".//*[@class='mat-table-body']//*[@class='allocationTableLink']"));
		int percent2 = 100 / (size2 - 1);

		for (int i = 1; i < size2; i++) {

			name = getTextByLocator(
					By.xpath("(.//*[@class='mat-table-body']//*[@class='allocationTableLink'])[" + i + "]"));
			if (name != "null" && name != null) {
				holdings.setNewAllocationForInvestment(name, String.valueOf(percent2));
				if (i == size2 - 1) {
					percent2 += Double
							.valueOf(getTextByLocator(By.xpath(".//*[.='Cash']//parent::td//following-sibling::td[2]"))
									.split("%")[0]);
					holdings.setNewAllocationForInvestment(name, String.valueOf(percent));
				}
			}

		}
		holdings2.clickRebalancePreviewAndChooseRebalanceTypeAndConfirm("Single Rebalance", investment2);

		waitForElementVisible(By.id("gwt-debug-TabPortfolioTransactionHistoryView-contentPanel"), 30);

		checkDownloadedFile("32181.TBA");

	}

	@Ignore
	public void testCheckExecutionModeByManualRebalance() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);
		String account = "Rebalance Manual Account";
		String investment = "Goldman Sachs Group, Inc.";

		HoldingsPage holding = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage()
				.simpleSearchByString(account).goToAccountHoldingsPageByName(account).clickReallocateButton();

		if (pageContainsStr(investment)) {
			holding.setNewAllocationForInvestment(investment, "0").clickRebalancePreviewAndConfirm(investment)
					.goToHoldingsPage().clickReallocateButton();
		}

		RecentOrdersPage orders = holding.clickAddInvestmentButton().simpleSearchByName(investment)
				.selectInvestmentByName(investment).clickAddToPortfolioButtonToHoldingPage()
				.setNewAllocationForInvestment(investment, "20").clickRebalancePreviewAndConfirm(investment)
				.goToRecentOrderPage();

		try {
			waitForElementVisible(By.xpath(
					"//td[div[contains(text(),'" + investment + "')]]/following-sibling::td[button]//button[.='Lock']"),
					15);

			orders.clickLockButtonOfTheOpenOrder(investment).clickEnterButtonOfTheOrder(investment)
					.editPriceAndClickConfirmOrderFilledButton(investment, "10").goToHistoricalOrders();

			waitForElementVisible(By.xpath("//td[div[contains(text(),'" + investment
					+ "')]]/following-sibling::td[button]//button[.='Filled fully']"), 15);

			assertTrue(isElementVisible(By.xpath("//td[div[contains(text(),'" + investment
					+ "')]]/following-sibling::td[button]//button[.='Filled fully']")));

			orders.goToHoldingsPage().clickReallocateButton().setNewAllocationForInvestment(investment, "0")
					.clickRebalancePreviewAndConfirm(investment);
		} catch (TimeoutException e) {

		}

	}

	@Ignore
	public void testCheckExecutionModeByReconfirmTrade() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);
		String account = "Rebalance Manual Account";
		String investment = "Goldman Sachs Group, Inc.";

		RecentOrdersPage orders = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage()
				.simpleSearchByString(account).goToAccountHoldingsPageByName(account).clickTradeButton()
				.editInvestmentOrderAssetToBuy().simpleSearchByName(investment)
				.selectInvestmentByNameForInvestmentOrder(investment).editInvestmentOrderAmount("100")
				.clickPreviewButtonForInvestmentOrder();

		if (!pageContainsStr("Portfolio switch cannot be process")) {
			waitForElementVisible(By.xpath(
					"//td[contains(text(),'" + investment + "')]/following-sibling::td[button]//button[.='Lock']"), 15);

			orders.clickLockButtonOfTheOpenOrder(investment).clickEnterButtonOfTheOrder(investment)
					.editPriceAndClickConfirmOrderFilledButton(investment, "10");

			// wait(5);
			orders.goToHistoricalOrders();
			assertTrue(isElementVisible(By.xpath(
					"//td[contains(text(),'" + investment + "')]//following-sibling::td[div[.='Filled fully']]")));

		} else {

		}
	}

	@Test
	public void testCheckAdvancedDialogEdit() throws InterruptedException {
		LoginPage main = new LoginPage(webDriver);
		String account = "Selenium Test";
		String investment = "Goldman Sachs Group, Inc.";

		HoldingsPage holding = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage()
				.simpleSearchByString(account).goToAccountHoldingsPageByName(account);

		if (pageContainsStr("This portfolio is linked")) {
			holding.goToDetailsPage().goToEditPageByField("Model Portfolio");
			clickElement(By.id("gwt-debug-ModelPortfolioWidget-modelUnLink"));
			main.goToHoldingsPage();
		}

		if (pageContainsStr(investment)) {
			holding.clickReallocateButton().setNewAllocationForInvestment(investment, "0")
					.clickRebalancePreviewAndConfirm(investment).goToTransactionHistoryPage().confirmHistoryStatus()
					.goToHoldingsPage();
		}

		RecentOrdersPage orders = holding.clickTradeButton().editInvestmentOrderAssetToBuy()
				.simpleSearchByName(investment).selectInvestmentByNameForInvestmentOrder(investment)
				.editInvestmentOrderSide("Buy").editInvestmentOrderAmount("1").clickPreviewButtonForInvestmentOrder();

		this.waitForWaitingScreenNotVisible();

		orders.goToHistoricalOrders();
		try {
			for (int i = 0; i < 5; i++) {
				orders.clickRefereshOrderButton();
			}
			waitForElementVisible(By.xpath(
					"//td[div[contains(text(),'" + investment + "')]]//following-sibling::td[div[.='Filled fully']]"),
					30);
		} catch (TimeoutException e) {
			this.refreshPage();
			orders.goToHistoricalOrders();
			for (int i = 0; i < 5; i++) {
				orders.clickRefereshOrderButton();
			}
		}
		assertTrue(isElementVisible(By.xpath(
				"//td[div[contains(text(),'" + investment + "')]]//following-sibling::td[div[.='Filled fully']]")));
		this.refreshPage();
		orders.goToHoldingsPage().clickReallocateButton().setNewAllocationForInvestment(investment, "0")
				.clickRebalancePreviewAndConfirm(investment);

	}

	@Ignore
	public void checkExecutionModeReconfirm_Rebalance() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		String account = "Rebalance Reconfim Accocunt";
		String investment = "GOLDMAN SACHS GROUP INC";
		String format = "d-MMM-yyyy HH:mm";

		RecentOrdersPage orders = ((HoldingsPage) main.loginAs(Settings.USERNAME, Settings.PASSWORD)
				.goToAccountOverviewPage().simpleSearchByString(account).goToAccountHoldingsPageByName(account)
				.clickReallocateButton().clickAddInvestmentButton().simpleSearchByName(investment)
				.selectInvestmentByName(investment).clickAddToPortfolioButton())
						.setNewAllocationForInvestment(investment, "10").clickRebalancePreviewAndConfirm(investment)
						.goToRecentOrderPage();

		this.refreshPage();
		String currentTime = getCurrentTimeInFormat(format) + " (UTC+8)";

		waitForElementVisible(By.xpath("//*[contains(text(),'Pre Trade Queue')]"), 30);

		currentTime = getTextByLocator(By.xpath(
				"(.//*[@id='gwt-debug-OrdersTableWidgetView-openOrdersFlexTable']/div/table/tbody/tr/td[1])[1]"));

		log("current: " + currentTime);
		for (int i = 0; i < Settings.ATTEMPT_LOOPING_NUMBER; i++) {
			if (!isElementDisplayed(By.xpath(
					".//*[contains(@id,'openOrdersFlexTable') and contains(text(),'No open orders found.')]]"))) {

				orders.clickConfirmButtonOfTheOpenOrder(currentTime);
				// try {
				//
				// } catch (Exception e) {
				// // TODO: handle exception
				// }

				if (isElementPresent(By.xpath(
						".//*[contains(@id,'openOrdersFlexTable') and contains(text(),'No open orders found.')]"))) {
					break;

				}

			}

		}

		HoldingsPage holdings = orders.goToTransactionHistoryPage().confirmHistoryStatus().goToHoldingsPage();

		assertTrue(pageContainsStr(investment));

		// delete the asset2
		holdings.clickReallocateButton().setNewAllocationForInvestment(investment, "0")
				.clickRebalancePreviewAndConfirm(investment).goToRecentOrderPage();

		waitForElementVisible(By.xpath("//*[contains(text(),'Submission Time')]"), 30);

		currentTime = getTextByLocator(By.xpath(
				"(.//*[@id='gwt-debug-OrdersTableWidgetView-openOrdersFlexTable']/div/table/tbody/tr/td[1])[1]"));

		orders.clickConfirmButtonOfTheOpenOrder(currentTime);

	}
}
