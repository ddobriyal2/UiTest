package org.sly.uitest.sections.clients;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.abstractpage.MenuBarPage;
import org.sly.uitest.pageobjects.clientsandaccounts.AccountOverviewPage;
import org.sly.uitest.pageobjects.clientsandaccounts.ClientOverviewPage;
import org.sly.uitest.settings.Settings;

/**
 * @author Lynne Huang
 * @date : 16 Sep, 2015
 * @company Prive Financial
 */
public class ClientOverviewTest extends AbstractTest {

	@Test
	public void testSearchClientByServingOffice() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		ClientOverviewPage clients = main.loginAs(Settings.CRM_USERNAME, Settings.CRM_PASSWORD)
				.goToClientOverviewPage();

		// Serving Office
		clients.goToAdvancedSearchPage().searchByClientServingOffice("Bangkok").clickSearchButton();
		waitForElementVisible(By.id("gwt-debug-InvestorOverviewView-linkPersonName"), 30);
		assertTrue(pageContainsStr("Bangkok"));
	}

	@Test
	public void testSearchClientByResidence() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		ClientOverviewPage clients = main.loginAs(Settings.CRM_USERNAME, Settings.CRM_PASSWORD)
				.goToClientOverviewPage();

		// Country of Residence/following-sibling::td[1]
		clients.clickClearSearchIcon().goToAdvancedSearchPage().searchByClientCountryOfResidence("Cambodia")
				.clickSearchButton();
		assertTrue(pageContainsStr("Cambodia"));

		int size = getSizeOfElements(By.xpath(".//*[@id='gwt-debug-InvestorOverviewView-linkPersonName']"));
		for (int i = 2; i < size + 2; i++) {

			try {
				clickElementAndWait3(By.id("gwt-debug-InvestorOverviewView-linkPersonName"));
				this.waitForElementVisible(By.xpath("//td[.='Country of Residence:']/following-sibling::td[1]"), 30);

				assertTrue(this.getTextByLocator(By.xpath("//td[.='Country of Residence:']/following-sibling::td[1]"))
						.equals("Cambodia"));
			} catch (org.openqa.selenium.NoSuchElementException e) {
				break;
			}
		}
	}

	@Test
	public void testSearchClientByNationality() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		ClientOverviewPage clients = main.loginAs(Settings.CRM_USERNAME, Settings.CRM_PASSWORD)
				.goToClientOverviewPage();

		clients.goToClientOverviewPage();

		// Country of Nationality
		clients.goToAdvancedSearchPage().searchByClientNationality("United Kingdom").clickSearchButton();

		int size = getSizeOfElements(By.xpath(".//*[@id='gwt-debug-InvestorOve"
				+ ""
				+ "rviewView-linkPersonName']"));

		for (int i = 2; i < size + 2; i++) {

			try {

				assertTrue(getTextByLocator(By
						.xpath(".//*[@id='gwt-debug-InvestorOverviewView-tablePanel']/table/tbody/tr[" + i + "]/td[5]"))
								.equals("United Kingdom"));

			} catch (org.openqa.selenium.NoSuchElementException e) {
				break;
			}
		}
	}

	@Test
	public void testSearchClientByEmail() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		ClientOverviewPage clients = main.loginAs(Settings.CRM_USERNAME, Settings.CRM_PASSWORD)
				.goToClientOverviewPage();

		// Email
		clients.clickClearSearchIcon().goToAdvancedSearchPage().searchByClientEmail("@example.com").clickSearchButton();

		int size = getSizeOfElements(By.xpath(".//*[@id='gwt-debug-InvestorOverviewView-linkPersonName']"));
		log(String.valueOf(size));
		for (int i = 2; i < size + 2; i++) {

			try {

				assertTrue(getTextByLocator(By
						.xpath(".//*[@id='gwt-debug-InvestorOverviewView-tablePanel']/table/tbody/tr[" + i + "]/td[6]"))
								.contains("@example.com"));

			} catch (org.openqa.selenium.NoSuchElementException e) {
				break;
			}
		}

	}

	@Test
	public void testSearchClientByTags() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		String tagName = "Gold";

		main.loginAs(Settings.CRM_USERNAME, Settings.CRM_PASSWORD).goToClientOverviewPage().goToAdvancedSearchPage()
				.searchByClientTag(tagName).clickSearchButton();

		assertTrue(pageContainsStr("Trevor Keidan"));
	}

	@Test
	public void testSearchClientByCategory() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		String category = "Existing Client";

		main.loginAs(Settings.CRM_USERNAME, Settings.CRM_PASSWORD).goToClientOverviewPage().goToAdvancedSearchPage()
				.searchByClientCategory(category).clickSearchButton();

		assertTrue(pageContainsStr("Carl Turner"));

	}

	@Test
	public void testDownloadClientSearchResultByClientDemographics() throws Exception {
		String file = "testByClientDemographics";
		String type = "Client Demographics";
		ClientOverviewPage client = this.init();

		MenuBarPage menu = client.exportSearchResult(type, file).goToProcess();

		menu.downloadExportFile(file);
		checkDownloadedExcelFile(file, "DATE OF BIRTH", "SEX", "MARITAL STATUS", "CORPORATE CLIENT", "NATIONALITY",
				"COUNTRY OF RESIDENCE", "OCCUPATION", "TOTAL");
	}

	@Test
	public void testDownloadClientSearchResultByClientList() throws Exception {
		String file = "testByClientList";
		String type = "Client List";
		String name = "";
		ClientOverviewPage client = this.init();
		int size = getSizeOfElements(By.id("gwt-debug-InvestorOverviewView-linkPersonName"));

		String[] surnameList = new String[size];

		for (int i = 0; i <= size - 1; i++) {
			name = getTextByLocator(By.xpath(
					"(.//*[@id='gwt-debug-InvestorOverviewView-linkPersonName'])[" + String.valueOf(i + 1) + "]"));
			if (name.contains(",")) {
				surnameList[i] = name.split(",")[0];
			} else {
				surnameList[i] = "";
			}

		}
		MenuBarPage menu = client.exportSearchResult(type, file).goToProcess();

		menu.downloadExportFile(file);
		checkDownloadedExcelFile(file, surnameList);
	}

	public ClientOverviewPage init() throws Exception {
		LoginPage main = new LoginPage(webDriver);

		AccountOverviewPage account = main.loginAs(Settings.USERNAME, Settings.PASSWORD)
				.goToUserPreferencePage(AccountOverviewPage.class).editLoadAccountAndClients(true).clickSubmitButton();

		ClientOverviewPage client = account.goToClientOverviewPage();

		return client;
	}
}
