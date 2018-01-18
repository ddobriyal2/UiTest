package org.sly.uitest.sections.salesprocess;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.framework.DevelopmentTest;
import org.sly.uitest.pageobjects.clientsandaccounts.CRMPage;
import org.sly.uitest.pageobjects.crm.DocumentsPage;
import org.sly.uitest.pageobjects.salesprocess.ClientRegisterPage;
import org.sly.uitest.pageobjects.salesprocess.FSLoginPage;
import org.sly.uitest.pageobjects.salesprocess.FSMenubarPage;
import org.sly.uitest.pageobjects.salesprocess.SalesWizardPage;
import org.sly.uitest.settings.Settings;

/**
 * This is the test for sales wizard. Check the URL below for test plan:
 * https://
 * docs.google.com/spreadsheets/d/1QVu2GH0dvYKWa1dDoz5MSeGvHbOs_k3uAE0qlPDdCkA
 * /edit#gid=0
 * 
 * @author by Benny Leung
 * @date Oct 4,2016
 * @company Prive Financial
 *
 */
public class SalesWizardTest extends AbstractTest {

	// test case 41
	@Ignore
	@Category(DevelopmentTest.class)
	public void testURLinProductPage() throws InterruptedException, MalformedURLException, IOException {
		FSLoginPage main = new FSLoginPage(webDriver);

		main.loginAs(Settings.FINANCESALES_ADVISOR_USERNAME, Settings.FINANCESALES_ADVISOR_PASSWORD)
				.goToProductOverviewPageFS().goToAdamasPage();

		int size = getSizeOfElements(By.xpath(".//ul/li"));

		for (int i = 1; i <= size; i++) {
			Integer index = i;
			String urlToCheck = getAttributeStringByLocator(By.xpath(".//ul/li[" + index.toString() + "]/a"), "href");
			log(urlToCheck);
			checkUrlValid(urlToCheck);
		}

	}

	// Test case 42
	@Ignore
	@Category(DevelopmentTest.class)
	public void testRetrievingClientListForSalesProcess() throws InterruptedException {

		ArrayList<String> clientList = new ArrayList<String>();
		clientList.add(" ");
		FSLoginPage main = new FSLoginPage(webDriver);

		main.loginAs(Settings.FINANCESALES_ADVISOR_USERNAME, Settings.FINANCESALES_ADVISOR_PASSWORD)
				.goToClientOverviewPageFS();

		int page = getPagesOfElements(By.id("gwt-debug-InvestorOverviewView-linkPersonName"));
		log(String.valueOf(page));
		for (int i = 0; i < page; i++) {
			waitForElementVisible(By.id("gwt-debug-InvestorOverviewView-linkPersonName"), 10);
			int size = getSizeOfElements(By.id("gwt-debug-InvestorOverviewView-linkPersonName"));

			for (int j = 0; j < size; j++) {
				Integer tmp = j + 1;
				String clientName = getTextByLocator(By.xpath(
						"(//*[@id='gwt-debug-InvestorOverviewView-linkPersonName'])" + "[" + tmp.toString() + "]"));

				String clientToBeAdded = clientName.split(", ")[1] + " " + clientName.split(", ")[0];

				clientList.add(clientToBeAdded);
				log(String.valueOf(clientList.size()));
				log(clientToBeAdded);
			}

			WebElement nextPageImg = webDriver.findElement(By.id("gwt-debug-PagerToolWidget-nextPageImg"));

			nextPageImg.click();
		}

		FSMenubarPage nav = new FSMenubarPage(webDriver);
		nav.goToProductOverviewPageFS().goToAdamasPage().clickAddSalesButton();

		waitForElementVisible(By.id("gwt-debug-AddToProductSalesControllerView-clientListBox"), 10);
		WebElement dropdown = webDriver.findElement(By.id("gwt-debug-AddToProductSalesControllerView-clientListBox"));

		Select select = new Select(dropdown);
		List<WebElement> optionList = select.getOptions();
		ArrayList<String> list1 = new ArrayList<String>();

		for (WebElement elem : optionList) {
			list1.add(elem.getText());
		}
		log(String.valueOf(clientList.size()));
		log(String.valueOf(list1.size()));
		assertTrue(Integer.valueOf(clientList.size()).equals(list1.size()));

	}

