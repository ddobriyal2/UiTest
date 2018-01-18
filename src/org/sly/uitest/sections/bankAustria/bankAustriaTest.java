package org.sly.uitest.sections.bankAustria;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.MenuBarPage;
import org.sly.uitest.pageobjects.assetmanagement.ModelPortfoliosPage;
import org.sly.uitest.pageobjects.clientsandaccounts.DetailPage;
import org.sly.uitest.pageobjects.clientsandaccounts.HoldingsPage;
import org.sly.uitest.pageobjects.clientsandaccounts.ReportPage;
import org.sly.uitest.settings.Settings;

/**
 * This test is for the following test plan:
 * https://docs.google.com/spreadsheets
 * /d/1YMSjPcGCmkCKElscWX2Qqpy7Tw0zQedROCupmMVcDX4/edit#gid=0
 * 
 * @author Benny Leung
 * @Date Oct 20, 2016
 * @company Prive Financial
 */
public class bankAustriaTest extends AbstractTest {

	// test case #1
	@Test
	public void testBankAustriaAccountLogin() throws InterruptedException {
		String portalAddress = "http://192.168.1.107:8080/SlyAWS/ba.html";

		String accountAddress = "http://192.168.1.107:8080/SlyAWS/?locale=de_DE#ext_activation;ext_accountKey=21906870260";
		String xpath = "(.//a[.='ext_accountKey=21906870260'])[1]";
		webDriver.get(portalAddress);

		waitForElementVisible(By.xpath(xpath), 30);

		editElementAttributeByLocator(By.xpath(xpath), "href", accountAddress);

		clickElement(By.xpath(xpath));

		// waitForElementVisible(By.id("gwt-debug-SlyUser-localeListBox"), 30);
		//
		// assertTrue(isElementVisible(By.id("gwt-debug-SlyUser-localeListBox")));
		// wait(Settings.WAIT_SECONDS * 2);
		try {
			waitForElementVisible(By.id("gwt-debug-PortfolioAllocationView-reallocateBtn"), 30);
		} catch (TimeoutException e) {
			webDriver.get(portalAddress);
			handleAlert();
			waitForElementVisible(By.xpath(".//*[.='Bank Austria Partner Portal']"), 60);

			editElementAttributeByLocator(By.xpath(".//a[.='ext_accountKey=21906870260']"), "href", accountAddress);

			clickElement(By.xpath(".//a[.='ext_accountKey=21906870260']"));

		}

		webDriver.get(
				"http://192.168.1.107:8080/SlyAWS/?locale=en&viewMode=MATERIAL#portfolioOverviewHoldings;investorAccountKey=32692");

		handleAlert();
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), Settings.WAIT_SECONDS);

		// wait(2);
		handleAlert();

		MenuBarPage mbPage = new MenuBarPage(webDriver);
	}

	// test case #2
	@Test
	public void testSwitchPortfolioOfCustomer() throws InterruptedException {

		String portfolioName = "Portfolio 12 - 32692";

		this.testBankAustriaAccountLogin();

		clickElement(By.xpath(".//a[contains(@id,'gwt-debug-Breadcrumb-generalUserDetailsClientDetail')]"));

		this.init(By.id("gwt-debug-GeneralUserDetailViewTab-hyperlink-wrapper"));

		assertTrue(pageContainsStr("Details"));
		assertTrue(pageContainsStr("Additional Details"));

		DetailPage details = new DetailPage(webDriver);

		details.goToAccountOverviewPage().simpleSearchByString(portfolioName)
				.goToAccountHoldingsPageByName(portfolioName);

		assertTrue(isElementVisible(By.id("gwt-debug-PortfolioAllocationView-satelliteWrapperDiv")));

	}

	// test case #3
	@Test
	public void testGenerateConsolidatedReportForBankAustria() throws InterruptedException {

		ArrayList<String> accountNames = new ArrayList<String>();

		this.testBankAustriaAccountLogin();

		MenuBarPage mbPage = new MenuBarPage(webDriver);

		clickElement(By.xpath(".//a[contains(@id,'gwt-debug-Breadcrumb-generalUserDetailsClientDetail')]"));

		this.init(By.id("gwt-debug-GeneralUserDetailViewTab-hyperlink-wrapper"));

		DetailPage details = new DetailPage(webDriver);

		ReportPage report = details.goToReportPage().addReport();

		waitForElementVisible(By.id("gwt-debug-PairedListBoxSelector-sourceList"), 10);

		Select select = new Select(webDriver.findElement(By.id("gwt-debug-PairedListBoxSelector-sourceList")));

		for (WebElement elem : select.getOptions()) {

			accountNames.add(elem.getText().split(" - ")[1]);

			log(elem.getText().split(" - ")[1]);
		}

		report.editAddAllAccounts();

		report.clickGenerateReportButton();

		waitForElementVisible(By.id("gwt-debug-CustomDialog-okButton"), 20);

		clickOkButtonIfVisible();

		report.goToProcess();

		for (String account : accountNames) {

			By reportUrl = By.xpath(".//table[contains(@title,'2016')]//div[contains(text(),'" + account
					+ "')]//ancestor::tr//following-sibling::tr[2]//a[@name='reportUrl' and .='Report']");

			waitForElementVisible(reportUrl, 120);

			clickElement(reportUrl);

			checkDownloadedFile(account);
			scrollToTop();
		}
	}

	// test case #4
	@Test
	public void testCreateModelPortfolioForBankAustria() throws InterruptedException {

		this.testBankAustriaAccountLogin();
		String name1 = "test" + getRandName();
		String description1 = "description" + getRandName();
		String name2 = "test" + getRandName();
		String description2 = "description" + getRandName();

		createModelPortfolio(name1, description1);

		waitForElementVisible(By.id("gwt-debug-PortfolioAllocationView-satelliteWrapperDiv"), 30);

		createModelPortfolio(name2, description2);

		waitForElementVisible(By.id("gwt-debug-PortfolioAllocationView-satelliteWrapperDiv"), 60);

		ModelPortfoliosPage model = new ModelPortfoliosPage(webDriver);

		model.goToModelPortfoliosPage().clickManageModelPortfolios().deleteModelPortfolioByName(name1)
				.deleteModelPortfolioByName(name2);
	}

	@Test
	public void testScreensOfBankAustria() throws InterruptedException {

		this.testBankAustriaAccountLogin();

		HoldingsPage holding = new HoldingsPage(webDriver);

		holding.clickReallocateButton();

		assertTrue(pageContainsStr("Current Weight"));

		MenuBarPage main = new MenuBarPage(webDriver);

		main.goToAlertsPageFromManage();
		assertTrue(pageContainsStr("Triggered Alerts"));

		main.goToInvestmentsPage();
		assertTrue(pageContainsStr("Investments"));

		// clickElement(By.id("gwt-debug-ManagerListItem-fundDocuments"));

		// checkDownloadedFile("CA");
	}

	public void createModelPortfolio(String name, String description) throws InterruptedException {

		MenuBarPage mbPage = new MenuBarPage(webDriver);

		this.init(By.xpath(".//*[.='Asset Management']"));

		ModelPortfoliosPage model = mbPage.goToModelPortfoliosPage().clickManageModelPortfolios();

		model.clickCreatePortfolioButton();

		waitForElementVisible(By.id("gwt-debug-ModelPortfolioEditView-portfolioStartValue"), 10);

		assertTrue(getTextByLocator(By.id("gwt-debug-ModelPortfolioEditView-portfolioStartValue")).equals("25,000.00"));

		model.editModelPortfolioName(name).editModelPortfolioDescription(description).clickSaveButton();

	}

	public void init(By target) {
		try {
			waitForElementVisible(target, 30);
		} catch (TimeoutException e) {
			for (int i = 0; i < Settings.ATTEMPT_LOOPING_NUMBER; i++) {
				if (!isElementVisible(By.id("gwt-debug-GeneralUserDetailViewTab-hyperlink-wrapper"))) {
					webDriver.get(
							"http://192.168.1.107:8080/SlyAWS/?locale=en&viewMode=MATERIAL#generalUserDetailsClientDetail;userKey=25872;detailType=1");
					waitForElementVisible(target, 30);
				}
			}

		}
	}
}
