package org.sly.uitest.pageobjects.commissioning;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.settings.Settings;

/**
 * This page object is for adivosry fees, which can be navigated by Company
 * Setting -> Company Fee Settings or Create model portfolio -> set fees
 * 
 * @author Benny Leung
 * @date 28 Jun 2017
 * @company Prive Financial
 */
public class AdvisoryFeesPage extends AbstractPage {

	private Class<?> theClass = null;

	/**
	 * @param webDriver
	 */
	public AdvisoryFeesPage(WebDriver webDriver, Class<?> theClass) {

		super();
		this.webDriver = webDriver;
		this.theClass = theClass;

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(60, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		try {
			scrollToTop();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gwt-debug-MyMainMaterialView-mainPanel")));

		} catch (Exception ex) {

		}

		assertTrue(pageContainsStr("Advisory Fees"));
	}

	/**
	 * click edit button for different types of current fees
	 * 
	 * @param type
	 * @return {@AdvisoryFeesPage}
	 */
	public AdvisoryFeesPage editCurrentFeesByType(String type) {
		this.clickElement(By.xpath(
				".//td[div[.='" + type + "']]//following-sibling::td//button[@id='gwt-debug-SingleFeeUi-editButton']"));
		return this;
	}

	/**
	 * edit amount of fee
	 * 
	 * @param amount
	 * @return {@AdvisoryFeesPage}
	 */
	public AdvisoryFeesPage editAmountOfAdvisoryFees(String amount) {
		sendKeysToElement(By.xpath(".//div[@id='gwt-debug-FeesDialogue-amountTextBoxPanel']//input"), amount,
				Keys.ENTER);
		return this;
	}

	/**
	 * edit reason of advisory fees
	 * 
	 * @param reason
	 * @return {@AdvisoryFeesPage}
	 */
	public AdvisoryFeesPage editReasonOfAdvisoryFees(String reason) {

		if (isElementVisible(By.id("gwt-debug-FeesDialogue-reasonModelPortfolioEditLnk"))) {
			clickElement(By.id("gwt-debug-FeesDialogue-reasonModelPortfolioEditLnk"));
		}

		this.selectFromDropdown(By.id("gwt-debug-FeesDialogue-reasonsTypeBox"), reason);
		return this;
	}

	/**
	 * edit model portfolio if reason = Model Portfolio
	 * 
	 * @param modelPortfolio
	 * @return
	 */
	public AdvisoryFeesPage editModelPortfolioOfAdvisoryFees(String modelPortfolio) {
		selectFromDropdown(By.id("gwt-debug-FeesDialogue-reasonModelPortfolioList"), modelPortfolio);
		return this;
	}

	/**
	 * edit fee calculation frequency
	 * 
	 * @param frequency
	 * @return {@AdvisoryFeesPage}
	 */
	public AdvisoryFeesPage editFeeCalculationFrequency(String frequency) {
		selectFromDropdown(By.id("gwt-debug-FeesDialogue-calculationFreqList"), frequency);
		return this;
	}

	/**
	 * Click tomorrow for From date
	 * 
	 * @return {@AdvisoryFeesPage}
	 */
	public AdvisoryFeesPage clickFromTomorrow() {
		clickElement(By.id("gwt-debug-FeesDialogue-tomorrowRadio-label"));
		return this;
	}

	/**
	 * edit specific date for From date
	 * 
	 * @param specificDate
	 * @return {@AdvisoryFeesPage}
	 */
	public AdvisoryFeesPage editFromSpecificDate(String specificDate) {
		clickElement(By.id("gwt-debug-FeesDialogue-dateFromRadio-label"));
		sendKeysToElement(
				By.xpath(
						".//td[span[label[@id='gwt-debug-FeesDialogue-dateFromRadio-label']]]//following-sibling::td//input"),
				specificDate);
		return this;
	}

	/**
	 * click Commencement Date for From date
	 * 
	 * @return {@AdvisoryFeesPage}
	 */
	public AdvisoryFeesPage clickCommencementDate() {
		clickElement(By.id("gwt-debug-FeesDialogue-accountStartDateRadio-label"));
		return this;
	}

	/**
	 * click until changed for To date
	 * 
	 * @return {@AdvisoryFeesPage}
	 */
	public AdvisoryFeesPage clickToUntilChanged() {
		clickElement(By.id(".//*[@id='gwt-debug-FeesDialogue-untilRadio-label']"));
		return this;
	}

	/**
	 * edit specific date for To date
	 * 
	 * @param specificDate
	 * @return {@AdvisoryFeesPage}
	 */
	public AdvisoryFeesPage editToSpecificDate(String specificDate) {
		clickElement(By.id("gwt-debug-FeesDialogue-dateToRadio-label"));
		sendKeysToElement(
				By.xpath(
						".//td[span[label[@id='gwt-debug-FeesDialogue-dateToRadio-label']]]//following-sibling::td//input"),
				specificDate);
		return this;
	}

	/**
	 * click Save button to save specific type of fees
	 * 
	 * @return {@AdvisoryFeesPage}
	 */
	public AdvisoryFeesPage clickSaveButttonForFees() {
		clickElement(By.id("gwt-debug-FeesDialogue-saveButton"));
		try {
			waitForElementVisible(By.xpath("(.//*[@id='gwt-debug-CustomDialog-yesButton'])[2]"), Settings.WAIT_SECONDS);
			clickElement(By.xpath("(.//*[@id='gwt-debug-CustomDialog-yesButton'])[2]"));
		} catch (TimeoutException e) {

		}

		return this;
	}

	/**
	 * Click Cancel button to dismiss create fee window
	 * 
	 * @return
	 */
	public AdvisoryFeesPage clickCancelButtonForFees() {
		clickElement(By.id("gwt-debug-FeesDialogue-cancelButton"));
		return this;
	}

	/**
	 * click OK button for advisory fees
	 * 
	 * @return return to the previous page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public <T> T clickOkButtonForAdvisoryFees() throws Exception {
		clickElement(By.id("gwt-debug-CustomDialog-okButton"));
		return (T) this.theClass.getDeclaredConstructor(WebDriver.class).newInstance(webDriver);
	}
}
