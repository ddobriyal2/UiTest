package org.sly.uitest.sections.accesstoken;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.framework.DevelopmentTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.admin.AdminEditPage;
import org.sly.uitest.pageobjects.clientsandaccounts.AccountOverviewPage;
import org.sly.uitest.settings.Settings;

public class ApiTest extends AbstractTest {
	String wsdl_107 = "http://192.168.1.107:8080/SlyAWS/wsdl/";

	@Test
	@Category(DevelopmentTest.class)
	public void testAccountPerformanceGetRequest() throws Exception {
		String username = "SeleniumTest";
		String password = "SeleniumTest";
		String callerSystemID = "LIQID";
		String priveAccountKey = "38602";
		String externalAccountKey = "";
		String url = wsdl_107 + "AccountsPerformanceGet.wsdl";

		log("---------------------------------AccountsPerformanceGetRequest-------------------------------");
		SOAPMessage soapResponse = this.accountPerformanceGetRequest(username, password, callerSystemID,
				priveAccountKey, externalAccountKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "Success").equalsIgnoreCase("true"));

		log("---------------------wrong account key--------------------------");
		soapResponse = this.accountPerformanceGetRequest(username, password, callerSystemID, "1234567",
				externalAccountKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage").contains("Portfolio Metrics Not found"));

		log("---------------------login account without access--------------------------");
		soapResponse = this.accountPerformanceGetRequest(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD,
				callerSystemID, priveAccountKey, externalAccountKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage").contains("Access is denied"));

		log("---------------------wrong login information--------------------------");
		soapResponse = this.accountPerformanceGetRequest(Settings.ADVISOR_USERNAME, "abc", callerSystemID,
				priveAccountKey, externalAccountKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(
				this.getTextContentByTagName(soapResponse, "faultstring").contains("Username or password incorrect"));

		log("---------------------invalid caller system id--------------------------");
		soapResponse = this.accountPerformanceGetRequest(username, password, "fake caller system id", priveAccountKey,
				externalAccountKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage").contains("Invalid caller system ID"));

		log("---------------------no account key--------------------------");
		soapResponse = this.accountPerformanceGetRequest(username, password, callerSystemID, "", "", url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage")
				.contains("Neither the Prive key nor the external key are given"));

	}

	@Test
	public void testAccountDetailsGetRequest() throws Exception {
		String username = "SeleniumTest";
		String password = "SeleniumTest";
		String callerSystemID = "LIQID";
		String priveAccountKey = "12292";
		String externalAccountKey = "";
		String url = wsdl_107 + "AccountDetailsGet.wsdl";

		log("---------------------------------AccountDetailsGet-------------------------------");
		SOAPMessage soapResponse = this.accountDetailGetRequest(username, password, callerSystemID, priveAccountKey,
				externalAccountKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "Name").equalsIgnoreCase("Selenium Test"));

		log("-----------------login account without access-----------------");
		soapResponse = this.accountDetailGetRequest(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD,
				callerSystemID, priveAccountKey, externalAccountKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage").contains("Access is denied"));

		log("-----------------wrong login information-----------------");
		soapResponse = this.accountDetailGetRequest("abc", password, callerSystemID, priveAccountKey,
				externalAccountKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(
				this.getTextContentByTagName(soapResponse, "faultstring").contains("Username or password incorrect"));

		log("--------------invalid caller system ID-----------------");
		soapResponse = this.accountDetailGetRequest(username, password, "fake caller system ID", priveAccountKey,
				externalAccountKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage").contains("Invalid caller system ID"));

		log("--------------Wrong account key-----------------");
		soapResponse = this.accountDetailGetRequest(username, password, callerSystemID, "12345", externalAccountKey,
				url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage").contains("Access is denied"));

		log("--------------No account key-----------------");
		soapResponse = this.accountDetailGetRequest(username, password, callerSystemID, "", "", url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage")
				.contains("Neither the Prive key nor the external key are given"));
	}

	@Test
	public void testAccountListGetRequest() throws Exception {
		String url = wsdl_107 + "AccountsListGet.wsdl";
		String username = Settings.USERNAME;
		String password = Settings.PASSWORD;
		String callerSystemID = "LIQID";
		String priveAdvisorKey = "";
		String entityType = "ADVISOR_COMPANY";
		String entityKey = "";
		String msg = "";
		ArrayList<String> accountNames = new ArrayList<String>();

		/**
		 * Check account list for SeleniumTest
		 */
		LoginPage main = new LoginPage(webDriver);
		AccountOverviewPage accountOverviewPage = main.loginAs(Settings.USERNAME, Settings.PASSWORD)
				.goToAccountOverviewPage();

		int size = getSizeOfElements(By.id("gwt-debug-InvestorAccountTable-linkPortfolioName"));

		for (int i = 1; i <= size; i++) {
			accountNames.add(getTextByLocator(
					By.xpath("(.//*[contains(@id,'gwt-debug-InvestorAccountTable-linkPortfolioName')])["
							+ String.valueOf(i) + "]")));
		}
		log("---------------------------------AccountsListGetRequest-------------------------------");
		log("");

		log("---------------------invalid callerSystemID---------------------------");
		SOAPMessage soapResponse = this.accountListGetRequest(username, password, "XX", priveAdvisorKey, entityType,
				entityKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage").contains("Invalid caller system ID"));

		log("---------------------wrong login information---------------------------");
		soapResponse = this.accountListGetRequest("abc", password, callerSystemID, priveAdvisorKey, entityType,
				entityKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(
				this.getTextContentByTagName(soapResponse, "faultstring").contains("Username or password incorrect"));

		log("---------------------whole list of account---------------------------");
		soapResponse = this.accountListGetRequest(username, password, callerSystemID, priveAdvisorKey, entityType,
				entityKey, url);

		msg = this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "CompanyName").contains("SeleniumTest"));

		for (String account : accountNames) {
			msg.contains(account);
		}

		accountNames.clear();
		this.checkLogout();
		handleAlert();

		/**
		 * Check account list for JarlAdvisor
		 */
		LoginPage main2 = new LoginPage(webDriver);
		accountOverviewPage = main2.loginAs(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD)
				.goToAccountOverviewPage();
		accountNames.clear();

		size = getSizeOfElements(By.id("gwt-debug-InvestorAccountTable-linkPortfolioName"));

		for (int i = 1; i <= size; i++) {
			accountNames.add(getTextByLocator(
					By.xpath("(.//*[contains(@id,'gwt-debug-InvestorAccountTable-linkPortfolioName')])["
							+ String.valueOf(i) + "]")));
		}

		priveAdvisorKey = "";
		soapResponse = this.accountListGetRequest(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD, callerSystemID,
				priveAdvisorKey, entityType, entityKey, url);

		msg = this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "CompanyName").contains("Advisor ABC Company"));

		for (String account : accountNames) {
			msg.contains(account);
		}

	}

	@Test
	public void testAccountHoldingsGetRequest() throws Exception {
		String url = wsdl_107 + "AccountHoldingsGet.wsdl";
		String username = "SeleniumTest";
		String password = "SeleniumTest";
		String priveAccountKey = "32181";
		String externalAccountKey = "";
		String callerSystemID = "LIQID";
		String portfolioInventorySource = "";

		log("---------------------------------AccountsHoldingsGetRequest-------------------------------");
		SOAPMessage soapResponse = this.accountHoldingsGetRequest(username, password, callerSystemID, priveAccountKey,
				externalAccountKey, portfolioInventorySource, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "AccountCurrency").contains(("USD")));

		log("---------------------login account without access---------------------------");
		soapResponse = this.accountHoldingsGetRequest(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD,
				callerSystemID, priveAccountKey, externalAccountKey, portfolioInventorySource, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage").contains("Access is denied"));

		log("---------------------wrong account key---------------------------");
		soapResponse = this.accountHoldingsGetRequest(username, password, callerSystemID, "123456", externalAccountKey,
				portfolioInventorySource, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage").contains("Access is denied"));

		log("---------------------wrong login information---------------------------");
		soapResponse = this.accountHoldingsGetRequest(username, "abc", callerSystemID, priveAccountKey,
				externalAccountKey, portfolioInventorySource, url);

		this.printSoapResponse(soapResponse);

		assertTrue(
				this.getTextContentByTagName(soapResponse, "faultstring").contains("Username or password incorrect"));

		log("---------------------invalid caller system ID---------------------------");
		soapResponse = this.accountHoldingsGetRequest(username, password, "fake caller system ID", priveAccountKey,
				externalAccountKey, portfolioInventorySource, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage").contains("Invalid caller system ID"));

		log("---------------------no account key---------------------------");
		soapResponse = this.accountHoldingsGetRequest(username, password, callerSystemID, "", "",
				portfolioInventorySource, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage")
				.contains("Neither the Prive key nor the external key are given"));
	}

	@Test
	public void testAccountTransactionsGetRequest() throws Exception {
		String username = "SeleniumTest";
		String password = "SeleniumTest";
		String callerSystemID = "LIQID";
		String priveAccountKey = "13031";
		String externalAccountKey = "";
		String url = wsdl_107 + "AccountTransactionsGet.wsdl";

		log("------------------------------AccountsTransactionsGetRequest-----------------------------");
		SOAPMessage soapResponse = this.accountTransactionsGetRequest(username, password, callerSystemID,
				priveAccountKey, externalAccountKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "Source").equalsIgnoreCase("DATAFEED"));

		log("---------------------login account without access---------------------------");
		soapResponse = this.accountTransactionsGetRequest(Settings.CRM_USERNAME, Settings.CRM_PASSWORD, callerSystemID,
				priveAccountKey, externalAccountKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage").contains("Access is denied"));

		log("---------------------wrong account key---------------------------");
		soapResponse = this.accountTransactionsGetRequest(username, password, callerSystemID, "123456",
				externalAccountKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage").contains("Access is denied"));

		log("---------------------invalid caller system ID---------------------------");
		soapResponse = this.accountTransactionsGetRequest(username, password, "fake caller system ID", priveAccountKey,
				externalAccountKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage").contains("Invalid caller system ID"));

		log("---------------------wrong login information---------------------------");
		soapResponse = this.accountTransactionsGetRequest(username, "abc", callerSystemID, priveAccountKey,
				externalAccountKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(
				this.getTextContentByTagName(soapResponse, "faultstring").contains("Username or password incorrect"));

		log("---------------------no account key---------------------------");
		soapResponse = this.accountTransactionsGetRequest(username, password, callerSystemID, "", "", url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage")
				.contains("Neither the Prive key nor the external key are given"));
	}

	@Test
	public void testInvestorGet() throws Exception {
		String url = wsdl_107 + "InvestorGet.wsdl";
		String username = "SeleniumTest";
		String password = "SeleniumTest";
		String priveAccountKey = "8462";
		String externalInvestorKey = "";
		String callerSystemID = "LIQID";

		log("------------------------------InvestorGetRequest-----------------------------");
		SOAPMessage soapResponse = this.investorGetRequest(username, password, callerSystemID, priveAccountKey,
				externalInvestorKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "UpdateKey").equalsIgnoreCase(priveAccountKey));

		log("---------------------login account without access---------------------------");
		soapResponse = this.investorGetRequest(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD, callerSystemID,
				priveAccountKey, externalInvestorKey, url);
		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "faultstring").contains("Access is denied"));

		log("---------------------wrong account key---------------------------");
		soapResponse = this.investorGetRequest(username, password, callerSystemID, "1234567", externalInvestorKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "faultstring").contains("Access is denied"));

		log("---------------------invalid caller system ID---------------------------");
		soapResponse = this.investorGetRequest(username, password, "invalid caller system id", priveAccountKey,
				externalInvestorKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage").contains("Invalid caller system ID"));

		log("---------------------wrong login information---------------------------");
		soapResponse = this.investorGetRequest("abc", password, callerSystemID, priveAccountKey, externalInvestorKey,
				url);

		this.printSoapResponse(soapResponse);

		assertTrue(
				this.getTextContentByTagName(soapResponse, "faultstring").contains("Username or password incorrect"));

		log("---------------------no account key---------------------------");
		soapResponse = this.investorGetRequest(username, password, callerSystemID, "", externalInvestorKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage")
				.contains("Neither the Prive key nor the external key are given"));

	}

	@Test
	public void testAdvisorGet() throws Exception {
		String username = Settings.USERNAME;
		String password = Settings.PASSWORD;
		String externalAdvisorKey = "";
		String priveAdvisorKey = "8461";
		String callerSystemID = "LIQID";
		String url = wsdl_107 + "AdvisorGet.wsdl";

		log("-----------------------AdvisorGet---------------------------");
		SOAPMessage soapResponse = this.advisorGetRequest(username, password, callerSystemID, externalAdvisorKey,
				priveAdvisorKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "UpdateKey").equalsIgnoreCase(priveAdvisorKey));

		log("---------------------invalid caller system ID---------------------------");
		soapResponse = this.advisorGetRequest(username, password, "invalid caller System ID", externalAdvisorKey,
				priveAdvisorKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage").contains("Invalid caller system ID"));

		log("---------------------invalid account key---------------------------");
		soapResponse = this.advisorGetRequest(username, password, callerSystemID, externalAdvisorKey, "13245", url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "faultstring").contains("Access is denied"));

		log("---------------------wrong login information---------------------------");
		soapResponse = this.advisorGetRequest(username, "abc", callerSystemID, externalAdvisorKey, priveAdvisorKey,
				url);

		this.printSoapResponse(soapResponse);

		assertTrue(
				this.getTextContentByTagName(soapResponse, "faultstring").contains("Username or password incorrect"));

		log("---------------------no account key---------------------------");
		soapResponse = this.advisorGetRequest(username, password, callerSystemID, externalAdvisorKey, "", url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage")
				.contains("Neither the Prive key nor the external key are given"));

	}

	@Test
	public void testInvestmentDetailsGetRequest() throws Exception {
		String username = Settings.USERNAME;
		String password = Settings.PASSWORD;
		String callerSystemID = "LIQID";
		String priveInvestmentKey = "20013";
		String isin = "";
		String currency = "";
		String datasourceDefinitionKey = "";
		String accessToken = "";
		String loc = "en_US";
		String url = wsdl_107 + "InvestmentDetailsGet.wsdl";

		log("-----------------------InvestmentDetailsGet---------------------------");
		SOAPMessage soapResponse = this.investmentDetailsGetRequest(username, password, callerSystemID,
				priveInvestmentKey, isin, currency, datasourceDefinitionKey, accessToken, loc, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "Name").contains("Tesla"));

		log("---------------------invalid caller system ID---------------------------");
		soapResponse = this.investmentDetailsGetRequest(username, password, "invalid caller system ID",
				priveInvestmentKey, isin, currency, datasourceDefinitionKey, accessToken, loc, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage").contains("Invalid caller system ID"));

		log("---------------------invalid investment key---------------------------");
		soapResponse = this.investmentDetailsGetRequest(username, password, callerSystemID, "123456", isin, currency,
				datasourceDefinitionKey, accessToken, loc, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage")
				.contains("Statistics not found for investment"));

		log("---------------------wrong login information---------------------------");
		soapResponse = this.investmentDetailsGetRequest("abc", password, callerSystemID, priveInvestmentKey, isin,
				currency, datasourceDefinitionKey, accessToken, loc, url);

		this.printSoapResponse(soapResponse);

		assertTrue(
				this.getTextContentByTagName(soapResponse, "faultstring").contains("Username or password incorrect"));

		log("---------------------only currency is provided for identifier---------------------------");
		priveInvestmentKey = "";

		currency = "USD";
		soapResponse = this.investmentDetailsGetRequest(username, password, callerSystemID, priveInvestmentKey, isin,
				currency, datasourceDefinitionKey, accessToken, loc, url);
		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage").contains("Invalid unique identifier"));

		log("---------------------only currency is provided for identifier---------------------------");

		isin = "US88160R1014";
		currency = "";
		soapResponse = this.investmentDetailsGetRequest(username, password, callerSystemID, priveInvestmentKey, isin,
				currency, datasourceDefinitionKey, accessToken, loc, url);
		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage").contains("Invalid unique identifier"));

	}

	@Test
	public void testInvestmentHistoryGetRequest() throws Exception {
		String username = Settings.USERNAME;
		String password = Settings.PASSWORD;
		String callerSystemID = "LIQID";
		String priveInvestmentKey = "20013";
		// String priveInvestmentKey = "";
		String isin = "";
		String currency = "";
		String datasourceDefinitionKey = "";
		String accessToken = "";
		String url = wsdl_107 + "InvestmentHistoryGet.wsdl";
		String field = "Advisor Company Module Permission";
		String module = "MODULE_WEBSERVICE_INVESTMENT_HISTORY_GET";
		LoginPage main = new LoginPage(webDriver);
		AdminEditPage adminEdit = main.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD)
				.goToAdminEditPage().editAdminThisField(field);

		adminEdit.jumpByKeyAndLoad("561").editModuleToggle(module, false, false).clickSubmitButton();

		// wait(Settings.WAIT_SECONDS * 2);

		log("------------testInvestmentHistoryGetRequest without module toggle permission---------------");
		SOAPMessage soapResponse = this.investmentHistoryGetRequest(username, password, callerSystemID,
				priveInvestmentKey, isin, currency, datasourceDefinitionKey, accessToken, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage")
				.contains("Web Service Module: MODULE_WEBSERVICE_INVESTMENT_HISTORY_GET is not enabled."));

		adminEdit.jumpByKeyAndLoad("561").editModuleToggle(module, false, true).clickSubmitButton();

		// wait for api to be successfully called
		wait(Settings.WAIT_SECONDS * 2);

		log("--------testInvestmentHistoryGetRequest with module toggle permission by investmentKey--------");
		soapResponse = this.investmentHistoryGetRequest(username, password, callerSystemID, priveInvestmentKey, isin,
				currency, datasourceDefinitionKey, accessToken, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "Success").equalsIgnoreCase("true"));

		log("---------------------invalid caller system ID---------------------------");
		soapResponse = this.investmentHistoryGetRequest(username, password, "invalid caller system ID",
				priveInvestmentKey, isin, currency, datasourceDefinitionKey, accessToken, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage").contains("Invalid caller system ID"));

		log("---------------------invalid investment key---------------------------");
		soapResponse = this.investmentHistoryGetRequest(username, password, callerSystemID, "123456", isin, currency,
				datasourceDefinitionKey, accessToken, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage")
				.contains("Statistics not found for investment"));

		log("---------------------wrong login information---------------------------");
		soapResponse = this.investmentHistoryGetRequest(username, "abc", callerSystemID, priveInvestmentKey, isin,
				currency, datasourceDefinitionKey, accessToken, url);

		this.printSoapResponse(soapResponse);

		assertTrue(
				this.getTextContentByTagName(soapResponse, "faultstring").contains("Username or password incorrect"));

		log("---------------------only currency is provided for identifier---------------------------");
		priveInvestmentKey = "";

		currency = "USD";
		soapResponse = this.investmentHistoryGetRequest(username, password, callerSystemID, priveInvestmentKey, isin,
				currency, datasourceDefinitionKey, accessToken, url);
		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage").contains("Invalid unique identifier"));

		log("---------------------only currency is provided for identifier---------------------------");

		isin = "US88160R1014";
		currency = "";
		soapResponse = this.investmentHistoryGetRequest(username, password, callerSystemID, priveInvestmentKey, isin,
				currency, datasourceDefinitionKey, accessToken, url);
		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage").contains("Invalid unique identifier"));

		log("--------testInvestmentHistoryGetRequest with module toggle permission by ISIN--------");
		isin = "US88160R1014";
		currency = "USD";
		soapResponse = this.investmentHistoryGetRequest(username, password, callerSystemID, priveInvestmentKey, isin,
				currency, datasourceDefinitionKey, accessToken, url);
		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "Success").equalsIgnoreCase("true"));
	}

	@Test
	public void testLinkModelPortfolioToAccountRequest() throws Exception {
		String accountname = "Generali Vision - TWRR";
		String username = Settings.USERNAME;
		String password = Settings.PASSWORD;
		String callerSystemID = "LIQID";
		String externalAccountKey = "";
		String priveAccountKey = "32181";
		String priveModelPortfolioKey = "97731";
		String action = "LINK_AND_REBALANCE";
		String action2 = "UNLINK";
		String action3 = "LINK_BUT_DONT_REBALANCE";
		String url = wsdl_107 + "LinkModelPortfolioToAccount.wsdl";
		LoginPage main = new LoginPage(webDriver);
		main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage().simpleSearchByString(accountname)
				.goToAccountHoldingsPageByName(accountname);
		log("--------------LinkModelPortfolioToAccount LINK_AND_BALANCE----------------------------");
		// link and rebalance
		SOAPMessage soapResponse = this.linkModelPortfolioToAccountRequest(username, password, callerSystemID, action,
				externalAccountKey, priveAccountKey, priveModelPortfolioKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "Success").equalsIgnoreCase("true"));

		this.refreshPage();

		try {
			this.waitForElementVisible(By.xpath(".//*[.='This portfolio is linked to Sample Model Portfolio']"),
					Settings.WAIT_SECONDS * 2);
			assertTrue(pageContainsStr("This portfolio is linked to Sample Model Portfolio"));
		} catch (TimeoutException e) {
			throw e;
		} catch (AssertionError e) {
			throw e;
		}

		log("--------------LinkModelPortfolioToAccount UNLINK----------------------------");
		soapResponse = this.linkModelPortfolioToAccountRequest(username, password, callerSystemID, action2,
				externalAccountKey, priveAccountKey, priveModelPortfolioKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "Success").equalsIgnoreCase("true"));

		this.refreshPage();

		try {
			this.waitForElementNotVisible(By.xpath(".//*[.='This portfolio is linked to Sample Model Portfolio']"),
					Settings.WAIT_SECONDS * 2);
			assertFalse(pageContainsStr("This portfolio is linked to Sample Model Portfolio"));
		} catch (TimeoutException e) {
			throw e;
		} catch (AssertionError e) {
			throw e;
		}

		log("--------------LinkModelPortfolioToAccount LINK_BUT_DONT_BALANCE----------------------------");
		soapResponse = this.linkModelPortfolioToAccountRequest(username, password, callerSystemID, action3,
				externalAccountKey, priveAccountKey, priveModelPortfolioKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "Success").equalsIgnoreCase("true"));

