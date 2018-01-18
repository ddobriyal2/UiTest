package org.sly.uitest.sections.accounts;

import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.openqa.selenium.By;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.clientsandaccounts.HoldingsPage;
import org.sly.uitest.settings.Settings;

/**
 * Tests functionality around switching from one account to another using the
 * drop down. <br>
 * <br>
 * Test is described here: <a href=
 * "https://docs.google.com/a/wismore.com/spreadsheets/d/1WEEiIIiVHNix_W5frGNpT53Rlv8l_g5XkyivjkjsIj4/edit#gid=0"
 * >here</a>
 * 
 * @author Julian Schillinger
 * @date : Aug 4, 2014
 * @company Prive Financial
 */
public class PortfolioChangeTest extends AbstractTest {

	/**
	 * Test plan (
	 * "Ensure changing account via account drop down leads to different account"
	 * Please ignore this. )
	 */

	@Ignore
	public void testAccountSwitchViaDrodown() throws Exception {

		LoginPage main2 = new LoginPage(webDriver);
		HoldingsPage holdingsPage = main2.loginAs(Settings.CONSULTANT_USERNAME, Settings.CONSULTANT_PASSWORD)
				.goToAccountOverviewPage().simpleSearchByString("Selenium Test")
				.goToAccountHoldingsPageByName("Selenium Test");

		/*
		 * Do test
		 */
		// find current account value

		String accountValue1 = this
				.getTextByLocator(By.xpath("//*[@debugid='PortfolioOverviewView-portfolioValueLabel'][1]"));
		log("Account Value 1: " + accountValue1);

		// switch to other account
		selectFromDropdown(waitGet(By.className("accountListDropDown")), "Test Account 2 (Don't Rebalance)");

		// find acocunt value after switch to account 2
		String accountValue2 = this
				.getTextByLocator(By.xpath("//*[@debugid='PortfolioOverviewView-portfolioValueLabel'][1]"));
		log("Account Value 2: " + accountValue2);

		// make sure account value is 1M and different from previous one
		assertTrue("Account value after switching to account 2 should be different from before.",
				!accountValue1.equals(accountValue2));
		assertTrue("Account of account 2 should be USD 1,000,000.00.", accountValue2.equals("1,000,000.00 USD"));

	}

}
