package org.sly.uitest.pageobjects.salesprocess;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;

public class ProductOverviewPage extends AbstractPage {
	public ProductOverviewPage(WebDriver webDriver) {
		super();
		this.webDriver = webDriver;

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gwt-debug-CustomContentView-contentPanel")));
	}

	public ProductOverviewPage goToAdamasPage() throws InterruptedException {
		clickElement(By.xpath(".//*[@id='gwt-debug-DocumentView-documentPanel']/div/div[1]/div/a/img"));
		waitForElementVisible(By.xpath(".//img[@src='img/financesales/icon_alle_kunden.png']"), 10);
		return this;
	}

	public ClientRegisterPage clickAddClientForFS() throws InterruptedException {
		clickElement(By.xpath(".//img[@src='img/financesales/icon_kunden_hinzufuegen.png']"));
		return new ClientRegisterPage(webDriver);
	}

	public ProductOverviewPage clickAddSalesButton() {
		waitForElementVisible(By.xpath(".//img[@src='img/financesales/icon_alle_kunden.png']"), 10);
		clickElement(By.xpath(".//img[@src='img/financesales/icon_alle_kunden.png']"));
		return this;
	}

	public SalesWizardPage startAdamasSalesWizard(String client) throws InterruptedException {

		waitForElementVisible(By.id("gwt-debug-AddToProductSalesControllerView-clientListBox"), 10);

		wait(1);
		selectFromDropdown(By.id("gwt-debug-AddToProductSalesControllerView-clientListBox"), client);

		wait(1);
		clickElement(By.id("gwt-debug-AddToProductSalesControllerView-okButton"));
		return new SalesWizardPage(webDriver);
	}
}
