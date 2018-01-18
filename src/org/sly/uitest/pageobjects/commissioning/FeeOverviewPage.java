package org.sly.uitest.pageobjects.commissioning;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;

/**
 * 
 * This class represents the Commission Fees Overview Page, which can be
 * navigated by clicking 'Accounting' -> 'Client Fees' or 'Advisor Commissions'
 * or 'Company Commissions'
 * 
 * URL:
 * http://192.168.1.104:8080/SlyAWS/?locale=en_US#feeoverview;feeWidgetType=1
 * 
 * @author Lynne Huang
 * @date : 11 Aug, 2015
 * @company Prive Financial
 * 
 *          PAGE NAVIGATION: Accounting -> Client Fees/Advisor
 *          Commissions/Company Commissions
 */

// This Page represent Client Fees, Advisor Commission, and Company Commission
public class FeeOverviewPage extends AbstractPage {

	/**
	 * @param driver
	 */
	public FeeOverviewPage(WebDriver webDriver, String title) {

		super();
		this.webDriver = webDriver;

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gwt-debug-MyMainMaterialView-mainPanel")));

		} catch (Exception e) {
			// wait.until(ExpectedConditions.visibilityOfElementLocated(By
			// .id("gwt-debug-MyMainMaterialView-mainPanel")));
		}

		try {
			waitForElementVisible(By.xpath(".//*[@class='introjs-button introjs-skipbutton']"), 6);

			clickElement(By.xpath(".//*[@class='introjs-button introjs-skipbutton']"));
		} catch (TimeoutException e) {

		}

