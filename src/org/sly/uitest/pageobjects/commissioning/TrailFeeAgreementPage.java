package org.sly.uitest.pageobjects.commissioning;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;

/**
 * 
 * This class represents the Trail Fee Agreement Page, which can be navigated by
 * clicking 'Accounting' -> 'Trail Fee Agreement'
 * 
 * URL: http://192.168.1.104:8080/SlyAWS/?locale=en_US#commfeetrailfee
 * 
 * @author Lynne Huang
 * @date : 26 Aug, 2015
 * @company Prive Financial
 *
 */
public class TrailFeeAgreementPage extends AbstractPage {

	/**
	 * @param webDriver
	 */
	public TrailFeeAgreementPage(WebDriver webDriver) {

		super();
		this.webDriver = webDriver;
		// TODO Auto-generated constructor stub

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gwt-debug-TrailFeeListView-feesPanel")));

		assertTrue(pageContainsStr(" Trail Fee Agreements "));
	}

}
