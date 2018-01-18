package org.sly.uitest.sections.investmentlist;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.settings.Settings;

/**
 * This test is for the following test plan:
 * https://docs.google.com/spreadsheets
 * /d/18Rr9NquQvT2N5sCy6-fZMCc0nQJJoGsnb2FbUAJhzPY/edit#gid=0
 * 
 * @author Benny Leung
 * @date Sept 1, 2016
 * @company Prive Financial
 *
 */
public class InvestmentlistTest extends AbstractTest {
	// For test case #2 and #6
	@Test

	public void testDocumentsInDifferentLanguage() throws MalformedURLException, IOException, InterruptedException {
		// Test case #2
		webDriver.get(Settings.BASE_URL
				+ "?locale=de_DE#investmentDetails;strategyKey=27510;hideBackButton=true;dataSource=131;token=qPTdfweRT");
		// wait(30);
		waitForElementVisible(By.id("gwt-debug-ManagerDisplayView-contentPanel"), 60);

		// Ensure documents show up
		// Check KIID

		assertTrue(pageContainsStr("KIID"));

		String KIIDUrl = getAttributeStringByLocator(By.xpath("//a[contains(text(), 'KIID')]"), "href");

		assertTrue(checkUrlValid(KIIDUrl));
		// Test case #2 and #6: Check Factsheet
		// Check Factsheet
		// webDriver
		// .get(Settings.BASE_URL
		// +
		// "?locale=de_DE#investmentDetails;strategyKey=12038;hideBackButton=true;dataSource=131;token=qPTdfweRT");
		// wait(30);
		//
		// waitForElementVisible(
		// By.id("gwt-debug-ManagerDisplayView-contentPanel"), 60);
		//
		// assertTrue(pageContainsStr("Factsheet"));
		//
		// String factsheetUrl = getAttributeStringByLocator(
		// By.xpath(".//a[.='Factsheet ']"), "href");
		//
		// // Ensure link of document work.
		// assertTrue(checkUrlValid(factsheetUrl));

		webDriver.get(Settings.BASE_URL
				+ "?locale=en#investmentDetails;strategyKey=27510;hideBackButton=true;dataSource=131;token=qPTdfweRT");

		waitForElementVisible(By.id("gwt-debug-ManagerDisplayView-contentPanel"), 60);
		// wait(30);
		// Ensure no benchmark
		assertFalse(isElementDisplayed(By.id("gwt-debug-ManagerDisplayWidgetView-benchmarkListBox")));

		// Test case #6
		assertTrue(pageContainsStr("Fact Sheet"));

		String factsheetUrl2 = getAttributeStringByLocator(By.xpath("//a[contains(text(), 'Fact Sheet')]"), "href");
		assertTrue(checkUrlValid(factsheetUrl2));
	}

	// Templeton Global Bond Fund A(Mdis)GBP
	@Test
	public void testDownloadingDocument() throws MalformedURLException, IOException {
		// Test case #3
		assertTrue(checkUrlValid(
				Settings.BASE_URL + "DownloadFundFactsheet?isin=DE0008490962&type=1&token=qPTdfweRT&lang=2"));
		// Test case #4
		assertTrue(checkUrlValid(
				Settings.BASE_URL + "DownloadFundFactsheet?isin=DE0008490962&type=3&token=qPTdfweRT&lang=2"));
	}

	@Test
	public void testScreenSize() throws InterruptedException {
		webDriver.get(Settings.BASE_URL
				+ "#investmentDetails;strategyKey=10257;hideBackButton=true;dataSource=131;token=qPTdfweRT");
		webDriver.manage().window().setSize(new Dimension(850, 850));

		waitForElementVisible(By.id("gwt-debug-ManagerDisplayWidgetView-strategyName"), 20);

		assertTrue(isElementVisible(By.id("gwt-debug-ManagerDisplayWidgetView-backButtonLabel")));
		assertTrue(isElementVisible(By.id("gwt-debug-ManagerDisplayWidgetView-managerDocumentContainer")));
		assertTrue(isElementVisible(By.id("gwt-debug-ManagerDisplayWidgetView-recentlyViewedPanel")));

	}
}
