package Page.PIMPage;

import Page.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SearchPage extends BasePage {

    private static final Logger log =
            LogManager.getLogger(SearchPage.class);

    private final By employeeNameInput =
            By.xpath("//label[normalize-space()='Employee Name']" +
                    "/following::div[contains(@class,'oxd-autocomplete-wrapper')][1]" +
                    "//input");
    private final By searchButton = By.xpath("//button[@type='submit' and normalize-space()='Search']");
    private By editButtonByUsername(String username) {
        return By.xpath(
                "//div[@role='row']" +
                        "[.//div[@role='cell'][normalize-space()='" + username + "']]" +
                        "//i[contains(@class,'bi-pencil-fill')]/ancestor::button"
        );
    }
    private final By nationalityDropdown =
            By.xpath(
                    "//label[normalize-space()='Nationality']" +
                            "/following::div[contains(@class,'oxd-select-text')][1]" +
                            "//div[contains(@class,'oxd-select-text-input')]"
            );

    private final By maritalStatusDropdown =
            By.xpath(
                    "//label[normalize-space()='Marital Status']" +
                            "/following::div[contains(@class,'oxd-select-text')][1]" +
                            "//div[contains(@class,'oxd-select-text-input')]"
            );

    public WebElement getEmployeeNameInput() {
        return findElement(employeeNameInput);
    }

    public WebElement getSearchButton() {
        return findElement(searchButton);
    }
    public WebElement getNationalityDropdown() {
        return findElement(nationalityDropdown);
    }
    public WebElement getMaritalStatusDropdown() {
        return findElement(maritalStatusDropdown);
    }

    public void searchEmployeeByName(String employeeName) {
        log.info("Searching for employee with name: {}", employeeName);
        findElement(employeeNameInput).sendKeys(employeeName);
        findElement(searchButton).click();
        log.info("Search initiated for employee: {}", employeeName);
    }

    public void clickEditByUsername(String username) {
        log.info("Clicking edit button for username: {}", username);
        findElement(editButtonByUsername(username)).click();
        log.info("Edit button clicked for username: {}", username);
    }

    public SearchPage(WebDriver driver) {
        super(driver);
    }



}
