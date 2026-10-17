package TestCases.PIMTest;

import Data.dataProv;
import DriverFactory.Base;
import Page.LoginPage.LoginPage;
import Page.PIMPage.AddEmployeePage;
import io.qameta.allure.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Orange HRM ")
@Feature("Employee Creation and Role Assignment (PIM + Admin)")
public class AddEmployeeTest extends Base {

    private static final Logger log =
            LogManager.getLogger(AddEmployeeTest.class);



    @BeforeMethod
    public void setUpPages() {

        log.info("Setting up Add Employee test");

        loginPage.loginMethod("Admin", "admin123");

        addEmployeePage.clickPimIcon();
        addEmployeePage.clickAddEmployeeButton();

        log.info("Add Employee page opened successfully");
    }

    @Test(
            dataProvider = "addEmployeeData",
            dataProviderClass = dataProv.class,
            groups = {"Smoke", "Regression", "PIM", "AddEmployees"}
    )
    @Description("Verify that a new employee can be added successfully using valid employee data from the JSON data provider.")
    @Severity(SeverityLevel.CRITICAL)
    public void addNewEmployee(
            String firstName,
            String middleName,
            String lastName,
            String username,
            String password,
            String confirmPassword) {

        log.info("Starting Add Employee test for username: {}", username);

        addEmployeePage.addNewEmployee(
                firstName,
                middleName,
                lastName,
                username,
                password,
                confirmPassword
        );

        Assert.assertTrue(
                addEmployeePage.getAssertEmployeeAddedSuccessfully().isDisplayed(),
                "Employee was not added successfully."
        );

        log.info("Employee '{}' added successfully", username);
    }

    @Test(groups = {"Smoke", "Regression", "PIM"})
    @Description("Verify that an employee can be added successfully without creating login details.")
    @Severity(SeverityLevel.NORMAL)
    public void addEmployeeWithoutLoginDetails() {

        log.info("Starting Add Employee without login details test");

        addEmployeePage.enterEmployeePersonalInformation(
                "Ahmed",
                "Mohamed",
                "Hassan"
        );

        addEmployeePage.saveEmployee();

        Assert.assertTrue(
                addEmployeePage.getAssertEmployeeAddedSuccessfully().isDisplayed(),
                "Employee was not added successfully without login details."
        );

        log.info("Employee added successfully without login details");
    }

    @Test(groups = {"Regression", "Negative", "PIM"})
    @Description("Verify that the system prevents creating an employee using an existing username.")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyDuplicateUsername() {

        log.info("Starting duplicate username validation");

        addEmployeePage.enterEmployeePersonalInformation(
                "Mohamed",
                "Ahmed",
                "Ali"
        );

        addEmployeePage.enableCreateLoginDetails();
        addEmployeePage.enterUsername("Admin");
        addEmployeePage.enterPassword("admin123");
        addEmployeePage.confirmPassword("admin123");
        addEmployeePage.saveEmployee();

        String actualMessage =
                addEmployeePage.getUsernameAlreadyExistsMessageText();

        Assert.assertEquals(
                actualMessage,
                "Username already exists",
                "Duplicate username validation message is incorrect."
        );

        log.info("Duplicate username validation passed");
    }

    @Test(groups = {"Regression", "Negative", "Validation", "PIM"})
    @Description("Verify that required validation messages are displayed when mandatory employee fields are empty.")
    @Severity(SeverityLevel.NORMAL)
    public void verifyRequiredFields() {

        log.info("Starting required fields validation");

        addEmployeePage.saveEmployee();

        String actualMessage =
                addEmployeePage.getRequiredMessageText();

        Assert.assertEquals(
                actualMessage,
                "Required",
                "Required validation message is not displayed."
        );

        log.info("Required fields validation passed");
    }

    @Test(groups = {"Regression", "Negative", "Validation", "PIM"})
    @Description("Verify that an error is displayed when password and confirm password do not match.")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyPasswordConfirmation() {

        log.info("Starting password confirmation validation");

        addEmployeePage.enterEmployeePersonalInformation(
                "Omar",
                "Mahmoud",
                "Ibrahim"
        );

        addEmployeePage.enableCreateLoginDetails();
        addEmployeePage.enterUsername("omarTestUser");
        addEmployeePage.enterPassword("Password123");
        addEmployeePage.confirmPassword("Password456");
        addEmployeePage.saveEmployee();

        String actualMessage =
                addEmployeePage.getPasswordsDoNotMatchMessageText();

        Assert.assertEquals(
                actualMessage,
                "Passwords do not match",
                "Password mismatch validation message is incorrect."
        );

        log.info("Password confirmation validation passed");
    }

