package org.sly.uitest.pageobjects.mysettings;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.pageobjects.abstractpage.MenuBarPage;

/**
 * This class represents the User Profile page, which can be navigated by
 * clicking 'User Settings' -> 'Pay Fee'
 * 
 * @author Lynne Huang
 * @date : 18 Sep, 2015
 * @company Prive Financial
 */
public class PayFeePage extends AbstractPage {

	/**
	 * @param webDriver
	 */
	public PayFeePage(WebDriver webDriver) {

		super();
		this.webDriver = webDriver;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gwt-debug-PayFeeEdit-dialogBoxBody")));

		assertTrue(pageContainsStr(" Pay Fee "));
		assertTrue(pageContainsStr("Advisor Company"));
	}

	/**
	 * ON the Pay Fee page, click the CANCEL button
	 * 
	 * @return {@link MenuBarPage}
	 */
	public MenuBarPage clickCancelButton() {

		clickElement(By.id("gwt-debug-PayFeeEdit-cancelBtn"));

		return new MenuBarPage(webDriver);
	}

}