	// Test case 43
	@Ignore
	@Category(DevelopmentTest.class)
	public void testRetrieveClientInformationForSalesProcess() throws InterruptedException {

		String detailField = "Weitere Informationen";

		// data in client information step 1
		String title = null;
		String firstname = null;
		String lastname = null;
		String address = null;
		String residence = null;
		String telephone = null;
		String email = null;
		String birthYear = null;
		String taxID = null;
		String taxAuthority = null;

		// data in client information step 2
		String typeOfId = null;
		String nationality = null;
		String id = null;
		String issuingAuthority = null;

		// data in client information step 3
		String iban = null;

		FSLoginPage main = new FSLoginPage(webDriver);

		ClientRegisterPage register = main
				.loginAs(Settings.FINANCESALES_ADMIN_USERNAME, Settings.FINANCESALES_ADMIN_PASSWORD)
				.goToClientOverviewPageFS().simpleSearchByString("Frenchadclient2, Frenchadclient2")
				.goToClientDetailPageByName("Frenchadclient2, Frenchadclient2").goToEditPageByFieldFS();

		// Retrieve information from step 1
		title = getSelectedTextFromDropDown(By.id("gwt-debug-ClientCreateTabOverviewView-titleListBox"));
		firstname = getTextByLocator(By.id("gwt-debug-ClientCreateTabOverviewView-nameTextBox"));
		lastname = getTextByLocator(By.id("gwt-debug-ClientCreateTabOverviewView-surnameTextBox"));
		address = getTextByLocator(By.id("gwt-debug-EditUserAddressWidget-address1TextBox"));
		residence = getSelectedTextFromDropDown(By.id("gwt-debug-ClientCreateTabOverviewView-taxResidenceListBox"));
		telephone = getTextByLocator(By.id("gwt-debug-EditUserPhoneWidget-numberTextBox"));
		email = getTextByLocator(By.id("gwt-debug-EditUserEmailWidget-emailTextBox"));
		birthYear = getTextByLocator(By.id("gwt-debug-CompositeDatePicker-yearTextBox"));
		taxID = getTextByLocator(By.id("gwt-debug-ClientCreateTabOverviewView-vatTaxIdTextBox"));
		taxAuthority = getTextByLocator(By.id("gwt-debug-ClientCreateTabOverviewView-taxAuthorityTextBox"));

		register.clickNextPage();

		// Retrieve information from step 2
		typeOfId = getSelectedTextFromDropDown(By.xpath(".//*[contains(@id,'idTypeListBox')]"));
		nationality = getSelectedTextFromDropDown(By.xpath(".//*[contains(@id,'nationalityListBox')]"));
		id = getTextByLocator(By.id("gwt-debug-ClientCreateTabPersonalIdentView-numberTextBox"));
		issuingAuthority = getTextByLocator(
				By.id("gwt-debug-ClientCreateTabPersonalIdentView-issuingAuthorityTextBox"));

		register.clickNextPage();

		// Retrieve information from step 3
		iban = getTextByLocator(By.id("gwt-debug-PaymentMethodInnerWidget-ibanTextBox"));

		register.clickCloseDialogBtn();

		FSMenubarPage menu = new FSMenubarPage(webDriver);

		SalesWizardPage salesWizard = menu.goToProductOverviewPageFS().goToAdamasPage().clickAddSalesButton()
				.startAdamasSalesWizard("Frenchadclient2 Frenchadclient2");

		// assert client information in step 1
		assertTrue(getSelectedTextFromDropDown(By.id("gwt-debug-MiniSalesProcessEditTabOverviewView-titleListBox"))
				.equals(title));
		assertTrue(
				getTextByLocator(By.id("gwt-debug-MiniSalesProcessEditTabOverviewView-nameTextBox")).equals(firstname));
		assertTrue(getTextByLocator(By.id("gwt-debug-MiniSalesProcessEditTabOverviewView-surnameTextBox"))
				.equals(lastname));
		assertTrue(getTextByLocator(By.id("gwt-debug-EditUserAddressWidget-address1TextBox")).equals(address));
		assertTrue(getTextByLocator(By.id("gwt-debug-EditUserPhoneWidget-numberTextBox")).equals(telephone));
		assertTrue(getTextByLocator(By.id("gwt-debug-EditUserEmailWidget-emailTextBox")).equals(email));
		assertTrue(getTextByLocator(By.id("gwt-debug-CompositeDatePicker-yearTextBox")).equals(birthYear));
		assertTrue(
				getSelectedTextFromDropDown(By.id("gwt-debug-MiniSalesProcessEditTabOverviewView-taxResidenceListBox"))
						.equals(residence));
		assertTrue(
				getTextByLocator(By.id("gwt-debug-MiniSalesProcessEditTabOverviewView-vatTaxIdTextBox")).equals(taxID));
		assertTrue(getTextByLocator(By.id("gwt-debug-MiniSalesProcessEditTabOverviewView-taxAuthorityTextBox"))
				.equals(taxAuthority));

		salesWizard.clickNextPage();

		assertTrue(getSelectedTextFromDropDown(
				By.id("gwt-debug-MiniSalesProcessEditTabPersonalIdentificationView-idTypeListBox")).equals(typeOfId));
		assertTrue(getSelectedTextFromDropDown(
				By.id("gwt-debug-MiniSalesProcessEditTabPersonalIdentificationView-nationalityListBox"))
						.equals(nationality));
		assertTrue(getTextByLocator(By.id("gwt-debug-MiniSalesProcessEditTabPersonalIdentificationView-numberTextBox"))
				.equals(id));
		assertTrue(getTextByLocator(
				By.id("gwt-debug-MiniSalesProcessEditTabPersonalIdentificationView-issuingAuthorityTextBox"))
						.equals(issuingAuthority));

		salesWizard.clickNextPage();

		assertTrue(getTextByLocator(By.id("gwt-debug-PaymentMethodInnerWidget-ibanTextBox")).equals(iban));

	}

