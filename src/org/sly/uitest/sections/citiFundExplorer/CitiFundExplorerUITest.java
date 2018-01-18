package org.sly.uitest.sections.citiFundExplorer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.EOFException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.admin.AdminEditPage;
import org.sly.uitest.settings.Settings;


/**
 * Test plan : <a href =
 * "https://docs.google.com/spreadsheets/d/1Q_OAcnzj4RBkV3AXowSx58Tbq3T8Alo6rwcX3W6kMhw/edit#gid=0"
 * >Here<a>
 * 
 * @author Benny Leung
 * @date Aug 18 ,2016
 * @company Prive Financial
 */
public class CitiFundExplorerUITest extends AbstractTest {

	String UrlForHongKongPlatform = Settings.BASE_URL
			+ "?locale=en&viewMode=BASIC#investmentList;P1=ce5fdc0e-0cdb-4e13-8ca9-11655d159e7c;P3=HKG";

	String UrlForSingaporeIPBPlatform = Settings.BASE_URL
			+ "?locale=en&viewMode=BASIC#investmentList;P1=ce5fdc0e-0cdb-4e13-8ca9-11655d159e7c;P3=SGPIPB";

	String UrlForSingaporeGCGPlatform = Settings.BASE_URL
			+ "?locale=en&viewMode=BASIC#investmentList;P1=ce5fdc0e-0cdb-4e13-8ca9-11655d159e7c;P3=SGPGCG";

	String UrlForThailandPlatform = Settings.BASE_URL
			+ "?locale=en&viewMode=BASIC#investmentList;P1=ce5fdc0e-0cdb-4e13-8ca9-11655d159e7c;P3=THA";
	
	/*Commentsss*/
	
	@Test
	public void testDifferentRegionPlatform() {

		// test case #1 - 4

		try{
			System.out.println("TC 1 : City Fund Explorer UI Tests Started ! ");
		webDriver.get(UrlForHongKongPlatform);	
		waitForElementVisible(By.id("gwt-debug-MyMainBasicView-logoPanel"), 30);
		System.out.println("User can login to"+UrlForHongKongPlatform+"Sucessfully");
		}
		catch(Exception e){  
			System.out.println(e);
		}
		
		System.out.println("TC 1 Ended !");
		
		
		try{
			wait(3);
		System.out.println("TC 2 : City Fund Explorer UI Tests Started ! ");
		webDriver.get(UrlForSingaporeIPBPlatform);
		waitForElementVisible(By.id("gwt-debug-MyMainBasicView-logoPanel"), 30);
		System.out.println("User can login to"+UrlForSingaporeIPBPlatform+"Sucessfully");
		}
		catch(Exception e){  
			System.out.println(e);
		}
		System.out.println("TC 2 Ended !");
		
		
		try { wait(3);
		System.out.println("TC 3 : City Fund Explorer UI Tests Started ! ");
		webDriver.get(UrlForSingaporeGCGPlatform);
		waitForElementVisible(By.id("gwt-debug-MyMainBasicView-logoPanel"), 30);
		System.out.println("User can login to"+UrlForSingaporeGCGPlatform+"Sucessfully");
		}
		catch(Exception e){  
			System.out.println(e);
		}
		System.out.println("TC 3 Ended !");
		
		
		try{
		wait(3);
		System.out.println("TC 4 : City Fund Explorer UI Tests Started ! ");
		webDriver.get(UrlForThailandPlatform);
		waitForElementVisible(By.id("gwt-debug-MyMainBasicView-logoPanel"), 30);
		System.out.println("User can login to"+UrlForThailandPlatform+"Sucessfully");
		}
		catch(Exception e){  
			System.out.println(e);
		}
		System.out.println("TC 4 Ended !");
	} 

