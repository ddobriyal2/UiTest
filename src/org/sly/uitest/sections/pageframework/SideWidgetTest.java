package org.sly.uitest.sections.pageframework;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.abstractpage.MenuBarPage;
import org.sly.uitest.pageobjects.clientsandaccounts.AccountOverviewPage;
import org.sly.uitest.settings.Settings;

/**
 * Tests the side widgets which the advisor can customize.
 * 
 * * @see <a href=
 * "https://docs.google.com/a/wismore.com/spreadsheets/d/1CuSpAEXyLu1SNdBFqHdWrSOkC06VJIAkEJMovFPo8RE/edit#gid=0"
 * >here</a>
 * 
 * @author Julian Schillinger
 * @date : Jan 2, 2014
 * @company Prive Financial
 */
public class SideWidgetTest extends AbstractTest {

	/**
	 * Adds a single widget and then removes it.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSingle() throws Exception {

		setScreenshotSuffix(SideWidgetTest.class.getSimpleName());

		checkLogin();

		AccountOverviewPage accountOverviewPage = new AccountOverviewPage(webDriver);
		accountOverviewPage.goToAccountOverviewPage();

		boolean isMaterial = isElementVisible(By.xpath(".//*[@id='page-wrapper']/div[1]/nav/div/a/i"));
		log("isMaterial " + isMaterial);
		/*
		 * Cleanup
		 */

		// Make sure all previous entries are removed
		removeAllWidgets(isMaterial);

		/*
		 * Test Adding of new content widgets
		 */

		// add new widget
		addSideWidget("TestWidget1", "TestWidgetText1", isMaterial);

		// ensure it is there
		assertTrue(pageContainsStr("TestWidget1"));
		assertTrue(pageContainsStr("TestWidgetText1"));

		/*
		 * Test removing of widgets
		 */
		removeAllWidgets(isMaterial);
		this.waitForElementNotVisible(By.id("gwt-debug-TeaserCustomization-removeBtn"), 2 * Settings.WAIT_SECONDS);

