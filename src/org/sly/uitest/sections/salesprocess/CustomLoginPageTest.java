package org.sly.uitest.sections.salesprocess;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.AWTException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.framework.DevelopmentTest;
import org.sly.uitest.pageobjects.salesprocess.AdvisorRegisterPage;
import org.sly.uitest.pageobjects.salesprocess.FSLoginPage;
import org.sly.uitest.pageobjects.salesprocess.TrialAdvisorPage;
import org.sly.uitest.settings.Settings;

/**
 * This is the test for custom login page of Finance Sales Check the URL below
 * for test plan https://docs.google.com/spreadsheets/d/1
 * QVu2GH0dvYKWa1dDoz5MSeGvHbOs_k3uAE0qlPDdCkA/edit#gid=0
 * 
 * @author by Benny Leung
 * @date Oct 6,2016
 * @company Prive Financial
 *
 */
public class CustomLoginPageTest extends AbstractTest {

	String firstname = null;
	String lastname = null;

	@Ignore
	@Category(DevelopmentTest.class)
	public void testURLinCustomLoginPage() throws MalformedURLException, IOException {
		FSLoginPage main = new FSLoginPage(webDriver);

		String disclaimerURL = getAttributeStringByLocator(By.xpath("//a[contains(text(), 'Disclaimer')]"), "href");
		checkUrlValid(disclaimerURL);

		String contactURL = getAttributeStringByLocator(By.xpath("//a[contains(text(), 'Kontakt')]"), "href");
		checkUrlValid(contactURL);
	}

	@Ignore
	@Category(DevelopmentTest.class)
	public void testTrialAdvisorPage() throws InterruptedException {

		FSLoginPage main = new FSLoginPage(webDriver);

		TrialAdvisorPage trial = main.goToTrialAdvisorPage();
		// step 3
		trial.clickSubmitButton();
		assertTrue(isElementVisible(By.xpath(
				".//input[@id='gwt-debug-TrialAdvisorRegistrationPopupView-firstNameTextBox' and @class='formTextBox formTextBoxInvalid']")));
		assertTrue(isElementVisible(By.xpath(
				".//input[@id='gwt-debug-TrialAdvisorRegistrationPopupView-lastNameTextBox' and @class='formTextBox formTextBoxInvalid']")));
		assertTrue(isElementVisible(By.xpath(
				".//input[@id='gwt-debug-TrialAdvisorRegistrationPopupView-emailTextBox' and @class='formTextBox formTextBoxInvalid']")));

		// step 5
		trial.editFirstName("TestSurname" + getRandName()).clickSubmitButton();
		assertTrue(isElementVisible(By.xpath(
				".//input[@id='gwt-debug-TrialAdvisorRegistrationPopupView-lastNameTextBox' and @class='formTextBox formTextBoxInvalid']")));
		assertTrue(isElementVisible(By.xpath(
				".//input[@id='gwt-debug-TrialAdvisorRegistrationPopupView-emailTextBox' and @class='formTextBox formTextBoxInvalid']")));

		// step 7
		trial.editLastName("TestLastName" + getRandName()).clickSubmitButton();
		assertTrue(isElementVisible(By.xpath(
				".//input[@id='gwt-debug-TrialAdvisorRegistrationPopupView-emailTextBox' and @class='formTextBox formTextBoxInvalid']")));

		// step 9
		trial.editEmail("testEmail@").clickSubmitButton();
		assertTrue(isElementVisible(By.xpath(
				".//input[@id='gwt-debug-TrialAdvisorRegistrationPopupView-emailTextBox' and @class='formTextBox formTextBoxInvalid']")));

		// step 11
		trial.editEmail(Settings.EMAIL_FOR_TESTING).editTelephone(getRandName()).clickSubmitButton();
		assertTrue(!isElementVisible(By.xpath(
				".//input[@id='gwt-debug-TrialAdvisorRegistrationPopupView-emailTextBox' and @class='formTextBox formTextBoxInvalid']")));

	}

