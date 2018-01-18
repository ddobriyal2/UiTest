package org.sly.uitest.pageobjects.clientsandaccounts;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;

/**
 * This class represents the Risk Profile page, which can be navigated by
 * clicking 'Plan' -> 'Risk Profile'
 * 
 * @author Lynne Huang
 * @date : 18 Sep, 2015
 * @company Prive Financial
 */
public class RiskProfilePage extends AbstractPage {

	/**
	 * @param webDriver
	 */
	public RiskProfilePage(WebDriver webDriver) {

		super();
		this.webDriver = webDriver;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		try {
			wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.id("gwt-debug-RiskProfileView-riskProfilePanel")));

		} catch (Exception ex) {
			// wait.until(ExpectedConditions.visibilityOfElementLocated(By
			// .id("gwt-debug-MyMainMaterialView-mainPanel")));

		}

		assertTrue(pageContainsStr(" Risk Profile "));
	}

	public AccountOverviewPage clickSaveButton() {

		clickElement(By.id("gwt-debug-RiskProfile-submitButton"));
		clickOkButtonIfVisible();
		return new AccountOverviewPage(webDriver);
	}

	/**
	 * select the risk exposure of risk profile
	 * 
	 * @param exposure
	 */
	public RiskProfilePage selectRiskExposure(String exposure) {
		waitForElementVisible(By.xpath(".//*[contains(text(),' Please select the corresponding proportion below. ')]"),
				10);
		switch (exposure) {
		case "0 to 20":
			clickElement(By.xpath(".//*[contains(text(),'0% to 20%')]"));
			break;
		case "20 to 40":
			clickElement(By.xpath(".//*[contains(text(),'20% to 40%')]"));
			break;
		case "40 to 60":
			clickElement(By.xpath(".//*[contains(text(),'40% to 60%')]"));
			break;
		case "60 to 80":
			clickElement(By.xpath(".//*[contains(text(),'60% to 80%')]"));
			break;
		case "80 to 100":
			clickElement(By.xpath(".//*[contains(text(),'80% to 100%')]"));
			break;
		default:
			clickElement(By.xpath(".//*[contains(text(),'40% to 60%')]"));
			break;

		}

		return this;
	}

	/**
	 * click increase button to add investment experience by asset
	 * 
	 * @param asset
	 * @return
	 */
	public RiskProfilePage clickIncreaseInvestmentExperienceByAsset(String asset) {

		String experience = getTextByLocator(
				By.xpath(".//tr[td[div[contains(text(),'" + asset + "')]]]//following-sibling::tr[3]//div"));

		clickElement(
				By.xpath(".//tr[td[div[contains(text(),'" + asset + "')]]]//following-sibling::tr[2]//button[.='+']"));

		if (!experience.equals("Investment Experience")) {
			assertFalse(experience.equals(
					By.xpath(".//tr[td[div[contains(text(),'" + asset + "')]]]//following-sibling::tr[3]//div")));
		}
		return this;
	}

	/**
	 * click increase button to add investment experience by asset
	 * 
	 * @param asset
	 * @return
	 */
	public RiskProfilePage clickDecreaseInvestmentExperienceByAsset(String asset) {

		String experience = getTextByLocator(
				By.xpath(".//tr[td[div[contains(text(),'" + asset + "')]]]//following-sibling::tr[3]//div"));

		clickElement(
				By.xpath(".//tr[td[div[contains(text(),'" + asset + "')]]]//following-sibling::tr[2]//button[.='-']"));

		if (!experience.equals("No Knowledge")) {
			assertFalse(experience.equals(
					By.xpath(".//tr[td[div[contains(text(),'" + asset + "')]]]//following-sibling::tr[3]//div")));
		}
		return this;
	}

	/**
	 * select region of investment
	 * 
	 * @param region
	 * @return
	 */
	public RiskProfilePage selectRegion(String region) {

		waitForElementVisible(By.xpath(".//td[contains(text(),'" + region + "')]//following-sibling::td//input"), 30);

		WebElement elem = webDriver
				.findElement(By.xpath(".//td[contains(text(),'" + region + "')]//following-sibling::td//input"));

		setCheckboxStatus2(elem, true);

		return this;
	}

	public RiskProfilePage selectAnswerForScenario1(String answer) {
		switch (answer) {
		case "Red":
			clickElement(By.xpath(
					".//p[contains(text(),'Scenario 1')]//following-sibling::table[1]//label[contains(text(),'Red')]"));
			break;
		case "Blue":
			clickElement(By.xpath(
					".//p[contains(text(),'Scenario 1')]//following-sibling::table[1]//label[contains(text(),'Blue')]"));
			break;
		}
		return this;
	}

	public RiskProfilePage selectAnswerForScenario2(String answer) {
		switch (answer) {
		case "Red":
			clickElement(By.xpath(
					".//p[contains(text(),'Scenario 2')]//following-sibling::table[1]//label[contains(text(),'Red')]"));
			break;
		case "Blue":
			clickElement(By.xpath(
					".//p[contains(text(),'Scenario 2')]//following-sibling::table[1]//label[contains(text(),'Blue')]"));
			break;
		}
		return this;
	}

	public RiskProfilePage selectAnswerForScenario3(String answer) {
		switch (answer) {
		case "Red":
			clickElement(By.xpath(
					".//p[contains(text(),'Scenario 3')]//following-sibling::table[1]//label[contains(text(),'Red')]"));
			break;
		case "Blue":
			clickElement(By.xpath(
					".//p[contains(text(),'Scenario 3')]//following-sibling::table[1]//label[contains(text(),'Blue')]"));
			break;
		}
		return this;
	}

	public RiskProfilePage selectAnswerForScenario4(String answer) {
		switch (answer) {
		case "Red":
			clickElement(By.xpath(
					".//p[contains(text(),'Scenario 4')]//following-sibling::table[1]//label[contains(text(),'Red')]"));
			break;
		case "Blue":
			clickElement(By.xpath(
					".//p[contains(text(),'Scenario 4')]//following-sibling::table[1]//label[contains(text(),'Blue')]"));
			break;
		}
		return this;
	}

	public RiskProfilePage selectAnswerForScenario5(String answer) {
		switch (answer) {
		case "Red":
			clickElement(By.xpath(
					".//p[contains(text(),'Scenario 5')]//following-sibling::table[1]//label[contains(text(),'Red')]"));
			break;
		case "Blue":
			clickElement(By.xpath(
					".//p[contains(text(),'Scenario 5')]//following-sibling::table[1]//label[contains(text(),'Blue')]"));
			break;
		}
		return this;
	}

	public RiskProfilePage selectAnswerForScenario6(String answer) {
		switch (answer) {
		case "Red":
			clickElement(By.xpath(
					".//p[contains(text(),'Scenario 6')]//following-sibling::table[1]//label[contains(text(),'Red')]"));
			break;
		case "Blue":
			clickElement(By.xpath(
					".//p[contains(text(),'Scenario 6')]//following-sibling::table[1]//label[contains(text(),'Blue')]"));
			break;
		}
		return this;
	}

	public RiskProfilePage selectAnswerForScenario7(String answer) {
		switch (answer) {
		case "Red":
			clickElement(By.xpath(
					".//p[contains(text(),'Scenario 7')]//following-sibling::table[1]//label[contains(text(),'Red')]"));
			break;
		case "Blue":
			clickElement(By.xpath(
					".//p[contains(text(),'Scenario 7')]//following-sibling::table[1]//label[contains(text(),'Blue')]"));
			break;
		}
		return this;
	}

	public RiskProfilePage selectAnswerForScenario8(String answer) {
		switch (answer) {
		case "Buy More":
			clickElement(By.xpath(
					".//p[contains(text(),'Scenario 8')]//following-sibling::table[1]//label[contains(text(),'Buy More')]"));
			break;
		case "Get Out":
			clickElement(By.xpath(
					".//p[contains(text(),'Scenario 8')]//following-sibling::table[1]//label[contains(text(),'Get Out')]"));
			break;
		case "Do Nothing":
			clickElement(By.xpath(
					".//p[contains(text(),'Scenario 8')]//following-sibling::table[1]//label[contains(text(),'Do Nothing')]"));
			break;
		}
		return this;
	}

	public RiskProfilePage selectAnswerForScenario9(String answer) {
		switch (answer) {
		case "Buy More":
			clickElement(By.xpath(
					".//p[contains(text(),'Scenario 9')]//following-sibling::table[1]//label[contains(text(),'Buy More')]"));
			break;
		case "Get Out":
			clickElement(By.xpath(
					".//p[contains(text(),'Scenario 9')]//following-sibling::table[1]//label[contains(text(),'Get Out')]"));
			break;
		case "Do Nothing":
			clickElement(By.xpath(
					".//p[contains(text(),'Scenario 9')]//following-sibling::table[1]//label[contains(text(),'Do Nothing')]"));
			break;
		}
		return this;
	}
}