		List<WebElement> teaserRemoveButtons = webDriver.findElements(By.id("gwt-debug-TeaserCustomization-removeBtn"));
		assertTrue(teaserRemoveButtons.size() == 0);

	}

	/**
	 * Adds a MANY widgets and then removes them.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testMany() throws Exception {

		// this is known to fail due to a bug. See JIRA 2977

		// check and login
		checkLogin();

		AccountOverviewPage accountOverviewPage = new AccountOverviewPage(webDriver);
		accountOverviewPage.goToAccountOverviewPage();

		boolean isMaterial = isElementVisible(By.xpath(".//*[@id='page-wrapper']/div[1]/nav/div/a/i"));

		/*
		 * Cleanup
		 */

		// Make sure all previous entries are removed
		removeAllWidgets(isMaterial);

		/*
		 * Test Adding of new content widgets
		 */

		// add new widget
		addSideWidget("TestWidget1", "TestWidgetText1", isMaterial);
		addSideWidget("TestWidget2", "TestWidgetText2", isMaterial);
		addSideWidget("TestWidget3", "TestWidgetText3", isMaterial);

		// ensure it is there
		assertTrue(pageContainsStr("TestWidget1"));
		assertTrue(pageContainsStr("TestWidgetText1"));
		assertTrue(pageContainsStr("TestWidget2"));
		assertTrue(pageContainsStr("TestWidgetText2"));
		assertTrue(pageContainsStr("TestWidget3"));
		assertTrue(pageContainsStr("TestWidgetText3"));

		/*
		 * Test removing of widgets
		 */
		removeAllWidgets(isMaterial);
		this.waitForWaitingScreenNotVisible();
		List<WebElement> teaserRemoveButtons = webDriver.findElements(By.id("gwt-debug-TeaserCustomization-removeBtn"));
		assertTrue(teaserRemoveButtons.size() == 0);
	}

	/**
	 * Adds a content widget to the page with given title and content text.
	 * 
	 * @param title
	 * @param content
	 * @throws InterruptedException
	 */
	private void addSideWidget(String title, String content, boolean isMaterial) throws InterruptedException {
		wait(1);

		if (isMaterial) {
			By byOfAddButton = By.xpath(
					"//div[@class='sidebar-closed sidebar-open']//button[@id='gwt-debug-TeaserCustomizationOptions-addBtn']");
			if (!isElementVisible(byOfAddButton)) {
				clickElement(By.className("right-sidebar-toggle"));
			}
			this.waitForElementVisible(byOfAddButton, Settings.WAIT_SECONDS);
			clickElement(byOfAddButton);
			this.waitForElementPresent(By.className("teaserGeneralPanel"), Settings.WAIT_SECONDS);
			List<WebElement> boxes = webDriver.findElements(By.className("teaserGeneralPanel"));
			WebElement newBox = boxes.get(boxes.size() - 2);

			sendKeysToElement(By
					.xpath("//div[@class='sidebar-closed sidebar-open']//*[@id='gwt-debug-TeaserCustomizationsPanel-localWidgetsPanel']/div["
							+ (boxes.size() - 2) + "]/div[3]/div/input[1]"),
					title);

			sendKeysToElement(By
					.xpath("//div[@class='sidebar-closed sidebar-open']//*[@id='gwt-debug-TeaserCustomizationsPanel-localWidgetsPanel']/div["
							+ (boxes.size() - 2) + "]/div[3]/div/textarea"),
					content);

			clickElement(By
					.xpath("//div[@class='sidebar-closed sidebar-open']//*[@id='gwt-debug-TeaserCustomizationsPanel-localWidgetsPanel']/div["
							+ (boxes.size() - 2) + "]/div[4]/button[2]"));
		} else {

			// wait(3);
			clickElement(By.id("gwt-debug-TeaserCustomizationOptions-addBtn"));
			this.waitForElementVisible(By.className("teaserGeneralPanel"), Settings.WAIT_SECONDS);
			waitForElementVisible(By.className("teaserGeneralPanel"), 30);
			List<WebElement> boxes = webDriver.findElements(By.className("teaserGeneralPanel"));
			WebElement newBox = boxes.get(boxes.size() - 2);

			sendKeysToElement(By.xpath("//*[@id='gwt-debug-TeaserCustomizationsPanel-localWidgetsPanel']/div["
					+ (boxes.size() - 1) + "]/div[3]/div/input[1]"), title);

			sendKeysToElement(By.xpath("//*[@id='gwt-debug-TeaserCustomizationsPanel-localWidgetsPanel']/div["
					+ (boxes.size() - 1) + "]/div[3]/div/textarea"), content);

			clickElement(By.xpath("//*[@id='gwt-debug-TeaserCustomizationsPanel-localWidgetsPanel']/div["
					+ (boxes.size() - 1) + "]/div[4]/button[2]"));
		}

	}

	/**
	 * Removes all widgets by clicking the remove button from the side bar.
	 * 
	 * @throws InterruptedException
	 */
	private void removeAllWidgets(boolean isMaterial) throws InterruptedException {

		if (isMaterial) {
			if (!isElementVisible(By.xpath(
					"//div[@class='sidebar-closed sidebar-open']//button[@id='gwt-debug-TeaserCustomizationOptions-addBtn']"))) {
				clickElement(By.className("right-sidebar-toggle"));
			}
			// assertTrue(isElementVisible(By.id("gwt-debug-TeaserCustomization-title")));
			List<WebElement> teaserRemoveButtonList = webDriver
					.findElements(By.id("gwt-debug-TeaserCustomization-title"));

			if (isNullOrEmptyColl(teaserRemoveButtonList)) {
				return;
			}
			log(String.valueOf(teaserRemoveButtonList.size()));
			for (@SuppressWarnings("unused")
			WebElement elem : teaserRemoveButtonList) {
				wait(2);
				Actions builder = new Actions(webDriver);
				WebElement clientMenuButton = webDriver.findElement(By.xpath(
						"//div[@class='sidebar-closed sidebar-open']//*[@id='gwt-debug-TeaserCustomization-title']"));
				builder.moveToElement(clientMenuButton).click().build().perform();
				clickElement(By.xpath(
						"//div[@class='sidebar-closed sidebar-open']//*[@id='gwt-debug-TeaserCustomization-removeBtn']"));

				assertTrue(pageContainsStr("Do you want to remove widget"));
				clickElement(By.id("gwt-debug-CustomDialog-yesButton"));

			}
		} else {

			List<WebElement> teaserRemoveButtonList = webDriver
					.findElements(By.id("gwt-debug-TeaserCustomization-title"));

			if (isNullOrEmptyColl(teaserRemoveButtonList)) {
				return;
			}
			assertTrue(isElementVisible(By.id("gwt-debug-TeaserCustomization-title")));

			log(String.valueOf(teaserRemoveButtonList.size()));
			for (@SuppressWarnings("unused")
			WebElement elem : teaserRemoveButtonList) {
				Actions builder = new Actions(webDriver);
				WebElement clientMenuButton = webDriver.findElement(By.id("gwt-debug-TeaserCustomization-title"));
				builder.moveToElement(clientMenuButton).perform();
				clickElement(By.id("gwt-debug-TeaserCustomization-removeBtn"));

				assertTrue(pageContainsStr("Do you want to remove widget"));

				clickElement(By.id("gwt-debug-CustomDialog-yesButton"));

			}
		}
	}

	@Test
	public void testNewContentWidget() throws InterruptedException {

		String widget = "Add New Content Widget";
		String title1 = "New Content Test" + getRandName();
		String title2 = "New Content Test" + getRandName();
		String content1 = "This is a new content widget." + getRandName();
		String content2 = "This is an old content widget." + getRandName();
		String newNameInBlue = "New Content Widget";

		LoginPage main = new LoginPage(webDriver);

		// create
		MenuBarPage sidebar = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToSideManager();
		sidebar.addNewWidget(widget).editWidgetTitle(title1, newNameInBlue).editWidgetContent(content1, newNameInBlue)
				.clickSaveButton(newNameInBlue);

		assertTrue(pageContainsStr(title1));
		assertTrue(pageContainsStr(content1));

		// edit
		sidebar.editWidgetByName(title1).editWidgetTitle(title2, title1).editWidgetContent(content2, title1)
				.clickSaveButton(title1);

		assertTrue(pageContainsStr(title2));
		assertTrue(pageContainsStr(content2));

		// delete
		sidebar.deleteWidgetByName(title2);
		this.waitForElementNotVisible(By.xpath(".//*[contains(text(),'" + title2 + "')]"), Settings.WAIT_SECONDS);
		assertFalse(pageContainsStr(title2));
		assertFalse(pageContainsStr(content2));

	}

	@Test
	public void testNewIndicesWidget() throws InterruptedException {

		String widget = "Add New Indices Widget";
		String newNameInBlue = "Market Indices";
		String index1 = "S&P 500 PR";
		String index2 = "Shanghai SE Composite PR CNY";

		LoginPage main = new LoginPage(webDriver);

		// create
		MenuBarPage sidebar = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToSideManager();

		if (pageContainsStr(newNameInBlue)) {
			sidebar.deleteWidgetByName(newNameInBlue);
		}

		sidebar.addNewWidget(widget).clickCreateButton(newNameInBlue);

		assertTrue(pageContainsStr(index1));
		assertTrue(pageContainsStr(index2));
		assertTrue(pageContainsStr(newNameInBlue));

		// delete
		sidebar.deleteWidgetByName(newNameInBlue);
		this.waitForElementNotVisible(By.xpath(".//*[contains('" + newNameInBlue + "')]"), Settings.WAIT_SECONDS);
		assertFalse(pageContainsStr(newNameInBlue));
	}

	@Test
	public void testNewRSSWidget() throws InterruptedException {

		String widget = "Add New RSS Widget";
		String title1 = "New RSS Test" + getRandName();
		String title2 = "New RSS Test" + getRandName();
		String rssUrl = "https://news.google.com/?output=rss";
		int row = 3;
		String newNameInBlue = "New RSS Widget";

		LoginPage main = new LoginPage(webDriver);

		// create
		MenuBarPage sidebar = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToSideManager().addNewWidget(widget)
				.editWidgetTitle(title1, newNameInBlue).editWidgetRssFeedUrl("", newNameInBlue)
				.clickSaveButton(newNameInBlue);

		assertTrue(pageContainsStr(title1));
		assertTrue(pageContainsStr("error occurs when parsing rss feed"));

		// edit
		sidebar.editWidgetByName(title1).editWidgetTitle(title2, title1).editWidgetRssFeedUrl(rssUrl, title1)
				.editWidgetMaxRowsToDisplay(row, title1).clickSaveButton(title1);

		this.waitForElementVisible(By.className("widget_rss_li"), Settings.WAIT_SECONDS * 2);

		int size = getSizeOfElements(By.className("widget_rss_li"));
		assertTrue(pageContainsStr(title2));
		assertTrue(size <= row);

		// delete
		sidebar.deleteWidgetByName(title2);
		this.waitForElementNotVisible(By.xpath(".//*[contains(text(),'abc')]"), Settings.WAIT_SECONDS * 2);
		assertFalse(pageContainsStr(title2));

	}
}
