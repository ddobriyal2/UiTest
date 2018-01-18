package org.sly.uitest.sections.crm;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.abstractpage.LoginPage;
import org.sly.uitest.pageobjects.abstractpage.MenuBarPage;
import org.sly.uitest.pageobjects.clientsandaccounts.CRMPage;
import org.sly.uitest.pageobjects.crm.TasksPage;
import org.sly.uitest.settings.Settings;

/**
 * Tests the tasks screen. <br>
 * <br>
 * 
 * @see <a href=
 *      "https://docs.google.com/a/wismore.com/spreadsheets/d/1gbJhwKT9CmbV2onoCOOaI7Xl0CBqbmsMQVbHcR9LcQE/edit#gid=0">
 *      CRM test plan</a>
 * @author Julian Schillinger
 * @date : Jan 3, 2014
 * @company Prive Financial
 */
public class TasksTest extends AbstractTest {

	/**
	 * Tests creation, update, complete and Search for tasks.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testTaskCreateAndEditAndCompleted() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		String taskName1 = "TestTask_" + getRandName();
		String taskName2 = "TestTask_" + getRandName();

		// create one task
		TasksPage task = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToTasksPage().clickCreateTaskButton()
				.editTaskName(taskName1).clickSaveButton();

		assertTrue(pageContainsStr(taskName1));
		scrollToTop();
		// edit this task
		task.editTaskByName(taskName1).editTaskName(taskName2).clickSaveButton();

		assertTrue(pageContainsStr(taskName2));

		// mark it as Completed
		String status = "Completed";
		String date = this.getCurrentTimeInFormat("d-MMM-yyyy");

		task.editTaskByName(taskName2).editTaskStatus(status).editTaskCreateDate(date)
				.editTaskDueDateAndTime(date, "", "").clickSaveButton();

		// make sure it does no longer show up on page
		assertFalse(pageContainsStr(taskName1));
		assertFalse(pageContainsStr(taskName2));

		// completed button
		task.checkIncludeCompletedTasks(true).editTaskByName(taskName2);

		assertTrue(pageContainsStr("Completed"));
		assertTrue(pageContainsStr(taskName2));

	}

	/**
	 * Tests loading the task overview page.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testOpenTaskOverview() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToTasksPage();

		assertTrue(pageContainsStr("Tasks"));

		assertTrue(webDriver.findElement(By.id("gwt-debug-TaskOverviewView-showOnlyMyTasks-input")).isSelected());

		assertTrue(!webDriver.findElement(By.id("gwt-debug-TaskOverviewView-showCompletedTasks-input")).isSelected());

	}

	/**
	 * Tests loading the task overview page and viewing others' tasks.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testOpenTaskOverviewSeeOthersTasks() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		String name = "TestTask" + this.getRandName();
		String oldType = "Super User";
		// String oldName = "Selenium1 Test1";
		String newType = "Admin";
		String newName = "Seleniumtestclerical Seleniumtestclerical";

		TasksPage task = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToTasksPage().clickCreateTaskButton()
				.editTaskName(name);

		String oldName = getTextByLocator(By.id("gwt-debug-EditTaskPresenter-ownerAnchor"));

		task.editRemoveTaskOwner(oldType, oldName).editAddTaskOwner(newType, newName)
				.editRemoveTaskAssignedTo(oldType, oldName).editAddTaskAssignedTo(newType, newName).clickSaveButton();

		clickOkButtonIfVisible();

		task.goToAdvancedSearchPage().searchByTaskName(name).clickSearchButton();

		assertFalse(pageContainsStr(name));

		scrollToTop();

		task.checkShowOnlyMyTasks(false);

		assertTrue(pageContainsStr(name));

	}

	/**
	 * Tests loading the task overview page by link and check only show my tasks
	 * function
	 * 
	 * @throws Exception
	 */
	@Test
	public void testOpenTaskWithoutShowMyTaskBylink() throws Exception {

		setScreenshotSuffix(TasksTest.class.getSimpleName());

		LoginPage main = new LoginPage(webDriver);
		MenuBarPage menu = main.loginAs(Settings.USERNAME, Settings.PASSWORD);
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel"), 60);
		webDriver.navigate().to(baseUrl + "#tasklist;showCompletedTasks=false;showOnlyMyTasks=false");

		handleAlert();

		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel"), 60);

		assertTrue(getRadioButtonOrCheckBoxStatus(By.id("gwt-debug-TaskOverviewView-showOnlyMyTasks-input")) == false);

	}

	@Test
	public void testTaskRegardingToAccount() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		String taskName = "TestTask_0" + getRandName();
		String type = "Client Account";
		String taskAccount = "Generali Vision - NEW (John Doe)";

		TasksPage task = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToTasksPage().clickCreateTaskButton()
				.editTaskName(taskName).editAddTaskRegarding(type, taskAccount).clickSaveButton();

		// check CRM
		CRMPage crm = task.goToAccountOverviewPage().simpleSearchByString("John")
				.goToAccountHoldingsPageByNameHoldingPageNoData("Generali Vision - NEW").goToCRMPage();
		this.waitForElementVisible(By.xpath(".//*[contains(text(),'" + taskName + "')]"), Settings.WAIT_SECONDS);
		assertTrue(pageContainsStr(taskName));

		// mark the task as Completed
		crm.goToTasksPage().editTaskByName(taskName).editTaskStatus("Completed").clickSaveButton()

				// check CRM
				.goToAccountOverviewPage().simpleSearchByString("John")
				.goToAccountHoldingsPageByNameHoldingPageNoData("Generali Vision - NEW").goToCRMPage();
		waitForElementVisible(By.xpath(".//*[@class='generalOverviewSectionTitle' and .='Tasks']"), 30);
		assertFalse(pageContainsStr(taskName));

	}

	@Test
	public void testSearchTasks() throws Exception {

		LoginPage main = new LoginPage(webDriver);

		String name = "TestTask" + this.getRandName();
		String oldType = "Super User";
		// String oldName = "Selenium7 Test7";
		String newType = "Admin";
		String newName = "Seleniumtestclerical Seleniumtestclerical";

		TasksPage task = main.loginAs(Settings.USERNAME, Settings.PASSWORD).goToTasksPage().checkShowOnlyMyTasks(false)
				.checkIncludeCompletedTasks(false).clickCreateTaskButton().editTaskName(name);

		String oldName = getTextByLocator(By.id("gwt-debug-EditTaskPresenter-ownerAnchor"));

		task.editRemoveTaskOwner(oldType, oldName).editAddTaskOwner(newType, newName).clickSaveButton();

		clickOkButtonIfVisible();

		task.goToAdvancedSearchPage().searchByTaskOwner(newName).clickSearchButton();

		assertTrue(pageContainsStr(name));

		int size = this
				.getSizeOfElements(By.xpath(".//*[@id='gwt-debug-TaskOverviewTable-tablePanel']/table/tbody/tr"));

		System.out.print(size);

		for (int i = 2; i < size; i++) {

			assertTrue(this
					.getTextByLocator(By.xpath(
							".//*[@id='gwt-debug-TaskOverviewTable-tablePanel']/table/tbody/tr[" + i + "]/td[2]/div"))
					.equals(newName));
		}

	}

	@Test
	public void testContentsOfRelatedElementsTabEmpty() throws InterruptedException {

		LoginPage main = new LoginPage(webDriver);

		main.loginAs(Settings.CRM_USERNAME, Settings.CRM_PASSWORD).goToTasksPage().goToWorkflowTask("")
				.goToRelatedElementsTab();

		assertTrue(pageContainsStr("No related elements found."));
	}

}
