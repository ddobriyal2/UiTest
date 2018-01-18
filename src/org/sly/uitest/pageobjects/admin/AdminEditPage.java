package org.sly.uitest.pageobjects.admin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.pageobjects.abstractpage.MenuBarPage;
import org.sly.uitest.settings.Settings;

/**
 * This class represents the Edit Page when login as system admin
 * 
 * @author Lynne Huang
 * @date : 8 Sep, 2015
 * @company Prive Financial
 */
public class AdminEditPage extends AbstractPage {

	/**
	 * @param webDriver
	 */
	public AdminEditPage(WebDriver webDriver) {

		super();
		this.webDriver = webDriver;

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		/*
		 * try { wait.until(ExpectedConditions.visibilityOfElementLocated(By
		 * .id("gwt-debug-MyMainClassicView-topPanel"))); } catch (Exception ex)
		 * { ex.printStackTrace();
		 * wait.until(ExpectedConditions.visibilityOfElementLocated(By
		 * .id("gwt-debug-MyMainMaterialView-myMaterialTopMenuBarPanel"))); }
		 */
	}

	/**
	 * 
	 * On the Admin Edit Screen page, click the name of the link to go the edit
	 * page
	 * 
	 * @param field
	 *            the name of the link
	 * @return {@link AdminEditPage}
	 * @throws InterruptedException
	 */
	public AdminEditPage editAdminThisField(String field) {

		waitForElementVisible(By.xpath("//*[.='" + field + "']"), 30);

		clickElement(By.xpath("//*[.='" + field + "']"));

		try {
			waitForElementVisible(By.id("gwt-debug-AdminSelect-keyTextBox"), 10);
		} catch (TimeoutException e) {
			clickElementByKeyboard(By.xpath("//*[.='" + field + "']"));
		}
		return this;
	}

	/**
	 * 
	 * */
	public AdminEditPage editAccessToken(String token, String type, String object) {

		sendKeysToElement(By.id("gwt-debug-AdminAccessTokenEdit-token"), token);

		selectFromDropdown(By.id("gwt-debug-AdminAccessTokenEdit-typeList"), type);

		selectFromDropdown(By.id("gwt-debug-AdminAccessTokenEdit-objectListBox"), object);

		return this;
	}

	/**
	 * click Active checkbox for access token
	 * 
	 * @return {@link AdminEditPage}
	 */
	public AdminEditPage activateAccessToken() {
		clickElement(By.id("gwt-debug-AdminAccessTokenEdit-activeCheckBox-input"));
		return this;
	}

	/**
	 * On any admin edit page, input the key of the token and click the Load
	 * button
	 * 
	 * @param key
	 *            the key of the token
	 * 
	 * @return {@link AdminEditPage}
	 * @throws InterruptedException
	 */
	public AdminEditPage jumpByKeyAndLoad(String key) throws InterruptedException {

		waitForElementVisible(By.id("gwt-debug-AdminSelect-keyTextBox"), 10);
		wait(1);
		sendKeysToElement(By.id("gwt-debug-AdminSelect-keyTextBox"), key);

		clickElement(By.id("gwt-debug-AdminSelect-selectManagerLoadSpecific"));

		try {
			this.waitForWaitingScreenNotVisible();
			this.waitForElementVisible(By.xpath(".//*[contains(@id,'permissionTable')]"), Settings.WAIT_SECONDS);

		} catch (TimeoutException e) {
			clickElement(By.id("gwt-debug-AdminSelect-selectManagerLoadSpecific"));
			this.waitForWaitingScreenNotVisible();
		}

		return this;
	}

	/**
	 * On any admin edit page, select the token which contains parts of the
	 * string, it could be the key or the name
	 * 
	 * @param string
	 *            parts of the key or the name
	 * 
	 * @return {@link AdminEditPage}
	 */
	public AdminEditPage selectTokenByNameAndKey(String name, String key) {

		String string = name + " (Key: " + key + ")";
		scrollToTop();
		waitForElementVisible(By.xpath(".//*[@id='gwt-debug-AdminSelect-listBox']/option[@value='" + string + "']"),
				60);

		selectFromDropdown(By.id("gwt-debug-AdminSelect-listBox"), string);

		return this;
	}

