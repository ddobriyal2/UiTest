package org.sly.uitest.sections.pageframework;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.abstractpage.MenuBarPage;
import org.sly.uitest.settings.Settings;

/**
 * This test is for the following test plan:
 * https://docs.google.com/spreadsheets
 * /d/1K9orbhiGgKQgUnZ10LVgvalr0frL_ZnA_7eCkW2Ibfo/edit#gid=0
 * 
 * @author Benny Leung
 * @date Oct 13 ,2016
 * @company Prive Financial
 */
public class MaterialFrameworkTest extends AbstractTest {

	@Test
	public void testPreLogin() throws InterruptedException {
		LoginPage main = new LoginPage(webDriver);
		// Only login in side menu bar
		assertEquals("Login", getTextByLocator(
				By.xpath(".//div[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a//span")));
		// Only language settings in head menu bar
		assertEquals(1, getSizeOfElements(
				By.xpath(".//div[@id='gwt-debug-MyMainMaterialView-myMaterialTopMenuBarPanel']//ul//ul")));

		assertTrue(isElementVisible(By.xpath(".//a[@title = 'Language']")));

		clickElement(By.xpath(".//a[@title = 'Language']"));
		checkLanguageList();

	}

	@Test
	public void testReponsiveness() throws InterruptedException {
		LoginPage main = new LoginPage(webDriver);
		webDriver.manage().window().setSize(new Dimension(2000, 1300));

		checkingElementOnLoginPage(true);

		clickElement(By.xpath(".//*[@id='page-wrapper']/div[1]/nav/div/a/i"));

		checkingElementOnLoginPage(false);

		webDriver.manage().window().setSize(new Dimension(800, 800));

		checkingElementOnLoginPage(false);

		clickElement(By.xpath(".//*[@id='page-wrapper']/div[1]/nav/div/a/i"));

		checkingElementOnLoginPage(true);
	}

