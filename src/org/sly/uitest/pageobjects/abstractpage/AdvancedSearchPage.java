package org.sly.uitest.pageobjects.abstractpage;

import static org.junit.Assert.assertTrue;
/**
 * @author Lynne Huang
 * @date : 12 Aug, 2015
 * @company Prive Financial
 */

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.companyadmin.CustomTagPage;
import org.sly.uitest.settings.Settings;

/**
 * This class represents the Advanced Search Panel, which can be navigated from
 * the Accounts Overview Page, Clients Overview Page, New Business Event Page,
 * Tasks Page etc.
 * 
 * @author Lynne Huang
 * @date : 11 Aug, 2015
 * @company Prive Financial
 */

public class AdvancedSearchPage extends AbstractPage {

	private Class<?> theClass = null;

	public AdvancedSearchPage(WebDriver webDriver, Class<?> theClass) throws InterruptedException {

		super();
		this.webDriver = webDriver;
		this.theClass = theClass;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(60, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gwt-debug-MyMainMaterialView-mainPanel")));
		} catch (TimeoutException e) {

		}
		assertTrue(pageContainsStr(" Advanced Search "));
	}

	/**
	 * @param name
	 *            the name of investment
	 * @return {@link AdvancedSearchPage}
	 */
	public AdvancedSearchPage searchByName(String name) {

		waitForElementVisible(By.id("gwt-debug-AdvancedSearchPanel-name"), 10);
		sendKeysToElement(By.id("gwt-debug-AdvancedSearchPanel-name"), name);

		return this;
	}

	/**
	 * @param name
	 *            the name of client
	 * @return {@link AdvancedSearchPage}
	 */
	public AdvancedSearchPage searchByClientName(String name) {

		sendKeysToElement(By.id("gwt-debug-AdvancedSearchPanel-Client"), name);

		return this;
	}

	/**
	 * @param nationality
	 *            the nationality of the client
	 * @return {@link AdvancedSearchPage}
	 */
	public AdvancedSearchPage searchByClientNationality(String nationality) {

		selectFromDropdown(By.xpath("//td[.='Client Nationality']/following-sibling::td[1]/select"), nationality);

		return this;
	}

	/**
	 * @param country
	 *            the country of residence of the client
	 * @return {@link AdvancedSearchPage}
	 */
	public AdvancedSearchPage searchByClientCountryOfResidence(String country) {

		selectFromDropdown(By.xpath("//td[.='Client Country of Residence']/following-sibling::td[1]/select"), country);

		return this;
	}

	/**
	 * @param tagName
	 *            the name of the tag that is selected by the client
	 * @return {@link AdvancedSearchPage}
	 */
	public AdvancedSearchPage searchByClientTag(String tagName) {

		By dropdown = By.xpath("//td[.='Client Tag']/following-sibling::td[1]/div/table/tbody/tr/td[2]/button");
		waitForElementVisible(dropdown, Settings.WAIT_SECONDS);
		clickElement(dropdown);

		int size = this.getSizeOfElements(By.xpath("(//label[.='" + tagName + "'])"));

		clickElement(By.xpath("(//label[.='" + tagName + "'])[" + size + "]/preceding-sibling::input[1]"));

		return this;
	}

	/**
	 * @param office
	 *            the name of the office that is selected by the client
	 * 
	 * @return {@link AdvancedSearchPage}
	 */
	public AdvancedSearchPage searchByClientServingOffice(String office) {

		sendKeysToElement(By.id("gwt-debug-AdvancedSearchPanel-ServingOffice"), office);

		return this;
	}

	/**
	 * @param email
	 *            the email address of the client
	 * 
	 * @return AdvancedSearchPage
	 */
	public AdvancedSearchPage searchByClientEmail(String email) {

		sendKeysToElement(By.id("gwt-debug-AdvancedSearchPanel-ClientEmail"), email);

		return this;

	}

	/**
	 * @param exRefID
	 *            the External Reference ID of the client
	 * @return {@link AdvancedSearchPage}
	 */
	public AdvancedSearchPage searchByClientExternalReferenceID(String exRefID) {

		sendKeysToElement(By.id("gwt-debug-AdvancedSearchPanel-ClientExtRef"), exRefID);

		return this;
	}

