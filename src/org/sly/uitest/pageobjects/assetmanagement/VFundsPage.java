package org.sly.uitest.pageobjects.assetmanagement;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.pageobjects.investments.InvestmentsPage;
import org.sly.uitest.settings.Settings;

/**
 * 
 * This class represents the vFunds Page and the vFunds Edit Page, which can be
 * navigated by clicking 'Build' -> 'vFunds'
 * 
 * @author Lynne Huang
 * @date : 11 Aug, 2015
 * @company Prive Financial
 * 
 */
public class VFundsPage extends AbstractPage {

	/**
	 * @param webDriver
	 */
	public VFundsPage(WebDriver webDriver) {

		super();
		this.webDriver = webDriver;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gwt-debug-MyMainMaterialView-mainPanel")));

		} catch (Exception e) {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.id("gwt-debug-StrategyListForManagerView-allocationTablePanel")));

		}

		// assertTrue(pageContainsStr(" My vFunds ")
		// || pageContainsStr("My Personal vFunds"));
	}

	/**
	 * Click the CREATE VFUND button to create a new vFund
	 * 
	 * @return {@link VFundsPage}
	 */
	public VFundsPage clickCreateVfundButton() {

		clickElement(By.id("gwt-debug-StrategyListForManagerView-createButton"));

		return this;
	}

	/**
	 * Click the green chart icon to allocate the vFund with the given name
	 * 
	 * @param name
	 *            the name of the vFund
	 * @return {@link VFundsPage}
	 */
	public VFundsPage clickAllocateVfundsIconByName(String name) {

		waitForElementVisible(
				By.xpath("//td[.='" + name + "']/following-sibling::td[button[@title='Allocate']]/button"), 150);
		clickElement(By.xpath("//td[.='" + name + "']/following-sibling::td[button[@title='Allocate']]/button"));

		return this;
	}

	public VFundsPage clickEditVfundsIconByName(String name) {
		clickElement(By.xpath(".//td[*[contains(text(),'" + name
				+ "')]]//following-sibling::td//button[@id='gwt-debug-StrategyOverviewTable-editButton']"));
		return this;
	}

	/**
	 * Click the pencil icon to edit the vFund with the given name
	 * 
	 * @param name
	 *            the name of the vFund
	 * @return {@link VFundsPage}
	 */
	public VFundsPage editVfundByName(String name) {

		clickElement(By.xpath("//td[.='" + name + "']/following-sibling::td[button[@title='Edit']]/button"));

		return this;
	}

	/**
	 * Click the red icon to delete the vFund with the given name
	 * 
	 * @param name
	 *            the name of the vFund
	 * @return {@link VFundsPage}
	 * @throws InterruptedException
	 */
	public VFundsPage deleteVfundByName(String name) throws InterruptedException {

		clickElement(By.xpath("//td[.='" + name + "']/following-sibling::td[button[@title='Delete']]/button"));
		this.waitForElementVisible(By.id("gwt-debug-CustomDialog-yesButton"), Settings.WAIT_SECONDS);
		clickYesButtonIfVisible();
		return this;
	}

	/**
	 * In the Edit vFund or New vFund page, edit the name of the vFund
	 * 
	 * @param name
	 *            the name of the vFund
	 * @return {@link VFundsPage}
	 */
	public VFundsPage editVfundname(String name) {

		sendKeysToElement(By.id("gwt-debug-StrategyEditManagerView-strategyName"), name);

		return this;
	}

	/**
	 * In the Edit vFund or New vFund page, edit the strategy fee of the vFund
	 * 
	 * @param fee
	 *            the strategy fee of the vFund
	 * @return {@link VFundsPage}
	 */
	public VFundsPage editVfundStrategyFee(String fee) {

		sendKeysToElement(By.id("gwt-debug-StrategyEditManagerView-strategyFees"), fee);

		return this;
	}

	/**
	 * In the Edit vFund or New vFund page, edit the minimum investments of this
	 * vFund
	 * 
	 * @param investment
	 *            the minimum amount of the investment
	 * @return {@link VFundsPage}
	 */
	public VFundsPage editVfundStrategyMinInvestment(String investment) {

		sendKeysToElement(By.id("gwt-debug-StrategyEditManagerView-strategyMinInvestment"), investment);

		return this;
	}

	/**
	 * Click the SUBMIT button after finish creating or editing vFund
	 * 
	 * @return {@link VFundsPage}
	 */
	public VFundsPage clickSubmitButton() {

		clickElement(By.id("gwt-debug-StrategyEditManagerView-submitBtn"));

		return this;
	}

	/**
	 * Click the PUBLISH button after finish reallocating the investments in the
	 * Review/Rebalance vFunds page
	 * 
	 * @return {@link VFundsPage}
	 * @throws InterruptedException
	 */
	public VFundsPage clickPublishButton() throws InterruptedException {

		WebElement element = webDriver.findElement(By.id("gwt-debug-StrategyRebalanceView-submitAllocationButton"));
		element.sendKeys(Keys.RETURN);

		clickOkButtonIfVisible();
		/*
		 * clickElement(By
		 * .id("gwt-debug-StrategyRebalanceView-submitAllocationButton"));
		 */
		return this;
	}

	/**
	 * After click the allocation icon, click REALLOCATE button in the
	 * Review/Rebalance vFund page
	 * 
	 * @return {@link VFundsPage}
	 */
	public VFundsPage clickReallocateVfundsButton() {

		clickElementByKeyboard(By.id("gwt-debug-StrategyRebalanceView-reallocateBtn"));

		return this;
	}

	/**
	 * After click the allocation icon and click REALLOCATE button in the
	 * Review/Rebalance vFund page, click the ADD button to add new investment
	 * 
	 * @return {@link VFundsPage}
	 */
	public InvestmentsPage clickAddButton() {

		WebElement addBtn = webDriver.findElement(By.id("gwt-debug-StrategyRebalanceView-addBtn"));
		new Actions(webDriver).moveToElement(addBtn);
		clickElementByKeyboard(By.id("gwt-debug-StrategyRebalanceView-addBtn"));
		clickOkButtonIfVisible();
		return new InvestmentsPage(webDriver, VFundsPage.class);

	}

	/**
	 * After allocate the vFund, reallocate the investments, and add ticker to
	 * the vFund, click the plus button to set the allocation for the new ticker
	 * 
	 * @param investment
	 *            the name of the investment to be reallocated
	 * @param times
	 *            the times to click the plus button
	 * 
	 * @throws InterruptedException
	 * 
	 */
	public VFundsPage editNewAllocationByPlusButton(String investment, int times) throws InterruptedException {

		for (int i = 0; i < times; i++) {
			waitForElementVisible(
					By.xpath("//*[contains(text(), '" + investment
							+ "')]/parent::td/following-sibling::td//button[@id='gwt-debug-TextBoxPercentageSpinner-plusButton']"),
					5);
			clickElement(By.xpath("//*[contains(text(), '" + investment
					+ "')]/parent::td/following-sibling::td//button[@id='gwt-debug-TextBoxPercentageSpinner-plusButton']"));
		}
		wait(Settings.WAIT_SECONDS);
		return this;
	}

	/**
	 * After allocate the vFund, reallocate the investments, and add ticker to
	 * the vFund, click the minus button to set the allocation for the new
	 * ticker
	 * 
	 * @param investment
	 *            the name of the investment to be reallocated
	 * @param times
	 *            the times to click the minus button
	 * 
	 * @throws InterruptedException
	 * 
	 */
	public VFundsPage editNewAllocationByMinusButton(String investment, int times) {

		for (int i = 0; i < times; i++) {

			clickElement(By.xpath("//td[.='" + investment
					+ "']/following-sibling::td//button[@id='gwt-debug-TextBoxPercentageSpinner-minusButton']"));

		}

		return this;
	}

	/**
	 * When the page was navigated to the vFund Rebalancing History page, click
	 * the downward double-arrow to expand the vFund with the given date
	 * 
	 * @param date
	 *            the date of the history
	 * @return {@link VFundsPage}
	 */
	public VFundsPage expandVfundRebalancingHistory(String date) {

		if (date.equals("")) {

			// expand the first newest one
			new Actions(webDriver)
					.moveToElement(webDriver.findElement((By
							.xpath(".//*[@id='gwt-debug-StrategyRebalancingHistorySingleView-tableDetailsToggle']/div/img"))))
					.perform();
			clickElement(
					By.xpath(".//*[@id='gwt-debug-StrategyRebalancingHistorySingleView-tableDetailsToggle']/div/img"));

		} else {

			clickElement(By.xpath("//td[div[.='" + date + "']]/preceding-sibling::td[1]//img"));

		}

		return this;
	}

}
