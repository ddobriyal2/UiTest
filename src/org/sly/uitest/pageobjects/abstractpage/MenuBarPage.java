package org.sly.uitest.pageobjects.abstractpage;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.settings.Settings;

/**
 * This class represents the general page including sidebar manager
 * 
 * @author Lynne Huang
 * @date : 20 Aug, 2015
 * @company Prive Financial
 */
public class MenuBarPage extends AbstractPage {

	public MenuBarPage(WebDriver webDriver) {

		super();
		this.webDriver = webDriver;

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(300, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);
		try {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.id("gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel")));
			// this.waitForWaitingScreenNotVisible();
		} catch (Exception ex) {
			// wait.until(ExpectedConditions.visibilityOfElementLocated(By
			// .id("gwt-debug-MyMainClassicView-topPanel")));
		}

		// assertTrue(pageContainsStr(" Admin Edit Screen "));
	}

	/**
	 * Choose a type of the new widget to create and click the green plus icon
	 * on the right of the dropdown field
	 * 
	 * @param type
	 *            the type of the new widget
	 * @return {@link MenuBarPage}
	 * @throws InterruptedException
	 */
	public MenuBarPage addNewWidget(String type) throws InterruptedException {
		this.waitForElementVisible(By.xpath(".//*[@id='page-wrapper']/div[1]/nav/div/a/i"), Settings.WAIT_SECONDS);
		Boolean isMaterial = isElementVisible(By.xpath(".//*[@id='page-wrapper']/div[1]/nav/div/a/i"));
		Boolean isMaterialURL = webDriver.getCurrentUrl().indexOf("MATERIAL") > -1;
		log(String.valueOf(isMaterial));
		if (isMaterial || isMaterialURL) {
			By byOfAddButton = By.xpath(
					"//div[@class='sidebar-closed sidebar-open']//button[@id='gwt-debug-TeaserCustomizationOptions-addBtn']");
			if (!isElementVisible(byOfAddButton)) {
				clickElement(By.className("right-sidebar-toggle"));
			}
			selectFromDropdown(
					By.xpath(
							"//div[@class='sidebar-closed sidebar-open']//*[@id='gwt-debug-TeaserCustomizationOptions-optionList']"),
					type);

			clickElement(byOfAddButton);
		} else {
			waitForElementVisible(By.xpath("//*[@id='gwt-debug-TeaserCustomizationOptions-optionList']"), 30);
			selectFromDropdown(By.xpath("//*[@id='gwt-debug-TeaserCustomizationOptions-optionList']"), type);

			clickElement(By.id("gwt-debug-TeaserCustomizationOptions-addBtn"));
		}

		return this;
	}

	/**
	 * Edit the widget title, which is named nameInBlue
	 * 
	 * @param title
	 *            the title of the widget
	 * @param nameInBlue
	 *            the existing name of the widget that is to be edited
	 * @return {@link MenuBarPage}
	 */
	public MenuBarPage editWidgetTitle(String title, String nameInBlue) {

		sendKeysToElement(By.xpath("//div[div[.='" + nameInBlue
				+ "']]/following-sibling::div//div[.='Title']/following-sibling::input[1]"), title);

		return this;
	}

	/**
	 * When create or edit a New Content Widget, edit the widget image url,
	 * which is named nameInBlue
	 * 
	 * @param url
	 *            the image url of the widget
	 * @param nameInBlue
	 *            the existing name of the widget that is to be edited
	 * @return {@link MenuBarPage}
	 * 
	 */
	public MenuBarPage editWidgetImageUrl(String url, String nameInBlue) {

		sendKeysToElement(By.xpath("//div[div[.='" + nameInBlue
				+ "']]/following-sibling::div//div[.='Image Url']/following-sibling::input[1]"), url);

		return this;
	}

	/**
	 * When create or edit a New Content Widget, edit the widget video url,
	 * which is named nameInBlue
	 * 
	 * @param url
	 *            the video url of the widget
	 * @param nameInBlue
	 *            the existing name of the widget that is to be edited
	 * @return {@link MenuBarPage}
	 * 
	 */
	public MenuBarPage editWidgetVideoUrl(String url, String nameInBlue) {

		sendKeysToElement(
				By.xpath("//div[div[.='" + nameInBlue
						+ "']]/following-sibling::div//div[.='Video Url (only youtube supported)']/following-sibling::input[1]"),
				url);

		return this;
	}

	/**
	 * When create or edit a New Content Widget, edit the widget content, which
	 * is named nameInBlue
	 * 
	 * @param content
	 *            the content of the widget
	 * @param nameInBlue
	 *            the existing name of the widget that is to be edited
	 * @return {@link MenuBarPage}
	 * 
	 */
	public MenuBarPage editWidgetContent(String content, String nameInBlue) {

		sendKeysToElement(By.xpath("//div[div[.='" + nameInBlue
				+ "']]/following-sibling::div//div[.='Content']/following-sibling::textarea[1]"), content);

		return this;
	}

	/**
	 * When create or edit a New RSS Widget, edit the widget RSS Feed with the
	 * given url, the widget is named as nameInBlue
	 * 
	 * @param url
	 *            the RSS Feed URL of the widget
	 * @param nameInBlue
	 *            the existing name of the widget that is to be edited
	 * @return {@link MenuBarPage}
	 */
	public MenuBarPage editWidgetRssFeedUrl(String url, String nameInBlue) {

		sendKeysToElement(By.xpath("//div[div[.='" + nameInBlue
				+ "']]/following-sibling::div//div[.='RSS Feed Url']/following-sibling::input[1]"), url);

		return this;

	}

	/**
	 * When create or edit a New RSS Widget, edit the max rows to display the
	 * news, the widget is named as nameInBlue
	 * 
	 * @param rows
	 *            the number of rows to display
	 * @param nameInBlue
	 *            the existing name of the widget that is to be edited
	 * @return {@link MenuBarPage}
	 */
	public MenuBarPage editWidgetMaxRowsToDisplay(int rows, String nameInBlue) {

		String rowString = Integer.toString(rows);

		sendKeysToElement(
				By.xpath("//div[div[.='" + nameInBlue
						+ "']]/following-sibling::div//div[.='Max Rows To Display']/following-sibling::input[1]"),
				rowString);

		return this;

	}

	/**
	 * Click the CREATE button to create a Market Indices
	 * 
	 * @param nameInBlue
	 *            the existing name of the widget that is to be edited
	 * @return {@link MenuBarPage}
	 * 
	 */
	public MenuBarPage clickCreateButton(String nameInBlue) {

		clickElement(By.xpath("//div[div[.='" + nameInBlue + "']]/following-sibling::div[button]/button[.='Create']"));

		return this;
	}
	

	/**
	 * Click the SAVE button to create a new widget or save the edited widget
	 * 
	 * @param nameInBlue
	 *            the existing name of the widget that is to be saved
	 * @return {@link MenuBarPage}
	 */
	public MenuBarPage clickSaveButton(String nameInBlue) {

		clickElement(By.xpath("//div[div[.='" + nameInBlue + "']]/following-sibling::div[button]/button[.='Save']"));

		return this;
	}

	/**
	 * Click the CANCEL button to cancel the new or the edited widget
	 * 
	 * @param nameInBlue
	 *            the existing name of the widget that is to be canceled
	 * @return {@link MenuBarPage}
	 */
	public MenuBarPage clickCancelButton(String nameInBlue) {

		clickElement(By.xpath("//div[div[.='" + nameInBlue + "']]/following-sibling::div[button]/button[.='Cancel']"));

		return this;
	}

	/**
	 * Click the edit icon(pencle) to edit the widget with the given name
	 *
	 * @param name
	 *            the name of the widget to be edited
	 * @return {@link MenuBarPage}
	 * @throws InterruptedException
	 * 
	 */
	public MenuBarPage editWidgetByName(String name) throws InterruptedException {

		navigateToPage(By.xpath(".//*[@id='gwt-debug-TeaserCustomization-title' and .='" + name + "']"), By.xpath(
				"//div[.='" + name + "']/following-sibling::button[@id='gwt-debug-TeaserCustomization-editBtn']"));

		return this;
	}

	/**
	 * Click the red delete icon to delete the widget with the given name
	 * 
	 * @param name
	 *            the name of the widget to be edited
	 * @return {@link MenuBarPage}
	 * @throws InterruptedException
	 * 
	 */
	public MenuBarPage deleteWidgetByName(String name) throws InterruptedException {

		int size = getSizeOfElements(
				By.xpath(".//*[@class='sidebar-closed sidebar-open']//*[@id='gwt-debug-TeaserCustomization-title']"));
		if (size > 0) {
			waitForElementVisible(By.xpath("(.//*[@id='gwt-debug-TeaserCustomization-title' and .='" + name + "'])["
					+ String.valueOf(size) + "]"), 10);

			WebElement elem = webDriver.findElement(By.xpath("(.//*[@id='gwt-debug-TeaserCustomization-title' and .='"
					+ name + "'])[" + String.valueOf(size) + "]"));

			new Actions(webDriver).moveToElement(elem).clickAndHold().build().perform();

			// waitForElementVisible(
			// By.xpath(".//*[@id='gwt-debug-TeaserCustomization-title' and .='"
			// + name
			// +
			// "']/following-sibling::button[@id='gwt-debug-TeaserCustomization-removeBtn']"),
			// 10);

			clickElement(By.xpath("//div[.='" + name
					+ "']/following-sibling::button[@id='gwt-debug-TeaserCustomization-removeBtn']"));

			clickYesButtonIfVisible();

		}

		return this;
	}

	/**
	 * Remove the export file in the process panel (process panel is on the top
	 * right, which could be opened by clicking the Process icon)
	 * 
	 * @param name
	 *            the name of the export file
	 * @return {@link MenuBarPage}
	 * @throws InterruptedException
	 */
	public MenuBarPage removeExportFile(String name) throws InterruptedException {

		clickElement(By.xpath("//tr[td[div[contains(text(),'" + name
				+ "')]]]/following-sibling::tr//a[@id='gwt-debug-ProcessImageMenuButton-removeLink']"));

		clickYesButtonIfVisible();

		clickOkButtonIfVisible();

		return this;
	}

	public MenuBarPage downloadExportFile(String name) {
		By by = By.xpath("//tr[td[div[contains(text(),'" + name
				+ "')]]]/following-sibling::tr//a[@id='gwt-debug-ProcessImageMenuButton-exportLink']");
		waitForElementVisible(by, 240);
		assertTrue(isElementVisible(by));
		clickElement(by);

		return this;
	}
}