	// test case 44
	@Ignore
	@Category(DevelopmentTest.class)
	public void testFinanceSalesCalculation() throws InterruptedException {

		FSLoginPage main = new FSLoginPage(webDriver);

		String client = "Frenchadclient Frenchadclient";

		SalesWizardPage salesWizard = main
				.loginAs(Settings.FINANCESALES_ADVISOR_USERNAME, Settings.FINANCESALES_ADVISOR_PASSWORD)
				.goToProductOverviewPageFS().goToAdamasPage().clickAddSalesButton().startAdamasSalesWizard(client);

		waitForElementVisible(By.id("gwt-debug-MiniSalesProcessEditTabOverviewView-titleListBox"), 60);

		salesWizard.clickNextPage().clickNextPage().clickNextPage();
		// step 1
		salesWizard.editShares("1000").clickNextPage();

		clickOkButtonIfVisible();
		wait(3);
		webDriver.switchTo().frame(webDriver.findElement(By.id("gwt-debug-OrbeonView-content")));

		clickElement(By.id("o0fr-error-dialog≡xf-736≡≡c"));

		assertValidationOfElement(false, "Art der Mitgliedschaft");

		// step 2
		salesWizard.editMembershipType("1").clickNextPage();
		clickOkButtonIfVisible();
		assertValidationOfElement(true, "Art der Mitgliedschaft");

		// step 3
		salesWizard.editShares("100.7").clickNextPage();
		clickOkButtonIfVisible();
		assertValidationOfElement(false, "Anzahl der Anteile");

		// step 4
		salesWizard.editShares("*").clickNextPage();
		clickOkButtonIfVisible();
		assertValidationOfElement(false, "Anzahl der Anteile");

		// step 5
		salesWizard.editShares("1*100").clickNextPage();
		clickOkButtonIfVisible();
		assertValidationOfElement(false, "Anzahl der Anteile");

		// step 6
		salesWizard.editShares("hundred").clickNextPage();
		clickOkButtonIfVisible();
		assertValidationOfElement(false, "Anzahl der Anteile");

		// step 7
		salesWizard.editShares("9").clickNextPage();
		clickOkButtonIfVisible();
		assertCalculation("10.350,00");
		assertValidationOfElement(false, "Anzahl der Anteile");

		// step 8
		salesWizard.editShares("10").clickNextPage();
		clickOkButtonIfVisible();
		assertCalculation("11.500,00");
		assertValidationOfElement(true, "Anzahl der Anteile");

		// step 9
		salesWizard.editShares("11").clickNextPage();
		clickOkButtonIfVisible();
		assertCalculation("12.650,00");
		assertValidationOfElement(true, "Anzahl der Anteile");

		// step 10
		salesWizard.editMembershipType("2").clickNextPage();
		clickOkButtonIfVisible();
		assertValidationOfElement(true, "Art der Mitgliedschaft");

		// step 11
		salesWizard.editShares("100.7").clickNextPage();
		clickOkButtonIfVisible();
		assertValidationOfElement(false, "Anzahl der Anteile");

		// step 11
		salesWizard.editShares("*").clickNextPage();
		clickOkButtonIfVisible();
		assertValidationOfElement(false, "Anzahl der Anteile");

		// step 11
		salesWizard.editShares("1*100").clickNextPage();
		clickOkButtonIfVisible();
		assertValidationOfElement(false, "Anzahl der Anteile");

		// step 12
		salesWizard.editShares("4").clickNextPage();
		clickOkButtonIfVisible();
		assertCalculation("4.200,00");
		assertValidationOfElement(false, "Anzahl der Anteile");

		// step 13
		salesWizard.editShares("5").clickNextPage();
		clickOkButtonIfVisible();
		assertCalculation("5.250,00");
		assertValidationOfElement(true, "Anzahl der Anteile");

		// step 14
		salesWizard.editShares("6").clickNextPage();
		clickOkButtonIfVisible();
		assertCalculation("6.300,00");
		assertValidationOfElement(true, "Anzahl der Anteile");
	}