	@Test
	public void testMenubarForAdvisor() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);
		main.loginAs(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD);
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 30);

		ArrayList<String> sideComponentList = new ArrayList<String>();
		sideComponentList.add("Clients & Accounts");
		sideComponentList.add("Investments");
		sideComponentList.add("CRM");
		sideComponentList.add("Asset Management");
		sideComponentList.add("Planning");
		sideComponentList.add("Commissioning");
		sideComponentList.add("Operations");
		sideComponentList.add("Workflow");
		sideComponentList.add("My Settings");
		sideComponentList.add("Company Admin");

		checkSideMenubarComponent(sideComponentList);

		assertTrue(isElementVisible(By.xpath(
				".//div[@id='gwt-debug-MyMainMaterialView-myMaterialTopMenuBarPanel']//span[contains(text(),'Welcome')]")));

		ArrayList<String> topComponentList = new ArrayList<String>();
		topComponentList.add("Alerts");
		topComponentList.add("Help");
		topComponentList.add("Processes");
		topComponentList.add("Language");
		topComponentList.add("Logout");
		topComponentList.add("Sidebar Manager");

		checkTopSideMenubarComponent(topComponentList);

		main.goToProcess();
		assertTrue(
				isElementVisible(By.xpath(".//div[@class='mat-processImageMenuPopup']//div[@class='popupContent']")));

		checkLanguageList();

	}

	@Test
	public void testMenubarForInvestor() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);
		main.loginAs("JarlInvestor", "JarlInvestor");
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 30);

		ArrayList<String> sideComponentList = new ArrayList<String>();
		sideComponentList.add("Accounts");
		sideComponentList.add("Investments");
		sideComponentList.add("Asset Management");
		sideComponentList.add("My Settings");

		checkSideMenubarComponent(sideComponentList);

		assertTrue(isElementVisible(By.xpath(
				".//div[@id='gwt-debug-MyMainMaterialView-myMaterialTopMenuBarPanel']//span[contains(text(),'Welcome')]")));

		ArrayList<String> topComponentList = new ArrayList<String>();
		topComponentList.add("Contact Info");
		topComponentList.add("Processes");
		topComponentList.add("Language");
		topComponentList.add("Logout");
		topComponentList.add("Sidebar Manager");

		checkTopSideMenubarComponent(topComponentList);

		main.goToProcess();
		assertTrue(
				isElementVisible(By.xpath(".//div[@class='mat-processImageMenuPopup']//div[@class='popupContent']")));

		checkLanguageList();

		main.goToContact();
		assertTrue(isElementVisible(By.xpath(".//div[@class='gwt-PopupPanel faContactInfo']")));
	}

	@Test
	public void testRedIcon() throws InterruptedException {
		LoginPage main = new LoginPage(webDriver);

		main.loginAs(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD).goToAlertsPageFromManage()

				// if (isElementVisible(By
				// .id("gwt-debug-TriggeredUserAlertListWidget-tablePanel"))) {
				// assertTrue(isElementVisible(By
				// .xpath(".//a[@title = 'Alerts']//span[@class='label
				// label-primary']")));
				// }
				//
				// main
				.goToProcess();
		if (isElementVisible(By.xpath(".//div[@class='mat-processImageMenuPopup']"))) {
			assertTrue(isElementVisible(By.xpath(".//a[@title = 'Processes']//span[@class='label label-primary']")));
		}

	}

	public void checkingElementOnLoginPage(boolean normalSize) {

		try {
			waitForElementVisible(By.xpath(".//a[@title = 'Language']"), 20);
		} catch (TimeoutException e) {

		}
		assertTrue(isElementVisible(By.xpath(".//a[@title = 'Language']")));

		if (normalSize) {
			// company logo appears
			assertTrue(isElementVisible(By.xpath(".//div[@id ='gwt-debug-MyMainMaterialView-logoPanel']/img")));
			// logout logo appears
			assertTrue(isElementVisible(By.xpath(
					".//div[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//i[@class='fa fa-sign-out']")));
			// login text appears
			assertTrue(isElementVisible(
					By.xpath(".//div[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//span[.='Login']")));
		} else {
			// company logo does not appear
			assertFalse(isElementVisible(By.xpath(".//div[@id ='gwt-debug-MyMainMaterialView-logoPanel']/img")));
			// logout logo does not appear
			assertFalse(isElementVisible(By.xpath(
					".//div[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//i[@class='fa fa-sign-out']")));
			// login text does not appear
			assertFalse(isElementVisible(
					By.xpath(".//div[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//span[.='Login']")));
		}
	}

	public void checkSideMenubarComponent(ArrayList<String> componentList) {
		this.waitForWaitingScreenNotVisible();
		for (String component : componentList) {

			try {
				waitForElementVisible(By
						.xpath(".//div[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//span[contains(text(),'"
								+ component + "')]"),
						20);
			} catch (TimeoutException e) {

			}

			assertTrue(isElementVisible(
					By.xpath(".//div[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//span[contains(text(),'"
							+ component + "')]")));

			WebElement elem = webDriver.findElement(
					By.xpath(".//div[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//span[contains(text(),'"
							+ component + "')]"));

			// assertion that the element is clickable.
			WebDriverWait wait = new WebDriverWait(webDriver, 1);
			wait.until(ExpectedConditions.elementToBeClickable(elem));
		}
	}

	public void checkTopSideMenubarComponent(ArrayList<String> componentList) {
		for (String component : componentList) {

			waitForElementVisible(
					By.xpath(".//div[@id='gwt-debug-MyMainMaterialView-myMaterialTopMenuBarPanel']//a[@title='"
							+ component + "']"),
					30);

			assertTrue(isElementVisible(
					By.xpath(".//div[@id='gwt-debug-MyMainMaterialView-myMaterialTopMenuBarPanel']//a[@title='"
							+ component + "']")));

			WebElement elem = webDriver.findElement(
					By.xpath(".//div[@id='gwt-debug-MyMainMaterialView-myMaterialTopMenuBarPanel']//a[@title='"
							+ component + "']"));

			// assertion that the element is clickable.
			WebDriverWait wait = new WebDriverWait(webDriver, 1);
			wait.until(ExpectedConditions.elementToBeClickable(elem));
		}
	}

	public void checkLanguageList() throws InterruptedException {

		MenuBarPage menu = new MenuBarPage(webDriver);

		menu.goToLanguage();

		ArrayList<String> languageList = new ArrayList<String>();
		languageList.add("English");
		languageList.add("Deutsch");
		languageList.add("日本語");
		languageList.add("한글");
		languageList.add("简体中文");
		languageList.add("繁體中文(香港)");
		languageList.add("繁體中文(台灣)");
		languageList.add("Français");
		languageList.add("Bahasa");

		for (String language : languageList) {
			assertTrue(pageContainsStr(language));
		}

	}
}
