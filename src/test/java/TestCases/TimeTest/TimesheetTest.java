
package TestCases.TimeTest;

import DriverFactory.Base;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.List;

@Epic("Orange HRM")
@Feature("Timesheet Submission and Approval")
public class TimesheetTest extends Base {

    private static final Logger log =
            LogManager.getLogger(TimesheetTest.class);

    @Test(
            priority = 1,
            groups = {"Smoke", "Regression", "Time"}
    )
    @Description("Verify that the current timesheet week is displayed correctly.")
    @Severity(SeverityLevel.NORMAL)
    public void verifyCurrentTimesheetWeek() {

        loginAsEmployee();

        driver.get(
                "https://opensource-demo.orangehrmlive.com/web/index.php/time/viewMyTimesheet"
        );

        LocalDate today = LocalDate.now();

        timesheetPage.enterCurrentWeek();

        Assert.assertTrue(
                timesheetPage.isWeekShown(
                        today.getYear(),
                        today.getMonthValue(),
                        today.getDayOfMonth()
                ),
                "The current timesheet week was not displayed correctly."
        );

        log.info("Current timesheet week verified.");
    }

    @Test(
            priority = 2,
            groups = {"Regression", "Time"}
    )
    @Description("Verify that project, activity and one daily hour entry can be entered and saved.")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyTimesheetEntryAndSave() {

        loginAsEmployee();

        driver.get(
                "https://opensource-demo.orangehrmlive.com/web/index.php/time/viewMyTimesheet"
        );

        timesheetPage.clickEditAndWaitForNavigation();

        timesheetPage.selectCocaColaProject();

        String activity = timesheetPage.selectRandomActivity();

        Assert.assertFalse(
                activity.isBlank(),
                "An activity must be selected."
        );

        String enteredHours =
                timesheetPage.enterRandomHoursForToday();

        Assert.assertTrue(
                enteredHours.matches(
                        "^(?:[01]\\d|2[0-3]):[0-5]\\d$"
                ),
                "Invalid 24-hour time format: " + enteredHours
        );

        List<String> displayedHours =
                timesheetPage.getSavedHours();

        int todayIndex =
                LocalDate.now().getDayOfWeek().getValue() - 1;

        Assert.assertEquals(
                displayedHours.get(todayIndex),
                enteredHours,
                "The entered hours do not match today's timesheet value."
        );

        timesheetPage.clickSaveButton();

        Assert.assertTrue(
                timesheetPage.getEditButton().isDisplayed(),
                "The timesheet did not return to view mode after saving."
        );

        log.info(
                "Timesheet saved. Activity: {}, daily hours: {}, day index: {}",
                activity,
                enteredHours,
                todayIndex
        );
    }

    @Test(
            priority = 3,
            groups = {"Regression", "Time", "SubmitTimesheet"},
            dependsOnMethods = {"verifyTimesheetEntryAndSave"}
    )
    @Description("Verify that the employee can submit the timesheet.")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyTimesheetSubmission() {

        loginAsEmployee();

        driver.get(
                "https://opensource-demo.orangehrmlive.com/web/index.php/time/viewMyTimesheet"
        );

        timesheetPage.clickSubmitButton();

        timesheetPage.waitForSubmittedStatus();

        Assert.assertTrue(
                timesheetPage.isSubmittedStatusDisplayed(),
                "The timesheet status should be Submitted."
        );

        log.info("Timesheet submitted successfully.");
    }

    @Test(
            priority = 4,
            groups = {"Regression", "Time", "Approval"},
            dependsOnMethods = {"verifyTimesheetSubmission"}
    )
    @Description("Verify that the manager can view and approve the employee timesheet.")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyTimesheetApproval() {

        String employeeName =
                configHandler.getValue("employeeFirstName")
                        + " "
                        + configHandler.getValue("employeeLastName");

        loginAsAdmin();

        driver.get(
                "https://opensource-demo.orangehrmlive.com/web/index.php/time/viewEmployeeTimesheet"
        );

        timesheetPage.enterEmployeeName(employeeName);

        timesheetPage.clickViewEmployeeButton();

        Assert.assertFalse(
                timesheetPage.getViewProjectName().isBlank(),
                "The employee timesheet project should be displayed."
        );

        Assert.assertFalse(
                timesheetPage.getViewActivityName().isBlank(),
                "The employee timesheet activity should be displayed."
        );

        timesheetPage.clickApproveButton();

        timesheetPage.waitForApproval();

        Assert.assertEquals(
                timesheetPage.getApprovalStatus(),
                "Approved",
                "The timesheet was not approved successfully."
        );

        log.info(
                "Timesheet approved for employee: {}",
                employeeName
        );
    }
}
