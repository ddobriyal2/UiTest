package org.sly.uitest.pageobjects.planning;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;

/**
 * @author Lynne Huang
 * @date : 19 Oct, 2015
 * @company Prive Financial
 */
public class WealthPlanningOverviewPage extends AbstractPage {

	/**
	 * @param webDriver
	 */
	public WealthPlanningOverviewPage(WebDriver webDriver) {

		super();
		this.webDriver = webDriver;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("gwt-debug-MyVisibilityTabPanel-tabContentContainer")));

		// assertTrue(pageContainsStr(" Current Wealth "));
	}

}