	@Ignore
	@Category(DevelopmentTest.class)
	public void testForgetPasswordPage() throws InterruptedException {
		webDriver.get(Settings.FINANCESALES_URL);
		FSLoginPage main = new FSLoginPage(webDriver);

		// step 4
		main.gotoForgetPasswordPage().editUsername(getRandName()).clickResetPasswordButton();
		assertTrue(pageContainsStr("Das Benutzer Konto mit dem spezifizierten Benutzernamen wurde nicht gefunden."));
		clickOkButtonIfVisible();

		// step 6
		main.gotoForgetPasswordPage().editUsername("testAccount1").clickResetPasswordButton();
		assertTrue(!pageContainsStr("Das Benutzer Konto mit dem spezifizierten Benutzernamen wurde nicht gefunden."));

	}

	@Ignore
	public void testRegistration() throws InterruptedException, AWTException {

		webDriver.get(Settings.FINANCESALES_URL);

		FSLoginPage main = new FSLoginPage(webDriver);

		main.goToAdvisorRegisterPage();

		advisorRegistrationStep1();

		advisorRegistrationStep2();

		advisorRegistrationStep3();

		advisorRegistrationStep4();

		advisorRegistrationStep5();

		advisorRegistrationStep6();

		advisorRegistrationStep7();

		passStep8();

		passStep9();
	}

	public void advisorRegistrationStep1() throws InterruptedException {

		List<String> countryList = Arrays.asList("Belgien", "Deutschland", "Frankreich", "Luxemburg", "Schweiz",
				"Österreich");

		List<String> titleList1 = Arrays.asList("", "Herr", "Frau");
		// test case 6
		checkMaritalStatus();

		// test case 7
		checkList(By.id("gwt-debug-AdvisorEditTabOverviewView-taxResidenceListBox"), countryList);
		// test case 8
		checkList(By.id("gwt-debug-AdvisorEditTabOverviewView-titleListBox"), titleList1);

		// test case 9
		DateFormat dateFormat = new SimpleDateFormat("yyyy");
		Calendar cal = Calendar.getInstance();

		String yearInString = dateFormat.format(cal.getTime());
		int year = Integer.valueOf(yearInString);

		checkDate("Geburtsdatum", "1", "Jan", "2099", false);
		checkDate("Geburtsdatum", "1", "Jan", String.valueOf(year - 17), false);
		checkDate("Geburtsdatum", "1", "Jan", String.valueOf(year - 18), true);
		checkDate("Geburtsdatum", "1", "Jan", String.valueOf(year - 19), true);

		// test case 10
		checkField("Email", "123@123", false);
		checkField("Email", Settings.EMAIL_FOR_TESTING, true);

		// test case 11
		passStep1();
	}

	public void advisorRegistrationStep2() throws InterruptedException, AWTException {

		waitForElementVisible(By.id("gwt-debug-AdvisorEditTabPersonalIdentificationView-idTypeListBox"), 10);
		List<String> idList = Arrays.asList("", "Personalausweis", "Reisepass");

		List<String> countryList = Arrays.asList("Belgien", "Deutschland", "Frankreich", "Luxemburg", "Schweiz",
				"Österreich");

		// test case 15
		checkUploadFile("PDF des Identitätsnachweises");

		// test case 12
		checkList(By.id("gwt-debug-AdvisorEditTabPersonalIdentificationView-idTypeListBox"), idList);

		// test case 13
		checkList(By.id("gwt-debug-AdvisorEditTabPersonalIdentificationView-nationalityListBox"), countryList);

		// test case 14
		DateFormat yearFormat = new SimpleDateFormat("yyyy");
		Calendar cal = Calendar.getInstance();

		String yearInString = yearFormat.format(cal.getTime());
		int year = Integer.valueOf(yearInString);

		checkDate("Gültig bis", "1", "Sep", String.valueOf(year - 1), false);
		checkDate("Gültig bis", "1", "Sep", yearInString, false);
		checkDate("Gültig bis", "1", "Sep", String.valueOf("2099"), true);

		// test case 16
		passStep2();

	}

