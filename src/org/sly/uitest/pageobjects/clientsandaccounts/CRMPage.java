package org.sly.uitest.pageobjects.clientsandaccounts;

import java.awt.AWTException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.pageobjects.crm.DocumentsPage;
import org.sly.uitest.pageobjects.crm.TasksPage;
import org.sly.uitest.pageobjects.planning.WealthPlanningOverviewPage;
import org.sly.uitest.settings.Settings;

/**
 * This class represents the CRM Page (tab) of an account or a client, which can
 * be navigated by clicking 'Clients' -> 'Account Overview' -> choose any
 * account -> 'CRM (tab)' or 'Build' -> 'Model Portfolios' -> choose any model
 * portfolio -> 'CRM (tab)' or 'Clients' -> 'Client Overview' -> choose any
 * client -> 'CRM (tab)'
 * 
 * URL:
 * "http://192.168.1.104:8080/SlyAWS/?locale=en#generalUserDetailsCRM;userKey=8462;detailType=1"
 * 
 * @author Lynne Huang
 * @date : 20 Aug, 2015
 * @company Prive Financial
 * 
 *          PAGE NAVIGATION: Client -> Account Overview -> *account -> CRM (Tab)
 *          OR Client -> Client Overview -> *client -> CRM
 */
public class CRMPage extends AbstractPage {

