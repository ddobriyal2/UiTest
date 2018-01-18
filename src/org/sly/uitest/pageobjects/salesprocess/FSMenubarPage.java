package org.sly.uitest.pageobjects.salesprocess;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.pageobjects.clientsandaccounts.ClientOverviewPage;
import org.sly.uitest.pageobjects.commissioning.FeeOverviewPage;
import org.sly.uitest.pageobjects.commissioning.NewBusinessEventPage;
import org.sly.uitest.pageobjects.companyadmin.EmployeePage;
import org.sly.uitest.pageobjects.mysettings.UserProfilePage;

public class FSMenubarPage extends AbstractPage {
	public FSMenubarPage(WebDriver webDriver) {
		super();
		this.webDriver = webDriver;

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gwt-debug-FinanceSalesView-topSection")));

	}

	/**
	 * Go to SalesWizardPage in FS
	 * 
	 * @return {@link SalesWizardPage}
	 * @throws InterruptedException
	 */
	public ProductOverviewPage goToProductOverviewPageFS() throws InterruptedException {
		clickElement(By.xpath(".//a[@href='#customcontent;contentName=products']"));
		return new ProductOverviewPage(webDriver);
	}

	/**
	 * Go to ClientOverviewPage in FS
	 * 
	 * @return {@link ClientOverviewPage}
	 */
	public ClientOverviewPage goToClientOverviewPageFS() {

		waitForElementVisible(By.xpath(".//a[@href='#investorList']"), 30);

		waitForElementClickable(By.xpath(".//a[@href='#investorList']"), 30);

		clickElement(By.xpath(".//a[@href='#investorList']"));

		return new ClientOverviewPage(webDriver);
	}

	/**
	 * Go to FeeOverviewPage in FS
	 * 
	 * @return {@link FeeOverviewPage}
	 */
	public FeeOverviewPage goToCommissionPageFS() {
		clickElement(By.xpath(".//a[@href='#feeoverview;feeWidgetType=2']"));
		return new FeeOverviewPage(webDriver, "");
	}

	/**
	 * Go to UserProfilePage in FS
	 * 
	 * @return {@link UserProfilePage}
	 */
	public UserProfilePage goToUserProfilePageFS() {
		clickElement(By.xpath(".//a[@href='#myprofile']"));

		return new UserProfilePage(webDriver);
	}

	/**
	 * Click Logout button in FS
	 */
	public void checkLogoutFS() {

		clickElement(By.xpath(".//*[@class='logoutButton']"));
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(60, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);
		wait.until(ExpectedConditions.titleContains("Startseite"));
	}

	/**
	 * Go to NewBusinessEventPage in FS
	 * 
	 * @return {@link NewBusinessEventPage}
	 */
	public NewBusinessEventPage goToNewBusinessEventPageFS() {

		webDriver.get("https://fsuat.privemanagers.com/?locale=de_DE&viewMode=FINANCESALES#businesseventlist");

		return new NewBusinessEventPage(webDriver);
	}

	/**
	 * Go to FSTasksPage in FS
	 * 
	 * @return {@link FSTasksPage}
	 * @throws InterruptedException
	 */
	public FSTasksPage goToTasklistPageFS() throws InterruptedException {

		wait(3);
		webDriver.get("https://fsuat.privemanagers.com/?locale=de_DE&viewMode=FINANCESALES#tasklist");
		wait(5);
		return new FSTasksPage(webDriver);
	}

	public EmployeePage goToEmployeePage() throws InterruptedException {

		wait(3);
		webDriver.get("https://fsuat.privemanagers.com/?locale=de_DE&viewMode=FINANCESALES#companyAdmin");
		wait(3);

		return new EmployeePage(webDriver);
	}

}