	/**
	 * On any admin edit page, if it requires to input value to the text box,
	 * use this method
	 * 
	 * @param field
	 *            the name of the edit field
	 * @param value
	 *            the value to input
	 * 
	 * @return {@link AdminEditPage}
	 * @throws InterruptedException
	 * 
	 */
	public AdminEditPage inputValueToCertainField(String field, String value) throws InterruptedException {

		waitForElementVisible(
				By.xpath("//td[contains(text(), '" + field + "')]/following-sibling::td//input[@type='text']"), 10);

		wait(Settings.WAIT_SECONDS);

		sendKeysToElement(
				By.xpath("//td[contains(text(), '" + field + "')]/following-sibling::td//input[@type='text']"), value);

		return this;
	}

	/**
	 * On any admin edit page, if it requires to input value to the text box,
	 * use this method
	 * 
	 * @param field
	 *            the name of the edit field
	 * @param value
	 *            the value to input
	 * 
	 * @return {@link AdminEditPage}
	 * @throws InterruptedException
	 * 
	 */
	public AdminEditPage verifyInputValueToCertainField(String field, String value) throws InterruptedException {

		waitForElementVisible(
				By.xpath("//td[contains(text(), '" + field + "')]/following-sibling::td//input[@type='text']"), 30);
		// wait(2 * Settings.WAIT_SECONDS);
		assertTrue(getInputByLocator(
				By.xpath("//td[contains(text(), '" + field + "')]/following-sibling::td//input[@type='text']"))
						.equals(value));

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
	public AdminEditPage selectOptionForCertainField(String field, String option) {
		this.waitForWaitingScreenNotVisible();
		waitForElementVisible(By.xpath("//td[contains(text(), '" + field + "')]/following-sibling::td//select/option"),
				300);

		selectFromDropdown(By.xpath("//td[contains(text(), '" + field + "')]/following-sibling::td//select"), option);
		return this;
	}

	/**
	 * On any admin edit page, if it requires to select multi options from the
	 * drop-down list for the field, use this method
	 * 
	 * @param field
	 *            the name of the edit field
	 * @param options
	 *            the options to select
	 * 
	 * @return {@link AdminEditPage}
	 * 
	 */
	public AdminEditPage selectMultiOptionForCertainField(String field, String... options) {

		clickElement(By.xpath("//td[.='" + field + "']/following-sibling::td//img"));

		for (String option : options) {
			WebElement we = webDriver.findElement(By.xpath("//label[.='" + option + "']/preceding-sibling::input"));

			setCheckboxStatus2(we, true);

		}

		return this;
	}

	/**
	 * On any admin edit page, if it requires to check or uncheck the checkbox,
	 * use this method; the checkbox is in front of the label
	 * 
	 * @param field
	 *            the name of the edit field
	 * @param checked
	 *            if true, check the checkbox; if false, uncheck
	 * 
	 * @return {@link AdminEditPage}
	 * @throws InterruptedException
	 * 
	 */
	public AdminEditPage checkTheCheckboxBeforeCertainField(String field, Boolean checked) throws InterruptedException {

		// wait for elem to be attached to page, do not remove.
		wait(Settings.WAIT_SECONDS);

		waitForElementVisible(By.xpath("//label[.='" + field + "']//preceding-sibling::input"), 30);

		WebElement we = webDriver.findElement(By.xpath("//label[.='" + field + "']//preceding-sibling::input"));

		new Actions(webDriver).moveToElement(we).build().perform();

		this.setCheckboxStatus2(we, checked);
		clickYesButtonIfVisible();
		for (int i = 0; i < Settings.ATTEMPT_LOOPING_NUMBER; i++) {
			new Actions(webDriver).moveToElement(we).build().perform();
			if (!checked.equals(we.isSelected())) {
				setCheckboxStatus2(we, checked);
			}
		}

		wait(Settings.WAIT_SECONDS);

		clickYesButtonIfVisible();

		return this;
	}

	/**
	 * On any admin edit page, if it requires to check or uncheck the checkbox,
	 * use this method; the checkbox is after the label
	 * 
	 * @param field
	 *            the name of the edit field
	 * @param checked
	 *            if true, check the checkbox; if false, uncheck
	 * 
	 * @return {@link AdminEditPage}
	 * 
	 */
	public AdminEditPage checkTheCheckboxAfterCertainField(String field, Boolean checked) {

		waitForElementVisible(By.xpath("//td[.='" + field + "']/following-sibling::td//input[@type='checkbox']"), 30);

		WebElement we = webDriver
				.findElement(By.xpath("//td[.='" + field + "']/following-sibling::td//input[@type='checkbox']"));

		setCheckboxStatus2(we, checked);

		return this;
	}

	/**
	 * @throws InterruptedException
	 * 
	 */
	public AdminEditPage editSingleModuleToggle(String module, Boolean user) throws InterruptedException {
		By by = By.xpath("//td[.='" + module + "']/following-sibling::td[1]//input");
		waitForElementVisible(by, 30);

		WebElement we_investor = webDriver.findElement(by);

		setCheckboxStatus2(we_investor, user);

		boolean flag1 = we_investor.isSelected();

		// wait(5);
		if (flag1 != user) {
			clickElementByKeyboard(by);
		}

		return this;

	}

	/**
	 * @throws InterruptedException
	 * 
	 */
	public AdminEditPage editModuleToggle(String module, Boolean investor, Boolean advisor)
			throws InterruptedException {
		boolean flag1 = true;
		boolean flag2 = true;
		By investorBy = By.xpath("//td[.='" + module + "']/following-sibling::td[1]//input");
		By advisorBy = By.xpath("//td[.='" + module + "']/following-sibling::td[2]//input");
		waitForElementVisible(investorBy, 30);
		waitForElementVisible(advisorBy, 30);

		WebElement we_investor = webDriver.findElement(investorBy);

		new Actions(webDriver).moveToElement(we_investor).build().perform();
		setCheckboxStatus2(we_investor, investor);
		wait(1);
		try {
			new Actions(webDriver).moveToElement(we_investor).build().perform();
			flag1 = we_investor.isSelected();
		} catch (StaleElementReferenceException e) {

			flag1 = we_investor.isSelected();
		}

		WebElement we2_advisor = webDriver.findElement(advisorBy);

		new Actions(webDriver).moveToElement(we2_advisor).build().perform();
		setCheckboxStatus2(we2_advisor, advisor);
		wait(1);

		try {
			flag2 = we2_advisor.isSelected();
		} catch (StaleElementReferenceException e) {
			new Actions(webDriver).moveToElement(we2_advisor).build().perform();
			flag2 = we2_advisor.isSelected();
		}

		wait(5);
		if (flag1 != investor) {
			clickElement(investorBy);
		}
		if (flag2 != advisor) {
			clickElement(advisorBy);
		}

		return this;

	}

	public AdminEditPage assertionModuleToggle(String module, Boolean investor, Boolean advisor) {
		waitForElementVisible(By.xpath("//td[.='" + module + "']/following-sibling::td[1]//input"), 30);
		waitForElementVisible(By.xpath("//td[.='" + module + "']/following-sibling::td[2]//input"), 30);

		WebElement we_investor = webDriver
				.findElement(By.xpath("//td[.='" + module + "']/following-sibling::td[1]//input"));
		WebElement we2_advisor = webDriver
				.findElement(By.xpath("//td[.='" + module + "']/following-sibling::td[2]//input"));

		boolean flag1 = we_investor.isSelected();
		boolean flag2 = we2_advisor.isSelected();

		assertEquals(flag1, investor);
		assertEquals(flag2, advisor);

		return this;
	}

	public AdminEditPage assertionSingleModuleToggle(String module, Boolean user) throws InterruptedException {

		waitForElementVisible(By.xpath("//td[.='" + module + "']/following-sibling::td[1]//input"), 30);

		WebElement we_investor = webDriver
				.findElement(By.xpath("//td[.='" + module + "']/following-sibling::td[1]//input"));

		boolean flag1 = we_investor.isSelected();

		assertEquals(flag1, user);

		return this;
	}

	/**
	 * On any admin edit page, click the New button to create a new token, and
	 * the edit page will be loaded
	 * 
	 * 
	 * @return {@link AdminEditPage}
	 * @throws InterruptedException
	 */
	public AdminEditPage clickNewButtonToCreateNewToken() throws InterruptedException {

		this.waitForElementVisible(By.id("gwt-debug-AdminSelect-selectManagerNew"), Settings.WAIT_SECONDS);
		scrollToTop();

		clickElement(By.id("gwt-debug-AdminSelect-selectManagerNew"));

		/*
		 * waitForElementVisible(By.id("gwt-debug-AdminSelect-selectManagerNew")
		 * , 30);
		 * 
		 * clickElement(By.id("gwt-debug-AdminSelect-selectManagerNew"));
		 */

		return this;
	}

	/**
	 * On any admin edit page, click the Submit button to submit the information
	 * 
	 * @return {@link AdminEditPage}
	 * @throws InterruptedException
	 */
	public AdminEditPage clickSubmitButton() throws InterruptedException {

		scrollToBottom();

		waitForElementVisible(By.xpath("//button[.='Submit']"), 900);

		clickElement(By.xpath("//button[.='Submit']"));

		// wait until the changes are submitted successfully
		this.waitForWaitingScreenNotVisible();
		this.waitForElementNotVisible(By.xpath(".//*[contains(@id,'permissionTable')]"), Settings.WAIT_SECONDS);
		scrollToTop();
		return this;
	}

	/**
	 * On the Edit User page, click the Login As This User button beside the key
	 * of the user
	 * 
	 * @return {@link MenuBarPage}
	 * @throws InterruptedException
	 */
	public MenuBarPage clickLoginAsThisUserButton() throws InterruptedException {
		this.waitForElementVisible(By.id("gwt-debug-AdminUserEdit-loginAsBtn"), Settings.WAIT_SECONDS);
		clickElement(By.id("gwt-debug-AdminUserEdit-loginAsBtn"));

		return new MenuBarPage(webDriver);
	}

	/**
	 * 
	 * */
	public AdminEditPage clickYesButton() {
		clickYesButtonIfVisible();

		return this;
	}

	/**
	 * 
	 * */
	public AdminEditPage clickNoButton() {

		clickNoButtonIfVisible();

		return this;
	}

	/**
	 * @throws InterruptedException
	 * 
	 */
	public AdminEditPage clickOkButton() throws InterruptedException {
		clickOkButtonIfVisible();

		return this;
	}

	/**
	 * click any button with a provided name
	 * 
	 * @param name
	 *            text on the button
	 * @return
	 */
	public AdminEditPage clickCustomButton(String name) {
		clickElement(By.xpath(".//button[contains(text(),'" + name + "')]"));
		return this;
	}

	public AdminEditPage textForCertainField(String value) {

		/*
		 * waitForElementVisible(
		 * By.xpath(".//*[@id='gwt-debug-AdminDocumentEdit-docName']"), 90);
		 */
		sendKeysToElement(By.id("gwt-debug-AdminDocumentEdit-docName"), value);
		return this;
	}

	public AdminEditPage inputValueToCurrencyField(String value) {

		waitForElementVisible(By.xpath(".//*[@id='gwt-debug-AdminStrategyEdit-strategyCurrency']"), 30);
		WebElement we = webDriver.findElement(By.id("gwt-debug-AdminStrategyEdit-strategyCurrencyUpdate-input"));
		setCheckboxStatus2(we, true);

		clickYesButtonIfVisible();

		selectFromDropdown(By.xpath(".//*[@id='gwt-debug-AdminStrategyEdit-strategyCurrency']"), value);
		return this;
	}

	public AdminEditPage selectOptionForAssetTypeFieldValue(String option) {

		waitForElementVisible(By.id("gwt-debug-AdminTickerEdit-tickerAssetType"), 90);
		selectFromDropdown(By.id("gwt-debug-AdminTickerEdit-tickerAssetType"), option);
		return this;
	}

	public AdminEditPage selectOptionForExchangeFieldValue(String option) {

		waitForElementVisible(By.id("gwt-debug-AdminTickerEdit-tickerExchange"), 90);
		// try {
		// wait(3);
		// } catch (InterruptedException e) {
		// }
		new Actions(webDriver).moveToElement(webDriver.findElement(By.id("gwt-debug-AdminTickerEdit-tickerExchange")))
				.perform();

		selectFromDropdown(By.id("gwt-debug-AdminTickerEdit-tickerExchange"), option);
		return this;
	}

	public AdminEditPage selectOptionForDataSourceFieldValue(String option, boolean isChecked)
			throws InterruptedException {

		// JavascriptExecutor javascript = (JavascriptExecutor) webDriver;
		// wait(5);

		waitForElementVisible(By.id("gwt-debug-MultiSelectDropDownBox-textBox"), 90);

		waitForElementVisible(By.id("gwt-debug-MultiSelectDropDownBox-dropDown"), 10);
		//
		// javascript
		// .executeScript("document.getElementById('gwt-debug-AdminTickerEdit-tickerExchange').scrollIntoView(true)");
		// wait(3);

		clickElementByKeyboard(By.xpath(".//button[@id='gwt-debug-MultiSelectDropDownBox-dropDown']"));

		waitForElementVisible(By.xpath("//input[following-sibling::label[.='" + option + "']]"), 30);

		WebElement we = webDriver.findElement(By.xpath("//input[following-sibling::label[.='" + option + "']]"));

		setCheckboxStatus2(we, isChecked);

		return this;
	}

	public AdminEditPage selectOptionValues(String element, String option) {

		waitForElementVisible(By.xpath(element), 90);
		selectFromDropdown(By.xpath(element), option);
		return this;
	}

	public AdminEditPage inputValueToCurrencyFieldTicker(String value) throws InterruptedException {

		waitForElementVisible(By.xpath(".//*[@id='gwt-debug-AdminTickerEdit-tickerCurrency']"), 30);
		// wait(5);
		selectFromDropdown(By.xpath(".//*[@id='gwt-debug-AdminTickerEdit-tickerCurrency']"), value);
		return this;
	}

	public AdminEditPage editCouponRate(String couponRate) {
		sendKeysToElement(By.id("gwt-debug-AdminTickerEdit-couponRate"), couponRate);
		return this;
	}

	public AdminEditPage addBenchmarkInUniverse(String benchmark) throws InterruptedException {

		selectFromDropdown(
				By.xpath(".//div[@id='gwt-debug-AdminInvestmentUniverseEdit-iuBenchmark']/table/tbody/tr/td[1]/select"),
				benchmark);

		clickElement(By.xpath(
				".//div[@id='gwt-debug-AdminInvestmentUniverseEdit-iuBenchmark']/table/tbody/tr/td[2]/div[1]/img"));

		// clickElementAndWait3(By.xpath(".//div[@id='gwt-debug-AdminInvestmentUniverseEdit-iuBenchmark']/table/tbody/tr/td[2]/div[1]/img"));

		return this;
	}

	public AdminEditPage deleteBenchmarkInUniverse() throws InterruptedException {
		this.waitForElementVisible(
				By.xpath(".//div[@id='gwt-debug-AdminInvestmentUniverseEdit-iuBenchmark']/table/tbody/tr/td[3]/select"),
				Settings.WAIT_SECONDS);

		WebElement dropDownToDelete = webDriver.findElement(By
				.xpath(".//div[@id='gwt-debug-AdminInvestmentUniverseEdit-iuBenchmark']/table/tbody/tr/td[3]/select"));

		Select selectToDelete = new Select(dropDownToDelete);
		// wait(1);
		List<WebElement> list = selectToDelete.getOptions();
		// log("list size " + String.valueOf(list.size()));

		if (!list.isEmpty() | list.size() > 0) {
			for (WebElement options : list) {
				String desc = options.getText();
				selectToDelete.selectByVisibleText(desc);
				clickElementAndWait3(By.xpath(
						".//div[@id='gwt-debug-AdminInvestmentUniverseEdit-iuBenchmark']/table/tbody/tr/td[2]/div[2]/img"));
			}
		}

		return this;

	}

}
