package org.sly.uitest.sections.structuredproduct;

import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.assetmanagement.StructuredProductPage;
import org.sly.uitest.settings.Settings;

/**
 * Test structured products.
 * 
 * @author Harry Chin
 * @date : Jul 25, 2014
 * @company Prive Financial
 */
public class StructuredProductTest extends AbstractTest {

	/**
	 * Navigate to the structured product overview page.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testOpenStructuredProductOverview() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToStructuredProductPage();

		assertTrue(pageContainsStr("My Structured Products"));

	}

	@Test
	public void testFailtoCreateNewStructuredProductWithoutName() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToStructuredProductPage()
				.clickNewButtonTocCreateStructuredProduct().clickSaveButton();

		WebElement elem = waitGet(By.id("gwt-debug-StructuredProductEditView-structuredProductName"));

		assertTrue(elem.getAttribute("class").contains("formTextBoxInvalid"));

	}

	@Test
	public void testCreateAndEditStructuredProduct() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		StructuredProductPage newProduct = main.loginAs(Settings.USERNAME, Settings.PASSWORD)
				.goToStructuredProductPage();

		String newName = null;

		// Create a new structured product
		newName = "TestProduct" + UUID.randomUUID().getMostSignificantBits();

		StructuredProductPage editProduct = newProduct.clickNewButtonTocCreateStructuredProduct()
				.editStructuredProductName(newName).clickSaveButton();

		assertTrue(pageContainsStr(newName));

		// Test Edit a given product
		String testDesc = "testDesc";
		String name2 = "TestProduct" + UUID.randomUUID().getMostSignificantBits();

		StructuredProductPage checkProduct = editProduct.editExistingStructuredProduct(newName)
				.editStructuredProductName(name2).editStructuredProductDescription(testDesc)
				.editStructuredProductVisibility("Visible to creator").editStructuredProductPayoff("Vanilla Call")
				.clickSaveButton();

		assertTrue(pageContainsStr(name2));

		StructuredProductPage checkPayoff = checkProduct.editExistingStructuredProduct(name2);

		assertTrue(compareInputValue(By.id("gwt-debug-StructuredProductEditView-structuredProductName"), name2));

		assertTrue("Visible to creator".equals(
				getSelectedTextFromDropDown(By.id("gwt-debug-StructuredProductEditView-structuredProductVisibility"))));

		assertTrue(pageContainsStr("Performance(t) minus"));

		assertTrue(
				compareInputValue(By.id("gwt-debug-StructuredProductEditView-structuredProductDescription"), testDesc));

		checkPayoff.deleteStructuredProductPayoff();

		assertTrue(!pageContainsStr("Performance(t) minus"));

		clickElement(By.id("gwt-debug-StructuredProductEditView-submitButton"));

	}

	@Test
	public void testCreateAndCancelStructuredProduct() throws Exception {

		String newName = "TestProduct " + UUID.randomUUID().getMostSignificantBits();

		LoginPage main = new LoginPage(webDriver);

		main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToStructuredProductPage()
				.clickNewButtonTocCreateStructuredProduct().editStructuredProductName(newName).clickCancelButton();

		assertTrue(!pageContainsStr(newName));
	}

}
