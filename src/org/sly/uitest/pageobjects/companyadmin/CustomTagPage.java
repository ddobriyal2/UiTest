package org.sly.uitest.pageobjects.companyadmin;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.settings.Settings;

/**
 * This class represents the Custom Tag Overview page and the pop-up Edit Custom
 * Tag page, which can be navigated by clicking 'Company Settings' -> 'Custom
 * Tag'
 * 
 * URL:"http://192.168.1.104:8080/SlyAWS/?locale=en#customTagList"
 * 
 * @author Lynne Huang
 * @date : 13 Aug, 2015
 * @company Prive Financial
 * 
 */
public class CustomTagPage extends AbstractPage {

	public CustomTagPage(WebDriver webDriver) {

		super();
		this.webDriver = webDriver;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.id("gwt-debug-CustomTagOverviewView-customTagTablePanel")));

		assertTrue(pageContainsStr(" Custom Tag Overview "));
	}

	/**
	 * On the Custom Tag Overview page, click the CREATE CUSTOM TAG button
	 * 
	 * @return {@link CustomTagPage}
	 * @throws InterruptedException
	 */
	public CustomTagPage clickCreateCustomTagButton() throws InterruptedException {
		wait(Settings.WAIT_SECONDS);
		clickElement(By.id("gwt-debug-CustomTagOverviewView-createCustomTagButton"));

		return this;
	}

	/**
	 * On the Custom Tag Overview page, click the pencil icon to edit the custom
	 * tag with the given name
	 * 
	 * @param name
	 *            the custom tag name
	 * 
	 * @return {@link CustomTagPage}
	 */
	public CustomTagPage editCustomTagByName(String name) {

		clickNextPageUntilFindElement(By.xpath("//tr[contains(@class,'mat-table-body')]"), By.xpath("//td[.='" + name
				+ "']/following-sibling::td[//button]//button[@id='gwt-debug-CustomTagOverviewPresenter-editButton']"));
		clickElement(By.xpath("//td[.='" + name
				+ "']/following-sibling::td[//button]//button[@id='gwt-debug-CustomTagOverviewPresenter-editButton']"));

		return this;
	}

	/**
	 * On the Custom Tag Overview page, click the red-white minus icon to delete
	 * the non-used custom tag with the given name
	 * 
	 * @param name
	 *            the custom tag name
	 * 
	 * @return {@link CustomTagPage}
	 */
	public CustomTagPage deleteCustomTagByName(String name) {

		clickNextPageUntilFindElement(By.xpath("//tr[contains(@class,'mat-table-body')]"), By.xpath("//td[.='" + name
				+ "']/following-sibling::td[//button]//button[@id='gwt-debug-CustomTagOverviewPresenter-deleteButton']"));

		clickElement(By.xpath("//td[.='" + name
				+ "']/following-sibling::td[//button]//button[@id='gwt-debug-CustomTagOverviewPresenter-deleteButton']"));

		clickYesButtonIfVisible();
		clickYesButtonIfVisible();

		clickElement(By.id("gwt-debug-CustomDialog-okButton"));

		return this;
	}

	/**
	 * On the Custom Tag Overview page, click the red-white minus icon to delete
	 * the used custom tag with the given name
	 * 
	 * @param name
	 *            the custom tag name
	 * 
	 * @return {@link CustomTagPage}
	 * @throws InterruptedException
	 */
	public CustomTagPage deleteCustomTagInUseByName(String name) throws InterruptedException {

		clickElement(By.xpath("//td[.='" + name
				+ "']/following-sibling::td[//button]//button[@id='gwt-debug-CustomTagOverviewPresenter-deleteButton']"));

		clickYesButtonIfVisible();

		clickOkButtonIfVisible();

		return this;
	}

	/**
	 * On the Custom Tag Overview page, after click the CREATE CUSTOM TAG or
	 * click the edit icon, on the Edit Custom Tag page, edit the tag name
	 * 
	 * @param name
	 *            the custom tag name
	 * 
	 * @return {@link CustomTagPage}
	 */
	public CustomTagPage editTagName(String name) {

		waitForElementVisible(By.id("gwt-debug-EditCustomTagPopupPresenterWidgetView-name"), 150);
		sendKeysToElement(By.id("gwt-debug-EditCustomTagPopupPresenterWidgetView-name"), name + '\n');

		return this;
	}

	/**
	 * On the Custom Tag Overview page, after click the CREATE CUSTOM TAG or
	 * click the edit icon, on the Edit Custom Tag page, edit the tag
	 * description
	 * 
	 * @param description
	 * 
	 * @return {@link CustomTagPage}
	 * @throws InterruptedException
	 */
	public CustomTagPage editTagDescription(String description) throws InterruptedException {
		this.waitForWaitingScreenNotVisible();
		sendKeysToElement(By.id("gwt-debug-EditCustomTagPopupPresenterWidgetView-description"), description + '\n');

		return this;
	}

	/**
	 * On the Custom Tag Overview page, after click the CREATE CUSTOM TAG or
	 * click the edit icon, on the Edit Custom Tag page, edit the tag color
	 * 
	 * @return {@link CustomTagPage}
	 */
	public CustomTagPage editTagColor() {

		webDriver.switchTo().activeElement();

		waitForElementVisible(By.id("gwt-debug-EditCustomTagPopupPresenterWidgetView-contentPanel"), 10);
		clickElement(By.id("gwt-debug-EditCustomTagPopupPresenterWidgetView-contentPanel"));

		for (int i = 0; i < 5; i++) {

			try {

				webDriver.findElement(By.id("gwt-debug-ColorPickerPopupPresenterWidgetView-dialogBox-caption"));

				break;

			} catch (org.openqa.selenium.TimeoutException e) {

				clickElement(By.xpath("//td[.=' Color ']/following-sibling::td[1]/input"));

			} catch (org.openqa.selenium.NoSuchElementException e) {

				clickElement(By.id("gwt-debug-EditCustomTagPopupPresenterWidgetView-color"));

			}

		}

		clickElement(By.id("gwt-debug-ColorPickerPopupPresenterWidgetView-okButton"));

		return this;
	}

	/**
	 * On the Custom Tag Overview page, after click the CREATE CUSTOM TAG or
	 * click the edit icon, on the Edit Custom Tag page, edit the tag type
	 * 
	 * @param type
	 * 
	 * @return {@link CustomTagPage}
	 */
	public CustomTagPage editTagTargetType(String type) {

		selectFromDropdown(By.id("gwt-debug-EditCustomTagPopupPresenterWidgetView-targetType"), type);

		return this;
	}

	/**
	 * On the Custom Tag Overview page, after click the CREATE CUSTOM TAG or
	 * click the edit icon, on the Edit Custom Tag page, click the SAVE button
	 * 
	 * @return {@link CustomTagPage}
	 * @throws InterruptedException
	 */
	public CustomTagPage clickSaveButton() throws InterruptedException {
		wait(Settings.WAIT_SECONDS);
		clickElement(By.id("gwt-debug-EditCustomTagPopupPresenterWidgetView-saveBtn"));

		return this;
	}

	/**
	 * On the Custom Tag Overview page, after click the CREATE CUSTOM TAG or
	 * click the edit icon, on the Edit Custom Tag page, click the CANCEL button
	 * 
	 * @return {@link CustomTagPage}
	 */
	public CustomTagPage clickCancelButton() {

		clickElement(By.id("gwt-debug-EditCustomTagPopupPresenterWidgetView-cancelBtn"));

		return this;
	}
}