	/**
	 * @param emailFunction
	 *            the email Function could be enabled or disabled
	 * 
	 * @return {@link AdvancedSearchPage}
	 */
	public AdvancedSearchPage searchByClientEmailFunction(String emailFunction) {

		selectFromDropdown(By.xpath("//td[.='Email Function']/following-sibling::td[1]/select"), emailFunction);

		return this;
	}

	/**
	 * @param category
	 *            the category of the client
	 * 
	 * @return {@link AdvancedSearchPage}
	 */
	public AdvancedSearchPage searchByClientCategory(String category) {

		selectFromDropdown(By.xpath("//select[@name='ClientCategory']"), category);

		return this;

	}

	/**
	 * @param name
	 *            the name of the account
	 * @return {@link AdvancedSearchPage}
	 */
	public AdvancedSearchPage searchByAccountName(String name) {

		sendKeysToElement(By.id("gwt-debug-AdvancedSearchPanel-AccountName"), name);

		return this;
	}

	/**
	 * @param advisor
	 *            the advisor name of the account
	 * @return {@link AdvancedSearchPage}
	 */
	public AdvancedSearchPage searchByAccountAdvisor(String advisor) {

		sendKeysToElement(By.id("gwt-debug-AdvancedSearchPanel-Advisor"), advisor);

		return this;
	}

	/**
	 * @param String
	 *            accountStatus
	 * @return {@link AdvancedSearchPage}
	 */
	public AdvancedSearchPage searchByAccountStatus(String... accountStatus) {

		clickElement(By.xpath("//td[.='Account Status']/following-sibling::td//button/i"));

		for (int i = 0; i < accountStatus.length; i++) {

			clickElement(By.xpath("(//label[.='" + accountStatus[i] + "'])[2]/preceding-sibling::input[1]"));

		}

		return this;
	}

	/**
	 * @param reportGeneration
	 *            to decide that report generation function is "Enabled" or
	 *            "Disabled"
	 * @return
	 */
	public AdvancedSearchPage searchByAccountReportGeneration(String reportGeneration) {

		selectFromDropdown(By.xpath(".//select[@name='ReportGeneration']"), reportGeneration);

		return this;
	}

	/**
	 * 
	 * @param ifIncludeAssetAccount
	 *            to decide if non-asset account is included in search result
	 * @return
	 */
	public AdvancedSearchPage searchByNonAssetAccount(String ifIncludeAssetAccount) {

		selectFromDropdown(By.xpath(".//select[@name='NonassetAccount']"), ifIncludeAssetAccount);

		return this;
	}

	/**
	 * @param tagName
	 *            the name of the tag that is selected by the account
	 * @return {@link AdvancedSearchPage}
	 */
	public AdvancedSearchPage searchByAccountTag(String tagName) {

		clickElement(By.xpath(
				"//div[@id='gwt-debug-AdvancedSearchPanel-multiSelector-InvestorAccountTag']//table/tbody/tr/td[2]/button[@id='gwt-debug-MultiSelectDropDownBox-dropDown']/i"));

		clickElement(By.xpath("(//label[.='" + tagName + "'])[2]/preceding-sibling::input[1]"));

		return this;
	}

	/**
	 * @param access
	 *            the client access of the account
	 * 
	 * @return {@link AdvancedSearchPage}
	 * 
	 */
	public AdvancedSearchPage searchByAccountClientAccess(String access) {

		selectFromDropdown(By.xpath("(//td[.='Client Access'])[1]/following-sibling::td[1]/select"), access);

		return this;
	}

	/**
	 * @param status
	 *            the policy status of the account
	 * 
	 * @return {@link AdvancedSearchPage}
	 */
	public AdvancedSearchPage searchByAccountPolicyStatus(String status) {

		sendKeysToElement(By.id("gwt-debug-AdvancedSearchPanel-PolicyStatus"), status);

		return this;
	}

	/**
	 * The fund region field could be 'select' or 'input'
	 * 
	 * @param region
	 *            the fund region of the account
	 * @return {@link AdvancedSearchPage}
	 */
	public AdvancedSearchPage searchByAccountFundRegion(String region) {

		try {
			webDriver.findElement(By.id("gwt-debug-AdvancedSearchPanel-region"));
			sendKeysToElement(By.id("gwt-debug-AdvancedSearchPanel-region"), region);
		} catch (Exception e) {
			selectFromDropdown(By.xpath("//select[@name='FundRegion']"), region);
		}

		return this;

	}