	// test case #5 
	@Test
	public void testCompareFund() throws InterruptedException {

		System.out.println("TC 5 : City Fund Explorer UI Tests Started ! ");
	init();
	System.out.println("TC 5 : City Fund Explorer UI Tests Started ! ");
		// record first three investments
		String investment1 = getTextByLocator(By.xpath("(.//*[@id='gwt-debug-ManagerListItem-strategyName'])[1]"));

		String investment2 = getTextByLocator(By.xpath("(.//*[@id='gwt-debug-ManagerListItem-strategyName'])[2]"));

		String investment3 = getTextByLocator(By.xpath("(.//*[@id='gwt-debug-ManagerListItem-strategyName'])[3]"));

		ArrayList<String> investmentList = new ArrayList<String>();

		investmentList.add(investment1);
		investmentList.add(investment2);
		investmentList.add(investment3);

		new Actions(webDriver).moveToElement(webDriver.findElement(By.id("gwt-debug-ManagerListItem-strategyName")))
				.perform();

		// select one invesment only
		clickElement(By.xpath(
				"(.//*[@id='gwt-debug-ManagerListItem-strategyName'])[1]/parent::div/parent::td/preceding-sibling::td//button/span"));

		clickElement(By.xpath(".//*[@class='FundExplorerActionColumnTable']//a[.='Select Investment']"));
		// compare investment
		clickElement(By.id("gwt-debug-ManagerStrategySelectionWidget-compareAllBtn"));
		// user would see the message and cannot see the comparison
		assertTrue(pageContainsStr("You need to select at least two products to run a comparison."));
		clickOkButtonIfVisible();

		clickElement(By.id("gwt-debug-ManagerStrategySelectionWidget-clearAllBtn"));
		waitForElementVisible(By.id("gwt-debug-ManagerListItem-strategyName"), 30);

		// select the investments and make sure they are not from the below
		// table
		for (int i = 0; i < 3; i++) {
			Integer looping = i + 1;
			waitForElementVisible(By.xpath("(.//*[@id='gwt-debug-ManagerListItem-strategyName'])[" + looping
					+ "]/parent::div/parent::td/preceding-sibling::td//button/span"), Settings.WAIT_SECONDS);

			clickElement(By.xpath("(.//*[@id='gwt-debug-ManagerListItem-strategyName'])[" + looping
					+ "]/parent::div/parent::td/preceding-sibling::td//button/span"));

			clickElement(By.xpath(".//*[@class='FundExplorerActionColumnTable']//a[.='Select Investment']"));
			assertTrue(!isElementVisible(By
					.xpath(".//*[@id='gwt-debug-ManagerListItem-strategyName' and .=" + investmentList.get(i) + "]")));
		}
		// Click Compare button
		clickElement(By.id("gwt-debug-ManagerStrategySelectionWidget-compareAllBtn"));
		waitForElementVisible(By.id("gwt-debug-ManagerCompareView-PerformanceChart"), 30);

		// Make sure the compare chart appears in the compare page
		assertTrue(isElementVisible(By.id("gwt-debug-ManagerCompareView-PerformanceChart")));
		// Make sure those investments appear in the compare page
		assertTrue(pageContainsStr(investment1));
		assertTrue(pageContainsStr(investment2));
		assertTrue(pageContainsStr(investment3));
		// wait(2);
		// back to fund list
		clickElement(By.xpath(".//*[@class='thisBackButton']"));
		waitForElementVisible(By.id("gwt-debug-ManagerListItem-strategyName"), 30);
		// clear selected investments
		clickElement(By.id("gwt-debug-ManagerStrategySelectionWidget-clearAllBtn"));
		waitForElementNotVisible(By.id("gwt-debug-ManagerListWidgetView-selectedPairWidget"), Settings.WAIT_SECONDS);
		assertFalse(isElementDisplayed(By.id("gwt-debug-ManagerListWidgetView-selectedPairWidget")));
		
		System.out.println("TC 5 Ended !");
	}
// Test case #6
	@Test
	public void testSnapshotPopup() throws InterruptedException {

		init();
		System.out.println("TC 6 : City Fund Explorer UI Tests Started ! ");
		// click on first investment
		clickInvestment("1");
		System.out.println("TC 6 Ended !");

	}
	
	
	// Test case #7
	@Test
	public void testOnlineFactsheet() throws InterruptedException {
		init();
		System.out.println("TC 7 : City Fund Explorer UI Tests Started ! ");
		clickInvestment("1");
		waitForElementVisible(By.id("gwt-debug-ManagerDisplayWidgetView-strategyName"), 30);

		assertTrue(isElementVisible(By.id("gwt-debug-ManagerDisplayWidgetView-strategyDescription")));
		assertTrue(isElementVisible(By.id("gwt-debug-ManagerDisplayWidgetView-chartPanel")));
		assertTrue(isElementVisible(By.id("gwt-debug-ManagerDisplay-FactsContainer")));
		assertTrue(isElementVisible(By.id("gwt-debug-ManagerDisplay-DetialIdentifierSection")));
		assertTrue(isElementVisible(By.id("gwt-debug-ManagerDisplay-CategoryContainer")));
		assertTrue(isElementVisible(By.id("gwt-debug-ManagerDisplay-DebugContainer")));
		assertTrue(isElementVisible(By.id("gwt-debug-ManagerDisplayWidgetView-riskRainbowDrawDown")));
		assertTrue(isElementVisible(By.id("gwt-debug-ManagerDisplayWidgetView-riskRainbowNegMonths")));
		assertTrue(isElementVisible(By.id("gwt-debug-ManagerDisplayWidgetView-riskRainbowVol")));
		assertTrue(isElementVisible(By.id("gwt-debug-ManagerDisplayWidgetView-riskRainbowDownVol")));
		assertTrue(isElementVisible(By.id("gwt-debug-ManagerDisplayWidgetView-assetClassChartHoldingStatisticsPanel")));
		assertTrue(isElementVisible(By.id("gwt-debug-ManagerDisplayWidgetView-top10HoldingTablePanel")));
		assertTrue(isElementVisible(By.id("gwt-debug-ManagerDisplay-docPanel")));
		assertTrue(isElementVisible(By.xpath(".//*[@class='managerDisplaySubHeader' and .=' Return Analysis ']")));

		System.out.println("TC 7 Ended !");
	}

