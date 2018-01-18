package org.sly.uitest.pageobjects.clientsandaccounts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;

/**
 * This class represents the Compare Page (tab) of an account, which can be
 * navigated by clicking 'Clients' -> 'Account Overview' -> choose the account
 * contains 2 or more investments -> 'Compare (tab)' or 'Build' -> 'Model
 * Portfolios' -> choose model portfolio contains 2 or more investments ->
 * 'Compare (tab)'
 * 
 * URL:
 * "http://192.168.1.104:8080/SlyAWS/?locale=en#portfolioOverviewCompare;investorAccountKey=32181;valueType=2"
 * 
 * @author Lynne Huang
 * @date : 20 Aug, 2015
 * @company Prive Financial
 * 
 *          PAGE NAVIGATION: Client -> Account Overview -> *account -> COMPARE
 *          (Tab) OR Build -> Model Portfolios -> *model portfolio -> COMPARE
 */
public class ComparePage extends AbstractPage {

	/**
	 * @param webDriver
	 */
	public ComparePage(WebDriver webDriver) {

		super();
		this.webDriver = webDriver;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		try {
			FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(10, TimeUnit.SECONDS)
					.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.id("gwt-debug-ManagerCompare-compareLayoutPanel")));

		} catch (org.openqa.selenium.TimeoutException e) {

			clickElement(By.id("gwt-debug-CustomDialog-okButton"));
		}

	}

}
