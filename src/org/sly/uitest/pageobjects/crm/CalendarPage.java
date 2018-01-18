package org.sly.uitest.pageobjects.crm;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.framework.AbstractTest;

/**
 * This class is for the Calendar page. In classic view, it can be accessed by
 * clicking 'CRM -> 'Calendar' In material view, it can be accessed by clicking
 * 'Asset Management' -> 'Calendar'
 * 
 * @author Benny Leung
 * @date : 4 Aug, 2016
 * @company Prive Financial
 */
public class CalendarPage extends AbstractTest {

	public CalendarPage(WebDriver webDriver) throws InterruptedException {

		super();
		this.webDriver = webDriver;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gwt-debug-CalendarEventsView-calendarPanel")));

		assertTrue(pageContainsStr(" Calendar Events "));

	}

	/**
	 * Click ADD NEW EVENT button to create new event
	 * 
	 * @return {@link CalendarPage}
	 */
	public CalendarPage addNewEvent() {

		clickElement(By.id("gwt-debug-CalendarEventsView-addNewEventButton"));

		return this;
	}

	/**
	 * Edit the Event Name field with activityName
	 * 
	 * @param activityName
	 *            - name of the activity
	 * @return {@link CalendarPage}
	 */
	public CalendarPage editActivityName(String activityName) {

		sendKeysToElement(By.id("gwt-debug-EditActivity-activityName"), activityName);

		return this;
	}

	/**
	 * Click SAVE button to save the event
	 * 
	 * @return {@link CalendarPage}
	 */
	public CalendarPage clickSaveActivityButton() {

		waitForElementVisible(By.id("gwt-debug-EditActivity-saveButton"), 10);

		clickElement(By.id("gwt-debug-EditActivity-saveButton"));

		return this;
	}

	/**
	 * Click the activity name in the Calendar Event panel to edit activity
	 * 
	 * @param activityName
	 * @return {@link CalendarPage}
	 */
	public CalendarPage enterActivityByName(String activityName) {

		By by = By.linkText(activityName);
		clickElement(by);

		return this;
	}

	/**
	 * Select type from Type dropdown
	 * 
	 * @param type
	 *            : Open, Pending, Completed or Cancelled
	 * @return {@link CalendarPage}
	 */
	public CalendarPage editActivityType(String type) {

		selectFromDropdownByValue(By.id("gwt-debug-EditActivity-statusListBox"), type);

		return this;
	}

	/**
	 * editFinishedDate with new Date
	 * 
	 * @return {@link CalendarPage}
	 */
	public CalendarPage editFinishedDateForCompletedActivity(String date) {

		sendKeysToElement(By.id("gwt-debug-EditActivity-finishedDate"), date);

		return this;
	}

	/**
	 * open the regarding panel in edit event page
	 * 
	 * @return {@link CalendarPage}
	 */
	public CalendarPage openRegardingPanel() {
		waitForElementVisible(By.xpath(".//*[@id='gwt-debug-EditActivity-regardingPanel']/button"), 10);
		clickElementByKeyboard(By.xpath(".//*[@id='gwt-debug-EditActivity-regardingPanel']/button"));

		return this;
	}

	/**
	 * 
	 * @param type
	 *            : Investor or Investor Account
	 * @param name
	 *            : Name of the regarding
	 * @return {@link CalendarPage}
	 * @throws InterruptedException
	 */
	public CalendarPage addRegarding(String type, String name) throws InterruptedException {

		waitForElementVisible(By.id("gwt-debug-SelectionUi-typeListBox"), 30);
		selectFromDropdown(By.id("gwt-debug-SelectionUi-typeListBox"), type);

		// wait for source list to be refreshed
		wait(2);
		selectFromDropdown(By.id("gwt-debug-PairedListBoxSelector-sourceList"), name);

		clickElement(By.id("gwt-debug-PairedListBoxSelector-addImg"));

		return this;
	}

	/**
	 * click the OK button in regarding panel to confirm regarding
	 * 
	 * @return {@link CalendarPage}
	 */
	public CalendarPage clickConfirmRegardingButton() {

		clickElement(By.id("gwt-debug-SelectionUi-confirmButton"));

		return this;
	}

}