	/**
	 * @param tagName
	 *            the tag name of the product that is selected by the account or
	 *            the client
	 * @return {@link AdvancedSearchPage}
	 */
	public AdvancedSearchPage searchByProductInvestmentTag(String tagName) {

		clickElement(By.xpath("//td[.='Account Tag']/following-sibling::td[1]/div/table/tbody/tr/td[2]/img"));

		clickElement(By.xpath("(//label[.='" + tagName + "'])[2]/preceding-sibling::input[1]"));

		return this;
	}

	/**
	 * @param name
	 *            the investment name of the product that is selected by the
	 *            account or the client
	 * @return {@link AdvancedSearchPage}
	 */
	public AdvancedSearchPage searchByProductInvestmentName(String name) {

		sendKeysToElement(By.id("gwt-debug-AdvancedSearchPanel-FundName"), name);

		return this;
	}

	/**
	 * This is to select investment by clicking the magnifying glass next to
	 * "Investment Name"
	 * 
	 * @param name
	 *            the investment name of the product that is selected by the
	 *            account or the client
	 * @return {@link AdvancedSearchPage}
	 * @throws InterruptedException
	 */
	public AdvancedSearchPage searchByProductInvestmentNameAndMagnifyingGlass(String name) throws InterruptedException {

		// click on the magnifying glass
		clickElement(By.xpath(".//td[.='Investment Name']/following-sibling::td/img[@class='imgLink']"));

		waitForElementVisible(By.xpath(".//*[@id='gwt-debug-ManagerListWidgetView-searchBox']"), 30);

		sendKeysToElement(By.xpath(".//*[@id='gwt-debug-ManagerListWidgetView-searchBox']"), name);

		clickElement(By.id("gwt-debug-ManagerListWidgetView-searchBtn"));
		this.waitForWaitingScreenNotVisible();

		clickElement(By.xpath(
				".//td[.='" + name + "']//preceding-sibling::td[//button[@class='ActionColumnButton']]//button"));

		clickElement(By.xpath("html/body/div/div/table/tbody/tr/td/a[.='Select Investment']"));

		clickOkButtonIfVisible();

		return this;
	}

	/**
	 * @param manager
	 *            the fund manager name of the product that is selected by the
	 *            account or the client
	 * @return {@link AdvancedSearchPage}
	 */
	public AdvancedSearchPage searchByProductFundManager(String manager) {

		sendKeysToElement(By.id("gwt-debug-AdvancedSearchPanel-FundManager"), manager);

		return this;
	}

	/**
	 * @param type
	 *            the investment type of the product that is selected by the
	 *            account or the client
	 * @return {@link AdvancedSearchPage}
	 */
	public AdvancedSearchPage searchByProductInvestmentType(String type) {

		selectFromDropdown(By.xpath("//select[@name='InvestmentType']"), type);

		return this;
	}

	/**
	 * @param asset
	 *            the asset class of the product
	 * @return {@link AdvancedSearchPage}
	 */
	public AdvancedSearchPage searchByProductAssetClass(String asset) {

		selectFromDropdown(By.xpath("//select[@name='AssetClass']"), asset);

		return this;
	}

	/**
	 * @param asset
	 *            the asset class of the product
	 * @return {@link AdvancedSearchPage}
	 */
	public AdvancedSearchPage searchByProductAssetType(String asset) {

		selectFromDropdown(By.xpath("//select[@name='AssetType']"), asset);

		return this;
	}

	/**
	 * @param provider
	 *            the provider of the product that is selected by the account or
	 *            client
	 * @return {@link AdvancedSearchPage}
	 */
	public AdvancedSearchPage searchByProductProvider(String provider) {
		By by = By.id("gwt-debug-AdvancedSearchPanel-ProductProvider");
		waitForElementVisible(by, Settings.WAIT_SECONDS);
		sendKeysToElement(by, provider);

		return this;
	}

	/**
	 * @param provider
	 *            the provider of the product
	 * @return {@link AdvancedSearchPage}
	 */
	public AdvancedSearchPage searchByProvider(String provider) {
		waitForElementVisible(By.id("gwt-debug-AdvancedSearchPanel-Provider"), Settings.WAIT_SECONDS);

		sendKeysToElement(By.id("gwt-debug-AdvancedSearchPanel-Provider"), provider);

		return this;
	}

