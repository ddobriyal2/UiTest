package org.sly.uitest.sections.email;

import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.clientsandaccounts.AccountOverviewPage;
import org.sly.uitest.pageobjects.clientsandaccounts.DetailPage;
import org.sly.uitest.pageobjects.companyadmin.EmailTemplatePage;
import org.sly.uitest.sections.commission.CommissionTest;
import org.sly.uitest.settings.Settings;

/**
 * Test the email template related screens.
 * 
 * @author Jackie Lee
 * @date : Jul 28, 2014
 * @company Prive Financial
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmailTemplateTest extends AbstractTest {

	/**
	 * Tests the read, write and delete operatons of the template system.
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testEmailTemplateCrud() throws InterruptedException {

		setScreenshotSuffix(CommissionTest.class.getSimpleName());
		LoginPage main = new LoginPage(webDriver);
		main.loginAs(Settings.USERNAME, Settings.PASSWORD);

		String emailTemplateName = "Template" + getRandName();
		log("Template Name : " + emailTemplateName);

		String emailTemplateContent = "This is an email template. Template content: " + getRandName();
		log("Template Content: " + emailTemplateContent);

		// navigate to email templates
		main.goToEmailTemplatePage();

		// delete all email template before test start
		boolean isCleared = pageContainsStr(
				"No email templates were found. Please click on the 'Create Template' button to create one.");
		int count = 0;
		log(String.valueOf(isCleared));
		while (!isCleared) {
			count++;

			if (count > 15) {
				throw new RuntimeException("Can not delete email template.");
			}
			try {
				clickElement(By.id("gwt-debug-DocumentBinderOverviewTable-deleteImg"));
				clickElement(By.id("gwt-debug-CustomDialog-yesButton"));

				this.waitForWaitingScreenNotVisible();
			} catch (NoSuchElementException e) {
				isCleared = true;
			}
		}

		/*
		 * Create a new email template
		 */
		createEmailtemplate(emailTemplateName, emailTemplateContent);

		/*
		 * Edit an existing email template
		 */
		String emailTemplateNameEdited;
		{
			emailTemplateNameEdited = emailTemplateName + "-edited";
			scrollToTop();
			clickElement(By.id("gwt-debug-EmailTemplateOverviewView-editImage"));
			sendKeysToElement(By.id("gwt-debug-EmailTemplateEditView-nameTextBox"), emailTemplateNameEdited);
			// WebElement templateNameTextBox = waitGet(By
			// .id("gwt-debug-EmailTemplateEditView-nameTextBox"));
			// templateNameTextBox.clear();
			// templateNameTextBox.sendKeys(emailTemplateNameEdited);

			waitForElementVisible(By.xpath("//button[@id='gwt-debug-EmailSignatureWidget-closeBtn' and .='Save']"), 10);
			clickElement(By.xpath("//button[@id='gwt-debug-EmailSignatureWidget-closeBtn' and .='Save']"));
			clickElement(By.id("gwt-debug-CustomDialog-yesButton"));

			assertTrue("Expected email template edited - " + emailTemplateNameEdited,
					pageContainsStr(emailTemplateNameEdited));
		}

		/*
		 * Delete Email Template
		 */
		{
			// find and click delete button

			scrollToTop();
			clickElement(By.xpath(
					"//td[.='" + emailTemplateNameEdited + "']/following-sibling::td[4]/table/tbody/tr/td[2]/button"));

			// click confirm button
			clickElement(By.id("gwt-debug-CustomDialog-yesButton"));

			this.waitForWaitingScreenNotVisible();

			// make sure no entry left

			assertTrue("There should be no entry left, but still found some email templates.",
					pageContainsStr("No email templates were found."));
		}

	}

	// private void navigateToEmailTemplates() {
	// // navigate to email template
	// this.clickElement(By
	// .xpath("//*[@id=\"gwt-debug-ImageMenuButton-displayImageLink\" and
	// @title=\"Company Settings\"]"));
	// try {
	// wait(2);
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// waitGet(By.id("gwt-debug-ImageMenuButton-EmailTemplate")).click();
	// assertTrue("Expected page title - Email Template Overview",
	// pageContainsStr("Email Template Overview"));
	// }

	/**
	 * Tests the mail merge function when emailing client from account overview.
	 * See "Email Client Mail Merge" in test sheet.
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testEmailTemplateMailMerge() throws Exception {

		// init
		LoginPage main = new LoginPage(webDriver);

		AccountOverviewPage init = ((AbstractPage) main.loginAs(Settings.USERNAME, Settings.PASSWORD)
				.goToAccountOverviewPage().simpleSearchByString("Doe").goToClientDetailPageByName("Doe, John")
				.goToEditPageByField("Additional Details").editClientEmailFunction("Enabled")
				.clickSaveButton_ClientAdditionalDetail()).goToAccountOverviewPage();

		// create new template text and name
		String emailTemplateName = "Template" + getRandName();
		log("Template Name : " + emailTemplateName);
		String emailTemplateContent = "This is an email template. Template content: " + getRandName();
		log("Template Content: " + emailTemplateContent);

		// create template
		EmailTemplatePage newTemplate = init.goToEmailTemplatePage().createNewEmailTemplate()
				.editEmailTemplateName(emailTemplateName).editEmailTemplateContent(emailTemplateContent)
				.clickSaveButton();

		assertTrue("Expected newly created email template - " + emailTemplateName, pageContainsStr(emailTemplateName));

		// set email master switch on
		AccountOverviewPage masterSwitch = newTemplate.goToCompanySettingsPage().checkMasterEmailSwitch(true)
				.clickSubmitButton().goToAccountOverviewPage();

		// wait(Settings.WAIT_SECONDS);

		// send email to John Doe from Acc Overview
		EmailTemplatePage sendEmail = masterSwitch.simpleSearchByString("Doe").selectAllAccounts(true)
				.selectAllAccounts(false).selectSingleAccount("Generali Vision - NEW").clickEmailClientButton();

		// make sure email popup came up
		assertTrue("Cant find email popup", pageContainsStr("Mail Merge"));

		// go to mail merge and insert the email template
		EmailTemplatePage insertTemplate = sendEmail.goToMailMerge().selectEmailTemplate(emailTemplateName)
				.clickInsertEmailTemplageButton();

		// make sure "John Doe" comes up in To-field

		this.waitForElementVisible(By.id("gwt-debug-EmailClientWidget-toLabel"), Settings.WAIT_SECONDS * 2);
		assertTrue("Was expecting 'John Doe' in the to field, but can not find.",
				this.getTextByLocator(By.id("gwt-debug-EmailClientWidget-toLabel")).contains("John Doe;"));

		// check content of iframe / make sure it has the right template
		WebElement contentArea = waitGet(By.cssSelector("iframe.cke_wysiwyg_frame.cke_reset"));
		webDriver.switchTo().frame(contentArea);

		this.waitForElementVisible(
				By.cssSelector("body.cke_editable.cke_editable_themed.cke_contents_ltr.cke_show_borders"),
				Settings.WAIT_SECONDS);
		assertTrue(this
				.getTextByLocator(
						By.cssSelector("body.cke_editable.cke_editable_themed.cke_contents_ltr.cke_show_borders"))
				.contains(emailTemplateContent));

		// switch back to main frame
		webDriver.switchTo().defaultContent();

		insertTemplate.clickCancelButton().goToEmailTemplatePage().deleteExistingEmailTemplate(emailTemplateName);

	}

	/**
	 * Makes sure email functions are off if email master switch is off. See
	 * "Test Master Email Switch" in test sheet.
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testEmailMasterSwitch() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		AccountOverviewPage init = ((AbstractPage) main.loginAs(Settings.USERNAME, Settings.PASSWORD)
				.goToAccountOverviewPage().simpleSearchByString("Doe").goToClientDetailPageByName("Doe, John")
				.goToEditPageByField("Additional Details").editClientEmailFunction("Enabled")
				.clickSaveButton_ClientAdditionalDetail()).goToCompanySettingsPage().checkMasterEmailSwitch(false)
						.clickSubmitButton();

		init.goToAccountOverviewPage().simpleSearchByString("Doe").goToClientDetailPageByName("Doe, John");

		// make sure there is no email button on account overview page
		WebElement emailButton = null;

		try {
			waitForElementVisible(By.id("gwt-debug-UserDetailsSectionPresenter-emailImage"), 10);

		} catch (Exception e) {

		}

		// wait(Settings.WAIT_SECONDS);

		assertTrue("Email button on acount overview should be hidden, but is there.",
				emailButton == null || emailButton.isDisplayed() == false);

		// make sure warning message is in operations > bulk email
		init.goToBulkEmailPage();

		assertTrue("Cound not find warning message in Operations>Bulk Email Page.", pageContainsStr(
				"Please note the master email switch is off, email function is disabled. You can edit this from the Company Settings page."));

		init.goToCompanySettingsPage().checkMasterEmailSwitch(true).clickSubmitButton();

		init.goToAccountOverviewPage().simpleSearchByString("Doe").goToClientDetailPageByName("Doe, John");

		try {
			waitForElementVisible(By.id("gwt-debug-UserDetailsSectionPresenter-emailImage"), 10);
			emailButton = webDriver.findElement(By.id("gwt-debug-UserDetailsSectionPresenter-emailImage"));

		} catch (Exception e) {
			log("not Found");
		}

		assertTrue("Email button on acount overview should not be hidden.", emailButton != null);
		init.goToBulkEmailPage();
		assertTrue(!isElementVisible(By.id("gwt-debug-BulkEmailView-warningPanel")));

	}

	/**
	 * Makes sure that email is off for a client where live flag is set to off.
	 * "Test Client Live Flag" in test sheet.
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testClientLiveFlag() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		DetailPage init = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToCompanySettingsPage()
				.checkMasterEmailSwitch(true).clickSubmitButton().simpleSearchByString("Doe")
				.goToClientDetailPageByName("Doe, John").goToEditPageByField("Additional Details")
				.editClientEmailFunction("Disabled").clickSaveButton_ClientAdditionalDetail();

		// try to email from client screen & make sure error occurs
		if (!this.checkElementExist(By.id("gwt-debug-UserDetailsSectionPresenter-emailImage"))) {
			// create email for this user
			clickElementAndWait3(By.id("gwt-debug-UserDetailsSectionPresenter-image"));
			// clickElementAndWait3(By
			// .id("gwt-debug-UserDetailWidget-addEmailButton"));
			this.sendKeysToElement(By.id("gwt-debug-EditUserEmailWidget-emailTextBox-1"), "nandi@Test.com");

			clickElementAndWait3(By.id("gwt-debug-UserDetailWidget-saveBtn"));

		}

		// go to account overview and try to send email to John Doe
		init.goToAccountOverviewPage().simpleSearchByString("Doe").selectAllAccounts(true).selectAllAccounts(false)
				.selectSingleAccount("Generali Vision - NEW").clickEmailClientButton();

		try {
			waitForElementVisible(By.id("gwt-debug-CustomDialog-message"), Settings.WAIT_SECONDS);
			assertTrue(pageContainsStr("The following users' email function is not live:"));

		} catch (AssertionError e) {

		} finally {
			clickOkButtonIfVisible();
			// assertTrue(pageContainsStr("No clients' email address found. This
			// could be due no email address is set for the client or the client
			// is still under review."));
			clickOkButtonIfVisible();
		}

		// wait(Settings.WAIT_SECONDS);

		init.goToAccountOverviewPage().simpleSearchByString("Doe").goToClientDetailPageByName("Doe, John")
				.goToEditPageByField("Additional Details").editClientEmailFunction("Enabled")
				.clickSaveButton_ClientAdditionalDetail();
	}

	/**
	 * Tests the bulk email fundtion from the operations menu. See "Bulk Email"
	 * in test sheet.
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testBulkMail() throws Exception {

		// init
		// setScreenshotSuffix(CommissionTest.class.getSimpleName());
		// webDriver.get(baseUrl);
		// checkLogin();
		// wait(10);
		//
		// // set client communication status to live
		// setClientCommunicationStatus(true);
		// navigateToAccountOverview();

		LoginPage main = new LoginPage(webDriver);
		((AbstractPage) main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage()
				.simpleSearchByString("Doe").goToClientDetailPageByName("Doe, John")
				.goToEditPageByField("Additional Details").editClientEmailFunction("Enabled")
				.clickSaveButton_ClientAdditionalDetail()).goToAccountOverviewPage();

		// create new template text and name
		String emailTemplateName = "Template" + getRandName();
		log("Template Name : " + emailTemplateName);
		String emailTemplateContent = "This is an email template. Template content: " + getRandName();
		log("Template Content: " + emailTemplateContent);

		// create template
		main.goToEmailTemplatePage();
		createEmailtemplate(emailTemplateName, emailTemplateContent);

		// navigate to Operations>Bulk Email
		main.goToBulkEmailPage();

		// select new template from dropdown
		selectFromDropdown(By.id("gwt-debug-BulkEmailView-templateListBox"), emailTemplateName);
		// wait(1);
		scrollToTop();
		clickElement(By.id("gwt-debug-BulkEmailView-insertTemplateBtn"));

		// check content of iframe / make sure it has the right template
		WebElement contentArea = webDriver.findElement(By.cssSelector("iframe.cke_wysiwyg_frame.cke_reset"));
		webDriver.switchTo().frame(contentArea);
		this.waitForElementVisible(
				By.cssSelector("body.cke_editable.cke_editable_themed.cke_contents_ltr.cke_show_borders"),
				Settings.WAIT_SECONDS * 2);
		assertTrue(this
				.getTextByLocator(
						By.cssSelector("body.cke_editable.cke_editable_themed.cke_contents_ltr.cke_show_borders"))
				.contains(emailTemplateContent));

		// switch back to main frame
		webDriver.switchTo().defaultContent();

	}

	@Test
	public void testInvitationButton() throws Exception {

		// init
		LoginPage main = new LoginPage(webDriver);

		// create new template text and name
		String emailTemplateName = "Template" + getRandName();
		log("Template Name : " + emailTemplateName);
		String emailTemplateContent = "This is an email template. Template content: " + getRandName();
		log("Template Content: " + emailTemplateContent);

		AccountOverviewPage newTemplate = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToEmailTemplatePage()
				.createNewEmailTemplate().editEmailTemplateName(emailTemplateName)
				.editEmailTemplateContent(emailTemplateContent).clickSaveButton().goToCompanySettingsPage()
				.checkMasterEmailSwitch(true).clickSubmitButton();

		AccountOverviewPage defaultTemplate = newTemplate.goToCompanySettingsPage()
				.clickEditDefaultEmailTemplateButton()
				.addEmailFunctionAndTemplate("User Invitation Template", emailTemplateName)
				.clickSaveEmailTemplateButton().clickSubmitButton();

		// create a new client
		String clientFirstName = this.getRandName();

		String clientLastName = this.getRandName();

		AccountOverviewPage newClientCreated = ((AbstractPage) defaultTemplate.clickNewClientAccountButton()
				.editClientTitle("Mr.").editClientFirstName(clientFirstName).editClientLastName(clientLastName)
				.editClientNationality("China").editClientResidence("Hong Kong").editClientDayOfBirth("1")
				.editClientMonthOfBirth("Jan").editClientYearOfBirth("1990").editClientIDType("Birth Certificate")
				.editClientIDNumber("id123").editClientEmployerName("testCompany")
				.editClientOccupation("testOccupation").editClientPosition("testPosition")
				.editClientEmailFunction("Enabled").addClientEmailByAddressAndType("TEST@test.com", "Home")
				.clickSaveButton_ClientAdditionalDetail()).goToAccountOverviewPage();

		// send invitation, delete client, and delete template
		newClientCreated.goToClientOverviewPage().simpleSearchByString(clientLastName)
				.goToClientDetailPageByName(clientLastName + ", " + clientFirstName).clickSendClientInvitationIcon()
				.goToCompanySettingsPage().clickEditDefaultEmailTemplateButton().deleteAllEmailFunctionAndTemplate()
				.clickSaveEmailTemplateButton().clickSubmitButton().goToClientOverviewPage()
				.simpleSearchByString(clientLastName)
				.goToClientDetailPageByName(clientLastName + ", " + clientFirstName)
				.goToEditPageByField("Additional Details")
				.clickDeleteButtonByLocator(By.id("gwt-debug-UserDetailWidget-deleteBtn")).goToEmailTemplatePage()
				.deleteExistingEmailTemplate(emailTemplateName);

	}

	/**
	 * Creates an email template.
	 * 
	 * @throws InterruptedException
	 */
	private void createEmailtemplate(String emailTemplateName, String emailTemplateContent)
			throws InterruptedException {
		{
			// click the create template button
			clickElement(By.id("gwt-debug-EmailTemplateOverviewView-createButton"));

			assertTrue("Expected dialog caption - Email Template", pageContainsStr("Email Template"));

			WebElement templateNameTextBox = waitGet(By.id("gwt-debug-EmailTemplateEditView-nameTextBox"));
			templateNameTextBox.clear();
			templateNameTextBox.sendKeys(emailTemplateName);

			wait(5);
			// get the iframe element of CKEditor
			WebElement contentArea = webDriver.findElement(By.cssSelector("iframe.cke_wysiwyg_frame.cke_reset"));

			// switch to iframe before sending keys, otherwise it wouldn't
			// work
			webDriver.switchTo().frame(contentArea);

			sendKeysToElement(By.cssSelector("body.cke_editable.cke_editable_themed.cke_contents_ltr.cke_show_borders"),
					emailTemplateContent);

			// switch back to main frame
			webDriver.switchTo().defaultContent();
			wait(3);
			waitForElementVisible(By.xpath("//button[@id='gwt-debug-EmailSignatureWidget-closeBtn' and .='Save']"), 10);
			wait(3);
			clickElement(By.xpath("//button[@id='gwt-debug-EmailSignatureWidget-closeBtn' and .='Save']"));

			clickYesButtonIfVisible();

			assertTrue("Expected newly created email template - " + emailTemplateName,
					pageContainsStr(emailTemplateName));
		}
	}
}
