package org.sly.uitest.pageobjects.companyadmin;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.pageobjects.clientsandaccounts.DetailEditPage;
import org.sly.uitest.pageobjects.clientsandaccounts.DetailPage;

/**
 * This class represents the Alerts Page, which can be navigated by clicking
 * 'Manage' -> 'Employee' or clicking the 'User Alert'image icon
 * 
 * @author Lynne Huang
 * @date : 9 Oct, 2015
 * @company Prive Financial
 */
public class EmployeePage extends AbstractPage {

	/**
	 * @param webDriver
	 */
	public EmployeePage(WebDriver webDriver) {

		super();
		this.webDriver = webDriver;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gwt-debug-SortableFlexTableAsync-table")));

		// assertTrue(pageContainsStr(" Employee "));
	}

	/**
	 * On the Employee page, click the NEW button to create a new employee
	 * 
	 * @return {@link DetailEditPage}
	 * @throws InterruptedException
	 */
	public DetailEditPage clickNewButton() throws InterruptedException {
		wait(2);
		clickElement(By.id("gwt-debug-CompanyAdminView-addAdminBtn"));

		return new DetailEditPage(webDriver, EmployeePage.class);
	}

	/**
	 * On the Employee page, click the pencil icon to edit the employee with the
	 * given name
	 * 
	 * @param employee
	 *            the name of the employee
	 * 
	 * @return {@link DetailEditPage}
	 */
	public DetailEditPage editEmployee(String employee) {

		clickElement(By.xpath("//td[.='" + employee + "']/following-sibling::td[button]/button[@title='Edit']"));

		return new DetailEditPage(webDriver, EmployeePage.class);
	}

	/**
	 * Do simple searching on the employee page
	 * 
	 * @param employee
	 * @return {@link EmployeePage}
	 */
	public EmployeePage simpleSearchEmployee(String employee) {

		waitForElementVisible(By.id("gwt-debug-CompanyAdminView-searchBox"), 10);

		sendKeysToElement(By.id("gwt-debug-CompanyAdminView-searchBox"), employee);

		clickElement(By.id("gwt-debug-CompanyAdminView-searchBtn"));
		return this;
	}

	public DetailPage clickEmployee(String employee) {
		clickElement(By.xpath(".//a[@id='gwt-debug-AdvisorAccountTable-advisorAccountName' and contains(text(),'"
				+ employee + "')]"));
		return new DetailPage(webDriver);
	}
}
