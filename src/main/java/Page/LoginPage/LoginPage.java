package Page.LoginPage;

import Page.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {

    private final By userNameField = By.name("username");
    private final By passwordField = By.name("password");

    public final By loginButton =
            By.xpath("//button[contains(@class,'orangehrm-login-button')]");

    public final By alertErrorForInvalidCredentials =
            By.xpath("//div[@role='alert']//p[normalize-space()='Invalid credentials']");

    public final By usernameRequired = By.xpath(
            "//label[normalize-space()='Username']/ancestor::div[contains(@class,'oxd-input-group')]//span[normalize-space()='Required']"
    );

    public final By passwordRequired = By.xpath(
            "//label[normalize-space()='Password']/ancestor::div[contains(@class,'oxd-input-group')]//span[normalize-space()='Required']"
    );

    public final By dashboardText =
            By.cssSelector("h6.oxd-topbar-header-breadcrumb-module");


    public LoginPage(WebDriver driver) {
        super(driver);
    }


    public WebElement getUserNameField() {
        return findElement(userNameField);
    }

    public WebElement getPasswordField() {
        return findElement(passwordField);
    }

    public WebElement getLoginButton() {
        return findElement(loginButton);
    }

    public WebElement getAlertErrorForInvalidCredentials() {
        return findElement(alertErrorForInvalidCredentials);
    }

    public WebElement getUserNameRequiredMessage() {
        return findElement(usernameRequired);
    }

    public WebElement getPasswordRequiredMessage() {
        return findElement(passwordRequired);
    }

    public WebElement getDashboardText() {
        return findElement(dashboardText);
    }


    public void loginMethod(String username, String password) {
        getUserNameField().sendKeys(username);
        getPasswordField().sendKeys(password);
        getLoginButton().click();
    }


    public String alertErrorForInvalidCredentials() {
        return getAlertErrorForInvalidCredentials().getText();
    }

    public String userNameRequiredMessage() {
        return getUserNameRequiredMessage().getText();
    }

    public String passwordRequiredMessage() {
        return getPasswordRequiredMessage().getText();
    }

    public String dashboardText() {
        return getDashboardText().getText();
    }
}