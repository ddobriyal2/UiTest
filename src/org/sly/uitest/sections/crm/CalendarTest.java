package org.sly.uitest.sections.crm;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.crm.CalendarPage;
import org.sly.uitest.settings.Settings;

/**
 * Tests calendar event-related features.
 * 
 * @author Harry Chin
 * @date : Jul 28, 2014
 * @company Prive Financial
 */
public class CalendarTest extends AbstractTest {

	/**
	 * Tests opening the calendar overview page.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testOpenCalendarOverview() throws Exception {

		setScreenshotSuffix(CalendarTest.class.getSimpleName());

		LoginPage main = new LoginPage(webDriver);

		CalendarPage calendarPage = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToCalendarPage();

		waitForElementVisible(By.xpath(".//*[contains(text(),'Calendar Events')]"), 10);

		assertTrue(pageContainsStr("Calendar Events"));

	}

	/**
	 * Tests creating and completing calendar events.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateCalenderEvents() throws Exception {

		setScreenshotSuffix(CalendarTest.class.getSimpleName());

		LoginPage main = new LoginPage(webDriver);

		CalendarPage calendarPage = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToCalendarPage();

		// Get random activity name, do not make too long
		String randomActivityName1 = getRandName();
		log("Random Activity Name 1: " + randomActivityName1);
		String randomActivityName2 = getRandName();
		log("Random Activity Name 2: " + randomActivityName2);

		/*
		 * Create activity
		 */
		{
			// wait(Settings.WAIT_SECONDS);
			calendarPage.addNewEvent().editActivityName(randomActivityName1).clickSaveActivityButton();

			assertTrue(pageContainsStr(randomActivityName1));
		}

		/*
		 * Change activity name
		 */
		{
			calendarPage.enterActivityByName("14:00 " + randomActivityName1).editActivityName(randomActivityName2)
					.clickSaveActivityButton();

			assertTrue(pageContainsStr(randomActivityName2));
		}

		/*
		 * Complete activity
		 */
		{
			calendarPage.enterActivityByName("14:00 " + randomActivityName2).editActivityType("Completed")
					.editFinishedDateForCompletedActivity(String.format("%1$td-%1$tb-%1$tY", new Date()))
					.clickSaveActivityButton();

			wait(2);

			List<WebElement> list = webDriver
					.findElements(By.xpath("//*[@class='calMonthEventLink' and .='" + randomActivityName2 + "']"));
			assertTrue("Text not found!", list.size() == 0);
		}

	}

	/**
	 * Tests opening the create calendar event dialog and opening the regarding
	 * dialog.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateCalenderEventOpenRegardingDialog() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		CalendarPage calendarPage = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToCalendarPage();

		// Get random activity name, do not make too long
		String randomActivityName1 = getRandName();
		log("Random Activity Name 1: " + randomActivityName1);
		String randomActivityName2 = getRandName();
		log("Random Activity Name 2: " + randomActivityName2);

		/*
		 * Create activity
		 */
		{
			// wait(Settings.WAIT_SECONDS);
			calendarPage.addNewEvent().editActivityName(randomActivityName1).openRegardingPanel()
					.clickConfirmRegardingButton();

			assertTrue(!pageContainsStr("Filter By :"));
		}

	}

	/**
	 * Tests opening the create calendar event dialog and opening the regarding
	 * dialog.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateCalenderEventRegardingInvestor() throws Exception {

		setScreenshotSuffix(CalendarTest.class.getSimpleName());

		LoginPage main = new LoginPage(webDriver);

		CalendarPage calendarPage = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToCalendarPage();

		// Get random activity name, do not make too long
		String randomActivityName1 = getRandName();
		log("Random Activity Name 1: " + randomActivityName1);
		String randomActivityName2 = getRandName();
		log("Random Activity Name 2: " + randomActivityName2);

		/*
		 * Create activity
		 */
		{
			// wait(Settings.WAIT_SECONDS);
			calendarPage.addNewEvent().editActivityName(randomActivityName1).openRegardingPanel()
					.addRegarding("Investor", "Dummy User (xxxxx)").clickConfirmRegardingButton();

			assertTrue(pageContainsStr("Dummy User"));

			calendarPage.clickSaveActivityButton();

			assertTrue(pageContainsStr(randomActivityName1));

		}

	}

	/**
	 * Test creating a calendar event regarding an investor account.
	 * 
	 * @throws Exception
	 */

	@Test
	public void testCreateCalenderEventRegardingInvestorAccount() throws Exception {

		// setScreenshotSuffix(CalendarTest.class.getSimpleName());

		LoginPage main = new LoginPage(webDriver);

		CalendarPage calendarPage = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToCalendarPage();

		// Get random activity name, do not make too long
		String randomActivityName1 = "Random Activity Name 1: " + getRandName();
		log("Random Activity Name 1: " + randomActivityName1);
		String randomActivityName2 = "Random Activity Name 2: " + getRandName();
		log("Random Activity Name 2: " + randomActivityName2);

		/*
		 * Create activity
		 */
		{
			// wait(2 * Settings.WAIT_SECONDS);
			calendarPage.addNewEvent().editActivityName(randomActivityName1).openRegardingPanel()
					.addRegarding("Investor Account", "Selenium Test (Selenium Investor)").clickConfirmRegardingButton()
					.clickSaveActivityButton();
			assertTrue(pageContainsStr(randomActivityName1));

		}

		/*
		 * Change activity name
		 */
		{
			calendarPage.enterActivityByName("14:00 " + randomActivityName1).editActivityName(randomActivityName2)
					.clickSaveActivityButton();

			assertTrue(pageContainsStr(randomActivityName2));

		}

		/*
		 * Complete activity
		 */
		{
			calendarPage.enterActivityByName("14:00 " + randomActivityName2).editActivityType("Completed")
					.editFinishedDateForCompletedActivity(String.format("%1$td-%1$tb-%1$tY", new Date()))
					.clickSaveActivityButton();

			waitForElementVisible(By.id("gwt-debug-CalendarEventsView-calendarPanel"), 10);

			List<WebElement> list = webDriver
					.findElements(By.xpath("//*[@class='calMonthEventLink' and .='" + randomActivityName2 + "']"));

			log(String.valueOf(list.size()));
			assertTrue("Text not found!", list.size() == 0);

		}

	}

}
