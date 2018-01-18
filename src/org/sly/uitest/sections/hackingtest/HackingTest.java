package org.sly.uitest.sections.hackingtest;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.abstractpage.MenuBarPage;
import org.sly.uitest.settings.Settings;

public class HackingTest extends AbstractTest {

	@Test
	public void testInvestorCannotSeeAdvisorPage() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		main.loginAs(Settings.INVESTOR_USERNAME, Settings.INVESTOR_PASSWORD);
		this.waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), Settings.WAIT_SECONDS);
		log("Investor Cannot See Advisor Page: In");

		webDriver.get(Settings.BASE_URL + "#settings;fakeKey=testKey");

		handleAlert();
		this.waitForElementVisible(By.xpath(".//*[.='Error']"), Settings.WAIT_SECONDS);
		assertTrue(pageContainsStr("Error"));
		// assertTrue(pageContainsStr("The current user is not an advisor.
		// Please contact your system administrator."));

		log("Investor Cannot See Advisor Page: Checked");
	}

	@Test
	public void testAdvisorCannotSeeAdvisorAdminPage() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		main.loginAs(Settings.CONSULTANT_USERNAME, Settings.CONSULTANT_PASSWORD);

		log("Advisor Cannot See Advisor Admin Page: In");
		this.waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), Settings.WAIT_SECONDS);

		webDriver.get(Settings.BASE_URL + "#settings;fakeKey=testKey");

		this.waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), Settings.WAIT_SECONDS);

		handleAlert();

		assertTrue(pageContainsStr("Error"));
		assertTrue(pageContainsStr("The current user is not a super user."));

		log("Advisor Cannot See Advisor Admin Page: Checked");

	}

	@Test
	public void testAdvisorCannotSeeSystemAdminPage() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		main.loginAs(Settings.USERNAME, Settings.PASSWORD);
		this.waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), Settings.WAIT_SECONDS);
		log("Advisor Cannot See System Admin Page: In");

		webDriver.get(Settings.BASE_URL + "#editusers");

		this.waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), Settings.WAIT_SECONDS);

		handleAlert();

		assertTrue(pageContainsStr("Error"));
		assertTrue(pageContainsStr("The current user is not a system administrator."));

		log("Advisor Cannot See System Admin Page: Checked");

	}

	@Test
	public void testInvestorCannotSeeAnotherCompanyInvestor() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		MenuBarPage menu = main.loginAs(Settings.USERNAME, Settings.PASSWORD);
		// wait(Settings.WAIT_SECONDS);
		this.waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), Settings.WAIT_SECONDS);
		log("Advisor Cannot See Another Company Investor Page: In");

		webDriver.get(
				Settings.BASE_URL + "#generalUserDetails;userKey=4598;detailType=1;rebuildbdcm=false;rmLastLnk=false");

		wait(Settings.WAIT_SECONDS);

		try {
			handleAlert();
		} catch (Exception e) {
			// TODO: handle exception
		}

		assertTrue(pageContainsStr("Error"));
		// assertTrue(pageContainsStr("You have no permission to access this
		// user details"));

		log("Advisor Cannot See Another Company Investor Page: Checked");

	}

	@Test
	public void testInvestorCannotSeeAnotherComapnyAccount() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		MenuBarPage menu = main.loginAs(Settings.USERNAME, Settings.PASSWORD);
		this.waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), Settings.WAIT_SECONDS);
		log("Advisor Cannot See Another Comapany Account Page: In");

		webDriver.get(Settings.BASE_URL + "#portfolioOverviewDetails;investorAccountKey=11401;valueType=2");

		// wait for the alert
		wait(Settings.WAIT_SECONDS);

		handleAlert();

		// assertTrue(pageContainsStr("Access is Denied"));
		waitForElementVisible(By.id("gwt-debug-ErrorScreenView-errorPanel"), 30);
		assertTrue(pageContainsStr("No Access Permission"));

		log("Advisor Cannot See Another Comapany Account Page: Checked");

	}

}
