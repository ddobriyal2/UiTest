package org.sly.uitest.pageobjects.abstractpage;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.sly.uitest.framework.AbstractTest;
import org.sly.uitest.pageobjects.admin.AdminEditPage;
import org.sly.uitest.pageobjects.admin.AdminMaintenancePage;
import org.sly.uitest.pageobjects.admin.AdminReportingPage;
import org.sly.uitest.pageobjects.admin.AdminSearchPage;
import org.sly.uitest.pageobjects.alerts.AlertsPage;
import org.sly.uitest.pageobjects.assetmanagement.MarketEquitiesPage;
import org.sly.uitest.pageobjects.assetmanagement.MarketRatesPage;
import org.sly.uitest.pageobjects.assetmanagement.ModelPortfoliosPage;
import org.sly.uitest.pageobjects.assetmanagement.RebalancingsPage;
import org.sly.uitest.pageobjects.assetmanagement.StrategyRulesPage;
import org.sly.uitest.pageobjects.assetmanagement.StructuredProductPage;
import org.sly.uitest.pageobjects.assetmanagement.VFundsPage;
import org.sly.uitest.pageobjects.clientsandaccounts.AccountOverviewPage;
import org.sly.uitest.pageobjects.clientsandaccounts.AccountsPage;
import org.sly.uitest.pageobjects.clientsandaccounts.AnalysisPage;
import org.sly.uitest.pageobjects.clientsandaccounts.CRMPage;
import org.sly.uitest.pageobjects.clientsandaccounts.CashflowPage;
import org.sly.uitest.pageobjects.clientsandaccounts.ClientOverviewPage;
import org.sly.uitest.pageobjects.clientsandaccounts.ComparePage;
import org.sly.uitest.pageobjects.clientsandaccounts.DetailPage;
import org.sly.uitest.pageobjects.clientsandaccounts.HistoryPage;
import org.sly.uitest.pageobjects.clientsandaccounts.HoldingsPage;
import org.sly.uitest.pageobjects.clientsandaccounts.RecentOrdersPage;
import org.sly.uitest.pageobjects.clientsandaccounts.ReportPage;
import org.sly.uitest.pageobjects.clientsandaccounts.RiskProfilePage;
import org.sly.uitest.pageobjects.commissioning.CashflowOverviewPage;
import org.sly.uitest.pageobjects.commissioning.FeeOverviewPage;
import org.sly.uitest.pageobjects.commissioning.IndemnityReleasesPage;
import org.sly.uitest.pageobjects.commissioning.NewBusinessEventPage;
import org.sly.uitest.pageobjects.commissioning.TrailFeeAgreementPage;
import org.sly.uitest.pageobjects.companyadmin.AdvisorPerformancePage;
import org.sly.uitest.pageobjects.companyadmin.CompanySettingsPage;
import org.sly.uitest.pageobjects.companyadmin.CustomAssetPage;
import org.sly.uitest.pageobjects.companyadmin.CustomFieldPage;
import org.sly.uitest.pageobjects.companyadmin.CustomTagPage;
import org.sly.uitest.pageobjects.companyadmin.DashboardPage;
import org.sly.uitest.pageobjects.companyadmin.EmailTemplatePage;
import org.sly.uitest.pageobjects.companyadmin.EmployeePage;
import org.sly.uitest.pageobjects.companyadmin.WhiteListsPage;
import org.sly.uitest.pageobjects.crm.BulkEmailPage;
import org.sly.uitest.pageobjects.crm.CalendarPage;
import org.sly.uitest.pageobjects.crm.DocumentsPage;
import org.sly.uitest.pageobjects.crm.NotesPage;
import org.sly.uitest.pageobjects.crm.OpportunitiesPage;
import org.sly.uitest.pageobjects.crm.TasksPage;
import org.sly.uitest.pageobjects.investments.InvestmentsPage;
import org.sly.uitest.pageobjects.mysettings.PayFeePage;
import org.sly.uitest.pageobjects.mysettings.UserPreferencePage;
import org.sly.uitest.pageobjects.mysettings.UserProfilePage;
import org.sly.uitest.pageobjects.operations.AccountValueSpikesPage;
import org.sly.uitest.pageobjects.operations.DataReconciliationPage;
import org.sly.uitest.pageobjects.operations.LastAccountValueUpdatePage;
import org.sly.uitest.pageobjects.operations.ProductMaintenancePage;
import org.sly.uitest.pageobjects.operations.ReportingOverviewPage;
import org.sly.uitest.pageobjects.operations.UserActivityPage;
import org.sly.uitest.pageobjects.planning.IllustrationPage;
import org.sly.uitest.pageobjects.workflow.ProcessTasksPage;
import org.sly.uitest.pageobjects.workflow.ProcessesPage;
import org.sly.uitest.pageobjects.workflow.WorkflowOverviewPage;
import org.sly.uitest.settings.Settings;

/**
 * This class includes all the page navigation method, which extends
 * AbstractTest, and is extended by all other classes
 * 
 * @author Lynne Huang
 * @date : 11 Aug, 2015
 * @company Prive Financial
 */
public class AbstractPage extends AbstractTest {

	/**
	 * Navigate to Home Page by clicking 'Home' from the breadcrumb panel
	 * 
	 * @return MenuBarPage
	 */
	public MenuBarPage goToHomePage() {

		clickElement(By.id("gwt-debug-Breadcrumb-home-128"));

		return new MenuBarPage(webDriver);
	}

	
	/**
	 * In classic view, Navigate to the UserProfile Page by clicking 'User
	 * Settings' -> 'User Profile'
	 * 
	 * In material view, Navigate to the UserProfile Page by clicking 'My
	 * Settings' -> 'User Profile' Profile'.
	 * 
	 * @return {@link UserProfilePage}
	 */
	public UserProfilePage goToUserProfilePage() throws InterruptedException {
		scrollToTop();
		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			wait(1);
			if (isElementVisible(By.xpath(
					".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='My Settings']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
				navigateToPage(By
						.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='User Profile']"));
			} else {
				navigateToPageMaterialView(
						By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='My Settings']"),
						By.xpath(
								".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='User Profile']"));
			}
		} else {
			clickElement(By.xpath("//a[@id='gwt-debug-ImageMenuButton-displayImageLink' and @title='User Settings']"));

			assertTrue(pageContainsStr("User Profile"));
			assertTrue(pageContainsStr("log out"));

			clickElement(By.id("gwt-debug-ImageMenuButton-UserProfile"));

		}

		return new UserProfilePage(webDriver);
	}

	/**
	 * In classic view Navigate to the UserPreference Page by clicking 'User
	 * Settings' -> 'User Preference'.
	 * 
	 * In material view, Navigate to the UserProfile Page by clicking 'My
	 * Settings' -> 'User Preference'
	 * 
	 * @param returnClass
	 *            after submit the changes for User Preference, the page will be
	 *            redirected to Homepage (returnClass)
	 * @return UserPreferencePage
	 */
	public UserPreferencePage goToUserPreferencePage(Class<?> returnClass) throws InterruptedException {
		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			waitForElementVisible(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='My Settings']"),
					10);
			if (isElementVisible(By.xpath(
					".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='My Settings']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
				navigateToPage(By.xpath(
						".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='User Preference']"));
			} else {
				navigateToPageMaterialView(
						By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='My Settings']"),
						By.xpath(
								".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='User Preference']"));
			}
		} else {
			scrollToTop();
			clickElement(By.xpath("//a[@id='gwt-debug-ImageMenuButton-displayImageLink' and @title='User Settings']"));
			waitForElementVisible(By.id("gwt-debug-ImageMenuButton-UserPreference"), 30);
			assertTrue(pageContainsStr("User Preference"));
			assertTrue(pageContainsStr("log out"));

			clickElement(By.id("gwt-debug-ImageMenuButton-UserPreference"));
		}