	// test case 45
	@Ignore
	@Category(DevelopmentTest.class)
	public void testPaybackOption() throws InterruptedException {

		FSLoginPage main = new FSLoginPage(webDriver);

		String client = "Frenchadclient Frenchadclient";

		SalesWizardPage salesWizard = main
				.loginAs(Settings.FINANCESALES_ADVISOR_USERNAME, Settings.FINANCESALES_ADVISOR_PASSWORD)
				.goToProductOverviewPageFS().goToAdamasPage().clickAddSalesButton().startAdamasSalesWizard(client);

		waitForElementVisible(By.id("gwt-debug-MiniSalesProcessEditTabOverviewView-titleListBox"), 60);

		salesWizard.clickNextPage().clickNextPage().clickNextPage();

		// step 1
		assertTrue(!isElementVisible(By.id("o0o0section-1-control≡xf-386≡control-19-control≡≡e0")));
		assertTrue(!isElementVisible(By.id("o0o0section-1-control≡xf-386≡control-19-control≡≡e1")));
		assertTrue(!isElementVisible(By.id("o0o0section-1-control≡xf-386≡control-19-control≡≡e2")));

		// step 2 & 3
		salesWizard.editPaybackOption(true, "1");
		salesWizard.editPaybackOption(true, "2");
		salesWizard.editPaybackOption(true, "3");

		assertTrue(isElementVisible(By.id("o0o0section-1-control≡xf-386≡control-19-control≡≡e0")));
		assertTrue(isElementVisible(By.id("o0o0section-1-control≡xf-386≡control-19-control≡≡e1")));
		assertTrue(isElementVisible(By.id("o0o0section-1-control≡xf-386≡control-19-control≡≡e2")));
	}

