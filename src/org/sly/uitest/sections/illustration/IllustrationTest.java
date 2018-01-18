package org.sly.uitest.sections.illustration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.investments.InvestmentsPage;
import org.sly.uitest.pageobjects.planning.IllustrationPage;
import org.sly.uitest.settings.Settings;

import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;

/**
 * @author Lynne Huang
 * @date : 23 Sep, 2015
 * @company Prive Financial
 */
public class IllustrationTest extends AbstractTest {
	String username = Settings.NORTHSTAR_USERNAME;
	String password = Settings.NORTHSTAR_PASSWORD;
	String wholeUsername = Settings.NORTHSTAR_WHOLESALER_USERNAME;
	String wholePassword = Settings.NORTHSTAR_WHOLESALER_PASSWORD;
	String name = "Illsutraion" + this.getRandName();

	public void testGIValidation(String username, String password) throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		IllustrationPage illustration = main.loginAs(username, password).goToIllustrationPage().createNewIllustration();

		String name = "Illsutraion" + this.getRandName();
		String prospect = "Alice Lanaria";
		String product = "Global VIP Fully Liquid";
		String currency = "USD";
		String amount = "1000";
		String invalidAmount1 = "abcd";
		String invalidAmount2 = "-10000";
		String type = "Individual";
		String firstname = "first" + this.getRandName();
		String lastname = "last" + this.getRandName();

		/**********
		 * Test Case 1: No illustration name input; not allowed
		 **********/

		illustration.editIIName("").editIIRegardingAddNewProspect(type, firstname, lastname).editIIProduct(product)
				.editIIPurchasePayment(currency, amount);

		// NEXT button
		illustration.clickNextButton_onGI();
		assertTrue(pageContainsStr("Please review the highlighted fields."));
		clickOkButtonIfVisible();

		// next tab
		illustration.jumpToFROTag();
		assertTrue(webDriver
				.findElement(By.xpath("//div[@class='TabLayoutPanelTabHighlight' and .='General Information']"))
				.isDisplayed());

		log("Test Case 1: No illustration name input");

		/**********
		 * Test Case 2: No Regarding chosen from existing prospect; not allowed
		 **********/

		illustration.editIIName(name).editIIAddAdvisor(1, null).editIIRegardingRemoveExistingProspect()
				.editIIProduct(product).editIIPurchasePayment(currency, amount);

		// NEXT button
		illustration.clickNextButton_onGI();
		assertTrue(pageContainsStr("Please review the highlighted fields."));
		clickOkButtonIfVisible();

		// next tab
		illustration.jumpToFROTag();
		assertTrue(webDriver
				.findElement(By.xpath("//div[@class='TabLayoutPanelTabHighlight' and .='General Information']"))
				.isDisplayed());

		log("Test Case 2: No Regarding chosen from existing prospect");

		/********** Test Case 3: No Product chosen; not allowed **********/

		illustration.editIIName(name).editIIAddAdvisor(1, null).editIIRegardingAddNewProspect(type, firstname, lastname)
				.editIIProduct("");

		// NEXT button
		illustration.clickNextButton_onGI();
		assertTrue(pageContainsStr("Please review the highlighted fields."));
		clickOkButtonIfVisible();

		// next tab
		illustration.jumpToFROTag();
		assertTrue(webDriver
				.findElement(By.xpath("//div[@class='TabLayoutPanelTabHighlight' and .='General Information']"))
				.isDisplayed());

		log("Test Case 3: No Product chosen");

		/********** Test Case 4: No Payment input; not allowed **********/

		illustration.editIIName(name).editIIProduct(product).editIIPurchasePayment(currency, "");

		// NEXT button
		illustration.clickNextButton_onGI();
		assertTrue(pageContainsStr("Please review the highlighted fields."));
		clickOkButtonIfVisible();

		// next tab
		illustration.jumpToFROTag();
		assertTrue(webDriver
				.findElement(By.xpath("//div[@class='TabLayoutPanelTabHighlight' and .='General Information']"))
				.isDisplayed());

		log("Test Case 4: No Payment input");

		/********** Test Case 5: Payment = 'abcd'; not allowed **********/

		illustration.editIIName(name).editIIProduct(product).editIIPurchasePayment(currency, invalidAmount1);

		// NEXT button
		illustration.clickNextButton_onGI();
		assertTrue(pageContainsStr("Please review the highlighted fields."));
		clickOkButtonIfVisible();

		// next tab
		illustration.jumpToFROTag();
		assertTrue(webDriver
				.findElement(By.xpath("//div[@class='TabLayoutPanelTabHighlight' and .='General Information']"))
				.isDisplayed());

		/********** Test Case 6: Payment = '-10000'; not allowed **********/
		illustration.editIIPurchasePayment(currency, invalidAmount2);

		// NEXT button
		illustration.clickNextButton_onGI();
		assertTrue(pageContainsStr("Please review the highlighted fields."));
		clickOkButtonIfVisible();

		// next tab
		illustration.jumpToFROTag();
		assertTrue(webDriver
				.findElement(By.xpath("//div[@class='TabLayoutPanelTabHighlight' and .='General Information']"))
				.isDisplayed());

		log("Test Case 5: Payment = 'abcd' & '-10000'");

		/********** Test Case 7: valid input **********/
		product = "Global VIP 5 Year";
		illustration.editIIName(name).editIIProduct(product).editIIPurchasePayment(currency, amount);

		// NEXT button
		illustration.clickNextButton_onGI();

		assertTrue(
				webDriver.findElement(By.xpath("//div[@class='TabLayoutPanelTabHighlight' and .='Fixed Rate Options']"))
						.isDisplayed());

