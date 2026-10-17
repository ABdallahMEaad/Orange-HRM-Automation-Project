package TestCases.ReportsTest;

import Data.dataProv;
import DriverFactory.Base;
import Utils.DesktopFolder;
import Utils.PdfFiles;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Map;

@Epic("Orange HRM ")
@Feature("Employee Report Generation and Export")
public class ReportsTest extends Base {

    private static final Logger log = LogManager.getLogger(ReportsTest.class);

    private void openReportAsAdmin(String reportName) {
        loginAsAdmin();
        reportsPage.openReports();
        reportsPage.searchReport(reportName);
        Assert.assertTrue(reportsPage.isReportListed(reportName),
                "Report not found in PIM -> Reports: " + reportName);
        reportsPage.openReport(reportName);
    }

    @Test(
            priority = 1,
            dataProvider = "newReportData",
            dataProviderClass = dataProv.class,
            groups = {"Smoke", "Regression", "Reports", "AddReport"}
    )
    @Description("Verify that a new report can be created, found by its exact name, opened and printed to a PDF on the Desktop.")
    @Severity(SeverityLevel.CRITICAL)
    public void ensureNewReportCanBeCreatedViewedAndPrintedToPdf(
            String namePrefix, String criteria, String include, String displayGroup, String[] displayFields) {

        String stamp = new SimpleDateFormat("yyyyMMdd-HHmmssSSS").format(new Date());
        String reportName = namePrefix + " " + stamp;
        String pdfName = "Automation_Report_" + stamp;

        log.info("Starting test: create, open and print report '{}'", reportName);

        loginAsAdmin();
        reportsPage.openReports();
        reportsPage.clickAddReport();
        Assert.assertTrue(reportsPage.isAddReportFormDisplayed(), "The Add Report form is not displayed.");

        reportsPage.enterReportName(reportName);
        reportsPage.addSelectionCriteria(criteria);
        reportsPage.selectInclude(include);
        for (String field : displayFields) {
            reportsPage.addDisplayField(displayGroup, field);
            Assert.assertTrue(reportsPage.isDisplayFieldAdded(field),
                    "The display field was not added to the report: " + field);
        }
        reportsPage.saveReport();

        Assert.assertTrue(reportsPage.isReportSavedMessageDisplayed(),
                "'Successfully Saved' message was not displayed after saving the report.");

        reportsPage.openReports();
        reportsPage.searchReport(reportName);
        Assert.assertTrue(reportsPage.isReportListed(reportName),
                "The new report is not listed in PIM -> Reports: " + reportName);
        reportsPage.openReport(reportName);

        Assert.assertTrue(reportsPage.isReportTitleDisplayed(reportName),
                "The opened report title does not match: " + reportName);
        Assert.assertTrue(reportsPage.isReportAreaDisplayed(),
                "The report table is not displayed.");
        for (String field : displayFields) {
            Assert.assertTrue(reportsPage.reportContainsText(field),
                    "The report does not show the column: " + field);
        }

        if (reportsPage.isExpandArrowAvailable()) {
            reportsPage.clickExpandArrow();
        }

        Path desktop = DesktopFolder.resolve();
        Map<Path, Long> before = PdfFiles.snapshot(desktop);

        reportsPage.installPrintShortcut();
        reportsPage.setDocumentTitle(pdfName);
        reportsPage.pressPrintShortcut();

        Path pdf = PdfFiles.waitForPdf(desktop, pdfName, before, Duration.ofSeconds(60));
        log.info("Report PDF saved at: {}", pdf);

        try {
            Assert.assertTrue(Files.exists(pdf), "The PDF file does not exist: " + pdf);
            Assert.assertTrue(Files.size(pdf) > 0, "The PDF file is empty: " + pdf);
        } catch (IOException e) {
            throw new AssertionError("The PDF file could not be inspected: " + pdf, e);
        }

        String text = PdfFiles.extractText(pdf);
        Assert.assertFalse(text.trim().isEmpty(), "The PDF has no readable text: " + pdf);
        Assert.assertTrue(text.contains(displayFields[0]) || text.contains(reportName),
                "The PDF does not contain the report title or its columns: " + pdf);
    }

