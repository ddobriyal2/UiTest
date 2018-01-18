package org.sly.uitest.pageobjects.clientsandaccounts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.pageobjects.investments.InvestmentsPage;
import org.sly.uitest.settings.Settings;

/**
 * 
 * This class represents the History Page (tab) of an account, which can be
 * navigated by clicking 'Clients' -> 'Account Overview' -> choose any account
 * -> 'History (tab)' or 'Build' -> 'Model Portfolios' -> choose any model
 * portfolio -> 'History (tab)'
 * 
 * URL:
 * "192.168.1.104:8080/SlyAWS/?locale=en#portfolioOverviewHistory;investorAccountKey=32332;valueType=2"
 * 
 * @author Lynne Huang
 * @date : 19 Aug, 2015
 * @company Prive Financial
 * 
 */
public class HistoryPage extends AbstractPage {

	public HistoryPage(WebDriver webDriver) throws InterruptedException {

		super();
		this.webDriver = webDriver;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(60, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);
		handleAlert();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[contains(@id,'contentPanel')]")));

	}

	/**
	 * select history source in History page
	 * 
	 * @param source
	 *            source of history(Datafeed/System)
	 * @return {@link HistoryPage}
	 * @throws InterruptedException
	 */
	public HistoryPage editHistorySource(String source) throws InterruptedException {

		selectFromDropdown(By.id("gwt-debug-TransactionListView-tranSourceTypeListBox"), source);

		// wait for refreshing
		wait(Settings.WAIT_SECONDS);
		return this;
	}

	/**
	 * edit the type of transaction
	 * 
	 * @param type
	 * @return {@link HistoryPage}
	 * @throws InterruptedException
	 */
	public HistoryPage editTransactionType(String type) throws InterruptedException {
		selectFromDropdown(By.id("gwt-debug-TransactionListView-tranTypeListBox"), type);

		if (isElementVisible(By.id("gwt-debug-CustomDialog-okButton"))) {
			clickElement(By.id("gwt-debug-CustomDialog-okButton"));
		}

		clickYesButtonIfVisible();
		wait(3);

		return this;
	}

	/**
	 * When the reallocation is confirmed, the page will be navigated to the
	 * History page, and this method is to confirm the updated history status
	 * changes to Completed, and then the test can be executed
	 * 
	 * @return {@link HoldingsPage}
	 * @throws InterruptedException
	 * 
	 */
	public HistoryPage confirmHistoryStatus() throws InterruptedException {

		this.refreshPage();

		this.goToHoldingsPage().goToTransactionHistoryPage();

		// FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver)
		// .withTimeout(30, TimeUnit.SECONDS)
		// .pollingEvery(2, TimeUnit.SECONDS)
		// .ignoring(org.openqa.selenium.NoSuchElementException.class);
		try {
			waitForElementVisible(By.xpath(".//*[contains(@id,'statusLabel')]"), 30);
		} catch (TimeoutException e) {

		}

		if (isElementVisible(By.id("gwt-debug-TransactionListView-autoRefreshCheckbox-input"))) {

			WebElement elem = webDriver.findElement(By.id("gwt-debug-TransactionListView-autoRefreshCheckbox-input"));

			setCheckboxStatus2(elem, true);
		}

		wait(60);

		log("check History Status: In");

		if (isElementVisible(By.id("gwt-debug-RebalancingHistorySingleView-statusLabel"))) {
			for (int i = 0; i < 3; i++) {
				try {
					waitForTextVisible(By.id("gwt-debug-RebalancingHistorySingleView-statusLabel"), "Completed");
				} catch (TimeoutException e) {
					this.refreshPage();
					this.goToHoldingsPage().goToTransactionHistoryPage();

					waitForTextVisible(By.id("gwt-debug-RebalancingHistorySingleView-statusLabel"), "Completed");
				}

			}

			// assertEquals(getTextByLocator(By.id("gwt-debug-RebalancingHistorySingleView-statusLabel"))
			// ,"Completed");
		} else {
			for (int i = 0; i < 3; i++) {
				if (isElementVisible(By.id("gwt-debug-TransactionPortfolioView-statusLabel"))) {
					try {
						waitForTextVisible(By.id("gwt-debug-TransactionPortfolioView-statusLabel"), "Completed");
					} catch (TimeoutException e) {
						this.refreshPage();
						waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 30);

						this.goToHoldingsPage().goToTransactionHistoryPage();

						waitForTextVisible(By.id("gwt-debug-TransactionPortfolioView-statusLabel"), "Completed");
					}

					// assertEquals(getTextByLocator(By.id("gwt-debug-TransactionPortfolioView-statusLabel"))
					// ,"Completed");
				}
			}

			if (isElementVisible(By.id("gwt-debug-TransactionPortfolioSwitchView-statusLabel"))) {
				try {
					waitForTextVisible(By.id("gwt-debug-TransactionPortfolioSwitchView-statusLabel"), "Filled fully");

				} catch (TimeoutException e) {
					this.refreshPage();
					this.goToHoldingsPage();

					waitForTextVisible(By.id("gwt-debug-TransactionPortfolioSwitchView-statusLabel"), "Filled fully");
				}

			}
		}

