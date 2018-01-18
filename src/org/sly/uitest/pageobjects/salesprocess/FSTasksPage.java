package org.sly.uitest.pageobjects.salesprocess;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;

/**
 * This page is for the task page of Finance Sales Tasklist Page:
 * https://fsuat.privemanagers.com/?locale=de_DE&viewMode=FINANCESALES#tasklist
 * 
 * 
 * @author Benny Leung
 * @date : Oct 6, 2016
 * @company Prive Financial
 */
public class FSTasksPage extends AbstractPage {

	public FSTasksPage(WebDriver webDriver) {

		super();
		this.webDriver = webDriver;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gwt-debug-FinanceSalesView-mainPanel")));
			this.waitForWaitingScreenNotVisible();
		} catch (Exception ex) {

		}

	}

	/**
	 * On the Tasks page, click the task name directly to invoke the Task edit
	 * page
	 * 
	 * @param name
	 *            the task name
	 * @return {@link FSTasksPage}
	 */
	public FSTasksPage editTaskByName(String name) {
		waitForElementVisible(By.id("gwt-debug-TaskOverviewView-searchBox"), 30);
		sendKeysToElement(By.id("gwt-debug-TaskOverviewView-searchBox"), name);
		clickElement(By.id("gwt-debug-TaskOverviewView-searchImg"));

		waitForElementVisible(By.xpath("//a[contains(text(),'" + name + "')]"), 60);
		waitForElementClickable(By.xpath("//a[contains(text(),'" + name + "')]"), 60);
		clickElement(By.xpath("//a[contains(text(),'" + name + "')]"));

		return this;

	}

	/**
	 * After click on any task, on the popup Task edit page, edit the task
	 * status
	 * 
	 * @param status
	 *            status of task
	 * @return {@link FSTasksPage}
	 */
	public FSTasksPage editTaskStatus(String status) {

		waitForElementVisible(By.id("gwt-debug-EditTaskView-statusListBox"), 5);

		selectFromDropdown(By.id("gwt-debug-EditTaskView-statusListBox"), status);

		return this;
	}

	/**
	 * FS to confirm whether advisor is already registered with Adamas
	 * 
	 * @param registered
	 * @return {@link FSTasksPage}
	 */
	public FSTasksPage confirmAdvisorRegistration(boolean registered) {

		waitForElementVisible(By.id("gwt-debug-ActivitiTaskUtils-advisorRegisterWrapperListBox"), 30);

		if (registered) {
			selectFromDropdown(By.id("gwt-debug-ActivitiTaskUtils-advisorRegisterWrapperListBox"), "Registriert");
		} else {
			selectFromDropdown(By.id("gwt-debug-ActivitiTaskUtils-advisorRegisterWrapperListBox"), "Nicht registriert");
		}

		return this;
	}

	/**
	 * Internal Approval of the advisor application
	 * 
	 * @param approve
	 * @return {@link FSTasksPage}
	 */
	public FSTasksPage approveAdvisorApplication(boolean approve) {

		waitForElementVisible(By.id("gwt-debug-ActivitiTaskUtils-advisorApprovalWrapperListBox"), 5);

		if (approve) {
			selectFromDropdown(By.id("gwt-debug-ActivitiTaskUtils-advisorApprovalWrapperListBox"), "Bewilligt");
		} else {
			selectFromDropdown(By.id("gwt-debug-ActivitiTaskUtils-advisorApprovalWrapperListBox"), "Nicht bewilligt");
		}
		return this;
	}

	/**
	 * Internal Approvalof the Sale New Business Event
	 * 
	 * @param approve
	 * @return {@link FSTasksPage}
	 */
	public FSTasksPage approveSalesNBE(boolean approve) {

		waitForElementVisible(By.id("gwt-debug-ActivitiTaskUtils-InternalApprove1WrapperCheckBox-input"), 10);

		WebElement elem = webDriver
				.findElement(By.id("gwt-debug-ActivitiTaskUtils-InternalApprove1WrapperCheckBox-input"));

		setCheckboxStatus2(elem, approve);

		return this;
	}

	/**
	 * Click Save button of the task
	 * 
	 * @return {@link FSTasksPage}
	 * @throws InterruptedException
	 */
	public FSTasksPage clickSaveButton() throws InterruptedException {
		clickElement(By.id("gwt-debug-EditTaskView-saveButton"));
		wait(10);
		return this;
	}

	public FSTasksPage clickClearSearchButton() {
		waitForElementVisible(By.id("gwt-debug-TaskOverviewView-clearImg"), 30);
		clickElement(By.id("gwt-debug-TaskOverviewView-clearImg"));
		this.editShowOnlyMyTask(false);
		waitForElementVisible(By.id("gwt-debug-TaskOverviewTable-anchorName"), 10);

		return this;
	}

	/**
	 * Edit the Show only my task checkbox
	 * 
	 * @param show
	 * @return {@link FSTasksPage}
	 */
	public FSTasksPage editShowOnlyMyTask(boolean show) {

		waitForElementVisible(By.id("gwt-debug-TaskOverviewView-showOnlyMyTasks-input"), 30);

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(60, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		wait.until(ExpectedConditions.elementToBeClickable(By.id("gwt-debug-TaskOverviewView-showOnlyMyTasks-input")));

		WebElement elem = webDriver.findElement(By.id("gwt-debug-TaskOverviewView-showOnlyMyTasks-input"));

		setCheckboxStatus2(elem, show);

		return this;
	}
}
