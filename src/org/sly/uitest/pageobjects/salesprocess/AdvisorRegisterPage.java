package org.sly.uitest.pageobjects.salesprocess;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.settings.Settings;

/**
 * This class represents the RegisterPage, which is used for 1. Advisor
 * Registration 2. Client Registration 3. Edit Client
 * http://fsuat.privemanagers.com/financesales/custom_login_page.html
 * 
 * @author Benny Leung
 * @date : 13 Sept, 2016
 * @company Prive Financial
 * 
 */
public class AdvisorRegisterPage extends AbstractPage {
	public AdvisorRegisterPage(WebDriver webDriver) throws InterruptedException {

		super();
		this.webDriver = webDriver;
		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='myDialogBox']")));

	}

	/**
	 * edit Title in register page
	 * 
	 * @param title
	 * @return {@link AdvisorRegisterPage}
	 */
	public AdvisorRegisterPage editTitle(String title) {
		selectFromDropdown(By.id("gwt-debug-AdvisorEditTabOverviewView-titleListBox"), title);
		return this;
	}

	/**
	 * edit second Title in register page
	 * 
	 * @param title
	 * @return {@link AdvisorRegisterPage}
	 */
	public AdvisorRegisterPage editSecondTitle(String title) {
		selectFromDropdown(By.id("gwt-debug-AdvisorEditTabCompanyInformationView-secondOwnerTitleListBox"), title);
		return this;
	}

	public AdvisorRegisterPage editName(String firstname, String lastname) {

		sendKeysToElement(By.id("gwt-debug-AdvisorEditTabOverviewView-firstNameTextBox"), firstname);
		sendKeysToElement(By.id("gwt-debug-AdvisorEditTabOverviewView-lastNameTextBox"), lastname);

		return this;
	}

	public AdvisorRegisterPage editEmail(String email) {

		sendKeysToElement(By.id("gwt-debug-EditUserEmailWidget-emailTextBox"), email);

		return this;

	}

	/**
	 * edit address in register page
	 * 
	 * @param title
	 * @return {@link AdvisorRegisterPage}
	 */
	public AdvisorRegisterPage editAddress(String address) {
		sendKeysToElement(By.id("gwt-debug-EditUserAddressWidget-address1TextBox"), address);
		return this;
	}

	/**
	 * edit telephone in register page
	 * 
	 * @param telephone
	 * @return {@link AdvisorRegisterPage}
	 */
	public AdvisorRegisterPage editTelephone(String telephone) {
		sendKeysToElement(By.id("gwt-debug-EditUserPhoneWidget-numberTextBox"), telephone);
		return this;
	}

	/**
	 * edit date in register page
	 * 
	 * @param field
	 * @param day
	 * @param month
	 * @param year
	 * @return {@link AdvisorRegisterPage}
	 * @throws InterruptedException
	 */
	public AdvisorRegisterPage editDate(String field, String day, String month, String year)
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
	 * edit status in register page: 1 for single, 2 for married, 3 for
	 * divorced, 4 for widowed
	 * 
	 * @param status
	 * @return {@link AdvisorRegisterPage}
	 */
	public AdvisorRegisterPage editMaritalStatus(int status) {
		switch (status) {
		case 1:
			WebElement elem = webDriver
					.findElement(By.id("gwt-debug-AdvisorEditTabOverviewView-maritalStatusSingleCheckBox-input"));

			if (!elem.isSelected()) {
				elem.click();
			}
			break;
		case 2:
			WebElement elem2 = webDriver
					.findElement(By.id("gwt-debug-AdvisorEditTabOverviewView-maritalStatusMarriedCheckBox-input"));

			if (!elem2.isSelected()) {
				elem2.click();
			}
			break;
		case 3:
			WebElement elem3 = webDriver
					.findElement(By.id("gwt-debug-AdvisorEditTabOverviewView-maritalStatusDivorcedCheckBox-input"));

			if (!elem3.isSelected()) {
				elem3.click();
			}
			break;
		case 4:
			WebElement elem4 = webDriver
					.findElement(By.id("gwt-debug-AdvisorEditTabOverviewView-maritalStatusWidowedCheckBox-input"));

			if (!elem4.isSelected()) {
				elem4.click();
			}
			break;
		}
		return this;
	}

	/**
	 * edit property type in advisor register page:
	 * 
	 * 1 for By Law, 2 for Comu of Goods, 3 for Sperate Estate
	 * 
	 * @param type
	 * @return {@link AdvisorRegisterPage}
	 */
	public AdvisorRegisterPage editPropertyType(int type) {
		switch (type) {
		case 1:
			WebElement elem1 = webDriver.findElement(
					By.id("gwt-debug-AdvisorEditTabOverviewView-martimonialPropertyRegimeByLawCheckBox-input"));

			if (!elem1.isSelected()) {
				elem1.click();
			}
			break;
		case 2:
			WebElement elem2 = webDriver.findElement(
					By.id("gwt-debug-AdvisorEditTabOverviewView-martimonialPropertyRegimeComuOfGoodsCheckBox-input"));

			if (!elem2.isSelected()) {
				elem2.click();
			}
			break;
		case 3:
			WebElement elem3 = webDriver.findElement(By
					.id("gwt-debug-AdvisorEditTabOverviewView-martimonialPropertyRegimeSeparateEstateCheckBox-input"));

			if (!elem3.isSelected()) {
				elem3.click();
			}
			break;
		}
		return this;
	}

	/**
	 * edit residence in register page
	 * 
	 * @param residence
	 * @return {@link AdvisorRegisterPage}
	 */
	public AdvisorRegisterPage editCountryOfResidence(String residence) {

		waitForElementVisible(By.id("gwt-debug-AdvisorEditTabOverviewView-countryListBox"), 5);
		selectFromDropdown(By.id("gwt-debug-AdvisorEditTabOverviewView-countryListBox"), residence);
		return this;
	}

	/**
	 * edit political expose in register page
	 * 
	 * @param office
	 * @return {@link AdvisorRegisterPage}
	 */
	public AdvisorRegisterPage editPoliticalExpose(boolean toCheck) {
		WebElement elem = webDriver
				.findElement(By.id("gwt-debug-AdvisorEditTabOverviewView-politicallyExposedPersonCheckBox-input"));
		boolean checked = elem.isSelected();
		if (toCheck) {
			if (!checked) {
				elem.click();
			}
		} else {
			if (checked) {
				elem.click();
			}
		}
		return this;

	}

	/**
	 * click next page button in register page
	 * 
	 * @return {@link AdvisorRegisterPage}
	 */
	public AdvisorRegisterPage clickNextPage() {
		clickElement(By.id("gwt-debug-AdvisorEditWizardControllerView-nextBtn"));
		return this;
	}

	/**
	 * click last page button in register page
	 * 
	 * @return {@link AdvisorRegisterPage}
	 */
	public AdvisorRegisterPage clickPreviousPage() {
		clickElement(By.id("gwt-debug-AdvisorEditWizardControllerView-backBtn"));
		return this;
	}

	/**
	 * click the complete nutton in register page
	 * 
	 * @return
	 */
	public FSLoginPage clickCompletePage() {
		waitForElementVisible(By.id("gwt-debug-AdvisorEditWizardControllerView-nextBtn"), 10);
		clickElement(By.id("gwt-debug-AdvisorEditWizardControllerView-nextBtn"));
		return new FSLoginPage(webDriver);
	}

	/**
	 * edit id type in register page
	 * 
	 * @param type
	 * @return {@link AdvisorRegisterPage}
	 */
	public AdvisorRegisterPage editIdType(String type) {
		selectFromDropdown(By.id("gwt-debug-AdvisorEditTabPersonalIdentificationView-idTypeListBox"), type);
		return this;
	}

	/**
	 * edit nationality in register page
	 * 
	 * @param nationality
	 * @return {@link AdvisorRegisterPage}
	 */
	public AdvisorRegisterPage editNationality(String nationality) {
		selectFromDropdown(By.id("gwt-debug-AdvisorEditTabPersonalIdentificationView-nationalityListBox"), nationality);
		return this;
	}

	/**
	 * edit ID in register page
	 * 
	 * @param id
	 * @return {@link AdvisorRegisterPage}
	 */
	public AdvisorRegisterPage editIdNumber(String id) {
		sendKeysToElement(By.id("gwt-debug-AdvisorEditTabPersonalIdentificationView-idNumberTextBox"), id);
		return this;
	}

	/**
	 * edit issuing entity in register page
	 * 
	 * @param entity
	 * @return {@link AdvisorRegisterPage}
	 */
	public AdvisorRegisterPage editIssuingEntity(String entity) {
		sendKeysToElement(By.id("gwt-debug-AdvisorEditTabPersonalIdentificationView-idIssuingEntityTextBox"), entity);
		return this;
	}

	/**
	 * edit tax id in register page
	 * 
	 * @param taxid
	 * @return {@link AdvisorRegisterPage}
	 */
	public AdvisorRegisterPage editTaxId(String taxid) {
		sendKeysToElement(By.id("gwt-debug-AdvisorEditTabOverviewView-vatTaxIdTextBox"), taxid);
		return this;
	}

	/**
	 * edit company formation year in register page
	 * 
	 * @param year
	 * @return {@link AdvisorRegisterPage}
	 */
	public AdvisorRegisterPage editCompanyFormationYear(String year) {

		sendKeysToElement(By.id("gwt-debug-AdvisorEditTabCompanyView-companyYearFormationTextBox"), year);
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
	public AdvisorRegisterPage uploadFile(String field) throws AWTException, InterruptedException {
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

	public AdvisorRegisterPage uploadFileForDocument(String field, String documentField)
			throws AWTException, InterruptedException {
		wait(5);
		if (!isElementVisible(By.xpath("//*[contains(text(), '" + field
				+ "')]/parent::td/parent::tr/following-sibling::tr[1]//*[contains(text(),'" + documentField
				+ "')]/parent::td/following-sibling::td//*[@id='gwt-debug-SingleUploaderWithResetWidget-resetBtn']"))) {

			clickElement(By.xpath("//*[contains(text(), '" + field
					+ "')]/parent::td/parent::tr/following-sibling::tr[1]//*[contains(text(),'" + documentField
					+ "')]/parent::td/following-sibling::td//div[@class='upld-form-elements']"));
			wait(3);
			Robot robot = new Robot();

			robot.keyPress(KeyEvent.VK_RIGHT);
			robot.keyPress(KeyEvent.VK_RIGHT);
			robot.keyPress(KeyEvent.VK_DOWN);
			robot.keyPress(KeyEvent.VK_DOWN);
			robot.keyPress(KeyEvent.VK_DOWN);

			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyPress(KeyEvent.VK_ENTER);
		}

		wait(5);

		return this;
	}

	/**
	 * send keys to the field in register page
	 * 
	 * @param fields
	 *            field to be edited
	 * @param value
	 *            value to input
	 * @return {@link AdvisorRegisterPage}
	 */
	public AdvisorRegisterPage editField(String field, String value) {

		sendKeysToElement(By.xpath("//*[contains(text(), '" + field + "')]/parent::td/following-sibling::td//input"),
				value);
		return this;
	}

	/**
	 * edit compnay name in register page
	 * 
	 * @param companyName
	 * @return {@link AdvisorRegisterPage}
	 */
	public AdvisorRegisterPage editCompanyName(String companyName) {

		sendKeysToElement(By.id("gwt-debug-AdvisorEditTabCompanyView-companyNameTextBox"), companyName);
		return this;
	}

	/**
	 * edit company country in register page
	 * 
	 * @param companyCountry
	 * @return {@link AdvisorRegisterPage}
	 */
	public AdvisorRegisterPage editCompanyCountry(String companyCountry) {

		selectFromDropdown(By.id("gwt-debug-AdvisorEditTabCompanyView-companyLegalAuthorityListBox"), companyCountry);
		return this;
	}

	/**
	 * edit tax number in register page
	 * 
	 * @param number
	 * @return {@link AdvisorRegisterPage}
	 */
	public AdvisorRegisterPage editTaxNumber(String number) {
		sendKeysToElement(By.id("gwt-debug-AdvisorEditTabCompanyView-taxNumberTextBox"), number);
		return this;
	}

	/**
	 * edit tax authority in register page
	 * 
	 * @param authority
	 * @return {@link AdvisorRegisterPage}
	 */
	public AdvisorRegisterPage editTaxAuthority(String authority) {
		sendKeysToElement(By.id("gwt-debug-AdvisorEditTabOverviewView-taxAuthorityTextBox"), authority);
		return this;
	}

	/**
	 * edit tax authority in register page
	 * 
	 * @param authority
	 * @return {@link AdvisorRegisterPage}
	 */
	public AdvisorRegisterPage editCompanyTaxId(String authority) {
		sendKeysToElement(By.id("gwt-debug-AdvisorEditTabCompanyView-vatTaxIdTextBox"), authority);
		return this;
	}

	/**
	 * edit product provider in register page (Produktanbieter)
	 * 
	 * @param productProvider
	 * @return {@link AdvisorRegisterPage}
	 */
	public AdvisorRegisterPage editProductProvider(String productProvider) {
		sendKeysToElement(By.id("gwt-debug-AdvisorEditTabCompanyView-providerOfInvestmentTextArea"), productProvider);
		return this;
	}

	/**
	 * edit company form in register page
	 * 
	 * @param companyForm
	 * @return {@link AdvisorRegisterPage}
	 */
	public AdvisorRegisterPage editCompanyForm(String companyForm) {
		selectFromDropdown(By.id("gwt-debug-AdvisorEditTabCompanyView-companyLegalTypeListBox"), companyForm);
		return this;
	}

	/**
	 * check yes/no radiobutton for first Record in register page
	 * 
	 * @param field
	 * @param decision
	 *            True = Yes False = No
	 * @return {@link AdvisorRegisterPage}
	 */
	public AdvisorRegisterPage editFirstRecordRadioButton(boolean decision) {
		if (decision) {
			clickElement(By.id("gwt-debug-AdvisorEditTabCompanyInformationView-yesFirstOwnerHasOpenPoliceRecord"));
		} else {
			clickElement(By.id("gwt-debug-AdvisorEditTabCompanyInformationView-noFirstOwnerHasOpenPoliceRecord"));
		}
		return this;
	}

	public AdvisorRegisterPage editFirstCompanyPosition(String position) {
		sendKeysToElement(By.id("gwt-debug-AdvisorEditTabCompanyInformationView-firstOwnerCompanyPositionNameTextBox"),
				position);
		return this;
	}

	/**
	 * check yes/no radiobutton for second Record in register page
	 * 
	 * @param field
	 * @param decision
	 *            True = Yes False = No
	 * @return {@link AdvisorRegisterPage}
	 */
	public AdvisorRegisterPage editSecondRecordRadioButton(boolean decision) {
		if (decision) {
			clickElement(By.id("gwt-debug-AdvisorEditTabCompanyInformationView-yesSecondOwnerHasOpenPoliceRecord"));
		} else {
			clickElement(By.id("gwt-debug-AdvisorEditTabCompanyInformationView-noSecondOwnerHasOpenPoliceRecord"));
		}
		return this;
	}

	public AdvisorRegisterPage editFirstOwnerOnlyRadioButton(boolean decision) {
		if (decision) {
			clickElement(By.id("gwt-debug-AdvisorEditTabCompanyInformationView-yesFirstOwnerSoloRepresentation"));
		} else {
			clickElement(By.id("gwt-debug-AdvisorEditTabCompanyInformationView-noFirstOwnerSoloRepresentation"));
		}
		return this;
	}

	/**
	 * edit second owner name in register page
	 * 
	 * @param firstname
	 * @param lastname
	 * @return {@link AdvisorRegisterPage}
	 */
	public AdvisorRegisterPage editSecondOwnerName(String firstname, String lastname) {

		sendKeysToElement(By.id("gwt-debug-AdvisorEditTabCompanyInformationView-secondOwnerFirstNameTextBox"),
				firstname);

		sendKeysToElement(By.id("gwt-debug-AdvisorEditTabCompanyInformationView-secondOwnerLastNameTextBox"), lastname);

		return this;
	}

	/**
	 * complete the permission document in register page
	 * 
	 * @param toCheck
	 *            decision to check the checkbox
	 * @param field
	 *            field to be completed
	 * @param day
	 *            day for document
	 * @param month
	 *            month for document
	 * @param year
	 *            year for document
	 * @param registerNumber
	 *            register number for document
	 * @param filepath
	 *            proof of the document
	 * @return {@link AdvisorRegisterPage}
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public AdvisorRegisterPage editGermanPermissionDocument(Boolean toCheck, String field, String day, String month,
			String year, String registerNumber, String documentField) throws AWTException, InterruptedException {

		WebElement elem = webDriver.findElement(
				By.xpath("//*[contains(text(), '" + field + "')]/parent::td/following-sibling::td/span/input"));
		boolean checked = elem.isSelected();
		if (toCheck) {
			if (!checked) {
				elem.click();
			}
		} else {
			if (checked) {
				elem.click();
			}
		}

		selectFromDropdown(
				By.xpath("//*[contains(text(), '" + field
						+ "')]/parent::td/parent::tr/following-sibling::tr[1]//select[@id='gwt-debug-CompositeDatePicker-dayListBox']"),
				day);

		selectFromDropdown(
				By.xpath("//*[contains(text(), '" + field
						+ "')]/parent::td/parent::tr/following-sibling::tr[1]//select[@id='gwt-debug-CompositeDatePicker-monthListBox']"),
				month);

		sendKeysToElement(
				By.xpath("//*[contains(text(), '" + field
						+ "')]/parent::td/parent::tr/following-sibling::tr[1]//input[@id='gwt-debug-CompositeDatePicker-yearTextBox']"),
				year);

		sendKeysToElement(By.xpath(
				"(//*[contains(text(), '" + field + "')]/parent::td/parent::tr/following-sibling::tr[1]//input)[3]"),
				registerNumber);
		//
		this.uploadFileForDocument(field, documentField);

		return this;
	}

	/**
	 * edit register number in register page
	 * 
	 * @param number
	 * @return {@link AdvisorRegisterPage}
	 */
	public AdvisorRegisterPage editRegisterNumber(String number) {

		sendKeysToElement(By.id("gwt-debug-AdvisorEditTabCompanyIdentificationView-registerNumberTextBox"), number);
		return this;
	}

	/**
	 * edit register court and city in register page
	 * 
	 * @param place
	 * @return {@link AdvisorRegisterPage}
	 */
	public AdvisorRegisterPage editRegisterCourtAndCity(String place) {

		sendKeysToElement(By.id("gwt-debug-AdvisorEditTabCompanyIdentificationView-registerCourtAndCityTextBox"),
				place);
		return this;
	}

	/**
	 * edit Germany Registration Licence in register page
	 * 
	 * @param name
	 * @return {@link AdvisorRegisterPage}
	 */
	public AdvisorRegisterPage editGermanyRegistrationLicence(String name) {

		sendKeysToElement(By.id("gwt-debug-AdvisorEditTabLicencesForGermanyView-liIssuingEntityTextBox"), name);

		return this;
	}

	/**
	 * edit Austria Permission Document in register page
	 * 
	 * @param toCheck
	 * @param field
	 * @param day
	 * @param month
	 * @param year
	 * @param registerNumber
	 * @param registerName
	 * @param filepath
	 * @return {@link AdvisorRegisterPage}
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public AdvisorRegisterPage editAustriaPermissionDocument(Boolean toCheck, String field, String day, String month,
			String year, String registerNumber, String registerName, String documentField)
			throws AWTException, InterruptedException {

		WebElement elem = webDriver.findElement(
				By.xpath("//*[contains(text(), '" + field + "')]/parent::td/following-sibling::td/span/input"));
		boolean checked = elem.isSelected();
		if (toCheck) {
			if (!checked) {
				elem.click();
			}
		} else {
			if (checked) {
				elem.click();
			}
		}

		selectFromDropdown(
				By.xpath("//*[contains(text(),'" + field
						+ "')]/parent::td/parent::tr/following-sibling::tr[1]//select[@id='gwt-debug-CompositeDatePicker-dayListBox']"),
				day);

		selectFromDropdown(
				By.xpath("//*[contains(text(),'" + field
						+ "')]/parent::td/parent::tr/following-sibling::tr[1]//select[@id='gwt-debug-CompositeDatePicker-monthListBox']"),
				month);

		sendKeysToElement(
				By.xpath("//*[contains(text(),'" + field
						+ "')]/parent::td/parent::tr/following-sibling::tr[1]//input[@id='gwt-debug-CompositeDatePicker-yearTextBox']"),
				year);

		sendKeysToElement(By.xpath(
				"(//*[contains(text(),'" + field + "')]/parent::td/parent::tr/following-sibling::tr[1]//input)[3]"),
				registerNumber);

		sendKeysToElement(By.xpath(
				"(//*[contains(text(),'" + field + "')]/parent::td/parent::tr/following-sibling::tr[1]//input)[4]"),
				registerName);

		this.uploadFileForDocument(field, documentField);

		return this;
	}

	/**
	 * edit France Permission Document in register page
	 * 
	 * @param toCheck
	 * @param field
	 * @param day
	 * @param month
	 * @param year
	 * @param registerNumber
	 * @param registerName
	 * @param filepath
	 * @return {@link AdvisorRegisterPage}
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public AdvisorRegisterPage editFrancePermissionDocument(Boolean toCheck, String field, String day, String month,
			String year, String registerNumber, String registerName, String documentField)
			throws AWTException, InterruptedException {

		WebElement elem = webDriver.findElement(
				By.xpath("//*[contains(text(), '" + field + "')]/parent::td/following-sibling::td/span/input"));
		boolean checked = elem.isSelected();
		if (toCheck) {
			if (!checked) {
				elem.click();
			}
		} else {
			if (checked) {
				elem.click();
			}
		}

		this.uploadFileForDocument(field, documentField);

		selectFromDropdown(
				By.xpath("//*[contains(text(),'" + field
						+ "')]/parent::td/parent::tr/following-sibling::tr[1]//select[@id='gwt-debug-CompositeDatePicker-dayListBox']"),
				day);

		selectFromDropdown(
				By.xpath("//*[contains(text(),'" + field
						+ "')]/parent::td/parent::tr/following-sibling::tr[1]//select[@id='gwt-debug-CompositeDatePicker-monthListBox']"),
				month);

		sendKeysToElement(
				By.xpath("//*[contains(text(),'" + field
						+ "')]/parent::td/parent::tr/following-sibling::tr[1]//input[@id='gwt-debug-CompositeDatePicker-yearTextBox']"),
				year);

		sendKeysToElement(By.xpath(
				"(//*[contains(text(),'" + field + "')]/parent::td/parent::tr/following-sibling::tr[1]//input)[3]"),
				registerNumber);

		sendKeysToElement(By.xpath(
				"(//*[contains(text(),'" + field + "')]/parent::td/parent::tr/following-sibling::tr[1]//input)[4]"),
				registerName);

		return this;
	}

	/**
	 * edit IBAN in register page
	 * 
	 * @param iban
	 * @return {@link AdvisorRegisterPage}
	 */
	public AdvisorRegisterPage editIBAN(String iban) {

		sendKeysToElement(By.id("gwt-debug-PaymentMethodInnerWidget-ibanTextBox"), iban);

		return this;
	}

}