	public void advisorRegistrationStep3() throws InterruptedException, AWTException {

		AdvisorRegisterPage register = new AdvisorRegisterPage(webDriver);

		List<String> landList = Arrays.asList("", "Deutschland", "Frankreich", "Österreich");

		List<String> listForDeustchland = Arrays.asList("", "AG", "Co & KG", "Einzelperson", "Gmbh", "OHG");
		List<String> listForFrankreich = Arrays.asList("", "GIE", "SARL", "SAS", "SA", "SCA", "SCS", "SE", "SNC");
		List<String> listForÖsterreich = Arrays.asList("", "Eingetragener Unternehmer (e.U.)", "Genossenschaft",
				"Gesb. R.", "Gmbh.", "KG", "OG");

		HashMap<String, List<String>> m1 = new HashMap<String, List<String>>();

		m1.put("Frankreich", listForFrankreich);
		m1.put("Österreich", listForÖsterreich);
		m1.put("Deutschland", listForDeustchland);

		// test case 17
		assertTrue(getTextByLocator(By.id("gwt-debug-AdvisorEditTabCompanyView-employeesNumberTextBox")).equals("0"));
		assertTrue(
				getTextByLocator(By.id("gwt-debug-AdvisorEditTabCompanyView-fieldWorkEmployeesTextBox")).equals("0"));

		// test case 18
		checkList(By.id("gwt-debug-AdvisorEditTabCompanyView-companyLegalAuthorityListBox"), landList);

		for (String land : landList.subList(1, landList.size())) {

			register.editCompanyCountry(land);
			checkList(By.id("gwt-debug-AdvisorEditTabCompanyView-companyLegalTypeListBox"), m1.get(land));
		}

		// test case 19
		checkField("Firmengründungsjahr", "2017", false);
		checkField("Firmengründungsjahr", "2016", true);
		checkField("Firmengründungsjahr", "2015", true);

		// test case 20
		passStep3();
	}

	public void advisorRegistrationStep4() throws InterruptedException, AWTException {

		waitForElementVisible(By.id("gwt-debug-AdvisorEditTabCompanyInformationView-secondOwnerTitleListBox"), 10);
		// test case 21
		List<String> titleList = Arrays.asList("", "Herr", "Frau", "Fräulein", "Fr.", "Dr.");
		checkList(By.id("gwt-debug-AdvisorEditTabCompanyInformationView-secondOwnerTitleListBox"), titleList);

		// test case 22
		// DateFormat dateFormat = new SimpleDateFormat("yyyy");
		// Calendar cal = Calendar.getInstance();
		//
		// String yearInString = dateFormat.format(cal.getTime());
		// int year = Integer.valueOf(yearInString);
		//
		// checkDate("Geburtstag des zweiten Inhabers", "1", "Jan", "2099",
		// false);
		// checkDate("Geburtstag des zweiten Inhabers", "1", "Jan",
		// String.valueOf(year - 17), false);
		// checkDate("Geburtstag des zweiten Inhabers", "1", "Jan",
		// String.valueOf(year - 18), true);
		// checkDate("Geburtstag des zweiten Inhabers", "1", "Jan",
		// String.valueOf(year - 19), true);

		// test case 23
		if (pageContainsStr("Polizeiliches Führungszeugnis (max. 6 Monate alt)")) {
			checkUploadFile("Polizeiliches Führungszeugnis (max. 6 Monate alt)");
		}

		wait(5);
		// checkUploadFile("Persönliche Identifizierung (Ausweis, PDF)");

		// test case 24
		passStep4();
	}

	public void advisorRegistrationStep5() throws InterruptedException, AWTException {

		DateFormat dateFormat = new SimpleDateFormat("yyyy");
		Calendar cal = Calendar.getInstance();

		String yearInString = dateFormat.format(cal.getTime());
		int year = Integer.valueOf(yearInString);
		// test case 25
		checkDate("Identifizierungsdatum", "1", "Jan", "2099", false);
		checkDate("Identifizierungsdatum", "1", "Jan", String.valueOf(year), true);
		checkDate("Identifizierungsdatum", "1", "Jan", String.valueOf(year - 1), true);

		checkDate("Handelsregisterauszugdatum", "1", "Jan", "2099", false);
		checkDate("Handelsregisterauszugdatum", "1", "Jan", String.valueOf(year), true);
		checkDate("Handelsregisterauszugdatum", "1", "Jan", String.valueOf(year - 1), true);
		// test case 26
		passStep5();
	}

