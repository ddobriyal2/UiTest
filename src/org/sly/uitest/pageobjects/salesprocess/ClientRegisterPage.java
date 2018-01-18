package org.sly.uitest.pageobjects.salesprocess;

import java.awt.AWTException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.pageobjects.clientsandaccounts.ClientOverviewPage;
import org.sly.uitest.settings.Settings;

public class ClientRegisterPage extends AbstractPage {

	public ClientRegisterPage(WebDriver webDriver) throws InterruptedException {

		super();
		this.webDriver = webDriver;
		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='myDialogBox']")));

	}

	/**
	 * edit status in client page: 1 for single, 2 for married, 3 for divorced,
	 * 4 for widowed
	 * 
	 * @param status
	 * @return {@link ClientRegisterPage}
	 */
	public ClientRegisterPage editMaritalStatusForClient(int status) {
		switch (status) {
		case 1:
			WebElement elem = webDriver
					.findElement(By.id("gwt-debug-ClientCreateTabOverviewView-maritalStatusSingleCheckBox-input"));

			if (!elem.isSelected()) {
				elem.click();
			}
			break;
		case 2:
			WebElement elem2 = webDriver
					.findElement(By.id("gwt-debug-ClientCreateTabOverviewView-maritalStatusMarriedCheckBox-input"));

			if (!elem2.isSelected()) {
				elem2.click();
			}
			break;
		case 3:
			WebElement elem3 = webDriver
					.findElement(By.id("gwt-debug-ClientCreateTabOverviewView-maritalStatusDivorcedCheckBox-input"));

			if (!elem3.isSelected()) {
				elem3.click();
			}
			break;
		case 4:
			WebElement elem4 = webDriver
					.findElement(By.id("gwt-debug-ClientCreateTabOverviewView-maritalStatusWidowedCheckBox-input"));

			if (!elem4.isSelected()) {
				elem4.click();
			}
			break;
		}
		return this;
	}

	/**
	 * edit property type in client client page: 1 for By Law, 2 for Comu of
	 * Goods, 3 for Sperate Estate
	 * 
	 * @param type
	 * @return {@link ClientRegisterPage}
	 */
	public ClientRegisterPage editPropertyTypeForClient(int type) {
		switch (type) {
		case 1:
			WebElement elem1 = webDriver.findElement(
					By.id("gwt-debug-ClientCreateTabOverviewView-martimonialPropertyRegimeByLawCheckBox-input"));

			if (!elem1.isSelected()) {
				elem1.click();
			}
			break;
		case 2:
			WebElement elem2 = webDriver.findElement(
					By.id("gwt-debug-ClientCreateTabOverviewView-martimonialPropertyRegimeComuOfGoodsCheckBox-input"));

			if (!elem2.isSelected()) {
				elem2.click();
			}
			break;
		case 3:
			WebElement elem3 = webDriver.findElement(By
					.id("gwt-debug-ClientCreateTabOverviewView-martimonialPropertyRegimeSeparateEstateCheckBox-input"));

			if (!elem3.isSelected()) {
				elem3.click();
			}
			break;
		}
		return this;
	}

	/**
	 * edit Title in client page
	 * 
	 * @param title
	 * @return {@link ClientRegisterPage}
	 */
	public ClientRegisterPage editTitle(String title) {
		selectFromDropdown(By.id("gwt-debug-ClientCreateTabOverviewView-titleListBox"), title);
		return this;
	}

	/**
	 * edit Title in client page
	 * 
	 * @param value
	 * @return {@link ClientRegisterPage}
	 */
	public ClientRegisterPage editTitleByValue(String value) {
		selectFromDropdownByValue(By.id("gwt-debug-ClientCreateTabOverviewView-titleListBox"), value);
		return this;
	}

	/**
	 * edit second Title in client page
	 * 
	 * @param title
	 * @return {@link ClientRegisterPage}
	 */
	public ClientRegisterPage editSecondTitle(String title) {
		selectFromDropdown(By.id("gwt-debug-AdvisorEditTabCompanyInformationView-secondOwnerTitleListBox"), title);
		return this;
	}

	/**
	 * Edit the name of the client
	 * 
	 * @param firstname
	 * @param lastname
	 * @return {@link ClientRegisterPage}
	 */
	public ClientRegisterPage editName(String firstname, String lastname) {

		sendKeysToElement(By.id("gwt-debug-ClientCreateTabOverviewView-nameTextBox"), firstname);
		sendKeysToElement(By.id("gwt-debug-ClientCreateTabOverviewView-surnameTextBox"), lastname);

		return this;
	}

	/**
	 * Edit email of client
	 * 
	 * @param email
	 * @return {@link ClientRegisterPage}
	 */
	public ClientRegisterPage editEmail(String email) {

		sendKeysToElement(By.id("gwt-debug-EditUserEmailWidget-emailTextBox"), email);

		return this;

	}

	/**
	 * edit address in client page
	 * 
	 * @param title
	 * @return {@link ClientRegisterPage}
	 */
	public ClientRegisterPage editAddress(String address) {
		sendKeysToElement(By.id("gwt-debug-EditUserAddressWidget-address1TextBox"), address);
		return this;
	}

	/**
	 * edit telephone in client page
	 * 
	 * @param telephone
	 * @return {@link ClientRegisterPage}
	 */
	public ClientRegisterPage editTelephone(String telephone) {
		sendKeysToElement(By.id("gwt-debug-EditUserPhoneWidget-numberTextBox"), telephone);
		return this;
	}

	/**
	 * edit date in client page
	 * 
	 * @param field
	 * @param day
	 * @param month
	 * @param year
	 * @return {@link ClientRegisterPage}
	 * @throws InterruptedException
	 */
	public ClientRegisterPage editDate(String field, String day, String month, String year)
			throws InterruptedException {
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
	 * select tax nationality in client page
	 * 
	 * @param nationality
	 * @return
	 */
	public ClientRegisterPage editTaxResidence(String nationality) {

		selectFromDropdown(By.id("gwt-debug-ClientCreateTabOverviewView-taxResidenceListBox"), nationality);

		return this;
	}

	/**
	 * edit id type in client page
	 * 
	 * @param type
	 * @return {@link ClientRegisterPage}
	 */
	public ClientRegisterPage editIdType(String type) {
		selectFromDropdown(By.xpath(".//select[contains(@id,'idTypeListBox')]"), type);
		return this;
	}

	/**
	 * edit nationality in client page
	 * 
	 * @param nationality
	 * @return {@link ClientRegisterPage}
	 */
	public ClientRegisterPage editNationality(String nationality) {
		selectFromDropdown(By.xpath(".//select[contains(@id,'nationalityListBox')]"), nationality);
		return this;
	}

	/**
	 * edit Tax id in client page
	 * 
	 * @param entity
	 * @return {@link ClientRegisterPage}
	 */
	public ClientRegisterPage editTaxId(String entity) {
		sendKeysToElement(By.id("gwt-debug-ClientCreateTabOverviewView-vatTaxIdTextBox"), entity);
		return this;
	}

	/**
	 * edit tax authority in client page
	 * 
	 * @param authority
	 * @return {@link ClientRegisterPage}
	 */
	public ClientRegisterPage editTaxAuthority(String authority) {
		sendKeysToElement(By.id("gwt-debug-ClientCreateTabOverviewView-taxAuthorityTextBox"), authority);
		return this;
	}

	/**
	 * edit id in client page
	 * 
	 * @param id
	 * @return {@link ClientRegistePage}
	 */
	public ClientRegisterPage editID(String id) {
		sendKeysToElement(By.id("gwt-debug-ClientCreateTabOverviewView-numberTextBox"), id);
		return this;
	}

	/**
	 * edit issuing entity in client page
	 * 
	 * @param entity
	 * @return {@link ClientRegisterPage}
	 */
	public ClientRegisterPage editIssuingEntity(String entity) {
		sendKeysToElement(By.id("gwt-debug-ClientCreateTabPersonalIdentView-issuingAuthorityTextBox"), entity);
		return this;
	}

	/**
	 * edit IBAN in client page
	 * 
	 * @param iban
	 * @return {@link ClientRegisterPage}
	 */
	public ClientRegisterPage editIBAN(String iban) {

		sendKeysToElement(By.id("gwt-debug-PaymentMethodInnerWidget-ibanTextBox"), iban);

		return this;
	}

	/**
	 * click next page button in client page
	 * 
	 * @return {@link AdvisorRegisterPage}
	 */
	public ClientRegisterPage clickNextPage() {
		clickElement(By.id("gwt-debug-ClientCreateWizardControllerView-nextBtn"));
		return this;
	}

	/**
	 * click last page button in client page
	 * 
	 * @return {@link AdvisorRegisterPage}
	 */
	public ClientRegisterPage clickPreviousPage() {
		clickElement(By.id("gwt-debug-ClientCreateWizardControllerView-backBtn"));
		return this;
	}

	/**
	 * click close dialog button
	 */
	public void clickCloseDialogBtn() {

		clickElementByKeyboard(By.xpath(".//button[@class='dialogBoxCloseBtn']"));

	}

	/**
	 * click the complete nutton in register page
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public ClientOverviewPage clickCompletePage() throws InterruptedException {

		waitForElementVisible(By.id("gwt-debug-ClientCreateWizardControllerView-nextBtn"), 10);
		clickElement(By.id("gwt-debug-ClientCreateWizardControllerView-nextBtn"));
		clickOkButtonIfVisible();
		return new ClientOverviewPage(webDriver);
	}

	/**
	 * edit id number in client page
	 * 
	 * @param idNumber
	 * @return
	 */
	public ClientRegisterPage editIdNumber(String idNumber) {

		sendKeysToElement(By.id("gwt-debug-ClientCreateTabPersonalIdentView-numberTextBox"), idNumber);
		return this;
	}

	/**
	 * upload file in register page
	 * 
	 * @param field
	 * @param filePath
	 * @return {@link AdvisorRegisterPage}
	 * @throws AWTException
	 * @throws InterruptedException
	 */
	public ClientRegisterPage uploadFile(String field) throws AWTException, InterruptedException {
		wait(5);
		if (!isElementVisible(By.xpath("//*[contains(text(), '" + field
				+ "')]/parent::td/following-sibling::td//*[@id='gwt-debug-SingleUploaderWithResetWidget-resetBtn']"))) {

			WebElement elem = webDriver.findElement(By.xpath("//*[contains(text(), '" + field
					+ "')]/parent::td/following-sibling::td//input[@class='gwt-FileUpload']"));

			elem.sendKeys(Settings.FILE_LOCATION);
			wait(Settings.WAIT_SECONDS * 2);
		}

		return this;
	}
}