		return new UserPreferencePage(webDriver, returnClass);
	}

	/**
	 * In classic view, Navigate to the VFunds Page by clicking 'User Settings'
	 * -> 'Personal vFund' In material view, Navigate to the VFunds Page by
	 * clicking 'My Settings' -> 'Personal vFund'
	 * 
	 * @throws InterruptedException
	 * 
	 */
	public VFundsPage goToPersonalvFundPage() throws InterruptedException {
		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			if (isElementVisible(By.xpath(
					".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='My Settings']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
				navigateToPage(By.xpath(
						".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Personal vFund']"));
			} else {
				navigateToPageMaterialView(
						By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='My Settings']"),
						By.xpath(
								".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Personal vFund']"));
			}
		} else {
			navigateToPage(
					By.xpath("//*[@id=\"gwt-debug-ImageMenuButton-displayImageLink\" and @title=\"User Settings\"]"));

			// personal vFund screen

			clickElement(By.xpath("//*[@id=\"gwt-debug-ImageMenuButton-PersonalvFund\"]"));
		}

		return new VFundsPage(webDriver);
	}

	/**
	 * It will pop up the financial advisor information after clicking Your
	 * 'Financial Advisor' icon.
	 * 
	 * @throws InterruptedException
	 * 
	 */
	public MenuBarPage goToYourFinancialAdvisor() throws InterruptedException {
		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			clickElement(By.xpath(".//*[@class='dropdown-toggle count-info' and @ title='Contact Info']/i"));
		} else {
			clickElement(By.xpath(
					"//a[@id='gwt-debug-ImageMenuButton-displayImageLink' and @title='Your Financial Advisor']"));
		}

		return new MenuBarPage(webDriver);
	}

	/**
	 * In classic view Navigate To PayFee Page by clicking 'User Settings' ->
	 * 'Pay Fee'. In material view Navigate To PayFee Page by clicking 'My
	 * Settings' -> 'Pay Fee'.
	 * 
	 * @throws InterruptedException
	 * 
	 */
	public PayFeePage goToPayFeePage() throws InterruptedException {
		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			if (isElementVisible(By.xpath(
					".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='My Settings']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
				navigateToPage(
						By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Pay Fees']"));
			} else {
				navigateToPageMaterialView(
						By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='My Settings']"),
						By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Pay Fees']"));
			}
		} else {
			navigateToPage(
					By.xpath("//*[@id=\"gwt-debug-ImageMenuButton-displayImageLink\" and @title=\"User Settings\"]"));

			// pay fees screen
			clickElement(By.xpath("//*[@id=\"gwt-debug-ImageMenuButton-PayFees\"]"));
		}

		return new PayFeePage(webDriver);
	}

	/**
	 * Navigate to CompanySettings Page by clicking 'Company Settings' ->
	 * 'Company Settings'. or using navigation in material view
	 * 
	 * @return CompanySettingsPage
	 * @throws InterruptedException
	 */
	public CompanySettingsPage goToCompanySettingsPage() throws InterruptedException {
		Boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			this.goToCompanySettingsPageMaterialView();
		} else {
			wait(Settings.WAIT_SECONDS);
			clickElement(
					By.xpath("//a[@id='gwt-debug-ImageMenuButton-displayImageLink' and @title='Company Settings']"));

			assertTrue(pageContainsStr("Company Settings"));
			wait(Settings.WAIT_SECONDS);
			clickElement(By.id("gwt-debug-ImageMenuButton-CompanySettings"));
		}

		return new CompanySettingsPage(webDriver);

	}

	/**
	 * Navigate to Dashboard Page by clicking 'Company Settings' -> 'Dashboard'.
	 * or using navigation in material view
	 * 
	 * @return DashboardPage
	 * @throws InterruptedException
	 */
	public DashboardPage goToDashboardPage() throws InterruptedException {
		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			this.goToDashboardPageMaterialView();
		} else {
			clickElement(
					By.xpath("//a[@id='gwt-debug-ImageMenuButton-displayImageLink' and @title='Company Settings']"));

			assertTrue(pageContainsStr("Dashboard"));

			clickElement(By.id("gwt-debug-ImageMenuButton-Dashboard"));
		}

		return new DashboardPage(webDriver);

	}

	/**
	 * Navigate to CustomField Page by clicking 'Company Settings' -> 'Custom
	 * Field'. or using navigation in material view
	 * 
	 * @return CustomFiledPage
	 * @throws InterruptedException
	 */
	public CustomFieldPage goToCustomFieldPage() throws InterruptedException {
		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			goToCustomFieldPageMaterialView();
		} else {
			clickElement(
					By.xpath("//a[@id='gwt-debug-ImageMenuButton-displayImageLink' and @title='Company Settings']"));

			assertTrue(pageContainsStr("Custom Field"));

			clickElement(By.id("gwt-debug-ImageMenuButton-CustomField"));
		}

		return new CustomFieldPage(webDriver);

	}

	/**
	 * Navigate to EmailTemplateOverview Page by clicking 'Company Settings' ->
	 * 'Email Template'. or using navigation in material view
	 * 
	 * @return EmailTemplatePage
	 */
	public EmailTemplatePage goToEmailTemplatePage() throws InterruptedException {
		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			this.goToEmailTemplatePageMaterialView();
		} else {
			navigateToPage(By.xpath("//a[@title='Company Settings']/img"));

			assertTrue(pageContainsStr("Email Template"));

			navigateToPage(By.id("gwt-debug-ImageMenuButton-EmailTemplate"));
		}

		return new EmailTemplatePage(webDriver);
	}

	/**
	 * Navigate to CustomAsset Page by clicking 'Company Settings' -> 'Custom
	 * Asset'. or using navigation in material view
	 * 
	 * @return go to the Custom Asset Page via the main menu button
	 */
	public CustomAssetPage goToCustomAssetPage() throws InterruptedException {
		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			this.goToCustomAssetPageMaterialView();
		} else {
			wait(Settings.WAIT_SECONDS);
			navigateToPage(By.xpath("//a[@title='Company Settings']/img"));

			assertTrue(pageContainsStr("Custom Asset"));

			navigateToPage(By.id("gwt-debug-ImageMenuButton-CustomAsset"));
		}

		return new CustomAssetPage(webDriver);

	}

	/**
	 * Navigate to CustomTag Page by clicking 'Company Settings' -> 'Custom
	 * Tag'. or using navigation in material view
	 * 
	 * @return go to the Custom Tag Page via the main menu button
	 */
	public CustomTagPage goToCustomTagPage() throws InterruptedException {
		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			this.goToCustomTagPageMaterialView();
		} else {
			navigateToPage(By.xpath("//a[@title='Company Settings']/img"));

			assertTrue(pageContainsStr("Custom Tag"));

			navigateToPage(By.id("gwt-debug-ImageMenuButton-CustomTag"));
		}

		return new CustomTagPage(webDriver);

	}

	/**
	 * Navigate to Alerts Page by clicking' User Alert'.
	 * 
	 * @return go to the User Alert Page
	 */
	public AlertsPage goToUserAlertPage() throws InterruptedException {
		scrollToTop();
		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			navigateToPage(By.xpath("//a[@title='Alerts']/span"));
		} else {
			navigateToPage(By.xpath("//a[@title='User Alert']/img"));
		}
		return new AlertsPage(webDriver);

	}

	/**
	 * It will pop up the updated information of processes after clicking
	 * 'Processes' icon, including generating reports.
	 * 
	 * @throws InterruptedException
	 * 
	 */
	public MenuBarPage goToProcess() throws InterruptedException {

		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			clickElement(By.xpath("//*[@class='dropdown-toggle count-info' and @title='Processes']"));
		} else {
			clickElement(By
					.xpath("//*[@id=\"gwt-debug-ProcessImageMenuButton-displayImageLink\" and @title=\"Processes\"]"));

		}

		return new MenuBarPage(webDriver);

	}

	/**
	 * It will pop up the language list of processes after clicking 'Language'
	 * icon, including generating reports.
	 * 
	 * @throws InterruptedException
	 * 
	 */
	public MenuBarPage goToLanguage() throws InterruptedException {

		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			clickElement(By.xpath("//*[@class='dropdown-toggle count-info' and @title='Language']"));
		} else {
			clickElement(
					By.xpath("//*[@id=\"gwt-debug-ProcessImageMenuButton-displayImageLink\" and @title=\"Language\"]"));

		}

		return new MenuBarPage(webDriver);

	}

	/**
	 * It will pop up the contact information after clicking 'Contact' icon,
	 * including generating reports.
	 * 
	 * @throws InterruptedException
	 * 
	 */
	public MenuBarPage goToContact() throws InterruptedException {
		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			clickElement(By.xpath("//*[@class='dropdown-toggle count-info' and @title='Contact Info']"));
		} else {
			clickElement(By.xpath(
					"//*[@id=\"gwt-debug-ProcessImageMenuButton-displayImageLink\" and @title=\"Contact Info\"]"));

		}

		return new MenuBarPage(webDriver);
	}

	public MenuBarPage goToSideManager() {

		clickElement(By.xpath("//*[@class='right-sidebar-toggle' and @title='Sidebar Manager']"));

		return new MenuBarPage(webDriver);
	}

	/**
	 * Navigate to the Client Overview Page by clicking 'Clients' directly on
	 * the menu bar
	 * 
	 * @return go to Client Overview Page
	 */
	public ClientOverviewPage goToClientsDEFAULTPage() throws InterruptedException {
		wait(Settings.WAIT_SECONDS);
		clickElement(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Clients"));

		return new ClientOverviewPage(webDriver);

	}

	/**
	 * Navigate to the Account Overview page by clicking 'Accounts' directly on
	 * the menu bar
	 * 
	 * @return go to Account Overview Page
	 */
	public AccountOverviewPage goToAccountsDEFAULTPage() throws InterruptedException {
		wait(Settings.WAIT_SECONDS);
		clickElement(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Accounts"));
		wait(2 * Settings.WAIT_SECONDS);
		return new AccountOverviewPage(webDriver);

	}

	/**
	 * Navigate to the Account Overview Page by clicking 'Clients' -> 'Accounts'
	 * in most cases; if the menu bar does not have 'Clients' but 'Accounts',
	 * the page will be navigated by Accounts -> Accounts
	 * 
	 * @return go to Account Overview Page
	 */
	public AccountOverviewPage goToAccountOverviewPage() throws InterruptedException {
		scrollToTop();
		if (!isElementVisible(By.id("gwt-debug-InvestorAccountOverviewView-tablePanel"))) {
			boolean isMaterial = checkIfMaterial();
			if (isMaterial) {
				this.goToAccountOverviewPageMaterialView();
			} else {
				try {
					navigateToPage(By.xpath(".//*[@id='gwt-debug-MenuButtonHorizontal-menuButtonTop-Accounts']"),
							By.id("gwt-debug-MenuButtonHorizontal-AccountOverview"));
				} catch (org.openqa.selenium.TimeoutException e) {
					navigateToPage(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Clients"),
							By.id("gwt-debug-MenuButtonHorizontal-AccountOverview"));
				}
			}

		}

		return new AccountOverviewPage(webDriver);

	}

	/**
	 * Navigate to Client Overview Page by clicking 'Clients' -> 'Clients'
	 * 
	 * @return go to Client Overview Page
	 */
	public ClientOverviewPage goToClientOverviewPage() throws InterruptedException {
		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			this.goToClientOverviewPageMaterialView();
		} else {
			navigateToPage(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Clients"),
					By.id("gwt-debug-MenuButtonHorizontal-ClientOverview"));
		}

		return new ClientOverviewPage(webDriver);

	}

	/**
	 * Navigate to Opportunities Page by clicking 'Clients' -> 'Opportunities'
	 * or use the navigation in material view
	 * 
	 * @return go to OpportunitiesPage
	 */
	public OpportunitiesPage goToOpportunitiesPage() throws InterruptedException {
		Boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			this.goToOpportunitiesPageMaterialView();
		} else {
			navigateToPage(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Clients"),
					By.id("gwt-debug-MenuButtonHorizontal-Opportunities"));
		}

		return new OpportunitiesPage(webDriver);

	}

	/**
	 * Navigate to New Business Page by clicking 'Clients' -> 'New Business'
	 * 
	 * @return go to the New Business Page via the main menu button
	 */
	public NewBusinessEventPage goToNewBusinessPage() throws InterruptedException {
		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			this.goToNewBusinessPageMaterialView();
		} else {
			navigateToPage(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Clients"),
					By.id("gwt-debug-MenuButtonHorizontal-NewBusiness"));

		}
		scrollToTop();
		return new NewBusinessEventPage(webDriver);

	}

	/**
	 * Navigate to Investments Page by clicking 'Explore' directly in the menu
	 * bar
	 * 
	 * @return InvestmentPage
	 */
	public InvestmentsPage goToExploreDEFAULTPage() throws InterruptedException {

		clickElement(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Explore"));

		return new InvestmentsPage(webDriver);
	}

	/**
	 * Navigate to Investments Page by clicking 'Explore' -> 'Investments'
	 * 
	 * @return InvestmentPage
	 */
	public InvestmentsPage goToInvestmentsPage() throws InterruptedException {
		scrollToTop();
		boolean isMaterial = checkIfMaterial();
		if (!isMaterial) {
			wait(Settings.WAIT_SECONDS);
			waitForElementVisible(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Explore"), 30);
			navigateToPage(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Explore"),
					By.id("gwt-debug-MenuButtonHorizontal-Investments"));

		} else {
			wait(1);
			clickElement(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Investments']"));
		}
		this.waitForWaitingScreenNotVisible();
		return new InvestmentsPage(webDriver);

	}

	/**
	 * Navigate to Model Portfolios Page by clicking 'Explore' -> 'Model
	 * Portfolio' or use the navigation in material view
	 * 
	 * @return go to the Model Portfolios Page via the main menu button......
	 *         FROM EXPLORE FOR ADVISOR
	 */
	public ModelPortfoliosPage goToModelPortfoliosPage() throws InterruptedException {
		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			this.goToModelPortfoliosPageMaterialView();

		} else {
			waitForElementVisible(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Explore"), 30);
			navigateToPage(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Explore"),
					By.id("gwt-debug-MenuButtonHorizontal-ModelPortfolios"));
		}

		return new ModelPortfoliosPage(webDriver);

	}

	/**
	 * Navigate to Risk Profile Page by clicking 'Plan' directly
	 * 
	 * @return RiskProfilePage
	 * @throws InterruptedException
	 * 
	 */
	public RiskProfilePage goToPlanDEFAULTPage() throws InterruptedException {

		navigateToPage(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Plan"));

		return new RiskProfilePage(webDriver);
	}

	/**
	 * Navigate to Risk Profile Page by clicking 'Plan' -> 'RiskProfile'
	 * 
	 * @return RiskProfilePage
	 * @throws InterruptedException
	 * 
	 */
	public RiskProfilePage goToRiskProfilePage() throws InterruptedException {

		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			this.goToRiskProfilePageMaterialView();
		} else {
			navigateToPage(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Plan"),
					By.id("gwt-debug-MenuButtonHorizontal-RiskProfile"));
		}

		return new RiskProfilePage(webDriver);
	}

	/**
	 * Navigate to Tasks page by clicking 'Manage' directly
	 * 
	 * @return TasksPage
	 */
	public TasksPage goToManageDEFAULTPage() throws InterruptedException {

		navigateToPage(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Manage"));

		return new TasksPage(webDriver);

	}

	/**
	 * Navigate to TasksPage by clicking 'Manage' -> 'Tasks' or use the
	 * navigation in material view
	 * 
	 * @return TasksPage
	 */
	public TasksPage goToTasksPage() throws InterruptedException {
		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			this.goToTasksPageMaterialView();
		} else {
			scrollToTop();
			navigateToPage(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Manage"),
					By.id("gwt-debug-MenuButtonHorizontal-Tasks"));

		}

		return new TasksPage(webDriver);
	}

	/**
	 * Navigate to Calendar Page by clicking 'Manage' -> 'Calendar' or using the
	 * navigation in material view
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public CalendarPage goToCalendarPage() throws InterruptedException {
		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			this.goToCalendarPageMaterialView();
		} else {
			scrollToTop();
			navigateToPage(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Manage"),
					By.id("gwt-debug-MenuButtonHorizontal-Calendar"));
		}
		return new CalendarPage(webDriver);
	}

	public AdvisorPerformancePage goToAdvisorPerformancePage() throws InterruptedException {
		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			this.goToAdvisorPerformancePageMaterialView();
		} else {
			navigateToPage(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Manage"),
					By.id("gwt-debug-MenuButtonHorizontal-AdvisorPerformance"));
		}

		return new AdvisorPerformancePage(webDriver);
	}

	/**
	 * Navigate to Documents Page by clicking 'Manage' -> 'Documents'
	 * 
	 * @return DocumentsPage
	 * @throws InterruptedException
	 * 
	 */
	public DocumentsPage goToDocumentsPage() throws InterruptedException {

		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			this.goToDocumentsPageMaterialView();
		} else {

			navigateToPage(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Manage"),
					By.id("gwt-debug-MenuButtonHorizontal-Documents"));

		}

		return new DocumentsPage(webDriver);
	}

	/**
	 * Navigate to WhiteLists Page by clicking 'Manage' -> 'WhiteLists'
	 * 
	 * @return WhiteListsPage
	 */
	public WhiteListsPage goToWhiteListsPage() throws InterruptedException {

		wait(3);

		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			this.goToWhiteListPageMaterialView();
		} else {
			navigateToPage(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Manage"),
					By.id("gwt-debug-MenuButtonHorizontal-WhiteLists"));
		}

		return new WhiteListsPage(webDriver);

	}

	/**
	 * Navigate Employee Page by clicking 'Manage' -> 'Employee'
	 * 
	 * @return {@link EmployeePage}
	 * @throws InterruptedException
	 * 
	 */
	public EmployeePage goToEmployeePage() throws InterruptedException {
		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			this.goToEmployeePageMaterialView();
		} else {
			navigateToPage(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Manage"),
					By.id("gwt-debug-MenuButtonHorizontal-Employee"));
		}

		return new EmployeePage(webDriver);
	}

	/**
	 * Navigate to Alerts Page by clicking 'Manage' -> 'Alerts' or ,in material
	 * view, click the Alert icon
	 * 
	 * @return {@link AlertsPage}
	 */
	public AlertsPage goToAlertsPageFromManage() throws InterruptedException {

		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			clickElement(By.xpath("//*[@class='dropdown-toggle count-info' and @title='Alerts']"));
		} else {
			navigateToPage(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Manage"),
					By.id("gwt-debug-MenuButtonHorizontal-Alerts"));
		}

		return new AlertsPage(webDriver);
	}

	/**
	 * Navigate to Rebalancings Page by clicking 'Manage' -> 'Notes'
	 * 
	 * @return {@link RebalancingsPage}
	 * @throws InterruptedException
	 */
	public RebalancingsPage goToRebalancingsPage() throws InterruptedException {

		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			this.goToRebalancingsPageMaterailView();
		} else {
			navigateToPage(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Manage"),
					By.id("gwt-debug-MenuButtonHorizontal-Rebalancings"));
		}

		return new RebalancingsPage(webDriver);
	}

	public NotesPage goToNotesPage() throws InterruptedException {

		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			this.goToNotesPageMaterialView();
		} else {
			navigateToPage(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Manage"),
					By.id("gwt-debug-MenuButtonHorizontal-Notes"));
		}

		return new NotesPage(webDriver);
	}

	/**
	 * Navigate to Process Tasks Page by clicking 'Workflow' directly
	 * 
	 * @return ProcessTasksPage
	 * @throws InterruptedException
	 * 
	 */
	public ProcessTasksPage goToWorkflowDEFAULTPage() throws InterruptedException {

		navigateToPage(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Workflow"));

		clickOkButtonIfVisible();

		return new ProcessTasksPage(webDriver);
	}

	/**
	 * Navigate to ProcessTasksPage by clicking 'Workflow' -> Process Tasks in
	 * both classic or material view
	 * 
	 * @return {@link ProcessTasksPage}
	 * @throws InterruptedException
	 */
	public ProcessTasksPage goToProcessTasksPage() throws InterruptedException {
		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			this.goToProcessTasksPageMaterialView();
		} else {
			navigateToPage(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Workflow"),
					By.id("gwt-debug-MenuButtonHorizontal-Process Tasks"));
		}

		return new ProcessTasksPage(webDriver);
	}

	/**
	 * Navigate to ProcessesPage by clicking 'Workflow' -> Processes in both
	 * classic or material view
	 * 
	 * @return {@link ProcessesPage}
	 * @throws InterruptedException
	 */
	public ProcessesPage goToProcessesPage() throws InterruptedException {
		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			this.goToProcessesPageMaterialView();
		} else {
			navigateToPage(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Workflow"),
					By.id("gwt-debug-MenuButtonHorizontal-Processes"));
		}

		return new ProcessesPage(webDriver);
	}

	/**
	 * Navigate to WorkflowOverviewPage by clicking 'Workflow' -> Workflow
	 * Overview in both classic or material view
	 * 
	 * @return {@link WorkflowOverviewPage}
	 * @throws InterruptedException
	 */
	public WorkflowOverviewPage goToWorkflowOverviewPage() throws InterruptedException {
		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			this.goToWorkflowOverviewPageMaterialView();
		} else {
			navigateToPage(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Workflow"),
					By.id("gwt-debug-MenuButtonHorizontal-WorkflowOverview"));
		}

		return new WorkflowOverviewPage(webDriver);
	}

	/**
	 * Navitage to Client Fees Page by clicking 'Accounting' directly
	 * 
	 * @return go to the Client Fees Page via the main menu button
	 */
	public FeeOverviewPage goToAccountingDEFAULTPage() throws InterruptedException {

		clickElement(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Accounting"));

		return new FeeOverviewPage(webDriver, "Client Fees");

	}

	/**
	 * Navigate to Advisor Commissions Page by clicking 'Accounting' directly
	 * when login as Consultant
	 * 
	 * @return go to the Client Fees Page via the main menu button
	 */
	public FeeOverviewPage goToAccountingDEFAULTPageAsConsultant() throws InterruptedException {

		clickElement(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Accounting"));

		return new FeeOverviewPage(webDriver, "Advisor Commissions");

	}

	/**
	 * Navigate to Client Fees Page by clicking 'Accounting' -> 'Client Fees' in
	 * classic view or 'Commissioning' -> 'Client Fees' in material view
	 * 
	 * @return go to the Client Fees Page via the main menu button
	 */
	public FeeOverviewPage goToClientFeesPage() throws InterruptedException {

		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			this.goToFeeOverivewMaterialView("Client Fees");
		} else {
			navigateToPage(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Accounting"),
					By.id("gwt-debug-MenuButtonHorizontal-ClientFees"));
		}

		return new FeeOverviewPage(webDriver, "Client Fees");

	}

	/**
	 * Navigate to Advisor Commissions Fee Page by clicking 'Accounting' ->
	 * 'Advisor Commissions' in classic view or 'Commissioning' -> 'Advisor
	 * Commissions' in material view
	 * 
	 * @return go to the Advisor Commission Page via the main menu button
	 */
	public FeeOverviewPage goToAdvisorCommissionPage() throws InterruptedException {

		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			this.goToFeeOverivewMaterialView("Advisor Commissions");
		} else {
			navigateToPage(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Accounting"),
					By.id("gwt-debug-MenuButtonHorizontal-AdvisorCommissions"));
		}

		return new FeeOverviewPage(webDriver, "Advisor Commissions");

	}

	/**
	 * Navigate to Company Commissions Page by clicking 'Accounting' -> 'Company
	 * Commissions' in classic view or 'Commissioning' -> 'Company Commission'
	 * in material view
	 * 
	 * @return go to the Company Commission Page via the main menu button
	 */
	public FeeOverviewPage goToCompanyCommissionPage() throws InterruptedException {

		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			this.goToFeeOverivewMaterialView("Company Commissions");
		} else {
			navigateToPage(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Accounting"),
					By.id("gwt-debug-MenuButtonHorizontal-CompanyCommissions"));
		}

		return new FeeOverviewPage(webDriver, "Company Commissions");

	}

	/**
	 * Navigate to Indemnity Releases Page by clicking 'Accounting' ->
	 * 'Indemnity Releases' in classic view or 'Commissioning' -> Indemnity
	 * Releases in material view
	 * 
	 * @return go to the Indemnity Releases Page via the main menu button
	 */
	public IndemnityReleasesPage goToIndemnityReleasesPage() throws InterruptedException {
		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			this.goToIndemnityReleasesPageMaterialView();
		} else {
			navigateToPage(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Accounting"),
					By.id("gwt-debug-MenuButtonHorizontal-IndemnityReleases"));
		}
		return new IndemnityReleasesPage(webDriver);

	}

	/**
	 * Navigate to Cashflow Overview Page by clicking 'Accounting' -> 'Cashflow'
	 * or 'Commissioning' -> Cash Flows in material view
	 * 
	 * @return go to the Cashflow Overview Page via the main menu button
	 */
	public CashflowOverviewPage goToCashflowOverviewPage() throws InterruptedException {

		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			this.goToCashflowOverviewPageMaterialView();
		} else {
			scrollToTop();
			navigateToPage(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Accounting"),
					By.id("gwt-debug-MenuButtonHorizontal-CashflowOverview"));
		}
		return new CashflowOverviewPage(webDriver);

	}

	/**
	 * Navigate to Trail Fee Agreements Page by clicking 'Accounting' -> 'Trail
	 * Fee Agreements' or 'Commissioning' -> Trail Fees in material view
	 * 
	 * @return go to the TrailFeeAgreementPage via the main menu button
	 */
	public TrailFeeAgreementPage goToTrailFeeAgreementPage() throws InterruptedException {
		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			this.goToTrailFeeAgreementPageMaterialView();
		} else {
			navigateToPage(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Accounting"),
					By.id("gwt-debug-MenuButtonHorizontal-TrailFeeAgreements"));
		}
		return new TrailFeeAgreementPage(webDriver);

	}

	/**
	 * Navigate to Email Template Page by clicking 'Operations' directly
	 * 
	 * @return EditEmailTemplatePage
	 */
	public EmailTemplatePage goToOperationsDEFAULTPage() throws InterruptedException {

		clickElement(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Operations"));

		return new EmailTemplatePage(webDriver);
	}

	/**
	 * Navigate to Email Template Page by clicking 'Operations' -> 'Bulk Email'
	 * in classic view or 'CRM' -> 'Email' in material view
	 * 
	 * @return EditEmailTemplatePage
	 */
	public BulkEmailPage goToBulkEmailPage() throws InterruptedException {
		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			this.goToBulkEmailPageMaterialView();
		} else {
			navigateToPage(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Operations"),
					By.id("gwt-debug-MenuButtonHorizontal-BulkEmail"));
		}

		return new BulkEmailPage(webDriver);
	}

	/**
	 * Navigate to ReportingOverview Page by clicking 'Operations' -> 'Reporting
	 * Overview' in classic view and material view
	 * 
	 * @return ReportingOverviewPage
	 * @throws InterruptedException
	 */
	public ReportingOverviewPage goToReportingOverviewPage() throws InterruptedException {
		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			this.goToReportingOverviewPageMaterialView();
		} else {
			navigateToPage(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Operations"),
					By.id("gwt-debug-MenuButtonHorizontal-ReportingOverview"));
		}

		return new ReportingOverviewPage(webDriver);
	}

	/**
	 * Navigate to AccountValueSpikes Page by clicking 'Operations' -> 'Account
	 * Value Spikes' in classic view and material view
	 * 
	 * @return {@link AccountValueSpikesPage}
	 * @throws InterruptedException
	 */
	public AccountValueSpikesPage goToAccountValueSpikesPage() throws InterruptedException {

		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			this.goToAccountValueSpikesPageMaterialView();
		} else {
			navigateToPage(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Operations"),
					By.id("gwt-debug-MenuButtonHorizontal-AccountValueSpikes"));
		}

		return new AccountValueSpikesPage(webDriver);

	}

	/**
	 * Navigate to DataReconciliation Page by clicking 'Operations' -> 'Data
	 * Reconciliation' in classic view and material view
	 * 
	 * @return {@link DataReconciliationPage}
	 * @throws InterruptedException
	 */
	public DataReconciliationPage goToDataReconciliationPage() throws InterruptedException {

		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			this.goToDataReconciliationPageMaterialView();
		} else {
			navigateToPage(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Operations"),
					By.id("gwt-debug-MenuButtonHorizontal-DataReconciliation"));
		}

		return new DataReconciliationPage(webDriver);
	}

	/**
	 * Navigate to UserActivity Page by clicking 'Operations' -> 'User Activity'
	 * in classic view and material view
	 * 
	 * @return {@link UserActivityPage}
	 * @throws InterruptedException
	 */
	public UserActivityPage goToUserActivityPage() throws InterruptedException {

		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			this.goToUserActivityPageMaterialView();
		} else {
			navigateToPage(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Operations"),
					By.id("gwt-debug-MenuButtonHorizontal-UserActivity"));
		}

		return new UserActivityPage(webDriver);
	}

	/**
	 * Navigate to Product Maintenance Page by clicking Operations -> Product
	 * Maintenance Only applicable for material view
	 * 
	 * @return {@link ProductMaintenancePage}
	 * @throws InterruptedException
	 */
	public ProductMaintenancePage goToProductMaintenancePage() throws InterruptedException {

		if (isElementVisible(By.xpath(
				".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Operations']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
			navigateToPage(By.xpath(
					".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Product Maintenance']"));
		} else {
			navigateToPageMaterialView(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Operations']"),
					By.xpath(
							".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Product Maintenance']"));
		}

		return new ProductMaintenancePage(webDriver);

	}

	/**
	 * Navigate to Model Portfolios Page by clicking 'Build' directly
	 * 
	 * @return go to the Model Portfolios Page via the main menu button......
	 *         FROM BUILD FOR ADMIN
	 */
	public ModelPortfoliosPage goToBuildDEFAULTPage() throws InterruptedException {

		clickElement(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Build"));

		return new ModelPortfoliosPage(webDriver);

	}

	// /**
	// * Navigate to Model Portfolios Page by clicking 'Build' -> 'Model
	// * Portfolios'
	// *
	// * @return go to the Model Portfolios Page via the main menu button......
	// * FROM BUILD FOR ADMIN
	// * */
	// public ModelPortfoliosPage goToModelPortfoliosPageFromExplore()
	// throws InterruptedException {
	// wait(Settings.WAIT_SECONDS);
	// boolean isMaterial = checkIfMaterial();
	// if (isMaterial) {
	// this.goToModelPortfoliosPageMaterialView()
	// .goToPortfolioManagement();
	//
	// } else {
	// navigateToPage(
	// By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Build"),
	// By.xpath("//td//a[.='Build']/parent::div[1]/following-sibling::div[1]//a[@id='gwt-debug-MenuButtonHorizontal-ModelPortfolios']"));
	//
	// }
	// this.waitForWaitingScreenNotVisible();
	// return new ModelPortfoliosPage(webDriver);
	//
	// }

	/**
	 * In CLASSIC View Navigate to vFunds Page by clicking 'Build' -> 'vFunds'
	 * 
	 * In MATERIAL View go to goToVfunds Page by clicking 'Asset Management' ->
	 * 'vFunds'
	 * 
	 * @return vFunds page
	 */
	public VFundsPage goToVFundsPage() throws InterruptedException {

		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			this.goToVfundsPageMaterialView();
		} else {
			navigateToPage(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Build"),
					By.id("gwt-debug-MenuButtonHorizontal-vFunds"));
		}

		return new VFundsPage(webDriver);

	}

	/**
	 * In Classic View Navigate to Strategy Rules Page by clicking 'Build' ->
	 * 'Strategy Rules'
	 * 
	 * In Material View Navigate to Strategy Rules Page by clicking 'Asset
	 * Management' -> 'Strategy Rules'
	 * 
	 * @return go to the Advisor Commission Page via the main menu button
	 */
	public StrategyRulesPage goToStrategyRulesPage() throws InterruptedException {
		wait(5);
		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			this.goToStrategyRulesPageMaterialView();
		} else {
			navigateToPage(By.xpath(".//*[@id='gwt-debug-MenuButtonHorizontal-menuButtonTop-Build']"),
					By.xpath(".//*[@id='gwt-debug-MenuButtonHorizontal-StrategyRules']"));

		}

		return new StrategyRulesPage(webDriver);
	}

	/**
	 * In Classic View Navigate to Structured Product Page by clicking 'Build'
	 * -> 'Structured Product'
	 * 
	 * In Material View Navigate to Structured Product Page by clicking 'Asset
	 * Management' -> 'Structured Product'
	 * 
	 * @return go to the New Business Page via the main menu button
	 */
	public StructuredProductPage goToStructuredProductPage() throws InterruptedException {

		wait(5);
		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			this.goToStructuredProductPageMaterialView();
		} else {
			navigateToPage(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Build"),
					By.id("gwt-debug-MenuButtonHorizontal-StructuredProduct"));

		}

		return new StructuredProductPage(webDriver);
	}

	/**
	 * In Classic View Navigate to Structured Product Page by clicking 'Build'
	 * -> 'Market Equities'
	 * 
	 * In Material View Navigate to Strategy Rules Page by clicking 'Asset
	 * Management' -> 'Market Equities'
	 * 
	 * @return go to the New Business Page via the main menu button
	 */
	public MarketEquitiesPage goToMarketEquitiesPage() throws InterruptedException {

		wait(5);
		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			this.goToMarketEquitiesPageMaterialView();
		} else {
			navigateToPage(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Build"),
					By.id("gwt-debug-MenuButtonHorizontal-MarketEquities"));

		}

		return new MarketEquitiesPage(webDriver);
	}

	public MarketRatesPage goToMarketRatesPage() throws InterruptedException {

		wait(5);

		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			this.goToMarketRatesPageMaterialView();
		} else {
			navigateToPage(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Build"),
					By.id("gwt-debug-MenuButtonHorizontal-MarketRates"));
		}

		return new MarketRatesPage(webDriver);
	}

	/**
	 * In both classic and material Navigate to Last Account Value Update Page
	 * by clicking 'Operations' -> 'Last Account Value Update'
	 * 
	 * @return LastAccountValueUpdatePage
	 * @throws InterruptedException
	 * 
	 */
	public LastAccountValueUpdatePage goToLastAccountValueUpdatePage() throws InterruptedException {
		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			this.goToLastAccountValueUpdatePageMaterialView();
		} else {
			navigateToPage(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Operations"),
					By.id("gwt-debug-MenuButtonHorizontal-LastAccountValueUpdate"));
		}

		return new LastAccountValueUpdatePage(webDriver);
	}

	/**
	 * Navigate to Holdings Page of the account by clicking 'Holdings' tab
	 * 
	 * @return HoldingsPage
	 */
	public HoldingsPage goToHoldingsPage() throws InterruptedException {
		scrollToTop();
		clickElement(By.xpath("//a[@id='gwt-debug-PortfolioOverviewTab-hyperlink' and .='Holdings']"));

		// clickElement(By.xpath("//a[.='Details']"));

		return new HoldingsPage(webDriver);
	}

	/**
	 * Navigate to Details Page of the account or the client by clicking
	 * 'Details' tab
	 * 
	 * @return DetailsPage
	 */
	public DetailPage goToDetailsPage() throws InterruptedException {

		scrollToTop();
		this.waitForWaitingScreenNotVisible();
		waitForElementVisible(By.xpath("//a[@id='gwt-debug-PortfolioOverviewTab-hyperlink' and .='Details']"), 10);
		clickElement(By.xpath("//a[@id='gwt-debug-PortfolioOverviewTab-hyperlink' and .='Details']"));

		// clickElement(By.xpath("//a[.='Details']"));
		wait(Settings.WAIT_SECONDS);

		return new DetailPage(webDriver);
	}

	/**
	 * Navigate to Compare Page of the account by clicking 'Compare' tab
	 * 
	 * @return ComparePage
	 */
	public ComparePage goToComparePage() throws InterruptedException {

		clickElement(By.xpath("//a[@id='gwt-debug-PortfolioOverviewTab-hyperlink' and .='Compare']"));

		// clickElement(By.xpath("//a[.='Compare']"));

		return new ComparePage(webDriver);
	}

	/**
	 * Navigate to Analysis Page of the account by clicking 'Analysis' tab
	 * 
	 * @return AnalysisPage
	 */
	public AnalysisPage goToAnalysisPage() throws InterruptedException {
		scrollToTop();
		clickElement(By.xpath("//a[@id='gwt-debug-PortfolioOverviewTab-hyperlink' and .='Analysis']"));

		// clickElement(By.xpath("//a[.='Analysis']"));

		return new AnalysisPage(webDriver);
	}

	/**
	 * Navigate to Recent Order Page of the account by clicking 'Recent Order'
	 * tab
	 * 
	 * @return RecentsOrderPage
	 */
	public RecentOrdersPage goToRecentOrderPage() throws InterruptedException {

		clickElement(By.xpath("//a[@id='gwt-debug-PortfolioOverviewTab-hyperlink' and .='Recent Orders']"));

		// clickElement(By.xpath("//a[.='Analysis']"));

		return new RecentOrdersPage(webDriver);
	}

	/**
	 * Navigate to Alerts Page of the account by clicking 'Alert'
	 * 
	 * @return {@AlertsPage}
	 * @throws InterruptedException
	 */
	public AlertsPage goToAlertsPage() throws InterruptedException {
		clickElement(By.xpath("//a[@id='gwt-debug-PortfolioOverviewTab-hyperlink' and .='Alerts']"));
		return new AlertsPage(webDriver);
	}

	/**
	 * Navigate to History Page of the account by clicking 'History' tab and
	 * click Transaction
	 * 
	 * @return HistoryPage
	 */
	public HistoryPage goToTransactionHistoryPage() throws InterruptedException {

		waitForElementVisible(By.xpath("//*[@id='gwt-debug-MyVisibilityTabPanel-verticalTabLabel' and .='History']"),
				120);

		clickElement(By.xpath("//*[@id='gwt-debug-MyVisibilityTabPanel-verticalTabLabel' and .='History']"));

		this.waitForWaitingScreenNotVisible();
		if (!isElementVisible(By.xpath("//a[@id='gwt-debug-PortfolioOverviewTab-hyperlink' and .='Transactions']"))) {
			clickElement(By.xpath("//*[@id='gwt-debug-MyVisibilityTabPanel-verticalTabLabel' and .='History']"));
		}

		clickElement(By.xpath("//a[@id='gwt-debug-PortfolioOverviewTab-hyperlink' and .='Transactions']"));
		scrollToTop();

		wait(Settings.WAIT_SECONDS);

		return new HistoryPage(webDriver);
	}

	/**
	 * Navigate to History Page of the account by clicking 'History' tab and
	 * click Portfolio
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public HistoryPage goToPortfolioHistoryPage() throws InterruptedException {
		waitForElementVisible(By.xpath("//*[@id='gwt-debug-MyVisibilityTabPanel-verticalTabLabel' and .='History']"),
				120);

		clickElement(By.xpath("//*[@id='gwt-debug-MyVisibilityTabPanel-verticalTabLabel' and .='History']"));

		if (!isElementVisible(By.xpath("//a[@id='gwt-debug-PortfolioOverviewTab-hyperlink' and .='Portfolio']"))) {
			clickElementByKeyboard(
					By.xpath("//*[@id='gwt-debug-MyVisibilityTabPanel-verticalTabLabel' and .='History']"));
		}

		clickElement(By.xpath("//a[@id='gwt-debug-PortfolioOverviewTab-hyperlink' and .='Portfolio']"));
		scrollToTop();

		return new HistoryPage(webDriver);
	}

	/**
	 * Navigate to CRM Page of the client by clicking 'CRM' tab
	 * 
	 * @return CRMPage
	 */
	public CRMPage goToCRMPage() throws InterruptedException {

		try {
			wait(Settings.WAIT_SECONDS);
			clickElement(By.xpath(".//*[contains(@id,'hyperlink') and .='CRM']"));

		} catch (org.openqa.selenium.TimeoutException e) {
			// SeleniumInvestor
			wait(Settings.WAIT_SECONDS);
			clickElement(By.xpath(".//*[@class='TabHeaderHorizontalBar']//a[.='CRM']"));
		}

		return new CRMPage(webDriver);
	}

	/**
	 * Navigate to Cashflow Page of the account by clicking 'Cashflow' tab
	 * 
	 * @return CashflowPage
	 */
	public CashflowPage goToCashflowPage() throws InterruptedException {
		waitForElementVisible(By.xpath("//*[@id='gwt-debug-MyVisibilityTabPanel-verticalTabLabel' and .='History']"),
				120);

		clickElement(By.xpath("//*[@id='gwt-debug-MyVisibilityTabPanel-verticalTabLabel' and .='History']"));

		if (!isElementVisible(By.xpath("//a[@id='gwt-debug-PortfolioOverviewTab-hyperlink' and .='Cashflow']"))) {
			clickElementByKeyboard(
					By.xpath("//*[@id='gwt-debug-MyVisibilityTabPanel-verticalTabLabel' and .='History']"));
		}

		clickElement(By.xpath("//a[@id='gwt-debug-PortfolioOverviewTab-hyperlink' and .='Cashflow']"));
		this.waitForWaitingScreenNotVisible();
		return new CashflowPage(webDriver);
	}

	/**
	 * Navigate to Accounts page of the client by clicking 'Accounts' tab
	 * 
	 * @return AccountsPage
	 */
	public AccountsPage goToAccountsTab() throws InterruptedException {

		wait(Settings.WAIT_SECONDS);
		clickElement(By.xpath(".//*[@id='gwt-debug-GeneralUserDetailViewTab-hyperlink' and .='Accounts']"));

		return new AccountsPage(webDriver);
	}

	public AccountsPage goToAccountsTabFS() throws InterruptedException {
		wait(Settings.WAIT_SECONDS);
		clickElement(By.xpath(".//*[@id='gwt-debug-GeneralUserDetailViewTab-hyperlink' and .='Konten']"));

		return new AccountsPage(webDriver);
	}

	/**
	 * Navigate to Accounts page of the client by clicking 'Konten' tab
	 * 
	 * @return AccountsPage
	 */
	public AccountsPage goToKontenTab() throws InterruptedException {

		wait(Settings.WAIT_SECONDS);
		clickElement(By.xpath(".//*[@id='gwt-debug-GeneralUserDetailViewTab-hyperlink' and .='Konten']"));

		return new AccountsPage(webDriver);
	}

	/**
	 * Navigate to Reports Page of the client by clicking 'Reports' tab
	 * 
	 * @return reportPage
	 */
	public ReportPage goToReportPage() throws InterruptedException {
		wait(Settings.WAIT_SECONDS);
		clickElement(By.xpath(".//*[@id='gwt-debug-GeneralUserDetailViewTab-hyperlink' and .='Reports']"));

		return new ReportPage(webDriver);
	}

	/**
	 * Navigate to Admin Reporting Page by clicking 'Administration' ->
	 * 'Reporting'
	 * 
	 * @return AdminReportingPage
	 */
	public AdminReportingPage goToAdminReportingPage() throws InterruptedException {

		boolean isMaterial = checkIfMaterial();

		if (isMaterial) {
			this.goToAdminReportingPageMaterialView();
		} else {
			navigateToPage(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Administration"),
					By.id("gwt-debug-MenuButtonHorizontal-Reporting"));
		}

		return new AdminReportingPage(webDriver);
	}

	/**
	 * Navigate to Admin Edit Page by clicking 'Administration' -> 'Edit'
	 * 
	 * @return AdminEditPage
	 */
	public AdminEditPage goToAdminEditPage() throws InterruptedException {

		boolean isMaterial = checkIfMaterial();

		if (isMaterial) {
			this.goToAdminEditPageMaterialView();
		} else {
			scrollToTop();
			navigateToPage(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Administration"),
					By.id("gwt-debug-MenuButtonHorizontal-Edit"));
		}

		return new AdminEditPage(webDriver);
	}

	/**
	 * Navigate to Admin Edit Page by clicking 'Administration' -> 'Edit'
	 * 
	 * @return AdminEditPage
	 */
	public AdminSearchPage goToAdminSearchPage() throws InterruptedException {

		boolean isMaterial = checkIfMaterial();

		if (isMaterial) {
			this.goToAdminSearchPageMaterialView();
		} else {
			scrollToTop();
			navigateToPage(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Administration"),
					By.id("gwt-debug-MenuButtonHorizontal-Search"));
		}

		return new AdminSearchPage(webDriver);
	}

	/**
	 * Navigate to Admin Edit Page by clicking 'Administration' -> 'Maintenance'
	 * 
	 * @return AdminEditPage
	 */
	public AdminMaintenancePage goToAdminMaintenancePage() throws InterruptedException {

		boolean isMaterial = checkIfMaterial();

		if (isMaterial) {
			this.goToAdminMaintenancePageMaterialView();
		} else {
			scrollToTop();
			navigateToPage(By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Administration"),
					By.id("gwt-debug-MenuButtonHorizontal-Maintenance"));
		}

		return new AdminMaintenancePage(webDriver);
	}

	public RecentOrdersPage goToOrderBookPage() throws InterruptedException {
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 60);
		scrollToTop();
		if (isElementVisible(By.xpath(
				".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Ordering']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
			clickElement(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Order Book']"));
		} else {
			navigateToPageMaterialView(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Ordering']"),
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Order Book']"));
			try {
				waitForElementVisible(By.id("gwt-debug-RecentOrdersOverviewView-overviewPanel"), 30);

			} catch (TimeoutException e) {
				this.goToOrderBookPage();
			}

		}
		return new RecentOrdersPage(webDriver);
	}

	/**
	 * Navigate to Illustration page by clicking 'Manage' -> 'Illustration'
	 * 
	 * @return IllustrationPage
	 * @throws InterruptedException
	 * 
	 */
	public IllustrationPage goToIllustrationPage() throws InterruptedException {

		// FluentWait<WebDriver> wait_noInfo = new
		// FluentWait<WebDriver>(webDriver)
		// .withTimeout(10, TimeUnit.SECONDS)
		// .pollingEvery(2, TimeUnit.SECONDS)
		// .ignoring(org.openqa.selenium.NoSuchElementException.class);
		//
		// FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver)
		// .withTimeout(75, TimeUnit.SECONDS)
		// .pollingEvery(2, TimeUnit.SECONDS)
		// .ignoring(org.openqa.selenium.NoSuchElementException.class);
		//
		// try {
		//
		// wait_noInfo.until(ExpectedConditions.visibilityOfElementLocated(By
		// .id("gwt-debug-InvestorAccountTable-noInfoPanel")));
		//
		// } catch (org.openqa.selenium.TimeoutException e) {
		//
		// wait.until(ExpectedConditions.visibilityOfElementLocated(By
		// .id("gwt-debug-InvestorAccountTable-accountTablePanel")));
		//
		// }

		// classic
		// navigateToPage(
		// By.id("gwt-debug-MenuButtonHorizontal-menuButtonTop-Manage"),
		// By.id("gwt-debug-MenuButtonHorizontal-Illustration"));

		// material
		waitForElementVisible(By.xpath(".//a[span[.='Planning']]"), 90);
		navigateToPageMaterialView(By.xpath(".//a[span[.='Planning']]"), By.xpath("//a[.='Illustration']"));

		return new IllustrationPage(webDriver);
	}

	/**
	 * Click the back icon to the previous page
	 */
	public AbstractPage goBackToPreviousPage(WebDriver webDriver) {

		webDriver.navigate().back();

		return this;

	}

	public void customAssetPageNotPresent() throws InterruptedException {
		if (checkIfMaterial()) {
			if (!isElementVisible(By.xpath(
					".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Company Admin']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
				navigateToPage(By.xpath(
						".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Company Admin']"));
			}
		} else {
			navigateToPage(By.xpath("//a[@title='Company Settings']/img"));
		}
		assertTrue(!pageContainsStr("Custom Asset"));

	}

	/**
	 * Navigate to Edit page by clicking Edit tab directly or by clicking
	 * Administration -> Edit
	 * 
	 * @return AdminEditPage
	 * @throws InterruptedException
	 */
	public AdminEditPage goToAdminEditPageMaterialView() throws InterruptedException {
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 60);
		scrollToTop();
		if (isElementVisible(By.xpath(
				".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Administration']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
			clickElement(By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Edit']"));
		} else {
			navigateToPageMaterialView(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Administration']"),
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Edit']"));
		}

		return new AdminEditPage(webDriver);
	}

	/**
	 * Navigate to Edit page by clicking Edit tab directly or by clicking
	 * Administration -> Search
	 * 
	 * @return AdminSearchPage
	 * @throws InterruptedException
	 */
	public AdminSearchPage goToAdminSearchPageMaterialView() throws InterruptedException {
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 60);
		scrollToTop();
		if (isElementVisible(By.xpath(
				".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Administration']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
			clickElement(By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Search']"));
		} else {
			navigateToPageMaterialView(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Administration']"),
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Search']"));
		}

		return new AdminSearchPage(webDriver);
	}

	/**
	 * Navigate to Edit page by clicking Maintenance tab directly or by clicking
	 * Administration -> Maintenance
	 * 
	 * @return AdminEditPage
	 * @throws InterruptedException
	 */
	public AdminMaintenancePage goToAdminMaintenancePageMaterialView() throws InterruptedException {
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 60);
		scrollToTop();
		if (isElementVisible(By.xpath(
				".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Administration']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
			clickElement(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Maintenance']"));
		} else {
			navigateToPageMaterialView(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Administration']"),
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Maintenance']"));
		}

		return new AdminMaintenancePage(webDriver);
	}

	/**
	 * Navigate to AccountOverviewPage by clicking Clients & Accounts or
	 * Accounts tab and clicking Accounts if the Accounts page is not visible or
	 * if the Account page is visible, directly navigate to AccountOverviewPage
	 * page by clicking Accounts under Clients & Accounts/Accounts.
	 * 
	 * @return AccountOverviewPage
	 * @throws InterruptedException
	 */
	public AccountOverviewPage goToAccountOverviewPageMaterialView() throws InterruptedException {

		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 60);
		wait(Settings.WAIT_SECONDS);
		try {
			if (isElementVisible(By.xpath(
					".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Clients & Accounts']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
				navigateToPage(
						By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Accounts']"));
			} else {
				navigateToPageMaterialView(
						By.xpath(
								".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Clients & Accounts']"),
						By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Accounts']"));
			}
		} catch (TimeoutException e) {

			if (isElementVisible(By.xpath(
					".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Accounts']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
				navigateToPage(By.xpath(
						".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Account Overview']"));
			} else {
				navigateToPageMaterialView(
						By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Accounts']"),
						By.xpath(
								".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Account Overview']"));
			}
		}
		return new AccountOverviewPage(webDriver);
	}

	/**
	 * Navigate to ModelPortfoliosPage by directly clicking Model Portfolios or
	 * clicking Asset Management -> Model Porfolios
	 * 
	 * @return ModelPortfoliosPage
	 * @throws InterruptedException
	 */
	public ModelPortfoliosPage goToModelPortfoliosPageMaterialView() throws InterruptedException {
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 60);
		if (isElementVisible(By.xpath(
				".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Asset Management']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
			navigateToPage(By
					.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Model Portfolios']"));
		} else {
			navigateToPageMaterialView(
					By.xpath(
							".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Asset Management']"),
					By.xpath(
							".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Model Portfolios']"));
		}

		return new ModelPortfoliosPage(webDriver);
	}

	/**
	 * Navigate to CompanySettingsPage by directly clicking on Company Settings
	 * or clicking Company Admin -> Company Settings
	 * 
	 * @return CompanySettingsPage
	 * @throws InterruptedException
	 */
	public CompanySettingsPage goToCompanySettingsPageMaterialView() throws InterruptedException {
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 60);
		if (isElementVisible(By.xpath(
				".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Company Admin']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
			navigateToPage(By
					.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Company Settings']"));
		} else {
			navigateToPageMaterialView(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Company Admin']"),
					By.xpath(
							".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Company Settings']"));
		}

		assertTrue(pageContainsStr("Company Settings"));
		return new CompanySettingsPage(webDriver);
	}

	/**
	 * Navigate to DashboardPage by directly clicking on Dashboard or clicking
	 * Company Admin -> Dashboard
	 * 
	 * @return DashboardPage
	 * @throws InterruptedException
	 */
	public DashboardPage goToDashboardPageMaterialView() throws InterruptedException {
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 60);

		if (isElementVisible(By.xpath(
				".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Company Admin']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
			navigateToPage(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Dashboard']"));
		} else {
			navigateToPageMaterialView(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Company Admin']"),
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Dashboard']"));
		}

		return new DashboardPage(webDriver);
	}

	/**
	 * Navigate to CustomFieldPage by directly clicking on Custom Field or
	 * clicking Company Admin -> Custom Field
	 * 
	 * @return CustomFieldPage
	 * @throws InterruptedException
	 */
	public CustomFieldPage goToCustomFieldPageMaterialView() throws InterruptedException {
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 60);

		if (isElementVisible(By.xpath(
				".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Company Admin']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
			navigateToPage(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Custom Field']"));
		} else {
			navigateToPageMaterialView(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Company Admin']"),
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Custom Field']"));

		}
		return new CustomFieldPage(webDriver);
	}

	/**
	 * Navigate to EmailTemplatePage by directly clicking on Email Template or
	 * clicking Company Admin -> Email Template
	 * 
	 * @return EmailTemplatePage
	 * @throws InterruptedException
	 */
	public EmailTemplatePage goToEmailTemplatePageMaterialView() throws InterruptedException {
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 60);
		if (isElementVisible(By.xpath(
				".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Company Admin']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
			navigateToPage(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Email Template']"));
		} else {
			navigateToPageMaterialView(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Company Admin']"),
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Email Template']"));

		}
		return new EmailTemplatePage(webDriver);
	}

	/**
	 * Navigate to CustomAssetPage by directly clicking Custom Asset or clicking
	 * Company Admin -> Custom Asset
	 * 
	 * @return CustomAssetPage
	 * @throws InterruptedException
	 */
	public CustomAssetPage goToCustomAssetPageMaterialView() throws InterruptedException {
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 60);
		if (isElementVisible(By.xpath(
				".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Company Admin']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
			navigateToPage(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Custom Asset']"));
		} else {
			navigateToPageMaterialView(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Company Admin']"),
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Custom Asset']"));
		}

		assertTrue(pageContainsStr("Custom Asset"));
		return new CustomAssetPage(webDriver);
	}

	public CustomAssetPage noCustomAssetPage() throws InterruptedException {
		this.waitForWaitingScreenNotVisible();
		if (checkIfMaterial()) {
			waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 60);
			if (!isElementVisible(By.xpath(
					".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Company Admin']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
				navigateToPage(By.xpath(
						".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Company Admin']"));
			}
		} else {
			clickElement(By.xpath("//a[@title='Company Settings']/img"));
		}
		boolean flag = pageContainsStr("Custom Asset");
		assertTrue(!flag);
		return new CustomAssetPage(webDriver);
	}

	/**
	 * Navigate to Page by directly clicking Custom Tag or clicking Company
	 * Admin -> Custom Tag
	 * 
	 * @return CustomTagPage
	 * @throws InterruptedException
	 */
	public CustomTagPage goToCustomTagPageMaterialView() throws InterruptedException {

		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 60);
		if (isElementVisible(By.xpath(
				".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Company Admin']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
			navigateToPage(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Custom Tag']"));

		} else {
			navigateToPageMaterialView(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Company Admin']"),
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Custom Tag']"));
		}

		return new CustomTagPage(webDriver);
	}

	/**
	 * Navigate to OpportunitiesPage by directly clicking on Opportunities or
	 * clicking CRM -> Opportunities
	 * 
	 * @return OpportunitiesPage
	 * @throws InterruptedException
	 */
	public OpportunitiesPage goToOpportunitiesPageMaterialView() throws InterruptedException {
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 60);
		if (isElementVisible(By.xpath(
				".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='CRM']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
			navigateToPage(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Opportunities']"));

		} else {
			navigateToPageMaterialView(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='CRM']"),
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Opportunities']"));
		}
		return new OpportunitiesPage(webDriver);

	}

	/**
	 * Navigate to NewBusinessEventPage by directly clicking on New Business
	 * Event or clicking Commissioning -> New Business Event
	 * 
	 * @return NewBusinessEventPage
	 * @throws InterruptedException
	 */
	public NewBusinessEventPage goToNewBusinessPageMaterialView() throws InterruptedException {
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 60);
		if (isElementVisible(By.xpath(
				".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Commissioning']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
			navigateToPage(By.xpath(
					".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='New Business Events']"));

		} else {
			navigateToPageMaterialView(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Commissioning']"),
					By.xpath(
							".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='New Business Events']"));
		}

		return new NewBusinessEventPage(webDriver);

	}

	/**
	 * Navigate to TasksPage by directly clicking on Tasks or clicking CRM ->
	 * Tasks
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public TasksPage goToTasksPageMaterialView() throws InterruptedException {
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 60);
		if (isElementVisible(By.xpath(
				".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='CRM']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
			navigateToPage(By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Tasks']"));

		} else {
			navigateToPageMaterialView(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='CRM']"),
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Tasks']"));
		}

		return new TasksPage(webDriver);
	}

	/**
	 * Navigate to CalendarPage by directly clicking on Calendar or clicking
	 * Commissioning -> Calendar
	 * 
	 * @return {@link CalendarPage}
	 * @throws InterruptedException
	 */
	public CalendarPage goToCalendarPageMaterialView() throws InterruptedException {
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 60);
		if (isElementVisible(By.xpath(
				".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='CRM']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
			navigateToPage(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Calendar']"));

		} else {
			navigateToPageMaterialView(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='CRM']"),
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Calendar']"));
		}
		return new CalendarPage(webDriver);
	}

	/**
	 * Navigate to AdvisorPerformancePage by directly clicking on Advisor
	 * Performance or clicking Company Admin -> Advisor Performance
	 * 
	 * @return {@link AdvisorPerformancePage}
	 * @throws InterruptedException
	 */
	public AdvisorPerformancePage goToAdvisorPerformancePageMaterialView() throws InterruptedException {
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 60);
		if (isElementVisible(By.xpath(
				".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Company Admin']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
			navigateToPage(By.xpath(
					".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Advisor Performance']"));

		} else {
			navigateToPageMaterialView(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Company Admin']"),
					By.xpath(
							".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Advisor Performance']"));
		}

		return new AdvisorPerformancePage(webDriver);
	}

	/**
	 * Navigate to EmployeePage by directly clicking on Employee or clicking
	 * Company Admin -> Employee
	 * 
	 * @return {@link EmployeePage}
	 * @throws InterruptedException
	 */
	public EmployeePage goToEmployeePageMaterialView() throws InterruptedException {
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 60);
		if (isElementVisible(By.xpath(
				".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Company Admin']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
			navigateToPage(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Employee']"));

		} else {
			navigateToPageMaterialView(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Company Admin']"),
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Employee']"));
		}

		return new EmployeePage(webDriver);
	}

	/**
	 * Navigate to WhiteListsPage by directly clicking on White Lists or
	 * clicking Company Admin -> White Lists
	 * 
	 * @return {@link WhiteListsPage}
	 * @throws InterruptedException
	 */
	public WhiteListsPage goToWhiteListPageMaterialView() throws InterruptedException {
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 60);
		if (isElementVisible(By.xpath(
				".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Company Admin']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
			navigateToPage(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='White Lists']"));

		} else {
			navigateToPageMaterialView(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Company Admin']"),
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='White Lists']"));
		}

		return new WhiteListsPage(webDriver);
	}

	/**
	 * Navigate to DocumentsPage by directly clicking on Documents or clicking
	 * CRM -> White Documents
	 * 
	 * @return {@link DocumentsPage}
	 * @throws InterruptedException
	 */
	public DocumentsPage goToDocumentsPageMaterialView() throws InterruptedException {
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 60);
		if (isElementVisible(By.xpath(
				".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='CRM']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
			navigateToPage(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Documents']"));

		} else {
			navigateToPageMaterialView(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='CRM']"),
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Documents']"));
		}
		scrollToTop();
		return new DocumentsPage(webDriver);

	}

	/**
	 * Navigate to RebalancingsPage by directly clicking on Asset Management or
	 * clicking Asset Management -> Rebalancings
	 * 
	 * @return {@link RebalancingsPage}
	 * @throws InterruptedException
	 */
	public RebalancingsPage goToRebalancingsPageMaterailView() throws InterruptedException {
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 60);
		if (isElementVisible(By.xpath(
				".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Asset Management']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
			navigateToPage(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Rebalancings']"));

		} else {
			navigateToPageMaterialView(
					By.xpath(
							".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Asset Management']"),
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Rebalancings']"));
		}

		return new RebalancingsPage(webDriver);
	}

	/**
	 * Navigate to NotesPage by directly clicking on Notes or clicking CRM ->
	 * Notes
	 * 
	 * @return {@link NotesPage}
	 * @throws InterruptedException
	 */
	public NotesPage goToNotesPageMaterialView() throws InterruptedException {
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 60);
		if (isElementVisible(By.xpath(
				".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='CRM']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
			navigateToPage(By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Notes']"));

		} else {
			navigateToPageMaterialView(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='CRM']"),
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Notes']"));
		}
		return new NotesPage(webDriver);
	}

	/**
	 * Navigate to ProcessTasks Page by directly clicking on Process Tasks or
	 * clicking Workflow -> Process Tasks
	 * 
	 * @return {@link ProcessTasksPage}
	 * @throws InterruptedException
	 */
	public ProcessTasksPage goToProcessTasksPageMaterialView() throws InterruptedException {

		if (isElementVisible(By.xpath(
				".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Workflow']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
			navigateToPage(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Process Tasks']"));
		} else {
			navigateToPageMaterialView(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Workflow']"),
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Process Tasks']"));
		}

		return new ProcessTasksPage(webDriver);
	}

	/**
	 * Navigate to Processes Page by directly clicking on Processes or clicking
	 * Workflow -> Processes
	 * 
	 * @return {@link ProcessesPage}
	 * @throws InterruptedException
	 */
	public ProcessesPage goToProcessesPageMaterialView() throws InterruptedException {
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 60);
		if (isElementVisible(By.xpath(
				".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Workflow']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
			navigateToPage(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Processes']"));
		} else {
			navigateToPageMaterialView(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Workflow']"),
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Processes']"));
		}

		return new ProcessesPage(webDriver);
	}

	/**
	 * Navigate to WorkflowOverview Page by directly clicking on Workflow
	 * Overview or clicking Workflow -> Workflow Overview
	 * 
	 * @return {@link WorkflowOverviewPage}
	 * @throws InterruptedException
	 */
	public WorkflowOverviewPage goToWorkflowOverviewPageMaterialView() throws InterruptedException {
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 60);
		if (isElementVisible(By.xpath(
				".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Workflow']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
			navigateToPage(By.xpath(
					".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Workflow Overview']"));
		} else {
			navigateToPageMaterialView(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Workflow']"),
					By.xpath(
							".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Workflow Overview']"));
		}

		return new WorkflowOverviewPage(webDriver);
	}

	/**
	 * Navigate to FeeOverview Page by directly clicking on Client Fees/Advisor
	 * Commissions/Company Commissions or clicking Commissioning -> Client
	 * Fees/Advisor Commissions/Company Commissions
	 * 
	 * The decision on going which page is based on the title received.
	 * 
	 * @return {@link FeeOverviewPage}
	 * @throws InterruptedException
	 */
	public FeeOverviewPage goToFeeOverivewMaterialView(String title) throws InterruptedException {
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 60);
		if (isElementVisible(By.xpath(
				".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Commissioning']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
			navigateToPage(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='" + title + "']"));
		} else {
			navigateToPageMaterialView(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Commissioning']"),
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='" + title + "']"));
		}
		scrollToTop();
		return new FeeOverviewPage(webDriver, title);
	}

	/**
	 * Navigate to IndemnityReleases Page by directly clicking on Indemnity
	 * Releases or clicking Commissioning -> Indemnity Releases
	 * 
	 * @return {@link IndemnityReleasesPage}
	 * @throws InterruptedException
	 */
	public IndemnityReleasesPage goToIndemnityReleasesPageMaterialView() throws InterruptedException {
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 60);
		if (isElementVisible(By.xpath(
				".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Commissioning']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
			navigateToPage(By.xpath(
					".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Indemnity Releases']"));
		} else {
			navigateToPageMaterialView(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Commissioning']"),
					By.xpath(
							".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Indemnity Releases']"));
		}

		return new IndemnityReleasesPage(webDriver);
	}

	/**
	 * Navigate to CashflowOverview Page by directly clicking on Cash Flows or
	 * clicking Commissioning -> Cash Flows
	 * 
	 * @return {@link CashflowOverviewPage}
	 * @throws InterruptedException
	 */
	public CashflowOverviewPage goToCashflowOverviewPageMaterialView() throws InterruptedException {
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 60);
		if (isElementVisible(By.xpath(
				".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Commissioning']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
			navigateToPage(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Cash Flows']"));
		} else {
			navigateToPageMaterialView(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Commissioning']"),
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Cash Flows']"));
		}

		return new CashflowOverviewPage(webDriver);
	}

	/**
	 * Navigate to TrailFeeAgreement Page by directly clicking on Trail Fees or
	 * clicking Commissioning -> Trail Fees
	 * 
	 * @return {@link TrailFeeAgreementPage}
	 * @throws InterruptedException
	 */
	public TrailFeeAgreementPage goToTrailFeeAgreementPageMaterialView() throws InterruptedException {
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 60);
		if (isElementVisible(By.xpath(
				".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Commissioning']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
			navigateToPage(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Trail Fees']"));
		} else {
			navigateToPageMaterialView(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Commissioning']"),
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Trail Fees']"));
		}

		return new TrailFeeAgreementPage(webDriver);
	}

	/**
	 * Navigate to EmailTemplate Page by directly clicking on Email or clicking
	 * CRM -> Email
	 * 
	 * @return {@link EmailTemplatePage}
	 * @throws InterruptedException
	 */
	public BulkEmailPage goToBulkEmailPageMaterialView() throws InterruptedException {
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 60);
		if (isElementVisible(By.xpath(
				".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='CRM']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
			navigateToPage(By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Email']"));
		} else {
			navigateToPageMaterialView(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='CRM']"),
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Email']"));
		}

		return new BulkEmailPage(webDriver);
	}

	/**
	 * Navigate to ReportingOverview Page by directly clicking on Reporting
	 * Overview or clicking Operations -> Reporting Overview
	 * 
	 * @return {@link ReportingOverviewPage}
	 * @throws InterruptedException
	 */
	public ReportingOverviewPage goToReportingOverviewPageMaterialView() throws InterruptedException {
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 60);
		if (isElementVisible(By.xpath(
				".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Operations']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
			navigateToPage(By.xpath(
					".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Reporting Overview']"));
		} else {
			navigateToPageMaterialView(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Operations']"),
					By.xpath(
							".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Reporting Overview']"));
		}

		return new ReportingOverviewPage(webDriver);
	}

	/**
	 * Navigate to AccountValueSpikes Page by directly clicking on Account Value
	 * Spikes or clicking Operations -> Account Value Spikes
	 * 
	 * @return {@link AccountValueSpikesPage}
	 * @throws InterruptedException
	 */
	public AccountValueSpikesPage goToAccountValueSpikesPageMaterialView() throws InterruptedException {
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 60);
		if (isElementVisible(By.xpath(
				".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Operations']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
			navigateToPage(By.xpath(
					".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Account Value Spikes']"));
		} else {
			navigateToPageMaterialView(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Operations']"),
					By.xpath(
							".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Account Value Spikes']"));
		}

		return new AccountValueSpikesPage(webDriver);
	}

	/**
	 * Navigate to DataReconciliation Page by directly clicking on Data
	 * Reconciliation or clicking Operations -> Data Reconciliation
	 * 
	 * @return {@link DataReconciliationPage}
	 * @throws InterruptedException
	 */
	public DataReconciliationPage goToDataReconciliationPageMaterialView() throws InterruptedException {
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 60);
		if (isElementVisible(By.xpath(
				".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Operations']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
			navigateToPage(By.xpath(
					".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Data Reconciliation']"));
		} else {
			navigateToPageMaterialView(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Operations']"),
					By.xpath(
							".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Data Reconciliation']"));
		}

		return new DataReconciliationPage(webDriver);
	}

	/**
	 * Navigate to UserActivity Page by directly clicking on User Activity or
	 * clicking Operations -> User Activity
	 * 
	 * @return {@link UserActivityPage}
	 * @throws InterruptedException
	 */
	public UserActivityPage goToUserActivityPageMaterialView() throws InterruptedException {
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 60);
		if (isElementVisible(By.xpath(
				".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Operations']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
			navigateToPage(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='User Activity']"));
		} else {
			navigateToPageMaterialView(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Operations']"),
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='User Activity']"));
		}

		return new UserActivityPage(webDriver);
	}

	/**
	 * Navigate to UserActivity Page by directly clicking on Last Value Update
	 * or clicking Operations -> Last Value Update
	 * 
	 * @return {@link LastAccountValueUpdatePage}
	 * @throws InterruptedException
	 */
	public LastAccountValueUpdatePage goToLastAccountValueUpdatePageMaterialView() throws InterruptedException {
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 60);
		if (isElementVisible(By.xpath(
				".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Operations']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {

			navigateToPage(By.xpath(
					".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Last Value Update']"));
		} else {
			navigateToPageMaterialView(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Operations']"),
					By.xpath(
							".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Last Value Update']"));
		}

		return new LastAccountValueUpdatePage(webDriver);
	}

	/**
	 * Navigate to AdminReportingPage by directly clicking on Reporting or
	 * clicking Administration -> Reporting
	 * 
	 * @return {@link AdminReportingPage}
	 * @throws InterruptedException
	 */
	public AdminReportingPage goToAdminReportingPageMaterialView() throws InterruptedException {
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 60);
		if (isElementVisible(By.xpath(
				".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Administration']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
			navigateToPage(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Reporting']"));
		} else {
			navigateToPageMaterialView(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Administration']"),
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Reporting']"));
		}

		return new AdminReportingPage(webDriver);
	}

	/**
	 * Navigate to ClientOverviewPage by directly clicking on Clients or
	 * clicking Clients & Accounts/Clients -> Clients
	 * 
	 * @return {@link ClientOverviewPage}
	 * @throws InterruptedException
	 */
	public ClientOverviewPage goToClientOverviewPageMaterialView() throws InterruptedException {
		scrollToTop();
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 60);
		try {
			if (isElementVisible(By.xpath(
					".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Clients & Accounts']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
				navigateToPage(
						By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Clients']"));
			} else {
				navigateToPageMaterialView(
						By.xpath(
								".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Clients & Accounts']"),
						By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Clients']"));

			}
		} catch (org.openqa.selenium.TimeoutException e) {
			if (isElementVisible(By.xpath(
					".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Clients']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
				navigateToPage(
						By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Clients']"));
			} else {
				navigateToPageMaterialView(
						By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Clients']"),
						By.xpath(
								".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Client Overview']"));
			}
		}
		return new ClientOverviewPage(webDriver);
	}

	/**
	 * Navigate to VFundsPage by directly clicking on vFunds or clicking Asset
	 * Management -> VFundsPage
	 * 
	 * @return VFundsPage
	 * @throws InterruptedException
	 */
	public VFundsPage goToVfundsPageMaterialView() throws InterruptedException {
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 60);
		if (isElementVisible(By.xpath(
				".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Asset Management']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
			navigateToPage(By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='vFunds']"));
		} else {
			navigateToPageMaterialView(
					By.xpath(
							".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Asset Management']"),
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='vFunds']"));

		}
		return new VFundsPage(webDriver);
	}

	/**
	 * Navigate to StrategyRulesPage by directly clicking on Strategy Rules or
	 * clicking Asset Management -> Strategy Rules
	 * 
	 * @return StrategyRulesPage
	 * @throws InterruptedException
	 */
	public StrategyRulesPage goToStrategyRulesPageMaterialView() throws InterruptedException {
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 60);
		if (isElementVisible(By.xpath(
				".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Asset Management']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
			navigateToPage(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Strategy Rules']"));
		} else {
			navigateToPageMaterialView(
					By.xpath(
							".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Asset Management']"),
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Strategy Rules']"));

		}
		return new StrategyRulesPage(webDriver);
	}

	/**
	 * Navigate to StructuredProductPage by directly clicking on Structured
	 * Product or clicking Asset Management -> Structured Product
	 * 
	 * @return StrategyRulesPage
	 * @throws InterruptedException
	 */
	public StructuredProductPage goToStructuredProductPageMaterialView() throws InterruptedException {
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 60);
		if (isElementVisible(By.xpath(
				".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Asset Management']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {
			navigateToPage(By.xpath(
					".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Structured Product']"));
		} else {
			navigateToPageMaterialView(
					By.xpath(
							".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Asset Management']"),
					By.xpath(
							".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Structured Product']"));

		}
		return new StructuredProductPage(webDriver);
	}

	/**
	 * Navigate to MarketEquitiesPage by directly clicking on Market Equities or
	 * clicking Asset Management -> Market Equities
	 * 
	 * @return {@link MarketEquitiesPage}
	 * @throws InterruptedException
	 */
	public MarketEquitiesPage goToMarketEquitiesPageMaterialView() throws InterruptedException {
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 60);
		if (isElementVisible(By.xpath(
				".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Asset Management']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {

			navigateToPage(By
					.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Market Equities']"));
		} else {
			navigateToPageMaterialView(
					By.xpath(
							".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Asset Management']"),
					By.xpath(
							".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Market Equities']"));

		}
		return new MarketEquitiesPage(webDriver);
	}

	/**
	 * Navigate to MarketRatesPage by directly clicking on Market Rates or
	 * clicking Asset Management -> Market Rates
	 * 
	 * @return {@link MarketRatesPage}
	 * @throws InterruptedException
	 */
	public MarketRatesPage goToMarketRatesPageMaterialView() throws InterruptedException {
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 60);
		if (isElementVisible(By.xpath(
				".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Asset Management']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {

			navigateToPage(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Market Rates']"));
		} else {
			navigateToPageMaterialView(
					By.xpath(
							".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Asset Management']"),
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Market Rates']"));
		}

		return new MarketRatesPage(webDriver);
	}

	public RiskProfilePage goToRiskProfilePageMaterialView() throws InterruptedException {
		waitForElementVisible(By.id("gwt-debug-MyMainMaterialView-mainPanel"), 60);
		if (isElementVisible(By.xpath(
				".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Plan']/following-sibling::ul[@class='navmat navmat-second-level collapse in']"))) {

			navigateToPage(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Risk Profile']"));
		} else {
			navigateToPageMaterialView(
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Plan']"),
					By.xpath(".//*[@id='gwt-debug-MyMainMaterialView-myMaterialMenuBarPanel']//a[.='Risk Profile']"));
		}
		return new RiskProfilePage(webDriver);
	}

	public void goToHelpPage() throws InterruptedException {
		boolean isMaterial = checkIfMaterial();
		if (isMaterial) {
			clickElement(By.xpath(".//a[@class='dropdown-toggle count-info' and @title='Help']/i"));
		} else {
			scrollToTop();
			clickElement(By.xpath(".//*[@id='gwt-debug-ImageMenuButton-displayImageLink' and @href='help']/img"));
		}
	}

	public CRMPage goToCRMPageGeneralUserDetailViewTab() throws InterruptedException {

		waitForElementVisible(By.xpath(".//*[@id='gwt-debug-GeneralUserDetailViewTab-hyperlink' and .='CRM']"), 5);

		clickElement(By.xpath(".//*[@id='gwt-debug-GeneralUserDetailViewTab-hyperlink' and .='CRM']"));
		return new CRMPage(webDriver);
	}

	public boolean checkIfMaterial() throws InterruptedException {
		wait(Settings.WAIT_SECONDS);

		Boolean isMaterial = false;
		Boolean isMaterialURL = webDriver.getCurrentUrl().indexOf("MATERIAL") > -1;

		try {

			waitForElementVisible(By.xpath(".//*[@id='page-wrapper']/div[1]/nav/div/a/i"), 15);
			isMaterial = true;

		} catch (Exception e) {

			if (isMaterialURL) {
				isMaterial = true;
			} else {
				isMaterial = false;
				log("isClassic");
			}
		}
		return isMaterial;

	}
}
