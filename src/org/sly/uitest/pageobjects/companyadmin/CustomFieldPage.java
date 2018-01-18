package org.sly.uitest.pageobjects.companyadmin;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;

/**
 * 
 * This class represents the Custom Field page, which can be navigated by
 * clicking 'Company Settings' -> 'Custom Field'
 * 
 * URL:"http://192.168.1.104:8080/SlyAWS/?locale=en#customfieldedit"
 * 
 * @author Lynne Huang
 * @date : 27 Aug, 2015
 * @company Prive Financial
 */
public class CustomFieldPage extends AbstractPage {

	/**
	 * @param webDriver
	 */
	public CustomFieldPage(WebDriver webDriver) {
		super();
		this.webDriver = webDriver;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		try {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.id("gwt-debug-CustomFieldDefinitionEditView-addBtn")));

		} catch (Exception ex) {
			// wait.until(ExpectedConditions.visibilityOfElementLocated(By
			// .id("gwt-debug-MyMainMaterialView-mainPanel")));

		}

		assertTrue(pageContainsStr(" Custom Field Definition "));
	}

}