		// try{
		// wait.until(ExpectedConditions.textToBePresentInElementLocated(
		// By.id("gwt-debug-RebalancingHistorySingleView-statusLabel"),
		// "Completed"));
		// }catch(Exception e){
		// wait.until(ExpectedConditions.textToBePresentInElementLocated(
		// By.id("gwt-debug-TransactionPortfolioView-statusLabel"),
		// "Completed"));
		// }

		log("check Status: Out");

		return this;

	}

	/**
	 * When create a new model portfolio, the page is navigated to the history
	 * page, click the EDIT HISTORY button
	 * 
	 * Example: Model Portfolio -> History tab -> EDIT HISTORY button
	 * 
	 * @return {@link HistoryPage}
	 */
	public HistoryPage editModelPortfolioHistory() {

		clickElement(By.id("gwt-debug-RebalancingHistoryListView-editHistoryBtn"));

		clickElement(By.id("gwt-debug-CustomDialog-yesButton"));

		return this;
	}

	/**
	 * After confirm and proceed to edit the history, the edit(pencil) icon will
	 * be displayed for each history. Click the pencil icon to edit the specific
	 * history according to the date
	 * 
	 * Example: Model Portfolio -> History tab -> EDIT HISTORY button -> edit
	 * icon
	 * 
	 * @param date
	 * 
	 * @return {@link HistoryPage}
	 */
	public HistoryPage editModelPortfolioHistoryByDate(String date) {

		clickElement(By.xpath("//td[div[.='" + date
				+ "']]/following-sibling::td[2]//img[@id='gwt-debug-RebalancingHistoryListView-editImg']"));

		return this;
	}

	/**
	 * On the pop-up Edit Allocation dialog triggered by editing the history,
	 * click the SAVE button
	 * 
	 * Example: Model Portfolio -> History tab -> EDIT HISTORY button -> SAVE
	 * button
	 * 
	 * @return {@link HistoryPage}
	 */
	public HistoryPage saveModelPortfolioHistory() {

		clickElement(By.id("gwt-debug-RebalancingHistoryListView-saveBtn"));

		clickElement(By.id("gwt-debug-CustomDialog-yesButton"));

		clickElement(By.id("gwt-debug-CustomDialog-okButton"));

		return this;
	}

	/**
	 * After confirm and proceed to edit the history, click the green plus icon
	 * beside "Add new historical rebalancing" to add new historical rebalancing
	 * 
	 * Example: Model Portfolio -> History tab -> EDIT HISTORY button -> Add new
	 * historical rebalancing icon
	 * 
	 * @return {@link HoldingsPage}
	 */
	public HistoryPage addModelPortfolioNewHistoricalRebalancing() {

		clickElement(By.id("gwt-debug-RebalancingHistoryListView-addLabel"));

		return this;
	}

	/**
	 * After click "Add new historical rebalancing", on the pop-up Edit
	 * Allocation page, edit the allocation date that is located on the top left
	 * 
	 * Example: Model Portfolio -> History tab -> EDIT HISTORY button -> Add new
	 * historical rebalancing icon -> Allocation Date edit box
	 * 
	 * @param date
	 *            the allocation date
	 * 
	 * @return {@link HistoryPage}
	 */
	public HistoryPage editModelPortfolioAllocationDate(String date) {

		sendKeysToElement(By.id("gwt-debug-AllocationEditDialog-allocationDateTextBox"), date);

		return this;
	}

	/**
	 * After click "Add new historical rebalancing", on the pop-up Edit
	 * Allocation page, you can click ADD INVESTMENT button, and the page will
	 * be navigated to {@link InvestmentsPage}
	 * 
	 * @return {@link InvestmentsPage}
	 */
	public InvestmentsPage addModelPortfolioInvestments() {

		clickElement(By.id("gwt-debug-AllocationEditDialog-addInvestmentBtn"));

		return new InvestmentsPage(webDriver, HistoryPage.class);
	}

	/**
	 * After click REALLOCATE button, the page is in the edit allocation mode,
	 * and then it's ready to input the new allocation for the given investment
	 * 
	 * @param investmentName
	 *            the name of the investment to be reallocated
	 * @param allocation
	 * 
	 * @return {@link HoldingsPage}
	 */
	public HistoryPage setNewAllocationForInvestment(String investmentName, String allocation) {

		sendKeysToElement(
				By.xpath("//td[.='" + investmentName
						+ "']/following-sibling::td[//input[@id='gwt-debug-TextBoxPercentageSpinner-percentField']]//input"),
				allocation + "\n");

		return this;
	}

	/**
	 * After click "Add new historical rebalancing", on the pop-up Edit
	 * Allocation page, click the SAVE button
	 * 
	 * Example: Model Portfolio -> History tab -> EDIT HISTORY button -> Add new
	 * historical rebalancing icon -> SAVE button
	 * 
	 * @return {@link HistoryPage}
	 */
	public HistoryPage clickModelPortfolioAllocationSaveButton() {
		waitForElementVisible(By.id("gwt-debug-AllocationEditDialog-saveBtn"), 30);
		// element.sendKeys(Keys.RETURN);
		clickElement(By.id("gwt-debug-AllocationEditDialog-saveBtn"));

		return this;
	}

	/**
	 * On the model portfolio history page, after click EDIT HISTORY button,
	 * click the SAVE button
	 * 
	 * Example: Model portfolio -> History tab -> EDIT HISTORY button -> SAVE
	 * button
	 * 
	 * @return {@link HoldingsPage}
	 * 
	 * @throws InterruptedException
	 * 
	 */
	public HoldingsPage clickModelPortfolioRebalancingSaveButton() throws InterruptedException {
		waitForElementVisible(By.id("gwt-debug-RebalancingHistoryListView-saveBtn"), 30);

		clickElement(By.id("gwt-debug-RebalancingHistoryListView-saveBtn"));

		clickYesButtonIfVisible();

		try {

			FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(60, TimeUnit.SECONDS)
					.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gwt-debug-CustomDialog-okButton")));

			clickOkButtonIfVisible();
		} catch (Exception e) {
			// TODO: handle exception
		}

		try {
			this.confirmHistoryStatus();
		} catch (Exception e) {

			// TODO: handle exception
		}
		return new HoldingsPage(webDriver);

		// wait(Settings.WAIT_SECONDS);

		// return this;
	}

	/**
	 * On the account history page, which has Manual Portfolio History section,
	 * click the ADD button to add new portfolio history
	 * 
	 * Example: Account -> History tab -> ADD button
	 * 
	 * @return {@link HistoryPage}
	 */
	public HistoryPage addManualPortfolioHistory() {

		clickElement(By.id("gwt-debug-ManualPortfolioHistoryView-addButton"));

		return this;

	}

	/**
	 * On the pop-up Edit Manual Portfolio Snapshot page that is triggered by
	 * adding manual portfolio history, edit the date
	 * 
	 * Example: Account -> History tab -> ADD button -> Date edit box
	 * 
	 * @param date
	 * @return {@link HistoryPage}
	 */
	public HistoryPage editManualHistoryDate(String date) {

		sendKeysToElement(By.id("gwt-debug-ManualPortfolioHistoryEditPopupView-effectiveDate"), date);

		return this;
	}

	/**
	 * On the pop-up Edit Manual Portfolio Snapshot page that is triggered by
	 * adding manual portfolio history, click the green plus icon under the
	 * Assets section
	 * 
	 * Example: Account -> History tab -> ADD button -> add asset icon
	 * 
	 * @return {@link HistoryPage}
	 */
	public HistoryPage clickManualHistoryAddAssetIcon() {

		clickElement(By.id("gwt-debug-ManualPortfolioHistoryEditPopupView-addAssetButton"));

		return this;
	}

	/**
	 * On the pop-up Edit Manual Portfolio Snapshot page that is triggered by
	 * adding manual portfolio history, click the red-white minus icon under the
	 * Assets section
	 * 
	 * Example: Account -> History tab -> ADD button -> delete asset icon
	 * 
	 * @return {@link HistoryPage}
	 */
	public HistoryPage clickManualHistoryDeleteAssetIcon() {

		if (isElementDisplayed(By.id("gwt-debug-ManualAssetWidgetView-deleteImg"))) {
			clickElement(By.id("gwt-debug-ManualAssetWidgetView-deleteImg"));
		}

		return this;
	}

	/**
	 * On the pop-up Edit Manual Portfolio Snapshot page that is triggered by
	 * adding manual portfolio history, after 'clickAddAssetIcon()', click the
	 * pencil icon to select asset; the page will be navigated to the Add Ticker
	 * page
	 * 
	 * Example: Account -> History tab -> ADD button -> add asset icon -> pencil
	 * icon
	 * 
	 * @return {@link DetailEditPage}
	 */
	public DetailEditPage clickManualHistorySelectAssetIcon() {

		clickElement(By.id("gwt-debug-ManualAssetWidgetView-addImg"));

		return new DetailEditPage(webDriver, HistoryPage.class);
	}

	/**
	 * On the pop-up Edit Manual Portfolio Snapshot page that is triggered by
	 * adding manual portfolio history, under the Assets section, edit the
	 * quantity of the asset
	 * 
	 * Example: Account -> History tab -> ADD button -> add asset icon ->
	 * Quantity edit box
	 * 
	 * @param quantity
	 * @return {@link HistoryPage}
	 * 
	 */
	public HistoryPage editManualHistoryAssetQuantity(String quantity) {

		waitForElementVisible(By.id("gwt-debug-ManualAssetWidgetView-quantityTextBox"), 30);
		try {
			wait(1);
		} catch (InterruptedException e) {

		}
		sendKeysToElement(By.id("gwt-debug-ManualAssetWidgetView-quantityTextBox"), quantity);

		return this;
	}

	/**
	 * On the pop-up Edit Manual Portfolio Snapshot page that is triggered by
	 * adding manual portfolio history, on the bottom of the page, click the
	 * blue refresh icon to calculate the portfolio value
	 * 
	 * Example: Account -> History tab -> ADD button -> Refresh icon
	 * 
	 * @return {@link HistoryPage}
	 * 
	 */
	public HistoryPage clickManualHistoryRefreshIcon() {

		clickElement(By.id("gwt-debug-ManualPortfolioHistoryEditPopupView-recalcButton"));

		return this;
	}

	/**
	 * On the pop-up Edit Manual Portfolio Snapshot page that is triggered by
	 * adding manual portfolio history, click the SAVE button
	 * 
	 * Example: Account -> History tab -> ADD button -> SAVE button
	 * 
	 * @return {@link HistoryPage}
	 * @throws InterruptedException
	 * 
	 */
	public HistoryPage clickManualHistorySaveButton() throws InterruptedException {

		clickElement(By.id("gwt-debug-ManualPortfolioHistoryEditPopupView-submitButton"));
		clickYesButtonIfVisible();
		clickOkButtonIfVisible();

		return this;
	}

	/**
	 * 
	 * On the Manual Portfolio History page, click the red minus icon to delete
	 * the history with the given date and portfolio value
	 * 
	 * Example: Account(Generali Vision - NEW) -> History tab -> delete icon
	 * 
	 * @param date
	 * @param portfolio
	 * 
	 * @return {@link HistoryPage}
	 */
	public HistoryPage clickManualHistoryDeleteButton(String date, String portfolioValue) {

		clickElement(By.xpath("//td[.='" + date + "']/following-sibling::td[.='" + portfolioValue
				+ "']/following-sibling::td[4]/button[@title='Delete']"));

		clickYesButtonIfVisible();

		return this;
	}

	/**
	 * click Edit History button in history page
	 * 
	 * @return {@link HistoryPage}
	 */
	public HistoryPage clickEditHistoryButton() {

		waitForElementVisible(By.id("gwt-debug-TransactionListView-editHistoryBtn"), 10);

		clickElement(By.id("gwt-debug-TransactionListView-editHistoryBtn"));

		if (!isElementVisible(By.id("gwt-debug-TransactionListView-cancelBtn"))) {
			clickElementByKeyboard(By.id("gwt-debug-TransactionListView-editHistoryBtn"));
		}

		return this;
	}

	/**
	 * click Exit Edit Mode Button in history page
	 * 
	 * @return {@link HistoryPage}
	 */
	public HistoryPage clickExitEditModeButton() {

		waitForElementVisible(By.id("gwt-debug-TransactionListView-cancelBtn"), 30);

		clickElementByKeyboard(By.id("gwt-debug-TransactionListView-cancelBtn"));

		try {
			waitForElementVisible(By.id("gwt-debug-CustomDialog-yesButton"), 3);
			clickElement(By.id("gwt-debug-CustomDialog-yesButton"));
			clickOkButtonIfVisible();
		} catch (TimeoutException e) {

		}

		scrollToTop();

		return this;
	}

	/**
	 * click Add Historical transaction in history page
	 * 
	 * @param type
	 *            type of transaction to add
	 * @param theClass
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public <T> T addHistoricalTransaction(String type, Class<?> theClass) throws Exception {

		waitForElementVisible(By.id("gwt-debug-TransactionListView-addLabel"), 10);

		clickElement(By.id("gwt-debug-TransactionListView-addLabel"));

		waitForElementVisible(By.id("gwt-debug-TransactionAddHistoricalTypeSelectionPopupView-proceedButton"), 10);

		clickElement(By
				.xpath(".//div[@id='gwt-debug-TransactionAddHistoricalTypeSelectionPopupView-explanation']//following-sibling::table//label[contains(text(),'"
						+ type + "')]"));

		clickElement(By.id("gwt-debug-TransactionAddHistoricalTypeSelectionPopupView-proceedButton"));

		return (T) theClass.getDeclaredConstructor(WebDriver.class).newInstance(webDriver);
	}

	/**
	 * change the period for displaying transactions history
	 * 
	 * @param period
	 *            period for displaying transaction history
	 * @return
	 * @throws InterruptedException
	 */
	public HistoryPage editShowTransactionsHistoryFor(String period) throws InterruptedException {

		waitForElementVisible(By.id("gwt-debug-TransactionListView-lastxdaysListBox"), 10);
		selectFromDropdown(By.id("gwt-debug-TransactionListView-lastxdaysListBox"), period);
		try {
			waitForElementVisible(By.id("gwt-debug-TransactionOtherView-descLabel"), 30);
		} catch (TimeoutException e) {

		}
		return this;
	}

	/**
	 * 
	 * @param transactionName
	 *            (you may consider input type or name of transaction)
	 * @param returnClass
	 *            The expected page object class to open
	 * @return
	 * @throws InterruptedException
	 */
	public LinkTransactionPage clickLinkSystemTransactionButton(String transactionName, Class<?> returnClass)
			throws InterruptedException {

		waitForElementVisible(
				By.xpath("(.//*[contains(text(),'" + transactionName
						+ "')]/parent::td/following-sibling::td//*[contains(@id,'transactionLinkBroken') and not(@aria-hidden='true')])[1]"),
				60);

		clickElement(By.xpath("(.//*[contains(text(),'" + transactionName
				+ "')]/parent::td/following-sibling::td//*[contains(@id,'transactionLinkBroken') and not(@aria-hidden='true')])[1]"));

		return new LinkTransactionPage(webDriver, returnClass);

	}

	/**
	 * Unlink the system transaction
	 * 
	 * @param transactionName
	 *            name of the system transaction
	 * @return {@link HistoryPage}
	 * @throws InterruptedException
	 */
	public LinkTransactionPage clickUnlinkSystemTransactionButton(String transactionName) throws InterruptedException {

		waitForElementVisible(
				By.xpath(".//*[contains(text(),'" + transactionName
						+ "')]/parent::td/following-sibling::td//*[@id='gwt-debug-TransactionDetailsView-transactionLink' and not(@aria-hidden='true')]"),
				10);

		clickElement(By.xpath(".//*[contains(text(),'" + transactionName
				+ "')]/parent::td/following-sibling::td//*[@id='gwt-debug-TransactionDetailsView-transactionLink' and not(@aria-hidden='true')]"));

		return new LinkTransactionPage(webDriver, HistoryPage.class);

	}

	/**
	 * edit the date range for showing transactions
	 * 
	 * @param startDate
	 * @param endDate
	 * @return {@link HistoryPage}
	 */
	public HistoryPage editDateRangeToShowTransaction(String startDate, String endDate) {

		waitForElementVisible(By.id("gwt-debug-TransactionListView-fetchButton"), 10);

		sendKeysToElement(By.id("gwt-debug-TransactionListView-fromDateTextBox"), startDate);

		sendKeysToElement(By.id("gwt-debug-TransactionListView-toDateTextBox"), endDate);

		try {
			if (!getTextByLocator(By.id("gwt-debug-TransactionListView-fromDateTextBox")).equals(startDate)) {
				sendKeysToElement(By.id("gwt-debug-TransactionListView-fromDateTextBox"), startDate);
			}
		} catch (NullPointerException e) {
			sendKeysToElement(By.id("gwt-debug-TransactionListView-fromDateTextBox"), startDate);

		}

		try {
			if (!getTextByLocator(By.id("gwt-debug-TransactionListView-toDateTextBox")).equals(startDate)) {
				sendKeysToElement(By.id("gwt-debug-TransactionListView-toDateTextBox"), endDate);
			}

		} catch (NullPointerException e) {
			sendKeysToElement(By.id("gwt-debug-TransactionListView-toDateTextBox"), endDate);
		}

		clickElement(By.id("gwt-debug-TransactionListView-fetchButton"));
		clickYesButtonIfVisible();
		return this;
	}

	/**
	 * decide whether to check the checkbox for Auto refresh
	 * 
	 * @param toCheck
	 *            to check the auto refresh checkbox or not
	 * @return {@link HistoryPage}
	 * @throws InterruptedException
	 */
	public HistoryPage clickAutoRefreshBox(boolean toCheck) throws InterruptedException {

		waitForElementVisible(By.id("gwt-debug-TransactionListView-autoRefreshCheckbox-input"), 10);
		WebElement elem = webDriver.findElement(By.id("gwt-debug-TransactionListView-autoRefreshCheckbox-input"));

		setCheckboxStatus2(elem, toCheck);

		wait(Settings.WAIT_SECONDS);

		return this;
	}

	/**
	 * check the checkbox for Show only unmatched transactions
	 * 
	 * @param show
	 *            whether to show only unmatched transaction
	 * @return {@link HistoryPage}
	 */
	public HistoryPage checkShowOnlyUnmatchedBox(boolean show) {

		WebElement checkbox = webDriver
				.findElement(By.id("gwt-debug-TransactionListView-showUnmatchedOnlyCheckbox-input"));

		setCheckboxStatus2(checkbox, show);

		return this;
	}

	/**
	 * check the checkbox for Include Cancel transactions
	 * 
	 * @param include
	 *            whether to include Cancel transactions
	 * @return {@link HistoryPage}
	 */
	public HistoryPage checkIncludeCancelTransactionBox(boolean include) {

		WebElement checkbox = webDriver
				.findElement(By.id("gwt-debug-TransactionListView-includeTransactionCancelsCheckbox-input"));

		setCheckboxStatus2(checkbox, include);

		return this;
	}

	@SuppressWarnings("unchecked")
	public <T> T clickEditTransactionButtonByName(String transaction, Class<?> theClass) throws Exception {
		clickElement(By.xpath("(.//td[*[contains(text(),'" + transaction
				+ "')]]/following-sibling::td//*[contains(@id,'editTransactionImg') and not(@style='display: none;')])[1]"));
		if (getSizeOfElements(By.id("gwt-debug-CustomDialog-okButton")) > 0) {
			clickElement(By.id("gwt-debug-CustomDialog-okButton"));
		}
		return (T) theClass.getDeclaredConstructor(WebDriver.class).newInstance(webDriver);
	}

	public HistoryPage clickDeleteTransactionButtonByName(String transaction) {
		waitForElementVisible(By.xpath("(.//td[*[contains(text(),'" + transaction
				+ "')]]/following-sibling::td//*[contains(@id,'deleteTransactionImg')])[1]"), 10);
		try {
			clickElement(By.xpath("(.//td[*[contains(text(),'" + transaction
					+ "')]]/following-sibling::td//*[contains(@id,'deleteTransactionImg')])[1]"));
		} catch (TimeoutException e) {
			clickElementByKeyboard(By.xpath("(.//td[*[contains(text(),'" + transaction
					+ "')]]/following-sibling::td//*[contains(@id,'deleteTransactionImg')])[1]"));
		}
		clickYesButtonIfVisible();
		clickOkButtonIfVisible();
		return this;
	}

	/**
	 * This is for checking logic after clicking delete button
	 * 
	 * @param transaction
	 * @return
	 */
	public HistoryPage clickDeleteTransactionButtonByNameForChecking(String transaction) {
		waitForElementVisible(By.xpath("(.//td[*[contains(text(),'" + transaction
				+ "')]]/following-sibling::td//*[contains(@id,'deleteTransactionImg')])[1]"), 10);
		clickElement(By.xpath("(.//td[*[contains(text(),'" + transaction
				+ "')]]/following-sibling::td//*[contains(@id,'deleteTransactionImg')])[1]"));

		return this;
	}

	public HistoryPage editHistoryAction(String action) {

		waitForElementVisible(By.id("gwt-debug-RebalancingHistorySingleView-actionListBox"), 10);
		selectFromDropdown(By.id("gwt-debug-RebalancingHistorySingleView-actionListBox"), action);

		clickElement(By.id("gwt-debug-RebalancingHistorySingleView-goButton"));
		return this;
	}

	@SuppressWarnings("unchecked")
	public <T> T editTransactionByInvestment(String investment, Class<?> theClass) throws Exception {
		this.waitForWaitingScreenNotVisible();
		if (isElementVisible(By.id("gwt-debug-CustomDialog-okButton"))) {
			clickElement(By.id("gwt-debug-CustomDialog-okButton"));
		}
		clickElement(By.xpath("(.//*[contains(text(),'" + investment
				+ "')]//ancestor::td//following-sibling::td//*[contains(@id,'editTransactionImg')])[1]"));
		return (T) theClass.getDeclaredConstructor(WebDriver.class).newInstance(webDriver);

	}

	/**
	 * expand transaction by the given investment description
	 * 
	 * @param investment
	 * @return
	 */
	public HistoryPage expandTransactionByInvestment(String transaction) {

		clickElement(
				By.xpath("(.//td[*[contains(text(),'" + transaction + "')]]//preceding-sibling::td//div//div)[1]//i"));

		return this;
	}

}