	public void advisorRegistrationStep6() throws InterruptedException, AWTException {

		String country1 = "Deutschland";
		String country2 = "Frankreich";
		String country3 = "Österreich";
		String day = "1";
		String month = "Jan";
		String year = "1993";
		String registerNumber = getRandName();
		String registerName = getRandName();
		String documentField = "Nachweis (PDF)";

		// test case 27 for Germany
		AdvisorRegisterPage register = new AdvisorRegisterPage(webDriver);
		this.changeCompanyCountryAndRequiredDocument(country1, "AG");
		assertTrue(pageContainsStr("IHK-Erlaubnis nach §34d vorhanden?"));
		assertTrue(pageContainsStr("IHK-Erlaubnis nach §34f Abs. 1 vorhanden?"));
		assertTrue(pageContainsStr("IHK-Erlaubnis nach §34f Abs. 2 vorhanden?"));
		assertTrue(pageContainsStr("IHK-Erlaubnis nach §34f Abs. 3 vorhanden?"));
		assertTrue(pageContainsStr("IHK-Erlaubnis nach §34i vorhanden?"));
		assertTrue(pageContainsStr("IHK-Erlaubnis nach §32 KWG vorhanden?"));

		// test case 28 for Germany
		register.editGermanPermissionDocument(true, "IHK-Erlaubnis nach §34d vorhanden?", day, month, year,
				registerNumber, documentField).clickNextPage();

		register.editGermanyRegistrationLicence("name").editAddress("address").clickNextPage();

		assertTrue(!isElementVisible(By.xpath(".//span[.='6']")));

		register.clickPreviousPage();

		register.editGermanPermissionDocument(true, "IHK-Erlaubnis nach §34f Abs. 1 vorhanden?", day, month, year,
				registerNumber, documentField).clickNextPage();

		assertTrue(!isElementVisible(By.xpath(".//span[.='6']")));

		// test case 27 for Austria
		this.changeCompanyCountryAndRequiredDocument(country3, "KG");
		assertTrue(pageContainsStr("Vermögensberater ?"));
		assertTrue(pageContainsStr("Finanzdienstleistungsassistent ?"));
		assertTrue(pageContainsStr("Versicherungsmakler ?"));

		// test case 28 for Austria
		register.editAustriaPermissionDocument(true, "Vermögensberater ?", day, month, year, registerNumber,
				registerName, documentField);
		register.clickNextPage();
		assertTrue(!isElementVisible(By.xpath(".//span[.='6']")));

		register.clickPreviousPage();

		register.editAustriaPermissionDocument(true, "Finanzdienstleistungsassistent ?", day, month, year,
				registerNumber, registerName, documentField);
		register.clickNextPage();
		assertTrue(!isElementVisible(By.xpath(".//span[.='6']")));

		// test case 27 for France
		this.changeCompanyCountryAndRequiredDocument(country2, "SE");
		assertTrue(pageContainsStr("ORIAS ?"));
		assertTrue(pageContainsStr("Membership Certificate ?"));

		// test case 28 for France
		register.editFrancePermissionDocument(true, "ORIAS ?", day, month, year, registerNumber, registerName,
				documentField);
		register.clickNextPage();
		assertTrue(!isElementVisible(By.xpath(".//span[.='6']")));

		register.clickPreviousPage();

		register.editFrancePermissionDocument(true, "Membership Certificate ?", day, month, year, registerNumber,
				registerName, documentField);
		register.clickNextPage();
		assertTrue(!isElementVisible(By.xpath(".//span[.='6']")));

		register.clickPreviousPage();

		// test case 29
		passStep6();
	}

	public void advisorRegistrationStep7() throws InterruptedException, AWTException {

		log("firstname : " + firstname);
		log("lastname : " + lastname);
		String ownername = getTextByLocator(By.id("gwt-debug-PaymentMethodInnerWidget-accountOwnerTextBox"));
		// test case 30
		// assertTrue(ownername.contains("Lastname"));
		assertTrue(ownername.contains("Firstname"));

		AdvisorRegisterPage register = new AdvisorRegisterPage(webDriver);

		// test case 31
		register.clickNextPage();
		assertTrue(isElementVisible(By.xpath(
				".//input[@id='gwt-debug-PaymentMethodInnerWidget-ibanTextBox' and @class='formTextBox formTextBoxInvalid']")));

		register.editIBAN("DE123450").clickNextPage();
		assertTrue(isElementVisible(By.xpath(
				".//input[@id='gwt-debug-PaymentMethodInnerWidget-ibanTextBox' and @class='formTextBox formTextBoxInvalid']")));

		register.editIBAN("DE12345678901234567980").clickNextPage();
		assertTrue(!isElementVisible(By.xpath(".//span[.='7']")));

		register.clickPreviousPage();

		passStep7();
	}

