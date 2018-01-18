package org.sly.uitest.pageobjects.commissioning;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.pageobjects.abstractpage.AdvancedSearchPage;

/**
 * 
 * This class represents the Pending New Business Processes page, which can be
 * navigated by clicking 'Clients' -> 'New Business'
 * 
 * URL: "http://192.168.1.104:8080/SlyAWS/?locale=en#businesseventlist"
 * 
 * @author Lynne Huang
 * @date : 13 Aug, 2015
 * @company Prive Financial
 * 
 */
public class NewBusinessEventPage extends AbstractPage {

	/**
	 * @param webDriver
	 */
	public NewBusinessEventPage(WebDriver webDriver) {

		super();
		this.webDriver = webDriver;

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);
		scrollToTop();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gwt-debug-SortableFlexTableAsync-table")));

		// assertTrue(pageContainsStr("Pending New Business Processes"));
	}

	/**
	 * Simply input the string into the search box
	 * 
	 * @param string
	 *            the string to search
	 * @return {@link NewBusinessEventPage}
	 */
	public NewBusinessEventPage simpleSearchByString(String string) {
		scrollToTop();
		sendKeysToElement(By.id("gwt-debug-BusinessEventListView-searchBox"), string);

		clickElement(By.id("gwt-debug-BusinessEventListView-searchImg"));

		return this;

	}

	/**
	 * Click the cross icon besides the search box to clear the previous search
	 * 
	 * @return {@link NewBusinessEventPage}
	 */
	public NewBusinessEventPage clickClearSearchIcon() {

		try {

			waitForElementVisible(By.id("gwt-debug-BusinessEventListView-clearImg"), 15);

			if (isElementVisible(By.id("gwt-debug-BusinessEventListView-clearImg"))) {

				clickElement(By.id("gwt-debug-BusinessEventListView-clearImg"));
			}
		} catch (Exception e) {

		}

		return this;
	}

	/**
	 * Click the downward triangle icon to open the Advanced Search Panel on the
	 * Pending New Business Processes page
	 * 
	 * @return {@link AdvancedSearchPage}
	 */
	public AdvancedSearchPage goToAdvancedSearchPage() throws InterruptedException {
		scrollToTop();
		clickElement(By.id("gwt-debug-BusinessEventListView-advancedSearchBtn"));

		return new AdvancedSearchPage(webDriver, NewBusinessEventPage.class);
	}

	/**
	 * Click the NEW button to create a new business
	 * 
	 * @return {@link NewBusinessEventEditPage}
	 */
	public NewBusinessEventEditPage clickNewButtonToCreateNewBusniess() {

		clickElement(By.id("gwt-debug-BusinessEventListView-newEvent"));

		return new NewBusinessEventEditPage(webDriver);
	}

	/**
	 * Click the APPROVE button to approve a business event
	 * 
	 * @return {@link NewBusinessEventPage}
	 */
	public NewBusinessEventPage clickApproveNewBusniessButton() {

		clickElement(By.id("gwt-debug-BusinessEventListView-approveBtn"));

		clickYesButtonIfVisible();
		clickOkButtonIfVisible();

		return new NewBusinessEventPage(webDriver);
	}

	/**
	 * Click the DELETE button to delete a business event
	 * 
	 * @return {@link NewBusinessEventPage}
	 */
	public NewBusinessEventPage clickDeleteButton() {

		clickElement(By.id("gwt-debug-BusinessEventListView-deleteEvents"));

		clickYesButtonIfVisible();

		return this;

	}

	/**
	 * Click the red minus icon to delete a business event with the given client
	 * name
	 * 
	 * @param name
	 *            the client name
	 * @return {@link NewBusinessEventPage}
	 */
	public NewBusinessEventPage clickDeleteIconByClientName(String name) {

		clickElement(By.xpath("//td[.='" + name + "']/following-sibling::td[button[@title='Delete']]/button"));

		clickElement(By.id("gwt-debug-CustomDialog-yesButton"));

		return this;

	}

	/**
	 * check the chekcbox next to the client
	 * 
	 * @param name
	 * @return
	 */
	public NewBusinessEventPage clickCheckboxByClientName(String name) {
		WebElement elem = webDriver
				.findElement(By.xpath(".//td[div[a[contains(text(),'" + name + "')]]]//preceding-sibling::td//input"));
		setCheckboxStatus2(elem, true);

		return this;
	}

	/**
	 * 
	 * */
	public NewBusinessEventEditPage clickEditIconByClientName(String name) {

		clickElement(By.xpath("//td[.='" + name + "']/following-sibling::td[button[@title='Edit']]/button"));
		log("Edit button: Clicked");
		return new NewBusinessEventEditPage(webDriver);

	}

	/**
	 * Check the checkbox of a business event with the given client name
	 * 
	 * @param name
	 *            the client name
	 * @param checked
	 *            if true, select the business event; if false, deselect the
	 *            business event
	 * @return {@link NewBusinessEventPage}
	 */
	public NewBusinessEventPage checkNewBusinessEventByClientName(String name, Boolean checked) {

		WebElement we = webDriver
				.findElement(By.xpath("//td[.='" + name + "']/preceding-sibling::td[3]//input[@type='checkbox']"));

		setCheckboxStatus2(we, checked);

		return this;

	}

	/**
	 * On the Pending New Business Processes page, get the Ref number with the
	 * given client name
	 * 
	 * @param name
	 *            the client name
	 * @return String the refNo
	 */
	public String extractRefNoByName(String name) {

		WebElement elem = waitGet(By.xpath("//a[.='" + name + "']/parent::div/parent::td"));
		String id = elem.getAttribute("id");
		System.out.println(id);
		String refNo = getTextByLocator(By.id(id.substring(0, id.length() - 1) + "2"));

		refNo = refNo.substring(1, refNo.length());

		return refNo;

	}

	/**
	 * On the Pending New Business Processes page, input a refNo into the box
	 * beside the HARD DELETE NBE, and click the HARD DELETE NBE button
	 * 
	 * @param refNo
	 * 
	 * @return {@link NewBusinessEventPage}
	 */
	public NewBusinessEventPage clickHardDeleteNBEButtonByRefNo(String refNo) {

		sendKeysToElement(By.id("gwt-debug-BusinessEventListView-refNoField"), refNo);

		clickElement(By.id("gwt-debug-BusinessEventListView-deleteBusinessBtn"));
		clickElement(By.id("gwt-debug-CustomDialog-customButton"));

		clickElement(By.id("gwt-debug-CustomDialog-okButton"));

		return this;
	}

	public NewBusinessEventPage checkShowOnlyMyTaskCheckbox(boolean check) {
		By by = By.id("gwt-debug-TaskOverviewView-showOnlyMyTasks-input");
		WebElement elem = webDriver.findElement(by);
		clickElement(by);
		setCheckboxStatus2(elem, check);

		return this;
	}
}