    @Test(groups = {"Regression", "Negative", "Validation", "PIM"})
    @Description("Verify that username is required when Create Login Details is enabled.")
    @Severity(SeverityLevel.NORMAL)
    public void verifyEmptyUsername() {

        log.info("Starting empty username validation");

        addEmployeePage.enterEmployeePersonalInformation(
                "Youssef",
                "Hesham",
                "Mostafa"
        );

        addEmployeePage.enableCreateLoginDetails();
        addEmployeePage.enterPassword("Password123");
        addEmployeePage.confirmPassword("Password123");
        addEmployeePage.saveEmployee();

        String actualMessage =
                addEmployeePage.getRequiredMessageText();

        Assert.assertEquals(
                actualMessage,
                "Required",
                "Required message is not displayed for empty username."
        );

        log.info("Empty username validation passed");
    }

    @Test(groups = {"Regression", "Negative", "Validation", "PIM"})
    @Description("Verify that password is required when Create Login Details is enabled.")
    @Severity(SeverityLevel.NORMAL)
    public void verifyEmptyPassword() {

        log.info("Starting empty password validation");

        addEmployeePage.enterEmployeePersonalInformation(
                "Karim",
                "Ashraf",
                "Samir"
        );

        addEmployeePage.enableCreateLoginDetails();
        addEmployeePage.enterUsername("karimTestUser");
        addEmployeePage.confirmPassword("Password123");
        addEmployeePage.saveEmployee();

        String actualMessage =
                addEmployeePage.getRequiredMessageText();

        Assert.assertEquals(
                actualMessage,
                "Required",
                "Required message is not displayed for empty password."
        );

        log.info("Empty password validation passed");
    }

    @Test(groups = {"Regression", "Negative", "Validation", "PIM"})
    @Description("Verify that confirm password is required when Create Login Details is enabled.")
    @Severity(SeverityLevel.NORMAL)
    public void verifyEmptyConfirmPassword() {

        log.info("Starting empty confirm password validation");

        addEmployeePage.enterEmployeePersonalInformation(
                "Abdelrahman",
                "Tarek",
                "Fathy"
        );

        addEmployeePage.enableCreateLoginDetails();
        addEmployeePage.enterUsername("abdelrahmanTestUser");
        addEmployeePage.enterPassword("Password123");
        addEmployeePage.saveEmployee();

        String actualMessage =
                addEmployeePage.getRequiredMessageText();

        Assert.assertEquals(
                actualMessage,
                "Required",
                "Required message is not displayed for empty confirm password."
        );

        log.info("Empty confirm password validation passed");
    }

    @Test(groups = {"Regression", "Negative", "Validation", "PIM"})
    @Description("Verify that first name is required when adding a new employee.")
    @Severity(SeverityLevel.NORMAL)
    public void verifyEmptyFirstName() {

        log.info("Starting empty first name validation");

        addEmployeePage.getMiddleNameField().sendKeys("Ahmed");
        addEmployeePage.getLastNameField().sendKeys("Mahmoud");
        addEmployeePage.saveEmployee();

        String actualMessage =
                addEmployeePage.getRequiredMessageText();

        Assert.assertEquals(
                actualMessage,
                "Required",
                "Required message is not displayed for empty first name."
        );

        log.info("Empty first name validation passed");
    }

    @Test(groups = {"Regression", "Negative", "Validation", "PIM"})
    @Description("Verify that last name is required when adding a new employee.")
    @Severity(SeverityLevel.NORMAL)
    public void verifyEmptyLastName() {

        log.info("Starting empty last name validation");

        addEmployeePage.getFirstNameField().sendKeys("Mostafa");
        addEmployeePage.getMiddleNameField().sendKeys("Mohamed");
        addEmployeePage.saveEmployee();

        String actualMessage =
                addEmployeePage.getRequiredMessageText();

        Assert.assertEquals(
                actualMessage,
                "Required",
                "Required message is not displayed for empty last name."
        );

        log.info("Empty last name validation passed");
    }
}

