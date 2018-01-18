package org.sly.uitest.sections.crm;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.crm.DocumentsPage;
import org.sly.uitest.settings.Settings;

/**
 * @author: Nandi Ouyang
 * @date: 15 Nov, 2014
 * @company: Prive Financial
 */
public class DocumentBinderTest extends AbstractTest {

	@Test
	public void testCreateAndSearchDocumentBinder() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		String name = this.getRandName();
		String date = "6-Nov-2014";
		String url = "testLink";
		String transactionReference = "CF900890";
		DocumentsPage binder = main.loginAs(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD).goToDocumentsPage()
				.goToAdvancedSearchPage().searchByTransactionReference(transactionReference).clickSearchButton();
		waitForWaitingScreenNotVisible();

		if (!this.pageContainsStr("No Document Binder Found")) {
			try {
				String foundName = getTextByLocator(By.xpath(".//a[@class = 'gwt-Anchor']"));
				binder.deleteBinderByName(foundName);
			} catch (TimeoutException e) {

			}

		}

		binder.clickClearSearchButton().clickCreateBinderButton()
				.editAddRegarding("Client Account", "Ageas Columbus - 00200754 (Miroslav Bernarde)")
				.editDocumentBinderName(name).editAttachementByURL(url).editTransactionReference(transactionReference)
				.clickSaveButton();

		DocumentsPage document = new DocumentsPage(webDriver);
		document.simpleSearchInDocumentPage(name);

		assertTrue(pageContainsStr("1 - 1 of 1 Results"));
		assertTrue(pageContainsStr(name));

		binder.deleteBinderByName(name);

	}
}
