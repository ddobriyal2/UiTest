package org.sly.uitest.sections.accounts;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.abstractpage.MenuBarPage;
import org.sly.uitest.pageobjects.clientsandaccounts.HoldingsPage;
import org.sly.uitest.settings.Settings;

/**
 * 
 * Tests functionality around the compare screen for a portfolio/account. <br>
 * <br>
 * Test is described here: <a href=
 * "https://docs.google.com/a/wismore.com/spreadsheets/d/1WEEiIIiVHNix_W5frGNpT53Rlv8l_g5XkyivjkjsIj4/edit#gid=0"
 * >here</a>
 * 
 * @author Julian Schillinger
 * @date : Jul 23, 2014
 * @company Prive Financial
 */
public class PortfolioCompareTest extends AbstractTest {

	@Test
	public void testCompareTab() throws Exception {
		String account = "Private Banking Test";
		// Init

		LoginPage main = new LoginPage(webDriver);
		MenuBarPage mbPage = main.loginAs(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD);
		// wait(5);

		HoldingsPage holdings = mbPage.goToAccountOverviewPage().simpleSearchByString(account)
				.goToAccountHoldingsPageByName(account).goToHoldingsPage();

		// wait(10);
		// Find strategy names from holdings tab
		ArrayList<String> strategyNames = getStrategyNamesFromAccountsHoldingsTab();
		log("Found following startegy names on holdings page: " + strategyNames);

		// change to compare tab
		holdings.goToComparePage();
		waitForWaitingScreenNotVisible();
		// make sure strategies show up in table in compare tab
		// managerCompareAllocationTable, textInTable.contains(strategyName)
		// String textInTable = waitGet(By.className("allocationTableLink"))
		// .getText();
		// System.out.print(textInTable);
		for (String strategyName : strategyNames) {
			System.out.println(strategyName);
			assertTrue("Strategy not found in table: " + strategyName, pageContainsStr(strategyName));
		}

	}

	/**
	 * Returns the strategy names in the accounts holdings tab. Expects the
	 * holdings tab to be already open (ie holdings table is already visible).
	 * Does not work in rebalance mode.
	 * gwt-debug-AllocationEditTableWidgetNormal-allocationTablePanel
	 */
	protected ArrayList<String> getStrategyNamesFromAccountsHoldingsTab() {
		ArrayList<String> strategyNames = new ArrayList<String>();
		List<WebElement> strategyLinks = waitGet(By.id("gwt-debug-PortfolioAllocationView-allocationTablePanel"))
				.findElements(By.className("allocationTableLink"));
		for (WebElement strategyLink : strategyLinks) {
			strategyNames.add(strategyLink.getText());
			// System.out.println(strategyNames);
		}
		return strategyNames;
	}
}