	// Test case #8
	@Test
	public void testSearchInvestment() throws InterruptedException {
		init();
		
		// search for keyword "government"
		System.out.println("TC 8 : City Fund Explorer UI Tests Started ! ");
		sendKeysToElement(By.id("gwt-debug-ManagerListWidgetView-searchBox"), "government");
		clickElement(By.id("gwt-debug-ManagerListWidgetView-searchBtn"));
		waitForElementVisible(By.id("gwt-debug-ManagerListItem-strategyName"), 20);
		assertTrue(pageContainsStr("Franklin U.S. Government "));

		clickElement(By.id("gwt-debug-ManagerListWidgetView-clearImg"));
		waitForElementVisible(By.id("gwt-debug-ManagerListItem-strategyName"), 20);
		// Filter: Asset Class
		filterResult("1", "Europe Dev.");
		filterResult("2", "USD");
		filterResult("2", "EUR");
		System.out.println("TC 8 Ended !");
	}
	
	// Test case #9
	@Test
	public void testHelpOverlay() throws InterruptedException {
		
		System.out.println("TC 9 : City Fund Explorer UI Tests Started ! ");
		String fieldName = "Execution Platform Company Module Permission";
		String module1 = "MODULE_SHOW_HELP_ICON";
		String module2 = "MODULE_SHOW_HELP_OVERLAY_ICON";
		LoginPage main = new LoginPage(webDriver);
		AdminEditPage adminEdit = main.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD)
				.goToAdminEditPage().editAdminThisField(fieldName).jumpByKeyAndLoad("611")
				.editSingleModuleToggle(module1, true).editSingleModuleToggle(module2, true).clickSubmitButton();
		checkLogout();
		handleAlert();

		webDriver.get(UrlForHongKongPlatform);
		checkHelpOverlay();

