package org.sly.uitest.pageobjects.commissioning;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.pageobjects.abstractpage.AdvancedSearchPage;

/**
 * This class represents the Cashflow page, which can be navigated by clicking
 * 'Accounting' -> 'Cashflow Overview'
 * 
 * @link http://192.168.1.104:8080/SlyAWS/?locale=en#cashflowlist
 * 
 * @author Lynne Huang
 * @date : 26 Aug, 2015
 * @company Prive Financial
 * 
 */
public class CashflowOverviewPage extends AbstractPage {

	/**
	 *
	 * @param webDriver
	 */
	public CashflowOverviewPage(WebDriver webDriver) {

		super();
		this.webDriver = webDriver;

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gwt-debug-CashflowListView-cashflowPanel")));

		assertTrue(pageContainsStr(" Cashflow Overview "));
	}

	public CashflowOverviewPage sortColumn(String columnName) {

		waitForElementVisible(By.xpath(".//div[.='" + columnName + "']/following-sibling::div/button"), 30);
		clickElement(By.xpath(".//div[.='" + columnName + "']/following-sibling::div/button"));
		return this;
	}

	/**
	 * In the Cashflow Overview page, click triangle icon to open the Advanced
	 * Search Panel
	 * 
	 * @return AdvancedSearchPage
	 */
	public AdvancedSearchPage goToAdvancedSearchPage() throws InterruptedException {
		scrollToTop();
		clickElement(By.id("gwt-debug-CashflowListView-advancedSearchBtn"));

		return new AdvancedSearchPage(webDriver, CashflowOverviewPage.class);

	}
}
