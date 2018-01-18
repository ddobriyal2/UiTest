package org.sly.uitest.pageobjects.workflow;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;

/**
 * 
 * This class represents the Process Tasks page, which can be navigated by
 * clicking 'Workflow' -> 'Process Tasks'
 * 
 * URL: "http://192.168.1.104:8080/SlyAWS/?locale=en#workflow;workflowLoc=1"
 * 
 * @author Lynne Huang
 * @date : 7 Oct, 2015
 * @company Prive Financial
 */
public class ProcessTasksPage extends AbstractPage {

	/**
	 * @param webDriver
	 */
	public ProcessTasksPage(WebDriver webDriver) {
		super();
		this.webDriver = webDriver;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.className("v-verticallayout v-verticallayout-main main")));

		assertTrue(pageContainsStr("Inbox"));
	}

}
