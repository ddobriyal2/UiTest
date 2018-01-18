package org.sly.uitest.sections.reporting.portfolio;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.abstractpage.MenuBarPage;
import org.sly.uitest.settings.Settings;

/**
 * Tests cashflow overview search.
 * 
 * @author Harry Chin
 * @date : Jul 14, 2014
 * @company Prive Financial
 */
public class GenerationTest extends AbstractTest {

	@Test
	public void testGeneration() throws Exception {

		setScreenshotSuffix(GenerationTest.class.getSimpleName());

		LoginPage main = new LoginPage(webDriver);

		main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToCashflowOverviewPage();

		// Show advanced search panel
		clickElement(By.id("gwt-debug-CashflowListView-advancedSearchBtn"));

		// Start date
		sendKeysToElement(By.id("gwt-debug-AdvancedSearchPanel-StartDate"), "14-Jul-2010");

		// End date
		sendKeysToElement(By.id("gwt-debug-AdvancedSearchPanel-EndDate"), "14-Jul-2014");

		// Click search button
		clickElement(By.id("gwt-debug-AdvancedSearchPanel-searchButton"));

		// wait(1);
		//
		// screenshot();

		assertTrue(!pageContainsStr("No results found."));

	}

	@Test
	public void testRemoveGeneratedReportFromBackgroundProcess() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		String select = "Account List";
		String file = "ClientList." + getCurrentTimeInFormat("yyyyMMdd HH:mm") + getRandName();

		MenuBarPage process = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage()
				.exportSearchResult(select, file).goToProcess();

		boolean isMaterial = isElementVisible(By.xpath(".//*[@id='page-wrapper']/div[1]/nav/div/a/i"));

		if (isMaterial) {
			waitForElementVisible(By.xpath(".//*[contains(text(),'" + file + "')]"), 60);
			assertTrue(pageContainsStr(file + ".xls"));
		} else {
			waitForTextVisible(By.xpath("html/body/div[16]/div/div/table[1]/tbody/tr[1]/td[2]/div"), file + ".xls");
		}

		process.removeExportFile(file + ".xls");

		wait(2 * Settings.WAIT_SECONDS);

		assertFalse(pageContainsStr(file));

	}
}
