package org.sly.uitest.pageobjects.operations;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;

/**
 * This class represents the Risk Profile page, which can be navigated by
 * clicking 'Operations' -> 'Last Account Value Update'
 * 
 * @author Lynne Huang
 * @date : 6 Oct, 2015
 * @company Prive Financial
 */
public class LastAccountValueUpdatePage extends AbstractPage {

	/**
	 * @param webDriver
	 */
	public LastAccountValueUpdatePage(WebDriver webDriver) {

		super();
		this.webDriver = webDriver;

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		try {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.id("gwt-debug-LastAccountUpdateView-submitButton")));

		} catch (Exception ex) {
			// wait.until(ExpectedConditions.visibilityOfElementLocated(By
			// .id("gwt-debug-MyMainMaterialView-mainPanel")));

		}

		assertTrue(pageContainsStr(" Last Account Value Update "));

	}

	/**
	 * On the Last Account Value Update page, verify if the date of the
	 * Reference Date is correct or not
	 * 
	 * @return {@link LastAccountValueUpdatePage}
	 * @throws ParseException
	 */
	public LastAccountValueUpdatePage verifyReferenceDate(String today) throws ParseException {

		String format = "d-MMM-yyyy";
		String today1 = getCurrentTimeInFormat(format);

		waitForElementVisible(By.xpath(".//div[@id='gwt-debug-LastAccountUpdateView-valueDate']"), 10);
		try {
			assertTrue(getTextByLocator(By.xpath(".//div[@id='gwt-debug-LastAccountUpdateView-valueDate']"))
					.contains(today));
		} catch (AssertionError e) {
			assertTrue(getTextByLocator(By.xpath(".//div[@id='gwt-debug-LastAccountUpdateView-valueDate']"))
					.contains(today1));
		}

		return this;
	}

	/**
	 * On the Last Account Value Update page, edit the date range
	 * 
	 * @param range
	 * 
	 * @return {@link LastAccountValueUpdatePage}
	 */
	public LastAccountValueUpdatePage editDateRange(String range) {

		selectFromDropdown(By.id("gwt-debug-LastAccountUpdateView-filterList"), range);

		return this;
	}

	/**
	 * On the Last Account Value Update page, add data sources
	 * 
	 * @param sources
	 * 
	 * @return {@link LastAccountValueUpdatePage}
	 */
	public LastAccountValueUpdatePage editAddDataSource(String... sources) {

		clickElement(By.xpath(".//button[@id='gwt-debug-MultiSelectDropDownBox-dropDown']/i"));

		// clickElement(By.id("gwt-debug-MultiSelectDropDownBox-dropDown"));

		for (String source : sources) {
			waitForElementVisible(By.xpath("//label[.='" + source + "']/preceding-sibling::input"), 10);
			clickElement(By.xpath("//label[.='" + source + "']/preceding-sibling::input"));
		}

		return this;
	}

	/**
	 * On the Last Account Value Update page, remove data sources
	 * 
	 * @param sources
	 * 
	 * @return {@link LastAccountValueUpdatePage}
	 * @throws InterruptedException
	 */
	public LastAccountValueUpdatePage editRemoveDataSource(String... sources) throws InterruptedException {

		WebElement elem = webDriver.findElement(By.xpath(".//button[@id='gwt-debug-MultiSelectDropDownBox-dropDown']"));
		clickElement(By.id("gwt-debug-MultiSelectDropDownBox-dropDown"));

		if (!isElementVisible(By.xpath("//label[.='" + sources + "']/preceding-sibling::input"))) {

			clickElement(By.id("gwt-debug-MultiSelectDropDownBox-dropDown"));

		}
		for (String source : sources) {

			WebElement we = webDriver.findElement(By.xpath("//label[.='" + source + "']/preceding-sibling::input"));

			setCheckboxStatus2(we, false);
		}

		return this;
	}

	/**
	 * On the Last Account Value Update page, select an option to exclude
	 * accounts
	 * 
	 * @param accounts
	 *            the option
	 * 
	 * @return {@link LastAccountValueUpdatePage}
	 */
	public LastAccountValueUpdatePage editExcludeAccounts(String accounts) {

		selectFromDropdown(By.id("gwt-debug-LastAccountUpdateView-excludeAccountsDate"), accounts);

		return this;
	}

	/**
	 * On the Last Account Value Update page, check the checkbox of the Include
	 * accounts where update source is set to manual
	 * 
	 * @param included
	 *            if true, check; if false, uncheck
	 * 
	 * @return {@link LastAccountValueUpdatePage}
	 */
	public LastAccountValueUpdatePage checkIncludeManualAccounts(Boolean included) {

		WebElement we = webDriver.findElement(By.id("gwt-debug-LastAccountUpdateView-includeManual-input"));

		setCheckboxStatus2(we, included);

		return this;
	}

	/**
	 * On the Last Account Value Update page, check the checkbox of the Include
	 * inactive accounts
	 * 
	 * @param included
	 *            if true, check; if false, uncheck
	 * 
	 * @return {@link LastAccountValueUpdatePage}
	 */
	public LastAccountValueUpdatePage checkIncludeInactiveAccounts(Boolean included) {

		WebElement we = webDriver.findElement(By.id("gwt-debug-LastAccountUpdateView-includeInAciveAccount-input"));

		setCheckboxStatus2(we, included);

		return this;
	}

	/**
	 * On the Last Account Value Update page, click the PROCESS button
	 */
	public LastAccountValueUpdatePage clickProcessButton() {

		waitForElementVisible(By.id("gwt-debug-LastAccountUpdateView-submitButton"), 30);

		clickElement(By.id("gwt-debug-LastAccountUpdateView-submitButton"));

		if (!isElementDisplayed(By.id("gwt-debug-LastAccountUpdateView-noResultLabel"))) {

			FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(120, TimeUnit.SECONDS)
					.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gwt-debug-SortableFlexTableAsync-table")));
		}

		return this;
	}
}
