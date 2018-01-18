package org.sly.uitest.pageobjects.assetmanagement;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.pageobjects.clientsandaccounts.HoldingsPage;
import org.sly.uitest.pageobjects.commissioning.AdvisoryFeesPage;
import org.sly.uitest.settings.Settings;

/**
 * This class represents the Model Portfolios Overview Page and its Edit page,
 * which can be navigated by clicking 'Explore' -> 'Model Portfolios' (by
 * advisor) or 'Build' -> 'Model Portfolios'(by admin)
 * 
 * @author Lynne Huang
 * @date : 12 Aug, 2015
 * @company Prive Financial
 * 
 */

public class ModelPortfoliosPage extends AbstractPage {

	/**
	 * @param webDriver
	 */
	public ModelPortfoliosPage(WebDriver webDriver) {

		super();
		this.webDriver = webDriver;

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(60, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		try {
			scrollToTop();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gwt-debug-MyMainMaterialView-mainPanel")));

		} catch (Exception ex) {

			// wait.until(ExpectedConditions.visibilityOfElementLocated(By
			// .id("gwt-debug-MyMainMaterialView-mainPanel")));
		}
	}

	/**
	 * When the Model Portfolios overview page is navigated from the Explore by
	 * advisor, open a specific model portfolio; the page will be navigated to
	 * the model portfolio factsheet page
	 * 
	 * @param name
	 *            the model portfolio name
	 * 
	 * @return {@link ModelPortfoliosPage}
	 */
	public ModelPortfoliosPage goToModelPortfolioInfoPage(String name) throws InterruptedException {
		this.waitForWaitingScreenNotVisible();
		waitForElementVisible(By.xpath("//td[.='" + name + "']/div/a"), 30);

		clickElement(By.xpath("//td[.='" + name + "']/div/a"));
		// new Actions(webDriver).moveToElement(elem).perform();
		// elem.sendKeys("");
		// elem.sendKeys(Keys.RETURN);
		// clickElementAndWait3(By.xpath("//td[.='" + name + "']/div/a"));

		return new ModelPortfoliosPage(webDriver);

	}

	/**
	 * When the Model Portfolios overview page is navigated from the Build by
	 * admin, open a specific model portfolio; the page will be navigated to the
	 * model portfolio {@link HoldingsPage}
	 * 
	 * @param name
	 *            the model portfolio name
	 * 
	 * @return {@link ModelPortfoliosPage}
	 */
	public HoldingsPage goToModelPortfolio(String name) throws Exception {
		waitForElementVisible(By.xpath("//*[contains(text(),'" + name + "')]"), 30);
		clickElement(By.xpath("//*[contains(text(),'" + name + "')]"));
		// new Actions(webDriver).moveToElement(elem).perform();
		// elem.sendKeys("");
		// elem.sendKeys(Keys.RETURN);

		// clickElementAndWait3(By.xpath("//td[.='" + name + "']/div/a"));

		return new HoldingsPage(webDriver);
	}

	/**
	 * When the Model Portfolios overview page is navigated from the Build by
	 * admin, click the CREATE PORTFOLIO button
	 * 
	 * @return {@link ModelPortfoliosPage}
	 * @throws Exception
	 * 
	 */
	public ModelPortfoliosPage clickCreatePortfolioButton() {

		scrollToBottom();
		waitForElementVisible(By.id("gwt-debug-InvestorAccountTable-createModelPortBtn"), 10);
		clickElement(By.id("gwt-debug-InvestorAccountTable-createModelPortBtn"));
		// WebElement element =
		// webDriver.findElement(By.id("gwt-debug-InvestorAccountTable-createModelPortBtn"));
		// element.sendKeys(Keys.RETURN);

		return this;
	}

	/**
	 * When the Model Portfolios overview page is navigated from the Build by
	 * admin, click the pencil icon to edit the model portfolio
	 * 
	 * @param name
	 *            the model portfolio name
	 * @return {@link ModelPortfoliosPage}
	 * @throws Exception
	 * 
	 */
	public ModelPortfoliosPage editModelPortfolioByName(String name) throws Exception {
		wait(5);
		WebElement elem = webDriver.findElement(By.xpath("//td[.='" + name
				+ "']/following-sibling::td[table[@class='controlButtonPanel']]//button[@title='Edit']"));
		elem.sendKeys("");
		new Actions(webDriver).moveToElement(elem).perform();

		clickElementAndWait3(By.xpath("//td[.='" + name
				+ "']/following-sibling::td[table[@class='controlButtonPanel']]//button[@title='Edit']"));

		return this;
	}

	/**
	 * When the Model Portfolios overview page is navigated from the Build by
	 * admin, click the red-white minus icon to delete the model portfolio
	 * 
	 * @param name
	 *            the model portfolio name
	 * @return {@link ModelPortfoliosPage}
	 * @throws InterruptedException
	 * @throws Exception
	 * 
	 */
	public ModelPortfoliosPage deleteModelPortfolioByName(String name) throws InterruptedException {
		this.waitForWaitingScreenNotVisible();
		this.waitForElementVisible(By.xpath("//td[.='" + name + "']"), Settings.WAIT_SECONDS);
		assertTrue(pageContainsStr(name));

		WebElement elem = webDriver.findElement(By.xpath("//td[.='" + name
				+ "']/following-sibling::td[table[@class='controlButtonPanel']]//button[@title='Delete']"));
		// new Actions(webDriver).moveToElement(elem).build().perform();
		clickElement(By.xpath("//td[.='" + name
				+ "']/following-sibling::td[table[@class='controlButtonPanel']]//button[@title='Delete']"));

		clickElement(By.id("gwt-debug-CustomDialog-yesButton"));
		clickOkButtonIfVisible();
		return this;
	}

	/**
	 * When the Model Portfolios overview page is navigated from the Build by
	 * admin, after click the CREATE PORTFOLIO button or the pencil icon to edit
	 * the model portfolio, on the Edit Model Portfolio page, edit the model
	 * portfolio name
	 * 
	 * @param name
	 *            the model portfolio name
	 * @return {@link ModelPortfoliosPage}
	 * @throws Exception
	 * 
	 */
	public ModelPortfoliosPage editModelPortfolioName(String name) {

		sendKeysToElement(By.id("gwt-debug-ModelPortfolioEditView-modelName"), name);

		return this;
	}

	/**
	 * When the Model Portfolios overview page is navigated from the Build by
	 * admin, after click the CREATE PORTFOLIO button or the pencil icon to edit
	 * the portfolio, on the Edit Model Portfolio page, edit the model portfolio
	 * description
	 * 
	 * @param description
	 *            the model portfolio description
	 * @return {@link ModelPortfoliosPage}
	 * @throws Exception
	 * 
	 */
	public ModelPortfoliosPage editModelPortfolioDescription(String description) {

		sendKeysToElement(By.id("gwt-debug-ModelPortfolioEditView-strategyDesc"), description);

		return this;
	}

	/**
	 * When the Model Portfolios overview page is navigated from the Build by
	 * admin, after click the CREATE PORTFOLIO button or the pencil icon to edit
	 * the portfolio, on the Edit Model Portfolio page, edit the model portfolio
	 * start value
	 * 
	 * @param value
	 *            the model portfolio start value
	 * @return {@link ModelPortfoliosPage}
	 * @throws Exception
	 * 
	 */
	public ModelPortfoliosPage editModelPortfolioStartValue(String value) {

		sendKeysToElement(By.id("gwt-debug-ModelPortfolioEditView-portfolioStartValue"), value);

		return this;
	}

	/**
	 * When the Model Portfolios overview page is navigated from the Build by
	 * admin, after click the CREATE PORTFOLIO button or the pencil icon to edit
	 * the portfolio, on the Edit Model Portfolio page, edit the model portfolio
	 * currency
	 * 
	 * @param currency
	 *            the model portfolio currency
	 * @return {@link ModelPortfoliosPage}
	 * @throws Exception
	 * 
	 */
	public ModelPortfoliosPage editModelPortfolioCurrency(String currency) {

		selectFromDropdown(By.id("gwt-debug-ModelPortfolioEditView-modelCurrencyList"), currency);

		return this;
	}

	/**
	 * When the Model Portfolios overview page is navigated from the Build by
	 * admin, after click the CREATE PORTFOLIO button or the pencil icon to edit
	 * the portfolio, on the Edit Model Portfolio page, add the model portfolio
	 * platforms
	 * 
	 * @param platform
	 *            the model portfolio platform
	 * @return {@link ModelPortfoliosPage}
	 * @throws Exception
	 * 
	 */
	public ModelPortfoliosPage editModelPortfolioAddPlatform(String platform) {

		selectFromDropdown(
				By.xpath(
						"//td[contains(text(), 'Provider Platform')]/following-sibling::td[1]//select[@id='gwt-debug-PairedListBoxSelector-sourceList']"),
				platform);

		clickElement(By.xpath(
				"//td[contains(text(), 'Provider Platform')]/following-sibling::td[1]//img[@id='gwt-debug-PairedListBoxSelector-addImg']"));

		return this;
	}

	/**
	 * When the Model Portfolios overview page is navigated from the Build by
	 * admin, after click the CREATE PORTFOLIO button or the pencil icon to edit
	 * the portfolio, on the Edit Model Portfolio page, remove the model
	 * portfolio platform
	 * 
	 * @param platform
	 *            the model portfolio platform
	 * @return {@link ModelPortfoliosPage}
	 * @throws Exception
	 * 
	 */
	public ModelPortfoliosPage editModelPortfolioRemovePlatform(String platform) {

		selectFromDropdown(
				By.xpath(
						".//*[@id='gwt-debug-ModelPortfolioEditView-providerPlatformHover']/parent::td/following-sibling::td[1]//select[@id='gwt-debug-PairedListBoxSelector-targetList']"),
				platform);

		clickElement(By.xpath(
				".//*[@id='gwt-debug-ModelPortfolioEditView-providerPlatformHover']/parent::td/following-sibling::td[1]//img[@id='gwt-debug-PairedListBoxSelector-removeImg']"));

		return this;
	}

	/**
	 * When the Model Portfolios overview page is navigated from the Build by
	 * admin, after click the CREATE PORTFOLIO button or the pencil icon to edit
	 * the portfolio, on the Edit Model Portfolio page, edit the model portfolio
	 * visibility
	 * 
	 * @param visibility
	 *            the model portfolio visibility
	 * @return {@link ModelPortfoliosPage}
	 * @throws Exception
	 * 
	 */
	public ModelPortfoliosPage editModelPortfolioVisibility(String visibility) {

		selectFromDropdown(By.id("gwt-debug-ModelPortfolioEditView-modelVisibility"), visibility);

		return this;
	}

	/**
	 * When the Model Portfolios overview page is navigated from the Build by
	 * admin, after click the CREATE PORTFOLIO button or the pencil icon to edit
	 * the portfolio, on the Edit Model Portfolio page, add the model portfolio
	 * benchmark
	 * 
	 * @param benchmark
	 *            the model portfolio benchmark
	 * @return {@link ModelPortfoliosPage}
	 * @throws InterruptedException
	 * @throws Exception
	 * 
	 */
	public ModelPortfoliosPage editModelPortfolioAddBenchmark(String benchmark) throws InterruptedException {

		// wait(Settings.WAIT_SECONDS);
		// try {
		// editModelPortfolioRemoveBenchmark(benchmark);
		// } catch(Exception ex) {
		// }
		// wait(Settings.WAIT_SECONDS);

		selectFromDropdown(
				By.xpath(
						"//td[contains(text(), 'Benchmark')]/following-sibling::td[1]//select[@id='gwt-debug-PairedListBoxSelector-sourceList']"),
				benchmark);
		// wait for benchmark to be selected
		wait(Settings.WAIT_SECONDS);
		clickElement(By.xpath(
				"//td[contains(text(), 'Benchmark')]/following-sibling::td[1]//img[@id='gwt-debug-PairedListBoxSelector-addImg']"));

		return this;
	}

	/**
	 * When the Model Portfolios overview page is navigated from the Build by
	 * admin, after click the CREATE PORTFOLIO button or the pencil icon to edit
	 * the portfolio, on the Edit Model Portfolio page, remove the model
	 * portfolio benchmark
	 * 
	 * @param benchmark
	 *            the model portfolio benchmark
	 * @return {@link ModelPortfoliosPage}
	 * @throws Exception
	 * 
	 */
	public ModelPortfoliosPage editModelPortfolioRemoveBenchmark(String benchmark) {

		selectFromDropdown(
				By.xpath(
						"//td[.=' Benchmark ']/following-sibling::td[1]//select[@id='gwt-debug-PairedListBoxSelector-targetList']"),
				benchmark);

		clickElement(By.xpath(
				"//td[.=' Benchmark ']/following-sibling::td[1]//img[@id='gwt-debug-PairedListBoxSelector-removeImg']"));

		return this;
	}

	/**
	 * When the Model Portfolios overview page is navigated from the Build by
	 * admin, after click the CREATE PORTFOLIO button or the pencil icon to edit
	 * the portfolio, on the Edit Model Portfolio page, click the SAVE button;
	 * the page will be navigated to the {@link HoldingsPage}
	 * 
	 * @return {@link HoldingsPage}
	 * @throws InterruptedException
	 */
	public HoldingsPage clickSaveButton() throws InterruptedException {

		clickElement(By.id("gwt-debug-ModelPortfolioEditView-submitBtn"));
		wait(Settings.WAIT_SECONDS);
		try {
			waitForElementVisible(By.id("gwt-debug-PortfolioAllocationView-mainPanel"), 60);
		} catch (TimeoutException e) {
			if (isElementVisible(By.id("gwt-debug-PortfolioAllocationView-mainPanel"))) {
				clickElementByKeyboard(By.id("gwt-debug-ModelPortfolioEditView-submitBtn"));
			}
		}
		this.waitForWaitingScreenNotVisible();
		return new HoldingsPage(webDriver);
	}

	/**
	 * When the Model Portfolios overview page is navigated from the Build by
	 * admin, after click the CREATE PORTFOLIO button or the pencil icon to edit
	 * the portfolio, on the Edit Model Portfolio page, click the CANCEL button;
	 * the page will be navigated back to the {@link ModelPortfoliosPage}
	 * 
	 * @return {@link HoldingsPage}
	 */
	public ModelPortfoliosPage clickCancelButton() {
		waitForElementVisible(By.id("gwt-debug-ModelPortfolioEditView-cancelBtn"), 30);
		WebElement element = webDriver.findElement(By.id("gwt-debug-ModelPortfolioEditView-cancelBtn"));
		element.sendKeys(Keys.RETURN);
		// clickElement(By.id("gwt-debug-ModelPortfolioEditView-cancelBtn"));
		return this;
	}

	/**
	 * Click Set Fees to set AUM fee while creating model portfolio
	 * 
	 * @return {@AdvisoryFeesPage}
	 */
	public AdvisoryFeesPage clickSetAumFees() {
		clickElement(By.id("gwt-debug-ModelPortfolioEditView-aumFeeSettingLink"));
		return new AdvisoryFeesPage(webDriver, this.getClass());
	}

	public ModelPortfoliosPage editModelPortfolioRemoveBenchmarkIfExist(String benchmark) {

		try {
			selectFromDropdown(
					By.xpath(
							"//td[.=' Benchmark ']/following-sibling::td[1]//select[@id='gwt-debug-PairedListBoxSelector-targetList']"),
					benchmark);
			clickElement(By.xpath(
					"//td[.=' Benchmark ']/following-sibling::td[1]//img[@id='gwt-debug-PairedListBoxSelector-removeImg']"));
		} catch (Exception ex) {
			System.out.println("Option: " + benchmark + " not present");
			ex.printStackTrace();
		}
		return this;
	}

	public ModelPortfoliosPage clickManageModelPortfolios() throws InterruptedException {

		scrollToTop();
		waitForElementVisible(By.id("gwt-debug-InvestorModelPortfolioOverviewView-linkToManageModelPortfolios"), 300);
		// WebElement element = webDriver
		// .findElement(By
		// .id("gwt-debug-InvestorModelPortfolioOverviewView-linkToManageModelPortfolios"));
		// element.click();
		clickElement(By.id("gwt-debug-InvestorModelPortfolioOverviewView-linkToManageModelPortfolios"));
		try {
			waitForElementVisible(By.id("gwt-debug-ModelPortfolioOverviewView-contentPanel"),
					Settings.WAIT_SECONDS * 2);
			this.waitForWaitingScreenNotVisible();
		} catch (TimeoutException e) {

		}
		// wait(3);mor
		// element.sendKeys(Keys.RETURN);
		return this;
	}

	public ModelPortfoliosPage removeProvider(String provider) throws InterruptedException {

		if (provider.equals("all")) {
			int size = getSizeOfElements(By.xpath(
					"//td[contains(text(), 'Provider Platform')]/following-sibling::td//select[@id='gwt-debug-PairedListBoxSelector-targetList']/option"));
			for (int i = 0; i < size; i++) {
				clickElement(By.xpath(".//*[@id='gwt-debug-PairedListBoxSelector-targetList']/option"));
				wait(1);
				clickElement(By.id("gwt-debug-PairedListBoxSelector-removeImg"));
			}

		}
		return this;
	}

	/**
	 * this method is to click the pdf image next to "Download Fact Sheet"
	 * 
	 * @return {@link ModelPortfoliosPage}
	 */
	public ModelPortfoliosPage clickDownloadFactsheetButton() {
		clickElement(By.id("gwt-debug-InvestorModelPortfolioView-pdfReport"));
		waitForElementVisible(By.id("gwt-debug-ExportSearchResultPopupView-fileNameTextBox"), 5);
		return this;
	}

	/**
	 * click proceed button for export model portfolio file
	 */
	public void clickProceedButtonForReporting() {
		clickElement(By.id("gwt-debug-ExportSearchResultPopupView-proceedButton"));

	}

	/**
	 * 
	 */
	public ModelPortfoliosPage editNameOfExportFile(String name) {

		waitForElementVisible(By.id("gwt-debug-ExportSearchResultPopupView-fileNameTextBox"), 10);

		sendKeysToElement(By.id("gwt-debug-ExportSearchResultPopupView-fileNameTextBox"), name);
		return this;
	}

	/**
	 * click add button by name to add model portfolio to account
	 * 
	 * @param modelPortfolio
	 * @return
	 */
	public ModelPortfoliosPage clickAddButtonByName(String modelPortfolio) {
		scrollToTop();
		waitForElementVisible(
				By.xpath(".//td[*[*[contains(text(),'" + modelPortfolio
						+ "')]]]//following-sibling::td//button[@id='gwt-debug-InvestorModelPortfolioOverviewPresenter-linkImage']"),
				30);
		clickElement(By.xpath(".//td[*[*[contains(text(),'" + modelPortfolio
				+ "')]]]//following-sibling::td//button[@id='gwt-debug-InvestorModelPortfolioOverviewPresenter-linkImage']"));
		return this;
	}

	/**
	 * 
	 * @param account
	 * @return
	 */
	public ModelPortfoliosPage addModelPortfolioToAccount(String account) {
		selectFromDropdown(By.id("gwt-debug-InvestorModelPortfolioOverviewPresenter-investorAccountListBox"), account);
		clickElement(By.id("gwt-debug-CustomDialog-yesButton"));

		clickOkButtonIfVisible();
		return this;
	}
}