		assertTrue(pageContainsStr(title));
	}

	/**
	 * Click the little downward arrow icon beside the account to expand the
	 * account with the given name
	 * 
	 * @param name
	 *            the name of the account
	 * @return {@link FeeOverviewPage}
	 */
	public FeeOverviewPage expandAccountByName(String name) {

		waitForElementVisible(By.xpath(".//a[@class='allocationTableLink']"), 30);
		int page = getPagesOfElements(By.xpath(".//a[@class='allocationTableLink']"));

		assertTrue(pageContainsStrMultiPages(name, page));

		clickElement(By.xpath(".//*[@id='gwt-debug-FeeOverviewView-feesPanel']/table/tbody/tr[contains(., '" + name
				+ "')]/td[1]/table/tbody/tr/td[2]/a"));
		try {
			waitForElementVisible(By.xpath(".//*[@class='introjs-button introjs-skipbutton']"), 5);

			clickElement(By.xpath(".//*[@class='introjs-button introjs-skipbutton']"));
		} catch (TimeoutException e) {

		}
		return this;
	}

	/**
	 * After expand the account, go to the Salary Account (commission fee) page
	 * by clicking 'Salary Acc' under the account
	 * 
	 * @param account
	 *            the expanded account
	 * @return {@link CommissionsFeesPage}
	 */
	public CommissionsFeesPage goToSalaryAccPage(String account) throws InterruptedException {

		clickElementAndWait3(By.xpath("//td[.='" + account
				+ "']/parent::tr[@class='mat-table-body']/following-sibling::tr[1]//a[.='Salary Acc']"));

		return new CommissionsFeesPage(webDriver);

	}

	/**
	 * After expand the account, go to the Payable Account (commission fee) page
	 * by clicking 'Payable Acc' under the account
	 * 
	 * @param account
	 *            the expanded account
	 * @return {@link CommissionsFeesPage}
	 */
	public CommissionsFeesPage goToPayableAccPage(String account) throws InterruptedException {

		clickElementAndWait3(By.xpath("//td[.='" + account
				+ "']/parent::tr[@class='mat-table-body']/following-sibling::tr[1]//a[.='Payable Acc']"));

		return new CommissionsFeesPage(webDriver);

	}

	/**
	 * After expand the account, go to the Indemnity Reserve (commission fee)
	 * page by clicking 'Indemnity Reserve' under the account
	 * 
	 * @param account
	 *            the expanded account
	 * @return {@link CommissionsFeesPage}
	 */
	public CommissionsFeesPage goToIndemnityReservePage(String account) throws InterruptedException {

		waitForElementVisible(
				By.xpath("//td[.='" + account
						+ "']/parent::tr[@class='mat-table-body']/following-sibling::tr[1]//a[.='Indemnity Reserve']"),
				30);

		clickElementAndWait3(By.xpath("//td[.='" + account
				+ "']/parent::tr[@class='mat-table-body']/following-sibling::tr[1]//a[.='Indemnity Reserve']"));

		return new CommissionsFeesPage(webDriver);

	}

	/**
	 * After expand the account, go to the Product Provider Account (commission
	 * fee) page by clicking 'Product Provider Acc' under the account
	 * 
	 * @param account
	 *            the expanded account
	 * @return {@link CommissionsFeesPage}
	 */
	public CommissionsFeesPage goToProductProviderAccPage(String account) throws InterruptedException {

		clickElementAndWait3(By.xpath("//td[.='" + account
				+ "']/parent::tr[@class='mat-table-body']/following-sibling::tr[1]//a[.='Product Provider Acc']"));

		return new CommissionsFeesPage(webDriver);

	}

	/**
	 * After expand the account, go to the Reconciliation Account (commission
	 * fee) page by clicking 'Reconciliation Acc' under the account
	 * 
	 * @param account
	 *            the expanded account
	 * @return {@link CommissionsFeesPage}
	 */
	public CommissionsFeesPage goToReconciliationAccPage(String accountName) throws InterruptedException {
		waitForElementVisible(
				By.xpath("//td[.='" + accountName
						+ "']/parent::tr[@class='mat-table-body']/following-sibling::tr[1]//a[.='Reconciliation Acc']"),
				10);

		clickElementAndWait3(By.xpath("//td[.='" + accountName
				+ "']/parent::tr[@class='mat-table-body']/following-sibling::tr[1]//a[.='Reconciliation Acc']"));

		return new CommissionsFeesPage(webDriver);

	}

	/**
	 * After expand the account, go to the Operation Revenue (commission fee)
	 * page by clicking 'Operating Revenues' under the account
	 * 
	 * @param account
	 *            the expanded account
	 * @return {@link CommissionsFeesPage}
	 */
	public CommissionsFeesPage goToOperationRevenuePage(String accountName) throws InterruptedException {

		clickElementAndWait3(By.xpath("//td[.='" + accountName
				+ "']/parent::tr[@class='mat-table-body']/following-sibling::tr[1]//a[.='Operating Revenues']"));

		return new CommissionsFeesPage(webDriver);

	}

	/**
	 * Search the account one by one. If the account is not in the current page,
	 * click rightward arrow to go to next page
	 * 
	 * @param name
	 *            the name of the target account
	 */
	public FeeOverviewPage findAccountOnThisPage(String name) {

		waitForElementVisible(By.id("gwt-debug-FeeOverviewView-feesPanel"), 30);

		boolean found = false;

		int size = getSizeOfElements(By.xpath(".//*[@class='mat-table-body']"));

		int page = getPagesOfElements(By.xpath(".//*[@class='mat-table-body']"));

		System.out.println(page);

		for (int i = 0; i < page; i++) {

			for (int j = 1; j < size; j++) {

				if (getTextByLocator(By.xpath("(.//*[@class='mat-table-body'])[" + j + "]//a")).equals(name)) {
					found = true;
					break;
				}

			}
			if (found) {
				break;
			} else {
				clickElement(By.id("gwt-debug-PagerToolWidget-nextPageImg"));
			}

		}

		return new FeeOverviewPage(webDriver, "Advisor Commissions");
	}

	public CommissionsFeesPage goToAccountByName(String name) throws InterruptedException {

		clickElementAndWait3(By.xpath(".//a[.='" + name + "']"));

		return new CommissionsFeesPage(webDriver);
	}
}