	/**
	 * @param platform
	 *            the platform of the product that is selected by the account or
	 *            client
	 * @param magnifying
	 *            if true, the platform is chosen by clicking the magnifying
	 *            icon first; if false, input the name of the platform directly
	 *            to the field
	 */
	public AdvancedSearchPage searchByProductPlatform(String platform, Boolean magnifying) {

		if (!magnifying) {
			sendKeysToElement(By.id("gwt-debug-AdvancedSearchPanel-ProviderName"), platform);

		} else {

			clickElement(By.xpath("//td[.='Product Platform']/following-sibling::td[2]/img"));

			selectFromDropdown(By.id("gwt-debug-PairedListBoxSelector-sourceList"), platform);

			clickElement(By.id("gwt-debug-PairedListBoxSelector-addImg"));

			clickElement(By.id("gwt-debug-ExecutionPlatformSelector-submitBtn"));
		}
		return this;
	}

	/**
	 * @param name
	 *            the model portfolio name of the product that is selected by
	 *            the account or the client
	 * @return {@link AdvancedSearchPage}
	 */
	public AdvancedSearchPage searchByProductModelPortfolioName(String name) {

		sendKeysToElement(By.id("gwt-debug-AdvancedSearchPanel-ModelPortfolioName"), name);

		return this;
	}

	/**
	 * @param provider
	 *            the model portfolio provider of the product that is selected
	 *            by the account or the client
	 * @return {@link AdvancedSearchPage}
	 */
	public AdvancedSearchPage searchByProductModelPortfolioProvider(String provider) {

		sendKeysToElement(By.id("gwt-debug-AdvancedSearchPanel-ModelPortfolioProvider"), provider);

		return this;
	}

	/**
	 * @param number
	 *            the reference number of the new business event
	 *
	 * @return {@link AdvancedSearchPage}
	 */
	public AdvancedSearchPage searchByNBERefNo(String number) {

		sendKeysToElement(By.id("gwt-debug-AdvancedSearchPanel-RefNo"), number);

		return this;
	}

	/**
	 * @param approved
	 *            the approvement status of the new business event
	 * 
	 * @return {@link AdvancedSearchPage}
	 * 
	 */
	public AdvancedSearchPage searchByNBEApproved(String approved) {

		selectFromDropdown(By.xpath("//select[@class='formTextBox_width' and @name='Approved']"), approved);

		return this;
	}

	/**
	 * @param date
	 *            the start date of the event
	 * 
	 * @return {@link AdvancedSearchPage}
	 */
	public AdvancedSearchPage searchByStartDate(String date) {

		sendKeysToElement(By.id("gwt-debug-AdvancedSearchPanel-StartDate"), date);

		return this;
	}

	/**
	 * @param date
	 *            the end date of the event
	 * @return {@link AdvancedSearchPage}
	 */
	public AdvancedSearchPage searchByEndDate(String date) {

		sendKeysToElement(By.id("gwt-debug-AdvancedSearchPanel-EndDate"), date);

		return this;
	}

	/**
	 * @param counterParty
	 *            the counter party of the commission
	 * @return {@link AdvancedSearchPage}
	 */
	public AdvancedSearchPage searchByCounterparty(String counterParty) {

		selectFromDropdown(By.xpath("//select[@class='formTextBox_width' and @name='Counterparty']"), counterParty);

		return this;
	}

	/**
	 * @param date
	 *            the start date of the release
	 * @return {@link AdvancedSearchPage}
	 */
	public AdvancedSearchPage searchByReleaseDateStartDate(String date) {

		sendKeysToElement(By.id("gwt-debug-AdvancedSearchPanel-ReleaseDateFrom"), date);

		return this;
	}

	/**
	 * @param date
	 *            the end date of the release
	 * @return {@link AdvancedSearchPage}
	 */
	public AdvancedSearchPage searchByReleaseDateEndDate(String date) {

		sendKeysToElement(By.id("gwt-debug-AdvancedSearchPanel-ReleaseDateTo"), date);

		return this;
	}

	/**
	 * @param owner
	 *            the name of the task owner
	 * @return {@link AdvancedSearchPage}
	 */
	public AdvancedSearchPage searchByTaskOwner(String owner) {

		sendKeysToElement(By.id("gwt-debug-AdvancedSearchPanel-Owner"), owner);

		return this;
	}

