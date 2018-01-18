package org.sly.uitest.pageobjects.clientsandaccounts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;

/**
 * This page is the transaction linking page which appears after clicking
 * link/unlink transactions
 * 
 * @author Benny Leung
 * @Date Oct 27, 2016
 * @Company Prive Financial
 */
public class LinkTransactionPage extends AbstractPage {

	private Class<?> theClass = null;

	public LinkTransactionPage(WebDriver webDriver, Class<?> theClass) throws InterruptedException {

		super();
		this.webDriver = webDriver;
		this.theClass = theClass;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gwt-debug-MyMainMaterialView-mainPanel")));

	}

	/**
	 * click match button in link transaction page
	 * 
	 * @return {@link LinkTransactionPage}
	 */
	@SuppressWarnings("unchecked")
	public <T> T clickMatchButton() throws Exception {

		try {
			waitForElementVisible(By.id("gwt-debug-TransactionMatchPopupView-matchButton"), 10);

			clickElement(By.id("gwt-debug-TransactionMatchPopupView-matchButton"));
		} catch (TimeoutException e) {

		}

		clickYesButtonIfVisible();
		clickOkButtonIfVisible();

		if (isElementVisible(By.id("gwt-debug-TransactionMatchPopupView-matchButton"))) {
			clickElementByKeyboard(By.id("gwt-debug-TransactionMatchPopupView-matchButton"));
			clickYesButtonIfVisible();
			clickOkButtonIfVisible();
		}

		if (theClass.getName().equals(this.getClass().getName())) {
			return (T) this;
		} else {
			return (T) this.theClass.getDeclaredConstructor(WebDriver.class).newInstance(webDriver);
		}

	}

	/**
	 * click Unmatch button in link transaction page
	 * 
	 * @return {@link LinkTransactionPage}
	 * @throws InterruptedException
	 */
	public LinkTransactionPage clickUnmatchButton() throws InterruptedException {

		try {
			waitForElementVisible(By.id("gwt-debug-TransactionUnMatchPopupView-unMatchButton"), 10);

			clickElementByKeyboard(By.id("gwt-debug-TransactionUnMatchPopupView-unMatchButton"));
		} catch (TimeoutException e) {

		}
		clickYesButtonIfVisible();
		clickOkButtonIfVisible();
		return this;
	}

	/**
	 * click Create and Match button in link transaction page
	 * 
	 * @return {@link LinkTransactionPage}
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public <T> T clickCreateAndMatchButton() throws Exception {

		waitForElementVisible(By.id("gwt-debug-TransactionMatchPopupView-createMatchButton"), 10);

		clickElementByKeyboard(By.id("gwt-debug-TransactionMatchPopupView-createMatchButton"));
		clickYesButtonIfVisible();
		clickOkButtonIfVisible();

		if (theClass.getName().equals(this.getClass().getName())) {
			return (T) this;
		} else {
			return (T) this.theClass.getDeclaredConstructor(WebDriver.class).newInstance(webDriver);
		}
	}

	/**
	 * click Amend Transaction button in LinkTransactionPage
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public <T> T clickAmendTransactionButton() throws Exception {

		waitForElementVisible(By.id("gwt-debug-TransactionMisMatchPopupView-amendButton"), 10);

		clickElement(By.id("gwt-debug-TransactionMisMatchPopupView-amendButton"));

		clickOkButtonIfVisible();
		if (theClass.getName().equals(this.getClass().getName())) {
			return (T) this;
		} else {
			return (T) this.theClass.getDeclaredConstructor(WebDriver.class).newInstance(webDriver);
		}
	}

	public HistoryPage startBreakWorkflow(String details) throws InterruptedException {

		clickElement(By.id("gwt-debug-TransactionMisMatchPopupView-workflowButton"));

		waitForElementVisible(By.id("gwt-debug-TransactionBreakWorkflowPopupView-breakDetailsTextArea"), 10);
		sendKeysToElement(By.id("gwt-debug-TransactionBreakWorkflowPopupView-breakDetailsTextArea"), details);

		clickElement(By.id("gwt-debug-TransactionBreakWorkflowPopupView-yesButton"));
		clickYesButtonIfVisible();

		return new HistoryPage(webDriver);
	}

	/**
	 * click Cancel button in link transaction page
	 * 
	 * @return {@link LinkTransactionPage}
	 * @throws InterruptedException
	 */
	public HistoryPage clickCancelButton() throws InterruptedException {

		waitForElementVisible(By.xpath(".//*[contains(@id,'cancelButton')]"), 10);

		clickElement(By.xpath(".//*[contains(@id,'cancelButton')]"));

		return new HistoryPage(webDriver);
	}

