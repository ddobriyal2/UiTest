package org.sly.uitest.pageobjects.salesprocess;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;

public class SalesWizardPage extends AbstractPage {
	public SalesWizardPage(WebDriver webDriver) throws InterruptedException {

		super();
		this.webDriver = webDriver;
		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='myDialogBox']")));

	}

	/**
	 * edit status in sales wizard page: 1 for single, 2 for married, 3 for
	 * divorced, 4 for widowed
	 * 
	 * @param status
	 * @return {@link SalesWizardPage}
	 */
	public SalesWizardPage editMaritalStatusForClient(int status) {
		switch (status) {
		case 1:
			WebElement elem = webDriver.findElement(
					By.id("gwt-debug-MiniSalesProcessEditTabOverviewView-maritalStatusSingleCheckBox-input"));

			if (!elem.isSelected()) {
				elem.click();
			}
			break;
		case 2:
			WebElement elem2 = webDriver.findElement(
					By.id("gwt-debug-MiniSalesProcessEditTabOverviewView-maritalStatusMarriedCheckBox-input"));

			if (!elem2.isSelected()) {
				elem2.click();
			}
			break;
		case 3:
			WebElement elem3 = webDriver.findElement(
					By.id("gwt-debug-MiniSalesProcessEditTabOverviewView-maritalStatusDivorcedCheckBox-input"));

			if (!elem3.isSelected()) {
				elem3.click();
			}
			break;
		case 4:
			WebElement elem4 = webDriver.findElement(
					By.id("gwt-debug-MiniSalesProcessEditTabOverviewView-maritalStatusWidowedCheckBox-input"));

			if (!elem4.isSelected()) {
				elem4.click();
			}
			break;
		}
		return this;
	}

	/**
	 * edit property type in client sales wizard page: 1 for By Law, 2 for Comu
	 * of Goods, 3 for Sperate Estate
	 * 
	 * @param type
	 * @return {@link SalesWizardPage}
	 */
	public SalesWizardPage editPropertyTypeForClient(int type) {
		switch (type) {
		case 1:
			WebElement elem1 = webDriver.findElement(By
					.id("gwt-debug-MiniSalesProcessEditTabOverviewView-martimonialPropertyRegimeByLawCheckBox-input"));

			if (!elem1.isSelected()) {
				elem1.click();
			}
			break;
		case 2:
			WebElement elem2 = webDriver.findElement(By.id(
					"gwt-debug-MiniSalesProcessEditTabOverviewView-martimonialPropertyRegimeComuOfGoodsCheckBox-input"));

			if (!elem2.isSelected()) {
				elem2.click();
			}
			break;
		case 3:
			WebElement elem3 = webDriver.findElement(By.id(
					"gwt-debug-MiniSalesProcessEditTabOverviewView-martimonialPropertyRegimeSeparateEstateCheckBox-input"));

			if (!elem3.isSelected()) {
				elem3.click();
			}
			break;
		}
		return this;
	}

	/**
	 * edit Title in sales wizard page
	 * 
	 * @param title
	 * @return {@link SalesWizardPage}
	 */
	public SalesWizardPage editTitle(String title) {
		selectFromDropdown(By.id("gwt-debug-MiniSalesProcessEditTabOverviewView-titleListBox"), title);
		return this;
	}

	/**
	 * Edit the name of the client
	 * 
	 * @param firstname
	 * @param lastname
	 * @return {@link SalesWizardPage}
	 */
	public SalesWizardPage editName(String firstname, String lastname) {

		sendKeysToElement(By.id("gwt-debug-MiniSalesProcessEditTabOverviewView-firstNameTextBox"), firstname);
		sendKeysToElement(By.id("gwt-debug-MiniSalesProcessEditTabOverviewView-lastNameTextBox"), lastname);

		return this;
	}

	/**
	 * Edit email of sales wizard page
	 * 
	 * @param email
	 * @return {@link SalesWizardPage}
	 */
	public SalesWizardPage editEmail(String email) {

		sendKeysToElement(By.id("gwt-debug-EditUserEmailWidget-emailTextBox"), email);

		return this;

	}

	/**
	 * edit address in sales wizard page
	 * 
	 * @param title
	 * @return {@link SalesWizardPage}
	 */
	public SalesWizardPage editAddress(String address) {
		sendKeysToElement(By.id("gwt-debug-EditUserAddressWidget-address1TextBox"), address);
		return this;
	}

	/**
	 * edit telephone in sales wizard page
	 * 
	 * @param telephone
	 * @return {@link SalesWizardPage}
	 */
	public SalesWizardPage editTelephone(String telephone) {
		sendKeysToElement(By.id("gwt-debug-EditUserPhoneWidget-numberTextBox"), telephone);
		return this;
	}

	/**
	 * edit date in sales wizard page
	 * 
	 * @param field
	 * @param day
	 * @param month
	 * @param year
	 * @return {@link SalesWizardPage}
	 * @throws InterruptedException
	 */
	public SalesWizardPage editDate(String field, String day, String month, String year) throws InterruptedException {
		selectFromDropdown(
				By.xpath("//*[contains(text(), '" + field
						+ "')]/parent::td/following-sibling::td//*[@id='gwt-debug-CompositeDatePicker-dayListBox']"),
				day);
		wait(2);
		selectFromDropdown(
				By.xpath("//*[contains(text(), '" + field
						+ "')]/parent::td/following-sibling::td//*[@id='gwt-debug-CompositeDatePicker-monthListBox']"),
				month);
		wait(2);
		sendKeysToElement(
				By.xpath("//*[contains(text(), '" + field
						+ "')]/parent::td/following-sibling::td//*[@id='gwt-debug-CompositeDatePicker-yearTextBox']"),
				year);

		return this;
	}

	/**
	 * edit id type in sales wizard page
	 * 
	 * @param type
	 * @return {@link SalesWizardPage}
	 */
	public SalesWizardPage editIdType(String type) {
		selectFromDropdown(By.id("gwt-debug-MiniSalesProcessEditTabPersonalIdentificationView-idTypeListBox"), type);
		return this;
	}

	/**
	 * edit nationality in sales wizard page
	 * 
	 * @param nationality
	 * @return {@link SalesWizardPage}
	 */
	public SalesWizardPage editNationality(String nationality) {
		selectFromDropdown(By.id("gwt-debug-MiniSalesProcessEditTabPersonalIdentificationView-nationalityListBox"),
				nationality);
		return this;
	}

	/**
	 * edit Tax id in sales wizard page
	 * 
	 * @param entity
	 * @return {@link SalesWizardPage}
	 */
	public SalesWizardPage editTaxId(String entity) {
		sendKeysToElement(By.id("gwt-debug-MiniSalesProcessEditTabOverviewView-vatTaxIdTextBox"), entity);
		return this;
	}

	/**
	 * edit tax authority in sales wizard page
	 * 
	 * @param authority
	 * @return {@link SalesWizardPage}
	 */
	public SalesWizardPage editTaxAuthority(String authority) {
		sendKeysToElement(By.id("gwt-debug-MiniSalesProcessEditTabOverviewView-taxAuthorityTextBox"), authority);
		return this;
	}

	/**
	 * edit id in sales wizard page
	 * 
	 * @param id
	 * @return {@link SalesWizardPage}
	 */
	public SalesWizardPage editID(String id) {
		sendKeysToElement(By.id("gwt-debug-MiniSalesProcessEditTabOverviewView-numberTextBox"), id);
		return this;
	}

	/**
	 * edit issuing entity in sales wizard page
	 * 
	 * @param entity
	 * @return {@link SalesWizardPage}
	 */
	public SalesWizardPage editIssuingEntity(String entity) {
		sendKeysToElement(By.id("gwt-debug-MiniSalesProcessEditTabOverviewView-issuingAuthorityTextBox"), entity);
		return this;
	}

	/**
	 * edit IBAN in sales wizard page
	 * 
	 * @param iban
	 * @return {@link SalesWizardPage}
	 */
	public SalesWizardPage editIBAN(String iban) {

		sendKeysToElement(By.id("gwt-debug-PaymentMethodInnerWidget-ibanTextBox"), iban);

		return this;
	}

	/**
	 * edit the member type in sales wizard 1. Blue 2. Green
	 * 
	 * @param type
	 * @return {@link SalesWizardPage}
	 */
	public SalesWizardPage editMembershipType(String type) {

		try {
			waitForElementVisible(By.id("gwt-debug-OrbeonView-content"), 2);
		} catch (TimeoutException e) {

		}

		if (isElementVisible(By.id("gwt-debug-OrbeonView-content"))) {
			webDriver.switchTo().frame(webDriver.findElement(By.id("gwt-debug-OrbeonView-content")));

		}
		waitForElementVisible(By.id("o0section-1-control≡xf-385≡membershipTyp-control≡select1≡≡c"), 10);

		selectFromDropdownByValue(By.id("o0section-1-control≡xf-385≡membershipTyp-control≡select1≡≡c"), type);

		return this;
	}

	/**
	 * edit number of sales in sales wizard
	 * 
	 * @param num
	 * @return {@link SalesWizardPage}
	 */
	public SalesWizardPage editShares(String num) {

		if (isElementVisible(By.id("gwt-debug-OrbeonView-content"))) {
			webDriver.switchTo().frame(webDriver.findElement(By.id("gwt-debug-OrbeonView-content")));

		}

		sendKeysToElement(By.id("o0section-1-control≡xf-385≡amountShares-control≡xforms-input-1"), num);

		return this;
	}

	/**
	 * choose sales payback option in sales wizard
	 * 
	 * @param choose
	 *            boolean - whether to tick checkbox for payback option
	 * @param option
	 *            1 for the first one, 2 for the second and 3 for the third
	 * @return {@link SalesWizardPage}
	 */
	public SalesWizardPage editPaybackOption(boolean choose, String option) {

		if (isElementVisible(By.id("gwt-debug-OrbeonView-content"))) {
			webDriver.switchTo().frame(webDriver.findElement(By.id("gwt-debug-OrbeonView-content")));

		}
		WebElement elem = webDriver.findElement(By.id("o0section-1-control≡xf-385≡payBackOption-control≡≡e0"));
		boolean selected = elem.isSelected();
		if (choose) {
			if (!selected) {
				elem.click();
			}

			switch (option) {
			case "1":
				clickElement(By.id("o0o0section-1-control≡xf-386≡control-19-control≡≡e0"));
				break;
			case "2":
				clickElement(By.id("o0o0section-1-control≡xf-386≡control-19-control≡≡e1"));
				break;
			case "3":
				clickElement(By.id("o0o0section-1-control≡xf-386≡control-19-control≡≡e2"));
				break;
			default:
				break;
			}

		} else {
			if (selected) {
				elem.click();
			}
		}

		return this;
	}

	/**
	 * edit the sales date in sales wizard
	 * 
	 * @param date
	 * @return {@link SalesWizardPage}
	 */
	public SalesWizardPage editSalesDate(String day) {

		if (isElementVisible(By.id("gwt-debug-OrbeonView-content"))) {
			webDriver.switchTo().frame(webDriver.findElement(By.id("gwt-debug-OrbeonView-content")));

		}
		clickElement(By.id("o0section-1-control≡xf-386≡control-18-control≡xforms-input-1"));

		clickElement(By.xpath(".//a[.='" + day + "']"));
		return this;
	}

	/**
	 * Manaully input the date
	 * 
	 * @param date
	 *            date to input
	 * @return
	 */
	public SalesWizardPage editSalesDateManually(String date) {

		if (isElementVisible(By.id("gwt-debug-OrbeonView-content"))) {
			webDriver.switchTo().frame(webDriver.findElement(By.id("gwt-debug-OrbeonView-content")));
		}

		sendKeysToElement(By.id("o0section-1-control≡xf-386≡control-18-control≡xforms-input-1"), date);

		return this;
	}

	/**
	 * edit the sales location in sales wizard
	 * 
	 * @param location
	 * @return
	 * @throws InterruptedException
	 */
	public SalesWizardPage editSalesLocation(String location) throws InterruptedException {

		if (isElementVisible(By.id("gwt-debug-OrbeonView-content"))) {
			webDriver.switchTo().frame(webDriver.findElement(By.id("gwt-debug-OrbeonView-content")));

		}

		WebElement elem = webDriver
				.findElement(By.xpath(".//input[@id='o0section-1-control≡xf-386≡control-17-control≡xforms-input-1']"));
		elem.sendKeys(location);
		return this;
	}

	/**
	 * edit sales legal requirement checkbox in sales wizard from 0 - 4,
	 * representing option 1 - 5
	 * 
	 * @param option
	 * @return
	 */
	public SalesWizardPage editLegalRequirementCheckbox(Integer option) {

		if (isElementVisible(By.id("gwt-debug-OrbeonView-content"))) {
			webDriver.switchTo().frame(webDriver.findElement(By.id("gwt-debug-OrbeonView-content")));

		}

		String locator = "o0section-1-control≡xf-386≡control-16-control≡≡e" + String.valueOf(option);

		By by = By.id(locator);

		WebElement elem = webDriver.findElement(by);
		setCheckboxStatus2(elem, true);
		return this;
	}

	/**
	 * edit sales tarif in sales wizard
	 * 
	 * @param option
	 * @return
	 */
	public SalesWizardPage editTarif(String option) {

		if (isElementVisible(By.id("gwt-debug-OrbeonView-content"))) {
			webDriver.switchTo().frame(webDriver.findElement(By.id("gwt-debug-OrbeonView-content")));

		}

		switch (option) {
		case "A":
			clickElement(By.id("o0section-1-control≡xf-386≡control-15-control≡≡e0"));

			WebElement elem = webDriver.findElement(By.id("o0section-1-control≡xf-386≡control-15-control≡≡e1"));
			assertTrue(!elem.isSelected());
			break;
		case "B":
			clickElement(By.id("o0section-1-control≡xf-386≡control-15-control≡≡e1"));

			WebElement elem2 = webDriver.findElement(By.id("o0section-1-control≡xf-386≡control-15-control≡≡e0"));
			assertTrue(!elem2.isSelected());
			break;
		}
		return this;
	}

	public SalesWizardPage clickNextPage() {

		webDriver.switchTo().defaultContent();

		clickElement(By.id("gwt-debug-MiniSalesProcessWizardControllerView-nextBtn"));

		return this;
	}

	public FSMenubarPage clickCompletePage() {

		clickElement(By.id("gwt-debug-MiniSalesProcessWizardControllerView-nextBtn"));

		waitForElementVisible(By.xpath(".//*[@class='logoutButton']"), 20);
		return new FSMenubarPage(webDriver);

	}

	public SalesWizardPage clickPreviousPage() {

		webDriver.switchTo().defaultContent();

		clickElement(By.id("gwt-debug-MiniSalesProcessWizardControllerView-backBtn"));

		return this;

	}
}