	public void checkMaritalStatus() throws InterruptedException {

		AdvisorRegisterPage register = new AdvisorRegisterPage(webDriver);

		// step 3
		register.editMaritalStatus(1);
		assertTrue(!isElementDisplayed(
				By.id("gwt-debug-AdvisorEditTabOverviewView-martimonialPropertyRegimeByLawCheckBox-input")));

		// step 6
		register.editMaritalStatus(3);
		assertTrue(!isElementDisplayed(
				By.id("gwt-debug-AdvisorEditTabOverviewView-martimonialPropertyRegimeByLawCheckBox-input")));

		// step 7
		register.editMaritalStatus(4);
		assertTrue(!isElementDisplayed(
				By.id("gwt-debug-AdvisorEditTabOverviewView-martimonialPropertyRegimeByLawCheckBox-input")));

		// step 8
		register.editMaritalStatus(2);
		assertTrue(isElementDisplayed(
				By.id("gwt-debug-AdvisorEditTabOverviewView-martimonialPropertyRegimeByLawCheckBox-input")));
		assertTrue(isElementDisplayed(
				By.id("gwt-debug-AdvisorEditTabOverviewView-martimonialPropertyRegimeComuOfGoodsCheckBox-input")));
		assertTrue(isElementDisplayed(
				By.id("gwt-debug-AdvisorEditTabOverviewView-martimonialPropertyRegimeSeparateEstateCheckBox-input")));

		// step 9 and 10
		register.editPropertyType(1);
		assertTrue(webDriver
				.findElement(By.id("gwt-debug-AdvisorEditTabOverviewView-martimonialPropertyRegimeByLawCheckBox-input"))
				.isSelected());

		register.editPropertyType(2);
		assertTrue(webDriver
				.findElement(
						By.id("gwt-debug-AdvisorEditTabOverviewView-martimonialPropertyRegimeComuOfGoodsCheckBox-input"))
				.isSelected());
		register.editPropertyType(3);
		assertTrue(webDriver
				.findElement(
						By.id("gwt-debug-AdvisorEditTabOverviewView-martimonialPropertyRegimeSeparateEstateCheckBox-input"))
				.isSelected());

	}

	public void checkList(By locator, List<String> checkList) {

		WebElement elem = webDriver.findElement(locator);
		Select select = new Select(elem);

		List<WebElement> list = select.getOptions();
		log(String.valueOf(list.size()));
		int i = 0;
		for (WebElement elems : list.subList(0, checkList.size())) {
			assertTrue(elems.getText().equals(checkList.get(i)));
			i++;
		}

	}

	public void checkDate(String field, String day, String month, String year, boolean valid)
			throws InterruptedException {
		AdvisorRegisterPage register = new AdvisorRegisterPage(webDriver);

		register.editDate(field, day, month, year).clickNextPage();
		wait(2);
		if (valid) {
			assertTrue(!isElementVisible(By.xpath("//*[contains(text(), '" + field
					+ "')]/parent::td/following-sibling::td//*[@id='gwt-debug-CompositeDatePicker-yearTextBox' and contains(@class,'Invalid')]")));
		} else {
			assertTrue(isElementVisible(By.xpath("//*[contains(text(), '" + field
					+ "')]/parent::td/following-sibling::td//*[@id='gwt-debug-CompositeDatePicker-yearTextBox' and contains(@class,'Invalid')]")));
		}
	}

	public void checkField(String field, String value, boolean valid) throws InterruptedException {
		AdvisorRegisterPage register = new AdvisorRegisterPage(webDriver);
		register.editField(field, value).clickNextPage();
		waitForElementVisible(
				By.xpath("//*[contains(text(), '" + field + "')]/parent::td/following-sibling::td//input"), 10);
		if (valid) {
			assertTrue(!isElementVisible(By.xpath("//*[contains(text(), '" + field
					+ "')]/parent::td/following-sibling::td//input[contains(@class ,'formTextBoxInvalid')]")));
		} else {
			assertTrue(isElementVisible(By.xpath("//*[contains(text(), '" + field
					+ "')]/parent::td/following-sibling::td//input[contains(@class ,'formTextBoxInvalid')]")));

		}
	}

