package Page.AdminPage;

import Page.BasePage;
import Page.PIMPage.AddEmployeePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AdminPage extends BasePage {


    private final By adminTab = By.xpath("//a[contains(@href,'/admin')]");
    private final By usernameFilterField = By.xpath("//label[normalize-space()='Username']/following::input[1]");
    private final By searchButton = By.xpath("//button[normalize-space()='Search']");
    private final By firstNameColumn = By.xpath("//div[contains(@class,'oxd-table-row')]/div[@role='cell'][4]/div");
    private final By userRoleColumn = By.xpath("//div[contains(@class,'oxd-table-row')]/div[@role='cell'][3]/div");
    private By editButtonByUsername(String username) {
        return By.xpath(
                "//div[contains(@class,'oxd-table-row') "
                        + "and .//div[@role='cell'][2][normalize-space()='" + username + "']]"
                        + "//i[contains(@class,'bi-pencil-fill')]/ancestor::button"
        );
    }     private final By userRoleDropdown =
            By.xpath("//div[contains(@class,'oxd-select-text')]//div[contains(@class,'oxd-select-text-input')]");
    private final By essOption =
            By.xpath("//div[contains(@class,'oxd-select-option')]//span[normalize-space()='ESS']");
    private final By adminOption =
            By.xpath("//div[contains(@class,'oxd-select-option')]//span[normalize-space()='Admin']");
    private final By saveButton = By.xpath("//button[@type='submit']");
    public AdminPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getAdminTab() {
        return findElement(adminTab);
    }

    public WebElement getUsernameFilterField() {
        return findElement(usernameFilterField);
    }

    public WebElement getSearchButton() {
        return findElement(searchButton);
    }

    public WebElement getUserRoleDropdown() {
        return findElement(userRoleDropdown);
    }
    public WebElement getEssOption() {
        return findElement(essOption);
    }
    public WebElement getAdminOption() {
        return findElement(adminOption);
    }


    public List<WebElement> getFirstNameColumn() {
        return driver.findElements(firstNameColumn);
    }
    public List<WebElement> findElements(By locator, Duration timeout) {
        return new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }


    public WebElement getSaveButton() {
        return findElement(saveButton);
    }
    public void clickSaveButton() {
        getSaveButton().click();
    }

    public WebElement getUserRoleColumn() {
        return findElement(userRoleColumn);
    }

    public void getUserRoleColumnText() {
        getUserRoleColumn().getText();
    }

    public void selectESSOption() {
        getUserRoleDropdown().click();
        getEssOption().click();
    }

    public void selectAdminOption() {
        getUserRoleDropdown().click();
        getAdminOption().click();
    }

    public void clickGoToAdminTab() {
        getAdminTab().click();
    }

    public void searchUserByUsername(String username) {
        getUsernameFilterField().clear();
        getUsernameFilterField().sendKeys(username);
        getSearchButton().click();
    }

    public void clickEditButtonByUsername(String username) {
        findElement(editButtonByUsername(username), Duration.ofSeconds(20)).click();
    }

}