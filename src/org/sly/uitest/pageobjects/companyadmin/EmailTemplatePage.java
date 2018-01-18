package org.sly.uitest.pageobjects.companyadmin;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.pageobjects.clientsandaccounts.AccountOverviewPage;
import org.sly.uitest.settings.Settings;

/**
 * 
 * This class represents the Email Template page and the pop-up Email Template
 * edit page, which can be navigated by clicking 'Company Settings' -> 'Email
 * Template'
 * 
 * @author Lynne Huang
 * @date : 24 Aug, 2015
 * @company Prive Financial
 * 
 */
public class EmailTemplatePage extends AbstractPage {

	/**
	 * @param webDriver
	 */
	public EmailTemplatePage(WebDriver webDriver) {
		super();
		this.webDriver = webDriver;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		try {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.id("gwt-debug-EmailTemplateOverviewView-contentPanel")));
			scrollToTop();
		} catch (Exception ex) {
			// wait.until(ExpectedConditions.visibilityOfElementLocated(By
			// .id("gwt-debug-MyMainMaterialView-mainPanel")));

		}

	}

	/**
	 * On the Email Template Overview page, click the CREATE TEMPLATE button
	 * 
	 * @return {@link EmailTemplatePage}
	 */
	public EmailTemplatePage createNewEmailTemplate() {

		clickElement(By.id("gwt-debug-EmailTemplateOverviewView-createButton"));

		return this;
	}

	/**
	 * On the Email Template Overview page, click the red-white minus icon to
	 * delete the existing email template
	 * 
	 * @param template
	 *            the name of the email template
	 * 
	 * @return {@link EmailTemplatePage}
	 */
	public EmailTemplatePage deleteExistingEmailTemplate(String template) {

		clickElement(By.xpath("//td[.='" + template
				+ "']/following-sibling::td[4]//button[@id='gwt-debug-EmailTemplateOverviewView-deleteImg']"));

		clickYesButtonIfVisible();

		return this;
	}

	/**
	 * On the Email Template Overview page, click the pencil icon to edit the
	 * existing email template with the given name
	 * 
	 * @param name
	 *            the name of the email template
	 * 
	 * @return {@link EmailTemplatePage}
	 */
	public EmailTemplatePage editEmailTemplateName(String name) {

		sendKeysToElement(By.id("gwt-debug-EmailTemplateEditView-nameTextBox"), name);

		return this;
	}

	/**
	 * On the Email Template Overview page, after click the CREATE TEMPLATE or
	 * click edit icon, on the pop-up Email Template page, edit the email
	 * template content
	 * 
	 * @param content
	 * 
	 * @return {@link EmailTemplatePage}
	 */
	public EmailTemplatePage editEmailTemplateContent(String content) {

		waitForElementVisible(By.cssSelector("iframe.cke_wysiwyg_frame.cke_reset"), 12 * Settings.WAIT_SECONDS);
		// get the iframe element of CKEditor
		WebElement contentArea = webDriver.findElement(By.cssSelector("iframe.cke_wysiwyg_frame.cke_reset"));

		// switch to iframe before sending keys, otherwise it wouldn't
		// work
		webDriver.switchTo().frame(contentArea);

		sendKeysToElement(By.cssSelector("body.cke_editable.cke_editable_themed.cke_contents_ltr.cke_show_borders"),
				content);

		// switch back to main frame
		webDriver.switchTo().defaultContent();

		return this;
	}

	/**
	 * On the Email Template Overview page, after click the CREATE TEMPLATE or
	 * click edit icon, on the pop-up Email Template page, click the SAVE button
	 * 
	 * @return {@link EmailTemplatePage}
	 */
	public EmailTemplatePage clickSaveButton() {

		waitForElementVisible(By.xpath("//button[@id='gwt-debug-EmailSignatureWidget-closeBtn' and .='Save']"), 10);
		clickElementByKeyboard(By.xpath("//button[@id='gwt-debug-EmailSignatureWidget-closeBtn' and .='Save']"));
		// waitGet(
		// By.xpath("//button[@id='gwt-debug-EmailSignatureWidget-closeBtn' and
		// .='Save']"))
		// .click();
		clickYesButtonIfVisible();

		return this;
	}

	/**
	 * On the Email Template Overview page, after click the CREATE TEMPLATE or
	 * click edit icon, on the pop-up Email Template page, click the CANCEL
	 * button
	 * 
	 * @return {@link EmailTemplatePage}
	 */
	public AccountOverviewPage clickCancelButton() {

		clickElement(By.id("gwt-debug-EmailClientWidget-cancelBtn"));

		return new AccountOverviewPage(webDriver);
	}

	/**
	 * After click the EMAIL CLIENT button on the Account Overview page, on the
	 * pop-up Email page, switch to the Mail Merge tab
	 * 
	 * @return {@link EmailTemplatePage}
	 */
	public EmailTemplatePage goToMailMerge() {

		WebElement elem = null;
		waitForElementVisible(By.xpath("(//div[.='Mail Merge']/div/div)[1]"), 20);

		clickElement(By.xpath("(//div[.='Mail Merge']/div/div)[1]"));
		try {
			elem = webDriver
					.findElement(By.xpath(".//*[@debugid='EmailClientWidget-copyRow' and @style='display: none;']"));
		} catch (NoSuchElementException e) {
			for (int i = 0; i < Settings.ATTEMPT_LOOPING_NUMBER; i++) {
				if (elem.equals(null)) {
					clickElement(By.xpath("(//div[.='Mail Merge']/div/div)[1]"));
					elem = webDriver.findElement(
							By.xpath(".//*[@debugid='EmailClientWidget-copyRow' and @style='display: none;']"));
				}
			}

		}

		return this;
	}

	/**
	 * After click the EMAIL CLIENT button on the Account Overview page and
	 * switch to the Mail Merge tab, select the email template
	 * 
	 * @param template
	 * 
	 * @return {@link EmailTemplatePage}
	 */
	public EmailTemplatePage selectEmailTemplate(String template) {

		selectFromDropdown(By.id("gwt-debug-EmailClientWidget-templateListBox"), template);

		return this;
	}

	/**
	 * After click the EMAIL CLIENT button on the Account Overview page, switch
	 * to the Mail Merge tab, and finish selecting an email template, click the
	 * Insert button
	 * 
	 * 
	 * @return {@link EmailTemplatePage}
	 */
	public EmailTemplatePage clickInsertEmailTemplageButton() {

		clickElement(By.id("gwt-debug-EmailClientWidget-insertTemplateBtn"));

		return this;
	}

}
