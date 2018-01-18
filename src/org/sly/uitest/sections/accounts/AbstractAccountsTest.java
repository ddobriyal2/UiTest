package org.sly.uitest.sections.accounts;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.sly.uitest.framework.AbstractTest;

/**
 * Abstract base class for common functions to test accounts.
 * 
 * @author Julian Schillinger
 * @date : Jul 16, 2014
 * @company Prive Financial
 */
public class AbstractAccountsTest extends AbstractTest {

	/** The name of the strategies expected to be present in the portfolio. */
	final static String[] EXISTING_STRATEGY_NAMES = { "JPMorgan Japan", "500 Benchmark vFund",
			"US Treasury Bond Benchmark Strategy" };

	/**
	 * Clicks into the sample account.
	 * 
	 * @param name
	 *            The name of the account to click into. Function uses first
	 *            occurence.
	 */
	protected void goInAccount(String name) {
		assertTrue(pageContainsStr(name));
		clickElement(By.xpath("//a[@id='gwt-debug-InvestorAccountTable-linkPortfolioName' and .='" + name + "']"));
	}

	/**
	 * Clicks into the default sample account ("Selenium Test").
	 */
	protected void goInAccount() {
		goInAccount("Selenium Test");
	}
	//
	// protected void init() throws InterruptedException {
	// // init
	// webDriver.get(loginUrl);
	//
	// // check and login
	// checkLogin();
	//
	// // click into account
	// goInAccount();
	// }

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
