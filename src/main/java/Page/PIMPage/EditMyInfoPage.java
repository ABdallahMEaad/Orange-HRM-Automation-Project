package Page.PIMPage;

import Page.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;

public class EditMyInfoPage extends BasePage {

    private static final Logger log =
            LogManager.getLogger(EditMyInfoPage.class);

    private final By personalDetailsTab =
            By.xpath("//a[contains(@href,'/pim/viewMyDetails')]");

    private final By maritalStatusDropdown =
            By.xpath("//label[normalize-space()='Marital Status']" +
                    "/following::div[contains(@class,'oxd-select-wrapper')][1]" +
                    "//div[contains(@class,'oxd-select-text')]");

    private By maritalStatusOption(String status) {
        return By.xpath(
                "//div[contains(@class,'oxd-select-option') and normalize-space()='" + status + "']"
        );
    }

    private final By nationalityDropdown =
            By.xpath("//label[normalize-space()='Nationality']" +
                    "/following::div[contains(@class,'oxd-select-wrapper')][1]" +
                    "//div[contains(@class,'oxd-select-text')]");

    private By nationalityOption(String nationality) {
        return By.xpath(
                "//div[contains(@class,'oxd-select-option') and normalize-space()='" + nationality + "']"
        );
    }

    private final By saveButton =
            By.xpath("//form[.//label[normalize-space()='Marital Status']]" +
                    "//div[contains(@class,'oxd-form-actions')]" +
                    "//button[@type='submit' and normalize-space()='Save']");

    private final By maleRadioButton =
            By.xpath("//input[@type='radio' and @value='1']/parent::label");

    private final By femaleRadioButton =
            By.xpath("//input[@type='radio' and @value='2']/parent::label");

    private final By successMessage =
            By.xpath("//div[contains(@class,'oxd-toast-content--success')]" +
                    "//p[normalize-space()='Successfully Updated']");


    public EditMyInfoPage(WebDriver driver) {
        super(driver);
        log.info("Edit My Info page initialized");
    }


    public WebElement getPersonalDetailsTab() {
        return findElement(
                personalDetailsTab,
                Duration.ofSeconds(20)
        );
    }

    public WebElement getMaritalStatusDropdown() {
        return findElement(
                maritalStatusDropdown,
                Duration.ofSeconds(20)
        );
    }

    public WebElement getMaritalStatusOption(String status) {
        return findElement(
                maritalStatusOption(status),
                Duration.ofSeconds(20)
        );
    }

    public WebElement getNationalityDropdown() {
        return findElement(
                nationalityDropdown,
                Duration.ofSeconds(20)
        );
    }

    public WebElement getNationalityOption(String nationality) {
        return findElement(
                nationalityOption(nationality),
                Duration.ofSeconds(20)
        );
    }

    public WebElement getMaleRadioButton() {
        return findElement(
                maleRadioButton,
                Duration.ofSeconds(20)
        );
    }

    public WebElement getFemaleRadioButton() {
        return findElement(
                femaleRadioButton,
                Duration.ofSeconds(20)
        );
    }

    public WebElement getSaveButton() {
        return findElement(
                saveButton,
                Duration.ofSeconds(20)
        );
    }

    public WebElement getSuccessMessage() {
        return findElement(
                successMessage,
                Duration.ofSeconds(20)
        );
    }


    public void clickPersonalDetailsTab() {
        log.info("Clicking Personal Details tab");

        getPersonalDetailsTab().click();

        log.info("Personal Details tab opened successfully");
    }


    public void selectMaritalStatus(String status) {
        log.info("Selecting marital status: {}", status);

        getMaritalStatusDropdown().click();

        log.debug("Marital Status dropdown opened");

        getMaritalStatusOption(status).click();

        log.info(
                "Marital status '{}' selected successfully",
                status
        );
    }


    public void selectNationality(String nationality) {
        log.info("Selecting nationality: {}", nationality);

        getNationalityDropdown().click();

        log.debug("Nationality dropdown opened");

        getNationalityOption(nationality).click();

        log.info(
                "Nationality '{}' selected successfully",
                nationality
        );
    }


    public void selectGender(String gender) {
        log.info("Selecting gender: {}", gender);

        if (gender.equalsIgnoreCase("Male")) {

            getMaleRadioButton().click();

            log.info(
                    "Gender 'Male' selected successfully"
            );

        } else if (gender.equalsIgnoreCase("Female")) {

            getFemaleRadioButton().click();

            log.info(
                    "Gender 'Female' selected successfully"
            );

        } else {

            log.warn(
                    "Invalid gender provided: {}",
                    gender
            );

            throw new IllegalArgumentException(
                    "Unsupported gender: " + gender
            );
        }
    }


    public void clickSaveButton() {
        log.info("Clicking Save button");

        getSaveButton().click();

        log.info("Save button clicked successfully");
    }


    public boolean isUpdateSuccessful() {
        boolean displayed =
                getSuccessMessage().isDisplayed();

        if (displayed) {

            log.info(
                    "Update successful: 'Successfully Updated' message displayed"
            );

        } else {

            log.warn(
                    "Update success message was not displayed"
            );
        }

        return displayed;
    }
}