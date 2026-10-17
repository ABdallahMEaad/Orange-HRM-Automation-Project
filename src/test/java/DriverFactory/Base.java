package DriverFactory;

import Page.AdminPage.AdminPage;
import Page.LoginPage.LoginPage;
import Page.PIMPage.*;
import Utils.ConfigHandler;
import Utils.ExcelFileManger;
import Utils.JSONFileManger;
import Utils.TakeScreenShot;
import Utils.TestLogCapture;
import io.qameta.allure.Allure;
import io.qameta.allure.AttachmentOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.ByteArrayInputStream;
import java.util.Arrays;

public class Base {

    private static final Logger log = LogManager.getLogger(Base.class);

    public WebDriver driver;

    public ConfigHandler configHandler;
    public ContactDetailsPage contactDetailsPage;
    public LoginPage loginPage;
    public EditMyInfoPage editMyInfoPage;
    public SearchPage searchPage;
    public AdminPage adminPage;
    public AddEmployeePage addEmployeePage;
    public EmergencyContactsPAge emergencyContactsPAge;
    public JSONFileManger jsonFileManger;
    public ExcelFileManger excelFileManger;

    @BeforeMethod(alwaysRun = true)
    public void initializeDriver() {

        TestLogCapture.start();

        log.info("Starting test setup");

        configHandler = new ConfigHandler("src/main/resources/config.properties");

        String browserName = configHandler.getValue("browserName");
        String url = configHandler.getValue("url");

        log.info("Selected browser: {}", browserName);
        log.info("Application URL: {}", url);

        driver = ChooseDriver.getWebdriver(browserName);

        log.info("WebDriver initialized successfully");

        driver.manage().window().maximize();

        driver.get(url);

        log.info("Application opened successfully");
        log.info("Test setup completed successfully");

        loginPage = new LoginPage(driver);
        contactDetailsPage = new ContactDetailsPage(driver);
        editMyInfoPage = new EditMyInfoPage(driver);
        addEmployeePage = new AddEmployeePage(driver);
        adminPage = new AdminPage(driver);
        searchPage = new SearchPage(driver);
        emergencyContactsPAge = new EmergencyContactsPAge(driver);

    }


    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {

        try {

            if (result.getStatus() == ITestResult.FAILURE) {
                attachFailureEvidence(result);
            }

        } finally {

            log.info("Starting test teardown");

            try {
                if (configHandler != null) {
                    ChooseDriver.quitDriver(configHandler.getValue("browserName"));
                }
            } finally {
                TestLogCapture.stop();
            }
        }
    }

    private void attachFailureEvidence(ITestResult result) {

        String testName = result.getName();

        if (result.getParameters() != null && result.getParameters().length > 0) {
            testName += " " + Arrays.toString(result.getParameters());
        }

        log.error("TEST FAILED: {}", testName, result.getThrowable());

        if (driver != null) {

            byte[] screenshot = TakeScreenShot.takesScreenshotAsBytes(driver);

            if (screenshot != null) {
                Allure.attachment(
                        "Failure screenshot - " + testName,
                        "image/png",
                        new ByteArrayInputStream(screenshot),
                        new AttachmentOptions()
                );
            }
        }

        Allure.attachment(
                "Test log - " + testName,
                TestLogCapture.getLog()
        );
    }
}