		clickElement(By.xpath(".//*[@id='gwt-debug-HelpOverLayView-showHelpOverlayNow']//i"));
		checkHelpOverlay();
		System.out.println("TC 9 Ended !");
	}

	// Test case #11
	@Test
	public void testDisclaimer() throws InterruptedException {
		init();
		System.out.println("TC 11 : City Fund Explorer UI Tests Started ! ");
		waitForElementVisible(By.id("gwt-debug-ManagerListWidgetView-returnCalExplainLabel"), 30);
		String disclaimer = "* Fund performance is calculated on a NAV to NAV basis in the relevant share class currency with dividends reinvested. The timeseries is adjusted for split events (where data is available)";
		assertTrue(pageContainsStr(disclaimer));
		System.out.println("TC 11 Ended !");
	}

	// Test case #14
	@Test
	public void testAdvancedSearch() throws InterruptedException {
		init();
		System.out.println("TC 14 : City Fund Explorer UI Tests Started !");
		waitForElementVisible(By.id("gwt-debug-ManagerListWidgetView-advancedSearchBtn"), 30);
		this.waitForElementVisible(By.id("gwt-debug-ManagerListItem-strategyName"), 30);
		clickElement(By.id("gwt-debug-ManagerListWidgetView-advancedSearchBtn"));
		clickElement(By.id("gwt-debug-AdvancedSearchPanel-imgClose"));

		advancedSearch("Investment Name", "JPMorgan", "JPMorgan");
		advancedSearch("Region", "Europe", "Europe");
		advancedSearch("ISIN", "HK", "JPMorgan");
		// advancedSearch("Citi Fund Code", "JFADO", "JPMorgan");
		System.out.println("TC 14 Ended !");
	}

	// test case #15
	@Test
	public void testAddToFavorite() throws InterruptedException {
		init();
		System.out.println("TC 15 : City Fund Explorer UI Tests Started !");
		ArrayList<String> favouriteInvestmentList = new ArrayList<String>();

		for (int i = 1; i <= 3; i++) {

			favouriteInvestmentList
					.add(getTextByLocator(By.xpath("(.//*[@id='gwt-debug-ManagerListItem-strategyName'])[" + i + "]")));

			clickElement(By.xpath("(.//*[@id='gwt-debug-ManagerListItem-strategyName'])[" + i
					+ "]/parent::div/parent::td/preceding-sibling::td//button/span"));

			clickElement(By.xpath(".//*[@class='FundExplorerActionColumnTable']//a[.='Add to Favorite']"));

		}
		favouriteInvestmentList.remove(removeFavourite(favouriteInvestmentList));
		favouriteInvestmentList.remove(removeFavourite(favouriteInvestmentList));
		favouriteInvestmentList.remove(removeFavourite(favouriteInvestmentList));
		System.out.println("TC 15 Ended !");
	}

	// test case #16
	@Test
	public void testNumberOfSearchResult() throws InterruptedException {
		init();
		System.out.println("TC 16 : City Fund Explorer UI Tests Started !");
		// search "JP" in simple search box
		sendKeysToElement(By.id("gwt-debug-ManagerListWidgetView-searchBox"), "JP");
		clickElement(By.id("gwt-debug-ManagerListWidgetView-searchBtn"));
		waitForElementVisible(By.id("gwt-debug-ManagerListItem-strategyName"), 20);

		// check for 15
		changeNumberOfSearchResult("15");
		// check for 25
		changeNumberOfSearchResult("25");
		// check for 50
		changeNumberOfSearchResult("50");
		System.out.println("TC 16 Ended !");
	}

	// test case #17
	@Test
	public void testChangePages() throws InterruptedException {
		init();
		System.out.println("TC 17 : City Fund Explorer UI Tests Started !");
		// search "JP" in simple search box
		sendKeysToElement(By.id("gwt-debug-ManagerListWidgetView-searchBox"), "JP");

		clickElement(By.id("gwt-debug-ManagerListWidgetView-searchBtn"));

		waitForElementVisible(By.id("gwt-debug-ManagerListItem-strategyName"), 20);

		changeNumberOfSearchResult("25");

		String result = getTextByLocator(By.id("gwt-debug-ManagerListWidgetView-pageInfoLabel"));
		// get total number from string "1 - 15 of 101 Results"
		double totalNumber = Double.valueOf(result.split("of ")[1].split(" Results")[0]);

		int timesToChangePage = (int) Math.ceil(totalNumber / 25);
		// When totalNumber = 102, timesToChangePage = 5, total number of result
		// on each page = 25,
		// the remaining number of result on last page would be:
		// 102-(5-1)*25 = 2
		int size2 = (int) totalNumber - (timesToChangePage - 1) * 25;

		for (int i = 0; i < timesToChangePage; i++) {
			changePageOfSearchResult(true);
			scrollToTop();
			waitForElementVisible(By.id("gwt-debug-ManagerListItem-strategyName"), 10);
			int size = getSizeOfElements(By.id("gwt-debug-ManagerListItem-strategyName"));
			try {
				// check if the number of investment on the page is 25

				assertEquals(size, 25);
			} catch (AssertionError e) {
				// check if the number of investment on the page equals to the
				// remainder of
				// total number of investment / 25
				assertEquals(size, size2);
			}
			
		}
		for (int i = 0; i < timesToChangePage; i++) {
			changePageOfSearchResult(false);
			scrollToTop();
			waitForElementVisible(By.id("gwt-debug-ManagerListItem-strategyName"), 10);
			int size = getSizeOfElements(By.id("gwt-debug-ManagerListItem-strategyName"));
			try {
				assertEquals(size, 25);
			} catch (AssertionError e) {
				assertEquals(size, size2);
			}
		}
		
		System.out.println("TC 17 Ended !");
		}
		
		

	// Test case #18
	@Ignore
	public void testColumns() throws InterruptedException {
		init();
		
		System.out.println("TC 18 : City Fund Explorer UI Tests Started !");
		selectColumnForResultPresentation("ISIN/WKN");

		// scroll to the 7th column
		JavascriptExecutor js = (JavascriptExecutor) webDriver;
		js.executeScript("document.getElementById('gwt-debug-SortableFlexTableAsync-table-0-8').scrollIntoView(true);");
		assertTrue(pageContainsStr("ISIN/WKN"));
		System.out.println("TC 18 Ended !");
	}

	// Test case #19
	@Test
	public void testRecentlyViewedInvestments() throws InterruptedException {
		init();
		System.out.println("TC 19 : City Fund Explorer UI Tests Started !");
		
		assertTrue(pageContainsStr("No recently viewed investments found."));
		clickInvestment("1");
		clickElement(By.xpath(".//*[@class='thisBackButton']"));
		waitForElementVisible(By.id("gwt-debug-ManagerListItem-strategyName"), 20);

		clickInvestment("3");
		clickElement(By.xpath(".//*[@class='thisBackButton']"));
		waitForElementVisible(By.id("gwt-debug-ManagerListItem-strategyName"), 20);

		clickInvestment("5");
		clickElement(By.xpath(".//*[@class='thisBackButton']"));
		waitForElementVisible(By.id("gwt-debug-ManagerListItem-strategyName"), 20);

		assertTrue(Integer.valueOf(getSizeOfElements(By.id("gwt-debug-RecentlyViewedStrategiesView-strategyName")))
				.equals(3));
		String lastInvestmentName = getTextByLocator(
				By.xpath("(.//*[@id='gwt-debug-RecentlyViewedStrategiesView-strategyName'])[3]"));

		clickInvestment("7");
		clickElement(By.xpath(".//*[@class='thisBackButton']"));
		waitForElementVisible(By.id("gwt-debug-ManagerListItem-strategyName"), 20);

		clickInvestment("9");
		clickElement(By.xpath(".//*[@class='thisBackButton']"));
		waitForElementVisible(By.id("gwt-debug-ManagerListItem-strategyName"), 20);

		assertFalse(lastInvestmentName.equals(
				getTextByLocator(By.xpath("(.//*[@id='gwt-debug-RecentlyViewedStrategiesView-strategyName'])[3]"))));
		System.out.println("TC 19 Ended !");
	}

	// Test case #20
	@Test
	public void testSortingInvestmentList() throws InterruptedException {
		init();
		System.out.println("TC 19 : City Fund Explorer UI Tests Started !");
		// count the number of sortable header of the list
		int size = getSizeOfElements(By.xpath(".//*[@class='sortableHeader']"));
		for (int i = 1; i <= size; i++) {
			log(String.valueOf(i));
			// we don't need to sort the Facts column
			if (!String.valueOf(i).equals("2")) {
				sortingResult(i);
			}

		}
		System.out.println("TC 20 Ended !");
	}

	@Test
	public void testCitibankProductionLink() throws InterruptedException {
		webDriver.get("https://www.citibank.co.th/THGCB/CBOL/muf/preprisea/flow.action");
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver)
				.withTimeout(Settings.WAIT_SECONDS * 2, TimeUnit.SECONDS).pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(org.openqa.selenium.NoSuchElementException.class);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[.='Prive Disclaimer']")));

		assertTrue(pageContainsStr("Prive Disclaimer"));
	}
	
	// go to HK platform and skip HelpOverlay
	public void init() throws InterruptedException {

		webDriver.get(UrlForHongKongPlatform);
		// wait for Help Overlay
		waitForElementVisible(By.xpath(".//*[@class='introjs-button introjs-skipbutton']"), 30);
		clickElement(By.xpath(".//*[@class='introjs-button introjs-skipbutton']"));
		clickOkButtonIfVisible();
		waitForElementVisible(By.id("gwt-debug-ManagerListItem-strategyName"), 10);
	}

	/*
	 * check the details of investment
	 * 
	 * @param numberOfInvestment
	 *            investment name to be clicked
	 */
	public void clickInvestment(String numberOfInvestment) {
		clickElement(By.xpath("(.//*[@id='gwt-debug-ManagerListItem-strategyName'])[" + numberOfInvestment + "]"));

		waitForElementVisible(By.xpath(".//*[@class='coloredBtn' and .='Details']"), 5);

		assertTrue(isElementVisible(By.id("gwt-debug-ManagetListDetailsPopupView-strategyDescLabel")));
		assertTrue(isElementVisible(By.id("gwt-debug-ManagetListDetailsPopupView-chartPanel")));

		clickElement(By.xpath(".//*[@class='coloredBtn' and .='Details']"));

		waitForElementVisible(By.xpath(".//*[@class='thisBackButton']"), 20);

	}

	/*
	 * filter result by criteria
	 * 
	 * @param numberOfColumn
	 *            1: Asset Class 2: Region 3: Currencies
	 * @param filterKeyword
	 *            Keyword for filtering
	 * @throws InterruptedException
	 */

	public void filterResult(String numberOfColumn, String filterKeyword) throws InterruptedException {

		String result = getTextByLocator(By.id("gwt-debug-ManagerListWidgetView-pageInfoLabel"));
		clickElement(By.xpath("(.//*[@class='fundExplorerFilterDropdowns'])[" + numberOfColumn + "]//button/i"));
		waitForElementVisible(By.xpath(".//*[.='" + filterKeyword + "']/preceding-sibling::input"), 10);
		clickElement(By.xpath(".//*[.='" + filterKeyword + "']/preceding-sibling::input"));

		// wait for refreshing
		wait(Settings.WAIT_SECONDS);

		assertTrue(!result.equals(getTextByLocator(By.id("gwt-debug-ManagerListWidgetView-pageInfoLabel"))));
		// reset search criteria
		clickElement(By.id("gwt-debug-FilterPanel-ResetButton"));

		waitForElementVisible(By.id("gwt-debug-ManagerListWidgetView-managerListPanel"), 120);
	}

	public void checkHelpOverlay() throws InterruptedException {
		waitForElementVisible(By.xpath(".//*[@class='introjs-button introjs-skipbutton']"), 30);
		assertTrue(pageContainsStr("Use this to quickly locate a specific fund"));

		clickElement(By.xpath(".//*[@class='introjs-button introjs-nextbutton']"));
		assertTrue(pageContainsStr("Use these advanced filters to narrow down your search"));

		clickElement(By.xpath(".//*[@class='introjs-button introjs-nextbutton']"));
		assertTrue(pageContainsStr("Choose fund related actions here"));

		clickElement(By.xpath(".//*[@class='introjs-button introjs-nextbutton']"));
		assertTrue(pageContainsStr("Use this to customise data display to your preference"));

		clickElement(By.xpath(".//*[@class='introjs-button introjs-nextbutton']"));
		assertTrue(pageContainsStr("Check your recently viewed funds here"));

		clickElement(By.xpath(".//*[@class='introjs-button introjs-skipbutton']"));
		assertTrue(pageContainsStr("To enable the help overlay again, please click on:"));
		// check for question mark image
		assertTrue(isElementVisible(By.xpath(".//div[@class='help-icon absoluteLeft44']")));

		clickOkButtonIfVisible();

	}

	/*
	 * do advanced search
	 * 
	 * @param field
	 *            field from the advacned search panel,including Investment
	 *            Name, Region, ISIN, Citi Fund Code
	 * @param keyword
	 *            keyword for advanced search
	 * @param expectedResult
	 *            expected result for the advanced search
	 */
	public void advancedSearch(String field, String keyword, String expectedResult) {
		waitForElementVisible(By.id("gwt-debug-ManagerListWidgetView-advancedSearchBtn"), 30);

		// String result = null;

		// try {
		// result =
		// getTextByLocator(By.id("gwt-debug-ManagerListWidgetView-pageInfoLabel"));
		// } catch (TimeoutException e) {
		//
		// }
		// click advanced search panel
		clickElementByKeyboard(By.id("gwt-debug-ManagerListWidgetView-advancedSearchBtn"));

		waitForElementVisible(By.id("gwt-debug-ManagerListWidgetView-advancedSearch"), 10);

		// send keyword to fields in advanced search panel
		sendKeysToElement(By.xpath(".//td[.='" + field + "']/following-sibling::td/input"), keyword);

		clickElement(By.id("gwt-debug-AdvancedSearchPanel-searchButton"));
		waitForElementVisible(By.id("gwt-debug-ManagerListItem-strategyName"), 60);

		// check if page contains the expected result
		assertTrue(pageContainsStr(expectedResult));

		clickElement(By.id("gwt-debug-ManagerListWidgetView-clearImg"));
		waitForElementVisible(By.id("gwt-debug-ManagerListItem-strategyName"), 30);
	}

	/**
	 * check the Show Favorite checkbox
	 * 
	 * @param check
	 *            this boolean decides whether the checkbox is checked.
	 */
	public void clickShowFavouriteCheckbox(boolean check) {

		boolean isChecked = waitGet(By.id("gwt-debug-FilterPanel-showFavBox-input")).isSelected();
		if (check) {
			if (isChecked) {
				// reload the favourite list
				setCheckboxStatus2(webDriver.findElement(By.id("gwt-debug-FilterPanel-showFavBox-input")), check);
				waitForElementVisible(By.id("gwt-debug-ManagerListItem-strategyName"), 20);
				clickElement(By.id("gwt-debug-FilterPanel-showFavBox-input"));
			} else {
				clickElement(By.id("gwt-debug-FilterPanel-showFavBox-input"));
			}
		} else {
			if (isChecked) {
				setCheckboxStatus2(webDriver.findElement(By.id("gwt-debug-FilterPanel-showFavBox-input")), check);
				waitForElementVisible(By.id("gwt-debug-ManagerListItem-strategyName"), 20);
			}
		}
	}

	/*
	 * Remove favourite from the favourite investment list
	 * 
	 * @param list
	 *            ArrayList of favorite investment
	 * @return
	 */
	public String removeFavourite(ArrayList<String> list) {

		this.clickShowFavouriteCheckbox(true);
		waitForElementVisible(By.id("gwt-debug-ManagerListItem-strategyName"), 20);

		try {
			for (String investments : list) {
				assertTrue(pageContainsStr(investments));
			}
		} catch (NullPointerException e) {
			// if the ArrayList is empty, it implies no favorite investment.
			assertFalse(isElementVisible(By.id("gwt-debug-ManagerListItem-strategyName")));
		}

		String investmentToBeRemoved = getTextByLocator(By.id("gwt-debug-ManagerListItem-strategyName"));

		clickElement(By.xpath(
				".//*[@id='gwt-debug-ManagerListItem-strategyName']/parent::div/parent::td/preceding-sibling::td//button/span"));

		clickElement(By.xpath(".//*[@class='FundExplorerActionColumnTable']//a[.='Remove from Favorite']"));

		clickShowFavouriteCheckbox(false);
		return investmentToBeRemoved;
	}

	/*
	 * change the number of search result. it is at the bottom of page and
	 * includes few option: 15 ,25 ,50
	 * 
	 * @param number
	 *            number of search result
	 */
	public void changeNumberOfSearchResult(String number) {

		clickElement(By.xpath(".//button[.='" + number + "']"));

		waitForElementVisible(By.id("gwt-debug-ManagerListItem-strategyName"), 30);

		// number of elements sharing same locator
		int size = getSizeOfElements(By.id("gwt-debug-ManagerListItem-strategyName"));

		// label showing the total number of result
		String numberOfResult = getTextByLocator(By.id("gwt-debug-ManagerListWidgetView-pageInfoLabel"));

		assertTrue(numberOfResult.contains(number));
		assertTrue(Integer.valueOf(number).equals(size));
	}

	/*
	 * click previous or next page for the search result
	 * 
	 * @param next
	 *            next is a boolean indicating that: if true, next page button
	 *            will be clicked. if false, previous page button will be
	 *            clicked.
	 */
	public void changePageOfSearchResult(boolean next) {
		By locator = null;
		if (next) {
			locator = By.id("gwt-debug-ManagerListWidgetView-nextPageBtn");
		} else {
			locator = By.id("gwt-debug-ManagerListWidgetView-prePageBtn");
		}
		clickElement(locator);
	}

	public void selectColumnForResultPresentation(String... columns) {
		clickElement(By.xpath(
				".//*[@id='gwt-debug-ManagerListWidgetView-tableControlPanel']//button[@id='gwt-debug-MultiSelectDropDownBox-dropDown']/i"));
		for (String column : columns) {
			clickElement(By.xpath(".//label[.='" + column + "']/preceding-sibling::input"));
		}
	}

	public void sortingResult(int columnNumber) {

		// to make the alignment of column number and sorting button
		String columnNumberText = String.valueOf(columnNumber);
		String columnToSort = columnNumberText;
		String resultBeforeSorting = null;
		String resultAfterSorting = null;
		if (Integer.valueOf(columnNumber) > 2) {
			columnToSort = String.valueOf(columnNumber - 1);
		}
		log("columnNumberText = " + columnNumberText);
		log("columnToSort = " + columnToSort);
		// sorting
		clickElement(By.xpath("(.//*[@class='sortableHeader']/following-sibling::div/button)[" + columnToSort + "]"));

		waitForElementVisible(
				By.xpath(".//*[@id='gwt-debug-SortableFlexTableAsync-table-1-" + columnNumberText + "']/div"), 20);

		if (columnNumber > 4) {
			columnNumber += 1;
			columnNumberText = String.valueOf(columnNumber);
			resultBeforeSorting = getTextByLocator(
					By.xpath(".//*[@id='gwt-debug-SortableFlexTableAsync-table-1-" + columnNumberText + "']/div"));
			columnNumber -= 1;
			columnNumberText = String.valueOf(columnNumber);
		} else {
			resultBeforeSorting = getTextByLocator(
					By.xpath(".//*[@id='gwt-debug-SortableFlexTableAsync-table-1-" + columnNumberText + "']/div"));
		}

		// sorting
		clickElement(By.xpath("(.//*[@class='sortableHeader']/following-sibling::div/button)[" + columnToSort + "]"));
		waitForElementVisible(
				By.xpath(".//*[@id='gwt-debug-SortableFlexTableAsync-table-1-" + columnNumberText + "']/div"), 30);

		if (columnNumber > 4) {
			columnNumber += 1;
			columnNumberText = String.valueOf(columnNumber);
			resultAfterSorting = getTextByLocator(
					By.xpath(".//*[@id='gwt-debug-SortableFlexTableAsync-table-1-" + columnNumberText + "']/div"));

		} else {
			resultAfterSorting = getTextByLocator(
					By.xpath(".//*[@id='gwt-debug-SortableFlexTableAsync-table-1-" + columnNumberText + "']/div"));
		}

		if (!resultAfterSorting.equals("n/a") || !resultBeforeSorting.equals("n/a"))
			log(resultAfterSorting);
		log(resultBeforeSorting);
		assertFalse(resultAfterSorting.equals(resultBeforeSorting));
	}

	}
