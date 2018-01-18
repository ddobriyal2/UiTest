package org.sly.uitest.sections.reporting.portfolio;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Test;
import org.openqa.selenium.By;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.clientsandaccounts.AccountOverviewPage;
import org.sly.uitest.pageobjects.clientsandaccounts.DetailPage;

public class InterFsHKTest extends AbstractTest {

	@Test
	public void testInterFSReport() throws InterruptedException, MalformedURLException, IOException {
		String investor = "ZIL Vista - 19932";
		String fileName = "";
		LoginPage main = new LoginPage(webDriver);

		AccountOverviewPage account = main.loginAs("IFSHK_10", "IFSHK_10").goToAccountOverviewPage();

		DetailPage detailPage = account.simpleSearchByString(investor).goToAccountHoldingsPageByName(investor)
				.goToDetailsPage();

		Integer size = getSizeOfElements(By.id("gwt-debug-ReportsSectionPresenter-reportUrl"));

		// if there is no existing report, generate one.
		if (!isElementVisible(By.xpath("(.//*[contains(text(),'Client13091.ACC19932')])[1]"))) {
			detailPage.goToAddPageByField("Reports");
			clickElement(By.id("gwt-debug-GenerateInvestorReport-submitBtn"));
			detailPage.goToProcess();
			waitForTextVisible(By.xpath(".//*[@class='processImageMenuPopupSubtitle']"), "Report - Completed");

			waitForElementVisible(By.xpath(".//*[lcontains(text(),'Client 13091 - ZIL Vista - 19932')]"), 60);

		}

		fileName = getTextByLocator(By.xpath("(.//*[contains(text(),'Client13091.ACC19932')])[1]"));

		clickElement(By.xpath("(.//*[contains(text(),'Client13091.ACC19932')])[1]"));

		wait(10);
		log(fileName);
		// check the name of the report
		assertTrue(fileName.contains("Client13091") && fileName.contains("ACC19932") && fileName.contains(".pdf"));
		checkDownloadedFile(fileName);
	}

}
