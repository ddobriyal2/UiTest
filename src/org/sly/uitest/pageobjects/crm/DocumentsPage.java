package org.sly.uitest.pageobjects.crm;

import java.awt.AWTException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.pageobjects.abstractpage.AdvancedSearchPage;
import org.sly.uitest.pageobjects.clientsandaccounts.CRMPage;
import org.sly.uitest.settings.Settings;

/**
 * 
 * This class represents the Investments Page, which can be navigated by
 * clicking 'Manage' -> 'Documents'
 * 
 * @author Lynne Huang
 * @date : 21 Sep, 2015
 * @company Prive Financial
 */
public class DocumentsPage extends AbstractPage {

	/**
	 * @param webDriver
	 */
	public DocumentsPage(WebDriver webDriver) {

		super();
		this.webDriver = webDriver;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[contains(@id,'mainPanel')]")));

		} catch (Exception ex) {
			// wait.until(ExpectedConditions.visibilityOfElementLocated(By
			// .id("gwt-debug-MyMainClassicView-mainPanel")));

		}

		// assertTrue(pageContainsStr(" Documents "));

	}

	/**
	 * On the Documents page, click CREATE BINDER button
	 *
	 * @return {@link DocumentsPage}
	 */
	public DocumentsPage clickCreateBinderButton() {

		clickElement(By.id("gwt-debug-DocumentBinderListView-createBinderButton"));

		return this;
	}

	/**
	 * After click the CREATE BINDER button or click the edit icon, on the popup
	 * Edit Document Binder page, edit the document binder name
	 * 
	 * @param name
	 *            the document binder name
	 * 
	 * @return {@link DocumentsPage}
	 */
	public DocumentsPage editDocumentBinderName(String name) {

		sendKeysToElement(By.id("gwt-debug-DocumentBinderEditView-nameTextBox"), name);

		return this;
	}

	/**
	 * After click the CREATE BINDER button or click the edit icon, on the popup
	 * Edit Document Binder page, add a document owner
	 * 
	 * @param name
	 *            the document owner name
	 * 
	 * @return {@link DocumentsPage}
	 */
	public DocumentsPage editAddOwner(String type, String owner) {

		clickElement(By.xpath(".//*[@id='gwt-debug-DocumentBinderEditView-ownerPanel']//button"));

		selectFromDropdown(By.id("gwt-debug-SelectionUi-typeListBox"), type);

		selectFromDropdown(By.id("gwt-debug-PairedListBoxSelector-sourceList"), owner);

		clickElement(By.id("gwt-debug-PairedListBoxSelector-addImg"));

		clickElement(By.id("gwt-debug-SelectionUi-confirmButton"));

		return this;
	}

	/**
	 * After click the CREATE BINDER button or click the edit icon, on the popup
	 * Edit Document Binder page, delete the document owner
	 * 
	 * @param name
	 *            the document owner name
	 * 
	 * @return {@link DocumentsPage}
	 */
	public DocumentsPage editDeleteOwner(String type, String owner) {

		clickElement(By.xpath(".//*[@id='gwt-debug-DocumentBinderEditView-ownerPanel']//button"));

		selectFromDropdown(By.id("gwt-debug-SelectionUi-typeListBox"), type);

		selectFromDropdown(By.id("gwt-debug-PairedListBoxSelector-targetList"), owner);

		clickElement(By.id("gwt-debug-PairedListBoxSelector-removeImg"));

		clickElement(By.id("gwt-debug-SelectionUi-confirmButton"));

		return this;
	}

	/**
	 * After click the CREATE BINDER button or click the edit icon, on the popup
	 * Edit Document Binder page, check or uncheck the checkbox of Share with
	 * Advisor
	 * 
	 * @param shared
	 *            if true, check; if false, uncheck
	 * 
	 * @return {@link DocumentsPage}
	 */
	public DocumentsPage checkShareWithAdvisor(Boolean shared) {

		WebElement we = webDriver
				.findElement(By.id("gwt-debug-DocumentBinderEditView-sharedWithAdvisorCheckBox-input"));

		setCheckboxStatus2(we, shared);

		return this;
	}

	/**
	 * After click the CREATE BINDER button or click the edit icon, on the popup
	 * Edit Document Binder page, check or uncheck the checkbox of Share with
	 * Client
	 * 
	 * @param shared
	 *            if true, check; if false, uncheck
	 * 
	 * @return {@link DocumentsPage}
	 */
	public DocumentsPage checkShareWithClient(Boolean shared) {

		WebElement we = webDriver.findElement(By.id("gwt-debug-DocumentBinderEditView-sharedWithClientCheckBox-input"));

		setCheckboxStatus2(we, shared);

		return this;
	}

	/**
	 * After click the CREATE BINDER button or click the edit icon, on the popup
	 * Edit Document Binder page, add a regarding
	 * 
	 * @param type
	 *            the type of the regarding
	 * @param name
	 *            the name of the regarding
	 * 
	 * @return {@link DocumentsPage}
	 * @throws InterruptedException
	 */
	public DocumentsPage editAddRegarding(String type, String name) throws InterruptedException {

		clickElement(By.xpath(".//*[@id='gwt-debug-DocumentBinderEditView-regardingPanel']//button"));

		selectFromDropdown(By.id("gwt-debug-SelectionUi-typeListBox"), type);
		wait(2);
		selectFromDropdown(By.id("gwt-debug-PairedListBoxSelector-sourceList"), name);
		clickElement(By.id("gwt-debug-PairedListBoxSelector-addImg"));

		clickElement(By.id("gwt-debug-SelectionUi-confirmButton"));

		if (isElementVisible(By.id("gwt-debug-SelectionUi-confirmButton"))) {
			clickElement(By.id("gwt-debug-SelectionUi-cancelButton"));
		}
		return this;
	}

	/**
	 * After click the CREATE BINDER button or click the edit icon, on the popup
	 * Edit Document Binder page, delete the regarding
	 * 
	 * @param type
	 *            the type of the regarding
	 * @param name
	 *            the name of the regarding
	 * 
	 * @return {@link DocumentsPage}
	 */
	public DocumentsPage editDeleteRegarding(String type, String name) {

		clickElement(By.xpath(".//*[@id='gwt-debug-DocumentBinderEditView-regardingPanel']//button"));

		selectFromDropdown(By.id("gwt-debug-SelectionUi-typeListBox"), type);

		selectFromDropdown(By.id("gwt-debug-PairedListBoxSelector-targetList"), name);

		clickElement(By.id("gwt-debug-SelectionUi-confirmButton"));

		return this;
	}

	/**
	 * After click the CREATE BINDER button or click the edit icon, on the popup
	 * Edit Document Binder page, edit the signature date
	 * 
	 * @param date
	 *            the signature date
	 * 
	 * @return {@link DocumentsPage}
	 */
	public DocumentsPage editSignatureDate(String date) {

		sendKeysToElement(By.id("gwt-debug-DocumentBinderEditView-sigDate"), date);

		return this;
	}

	public DocumentsPage editTransactionReference(String transactionReference) {

		sendKeysToElement(By.id("gwt-debug-DocumentBinderEditView-transactionReference"), transactionReference);

		return this;
	}

	/**
	 * After click the CREATE BINDER button or click the edit icon, on the popup
	 * Edit Document Binder page, edit the attachment
	 * 
	 * @param url
	 * 
	 * @return {@link DocumentsPage}
	 * @throws InterruptedException
	 */
	public DocumentsPage editAttachementByURL(String url) throws InterruptedException {

		waitForElementVisible(By.id("gwt-debug-DocumentBinderEditView-addDocBinderImage"), 10);

		clickElement(By.id("gwt-debug-DocumentBinderEditView-addDocBinderImage"));

		selectFromDropdown(By.id("gwt-debug-DocumentBinderEditPresenter-RowWidget-switchListBox"), "URL");

		sendKeysToElement(By.id("gwt-debug-DocumentBinderEditPresenter-RowWidget-textBox"), url);

		clickOkButtonIfVisible();

		return this;
	}

	/**
	 * After click the CREATE BINDER button or click the edit icon, on the popup
	 * Edit Document Binder page, click the SAVE button
	 * 
	 * 
	 * @return {@link DocumentsPage}
	 */
	public DocumentsPage clickSaveButton() {

		clickElement(By.id("gwt-debug-DocumentBinderEditView-submitBtn"));

		return this;
	}

	/**
	 * 
	 * On the Documents page, open the advanced search panel
	 * 
	 * @throws InterruptedException
	 * 
	 */
	public DocumentsPage goToAdvancedSearchPage() throws InterruptedException {
		scrollToTop();
		clickElement(By.id("gwt-debug-DocumentBinderListView-advancedSearchBtn"));

		return this;
	}

	/**
	 * On the Documents Advanced Search panel, click the SEARCH button
	 * 
	 * @return {@link DocumentsPage}
	 * @throws InterruptedException
	 */
	public DocumentsPage clickSearchButton() throws InterruptedException {

		clickElement(By.id("gwt-debug-AdvancedSearchPanel-searchButton"));

		return this;
	}

	/**
	 * On the Documents Advanced Search panel, click the SEARCH button
	 * 
	 * @return {@link DocumentsPage}
	 * @throws InterruptedException
	 */
	public DocumentsPage clickSearchImage() throws InterruptedException {

		clickElement(By.id("gwt-debug-DocumentBinderListView-searchImg"));

		return this;
	}

	/**
	 * On the Documents page, click the red-white minus icon to delete the
	 * document with the given name
	 * 
	 * @param name
	 *            the document binder name
	 * 
	 * @return {@link DocumentsPage}
	 */
	public DocumentsPage deleteBinderByName(String name) {

		clickElement(By.xpath("//td[.='" + name
				+ "']/following-sibling::td[table//button[@title='Delete']]//button[@title='Delete']"));

		clickYesButtonIfVisible();

		return this;
	}

	public DocumentsPage editBinderByName(String name) {
		clickElement(By.xpath(
				"//td[.='" + name + "']/following-sibling::td[table//button[@title='Edit']]//button[@title='Edit']"));
		return this;
	}

	public DocumentsPage addAttachmentForDoucmentBinder() throws InterruptedException, AWTException {

		waitForElementVisible(By.id("gwt-debug-DocumentBinderEditView-addDocBinderImage"), 5);

		clickElement(By.id("gwt-debug-DocumentBinderEditView-addDocBinderImage"));

		waitForElementVisible(By.id("gwt-debug-CustomDialog-enhancedPanel"), 10);

		WebElement elem = webDriver.findElement(By.xpath(".//input[@class='gwt-FileUpload' and @type='file']"));

		elem.sendKeys(Settings.FILE_LOCATION);
		waitForElementVisible(
				By.xpath(
						".//*[@class='status status-changed status-submiting status-queued status-inprogress status-success']"),
				10);
		clickOkButtonIfVisible();

		return this;
	}

	/**
	 * click clear search button in document page
	 */
	public DocumentsPage clickClearSearchButton() {

		if (isElementVisible(By.id("gwt-debug-DocumentBinderListView-clearImg"))) {

			clickElement(By.id("gwt-debug-DocumentBinderListView-clearImg"));

		}
		return this;
	}

	/**
	 * @param transactionReference
	 * @return {@link AdvancedSearchPage}
	 */
	public DocumentsPage searchByTransactionReference(String transactionReference) {

		sendKeysToElement(By.id("gwt-debug-AdvancedSearchPanel-TransactionReference"), transactionReference);

		return this;
	}

	/**
	 * @param name
	 * @return {@link AdvancedSearchPage}
	 */
	public DocumentsPage searchByDocumentBinderName(String name) {

		waitForElementVisible(By.id("gwt-debug-AdvancedSearchPanel-DocumentBinderName"), 5);
		sendKeysToElement(By.id("gwt-debug-AdvancedSearchPanel-DocumentBinderName"), name);

		return this;
	}

	public DocumentsPage simpleSearchInDocumentPage(String content) throws InterruptedException {

		sendKeysToElement(By.id("gwt-debug-DocumentBinderListView-searchBox"), content);

		this.clickSearchImage();
		return this;
	}

	public DocumentsPage downloadDocument(String document) throws InterruptedException {

		clickElement(By
				.xpath(".//a[@id='gwt-debug-MultiUploaderWidget-anchorDoc' and contains(text(),'" + document + "')]"));

		// wait(5);

		return this;
	}

	public CRMPage clickBackButton() throws InterruptedException {
		clickElement(By.id("gwt-debug-DocumentBinderEditView-cancelBtn"));
		return new CRMPage(webDriver);
	}

	public DocumentsPage clickCancelButton() {
		clickElement(By.id("gwt-debug-DocumentBinderEditView-cancelBtn"));
		return this;
	}
}