	public CRMPage(WebDriver webDriver) throws InterruptedException {

		super();
		this.webDriver = webDriver;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(60, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gwt-debug-GeneralOverviewView-contentTable")));

		// assertTrue(pageContainsStr("Tasks"));
		// assertTrue(pageContainsStr("Email Records"));
		// assertTrue(pageContainsStr("Notes"));
		// assertTrue(pageContainsStr("Upcoming Calendar Events"));
		// assertTrue(pageContainsStr("Documents"));

	}

	/**
	 * Click the black-white cross icon beside Notes to add note
	 * 
	 * @return {@link CRMPage}
	 */
	public CRMPage clickAddNotesIcon() {

		clickElement(By.id("gwt-debug-NotesSectionPresenter-image"));

		return this;
	}

	/**
	 * Click the name of the note, the Edit Note page will popup
	 * 
	 * @param name
	 *            the name of the note
	 * @return {@link CRMPage}
	 */
	public CRMPage editNoteByName(String name) {

		clickElement(By.xpath("//a[.='" + name + "']"));

		return this;
	}

	/**
	 * On the CRM page, delete the note with the given name
	 * 
	 * @param name
	 *            the name of the note
	 * 
	 * @return {@link CRMPage}
	 */
	public CRMPage deleteNoteByName(String name) {

		waitForElementVisible(
				By.xpath("//td[a[.='" + name + "']]/following-sibling::td[//button[@title='Delete']]//button"), 10);

		clickElementByKeyboard(
				By.xpath("//td[a[.='" + name + "']]/following-sibling::td[//button[@title='Delete']]//button"));

		clickYesButtonIfVisible();

		return this;

	}

	/**
	 * On the Edit Note page, click the [...] icon beside the "Owner", the
	 * paired list box selector will popup
	 * 
	 * @return {@link CRMPage}
	 * @throws InterruptedException
	 */
	public CRMPage clickEditOwnerIcon() throws InterruptedException {
		wait(5);
		clickElement(By.xpath(".//*[@id='gwt-debug-NoteEditView-ownerPanel']/button"));

		return this;
	}

	/**
	 * On the Edit Note page, click the [...] icon beside the "Regarding", the
	 * paired list box selector will popup
	 * 
	 * @return {@link CRMPage}
	 */
	public CRMPage clickEditRegardingIcon() {

		clickElement(By.xpath("//table[@id='gwt-debug-NoteEditView-regardingPanel']//button"));

		return this;
	}

	/**
	 * Under the Wealth Planning section, click the View link to navigate the
	 * page to the wealth planning overview page
	 * 
	 * @param date
	 *            the date of the welath planning data
	 * 
	 * @return {@link WealthPlanningOverviewPage}
	 */
	public WealthPlanningOverviewPage clickViewUnderWealthPlanning(String date) {

		clickElement(By.xpath("//td[.='" + date + "']/following-sibling::td//a"));
		return new WealthPlanningOverviewPage(webDriver);
	}

	/**
	 * On the Owner paired list box page, add task owner with the given type and
	 * owner
	 * 
	 * @param type
	 *            the type of the owner
	 * @param owner
	 *            the name of the owner
	 * @return {@link CRMPage}
	 */
	public CRMPage editAddTaskOwner(String type, String owner) {

		selectFromDropdown(By.id("gwt-debug-SelectionUi-typeListBox"), type);

		selectFromDropdown(By.id("gwt-debug-PairedListBoxSelector-sourceList"), owner);

		clickElement(By.id("gwt-debug-PairedListBoxSelector-addImg"));

		return this;
	}

	/**
	 * On the Owner paired list box page, remove task owner with the given type
	 * and owner
	 * 
	 * @param type
	 *            the type of the owner
	 * @param owner
	 *            the name of the owner
	 * @return {@link CRMPage}
	 * @throws InterruptedException
	 */
	public CRMPage editRemoveTaskOwner(String type, String owner) throws InterruptedException {

		this.waitForElementVisible(By.id("gwt-debug-SelectionUi-typeListBox"), Settings.WAIT_SECONDS);
		selectFromDropdown(By.id("gwt-debug-SelectionUi-typeListBox"), type);
		wait(5);
		selectFromDropdown(By.id("gwt-debug-PairedListBoxSelector-targetList"), owner);
		wait(5);
		clickElement(By.id("gwt-debug-PairedListBoxSelector-removeImg"));

		return this;
	}

	/**
	 * On the Owner paired list box page, add task regarding with the given type
	 * and regarding
	 * 
	 * @param type
	 *            the type of the regarding
	 * @param owner
	 *            the name of the regarding
	 * @return {@link CRMPage}
	 */
	public CRMPage editAddTaskRegarding(String type, String regarding) {

		selectFromDropdown(By.id("gwt-debug-SelectionUi-typeListBox"), type);

		selectFromDropdown(By.id("gwt-debug-PairedListBoxSelector-sourceList"), regarding);

		clickElement(By.id("gwt-debug-PairedListBoxSelector-addImg"));

		return this;
	}

	/**
	 * On the Owner paired list box page, remove task regarding with the given
	 * type and regarding
	 * 
	 * @param type
	 *            the type of the regarding
	 * @param owner
	 *            the name of the regarding
	 * @return {@link CRMPage}
	 */
	public CRMPage editRemoveTaskRegarding(String type, String regarding) {

		selectFromDropdown(By.id("gwt-debug-SelectionUi-typeListBox"), type);

		selectFromDropdown(By.id("gwt-debug-PairedListBoxSelector-targetList"), regarding);

		clickElement(By.id("gwt-debug-PairedListBoxSelector-removeImg"));

		return this;
	}

	/**
	 * On the Edit Note page, edit the name of the note
	 * 
	 * @param name
	 *            the name of the note
	 * @return {@link CRMPage}
	 */
	public CRMPage editNoteName(String name) {

		waitForElementVisible(By.id("gwt-debug-NoteEditView-noteNameBox"), 5);
		sendKeysToElement(By.id("gwt-debug-NoteEditView-noteNameBox"), name);

		return this;
	}

	/**
	 * On the Edit Note page, edit the text of the note
	 * 
	 * @param text
	 * @return {@link CRMPage}
	 */
	public CRMPage editNoteText(String text) {

		sendKeysToElement(By.id("gwt-debug-NoteEditView-descArea"), text);

		return this;
	}

	/**
	 * Click OK
	 * 
	 * @return {@link CRMPage}
	 */
	public CRMPage clickOkButton() {

		clickElement(By.id("gwt-debug-SelectionUi-confirmButton"));

		return this;
	}

	/**
	 * On the Edit Note page, click the SAVE button, then the page will return
	 * to the CRM page
	 * 
	 * @return {@link CRMPage}
	 * @throws InterruptedException
	 */
	public CRMPage clickSaveNoteButton() throws InterruptedException {

		waitForElementVisible(By.id("gwt-debug-NoteEditView-saveButton"), 150);
		System.out.println("123");
		clickElement(By.id("gwt-debug-NoteEditView-saveButton"));

		clickOkButtonIfVisible();

		return this;
	}

	/**
	 * On the Tasks page, click add binder button
	 * 
	 * @return {@link TasksPage}
	 */
	public CRMPage clickAddBindersButton() {

		waitForElementVisible(By.id("gwt-debug-NoteEditView-addBinderImage"), 10);
		clickElement(By.id("gwt-debug-NoteEditView-addBinderImage"));

		return this;
	}

	/**
	 * On the Tasks page, click the add attachment button
	 * 
	 * @return {@link CRMPage}
	 */
	public CRMPage clickAddAttachmentButton() {

		waitForElementVisible(By.id("gwt-debug-DocumentBinderWidgetView-addDocBinderImage"), 10);
		clickElement(By.id("gwt-debug-DocumentBinderWidgetView-addDocBinderImage"));

		return this;
	}

	/**
	 * On the tasks page, upload file for attachment
	 * 
	 * @return {@link CRMPage}
	 * @throws AWTException
	 * @throws InterruptedException
	 */
	public CRMPage uploadFileForAttachment() throws AWTException, InterruptedException {

		WebElement elem = webDriver.findElement(By.xpath(".//*[@class='gwt-FileUpload']"));

		elem.sendKeys(Settings.FILE_LOCATION);

		wait(3);

		return this;
	}

	/**
	 * On the tasks page, edit name of binder
	 * 
	 * @param name
	 *            name of the binder
	 * @return {@link CRMPage}
	 */
	public CRMPage editBinderName(String name) {

		sendKeysToElement(By.id("gwt-debug-DocumentBinderWidgetView-nameTextBox"), name);

		return this;
	}

	public CRMPage deleteAllBinders() {

		try {
			waitForElementVisible(By.id("gwt-debug-NoteEditPresenter-deleteImage"), 5);

			int size = getSizeOfElements(By.id("gwt-debug-NoteEditPresenter-deleteImage"));

			for (int i = 1; i < size + 1; i++) {
				clickElement(By.xpath("(.//img[@id='gwt-debug-NoteEditPresenter-deleteImage'])[" + i + "]"));
			}

		} catch (TimeoutException e) {

		}

		return this;
	}

	public DocumentsPage editDocumentByName(String document) {
		waitForElementVisible(By.xpath(
				".//a[@id='gwt-debug-BindersSectionPresenter-binderAnchor' and contains(text(),'" + document + "')]"),
				10);

		clickElement(By.xpath(
				".//a[@id='gwt-debug-BindersSectionPresenter-binderAnchor' and contains(text(),'" + document + "')]"));

		return new DocumentsPage(webDriver);
	}
}
