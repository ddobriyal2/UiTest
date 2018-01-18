package org.sly.uitest.pageobjects.crm;

import static org.junit.Assert.assertTrue;

import java.awt.AWTException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.sly.uitest.pageobjects.abstractpage.AbstractPage;
import org.sly.uitest.pageobjects.abstractpage.AdvancedSearchPage;
import org.sly.uitest.settings.Settings;

/**
 * This class represents the Alerts Page, which can be navigated by clicking
 * 'Manage' -> 'Tasks' or clicking the 'User Alert'image icon
 * 
 * @author Lynne Huang
 * @date : 7 Sep, 2015
 * @company Prive Financial
 */
public class TasksPage extends AbstractPage {

	/**
	 * @param webDriver
	 */
	public TasksPage(WebDriver webDriver) {

		super();
		this.webDriver = webDriver;

		// Waiting 30 seconds for an element to be present on the page,
		// checking for its presence once every 2 seconds
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class);

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gwt-debug-MyMainMaterialView-mainPanel")));

		} catch (Exception ex) {
			// wait.until(ExpectedConditions.visibilityOfElementLocated(By
			// .id("gwt-debug-MyMainClassicView-mainPanel")));

		}

		assertTrue(pageContainsStr(" Tasks "));
	}

	/**
	 * On the Tasks page, enter the string directly into the search box
	 * 
	 * @param string
	 * @return {@link TasksPage}
	 */
	public TasksPage simpleSearchByString(String string) {
		scrollToTop();
		sendKeysToElement(By.id("gwt-debug-TaskOverviewView-searchBox"), string);

		clickElement(By.id("gwt-debug-TaskOverviewView-searchImg"));

		return this;

	}

	/**
	 * On the Tasks page, click the downward triangle to open the
	 * {@link AdvancedSearchPage}
	 * 
	 * @return {@link AdvancedSearchPage}
	 * 
	 * @throws InterruptedException
	 * 
	 */
	public AdvancedSearchPage goToAdvancedSearchPage() throws InterruptedException {

		wait(Settings.WAIT_SECONDS);

		scrollToTop();

		clickElement(By.id("gwt-debug-TaskOverviewView-advancedSearchBtn"));

		return new AdvancedSearchPage(webDriver, TasksPage.class);

	}

	/**
	 * Click the cross icon besides the search box to clear the previous search
	 * 
	 * @return {@link TasksPage}
	 */
	public TasksPage clearSearch() {

		try {

			waitForElementVisible(By.id("gwt-debug-TaskOverviewView-clearImg"), 15);

			if (isElementVisible(By.id("gwt-debug-TaskOverviewView-clearImg"))) {

				clickElement(By.id("gwt-debug-TaskOverviewView-clearImg"));
			}
		} catch (Exception e) {

		}

		return this;
	}

	/**
	 * On the Tasks page, check the checkbox of the Show only my tasks
	 * 
	 * @param shown
	 *            if true, check; if false, uncheck
	 * @return {@link TasksPage}
	 * @throws InterruptedException
	 */
	public TasksPage checkShowOnlyMyTasks(Boolean shown) throws InterruptedException {

		waitForElementVisible(By.id("gwt-debug-TaskOverviewView-showOnlyMyTasks-input"), 300);
		WebElement we = webDriver.findElement(By.id("gwt-debug-TaskOverviewView-showOnlyMyTasks-input"));

		setCheckboxStatus2(we, shown);
		this.waitForWaitingScreenNotVisible();
		waitForElementVisible(By.id("gwt-debug-TaskOverviewTable-anchorName"), 300);
		return this;

	}

	/**
	 * On the Tasks page, check the checkbox of the Include completed tasks
	 * 
	 * @param shown
	 *            if true, check; if false, uncheck
	 * @return {@link TasksPage}
	 */
	public TasksPage checkIncludeCompletedTasks(Boolean included) {

		WebElement we = webDriver.findElement(By.id("gwt-debug-TaskOverviewView-showCompletedTasks-input"));

		setCheckboxStatus2(we, included);

		return this;
	}

	/**
	 * On the Tasks page, click the CREATE TASK button
	 * 
	 * @return {@link TasksPage}
	 * @throws InterruptedException
	 */
	public TasksPage clickCreateTaskButton() throws InterruptedException {
		this.waitForElementVisible(By.id("gwt-debug-TaskOverviewView-createTaskButton"), Settings.WAIT_SECONDS);
		clickElement(By.id("gwt-debug-TaskOverviewView-createTaskButton"));

		return this;
	}

	/**
	 * On the Tasks page, click add binder button
	 * 
	 * @return {@link TasksPage}
	 */
	public TasksPage clickAddBindersButton() {

		waitForElementVisible(By.id("gwt-debug-EditTaskView-addBinderImage"), 10);
		clickElement(By.id("gwt-debug-EditTaskView-addBinderImage"));

		return this;
	}

	/**
	 * On the Tasks page, click the add attachment button
	 * 
	 * @return {@link TasksPage}
	 */
	public TasksPage clickAddAttachmentButton() {

		waitForElementVisible(By.id("gwt-debug-DocumentBinderWidgetView-addDocBinderImage"), 10);
		clickElement(By.id("gwt-debug-DocumentBinderWidgetView-addDocBinderImage"));

		return this;
	}

	/**
	 * On the tasks page, upload file for attachment
	 * 
	 * @return {@link TasksPage}
	 * @throws AWTException
	 * @throws InterruptedException
	 */
	public TasksPage uploadFileForAttachment() throws AWTException, InterruptedException {

		WebElement elem = webDriver.findElement(By.xpath(".//*[@class='gwt-FileUpload']"));

		elem.sendKeys(Settings.FILE_LOCATION);
		wait(Settings.WAIT_SECONDS);
		return this;
	}

	public TasksPage deleteAllBinders() {

		try {
			waitForElementVisible(By.id("gwt-debug-EditTaskPresenter-deleteImage"), 5);

			int size = getSizeOfElements(By.id("gwt-debug-EditTaskPresenter-deleteImage"));

			for (int i = 1; i < size + 1; i++) {
				clickElement(By.xpath("(.//img[@id='gwt-debug-EditTaskPresenter-deleteImage'])[" + i + "]"));
			}

		} catch (TimeoutException e) {

		}

		return this;
	}

	/**
	 * On the tasks page, edit name of binder
	 * 
	 * @param name
	 *            name of the binder
	 * @return {@link TasksPage}
	 */
	public TasksPage editBinderName(String name) {

		sendKeysToElement(By.id("gwt-debug-DocumentBinderWidgetView-nameTextBox"), name);

		return this;
	}

	/**
	 * On the Tasks page, click the task name directly to invoke the Task edit
	 * page
	 * 
	 * @param name
	 *            the task name
	 * @return {@link TasksPage}
	 */
	public TasksPage editTaskByName(String name) {

		waitForElementVisible(By.xpath("//*[contains(text(),'" + name + "')]"), 30);

		clickElement(By.xpath("//*[contains(text(),'" + name + "')]"));

		return this;

	}

	/**
	 * After click the CREATE TASK button, on the popup Task edit page, edit the
	 * Task Name
	 * 
	 * @param name
	 *            the task name
	 * @return {@link TasksPage}
	 */
	public TasksPage editTaskName(String name) {

		sendKeysToElement(By.id("gwt-debug-EditTaskView-taskNameBox"), name);

		return this;
	}

	/**
	 * After click the CREATE TASK button, on the popup Task edit page, add a
	 * task owner
	 * 
	 * @param type
	 * @param owner
	 * 
	 * @return {@link TasksPage}
	 */
	public TasksPage editAddTaskOwner(String type, String owner) {

		clickElement(By.xpath(".//*[@id='gwt-debug-EditTaskView-ownerPanel']//button"));

		selectFromDropdown(By.id("gwt-debug-SelectionUi-typeListBox"), type);

		selectFromDropdown(By.id("gwt-debug-PairedListBoxSelector-sourceList"), owner);

		clickElement(By.id("gwt-debug-PairedListBoxSelector-addImg"));

		clickElement(By.id("gwt-debug-SelectionUi-confirmButton"));

		return this;
	}

	/**
	 * After click the CREATE TASK button, on the popup Task edit page, delete a
	 * task owner
	 * 
	 * @param type
	 * @param owner
	 * 
	 * @return {@link TasksPage}
	 */
	public TasksPage editRemoveTaskOwner(String type, String owner) {

		clickElement(By.xpath(".//*[@id='gwt-debug-EditTaskView-ownerPanel']//button"));

		selectFromDropdown(By.id("gwt-debug-SelectionUi-typeListBox"), type);

		selectFromDropdown(By.id("gwt-debug-PairedListBoxSelector-targetList"), owner);

		clickElement(By.id("gwt-debug-PairedListBoxSelector-removeImg"));

		clickElement(By.id("gwt-debug-SelectionUi-confirmButton"));

		return this;
	}

	/**
	 * After click the CREATE TASK button, on the popup Task edit page, add a
	 * task regarding
	 * 
	 * @param type
	 * @param regarding
	 * 
	 * @return {@link TasksPage}
	 * @throws InterruptedException
	 */
	public TasksPage editAddTaskRegarding(String type, String regarding) throws InterruptedException {

		clickElement(By.xpath(".//*[@id='gwt-debug-EditTaskView-regardingPanel']//button"));
		waitForElementVisible(By.id("gwt-debug-SelectionUi-typeListBox"), 30);
		selectFromDropdown(By.id("gwt-debug-SelectionUi-typeListBox"), type);
		wait(Settings.WAIT_SECONDS);
		selectFromDropdown(By.id("gwt-debug-PairedListBoxSelector-sourceList"), regarding);

		clickElement(By.id("gwt-debug-PairedListBoxSelector-addImg"));

		clickElement(By.id("gwt-debug-SelectionUi-confirmButton"));

		return this;
	}

	/**
	 * After click the CREATE TASK button, on the popup Task edit page, remove
	 * the task regarding
	 * 
	 * @param type
	 * @param regarding
	 * 
	 * @return {@link TasksPage}
	 */
	public TasksPage editRemoveTaskRegarding(String type, String regarding) {

		clickElement(By.xpath(".//*[@id='gwt-debug-EditTaskView-regardingPanel']//button"));

		selectFromDropdown(By.id("gwt-debug-SelectionUi-typeListBox"), type);

		selectFromDropdown(By.id("gwt-debug-PairedListBoxSelector-targetList"), regarding);

		clickElement(By.id("gwt-debug-PairedListBoxSelector-removeImg"));

		clickElement(By.id("gwt-debug-SelectionUi-confirmButton"));

		return this;
	}

	/**
	 * After click the CREATE TASK button, on the popup Task edit page, add a
	 * person to assign the task to
	 * 
	 * @param type
	 * @param assignee
	 * 
	 * @return {@link TasksPage}
	 */
	public TasksPage editAddTaskAssignedTo(String type, String assignee) {

		clickElement(By.xpath(".//*[@id='gwt-debug-EditTaskView-assignedToPanel']//button"));

		selectFromDropdown(By.id("gwt-debug-SelectionUi-typeListBox"), type);

		selectFromDropdown(By.id("gwt-debug-PairedListBoxSelector-sourceList"), assignee);

		clickElement(By.id("gwt-debug-PairedListBoxSelector-addImg"));

		clickElement(By.id("gwt-debug-SelectionUi-confirmButton"));

		return this;
	}

	/**
	 * After click the CREATE TASK button, on the popup Task edit page, remove
	 * the person to assign the task to
	 * 
	 * @param type
	 * @param assignee
	 * 
	 * @return {@link TasksPage}
	 */
	public TasksPage editRemoveTaskAssignedTo(String type, String assignee) {

		clickElement(By.xpath(".//*[@id='gwt-debug-EditTaskView-assignedToPanel']//button"));

		selectFromDropdown(By.id("gwt-debug-SelectionUi-typeListBox"), type);

		selectFromDropdown(By.id("gwt-debug-PairedListBoxSelector-targetList"), assignee);

		clickElement(By.id("gwt-debug-PairedListBoxSelector-removeImg"));

		clickElement(By.id("gwt-debug-SelectionUi-confirmButton"));

		return this;
	}

	/**
	 * After click the CREATE TASK button, on the popup Task edit page, edit the
	 * create date
	 * 
	 * @param date
	 * 
	 * @return {@link TasksPage}
	 */
	public TasksPage editTaskCreateDate(String date) {

		sendKeysToElement(By.id("gwt-debug-EditTaskView-startTimestamp"), date);

		return this;
	}

	/**
	 * After click the CREATE TASK button, on the popup Task edit page, edit the
	 * task Due Date/Time
	 * 
	 * @param date
	 * @param hour
	 * @param minute
	 * 
	 * @return {@link TasksPage}
	 */
	public TasksPage editTaskDueDateAndTime(String date, String hour, String minute) {

		if (!date.equals("")) {
			sendKeysToElement(By.id("gwt-debug-EditTaskView-endTimestamp"), date);
		}

		if (!hour.equals("")) {
			sendKeysToElement(By.id("gwt-debug-EditTaskView-endHour"), hour);
		}

		if (!minute.equals("")) {
			sendKeysToElement(By.id("gwt-debug-EditTaskView-endMinute"), minute);
		}

		return this;
	}

	/**
	 * After click the CREATE TASK button, on the popup Task edit page, edit the
	 * task status
	 * 
	 * @param status
	 * 
	 * @return {@link TasksPage}
	 */
	public TasksPage editTaskStatus(String status) {

		selectFromDropdown(By.id("gwt-debug-EditTaskView-statusListBox"), status);

		return this;
	}

	/**
	 * After click the CREATE TASK button, on the popup Task edit page, edit the
	 * task notes
	 * 
	 * @param notes
	 * 
	 * @return {@link TasksPage}
	 */
	public TasksPage editTaskNotes(String notes) {

		sendKeysToElement(By.id("gwt-debug-EditTaskView-descArea"), notes);

		return this;
	}

	/**
	 * After click the CREATE TASK button, on the popup Task edit page, edit the
	 * email magnets
	 * 
	 * 
	 * @return {@link TasksPage}
	 */
	public TasksPage editEmailMagnets() {

		clickElement(By.id("gwt-debug-EditTaskView-addNewDocMagButton"));

		clickElement(By.id("gwt-debug-AddDocumentMagnetView-generateBtn"));

		clickElement(By.id("gwt-debug-AddDocumentMagnetView-submitBtn"));

		return this;

	}

	/**
	 * After click the CREATE TASK button, on the popup Task edit page, click
	 * the SAVE button
	 * 
	 * @return {@link TasksPage}
	 */
	public TasksPage clickSaveButton() {

		clickElement(By.id("gwt-debug-EditTaskView-saveButton"));

		return this;
	}

	/**
	 * After click the CREATE TASK button, on the popup Task edit page, switch
	 * to the Related Elements tab
	 * 
	 * 
	 * @return {@link TasksPage}
	 */
	public TasksPage goToRelatedElementsTab() {

		clickElement(By.xpath("//div[@class='gwt-HTML' and .='Related Elements']"));

		return this;
	}

	/**
	 * On the Tasks page, choose a workflow task
	 * 
	 * @return {@link TasksPage}
	 */
	public TasksPage goToWorkflowTask(String task) {

		if (task.equals("")) {

			clickElement(By.xpath("//td[img[@alt='Workflow Task']]/following-sibling::td[1]/a"));

		} else {

			clickElement(By.xpath("//a[.='" + task + "']"));

		}

		return this;
	}

	public TasksPage editApproveTransaction(String decision) {
		selectFromDropdown(By.id("gwt-debug-ActivitiTaskUtils-approvedWrapperListBox"), decision);
		return this;
	}
}