	public void checkUploadFile(String field) throws AWTException, InterruptedException {

		AdvisorRegisterPage register = new AdvisorRegisterPage(webDriver);

		register.clickNextPage();

		assertTrue(isElementVisible(By.xpath(".//*[ @class='formTextBoxInvalid']")));

		register.uploadFile(field).clickNextPage();
	}

	public void changeCompanyCountryAndRequiredDocument(String country, String companyForm)
			throws InterruptedException, AWTException {

		AdvisorRegisterPage register = new AdvisorRegisterPage(webDriver);

		while (isElementVisible(
				By.xpath(".//div[@class='stepper-stepCircle stepper-stepCircleActive']//span[.='3']")) == false) {
			register.clickPreviousPage();
			wait(2);
		}

		register.editCompanyCountry(country).editCompanyForm(companyForm);

		register.clickNextPage();
		assertTrue(isElementVisible(
				By.xpath(".//div[@class='stepper-stepCircle stepper-stepCircleActive']//span[.='4']")) == true);
		passStep4();

		while (isElementVisible(
				By.xpath(".//div[@class='stepper-stepCircle stepper-stepCircleActive']//span[.='6']")) == false) {
			register.clickNextPage();
		}

	}

	public void passStep1() throws InterruptedException {
		AdvisorRegisterPage register = new AdvisorRegisterPage(webDriver);

		String title = "Herr";
		firstname = "firstname" + getRandName();
		lastname = "lastname" + getRandName();
		String address = "address" + getRandName();
		String telephone = getRandName();
		String Email = Settings.EMAIL_FOR_TESTING;
		String day = "1";
		String month = "Jan";
		String year = "1344";
		int status = 1;
		String country = "Deutschland";

		register.editTitle(title).editName(firstname, lastname).editAddress(address).editTelephone(telephone)
				.editEmail(Email).editDate("Geburtsdatum", day, month, year).editMaritalStatus(status)
				.editCountryOfResidence(country).editTaxId(getRandName()).editTaxAuthority(getRandName())
				.clickNextPage();
		assertTrue(!isElementVisible(By.xpath(".//span[.='1']")));
	}

	public void passStep2() throws InterruptedException, AWTException {

		String idType = "Reisepass";
		String day = "1";
		String month = "Jan";
		String year = "2099";
		String country = "Deutschland";

		AdvisorRegisterPage register = new AdvisorRegisterPage(webDriver);

		register.editDate("Gültig bis", day, month, year).editIdType(idType).uploadFile("PDF").editNationality(country)
				.editIdNumber(getRandName()).editIssuingEntity(getRandName()).clickNextPage();
		assertTrue(!isElementVisible(By.xpath(".//span[.='2']")));
	}

	public void passStep3() throws AWTException, InterruptedException {

		String firmName = "firm" + getRandName();
		String land1 = "Frankreich";
		String form = "SCA";
		String Umsatzsteuer = "Umsatzsteuer";
		String Steuernummer = "Steuernummer";
		String year = "1234";
		String product = "ABCABC";

		AdvisorRegisterPage register = new AdvisorRegisterPage(webDriver);
		register.editCompanyName(firmName).uploadFile("Gewerbeanmeldung").editCompanyCountry(land1)
				.editCompanyForm(form).editTaxNumber(Umsatzsteuer).editCompanyTaxId(Steuernummer)
				.editCompanyFormationYear(year).editProductProvider(product).clickNextPage();

		while (isElementVisible(By.xpath(".//span[.='3']"))) {

			wait(3);
			register.clickNextPage();
		}
		assertTrue(!isElementVisible(By.xpath(".//span[.='3']")));
	}

	public void passStep4() throws InterruptedException, AWTException {

		String title = "Herr";
		String firstname = "firstname" + getRandName();
		String lastname = "lastname" + getRandName();
		String firstname2 = "firstname" + getRandName();
		String lastname2 = "lastname" + getRandName();
		String address = "address" + getRandName();
		String day = "1";
		String month = "Jan";
		String year = "1993";
		AdvisorRegisterPage register = new AdvisorRegisterPage(webDriver);

		register.editFirstOwnerOnlyRadioButton(true).editFirstRecordRadioButton(false)
				.editFirstCompanyPosition("position");
		if (pageContainsStr("Polizeiliches Führungszeugnis (max. 6 Monate alt)")) {
			register.uploadFile("Polizeiliches Führungszeugnis (max. 6 Monate alt)");
		}

		wait(3);
		register.clickNextPage();
		assertTrue(!isElementVisible(By.xpath(".//span[.='4']")));
	}