		this.refreshPage();
		try {
			this.waitForElementVisible(By.xpath(".//*[.='This portfolio is linked to Sample Model Portfolio']"),
					Settings.WAIT_SECONDS * 2);
			assertTrue(pageContainsStr("This portfolio is linked to Sample Model Portfolio"));
		} catch (TimeoutException e) {
			throw e;
		} catch (AssertionError e) {
			throw e;
		}

		log("--------------LinkModelPortfolioToAccount UNLINK----------------------------");
		soapResponse = this.linkModelPortfolioToAccountRequest(username, password, callerSystemID, action2,
				externalAccountKey, priveAccountKey, priveModelPortfolioKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "Success").equalsIgnoreCase("true"));

		this.refreshPage();
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 30);
		try {
			this.waitForElementNotVisible(By.xpath(".//*[.='This portfolio is linked to Sample Model Portfolio']"),
					Settings.WAIT_SECONDS * 2);
			assertFalse(pageContainsStr("This portfolio is linked to Sample Model Portfolio"));
		} catch (TimeoutException e) {
			throw e;
		} catch (AssertionError e) {
			throw e;
		}

		log("-----------------------Login account without access-----------------------------");
		soapResponse = this.linkModelPortfolioToAccountRequest(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD,
				callerSystemID, action3, externalAccountKey, priveAccountKey, priveModelPortfolioKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "faultstring").contains("Access is denied"));

		log("-----------------------wrong login information-----------------------------");
		soapResponse = this.linkModelPortfolioToAccountRequest("abc", password, callerSystemID, action3,
				externalAccountKey, priveAccountKey, priveModelPortfolioKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(
				this.getTextContentByTagName(soapResponse, "faultstring").contains("Username or password incorrect"));

		log("-----------------------invalid account key-----------------------------");
		soapResponse = this.linkModelPortfolioToAccountRequest(username, password, callerSystemID, action3,
				externalAccountKey, "1234567", priveModelPortfolioKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "faultstring").contains("Access is denied"));

		log("-----------------------invalid porfolio key-----------------------------");
		soapResponse = this.linkModelPortfolioToAccountRequest(username, password, callerSystemID, action3,
				externalAccountKey, priveAccountKey, "1234567", url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "faultstring").contains("Access is denied"));

		log("-----------------------invalid caller system ID-----------------------------");
		soapResponse = this.linkModelPortfolioToAccountRequest(username, password, "invalid caller system ID", action3,
				externalAccountKey, priveAccountKey, priveModelPortfolioKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage").contains("Invalid caller system ID"));

		log("-----------------------invalid action-----------------------------");
		soapResponse = this.linkModelPortfolioToAccountRequest(username, password, callerSystemID, "INVALID_ACTION",
				externalAccountKey, priveAccountKey, priveModelPortfolioKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "faultstring").contains("NullPointerException"));

		log("-----------------------no account key-----------------------------");
		soapResponse = this.linkModelPortfolioToAccountRequest(username, password, callerSystemID, action3,
				externalAccountKey, "", priveModelPortfolioKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage")
				.contains("Neither the Prive key nor the external key are given"));
	}

	@Test
	public void testCommissioningInformationRequest() throws Exception {
		String username = Settings.ADVISOR_USERNAME;
		String password = Settings.ADVISOR_PASSWORD;
		String callerSystemID = "LIQID";
		String externalAccountKey = "";
		String priveAccountKey = "16712";
		String feesChargedVia = "FEES_CHARGED_OUTSIDE_PRODUCT_PROVIDER";
		String url = wsdl_107 + "CommissioningInformation.wsdl";

		log("--------------------CommissioningInformation--------------------");
		SOAPMessage soapResponse = this.commissioningInformationRequest(username, password, callerSystemID,
				priveAccountKey, externalAccountKey, feesChargedVia, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "FeeType").equalsIgnoreCase("TYPE_MINIMUM_FEE_OFFSET"));

		log("--------------------login acccount without access--------------------");
		soapResponse = this.commissioningInformationRequest(Settings.USERNAME, Settings.PASSWORD, callerSystemID,
				priveAccountKey, externalAccountKey, feesChargedVia, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "faultstring").contains("Access is denied"));

		log("--------------------wrong login information--------------------");
		soapResponse = this.commissioningInformationRequest(username, "abc", callerSystemID, priveAccountKey,
				externalAccountKey, feesChargedVia, url);

		this.printSoapResponse(soapResponse);

		assertTrue(
				this.getTextContentByTagName(soapResponse, "faultstring").contains("Username or password incorrect"));

		log("--------------------invalid caller system ID--------------------");
		soapResponse = this.commissioningInformationRequest(username, password, "invalid caller system ID",
				priveAccountKey, externalAccountKey, feesChargedVia, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage").contains("Invalid caller system ID"));

		log("--------------------Invalid account key--------------------");
		soapResponse = this.commissioningInformationRequest(username, password, callerSystemID, "123457",
				externalAccountKey, feesChargedVia, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "faultstring").contains("Access is denied"));

		log("--------------------Invalid feesChargedVia--------------------");
		soapResponse = this.commissioningInformationRequest(username, password, callerSystemID, priveAccountKey,
				externalAccountKey, "feesChargedVia", url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage")
				.contains("Invalid parameters or missing required parameters"));
	}

	@Test
	public void testCommissioningInformationByAdvisorRequest() throws Exception {
		String username = Settings.ADVISOR_USERNAME;
		String password = Settings.ADVISOR_PASSWORD;
		String callerSystemID = "LIQID";
		String externalAccountKey = "";
		String priveAccountKey = "31997";
		String feesChargedVia = "FEES_CHARGED_OUTSIDE_PRODUCT_PROVIDER";
		// String feesChargedVia = "";
		String url = wsdl_107 + "CommissioningInformationByAdvisor.wsdl";

		log("--------------------CommissioningInformationByAdvisor--------------------");
		SOAPMessage soapResponse = this.commissioningInformationByAdvisorRequest(username, password, callerSystemID,
				priveAccountKey, externalAccountKey, feesChargedVia, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "Success").equalsIgnoreCase("true"));

		log("--------------------login account without access--------------------");
		soapResponse = this.commissioningInformationByAdvisorRequest(Settings.USERNAME, Settings.PASSWORD,
				callerSystemID, priveAccountKey, externalAccountKey, feesChargedVia, url);
		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "faultstring").contains("Access is denied"));

		log("--------------------wrong login information--------------------");
		soapResponse = this.commissioningInformationByAdvisorRequest(username, "abc", callerSystemID, priveAccountKey,
				externalAccountKey, feesChargedVia, url);

		this.printSoapResponse(soapResponse);

		assertTrue(
				this.getTextContentByTagName(soapResponse, "faultstring").contains("Username or password incorrect"));

		log("--------------------invalid caller system ID--------------------");
		soapResponse = this.commissioningInformationByAdvisorRequest(username, password, "invalid caller system ID",
				priveAccountKey, externalAccountKey, feesChargedVia, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage").contains("Invalid caller system ID"));

		log("--------------------Invalid account key--------------------");
		soapResponse = this.commissioningInformationByAdvisorRequest(username, password, callerSystemID, "123457",
				externalAccountKey, feesChargedVia, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "faultstring").contains("NullPointerException"));

		log("--------------------Invalid feesChargedVia--------------------");
		soapResponse = this.commissioningInformationByAdvisorRequest(username, password, callerSystemID,
				priveAccountKey, externalAccountKey, "feesChargedVia", url);
		// TODO - assertion - SLYAWS-9621
		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "Success").contains("true"));
	}

	@Ignore
	public void testCRMActivityGetRequest() throws Exception {
		String username = Settings.CRM_USERNAME;
		String password = Settings.CRM_USERNAME;
		String url = wsdl_107 + "CRMActivityGet.wsdl";
		String callerSystemID = "LIQID";
		String priveUserKey = "190151402";
		String externalUserKey = "";
		String priveActivityKey = "";
		String externalActivityKey = "";
		String lastModifiedTimestampStartDate = "2016-12-02T10:00:00";
		String lastModifiedTimestampEndDate = "2016-12-02T11:00:00";
		SOAPMessage soapResponse = this.CRMActivityGetRequest(username, password, callerSystemID, priveUserKey,
				externalUserKey, priveActivityKey, externalActivityKey, lastModifiedTimestampStartDate,
				lastModifiedTimestampEndDate, url);
		this.printSoapResponse(soapResponse);
	}

	@Ignore
	public void testCRMTaskGetRequest() throws Exception {
		String username = Settings.CRM_USERNAME;
		String password = Settings.CRM_USERNAME;
		String url = wsdl_107 + "CRMActivityGet.wsdl";
		String callerSystemID = "LIQID";
		String priveUserKey = "190151402";
		String externalUserKey = "";
		String priveTaskKey = "";
		String externalTaskKey = "";
		String lastModifiedTimestampStartDate = "2016-12-02T10:00:00";
		String lastModifiedTimestampEndDate = "2016-12-02T11:00:00";
		SOAPMessage soapResponse = this.CRMTaskGetRequest(username, password, callerSystemID, priveUserKey,
				externalUserKey, priveTaskKey, externalTaskKey, lastModifiedTimestampStartDate,
				lastModifiedTimestampEndDate, url);
		this.printSoapResponse(soapResponse);
	}

	@Test
	public void testUpdateUserRequestAndInvestorGet() throws Exception {
		String username = "NorthstarWSUser";
		String password = "NorthstarWSUser";
		String callerSystemID = "LANSARE";
		String action = "CREATE_UPDATE";
		String action_delete = "DELETE";
		String priveKey = "";
		String externalKey = String.valueOf(new Random().nextInt(10000) + 1);
		String active = "true";
		String userRole = "INVESTOR";
		String gender = "MALE";
		String firstName = "Alejandro";
		String lastName = "Moreno";
		String birthdate = "1990-01-01";
		String preferred = "false";
		String emailType = "OFFICE";
		String email = "notarealaddress@nfs.bm";
		String baseCurrency = "USD";
		String country = "USA";
		String idNumber = externalKey;
		String idNumberType = "ID_NO";
		String martialStatus = "SINGLE";
		String url = wsdl_107 + "userUpdate.wsdl";
		String entityType = "ADVISOR_COMPANY";
		String companyPriveKey = "";
		String companyExternalKey = "NSI233";
		String companyName = "XXXX";
		String mainAdvisorExternalKey = "9029";
		log("---create---");
		SOAPMessage soapResponse = this.UpdateUserRequest(username, password, callerSystemID, action, priveKey,
				externalKey, active, userRole, gender, firstName, lastName, companyName, birthdate, preferred,
				emailType, email, baseCurrency, country, mainAdvisorExternalKey, idNumber, idNumberType, martialStatus,
				entityType, companyPriveKey, companyExternalKey, url);

		this.printSoapResponse(soapResponse);
		assertTrue(this.getTextContentByTagName(soapResponse, "ExternalKey").equalsIgnoreCase(externalKey));

		log("---investorGetRequest---");
		soapResponse = this.investorGetRequest(username, password, callerSystemID, "", externalKey, url);
		this.printSoapResponse(soapResponse);
		assertTrue(this.getTextContentByTagName(soapResponse, "FirstName").equalsIgnoreCase(firstName));

		log("---delete---");
		soapResponse = this.UpdateUserRequest(username, password, callerSystemID, action_delete, priveKey, externalKey,
				active, userRole, gender, firstName, lastName, companyName, birthdate, preferred, emailType, email,
				baseCurrency, country, mainAdvisorExternalKey, idNumber, idNumberType, martialStatus, entityType,
				companyPriveKey, companyExternalKey, url);

		this.printSoapResponse(soapResponse);
		assertTrue(this.getTextContentByTagName(soapResponse, "ExternalKey").equalsIgnoreCase(externalKey));
	}

	@Test
	public void testUpdateUserRequest() throws Exception {
		String username = "NorthstarWSUser";
		String password = "NorthstarWSUser";
		String callerSystemID = "LANSARE";
		String action = "CREATE_UPDATE";
		String action_delete = "DELETE";
		String priveKey = "";
		String externalKey = String.valueOf(new Random().nextInt(10000) + 1);
		String active = "true";
		String userRole = "EXECUTIONPLATFORM_USER";
		String gender = "MALE";
		String firstName = "Alejandro";
		String lastName = "Moreno";
		String companyName = "Northstar";
		String birthdate = "1990-01-01";
		String preferred = "false";
		String emailType = "OFFICE";
		String email = "notarealaddress@nfs.bm";
		String baseCurrency = "USD";
		String country = "USA";
		String idNumber = externalKey;
		String idNumberType = "ID_NO";
		String martialStatus = "SINGLE";
		String entityType = "EXECUTION_PLATFORM_PROVIDER";
		String companyPriveKey = "";
		String companyExternalKey = "000001";
		String mainAdvisorExternalKey = "";
		String url = wsdl_107 + "userUpdate.wsdl";
		log("-----------------------------testUpdateUserRequest-----------------------------------------");
		System.out.println();
		log("-----------------------------EXECUTIONPLATFORM_USER-----------------------------------------");
		// add EXECUTIONPLATFORM_USER
		System.out.println("--Add user--");
		SOAPMessage soapResponse = this.UpdateUserRequest(username, password, callerSystemID, action, priveKey,
				externalKey, active, userRole, gender, firstName, lastName, companyName, birthdate, preferred,
				emailType, email, baseCurrency, country, mainAdvisorExternalKey, idNumber, idNumberType, martialStatus,
				entityType, companyPriveKey, companyExternalKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ExternalKey").contains(externalKey));

		// delete EXECUTIONPLATFORM_USER
		System.out.println("--Delete user--");
		soapResponse = this.UpdateUserRequest(username, password, callerSystemID, action_delete, priveKey, externalKey,
				active, userRole, gender, firstName, lastName, companyName, birthdate, preferred, emailType, email,
				baseCurrency, country, mainAdvisorExternalKey, idNumber, idNumberType, martialStatus, entityType,
				companyPriveKey, companyExternalKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ExternalKey").contains(externalKey));

		System.out.println();
		log("\n" + "-----------------------------ADVISOR_CONSULTANT-----------------------------------------");
		externalKey = String.valueOf(new Random().nextInt(10000) + 1);
		active = "true";
		userRole = "ADVISOR_CONSULTANT";
		gender = "MALE";
		firstName = "Jorge";
		lastName = "Aroche";
		companyName = "ABC Com";
		birthdate = "1990-01-01";
		preferred = "false";
		emailType = "HOME";
		email = "lllllll@nfs.bm";
		baseCurrency = "USD";
		country = "USA";
		idNumber = externalKey;
		idNumberType = "ID_NO";
		martialStatus = "SINGLE";
		entityType = "ADVISOR_COMPANY";
		companyPriveKey = "";
		companyExternalKey = "NSI233";

		// add ADVISOR_CONSULTANT
		System.out.println("--Add user--");
		soapResponse = this.UpdateUserRequest(username, password, callerSystemID, action, priveKey, externalKey, active,
				userRole, gender, firstName, lastName, companyName, birthdate, preferred, emailType, email,
				baseCurrency, country, mainAdvisorExternalKey, idNumber, idNumberType, martialStatus, entityType,
				companyPriveKey, companyExternalKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ExternalKey").contains(externalKey));

		// delete ADVISOR_CONSULTANT
		System.out.println("--Delete user--");
		soapResponse = this.UpdateUserRequest(username, password, callerSystemID, action_delete, priveKey, externalKey,
				active, userRole, gender, firstName, lastName, companyName, birthdate, preferred, emailType, email,
				baseCurrency, country, mainAdvisorExternalKey, idNumber, idNumberType, martialStatus, entityType,
				companyPriveKey, companyExternalKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ExternalKey").contains(externalKey));

		System.out.println();
		log("\n" + "-----------------------------WHOLESALER_SALESPERSON-----------------------------------------");
		externalKey = String.valueOf(new Random().nextInt(10000) + 1);
		active = "true";
		userRole = "WHOLESALER_SALESPERSON";
		gender = "MALE";
		firstName = "Test";
		lastName = "Wholesaler";
		companyName = "ABC Com";
		birthdate = "1990-01-01";
		preferred = "false";
		emailType = "HOME";
		email = "lllllll@nfs.bm";
		baseCurrency = "USD";
		country = "USA";
		idNumber = externalKey;
		idNumberType = "ID_NO";
		martialStatus = "SINGLE";
		entityType = "WHOLESALER_COMPANY";
		companyPriveKey = "";
		companyExternalKey = "3";

		// add WHOLESALER_SALESPERSON
		System.out.println("--Add user--");
		soapResponse = this.UpdateUserRequest(username, password, callerSystemID, action, priveKey, externalKey, active,
				userRole, gender, firstName, lastName, companyName, birthdate, preferred, emailType, email,
				baseCurrency, country, mainAdvisorExternalKey, idNumber, idNumberType, martialStatus, entityType,
				companyPriveKey, companyExternalKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ExternalKey").contains(externalKey));

		System.out.println("--Delete user--");

		// delete WHOLESALER_SALESPERSON
		soapResponse = this.UpdateUserRequest(username, password, callerSystemID, action_delete, priveKey, externalKey,
				active, userRole, gender, firstName, lastName, companyName, birthdate, preferred, emailType, email,
				baseCurrency, country, mainAdvisorExternalKey, idNumber, idNumberType, martialStatus, entityType,
				companyPriveKey, companyExternalKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ExternalKey").contains(externalKey));

		log("-----------------------------invalid action-----------------------------------------");
		externalKey = String.valueOf(new Random().nextInt(10000) + 1);
		soapResponse = this.UpdateUserRequest(username, password, callerSystemID, "ABC", priveKey, externalKey, active,
				userRole, gender, firstName, lastName, companyName, birthdate, preferred, emailType, email,
				baseCurrency, country, mainAdvisorExternalKey, idNumber, idNumberType, martialStatus, entityType,
				companyPriveKey, companyExternalKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage")
				.contains("UserUpdate action has not been found in the request message"));

		log("-----------------------------no base currency-----------------------------------------");
		externalKey = String.valueOf(new Random().nextInt(10000) + 1);
		soapResponse = this.UpdateUserRequest(username, password, callerSystemID, action, priveKey, externalKey, active,
				userRole, gender, firstName, lastName, companyName, birthdate, preferred, emailType, email, "", country,
				mainAdvisorExternalKey, idNumber, idNumberType, martialStatus, entityType, companyPriveKey,
				companyExternalKey, url);

		this.printSoapResponse(soapResponse);

		// TODO - assertion

		log("-----------------------------wrong base currency-----------------------------------------");
		externalKey = String.valueOf(new Random().nextInt(10000) + 1);
		soapResponse = this.UpdateUserRequest(username, password, callerSystemID, action, priveKey, externalKey, active,
				userRole, gender, firstName, lastName, companyName, birthdate, preferred, emailType, email, "ABCDE",
				country, mainAdvisorExternalKey, idNumber, idNumberType, martialStatus, entityType, companyPriveKey,
				companyExternalKey, url);

		this.printSoapResponse(soapResponse);
		// TODO - assertion

		log("--------------------------login account without access----------------------------------");
		externalKey = String.valueOf(new Random().nextInt(10000) + 1);
		soapResponse = this.UpdateUserRequest(Settings.USERNAME, Settings.PASSWORD, callerSystemID, action, priveKey,
				externalKey, active, userRole, gender, firstName, lastName, companyName, birthdate, preferred,
				emailType, email, baseCurrency, country, mainAdvisorExternalKey, idNumber, idNumberType, martialStatus,
				entityType, companyPriveKey, companyExternalKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage").contains("Access is denied"));

		log("--------------------------wrong login information----------------------------------");
		externalKey = String.valueOf(new Random().nextInt(10000) + 1);
		soapResponse = this.UpdateUserRequest(Settings.USERNAME, password, callerSystemID, action, priveKey,
				externalKey, active, userRole, gender, firstName, lastName, companyName, birthdate, preferred,
				emailType, email, baseCurrency, country, mainAdvisorExternalKey, idNumber, idNumberType, martialStatus,
				entityType, companyPriveKey, companyExternalKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(
				this.getTextContentByTagName(soapResponse, "faultstring").contains("Username or password incorrect"));

		log("-----------------------------no external key-----------------------------------------");
		externalKey = "";
		soapResponse = this.UpdateUserRequest(username, password, callerSystemID, action, priveKey, externalKey, active,
				userRole, gender, firstName, lastName, companyName, birthdate, preferred, emailType, email,
				baseCurrency, country, mainAdvisorExternalKey, idNumber, idNumberType, martialStatus, entityType,
				companyPriveKey, companyExternalKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage")
				.contains("Neither the Prive key nor the external key are given"));

		log("--------------------------invalid caller system id------------------------------------");
		externalKey = String.valueOf(new Random().nextInt(10000) + 1);
		soapResponse = this.UpdateUserRequest(username, password, "invalid caller system id", action, priveKey,
				externalKey, active, userRole, gender, firstName, lastName, companyName, birthdate, preferred,
				emailType, email, baseCurrency, country, mainAdvisorExternalKey, idNumber, idNumberType, martialStatus,
				entityType, companyPriveKey, companyExternalKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage").contains("Invalid caller system ID"));

	}

	@Test
	public void testUpdateAccountRequest() throws Exception {

		String username = "NorthstarWSUser";
		String password = "NorthstarWSUser";
		String callerSystemID = "LANSARE";
		String action = "CREATE_UPDATE";
		String action_delete = "DELETE";
		String externalKey = String.valueOf(new Random().nextInt(10000) + 1);
		String ownedByUserExternalKey = "7200";
		// Go to DevAdmin -> Search the account NorthstarWSUser and edit the
		// Lansare external key
		String name = "Test";
		String executionPlatformExternalKey = "000001";
		String mainAdvisorExternalKey = "7200";
		String url = wsdl_107 + "accountUpdate.wsdl";
		log("---------------------------testUpdateAccountRequest--------------------------------------");
		System.out.println("");
		SOAPMessage soapResponse = null;
		// TODO : login account without access
		// log("---------------------------login account without
		// access--------------------------------------");
		// soapResponse = this.UpdateAccountRequest(Settings.USERNAME,
		// Settings.PASSWORD,
		// callerSystemID, action, externalKey,
		// executionPlatformExternalKey, name, ownedByUserExternalKey,
		// mainAdvisorExternalKey, url);
		//
		// this.printSoapResponse(soapResponse);
		//
		// assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage")
		// .contains("Access is denied"));
		//

		soapResponse = this.UpdateAccountRequest(username, password, callerSystemID, action, externalKey,
				executionPlatformExternalKey, name, ownedByUserExternalKey, mainAdvisorExternalKey, url);

		this.printSoapResponse(soapResponse);

		log("---------------------------wrong login information--------------------------------------");
		soapResponse = this.UpdateAccountRequest(Settings.USERNAME, password, callerSystemID, action, externalKey,
				executionPlatformExternalKey, name, ownedByUserExternalKey, mainAdvisorExternalKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(
				this.getTextContentByTagName(soapResponse, "faultstring").contains("Username or password incorrect"));

		log("---------------------------invalid caller system id--------------------------------------");
		soapResponse = this.UpdateAccountRequest(username, password, "invalid caller system id", action, externalKey,
				executionPlatformExternalKey, name, ownedByUserExternalKey, mainAdvisorExternalKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage").contains("Invalid caller system ID"));

		log("---------------------------no ExternalKey--------------------------------------");
		soapResponse = this.UpdateAccountRequest(username, password, callerSystemID, action, "",
				executionPlatformExternalKey, name, ownedByUserExternalKey, mainAdvisorExternalKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage")
				.contains("Neither the Prive key nor the external key are given"));

		log("---------------------------no ExecutionPlatformExternalKey--------------------------------------");
		soapResponse = this.UpdateAccountRequest(username, password, callerSystemID, action, externalKey, "", name,
				ownedByUserExternalKey, mainAdvisorExternalKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage")
				.contains("Execution platform not found for the account"));

		log("---------------------------no Name--------------------------------------");
		soapResponse = this.UpdateAccountRequest(username, password, callerSystemID, action, externalKey,
				executionPlatformExternalKey, "", ownedByUserExternalKey, mainAdvisorExternalKey, url);

		this.printSoapResponse(soapResponse);

		// TODO - assertion
		log("---------------------------no OwnedByUserExternalKey--------------------------------------");
		soapResponse = this.UpdateAccountRequest(username, password, callerSystemID, action, externalKey,
				executionPlatformExternalKey, name, "", mainAdvisorExternalKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage").contains("Account's owner not found"));

		log("---------------------------no MainAdvisorExternalKey--------------------------------------");
		soapResponse = this.UpdateAccountRequest(username, password, callerSystemID, action, externalKey,
				executionPlatformExternalKey, name, ownedByUserExternalKey, "", url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage")
				.contains("Account's Main Advisor not found"));

		log("---------------------------wrong action--------------------------------------");
		soapResponse = this.UpdateAccountRequest(username, password, callerSystemID, "abc", externalKey,
				executionPlatformExternalKey, name, ownedByUserExternalKey, mainAdvisorExternalKey, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage")
				.contains("AccountUpdate action has not been found in the request message"));
	}

	@Test
	public void testUpdateCompanyRequest() throws Exception {
		String username = "NorthstarWSUser";
		String password = "NorthstarWSUser";
		String callerSystemID = "LANSARE";
		String action = "CREATE_UPDATE";
		String action_delete = "DELETE";
		String externalKey = String.valueOf(new Random().nextInt(10000) + 1);
		String companyType = "ADVISOR_COMPANY";
		String name = "RAYMOND JAMES - BRANCH " + String.valueOf(new Random().nextInt(10000) + 1);
		String baseCurrency = "USD";
		String mainContactExternalKey = "8680";
		String wholesalerCompanyExternalKeys = "3";
		String preferred = "true";
		String type = "OFFICE";
		String country = "HKG";
		String city = "City";
		String zipCode = "1234567";
		String address = "Some street";
		String url = wsdl_107 + "companyUpdate.wsdl";
		log("---------------------------testUpdateCompanyRequest---------------------------------------");
		System.out.println("");
		log("---------------------------no company name---------------------------------------");
		SOAPMessage soapResponse = this.UpdateCompanyRequest(username, password, callerSystemID, action, externalKey,
				companyType, "", baseCurrency, mainContactExternalKey, wholesalerCompanyExternalKeys, preferred, type,
				country, city, zipCode, address, url);

		this.printSoapResponse(soapResponse);
		// TODO - assertion

		log("---------------------------no company type---------------------------------------");
		soapResponse = this.UpdateCompanyRequest(username, password, callerSystemID, action, externalKey, "", name,
				baseCurrency, mainContactExternalKey, wholesalerCompanyExternalKeys, preferred, type, country, city,
				zipCode, address, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage").contains("Company type is incorrect"));

		log("---------------------------no base currency---------------------------------------");
		soapResponse = this.UpdateCompanyRequest(username, password, callerSystemID, action, externalKey, companyType,
				name, "", mainContactExternalKey, wholesalerCompanyExternalKeys, preferred, type, country, city,
				zipCode, address, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage")
				.contains("Company's base currency is not found"));

		log("---------------------------wrong base currency---------------------------------------");
		soapResponse = this.UpdateCompanyRequest(username, password, callerSystemID, action, externalKey, companyType,
				name, "ABCDEF", mainContactExternalKey, wholesalerCompanyExternalKeys, preferred, type, country, city,
				zipCode, address, url);

		this.printSoapResponse(soapResponse);
		// TODO - assertion
		log("---------------------------login account without access---------------------------------------");
		soapResponse = this.UpdateCompanyRequest(Settings.USERNAME, Settings.PASSWORD, callerSystemID, action,
				externalKey, companyType, name, baseCurrency, mainContactExternalKey, wholesalerCompanyExternalKeys,
				preferred, type, country, city, zipCode, address, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage").contains("Access is denied"));

		log("---------------------------invalid caller system id---------------------------------------");
		soapResponse = this.UpdateCompanyRequest(username, password, "ABC", action, externalKey, companyType, name,
				baseCurrency, mainContactExternalKey, wholesalerCompanyExternalKeys, preferred, type, country, city,
				zipCode, address, url);

		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage").contains("Invalid caller system ID"));

		log("---------------------------wrong login information---------------------------------------");
		soapResponse = this.UpdateCompanyRequest(Settings.USERNAME, password, callerSystemID, action, externalKey,
				companyType, name, baseCurrency, mainContactExternalKey, wholesalerCompanyExternalKeys, preferred, type,
				country, city, zipCode, address, url);
		this.printSoapResponse(soapResponse);

		assertTrue(
				this.getTextContentByTagName(soapResponse, "faultstring").contains("Username or password incorrect"));

		log("---------------------------no external key---------------------------------------");
		soapResponse = this.UpdateCompanyRequest(username, password, callerSystemID, action, "", companyType, name,
				baseCurrency, mainContactExternalKey, wholesalerCompanyExternalKeys, preferred, type, country, city,
				zipCode, address, url);
		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage")
				.contains("Company external key is not found"));

		log("---------------------------invalid action---------------------------------------");
		soapResponse = this.UpdateCompanyRequest(username, password, callerSystemID, "ABC", externalKey, companyType,
				name, baseCurrency, mainContactExternalKey, wholesalerCompanyExternalKeys, preferred, type, country,
				city, zipCode, address, url);
		this.printSoapResponse(soapResponse);

		assertTrue(this.getTextContentByTagName(soapResponse, "ErrorMessage")
				.contains("No account details has been found to update account"));

	}

	@Test
	public void testCompleteUpdateFlow() throws Exception {
		/** company variables */
		String username = "NorthstarWSUser";
		String password = "NorthstarWSUser";
		String callerSystemID = "LANSARE";
		String action = "CREATE_UPDATE";
		String action_delete = "DELETE";
		String externalKey = String.valueOf(new Random().nextInt(10000) + 1);
		String companyType = "ADVISOR_COMPANY";
		String name = "RAYMOND JAMES - BRANCH " + String.valueOf(new Random().nextInt(10000) + 1);
		String baseCurrency = "USD";
		String mainContactExternalKey = "8680";
		String wholesalerCompanyExternalKeys = "3";
		String preferred = "true";
		String type = "OFFICE";
		String country = "HKG";
		String city = "City";
		String zipCode = "1234567";
		String address = "Some street";
		String company_url = wsdl_107 + "companyUpdate.wsdl";

		/** user variables */
		String priveKey = "";
		String active = "true";
		String userRole = "ADVISOR_CONSULTANT";
		String gender = "MALE";
		String firstName = "Alejandro";
		String lastName = "Moreno";
		String companyName = name;
		String birthdate = "1990-01-01";
		String emailType = "OFFICE";
		String email = "notarealaddress@nfs.bm";
		String companyPriveKey = "";
		String mainAdvisorExternalKey = "";
		String idNumber = externalKey;
		String idNumberType = "ID_NO";
		String martialStatus = "SINGLE";
		String entityType = "ADVISOR_COMPANY";
		String user_url = wsdl_107 + "userUpdate.wsdl";

		/** account variables */
		String ownedByUserExternalKey = "";
		String account_url = wsdl_107 + "accountUpdate.wsdl";
		log("---------------------------create company---------------------------------------");
		SOAPMessage soapResponse = this.UpdateCompanyRequest(username, password, callerSystemID, action, externalKey,
				companyType, name, baseCurrency, mainContactExternalKey, wholesalerCompanyExternalKeys, preferred, type,
				country, city, zipCode, address, company_url);
		this.printSoapResponse(soapResponse);
		assertTrue(this.getTextContentByTagName(soapResponse, "ExternalKey").equalsIgnoreCase(externalKey));
		String companyExternalKey = this.getTextContentByTagName(soapResponse, "ExternalKey");

		log("-----------------------------create user-----------------------------------------");
		externalKey = String.valueOf(new Random().nextInt(10000) + 1);
		idNumber = externalKey;
		soapResponse = this.UpdateUserRequest(username, password, callerSystemID, action, priveKey, externalKey, active,
				userRole, gender, firstName, lastName, companyName, birthdate, preferred, emailType, email,
				baseCurrency, country, mainAdvisorExternalKey, idNumber, idNumberType, martialStatus, entityType,
				companyPriveKey, companyExternalKey, user_url);
		this.printSoapResponse(soapResponse);
		assertTrue(this.getTextContentByTagName(soapResponse, "ExternalKey").equalsIgnoreCase(externalKey));
		String userExternalKey = this.getTextContentByTagName(soapResponse, "ExternalKey");

		// log("-----------------------------retrieve
		// advisor-----------------------------------------");
		// soapResponse = this.advisorGetRequest(username, password,
		// callerSystemID, userExternalKey, "", wsdl_107
		// + "AdvisorGet.wsdl");
		// this.printSoapResponse(soapResponse);
		// assertTrue(this.getTextContentByTagName(soapResponse, "Name")
		// .equalsIgnoreCase(firstName));

		log("-----------------------------create account-----------------------------------------");

		externalKey = String.valueOf(new Random().nextInt(10000) + 1);
		name = "James" + String.valueOf(new Random().nextInt(10000) + 1);
		ownedByUserExternalKey = userExternalKey;
		mainAdvisorExternalKey = userExternalKey;
		String executionPlatformExternalKey = "000002";

		soapResponse = this.UpdateAccountRequest(username, password, callerSystemID, action, externalKey,
				executionPlatformExternalKey, name, ownedByUserExternalKey, mainAdvisorExternalKey, account_url);
		this.printSoapResponse(soapResponse);
		assertTrue(this.getTextContentByTagName(soapResponse, "ExternalKey").equalsIgnoreCase(externalKey));

		log("-----------------------------retrieve account-----------------------------------------");
		soapResponse = this.accountDetailGetRequest(username, password, callerSystemID, "", externalKey,
				wsdl_107 + "AccountDetailsGet.wsdl");
		this.printSoapResponse(soapResponse);
		assertTrue(this.getTextContentByTagName(soapResponse, "Name").equalsIgnoreCase(name));

		log("-----------------------------delete account-----------------------------------------");
		soapResponse = this.UpdateAccountRequest(username, password, callerSystemID, action_delete, externalKey,
				executionPlatformExternalKey, name, ownedByUserExternalKey, mainAdvisorExternalKey, account_url);
		this.printSoapResponse(soapResponse);
		assertTrue(this.getTextContentByTagName(soapResponse, "ExternalKey").equalsIgnoreCase(externalKey));

		mainAdvisorExternalKey = "";
		log("-----------------------------delete user-----------------------------------------");
		soapResponse = this.UpdateUserRequest(username, password, callerSystemID, action_delete, priveKey,
				userExternalKey, active, userRole, gender, firstName, lastName, companyName, birthdate, preferred,
				emailType, email, baseCurrency, country, mainAdvisorExternalKey, idNumber, idNumberType, martialStatus,
				entityType, companyPriveKey, companyExternalKey, user_url);
		this.printSoapResponse(soapResponse);
		assertTrue(this.getTextContentByTagName(soapResponse, "ExternalKey").equalsIgnoreCase(userExternalKey));

		log("-----------------------------delete company-----------------------------------------");
		soapResponse = this.UpdateCompanyRequest(username, password, callerSystemID, action, companyExternalKey,
				companyType, name, baseCurrency, mainContactExternalKey, wholesalerCompanyExternalKeys, preferred, type,
				country, city, zipCode, address, company_url);
		this.printSoapResponse(soapResponse);
		assertTrue(this.getTextContentByTagName(soapResponse, "ExternalKey").equalsIgnoreCase(companyExternalKey));
	}

	@Ignore
	public void testSampleWebServices() {
		HttpClient httpClient = HttpClientBuilder.create().build();

		try {
			HttpPost request = new HttpPost("http://192.168.1.111/b2b/1/citi-hk/performance");
			StringEntity params = new StringEntity(
					"{\"reference-currency\": \"HKD\",\"months\": 12,\"current-portfolio\": [{\"asset-code\": \"LU0069950631\",\"asset-code-scheme\": \"ISIN\",\"asset-currency\": \"USD\",\"value\": 1000000},{\"asset-code\": \"LU0139292972\",\"asset-code-scheme\": \"ISIN\",\"asset-currency\": \"EUR\",\"value\": 42.42}],\"model\": \"P2\"}");
			request.addHeader("Content-Type", "application/json");
			request.setEntity(params);
			HttpResponse response = httpClient.execute(request);
			log(String.valueOf(response));
		} catch (Exception e) {

		}
	}

	public SOAPMessage accountPerformanceGetRequest(String username, String password, String callerSystemID,
			String priveAccountKey, String externalAccountKey, String url) throws Exception {

		MessageFactory factory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();

		SOAPMessage message = factory.createMessage();
		SOAPPart soapPart = message.getSOAPPart();
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("bin", "http://www.sly.org/main/server/webservice/bindings");

		SOAPHeader header = envelope.getHeader();
		header.addHeaderElement(
				new QName("bin", "bin:PriveHeader username='" + username + "' password='" + password + "'"));
		SOAPBody body = envelope.getBody();
		SOAPBodyElement element1 = body.addBodyElement(new QName("bin:AccountPerformanceGetRequest"));

		SOAPElement CallerSystemID = element1.addChildElement("CallerSystemID", "bin");
		CallerSystemID.addTextNode(callerSystemID);

		SOAPElement PriveAccountKey = element1.addChildElement("PriveAccountKey", "bin");
		PriveAccountKey.addTextNode(priveAccountKey);

		SOAPElement ExternalAccountKey = element1.addChildElement("ExternalAccountKey", "bin");
		ExternalAccountKey.addTextNode(externalAccountKey);

		SOAPElement portfolioInventorySource = element1.addChildElement("PortfolioInventorySource", "bin");
		portfolioInventorySource.addTextNode("REPORT");

		message.saveChanges();

		printRequestXML(message);

		SOAPConnection soapConnection = soapConnectionFactory.createConnection();

		SOAPMessage soapResponse = soapConnection.call(message, url);
		soapConnection.close();

		return soapResponse;
	}

	public SOAPMessage accountDetailGetRequest(String username, String password, String callerSystemID,
			String priveAccountKey, String externalAccountKey, String url) throws Exception {

		MessageFactory factory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();

		SOAPMessage message = factory.createMessage();
		SOAPPart soapPart = message.getSOAPPart();
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("bin", "http://www.sly.org/main/server/webservice/bindings");

		SOAPHeader header = envelope.getHeader();
		header.addHeaderElement(
				new QName("bin", "bin:PriveHeader username='" + username + "' password='" + password + "'"));
		SOAPBody body = envelope.getBody();
		SOAPBodyElement element1 = body.addBodyElement(new QName("bin:AccountDetailsGetRequest"));

		SOAPElement CallerSystemID = element1.addChildElement("CallerSystemID", "bin");
		CallerSystemID.addTextNode(callerSystemID);

		SOAPElement PriveAccountKey = element1.addChildElement("PriveAccountKey", "bin");
		PriveAccountKey.addTextNode(priveAccountKey);

		SOAPElement ExternalAccountKey = element1.addChildElement("ExternalAccountKey", "bin");
		ExternalAccountKey.addTextNode(externalAccountKey);
		message.saveChanges();

		printRequestXML(message);

		SOAPConnection soapConnection = soapConnectionFactory.createConnection();
		System.out.println("");
		log(url);
		System.out.println("");
		SOAPMessage soapResponse = soapConnection.call(message, url);
		soapConnection.close();

		return soapResponse;
	}

	public SOAPMessage accountListGetRequest(String username, String password, String callerSystemID,
			String priveInvestorKey, String entityType, String entityKey, String url)
			throws SOAPException, IOException, TransformerException {

		MessageFactory factory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();

		SOAPMessage message = factory.createMessage();
		SOAPPart soapPart = message.getSOAPPart();
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("bin", "http://www.sly.org/main/server/webservice/bindings");

		SOAPHeader header = envelope.getHeader();
		header.addHeaderElement(
				new QName("bin", "bin:PriveHeader username='" + username + "' password='" + password + "'"));
		SOAPBody body = envelope.getBody();
		SOAPBodyElement element1 = body.addBodyElement(new QName("bin:AccountsListGetRequest"));

		SOAPElement CallerSystemID = element1.addChildElement("CallerSystemID", "bin");
		CallerSystemID.addTextNode(callerSystemID);

		SOAPElement PriveInvestorKey = element1.addChildElement("PriveInvestorKey", "bin");
		PriveInvestorKey.addTextNode(priveInvestorKey);

		SOAPElement client = element1.addChildElement("Client", "bin");

		client.addChildElement("EntityType", "bin").addTextNode(entityType);

		client.addChildElement("EntityKey" + "", "bin").addTextNode(entityKey);

		message.saveChanges();

		printRequestXML(message);

		SOAPConnection soapConnection = soapConnectionFactory.createConnection();
		log(url);
		SOAPMessage soapResponse = soapConnection.call(message, url);
		soapConnection.close();

		return soapResponse;

	}

	public SOAPMessage accountHoldingsGetRequest(String username, String password, String callerSystemID,
			String priveAccountKey, String externalAccountKey, String portfolioInventorySource, String url)
			throws Exception {

		MessageFactory factory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();

		SOAPMessage message = factory.createMessage();
		SOAPPart soapPart = message.getSOAPPart();
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("bin", "http://www.sly.org/main/server/webservice/bindings");

		SOAPHeader header = envelope.getHeader();
		header.addHeaderElement(
				new QName("bin", "bin:PriveHeader username='" + username + "' password='" + password + "'"));
		SOAPBody body = envelope.getBody();
		SOAPBodyElement element1 = body.addBodyElement(new QName("bin:AccountHoldingsGetRequest"));

		SOAPElement CallerSystemID = element1.addChildElement("CallerSystemID", "bin");
		CallerSystemID.addTextNode(callerSystemID);

		SOAPElement PriveAccountKey = element1.addChildElement("PriveAccountKey", "bin");
		PriveAccountKey.addTextNode(priveAccountKey);

		SOAPElement ExternalAccountKey = element1.addChildElement("ExternalAccountKey", "bin");
		ExternalAccountKey.addTextNode(externalAccountKey);

		SOAPElement PortfolioInventorySource = element1.addChildElement("PortfolioInventorySource", "bin");
		PortfolioInventorySource.addTextNode(portfolioInventorySource);

		message.saveChanges();

		printRequestXML(message);

		SOAPConnection soapConnection = soapConnectionFactory.createConnection();
		log(url);
		SOAPMessage soapResponse = soapConnection.call(message, url);
		soapConnection.close();

		return soapResponse;
	}

	public SOAPMessage accountTransactionsGetRequest(String username, String password, String callerSystemID,
			String priveAccountKey, String externalAccountKey, String url) throws Exception {

		MessageFactory factory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();

		SOAPMessage message = factory.createMessage();
		SOAPPart soapPart = message.getSOAPPart();
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("bin", "http://www.sly.org/main/server/webservice/bindings");

		SOAPHeader header = envelope.getHeader();
		header.addHeaderElement(
				new QName("bin", "bin:PriveHeader username='" + username + "' password='" + password + "'"));
		SOAPBody body = envelope.getBody();
		SOAPBodyElement element1 = body.addBodyElement(new QName("bin:AccountTransactionsGetRequest"));

		SOAPElement CallerSystemID = element1.addChildElement("CallerSystemID", "bin");
		CallerSystemID.addTextNode(callerSystemID);

		SOAPElement PriveAccountKey = element1.addChildElement("PriveAccountKey", "bin");
		PriveAccountKey.addTextNode(priveAccountKey);

		SOAPElement ExternalAccountKey = element1.addChildElement("ExternalAccountKey", "bin");
		ExternalAccountKey.addTextNode(externalAccountKey);

		message.saveChanges();

		printRequestXML(message);

		SOAPConnection soapConnection = soapConnectionFactory.createConnection();

		SOAPMessage soapResponse = soapConnection.call(message, url);
		soapConnection.close();

		return soapResponse;
	}

	public SOAPMessage investorGetRequest(String username, String password, String callerSystemID,
			String priveInvestorKey, String externalInvestorKey, String url) throws Exception {

		MessageFactory factory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();

		SOAPMessage message = factory.createMessage();
		SOAPPart soapPart = message.getSOAPPart();
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("bin", "http://www.sly.org/main/server/webservice/bindings");

		SOAPHeader header = envelope.getHeader();
		header.addHeaderElement(
				new QName("bin", "bin:PriveHeader username='" + username + "' password='" + password + "'"));
		SOAPBody body = envelope.getBody();
		SOAPBodyElement element1 = body.addBodyElement(new QName("bin:InvestorGetRequest"));

		SOAPElement CallerSystemID = element1.addChildElement("CallerSystemID", "bin");
		CallerSystemID.addTextNode(callerSystemID);

		SOAPElement PriveInvestorKey = element1.addChildElement("PriveInvestorKey", "bin");
		PriveInvestorKey.addTextNode(priveInvestorKey);

		SOAPElement ExternalInvestorKey = element1.addChildElement("ExternalInvestorKey", "bin");
		ExternalInvestorKey.addTextNode(externalInvestorKey);
		message.saveChanges();

		printRequestXML(message);

		SOAPConnection soapConnection = soapConnectionFactory.createConnection();
		log(url);
		SOAPMessage soapResponse = soapConnection.call(message, url);
		soapConnection.close();

		return soapResponse;
	}

	public SOAPMessage advisorGetRequest(String username, String password, String callerSystemID,
			String externalAdvisorKey, String priveAdvisorKey, String url) throws Exception {
		MessageFactory factory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();

		SOAPMessage message = factory.createMessage();
		SOAPPart soapPart = message.getSOAPPart();
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("bin", "http://www.sly.org/main/server/webservice/bindings");

		SOAPHeader header = envelope.getHeader();
		header.addHeaderElement(
				new QName("bin", "bin:PriveHeader username='" + username + "' password='" + password + "'"));
		SOAPBody body = envelope.getBody();
		SOAPBodyElement element1 = body.addBodyElement(new QName("bin:AdvisorGetRequest"));

		SOAPElement CallerSystemID = element1.addChildElement("CallerSystemID", "bin");
		CallerSystemID.addTextNode(callerSystemID);

		SOAPElement ExternalAdvisorKey = element1.addChildElement("ExternalAdvisorKey", "bin");
		ExternalAdvisorKey.addTextNode(externalAdvisorKey);

		SOAPElement PriveAdvisorKey = element1.addChildElement("PriveAdvisorKey", "bin");
		PriveAdvisorKey.addTextNode(priveAdvisorKey);

		message.saveChanges();

		printRequestXML(message);

		SOAPConnection soapConnection = soapConnectionFactory.createConnection();
		log(url);
		SOAPMessage soapResponse = soapConnection.call(message, url);
		soapConnection.close();

		return soapResponse;
	}

	public SOAPMessage investmentDetailsGetRequest(String username, String password, String callerSystemID,
			String priveInvestmentKey, String isin, String currency, String datasourceDefinitionKey, String accessToken,
			String loc, String url) throws Exception {

		MessageFactory factory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();

		SOAPMessage message = factory.createMessage();
		SOAPPart soapPart = message.getSOAPPart();
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("bin", "http://www.sly.org/main/server/webservice/bindings");

		SOAPHeader header = envelope.getHeader();
		header.addHeaderElement(
				new QName("bin", "bin:PriveHeader username='" + username + "' password='" + password + "'"));
		SOAPBody body = envelope.getBody();
		SOAPBodyElement element1 = body.addBodyElement(new QName("bin:InvestmentDetailsGetRequest"));

		SOAPElement CallerSystemID = element1.addChildElement("CallerSystemID", "bin");
		CallerSystemID.addTextNode(callerSystemID);

		SOAPElement PriveInvestmentKey = element1.addChildElement("PriveInvestmentKey", "bin");
		PriveInvestmentKey.addTextNode(priveInvestmentKey);

		SOAPElement Isin = element1.addChildElement("Isin", "bin");
		Isin.addTextNode(isin);

		SOAPElement Currency = element1.addChildElement("Currency", "bin");
		Currency.addTextNode(currency);

		SOAPElement DatasourceDefinitionKey = element1.addChildElement("DatasourceDefinitionKey", "bin");
		DatasourceDefinitionKey.addTextNode(datasourceDefinitionKey);

		SOAPElement AccessToken = element1.addChildElement("AccessToken", "bin");
		AccessToken.addTextNode(accessToken);

		SOAPElement locale = element1.addChildElement("locale", "bin");
		locale.addTextNode(loc);

		message.saveChanges();

		printRequestXML(message);

		SOAPConnection soapConnection = soapConnectionFactory.createConnection();
		log(url);
		SOAPMessage soapResponse = soapConnection.call(message, url);
		soapConnection.close();

		return soapResponse;
	}

	public SOAPMessage investmentHistoryGetRequest(String username, String password, String callerSystemID,
			String priveInvestmentKey, String isin, String currency, String datasourceDefinitionKey, String accessToken,
			String url) throws Exception {

		MessageFactory factory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();

		SOAPMessage message = factory.createMessage();
		SOAPPart soapPart = message.getSOAPPart();
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("bin", "http://www.sly.org/main/server/webservice/bindings");

		SOAPHeader header = envelope.getHeader();
		header.addHeaderElement(
				new QName("bin", "bin:PriveHeader username='" + username + "' password='" + password + "'"));
		SOAPBody body = envelope.getBody();
		SOAPBodyElement element1 = body.addBodyElement(new QName("bin:InvestmentHistoryGetRequest"));

		SOAPElement CallerSystemID = element1.addChildElement("CallerSystemID", "bin");
		CallerSystemID.addTextNode(callerSystemID);

		SOAPElement PriveInvestmentKey = element1.addChildElement("PriveInvestmentKey", "bin");
		PriveInvestmentKey.addTextNode(priveInvestmentKey);

		SOAPElement Isin = element1.addChildElement("Isin", "bin");
		Isin.addTextNode(isin);

		SOAPElement Currency = element1.addChildElement("Currency", "bin");
		Currency.addTextNode(currency);

		SOAPElement DatasourceDefinitionKey = element1.addChildElement("DatasourceDefinitionKey", "bin");
		DatasourceDefinitionKey.addTextNode(datasourceDefinitionKey);

		SOAPElement AccessToken = element1.addChildElement("AccessToken", "bin");
		AccessToken.addTextNode(accessToken);

		message.saveChanges();

		printRequestXML(message);

		SOAPConnection soapConnection = soapConnectionFactory.createConnection();
		log(url);
		SOAPMessage soapResponse = soapConnection.call(message, url);
		soapConnection.close();

		return soapResponse;
	}

	public SOAPMessage linkModelPortfolioToAccountRequest(String username, String password, String callerSystemID,
			String action, String externalAccountKey, String priveAccountKey, String priveModelPortfolioKey, String url)
			throws Exception {

		MessageFactory factory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();

		SOAPMessage message = factory.createMessage();
		SOAPPart soapPart = message.getSOAPPart();
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("bin", "http://www.sly.org/main/server/webservice/bindings");

		SOAPHeader header = envelope.getHeader();
		header.addHeaderElement(
				new QName("bin", "bin:PriveHeader username='" + username + "' password='" + password + "'"));
		SOAPBody body = envelope.getBody();
		SOAPBodyElement element1 = body.addBodyElement(new QName("bin:LinkModelPortfolioToAccountRequest"));

		SOAPElement CallerSystemID = element1.addChildElement("CallerSystemID", "bin");
		CallerSystemID.addTextNode(callerSystemID);

		SOAPElement Action = element1.addChildElement("Action", "bin");
		Action.addTextNode(action);

		SOAPElement ExternalAccountKey = element1.addChildElement("ExternalAccountKey", "bin");
		ExternalAccountKey.addTextNode(externalAccountKey);

		SOAPElement PriveAccountKey = element1.addChildElement("PriveAccountKey", "bin");
		PriveAccountKey.addTextNode(priveAccountKey);

		SOAPElement PriveModelPortfolioKey = element1.addChildElement("PriveModelPortfolioKey", "bin");
		PriveModelPortfolioKey.addTextNode(priveModelPortfolioKey);

		message.saveChanges();

		printRequestXML(message);

		SOAPConnection soapConnection = soapConnectionFactory.createConnection();

		SOAPMessage soapResponse = soapConnection.call(message, url);
		soapConnection.close();

		return soapResponse;
	}

	public SOAPMessage commissioningInformationRequest(String username, String password, String callerSystemID,
			String priveAccountKey, String externalAccountKey, String feesChargedVia, String url) throws Exception {

		MessageFactory factory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();

		SOAPMessage message = factory.createMessage();
		SOAPPart soapPart = message.getSOAPPart();
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("bin", "http://www.sly.org/main/server/webservice/bindings");

		SOAPHeader header = envelope.getHeader();
		header.addHeaderElement(
				new QName("bin", "bin:PriveHeader username='" + username + "' password='" + password + "'"));
		SOAPBody body = envelope.getBody();
		SOAPBodyElement element1 = body.addBodyElement(new QName("bin:CommissioningInformationRequest"));

		SOAPElement CallerSystemID = element1.addChildElement("CallerSystemID", "bin");
		CallerSystemID.addTextNode(callerSystemID);

		SOAPElement PriveAccountKey = element1.addChildElement("PriveAccountKey", "bin");
		PriveAccountKey.addTextNode(priveAccountKey);

		SOAPElement ExternalAccountKey = element1.addChildElement("ExternalAccountKey", "bin");
		ExternalAccountKey.addTextNode(externalAccountKey);

		SOAPElement FeesChargedVia = element1.addChildElement("FeesChargedVia", "bin");
		FeesChargedVia.addTextNode(feesChargedVia);

		message.saveChanges();

		printRequestXML(message);

		SOAPConnection soapConnection = soapConnectionFactory.createConnection();

		SOAPMessage soapResponse = soapConnection.call(message, url);
		soapConnection.close();

		return soapResponse;
	}

	public SOAPMessage commissioningInformationByAdvisorRequest(String username, String password, String callerSystemID,
			String priveAccountKey, String externalAccountKey, String feesChargedVia, String url) throws Exception {

		MessageFactory factory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();

		SOAPMessage message = factory.createMessage();
		SOAPPart soapPart = message.getSOAPPart();
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("bin", "http://www.sly.org/main/server/webservice/bindings");

		SOAPHeader header = envelope.getHeader();
		header.addHeaderElement(
				new QName("bin", "bin:PriveHeader username='" + username + "' password='" + password + "'"));
		SOAPBody body = envelope.getBody();
		SOAPBodyElement element1 = body.addBodyElement(new QName("bin:CommissioningInformationByAdvisorRequest"));

		SOAPElement CallerSystemID = element1.addChildElement("CallerSystemID", "bin");
		CallerSystemID.addTextNode(callerSystemID);

		SOAPElement PriveAccountKey = element1.addChildElement("PriveAdvisorKey", "bin");
		PriveAccountKey.addTextNode(priveAccountKey);

		SOAPElement ExternalAccountKey = element1.addChildElement("ExternalAccountKey", "bin");
		ExternalAccountKey.addTextNode(externalAccountKey);

		SOAPElement FeesChargedVia = element1.addChildElement("FeesChargedVia", "bin");
		FeesChargedVia.addTextNode(feesChargedVia);

		message.saveChanges();

		printRequestXML(message);

		SOAPConnection soapConnection = soapConnectionFactory.createConnection();

		SOAPMessage soapResponse = soapConnection.call(message, url);
		soapConnection.close();

		return soapResponse;
	}

	public SOAPMessage CRMActivityGetRequest(String username, String password, String callerSystemID,
			String priveUserKey, String externalUserKey, String priveActivityKey, String externalActivityKey,
			String lastModifiedTimestampStartDate, String lastModifiedTimestampEndDate, String url) throws Exception {

		MessageFactory factory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();

		SOAPMessage message = factory.createMessage();
		SOAPPart soapPart = message.getSOAPPart();
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("bin", "http://www.sly.org/main/server/webservice/bindings");

		SOAPHeader header = envelope.getHeader();
		header.addHeaderElement(
				new QName("bin", "bin:PriveHeader username='" + username + "' password='" + password + "'"));
		SOAPBody body = envelope.getBody();
		SOAPBodyElement element1 = body.addBodyElement(new QName("bin:CRMActivityGetRequest"));

		SOAPElement CallerSystemID = element1.addChildElement("CallerSystemID", "bin");
		CallerSystemID.addTextNode(callerSystemID);

		SOAPElement PriveUserKey = element1.addChildElement("PriveUserKey", "bin");
		PriveUserKey.addTextNode(priveUserKey);

		SOAPElement ExternalUserKey = element1.addChildElement("ExternalUserKey", "bin");
		ExternalUserKey.addTextNode(externalUserKey);

		SOAPElement PriveActivityKey = element1.addChildElement("PriveActivityKey", "bin");
		PriveActivityKey.addTextNode(priveActivityKey);

		SOAPElement ExternalActivityKey = element1.addChildElement("ExternalActivityKey", "bin");
		ExternalActivityKey.addTextNode(externalActivityKey);

		SOAPElement LastModifiedTimestampStartDate = element1.addChildElement("LastModifiedTimestampStartDate", "bin");
		LastModifiedTimestampStartDate.addTextNode(lastModifiedTimestampStartDate);

		SOAPElement LastModifiedTimestampEndDate = element1.addChildElement("LastModifiedTimestampEndDate", "bin");
		LastModifiedTimestampEndDate.addTextNode(lastModifiedTimestampEndDate);

		message.saveChanges();

		printRequestXML(message);

		SOAPConnection soapConnection = soapConnectionFactory.createConnection();

		SOAPMessage soapResponse = soapConnection.call(message, url);
		soapConnection.close();

		return soapResponse;
	}

	public SOAPMessage CRMTaskGetRequest(String username, String password, String callerSystemID, String priveUserKey,
			String externalUserKey, String priveTaskKey, String externalTaskKey, String lastModifiedTimestampStartDate,
			String lastModifiedTimestampEndDate, String url) throws Exception {

		MessageFactory factory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();

		SOAPMessage message = factory.createMessage();
		SOAPPart soapPart = message.getSOAPPart();
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("bin", "http://www.sly.org/main/server/webservice/bindings");

		SOAPHeader header = envelope.getHeader();
		header.addHeaderElement(
				new QName("bin", "bin:PriveHeader username='" + username + "' password='" + password + "'"));
		SOAPBody body = envelope.getBody();
		SOAPBodyElement element1 = body.addBodyElement(new QName("bin:CRMActivityGetRequest"));

		SOAPElement CallerSystemID = element1.addChildElement("CallerSystemID", "bin");
		CallerSystemID.addTextNode(callerSystemID);

		SOAPElement PriveUserKey = element1.addChildElement("PriveUserKey", "bin");
		PriveUserKey.addTextNode(priveUserKey);

		SOAPElement ExternalUserKey = element1.addChildElement("ExternalUserKey", "bin");
		ExternalUserKey.addTextNode(externalUserKey);

		SOAPElement PriveTaskKey = element1.addChildElement("PriveTaskKey", "bin");
		PriveTaskKey.addTextNode(priveTaskKey);

		SOAPElement ExternalTaskKey = element1.addChildElement("ExternalTaskKey", "bin");
		ExternalTaskKey.addTextNode(externalTaskKey);

		SOAPElement LastModifiedTimestampStartDate = element1.addChildElement("LastModifiedTimestampStartDate", "bin");
		LastModifiedTimestampStartDate.addTextNode(lastModifiedTimestampStartDate);

		SOAPElement LastModifiedTimestampEndDate = element1.addChildElement("LastModifiedTimestampEndDate", "bin");
		LastModifiedTimestampEndDate.addTextNode(lastModifiedTimestampEndDate);

		message.saveChanges();

		printRequestXML(message);

		SOAPConnection soapConnection = soapConnectionFactory.createConnection();

		SOAPMessage soapResponse = soapConnection.call(message, url);
		soapConnection.close();

		return soapResponse;
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @param callerSystemID
	 * @param action
	 * @param priveKey
	 * @param externalKey
	 * @param active
	 * @param userRole
	 * @param gender
	 * @param firstName
	 * @param lastName
	 * @param companyName
	 * @param birthdate
	 * @param preferred
	 * @param emailType
	 * @param email
	 * @param baseCurrency
	 * @param country
	 * @param mainAdvisorExternalKey
	 *            applicable if going to create investor,
	 * @param idNumber
	 * @param idNumberType
	 * @param martialStatus
	 * @param entityType
	 * @param companyPriveKey
	 * @param companyExternalKey
	 * @param url
	 * @return
	 * @throws SOAPException
	 * @throws IOException
	 * @throws TransformerException
	 */
	public SOAPMessage UpdateUserRequest(String username, String password, String callerSystemID, String action,
			String priveKey, String externalKey, String active, String userRole, String gender, String firstName,
			String lastName, String companyName, String birthdate, String preferred, String emailType, String email,
			String baseCurrency, String country, String mainAdvisorExternalKey, String idNumber, String idNumberType,
			String martialStatus, String entityType, String companyPriveKey, String companyExternalKey, String url)
			throws SOAPException, IOException, TransformerException {

		MessageFactory factory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();

		SOAPMessage message = factory.createMessage();
		SOAPPart soapPart = message.getSOAPPart();
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("bin", "http://www.sly.org/main/server/webservice/bindings");

		SOAPHeader header = envelope.getHeader();
		header.addHeaderElement(
				new QName("bin", "bin:PriveHeader username='" + username + "' password='" + password + "'"));
		SOAPBody body = envelope.getBody();
		SOAPBodyElement element0 = body.addBodyElement(new QName("bin:UpdateUserRequest"));
		SOAPElement CallerSystemID = element0.addChildElement("CallerSystemID", "bin");
		CallerSystemID.addTextNode(callerSystemID);
		SOAPElement element1 = element0.addChildElement("UserUpdate", "bin");

		SOAPElement Action = element1.addChildElement("Action", "bin");
		Action.addTextNode(action);

		SOAPElement PriveKey = element1.addChildElement("PriveKey", "bin");
		PriveKey.addTextNode(priveKey);

		SOAPElement ExternalKey = element1.addChildElement("ExternalKey", "bin");
		ExternalKey.addTextNode(externalKey);

		SOAPElement UserDetails = element1.addChildElement("UserDetails", "bin");

		UserDetails.addChildElement("Active", "bin").addTextNode(active);
		UserDetails.addChildElement("UserRole", "bin").addTextNode(userRole);
		UserDetails.addChildElement("Gender", "bin").addTextNode(gender);
		UserDetails.addChildElement("FirstName", "bin").addTextNode(firstName);
		UserDetails.addChildElement("LastName", "bin").addTextNode(lastName);
		UserDetails.addChildElement("CompanyName", "bin").addTextNode(companyName);
		UserDetails.addChildElement("Birthdate", "bin").addTextNode(birthdate);

		SOAPElement EmailAddress = UserDetails.addChildElement("EmailAddress", "bin");
		EmailAddress.addChildElement("Preferred", "bin").addTextNode(preferred);
		EmailAddress.addChildElement("Type", "bin").addTextNode(emailType);
		EmailAddress.addChildElement("Email", "bin").addTextNode(email);

		UserDetails.addChildElement("BaseCurrency", "bin").addTextNode(baseCurrency);
		UserDetails.addChildElement("Country", "bin").addTextNode(country);
		UserDetails.addChildElement("MainAdvisorExternalKey", "bin").addTextNode(mainAdvisorExternalKey);
		UserDetails.addChildElement("IdNumber", "bin").addTextNode(idNumber);
		UserDetails.addChildElement("IdNumberType", "bin").addTextNode(idNumberType);
		UserDetails.addChildElement("MartialStatus", "bin").addTextNode(martialStatus);

		SOAPElement CompanyMembership = element1.addChildElement("CompanyMembership", "bin");

		CompanyMembership.addChildElement("PriveKey" + "", "bin").addTextNode(companyPriveKey);

		CompanyMembership.addChildElement("ExternalKey" + "", "bin").addTextNode(companyExternalKey);

		CompanyMembership.addChildElement("EntityType", "bin").addTextNode(entityType);

		message.saveChanges();

		printRequestXML(message);

		SOAPConnection soapConnection = soapConnectionFactory.createConnection();
		log(url);
		SOAPMessage soapResponse = soapConnection.call(message, url);
		soapConnection.close();

		return soapResponse;

	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @param callerSystemID
	 * @param action
	 * @param externalKey
	 * @param executionPlatformExternalKey
	 *            to link with execution platform
	 * @param name
	 * @param ownedByUserExternalKey
	 *            could be investor or advisor
	 * @param mainAdvisorExternalKey
	 *            to link with advisor
	 * @param url
	 * @return
	 * @throws SOAPException
	 * @throws IOException
	 * @throws TransformerException
	 */
	public SOAPMessage UpdateAccountRequest(String username, String password, String callerSystemID, String action,
			String externalKey, String executionPlatformExternalKey, String name, String ownedByUserExternalKey,
			String mainAdvisorExternalKey, String url) throws SOAPException, IOException, TransformerException {

		MessageFactory factory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();

		SOAPMessage message = factory.createMessage();
		SOAPPart soapPart = message.getSOAPPart();
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("bin", "http://www.sly.org/main/server/webservice/bindings");

		SOAPHeader header = envelope.getHeader();
		header.addHeaderElement(
				new QName("bin", "bin:PriveHeader username='" + username + "' password='" + password + "'"));
		SOAPBody body = envelope.getBody();
		SOAPBodyElement element0 = body.addBodyElement(new QName("bin:UpdateAccountRequest"));
		SOAPElement CallerSystemID = element0.addChildElement("CallerSystemID", "bin");
		CallerSystemID.addTextNode(callerSystemID);

		SOAPElement AccountUpdate = element0.addChildElement("AccountUpdate", "bin");
		AccountUpdate.addChildElement("Action", "bin").addTextNode(action);
		AccountUpdate.addChildElement("ExternalKey", "bin").addTextNode(externalKey);

		// existing executionPlatformExternalKey is 000002

		SOAPElement ExecutionPlatformExternalKey = AccountUpdate.addChildElement("ExecutionPlatformExternalKey", "bin");
		ExecutionPlatformExternalKey.addTextNode(executionPlatformExternalKey);

		SOAPElement AccountDetails = AccountUpdate.addChildElement("AccountDetails", "bin");
		AccountDetails.addChildElement("Name", "bin").addTextNode(name);
		AccountDetails.addChildElement("OwnedByUserExternalKey", "bin").addTextNode(ownedByUserExternalKey);
		AccountDetails.addChildElement("MainAdvisorExternalKey", "bin").addTextNode(mainAdvisorExternalKey);

		message.saveChanges();

		printRequestXML(message);

		SOAPConnection soapConnection = soapConnectionFactory.createConnection();
		log(url);
		SOAPMessage soapResponse = soapConnection.call(message, url);
		soapConnection.close();

		return soapResponse;

	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @param callerSystemID
	 * @param action
	 * @param externalKey
	 * @param companyType
	 * @param name
	 * @param baseCurrency
	 * @param mainContactExternalKey
	 * @param wholesalerCompanyExternalKeys
	 *            application for creation of advisor company
	 * @param preferred
	 * @param type
	 * @param country
	 * @param city
	 * @param zipCode
	 * @param address
	 * @param url
	 * @return
	 * @throws SOAPException
	 * @throws IOException
	 * @throws TransformerException
	 */
	public SOAPMessage UpdateCompanyRequest(String username, String password, String callerSystemID, String action,
			String externalKey, String companyType, String name, String baseCurrency, String mainContactExternalKey,
			String wholesalerCompanyExternalKeys, String preferred, String type, String country, String city,
			String zipCode, String address, String url) throws SOAPException, IOException, TransformerException {

		MessageFactory factory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();

		SOAPMessage message = factory.createMessage();
		SOAPPart soapPart = message.getSOAPPart();
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("bin", "http://www.sly.org/main/server/webservice/bindings");

		SOAPHeader header = envelope.getHeader();
		header.addHeaderElement(
				new QName("bin", "bin:PriveHeader username='" + username + "' password='" + password + "'"));
		SOAPBody body = envelope.getBody();
		SOAPBodyElement element0 = body.addBodyElement(new QName("bin:UpdateCompanyRequest"));
		SOAPElement CallerSystemID = element0.addChildElement("CallerSystemID", "bin");
		CallerSystemID.addTextNode(callerSystemID);

		SOAPElement CompanyUpdate = element0.addChildElement("CompanyUpdate", "bin");

		CompanyUpdate.addChildElement("Action", "bin").addTextNode(action);
		CompanyUpdate.addChildElement("ExternalKey", "bin").addTextNode(externalKey);
		CompanyUpdate.addChildElement("CompanyType", "bin").addTextNode(companyType);
		SOAPElement CompanyDetails = CompanyUpdate.addChildElement("CompanyDetails", "bin");
		CompanyDetails.addChildElement("Name", "bin").addTextNode(name);
		CompanyDetails.addChildElement("BaseCurrency", "bin").addTextNode(baseCurrency);
		CompanyDetails.addChildElement("MainContactExternalKey", "bin").addTextNode(mainContactExternalKey);
		CompanyDetails.addChildElement("WholesalerCompanyExternalKeys", "bin")
				.addTextNode(wholesalerCompanyExternalKeys);
		SOAPElement CompanyPostalAddress = CompanyDetails.addChildElement("CompanyPostalAddress", "bin");
		CompanyPostalAddress.addChildElement("Preferred", "bin").addTextNode(preferred);
		CompanyPostalAddress.addChildElement("Type", "bin").addTextNode(type);
		CompanyPostalAddress.addChildElement("Country", "bin").addTextNode(country);
		CompanyPostalAddress.addChildElement("City", "bin").addTextNode(city);
		CompanyPostalAddress.addChildElement("ZipCode", "bin").addTextNode(zipCode);
		CompanyPostalAddress.addChildElement("Address", "bin").addTextNode(address);

		message.saveChanges();

		printRequestXML(message);

		SOAPConnection soapConnection = soapConnectionFactory.createConnection();
		log(url);
		SOAPMessage soapResponse = soapConnection.call(message, url);
		soapConnection.close();

		return soapResponse;
	}

	public String printSoapResponse(SOAPMessage soapResponse) throws Exception {

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		Source sourceContent = soapResponse.getSOAPPart().getContent();

		System.out.println("\n----------SOAP Response-----------");
		// StreamResult result = new StreamResult(System.out);
		// transformer.transform(sourceContent, result);
		// System.out.println("\n");
		// System.out.println(result.toString());
		// System.out.println("\n");

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		soapResponse.writeTo(out);
		String strMsg = new String(out.toByteArray());
		System.out.print(strMsg);
		log("");

		return strMsg;
	}

	private void printRequestXML(SOAPMessage message) throws SOAPException, IOException, TransformerException {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		Source sourceContent = message.getSOAPPart().getContent();

		System.out.println("\n----------SOAP Request-----------");
		StreamResult result = new StreamResult(System.out);
		transformer.transform(sourceContent, result);
		// System.out.println(result.toString());
	}

	// this could only return the first corresponding content
	public String getTextContentByTagName(SOAPMessage soapResponse, String tag) throws Exception {

		String tagName1 = "";
		String tagName2 = "";
		String tagName3 = "";
		String tagName4 = "";
		String tagName5 = "";
		String tagName6 = "";
		String textContent = "";
		SOAPEnvelope envelope = soapResponse.getSOAPPart().getEnvelope();
		Iterator<?> iterator = envelope.getBody().getChildElements();
		SOAPElement se = null;
		Iterator<?> iterator2 = null;
		Iterator<?> iterator3 = null;
		Iterator<?> iterator4 = null;
		Iterator<?> iterator5 = null;
		Iterator<?> iterator6 = null;
		while (iterator.hasNext()) {
			se = (SOAPElement) iterator.next();
			tagName1 = se.getElementName().getLocalName();
			iterator2 = se.getChildElements();

			while (iterator2.hasNext()) {
				se = (SOAPElement) iterator2.next();
				tagName2 = se.getElementName().getLocalName();
				if (tagName2.equalsIgnoreCase(tag)) {
					log("text: " + se.getTextContent() + " found in tag: " + tagName2);
					return se.getTextContent();
				}
				iterator3 = se.getChildElements();
				while (iterator3.hasNext()) {
					try {
						se = (SOAPElement) iterator3.next();
						tagName3 = se.getElementName().getLocalName();
						if (tagName3.equalsIgnoreCase(tag)) {
							log("text: " + se.getTextContent() + " found in tag: " + tagName3);
							return se.getTextContent();
						}
					} catch (ClassCastException e) {
						break;
					}

					iterator4 = se.getChildElements();
					while (iterator4.hasNext()) {
						try {
							se = (SOAPElement) iterator4.next();
							tagName4 = se.getElementName().getLocalName();

							if (tagName4.equalsIgnoreCase(tag)) {
								log("text: " + se.getTextContent() + " found in tag: " + tagName4);
								return se.getTextContent();
							}
						} catch (ClassCastException e) {
							break;
						}
						iterator5 = se.getChildElements();
						while (iterator5.hasNext()) {
							try {
								se = (SOAPElement) iterator5.next();
								tagName5 = se.getElementName().getLocalName();

								if (tagName5.equalsIgnoreCase(tag)) {
									log("text: " + se.getTextContent() + " found in tag: " + tagName5);
									return se.getTextContent();
								}
							} catch (ClassCastException e) {
								break;
							}
						}
						iterator6 = se.getChildElements();
						while (iterator6.hasNext()) {
							try {
								se = (SOAPElement) iterator6.next();
								tagName6 = se.getElementName().getLocalName();

								if (tagName6.equalsIgnoreCase(tag)) {
									log("text: " + se.getTextContent() + " found in tag: " + tagName6);
									return se.getTextContent();
								}
							} catch (ClassCastException e) {
								break;
							}
						}
					}
				}

			}
		}
		return textContent;
	}
}
