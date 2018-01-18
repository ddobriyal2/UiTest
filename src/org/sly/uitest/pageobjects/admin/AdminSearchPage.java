package org.sly.uitest.pageobjects.admin;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.pageobjects.abstractpage.AdvancedSearchPage;

/**
 * This class represents the Admin Search page when login as system admin
 * 
 * @author Benny Leung
 * @date : Oct 14, 2016
 * @company Prive Financial
 */
public class AdminSearchPage extends AbstractPage {
	public AdminSearchPage(WebDriver webDriver) {

		super();
		this.webDriver = webDriver;
		// TODO Auto-generated constructor stub

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gwt-debug-MyMainMaterialView-mainPanel")));

		} catch (Exception ex) {
			// wait.until(ExpectedConditions.visibilityOfElementLocated(By
			// .id("gwt-debug-MyMainMaterialView-mainPanel")));

		}
		assertTrue(pageContainsStr(" Search "));

	}

	public AdminSearchPage editSearchType(String type) {

		waitForElementVisible(By.id("gwt-debug-AdminSearchScreenView-searchTypeList"), 20);

		selectFromDropdown(By.id("gwt-debug-AdminSearchScreenView-searchTypeList"), type);

		return this;
	}

	public AdvancedSearchPage clickAdvancedSearchButton() throws InterruptedException {

		waitForElementVisible(By.id("gwt-debug-AdminSearchScreenView-advancedSearchBtn"), 20);

		clickElement(By.id("gwt-debug-AdminSearchScreenView-advancedSearchBtn"));

		return new AdvancedSearchPage(webDriver, AdminSearchPage.class);
	}
}
