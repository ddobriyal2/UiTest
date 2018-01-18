package org.sly.uitest.pageobjects.admin;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.settings.Settings;

/**
 * This class is for Admin Maintenance. Go to Administration -> Maintenance
 */
public class AdminMaintenancePage extends AbstractPage {
	public AdminMaintenancePage(WebDriver webDriver) {

		super();
		this.webDriver = webDriver;

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gwt-debug-MyMainMaterialView-mainPanel")));

	}

	public AdminMaintenancePage editAlertKeys(String keys) {
		sendKeysToElement(By.id("gwt-debug-MaintenanceMainView-textBoxSingleUserAlertKey"), keys);
		return this;
	}

	public AdminMaintenancePage clickProcessSingleUserAlertBtn() {
		clickElement(By.id("gwt-debug-MaintenanceMainView-buttonProcessSingleUserAlert"));
		assertTrue(pageContainsStr("User alert is being processed in the background."));
		return this;
	}

	/**
	 * click any button with a provided name
	 * 
	 * @param name
	 *            text on the button
	 * @return
	 */
	public AdminMaintenancePage clickCustomButton(String name) {
		clickElement(By.xpath(".//button[contains(text(),'" + name + "')]"));
		return this;
	}

	/**
	 * On any admin edit page, if it requires to select an option from the
	 * drop-down list for the field, use this method
	 * 
	 * @param field
	 *            the name of the edit field
	 * @param option
	 *            the option to select
	 * 
	 * @return {@link AdminEditPage}
	 * 
	 */
	public AdminMaintenancePage selectOptionForCertainField(String field, String option) {

		this.waitForWaitingScreenNotVisible();

		waitForElementVisible(By.xpath("//td[contains(text(), '" + field + "')]/following-sibling::td//select/option"),
				Settings.WAIT_SECONDS * 3);

		selectFromDropdown(By.xpath("//td[contains(text(), '" + field + "')]/following-sibling::td//select"), option);
		return this;
	}

	/**
	 * select error type in maintenance page
	 * 
	 * @param err
	 *            types of error
	 * @return
	 */
	public AdminMaintenancePage selectTypeOfError(String err) {
		selectFromDropdown(By.id("gwt-debug-AdminShowErrorsView-errorTypeList"), err);
		return this;
	}
}
