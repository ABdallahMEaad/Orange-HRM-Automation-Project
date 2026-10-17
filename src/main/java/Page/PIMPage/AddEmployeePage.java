package Page.PIMPage;

import Page.BasePage;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AddEmployeePage extends BasePage {

    private static final Logger log =
            LogManager.getLogger(AddEmployeePage.class);

    private final By pimIcon =
            By.xpath("//a[contains(@href,'/pim/viewPim')]");

    private final By addEmployeeButton =
            By.xpath("//button[contains(@class,'oxd-button--secondary') and normalize-space()='Add']");

    private final By addImageButton =
            By.xpath("//button[contains(@class,'employee-image-action')]");

    private final By firstNameField =
            By.name("firstName");

    private final By middleNameField =
            By.name("middleName");

    private final By lastNameField =
            By.name("lastName");

    private final By switchButtonToCreateLoginDetails =
            By.xpath("//span[contains(@class,'oxd-switch-input')]");

    private final By usernameField =
            By.xpath("//label[normalize-space()='Username']/following::input[1]");

    private final By passwordField =
            By.xpath("//label[normalize-space()='Password']/following::input[@type='password'][1]");

    private final By confirmPasswordField =
            By.xpath("//label[normalize-space()='Confirm Password']/following::input[@type='password'][1]");

    private final By saveButton =
            By.xpath("//button[@type='submit' and normalize-space()='Save']");

    private final By cancelButton =
            By.xpath("//button[@type='button' and normalize-space()='Cancel']");

    private final By assertEmployeeAddedSuccessfully =
            By.xpath("//div[contains(@class,'oxd-date-input')]//input");

    private final By usernameAlreadyExistsMessage =
            By.xpath("//span[normalize-space()='Username already exists']");

    private final By requiredMessage =
            By.xpath("//span[normalize-space()='Required']");

    private final By passwordsDoNotMatchMessage =
            By.xpath("//span[normalize-space()='Passwords do not match']");

    public AddEmployeePage(WebDriver driver) {
        super(driver);
        log.info("AddEmployeePage initialized successfully");
    }

    @Step("Navigate to PIM page")
    public void clickPimIcon() {
        log.info("Navigating to PIM page");
        getPimIcon().click();
        log.info("PIM page opened successfully");
    }

    @Step("Open Add Employee page")
    public void clickAddEmployeeButton() {
        log.info("Opening Add Employee page");
        getAddEmployeeButton().click();
        log.info("Add Employee page opened successfully");
    }

    @Step("Enter employee personal information: {firstName} {middleName} {lastName}")
    public void enterEmployeePersonalInformation(
            String firstName,
            String middleName,
            String lastName) {

        log.info("Entering employee personal information");

        log.debug("Entering First Name: {}", firstName);
        getFirstNameField().sendKeys(firstName);

        log.debug("Entering Middle Name: {}", middleName);
        getMiddleNameField().sendKeys(middleName);

        log.debug("Entering Last Name: {}", lastName);
        getLastNameField().sendKeys(lastName);

        log.info("Employee personal information entered successfully");
    }

    @Step("Enable Create Login Details")
    public void enableCreateLoginDetails() {
        log.info("Enabling Create Login Details");
        getSwitchButtonToCreateLoginDetails().click();
        log.info("Create Login Details enabled successfully");
    }

    @Step("Enter login username: {username}")
    public void enterUsername(String username) {
        log.info("Entering username: {}", username);
        getUsernameField().sendKeys(username);
        log.info("Username entered successfully");
    }

    @Step("Enter password")
    public void enterPassword(String password) {
        log.info("Entering password");
        getPasswordField().sendKeys(password);
        log.info("Password entered successfully");
    }

    @Step("Confirm password")
    public void confirmPassword(String confirmPassword) {
        log.info("Entering confirm password");
        getConfirmPasswordField().sendKeys(confirmPassword);
        log.info("Confirm password entered successfully");
    }

    @Step("Save new employee")
    public void saveEmployee() {
        log.info("Clicking Save button");
        getSaveButton().click();
        log.info("Employee save request submitted successfully");
    }

    @Step("Add new employee")
    public void addNewEmployee(
            String firstName,
            String middleName,
            String lastName,
            String username,
            String password,
            String confirmPassword) {

        log.info("Starting Add Employee process");
        log.info("Employee Name: {} {} {}", firstName, middleName, lastName);
        log.info("Username: {}", username);

        enterEmployeePersonalInformation(firstName, middleName, lastName);
        enableCreateLoginDetails();
        enterUsername(username);
        enterPassword(password);
        confirmPassword(confirmPassword);
        saveEmployee();

        log.info("Add Employee process completed");
    }

    public WebElement getPimIcon() {
        log.debug("Finding PIM icon");
        return findElement(pimIcon);
    }

    public WebElement getAddEmployeeButton() {
        log.debug("Finding Add Employee button");
        return findElement(addEmployeeButton);
    }

    public WebElement getAddImageButton() {
        log.debug("Finding employee image button");
        return findElement(addImageButton);
    }

    public WebElement getFirstNameField() {
        log.debug("Finding First Name field");
        return findElement(firstNameField);
    }

    public WebElement getMiddleNameField() {
        log.debug("Finding Middle Name field");
        return findElement(middleNameField);
    }

    public WebElement getLastNameField() {
        log.debug("Finding Last Name field");
        return findElement(lastNameField);
    }

    public WebElement getSwitchButtonToCreateLoginDetails() {
        log.debug("Finding Create Login Details switch");
        return findElement(switchButtonToCreateLoginDetails);
    }

    public WebElement getUsernameField() {
        log.debug("Finding Username field");
        return findElement(usernameField);
    }

    public WebElement getPasswordField() {
        log.debug("Finding Password field");
        return findElement(passwordField);
    }

    public WebElement getConfirmPasswordField() {
        log.debug("Finding Confirm Password field");
        return findElement(confirmPasswordField);
    }

    public WebElement getSaveButton() {
        log.debug("Finding Save button");
        return findElement(saveButton);
    }

    public WebElement getCancelButton() {
        log.debug("Finding Cancel button");
        return findElement(cancelButton);
    }

    public WebElement getAssertEmployeeAddedSuccessfully() {
        log.debug("Finding employee name element");
        return findElement(assertEmployeeAddedSuccessfully);
    }

    public WebElement getUsernameAlreadyExistsMessage() {
        log.debug("Finding Username already exists message");
        return findElement(usernameAlreadyExistsMessage);
    }

    public WebElement getRequiredMessage() {
        log.debug("Finding Required message");
        return findElement(requiredMessage);
    }

    public WebElement getPasswordsDoNotMatchMessage() {
        log.debug("Finding Passwords do not match message");
        return findElement(passwordsDoNotMatchMessage);
    }

    public String getUsernameAlreadyExistsMessageText() {
        log.debug("Getting Username already exists message text");
        return getUsernameAlreadyExistsMessage().getText();
    }

    public String getRequiredMessageText() {
        log.debug("Getting Required message text");
        return getRequiredMessage().getText();
    }

    public String getPasswordsDoNotMatchMessageText() {
        log.debug("Getting Passwords do not match message text");
        return getPasswordsDoNotMatchMessage().getText();
    }
}