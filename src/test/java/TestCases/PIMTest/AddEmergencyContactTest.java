package TestCases.PIMTest;

import Data.dataProv;
import DriverFactory.Base;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AddEmergencyContactTest extends Base {

    @BeforeMethod
    public void initalizePages() {
        loginPage.loginMethod("Ahmed@123","Ahmed@123");
        editMyInfoPage.clickPersonalDetailsTab();
        emergencyContactsPAge.clickEmergencyContactsTab();
    }




    @Test(dataProvider = "addEmergencyContactData", dataProviderClass = dataProv.class)
    @Description("Verify that the user can add emergency contacts successfully.")
    public void addEmergencyContact(String name, String relationship,
                                    String homeTelephone, String mobile,
                                    String workTelephone) {
        emergencyContactsPAge.clickAddEmergencyContactButton();
        emergencyContactsPAge.setAddEmergencyContactMethod(
                name, relationship, homeTelephone, mobile, workTelephone);

        Assert.assertTrue(emergencyContactsPAge.getSuccessMessage().isDisplayed(), "Emergency contact was not added successfully.");
    }
}