	/**
	 * 
	 * @param name
	 *            task name
	 * @return {@link AdvancedSearchPage}
	 */
	public AdvancedSearchPage searchByTaskName(String name) {
		sendKeysToElement(By.id("gwt-debug-AdvancedSearchPanel-TaskName"), name);
		return this;
	}

	/**
	 * To do advanced search in investment page: Investments -> advanced search
	 * 
	 * @param fieldName
	 *            Name of field in advanced search panel ex: Symbol, ISIN,SEDOL
	 * @param searchKeyword
	 *            Keyword for searching
	 * @return {@link AdvancedSearchPage}
	 */
	public AdvancedSearchPage searchByCustomFields(String fieldName, String searchKeyword) {
		sendKeysToElement(By.xpath(".//td[.='" + fieldName + "']/following-sibling::td/input"), searchKeyword);
		return this;
	}

	/**
	 * Search by alert type in company wide
	 * 
	 * @param type
	 * @return {@AdvancedSearchPage}
	 */
	public AdvancedSearchPage searchByAlertTypeInCompanyWide(String type) {
		int size = this.getSizeOfElements(By.xpath(
				".//*[@id='gwt-debug-UserAlertOverviewView-advisorCompanyAlertsWidget']//*[@class='formTextBox_width' and @name='AlertType']"));
		selectFromDropdown(By
				.xpath("(.//*[@id='gwt-debug-UserAlertOverviewView-advisorCompanyAlertsWidget']//*[@class='formTextBox_width' and @name='AlertType'])["
						+ String.valueOf(size) + "]"),
				type);
		return this;
	}

	/**
	 * Search by alert type in My Alert
	 * 
	 * @param type
	 * @return {@AdvancedSearchPage}
	 */
	public AdvancedSearchPage searchByAlertTypeInMyAlerts(String type) {
		int size = this.getSizeOfElements(By.xpath(
				".//*[@id='gwt-debug-UserAlertOverviewView-myAlertsWidget']//*[@class='formTextBox_width' and @name='AlertType']"));
		selectFromDropdown(By
				.xpath("(.//*[@id='gwt-debug-UserAlertOverviewView-myAlertsWidget']//*[@class='formTextBox_width' and @name='AlertType'])["
						+ String.valueOf(size) + "]"),
				type);
		return this;
	}

	/**
	 * click the Saved Searches tab in the Advanced Search Panel
	 * 
	 * @return {@link AdvancedSearchPage}
	 */
	public AdvancedSearchPage goToSavedSearchesTab() {

		scrollToTop();
		waitForElementVisible(By.xpath("//div[@class='gwt-HTML' and .='Saved Searches']"), 10);

		clickElement(By.xpath("//div[@class='gwt-HTML' and .='Saved Searches']"));

		return this;
	}

	/**
	 * click the Search tab in the Advanced Search Panel
	 * 
	 * @return {@link AdvancedSearchPage}
	 */
	public AdvancedSearchPage goToSearchTab() {

		clickElement(By.xpath("//div[@class='gwt-HTML' and .='Search']"));

		return this;
	}

	/**
	 * @param the
	 *            name of the existing saved search criteria
	 * @return {@link AdvancedSearchPage}
	 */
	public AdvancedSearchPage selectSavedSearchName(String name) {

		waitForElementVisible(By.id("gwt-debug-AdvancedSearchPanel-presetsListBox"), 30);
		// try {
		// wait(5);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// }
		// log(name);
		selectFromDropdown(By.id("gwt-debug-AdvancedSearchPanel-presetsListBox"), name);

		return this;
	}