	// test case 46
	@Ignore
	@Category(DevelopmentTest.class)
	public void testSubscriptionDate() throws InterruptedException {

		FSLoginPage main = new FSLoginPage(webDriver);

		String client = "Frenchadclient Frenchadclient";

		SalesWizardPage salesWizard = main
				.loginAs(Settings.FINANCESALES_ADVISOR_USERNAME, Settings.FINANCESALES_ADVISOR_PASSWORD)
				.goToProductOverviewPageFS().goToAdamasPage().clickAddSalesButton().startAdamasSalesWizard(client);

		waitForElementVisible(By.id("gwt-debug-MiniSalesProcessEditTabOverviewView-titleListBox"), 60);

		salesWizard.clickNextPage().clickNextPage().clickNextPage();

		// step 1
		salesWizard.editSalesDateManually("2/30/2016").clickNextPage();
		clickOkButtonIfVisible();
		wait(2);
		webDriver.switchTo().frame(webDriver.findElement(By.id("gwt-debug-OrbeonView-content")));
		clickElementByKeyboard(By.id("o0section-1-control≡xf-386≡control-18-control≡xforms-input-1"));
		assertValidationOfElement(false, "Datum des Abschlusses");

		// step 2
		salesWizard.editSalesDateManually("9/31/2016").clickNextPage();
		clickOkButtonIfVisible();
		assertValidationOfElement(false, "Datum des Abschlusses");

		// step 3
		salesWizard.editSalesDateManually("13/30/2016").clickNextPage();
		clickOkButtonIfVisible();
		assertValidationOfElement(false, "Datum des Abschlusses");

		// step 4
		salesWizard.editSalesDate("22").clickNextPage();
		clickOkButtonIfVisible();
		assertValidationOfElement(true, "Datum des Abschlusses");

	}

	// tese case 47
	@Ignore
	@Category(DevelopmentTest.class)
	public void testLegalRequirements() throws InterruptedException {

		FSLoginPage main = new FSLoginPage(webDriver);

		String client = "Frenchadclient Frenchadclient";

		SalesWizardPage salesWizard = main
				.loginAs(Settings.FINANCESALES_ADVISOR_USERNAME, Settings.FINANCESALES_ADVISOR_PASSWORD)
				.goToProductOverviewPageFS().goToAdamasPage().clickAddSalesButton().startAdamasSalesWizard(client);

		waitForElementVisible(By.id("gwt-debug-MiniSalesProcessEditTabOverviewView-titleListBox"), 60);

		salesWizard.clickNextPage().clickNextPage().clickNextPage();
		wait(5);

		if (isElementVisible(By.id("gwt-debug-OrbeonView-content"))) {
			webDriver.switchTo().frame(webDriver.findElement(By.id("gwt-debug-OrbeonView-content")));

		}
		wait(1);
		WebElement elem = webDriver
				.findElement(By.xpath(".//span[@id='o0section-1-control≡xf-386≡control-21-control']//input"));
		// step 1
		assertTrue(elem.isSelected());

		// step 3
		salesWizard.editMembershipType("1").editShares("100").editLegalRequirementCheckbox(0).clickNextPage();
		wait(2);
		clickOkButtonIfVisible();
		wait(2);
		webDriver.switchTo().frame(webDriver.findElement(By.id("gwt-debug-OrbeonView-content")));
		clickElementByKeyboard(By.id("o0section-1-control≡xf-386≡control-17-control≡xforms-input-1"));

		assertValidationOfElement(false, "Bestätigung des Online-Abschlusses");

		// step 4
		salesWizard.editLegalRequirementCheckbox(1).clickNextPage();
		clickOkButtonIfVisible();
		assertValidationOfElement(false, "Bestätigung des Online-Abschlusses");

		// step 5
		salesWizard.editLegalRequirementCheckbox(2).clickNextPage();
		clickOkButtonIfVisible();
		assertValidationOfElement(false, "Bestätigung des Online-Abschlusses");

		// step 6
		salesWizard.editLegalRequirementCheckbox(3).clickNextPage();
		clickOkButtonIfVisible();
		assertValidationOfElement(false, "Bestätigung des Online-Abschlusses");

		// step 7
		salesWizard.editLegalRequirementCheckbox(4).clickNextPage();
		clickOkButtonIfVisible();
		assertValidationOfElement(true, "Bestätigung des Online-Abschlusses");

	}

