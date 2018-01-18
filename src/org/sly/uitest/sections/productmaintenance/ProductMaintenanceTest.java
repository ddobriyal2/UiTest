package org.sly.uitest.sections.productmaintenance;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.AdvancedSearchPage;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.abstractpage.MenuBarPage;
import org.sly.uitest.pageobjects.investments.InvestmentsPage;
import org.sly.uitest.pageobjects.operations.ProductMaintenancePage;
import org.sly.uitest.pageobjects.planning.IllustrationPage;
import org.sly.uitest.settings.Settings;

/**
 * This is a test for Product Manitenance. For details, <a href=
 * "https://docs.google.com/spreadsheets/d/1uUOWR42rI6izI5TzE2znBcSRzHkYmUjZru9Zhy8J_EQ/edit#gid=0"
 * >please check here</a>
 * 
 * @author Benny Leung
 * @date 24,Aug,2016
 * @company Prive Financial
 *
 */
public class ProductMaintenanceTest extends AbstractTest {

	@Test
	public void testProductMaintenance() throws Exception {
		LoginPage main = new LoginPage(webDriver);

		main.loginAs(Settings.Citi_P360_USER, Settings.Citi_P360_PASSWORD);

		testHandlePlatforms();
		String investmentName = testAddProduct();
		testEditProduct(investmentName);
		testSearching(investmentName);
		testDeleteProduct(investmentName);
		testDownloadSpreadSheet();
	}

	// Test case #1
	public void testHandlePlatforms() throws InterruptedException {
		log("testHandlePlatforms: begin");

		MenuBarPage menuBar = new MenuBarPage(webDriver);
		ProductMaintenancePage productMaintenance = menuBar.goToProductMaintenancePage();

		WebElement elem = webDriver.findElement(By.id("gwt-debug-ProductMaintenanceEditView-curExePlatformsListBox"));

		Select select = new Select(elem);
		List<WebElement> elementList = select.getOptions();
		ArrayList<String> optionList = new ArrayList<String>();

		for (WebElement element : elementList.subList(1, elementList.size())) {
			optionList.add(element.getText());
		}
		for (String option : optionList.subList(1, optionList.size())) {
			productMaintenance.loadProductUniverse(option);
		}

		log("testHandlePlatforms: finish");
	}

	// Test case #2
	public String testAddProduct() throws Exception {
		log("testAddProduct: begin");
		String universeName = "Citi P360 - Korea";

		MenuBarPage menuBar = new MenuBarPage(webDriver);
		ProductMaintenancePage productMaintenance = menuBar.goToProductMaintenancePage();

		InvestmentsPage investments = productMaintenance.loadProductUniverse(universeName).clickAddProductButton();

		waitForElementVisible(By.id("gwt-debug-ManagerListItem-strategyName"), 120);

		String investmentName = getTextByLocator(By.id("gwt-debug-ManagerListItem-strategyName"));

		investments.selectInvestmentByName(investmentName).clickAddToPortfolioButton();

		clickElement(By.xpath("(.//*[@id='gwt-debug-CustomDialog-okButton'])[1]"));
		clickElement(By.xpath("(.//*[@id='gwt-debug-CustomDialog-okButton'])[2]"));

		clickOkButtonIfVisible();
		productMaintenance.simpleSearch("Name:\"" + investmentName + "\"");
		assertTrue(pageContainsStr(investmentName));

		log("testAddProduct: finish");
		return investmentName;
	}

	// Test case #3
	public void testEditProduct(String investmentName) throws InterruptedException {
		log("testEditProduct: begin");
		String code = "ABC4";
		String riskLevel = "3";
		String status = "Active";
		String allowed = "Y";
		String universeName = "Citi P360 - Korea";

		MenuBarPage menuBar = new MenuBarPage(webDriver);
		ProductMaintenancePage productMaintenance = menuBar.goToProductMaintenancePage();

		productMaintenance.loadProductUniverse(universeName).simpleSearch("Name:\"" + investmentName + "\"")
				.editProductByName(investmentName);

		// risk level = blank
		this.editProduct(code, "", status, allowed);
		assertTrue(pageContainsStr("Please enter a valid number"));
		this.handleOkButton();

		// status = blank
		this.editProduct(code, riskLevel, "", allowed);
		assertTrue(pageContainsStr("Please select a status"));
		this.handleOkButton();

		// allowed online = blank
		this.editProduct(code, riskLevel, status, "");
		assertTrue(pageContainsStr("Please select a value for Allowed Online"));
		this.handleOkButton();

		// all filled
		this.editProduct(code, riskLevel, status, allowed);
		clickOkButtonIfVisible();

		productMaintenance.simpleSearch("Name:\"" + investmentName + "\"");
		assertTrue(pageContainsStr(code));

		log("testEditProduct: end");
	}

	// Test case #4
	public void testDeleteProduct(String investmentName) throws InterruptedException {

		log("testDeleteProduct: begin");

		String universeName = "Citi P360 - Korea";

		MenuBarPage menuBar = new MenuBarPage(webDriver);
		ProductMaintenancePage productMaintenance = menuBar.goToProductMaintenancePage();

		productMaintenance.loadProductUniverse(universeName).simpleSearch("Name:\"" + investmentName + "\"")
				.selectProductByName(investmentName).deleteSelectedProducts();

		this.waitForWaitingScreenNotVisible();

		productMaintenance.simpleSearch("Name:\"" + investmentName + "\"");
		assertTrue(pageContainsStr("No results found."));

		log("testDeleteProduct: end");
	}

