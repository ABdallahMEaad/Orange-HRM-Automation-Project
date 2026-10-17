package TestCases.PIMTest;

import DriverFactory.Base;
import Page.AdminPage.AdminPage;
import Page.LoginPage.LoginPage;
import Page.PIMPage.EditMyInfoPage;
import Page.PIMPage.SearchPage;
import io.qameta.allure.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Orange HRM ")
@Feature("Employee updates personal info and HR verifies it.")
public class EditMyInfoTest extends Base {

    private static final Logger log =
            LogManager.getLogger(EditMyInfoTest.class);

    public String AdminUsername;
    public String AdminPassword;
    public String EmployeeUsernameAndPassword;
    public String EmployeeUsername;
    public String Gender;
    public String Nationality;
    public String MaritalStatus;

    @BeforeMethod
    public void setUpPages() {

        log.info("Setting up Edit My Info test");


        AdminUsername = "Admin";
        AdminPassword = "admin123";

        EmployeeUsername = "Abdallah@123";
        EmployeeUsernameAndPassword = "Abdallah@123";

        Gender = "Male";
        Nationality = "Egyptian";
        MaritalStatus = "Single";
    }

    @Test(
            dependsOnGroups = "AddEmployees",
            groups = "EditEmployees"
    )
    @Description("Verify that an employee can successfully edit their personal information, including gender, nationality, and marital status.")
    @Severity(SeverityLevel.NORMAL)
    public void testEditMyInfo() {

        log.info("Starting test: Edit My Info");

        loginPage.loginMethod(
                EmployeeUsernameAndPassword,
                EmployeeUsernameAndPassword
        );

        editMyInfoPage.clickPersonalDetailsTab();

        editMyInfoPage.selectGender(Gender);
        editMyInfoPage.selectNationality(Nationality);
        editMyInfoPage.selectMaritalStatus(MaritalStatus);

        editMyInfoPage.clickSaveButton();

        Assert.assertTrue(
                editMyInfoPage.getSuccessMessage().isDisplayed(),
                "Success message is not displayed after editing My Info."
        );

        log.info("My Info edited successfully");
    }

    @Test(
            dependsOnGroups = "EditEmployees"
    )
    @Description("Verify that the edited personal information is correctly displayed in the Admin view after an employee updates their details.")
    @Severity(SeverityLevel.NORMAL)
    public void ensureThatEditDataShownInAdminViewSuccessfully() {

        log.info("Starting test: Ensure that edited data is shown in Admin view successfully");

        loginPage.loginMethod(
                AdminUsername,
                AdminPassword
        );

        adminPage.clickGoToAdminTab();

        searchPage.searchEmployeeByName(EmployeeUsername);

        searchPage.clickEditByUsername(EmployeeUsername);

        Assert.assertTrue(
                searchPage.getMaritalStatusDropdown()
                        .getText()
                        .contains(MaritalStatus),
                "Marital Status field does not contain the expected value."
        );

        Assert.assertTrue(
                searchPage.getNationalityDropdown()
                        .getText()
                        .contains(Nationality),
                "Nationality field does not contain the expected value."
        );

        log.info("Edited data is correctly displayed in Admin view");
    }
}
