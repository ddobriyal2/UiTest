package org.sly.uitest.pageobjects.planning;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.pageobjects.investments.InvestmentsPage;
import org.sly.uitest.settings.Settings;

/**
 * @author Lynne Huang
 * @date : 23 Sep, 2015
 * @company Prive Financial
 */
public class IllustrationPage extends AbstractPage {

	/**
	 * @param webDriver
	 */
	public IllustrationPage(WebDriver webDriver) {
		super();
		this.webDriver = webDriver;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		this.waitForWaitingScreenNotVisible();
		// wait.until(ExpectedConditions.visibilityOfElementLocated(By
		// .id("gwt-debug-MyMainClassicView-mainPanel")));
		waitForElementPresent(
				By.xpath(".//*[@id='waitScreen' and @style='display: none;']//*[@class='waitScreen_loading']"), 60);

		// assertTrue(pageContainsStr(" My Illustrations "));
	}

	/**
	 * @return
	 * 
	 */
	public IllustrationPage createNewIllustration() {

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(75, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gwt-debug-IllustrationListView-searchPanel")));

		clickElement(By.id("gwt-debug-IllustrationListView-newBtn"));

		try {
			waitForElementVisible(By.id("gwt-debug-IllustrationSettingsView-illustrationNameTxtBox"),
					Settings.WAIT_SECONDS);
		} catch (TimeoutException e) {
			clickElementByKeyboard(By.id("gwt-debug-IllustrationListView-newBtn"));
		}
		return this;
	}

	/**
	 * 
	 * */
	public IllustrationPage editIllustrationByName(String name) {

		waitForElementVisible(By.xpath("//td[.='" + name + "']/following-sibling::td[button[@title='Edit']]/button"),
				10);

		clickElement(By.xpath("//td[.='" + name + "']/following-sibling::td[button[@title='Edit']]/button"));

		return this;
	}

	/**
	 * 
	 * */
	public IllustrationPage deleteIllustrationByName(String name) {

		clickElement(By.xpath("//td[.='" + name + "']/following-sibling::td[button[@title='Delete']]/button"));

		clickYesButtonIfVisible();

		return this;
	}

	/**
	 * 
	 * */
	public IllustrationPage editIIName(String name) {

		sendKeysToElement(By.id("gwt-debug-IllustrationSettingsView-illustrationNameTxtBox"), name);
		return this;
	}

	/**
	 * 
	 * */
	public IllustrationPage editIIDescription(String description) {

		sendKeysToElement(By.id("gwt-debug-IllustrationSettingsView-illustrationDescTxtArea"), description);

		return this;
	}

	/**
	 * @throws InterruptedException
	 * 
	 */
	public IllustrationPage editIIRegardingAddExistingProspect(String... prospects) throws InterruptedException {

		clickElement(By.id("gwt-debug-IllustrationSettingsView-existingClientLink"));

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(120, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//select[@id='gwt-debug-PairedListBoxSelector-sourceList']/option")));

		for (String prospect : prospects) {

			selectFromDropdown(By.id("gwt-debug-PairedListBoxSelector-sourceList"), prospect);

		}

		clickElement(By.id("gwt-debug-PairedListBoxSelector-addImg"));

		clickElement(By.id("gwt-debug-SelectionUi-confirmButton"));

		return this;
	}

	/**
	 * @throws InterruptedException
	 * 
	 */
	public IllustrationPage editIIAddAdvisor(String advisor) throws InterruptedException {

		editIIAddAdvisor(-1, advisor);

		return this;
	}

	public IllustrationPage editIIAddAdvisor(Integer number, String advisor) throws InterruptedException {

		wait(Settings.WAIT_SECONDS);
		scrollToTop();
		waitForElementVisible(By.id("gwt-debug-IllustrationSettingsView-existingAdvisorLink"), 40);

		clickElement(By.id("gwt-debug-IllustrationSettingsView-existingAdvisorLink"));
		log("ExistingAdvisorLink: Clicked");
		wait(3);
		waitForElementVisible(By.xpath(".//*[@id='gwt-debug-PairedListBoxSelector-sourceList']/option"), 30);

		if (number > -1 && advisor == null) {

			advisor = getTextByLocator(
					By.xpath(".//*[@id='gwt-debug-PairedListBoxSelector-sourceList']/option[" + number + "]"));
		}

		selectFromDropdown(By.id("gwt-debug-PairedListBoxSelector-sourceList"), advisor);

		clickElement(By.id("gwt-debug-PairedListBoxSelector-addImg"));

		clickElement(By.id("gwt-debug-SelectionUi-confirmButton"));

		return this;
	}

	/**
	 * @throws InterruptedException
	 * 
	 */
	public IllustrationPage editIIRegardingRemoveExistingProspect() throws InterruptedException {

		clickElement(By.xpath("//button[@title='Remove']"));

		return this;

	}

	/**
	 * 
	 * */
	public IllustrationPage editIIRegardingAddNewProspect(String type, String firstname, String lastname) {

		clickElement(By.id("gwt-debug-IllustrationSettingsView-newClientLink"));

		selectFromDropdown(By.id("gwt-debug-ClientDetailWidget-clientTypeListBox"), type);

		sendKeysToElement(By.id("gwt-debug-ClientDetailWidget-firstNameTextBox"), firstname);

		sendKeysToElement(By.id("gwt-debug-ClientDetailWidget-lastNameTextBox"), lastname);

		clickElement(By.id("gwt-debug-ClientDetailWidget-saveBtn"));
		return this;
	}

	/**
	 * 
	 * */
	public IllustrationPage editIIOwner(String owner) {

		selectFromDropdown(By.id("gwt-debug-IllustrationSettingsView-illustrationOwnerListBox"), owner);

		return this;
	}

	/**
	 * 
	 * */
	public IllustrationPage editIIAdvisor(String advisor) {

		sendKeysToElement(By.id("gwt-debug-IllustrationSettingsView-illustrationAdvisor"), advisor);

		return this;
	}

	/**
	 * 
	 * */
	public IllustrationPage editIIProduct(String product) {

		waitForElementVisible(By.id("gwt-debug-IllustrationSettingsView-illustrationProductListBox"), 10);

		selectFromDropdown(By.id("gwt-debug-IllustrationSettingsView-illustrationProductListBox"), product);

		return this;
	}

	/**
	 * 
	 * */
	public IllustrationPage editIIBenchmark(String benchmark) {

		selectFromDropdown(By.id("gwt-debug-IllustrationSettingsView-illustrationBenchmarkListBox"), benchmark);

		return this;
	}

	/**
	 * 
	 * */
	public IllustrationPage editIIPurchasePayment(String currency, String amount) {

		selectFromDropdown(By.id("gwt-debug-IllustrationSettingsView-illustrationCurrencyListBox"), currency);

		sendKeysToElement(By.id("gwt-debug-IllustrationSettingsView-illustrationInitialAmountTxtBox"), amount);

		return this;
	}

	/**
	 * 
	 * */
	public IllustrationPage editIIType(String type) {

		selectFromDropdown(By.id("gwt-debug-IllustrationSettingsView-illustrationTimeframeListBox"), type);

		return this;
	}

	/**
	 * 
	 * */
	public IllustrationPage editIITrailingFrom(String selection) {

		clickElement(By.xpath("//label[.='" + selection + "']"));

		return this;
	}

	/**
	 * 
	 * */
	public IllustrationPage editIIDates(String from, String to) {

		sendKeysToElement(By.id("gwt-debug-IllustrationSettingsView-illustrationStartDate"), from);

		sendKeysToElement(By.id("gwt-debug-IllustrationSettingsView-illustrationEndDate"), to);

		return this;
	}

	/**
	 * 
	 * */
	public IllustrationPage editIIReportLanguage(String lang) {
		waitForElementVisible(By.id("gwt-debug-IllustrationSettingsView-reportLangListBox"), 30);
		WebElement elem = webDriver.findElement(By.id("gwt-debug-IllustrationSettingsView-reportLangListBox"));
		selectFromDropdown(elem, lang);
		return this;
	}

	/**
	 * 
	 * */
	public IllustrationPage editIIInvestorRiskProfile(String profile) {
		waitForElementVisible(By.id("gwt-debug-IllustrationSettingsView-illustrationIRPListBox"), 30);
		WebElement elem = webDriver.findElement(By.id("gwt-debug-IllustrationSettingsView-illustrationIRPListBox"));
		selectFromDropdown(elem, profile);
		return this;
	}

	/**
	 * 
	 * */
	public IllustrationPage editIIModelPortfolioType(String mp) {
		waitForElementVisible(By.id("gwt-debug-IllustrationSettingsView-illustrationModelPortfolioListBox"), 30);
		WebElement elem = webDriver
				.findElement(By.id("gwt-debug-IllustrationSettingsView-illustrationModelPortfolioListBox"));
		selectFromDropdown(elem, mp);
		return this;
	}

	public IllustrationPage editIICurrentInvestmentAmount(String amount) {
		sendKeysToElement(By.id("gwt-debug-IllustrationSettingsView-illustrationCurrentAmountTextBox"), amount);
		WebElement elem = webDriver
				.findElement(By.id("gwt-debug-IllustrationSettingsView-illustrationCurrentAmountTextBox"));
		elem.sendKeys(Keys.ENTER);
		return this;
	}

	public IllustrationPage editIIProposedInvestmentAmount(String amount) {
		sendKeysToElement(By.id("gwt-debug-IllustrationSettingsView-illustrationInitialAmountTxtBox"), amount);
		WebElement elem = webDriver
				.findElement(By.id("gwt-debug-IllustrationSettingsView-illustrationInitialAmountTxtBox"));
		elem.sendKeys(Keys.ENTER);
		return this;
	}

	public IllustrationPage editIIChartBenchmark(String benchmark) {
		waitForElementVisible(By.xpath("(.//td[contains(text(),'Benchmark')]//following-sibling::td)[1]//select"), 30);

		WebElement elem = webDriver
				.findElement(By.xpath("(.//td[contains(text(),'Benchmark')]//following-sibling::td)[1]//select"));
		selectFromDropdown(elem, benchmark);
		return this;
	}

	public IllustrationPage editAllocationByInvestmentInCurrentPorfolio(String investment, String allocation) {
		By by = By
				.xpath(".//table[@id='gwt-debug-IllustrationSettingsView-vioAllocationTableCompared']//td[a[contains(text(),'"
						+ investment
						+ "')]]//following-sibling::td//input[@id='gwt-debug-IllustrationSettingsPresenter-investmentAllocationTextBox']");
		waitForElementVisible(by, 30);
		sendKeysToElement(by, allocation);

		WebElement elem = webDriver.findElement(by);
		elem.sendKeys(Keys.ENTER);

		return this;
	}

	public IllustrationPage editMarketValueByInvestmentInCurrentPorfolio(String investment, String allocation) {
		By by = By
				.xpath(".//table[@id='gwt-debug-IllustrationSettingsView-vioAllocationTableCompared']//td[a[contains(text(),'"
						+ investment
						+ "')]]//following-sibling::td//input[@id='gwt-debug-IllustrationSettingsPresenter-investmentAllocationAmountTextBox']");
		waitForElementVisible(by, 30);
		sendKeysToElement(by, allocation);

		WebElement elem = webDriver.findElement(by);
		elem.sendKeys(Keys.ENTER);

		return this;
	}

	public IllustrationPage editAllocationByInvestmentInProposedPorfolio(String investment, String allocation) {
		By by = By.xpath(".//table[@id='gwt-debug-IllustrationSettingsView-vioAllocationTable']//td[a[contains(text(),'"
				+ investment
				+ "')]]//following-sibling::td//input[@id='gwt-debug-IllustrationSettingsPresenter-investmentAllocationTextBox']");
		waitForElementVisible(by, 30);
		sendKeysToElement(by, allocation);

		WebElement elem = webDriver.findElement(by);
		elem.sendKeys(Keys.ENTER);
		return this;
	}

	public IllustrationPage editMarketValueByInvestmentInProposedPorfolio(String investment, String allocation) {
		By by = By.xpath(".//table[@id='gwt-debug-IllustrationSettingsView-vioAllocationTable']//td[a[contains(text(),'"
				+ investment
				+ "')]]//following-sibling::td//input[@id='gwt-debug-IllustrationSettingsPresenter-investmentAllocationAmountTextBox']");
		waitForElementVisible(by, 30);
		sendKeysToElement(by, allocation);

		WebElement elem = webDriver.findElement(by);
		elem.sendKeys(Keys.ENTER);
		return this;
	}

	public IllustrationPage editMaximumCreditLimit(String limit) {
		sendKeysToElement(By.id("gwt-debug-IllustrationSettingsView-maxCreditTextBox"), limit);
		WebElement elem = webDriver.findElement(By.id("gwt-debug-IllustrationSettingsView-maxCreditTextBox"));
		elem.sendKeys(Keys.ENTER);
		return this;
	}

	public IllustrationPage editCurrentCreditLimit(String limit) {
		sendKeysToElement(By.id("gwt-debug-IllustrationSettingsView-currCreditTextBox"), limit);
		WebElement elem = webDriver.findElement(By.id("gwt-debug-IllustrationSettingsView-currCreditTextBox"));
		elem.sendKeys(Keys.ENTER);
		return this;
	}

	public IllustrationPage editUsedCreditLimit(String limit) {
		sendKeysToElement(By.id("gwt-debug-IllustrationSettingsView-usedCreditTextBox"), limit);
		WebElement elem = webDriver.findElement(By.id("gwt-debug-IllustrationSettingsView-usedCreditTextBox"));
		elem.sendKeys(Keys.ENTER);
		return this;
	}

	public IllustrationPage editBondLeverageInterestRate(String rate) {
		sendKeysToElement(By.id("gwt-debug-IllustrationSettingsView-leverageInterestRateTextBox"), rate);
		WebElement elem = webDriver
				.findElement(By.id("gwt-debug-IllustrationSettingsView-leverageInterestRateTextBox"));
		elem.sendKeys(Keys.ENTER);
		return this;
	}

	public IllustrationPage editBenchmarkInSensitivityAnalysis(String benchmark) {
		waitForElementVisible(
				By.xpath(
						".//div[@id='gwt-debug-IllustrationSettingsView-sensitivityAnalysisTabPanel']//td[//b[.='Benchmark']]//following-sibling::td//select"),
				30);
		int size = this.getSizeOfElements(By.xpath(
				".//div[@id='gwt-debug-IllustrationSettingsView-sensitivityAnalysisTabPanel']//td[//b[.='Benchmark']]//following-sibling::td//select"));
		WebElement elem = webDriver.findElement(By
				.xpath("(.//div[@id='gwt-debug-IllustrationSettingsView-sensitivityAnalysisTabPanel']//td[//b[.='Benchmark']]//following-sibling::td//select)["
						+ String.valueOf(size) + "]"));
		selectFromDropdown(elem, benchmark);
		return this;
	}

	public IllustrationPage clickAddBenchmarkButtonInII() {
		clickElement(By.xpath("(.//div[contains(text(),'Add another benchmark')])[1]//button"));
		return this;
	}

	public IllustrationPage clickAddBenchmarkButtonInSensitivityAnalysis() {
		clickElement(By.xpath(
				".//div[@id='gwt-debug-IllustrationSettingsView-sensitivityAnalysisTabPanel']//div[contains(text(),'Add another benchmark')]//button"));
		return this;
	}

	public IllustrationPage deleteAllInvestmentInCurrentPortfolio() {
		By by = By.xpath(
				".//table[@id='gwt-debug-IllustrationSettingsView-vioAllocationTableCompared']//button[contains(@id,'gwt-debug-IllustrationSettingsPresenter-deleteBtn')]");
		int size = this.getSizeOfElements(by);
		for (int i = 1; i < size; i++) {
			clickElement(by);
		}
		return this;
	}

	public IllustrationPage deleteAllInvestmentInProposedPortfolio() {
		By by = By.xpath(
				".//table[@id='gwt-debug-IllustrationSettingsView-vioAllocationTable']//button[contains(@id,'gwt-debug-IllustrationSettingsPresenter-deleteBtn')]");
		int size = this.getSizeOfElements(by);
		for (int i = 1; i < size; i++) {
			clickElement(by);
		}
		return this;
	}

	public IllustrationPage deleteAllBenchmark() {
		waitForElementVisible(By.xpath(".//td[.='Benchmark']//following-sibling::td//button"), 30);
		int size = getSizeOfElements(By.xpath(".//td[.='Benchmark']//following-sibling::td//button"));
		for (int i = size; i > 0; i--) {
			clickElement(By.xpath("(.//td[.='Benchmark']//following-sibling::td//button)[" + String.valueOf(i) + "]"));
		}

		return this;
	}

	public InvestmentsPage clickAddInvestmentButtonInCurrentPortfolio() {
		clickElement(By.xpath(
				".//table[@id='gwt-debug-IllustrationSettingsView-vioAllocationTableCompared']//button[@class='addButton']"));
		return new InvestmentsPage(webDriver, IllustrationPage.class);
	}

	public InvestmentsPage clickAddInvestmentButtonInProposedPortfolio() {
		clickElement(By.xpath(
				".//table[@id='gwt-debug-IllustrationSettingsView-vioAllocationTable']//button[@class='addButton']"));
		return new InvestmentsPage(webDriver, IllustrationPage.class);
	}

	public InvestmentsPage clickAddAnotherInvestmentInSensitivityAnalysis() {
		clickElement(By.xpath(".//div[contains(text(),'Add another investment')]//button"));
		return new InvestmentsPage(webDriver, IllustrationPage.class);
	}

	/**
	 * @throws InterruptedException
	 * 
	 */
	public IllustrationPage editFROOption(Boolean option) throws InterruptedException {

		wait(Settings.WAIT_SECONDS);

		waitForElementVisible(By.id("gwt-debug-IllustrationSettingsView-froMakeAllocation"), 30);

		if (option) {
			selectFromDropdown(By.id("gwt-debug-IllustrationSettingsView-froMakeAllocation"), "Yes");

			selectFromDropdownByValue(By.id("gwt-debug-IllustrationSettingsView-froMakeAllocation"), "true");
		} else {

			// otherwise the dropdown list cannot disappear

			if (isElementVisible(By.id("gwt-debug-IllustrationSettingsView-froRatesOptions"))) {
				try {
					editFRORateAssumptions("Current Rates");
				} catch (Exception e) {

				}
			}

			selectFromDropdown(By.id("gwt-debug-IllustrationSettingsView-froMakeAllocation"), "No");

			selectFromDropdownByValue(By.id("gwt-debug-IllustrationSettingsView-froMakeAllocation"), "false");

			// selectFromDropdown(
			// By.id("gwt-debug-IllustrationSettingsView-froMakeAllocation"),
			// "No");
			// clickElement(By.xpath(".//select[@id='gwt-debug-IllustrationSettingsView-froMakeAllocation']/option[.='No']"));

			// selectFromDropdownByValue(
			// By.id("gwt-debug-IllustrationSettingsView-froMakeAllocation"),
			// "false");
		}

		wait(Settings.WAIT_SECONDS);

		return this;
	}

	/**
	 * 
	 * */
	public IllustrationPage editFRORateAssumptions(String rates) {

		selectFromDropdown(By.id("gwt-debug-IllustrationSettingsView-froRatesOptions"), rates);

		return this;

	}

	/**
	 * 
	 * */
	public IllustrationPage editFRORateValues(String values) {

		selectFromDropdown(By.id("gwt-debug-IllustrationSettingsView-froRatesValues"), values);

		return this;

	}

	/**
	 * 
	 * */
	public IllustrationPage editFROAllocation(String name, String allocation) {

		sendKeysToElement(By.xpath("//td[.='" + name + "']/following-sibling::td[2]/input"), allocation + "\n");

		return this;

	}

	/**
	 * 
	 * */
	public IllustrationPage editFROUSD(String name, String usd) {

		sendKeysToElement(By.xpath("//td[.='" + name + "']/following-sibling::td[3]/input"), usd + "\n");
		return this;

	}

	/**
	 * @throws InterruptedException
	 * 
	 */
	public IllustrationPage editDCAOption(Boolean option) throws InterruptedException {
		scrollToTop();
		waitForElementVisible(By.id("gwt-debug-IllustrationSettingsView-dcaMakeAllocation"), 20);

		if (option) {
			selectFromDropdown(By.id("gwt-debug-IllustrationSettingsView-dcaMakeAllocation"), "Yes");

			selectFromDropdownByValue(By.id("gwt-debug-IllustrationSettingsView-dcaMakeAllocation"), "true");
		} else {

			// otherwise the dropdown list cannot disappear
			try {
				if (isElementVisible(By.id("gwt-debug-IllustrationSettingsView-dcaAllocationTextBox"))) {
					editDCAAllocation("100");
				}
			} catch (Exception e) {

			}

			selectFromDropdown(By.id("gwt-debug-IllustrationSettingsView-dcaMakeAllocation"), "No");

			selectFromDropdownByValue(By.id("gwt-debug-IllustrationSettingsView-dcaMakeAllocation"), "false");
		}

		wait(Settings.WAIT_SECONDS);

		return this;

	}

	/**
	 * 
	 * */
	public IllustrationPage editDCADuration(String duration) {

		selectFromDropdown(By.id("gwt-debug-IllustrationSettingsView-dcaDurationList"), duration);

		return this;

	}

	/**
	 * 
	 * */
	public IllustrationPage editDCAAllocation(String allocation) {

		WebElement elem = webDriver.findElement(By.id("gwt-debug-IllustrationSettingsView-dcaAllocationTextBox"));
		waitForElementVisible(By.id("gwt-debug-IllustrationSettingsView-dcaAllocationTextBox"), 30);

		sendKeysToElement(By.id("gwt-debug-IllustrationSettingsView-dcaAllocationTextBox"), allocation);
		elem.sendKeys(Keys.ENTER);
		return this;

	}

	/**
	 * 
	 * */
	public IllustrationPage editDCAUSD(String usd) {

		sendKeysToElement(By.id("gwt-debug-IllustrationSettingsView-dcaAllocationAmountTextBox"), usd + "\n");

		return this;

	}

	/**
	 * 
	 * */
	public IllustrationPage editDCAStartDate(String date) {

		selectFromDropdown(By.id("gwt-debug-IllustrationSettingsView-dcaStartDateOption"), date);

		return this;

	}

	/**
	 * @throws InterruptedException
	 * 
	 */
	public InvestmentsPage addDCAAllocationInvestment() throws InterruptedException {

		// wait(Settings.WAIT_SECONDS);

		waitForElementVisible(
				(By.xpath(
						"//td[.='Total']/following-sibling::td[button]/button[@class='fa fa-button fa-plus-circle size-16 add-green']")),
				30);

		clickElement(By.xpath(
				"//td[.='Total']/following-sibling::td[button]/button[@class='fa fa-button fa-plus-circle size-16 add-green']"));

		if (isElementVisible(By.xpath(
				"//td[.='Total']/following-sibling::td[button]/button[@class='fa fa-button fa-plus-circle size-16 add-green']"))) {
			clickElement(By.xpath(
					"//td[.='Total']/following-sibling::td[button]/button[@class='fa fa-button fa-plus-circle size-16 add-green']"));
		}

		return new InvestmentsPage(webDriver, IllustrationPage.class);

	}

	/**
	 * 
	 * */
	public IllustrationPage editDCAInvestmentAllocation(String name, String allocation) {
		waitForElementVisible(By.xpath("//td[a[.='" + name + "']]/following-sibling::td[1]/input"), 30);
		sendKeysToElement(By.xpath("//td[a[.='" + name + "']]/following-sibling::td[1]/input"), allocation + "\n");

		return this;

	}

	/**
	 * 
	 * */
	public IllustrationPage deleteDCAAllocationInvestment(String name) {

		clickElement(By.xpath("//td[a[.='" + name + "']]/following-sibling::td[2]/button[@title='Remove']"));

		return this;

	}

	/**
	 * @throws InterruptedException
	 * 
	 */
	public InvestmentsPage addVIOAllocationInvestment() throws InterruptedException {

		wait(Settings.WAIT_SECONDS);
		clickElementByKeyboard(By.xpath("//td[.='Total*']/following-sibling::td[button]/button[@class='addButton']"));

		return new InvestmentsPage(webDriver, IllustrationPage.class);
	}

	/**
	 * 
	 * */
	public IllustrationPage editVIOAllocation(String name, String allocation) {

		waitForElementVisible(By.xpath("//td[a[.='" + name + "']]/following-sibling::td[1]/input"), 10);
		try {
			wait(3);
		} catch (InterruptedException e) {

		}
		sendKeysToElement(By.xpath("//td[a[.='" + name + "']]/following-sibling::td[1]/input"), allocation + "\n");

		return this;
	}

	/**
	 * 
	 * */
	public IllustrationPage editVIOUSD(String name, String usd) {

		waitForElementVisible(By.xpath("//td[a[.='" + name + "']]/following-sibling::td[2]/input"), 10);
		sendKeysToElement(By.xpath("//td[a[.='" + name + "']]/following-sibling::td[2]/input"), "");
		sendKeysToElement(By.xpath("//td[a[.='" + name + "']]/following-sibling::td[2]/input"), usd + "\n");

		return this;
	}

	/**
	 * 
	 * */
	public IllustrationPage deleteVIOAllocationInvestment(String name) {

		clickElement(By.xpath("//td[a[.='" + name + "']]/following-sibling::td[3]/button[@title='Remove']"));

		return this;
	}

	/**
	 * 
	 * */
	public IllustrationPage editAROption(Boolean option) {

		waitForElementVisible(By.id("gwt-debug-IllustrationSettingsView-arpListBox"), 30);

		if (option) {
			selectFromDropdown(By.id("gwt-debug-IllustrationSettingsView-arpListBox"), "Yes");
		} else {
			selectFromDropdown(By.id("gwt-debug-IllustrationSettingsView-arpListBox"), "No");
		}

		return this;
	}

	/**
	 * 
	 * */
	public IllustrationPage editARFrequency(String frequency) {

		selectFromDropdown(By.id("gwt-debug-IllustrationSettingsView-arpFreqListBox"), frequency);

		return this;
	}

	/**
	 * 
	 * */
	public IllustrationPage editAPP() {

		return this;
	}

	/**
	 * @throws InterruptedException
	 * 
	 */
	public IllustrationPage clickNextButton_onGI() throws InterruptedException {

		wait(Settings.WAIT_SECONDS);

		clickElement(By.xpath("//*[@class='gwt-TabLayoutPanelTabInner']//div[.='General Information']"));

		waitForElementVisible(By.id("gwt-debug-IllustrationSettingsView-nextBtnIllustrationInfoTabPanel"), 30);

		clickElementByKeyboard(By.id("gwt-debug-IllustrationSettingsView-nextBtnIllustrationInfoTabPanel"));

		this.waitForWaitingScreenNotVisible();

		scrollToTop();
		return this;
	}

	/**
	 * @throws InterruptedException
	 * 
	 */
	public IllustrationPage clickNextButton_onFRO() throws InterruptedException {

		wait(Settings.WAIT_SECONDS);

		clickElement(By.xpath("//*[@class='gwt-TabLayoutPanelTabInner']//div[.='Fixed Rate Options']"));

		waitForElementVisible(By.id("gwt-debug-IllustrationSettingsView-nextBtnProposedFixedRateInvestmentsTabPanel"),
				30);
		clickElementByKeyboard(By.id("gwt-debug-IllustrationSettingsView-nextBtnProposedFixedRateInvestmentsTabPanel"));

		try {
			waitForElementVisible(By.id("gwt-debug-CustomDialog-mainPanel"), 3);
		} catch (TimeoutException e) {
			if (isElementVisible(By.xpath(
					".//div[@style='']//button[@id='gwt-debug-IllustrationSettingsView-nextBtnProposedFixedRateInvestmentsTabPanel']"))) {
				clickElementByKeyboard(By.xpath(
						".//div[@style='']//button[@id='gwt-debug-IllustrationSettingsView-nextBtnProposedFixedRateInvestmentsTabPanel']"));
			}
		}
		scrollToTop();
		return this;
	}

	/**
	 * @throws InterruptedException
	 * 
	 */
	public IllustrationPage clickNextButton_onDCA() throws InterruptedException {

		wait(Settings.WAIT_SECONDS);

		clickElement(By.xpath("//*[@class='gwt-TabLayoutPanelTabInner']//div[.='Dollar Cost Averaging']"));

		waitForElementVisible(By.id("gwt-debug-IllustrationSettingsView-nextBtnProposedDcaInvestmentsTabPanel"), 30);

		wait(Settings.WAIT_SECONDS);

		WebElement elem = webDriver
				.findElement(By.id("gwt-debug-IllustrationSettingsView-nextBtnProposedDcaInvestmentsTabPanel"));
		elem.sendKeys("");

		clickElementByKeyboard(By.id("gwt-debug-IllustrationSettingsView-nextBtnProposedDcaInvestmentsTabPanel"));

		try {
			waitForElementVisible(By.id("gwt-debug-CustomDialog-mainPanel"), 3);
		} catch (TimeoutException e) {
			if (isElementVisible(By.xpath(
					".//div[@style='']//button[@id='gwt-debug-IllustrationSettingsView-nextBtnProposedDcaInvestmentsTabPanel']"))) {
				clickElementByKeyboard(By.xpath(
						".//div[@style='']//button[@id='gwt-debug-IllustrationSettingsView-nextBtnProposedDcaInvestmentsTabPanel']"));
			}
		}

		return this;
	}

	/**
	 * @throws InterruptedException
	 * 
	 */
	public IllustrationPage clickNextButton_onVIO() throws InterruptedException {

		wait(Settings.WAIT_SECONDS);

		clickElement(By.xpath("//*[@class='gwt-TabLayoutPanelTabInner']//div[.='Varaible Investment Option']"));

		waitForElementVisible(By.id("gwt-debug-IllustrationSettingsView-nextBtnProposedVariableInvestmentsTabPanel"),
				30);

		wait(Settings.WAIT_SECONDS);

		WebElement elem = webDriver
				.findElement(By.id("gwt-debug-IllustrationSettingsView-nextBtnProposedVariableInvestmentsTabPanel"));
		elem.sendKeys("");
		clickElementByKeyboard(By.id("gwt-debug-IllustrationSettingsView-nextBtnProposedVariableInvestmentsTabPanel"));

		try {
			waitForElementVisible(By.id("gwt-debug-CustomDialog-mainPanel"), 3);
		} catch (TimeoutException e) {
			if (isElementVisible(By.xpath(
					".//div[@style='']//button[@id='gwt-debug-IllustrationSettingsView-nextBtnProposedVariableInvestmentsTabPanel']"))) {
				clickElementByKeyboard(By.xpath(
						".//div[@style='']//button[@id='gwt-debug-IllustrationSettingsView-nextBtnProposedVariableInvestmentsTabPanel']"));
			}
		}

		return this;
	}

	public IllustrationPage clickNextButton_onAR() throws InterruptedException {

		wait(Settings.WAIT_SECONDS);

		clickElement(By.xpath("//*[@class='gwt-TabLayoutPanelTabInner']//div[.='Asset Rebalancing']"));

		waitForElementVisible(By.id("gwt-debug-IllustrationSettingsView-nextBtnProposedAssetRebalancingTabPanel"), 30);

		clickElement(By.id("gwt-debug-IllustrationSettingsView-nextBtnProposedAssetRebalancingTabPanel"));

		wait(Settings.WAIT_SECONDS);

		return this;
	}

	public IllustrationPage clickNextButton_onCurrentPortfolio() {
		clickElement(By.id("gwt-debug-IllustrationSettingsView-nextBtnComparedVariableInvestmentsTabPanel"));
		return this;
	}

	public IllustrationPage clickNextButton_onProposedPortfolio() {
		clickElement(By.id("gwt-debug-IllustrationSettingsView-nextBtnProposedVariableInvestmentsTabPanel"));
		return this;
	}

	public IllustrationPage clickNextButton_onSensitivityAnalysis() {
		clickElement(By.id("gwt-debug-IllustrationSettingsView-nextBtnSensitivityAnalysisTabPanel"));
		return this;
	}

	public IllustrationPage clickNextButtonForCheckingField() {
		clickElement(By.id("gwt-debug-IllustrationSettingsView-nextBtnIllustrationInfoTabPanel"));
		if (isElementVisible(By.id("gwt-debug-CustomDialog-okButton"))) {
			assertTrue(pageContainsStr("Please review the highlighted fields"));
			clickOkButtonIfVisible();
		}

		return this;
	}

	public IllustrationPage clickMISDownloadButton() {
		clickElement(By.id("gwt-debug-IllustrationListView-MISDownloadBtn"));
		clickYesButtonIfVisible();
		clickElement(By.id("gwt-debug-ExportSearchResultPopupView-proceedButton"));
		clickOkButtonIfVisible();
		return this;
	}

	public IllustrationPage clickImportDollarValueFromCurrentPortfolio() {
		clickElement(By.id("gwt-debug-IllustrationSettingsView-importValueFromCurrentPortfolio"));
		return this;
	}

	public IllustrationPage clickImportPercentageValueFromCurrentPortfolio() {
		clickElement(By.id("gwt-debug-IllustrationSettingsView-importPercentageFromCurrentPortfolio"));
		return this;
	}

	public IllustrationPage waitAndDownloadIllustration(String illustrationName) throws InterruptedException {
		this.goToProcess();
		By by = By.xpath(".//tr[td[div[contains(text(),'" + illustrationName
				+ "')]]]//following-sibling::tr//*[@id='gwt-debug-BackgroundProcessTimer-downloadLink']");
		waitForElementVisible(by, Settings.WAIT_SECONDS * 24);
		clickElement(by);

		return this;
	}

	/**
	 * 
	 * */
	public IllustrationPage jumpToGITag() {

		JavascriptExecutor javascript = (JavascriptExecutor) webDriver;
		javascript.executeScript("scroll(0,0)");

		clickElement(By.xpath("//div[@class='TabLayoutPanelTabNormal' and .='General Information']"));

		return this;
	}

	/**
	 * 
	 * */
	public IllustrationPage jumpToFROTag() {

		scrollToTop();
		By by = By.xpath("//div[@class='TabLayoutPanelTabNormal' and .='Fixed Rate Options']");
		if (isElementVisible(by)) {
			clickElement(by);

		}

		return this;
	}

	/**
	 * @throws InterruptedException
	 * 
	 */
	public IllustrationPage jumpToDCATag() throws InterruptedException {

		this.scrollToTop();

		if (isElementVisible(By.xpath("//div[@class='TabLayoutPanelTabNormal' and .='Dollar Cost Averaging']"))) {
			clickElement(By.xpath("//div[@class='TabLayoutPanelTabNormal' and .='Dollar Cost Averaging']"));
		}

		return this;
	}

	/**
	 * 
	 * */
	public IllustrationPage jumpToVIOTag() {

		scrollToTop();
		clickElement(By.xpath("//div[@class='TabLayoutPanelTabNormal' and .='Variable Investment Option']"));

		return this;
	}

	/**
	 * 
	 * */
	public IllustrationPage jumpToARTag() {

		JavascriptExecutor javascript = (JavascriptExecutor) webDriver;
		javascript.executeScript("scroll(0,0)");

		clickElement(By.xpath("//div[@class='TabLayoutPanelTabNormal' and .='Asset Rebalancing']"));

		return this;
	}

	public IllustrationPage jumpToCurrentPortfolioTag() {
		scrollToTop();
		clickElement(By.xpath(".//div[.='Current Portfolio' and @class='TabLayoutPanelTabNormal']"));
		return this;
	}

	public IllustrationPage jumpToProposedPortfolioTag() {
		scrollToTop();
		clickElement(By.xpath(".//div[.='Proposed Portfolio' and @class='TabLayoutPanelTabNormal']"));
		return this;
	}

	public IllustrationPage jumpToSensitivityAnalysisTag() {
		scrollToTop();
		clickElement(By.xpath(".//div[.='Sensitivity Analysis' and @class='TabLayoutPanelTabNormal']"));
		return this;
	}

	public IllustrationPage jumpToLeveragedPortfolioTag() {
		scrollToTop();
		clickElement(By.xpath(".//div[.='Leveraged Portfolio' and @class='TabLayoutPanelTabNormal']"));
		return this;
	}

	/**
	 * @throws InterruptedException
	 * 
	 */
	public IllustrationPage clickSaveAndCalculate() throws InterruptedException {

		waitForElementVisible(By.id("gwt-debug-IllustrationSettingsView-saveBtn"), 30);
		clickElement(By.id("gwt-debug-IllustrationSettingsView-saveBtn"));

		for (int i = 0; i < 10; i++) {
			if (pageContainsStr("Illustration is already queued or it's processing")) {
				clickOkButtonIfVisible();
				wait(10);
				clickElement(By.id("gwt-debug-IllustrationSettingsView-saveBtn"));
			}
		}

		return this;
	}

	public IllustrationPage simpleSearch(String illustration) {

		sendKeysToElement(By.id("gwt-debug-IllustrationListView-searchBox"), illustration);
		clickElement(By.id("gwt-debug-IllustrationListView-searchBtn"));

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gwt-debug-IllustrationListView-searchBox")));

		return this;
	}

	public IllustrationPage downloadIllustrationByName(String illustration) {
		clickElement(By.xpath(".//td[.='" + illustration
				+ "']//following-sibling::td/*[@id='gwt-debug-IllustrationListPresenter-ownerReportLink']"));
		return this;
	}

}
