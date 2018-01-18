package org.sly.uitest.pageobjects.operations;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.pageobjects.abstractpage.AdvancedSearchPage;
import org.sly.uitest.pageobjects.investments.InvestmentsPage;

/**
 * This class is for the Product Maintenance page. In classic view and material
 * view, it can be accessed by clicking 'Operation -> 'Reporting Overview'
 * 
 * @author Benny Leung
 * @date : 24 Aug, 2016
 * @company Prive Financial
 */
public class ProductMaintenancePage extends AbstractPage {

	public ProductMaintenancePage(WebDriver webDriver) {

		super();
		this.webDriver = webDriver;
		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("gwt-debug-ProductMaintenanceEditView-loadExePlatformBtn")));

		assertTrue(pageContainsStr(" Product Maintenance "));

	}

	/**
	 * 
	 * @param universeName
	 * @return
	 * @throws InterruptedException
	 */
	public ProductMaintenancePage loadProductUniverse(String universeName) throws InterruptedException {
		selectFromDropdown(By.id("gwt-debug-ProductMaintenanceEditView-curExePlatformsListBox"), universeName);
		wait(2);
		clickElement(By.id("gwt-debug-ProductMaintenanceEditView-loadExePlatformBtn"));
		waitForElementVisible(By.id("gwt-debug-ProductMaintenanceEditView-tablePanel"), 30);
		return this;
	}

	/**
	 * click Add Product button in product maintenance page
	 * 
	 * @return {@link InvestmentsPage}
	 * @throws InterruptedException
	 */
	public InvestmentsPage clickAddProductButton() throws InterruptedException {
		waitForElementVisible(
				By.xpath(
						".//button[@id='gwt-debug-ProductMaintenanceEditView-loadExePlatformBtn' and .='Add Product']"),
				10);

		clickElement(By
				.xpath(".//button[@id='gwt-debug-ProductMaintenanceEditView-loadExePlatformBtn' and .='Add Product']"));

		return new InvestmentsPage(webDriver, ProductMaintenancePage.class);
	}

	/**
	 * Do simple search in product maintenance page
	 * 
	 * @param productName
	 * @return {@link ProductMaintenancePage}
	 */
	public ProductMaintenancePage simpleSearch(String productName) {
		waitForElementVisible(By.id("gwt-debug-ProductMaintenanceEditView-productSearchTextBox"), 10);
		sendKeysToElement(By.id("gwt-debug-ProductMaintenanceEditView-productSearchTextBox"), productName);
		waitForElementVisible(By.id("gwt-debug-ProductMaintenanceEditView-searchBtn"), 10);
		clickElement(By.id("gwt-debug-ProductMaintenanceEditView-searchBtn"));
		return this;
	}

	/**
	 * click product name to edit
	 * 
	 * @param productName
	 * @return {@link ProductMaintenancePage}
	 */
	public ProductMaintenancePage editProductByName(String productName) {
		clickElement(By.xpath(".//td[.='" + productName + "']/following-sibling::td[11]/button"));
		return this;
	}

	/**
	 * click the checkbox for products
	 * 
	 * @param productNames
	 * @return {@link ProductMaintenancePage}
	 */
	public ProductMaintenancePage selectProductByName(String... productNames) {
		for (String productName : productNames) {
			clickElement(By.xpath(".//td[.='" + productName + "']/preceding-sibling::td[2]/span/input"));
		}

		return this;
	}

	/**
	 * delete the selected product
	 * 
	 * @return {@link ProductMaintenancePage}
	 * @throws InterruptedException
	 */
	public ProductMaintenancePage deleteSelectedProducts() throws InterruptedException {
		clickElement(By.id("gwt-debug-ProductMaintenanceEditView-editManagerCancel"));
		clickOkButtonIfVisible();
		clickOkButtonIfVisible();
		waitForElementVisible(By.id("gwt-debug-ProductMaintenanceEditView-loadExePlatformBtn"), 30);
		return this;
	}

	/**
	 * click advanced search button in product maintenance page
	 * 
	 * @return {@link ProductMaintenancePage}
	 * @throws InterruptedException
	 */
	public AdvancedSearchPage clickAdvancedSearchButton() throws InterruptedException {
		waitForElementVisible(By.id("gwt-debug-ProductMaintenanceEditView-advancedSearchBtn"), 10);
		clickElement(By.id("gwt-debug-ProductMaintenanceEditView-advancedSearchBtn"));
		return new AdvancedSearchPage(webDriver, ProductMaintenancePage.class);
	}

	/**
	 * edit product code
	 * 
	 * @param productCode
	 * @return {@link ProductMaintenancePage}
	 */
	public ProductMaintenancePage editProductCode(String productCode) {

		waitForElementVisible(
				By.xpath(
						".//div[@id='gwt-debug-CustomDialog-enhancedPanel']//td[.='Product Code']/following-sibling::td/input"),
				10);
		sendKeysToElement(
				By.xpath(
						".//div[@id='gwt-debug-CustomDialog-enhancedPanel']//td[.='Product Code']/following-sibling::td/input"),
				productCode);
		return this;
	}

	/**
	 * edit risk level
	 * 
	 * @param riskLevel
	 * @return {@link ProductMaintenancePage}
	 * @throws InterruptedException
	 */
	public ProductMaintenancePage editRiskLevel(String riskLevel) throws InterruptedException {

		waitForElementVisible(
				By.xpath(
						".//div[@id='gwt-debug-CustomDialog-enhancedPanel']//td[.='Risk Level']/following-sibling::td/input"),
				10);

		sendKeysToElement(
				By.xpath(
						".//div[@id='gwt-debug-CustomDialog-enhancedPanel']//td[.='Risk Level']/following-sibling::td/input"),
				riskLevel);

		log(riskLevel);
		return this;
	}

	/**
	 * edit status of product
	 * 
	 * @param status
	 * @return {@link ProductMaintenancePage}
	 */
	public ProductMaintenancePage editStatus(String status) {
		selectFromDropdown(
				By.xpath(
						".//div[@id='gwt-debug-CustomDialog-enhancedPanel']//td[.='Status']/following-sibling::td/select"),
				status);
		return this;
	}

	/**
	 * edit whether product goes online
	 * 
	 * @param allowed
	 * @return {@link ProductMaintenancePage}
	 */
	public ProductMaintenancePage editAllowedOnline(String allowed) {
		selectFromDropdown(
				By.xpath(
						".//div[@id='gwt-debug-CustomDialog-enhancedPanel']//td[.='Allowed Online']/following-sibling::td/select"),
				allowed);
		return this;
	}

	/**
	 * click series ok button for product maintenance page
	 * 
	 * @return {@link ProductMaintenancePage}
	 * @throws InterruptedException
	 */
	public ProductMaintenancePage clickOkButton() throws InterruptedException {
		clickElement(By.id("gwt-debug-CustomDialog-okButton"));
		clickElement(By.xpath("(.//button[@id='gwt-debug-CustomDialog-yesButton'])[2]"));
		clickOkButtonIfVisible();
		return this;
	}

	/**
	 * click download spreadsheet button
	 * 
	 * @return {@link ProductMaintenancePage}
	 */
	public ProductMaintenancePage clickDownloadSpreadsheetButton() {
		clickElement(By.id("gwt-debug-ProductMaintenanceEditView-downloadBtn"));
		return this;
	}

	/**
	 * click download upload speardsheet button
	 * 
	 * @return {@link ProductMaintenancePage}
	 */
	public ProductMaintenancePage clickUploadSpreadsheetButton() {
		clickElement(By.id("gwt-debug-ProductMaintenanceEditView-downloadBtn"));
		return this;
	}

	/**
	 * click clear search button
	 * 
	 * @return {@link ProductMaintenancePage}
	 */
	public ProductMaintenancePage clickClearSearchButton() {
		clickElement(By.id("gwt-debug-ProductMaintenanceEditView-clearImg"));
		return this;
	}

}
