package org.sly.uitest.pageobjects.companyadmin;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.pageobjects.investments.InvestmentsPage;

/**
 * This class represents the Alerts Page, which can be navigated by clicking
 * 'Manage' -> 'White List' or clicking the 'User Alert'image icon
 * 
 * @author Lynne Huang
 * @date : 25 Sep, 2015
 * @company Prive Financial
 */
public class WhiteListsPage extends AbstractPage {

	public WhiteListsPage(WebDriver webDriver) {

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
			// .id("gwt-debug-MyMainMaterialView-mainPanel")));

		}

		assertTrue(pageContainsStr("White Lists"));

	}

	/**
	 * On the White List page, click the CREATE WHITE LIST button
	 * 
	 * @return {@link WhiteListsPage}
	 * @throws InterruptedException
	 * 
	 */
	public WhiteListsPage clickCreateWhiteListButton() throws InterruptedException {
		scrollToTop();
		wait(2);
		waitForElementVisible(By.xpath("/.//*[@id='gwt-debug-TabCustomWhiteListView-createWhiteListButton']"), 60);

		new Actions(webDriver)
				.moveToElement(webDriver
						.findElement(By.xpath("/.//*[@id='gwt-debug-TabCustomWhiteListView-createWhiteListButton']")))
				.perform();

		clickElement(By.xpath("/.//*[@id='gwt-debug-TabCustomWhiteListView-createWhiteListButton']"));
		wait(2);
		return this;
	}

	/**
	 * On the White List page, click the pencil icon to edit the white list with
	 * the given name
	 * 
	 * @param name
	 * 
	 * @return {@link WhiteListsPage}
	 */
	public WhiteListsPage editWhiteListByName(String name) {

		clickElementByKeyboard(By.xpath("//td[.='" + name
				+ "']/following-sibling::td[//button]//button[@id='gwt-debug-WhiteListOverviewPresenter-editImage']"));

		return this;
	}

	/**
	 * On the White List page, click the red-white minus icon to delete the
	 * white list with the given name
	 * 
	 * @param name
	 * 
	 * @return {@link WhiteListsPage}
	 */
	public WhiteListsPage deleteWhiteListByName(String name) {

		clickElementByKeyboard(By.xpath("//td[.='" + name
				+ "']/following-sibling::td[//button]//button[@id='gwt-debug-WhiteListOverviewPresenter-removeImage']"));

		clickYesButtonIfVisible();

		return this;
	}

	/**
	 * After click the CREATE WHITE LIST button or click the pencil icon, on the
	 * popup Edit White List page, edit the white list name
	 * 
	 * @param name
	 * 
	 * @return {@link WhiteListsPage}
	 * @throws InterruptedException
	 */
	public WhiteListsPage editWhiteListName(String name) throws InterruptedException {
		try {
			waitForElementVisible(By.id("gwt-debug-EditWhiteListView-nameTextBox"), 150);
		} catch (Exception ex) {
			// wait(Settings.WAIT_SECONDS);
		}
		sendKeysToElement(By.id("gwt-debug-EditWhiteListView-nameTextBox"), name);
		return this;
	}

	/**
	 * After click the CREATE WHITE LIST button or click the pencil icon, on the
	 * popup Edit White List page, edit the white list description
	 * 
	 * @param description
	 * 
	 * @return {@link WhiteListsPage}
	 */
	public WhiteListsPage editWhiteListDescription(String description) {

		sendKeysToElement(By.id("gwt-debug-EditWhiteListView-descriptionTextArea"), description);

		return this;
	}

	/**
	 * After click the CREATE WHITE LIST button or click the pencil icon, on the
	 * popup Edit White List page, edit the white list assets
	 * 
	 * @param assets
	 * 
	 * @return {@link WhiteListsPage}
	 */
	public WhiteListsPage editWhiteListAssets(Boolean add, String... assets) {

		if (add) {
			for (String asset : assets) {
				selectFromDropdown(
						By.xpath(
								".//*[@id='gwt-debug-EditWhiteListView-strategyTypeListBox']//select[@id='gwt-debug-PairedListBoxSelector-sourceList']"),
						asset);
			}
			clickElement(By.xpath(
					".//*[@id='gwt-debug-EditWhiteListView-strategyTypeListBox']//img[@id='gwt-debug-PairedListBoxSelector-addImg']"));
		} else {
			for (String asset : assets) {
				selectFromDropdown(
						By.xpath(
								".//*[@id='gwt-debug-EditWhiteListView-strategyTypeListBox']//select[@id='gwt-debug-PairedListBoxSelector-targetList']"),
						asset);
			}
			clickElement(By.xpath(
					".//*[@id='gwt-debug-EditWhiteListView-strategyTypeListBox']//img[@id='gwt-debug-PairedListBoxSelector-removeImg']"));
		}

		return this;
	}

	/**
	 * After click the CREATE WHITE LIST button or click the pencil icon, on the
	 * popup Edit White List page, edit the white list currency
	 * 
	 * @param currency
	 * 
	 * @return {@link WhiteListsPage}
	 */
	public WhiteListsPage editWhiteListCurrency(Boolean add, String... currencies) {

		if (add) {
			for (String currency : currencies) {
				selectFromDropdown(
						By.xpath(
								".//*[@id='gwt-debug-EditWhiteListView-currencyListBox']//select[@id='gwt-debug-PairedListBoxSelector-sourceList']"),
						currency);
			}
			clickElement(By.xpath(
					".//*[@id='gwt-debug-EditWhiteListView-currencyListBox']//img[@id='gwt-debug-PairedListBoxSelector-addImg']"));
		} else {
			for (String currency : currencies) {
				selectFromDropdown(
						By.xpath(
								".//*[@id='gwt-debug-EditWhiteListView-currencyListBox']//select[@id='gwt-debug-PairedListBoxSelector-targetList']"),
						currency);
			}
			clickElement(By.xpath(
					".//*[@id='gwt-debug-EditWhiteListView-currencyListBox']//img[@id='gwt-debug-PairedListBoxSelector-removeImg']"));
		}

		return this;
	}

	/**
	 * After click the CREATE WHITE LIST button or click the pencil icon, on the
	 * popup Edit White List page, edit the white list product providers
	 * 
	 * @param product
	 *            providers
	 * 
	 * @return {@link WhiteListsPage}
	 * @throws InterruptedException
	 */
	public WhiteListsPage editWhiteListProductProviders(Boolean add, String... providers) throws InterruptedException {

		waitForElementVisible(By.xpath(".//*[@id='gwt-debug-EditWhiteListView-productProviderListBox']"), 300);

		if (add) {
			waitForElementVisible(
					By.xpath(
							".//*[@id='gwt-debug-EditWhiteListView-productProviderListBox']//select[@id='gwt-debug-PairedListBoxSelector-sourceList']/option"),
					300);
			for (String provider : providers) {
				selectFromDropdown(
						By.xpath(
								".//*[@id='gwt-debug-EditWhiteListView-productProviderListBox']//select[@id='gwt-debug-PairedListBoxSelector-sourceList']"),
						provider);
				// wait for a while to choose next option
				wait(2);
			}
			clickElement(By.xpath(
					".//*[@id='gwt-debug-EditWhiteListView-productProviderListBox']//img[@id='gwt-debug-PairedListBoxSelector-addImg']"));
		} else {
			for (String provider : providers) {
				selectFromDropdown(
						By.xpath(
								".//*[@id='gwt-debug-EditWhiteListView-productProviderListBox']//select[@id='gwt-debug-PairedListBoxSelector-targetList']"),
						provider);
			}
			clickElement(By.xpath(
					".//*[@id='gwt-debug-EditWhiteListView-productProviderListBox']//img[@id='gwt-debug-PairedListBoxSelector-removeImg']"));
		}

		return this;
	}

	/**
	 * After click the CREATE WHITE LIST button or click the pencil icon, on the
	 * popup Edit White List page, edit the white list products
	 * 
	 * @param products
	 * 
	 * @return {@link WhiteListsPage}
	 */
	public WhiteListsPage editWhiteListProducts(Boolean add, String... products) {

		if (add) {
			for (String product : products) {
				selectFromDropdown(
						By.xpath(
								".//*[@id='gwt-debug-EditWhiteListView-platformListBox']//select[@id='gwt-debug-PairedListBoxSelector-sourceList']"),
						product);
			}
			clickElement(By.xpath(
					".//*[@id='gwt-debug-EditWhiteListView-platformListBox']//img[@id='gwt-debug-PairedListBoxSelector-addImg']"));
		} else {
			for (String product : products) {
				selectFromDropdown(
						By.xpath(
								".//*[@id='gwt-debug-EditWhiteListView-platformListBox']//select[@id='gwt-debug-PairedListBoxSelector-targetList']"),
						product);
			}
			clickElement(By.xpath(
					".//*[@id='gwt-debug-EditWhiteListView-platformListBox']//img[@id='gwt-debug-PairedListBoxSelector-removeImg']"));
		}

		return this;
	}

	/**
	 * After click the CREATE WHITE LIST button or click the pencil icon, on the
	 * popup Edit White List page, add the white list Investments to Include
	 * 
	 * 
	 * @return {@link InvestmentsPage}
	 */
	public InvestmentsPage editInvestmentsToInclude_Add() {

		clickElementByKeyboard(By.xpath(".//*[@id='gwt-debug-EditWhiteListView-includedSelector']//button[.='Add']"));

		return new InvestmentsPage(webDriver, WhiteListsPage.class);
	}

	/**
	 * After click the CREATE WHITE LIST button or click the pencil icon, on the
	 * popup Edit White List page, remove the white list Investments to Include
	 * 
	 * @param investments
	 * 
	 * @return {@link WhiteListsPage}
	 */
	public WhiteListsPage editInvestmentsToInclude_Remove(String... investments) {

		for (String investment : investments) {

			selectFromDropdown(By.xpath(".//*[@id='gwt-debug-EditWhiteListView-includedSelector']//select"),
					investment);
		}

		clickElement(By.xpath(".//*[@id='gwt-debug-EditWhiteListView-includedSelector']//button[.='Remove']"));

		return this;
	}

	/**
	 * After click the CREATE WHITE LIST button or click the pencil icon, on the
	 * popup Edit White List page, add the white list Investments to Exclude
	 *
	 * 
	 * @return {@link InvestmentsPage}
	 */
	public InvestmentsPage editInvestmentsToExclude_Add() {

		clickElementByKeyboard(By.xpath(".//*[@id='gwt-debug-EditWhiteListView-excludedSelector']//button[.='Add']"));

		return new InvestmentsPage(webDriver, WhiteListsPage.class);
	}

	/**
	 * After click the CREATE WHITE LIST button or click the pencil icon, on the
	 * popup Edit White List page, remove the white list Investments to Include
	 * 
	 * @param investments
	 * 
	 * @return {@link WhiteListsPage}
	 */
	public WhiteListsPage editInvestmentsToExclude_Remove(String... investments) {

		for (String investment : investments) {

			selectFromDropdown(By.xpath(".//*[@id='gwt-debug-EditWhiteListView-excludedSelector']//select"),
					investment);
		}

		clickElement(By.xpath(".//*[@id='gwt-debug-EditWhiteListView-excludedSelector']//button[.='Remove']"));

		return this;
	}

	/**
	 * After click the CREATE WHITE LIST button or click the pencil icon, on the
	 * popup Edit White List page, edit the Approved for Sale
	 * 
	 * @param add
	 * @param countries
	 * 
	 * @return {@link InvestmentsPage}
	 */
	public WhiteListsPage editWhiteListApprovedForSale(Boolean add, String... countries) {

		if (add) {
			for (String country : countries) {
				selectFromDropdown(
						By.xpath(
								".//*[@id='gwt-debug-EditWhiteListView-approvedForSaleSelector']//select[@id='gwt-debug-PairedListBoxSelector-sourceList']"),
						country);
			}
			clickElement(By.xpath(
					".//*[@id='gwt-debug-EditWhiteListView-approvedForSaleSelector']//img[@id='gwt-debug-PairedListBoxSelector-addImg']"));
		} else {
			for (String country : countries) {
				selectFromDropdown(
						By.xpath(
								".//*[@id='gwt-debug-EditWhiteListView-approvedForSaleSelector']//select[@id='gwt-debug-PairedListBoxSelector-targetList']"),
						country);
			}
			clickElement(By.xpath(
					".//*[@id='gwt-debug-EditWhiteListView-approvedForSaleSelector']//img[@id='gwt-debug-PairedListBoxSelector-removeImg']"));
		}

		return this;
	}

	/**
	 * After click the CREATE WHITE LIST button or click the pencil icon, on the
	 * popup Edit White List page, click the SUBMIT button
	 * 
	 * @return {@link WhiteListsPage}
	 */
	public WhiteListsPage clickSubmitButton() {

		clickElement(By.id("gwt-debug-EditWhiteListView-submitButtn"));
		this.waitForWaitingScreenNotVisible();
		return this;
	}

}
