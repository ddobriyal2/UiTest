package org.sly.uitest.sections.accounts;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.clientsandaccounts.AccountOverviewPage;
import org.sly.uitest.pageobjects.clientsandaccounts.DetailPage;
import org.sly.uitest.pageobjects.clientsandaccounts.SubAccountsPage;
import org.sly.uitest.settings.Settings;

/**
 * Test the functionality of Sub Account page Please refer to
 * https://docs.google
 * .com/spreadsheets/d/1qfGvoy9pTSFWoKa7J2Ny5Yjizwr4TA3_H-5KJrH
 * -BlI/edit#gid=750076521 for test plan
 * 
 * @author Benny Leung
 * @date : Aug 15, 2016
 * @company Prive Financial
 *
 */
public class SubAccountsTest extends AbstractTest {
	@Test
	public void testCreateSubAccount() throws Exception {
		String type = "Securities";
		String accountName1 = "TestName" + getRandName();
		String accountName2 = "TestName" + getRandName();
		String accountName3 = "TestName" + getRandName();
		String accountNumber = "12345";
		String currency = "AUD";
		String day = "1";
		String month = "Jan";
		String year1 = "2015";
		String year2 = "2016";
		String year3 = "2017";
		String accountName = "";

		LoginPage main = new LoginPage(webDriver);
		AccountOverviewPage account = main.loginAs(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD)
				.goToAccountOverviewPage();
		accountName = this.getTextByLocator(By.id("gwt-debug-InvestorAccountTable-linkPortfolioName"));

		account.goToAccountHoldingsPageByName(accountName).goToDetailsPage().goToSubAccountsPage();

		// test case no. 8
		this.createSubAccount("", accountName2, accountNumber, currency, day, month, year2, day, month, year3);

		assertTrue(isElementVisible(By.id("gwt-debug-SubAccountEditWidget-typeListBox")));

		clickElementAndWait3(By.id("gwt-debug-SubAccountEditWidget-cancelBtn"));

		// test case no. 9
		this.createSubAccount(type, accountName3, accountNumber, currency, day, month, year2, day, month, year1);

		assertTrue(pageContainsStr("Open date must be before the close date"));

		clickOkButtonIfVisible();

		clickElementAndWait3(By.id("gwt-debug-SubAccountEditWidget-cancelBtn"));

		// test case no. 7
		this.createSubAccount(type, accountName1, accountNumber, currency, day, month, year2, day, month, year3);

		assertTrue(pageContainsStr(accountName1));

		clickElement(By.xpath(".//td[.='" + accountName1
				+ "']/following-sibling::td//img[@id='gwt-debug-SubAccountSectionPresenter-deleteIcon']"));

		clickYesButtonIfVisible();

	}

	public void createSubAccount(String type, String name, String number, String currency, String openDay,
			String openMonth, String openYear, String endDay, String endMonth, String endYear) throws Exception {

		DetailPage details = new DetailPage(webDriver);
		SubAccountsPage subAccounts = details.goToSubAccountsPage();
		// wait(2);

		subAccounts.editOpenDate(openDay, openMonth, openYear).editEndDate(endDay, endMonth, endYear)
				.editSubAccountType(type).editName(name).editAccountNumber(number)

				.editCurrency(currency).clickSaveButton();

	}

}
