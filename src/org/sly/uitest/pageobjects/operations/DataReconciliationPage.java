package org.sly.uitest.pageobjects.operations;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;

/**
 * This class is for the Data Reconciliation Page. In classic view and material
 * view, it can be accessed by clicking 'Operation -> 'Data Reconciliation'
 * 
 * @author Benny Leung
 * @date : 9 Aug, 2016
 * @company Prive Financial
 */
public class DataReconciliationPage extends AbstractPage {

	public DataReconciliationPage(WebDriver webDriver) {

		super();
		this.webDriver = webDriver;
		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gwt-debug-DataReconciliationView-mainPanel")));

		assertTrue(pageContainsStr(" Data Reconciliation "));

	}
}
