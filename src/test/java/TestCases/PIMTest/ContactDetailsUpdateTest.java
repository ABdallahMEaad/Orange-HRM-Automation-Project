package TestCases.PIMTest;

import Data.dataProv;
import DriverFactory.Base;
import Page.LoginPage.LoginPage;
import Page.PIMPage.AddEmployeePage;
import Page.PIMPage.ContactDetailsPage;
import Page.PIMPage.EditMyInfoPage;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ContactDetailsUpdateTest extends Base {


@BeforeMethod
public void initalizePages() {
    loginPage.loginMethod("Ahmed@123","Ahmed@123");
    editMyInfoPage.clickPersonalDetailsTab();
    contactDetailsPage.clickContactDetailsTab();
}




    @Test(dataProvider = "addressData", dataProviderClass = dataProv.class)
    @Description("Verify that the user can update address and contact details successfully.")
    public void verifyUpdateAddress(
            String street1,
            String street2,
            String city,
            String state,
            String zipCode,
            String country,
            String homeTelephone,
            String mobile,
            String workTelephone,
            String workEmail,
            String otherEmail) {

        contactDetailsPage.enterAddress(
                street1,
                street2,
                city,
                state,
                zipCode,
                country,
                homeTelephone,
                mobile,
                workTelephone,
                workEmail,
                otherEmail
        );

        Assert.assertEquals(
                contactDetailsPage.getSuccessMessage().getText(),
                "Successfully Updated",
                "Address and contact details were not updated successfully."
        );
    }


}
