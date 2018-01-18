package org.sly.uitest.sections.citiFundExplorer;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Test;
import org.openqa.selenium.By;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.settings.Settings;

/**
 * Test plan <a href =
 * "https://docs.google.com/spreadsheets/d/1qiQSaUwRzP2cKDLwTIP3_rUhykjl3tz5RobXrJhskU0/edit#gid=0"
 * >Here<a>
 * 
 * @author Benny Leung
 * @date Aug 23, 2016
 * @company Prive Financial
 */
public class CitiFundExplorerDocumentTest extends AbstractTest {

	String UrlForSingaporeIPBPlatform = Settings.BASE_URL
			+ "?locale=en&viewMode=BASIC#investmentList;P1=ce5fdc0e-0cdb-4e13-8ca9-11655d159e7c;P3=SGPIPB";

	String UrlForSingaporeGCGPlatform = Settings.BASE_URL
			+ "?locale=en&viewMode=BASIC#investmentList;P1=ce5fdc0e-0cdb-4e13-8ca9-11655d159e7c;P3=SGPGCG";

	String UrlForThailandPlatform = Settings.BASE_URL
			+ "?locale=en&viewMode=BASIC#investmentList;P1=ce5fdc0e-0cdb-4e13-8ca9-11655d159e7c;P3=THA";

	String UrlForHongKongPlatform = Settings.BASE_URL
			+ "?locale=en&viewMode=BASIC#investmentList;P1=ce5fdc0e-0cdb-4e13-8ca9-11655d159e7c;P3=HKG";

	// Test case #1
	@Test
	public void testForDocumentForHKG() throws InterruptedException {
		init(UrlForHongKongPlatform);
		ArrayList<String> documentList = new ArrayList<String>();
		documentList.add("Fact Sheet (en)");
		documentList.add("Fact Sheet (zh_TW)");
		documentList.add("Product Key Facts (en)");
		documentList.add("Product Key Facts (zh_TW)");
		documentList.add("Prospectus (en)");
		documentList.add("Prospectus (zh_TW) ");

		ArrayList<Integer> investmentNumberList = getFiveRandomInteger();
		for (Integer investmentNumber : investmentNumberList) {
			clickInvestment(investmentNumber, documentList);
		}
	}

	// Test case #2
	@Test
	public void testForDocumentForSGPGCG() throws InterruptedException {
		init(UrlForSingaporeGCGPlatform);
		ArrayList<String> documentList = new ArrayList<String>();
		documentList.add("Fact Sheet (en)");
		documentList.add("Product Highlight Sheet (en)");
		documentList.add("Prospectus (en)");

		ArrayList<Integer> investmentNumberList = getFiveRandomInteger();
		for (Integer investmentNumber : investmentNumberList) {
			clickInvestment(investmentNumber, documentList);
		}

	}

	// Test case #3
	@Test
	public void testForDocumentForSGPIPB() throws InterruptedException {
		init(UrlForSingaporeIPBPlatform);
		ArrayList<String> documentList = new ArrayList<String>();
		documentList.add("Fact Sheet (en)");
		documentList.add("Product Highlight Sheet (en)");
		documentList.add("Prospectus (en)");

		ArrayList<Integer> investmentNumberList = getFiveRandomInteger();
		for (Integer investmentNumber : investmentNumberList) {
			clickInvestment(investmentNumber, documentList);
		}
	}

	// Test case #4
	@Test
	public void testForDocumentForTHA() throws InterruptedException {
		init(UrlForThailandPlatform);
		ArrayList<String> documentList = new ArrayList<String>();
		documentList.add("Annual Report (en)");
		documentList.add("Fact Sheet (en)");
		documentList.add("Prospectus (en)");

		ArrayList<Integer> investmentNumberList = getFiveRandomInteger();
		for (Integer investmentNumber : investmentNumberList) {
			clickInvestment(investmentNumber, documentList);
		}

	}

	// go to HK platform and skip HelpOverlay
	public void init(String platform) throws InterruptedException {

		webDriver.get(platform);
		// wait for Help Overlay
		waitForElementVisible(By.xpath(".//*[@class='introjs-button introjs-skipbutton']"), 30);
		clickElement(By.xpath(".//*[@class='introjs-button introjs-skipbutton']"));
		clickOkButtonIfVisible();
		waitForElementVisible(By.id("gwt-debug-ManagerListItem-strategyName"), 10);
	}

	/**
	 * click investment and check if documents are in the page.
	 * 
	 * @param numberOfInvestment
	 *            . number of investment to be chosen from investment list
	 * @param documentList
	 *            list of documents which are expected to be seen in the page
	 */
	public void clickInvestment(Integer numberOfInvestment, ArrayList<String> documentList) {

		String numberOfInvestmentText = String.valueOf(numberOfInvestment);

		waitForElementVisible(
				By.xpath("(.//*[@id='gwt-debug-ManagerListItem-strategyName'])[" + numberOfInvestmentText + "]"), 30);
		String investmentName = getTextByLocator(
				By.xpath("(.//*[@id='gwt-debug-ManagerListItem-strategyName'])[" + numberOfInvestmentText + "]"));
		clickElement(By.xpath("(.//*[@id='gwt-debug-ManagerListItem-strategyName'])[" + numberOfInvestmentText + "]"));

		waitForElementVisible(By.xpath(".//*[@class='coloredBtn' and .='Details']"), 5);

		assertTrue(isElementVisible(By.id("gwt-debug-ManagetListDetailsPopupView-strategyDescLabel")));
		assertTrue(isElementVisible(By.id("gwt-debug-ManagetListDetailsPopupView-chartPanel")));

		clickElement(By.xpath(".//*[@class='coloredBtn' and .='Details']"));

		waitForElementVisible(By.xpath(".//*[@class='thisBackButton']"), 20);

		for (String documentName : documentList) {
			try {
				assertTrue(pageContainsStr(documentName));
			} catch (AssertionError e) {
				log(documentName + " does not exist in " + investmentName);
			}
		}
		clickElement(By.xpath(".//*[@class='thisBackButton']"));
		waitForElementVisible(By.id("gwt-debug-ManagerListItem-strategyName"), 20);

	}

	public ArrayList<Integer> getFiveRandomInteger() {

		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(Integer.valueOf(randInt(1, 10)));
		list.add(Integer.valueOf(randInt(1, 10)));
		list.add(Integer.valueOf(randInt(1, 10)));
		list.add(Integer.valueOf(randInt(1, 10)));
		list.add(Integer.valueOf(randInt(1, 10)));

		return list;
	}

	public int randInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
}