	// Test case #5
	public void testDownloadSpreadSheet() throws InterruptedException {
		log("testDownloadSpreadSheet: begin");
		String universeName = "Citi P360 - Korea";

		MenuBarPage menuBar = new MenuBarPage(webDriver);
		ProductMaintenancePage productMaintenance = menuBar.goToProductMaintenancePage();

		productMaintenance.loadProductUniverse(universeName).clickDownloadSpreadsheetButton().clickYesButtonIfVisible();

		String fileName = getTextByLocator(By.id("gwt-debug-ExportSearchResultPopupView-fileNameTextBox"));
		clickElement(By.id("gwt-debug-ExportSearchResultPopupView-proceedButton"));
		clickOkButtonIfVisible();
		menuBar.goToProcess();

		waitForTextVisible(By.xpath("(.//div[@class='processImageMenuPopupTitle'])[1]"), fileName + ".xls");
		pageContainsStr(fileName);

		log("testDownloadSpreadSheet: finish");
	}

	// Test case #8
	public void testSearching(String investmentName) throws Exception {
		log("testSearching: begin");

		String universeName = "Citi P360 - Korea";
		String simpleSearchWord = "JPMorgan";
		String productCode = "ABC1";
		String ISIN = "KYG2110A1031";
		String assetType = "Equity";

		MenuBarPage menuBar = new MenuBarPage(webDriver);
		ProductMaintenancePage productMain = menuBar.goToProductMaintenancePage();
		productMain.loadProductUniverse(universeName);

		productMain.simpleSearch(simpleSearchWord);
		assertTrue(pageContainsStr(simpleSearchWord));
		productMain.clickClearSearchButton();

		AdvancedSearchPage advancedSearch = productMain.clickAdvancedSearchButton();

		advancedSearch.searchByCustomFields("Product Code", productCode).clickSearchButton();
		assertTrue(pageContainsStr(productCode));
		productMain.clickClearSearchButton();

		productMain.clickAdvancedSearchButton();
		advancedSearch.searchByCustomFields("Name", investmentName).clickSearchButton();
		assertTrue(pageContainsStr(investmentName));
		productMain.clickClearSearchButton();

		productMain.clickAdvancedSearchButton();
		advancedSearch.searchByCustomFields("ISIN", ISIN).clickSearchButton();
		assertTrue(pageContainsStr(ISIN));
		productMain.clickClearSearchButton();

		productMain.clickAdvancedSearchButton();
		advancedSearch.searchByProductAssetType(assetType).clickSearchButton();
		assertTrue(pageContainsStr(assetType));
		productMain.clickClearSearchButton();

		log("testSearching: finish");
	}

	@Test
	public void testMISDownload() throws InterruptedException, InvalidFormatException, IOException {
		String currentDate = this.getCurrentTimeInFormat("yyyyMMdd");

		String fileName = "IllustrationMIS." + currentDate;
		log("currentDate: " + currentDate);

		LoginPage main = new LoginPage(webDriver);

		IllustrationPage illustration = main.loginAs(Settings.Citi_P360_USER, Settings.Citi_P360_PASSWORD)
				.goToIllustrationPage().simpleSearch("Name:\"Mutual Fund\"");

		illustration.clickMISDownloadButton();

		MenuBarPage menu = new MenuBarPage(webDriver);

		menu.goToProcess();

		try {
			waitForElementVisible(By.xpath(".//div[contains(text(),'" + fileName + "')]"), 360);
		} catch (TimeoutException e) {
			checkLogout();
			LoginPage main2 = new LoginPage(webDriver);
			main2.loginAs(Settings.Citi_P360_USER, Settings.Citi_P360_PASSWORD).goToProcess();
			assertTrue(isElementVisible(By.xpath(".//div[contains(text(),'" + currentDate + "')]")));
		} catch (AssertionError e) {
			throw e;
		}

		menu.downloadExportFile(fileName);
		this.checkDownloadedFile(fileName);

	}

	public void editProduct(String code, String riskLevel, String status, String allowed) throws InterruptedException {

		ProductMaintenancePage productMaintenance = new ProductMaintenancePage(webDriver);

		productMaintenance.editStatus(status).editAllowedOnline(allowed).editProductCode(code).editRiskLevel(riskLevel);

		this.waitForElementVisible(By.id("gwt-debug-CustomDialog-okButton"), Settings.WAIT_SECONDS);
		clickElement(By.id("gwt-debug-CustomDialog-okButton"));

		// int size = getSizeOfElements(By
		// .xpath(".//*[@id='gwt-debug-CustomDialog-buttonsPanel']//*[@id='gwt-debug-CustomDialog-okButton']"));
		// if (size > 1) {
		// for (int i = size; i > 1; i--) {
		// clickElement(By
		// .xpath("(.//*[@id='gwt-debug-CustomDialog-buttonsPanel']//*[@id='gwt-debug-CustomDialog-okButton'])["
		// + String.valueOf(i) + "]"));
		// }
		// }

		this.waitForElementPresent(By.id("gwt-debug-CustomDialog-yesButton"), Settings.WAIT_SECONDS);
		if (isElementVisible(By.xpath("(//*[@id='gwt-debug-CustomDialog-yesButton'])[2]"))) {
			clickElement(By.xpath("(//*[@id='gwt-debug-CustomDialog-yesButton'])[2]"));
		}

	}

	public void handleOkButton() {
		int size = getSizeOfElements(By
				.xpath("(.//*[@id='gwt-debug-CustomDialog-buttonsPanel']//*[@id='gwt-debug-CustomDialog-okButton'])"));
		for (int i = size; i > 1; i--) {
			clickElement(By
					.xpath("(.//*[@id='gwt-debug-CustomDialog-buttonsPanel']//*[@id='gwt-debug-CustomDialog-okButton'])["
							+ String.valueOf(i) + "]"));
		}

	}

}
