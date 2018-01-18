package org.sly.uitest.sections.salesprocess;

import static org.junit.Assert.assertTrue;

import java.awt.AWTException;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.By;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.framework.DevelopmentTest;
import org.sly.uitest.pageobjects.clientsandaccounts.ClientOverviewPage;
import org.sly.uitest.pageobjects.salesprocess.ClientRegisterPage;
import org.sly.uitest.pageobjects.salesprocess.FSLoginPage;
import org.sly.uitest.pageobjects.salesprocess.FSMenubarPage;
import org.sly.uitest.settings.Settings;

/**
 * This is the test for client onboarding and editing client details. Check the
 * URL below for test plan https://docs.google.com/spreadsheets/d/1
 * QVu2GH0dvYKWa1dDoz5MSeGvHbOs_k3uAE0qlPDdCkA/edit#gid=0
 * 
 * @author by Benny Leung
 * @date Oct 5,2016
 * @company Prive Financial
 *
 */
public class FSClientTest extends AbstractTest {
	String firstname = null;
	String lastname = null;

	// Test case 39
	@Ignore
	@Category(DevelopmentTest.class)
	public void testCreateClient() throws Exception {

		FSLoginPage main = new FSLoginPage(webDriver);

		// variables for step 1
		firstname = "Firstname" + getRandName();
		lastname = "Lastname" + getRandName();
		String address = "address" + getRandName();

		// variable for step 2
		String day = "1";
		String month = "Jan";
		String year = "2099";
		String country = "Frankreich";
		String idType = "Reisepass";

		ClientRegisterPage register = main
				.loginAs(Settings.FINANCESALES_ADVISOR_USERNAME, Settings.FINANCESALES_ADVISOR_PASSWORD)
				.goToClientOverviewPageFS().clickCreateClientForFS();

		// step 1
		register.editTitleByValue("2").editName(firstname, lastname).editAddress(address).editEmail("abc@abc.com")
				.editDate("Geburtsdatum", "1", "Jan", "1234").editTelephone("123456789").editMaritalStatusForClient(1)
				.editTaxResidence("Frankreich").editTaxId(getRandName()).editTaxAuthority(getRandName())
				.clickNextPage();

		assertTrue(!isElementVisible(By.xpath(".//span[.='1']")));

		// step 2
		register.uploadFile("PDF").editDate("Gültig bis", day, month, year).editIdType(idType).editNationality(country)
				.editIdNumber(getRandName()).editIssuingEntity(getRandName()).clickNextPage();
		assertTrue(!isElementVisible(By.xpath(".//span[.='2']")));

		// step 3
		ClientOverviewPage clientOverview = register.editIBAN("DE12345678901234567980").clickCompletePage();

		clientOverview.simpleSearchByString(lastname);

		assertTrue(pageContainsStr(lastname));

		testEditClient(lastname, firstname);
	}

	// test case 40
	public void testEditClient(String oldLastname, String oldFirstname) throws AWTException, InterruptedException {

		String firstname = "Firstname2" + getRandName();
		String lastname = "Lastname2" + getRandName();
		String address = "address2" + getRandName();
		String telephone = getRandName();
		String taxId = getRandName();
		String issuingEntity = getRandName();
		String idNumber = getRandName();
		String taxAuthority = getRandName();
		String iban = "DE10101010101010101010";

		ClientOverviewPage clientOverview = new ClientOverviewPage(webDriver);
		clientOverview.simpleSearchByString(oldLastname);
		ClientRegisterPage register = clientOverview.goToClientDetailPageByName(oldLastname + ", " + oldFirstname)
				.goToEditPageByFieldFS();

		// step 1
		register.editTitleByValue("1").editName(firstname, lastname).editAddress(address)
				.editEmail(Settings.EMAIL_FOR_TESTING).editDate("Geburtsdatum", "1", "Jan", "1234")
				.editTelephone(telephone).editTaxId(taxId).editTaxAuthority(taxAuthority).clickNextPage();

		assertTrue(!isElementVisible(By.xpath(".//span[.='1']")));

		// step 2
		register.uploadFile("PDF");
		wait(5);

		waitForElementVisible(By.xpath("(.//*[@id='gwt-debug-SingleUploaderWithResetWidget-displayFilename'])[2]"), 3);

		String fileName = getTextByLocator(
				By.xpath("(.//*[@id='gwt-debug-SingleUploaderWithResetWidget-displayFilename'])[2]"));

		register.editIdNumber(idNumber).editIssuingEntity(issuingEntity).clickNextPage();

		assertTrue(!isElementVisible(By.xpath(".//span[.='2']")));

		// step 3
		clientOverview = register.editIBAN(iban).clickCompletePage();

		FSMenubarPage nav = new FSMenubarPage(webDriver);

		nav.goToClientOverviewPageFS().simpleSearchByString(lastname);

		register = clientOverview.goToClientDetailPageByName(firstname).goToEditPageByFieldFS();

		assertTrue(pageContainsStr(firstname));
		assertTrue(pageContainsStr(lastname));
		assertTrue(pageContainsStr(address));
		assertTrue(pageContainsStr(telephone));
		assertTrue(pageContainsStr(Settings.EMAIL_FOR_TESTING));
		register.clickNextPage();

		assertTrue(getTextByLocator(By.id("gwt-debug-ClientCreateTabPersonalIdentView-issuingAuthorityTextBox"))
				.equals(issuingEntity));
		assertTrue(
				getTextByLocator(By.id("gwt-debug-ClientCreateTabPersonalIdentView-numberTextBox")).equals(idNumber));
		register.clickNextPage();

		assertTrue(getTextByLocator(By.id("gwt-debug-PaymentMethodInnerWidget-ibanTextBox")).equals(iban));
		register.clickCompletePage();

		nav.goToClientOverviewPageFS().simpleSearchByString(lastname).goToClientDetailPageByName(firstname)
				.goToCRMPage();
		wait(5);
		assertTrue(pageContainsStr("ID"));
	}
}
