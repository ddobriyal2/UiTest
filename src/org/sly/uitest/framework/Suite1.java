package org.sly.uitest.framework;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.sly.uitest.sections.accesstoken.AccessTokenTest;
import org.sly.uitest.sections.accesstoken.ApiTest;
import org.sly.uitest.sections.accounts.AbstractAccountsTest;
import org.sly.uitest.sections.accounts.AccountOverviewAndSearchTest;
import org.sly.uitest.sections.accounts.AccountTest;
import org.sly.uitest.sections.accounts.CashflowTest;
import org.sly.uitest.sections.accounts.InvestorTest;
import org.sly.uitest.sections.accounts.PortfolioAnalysisTest;
import org.sly.uitest.sections.accounts.PortfolioChangeTest;
import org.sly.uitest.sections.accounts.PortfolioCompareTest;
import org.sly.uitest.sections.accounts.PortfolioManualHistoryTest;
import org.sly.uitest.sections.accounts.PortfolioOverviewTest;
import org.sly.uitest.sections.accounts.PortfolioRebalanceTest;
import org.sly.uitest.sections.accounts.SubAccountsTest;
import org.sly.uitest.sections.bankAustria.bankAustriaTest;
import org.sly.uitest.sections.basesystem.BaseSystemTest;
import org.sly.uitest.sections.basesystem.Getalltags;
import org.sly.uitest.sections.bulkoperation.BulkoperationsTest;
import org.sly.uitest.sections.citiFundExplorer.CitiFundExplorerDocumentTest;
import org.sly.uitest.sections.citiFundExplorer.CitiFundExplorerUITest;
import org.sly.uitest.sections.clients.ClientOverviewTest;
import org.sly.uitest.sections.commission.AdvisorCommissionTest;
import org.sly.uitest.sections.commission.CommissionTest;
import org.sly.uitest.sections.commission.NewBusinessEventTest;
import org.sly.uitest.sections.companysettings.CustomAssetTest;
import org.sly.uitest.sections.companysettings.CustomTagTest;
import org.sly.uitest.sections.companysettings.WhiteLabelTest;
import org.sly.uitest.sections.crm.AlertForDeviationPortfolioAllocationTest;
import org.sly.uitest.sections.crm.AlertForModelPortfolioLinkChangeTest;
import org.sly.uitest.sections.crm.AlertForModelPortfolioRebalancingTest;
import org.sly.uitest.sections.crm.AlertTest;
import org.sly.uitest.sections.crm.CRMTest;
import org.sly.uitest.sections.crm.CalendarTest;
import org.sly.uitest.sections.crm.DocumentBinderTest;
import org.sly.uitest.sections.crm.NotesTest;
import org.sly.uitest.sections.crm.TasksTest;
import org.sly.uitest.sections.dashboard.DashboardTest;
import org.sly.uitest.sections.documentsdownload.DocumentsDownloadTest;
import org.sly.uitest.sections.email.EmailTemplateTest;
import org.sly.uitest.sections.hackingtest.HackingTest;
import org.sly.uitest.sections.illustration.IllustrationTest;
import org.sly.uitest.sections.illustration.PlatformIllustrationTest;
import org.sly.uitest.sections.investmentlist.InvestmentlistTest;
import org.sly.uitest.sections.investmentoptions.InvestmentOptionTest;
import org.sly.uitest.sections.modelportfolio.ModelPortfolioOverviewTest;
import org.sly.uitest.sections.modelportfolio.ModelPortfolioTest;
import org.sly.uitest.sections.modelportfolio.PortfolioManagementTest;
import org.sly.uitest.sections.operations.LastAccountValueDateTest;
import org.sly.uitest.sections.ordering.CreateAndCancelOrderTest;
import org.sly.uitest.sections.ordering.GroupAndBreakOrderTest;
import org.sly.uitest.sections.ordering.OrderingAndVFundTest;
import org.sly.uitest.sections.pageframework.MaterialFrameworkTest;
import org.sly.uitest.sections.pageframework.NavigationTest;
import org.sly.uitest.sections.pageframework.PrintButtonTest;
import org.sly.uitest.sections.pageframework.SideWidgetTest;
import org.sly.uitest.sections.productmaintenance.ProductMaintenanceTest;
import org.sly.uitest.sections.reporting.portfolio.GenerationTest;
import org.sly.uitest.sections.reporting.portfolio.InterFsHKTest;
import org.sly.uitest.sections.reporting.portfolio.PortfolioReportingTest;
import org.sly.uitest.sections.riskandwealthplanning.RiskProfileTest;
import org.sly.uitest.sections.salesprocess.CustomLoginPageTest;
import org.sly.uitest.sections.salesprocess.FSClientTest;
import org.sly.uitest.sections.salesprocess.SalesWizardTest;
import org.sly.uitest.sections.structuredproduct.StructuredProductTest;
import org.sly.uitest.sections.systemadmin.SystemAdminTest;
import org.sly.uitest.sections.transactionreconciliation.LinkingSystemTransactionTest;
import org.sly.uitest.sections.transactionreconciliation.TradeOrderFormTest;
import org.sly.uitest.sections.transactionreconciliation.TransactionHistoryTest;
import org.sly.uitest.sections.transactionreconciliation.TransactionReconciliationTest;
import org.sly.uitest.sections.transactionreconciliation.TransactionReconciliationUITest;
import org.sly.uitest.sections.vfund.VFundTest;



