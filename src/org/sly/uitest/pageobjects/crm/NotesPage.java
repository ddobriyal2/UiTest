package org.sly.uitest.pageobjects.crm;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.framework.AbstractTest;

/**
 * This class is for the Notes page. In classic view, it can be accessed by
 * clicking 'Manage -> 'Notes' In material view, it can be accessed by clicking
 * 'Company Admin' -> 'Notes'
 * 
 * @author Benny Leung
 * @date : 5 Aug, 2016
 * @company Prive Financial
 */
public class NotesPage extends AbstractTest {
	public NotesPage(WebDriver webDriver) throws InterruptedException {

		super();
		this.webDriver = webDriver;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gwt-debug-NotesOverviewView-searchBox")));

		assertTrue(pageContainsStr(" Notes "));

	}
}