	public void passStep5() throws AWTException, InterruptedException {
		String day = "1";
		String month = "Jan";
		String registerNumber = "132";
		String registerCity = "NYC";
		String address = "address";
		DateFormat dateFormat = new SimpleDateFormat("yyyy");
		Calendar cal = Calendar.getInstance();

		String yearInString = dateFormat.format(cal.getTime());
		int year = Integer.valueOf(yearInString);

		AdvisorRegisterPage register = new AdvisorRegisterPage(webDriver);
		register.uploadFile("Nachweis (Handelsregisterauszug, PDF)")
				.editDate("Identifizierungsdatum", day, month, String.valueOf(year))

				.editDate("Handelsregisterauszugdatum", day, month, String.valueOf(year))
				.editRegisterNumber(registerNumber).uploadFile("Unterschrift des Vertretungsberechtigten")
				.editRegisterCourtAndCity(registerCity).editAddress(address)

				.clickNextPage();
		assertTrue(!isElementVisible(By.xpath(".//span[.='5']")));
	}

	public void passStep6() throws AWTException, InterruptedException {
		String day = "1";
		String month = "Jan";
		String year = "1993";
		String registerNumber = getRandName();
		String registerName = getRandName();
		String documentField = "Nachweis (PDF)";

		AdvisorRegisterPage register = new AdvisorRegisterPage(webDriver);

		register.editFrancePermissionDocument(true, "ORIAS ?", day, month, year, registerNumber, registerName,
				documentField);
		register.clickNextPage();
		assertTrue(!isElementVisible(By.xpath(".//span[.='6']")));
	}

	public void passStep7() throws InterruptedException {

		AdvisorRegisterPage register = new AdvisorRegisterPage(webDriver);

		register.editIBAN("DE12345678901234567980").clickNextPage();
		assertTrue(!isElementVisible(By.xpath(".//span[.='7']")));
	}

	public void passStep8() throws InterruptedException {

		AdvisorRegisterPage register = new AdvisorRegisterPage(webDriver);

		String confirmMessage = getTextByLocator(By.id("gwt-debug-AdvisorEditTabConfirmRegister-headerLabel"));

		assertTrue(confirmMessage.contains("Bitte schließen Sie jetzt Ihre Registrierung ab! Wir prüfen Ihre Daten"));

		register.clickNextPage();
	}

	public void passStep9() throws InterruptedException {

		AdvisorRegisterPage register = new AdvisorRegisterPage(webDriver);
		String confirmMessage = getTextByLocator(By.id("gwt-debug-AdvisorEditTabRegisterComplete-headerLabel"));
		assertTrue(confirmMessage.contains("Vielen Dank für die Registrierung!"));

		register.clickCompletePage();
	}

	public void passAll() throws InterruptedException, AWTException {
		webDriver.get(Settings.FINANCESALES_URL);

		FSLoginPage main = new FSLoginPage(webDriver);

		main.goToAdvisorRegisterPage();

		passStep1();
		passStep2();
		passStep3();
		passStep4();
		passStep5();
		passStep6();
		passStep7();
		passStep8();
		passStep9();
	}

	@Ignore
	@Category(DevelopmentTest.class)
	public void testLogout() throws InterruptedException {
		FSLoginPage main = new FSLoginPage(webDriver);
		main.loginAs(Settings.FINANCESALES_ADVISOR_USERNAME, Settings.FINANCESALES_ADVISOR_PASSWORD).checkLogoutFS();
		wait(5);
		assertEquals(webDriver.getCurrentUrl(), "https://www.finance-sales.de/");

		FSLoginPage main2 = new FSLoginPage(webDriver);
		main2.loginAs(Settings.FINANCESALES_ADMIN_USERNAME, Settings.FINANCESALES_ADMIN_PASSWORD).checkLogoutFS();
		wait(5);
		assertEquals(webDriver.getCurrentUrl(), "https://www.finance-sales.de/");
	}
}
