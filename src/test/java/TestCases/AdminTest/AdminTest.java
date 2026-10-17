package TestCases.AdminTest;

import Data.dataProv;
import DriverFactory.Base;
import Page.AdminPage.AdminPage;
import Page.LoginPage.LoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Orange HRM ")
@Feature("Employee updates personal info and HR verifies it.")
public class AdminTest extends Base {

    private static final Logger log =
            LogManager.getLogger(AdminTest.class);



    private String username = "Abdallah@123";


    @Test(
            dependsOnGroups = "EditEmployees",
            description = "Verify that an administrator can search for an existing user by username.",
            groups = {"Smoke", "Regression", "Admin"}
    )
    @Description("Verify that an existing user can be successfully searched using the username filter on the Admin page.")
    @Severity(SeverityLevel.NORMAL)
    public void testSearchUserByUsername() {

        log.info("Starting test: Search User by Username");

        adminPage.searchUserByUsername(username);

        Assert.assertEquals(
                adminPage.getUsernameFilterField().getAttribute("value"),
                username,
                "Username filter field does not contain the expected username."
        );

        log.info(
                "User '{}' was successfully searched on the Admin page",
                username
        );

        log.info("Test completed: Search User by Username");
    }

    @Test(
            dependsOnGroups = "EditEmployees",
            description = "Verify that an administrator can edit an existing user's role.",
            groups = {"Smoke", "Regression", "Admin"}
    )
    @Description("Verify that an existing user's role can be changed from ESS to Admin successfully from the Admin page.")
    @Severity(SeverityLevel.CRITICAL)
    public void testEditUserRole() {

        log.info(
                "Starting test: Edit User Role for username '{}'",
                username
        );

        adminPage.searchUserByUsername(username);

        log.info("User '{}' found successfully", username);

        adminPage.clickEditButtonByUsername(username);

        log.info("Edit button clicked for user '{}'", username);

        adminPage.selectAdminOption();

        log.info("Admin role selected for user '{}'", username);

        adminPage.clickSaveButton();

        log.info("User '{}' saved successfully", username);

        adminPage.searchUserByUsername(username);

        Assert.assertTrue(
                adminPage.getUserRoleColumn()
                        .getText()
                        .contains("Admin"),
                "User role column does not contain the expected value 'Admin'."
        );

        log.info(
                "User '{}' role was successfully updated to Admin",
                username
        );

        log.info("Test completed: Edit User Role");
    }

    @Test(dataProvider = "addressData", dataProviderClass = dataProv.class)
    @Description("Verify that updated contact details are displayed correctly in Admin view.")
    public void ensureThatAddContactDetailsShownInAdminViewSuccessfully(
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

        log.info("Starting test: Verify contact details in Admin view");

        loginPage.loginMethod("Admin", "admin123");
        addEmployeePage.clickPimIcon();
        searchPage.searchEmployeeByName("Ahmed Mohamed");
        searchPage.clickEditByUsername("Ahmed Mohamed");

        contactDetailsPage.clickContactDetailsTab();

        Assert.assertEquals(contactDetailsPage.getStreet1Value(), street1, "Street 1 mismatch");
        Assert.assertEquals(contactDetailsPage.getStreet2Value(), street2, "Street 2 mismatch");
        Assert.assertEquals(contactDetailsPage.getCityValue(), city, "City mismatch");
        Assert.assertEquals(contactDetailsPage.getStateValue(), state, "State mismatch");
        Assert.assertEquals(contactDetailsPage.getZipCodeValue(), zipCode, "Zip code mismatch");
        Assert.assertEquals(contactDetailsPage.getCountryValue(), country, "Country mismatch");
        Assert.assertEquals(contactDetailsPage.getHomeTelephoneValue(), homeTelephone, "Home telephone mismatch");
        Assert.assertEquals(contactDetailsPage.getMobileValue(), mobile, "Mobile mismatch");
        Assert.assertEquals(contactDetailsPage.getWorkTelephoneValue(), workTelephone, "Work telephone mismatch");
        Assert.assertEquals(contactDetailsPage.getWorkEmailValue(), workEmail, "Work email mismatch");
        Assert.assertEquals(contactDetailsPage.getOtherEmailValue(), otherEmail, "Other email mismatch");

        log.info("Contact details are displayed correctly in Admin view");
    }

    @Test(dataProvider = "addEmergencyContactData",
            dataProviderClass = dataProv.class)
    public void ensureEmergencyContactDisplayedInAdminView(
            String name,
            String relationship,
            String homeTelephone,
            String mobile,
            String workTelephone) {

        loginPage.loginMethod("Admin", "admin123");
        addEmployeePage.clickPimIcon();
        searchPage.searchEmployeeByName("Ahmed Mohamed");
        searchPage.clickEditByUsername("Ahmed Mohamed");

        emergencyContactsPAge.clickEmergencyContactsTab();

        Assert.assertTrue(
                emergencyContactsPAge.isEmergencyContactDisplayed(
                        name, relationship, homeTelephone, mobile, workTelephone
                ),
                "Emergency contact data was not found: " + name
        );
    }





}