	// Test case 49
	@Ignore
	@Category(DevelopmentTest.class)
	public void testTarif() throws InterruptedException {

		FSLoginPage main = new FSLoginPage(webDriver);

		String client = "Frenchadclient Frenchadclient";

		SalesWizardPage salesWizard = main
				.loginAs(Settings.FINANCESALES_ADVISOR_USERNAME, Settings.FINANCESALES_ADVISOR_PASSWORD)
				.goToProductOverviewPageFS().goToAdamasPage().clickAddSalesButton().startAdamasSalesWizard(client);

		waitForElementVisible(By.id("gwt-debug-MiniSalesProcessEditTabOverviewView-titleListBox"), 60);

		salesWizard.clickNextPage().clickNextPage().clickNextPage();

		wait(5);

		salesWizard.editTarif("A").editTarif("B");
	}

	// Test case 49 and 50
	@Ignore
	@Category(DevelopmentTest.class)
	public void testCorrectInformationAndPassSalesWizard() throws InterruptedException {

		FSLoginPage main = new FSLoginPage(webDriver);

		String client = "Frenchadclient Frenchadclient";
		String share = "10";
		String date = "9/11/2016";
		String city = "NYC";
		String member = null;
		SalesWizardPage salesWizard = main
				.loginAs(Settings.FINANCESALES_ADVISOR_USERNAME, Settings.FINANCESALES_ADVISOR_PASSWORD)
				.goToProductOverviewPageFS().goToAdamasPage().clickAddSalesButton().startAdamasSalesWizard(client);

		waitForElementVisible(By.id("gwt-debug-MiniSalesProcessEditTabOverviewView-titleListBox"), 60);

		salesWizard.clickNextPage().clickNextPage().clickNextPage();

		wait(5);

		if (isElementVisible(By.id("gwt-debug-OrbeonView-content"))) {
			webDriver.switchTo().frame(webDriver.findElement(By.id("gwt-debug-OrbeonView-content")));

		}

		// test case 49

		Select membershipdropdown = new Select(
				webDriver.findElement(By.id("o0section-1-control≡xf-385≡membershipTyp-control≡select1≡≡c")));
		member = membershipdropdown.getOptions().get(1).getText();
		salesWizard.editMembershipType("1").editSalesLocation(city).editShares(share).editSalesDateManually(date)
				.editPaybackOption(true, "1").editLegalRequirementCheckbox(0).editLegalRequirementCheckbox(1)
				.editLegalRequirementCheckbox(2).editLegalRequirementCheckbox(3).editLegalRequirementCheckbox(4)
				.editTarif("A").clickPreviousPage().clickNextPage();

		wait(5);

		if (isElementVisible(By.id("gwt-debug-OrbeonView-content"))) {
			webDriver.switchTo().frame(webDriver.findElement(By.id("gwt-debug-OrbeonView-content")));

		}

		// assertion for member,share,date and city
		assertTrue(membershipdropdown.getFirstSelectedOption().getText().equals(member));
		assertTrue(getTextByLocator(By.id("o0section-1-control≡xf-385≡amountShares-control≡xforms-input-1"))
				.equals(share));
		assertTrue(
				getTextByLocator(By.id("o0section-1-control≡xf-386≡control-18-control≡xforms-input-1")).equals(date));
		assertTrue(
				getTextByLocator(By.id("o0section-1-control≡xf-386≡control-17-control≡xforms-input-1")).equals(city));

		// assertion for payback option
		assertTrue(webDriver.findElement(By.id("o0section-1-control≡xf-385≡payBackOption-control≡≡e0")).isSelected());
		assertTrue(webDriver.findElement(By.id("o0o0section-1-control≡xf-386≡control-19-control≡≡e0")).isSelected());

		// assertion for legal requirement checkbox
		assertTrue(webDriver.findElement(By.id("o0section-1-control≡xf-386≡control-16-control≡≡e0")).isSelected());
		assertTrue(webDriver.findElement(By.id("o0section-1-control≡xf-386≡control-16-control≡≡e1")).isSelected());
		assertTrue(webDriver.findElement(By.id("o0section-1-control≡xf-386≡control-16-control≡≡e2")).isSelected());
		assertTrue(webDriver.findElement(By.id("o0section-1-control≡xf-386≡control-16-control≡≡e3")).isSelected());
		assertTrue(webDriver.findElement(By.id("o0section-1-control≡xf-386≡control-16-control≡≡e4")).isSelected());

		// assertion for tarif a
		assertTrue(webDriver.findElement(By.id("o0section-1-control≡xf-386≡control-15-control≡≡e0")).isSelected());

		// test case 50
		salesWizard.clickNextPage();
		try {
			waitForElementVisible(By.id("gwt-debug-MiniSalesProcessEditTabSummaryView-contentPanel"), 120);
		} catch (TimeoutException e) {
			if (!isElementVisible(By.id("gwt-debug-MiniSalesProcessEditTabSummaryView-contentPanel"))) {
				clickOkButtonIfVisible();
				salesWizard.clickNextPage();
				waitForElementVisible(By.id("gwt-debug-MiniSalesProcessEditTabSummaryView-contentPanel"), 120);
			}
		}

		salesWizard.clickCompletePage();

	}

