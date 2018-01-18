package org.sly.uitest.pageobjects.companyadmin;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;

/**
 * 
 * This class represents the Custom Asset page, which can be navigated by
 * clicking 'Company Settings' -> 'Dashboard'
 * 
 * URL: "http://192.168.1.104:8080/SlyAWS/?locale=en#dashboard"
 * 
 * @author Lynne Huang
 * @date : 27 Aug, 2015
 * @company Prive Financial
 */
public class DashboardPage extends AbstractPage {

	/**
	 * @param webDriver
	 */
	public DashboardPage(WebDriver webDriver) {
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

		assertTrue(pageContainsStr("Go to the Prive Managers WidgetStore"));
	}

	/**
	 * Click on button named "ADD WIDGETS"
	 * 
	 * @return {@link DashboardPage}
	 */
	public DashboardPage clickAddWidgetsButton() {
		clickElement(By.id("gwt-debug-DashBoardView-addWidgets"));
		return this;
	}

	/**
	 * Click X button on add widget window
	 * 
	 * @return
	 */
	public DashboardPage closeAddWidgetWindow() {
		new Actions(webDriver).moveToElement(webDriver.findElement(By.xpath(".//button[@class='dialogBoxCloseBtn']")))
				.click().perform();
		return this;
	}

	/**
	 * Click on red "-" button on the right hand side of the screen
	 * 
	 * @return {@link DashboardPage}
	 */
	public DashboardPage clickAddWindowButton() {
		clickElement(By.id("gwt-debug-DashBoardView-newTabButton"));
		return this;
	}

	/**
	 * Click on green "+" button on the right hand side of the screen
	 * 
	 * @return {@link DashboardPage}
	 */
	public DashboardPage clickDeleteWindowButton() {
		clickElement(By.id("gwt-debug-DashBoardView-deleteButton"));
		clickYesButtonIfVisible();
		return this;
	}

	public DashboardPage dragAndDropFavouriteInvestmentsWidget(String widgetName) {
		WebElement background = null;

		try {
			background = webDriver.findElement(By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-mainPanel']"));

		} catch (org.openqa.selenium.NoSuchElementException e) {
			background = webDriver.findElement(By.xpath(".//*[@id='gwt-debug-MyMainClassicView-mainPanel']"));
		}

		WebElement widget = null;

		switch (widgetName) {
		case "investments":
			widget = webDriver
					.findElement(By.xpath(".//*[@class='fa fa-button fa-star info-blue size-64 padding-top-20px']"));
			break;
		case "clients":
			widget = webDriver
					.findElement(By.xpath(".//*[@class='fa fa-button fa-users info-blue size-64 padding-top-20px']"));
			break;
		case "alerts":
			widget = webDriver.findElement(
					By.xpath(".//*[@class='fa fa-button fa-exclamation-circle info-blue size-64 padding-top-20px']"));
			break;
		}

		new Actions(webDriver).dragAndDrop(widget, background).perform();

		return this;
	}

	public DashboardPage clickSaveButton() {
		clickElement(By.id("gwt-debug-DashBoardView-saveButton"));
		return this;
	}
}
