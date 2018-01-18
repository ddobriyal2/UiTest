package org.sly.uitest.sections.operations;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;

import org.junit.Test;
import org.openqa.selenium.By;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.operations.LastAccountValueUpdatePage;
import org.sly.uitest.settings.Settings;

/**
 * @author Lynne Huang
 * @date : 6 Oct, 2015
 * @company Prive Financial
 */
public class LastAccountValueDateTest extends AbstractTest {

	@Test
	public void testLastAccountValueUpdateSearch() throws InterruptedException, ParseException {

		String dateRage_7 = "Show accounts not updated for 7 days or more";
		String dateRage_14 = "Show accounts not updated for 14 days or more";
		String dateRage_30 = "Show accounts not updated for 30 days or more";
		String dateRage_60 = "Show accounts not updated for 60 days or more";
		String dateRage_never = "Show accounts never updated";
		String dataSource = "Generali Vision";
		String dataSourceName = "Generali";

		String noAccounts = "All investor accounts are updated on the specified date.";

		String format = "d-MMM-yyyy";
		String today = getCurrentTimeInFormat(format);
		String yesterday = getDateAfterDays(today, -1, format);

		LoginPage main = new LoginPage(webDriver);

		LastAccountValueUpdatePage lavu = main.loginAs(Settings.CRM_USERNAME, Settings.CRM_PASSWORD)
				.goToLastAccountValueUpdatePage().verifyReferenceDate(yesterday).editAddDataSource(dataSource);

		// Show accounts not updated for 7 days or more
		lavu.editDateRange(dateRage_7).clickProcessButton();

		String before_7 = getDateAfterDays(yesterday, -7, format);

		int size_7 = getSizeOfElements(By.className("mat-table-body"));

		if (size_7 > 0) {

			for (int i = 1; i <= size_7; i++) {

				assertTrue(getTextByLocator(
						By.xpath("(.//*[@id='gwt-debug-lastAccountUpdate-linkPortfolioName'])[" + i + "]"))
								.toLowerCase().contains(dataSourceName.toLowerCase()));

				assertTrue(
						compareTwoDays(before_7,
								getTextByLocator(
										By.xpath(".//*[@id='gwt-debug-SortableFlexTableAsync-table-" + i + "-8']")),
								format) >= 0);

			}
		} else {
			assertTrue(pageContainsStr(noAccounts));
		}

		// Show accounts not updated for 14 days or more
		lavu.editDateRange(dateRage_14).clickProcessButton();

		String before_14 = getDateAfterDays(yesterday, -14, format);
		int size_14 = getSizeOfElements(By.className("mat-table-body"));

		if (size_14 > 0) {

			for (int i = 1; i <= size_14; i++) {

				assertTrue(getTextByLocator(
						By.xpath("(.//*[@id='gwt-debug-lastAccountUpdate-linkPortfolioName'])[" + i + "]"))
								.toLowerCase().contains(dataSourceName.toLowerCase()));

				assertTrue(
						compareTwoDays(before_14,
								getTextByLocator(
										By.xpath(".//*[@id='gwt-debug-SortableFlexTableAsync-table-" + i + "-8']")),
								format) >= 0);

			}
		} else {
			assertTrue(pageContainsStr(noAccounts));
		}

		// Show accounts not updated for 30 days or more
		lavu.editDateRange(dateRage_30).clickProcessButton();

		String before_30 = getDateAfterDays(yesterday, -30, format);
		int size_30 = getSizeOfElements(By.className("mat-table-body"));

		if (size_30 > 0) {

			for (int i = 1; i <= size_30; i++) {

				assertTrue(getTextByLocator(
						By.xpath("(.//*[@id='gwt-debug-lastAccountUpdate-linkPortfolioName'])[" + i + "]"))
								.toLowerCase().contains(dataSourceName.toLowerCase()));

				if (!getTextByLocator(By.xpath(".//*[@id='gwt-debug-SortableFlexTableAsync-table-" + i + "-8']"))
						.contains("N/A")) {
					assertTrue(compareTwoDays(before_30,
							getTextByLocator(
									By.xpath(".//*[@id='gwt-debug-SortableFlexTableAsync-table-" + i + "-8']")),
							format) >= 0);
				}

			}
		} else {
			assertTrue(pageContainsStr(noAccounts));
		}

		// Show accounts not updated for 60 days or more
		lavu.editDateRange(dateRage_60).clickProcessButton();

		String before_60 = getDateAfterDays(yesterday, -60, format);
		int size_60 = getSizeOfElements(By.className("mat-table-body"));

		if (size_60 > 0) {

			for (int i = 1; i <= size_60; i++) {

				assertTrue(getTextByLocator(
						By.xpath("(.//*[@id='gwt-debug-lastAccountUpdate-linkPortfolioName'])[" + i + "]"))
								.toLowerCase().contains(dataSourceName.toLowerCase()));

				if (!getTextByLocator(By.xpath(".//*[@id='gwt-debug-SortableFlexTableAsync-table-" + i + "-8']"))
						.contains("N/A")) {
					assertTrue(compareTwoDays(before_60,
							getTextByLocator(
									By.xpath(".//*[@id='gwt-debug-SortableFlexTableAsync-table-" + i + "-8']")),
							format) >= 0);
				}

			}
		} else {
			assertTrue(pageContainsStr(noAccounts));
		}

		// Show accounts never updated
		lavu.editDateRange(dateRage_never).clickProcessButton();

		int size_never = getSizeOfElements(By.className("mat-table-body"));

		if (size_never > 0) {

			for (int i = 1; i <= size_never; i++) {

				assertTrue(getTextByLocator(
						By.xpath("(.//*[@id='gwt-debug-lastAccountUpdate-linkPortfolioName'])[" + i + "]"))
								.toLowerCase().contains(dataSourceName.toLowerCase()));

				assertTrue(getTextByLocator(By.xpath(".//*[@id='gwt-debug-SortableFlexTableAsync-table-" + i + "-8']"))
						.equals("N/A"));

			}
		} else {
			assertTrue(pageContainsStr(noAccounts));
		}

		// Show accounts never updated
		lavu.editDateRange(dateRage_never).editRemoveDataSource(dataSource).clickProcessButton();

		size_never = getSizeOfElements(By.className("mat-table-body"));

		if (size_never > 0) {

			for (int i = 1; i <= size_never; i++) {

				assertTrue(getTextByLocator(By.xpath(".//*[@id='gwt-debug-SortableFlexTableAsync-table-" + i + "-8']"))
						.equals("N/A"));

			}
		} else {
			assertTrue(pageContainsStr(noAccounts));
		}
	}
}