	@Ignore
	@Category(DevelopmentTest.class)
	public void testCompleteAdamasSalesProcess() throws InterruptedException {
		this.passAdamasSalesWizard("Frenchadclient Frenchadclient", "1", "10", "9/10/2016", false, "1", "A");
	}

	@Ignore
	@Category(DevelopmentTest.class)
	public void testAdvisorDocument() throws InterruptedException {
		FSLoginPage main = new FSLoginPage(webDriver);

		String advisor = "TestGerman2, TestGerman2";
		String documentName1 = "Company Registration";
		String documentName2 = "Company Signature";
		String documentName3 = "Corporate Identification";
		String documentName4 = "Identification personnel";
		String documentName5 = "Licencing Austria FA";

		main.loginAs(Settings.FINANCESALES_ADMIN_USERNAME, Settings.FINANCESALES_ADMIN_PASSWORD).goToEmployeePage()
				.simpleSearchEmployee(advisor).clickEmployee(advisor).goToCRMPage();

		assertTrue(pageContainsStr(documentName1));
		assertTrue(pageContainsStr(documentName2));
		assertTrue(pageContainsStr(documentName3));
		assertTrue(pageContainsStr(documentName4));
		assertTrue(pageContainsStr(documentName5));

		checkAdvisorDocument(documentName1);
		checkAdvisorDocument(documentName2);
		checkAdvisorDocument(documentName3);
		checkAdvisorDocument(documentName4);
		checkAdvisorDocument(documentName5);

	}