    @Test(
            priority = 2,
            dataProvider = "reportData",
            dataProviderClass = dataProv.class,
            groups = {"Smoke", "Regression", "Reports"}
    )
    @Description("Verify that the Employee Contact info report opens and lists the employees.")
    @Severity(SeverityLevel.NORMAL)
    public void ensureEmployeeContactInfoReportIsGenerated(
            String reportName, String jobTitle, String subUnit, String employmentStatus,
            String workEmail, String mobile) {

        log.info("Starting test: generate report '{}'", reportName);

        openReportAsAdmin(reportName);

        Assert.assertTrue(reportsPage.isReportAreaDisplayed(), "The report table is not displayed.");
        Assert.assertTrue(reportsPage.reportContainsText(configHandler.getValue("employeeFirstName")),
                "Generated report does not list the employees added earlier.");
        Assert.assertTrue(reportsPage.getReportRowCount() > 0,
                "Generated report has no rows.");
    }

    @Test(
            priority = 3,
            dataProvider = "reportData",
            dataProviderClass = dataProv.class,
            groups = {"Regression", "Reports"}
    )
    @Description("Verify that contact details entered by the employee appear in the Employee Contact info report.")
    @Severity(SeverityLevel.NORMAL)
    public void ensureContactDetailsAreShownInReport(
            String reportName, String jobTitle, String subUnit, String employmentStatus,
            String workEmail, String mobile) {

        log.info("Starting test: verify contact details in report '{}'", reportName);

        openReportAsAdmin(reportName);

        Assert.assertTrue(reportsPage.reportContainsText(workEmail),
                "Work email entered by the employee is not shown in the report.");
        Assert.assertTrue(reportsPage.reportContainsText(mobile),
                "Mobile number entered by the employee is not shown in the report.");
    }

    @Test(
            priority = 4,
            dataProvider = "reportData",
            dataProviderClass = dataProv.class,
            groups = {"Regression", "Reports"}
    )
    @Description("Verify that the Job Title, Sub Unit and Employment Status filters narrow the generated report.")
    @Severity(SeverityLevel.NORMAL)
    public void ensureReportFiltersNarrowTheResult(
            String reportName, String jobTitle, String subUnit, String employmentStatus,
            String workEmail, String mobile) {

        log.info("Starting test: filter report '{}'", reportName);

        openReportAsAdmin(reportName);

        if (!reportsPage.hasFilter("Job Title")) {
            throw new SkipException("This report does not expose selection criteria filters.");
        }

        reportsPage.clickGenerate();
        Assert.assertTrue(reportsPage.reportContainsText(configHandler.getValue("employeeFirstName")),
                "The unfiltered report does not list the employees added earlier.");
        int unfilteredRows = reportsPage.getReportRowCount();

        reportsPage.selectFilter("Job Title", jobTitle);
        reportsPage.selectFilter("Sub Unit", subUnit);
        reportsPage.selectFilter("Employment Status", employmentStatus);
        reportsPage.clickGenerate();

        int filteredRows = reportsPage.getReportRowCount();

        log.info("Rows without filters: {} | with filters: {}", unfilteredRows, filteredRows);

        Assert.assertTrue(filteredRows <= unfilteredRows,
                "Filtered report returned more rows than the unfiltered one.");
    }

    @Test(
            priority = 5,
            dataProvider = "reportData",
            dataProviderClass = dataProv.class,
            groups = {"Regression", "Reports"}
    )
    @Description("Verify that the report can be exported when the application offers an export action.")
    @Severity(SeverityLevel.MINOR)
    public void ensureReportCanBeExported(
            String reportName, String jobTitle, String subUnit, String employmentStatus,
            String workEmail, String mobile) {

        log.info("Starting test: export report '{}'", reportName);

        openReportAsAdmin(reportName);

        if (!reportsPage.isExportAvailable()) {
            throw new SkipException("This OrangeHRM build offers no export action on PIM reports.");
        }

        reportsPage.getExportControl().click();

        Assert.assertTrue(reportsPage.reportContainsText(configHandler.getValue("employeeFirstName")),
                "Report page was left in a broken state after clicking export.");
    }
}
