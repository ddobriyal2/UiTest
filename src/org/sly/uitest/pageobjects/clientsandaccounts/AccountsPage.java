package org.sly.uitest.pageobjects.clientsandaccounts;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;

/**
 * This class represents the Accounts Page (tab) of a client, which can be
 * navigated by clicking 'Clients' -> 'Client Overview' -> choose any client ->
 * 'Accounts(tab)'
 * 
 * URL:
 * "http://192.168.1.104:8080/SlyAWS/?locale=en_US#generalUserDetailsAccountsuserKey=9484;detailType=1"
 * 
 * @author Lynne Huang
 * @date : 14 Aug, 2015
 * @company Prive Financial
 * 
 *          PAGE NAVIGATION: Client -> *client -> Accounts (tab)
 */
public class AccountsPage extends AbstractPage {

	/**
	 * @param webDriver
	 */
	public AccountsPage(WebDriver webDriver) {

		super();
		this.webDriver = webDriver;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gwt-debug-GeneralOverviewView-contentTable")));

		assertTrue(pageContainsStr("Accounts"));

	}

	/**
	 * Click the black-white plus icon, click NO button, and choose a type of
	 * account to create a non New Business Event account
	 * 
	 * @param accountType
	 *            the type of the account
	 * 
	 * @return EditPage
	 * 
	 */
	public DetailEditPage createNewAccount_NonNBE(String accountType) {

		waitForElementVisible(By.id("gwt-debug-InvestmentAccountsSectionPresenter-image"), 30);

		clickElement(By.id("gwt-debug-InvestmentAccountsSectionPresenter-image"));

		// clickNoButtonIfVisible();

		clickElement(By.xpath("//button[.='" + accountType + "']"));

		clickNoButtonIfVisible();

		return new DetailEditPage(webDriver, DetailPage.class);

	}

	/**
	 * Click the account with the given name, and the page will be navigated to
	 * the account detail page
	 * 
	 * @param accountName
	 *            the name of the account
	 * 
	 * @return the Detail Page
	 */
	public DetailPage selectAccountByName(String accountName) {

		clickElement(By.xpath("//a[contains(text(),'" + accountName + "')]"));

		return new DetailPage(webDriver);

	}

	/**
	 * When there is only one asset account of the client, go to the account
	 * detail page of this asset account
	 * 
	 * @return {@link DetailPage}
	 */
	public DetailPage goToTheOnlyOneAssetAccount() {

		clickElement(By.id("gwt-debug-InvestmentAccountsSectionPresenter-accountName"));

		return new DetailPage(webDriver);
	}

	/**
	 * Click the account with the given name, and the page will be navigated to
	 * the default page set by the User Preference
	 * 
	 * @param accountName
	 *            the name of the account
	 * @param returnClass
	 *            the page will be navigated to
	 * 
	 * @return returnClass
	 */
	@SuppressWarnings("unchecked")
	public <T> T goToAccountDefaultPageByName(String accountName, Class<?> returnClass) throws InterruptedException {

		clickElement(By.xpath("//td[.='" + accountName + "']/div/a"));

		return (T) returnClass;
	}

	/**
	 * Click the account with the given name, and the page will be navigated to
	 * the account holdings page
	 * 
	 * @param mame
	 *            the name of the account
	 * 
	 * @return {@link HoldingsPage}
	 */
	public HoldingsPage goToAccountHoldingsPageByNameFromClient(String name) throws InterruptedException {

		clickElement(By
				.xpath(".//*[@id='gwt-debug-InvestmentAccountsSectionPresenter-accountName' and .=\"" + name + "\"]"));

		for (int i = 0; i < 2; i++) {
			if (isElementVisible(By.id("gwt-debug-CustomDialog-okButton"))
					|| isElementPresent(By.id("gwt-debug-CustomDialog-okButton"))) {

				clickElement(By.id("gwt-debug-CustomDialog-okButton"));
			}
		}

		return new HoldingsPage(webDriver);
	}

	/**
	 * Click Show More Accounts to expand the account list
	 * 
	 * @return {@link AccountsPage}
	 */
	public AccountsPage showMoreAccounts() {

		if (pageContainsStr("Show more...") || pageContainsStr("Mehr anzeigen...")) {

			clickElement(By.id("gwt-debug-InvestmentAccountsSectionView-showMoreLessAnchor"));

		}

		return this;
	}

	public AccountsPage showMoreInactiveAccounts() {
		waitForElementClickable(By.id("gwt-debug-InvestmentAccountsSectionView-showMoreLessInactiveAnchor"), 30);
		clickElement(By.id("gwt-debug-InvestmentAccountsSectionView-showMoreLessInactiveAnchor"));

		return this;
	}

	/**
	 * Get all the asset account names
	 * 
	 * @return the list of all asset account names
	 * 
	 */
	public List<String> getAllAssetAccounts() {

		int size = this.getSizeOfElements(By.xpath(
				"//table[@id='gwt-debug-InvestmentAccountsSectionView-assetAccountTable']//a[@id='gwt-debug-InvestmentAccountsSectionPresenter-accountName']"));

		List<String> assetAccounts = new ArrayList<String>();

		for (int i = 1; i <= size; i++) {

			String account = this.getTextByLocator(By
					.xpath("(//table[@id='gwt-debug-InvestmentAccountsSectionView-assetAccountTable']//a[@id='gwt-debug-InvestmentAccountsSectionPresenter-accountName'])["
							+ i + "]"));

			assetAccounts.add(account);
		}

		return assetAccounts;
	}

	/**
	 * This method is to check each account's value. If the value != NA, the
	 * account will be added to the list
	 * 
	 * @return List<String> List of account name with valid value
	 */
	public List<String> getAccountsWithValidValue() {
		int size = this.getSizeOfElements(By.xpath(
				"//table[@id='gwt-debug-InvestmentAccountsSectionView-assetAccountTable']//a[@id='gwt-debug-InvestmentAccountsSectionPresenter-accountName']"));

		List<String> assetAccounts = new ArrayList<String>();

		for (int i = 1; i <= size; i++) {

			int position = i + 1;

			if (!getTextByLocator(
					By.xpath(".//table[@id='gwt-debug-InvestmentAccountsSectionView-assetAccountTable']//tr[" + position
							+ "]/td[3]")).equals("NA")) {

				String account = this.getTextByLocator(By
						.xpath("(//table[@id='gwt-debug-InvestmentAccountsSectionView-assetAccountTable']//a[@id='gwt-debug-InvestmentAccountsSectionPresenter-accountName'])["
								+ i + "]"));

				log(account);
				assetAccounts.add(account);

			}

		}
		return assetAccounts;

	}
}
