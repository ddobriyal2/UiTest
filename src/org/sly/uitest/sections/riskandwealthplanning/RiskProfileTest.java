package org.sly.uitest.sections.riskandwealthplanning;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.clientsandaccounts.AccountOverviewPage;
import org.sly.uitest.settings.Settings;

public class RiskProfileTest extends AbstractTest {
	@Test
	public void testCreateRiskProfile() throws InterruptedException {
		String account = "Ageas Columbus - 00200754";
		String today = getCurrentTimeInFormat("dd-MMM-yyyy");

		LoginPage main = new LoginPage(webDriver);
		AccountOverviewPage accountpage = main.loginAs(Settings.ADVISOR_USERNAME, Settings.ADVISOR_PASSWORD)
				.goToAccountOverviewPage().simpleSearchByString(account).goToAccountDetailPageByName(account)
				.goToDetailsPage().clickCreateNewRiskProfileButton().selectRiskExposure("0 to 20")
				.clickDecreaseInvestmentExperienceByAsset("Equity").clickDecreaseInvestmentExperienceByAsset("Equity")
				.clickDecreaseInvestmentExperienceByAsset("Equity").clickIncreaseInvestmentExperienceByAsset("Equity")
				.clickIncreaseInvestmentExperienceByAsset("Equity").clickIncreaseInvestmentExperienceByAsset("Equity")
				.selectRegion("North America").selectRegion("Europe").selectRegion("Asia-Pacific")
				.selectRegion("Emerging Markets").selectRegion("Global").selectAnswerForScenario1("Red")
				.selectAnswerForScenario1("Blue").selectAnswerForScenario2("Red").selectAnswerForScenario2("Blue")
				.selectAnswerForScenario3("Red").selectAnswerForScenario3("Blue").selectAnswerForScenario4("Red")
				.selectAnswerForScenario4("Blue").selectAnswerForScenario5("Red").selectAnswerForScenario5("Blue")
				.selectAnswerForScenario6("Red").selectAnswerForScenario6("Blue").selectAnswerForScenario7("Red")
				.selectAnswerForScenario7("Blue").selectAnswerForScenario8("Buy More")
				.selectAnswerForScenario8("Get Out").selectAnswerForScenario8("Do Nothing")
				.selectAnswerForScenario7("Blue").selectAnswerForScenario9("Buy More")
				.selectAnswerForScenario9("Get Out").selectAnswerForScenario9("Do Nothing").clickSaveButton();

		accountpage.goToAccountOverviewPage().simpleSearchByString(account).goToAccountDetailPageByName(account)
				.goToDetailsPage().editRiskProfileByDate(today);

		assertTrue(isElementVisible(By.xpath(".//*[@class='gwt-RadioButton gwt-RadioButton-disabled']")));
		assertTrue(isElementVisible(By.xpath(".//*[@class='gwt-CheckBox gwt-CheckBox-disabled']")));

	}
}