		// next tab
		illustration.jumpToGITag().jumpToFROTag();
		waitForElementVisible(By.xpath("//div[@class='TabLayoutPanelTabHighlight' and .='Fixed Rate Options']"), 10);
		assertTrue(
				webDriver.findElement(By.xpath("//div[@class='TabLayoutPanelTabHighlight' and .='Fixed Rate Options']"))
						.isDisplayed());

	}

	public void testFROValidation(String username, String password) throws InterruptedException {

		IllustrationPage illustration = this.init();
		this.completeGIPage();

		/* Test "Fixed Rate Options" Validation */

		/**********
		 * Test Case 1: GA V = "130000"; Expected Result: allowed; Check
		 * allocation
		 **********/

		String investment1 = "Global VIP - 5 Year IRTO";
		String investment2 = "Global VIP - 3 Year IRTO";
		String investment3 = "Global VIP - 7 Year IRTO";
		String usd_tc1 = "130000";
		String allocation_tc2 = "10";
		String allocation_tc3 = "0";

		illustration.editFROOption(true);

		illustration.editFROUSD(investment1, usd_tc1);

		assertTrue((new WebDriverWait(webDriver, 30))
				.until(ExpectedConditions.presenceOfElementLocated(
						By.xpath("//td[.='" + investment1 + "']/following-sibling::td[2]/input")))
				.getAttribute("value").equals("13%"));

		assertTrue(this.getTextByLocator(By.xpath("//td[.='Total']/following-sibling::td[3]")).equals("130,000.00"));

		assertTrue(this.getTextByLocator(By.xpath("//td[.='Total']/following-sibling::td[2]")).equals("13.00%"));

		// NEXT button
		illustration.clickNextButton_onFRO();

		assertTrue(pageContainsStr(" Dollar Cost Averaging Program "));

		assertTrue(pageContainsStr("$870,000.00"));

		log("Test Case 1: GA III USD = '130000'");

		/**********
		 * Test Case 2: GA III allocation = "10"; Expected Result: allowed;
		 * Check USD
		 **********/
		this.checkFROValidTestCase(allocation_tc2, "23.00%", "230,000.00", "$770,000.00", investment2);

		log("Test Case 2: GA III allocation = '10'");

		/**********
		 * Test Case 3: all allocation = 0; Expected Result: allowed
		 **********/
		this.checkFROValidTestCase(allocation_tc3, "0.00%", "0.00", "$1,000,000.00", investment1, investment2);

		log("Test Case 3: The remaning USD is correct\n");

		/**********
		 * Test Case 4: GA III allocation = "abcd"; Expected Result: NOT allowed
		 **********/
		String invalidAllocation1_tc4 = "abcd";
		String invalidAllocation2_tc4 = "101";
		String invalidAllocation3_tc4 = "-3";
		String invalidUSD1_tc4 = "abcd";
		String invalidUSD2_tc4 = "-0.1";

		this.checkFROInvalidTestCaseByAllocation(invalidAllocation1_tc4, "0.00", "0.00%", "0.00", investment2);

		log("Test Case 4: GA III allocation = 'abcd'");

		/**********
		 * Test Case 5: GA III allocation = "101"; Expected Result: NOT allowed
		 **********/
		this.checkFROInvalidTestCaseByAllocation(invalidAllocation2_tc4, "0.00", "0.00%", "0.00", investment2);

		log("Test Case 5: GA III allocation = '101'");

		/**********
		 * Test Case 6: GA III allocation = "-3"; Expected Result: NOT allowed
		 **********/

		this.checkFROInvalidTestCaseByAllocation(invalidAllocation3_tc4, "0.00", "0.00%", "0.00", investment2);

		log("Test Case 6: GA III allocation = '-3'");

		/**********
		 * Test Case 7: GA III USD = "abcd"; Expected Result: NOT allowed
		 **********/

		this.checkFROInvalidTestCaseByValue(investment2, invalidUSD1_tc4);

		log("Test Case 7: GA III USD = 'abcd'");

		/**********
		 * Test Case 8: GA III USD = "-0.1"; Expected Result: NOT allowed
		 * 
		 **********/
		this.checkFROInvalidTestCaseByValue(investment2, invalidUSD2_tc4);

		log("Test Case 8: GA III USD = '-0.1'");

		/**********
		 * Test Case 9: Total > 100%"; Expected Result: NOT allowed
		 **********/

		this.checkFROInvalidTestCaseByAllocation("60", "600,000.00", "120.00%", "1,200,000.00", investment2,
				investment3);

		log("Test Case 9: Total > 100%");

		/**********
		 * Test Case 10: Valid
		 **********/
		this.checkFROValidTestCase("10", "20.00%", "200,000.00", "$800,000.00", investment2, investment3);

		log("Fixed Rate Options Page Validation: Done\n");

	}

	public void testDCAValidation(String username1, String password1) throws Exception {

		String equity = "Morgan Stanley Inv Funds Indian Equity";
		String opportunity = "Franklin US Opportunities";

		String invalidAllocation1 = "abcd";
		String invalidAllocation2 = "100.1";
		String invalidAllocation3 = "-10";
		String invalidUSD1 = "1100000";
		String invalidUSD2 = "-1000";
		String validAllocation = "50";
		String validUSD = "450,000.00";

		String invalidDCAAllocation1 = "abcd";
		String invalidDCAAllocation2 = "100.1";
		String invalidDCAAllocation3 = "-11.1";
		String invalidDCAAllocation4 = "0";
		String invalidDCAAllocation5 = "80.1";
		String invalidDCAAllocation6 = "79.9";
		String validDCAAllocation1 = "20";
		String validDCAAllocation2 = "80";

		IllustrationPage illustration = this.init();

		this.completeGIPage();
		this.completeFROPage();

		/*
		 * Dollar Cost Average
		 */

		log("Dollar Cost Average Validation: Start");

		// illustration.clickNextButton_onFRO();

		illustration.editDCAOption(true).addDCAAllocationInvestment().simpleSearchByName(equity)
				.selectInvestmentByName(equity).clickAddToPortfolioButton();

		assertTrue(pageContainsStr(" Dollar Cost Averaging Program "));

		illustration.editDCAInvestmentAllocation(equity, "100");

		/********** Test Case 1: Invalid Allocation = 'abcd' **********/
		this.checkDCAInvalidAllocation(invalidAllocation1);
		log("Test Case 1: Invalid Allocation = 'abcd'");

		/********** Test Case 2: Invalid Allocation = '100.1' **********/
		this.checkDCAInvalidAllocation(invalidAllocation2);
		log("Test Case 2: Invalid Allocation = '100.1'");

		/********** Test Case 3: Invalid Allocation = '-10' **********/
		this.checkDCAInvalidAllocation(invalidAllocation3);
		log("Test Case 3: Invalid Allocation = '-10'");

		/********** Test Case 4: Invalid USD > Total USD **********/
		this.checkDCAInvalidValue(invalidUSD1);
		log("Test Case 4: Invalid USD > Total USD");

		/********** Test Case 5: Invalid USD = '-1000' **********/
		this.checkDCAInvalidValue(invalidUSD2);
		log("Test Case 5: Invalid USD = '-1000'");

		/********** Test Case 6: Valid Allocation = '50'; check USD **********/

		illustration.editDCAAllocation(validAllocation);

		assertTrue(
				this.getAttributeStringByLocator(By.id("gwt-debug-IllustrationSettingsView-dcaAllocationAmountTextBox"),
						"originalvalue").equals("450000"));

		// NEXT button
		illustration.clickNextButton_onDCA();

		assertEquals(this.getTextByLocator(By.id("gwt-debug-IllustrationSettingsView-vioRemainingAmountLabel")),
				"$450,000.00 will be allocated directly to the Variable Investment Options within the");

		assertTrue(pageContainsStr(" Allocate Purchase Payment "));

		log("Test Case 6: Valid Allocation = '50'; check USD");

		/********** Test Case 7: Valid USD = '450,000.00'; check USD **********/

		illustration.jumpToDCATag().editDCAUSD(validUSD);
		assertTrue(this
				.getAttributeStringByLocator(By.id("gwt-debug-IllustrationSettingsView-dcaAllocationTextBox"), "value")
				.equals("50%"));

		// NEXT button
		illustration.clickNextButton_onDCA();
		waitForElementVisible(By.id("gwt-debug-IllustrationSettingsView-vioRemainingAmountLabel"),
				Settings.WAIT_SECONDS);
		assertEquals(getTextByLocator(By.id("gwt-debug-IllustrationSettingsView-vioRemainingAmountLabel")).toString(),
				"$450,000.00 will be allocated directly to the Variable Investment Options within the");

		assertTrue(pageContainsStr(" Allocate Purchase Payment "));

		log("Test Case 7: Valid USD = '450,000.00'; check USD");

		/********** Test Case 8: Invalid DCA Allocation = 'abcd' **********/
		this.checkDCAInvalidInvestmentAllocation(equity, invalidDCAAllocation1);
		log("Test Case 8: Invalid DCA Allocation = 'abcd'");

		/********** Test Case 9: Invalid DCA Allocation = '100.1 **********/
		this.checkDCAInvalidInvestmentAllocation(equity, invalidDCAAllocation2);
		log("Test Case 9: Invalid DCA Allocation = '100.1'");

		/********** Test Case 10: Invalid DCA Allocation = '-11.1' **********/
		this.checkDCAInvalidInvestmentAllocation(equity, invalidDCAAllocation3);
		log("Test Case 10: Invalid DCA Allocation = '-11.1'");

		/********** Test Case 11: Invalid DCA Allocation = '0' **********/
		this.checkDCAInvalidInvestmentAllocation(equity, invalidDCAAllocation4);
		log("Test Case 11: Invalid DCA Allocation = '0' ");

		/**********
		 * Test Case 12: Valid DCA Allocation 1 = '20', 2 = '80.1'
		 **********/

		illustration.addDCAAllocationInvestment().simpleSearchByName(opportunity).selectInvestmentByName(opportunity)
				.clickAddToPortfolioButton();

		illustration.editDCAInvestmentAllocation(equity, validDCAAllocation1);
		this.checkDCAInvalidInvestmentAllocation(opportunity, invalidDCAAllocation5);
		log("Test Case 12: Valid DCA Allocation 1 = '20', 2 = '80.1'");

		/**********
		 * Test Case 13: Valid DCA Allocation 1 = '20', 2 = '79.9'
		 **********/
		this.checkDCAInvalidInvestmentAllocation(opportunity, invalidDCAAllocation6);
		log("Test Case 13: Valid DCA Allocation 1 = '20', 2 = '79.9'");

		/**********
		 * Test Case 14: Valid DCA Allocation 1 = '20', 2 = '80'
		 **********/

		illustration.editDCAInvestmentAllocation(opportunity, validDCAAllocation2);

		// NEXT button
		illustration.clickNextButton_onDCA();

		assertEquals(this.getTextByLocator(By.id("gwt-debug-IllustrationSettingsView-vioRemainingAmountLabel")),
				"$450,000.00 will be allocated directly to the Variable Investment Options within the");

		// next tab
		illustration.jumpToDCATag().jumpToVIOTag();

		waitForElementVisible(By.id("gwt-debug-IllustrationSettingsView-vioRemainingAmountLabel"), 10);

		assertEquals(this.getTextByLocator(By.id("gwt-debug-IllustrationSettingsView-vioRemainingAmountLabel")),
				"$450,000.00 will be allocated directly to the Variable Investment Options within the");

		log("Test Case 14: Valid DCA Allocation 1 = '20', 2 = '80'");

	}

	public void testVIOValidation(String username, String password) throws Exception {

		String property = "Morgan Stanley Inv Funds Global Property";
		String opportunity = "Franklin US Opportunities";
		String portfolio = "AllianceBernstein Intl Health Care Portfolio";

		String invalidVIOAllocation1 = "100.1";
		String invalidVIOAllocation2 = "abcd";
		String invalidVIOAllocation3 = "-0.01";
		String invalidVIOAllocation4 = "15";
		String invalidVIOUSD1 = "-100000";
		String invalidVIOUSD2 = "abcd";
		String invalidVIOUSD3 = "225000";

		String validVIOAllocation1 = "15";
		String validVIOAllocation2 = "85";
		String invalidVIOAllocation5 = "85.1";
		String invalidVIOAllocation6 = "84.9";

		IllustrationPage illustration = this.init();

		this.completeGIPage();
		this.completeFROPage();
		this.completeDCAPage();

		illustration.addVIOAllocationInvestment().simpleSearchByName(property).selectInvestmentByName(property)
				.clickAddToPortfolioButton();

		/********** Test Case 1: USD = '-100000' **********/
		this.checkVIOInvalidValue(property, invalidVIOUSD1, "0%");
		log("Test Case 1: USD = '-100000'");

		/********** Test Case 2: USD = 'abcd' **********/
		this.checkVIOInvalidValue(property, invalidVIOUSD2, "0%");
		log("Test Case 2: USD = 'abcd'");

		/********** Test Case 3: Allocation = 'abcd' **********/
		this.checkVIOInvalidAllocation(property, invalidVIOAllocation1, "0.00");
		log("Test Case 3: Allocation = 'abcd'");

		/********** Test Case 4: Allocation = '100.1' **********/
		this.checkVIOInvalidAllocation(property, invalidVIOAllocation2, "0.00");
		log("Test Case 4:  Allocation = '100.1'");

		/********** Test Case 5: Allocation = '-0.01' **********/
		this.checkVIOInvalidAllocation(property, invalidVIOAllocation3, "0.00");
		log("Test Case 5:  Allocation = '-0.01'");

		/********** Test Case 6: Allocation = '15' **********/
		this.checkVIOInvalidAllocation(property, invalidVIOAllocation4, "67,500.00");
		log("Test Case 6: Allocation = '15'");

		/********** Test Case 7.1: Check delete button **********/

		illustration.deleteVIOAllocationInvestment(property);

		assertTrue(this
				.getTextByLocator(
						By.xpath(".//*[@id='gwt-debug-IllustrationSettingsView-vioAllocationTable']/tbody/tr[2]/td[2]"))
				.equals("0.00%"));

		assertTrue(this
				.getTextByLocator(
						By.xpath(".//*[@id='gwt-debug-IllustrationSettingsView-vioAllocationTable']/tbody/tr[2]/td[3]"))
				.equals("0.00"));

		/********** Test Case 7.2: USD = '225000' **********/
		illustration.addVIOAllocationInvestment().simpleSearchByName(property).selectInvestmentByName(property)
				.clickAddToPortfolioButton();
		this.checkVIOInvalidValue(property, invalidVIOUSD3, "50%");

		log("Test Case 7: Check delete button and add again ");

		String[] investments = { property, portfolio };
		String[] allocations = { validVIOAllocation1, invalidVIOAllocation5 };

		/********** Test Case 8: Allocation 1 = '15' 2 = 85.1' **********/

		illustration.addVIOAllocationInvestment().simpleSearchByName(portfolio).selectInvestmentByName(portfolio)
				.clickAddToPortfolioButton();

		this.checkVIOMultipleAllocatrion(investments, allocations, false);
		log("Test Case 8: Allocation 1 = '15' 2 = '85.1'");

		/********** Test Case 9: Allocation 1 = '15' 2 = 84.9' **********/

		allocations.clone();
		allocations[0] = validVIOAllocation1;
		allocations[1] = invalidVIOAllocation6;
		this.checkVIOMultipleAllocatrion(investments, allocations, false);
		log("Test Case 9: Allocation 1 = '15' 2 = '84.9'");

		/********** Test Case 10: Allocation 1 = '15' 2 = 85' **********/
		allocations.clone();
		allocations[0] = validVIOAllocation1;
		allocations[1] = validVIOAllocation2;
		this.checkVIOMultipleAllocatrion(investments, allocations, true);
		log("Test Case 10: Allocation 1 = '15' 2 = '85'");

	}

	public void testLogic(String username, String password) throws Exception {

		LoginPage main = new LoginPage(webDriver);

		String equity = "Morgan Stanley Inv Funds Indian Equity";
		String name = "Illsutraion" + this.getRandName();
		String product = "Global VIP Fully Liquid";
		String currency = "USD";
		String amount = "1000000";
		String type = "Individual";
		String firstname = "first" + this.getRandName();
		String lastname = "last" + this.getRandName();

		String investment = "Global VIP - 5 Year IRTO";

		String type1 = "Trailing 5 Years";
		String type2 = "Trailing 10 Years";
		String type3 = "Earliest Common";
		String type4 = "Since Inception";
		String type5 = "Custom";

		String increase = "Increase Current Rate by";
		String decrease = "Decrease Current Rate by";

		String value1 = "0.10%";
		String value2 = "0.15%";
		String value3 = "0.20%";
		String value4 = "-0.10%";
		String value5 = "-0.15%";
		String value6 = "-0.20%";

		String duration1 = "6 Months";
		String duration2 = "12 Months";

		IllustrationPage illustration = main.loginAs(username, password).goToIllustrationPage().createNewIllustration();

		illustration.editIIName(name).editIIAddAdvisor(1, null).editIIRegardingAddNewProspect(type, firstname, lastname)
				.editIIProduct(product).editIIPurchasePayment(currency, amount);

		/* Type */

		/* Test Case 1: Trailing 5 Years */

		illustration.editIIType(type1);

		assertTrue(pageContainsStr(" Trailing From "));

		assertTrue(pageContainsStr("Current Date"));

		assertTrue(pageContainsStr("Month End"));

		log("Test Case 1: Trailing 5 Years Checked");

		/* Test Case 2: Trailing 10 Years */
		illustration.editIIType(type2);

		assertTrue(pageContainsStr(" Trailing From "));

		assertTrue(pageContainsStr("Current Date"));

		assertTrue(pageContainsStr("Month End"));

		log("Test Case 2: Trailing 10 Years: Checked");

		/* Test Case 3: Earliest Common */

		illustration.editIIType(type3);

		// Dates and Trailing From are hidden
		assertFalse(
				isElementVisible(By.xpath(".//*[@id='gwt-debug-IllustrationSettingsView-panel0']/table/tbody/tr[12]")));

		log("Test Case 3: Earliest Common: Checked");

		/* Test Case 4: Since Inception */
		illustration.editIIType(type4);

		// Dates and Trailing From are hidden
		assertFalse(
				isElementVisible(By.xpath(".//*[@id='gwt-debug-IllustrationSettingsView-panel0']/table/tbody/tr[12]")));

		log("Test Case 4: Since Inception: Checked");

		/* Test Case 5: Custom */
		illustration.editIIType(type5);

		assertTrue(pageContainsStr(" Dates(dd-MMM-yyyy) "));

		// check the date picker
		clickElement(By.id("gwt-debug-IllustrationSettingsView-illustrationStartDate"));

		assertTrue(isElementDisplayed(By.id("gwt-debug-IllustrationSettingsView-datePicker")));

		clickElement(By.id("gwt-debug-IllustrationSettingsView-illustrationEndDate"));

		assertTrue(isElementDisplayed(By.id("gwt-debug-IllustrationSettingsView-datePicker")));

		log("Test Case 5: Custom: Checked");

		/* Fixed Rate Allocation */

		illustration.editIIType(type1).clickNextButton_onGI();

		// Fixed Rate Options: selecting 'yes' will show the panel below while
		// 'no' will hide

		illustration.editFROOption(true);

		assertTrue(pageContainsStr("Rate Assumptions "));
		wait(3);

		illustration.editFROOption(false);

		wait(10);

		assertFalse(isElementVisible(By.id("gwt-debug-IllustrationSettingsView-froContentPanel")));

		log("Test Case Check Hidden Page: Fixed Rate Options: Checked");

		/* Current Rate */
		illustration.editFROOption(true);

		BigDecimal[] currentRate = new BigDecimal[11];

		currentRate = getRate();

		/* Increase Current Rate by 0.10% */

		illustration.editFRORateAssumptions(increase).editFRORateValues(value1);

		BigDecimal[] increaseByPointTen = new BigDecimal[11];

		increaseByPointTen = getRate();

		compareTheRateAssumptions(currentRate, increaseByPointTen, value1);

		log("Increase Current Rate by 0.10%: Checked");

		/* Increase Current Rate by 0.15% */

		illustration.editFRORateAssumptions(increase).editFRORateValues(value2);

		BigDecimal[] increaseByPointFifteen = new BigDecimal[11];

		increaseByPointFifteen = getRate();

		compareTheRateAssumptions(currentRate, increaseByPointFifteen, value2);

		log("Increase Current Rate by 0.15%: Checked");

		/* Increase Current Rate by 0.20% */

		illustration.editFRORateAssumptions(increase).editFRORateValues(value3);

		BigDecimal[] increaseByPointTwenty = new BigDecimal[11];

		increaseByPointTwenty = getRate();

		compareTheRateAssumptions(currentRate, increaseByPointTwenty, value3);

		log("Increase Current Rate by 0.20%: Checked");

		/* Decrease Current Rate by 0.10% */

		illustration.editFRORateAssumptions(decrease).editFRORateValues(value4);

		BigDecimal[] decreaseByPointTen = new BigDecimal[11];

		decreaseByPointTen = getRate();

		compareTheRateAssumptions(currentRate, decreaseByPointTen, value4);

		log("Decrease Current Rate by 0.10%: Checked");

		/* Decrease Current Rate by 0.15% */

		illustration.editFRORateAssumptions(decrease).editFRORateValues(value5);

		BigDecimal[] decreaseByPointFifteen = new BigDecimal[11];

		decreaseByPointFifteen = getRate();

		compareTheRateAssumptions(currentRate, decreaseByPointFifteen, value5);

		log("Decrease Current Rate by 0.15%: Checked");

		/* Decrease Current Rate by 0.20% */
		illustration.editFRORateAssumptions(decrease).editFRORateValues(value6);

		BigDecimal[] decreaseByPointTwenty = new BigDecimal[11];

		decreaseByPointTwenty = getRate();

		compareTheRateAssumptions(currentRate, decreaseByPointTwenty, value6);

		log("Increase Current Rate by 0.20%: Checked");

		/* Dollar cost Averating */

		illustration.editFROAllocation(investment, "50").clickNextButton_onFRO();

		// DCA: selecting 'yes' will show the panel below while
		// 'no' will hide

		illustration.editDCAOption(true);

		assertTrue(pageContainsStr(" DCA Program "));

		illustration.editDCAOption(false);

		assertFalse(isElementVisible(By.id("gwt-debug-IllustrationSettingsView-dcaContentPanel")));

		log("Test Case Check Hidden Page: DCA: Checked");

		/* Interest Rate */

		illustration.editDCAOption(true);

		illustration.editDCADuration(duration2);

		assertTrue(this.getTextByLocator(By.id("gwt-debug-IllustrationSettingsView-dcaRate")).equals("0.50%"));

		log("Duration 12 Months: checked");

		illustration.editDCADuration(duration1);
		assertTrue(this.getTextByLocator(By.id("gwt-debug-IllustrationSettingsView-dcaRate")).equals("0.10%"));

		log("Duration 6 Months: checked");

		/* Asset Rebalancing */

		illustration.editDCAOption(true).addDCAAllocationInvestment().simpleSearchByName(equity)
				.selectInvestmentByName(equity).clickAddToPortfolioButton();

		illustration.editDCAInvestmentAllocation(equity, "100").clickNextButton_onDCA();

		// Asset Rebalancing: selecting 'yes' will show the panel below while
		// 'no' will hide

		illustration.editAROption(true);

		assertTrue(pageContainsStr("Frequency "));

		illustration.editAROption(false);

		assertFalse(isElementVisible(By.id("gwt-debug-IllustrationSettingsView-arpContentPanel")));

		log("Test Case Check Hidden Page: Asset Rebalancing: Checked");
	}

	public void testCalculation(String username, String password) throws Exception {

		LoginPage main = new LoginPage(webDriver);

		IllustrationPage illustration = main.loginAs(username, password).goToIllustrationPage().createNewIllustration();

		log("\nTest Case 1");

		double remaining = 1000000;

		String usd1 = "400000.0";
		String usd2 = "300000.0";
		String usd3 = "600000.0";

		String type = "Individual";
		String firstname = "first" + this.getRandName();
		String lastname = "last" + this.getRandName();

		String name = "0Illsutraion" + this.getRandName();
		String prospect = "Alice Lanaria";
		String advisor = "Peter Shu";
		String product1 = "Global VIP 2 Year";
		String product2 = "Global VIP 5 Year";
		String product3 = "Global VIP Fully Liquid";
		String currency = "USD";
		String amount = "1000000";
		String property = "Morgan Stanley Inv Funds Global Property";

		String investment1 = "Global VIP - 3 Year IRTO";
		String investment2 = "Global VIP - 7 Year IRTO";
		String equity = "Morgan Stanley Inv Funds Indian Equity";
		String opportunity = "Franklin US Opportunities";

		String allocation1 = "30.0";
		String allocation2 = "40.0";
		String dcaAllocation = "100.00";
		String vioAllocation = "100.00";
		String frequency1 = "Quarterly";
		String frequency2 = "Semi-Annually";
		String frequency3 = "Annually";

		/************ test case 1 ***************/

		/* General Information */

		illustration.editIIName(name).editIIAddAdvisor(1, null).editIIRegardingAddNewProspect(type, firstname, lastname)
				.editIIProduct(product1).editIIPurchasePayment(currency, amount);

		illustration.clickNextButton_onGI();
		scrollToTop();
		/* Fixed Rate Options */
		illustration.editFROOption(true).editFROAllocation(investment1, allocation1);

		remaining = remaining - Double.parseDouble(amount) * Double.parseDouble(allocation1) / 100;

		illustration.clickNextButton_onFRO();

		assertEquals(getTextByLocator(By.id("gwt-debug-IllustrationSettingsView-dcaRemainingAmountLabel")),
				"$700,000.00");

		log("DCA remaining payment is correct");
		/* Go to Varaible Investment Option page */
		illustration.clickNextButton_onDCA();

		assertTrue(getTextByLocator(By.id("gwt-debug-IllustrationSettingsView-vioRemainingAmountLabel"))
				.equals("$700,000.00" + " will be allocated directly to the Variable Investment Options within the"));

		illustration.addVIOAllocationInvestment().simpleSearchByName(property).selectInvestmentByName(property)
				.clickAddToPortfolioButton();

		illustration.editVIOAllocation(property, vioAllocation);

		illustration.clickNextButton_onVIO();

		illustration.editAROption(true).editARFrequency(frequency2)
				// .clickNextButton_onAR()
				.clickSaveAndCalculate();

		try {
			waitForElementVisible(By.id("gwt-debug-CustomDialog-message"), 60);
		} catch (TimeoutException e) {
			if (isElementVisible(By.id("gwt-debug-IllustrationSettingsView-saveBtn"))) {
				illustration.clickSaveAndCalculate();
			}

		}
		assertTrue(pageContainsStr("You have successfully saved the illustration."));

		clickOkButtonIfVisible();

		// wait(Settings.WAIT_SECONDS * 6);
		waitForElementVisible(By.id("gwt-debug-IllustrationListView-searchBtn"), 60);
		// illustration.goToExploreDEFAULTPage().goToIllustrationPage();

		/************ test case 2 ***************/

		/* Edit product will clear all values */
		illustration.editIllustrationByName(name).editIIProduct(product2);

		illustration.clickNextButton_onGI();

		assertTrue(isElementVisible(By.id("gwt-debug-IllustrationSettingsView-proposedFixedRateInvestmentsTabPanel")));

		log("Fixed Rate Options: CLEAR");

		illustration.clickNextButton_onFRO();

		assertTrue(isElementVisible(By.id("gwt-debug-IllustrationSettingsView-proposedDcaInvestmentsTabPanel")));

		String paymentCurr = "$" + convertToCurrency(Double.parseDouble(amount));

		assertTrue(getTextByLocator(By.id("gwt-debug-IllustrationSettingsView-dcaRemainingAmountLabel"))
				.equals(paymentCurr));

		log("Dollar Cost Averaging: CLEAR");

		illustration.clickNextButton_onDCA();

		assertTrue(getTextByLocator(By.id("gwt-debug-IllustrationSettingsView-vioRemainingAmountLabel"))
				.equals(paymentCurr + " will be allocated directly to the Variable Investment Options within the"));

		illustration.clickNextButton_onVIO();

		assertTrue(pageContainsStr("Please make sure the allocation is 100%"));

		clickOkButtonIfVisible();

		// jump back to General Information tag
		illustration.jumpToGITag();

		/* After EDIT: Fixed Rate Options */

		log("\nTest Case 2");

		illustration.clickNextButton_onGI();

		illustration.editFROOption(true).editFROUSD(investment2, usd1);

		Double Allocation = Double.parseDouble(usd1) / Double.parseDouble(amount) * 100;

		log(String.valueOf(Allocation));
		String allocation = String.valueOf(Allocation.intValue()) + "%";

		log(getInputByLocator(By.xpath("//td[.='" + investment2 + "']/following-sibling::td[2]/input")));
		log(allocation);
		assertEquals(getInputByLocator(By.xpath("//td[.='" + investment2 + "']/following-sibling::td[2]/input")),
				allocation);

		remaining = remaining - Double.parseDouble(usd1);

		/* Fixed Rate Options */

		illustration.clickNextButton_onFRO();

		assertTrue(getTextByLocator(By.id("gwt-debug-IllustrationSettingsView-dcaRemainingAmountLabel"))
				.equals("$600,000.00"));

		/* Dollar Cost Averating */

		((IllustrationPage) illustration.editDCAOption(true).editDCAAllocation(allocation1).addDCAAllocationInvestment()
				.simpleSearchByName(equity).selectInvestmentByName(equity).clickAddToPortfolioButton())
						.editDCAInvestmentAllocation(equity, dcaAllocation);

		illustration.clickNextButton_onDCA();

		illustration.addVIOAllocationInvestment().simpleSearchByName(property).selectInvestmentByName(property)
				.clickAddToPortfolioButton();

		illustration.editVIOAllocation(property, vioAllocation);

		illustration.clickNextButton_onVIO();

		assertTrue(pageContainsStr(" Asset Rebalancing Program "));

		illustration.jumpToVIOTag();

		assertTrue(getTextByLocator(By.id("gwt-debug-IllustrationSettingsView-vioRemainingAmountLabel"))
				.equals("$420,000.00" + " will be allocated directly to the Variable Investment Options within the"));

		illustration.clickNextButton_onVIO();

		log("VIO remaining payment is correct");

		/* Asset Rebalancing */

		illustration.editAROption(true).editARFrequency(frequency2)
				// .clickNextButton_onAR()
				.clickSaveAndCalculate();

		clickOkButtonIfVisible();

		waitForElementVisible(By.id("gwt-debug-IllustrationListView-searchBtn"), 60);

		/************ test case 3 ***************/

		/* Edit product will clear all values */
		illustration.editIllustrationByName(name).editIIProduct(product3);

		illustration.clickNextButton_onGI();

		assertTrue(isElementVisible(By.id("gwt-debug-IllustrationSettingsView-proposedFixedRateInvestmentsTabPanel")));

		log("Fixed Rate Options: CLEAR");

		illustration.clickNextButton_onFRO();

		assertTrue(isElementVisible(By.id("gwt-debug-IllustrationSettingsView-proposedDcaInvestmentsTabPanel")));

		paymentCurr = "$" + convertToCurrency(Double.parseDouble(amount));

		assertEquals(getTextByLocator(By.id("gwt-debug-IllustrationSettingsView-dcaRemainingAmountLabel")),
				paymentCurr);

		log("Dollar Cost Averaging: CLEAR");

		illustration.clickNextButton_onDCA();

		assertEquals(getTextByLocator(By.id("gwt-debug-IllustrationSettingsView-vioRemainingAmountLabel")),
				paymentCurr + " will be allocated directly to the Variable Investment Options within the");

		illustration.clickNextButton_onVIO();

		assertTrue(pageContainsStr("Please make sure the allocation is 100%"));

		clickOkButtonIfVisible();

		// jump back to General Information tag
		illustration.jumpToGITag();

		/* After EDIT: Fixed Rate Options */
		log("\nTest Case 3");

		illustration.clickNextButton_onGI();

		illustration.editFROOption(true).editFROAllocation(investment1, allocation2);

		/* Fixed Rate Options */

		illustration.clickNextButton_onFRO();

		assertTrue(getTextByLocator(By.id("gwt-debug-IllustrationSettingsView-dcaRemainingAmountLabel"))
				.equals("$600,000.00"));

		log("DCA remaining payment is correct");

		((IllustrationPage) illustration.editDCAOption(true).editDCAUSD(usd2).addDCAAllocationInvestment()
				.simpleSearchByName(equity).selectInvestmentByName(equity).clickAddToPortfolioButton())
						.editDCAInvestmentAllocation(equity, dcaAllocation);

		illustration.clickNextButton_onDCA();

		assertTrue(getTextByLocator(By.id("gwt-debug-IllustrationSettingsView-vioRemainingAmountLabel"))
				.equals("$300,000.00" + " will be allocated directly to the Variable Investment Options within the"));

		log("VIO remaining payment is correct");

		/* Variable Investment Options */

		((IllustrationPage) illustration.addVIOAllocationInvestment().simpleSearchByName(opportunity)
				.selectInvestmentByName(opportunity).clickAddToPortfolioButton()).editVIOAllocation(opportunity,
						vioAllocation);

		illustration.clickNextButton_onVIO();

		/* Asset Rebalancing */

		illustration.editAROption(true).editARFrequency(frequency3)
				// .clickNextButton_onAR()
				.clickSaveAndCalculate();

		clickOkButtonIfVisible();

		/************ test case 4 ***************/

		/* Edit product will clear all values */
		illustration.editIllustrationByName(name).editIIProduct(product1);

		illustration.clickNextButton_onGI();

		assertTrue(isElementVisible(By.id("gwt-debug-IllustrationSettingsView-proposedFixedRateInvestmentsTabPanel")));

		log("Fixed Rate Options: CLEAR");

		illustration.clickNextButton_onFRO();

		assertTrue(isElementVisible(By.id("gwt-debug-IllustrationSettingsView-proposedDcaInvestmentsTabPanel")));

		paymentCurr = "$" + convertToCurrency(Double.parseDouble(amount));

		assertTrue(getTextByLocator(By.id("gwt-debug-IllustrationSettingsView-dcaRemainingAmountLabel"))
				.equals(paymentCurr));

		log("Dollar Cost Averaging: CLEAR");

		illustration.clickNextButton_onDCA();

		assertTrue(getTextByLocator(By.id("gwt-debug-IllustrationSettingsView-vioRemainingAmountLabel"))
				.equals(paymentCurr + " will be allocated directly to the Variable Investment Options within the"));

		illustration.clickNextButton_onVIO();

		assertTrue(pageContainsStr("Please make sure the allocation is 100%"));

		clickOkButtonIfVisible();

		// jump back to General Information tag
		illustration.jumpToGITag();

		/* After EDIT: Fixed Rate Options */

		log("\nTest Case 4");

		illustration.clickNextButton_onGI();

		wait(3);

		illustration.editFROOption(false);

		/* Fixed Rate Options */

		illustration.clickNextButton_onFRO();
		waitForElementVisible(By.id("gwt-debug-IllustrationSettingsView-dcaRemainingAmountLabel"), 10);
		assertTrue(getTextByLocator(By.id("gwt-debug-IllustrationSettingsView-dcaRemainingAmountLabel"))
				.equals("$1,000,000.00"));

		/* Dollar Cost Averaging */
		((IllustrationPage) illustration.editDCAOption(true).editDCAAllocation(allocation1).addDCAAllocationInvestment()
				.simpleSearchByName(equity).selectInvestmentByName(equity).clickAddToPortfolioButton())
						.editDCAInvestmentAllocation(equity, dcaAllocation);

		illustration.clickNextButton_onDCA();

		((IllustrationPage) illustration.addVIOAllocationInvestment().simpleSearchByName(opportunity)
				.selectInvestmentByName(opportunity).clickAddToPortfolioButton()).editVIOAllocation(opportunity,
						vioAllocation);

		illustration.clickNextButton_onVIO();

		assertTrue(pageContainsStr(" Asset Rebalancing Program "));

		illustration.jumpToVIOTag();

		assertTrue(getTextByLocator(By.id("gwt-debug-IllustrationSettingsView-vioRemainingAmountLabel"))
				.equals("$700,000.00" + " will be allocated directly to the Variable Investment Options within the"));

		/* Asset Rebalancing */
		illustration.clickNextButton_onVIO();

		illustration.editAROption(true).editARFrequency(frequency3)
				// .clickNextButton_onAR()
				.clickSaveAndCalculate();

		clickOkButtonIfVisible();

		log("VIO remaining payment is correct");

		/************ test case 5 ***************/

		/* General Information */

		illustration.editIllustrationByName(name).editIIProduct(product2);

		log("\nTest Case 5");

		illustration.clickNextButton_onGI();

		/* Fixed Rate Options */

		illustration.editFROOption(false);

		illustration.clickNextButton_onFRO();

		assertTrue(getTextByLocator(By.id("gwt-debug-IllustrationSettingsView-dcaRemainingAmountLabel"))
				.equals("$1,000,000.00"));

		/* Dollar Cost Averaging */
		((IllustrationPage) illustration.editDCAOption(true).editDCAAllocation(allocation2).addDCAAllocationInvestment()
				.simpleSearchByName(opportunity).selectInvestmentByName(opportunity).clickAddToPortfolioButton())
						.editDCAInvestmentAllocation(opportunity, dcaAllocation);

		illustration.clickNextButton_onDCA();

		assertTrue(getTextByLocator(By.id("gwt-debug-IllustrationSettingsView-vioRemainingAmountLabel"))
				.equals("$600,000.00" + " will be allocated directly to the Variable Investment Options within the"));

		/* Variable Investment Options */

		((IllustrationPage) illustration.addVIOAllocationInvestment().simpleSearchByName(equity)
				.selectInvestmentByName(equity).clickAddToPortfolioButton()).editVIOUSD(equity, usd3);

		illustration.clickNextButton_onVIO();

		/* Asset Rebalancing */

		illustration.editAROption(true).editARFrequency(frequency1)
				// .clickNextButton_onAR()
				.clickSaveAndCalculate();

		clickOkButtonIfVisible();

		/************ test case 6 ***************/

		/* General Information */

		illustration.editIllustrationByName(name).editIIProduct(product1);

		/* After EDIT: Fixed Rate Options */
		log("\nTest Case 6");

		illustration.clickNextButton_onGI();

		illustration.editFROOption(false);

		/* Fixed Rate Options */

		illustration.clickNextButton_onFRO();

		assertTrue(getTextByLocator(By.id("gwt-debug-IllustrationSettingsView-dcaRemainingAmountLabel"))
				.equals("$1,000,000.00"));

		/* Dollar Cost Averaging */

		illustration.editDCAOption(false);

		illustration.clickNextButton_onDCA();

		assertTrue(getTextByLocator(By.id("gwt-debug-IllustrationSettingsView-vioRemainingAmountLabel"))
				.equals("$1,000,000.00" + " will be allocated directly to the Variable Investment Options within the"));

		/* Variable Investment Options */

		((IllustrationPage) illustration.addVIOAllocationInvestment().simpleSearchByName(equity)
				.selectInvestmentByName(equity).clickAddToPortfolioButton()).editVIOAllocation(equity, vioAllocation);

		illustration.clickNextButton_onVIO();

		/* Asset Rebalancing */

		illustration.editAROption(true).editARFrequency(frequency3)
				// .clickNextButton_onAR()
				.clickSaveAndCalculate();

		clickOkButtonIfVisible();

		/********** Delete the illustration *******************/

		illustration.deleteIllustrationByName(name);

		log("Test Case: DONE\n");

	}

	public void testIllustrationGenerationAndDownload(String username, String password) throws Exception {

		IllustrationPage illustration = this.init();
		this.completeGIPage();
		this.completeFROPage();
		this.completeDCAPage();
		this.completeVIOPage();

		illustration.editAROption(false).clickSaveAndCalculate();

		try {
			waitForElementVisible(By.id("gwt-debug-CustomDialog-message"), 60);
		} catch (TimeoutException e) {
			if (isElementVisible(By.id("gwt-debug-IllustrationSettingsView-saveBtn"))) {
				illustration.clickSaveAndCalculate();
			}

		}
		// assertTrue(pageContainsStr("You have successfully saved the
		// illustration."));

		clickOkButtonIfVisible();

		// wait(Settings.WAIT_SECONDS * 6);
		waitForElementVisible(By.id("gwt-debug-IllustrationListView-searchBtn"), 60);
		illustration.simpleSearch(name);
		assertTrue(pageContainsStr(name));
		illustration.waitAndDownloadIllustration(name);

	}

	@Test
	public void testDCAValidationAdmin() throws Exception {
		this.testDCAValidation(username, password);
	}

	@Test
	public void testCalculationAdmin() throws Exception {
		this.testCalculation(username, password);
	}

	@Test
	public void testFROValidationAdmin() throws InterruptedException {
		this.testFROValidation(username, password);
	}

	@Test
	public void testGIValidationAdmin() throws InterruptedException {
		this.testGIValidation(username, password);
	}

	@Test
	public void testLogicAdmin() throws Exception {
		this.testLogic(username, password);
	}

	@Test
	public void testVIOValidationAdmin() throws Exception {
		this.testVIOValidation(username, password);
	}

	@Test
	public void testIllustrationGenerationAndDownloadAdmin() throws Exception {
		this.testIllustrationGenerationAndDownload(username, password);
	}

	@Ignore
	public void testDCAValidationWholesaler() throws Exception {
		this.testDCAValidation(wholeUsername, wholePassword);
	}

	@Ignore
	public void testCalculationWholesaler() throws Exception {
		this.testCalculation(wholeUsername, wholePassword);
	}

	@Ignore
	public void testFROValidationWholesaler() throws InterruptedException {
		this.testFROValidation(wholeUsername, wholePassword);
	}

	@Ignore
	public void testGIValidationWholesaler() throws InterruptedException {
		this.testGIValidation(wholeUsername, wholePassword);
	}

	@Ignore
	public void testLogicWholesaler() throws Exception {
		this.testLogic(wholeUsername, wholePassword);
	}

	@Ignore
	public void testVIOValidationWholesaler() throws Exception {
		this.testVIOValidation(wholeUsername, wholePassword);
	}

	@Test
	public void testIllustrationGenerationAndDownloadWholesaler() throws Exception {
		this.testIllustrationGenerationAndDownload(wholeUsername, wholePassword);
	}

	@Ignore
	public void testSameReports() throws InvalidPasswordException, IOException {
		// File file = new File("/home/bleung/Downloads/47231.optimized.pdf");
		// File file2 = new File("/home/bleung/Downloads/47231.original.pdf");
		//
		// PDDocument document1 = PDDocument.load(file);
		// PDDocument document2 = PDDocument.load(file2);

		// PDFTextStripper pdfStripper = new PDFTextStripper();
		//
		// String text1 = pdfStripper.getText(document1);
		// String text2 = pdfStripper.getText(document2);

		List<String> original = this
				.fileToLines("/home/bleung/Downloads/HKG_P360_20170309_629776_Sch_RegTest_1 (1).pdf");
		List<String> revised = this
				.fileToLines("/home/bleung/Downloads/HKG_P360_20170309_629777_Sch_RegTestOpti_1 (1).pdf");
		Patch patch = DiffUtils.diff(original, revised);

		// PDFRenderer renderer = new PDFRenderer(document1);
		// BufferedImage image = renderer.renderImage(0);
		for (Delta delta : patch.getDeltas()) {
			System.out.println(delta);
		}

	}

	public List<String> fileToLines(String filename) {
		List<String> lines = new ArrayList<String>();
		String line = "";
		File file = new File(filename);

		try {

			PDDocument document1 = PDDocument.load(file);
			PDFTextStripper pdfStripper = new PDFTextStripper();

			String text1 = pdfStripper.getText(document1);
			lines = new ArrayList<String>(Arrays.asList(text1.split(" ")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}

	/*
	 * Get the rate from the Fixed Rate - Allocation Table
	 */
	public BigDecimal[] getRate() {

		String[] rate = new String[11];
		BigDecimal[] rateNum = new BigDecimal[11];

		int size = getSizeOfElements(By.xpath(".//*[@class='inlineLabel']"));
		for (int i = 0; i < size; i++) {

			rate[i] = this.getTextByLocator(
					By.xpath(".//*[@id='gwt-debug-IllustrationSettingsView-froAllocationTable']/tbody/tr[" + (i + 2)
							+ "]/td[2]"));
			log(String.valueOf(new BigDecimal(rate[i].trim().replace("%", "")).divide(BigDecimal.valueOf(100))
					.setScale(4, RoundingMode.CEILING)));
			rateNum[i] = new BigDecimal(rate[i].trim().replace("%", "")).divide(BigDecimal.valueOf(100)).setScale(4,
					RoundingMode.CEILING);

			// log(rateNum[i] + "\n");

		}
		return rateNum;
	}

	/*
	 * Verify if the new rate is correct based on the current rates
	 */
	public void compareTheRateAssumptions(BigDecimal[] currentRate, BigDecimal[] newRate, String difference) {

		BigDecimal percentage = new BigDecimal(difference.trim().replace("%", "")).divide(BigDecimal.valueOf(100));
		int size = getSizeOfElements(By.xpath(".//*[@class='inlineLabel']"));
		for (int i = 0; i < size; i++) {
			// log(newRate[i] + " " + currentRate[i] + " " + percentage + " "
			// + currentRate[i].add(percentage));
			log("new rate: " + String.valueOf(newRate[i]));
			log("current rate: " + String.valueOf(currentRate[i]));
			log("percentage: " + String.valueOf(percentage));
			assertTrue(newRate[i].equals(currentRate[i].add(percentage)));
		}

	}

	public static String convertToCurrency(Double price) {

		DecimalFormat formatter;

		formatter = new DecimalFormat("###,###,##0.00");

		return formatter.format(price);
	}

	public IllustrationPage init() throws InterruptedException {
		LoginPage main = new LoginPage(webDriver);
		IllustrationPage illustration = main.loginAs(username, password).goToIllustrationPage().createNewIllustration();
		return illustration;
	}

	public void completeGIPage() throws InterruptedException {

		String product = "Global VIP Fully Liquid";
		String currency = "USD";
		String amount = "1000000";
		String type = "Individual";
		String firstname = "first" + this.getRandName();
		String lastname = "last" + this.getRandName();
		IllustrationPage illustration = new IllustrationPage(webDriver);
		illustration.editIIName(name).editIIAddAdvisor(1, null).editIIRegardingAddNewProspect(type, firstname, lastname)
				.editIIProduct(product).editIIPurchasePayment(currency, amount).clickNextButton_onGI().jumpToFROTag();
	}

	public void completeFROPage() throws InterruptedException {
		String investment1 = "Global VIP - 3 Year IRTO";
		IllustrationPage illustration = new IllustrationPage(webDriver);
		illustration.editFROOption(true).editFROAllocation(investment1, "10").clickNextButton_onFRO();
	}

	public void completeDCAPage() throws InterruptedException, Exception {
		String validAllocation = "50";
		String validDCAAllocation1 = "20";
		String validDCAAllocation2 = "80";
		String equity = "Morgan Stanley Inv Funds Indian Equity";
		String opportunity = "Franklin US Opportunities";

		IllustrationPage illustration = new IllustrationPage(webDriver);

		((IllustrationPage) illustration.editDCAOption(true).editDCAAllocation(validAllocation)
				.addDCAAllocationInvestment().simpleSearchByName(equity).selectInvestmentByName(equity)
				.simpleSearchByName(opportunity).selectInvestmentByName(opportunity).clickAddToPortfolioButton())
						.editDCAInvestmentAllocation(equity, validDCAAllocation1)
						.editDCAInvestmentAllocation(opportunity, validDCAAllocation2).clickNextButton_onDCA();
	}

	public void completeVIOPage() throws Exception {
		String property = "Morgan Stanley Inv Funds Global Property";
		String portfolio = "AllianceBernstein Intl Health Care Portfolio";
		String[] investments = { property, portfolio };
		String allocation = "50";

		IllustrationPage illustration = new IllustrationPage(webDriver);
		InvestmentsPage invest;
		invest = illustration.addVIOAllocationInvestment();

		for (String investment : investments) {
			invest.simpleSearchByName(investment).selectInvestmentByName(investment).clickClearSearchIcon();
		}
		invest.clickAddToPortfolioButton();

		for (String investment : investments) {
			illustration.editVIOAllocation(investment, allocation);
		}
		illustration.clickNextButton_onVIO();
	}

	public void checkFROValidTestCase(String allocation, String totalPercentToCheck, String totalValueToCheck,
			String dcaValueToCheck, String... investments) throws InterruptedException {
		IllustrationPage illustration = new IllustrationPage(webDriver);

		illustration.jumpToFROTag();

		for (String investment : investments) {
			illustration.editFROAllocation(investment, allocation);
		}

		assertTrue(this.getTextByLocator(By.xpath("//td[.='Total']/following-sibling::td[2]"))
				.equals(totalPercentToCheck));

		assertTrue(
				this.getTextByLocator(By.xpath("//td[.='Total']/following-sibling::td[3]")).equals(totalValueToCheck));

		// NEXT button
		illustration.clickNextButton_onFRO();
		// assertTrue(pageContainsStr(" Dollar Cost Averaging Program "));

		assertTrue(pageContainsStr(dcaValueToCheck));

		assertTrue(this.getTextByLocator(By.id("gwt-debug-IllustrationSettingsView-dcaRemainingAmountLabel"))
				.equals(dcaValueToCheck));
		// next tab
		illustration.jumpToFROTag().jumpToDCATag();
		assertTrue(pageContainsStr(" Dollar Cost Averaging Program "));

		illustration.jumpToFROTag();
	}

	public void checkFROInvalidTestCaseByAllocation(String allocation, String investmentValueToCheck,
			String totalPercentToCheck, String totalValueToCheck, String... investments) throws InterruptedException {

		IllustrationPage illustration = new IllustrationPage(webDriver);
		illustration.jumpToFROTag();

		for (String investment : investments) {
			illustration.editFROAllocation(investment, allocation);
			assertTrue(this
					.getAttributeStringByLocator(
							By.xpath("//td[.='" + investment + "']/following-sibling::td[3]/input"), "value")
					.equals(investmentValueToCheck));

		}

		assertTrue(this.getTextByLocator(By.xpath("//td[.='Total']/following-sibling::td[2]"))
				.equals(totalPercentToCheck));

		assertTrue(
				this.getTextByLocator(By.xpath("//td[.='Total']/following-sibling::td[3]")).equals(totalValueToCheck));

		if (isElementVisible(By.id("gwt-debug-CustomDialog-mainPanel"))) {
			// NEXT button
			illustration.clickNextButton_onFRO();
			assertTrue(pageContainsStr("Allocation should be between 0 and 100%"));
			clickOkButtonIfVisible();

			// next tab
			illustration.jumpToDCATag();
			assertTrue(pageContainsStr("Allocation should be between 0 and 100%"));
			clickOkButtonIfVisible();

		}

		illustration.jumpToFROTag();
	}

	public void checkFROInvalidTestCaseByValue(String investment, String value) throws InterruptedException {
		IllustrationPage illustration = new IllustrationPage(webDriver);
		illustration.jumpToFROTag().editFROUSD(investment, value);

		// NEXT button
		illustration.clickNextButton_onFRO();

		assertTrue(pageContainsStr(" Dollar Cost Averaging Program "));

		assertTrue(this.getTextByLocator(By.id("gwt-debug-IllustrationSettingsView-dcaRemainingAmountLabel"))
				.equals("$1,000,000.00"));

		// next tab
		illustration.jumpToFROTag().jumpToDCATag();

		assertTrue(pageContainsStr(" Dollar Cost Averaging Program "));

		assertTrue(this.getTextByLocator(By.id("gwt-debug-IllustrationSettingsView-dcaRemainingAmountLabel"))
				.equals("$1,000,000.00"));

		illustration.jumpToFROTag();
	}

	public void checkDCAInvalidAllocation(String allocation) throws InterruptedException {
		IllustrationPage illustration = new IllustrationPage(webDriver);
		illustration.editDCAAllocation(allocation);

		// NEXT button
		illustration.clickNextButton_onDCA();
		if (!isElementVisible(By.xpath(".//*[@class='gwt-DialogBox']"))) {
			illustration.clickNextButton_onDCA();
		}
		assertTrue(pageContainsStr("Please make sure the allocation is 100%"));
		clickOkButtonIfVisible();
		// next tab
		illustration.jumpToVIOTag();
		if (!isElementVisible(By.xpath(".//*[@class='gwt-DialogBox']"))) {
			illustration.jumpToVIOTag();
		}
		assertTrue(pageContainsStr("Please make sure the allocation is 100%"));
		clickOkButtonIfVisible();
	}

	public void checkDCAInvalidValue(String value) throws InterruptedException {
		IllustrationPage illustration = new IllustrationPage(webDriver);
		illustration.editDCAUSD(value);

		// NEXT button
		illustration.clickNextButton_onDCA();
		if (!isElementVisible(By.xpath(".//*[@class='gwt-DialogBox']"))) {
			illustration.clickNextButton_onDCA();
		}
		assertTrue(pageContainsStr("Please make sure the allocation is 100%"));
		clickOkButtonIfVisible();

		// next tab
		illustration.jumpToVIOTag();
		if (!isElementVisible(By.xpath(".//*[@class='gwt-DialogBox']"))) {
			illustration.jumpToVIOTag();
		}
		assertTrue(pageContainsStr("Please make sure the allocation is 100%"));
		clickOkButtonIfVisible();

	}

	public void checkDCAInvalidInvestmentAllocation(String equity, String allocation) throws InterruptedException {
		IllustrationPage illustration = new IllustrationPage(webDriver);
		illustration.jumpToDCATag();

		illustration.editDCAInvestmentAllocation(equity, allocation);

		// NEXT button
		illustration.clickNextButton_onDCA();
		if (!isElementVisible(By.xpath(".//*[@class='gwt-DialogBox']"))) {
			illustration.clickNextButton_onDCA();
		}
		assertTrue(pageContainsStr("Please make sure the allocation is 100%"));
		clickOkButtonIfVisible();

		// next tab
		illustration.jumpToVIOTag();
		if (!isElementVisible(By.xpath(".//*[@class='gwt-DialogBox']"))) {
			illustration.jumpToVIOTag();
		}
		assertTrue(pageContainsStr("Please make sure the allocation is 100%"));
		clickOkButtonIfVisible();

	}

	public void checkVIOInvalidValue(String property, String value, String percentToCheck) throws InterruptedException {
		IllustrationPage illustration = new IllustrationPage(webDriver);
		illustration.editVIOUSD(property, value);

		assertTrue(
				this.getAttributeStringByLocator(
						By.xpath(
								".//*[@id='gwt-debug-IllustrationSettingsView-vioAllocationTable']/tbody/tr[2]/td[2]/input"),
						"value").equals(percentToCheck));

		// NEXT button
		illustration.clickNextButton_onVIO();
		if (!isElementVisible(By.xpath(".//*[@class='gwt-DialogBox']"))) {
			illustration.clickNextButton_onVIO();
		}
		assertTrue(pageContainsStr("Please make sure the allocation is 100%"));
		clickOkButtonIfVisible();

		// next tab
		illustration.jumpToARTag();
		if (!isElementVisible(By.xpath(".//*[@class='gwt-DialogBox']"))) {
			illustration.jumpToARTag();
		}
		assertTrue(pageContainsStr("Please make sure the allocation is 100%"));
		clickOkButtonIfVisible();
	}

	public void checkVIOInvalidAllocation(String property, String allocation, String valueToCheck)
			throws InterruptedException {
		IllustrationPage illustration = new IllustrationPage(webDriver);
		illustration.editVIOAllocation(property, allocation);

		assertTrue(this.getAttributeStringByLocator(
				(By.xpath("//td[.='" + property + "']/following-sibling::td[2]/input")), "value").equals(valueToCheck));

		// NEXT button
		illustration.clickNextButton_onVIO();
		if (!isElementVisible(By.xpath(".//*[@class='gwt-DialogBox']"))) {
			illustration.clickNextButton_onVIO();
		}
		assertTrue(pageContainsStr("Please make sure the allocation is 100%"));
		clickOkButtonIfVisible();

		// next tab
		illustration.jumpToARTag();
		if (!isElementVisible(By.xpath(".//*[@class='gwt-DialogBox']"))) {
			illustration.jumpToARTag();
		}
		assertTrue(pageContainsStr("Please make sure the allocation is 100%"));
		clickOkButtonIfVisible();

	}

	public void checkVIOMultipleAllocatrion(String[] investments, String[] allocations, boolean valid)
			throws InterruptedException {

		IllustrationPage illustration = new IllustrationPage(webDriver);
		log(String.valueOf(investments.length));
		for (int i = 0; i < investments.length; i++) {
			illustration.editVIOAllocation(investments[i], allocations[i]);
		}

		if (valid) {
			// NEXT button
			illustration.clickNextButton_onVIO();
			assertTrue(pageContainsStr("Asset Rebalancing Program"));

			// next tab
			illustration.jumpToVIOTag().jumpToARTag();
			assertTrue(pageContainsStr("Asset Rebalancing Program"));

		} else {

			// NEXT button
			illustration.clickNextButton_onVIO();
			if (!isElementVisible(By.xpath(".//*[@class='gwt-DialogBox']"))) {
				illustration.clickNextButton_onVIO();
			}
			assertTrue(pageContainsStr("Please make sure the allocation is 100%"));
			clickOkButtonIfVisible();

			// next tab
			illustration.jumpToARTag();

			if (!isElementVisible(By.xpath(".//*[@class='gwt-DialogBox']"))) {
				illustration.jumpToARTag();
			}
			assertTrue(pageContainsStr("Please make sure the allocation is 100%"));
			clickOkButtonIfVisible();
		}
	}
}