	/**
	 * edit date to show history in link transaction page
	 * 
	 * @param from
	 * @param to
	 * @return {@link LinkTransactionPage}
	 */
	public LinkTransactionPage editDateForTransaction(String from, String to) {

		waitForElementVisible(By.id("gwt-debug-TransactionMatchPopupView-fetchButton"), 10);

		sendKeysToElementForLinkTransaction(By.id("gwt-debug-TransactionMatchPopupView-toDateTextBox"), to);

		sendKeysToElementForLinkTransaction(By.id("gwt-debug-TransactionMatchPopupView-fromDateTextBox"), from);

		clickElementByKeyboard(By.id("gwt-debug-TransactionMatchPopupView-fetchButton"));

		try {
			waitForElementVisible(By.id("gwt-debug-TransactionPortfolioOrderView-descLabel"), 5);
		} catch (TimeoutException e) {

		}
		return this;
	}

	/**
	 * click Show Only Unmatched Transaction checkbox
	 * 
	 * @param show
	 *            decide whether to click
	 * @return {@link LinkTransactionPage}
	 */
	public LinkTransactionPage clickShowOnlyUnmatchedTransactionsBox(boolean show) {
		waitForElementVisible(By.id("gwt-debug-TransactionMatchPopupView-showUnmatchedOnlyCheckbox-input"), 10);

		WebElement elem = webDriver
				.findElement(By.id("gwt-debug-TransactionMatchPopupView-showUnmatchedOnlyCheckbox-input"));
		setCheckboxStatus2(elem, show);

		return this;
	}

	/**
	 * click checkbox for transaction
	 * 
	 * @param transaction
	 *            name of transaction
	 * @param click
	 *            decide whether to click
	 * @return
	 */
	public LinkTransactionPage clickCheckboxForTransaction(String transaction, boolean toClick) {
		WebElement elem;
		waitForElementVisible(By
				.xpath("(.//*[@id='gwt-debug-TransactionMatchPopupView-optionTransactionsHolder']//td[*[contains(text(), '"
						+ transaction + "')]]//preceding-sibling::td//span//input)[1]"),
				60);
		try {
			elem = webDriver.findElement(By
					.xpath("(.//*[@id='gwt-debug-TransactionMatchPopupView-optionTransactionsHolder']//td[*[contains(text(), '"
							+ transaction
							+ "')]]//preceding-sibling::td[a[@aria-hidden]]//preceding-sibling::td//span//input)[1]"));
		} catch (NoSuchElementException e) {
			elem = webDriver.findElement(By
					.xpath("(.//*[@id='gwt-debug-TransactionMatchPopupView-optionTransactionsHolder']//td[*[contains(text(), '"
							+ transaction + "')]]//preceding-sibling::td//span//input)[1]"));
		}
		setCheckboxStatus2(elem, toClick);

		return this;
	}

	public LinkTransactionPage clickCheckboxForTransactionNotApproved(String transaction, boolean toClick) {
		waitForElementVisible(By
				.xpath("(.//*[@id='gwt-debug-TransactionMatchPopupView-optionTransactionsHolder']//td[div[contains(text(), '"
						+ transaction + "')]]//preceding-sibling::td//span//input)[1]"),
				60);

		WebElement elem = webDriver.findElement(By
				.xpath("(.//*[@id='gwt-debug-TransactionMatchPopupView-optionTransactionsHolder']//td[div[contains(text(), '"
						+ transaction + "')]]//preceding-sibling::td//span//input)[1]"));

		setCheckboxStatus2(elem, toClick);

		return this;
	}

	public void sendKeysToElementForLinkTransaction(By locator, CharSequence... charSequences) {

		waitForElementVisible(locator, 30);

		WebElement elem = waitGet(locator);

		elem.sendKeys(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END), Keys.BACK_SPACE);
		elem.sendKeys(charSequences);

	}
}
