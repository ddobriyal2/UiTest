package org.sly.uitest.pageobjects.clientsandaccounts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;

/**
 * This class represents the Analysis Page (tab) of an account, which can be
 * navigated by clicking 'Clients' -> 'Account Overview' -> choose any account
 * -> 'Analysis(tab)' or 'Build' -> 'Model Portfolios' -> choose any model
 * portfolio -> 'Analysis(tab)'
 * 
 * @author Lynne Huang
 * @date : 20 Aug, 2015
 * @company Prive Financial
 */
public class AnalysisPage extends AbstractPage {

	public AnalysisPage(WebDriver webDriver) throws InterruptedException {

		super();
		this.webDriver = webDriver;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.id("gwt-debug-TabPortfolioModelView-contentPanel")));

		// assertTrue(pageContainsStr("Sector Exposure"));

	}
}
