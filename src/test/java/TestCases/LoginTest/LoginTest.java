package TestCases.LoginTest;

import Data.dataProv;
import DriverFactory.Base;
import Page.LoginPage.LoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Orange HRM ")
@Feature("Employee Creation and Role Assignment (PIM + Admin)")
public class LoginTest extends Base {



    @Test(
            dataProvider = "validLoginData",
            dataProviderClass = dataProv.class
    )
    @Description("Verify successful login with valid credentials and display of the Dashboard page.")
    public void ValidLogin(String username, String Password) {

        loginPage.loginMethod(username, Password);

        Assert.assertEquals(
                loginPage.dashboardText(),
                "Dashboard"
        );
    }

    @Test(
            dataProvider = "invalidLoginDataWithMessages",
            dataProviderClass = dataProv.class
    )
    @Description("Verify that invalid login credentials display the expected error message")
    public void inValidLogin(
            String username,
            String Password,
            String errormessage) {

        loginPage.loginMethod(username, Password);

        Assert.assertEquals(
                loginPage.alertErrorForInvalidCredentials(),
                errormessage
        );
    }

    @Test
    @Description("Verify that invalid login With Empty UserName display the expected error message")
    public void inValidLoginWithEmptyUserName() {

        loginPage.loginMethod("", "Admin123");

        Assert.assertEquals(
                loginPage.userNameRequiredMessage(),
                "Required"
        );
    }

    @Test
    @Description("Verify that invalid login With Empty Password display the expected error message")
    public void inValidLoginWithEmptyPassword() {

        loginPage.loginMethod("Admin", "");

        Assert.assertEquals(
                loginPage.passwordRequiredMessage(),
                "Required"
        );
    }
}