	/**
	 * To create a new sale quickly
	 * 
	 * @param client
	 *            client name
	 * @param membership
	 *            "1" for blue, "2" for green
	 * @param share
	 *            minimum share: blue:10, green:5
	 * @param date
	 *            subscription date (m/dd/yyyy)
	 * @param paybackoption
	 *            whether to turn on payback option
	 * @param numberOfPaybackOption
	 *            number of payback option, 1 - 3
	 * @param tarif
	 *            name of tarif, A / B
	 * @throws InterruptedException
	 */
	public void passAdamasSalesWizard(String client, String membership, String share, String date,
			boolean paybackoption, String numberOfPaybackOption, String tarif) throws InterruptedException {
		FSLoginPage main = new FSLoginPage(webDriver);

		String city = "NYC";

		SalesWizardPage salesWizard = main
				.loginAs(Settings.FINANCESALES_ADVISOR_USERNAME, Settings.FINANCESALES_ADVISOR_PASSWORD)
				.goToProductOverviewPageFS().goToAdamasPage().clickAddSalesButton().startAdamasSalesWizard(client);

		waitForElementVisible(By.id("gwt-debug-MiniSalesProcessEditTabOverviewView-titleListBox"), 60);

		salesWizard.clickNextPage().clickNextPage().clickNextPage();

		wait(5);

		if (isElementVisible(By.id("gwt-debug-OrbeonView-content"))) {
			webDriver.switchTo().frame(webDriver.findElement(By.id("gwt-debug-OrbeonView-content")));

		}

		salesWizard.editMembershipType(membership).editSalesLocation(city).editShares(share).editSalesDateManually(date)
				.editPaybackOption(paybackoption, numberOfPaybackOption).editLegalRequirementCheckbox(0)
				.editLegalRequirementCheckbox(1).editLegalRequirementCheckbox(2).editLegalRequirementCheckbox(3)
				.editLegalRequirementCheckbox(4).editTarif(tarif).clickNextPage().clickCompletePage().checkLogoutFS();
	}

	public void assertValidationOfElement(boolean valid, String message) throws InterruptedException {
		try {
			this.waitForElementVisible(By.id("gwt-debug-OrbeonView-content"), Settings.WAIT_SECONDS);
		} catch (TimeoutException e) {

		}
		if (isElementVisible(By.id("gwt-debug-OrbeonView-content"))) {

			webDriver.switchTo().frame(webDriver.findElement(By.id("gwt-debug-OrbeonView-content")));

		}
		if (valid) {
			assertTrue(!isElementVisible(By.xpath(".//a[.='" + message + "']")));
		} else {
			assertTrue(isElementVisible(By.xpath(".//a[.='" + message + "']")));
		}

	}

	public void assertCalculation(String cal) {

		webDriver.switchTo().frame(webDriver.findElement(By.id("gwt-debug-OrbeonView-content")));

		deleteElementAttributeByLocator(By.id("o0section-1-control≡xf-385≡control-22-control≡xforms-input-1"),
				"disabled");
		// WebElement calculationResult = webDriver
		// .findElement(By
		// .id("o0section-1-control≡xf-385≡control-22-control≡xforms-input-1"));
		// ((JavascriptExecutor) webDriver).executeScript(
		// "arguments[0].removeAttribute('disabled')", calculationResult);

		String calculation = getTextByLocator(By.id("o0section-1-control≡xf-385≡control-22-control≡xforms-input-1"));

		assertTrue(calculation.equals(cal));
	}

	public void checkAdvisorDocument(String documentName) throws InterruptedException {

		String downloadDocument = "";
		CRMPage crm = new CRMPage(webDriver);

		DocumentsPage document = crm.editDocumentByName(documentName);

		downloadDocument = getTextByLocator(By.id("gwt-debug-MultiUploaderWidget-anchorDoc"));

		document.downloadDocument(downloadDocument).clickBackButton();

		checkDownloadedFile(downloadDocument);
	}
}