	/**
	 * click the Search button
	 *
	 * @return return to the previous page
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("unchecked")
	public <T> T clickSearchButton() throws Exception {
		boolean multilayer;
		By by;
		wait(Settings.WAIT_SECONDS);
		int size = this.getSizeOfElements(By.xpath(".//*[@id='gwt-debug-AdvancedSearchPanel-searchButton']"));
		if (isElementVisible(By.xpath("(.//*[@id='gwt-debug-AdvancedSearchPanel-searchButton'])[1]"))) {
			by = By.xpath("(.//*[@id='gwt-debug-AdvancedSearchPanel-searchButton'])[1]");
		} else {
			by = By.xpath("(.//*[@id='gwt-debug-AdvancedSearchPanel-searchButton'])[" + size + "]");
		}

		clickElement(by);
		// wait(Settings.WAIT_SECONDS);
		scrollToTop();
		this.waitForWaitingScreenNotVisible();
		return (T) this.theClass.getDeclaredConstructor(WebDriver.class).newInstance(webDriver);

	}

	/**
	 * click the Search button on the preset search page
	 *
	 * @return return to the previous page
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("unchecked")
	public <T> T clickSearchPresetButton() throws Exception {

		clickElement(By.id("gwt-debug-AdvancedSearchPanel-searchPresetButton"));

		return (T) this.theClass.getDeclaredConstructor(WebDriver.class).newInstance(webDriver);

	}

	/**
	 * Save the search criteria with the given name
	 * 
	 * @param name
	 *            the name of the saved search criteria
	 * @return {@link AdvancedSearchPage}
	 */
	public AdvancedSearchPage clickSaveButtonForThisSearch(String name) {

		clickElementByKeyboard(By.id("gwt-debug-AdvancedSearchPanel-saveButton"));
		sendKeysToElement(By.id("gwt-debug-AdvancedSearchPanel-presetNameTextBox"), name);

		return this;
	}

	/**
	 * Click the Edit button to edit the chosen search criteria
	 * 
	 * @return {@link AdvancedSearchPage}
	 */
	public AdvancedSearchPage clickEditButtonForThisSearch() {
		scrollToTop();
		waitForElementVisible(By.id("gwt-debug-AdvancedSearchPanel-editPresetButton"), 10);
		clickElement(By.id("gwt-debug-AdvancedSearchPanel-editPresetButton"));

		return this;
	}

	/**
	 * Click the Delete button to delete the chosen search criteria
	 * 
	 * @return {@link AdvancedSearchPage}
	 */
	public AdvancedSearchPage clickDeleteButtonForThisSearch() {

		clickElement(By.id("gwt-debug-AdvancedSearchPanel-deleteButton"));

		clickYesButtonIfVisible();

		return this;
	}

	public AdvancedSearchPage clickSaveButtonToSaveThisSearch(String name) throws InterruptedException {

		waitForElementVisible(By.id("gwt-debug-AdvancedSearchPanel-saveButton"), 10);
		wait(2);
		clickElementByKeyboard(By.id("gwt-debug-AdvancedSearchPanel-saveButton"));

		waitForElementVisible(By.xpath(".//*[@id='gwt-debug-CustomDialog-customButton' and .='Save']"), 30);
		sendKeysToElement(By.id("gwt-debug-AdvancedSearchPanel-presetNameTextBox"), name);

		clickElement(By.xpath(".//*[@id='gwt-debug-CustomDialog-customButton' and .='Save']"));

		wait(Settings.WAIT_SECONDS);

		return this;
	}

	@SuppressWarnings("unchecked")
	public <T> T clickSearchButtonCounterparty() throws Exception {

		waitForElementVisible(By.id("gwt-debug-AdvancedSearchPanel-searchButton"), 150);
		clickElement(By.id("gwt-debug-AdvancedSearchPanel-searchButton"));
		waitForElementVisible(By.id("gwt-debug-SortableFlexTableAsync-table"), 300);
		return (T) this.theClass.getDeclaredConstructor(WebDriver.class).newInstance(webDriver);

	}

	/**
	 * This is to go to the custom tag for client
	 * 
	 * @return
	 */
	public CustomTagPage clickCheckMoreCustomClientTag() {
		this.clickElement(By.xpath(
				".//*[@id='gwt-debug-AdvancedSearchPanel-searchCriteriaTable']//td[.='Client Tag']//following-sibling::td//img"));
		for (String winHandle : webDriver.getWindowHandles()) {
			webDriver.switchTo().window(winHandle);
		}
		return new CustomTagPage(webDriver);
	}

	/**
	 * This is to go to the account tag for client
	 * 
	 * @return
	 */
	public CustomTagPage clickCheckMoreAccountTag() {
		this.clickElement(By.xpath(
				".//*[@id='gwt-debug-AdvancedSearchPanel-searchCriteriaTable']//td[.='Account Tag']//following-sibling::td//img"));
		for (String winHandle : webDriver.getWindowHandles()) {
			webDriver.switchTo().window(winHandle);
		}
		return new CustomTagPage(webDriver);
	}
}
