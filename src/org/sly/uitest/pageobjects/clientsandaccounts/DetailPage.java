package org.sly.uitest.pageobjects.clientsandaccounts;

import java.awt.AWTException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.pageobjects.salesprocess.ClientRegisterPage;
import org.sly.uitest.settings.Settings;

/**
 *
 * This class represents the Detail Page (tab) of an account or a client, which
 * can be navigated by clicking 'Clients' -> 'Account Overview' -> choose any
 * account -> 'Details (tab)' or 'Clients' -> 'Client Overview' -> choose any
 * client -> 'Details (tab)'
 *
 * URL:
 * "http://192.168.1.104:8080/SlyAWS/?locale=en#generalUserDetailsClientDetail;userKey=9086;detailType=1"
 * or
 * "http://192.168.1.104:8080/SlyAWS/?locale=en#portfolioOverviewDetails;investorAccountKey=13031"
 *
 * @author Lynne Huang
 * @date : 13 Aug, 2015
 * @company Prive Financial
 *
 *
 */
public class DetailPage extends AbstractPage {

	/**
	 * @param webDriver
	 */
	public DetailPage(WebDriver webDriver) {

		super();
		this.webDriver = webDriver;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(40, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		/*
		 * wait.until(ExpectedConditions.visibilityOfElementLocated(By
		 * .id("gwt-debug-GeneralOverviewView-contentTable")));
		 */

		// assertTrue(pageContainsStr("Details"));
	}

	/**
	 * Click the pencil icon beside the given field, the page will be navigated
	 * to the {@link DetailEditPage}
	 *
	 * @param field
	 *            choose the specific field to edit, such as 'Details', 'Model
	 *            Portfolio'
	 * @return {@link DetailEditPage}
	 */
	public DetailEditPage goToEditPageByField(String field) throws InterruptedException {
		// clickElement(By.xpath("//tr[*/text()='" + field +
		// "']/td[2]/table/tbody/tr/td//img[@title='Edit']"));
		clickElement(By.xpath("//td[.='" + field + "']/table/tbody/tr/td[2]/table/tbody/tr/td//img[@title='Edit']"));

		return new DetailEditPage(webDriver, DetailPage.class);
	}

	/**
	 * Click the pencil icon beside the given field, the page will be navigated
	 * to the {@link DetailEditPage}
	 *
	 * @param field
	 *            choose the specific field to edit, such as 'Details', 'Model
	 *            Portfolio'
	 * @return {@link DetailEditPage}
	 */
	public ClientRegisterPage goToEditPageByFieldFS() throws InterruptedException {

		clickElement(By.xpath(
				"//td[@class='generalOverviewSectionTitleUnderline']/table/tbody/tr/td[2]/table/tbody/tr/td//img"));

		return new ClientRegisterPage(webDriver);
	}

	/**
	 * Click the black-white cross icon beside the given field, the page will be
	 * navigated to the {@link DetailEditPage}
	 *
	 * @param field
	 *            choose the specific field to add, such as 'Related Parties',
	 *            'Reports'
	 * @return {@link DetailEditPage}
	 */
	public DetailEditPage goToAddPageByField(String field) throws InterruptedException {

		clickElement(By.xpath("//td[.='" + field + "']/table/tbody/tr/td[2]/table/tbody/tr/td//img[@title='Add']"));

		return new DetailEditPage(webDriver, DetailPage.class);
	}

	/**
	 * Click the name of the "Main Advisors" in the Details Section, the page
	 * will be navigated to the advisor Details Page
	 *
	 * @return {@link DetailPage}
	 */
	public DetailPage goToMainAdvisorPageFromAccount() throws InterruptedException {

		clickElementAndWait3(By.id("gwt-debug-UserDetailsSectionPresenter-advisorName"));

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		try {
			// wait.until(ExpectedConditions.textToBePresentInElementLocated(By
			// .id("gwt-debug-MyMainClassicView-mainPanel"),"Advisor Admin"));

		} catch (Exception ex) {
			wait.until(ExpectedConditions
					.textToBePresentInElementLocated(By.id("gwt-debug-MyMainMaterialView-mainPanel"), "Advisor Admin"));

		}

		return new DetailPage(webDriver);
	}

	/**
	 * Click the name of the "Main Holder" in the Details Section, the page will
	 * be navigated to the holder Details Page
	 *
	 * @return {@link DetailPage}
	 * @throws InterruptedException
	 */
	public DetailPage goToMainHolderDetailPageFromAccount() throws InterruptedException {

		wait(5);
		scrollToTop();
		clickElement(By.id("gwt-debug-InvestorAccountDetailsSectionPresenter-ownerName"));

		return this;
	}

	/**
	 * On the account Detail Page, click the black-white cross icon beside
	 * "Related Parties", and then the page will be navigated to
	 * {@link DetailEditPage}
	 *
	 * @return {@link DetailEditPage}
	 */
	public DetailEditPage createAccountRelatedParties() throws InterruptedException {

		clickElement(By.id("gwt-debug-RelatedPartySectionPresenter-addIcon"));

		return new DetailEditPage(webDriver, DetailPage.class);
	}

	/**
	 * On the account Detail Page, click the certain related party with the
	 * given name, and then the page will be navigated to {@link DetailEditPage}
	 *
	 * @param name
	 *            the name of the related party
	 *
	 * @return {@link DetailEditPage}
	 */
	public DetailEditPage editAccountRelatedPartiesByName(String name) throws InterruptedException {

		clickElement(By.xpath("//td[.='" + name + " ']/following-sibling::td[3]//img[@title='Edit']"));

		return new DetailEditPage(webDriver, DetailPage.class);
	}

	/**
	 * On the account Detail Page, click the red minus icon beside the certain
	 * related party with the given name to delete the related party
	 *
	 * @param name
	 *            the name of the related party
	 *
	 * @return {@link DetailPage}
	 */
	public DetailPage deleteAccountRelatedPartiesByName(String name) throws InterruptedException {

		clickElement(By.xpath("//td[.='" + name + " ']/following-sibling::td[3]//img[@title='Delete']"));

		clickElementAndWait3(By.id("gwt-debug-CustomDialog-yesButton"));

		return new DetailPage(webDriver);
	}

	/**
	 * On the account Detail Page, click the red minus icon beside the certain
	 * report with the given name to delete the report
	 *
	 * @param report
	 *            the name of the report
	 *
	 * @return {@link DetailPage}
	 */
	public DetailPage deleteAccountReport(String report) {

		clickElement(By.xpath("//td[.='" + report + "']/following-sibling::td[1]/img"));

		clickYesButtonIfVisible();

		return this;
	}

	/**
	 * On the account Detail Page, click the red minus icons to delete all the
	 * reports
	 *
	 * @return {@link DetailPage}
	 */
	public DetailPage deleteAllAccountReports() throws InterruptedException {

		for (int i = 0; i < 3; i++) {

			try {
				clickElement(By.id("gwt-debug-ReportsSectionPresenter-deleteImage"));

				clickYesButtonIfVisible();

				this.waitForElementNotVisible(By.id("gwt-debug-CustomDialog-yesButton"), Settings.WAIT_SECONDS);

			} catch (org.openqa.selenium.NoSuchElementException e) {

				break;
			}
		}

		return this;
	}

	/**
	 *
	 * First click the Process icon on the top right of the page; second, if the
	 * updated process status is Completed, then the test can continue execution
	 *
	 * @throws InterruptedException
	 *
	 */
	public DetailPage confirmAccountReportGenerated(String completed) throws InterruptedException {

		this.goToProcess();

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(60, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		wait.until(ExpectedConditions
				.textToBePresentInElementLocated(By.xpath(".//*[@class='processImageMenuPopupSubtitle']"), completed));

		// wait(Settings.WAIT_SECONDS);

		return this;
	}

	/**
	 * On the account detail page, link one model portfolio for the account
	 *
	 * @param portfolio
	 *            the model portfolio to be linked to the account
	 * @param rebalance
	 *            if true, check the checkbox on the Model Portfolio popup page;
	 *            if true, not check
	 *
	 * @return {@link DetailPage}
	 * @throws InterruptedException
	 */
	public DetailPage linkModelPortfolioForAccount(String portfolio, Boolean rebalance) throws InterruptedException {

		if (pageContainsStr("Linked to:")) {
			wait(Settings.WAIT_SECONDS);
			clickElement(By.id("gwt-debug-ModelPortfolioSectionPresenter-image"));
			wait(Settings.WAIT_SECONDS);
			clickElement(By.id("gwt-debug-ModelPortfolioWidget-modelUnLink"));

		}
		wait(Settings.WAIT_SECONDS);
		clickElement(By.id("gwt-debug-ModelPortfolioSectionPresenter-image"));
		wait(Settings.WAIT_SECONDS);
		selectFromDropdown(By.id("gwt-debug-ModelPortfolioWidget-modelPortList"), portfolio);

		WebElement we = webDriver.findElement(By.id("gwt-debug-ModelPortfolioWidget-rebalanceFlagCheckBox-input"));

		setCheckboxStatus2(we, rebalance);

		clickElement(By.id("gwt-debug-ModelPortfolioWidget-modelLink"));

		wait(Settings.WAIT_SECONDS);

		clickYesButtonIfVisible();

		this.waitForWaitingScreenNotVisible();

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(60, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("gwt-debug-ModelPortfolioWidget-modelLink")));

		return this;
	}

	/**
	 * On the account detail page, unlink the existing linked model portfolio
	 *
	 * @return {@link DetailPage}
	 *
	 */
	public DetailPage unlinkModelPortfolioForAccount() {

		if (pageContainsStr("Linked to:")) {

			clickElement(By.id("gwt-debug-ModelPortfolioSectionPresenter-image"));
			scrollToBottom();
			clickElement(By.id("gwt-debug-ModelPortfolioWidget-modelUnLink"));

		}

		return this;

	}

	/**
	 * On the investor Detail Page, click the email icon beside the
	 * "Email Function:" (if enabled)
	 *
	 * @return {@link DetailPage}
	 *
	 * @throws InterruptedException
	 *
	 */
	public DetailPage clickSendClientInvitationIcon() throws InterruptedException {

		clickElementAndWait3(By.id("gwt-debug-UserDetailsSectionPresenter-imageInvite"));

		clickElement(By.id("gwt-debug-CustomDialog-yesButton"));
		clickOkButtonIfVisible();

		return this;
	}

	public DetailPage uploadSignature() throws AWTException, InterruptedException {

		wait(3);
		WebElement elem = webDriver.findElement(By.xpath(".//input[@class = 'gwt-FileUpload']"));
		elem.sendKeys(Settings.FILE_LOCATION);
		wait(Settings.WAIT_SECONDS);
		clickElement(By.id("gwt-debug-SingleUploaderWithResetWidget-saveBtn"));

		waitForElementVisible(By.id("gwt-debug-UserDetailsSectionPresenter-RowWidget-anchorDoc"), 30);

		return this;
	}

	public DetailPage deleteSignature() {

		clickElement(By.id("gwt-debug-UserDetailsSectionPresenter-deleteSignatureButton"));

		clickYesButtonIfVisible();

		waitForElementVisible(By.xpath(".//input[@class = 'gwt-FileUpload']"), 30);

		return this;
	}

	/**
	 * On the account detail page, edit the access right for the certain user
	 *
	 * @param user
	 * @param name
	 * @param readAccess
	 *            if true, read access is enabled; if false, read access is
	 *            disabled
	 * @param writeAccess
	 *            if true write access is enabled; if false, write access is
	 *            disabled
	 *
	 * @return {@link DetailPage}
	 * @throws InterruptedException
	 *
	 */
	public DetailPage editInvestorAccessRightsFromAccount(String user, String name, Boolean readAccess,
			Boolean writeAccess) throws InterruptedException {

		this.waitForElementVisible(By.xpath("//img[@title='Edit']"), Settings.WAIT_SECONDS);
		clickElement(By.xpath("//td[.='Access Rights']/table/tbody/tr/td[2]/table/tbody/tr/td//img[@title='Edit']"));
		try {
			waitForElementVisible(
					By.xpath(
							"//div[.='Selenium Consultant']/following::input[@id='gwt-debug-AccessRightsPresenter-writeAccessCheckBox-input'][1]"),
					5);
		} catch (Exception ex) {
			selectFromDropdown(By.id("gwt-debug-AccessRightsNewWidget-userListBox"), "Selenium Consultant");

			clickElement(By.xpath(".//*[@id='gwt-debug-AccessRightsNewWidget-addButton']"));
			wait(3);
			if (pageContainsStr("The selected user already has access rights.")) {
				clickOkButtonIfVisible();
			}
		}

		if (writeAccess) {
			clickOkButtonIfVisible();
			WebElement we = webDriver.findElement(By.xpath("//div[.='" + name
					+ "']/following::input[@id='gwt-debug-AccessRightsPresenter-writeAccessCheckBox-input'][1]"));
			// wait(1);
			setCheckboxStatus2(we, writeAccess);

		} else {
			WebElement we = webDriver.findElement(By.xpath("//div[.='" + name
					+ "']/following::input[@id='gwt-debug-AccessRightsPresenter-writeAccessCheckBox-input'][1]"));
			// wait(1);
			setCheckboxStatus2(we, writeAccess);

			waitForElementVisible(
					By.xpath("//div[.='" + name
							+ "']/following::input[@id='gwt-debug-AccessRightsPresenter-readAccessCheckBox-input'][1]"),
					5);

			we = webDriver.findElement(By.xpath("//div[.='" + name
					+ "']/following::input[@id='gwt-debug-AccessRightsPresenter-readAccessCheckBox-input'][1]"));
			// wait(1);
			setCheckboxStatus2(we, readAccess);
		}
		this.waitForElementVisible(By.id("gwt-debug-AccessRightsNewWidget-saveBtn"), Settings.WAIT_SECONDS);
		clickElement(By.id("gwt-debug-AccessRightsNewWidget-saveBtn"));

		try {
			this.waitForElementVisible(By.id("gwt-debug-CustomDialog-yesButton"), Settings.WAIT_SECONDS);
			clickElement(By.id("gwt-debug-CustomDialog-yesButton"));
		} catch (TimeoutException e) {

		}

		try {
			this.waitForElementVisible(By.id("gwt-debug-CustomDialog-okButton"), Settings.WAIT_SECONDS);
			clickElement(By.id("gwt-debug-CustomDialog-okButton"));
		} catch (TimeoutException e) {

		}
		return this;
	}

	public DetailEditPage goToEditPageByFieldAndSelectField(String field, String value) throws InterruptedException {

		wait(Settings.WAIT_SECONDS);

		if (!pageContainsStr(value)) {
			clickElement(
					By.xpath("//td[.='" + field + "']/table/tbody/tr/td[2]/table/tbody/tr/td//img[@title='Edit']"));
			// wait(Settings.WAIT_SECONDS);
			clickElement(By.id("gwt-debug-ModelPortfolioWidget-modelUnLink"));
			// wait(3);
			clickElement(
					By.xpath("//td[.='" + field + "']/table/tbody/tr/td[2]/table/tbody/tr/td//img[@title='Edit']"));
			waitForElementVisible(By.id("gwt-debug-ModelPortfolioWidget-modelPortList"), 10);
			selectFromDropdown(By.id("gwt-debug-ModelPortfolioWidget-modelPortList"), value);
			// wait(Settings.WAIT_SECONDS);
			clickElementAndWait3(By.id("gwt-debug-ModelPortfolioWidget-modelLink"));
			clickYesButtonIfVisible();
		}
		return new DetailEditPage(webDriver, DetailPage.class);
	}

	public SubAccountsPage goToSubAccountsPage() throws InterruptedException {

		waitForElementVisible(By.id("gwt-debug-SubAccountSectionPresenter-addIcon"), 10);

		WebElement elem = webDriver.findElement(By.xpath(".//*[@id='gwt-debug-SubAccountSectionPresenter-addIcon']"));
		scrollToTop();
		clickElement(By.id("gwt-debug-SubAccountSectionPresenter-addIcon"));
		// clickElement(
		// By.xpath(".//td[@class='generalOverviewSectionTitle']/following-sibling::td//img[@id='gwt-debug-SubAccountSectionPresenter-addIcon']"));
		//
		return new SubAccountsPage(webDriver);

	}

	public RiskProfilePage clickCreateNewRiskProfileButton() {
		clickElement(By.xpath(".//*[contains(text(),'Risk Profile')]//following-sibling::td//img"));
		return new RiskProfilePage(webDriver);
	}

	public DetailPage editRiskProfileByDate(String date) {

		clickElement(By.xpath(".//*[contains(text(),'" + date + "')]//following-sibling::td//a"));

		return this;
	}
}
