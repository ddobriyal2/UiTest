package org.sly.uitest.sections.crm;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.AWTException;

import org.junit.Test;
import org.openqa.selenium.By;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.clientsandaccounts.CRMPage;
import org.sly.uitest.settings.Settings;

/**
 * @author Lynne Huang
 * @date : 25 Sep, 2015
 * @company Prive Financial
 */
public class NotesTest extends AbstractPage {

	@Test
	public void testCreateAndEditAndDeleteNote() throws InterruptedException, AWTException {

		LoginPage main = new LoginPage(webDriver);

		String noteName = "NoteName" + this.getRandName();
		String newNoteName = "NewNoteName" + this.getRandName();
		String oldType = "Super User";
		// String oldName = "Selenium1 Test1";
		String newType = "Admin";
		String newName = "Seleniumtestclerical Seleniumtestclerical";
		String text = "Test text " + this.getRandName();
		String newText = "New Test Text " + this.getRandName();

		// create note
		CRMPage crm = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToAccountOverviewPage()
				.simpleSearchByString("John").goToClientDetailPageByName("Doe, John").goToCRMPage().clickAddNotesIcon()
				.clickAddBindersButton().clickAddAttachmentButton().uploadFileForAttachment()
				.editBinderName(getRandName()).editNoteName(noteName).editNoteText(text).clickSaveNoteButton();

		assertTrue(pageContainsStr(noteName));

		// edit note
		crm.editNoteByName(noteName).editNoteName(newNoteName).deleteAllBinders();

		String oldName = getTextByLocator(By.id("gwt-debug-EditTaskPresenter-ownerAnchor"));

		crm.clickEditOwnerIcon().editRemoveTaskOwner(oldType, oldName).editAddTaskOwner(newType, newName)
				.clickOkButton().editNoteText(newText).clickSaveNoteButton();

		waitForElementNotVisible(By.id("gwt-debug-NoteEditView-saveButton"), Settings.WAIT_SECONDS);
		// wait(Settings.WAIT_SECONDS);

		assertFalse(pageContainsStr(noteName));
		assertTrue(pageContainsStr(newNoteName));

		// delete note
		crm.deleteNoteByName(newNoteName);

		waitForElementNotVisible(By.xpath(".//*[contains(text(),'" + newNoteName + "')]"), Settings.WAIT_SECONDS);

		assertFalse(pageContainsStr(newNoteName));

	}
}
