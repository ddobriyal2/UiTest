package org.sly.uitest.pageobjects.assetmanagement;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;

/**
 * This class represents the Structured Product Page and the Structured Product
 * Edit Page, which can be navigated by clicking 'Build' -> 'Structured Product'
 * 
 * URL: http://192.168.1.104:8080/SlyAWS/?locale=en_US#structuredproduct
 * "http://192.168.1.104:8080/SlyAWS/?locale=en_US#editstructuredproduct;structuredProductKey=121"
 * 
 * @author Lynne Huang
 * @date : 24 Aug, 2015
 * @company Prive Financial
 * 
 *          PAGE NAVIGATION: Build -> Structured Product
 */
public class StructuredProductPage extends AbstractPage {

	/**
	 * @param webDriver
	 */
	public StructuredProductPage(WebDriver webDriver) {

		super();
		this.webDriver = webDriver;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gwt-debug-MyMainMaterialView-mainPanel")));

		} catch (Exception ex) {

		}

		assertTrue(pageContainsStr("My Structured Products") || pageContainsStr(" Edit Structured Product "));

	}

	/**
	 * Click the NEW button to create a new structured product
	 * 
	 * @return {@link StructuredProductPage}
	 */
	public StructuredProductPage clickNewButtonTocCreateStructuredProduct() {

		clickElementByKeyboard(By.id("gwt-debug-StructuredProductListForManagerPresenter-newButton"));

		return this;

	}

	/**
	 * Click the pencil icon to edit the existing structured product with the
	 * given name
	 * 
	 * @param name
	 *            the name of the existing structured product
	 * @return {@link StructuredProductPage}
	 */
	public StructuredProductPage editExistingStructuredProduct(String name) {

		clickElement(By.xpath("//td[.='" + name + "']/following-sibling::td[2]/button[@title='Edit']"));

		return this;
	}

	/**
	 * In the Edit Structured Product Page, edit the name of the structured
	 * product
	 * 
	 * @param name
	 *            the name of the structured product
	 * @return {@link StructuredProductPage}
	 */
	public StructuredProductPage editStructuredProductName(String name) {

		sendKeysToElement(By.id("gwt-debug-StructuredProductEditView-structuredProductName"), name);

		return this;
	}

	/**
	 * In the Edit Structured Product Page, edit the description of the
	 * structured product
	 * 
	 * @param description
	 *            the description of the structured product
	 * @return {@link StructuredProductPage}
	 */
	public StructuredProductPage editStructuredProductDescription(String description) {

		sendKeysToElement(By.id("gwt-debug-StructuredProductEditView-structuredProductDescription"), description);

		return this;
	}

	/**
	 * In the Edit Structured Product Page, edit the currency of the structured
	 * product
	 * 
	 * @param currency
	 *            the currency of the structured product
	 * @return {@link StructuredProductPage}
	 */
	public StructuredProductPage editStructuredProductCurrency(String currency) {

		selectFromDropdown(By.id("gwt-debug-StructuredProductEditView-structuredProductCurrency"), currency);

		return this;
	}

	/**
	 * In the Edit Structured Product Page, select the underlying for the
	 * structured product
	 * 
	 * @param underlying
	 * @return {@link StructuredProductPage}
	 */
	public StructuredProductPage editStructuredProductUnderlying(String underlying) {

		selectFromDropdown(By.id("gwt-debug-StructuredProductEditView-structuredProductUnderlying"), underlying);

		return this;
	}

	/**
	 * In the Edit Structured Product Page, select the visibility for the
	 * structured product
	 * 
	 * @param visibility
	 *            - Visible to selected advisors, Visible to colleagues, Visible
	 *            to creator
	 * @return {@link StructuredProductPage}
	 */
	public StructuredProductPage editStructuredProductVisibility(String visibility) {

		selectFromDropdown(By.id("gwt-debug-StructuredProductEditView-structuredProductVisibility"), visibility);

		return this;
	}

	/**
	 * In the Edit Structured Product Page, edit the payoff for the structured
	 * product
	 * 
	 * @param payoff
	 *            - Vanilla Call, Vanilla Put, Averaging Call, Averaging Put,
	 *            Digital Option, Barrier Call, Barrier Put, Fixed Cashflow
	 * @return {@link StructuredProductPage}
	 */
	public StructuredProductPage editStructuredProductPayoff(String payoff) {

		clickElement(By.id("gwt-debug-StructuredProductEditView-addPayoffButton1"));

		selectFromDropdown(By.xpath("//div[@title='Add Payoff']/center/select"), payoff);

		clickElement(By.id("gwt-debug-PayoffAddDialog-tickerAddButton"));

		return this;
	}

	/**
	 * In the Edit Structured Product Page, click the red cross icon to delete
	 * the payoff
	 * 
	 * @return {@link StructuredProductPage}
	 */
	public StructuredProductPage deleteStructuredProductPayoff() {

		clickElement(By.id("gwt-debug-PayoffWidgetView-deleteButton"));

		return this;
	}

	/**
	 * In the Edit Structured Product Page, click SAVE button to save the
	 * structured product
	 * 
	 * @return {@link StructuredProductPage}
	 */
	public StructuredProductPage clickSaveButton() {

		clickElementByKeyboard(By.id("gwt-debug-StructuredProductEditView-submitButton"));

		return this;
	}

	/**
	 * In the Edit Structured Product Page, click CANCEL button
	 * 
	 * @return {@link StructuredProductPage}
	 */
	public StructuredProductPage clickCancelButton() {

		clickElement(By.id("gwt-debug-StructuredProductEditView-cancelButton"));

		return this;
	}
}
