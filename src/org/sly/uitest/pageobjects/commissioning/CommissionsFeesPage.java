package org.sly.uitest.pageobjects.commissioning;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.pageobjects.abstractpage.AdvancedSearchPage;

/**
 * This class represents the Commissions Fees Page, which can be navigated by
 * clicking 'Accounting' -> 'Client Fees' or 'Advisor Commissions' or 'Company
 * Commissions' -> '*** Acc' (eg. Reconsiliation Acc, Product Provider Acc,
 * etc.)
 * 
 * URL: http://192.168.1.104:8080/SlyAWS/?locale=en_US#feehistory;acctKeyToLoad=
 * 13031;feeAcctKey=25421;feeWidgetType=1
 * 
 * @author Lynne Huang
 * @date : 11 Aug, 2015
 * @company Prive Financial
 * 
 *          PAGE NAVIGATION: Accounting -> Client Fees/Advisor
 *          Commissions/Company Commissions -> *** Acc
 */
public class CommissionsFeesPage extends AbstractPage {

	/**
	 * @param webDriver
	 */
	public CommissionsFeesPage(WebDriver webDriver) {

		super();
		this.webDriver = webDriver;

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(60, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gwt-debug-MyMainMaterialView-mainPanel")));

		assertTrue("Expected fee account summary - total fees settled", pageContainsStr("Total Fees Settled"));

		assertTrue("Expected fee account summary - total fees outstanding", pageContainsStr("Total Fees Outstanding"));
	}

	/**
	 * In the Commission Fees page, click triangle icon to open the Advanced
	 * Search Panel
	 * 
	 * @return AdvancedSearchPage
	 */
	public AdvancedSearchPage goToAdvancedSearchPage() throws InterruptedException {
		scrollToTop();
		clickElement(By.id("gwt-debug-FeeListView-advancedSearchBtn"));

		return new AdvancedSearchPage(webDriver, CommissionsFeesPage.class);

	}

	/**
	 * click NEW ENTRY button to add a new entry
	 * 
	 * @return EditFeeEntryPage
	 */
	public EditFeeEntryPage clickNewEntryButton() throws InterruptedException {

		clickElement(By.id("gwt-debug-FeeListView-newEntryBtn"));

		return new EditFeeEntryPage(webDriver);
	}

	/**
	 * @param name
	 *            the name of the client, whose fee entry needs edit
	 * 
	 * @return EditFeeEntryPage
	 */
	public EditFeeEntryPage editFeeEntryByClientName(String name) throws InterruptedException {
		WebElement elem = webDriver.findElement(
				By.xpath("//td[contains(.,'" + name + "')]/following-sibling::td[7]//button[@title='Edit']"));
		elem.sendKeys("", Keys.RETURN);

		return new EditFeeEntryPage(webDriver);
	}

	/**
	 * click Delete icon to delete the specific fee entry with given name and
	 * date
	 * 
	 * @param date
	 *            the date of the fee entry that is to be deleted
	 * @param name
	 *            the client name of the fee entry that is to be deleted
	 * 
	 * @return {@link EditFeeEntryPage}
	 * 
	 */
	public CommissionsFeesPage deleteTheEntry(String date, String name) throws InterruptedException {

		clickElement(By.xpath("//td[.='" + date + "']/following-sibling::td[contains(.,'" + name
				+ "')]/following-sibling::td//button[@title='Delete']"));

		clickElement(By.id("gwt-debug-CustomDialog-yesButton"));

		return this;
	}

	/**
	 * Check or uncheck the checkbox of all the entries according to the boolean
	 * value checked
	 * 
	 * @checked if true, check all the entries; if false, uncheck all the
	 *          entries
	 * 
	 * @return {@link CommissionsFeesPage}
	 */
	public CommissionsFeesPage checkAllTheEntries(boolean checked) {

		waitForElementVisible((By.xpath(".//*[@id='gwt-debug-FeeListPresenter-titleBox-input']")), 30);

		WebElement we = webDriver.findElement(By.xpath(".//*[@id='gwt-debug-FeeListPresenter-titleBox-input']"));

		setCheckboxStatus2(we, true);

		we = webDriver.findElement(By.xpath(".//*[@id='gwt-debug-FeeListPresenter-titleBox-input']"));
		log("all box checked");
		setCheckboxStatus2(we, checked);

		return this;
	}

	/**
	 * Check or uncheck the specific entry with the given name according to the
	 * boolean value checked
	 * 
	 * @param name
	 *            the name of the entry
	 * @param checked
	 *            if true check the entry; if false, uncheck the entry
	 */
	public CommissionsFeesPage checkTheEntry(String name, boolean checked) {

		clickElement(By.xpath("//td[contains(.,'" + name + "')]/preceding-sibling::td[6]//input[@type='checkbox']"));

		WebElement we = webDriver.findElement(
				By.xpath("//td[contains(.,'" + name + "')]/preceding-sibling::td[6]//input[@type='checkbox']"));

		setCheckboxStatus2(we, checked);

		return this;
	}

	/**
	 * Click the PROCESS button
	 * 
	 * @return {@link CommissionsFeesPage}
	 */
	public CommissionsFeesPage clickProcessButton() {

		clickElement(By.id("gwt-debug-FeeListView-processBtn"));

		clickYesButtonIfVisible();

		return this;
	}

	/**
	 * Click the Make Payment button
	 * 
	 * @return {@link CommissionsFeesPage}
	 */
	public CommissionsFeesPage clickMakePaymentButton() {

		clickElement(By.id("gwt-debug-FeeListView-payBtn"));

		clickYesButtonIfVisible();

		return this;
	}

	/**
	 * Check or uncheck the checkbox of Include Paid Items according to the
	 * boolean value include
	 * 
	 * @param include
	 *            if true, include paid items; if false not include paid items
	 */
	public CommissionsFeesPage checkIncludePaidItems(boolean include) {

		WebElement we = webDriver.findElement(By.id("gwt-debug-FeeListView-showPaidItems-input"));

		setCheckboxStatus2(we, include);

		return this;
	}

}
