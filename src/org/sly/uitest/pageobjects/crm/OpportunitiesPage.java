package org.sly.uitest.pageobjects.crm;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;

/**
 * 
 * This class represents the Opportunities Page of an account, which can be
 * navigated by clicking 'Clients' -> 'Opportunities'
 * 
 * URL: "http://192.168.1.104:8080/SlyAWS/?locale=en#opportunityoverview"
 * 
 * @author Lynne Huang
 * @date : 27 Aug, 2015
 * @company Prive Financial
 */
public class OpportunitiesPage extends AbstractPage {

	/**
	 * @param webDriver
	 */
	public OpportunitiesPage(WebDriver webDriver) {

		super();
		this.webDriver = webDriver;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gwt-debug-MyMainMaterialView-mainPanel")));

		} catch (Exception ex) {
			// wait.until(ExpectedConditions.visibilityOfElementLocated(By
			// .id("gwt-debug-MyMainClassicView-mainPanel")));

		}

		assertTrue(pageContainsStr(" Opportunities "));
		assertTrue(pageContainsStr("New Opportunity"));
	}

}
