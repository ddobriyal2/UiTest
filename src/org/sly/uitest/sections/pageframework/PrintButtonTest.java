package org.sly.uitest.sections.pageframework;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Test;
import org.openqa.selenium.By;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.admin.AdminEditPage;
import org.sly.uitest.settings.Settings;

/**
 * Test for Print Button
 * 
 * @see <a href=
 *      "https://docs.google.com/spreadsheets/d/1kVxuPMAAymt4xuazWz05XEcby5C95N-Nrmh5tD05vZQ/edit#gid=0">
 *      here</a>
 * @author Benny Leung
 * @date : 18 Aug, 2016
 * @company Prive Financial
 */
public class PrintButtonTest extends AbstractTest {

	@Test
	public void testPrintButtonNotExistBeforeLogin() throws MalformedURLException, IOException {

		webDriver.get(Settings.BASE_URL);

		waitForElementVisible(By.id("gwt-debug-UserLoginView-emailAddress"), 30);

		assertFalse(isElementVisible(By.xpath(".//a[@title='Print']")));
	}

	@Test
	public void testPrintButtonExistAfterLogin() throws InterruptedException {
		String field = "Advisor Company Module Permission";
		String module = "MODULE_PRINT_BUTTON";

		LoginPage main = new LoginPage(webDriver);
		AdminEditPage adminEdit = main.loginAs(Settings.SySAdmin_USERNAME, Settings.SysAdmin_PASSWORD)
				.goToAdminEditPage().editAdminThisField(field);

		adminEdit.jumpByKeyAndLoad("291").editModuleToggle(module, false, true).clickSubmitButton();

		checkLogout();
		handleAlert();

		LoginPage main1 = new LoginPage(webDriver);

		main.loginAs(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD);
		if (main.checkIfMaterial()) {

		} else {
			webDriver.get(Settings.BASE_URL + "?random=091506672&locale=en&viewMode=Material#invAccountList");
			handleAlert();
		}
		waitForElementVisible(By.xpath(".//a[@title='Print']"), 30);
		assertTrue(isElementVisible(By.xpath(".//a[@title='Print']")));

	}

}
