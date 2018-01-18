package org.sly.uitest.pageobjects.assetmanagement;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;

/**
 * This class represents the Strategy Rules Page which can be navigated by
 * clicking 'Build' -> 'Strategy Rules'
 * 
 * URL: http://192.168.1.104:8080/SlyAWS/?locale=en_US#mystrategyrule
 * 
 * @author Lynne Huang
 * @date : 24 Sep, 2015
 * @company Prive Financial
 */
public class StrategyRulesPage extends AbstractPage {

	/**
	 * @param webDriver
	 */
	public StrategyRulesPage(WebDriver webDriver) {

		super();
		this.webDriver = webDriver;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);
		try {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.id("gwt-debug-StrategyRuleListForManagerView-rulesTablePanel")));

		} catch (Exception e) {
			// wait.until(ExpectedConditions.visibilityOfElementLocated(By
			// .id("gwt-debug-MyMainMaterialView-mainPanel")));

		}

	}

	/**
	 * Click the Setup button with the given strategy rule name, and then go to
	 * Edit Strategy Rule
	 * 
	 * @param name
	 *            the name of the strategy rule
	 * @return {@link StrategyRulesPage}
	 */
	public StrategyRulesPage clickSetupByName(String name) {

		clickElement(By.xpath("//td[.='" + name + "']/following-sibling::td[//button]//button[.='Setup']"));

		return this;
	}

}