@RunWith(Suite.class)
@SuiteClasses({ 
	BaseSystemTest.class,
	CitiFundExplorerUITest.class,
	AccessTokenTest.class,
	ApiTest.class,
	AbstractAccountsTest.class,
	AccountOverviewAndSearchTest.class,
	AccountTest.class,
	CashflowTest.class,
	InvestorTest.class,
	PortfolioAnalysisTest.class,
	PortfolioChangeTest.class,
	PortfolioCompareTest.class,
	PortfolioManualHistoryTest.class,
	PortfolioOverviewTest.class,
	PortfolioRebalanceTest.class,
	SubAccountsTest.class,
	bankAustriaTest.class,
	BaseSystemTest.class,
	Getalltags.class,
	BulkoperationsTest.class,
	CitiFundExplorerDocumentTest.class,
	CitiFundExplorerUITest.class,
	ClientOverviewTest.class,
	AdvisorCommissionTest.class,
	CommissionTest.class,
	NewBusinessEventTest.class,
	CustomAssetTest.class,
	CustomTagTest.class,
	WhiteLabelTest.class,
	AlertForDeviationPortfolioAllocationTest.class,
	AlertForModelPortfolioLinkChangeTest.class,
	AlertForModelPortfolioRebalancingTest.class,
	AlertTest.class,
	CalendarTest.class,
	CRMTest.class,
	DocumentBinderTest.class,
	NotesTest.class,
	TasksTest.class,
	DashboardTest.class,
	DocumentsDownloadTest.class,
	EmailTemplateTest.class,
	HackingTest.class,
	IllustrationTest.class,
	PlatformIllustrationTest.class,
	InvestmentlistTest.class,
	InvestmentOptionTest.class,
	ModelPortfolioOverviewTest.class,
	ModelPortfolioTest.class,
	PortfolioManagementTest.class,
	LastAccountValueDateTest.class,
	CreateAndCancelOrderTest.class,
	GroupAndBreakOrderTest.class,
	OrderingAndVFundTest.class,
	MaterialFrameworkTest.class,
	NavigationTest.class,
	PrintButtonTest.class,
	SideWidgetTest.class,
	ProductMaintenanceTest.class,
	GenerationTest.class,
	InterFsHKTest.class,
	PortfolioReportingTest.class,
	RiskProfileTest.class,
	CustomLoginPageTest.class,
	FSClientTest.class,
	NewBusinessEventTest.class,
	SalesWizardTest.class,
	StructuredProductTest.class,
	SystemAdminTest.class,
	LinkingSystemTransactionTest.class,
	TradeOrderFormTest.class,
	TransactionHistoryTest.class,
	TransactionReconciliationTest.class,
	TransactionReconciliationUITest.class,
	VFundTest.class,
	
	
})
public class Suite1 {

}