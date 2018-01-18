package org.sly.uitest.pageobjects.clientsandaccounts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.pageobjects.abstractpage.AdvancedSearchPage;
import org.sly.uitest.pageobjects.salesprocess.ClientRegisterPage;

/**
 * This class represents the Client Overview Page, which can be navigated by
 * clicking 'Clients' -> 'Client Overview'
 * 
 * URL: "http://192.168.1.104:8080/SlyAWS/?locale=en#investorList"
 * 
 * @author Lynne Huang
 * @date : 26 Aug, 2015
 * @company Prive Financial
 * 
 *          PAGE NAVIGATION: Client -> Client Overview
 */
public class ClientOverviewPage extends AbstractPage {

	/**
	 * @param webDriver
	 */
	public ClientOverviewPage(WebDriver webDriver) {

		super();
		this.webDriver = webDriver;

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		try {
			wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.id("gwt-debug-InvestorOverviewView-tablePanel")));

		} catch (Exception ex) {
			// wait.until(ExpectedConditions.visibilityOfElementLocated(By
			// .id("gwt-debug-MyMainMaterialView-mainPanel")));

		}

		// assertTrue(pageContainsStr(" Summary of Clients "));
	}

	/**
	 * Click the downward triangle icon to open the Advanced Search Panel on the
	 * Client Overview page
	 * 
	 * @return AdvancedSearchPage
	 */
	public AdvancedSearchPage goToAdvancedSearchPage() throws InterruptedException {
		scrollToTop();
		clickElement(By.id("gwt-debug-InvestorOverviewView-advancedSearchBtn"));
		if (!isElementVisible(By.xpath(".//*[@class='advancedSearch']"))) {
			clickElementByKeyboard(By.id("gwt-debug-InvestorOverviewView-advancedSearchBtn"));
		}
		return new AdvancedSearchPage(webDriver, ClientOverviewPage.class);

	}

	/**
	 * Simply input the string into the search box
	 * 
	 * @param string
	 *            the string to search, such as client name, account name
	 * @return
	 */
	public ClientOverviewPage simpleSearchByString(String string) {
		scrollToTop();
		sendKeysToElement(By.id("gwt-debug-InvestorOverviewView-searchBox"), string);

		clickElement(By.id("gwt-debug-InvestorOverviewView-searchImg"));

		return this;

	}

	/**
	 * Click the given client/investor name, the page will be navigated to the
	 * client Detail page by default
	 * 
	 * @param name
	 *            the name of the client/investor
	 * 
	 * @return DetailPage
	 */
	public DetailPage goToClientDetailPageByName(String name) throws InterruptedException {

		waitForElementClickable(By.xpath(".//*[contains(text(),'" + name + "')]"), 30);

		clickElement(By.xpath(".//*[contains(text(),'" + name + "')]"));

		return new DetailPage(webDriver);
	}

	/**
	 * Click the cross icon besides the search box to clear the previous search
	 * 
	 * @return {@link ClientOverviewPage}
	 */
	public ClientOverviewPage clickClearSearchIcon() {

		if (isElementVisible(By.id("gwt-debug-InvestorOverviewView-clearImg"))) {
			clickElement(By.id("gwt-debug-InvestorOverviewView-clearImg"));
		}

		return this;
	}

	public ClientRegisterPage clickCreateClientForFS() throws InterruptedException {
		clickElement(By.xpath(".//img[@src='img/financesales/icon_kunden_hinzufuegen.png']"));
		return new ClientRegisterPage(webDriver);
	}

	/**
	 * Select a format for exporting the search result, and then click the XLS
	 * icon beside
	 * 
	 * @param select
	 *            the format of the search result
	 * @param file
	 *            the name of the search result
	 * 
	 * @return {@link ClientOverviewPage}
	 * @throws InterruptedException
	 */
	public ClientOverviewPage exportSearchResult(String type, String file) throws InterruptedException {

		selectFromDropdown(By.id("gwt-debug-ExportFileWidget-exportFormatListBox"), type);

		clickElement(By.id("gwt-debug-ExportFileWidget-iconXls"));

		clickYesButtonIfVisible();

		waitForElementVisible(By.id("gwt-debug-ExportSearchResultPopupView-fileNameTextBox"), 10);

		sendKeysToElement(By.id("gwt-debug-ExportSearchResultPopupView-fileNameTextBox"), file);

		clickElement(By.id("gwt-debug-ExportSearchResultPopupView-proceedButton"));

		clickOkButtonIfVisible();

		return this;

	}
}
